package com.gamingutils.source.core;


import com.gamingutils.source.utils.DecriptionTypes;
import com.gamingutils.source.utils.Gnaw;
import com.gamingutils.source.utils.Utils;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jordan Laptop on 9/08/2016.
 */
public class Decoder {

	//Decoder Rules:
	//1. All images that are being decoded are to be interpreted as a RBG image with not A component
	//2. Any Pixel that is completely Black #000000 is considered to be the end of a statement
	//3. Images being decoded can hold a masking image that will be subtracted from the image first
	//4. Before masking the total for all values must not exceed FF for a single colour total

	private static BufferedImage MASK;
	private static List<Gnaw> data = new ArrayList<>();

	public Decoder(BufferedImage mask) {
		this.MASK = mask;
	}

	public void stripImage(BufferedImage imageDecode, BufferedImage imageMask) {
		int[] imgToDecode = ((DataBufferInt) imageDecode.getRaster().getDataBuffer()).getData();
		int[] imgAsMask = ((DataBufferInt) imageMask.getRaster().getDataBuffer()).getData();

		for (int x = 0; x < imageDecode.getWidth(); x++) {
			for (int y = 0; y < imageDecode.getHeight(); y++) {
				imgToDecode[x + y*imageDecode.getWidth()] -= imgAsMask[x%imageMask.getWidth() + (y*imageDecode.getWidth())%imageMask.getHeight()];
			}
		}
	}

	public void maskImage(BufferedImage imageDecode, BufferedImage imageMask) {
		int[] imgToDecode = ((DataBufferInt) imageDecode.getRaster().getDataBuffer()).getData();
		int[] imgAsMask = ((DataBufferInt) imageMask.getRaster().getDataBuffer()).getData();

		for (int x = 0; x < imageDecode.getWidth(); x++) {
			for (int y = 0; y < imageDecode.getHeight(); y++) {
				imgToDecode[x + y*imageDecode.getWidth()] += imgAsMask[x%imageMask.getWidth() + (y*imageDecode.getWidth())%imageMask.getHeight()];
			}
		}
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
									String.valueOf(value.charAt(i))
							)
					)
			);
		}
		return true;
	}

	public void encodeInt(int value) {
		data.add(new Gnaw(DecriptionTypes.pInt32));
		Gnaw[] vals = Utils.convertInt32ToGnaws(value);
		for (int i = 0; i < vals.length; i++) {
			data.add(vals[i]);
		}
	}

	private void setNull() {
		data.add(new Gnaw((byte) (0)));
	}

	private byte getDataPoint(int point) {
		try {
			return data.get(point).value;
		} catch (Exception e) {
			return 0;
		}
	}

	public void storeData(int columns, String dataName, BufferedImage maskImage) {
		setNull();
		if (columns < 1) {
			columns = 1;
		}
		int width = MASK.getWidth()*columns,
				pixels = ((data.size() + 3 - data.size()%3)/3),
				frame = (MASK.getHeight()*width),
				height = MASK.getHeight()*((pixels + frame - pixels%frame)/frame);
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		int[] img = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				int pos = x + y*width;
				img[pos] = Utils.convertGnawToPixelInt(
						getDataPoint(pos*3),
						getDataPoint(pos*3 + 1),
						getDataPoint(pos*3 + 2)
				);
			}
		}
		if (maskImage != null) {
			maskImage(image, maskImage);
		}
		Utils.writeOutImage(image, dataName);
	}
}
