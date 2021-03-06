package com.gamingutils.source.core;


import com.gamingutils.source.utils.DecriptionTypes;
import com.gamingutils.source.utils.Gnaw;
import com.gamingutils.source.utils.Utils;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.DataBufferInt;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Jordan Laptop on 9/08/2016.
 */
public class Encoder {

	//Encoder Rules:
	//1. All images that are being decoded are to be interpreted as a RBG image with not A component
	//2. Any Pixel that is completely Black #000000 is considered to be the end of the script if there is not already a read in progress
	//3. Images being decoded can hold a masking image that will be subtracted from the image first
	//4. Before masking the total for all values must not exceed FF for a single colour total

	private static List<Gnaw> data = new ArrayList<>();

	public BufferedImage maskImage(BufferedImage imageDecode, BufferedImage imageMask) {
		int[] imgToDecode = ((DataBufferInt) imageDecode.getRaster().getDataBuffer()).getData();

		for (int x = 0; x < imageDecode.getWidth(); x++) {
			for (int y = 0; y < imageDecode.getHeight(); y++) {
				imgToDecode[x + y*imageDecode.getWidth()] += imageMask.getRGB(x%imageMask.getWidth(), y%imageMask.getHeight()); //imgAsMask[x%imageMask.getWidth() + (y*imageDecode.getWidth())%imageMask.getHeight()];
			}
		}
		return imageDecode;
	}

	public void dumpData() {
		data.clear();
	}

	public boolean encodeString(String value) {
		data.add(new Gnaw(DecriptionTypes.pString));
		encodeInt(value.length());
		for (int i = 0; i < value.length(); i++) {
			data.add(new Gnaw(
							Utils.scanCharacterSetForCharacter(
									DecriptionTypes.characterset_English,
									value.charAt(i)
							)
					)
			);
		}
		return true;
	}

	public void encodeInt(int value) {
		data.add(new Gnaw(DecriptionTypes.pInt32));
		Gnaw[] vals = Utils.convertInt32ToGnaws(value);
		Collections.addAll(data, vals);
	}

	private void setNull() {
		data.add(new Gnaw((byte) (0)));
	}

	private byte getDataPoint(int point) {
		try {
			//System.out.println(point + " | " + data.get(point).value);
			return data.get(point).value;
		} catch (Exception e) {
			//System.out.println(point + " | " + "NULL POINT");
			return 0;
		}
	}

	public BufferedImage dataToImage(int columns, BufferedImage maskImage) {
		setNull();
		if (columns < 1) {
			columns = 1;
		}
		System.out.println("___________________\n\nData Points To Encode: " + data.size() + "\n\n___________________");
		int width = maskImage.getWidth()*columns,
				pixels = ((data.size() + 3 - data.size()%3)/3),
				frame = (maskImage.getHeight()*width),
				height = maskImage.getHeight()*((pixels + frame - pixels%frame)/frame);
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		int[] img = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				int pos = y + x*height;
				img[pos] = Utils.convertGnawToPixelInt(
						getDataPoint(pos*3),
						getDataPoint(pos*3 + 1),
						getDataPoint(pos*3 + 2)

				);
				//System.out.println("Pixel: " + x + ", " + y + "\n\t" + getDataPoint(pos*3) + " | " + getDataPoint(pos*3 + 1) + " | " + getDataPoint(pos*3 + 2) + "\n____________________");
			}
		}
		return image;
	}
}
