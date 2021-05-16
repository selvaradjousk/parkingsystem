package com.parkit.parkingsystem;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Date;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.parkit.parkingsystem.constants.Fare;
import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.model.ParkingSpot;
import com.parkit.parkingsystem.model.Ticket;
import com.parkit.parkingsystem.service.FareCalculatorService;

/**
 * Class {@link FareCalculatorServiceTest} - Performs Unit Testing on Fare
 * calculation process for customer of ParkIt Class Tested:
 * {@link FareCalculatorService}
 * 
 * @package - com.parkit.parkingsystem
 * @project - P3 - parking system - ParkIt
 * @see Tests: {@link #calculateFareCar()}
 * 
 * @author Senthil
 */
public class FareCalculatorServiceTest {

	private static FareCalculatorService fareCalculatorService;
	private Ticket ticket;

	@BeforeAll
	private static void setUp() {
		fareCalculatorService = new FareCalculatorService();
	}

	@BeforeEach
	private void setUpPerTest() {
		ticket = new Ticket();
	}

	/**
	 * {@link #calculateFareCar()} Unit Test <br>
	 * GIVEN: One hour of parking time for a <b>CAR</b> <br>
	 * WHEN: calculation of fare service activated <br>
	 * THEN: calculation of <b>CAR</b> fare per hour <br>
	 * <b>Test Condition <i>PASSED</i>: </b>assertEquals <code><b>TRUE</b></code>
	 * <br>
	 * <b>Test Condition <i>FAILED</i>: </b>assertEquals <code><b>FALSE</b></code>
	 */
	@Test
	public void calculateFareCar() {

		// GIVEN
		Date inTime = new Date();
		inTime.setTime(System.currentTimeMillis() - (60 * 60 * 1000));
		Date outTime = new Date();
		ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR, false);

		ticket.setInTime(inTime);
		ticket.setOutTime(outTime);
		ticket.setParkingSpot(parkingSpot);

		// WHEN
		fareCalculatorService.calculateFare(ticket, false);

