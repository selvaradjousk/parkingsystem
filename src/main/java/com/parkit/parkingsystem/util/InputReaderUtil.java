package com.parkit.parkingsystem.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Class: {@link InputReaderUtil} - Input management service.<br>
 * <b>Project: </b> P3 - parking system - ParkIt<br>
 *
 * @author Senthil
 */
public class InputReaderUtil {

	/**
	 * Instance of ScannerWrapper.
	 */
	  private ScannerWrapper scannerWrapper = new ScannerWrapper();

	 /**
	 * Logger for InputReaderUtil class.
	 */
	private static final Logger LOGGER
	= LogManager.getLogger("InputReaderUtil");

	/**
	 * The maximum number to enter in the menu choice selection.
	 */
	private static final int MAX_INPUT_SELECT_VALUE_ALLOWED = 3;

	/**
	 * Maximum number of characters allocated for a license plate.
	 */
	private static final int MAX_CHARACTER_LIMIT_FOR_REG_NUMBER = 8;

	/**
	 * Constructor class for InputReaderUtil to
	 *  initialize scannerWrapper.
	 * @param scanner scannerWrapper instance.
	 */
	public InputReaderUtil(final ScannerWrapper scanner) {
		this.scannerWrapper = scanner;
	  }

	/**
	 * {@link #readSelection()}
	 * Read user input from Shell for vehicle type.
	 *
	 * @return selection value or -1 for invalid value entry.
	 * @exception Exception error reading input vehicle type.
	 */
	public int readSelection() {
		try {
			int input = Integer.parseInt(scannerWrapper.nextLine());

			if (input >= 1
					&& input
					<= MAX_INPUT_SELECT_VALUE_ALLOWED) {
		      return input;
		   }
		} catch (Exception e) {
			LOGGER.error("Error while reading user input"
					+ " from Shell", e);
			System.out.println("Error reading input."
					+ " Please enter valid number"
					+ " for proceeding further");
		}
		return -1;
	}

	/**
	 * {@link #readVehicleRegistrationNumber()}
	 *  Read input Registration number.
	 * @return vehicle registration number
	 * @throws Exception           Input on vehicle registration number
	 * @throws NullPointerException When invalid input provided
	 */
	public String readVehicleRegistrationNumber()
			throws NullPointerException {
		try {
			String vehicleRegNumber = scannerWrapper.nextLine();
			if (vehicleRegNumber == null
					|| vehicleRegNumber.trim().length()
					== 0
					|| vehicleRegNumber.trim().length()
					> MAX_CHARACTER_LIMIT_FOR_REG_NUMBER) {
				throw new IllegalArgumentException("Invalid"
						+ " input provided");
			}
			return vehicleRegNumber;
		} catch (Exception e) {
			LOGGER.error("Error while reading user input from "
					+ "Shell", e);
			System.out.println("Error reading input. Please"
					+ " enter a valid string for vehicle"
					+ " registration number");
			throw e;
		}
	}
}
