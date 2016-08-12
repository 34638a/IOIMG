package com.gamingutils.source.utils;

/**
 * Created by Jordan Laptop on 12/08/2016.
 */
public class Gnaw {

	//Gnaw, Peck, Chew, Chop, Much, Gum
	public byte value;

	public Gnaw(byte value) {
		if (value > 63) {
			value = 63;
		}
		this.value = value;
	}
}
