package com.parkit.parkingsystem.util;

import java.util.Scanner;

/**
 * Class: {@link ScannerWrapper} - provide support to{@link InputReaderUtil} to
 * read keyboard input and write.<br>
 * <b>Project: </b> P3 - parking system - ParkIt<br>
 * 
 * @see Methods: {@link #readVehicleRegistrationNumber()},
 *      {@link #readSelection()}
 * 
 * @author Senthil
 */
public class ScannerWrapper {

	/**
	 * Initialize scanner to read keyboard input.
	 */
	private static final Scanner SCANNER = new Scanner(System.in, "UTF-8");

	/**
	 * Reads keyboard input.
	 *
	 * @return entered keyboard value.
	 */
	public String nextLine() {
		return SCANNER.nextLine();
	}
}
