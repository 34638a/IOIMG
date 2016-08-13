package com.gamingutils.source;

import com.gamingutils.source.core.Decoder;
import com.gamingutils.source.utils.DecriptionTypes;
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
		text += "The quick brown fox jumps over the lazy dog!";
		Decoder d = new Decoder(new BufferedImage(10, 10, BufferedImage.TYPE_INT_RGB));
		d.encodeString(text);
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
		d.storeData(1, "Test1", Utils.readInImage("starmask"));
	}


}
