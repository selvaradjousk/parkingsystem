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
 * Class: {@link App Main Class} This class deals with database connection
 * configuration settings
 * 
 * @package - com.parkit.parkingsystem
 * @project - P3 - parking system - ParkIt
 * @see <b>Class: </b> {@link DataBaseConfig} - For database connection setting
 *      configuration
 * @see <b>Class: </b> {@link DBConstants} - For different database queries
 *      pre-assigned and stored in variable constants for access
 * @see <b>Class: </b> {@link Fare} - For variables listed as constants to
 *      access pre-assigned parking fare values
 * @see <b>Class: </b> {@link ParkingType} - For variables listed as constants
 *      to access pre-assigned Vechicle types
 * @see <b>Class: </b> {@link ParkingSpotDAO} - Functions and Methods for
 *      dealing with availability management of parking spots /slots
 * @see <b>Class: </b> {@link TicketDAO} - Functions and Methods for dealing
 *      with Ticket management for customers parking
 * @see <b>Class: </b> {@link ParkingSpot} - Declarations of variables,
 *      instances, setters and getters parking Management datasets
 * @see <b>Class: </b> {@link Ticket} - Declarations of variables, instances,
 *      setters and getters Ticket Management datasets
 * @see <b>Class: </b> {@link FareCalculatorService} - Functions and Methods for
 *      dealing with parking fare calculation service
 * @see <b>Class: </b> {@link InteractiveShell} - Functions and Methods for
 *      dealing with Application workflow service
 * @see <b>Class: </b> {@link ParkingService} - Functions and Methods for
 *      dealing with parking availability management service
 * @see <b>Class: </b> {@link InputReaderUtil} - Functions and Methods for
 *      dealing with Customer input data management service<br>
 *      
 * @see <b>Test Class:</b>{@link com.parkit.parkingsystem.integration.service.DataBasePrepareService DataBasePrepareService} - Functions and
 *      Methods for dealing with parking availability management service <br>
 * @see <b>Test Class:</b>{@link com.parkit.parkingsystem.integration.dao.TicketDAOIT TicketDAOIT} - Vehicle Ticket Data Access Object -
 *      Integration Testing on Functions and Methods for dealing with Ticket
 *      management for customers parking <br>
 * @see <b>Test Class:</b>{@link com.parkit.parkingsystem.integration.dao.ParkingSpotDAOIT ParkingSpotDAOIT} - Parking Spot Data Access Object
 *      - Tests on Functions and Methods for dealing with availability
 *      management of parking spots / slots <br>
 * @see <b>Test Class:</b>{@link com.parkit.parkingsystem.integration.config.DataBaseTestConfig DataBaseTestConfig} - Integration Tests for
 *      database connection configuration setting <br>
 * @see <b>Test Class:</b>{@link com.parkit.parkingsystem.integration.config.DataBaseConfigIT DataBaseConfigIT} - Integration Testing For
 *      database connection configuration setting <br>
 * @see <b>Test Class:</b>{@link com.parkit.parkingsystem.integration.ParkingDataBaseIT ParkingDataBaseIT} - Performs Integration Test on
 *      Parking Database <br>
 * @see <b>Test Class:</b>{@link com.parkit.parkingsystem.unittest.service.ParkingServiceTest ParkingServiceTest} - Performs Integration Test on
 *      Parking Services for customer of ParkIt<br>
 * @see <b>Test Class:</b>{@link com.parkit.parkingsystem.unittest.service.FareCalculatorServiceTest FareCalculatorServiceTest} - Performs Unit Testing
 *      on Fare calculation process for customer of ParkIt <br>
 * 
 * @author Senthil
 *
 */
public class App {
	private static final Logger logger = LogManager.getLogger("App");

	public static void main(String[] args) {
		logger.info("Initializing Parking System");
		InteractiveShell.loadInterface();
	}
}
