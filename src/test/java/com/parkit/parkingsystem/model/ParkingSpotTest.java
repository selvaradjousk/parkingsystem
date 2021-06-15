package com.parkit.parkingsystem.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import com.parkit.parkingsystem.constants.ParkingType;

import junit.framework.Assert;

@DisplayName("Test Parking Spot Model Class")
class ParkingSpotTest {
	ParkingSpot parkingSpot;
	private static final ParkingType CAR = null;
	private static final ParkingType BIKE = null;
	public static final ParkingSpot pSpotCar = new ParkingSpot(2, CAR, false);
	public static final ParkingSpot pSpotBike = new ParkingSpot(2, BIKE, false);

	@DisplayName("Test Parking Spot WHEN get idfor car THEN gets id")
	@Test
	public void testParkingSpotCarGetId() {
		int expected = (int) Math.random();
		pSpotCar.setId(expected);
		int actual = pSpotCar.getId();
		assertEquals(expected, actual);
	}

	@DisplayName("Test Parking Spot WHEN get id for bike THEN gets id")
	@Test
	public void testParkingSpotBikeGetId() {
		int expected = (int) Math.random();
		pSpotBike.setId(expected);
		int actual = pSpotBike.getId();
		assertEquals(expected, actual);
	}

	@DisplayName("Test Parking Spot WHEN get parking type for car THEN gets parking type")
	@Test
	public void testParkingSpotCarParkingType() {
		ParkingType expected = ParkingType.CAR;
		pSpotCar.setParkingType(expected);
		ParkingType actual = pSpotCar.getParkingType();
		assertEquals(expected, actual);
	}

	@DisplayName("Test Parking Spot WHEN get parking type for bike THEN gets parking type")
	@Test
	public void testParkingSpotBikeParkingType() {
		ParkingType expected = ParkingType.BIKE;
		pSpotBike.setParkingType(expected);
		ParkingType actual = pSpotBike.getParkingType();
		assertEquals(expected, actual);
	}

	@ParameterizedTest(name = "Test ParkingSpot WHEN availability set THEN status : {0} - {1} confirmed")
	@CsvSource({ "Car, true", "Car, false", "Bike, true", "Bike, False" })
	public void testParkingSpotVehicleIsAvailableTrue(String vehicleType, boolean expected) {
		pSpotCar.setAvailable(expected);
		boolean actual = pSpotCar.isAvailable();
		assertEquals(expected, actual);
	}

	@DisplayName("Test Parking Spot Equals WHEN comparing the same instance THEN returns TRUE")
	@Test
	public void testEqualsShouldReturnTrueWhenComparingTheSameInstance() {
		ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR, false);
		assertTrue(parkingSpot.equals(parkingSpot));
	}
	
	
	@DisplayName("Test Parking Spot Equals WHEN instance is not null THEN returns FALSE for null value")
	@Test
	public void testEqualsShouldReturnFalseWhenComparingTheNullValue() {
		ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR, false);
		assertFalse(parkingSpot.equals(null));
	}
	
	@DisplayName("Test Parking Spot Equals WHEN comparing wrong type THEN returns Equals Not Equals getcClass")
	@Test
	public void testEqualsShouldReturnFalseWhenComparingTheWrongType() {
		ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR, false);
		assertFalse(parkingSpot.equals(ParkingType.CAR));
	}
	
	@DisplayName("Test Parking Spot Equals WHEN comparing New Instance With Same Values THEN returns TRUE")
	@Test
	public void testEqualsShouldReturnTrueWhenComparingNewInstanceWithSameValues() {
		ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR, false);
		assertTrue(parkingSpot.equals(new ParkingSpot(1, ParkingType.CAR, false)));
	}
	
	@DisplayName("Test Parking Spot hashCode WHEN two Instance With Same Values THEN returns confirmation equals")
	@Test
	public void testHashCodeShouldBeEqualWithTwoInstanceWithSameValues() {
		ParkingSpot parkingSpot1 = new ParkingSpot(1, ParkingType.CAR, false);
		ParkingSpot parkingSpot2 = new ParkingSpot(1, ParkingType.CAR, false);
		assertEquals(parkingSpot1.hashCode(), parkingSpot2.hashCode());
	}
}
