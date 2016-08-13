package com.gamingutils.source;

import com.gamingutils.source.core.Decoder;
import com.gamingutils.source.core.Encoder;
import com.gamingutils.source.utils.Utils;

import java.awt.image.BufferedImage;

/**
 * Created by Jordan Laptop on 9/08/2016.
 */
public class Source {

	public static void main(String[] args) {
		String text ="abcdefghijklmnopqrstuvwxyz" +
				"ABCDEFGHIJKLMNOPQRSTUVWXYZ" +
				" .!?\'\"-,:;()";
		text = "The quick brown fox jumps over the lazy dog! Happy";
		text += "The quick brown fox jumps over the lazy dog! Happy";
		text += "The quick brown fox jumps over the lazy dog! Happy";
		text += "The quick brown fox jumps over the lazy dog! Happy";
		text += "The quick brown fox jumps over the lazy dog! Happy";
		text += "The quick brown fox jumps over the ";
		Encoder e = new Encoder();
		Decoder d = new Decoder();
		System.out.println(text.length() + " | " + Utils.convertGnawsToInt32(Utils.convertInt32ToGnaws(text.length())));
		byte dataplace = 0b001110111;
		System.out.println(dataplace + " | " + Utils.GnawToByte(Utils.ByteToGnaw(dataplace)));
		e.encodeString(text);
		/*
		for (int i = 0; i < text.length(); i++) {
			System.out.println(
					Utils.GnawToHex(
							Utils.scanCharacterSetForCharacter(
									DecriptionTypes.characterset_English,
									text.charAt(i)
							)
					)
			);
		}//*/
		BufferedImage mask = Utils.readInImage("starmask");
		BufferedImage image = e.dataToImage(1, mask);
		Utils.writeOutImage(e.maskImage(image, mask), "Test1");
		//Utils.writeOutImage(d.stripImage(image, mask), "Test2");
		d.imageToData(image, mask);
		System.out.println(d.decodedStrings.size());
		for (String s : d.decodedStrings) {
			System.out.println(s);
		}
	}


}
