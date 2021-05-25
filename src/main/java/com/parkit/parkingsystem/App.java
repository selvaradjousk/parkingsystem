package com.parkit.parkingsystem;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.parkit.parkingsystem.config.DataBaseConfig;
import com.parkit.parkingsystem.constants.DBConstants;
import com.parkit.parkingsystem.constants.Fare;
import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.dao.ParkingSpotDAO;
import com.parkit.parkingsystem.dao.TicketDAO;
import com.parkit.parkingsystem.model.ParkingSpot;
import com.parkit.parkingsystem.model.Ticket;
import com.parkit.parkingsystem.service.FareCalculatorService;
import com.parkit.parkingsystem.service.InteractiveShell;
import com.parkit.parkingsystem.service.ParkingService;
import com.parkit.parkingsystem.util.InputReaderUtil;

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
 * 
 * @see <b>Test
 *      Class:</b>{@link com.parkit.parkingsystem.integration.service.DataBasePrepareService
 *      DataBasePrepareService} Parking availability management service Testing
 *      <br>
 * @see <b>Test
 *      Class:</b>{@link com.parkit.parkingsystem.integration.dao.TicketDAOIT
 *      TicketDAOIT} - Ticket management Testing management for customers
 *      parking <br>
 * @see <b>Test
 *      Class:</b>{@link com.parkit.parkingsystem.integration.dao.ParkingSpotDAOIT
 *      ParkingSpotDAOIT} - Parking Spot availability services testing
 *      management of parking spots / slots <br>
 * @see <b>Test
 *      Class:</b>{@link com.parkit.parkingsystem.integration.config.DataBaseTestConfig
 *      DataBaseTestConfig} - Integration Tests for database connection
 *      configurations <br>
 * @see <b>Test
 *      Class:</b>{@link com.parkit.parkingsystem.integration.config.DataBaseConfigIT
 *      DataBaseConfigIT} - Integration Testing For database connection
 *      configuration setting <br>
 * @see <b>Test
 *      Class:</b>{@link com.parkit.parkingsystem.integration.ParkingDataBaseIT
 *      ParkingDataBaseIT} - Integration Test on Parking Database <br>
 * @see <b>Test
 *      Class:</b>{@link com.parkit.parkingsystem.unittest.service.ParkingServiceTest
 *      ParkingServiceTest} - Integration Test on Parking Services for customer
 *      of ParkIt<br>
 * @see <b>Test
 *      Class:</b>{@link com.parkit.parkingsystem.unittest.service.FareCalculatorServiceTest
 *      FareCalculatorServiceTest} - Unit Testing on Fare calculation process
 *      for customer of ParkIt <br>
 * 
 * @author Senthil
 *
 */
public class App {

	/**
	 * Logger for the application.
	 */
	private static final Logger LOGGER = LogManager.getLogger("App");

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