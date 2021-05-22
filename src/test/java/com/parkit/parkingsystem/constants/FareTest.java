/**
 * 
 */
package com.parkit.parkingsystem.constants;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Class: {@link FareTest} - testing Class: {@link Fare}.
 *
 * @author Senthil
 */
final class FareTest {
	/**
	 * Instance of Fare class
	 */
	Fare fare;

	/**
	 * Private Constructor for Fare class.
	 */
	private FareTest() {
	}

	/**
	 * Parking rate BIKE_RATE_PER_HOUR.
	 */
	final double BIKE_RATE_PER_HOUR_expected = 1.0;

	/**
	 * Parking rate CAR_RATE_PER_HOUR.
	 */
	final double CAR_RATE_PER_HOUR_expected = 1.5;

	
	/**
	 * Test bike fare rate per hour.
	 */
	@DisplayName("Test BIKE_RATE_PER_HOUR")
	@Test
	void testBikeRateDbConstanst() {
		assertEquals(fare.BIKE_RATE_PER_HOUR, BIKE_RATE_PER_HOUR_expected);
	}
	
	/**
	 * Test car fare rate per hour.
	 */
	@DisplayName("TestCAR_RATE_PER_HOUR")
	@Test
	void testCarRateDbConstanst() {
		assertEquals(fare.BIKE_RATE_PER_HOUR, BIKE_RATE_PER_HOUR_expected);
	}
	
}