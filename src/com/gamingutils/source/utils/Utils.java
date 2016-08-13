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

	public static Gnaw[] getPixelToGnaw(int value) {
		Gnaw[] g = new Gnaw[3];
		g[0] = new Gnaw(ByteToGnaw((byte) ((value & 0x00ff0000)>> 16)));
		g[1] = new Gnaw(ByteToGnaw((byte) ((value & 0x0000ff00) >> 8)));
		g[2] = new Gnaw(ByteToGnaw((byte) (value & 0x000000ff)));
		return g;
	}

	public static int convertGnawToPixelInt(byte r, byte b, byte g) {
		int x = Utils.GnawToByte(r) << 16,
			y = Utils.GnawToByte(b) << 8,
			z = Utils.GnawToByte(g);

		return x + y + z;
	}

	public static Gnaw[] convertInt32ToGnaws(int value) {
		int hexMask1 = 0x3F;
		int hexMask2 = 0xFC0;
		int hexMask3 = 0x3F000;
		int hexMask4 = 0xFC0000;
		int hexMask5 = 0x3F000000;
		int hexMask6 = 0xC0000000;
		Gnaw[] g = new Gnaw[6];

		g[0] = new Gnaw((byte) ((value & hexMask1) >> 0));
		g[1] = new Gnaw((byte) ((value & hexMask2) >> 6));
		g[2] = new Gnaw((byte) ((value & hexMask3) >> 12));
		g[3] = new Gnaw((byte) ((value & hexMask4) >> 18));
		g[4] = new Gnaw((byte) ((value & hexMask5) >> 24));
		g[5] = new Gnaw((byte) ((value & hexMask6) >> 30));

		return g;
	}

	public static int convertGnawsToInt32(Gnaw[] g) {
		return ((g[0].value + (g[1].value << 6) + (g[2].value << 12) + (g[3].value << 18) + (g[4].value << 24) + (g[5].value << 30)));
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

	public static String convertGnawsToString(byte characterset, Gnaw[] g) {
		String val = "";
		for (int i = g.length; i > -1; i--) {
			val += String.valueOf(DecriptionTypes.getCharacterSet(characterset).charAt(g[i].value));
		}
		return val;
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

	public static BufferedImage readInImage(String fileName) {
		File inFile = new File("./" + fileName + ".png");
		try
		{
			return ImageIO.read(inFile);
		}
		catch (IOException e)
		{
			System.out.println("An Error Occurred for File Read at Path: " + inFile.getAbsolutePath());
			e.printStackTrace();
		}
		return null;
	}
}
