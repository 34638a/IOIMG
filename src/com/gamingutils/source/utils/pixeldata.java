package com.gamingutils.source.utils;

/**
 * Created by Jordan Laptop on 10/08/2016.
 */
public class pixeldata {

	private byte r = 0, g = 0, b = 0;

	public void pixeldata(int dataIn) {
		r = Utils.ByteToShortByte((byte) ((dataIn & 0x00ff0000) >> 16));
		g = Utils.ByteToShortByte((byte) ((dataIn & 0x0000ff00) >> 8));
		b = Utils.ByteToShortByte((byte) ((dataIn & 0x000000ff)));
	}

	public int convertToNumber(boolean user, boolean useb, boolean useg) {
		int x = 0, y = 0, z = 0;
		if (user) {
			x = Utils.ShortByteToByte(r) << 16;
		}
		if (useg) {
			y = Utils.ShortByteToByte(g) << 8;
		}
		if (useb) {
			z = Utils.ShortByteToByte(b);
		}
		return x + y + z;
	}


}
