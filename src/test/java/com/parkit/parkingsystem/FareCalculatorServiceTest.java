package com.parkit.parkingsystem;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.mockito.junit.jupiter.MockitoExtension;

import com.parkit.parkingsystem.constants.Fare;
import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.model.ParkingSpot;
import com.parkit.parkingsystem.model.Ticket;
import com.parkit.parkingsystem.service.FareCalculatorService;

/**
 * <b>Test Class: </b> {@link FareCalculatorServiceTest} - Performs Unit Testing
 * on Fare calculation process for customer of ParkIt <br>
 * <b>Class Tested:</b> {@link FareCalculatorService}
 * 
 * @package - com.parkit.parkingsystem
 * @project - P3 - parking system - ParkIt
 * @see <b>Tests:</b><br>
 *      {@link #calculateFareCar(long minutesParked, double priceFactor)}:
 *      Parking Fare: CAR one hours + in minutes <br>
 *      {@link #calculateFareBike(long minutesParked, double priceFactor)}:
 *      Parking Fare: BIKE one hours + in minutes <br>
 *      {@link #calculateFareRecurentUserCar(long minutesParked, double priceFactor)}:
 *      Parking Fare: CAR recurrent user <br>
 *      {@link #calculateFareUnkownType()}: Parking Fare: ERROR-CHECK Unknown
 *      Type of Vehicle <br>
 *      {@link #calculateFareBikeWithFutureInTime()}: Parking Fare: ERROR-CHECK
 *      Bike With Future In Time <br>
 *      {@link #calculateFareBikeWithNegativeDurationTime()}: Parking Fare:
 *      ERROR-CHECK Bike Negative Duration Park Time <br>
 *      {@link #calculateFareBikeWithLessThanThirtyMinutesParkingTime(int arg)}
 *      : Parking Fare: Bike < 30 minutes parking time <br>
 *      {@link #calculateFareCarWithLessThanThirtyMinutesParkingTime(int arg)}:
 *      Parking Fare: CAR < 30 minutes parking time <br>
 *      {@link #calculateFareBikeWithLessThanOneHourParkingTime(int minutes, double priceConversionFactor)}:
 *      Parking Fare: BIKE < 1 hour parking time <br>
 *      {@link #calculateFareCarWithLessThanOneHourParkingTime(int minutes, double priceConversionFactor)}:
 *      Parking Fare: CAR < 1 hour parking time <br>
 *      {@link #calculateFareCarWithMoreThanADayParkingTime(long daysParked, double estimatedPrice)}:
 *      Parking Fare: CAR >= 1 day parking time <br>
 *      {@link #calculateFareBikeWithMoreThanADayParkingTime(long daysParked, double estimatedPrice)}:
 *      Parking Fare: BIKE >= 1 day parking time <br>
 * 
 * @author Senthil
 */
