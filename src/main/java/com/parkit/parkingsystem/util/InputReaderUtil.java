package com.parkit.parkingsystem.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Class: {@link InputReaderUtil} - Customer input data management service.<br>
 * <b>Project: </b> P3 - parking system - ParkIt<br>
 * 
 * @see Methods: {@link #readVehicleRegistrationNumber()},
 *      {@link #readSelection()}
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
	private static final Logger logger = LogManager.getLogger("InputReaderUtil");
	
	
	  /**
	   * The maximum number to enter in the menu choice selection.
	   */
	  private static final int MAX_INPUT_SELECT_VALUE_ALLOWED = 3;

	  /**
	   * Maximum number of characters allocated for a licence plate.
	   */
	  private static final int MAX_CHARACTER_LIMIT_FOR_REG_NUMBER = 8;
	  
	  
	  /**
	   * Constructor class for InputReaderUtil to initialize scannerWrapper.
	   *
	   * @param scanner scannerWrapper instance.
	   */
	  public InputReaderUtil(ScannerWrapper scanner) {
	    this.scannerWrapper = scanner;
	  }

	/**
	 * {@link #readSelection()} Read user input from Shell for vehicle type.
	 * 
	 * @return selection value or -1 for invalid value entry.
	 * @exception Exception when error while reading user input on selection of
	 *                      vehicle type.
	 */
	public int readSelection() {
		try {
			int input = Integer.parseInt(scannerWrapper.nextLine());
			
		   if (input >= 1 && input <= MAX_INPUT_SELECT_VALUE_ALLOWED) {
		      return input;
		   }      
		} catch (Exception e) {
			logger.error("Error while reading user input from Shell", e);
			System.out.println("Error reading input. Please enter valid "
					+ "number for proceeding further");
		}
		return -1;
	}

	/**
	 * {@link #readVehicleRegistrationNumber()} Read user input for vehicle
	 *  Registration number.
	 * @return vehicle registration number.
	 * @throws Exception                When error while input on vehicle
	 *  registration number.
	 * @throws IllegalArgumentException When invalid input provided.
	 */
	public String readVehicleRegistrationNumber() throws NullPointerException {
		try {
			String vehicleRegNumber = scannerWrapper.nextLine();
			if (vehicleRegNumber == null || vehicleRegNumber.trim().length() == 0
					|| vehicleRegNumber.trim().length() > MAX_CHARACTER_LIMIT_FOR_REG_NUMBER) {
				throw new IllegalArgumentException("Invalid input provided");
			}
			return vehicleRegNumber;
		} catch (Exception e) {
			logger.error("Error while reading user input from Shell", e);
			System.out.println("Error reading input. Please enter a valid "
					+ "string for vehicle registration number");
			throw e;
		}
	}

}
