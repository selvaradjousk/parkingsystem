package com.parkit.parkingsystem.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.parkit.parkingsystem.constants.Fare;
import com.parkit.parkingsystem.model.Ticket;

/**
 * Class: {@link FareCalculatorService} - Performs functions and Methods for
 * dealing with parking fare calculation service
 * 
 * @package - com.parkit.parkingsystem.service
 * @project - P3 - parking system - ParkIt
 * @see Methods: {@link #calculateFare()}, {@link #computeFare(double duration,
 *      double selectedFareType, boolean isRecurrent)}
 * 
 * @author Senthil
 */
public class FareCalculatorService {
	private static final Logger logger = LogManager.getLogger("FareCalculatorService");

	/**
	 * {@link #calculateFare()} This method calculates fare on exiting the parking by
	 * the customer
	 * 
	 * @param ticket      Instance of Ticket class
	 * @param isReccurent Return value of isRecurrent variable set from the result
	 *                    value of occurrences from the getVehicleOccurence() method
	 *                    of the TicketDAO class
	 * @throws IllegalArgumentException when vehicle exit time is incorrect & in
	 *                                  case of unknown parking time
	 */
	public void calculateFare(Ticket ticket, boolean isRecurrent) {
		if ((ticket.getOutTime() == null) || (ticket.getOutTime().before(ticket.getInTime()))) {
			throw new IllegalArgumentException("Out time provided is incorrect:" + ticket.getOutTime().toString());
		}

		// Values for entryTime and exitTime returned from getTime() is set in
		// milliseconds
		double entryTime = ticket.getInTime().getTime();
		double exitTime = ticket.getOutTime().getTime();

		// *******************TASK COMPLETED*****************************************
		// TODO: Some tests are failing here. Need to check if this logic is correct
		// *******************TASK COMPLETED*****************************************

		// Duration values are computed into hours taken account of milliseconds unit
		// terms
		double duration = (exitTime - entryTime) / (60.0 * 60.0 * 1000);

		// *******************TASK COMPLETED - 30 Minutes free**********************
		// Fare for the parking is set to be free
		// when the user parking time is less than 30 minutes
		// *******************TASK COMPLETED - 30 Minutes free**********************

		if (duration < 0.5) {
			ticket.setPrice(0);
			logger.info("Parking visit is below 30 minutes, fare to pay set to free");
			return;
		}

		double selectedFare = 0;

		switch (ticket.getParkingSpot().getParkingType()) {
		case CAR:
			selectedFare = Fare.CAR_RATE_PER_HOUR;
			break;

		case BIKE:
			selectedFare = Fare.BIKE_RATE_PER_HOUR;
			break;

		default:
			throw new IllegalArgumentException("Unkown Parking Type");
		}

		ticket.setPrice(computeFare(duration, selectedFare, isRecurrent));
	}

	/**
	 * {@link #computeFare(double duration, double selectedFareType, boolean
	 * isRecurrent)} This method called in the calculateFare() method to
	 * recalculates fare for recurrent customer
	 * 
	 * @param duration         Return value of
	 *                         {@link #calculateFare(Ticket, boolean)}
	 * @param selectedFareType Return value of
	 *                         {@link #calculateFare(Ticket, boolean)} value of
	 *                         occurrences from the getVehicleOccurence() method
	 * @param isRecurrent      Return value of
	 *                         {@link #calculateFare(Ticket, boolean)}
	 */
	double computeFare(double duration, double selectedFareType, boolean isRecurrent) {
		double fare = duration * selectedFareType;

		// discounted fare value for the recurrent users
		if (isRecurrent)
			fare -= (fare / 100 * 5);

		double computedFare = fare;
		System.out.println(
				"We welcome you on behalf of ParkIt and pleased to inform that you have a 5% discount for your regular visit");
		logger.info("Parking visit is below is reccurent, welcome note and 5 % discount displayed ");
		return computedFare;
	}
}