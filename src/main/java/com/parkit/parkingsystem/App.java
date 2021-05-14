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
import com.parkit.parkingsystem.service.InteractiveShell;
import com.parkit.parkingsystem.service.ParkingService;
import com.parkit.parkingsystem.util.InputReaderUtil;

/**
 * App.java This class deals with database connection configuration settings
 * 
 * @package - com.parkit.parkingsystem
 * @project - P3 - parking system - ParkIt
 * @see Class: {@link DataBaseConfig} - For database connection setting
 *      configuration
 * @see Class: {@link DBConstants} - For different database queries pre-assigned
 *      and stored in variable constants for access
 * @see Class: {@link Fare} - For variables listed as constants to access
 *      pre-assigned parking fare values
 * @see Class: {@link ParkingType} - For variables listed as constants to access
 *      pre-assigned Vechicle types
 * @see Class: {@link ParkingSpotDAO} - Functions and Methods for dealing with
 *      availability management of parking spots /slots
 * @see Class: {@link TicketDAO} - Functions and Methods for dealing with Ticket
 *      management for customers parking
 * @see Class: {@link ParkingSpot} - Declarations of variables, instances,
 *      setters and getters parking Management datasets
 * @see Class: {@link Ticket} - Declarations of variables, instances, setters
 *      and getters Ticket Management datasets
 * @see Class: {@link FareCalculationService} - Functions and Methods for
 *      dealing with parking fare calculation service
 * @see Class: {@link InteractiveShell} - Functions and Methods for dealing with
 *      Application workflow service
 * @see Class: {@link ParkingService} - Functions and Methods for dealing with
 *      parking availability management service
 * @see Class: {@link InputReaderUtil} - Functions and Methods for dealing with
 *      Customer input data management service
 * @author Senthil
 *
 */
public class App {
	private static final Logger logger = LogManager.getLogger("App");

	public static void main(String args[]) {
		logger.info("Initializing Parking System");
		InteractiveShell.loadInterface();
	}
}
