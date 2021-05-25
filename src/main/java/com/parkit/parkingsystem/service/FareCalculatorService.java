package com.parkit.parkingsystem.service;

import com.parkit.parkingsystem.constants.Fare;
import com.parkit.parkingsystem.model.Ticket;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Class: {@link FareCalculatorService} - Fare Computation Service.<br>
 * <b>Project: </b> P3 - parking system - ParkIt<br>
 * 
 * @see Methods: {@link #checkPertinanceOfGetOutTime(Ticket ticket)},
 *      {@link #calculateFare()},
 *      {@link #identifyVehicleTypeForComputeFare(Ticket ticket)},
 *      {@link #fareSetZeroValueForLessThanThirtyMinutesParking(Ticket ticket, double duration)},
 *      {@link #computeFare(double duration, double selectedFareType, boolean isRecurrent)}.
 * 
 * @author Senthil
 */
public class FareCalculatorService {

	/**
	 * Logger for FareClaculator Service class.
	 * 
	 */
	private static final Logger LOGGER = LogManager.getLogger("FareCalculatorService");

	/**
	 * {@link #calculateFare()} Calculates fare on exiting the parking.
	 * 
	 * @param ticket      Instance of Ticket class
	 * @param isRecurrent Return value of isRecurrent variable set
	 * @throws IllegalArgumentException when vehicle exit time is incorrect
	 */
	public void calculateFare(final Ticket ticket, final boolean isRecurrent) {

		// This method checks if Exit time provided is correct.
		checkPertinanceOfGetOutTime(ticket);

		// Values for entryTime and exitTime set in milliseconds.
		double entryTime = ticket.getInTime().getTime();
		double exitTime = ticket.getOutTime().getTime();

		// Duration values are computed into hours from milliseconds.
		double duration = (exitTime - entryTime) / Fare.MILLISECONDS_HOURS_CONVERSION_FACTOR;

		// identifyVehicleTypeForComputeFare(ticket) stored in selectedFare.
		double selectedFare = identifyVehicleTypeForComputeFare(ticket);

		// 30 minutes parking is set to zero
		if (duration < Fare.THIRTY_MIN_FREE_PARKING_TIME) {
			ticket.setPrice(0);
			LOGGER.info("Parking visit is below 30 minutes, "
					+ "fare to pay set to free");
			return;
		}

		// Fare calculation for parking is performed
		ticket.setPrice(computeFare(duration, selectedFare, isRecurrent));
	}

	/**
	 * {@link #identifyVehicleTypeForComputeFare(final Ticket ticket)} 
	 * This method identifies the vehicle type to compute the fare.
	 * 
	 * @param ticket Instance of Ticket class
	 * @return selected fare value base don vehicle type
	 * @throws IllegalArgumentException for Unknown Parking Type
	 */
	private double identifyVehicleTypeForComputeFare(final Ticket ticket) {
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
		return selectedFare;
	}

	/**
	 * {@link #checkPertinanceOfGetOutTime(final Ticket ticket)} 
	 * This method checks if Exit time provided is correct.
	 * 
	 * @param ticket Instance of Ticket class
	 * @throws IllegalArgumentException when Out time 
	 * provided is incorrect
	 */
	public void checkPertinanceOfGetOutTime(final Ticket ticket) {
		if ((ticket.getOutTime() == null) || (ticket.getOutTime().before(ticket.getInTime()))) {
			throw new IllegalArgumentException("Out time provided is incorrect:" + ticket.getOutTime().toString());
		}
	}

	/**
	 * {@link #computeFare(double duration, double selectedFareType, boolean isRecurrent)}
	 * This method called in the calculateFare() method 
	 * to recalculates fare for recurrent customer.
	 * 
	 * @param duration
	 * @param selectedFareType Return value of
	 *                         {@link #identifyVehicleTypeForComputeFare(Ticket ticket)}
	 * @param isRecurrent      Return value of getVehicleOccurence()
	 * @return computed fare value in rounded 2 decimal format
	 */
	double computeFare(double duration, double selectedFareType, boolean isRecurrent) {
		double fare = duration * selectedFareType;

		// discount for the fare value for the recurrent users.
		if (isRecurrent) {
			fare -= (fare * Fare.DISCOUNT_RATE);
		}

		BigDecimal fare2 = new BigDecimal(fare)
				.setScale(3, RoundingMode.HALF_UP);
		double estimate = fare2.doubleValue();
		
		double computedFare = estimate;

		System.out.println(
				"We welcome you on behalf of ParkIt and pleased "
				+ "to inform that you have a 5% discount for "
				+ "your regular visit");
		LOGGER.info("Parking visit is below is reccurent, "
				+ "welcome note and 5 % discount displayed ");
		return computedFare;
	}
}