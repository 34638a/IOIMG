package com.gamingutils.source.core;

import com.gamingutils.source.utils.DecriptionTypes;
import com.gamingutils.source.utils.Gnaw;
import com.gamingutils.source.utils.Utils;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jordan Laptop on 13/08/2016.
 */
public class Decoder {

	private static List<Gnaw> data = new ArrayList<>();
	public List<String> decodedStrings;
	public List<Integer> decodedInt;

	public Decoder() {
		this.decodedStrings = new ArrayList<>();
		this.decodedInt = new ArrayList<>();
	}

	public BufferedImage stripImage(BufferedImage imageDecode, BufferedImage imageMask) {
		int[] imgToDecode = ((DataBufferInt) imageDecode.getRaster().getDataBuffer()).getData();

		for (int x = 0; x < imageDecode.getWidth(); x++) {
			for (int y = 0; y < imageDecode.getHeight(); y++) {
				imgToDecode[x + y*imageDecode.getWidth()] -= imageMask.getRGB(x%imageMask.getWidth(), y%imageMask.getHeight());
			}
		}
		return imageDecode;
	}

	public void imageToData(BufferedImage dataImage) {
		BufferedImage fakeMask = new BufferedImage(dataImage.getWidth(), dataImage.getHeight(), BufferedImage.TYPE_INT_RGB);
		imageToData(dataImage, fakeMask);
	}

	public void imageToData(BufferedImage dataImage, BufferedImage maskImage) {
		BufferedImage dataStripped = stripImage(dataImage, maskImage);

		for (int y = 0; y < dataStripped.getHeight(); y++) {
			for (int x = 0; x < dataStripped.getWidth(); x++) {
				Gnaw[] g = Utils.getPixelToGnaw(dataStripped.getRGB(x, y));
				data.add(g[0]);
				data.add(g[1]);
				data.add(g[2]);
				//System.out.println("Pixel Int32: " + dataStripped.getRGB(x, y));
				//System.out.println("Pixel: " + x + ", " + y + "\n\t" + data.get(data.size() - 3).value + " | " + data.get(data.size() - 2).value + " | " + data.get(data.size() - 1).value + "\n____________________");
			}
		}

		for (int i = 0; i < data.size(); i++) {
			//System.out.println("Decoded Value: " + data.get(i).value);
		}

		filterData();
	}

	public void filterData() {
		System.out.println("Processing");
		for (int i = 0; i < data.size(); i++) {
			//System.out.println("POS-READ " + i);
			if (!processGnaw(data.get(i))) {
				break;
			}
		}
	}

	public byte readType = -1;
	public int lengthOfRead = -1;
	private Gnaw[] gnaws;

	public boolean processGnaw(Gnaw gnaw) {
		//System.out.println("Out " + gnaw.value);
		if (readType == 0) {
			return false;
		} else if (readType == -1) {
			readType = gnaw.value;
		} else {
			if (readType == DecriptionTypes.pString) {
				//System.out.println("a" + gnaw.value);
				if (lengthOfRead == -1) {
					readType = DecriptionTypes.pStringLength;
				} else {
					if (gnaws == null) {
						gnaws = new Gnaw[lengthOfRead];
					}
					lengthOfRead--;
					//System.out.println(lengthOfRead);
					if (lengthOfRead != 0) {
						gnaws[lengthOfRead] = gnaw;
					} else if (lengthOfRead == 0) {
						gnaws[lengthOfRead] = gnaw;
						decodedStrings.add(Utils.convertGnawsToString(DecriptionTypes.characterset_English, gnaws));
						//System.out.println(decodedStrings.size());
						readType = -1;
						lengthOfRead = -1;
						gnaws = null;
						System.out.println("Storing String");
					}
				}
			} else if (readType == DecriptionTypes.pStringLength || readType == DecriptionTypes.pInt32) {
				if (gnaws == null) {
					gnaws = new Gnaw[6];
					lengthOfRead = new Integer(0);
				}
				gnaws[lengthOfRead] = gnaw;
				//System.out.println(gnaws[lengthOfRead].value);
				lengthOfRead++;
				if (lengthOfRead == gnaws.length) {
					lengthOfRead = Utils.convertGnawsToInt32(gnaws);
					//System.out.println("b " +lengthOfRead);
					if (readType == DecriptionTypes.pStringLength) {
						readType = DecriptionTypes.pString;
					} else {
						System.out.println("Storing Int");
						decodedInt.add(lengthOfRead);
						lengthOfRead = -1;
						readType = -1;
					}
					gnaws = null;
				}
			}
		}
		//System.out.println("Data:\n\tValue: " + gnaw.value + "\n\tReadType: " + readType + "\n\tLength: " + lengthOfRead);
		return true;
	}
}
