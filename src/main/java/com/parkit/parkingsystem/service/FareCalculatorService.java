package com.parkit.parkingsystem.service;

import com.parkit.parkingsystem.constants.Fare;
import com.parkit.parkingsystem.model.Ticket;

public class FareCalculatorService {

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

	double computeFare(double duration, double selectedFareType, boolean isRecurrent) {
		double fare = duration * selectedFareType;

		// discounted fare value for the recurrent users
		if (isRecurrent)
			fare -= (fare / 100 * 5);
		
		double computedFare = fare;
		System.out.println("We welcome you on behalf of ParkIt and pleased to inform that you have a 5% discount for your regular visit");
		return computedFare;
	}
}