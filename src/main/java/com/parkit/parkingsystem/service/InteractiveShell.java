package com.parkit.parkingsystem.service;

import com.parkit.parkingsystem.dao.ParkingSpotDAO;
import com.parkit.parkingsystem.dao.TicketDAO;
import com.parkit.parkingsystem.model.Ticket;
import com.parkit.parkingsystem.util.InputReaderUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Class: {@link InteractiveShell} - Functions and Methods for dealing with
 * Application workflow service
 * 
 * @package - com.parkit.parkingsystem.service
 * @project - P3 - parking system - ParkIt
 * @see Methods: {@link #loadInterface()}, {@link #loadMenu()}
 * 
 * @author Senthil
 */
public class InteractiveShell {

	private static final Logger logger = LogManager.getLogger("InteractiveShell");

	/**
	 * {@link #loadInterface()} This method loads the subsequent interface for the
	 * menu choice done using Application Menu {@link #loadMenu()}
	 * 
	 */
	public static void loadInterface() {
		logger.info("App initialized!!!");
		System.out.println("Welcome to Parking System!");

		boolean continueApp = true;
		InputReaderUtil inputReaderUtil = new InputReaderUtil();
		ParkingSpotDAO parkingSpotDAO = new ParkingSpotDAO();
		TicketDAO ticketDAO = new TicketDAO();
		ParkingService parkingService = new ParkingService(inputReaderUtil, parkingSpotDAO, ticketDAO);

		while (continueApp) {
			loadMenu();
			int option = inputReaderUtil.readSelection();
			switch (option) {
			case 1: {
				parkingService.processIncomingVehicle();
				break;
			}
			case 2: {
				parkingService.processExitingVehicle();
				break;
			}
			case 3: {
				System.out.println("Exiting from the system!");
				continueApp = false;
				break;
			}
			default:
				System.out.println("Unsupported option. Please enter a number corresponding to the provided menu");
			}
		}
	}

	/**
	 * {@link #loadMenu()} This method displays the menu choice for using Parking
	 * Application - ParkIt
	 * 
	 */
	private static void loadMenu() {
		System.out.println("Please select an option. Simply enter the number to choose an action");
		System.out.println("1 New Vehicle Entering - Allocate Parking Space");
		System.out.println("2 Vehicle Exiting - Generate Ticket Price");
		System.out.println("3 Shutdown System");
	}

}