		// THEN
		assertEquals(ticket.getPrice(), Fare.CAR_RATE_PER_HOUR);
	}

	/**
	 * {@link #calculateFareRecurentUserCar()} Unit Test <br>
	 * GIVEN: Five hour of parking time for a <b>CAR</b> <br>
	 * WHEN: calculation of fare service activated <br>
	 * THEN: calculation of <b>Recurrence of user</b> tested <br>
	 * <b>Test Condition <i>PASSED</i>: </b>assertEquals fare = 7.125€
	 * <code><b>TRUE</b></code> <br>
	 * <b>Test Condition <i>FAILED</i>: </b>assertEquals fare != 7.125€
	 * <code><b>FALSE</b></code>
	 */
	@Test
	public void calculateFareRecurentUserCar() {

		// GIVEN
		Date inTime = new Date();
		inTime.setTime(System.currentTimeMillis() - (5 * 60 * 60 * 1000));
		Date outTime = new Date();
		ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR, false);

		ticket.setInTime(inTime);
		ticket.setOutTime(outTime);
		ticket.setParkingSpot(parkingSpot);

		// WHEN
		fareCalculatorService.calculateFare(ticket, true);

		// THEN
		assertEquals((7.125), ticket.getPrice());
	}

	/**
	 * {@link #calculateFareCar()} Unit Test <br>
	 * GIVEN: One hour of parking time for a <b>BIKE</b> <br>
	 * WHEN: calculation of fare service activated <br>
	 * THEN: calculation of <b>BIKE</b> fare per hour <br>
	 * <b>Test Condition <i>PASSED</i>: </b>assertEquals <code><b>TRUE</b></code>
	 * <br>
	 * <b>Test Condition <i>FAILED</i>: </b>assertEquals <code><b>FALSE</b></code>
	 */
	@Test
	public void calculateFareBike() {

		// GIVEN
		Date inTime = new Date();
		inTime.setTime(System.currentTimeMillis() - (60 * 60 * 1000));
		Date outTime = new Date();
		ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.BIKE, false);

		ticket.setInTime(inTime);
		ticket.setOutTime(outTime);
		ticket.setParkingSpot(parkingSpot);

		// WHEN
		fareCalculatorService.calculateFare(ticket, false);

		// THEN
		assertEquals(ticket.getPrice(), Fare.BIKE_RATE_PER_HOUR);
	}

	/**
	 * {@link #calculateFareUnkownType()} Unit Test <br>
	 * GIVEN: One hour of parking time for a <b>UNKNOWN VEHICLE TYPE</b> <br>
	 * WHEN: calculation of fare service activated <br>
	 * THEN: Identify <b>UNKNOWN VEHICLE TYPE</b> <br>
	 * <b>Test Condition <i>PASSED</i>: </b>assertThrows NullPointerException
	 * <code><b>TRUE</b></code> <br>
	 * <b>Test Condition <i>FAILED</i>: </b>assertThrows NullPointerException
	 * <code><b>FALSE</b></code>
	 */
	@Test
	public void calculateFareUnkownType() {

		// GIVEN
		Date inTime = new Date();
		inTime.setTime(System.currentTimeMillis() - (60 * 60 * 1000));
		Date outTime = new Date();
		ParkingSpot parkingSpot = new ParkingSpot(1, null, false);

		ticket.setInTime(inTime);
		ticket.setOutTime(outTime);
		ticket.setParkingSpot(parkingSpot);

		// THEN
		assertThrows(NullPointerException.class, () -> fareCalculatorService.calculateFare(ticket, false));// WHEN

	}

	/**
	 * {@link #calculateFareBikeWithFutureInTime()} Unit Test <br>
	 * GIVEN: Parking entry time for the <b>BIKE</b> set to <b>FUTURE TIME</b> <br>
	 * WHEN: calculation of fare service activated <br>
	 * THEN: calculation of fare per hour checks for entry time compliance to
	 * current time or not <br>
	 * <b>Test Condition <i>PASSED</i>: </b>assertThrows IllegalArgumentException
	 * <code><b>TRUE</b></code> <br>
	 * <b>Test Condition <i>FAILED</i>: </b>assertThrows IllegalArgumentException
	 * <code><b>FALSE</b></code>
	 */
	@Test
	public void calculateFareBikeWithFutureInTime() {

		// GIVEN
		Date inTime = new Date();
		inTime.setTime(System.currentTimeMillis() + (60 * 60 * 1000));
		Date outTime = new Date();
		ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.BIKE, false);

		ticket.setInTime(inTime);
		ticket.setOutTime(outTime);
		ticket.setParkingSpot(parkingSpot);

		// THEN
		assertThrows(IllegalArgumentException.class, () -> fareCalculatorService.calculateFare(ticket, false)); // WHEN
	}

	/**
	 * {@link #calculateFareBikeWithNegativeDurationTime()} Unit Test <br>
	 * GIVEN: Parking duration for the <b>BIKE</b> set to <b>NEGATIVE TIME VALUE</b>
	 * <br>
	 * WHEN: calculation of fare service activated <br>
	 * THEN: calculation of fare per hour checks for duration value compliance <br>
	 * <b>Test Condition <i>PASSED</i>: </b>assertThrows IllegalArgumentException
	 * <code><b>TRUE</b></code> <br>
	 * <b>Test Condition <i>FAILED</i>: </b>assertThrows IllegalArgumentException
	 * <code><b>FALSE</b></code>
	 */
	@Test
	public void calculateFareBikeWithNegativeDurationTime() {

		// GIVEN
		Date inTime = new Date();
//		inTime.setTime(System.currentTimeMillis() + (60 * 60 * 1000));
//		Date outTime = new Date();
		inTime.setTime(System.currentTimeMillis());
		Date outTime = new Date();
		outTime.setTime(System.currentTimeMillis() - (60 * 60 * 1000));
		ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.BIKE, false);

		ticket.setInTime(inTime);
		ticket.setOutTime(outTime);
		ticket.setParkingSpot(parkingSpot);

		// THEN
		assertThrows(IllegalArgumentException.class, () -> fareCalculatorService.calculateFare(ticket, false)); // WHEN
	}

	/**
	 * {@link #calculateFareBikeWithLessThanThirtyMinutesParkingTime()} Unit Test
	 * <br>
	 * GIVEN: Parking exit time for the <b>BIKE</b> set to <b>less than 30
	 * minutes</b> <br>
	 * WHEN: calculation of fare service activated <br>
	 * THEN: calculation of fare service checks for duration threshold below 30
	 * minutes <br>
	 * <b>Test Condition <i>PASSED</i>: </b>assertEquals fare = 0€
	 * <code><b>TRUE</b></code> <br>
	 * <b>Test Condition <i>FAILED</i>: </b>assertEquals fare != 0€
	 * <code><b>FALSE</b></code>
	 */
	@Test
	public void calculateFareBikeWithLessThanThirtyMinutesParkingTime() {

		// GIVEN
		Date inTime = new Date();
		inTime.setTime(System.currentTimeMillis() - (29 * 60 * 1000));// less than 30 minutes parking time should give
																		// NO parking fare - free parking
		Date outTime = new Date();
		ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.BIKE, false);

		ticket.setInTime(inTime);
		ticket.setOutTime(outTime);
		ticket.setParkingSpot(parkingSpot);

		// WHEN
		fareCalculatorService.calculateFare(ticket, false);

		// THEN
		assertEquals((0.0 * Fare.BIKE_RATE_PER_HOUR), ticket.getPrice());
	}

	/**
	 * {@link #calculateFareCarWithLessThanThirtyMinutesParkingTime()} Unit Test
	 * <br>
	 * GIVEN: Parking exit time for the <b>CAR</b> set to <b>less than 30
	 * minutes</b> <br>
	 * WHEN: calculation of fare service activated <br>
	 * THEN: calculation of fare service checks for duration threshold below 30
	 * minutes <br>
	 * <b>Test Condition <i>PASSED</i>: </b>assertEquals fare = 0€
	 * <code><b>TRUE</b></code> <br>
	 * <b>Test Condition <i>FAILED</i>: </b>assertEquals fare != 0€
	 * <code><b>FALSE</b></code>
	 */
	@Test
	public void calculateFareCarWithLessThanThirtyMinutesParkingTime() {

		// GIVEN
		Date inTime = new Date();
		inTime.setTime(System.currentTimeMillis() - (29 * 60 * 1000));// less than 30 minutes parking time should give
																		// NO parking fare - free parking
		Date outTime = new Date();
		ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR, false);

		ticket.setInTime(inTime);
		ticket.setOutTime(outTime);
		ticket.setParkingSpot(parkingSpot);

		// WHEN
		fareCalculatorService.calculateFare(ticket, false);

		// THEN
		assertEquals((0.0 * Fare.CAR_RATE_PER_HOUR), ticket.getPrice());
	}

	/**
	 * {@link #calculateFareCarWithLessThanOneHourParkingTime()} Unit Test <br>
	 * GIVEN: Parking exit time for the <b>BIKE</b> set to less than One hour and
	 * more than 30 minutes i.e. <b>45 minutes</b> <br>
	 * WHEN: calculation of fare service activated <br>
	 * THEN: calculation of fare service checks for duration threshold below One
	 * hour <br>
	 * <b>Test Condition <i>PASSED</i>: </b>assertEquals fare = "0.75 *
	 * Fare.CAR_RATE_PER_HOUR" <code><b>TRUE</b></code> <br>
	 * <b>Test Condition <i>FAILED</i>: </b>assertEquals fare != "0.75 *
	 * Fare.CAR_RATE_PER_HOUR" <code><b>FALSE</b></code>
	 */
	@Test
	public void calculateFareBikeWithLessThanOneHourParkingTime() {

		// GIVEN
		Date inTime = new Date();
		inTime.setTime(System.currentTimeMillis() - (45 * 60 * 1000));// 45 minutes parking time should give 3/4th
																		// parking fare
		Date outTime = new Date();
		ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.BIKE, false);

		ticket.setInTime(inTime);
		ticket.setOutTime(outTime);
		ticket.setParkingSpot(parkingSpot);

		// WHEN
		fareCalculatorService.calculateFare(ticket, false);

		// THEN
		assertEquals((0.75 * Fare.BIKE_RATE_PER_HOUR), ticket.getPrice());
	}

	/**
	 * {@link #calculateFareCarWithLessThanOneHourParkingTime()} Unit Test <br>
	 * GIVEN: Parking exit time for the <b>CAR</b> set to less than One hour and
	 * more than 30 minutes i.e. <b>45 minutes</b> <br>
	 * WHEN: calculation of fare service activated <br>
	 * THEN: calculation of fare service checks for duration threshold below One
	 * hour <br>
	 * <b>Test Condition <i>PASSED</i>: </b>assertEquals fare = "0.75 *
	 * Fare.CAR_RATE_PER_HOUR" <code><b>TRUE</b></code> <br>
	 * <b>Test Condition <i>FAILED</i>: </b>assertEquals fare != "0.75 *
	 * Fare.CAR_RATE_PER_HOUR" <code><b>FALSE</b></code>
	 */
	@Test
	public void calculateFareCarWithLessThanOneHourParkingTime() {

		// GIVEN
		Date inTime = new Date();
		inTime.setTime(System.currentTimeMillis() - (45 * 60 * 1000));// 45 minutes parking time should give 3/4th
																		// parking fare
		Date outTime = new Date();
		ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR, false);

		ticket.setInTime(inTime);
		ticket.setOutTime(outTime);
		ticket.setParkingSpot(parkingSpot);

		// WHEN
		fareCalculatorService.calculateFare(ticket, false);

		// THEN
		assertEquals((0.75 * Fare.CAR_RATE_PER_HOUR), ticket.getPrice());
	}

	/**
	 * {@link #calculateFareCarWithMoreThanADayParkingTime()} Unit Test <br>
	 * GIVEN: Parking exit time for the <b>CAR</b> set to more than a day i.e. <b>24
	 * hours</b> <br>
	 * WHEN: calculation of fare service activated <br>
	 * THEN: calculation of fare service checks for duration threshold for One day
	 * and more <br>
	 * <b>Test Condition <i>PASSED</i>: </b>assertEquals (24 *
	 * Fare.CAR_RATE_PER_HOUR) <code><b>TRUE</b></code> <br>
	 * <b>Test Condition <i>FAILED</i>: </b>assertEquals (24 *
	 * Fare.CAR_RATE_PER_HOUR) <code><b>FALSE</b></code>
	 */
	@Test
	public void calculateFareCarWithMoreThanADayParkingTime() {

		// GIVEN
		Date inTime = new Date();
		inTime.setTime(System.currentTimeMillis() - (24 * 60 * 60 * 1000));// 24 hours parking time should give 24 *
																			// parking fare per hour
		Date outTime = new Date();
		ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR, false);

		ticket.setInTime(inTime);
		ticket.setOutTime(outTime);
		ticket.setParkingSpot(parkingSpot);

		// WHEN
		fareCalculatorService.calculateFare(ticket, false);

		// THEN
		assertEquals((24 * Fare.CAR_RATE_PER_HOUR), ticket.getPrice());
	}

}
