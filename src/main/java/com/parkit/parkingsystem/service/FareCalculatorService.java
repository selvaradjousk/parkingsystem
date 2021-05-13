package com.parkit.parkingsystem.service;

import com.parkit.parkingsystem.constants.Fare;
import com.parkit.parkingsystem.model.Ticket;

public class FareCalculatorService {

	public void calculateFare(Ticket ticket) {
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
			duration = 0;
		}

		switch (ticket.getParkingSpot().getParkingType()) {
		case CAR: {
			ticket.setPrice(duration * Fare.CAR_RATE_PER_HOUR);
			break;
		}
		case BIKE: {
			ticket.setPrice(duration * Fare.BIKE_RATE_PER_HOUR);
			break;
		}
		default:
			throw new IllegalArgumentException("Unkown Parking Type");
		}
	}
}