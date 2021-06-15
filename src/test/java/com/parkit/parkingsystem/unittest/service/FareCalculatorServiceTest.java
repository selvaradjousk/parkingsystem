package com.parkit.parkingsystem.unittest.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import com.parkit.parkingsystem.constants.Fare;
import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.model.ParkingSpot;
import com.parkit.parkingsystem.model.Ticket;
import com.parkit.parkingsystem.service.FareCalculatorService;

@DisplayName("Test Parking Fare Calculation Service ")
@ExtendWith(MockitoExtension.class)
@TestMethodOrder(OrderAnnotation.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class FareCalculatorServiceTest {

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

	@DisplayName("Test parking Fare WHEN for CAR one hours and more displayed in minutes THEN asserts fare values")
	@ParameterizedTest(name = "Duration of Parking: {0} minutes")
	@Order(1)
	@CsvSource({ "60, 1", "90, 1.5", "300, 5" })
	void calculateFareCar(long minutesParked, double priceFactor) {

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
		assertEquals(priceFactor * Fare.CAR_RATE_PER_HOUR, ticket.getPrice(), "Result: estimated and actual price match");
	}

	@DisplayName("Test parking Fare WHEN for BIKE one hours and more displayed in minutes THEN asserts fare values")
	@ParameterizedTest(name = "Duration of Parking: {0} minutes")
	@Order(2)
	@CsvSource({ "60, 1", "90, 1.5", "300, 5" })
	void calculateFareBike(long minutesParked, double priceFactor) {

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

	@DisplayName("Test parking Fare WHEN for CAR less than one hour and more than 30 minutes THEN asserts fare values")
	@ParameterizedTest(name = "Duration of car parking: {0} Minute(s) i.e less than one hour")
	@Order(3)
	@CsvSource({ "30, 0.5", "45, 0.75", "54, 0.9" })
	void calculateFareCarWithLessThanOneHourParkingTime(int minutes, double priceConversionFactor) {

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

	@DisplayName("Test parking Fare WHEN for BIKE less than one hour and more than 30 minutes THEN asserts fare values")
	@ParameterizedTest(name = "Duration of Bike parking: {0} Minute(s) i.e less than one hour")
	@Order(4)
	@CsvSource({ "30, 0.5", "45, 0.75", "54, 0.9" })
	void calculateFareBikeWithLessThanOneHourParkingTime(int minutes, double priceConversionFactor) {

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


	@DisplayName("Test parking Fare WHEN for CAR less than 30 minutes THEN asserts fare values")
	@Order(5)
	@ParameterizedTest(name = "Duration of Car parking: {0} Minute(s) - Free parking less than 30 minutes")
	@ValueSource(ints = { 1, 15, 29 })
	void calculateFareCarWithLessThanThirtyMinutesParkingTime(int arg) {

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

	@DisplayName("Test parking Fare WHEN for BIKE less than 30 minutes THEN asserts fare values")
	@ParameterizedTest(name = "Duration of bike parking: {0} Minute(s) - Free parking less than 30 minutes")
	@Order(6)
	@ValueSource(ints = { 1, 15, 29 })
	void calculateFareBikeWithLessThanThirtyMinutesParkingTime(int arg) {

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

	@DisplayName("Test parking Fare WHEN for CAR more or equal to one day THEN asserts fare values")
	@ParameterizedTest(name = "Duration of Parking: {0} Day(s)")
	@Order(7)
	@CsvSource({ "1, 24", "2, 48", "10, 240"})
	void calculateFareCarWithMoreThanADayParkingTime(long daysParked, double estimatedPrice) {

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

	@DisplayName("Test parking Fare WHEN for BIKE more or equal to one day THEN asserts fare values")
	@ParameterizedTest(name = "Duration of Parking: {0} Day(s)")
	@Order(8)
	@CsvSource({ "1, 24", "2, 48", "10, 240" })
	void calculateFareBikeWithMoreThanADayParkingTime(long daysParked, double estimatedPrice) {

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

	@DisplayName("Test parking Fare WHEN for CAR recurrent user THEN asserts fare values with discount")
	@ParameterizedTest(name = "Duration of Parking: {0} minutes")
	@Order(9)
	@CsvSource({ "60, 1", "120, 2", "240, 4" })
	void calculateFareRecurentUserCar(long minutesParked, double priceFactor) {

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

	@DisplayName("Test parking Fare WHEN for BIKE recurrent user THEN asserts fare values with discount")
	@ParameterizedTest(name = "Duration of Parking: {0} minutes")
	@Order(10)
	@CsvSource({ "60, 1", "120, 2", "240, 4" })
	void calculateFareRecurentUserBuke(long minutesParked, double priceFactor) {

		// GIVEN
		Date inTime = new Date();
		inTime.setTime(System.currentTimeMillis() - (minutesParked * 60 * 1000));
		Date outTime = new Date();
		ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.BIKE, false);

		ticket.setInTime(inTime);
		ticket.setOutTime(outTime);
		ticket.setParkingSpot(parkingSpot);

		// WHEN
		fareCalculatorService.calculateFare(ticket, true);

		BigDecimal bd = new BigDecimal(priceFactor * Fare.BIKE_RATE_PER_HOUR * 0.95).setScale(3, RoundingMode.HALF_UP);
		double estimate = bd.doubleValue();

		// THEN
		assertEquals(estimate, ticket.getPrice(), "Result: estimated and actual price match");
	}

	@DisplayName("Test parking Fare WHEN Unknown Type of Vehicle THEN throws Null Pointer Exception")
	@Order(11)
	@Test
	void calculateFareUnkownType() {

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

	@DisplayName("Test parking Fare WHEN error check CAR future incoming time THEN throws Illegal Argument Exception")
	@Test
	@Order(12)
	void calculateFareCarWithFutureInTime() {

		// GIVEN
		Date inTime = new Date();
		inTime.setTime(System.currentTimeMillis() + (60 * 60 * 1000));
		Date outTime = new Date();
		ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR, false);

		ticket.setInTime(inTime);
		ticket.setOutTime(outTime);
		ticket.setParkingSpot(parkingSpot);

		// THEN
		assertThrows(IllegalArgumentException.class, () -> fareCalculatorService.calculateFare(ticket, false)); // WHEN
	}

	@DisplayName("Test parking Fare WHEN error check BIKE future incoming time THEN throws Illegal Argument Exception")
	@Test
	@Order(13)
	void calculateFareBikeWithFutureInTime() {

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

	@DisplayName("Test parking Fare WHEN error check Bike Negative Duration Park Time THEN throws Illegal Argument Exception")
	@Test
	@Order(14)
	void calculateFareBikeWithNegativeDurationTime() {

		// GIVEN
		Date inTime = new Date();
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

	@ParameterizedTest
	@Order(15)
	@Tag("Exceptions")
	@DisplayName("Test parking Fare WHEN for a ticket with null out time, THEN calculatorFare should raise an Illegal Argument Exception")
	@EnumSource(value = ParkingType.class, names = { "CAR", "BIKE" })
	void givenATicketWithNoOutTime_whenGetCalculatedFare_thenIllegalArgumentExceptionThrown(
			ParkingType parkingType) {

		// GIVEN
		Date inTime = new Date();
		inTime.setTime(System.currentTimeMillis());
		ParkingSpot parkingSpot = new ParkingSpot(1, parkingType, false);

		ticket.setInTime(inTime);
		ticket.setOutTime(null);
		ticket.setParkingSpot(parkingSpot);

		// THEN
		assertThrows(NullPointerException.class, () -> fareCalculatorService.calculateFare(ticket, false),
				"Result: Exception thrown"); // WHEN

	}
	
	@ParameterizedTest
	@Order(15)
	@Tag("Exceptions")
	@DisplayName("Test parking Fare WHEN for a ticket with null out time THEN calculatorFare should raise an Illegal Argument Exception")
	@EnumSource(value = ParkingType.class, names = { "CAR", "BIKE" })
	void givenAVehicleTypeNull_whenGetCalculatedFare_thenNullPointerExceptionThrown(
			ParkingType parkingType) {

		// GIVEN
		Date inTime = new Date();
		inTime.setTime(System.currentTimeMillis());
//		ParkingSpot parkingSpot = new ParkingSpot(1, null, false);

		ticket.setInTime(inTime);
		ticket.setOutTime(null);
		ticket.setParkingSpot(null);

		// THEN
		assertThrows(NullPointerException.class, () -> fareCalculatorService.identifyVehicleTypeForComputeFare(ticket),
				"Result: Exception thrown"); // WHEN

	}

}
