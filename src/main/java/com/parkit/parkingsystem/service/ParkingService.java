package com.parkit.parkingsystem.service;

import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.dao.ParkingSpotDAO;
import com.parkit.parkingsystem.dao.TicketDAO;
import com.parkit.parkingsystem.model.ParkingSpot;
import com.parkit.parkingsystem.model.Ticket;
import com.parkit.parkingsystem.util.InputReaderUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Date;

/**
 * Class: {@link ParkingService} - Deals with parking availability management 
 * service.
 * 
 * {@link #ParkingService(InputReaderUtil, ParkingSpotDAO, TicketDAO)} The
 * constructor for ParkingService
 * <b>Project: </b> P3 - parking system - ParkIt<br>
 * 
 * @see Methods: {@link #processIncomingVehicle()},
 *      {@link #processExitingVehicle()}, {@link #getVehicleRegNumber()}, 
 *      {@link #getVehichleType()}, {@link #getNextParkingNumberIfAvailable()}
 * 
 * @author Senthil
 */
public class ParkingService {

	/**
	 * Logger for ParkingService.
	 * 
	 * @throws Exception when Unable to process incoming vehicle
	 */
	private static final Logger logger = LogManager.getLogger("ParkingService");

	/**
	 * Instantiation of FareCalculatorService.
	 *
	 */
	private static FareCalculatorService fareCalculatorService = new FareCalculatorService();

	/**
	 * InputReaderUtil object.
	 *
	 */
	private InputReaderUtil inputReaderUtil;
	
	/**
	 * ParkingSpotDAO object.
	 *
	 */
	private ParkingSpotDAO parkingSpotDAO;
	
	/**
	 * TicketDAO object.
	 *
	 */
	private TicketDAO ticketDAO;
	
	/**
	 * setting recurrent user occurrences threshold value.
	 *
	 */
	final static int recurentUserOccurencesThreshold = 2;

	/**
	 * {@link #ParkingService(InputReaderUtil, ParkingSpotDAO, TicketDAO)} The
	 * constructor with instantiated classes as parameter inputs:
	 * {@link InputReaderUtil}, {@link ParkingSpotDAO}, {@link TicketDAO}.
	 * 
	 * @param inputReaderUtil Instance of InputReaderUtil class
	 * @param parkingSpotDAO  Instance of ParkingSpotDAO class
	 * @param ticketDAO       Instance of TicketDAO class
	 */
	public ParkingService(InputReaderUtil inputReaderUtil, ParkingSpotDAO 
			parkingSpotDAO, TicketDAO ticketDAO) {
		this.inputReaderUtil = inputReaderUtil;
		this.parkingSpotDAO = parkingSpotDAO;
		this.ticketDAO = ticketDAO;
	}

	/**
	 * {@link #processIncomingVehicle()} Process the parking
	 * spot availability and allocation.
	 * 
	 * @throws Exception when Unable to process incoming vehicle
	 */
	public void processIncomingVehicle() {
		try {
			ParkingSpot parkingSpot = getNextParkingNumberIfAvailable();
			
			if (parkingSpot != null && parkingSpot.getId() > 0) {
				
				// Information on vehicle number is gathered.
				String vehicleRegNumber = getVehicleRegNumber();

				// Parking spot availability set to parking spot taken.
				parkingSpot.setAvailable(false);
				
				// allot this parking space and mark it's availability as false.
				parkingSpotDAO.updateParking(parkingSpot);

				// Incoming time set.
				Date inTime = new Date();
				
				// Incoming ticket values set.
				Ticket ticket = new Ticket();
				// ID, PARKING_NUMBER, VEHICLE_REG_NUMBER, PRICE, IN_TIME, OUT_TIME)
				// ticket.setId(ticketID);
				ticket.setParkingSpot(parkingSpot);
				ticket.setVehicleRegNumber(vehicleRegNumber);
				ticket.setPrice(0);
				ticket.setInTime(inTime);
				ticket.setOutTime(null);
				
				// Ticket information saved in DB.
				ticketDAO.saveTicket(ticket);
				System.out.println("Generated Ticket and saved in DB");
				System.out.println("Please park your vehicle in spot number:" + parkingSpot.getId());
				System.out.println("Recorded in-time for vehicle number:" + vehicleRegNumber + " is:" + inTime);
			}
		} catch (Exception e) {
			logger.error("Unable to process incoming vehicle", e);
		}
	}

	/**
	 * {@link #getVehicleRegNumber()} Collects vehicle registration number.
	 * 
	 * @return readVehicleRegistrationNumber() to the method inputReaderUtil in
	 *         InputReaderUtil Class
	 */
	private String getVehicleRegNumber() throws Exception {
		System.out.println("Please type the vehicle registration number and "
				+ "press enter key");
		return inputReaderUtil.readVehicleRegistrationNumber();
	}

