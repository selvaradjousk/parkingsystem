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
@DisplayName("Test on fare constant values")
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

	
	@DisplayName("Test fare constant WHEN bike rate per hour THEN "
			+ "equals bike rate per hour")
	@Test
	void testBikeRateDbConstanst() {
		assertEquals(fare.BIKE_RATE_PER_HOUR, BIKE_RATE_PER_HOUR_expected);
	}
	
	@DisplayName("Test fare constant WHEN car rate per hour THEN "
			+ "equals car rate per hour")
	@Test
	void testCarRateDbConstanst() {
		assertEquals(fare.BIKE_RATE_PER_HOUR, BIKE_RATE_PER_HOUR_expected);
	}
	
}