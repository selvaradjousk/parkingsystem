package com.parkit.parkingsystem.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import com.parkit.parkingsystem.constants.ParkingType;

import junit.framework.Assert;

class ParkingSpotTest {
	ParkingSpot parkingSpot;
	private static final ParkingType CAR = null;
	private static final ParkingType BIKE = null;
	public static final ParkingSpot pSpotCar = new ParkingSpot(2, CAR, false);
	public static final ParkingSpot pSpotBike = new ParkingSpot(2, BIKE, false);

	/**
	 * test Parking Spot Car getId.
	 */
	@Test
	public void testParkingSpotCarGetId() {
		int expected = (int) Math.random();
		pSpotCar.setId(expected);
		int actual = pSpotCar.getId();
		assertEquals(expected, actual);
	}

	/**
	 * test Parking Spot Bike getId.
	 */
	@Test
	public void testParkingSpotBikeGetId() {
		int expected = (int) Math.random();
		pSpotBike.setId(expected);
		int actual = pSpotBike.getId();
		assertEquals(expected, actual);
	}

	/**
	 * test Parking Spot Car Parking Type.
	 */
	@Test
	public void testParkingSpotCarParkingType() {
		ParkingType expected = ParkingType.CAR;
		pSpotCar.setParkingType(expected);
		ParkingType actual = pSpotCar.getParkingType();
		assertEquals(expected, actual);
	}

	/**
	 * test Parking Spot Bike Parking Type.
	 */
	@Test
	public void testParkingSpotBikeParkingType() {
		ParkingType expected = ParkingType.BIKE;
		pSpotBike.setParkingType(expected);
		ParkingType actual = pSpotBike.getParkingType();
		assertEquals(expected, actual);
	}

	/**
	 * Test Car Vehicle availability status
	 * @param vehicleType
	 * @param expected
	 */
	@ParameterizedTest(name = "Testing availability: {0} - {1}")
	@CsvSource({ "Car, true", "Car, false", "Bike, true", "Bike, False" })
	public void testParkingSpotVehicleIsAvailableTrue(String vehicleType, boolean expected) {
		pSpotCar.setAvailable(expected);
		boolean actual = pSpotCar.isAvailable();
		assertEquals(expected, actual);
	}

	
	/**
	 * instance of test for Equals Equals true (this equals object o).
	 */
	@Test
	public void testEqualsShouldReturnTrueWhenComparingTheSameInstance() {
		ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR, false);
		assertTrue(parkingSpot.equals(parkingSpot));
	}
	
	
	/**
	 * instance of test for Equals Equals Null ( o equals null).
	 */
	@Test
	public void testEqualsShouldReturnFalseWhenComparingTheNullValue() {
		ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR, false);
		assertFalse(parkingSpot.equals(null));
	}
	
	/**
	 * instance of test for Equals Not Equals getcClass.
	 */
	@Test
	public void testEqualsShouldReturnFalseWhenComparingTheWrongType() {
		ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR, false);
		assertFalse(parkingSpot.equals(ParkingType.CAR));
	}
	
	/**
	 * instance of test for Equals true Comparing New Instance With Same Values.
	 */
	@Test
	public void testEqualsShouldReturnTrueWhenComparingNewInstanceWithSameValues() {
		ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR, false);
		assertTrue(parkingSpot.equals(new ParkingSpot(1, ParkingType.CAR, false)));
	}
	
	/**
	 * instance of hashCode.
	 */
	@Test
	public void testHashCodeShouldBeEqualWithTwoInstanceWithSameValues() {
		ParkingSpot parkingSpot1 = new ParkingSpot(1, ParkingType.CAR, false);
		ParkingSpot parkingSpot2 = new ParkingSpot(1, ParkingType.CAR, false);
		assertEquals(parkingSpot1.hashCode(), parkingSpot2.hashCode());
	}
}