@DisplayName("Vehicle Parking Fare Calculation Service ")
@ExtendWith(MockitoExtension.class)
//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@MockitoSettings(strictness = Strictness.LENIENT)
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
	 * {@link #calculateFareCar(long minutesParked, double priceFactor)} Unit Test
	 * <br>
	 * GIVEN: One hour and plus of parking time for a <b>CAR</b> <br>
	 * WHEN: calculation of fare service activated <br>
	 * THEN: calculation of <b>CAR</b> fare per hour <br>
	 * <b>Test Condition <i>PASSED</i>: </b>assertEquals <code><b>TRUE</b></code>
	 * <br>
	 * <b>Test Condition <i>FAILED</i>: </b>assertEquals <code><b>FALSE</b></code>
	 */
	@DisplayName("Parking Fare: CAR one hours + in minutes ")
	@ParameterizedTest(name = "Duration of Parking: {0} minutes")
	@CsvSource({ "60, 1", "90, 1.5", "120, 2", "180, 3", "240, 4", "300, 5" })
	public void calculateFareCar(long minutesParked, double priceFactor) {

		// GIVEN
		Date inTime = new Date();
		inTime.setTime(System.currentTimeMillis() - (minutesParked * 60 * 1000));
		Date outTime = new Date();
		ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR, false);

		ticket.setInTime(inTime);
		ticket.setOutTime(outTime);
		ticket.setParkingSpot(parkingSpot);

		// WHEN
		fareCalculatorService.calculateFare(ticket, false);

		// THEN
		assertEquals(priceFactor * Fare.CAR_RATE_PER_HOUR, ticket.getPrice(),
				"Result: estimated and actual price match");
	}

	/**
	 * {@link #calculateFareBike(long minutesParked, double priceFactor)} Unit Test
	 * <br>
	 * GIVEN: One hour and plus of parking time for a <b>CAR</b> <br>
	 * WHEN: calculation of fare service activated <br>
	 * THEN: calculation of <b>BIKE</b> fare per hour <br>
	 * <b>Test Condition <i>PASSED</i>: </b>assertEquals <code><b>TRUE</b></code>
	 * <br>
	 * <b>Test Condition <i>FAILED</i>: </b>assertEquals <code><b>FALSE</b></code>
	 */
	@DisplayName("Parking Fare: BIKE one hours + in minutes ")
	@ParameterizedTest(name = "Duration of Parking: {0} minutes")
	@CsvSource({ "60, 1", "90, 1.5", "120, 2", "180, 3", "240, 4", "300, 5" })
	public void calculateFareBike(long minutesParked, double priceFactor) {

		// GIVEN
		Date inTime = new Date();
		inTime.setTime(System.currentTimeMillis() - (minutesParked * 60 * 1000));
		Date outTime = new Date();
		ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.BIKE, false);

		ticket.setInTime(inTime);
		ticket.setOutTime(outTime);
		ticket.setParkingSpot(parkingSpot);

		// WHEN
		fareCalculatorService.calculateFare(ticket, false);

		BigDecimal bd = new BigDecimal(priceFactor * Fare.BIKE_RATE_PER_HOUR).setScale(3, RoundingMode.HALF_UP);
		double estimate = bd.doubleValue();

		// THEN
		assertEquals(estimate, ticket.getPrice(), "Result: estimated and actual price match");
	}

	/**
	 * {@link #calculateFareRecurentUserCar(long minutesParked, double priceFactor)}
	 * Unit Test <br>
	 * GIVEN: Five hour of parking time for a <b>CAR</b> <br>
	 * WHEN: calculation of fare service activated <br>
	 * THEN: calculation of <b>Recurrence of user</b> tested <br>
	 * <b>Test Condition <i>PASSED</i>: </b>assertEquals fare = 7.125€
	 * <code><b>TRUE</b></code> <br>
	 * <b>Test Condition <i>FAILED</i>: </b>assertEquals fare != 7.125€
	 * <code><b>FALSE</b></code>
	 */
	@DisplayName("Parking Fare: CAR recurrent user ")
	@ParameterizedTest(name = "Duration of Parking: {0} minutes")
	@CsvSource({ "60, 1", "120, 2", "180, 3", "240, 4", "300, 5" })
	public void calculateFareRecurentUserCar(long minutesParked, double priceFactor) {

		// GIVEN
		Date inTime = new Date();
		inTime.setTime(System.currentTimeMillis() - (minutesParked * 60 * 1000));
		Date outTime = new Date();
		ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR, false);

		ticket.setInTime(inTime);
		ticket.setOutTime(outTime);
		ticket.setParkingSpot(parkingSpot);

		// WHEN
		fareCalculatorService.calculateFare(ticket, true);

		BigDecimal bd = new BigDecimal(priceFactor * Fare.CAR_RATE_PER_HOUR * 0.95).setScale(3, RoundingMode.HALF_UP);
		double estimate = bd.doubleValue();

		// THEN
		assertEquals(estimate, ticket.getPrice(), "Result: estimated and actual price match");
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
	@DisplayName("Parking Fare: ERROR-CHECK Unknown Type of Vehicle ")
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
		assertThrows(NullPointerException.class, () -> fareCalculatorService.calculateFare(ticket, false),
				"Result: exception thrown");// WHEN

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
	@DisplayName("Parking Fare: ERROR-CHECK Bike With Future In Time ")
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
	@DisplayName("Parking Fare: ERROR-CHECK Bike Negative Duration Park Time ")
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
		assertThrows(IllegalArgumentException.class, () -> fareCalculatorService.calculateFare(ticket, false),
				"Result: Exception thrown"); // WHEN
	}

	/**
	 * {@link #calculateFareBikeWithLessThanThirtyMinutesParkingTime(int arg)} Unit
	 * Test <br>
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
	@DisplayName("Parking Fare: Bike < 30 minutes parking time ")
	@ParameterizedTest(name = "Duration of bike parking: {0} Minute(s) - Free parking less than 30 minutes")
	@ValueSource(ints = { 1, 15, 29 })
	public void calculateFareBikeWithLessThanThirtyMinutesParkingTime(int arg) {

		// GIVEN
		Date inTime = new Date();
		inTime.setTime(System.currentTimeMillis() - (arg * 60 * 1000));// less than 30 minutes parking time should give
																		// NO parking fare - free parking
		Date outTime = new Date();
		ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.BIKE, false);

		ticket.setInTime(inTime);
		ticket.setOutTime(outTime);
		ticket.setParkingSpot(parkingSpot);

		// WHEN
		fareCalculatorService.calculateFare(ticket, false);

		// THEN
		assertEquals((0.0 * Fare.BIKE_RATE_PER_HOUR), ticket.getPrice(), "Result: estimated and actual price match");
	}

	/**
	 * {@link #calculateFareCarWithLessThanThirtyMinutesParkingTime(int arg)} Unit
	 * Test <br>
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
	@DisplayName("Parking Fare: CAR < 30 minutes parking time ")
	@ParameterizedTest(name = "Duration of Car parking: {0} Minute(s) - Free parking less than 30 minutes")
	@ValueSource(ints = { 1, 15, 29 })
	public void calculateFareCarWithLessThanThirtyMinutesParkingTime(int arg) {

		// GIVEN
		Date inTime = new Date();
		inTime.setTime(System.currentTimeMillis() - (arg * 60 * 1000));// less than 30 minutes parking time should give
																		// NO parking fare - free parking
		Date outTime = new Date();
		ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR, false);

		ticket.setInTime(inTime);
		ticket.setOutTime(outTime);
		ticket.setParkingSpot(parkingSpot);

		// WHEN
		fareCalculatorService.calculateFare(ticket, false);

		// THEN
		assertEquals((0.0 * Fare.CAR_RATE_PER_HOUR), ticket.getPrice(), "Result: estimated and actual price match");
	}

	/**
	 * {@link calculateFareBikeWithLessThanOneHourParkingTime() Unit Test <br>
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
	@DisplayName("Parking Fare: BIKE < 1 hour parking time ")
	@ParameterizedTest(name = "Duration of Bike parking: {0} Minute(s) i.e less than one hour")
	@CsvSource({ "30, 0.5", "45, 0.75", "54, 0.9" })
	public void calculateFareBikeWithLessThanOneHourParkingTime(int minutes, double priceConversionFactor) {

		// GIVEN
		Date inTime = new Date();
		inTime.setTime(System.currentTimeMillis() - (minutes * 60 * 1000));// 45 minutes parking time should give 3/4th
																			// parking fare
		Date outTime = new Date();
		ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.BIKE, false);

		ticket.setInTime(inTime);
		ticket.setOutTime(outTime);
		ticket.setParkingSpot(parkingSpot);

		// WHEN
		fareCalculatorService.calculateFare(ticket, false);

		// THEN
		assertEquals((priceConversionFactor * Fare.BIKE_RATE_PER_HOUR), ticket.getPrice(),
				"Result: estimated and actual price match");
	}

	/**
	 * {@link #calculateFareCarWithLessThanOneHourParkingTime(int minutes, double priceConversionFactor)}
	 * Unit Test <br>
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
	@DisplayName("Parking Fare: CAR < 1 hour parking time ")
	@ParameterizedTest(name = "Duration of car parking: {0} Minute(s) i.e less than one hour")
	@CsvSource({ "30, 0.5", "45, 0.75", "54, 0.9" })
	public void calculateFareCarWithLessThanOneHourParkingTime(int minutes, double priceConversionFactor) {

		// GIVEN
		Date inTime = new Date();
		inTime.setTime(System.currentTimeMillis() - (minutes * 60 * 1000));// 45 minutes parking time should give 3/4th
																			// parking fare
		Date outTime = new Date();
		ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR, false);

		ticket.setInTime(inTime);
		ticket.setOutTime(outTime);
		ticket.setParkingSpot(parkingSpot);

		// WHEN
		fareCalculatorService.calculateFare(ticket, false);

		// THEN
		assertEquals((priceConversionFactor * Fare.CAR_RATE_PER_HOUR), ticket.getPrice(),
				"Result: estimated and actual price match");
	}

	/**
	 * {@link #calculateFareCarWithMoreThanADayParkingTime(long daysParked, double estimatedPrice)}
	 * Unit Test <br>
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
	@DisplayName("Parking Fare: CAR >= 1 day parking time ")
	@ParameterizedTest(name = "Duration of Parking: {0} Day(s)")
	@CsvSource({ "1, 24", "2, 48", "3, 72", "5, 120", "10, 240", "15, 360" })
	public void calculateFareCarWithMoreThanADayParkingTime(long daysParked, double estimatedPrice) {

		// GIVEN
		Date inTime = new Date();
		inTime.setTime(System.currentTimeMillis() - (daysParked * 24 * 60 * 60 * 1000));// 24 hours parking time should
																						// give 24 *
		// parking fare per hour
		Date outTime = new Date();
		ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR, false);

		ticket.setInTime(inTime);
		ticket.setOutTime(outTime);
		ticket.setParkingSpot(parkingSpot);

		// WHEN
		fareCalculatorService.calculateFare(ticket, false);

		// THEN
		assertEquals((estimatedPrice * Fare.CAR_RATE_PER_HOUR), ticket.getPrice(),
				"Result: estimated and actual price match");
	}

	/**
	 * {@link #calculateFareBikeWithMoreThanADayParkingTime(long daysParked, double estimatedPrice)}
	 * Unit Test <br>
	 * GIVEN: Parking exit time for the <b>BIKE</b> set to more than a day i.e.
	 * <b>24 hours</b> <br>
	 * WHEN: calculation of fare service activated <br>
	 * THEN: calculation of fare service checks for duration threshold for One day
	 * and more <br>
	 * <b>Test Condition <i>PASSED</i>: </b>assertEquals (24 *
	 * Fare.CAR_RATE_PER_HOUR) <code><b>TRUE</b></code> <br>
	 * <b>Test Condition <i>FAILED</i>: </b>assertEquals (24 *
	 * Fare.CAR_RATE_PER_HOUR) <code><b>FALSE</b></code>
	 */
	@DisplayName("Parking Fare: BIKE >= 1 day parking time ")
	@ParameterizedTest(name = "Duration of Parking: {0} Day(s)")
	@CsvSource({ "1, 24", "2, 48", "3, 72", "5, 120", "10, 240", "15, 360" })
	public void calculateFareBikeWithMoreThanADayParkingTime(long daysParked, double estimatedPrice) {

		// GIVEN
		Date inTime = new Date();
		inTime.setTime(System.currentTimeMillis() - (daysParked * 24 * 60 * 60 * 1000));// 24 hours parking time should
																						// give 24 *
		// parking fare per hour
		Date outTime = new Date();
		ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.BIKE, false);

		ticket.setInTime(inTime);
		ticket.setOutTime(outTime);
		ticket.setParkingSpot(parkingSpot);

		// WHEN
		fareCalculatorService.calculateFare(ticket, false);

		// THEN
		assertEquals((estimatedPrice * Fare.BIKE_RATE_PER_HOUR), ticket.getPrice(),
				"Result: estimated and actual price match");
	}

}
