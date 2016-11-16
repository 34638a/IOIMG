package com.gamingutils.source.utils;

/**
 * Created by Jordan Laptop on 10/08/2016.
 */
public class DecriptionTypes {

	public static final byte pInt32 = 1;
	public static final byte pInt64 = 2;
	public static final byte pLong = 2;
	public static final byte pString = 3;
	public static final byte pStringLength = 4;
	public static final byte pFloat = 5;
	public static final byte pDouble = 6;
	public static final byte pUnicodeString = 7;
	//Unicode Rules:
	//All of unicode can currently be displayed within 00FF FFFF or 4 Gnaws at 111-111 111-111 111-111 111-111
	//0ccccc = 0-31
	//10cccc_cccccc = 0-1023
	//110ccc_cccccc_cccccc =


	//Character Sets
		//English
	public static byte characterset_English = 0;
	public static String c_English =
			"abcdefghijklmnopqrstuvwxyz" +
			"ABCDEFGHIJKLMNOPQRSTUVWXYZ" +
			" .!?\'\n-,:;()";
		//Numbers and Symbols
	public static byte characterset_NumbersAndSymbols = 1;
	public static String c_NumbersAndSymbols =
			"1234567890@#$^&_~`-+*/%<>=" +
			"{}[]\"\t                    " +
			"            ";

	public static String getCharacterSet(int value) {
		if (value == characterset_English) {
			return c_English;
		} else if (value == characterset_NumbersAndSymbols) {
			return c_NumbersAndSymbols;
		} else {
			return c_English;
		}
	}
}
