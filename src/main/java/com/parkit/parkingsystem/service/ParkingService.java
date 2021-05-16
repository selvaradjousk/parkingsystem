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
 * Class: {@link ParkingService} - Functions and Methods for dealing with
 * parking availability management service
 * 
 * {@link #ParkingService(InputReaderUtil, ParkingSpotDAO, TicketDAO)} The
 * constructor for ParkingService
 * 
 * @package - com.parkit.parkingsystem.service
 * @project - P3 - parking system - ParkIt
 * @see Methods: {@link #processIncomingVehicle()},
 *      {@link #processExitingVehicle()}, {@link #getVehichleRegNumber()}, {@link #getVehichleType()}, {@link #getNextParkingNumberIfAvailable()}
 * 
 * @author Senthil
 */
public class ParkingService {

	private static final Logger logger = LogManager.getLogger("ParkingService");

	private static FareCalculatorService fareCalculatorService = new FareCalculatorService();

	private InputReaderUtil inputReaderUtil;
	private ParkingSpotDAO parkingSpotDAO;
	private TicketDAO ticketDAO;
	final static int recurentUserOccurencesThreshold = 2;

	/**
	 * {@link #ParkingService(InputReaderUtil, ParkingSpotDAO, TicketDAO)} The
	 * constructor with instantiated classes as parameter inputs:
	 * {@link InputReaderUtil}, {@link ParkingSpotDAO}, {@link TicketDAO}
	 * 
	 * @param inputReaderUtil Instance of InputReaderUtil class
	 * @param parkingSpotDAO  Instance of ParkingSpotDAO class
	 * @param ticketDAO       Instance of TicketDAO class
	 */
	public ParkingService(InputReaderUtil inputReaderUtil, ParkingSpotDAO parkingSpotDAO, TicketDAO ticketDAO) {
		this.inputReaderUtil = inputReaderUtil;
		this.parkingSpotDAO = parkingSpotDAO;
		this.ticketDAO = ticketDAO;
	}

	/**
	 * {@link #processIncomingVehicle()} The method do processing of the parking
	 * spot availability and allocation process
	 * 
	 * @throws Exception when Unable to process incoming vehicle
	 */
	public void processIncomingVehicle() {
		try {
			ParkingSpot parkingSpot = getNextParkingNumberIfAvailable();
			if (parkingSpot != null && parkingSpot.getId() > 0) {
				String vehicleRegNumber = getVehichleRegNumber();
				parkingSpot.setAvailable(false);
				parkingSpotDAO.updateParking(parkingSpot);// allot this parking space and mark it's availability as
															// false

				Date inTime = new Date();
				Ticket ticket = new Ticket();
				// ID, PARKING_NUMBER, VEHICLE_REG_NUMBER, PRICE, IN_TIME, OUT_TIME)
				// ticket.setId(ticketID);
				ticket.setParkingSpot(parkingSpot);
				ticket.setVehicleRegNumber(vehicleRegNumber);
				ticket.setPrice(0);
				ticket.setInTime(inTime);
				ticket.setOutTime(null);
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
	 * {@link #getVehichleRegNumber()} The method execute the process of collecting
	 * vehicle registration number
	 * 
	 * @return readVehicleRegistrationNumber() to the method inputReaderUtil in
	 *         InputReaderUtil Class
	 */
	private String getVehichleRegNumber() throws Exception {
		System.out.println("Please type the vehicle registration number and press enter key");
		return inputReaderUtil.readVehicleRegistrationNumber();
	}

	/**
	 * {@link #getNextParkingNumberIfAvailable()} The method execute the process of
	 * getting a check on the Parking spot availability for Parking
	 * 
	 * @return parkingSpot Provides information on the availability of parking spot
	 *         identified
	 * @throws Exception                when error fetching next available parking
	 *                                  slot
	 * @throws IllegalArgumentException when error parsing user input for type of
	 *                                  vehicle
	 */
	public ParkingSpot getNextParkingNumberIfAvailable() {
		int parkingNumber = 0;
		ParkingSpot parkingSpot = null;
		try {
			ParkingType parkingType = getVehichleType();
			parkingNumber = parkingSpotDAO.getNextAvailableSpot(parkingType);
			if (parkingNumber > 0) {
				parkingSpot = new ParkingSpot(parkingNumber, parkingType, true);
			} else {
				throw new Exception("Error fetching parking number from DB. Parking slots might be full");
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
		int input = inputReaderUtil.readSelection();
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
			String vehicleRegNumber = getVehichleRegNumber();
			Ticket ticket = ticketDAO.getTicket(vehicleRegNumber);
			Date outTime = new Date();
			ticket.setOutTime(outTime);

			int vehicleOccurence = ticketDAO.getVehicleOccurence(vehicleRegNumber);
			fareCalculatorService.calculateFare(ticket, vehicleOccurence >= recurentUserOccurencesThreshold);
			if (ticketDAO.updateTicket(ticket)) {
				ParkingSpot parkingSpot = ticket.getParkingSpot();
				parkingSpot.setAvailable(true);
				parkingSpotDAO.updateParking(parkingSpot);

				if (vehicleOccurence >= recurentUserOccurencesThreshold)
					System.out.println("Applied 5% discount for recurent user");

				System.out.println("Please pay the parking fare:" + ticket.getPrice());
				System.out.println(
						"Recorded out-time for vehicle number:" + ticket.getVehicleRegNumber() + " is:" + outTime);
			} else {
				System.out.println("Unable to update ticket information. Error occurred");
			}
		} catch (Exception e) {
			logger.error("Unable to process exiting vehicle", e);
		}
	}
}
