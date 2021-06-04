package com.parkit.parkingsystem.service;

import com.parkit.parkingsystem.dao.ParkingSpotDAO;
import com.parkit.parkingsystem.dao.TicketDAO;
import com.parkit.parkingsystem.util.InputReaderUtil;
import com.parkit.parkingsystem.util.ScannerWrapper;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Class: {@link InteractiveShell} - Application workflow 
 * services.<br>
 * <b>Project: </b> P3 - parking system - ParkIt<br>
 * 
 * @see Methods: {@link #loadInterface()}, {@link #loadMenu()}
 * 
 * @author Senthil
 */
public final class InteractiveShell {

	/**
	 * Logger InteractiveShell.
	 * 
	 */
	private static final Logger LOGGER 
	= LogManager.getLogger("InteractiveShell");

	 /**
	   * InteractiveShell constructor.
	   */
	  private InteractiveShell() {
	  }

	/**
	 * {@link #loadInterface()} This method loads 
	 * the subsequent interface for the menu choice done
	 *  using Application Menu {@link #loadMenu()}.
	 * 
	 */
	public static void loadInterface() {
		LOGGER.info("App initialized!!!");
		System.out.println("Welcome to Parking System!");

		boolean continueApp = true;

		ScannerWrapper scannerWrapper = new ScannerWrapper();
		InputReaderUtil inputReaderUtil = new InputReaderUtil(scannerWrapper);
		ParkingSpotDAO parkingSpotDAO = new ParkingSpotDAO();
		TicketDAO ticketDAO = new TicketDAO();
		ParkingService parkingService = new ParkingService(inputReaderUtil,
				parkingSpotDAO, ticketDAO);

		while (continueApp) {
			loadMenu();
			int option = inputReaderUtil.readSelection();
		      final int incomingVehicle = 1;
		      final int exitingVehicle = 2;
		      final int exitingSystem = 3;
		      
			switch (option) {
			case incomingVehicle: 
				parkingService.processIncomingVehicle();
				break;
			case exitingVehicle: 
				parkingService.processExitingVehicle();
				break;
			case exitingSystem: 
				System.out.println("Exiting from the system!");
				continueApp = false;
				break;
				
				// default is unreachable and redundant code
				// because InputReaderUtil readSelection handles exception
				// for unsupported inputs. these codes can be removed
//			default:
//				System.out.println("Unsupported option. Please enter"
//					+ " a number corresponding to the provided menu");
			}
		}
	}

	/**
	 * {@link #loadMenu()} This method displays the menu choice
	 *  for using Parking Application - ParkIt.
	 * 
	 */
	private static void loadMenu() {
		System.out.println("Please select an option. Simply enter "
				+ "the number to choose an action");
		System.out.println("1 New Vehicle Entering - Allocate "
				+ "Parking Space");
		System.out.println("2 Vehicle Exiting - Generate "
				+ "Ticket Price");
		System.out.println("3 Shutdown System");
	}
}
