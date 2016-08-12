package com.gamingutils.source.core;


import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

/**
 * Created by Jordan Laptop on 9/08/2016.
 */
public class Decoder {

	//Decoder Rules:
	//1. All images that are being decoded are to be interpreted as a RBG image with not A component
	//2. Any Pixel that is completely Black #000000 is considered to be the end of a statement
	//3. Images being decoded can hold a masking image that will be subtracted from the image first
	//4. Before masking the total for all values must not exceed FF for a single colour total


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

	public int convertToPixel(int value) {


		return 0;
	}
}
