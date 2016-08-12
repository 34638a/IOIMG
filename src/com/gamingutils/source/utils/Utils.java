package com.gamingutils.source.utils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by Jordan Laptop on 10/08/2016.
 */
public class Utils {

	public static byte ByteToGnaw(byte input) {
		return (byte) ((input&0xf0) >> 1 | (input&0x0f));
	}

	public static byte GnawToByte(byte input) {
		return (byte) ((input&0x38) << 1 | (input&0x7));
	}

	public static String ByteToHex(byte input) {
		return hexCharacter((byte)((input&0xf0)>>4)) + "|" + hexCharacter((byte)(input&0x0f));
	}

	public static String GnawToHex(byte input) {
		return ByteToHex(GnawToByte(input));
	}

	public static byte scanCharacterSetForCharacter(byte characterset, char character) {
		String characters = DecriptionTypes.getCharacterSet(characterset);
		for (byte i = 0; i < characters.length(); i++) {
			if (character == characters.charAt(i)) {
				return i;
			}
		}
		return -1;
	}

	public static int convertGnawToNumber(byte r, byte b, byte g) {
		int x = Utils.GnawToByte(r) << 16,
			y = Utils.GnawToByte(b) << 8,
			z = Utils.GnawToByte(g);

		return x + y + z;
	}

	public static String convertToStringCharacter(byte r, byte g, byte b) {
		return convertToStringCharacter(DecriptionTypes.characterset_English, r, g, b);
	}

	public static String convertToStringCharacter(byte characterset, byte r, byte g, byte b) {
		String str = "";
		str += String.valueOf(DecriptionTypes.getCharacterSet(characterset).charAt(r));
		str += String.valueOf(DecriptionTypes.getCharacterSet(characterset).charAt(g));
		str += String.valueOf(DecriptionTypes.getCharacterSet(characterset).charAt(b));
		return str;
	}

	private static String hexCharacter(byte input) {
		String val = "";
		switch (input) {
			case 0:
				val = "0";
				break;
			case 1:
				val = "1";
				break;
			case 2:
				val = "2";
				break;
			case 3:
				val = "3";
				break;
			case 4:
				val = "4";
				break;
			case 5:
				val = "5";
				break;
			case 6:
				val = "6";
				break;
			case 7:
				val = "7";
				break;
			case 8:
				val = "8";
				break;
			case 9:
				val = "9";
				break;
			case 10:
				val = "A";
				break;
			case 11:
				val = "B";
				break;
			case 12:
				val = "C";
				break;
			case 13:
				val = "D";
				break;
			case 14:
				val = "E";
				break;
			case 15:
				val = "F";
				break;
		}
		return val;
	}



	public static void writeOutImage(BufferedImage img, String fileName) {
		File outFile = new File("./" + fileName + ".png");
		System.out.println("Writing out");
		try {
			ImageIO.write(img, "png", outFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
