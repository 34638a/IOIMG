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

	public void imageToData(BufferedImage dataImage, BufferedImage maskImage) {
		BufferedImage dataStripped = stripImage(dataImage, maskImage);
		for (int x = 0; x < dataStripped.getWidth(); x++) {
			for (int y = 0; y < dataStripped.getWidth(); y++) {
				Gnaw[] g = Utils.getPixelToGnaw(dataStripped.getRGB(x, y));
				data.add(g[0]);
				data.add(g[1]);
				data.add(g[2]);
			}
		}
		filterData();
	}

	public void filterData() {
		System.out.println("Processing");
		for (int i = 0; i < data.size(); i++) {
			System.out.println("POS-READ " + i);
			processGnaw(data.get(i));
		}
	}

	public byte readType = -1;
	public int lengthOfRead = -1;
	private Gnaw[] gnaws;

	public void processGnaw(Gnaw gnaw) {
		//System.out.println("Data:\n\t" + "\n\t" + data.size() + "\n\tReadType: " + readType + "\n\tLength: " + lengthOfRead);

		if (readType == -1) {
			readType = gnaw.value;
		} else {
			if (readType == DecriptionTypes.pString) {
				if (lengthOfRead == -1) {
					readType = DecriptionTypes.pStringLength;
				} else {
					if (gnaws == null) {
						gnaws = new Gnaw[lengthOfRead];
					}
					lengthOfRead--;
					System.out.println(lengthOfRead);
					if (lengthOfRead != 0) {
						gnaws[lengthOfRead] = gnaw;
					} else if (lengthOfRead == 0) {
						gnaws[lengthOfRead] = gnaw;
						decodedStrings.add("TEST"); //Utils.convertGnawsToString(DecriptionTypes.characterset_English, gnaws));
						System.out.println(decodedStrings.size());
						readType = -1;
					}
				}
			} else if (readType == DecriptionTypes.pStringLength || readType == DecriptionTypes.pInt32) {
				if (gnaws == null) {
					gnaws = new Gnaw[7];
					lengthOfRead = new Integer(0);
				}
				gnaws[lengthOfRead] = gnaw;
				System.out.println(gnaws[lengthOfRead].value);
				lengthOfRead++;
				if (lengthOfRead == 7) {
					System.out.println("a " +lengthOfRead);
					for (int i = 0; i < gnaws.length; i++) {
						System.out.println(gnaws[i].value);
					}
					lengthOfRead = Utils.convertGnawsToInt32(gnaws);
					System.out.println("b " +lengthOfRead);
					if (readType == DecriptionTypes.pStringLength) {
						readType = DecriptionTypes.pString;
					} else {
						decodedInt.add(lengthOfRead);
						readType = -1;
					}
					gnaws = null;
				}
			}
		}
	}
}