	/**
	 * {@link #getNextParkingNumberIfAvailable()} Checks on the Parking spot availability.
	 * 
	 * @return parkingSpot Information on the availability of parking spot
	 * @throws Exception                when error fetching next available parking
	 *                                  slot.
	 * @throws IllegalArgumentException when error parsing user input for type of
	 *                                  vehicle.
	 */
	public ParkingSpot getNextParkingNumberIfAvailable() {
		int parkingNumber = 0;
		ParkingSpot parkingSpot = null;
		try {
			
			// Fetching information on vehicle type.
			ParkingType parkingType = getVehichleType();
			
			// Checking for availability of parking spot not taken.
			parkingNumber = parkingSpotDAO.getNextAvailableSpot(parkingType);
			if (parkingNumber > 0) {
				parkingSpot = new ParkingSpot(parkingNumber, parkingType, true);
			} else {
				throw new Exception("Error fetching parking number from DB. "
						+ "Parking slots might be full");
			}
		} catch (IllegalArgumentException ie) {
			logger.error("Error parsing user input for type of vehicle", ie);
		} catch (Exception e) {
			logger.error("Error fetching next available parking slot", e);
		}
		return parkingSpot;
	}

	/**
	 * {@link #getVehichleType()} The method execute the process of getting
	 * gathering the information on the Vehicle Type that needs to be using the
	 * Parking
	 * 
	 * @throws IllegalArgumentException when entered input is invalid
	 */
	private ParkingType getVehichleType() {
		System.out.println("Please select vehicle type from menu");
		System.out.println("1 CAR");
		System.out.println("2 BIKE");
		
		// Get information on user input screen entry value.
		int input = inputReaderUtil.readSelection();
		
		// Get information on Parking type
		switch (input) {
		case 1: {
			return ParkingType.CAR;
		}
		case 2: {
			return ParkingType.BIKE;
		}
		default: {
			System.out.println("Incorrect input provided");
			throw new IllegalArgumentException("Entered input is invalid");
		}
		}
	}

	/**
	 * {@link #processExitingVehicle()} The method execute the process of supporting
	 * the fare calculation and update of the information on the customer on the
	 * database during exit
	 * 
	 * @throws Exception error when unable to process exiting vehicle
	 */
	public void processExitingVehicle() {
		try {
			String vehicleRegNumber = getVehicleRegNumber();
			
			// Gets information on the registration number from DB on the last ticket saved.
			Ticket ticket = ticketDAO.getTicket(vehicleRegNumber);
			Date outTime = new Date();
			ticket.setOutTime(outTime);

			// Eligibility for 5% discount is checked. 
			int vehicleOccurence = checkForDiscountEligibility(vehicleRegNumber, ticket);
			
			// Ticket updated with fare and exit time in database.
			if (ticketDAO.updateTicket(ticket)) {
				
				// Retrieve information on parking spot of the ticket concerned.
				ParkingSpot parkingSpot = ticket.getParkingSpot();

				// Availability of parking spot is set available for future parking access.
				parkingSpot.setAvailable(true);

				// Availability of parking spot updated in DB.
				parkingSpotDAO.updateParking(parkingSpot);

				// Confirms the discount eligibility to user.
				confirmOfferForDiscount(vehicleOccurence);

				// Displays information on Fare to be paid and exit time.
				displayFareToBePaidInformation(ticket, outTime);
			} else {
				// displays error message - unable to update.
				displayErrorUpdateTicketInformation();
			}
		} catch (Exception e) {
			logger.error("Unable to process exiting vehicle", e);
		}
	}

	/**
	 * Checks for discount eligibility for the vehicle.
	 * 
	 * @param vehicleRegNumber
	 * @param ticket
	 * @return
	 */
	private int checkForDiscountEligibility(String vehicleRegNumber, Ticket ticket) {
		int vehicleOccurence = ticketDAO.getVehicleOccurence(vehicleRegNumber);
		fareCalculatorService.calculateFare(ticket, vehicleOccurence >= recurentUserOccurencesThreshold);
		return vehicleOccurence;
	}
	
	/**
	 * Confirms eligibility and displays 5% discount offer.
	 * 
	 * @param vehicleOccurence
	 */
	private void confirmOfferForDiscount(int vehicleOccurence) {
		if (vehicleOccurence >= recurentUserOccurencesThreshold)
			System.out.println("Applied 5% discount for recurent user");
	}
	
	/**
	 * Display details on the Fare amount to be paid with exit time info.
	 * 
	 * @param ticket
	 * @param outTime
	 */
	private void displayFareToBePaidInformation(Ticket ticket, Date outTime) {
		System.out.println("Please pay the parking fare:" 
		+ ticket.getPrice());
		System.out.println(
				"Recorded out-time for vehicle number:" 
		+ ticket.getVehicleRegNumber() + " is:" + outTime);
	}
	
	/**
	 * Displays error in case not able to update ticket information.
	 */
	private void displayErrorUpdateTicketInformation() {
		System.out.println("Unable to update ticket information.");
	}
}
