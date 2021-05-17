package com.parkit.parkingsystem.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Scanner;

/**
 * Class: {@link InputReaderUtil} - Customer input data management service.<br>
 * <b>Project: <b> P3 - parking system - ParkIt<br>
 * 
 * @see Methods: {@link #readVehicleRegistrationNumber()},
 *      {@link #readSelection()}
 * 
 * @author Senthil
 */
public class InputReaderUtil {

	private static Scanner scan = new Scanner(System.in);
	private static final Logger logger = LogManager.getLogger("InputReaderUtil");

	/**
	 * {@link #readSelection()} The method do reading user input from Shell for
	 * selection of vehicle type
	 * 
	 * @exception Exception when error while reading user input on selection of
	 *                      vehicle type
	 */
	public int readSelection() {
		try {
			int input = Integer.parseInt(scan.nextLine());
			return input;
		} catch (Exception e) {
			logger.error("Error while reading user input from Shell", e);
			System.out.println("Error reading input. Please enter valid number for proceeding further");
			return -1;
		}
	}

	/**
	 * {@link #readVehicleRegistrationNumber()} The method do reading user input
	 * from Shell for entering of vehicle Registration number
	 * 
	 * @throws Exception                When error while reading user input on
	 *                                  selection of for vehicle registration number
	 * @throws IllegalArgumentException When invalid input provided
	 */
	public String readVehicleRegistrationNumber() throws Exception {
		try {
			String vehicleRegNumber = scan.nextLine();
			if (vehicleRegNumber == null || vehicleRegNumber.trim().length() == 0) {
				throw new IllegalArgumentException("Invalid input provided");
			}
			return vehicleRegNumber;
		} catch (Exception e) {
			logger.error("Error while reading user input from Shell", e);
			System.out.println("Error reading input. Please enter a valid string for vehicle registration number");
			throw e;
		}
	}

}
