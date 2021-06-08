package com.parkit.parkingsystem;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.parkit.parkingsystem.service.InteractiveShell;

/**
 * Class: {@link App} Main class for application ParkIt.<br>
 * <b>Project: </b> P3 - parking system - ParkIt<br>
 *
 * @see <b>Class: </b> {@link DataBaseConfig} Database connection<br>
 * @see <b>Class: </b> {@link DBConstants} - Database constants<br>
 * @see <b>Class: </b> {@link Fare} - DB constants on Fare values<br>
 * @see <b>Class: </b> {@link ParkingType} - DB constants on Parking Type<br>
 * @see <b>Class: </b> {@link ParkingSpotDAO} - Parking spots management<br>
 * @see <b>Class: </b> {@link TicketDAO} - Ticket management<br>
 * @see <b>Class: </b> {@link ParkingSpot} - Parking spot data structure<br>
 * @see <b>Class: </b> {@link Ticket} - Ticket data structure<br>
 * @see <b>Class: </b> {@link FareCalculatorService} - Fare Service<br>
 * @see <b>Class: </b> {@link InteractiveShell} - Interface interaction<br>
 * @see <b>Class: </b> {@link ParkingService} - Parking service management<br>
 * @see <b>Class: </b> {@link InputReaderUtil} - I/O management<br>
 * @author Senthil
 *
 */
public class App {

	/**
	 * Logger for the application.
	 */
	private static final Logger LOGGER
	= LogManager.getLogger("App");

	/**
	 * Constructor of App class.
	 */
	App() {
	}

	/**
	 * Parkit application.
	 *
	 * @param args no arguments defined
	 */
	public static void main(final String[] args) {
		LOGGER.info("Initializing Parking System");
		InteractiveShell.loadInterface();
	}
}
