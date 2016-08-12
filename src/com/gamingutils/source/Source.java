package com.gamingutils.source;

import com.gamingutils.source.utils.DecriptionTypes;
import com.gamingutils.source.utils.Utils;

/**
 * Created by Jordan Laptop on 9/08/2016.
 */
public class Source {

	public static void main(String[] args) {
		String text ="abcdefghijklmnopqrstuvwxyz" +
				"ABCDEFGHIJKLMNOPQRSTUVWXYZ" +
				" .!?\'\"-,:;()";
		for (int i = 0; i < text.length(); i++) {
			System.out.println(
					Utils.GnawToHex(
							Utils.scanCharacterSetForCharacter(
									DecriptionTypes.characterset_English,
									text.charAt(i)
							)
					)
			);
		}
	}


}
