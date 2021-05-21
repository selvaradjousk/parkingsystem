package com.parkit.parkingsystem.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.parkit.parkingsystem.constants.ParkingType;

class ParkingSpotTest {
	private static final ParkingType CAR = null;
	private static final ParkingType BIKE = null;
	public static final ParkingSpot pSpotCar = new ParkingSpot(2, CAR, false);
	public static final ParkingSpot pSpotBike = new ParkingSpot(2, BIKE, false);

	@Test
	public void testParkingSpotCarGetId() {
		int expected = (int) Math.random();
		pSpotCar.setId(expected);
		int actual = pSpotCar.getId();
		assertEquals(expected, actual);
	}

	@Test
	public void testParkingSpotBikeGetId() {
		int expected = (int) Math.random();
		pSpotBike.setId(expected);
		int actual = pSpotBike.getId();
		assertEquals(expected, actual);
	}

	@Test
	public void testParkingSpotCarParkingType() {
		ParkingType expected = CAR;
		pSpotCar.setParkingType(expected);
		ParkingType actual = pSpotCar.getParkingType();
		assertEquals(expected, actual);
	}

	@Test
	public void testParkingSpotBikeParkingType() {
		ParkingType expected = BIKE;
		pSpotBike.setParkingType(expected);
		ParkingType actual = pSpotBike.getParkingType();
		assertEquals(expected, actual);
	}

	@Test
	public void testParkingSpotCarIsAvailableTrue() {
		boolean expected = true;
		pSpotCar.setAvailable(expected);
		boolean actual = pSpotCar.isAvailable();
		assertEquals(expected, actual);
	}

	@Test
	public void testParkingSpotCarIsAvailableFalse() {
		boolean expected = false;
		pSpotCar.setAvailable(expected);
		boolean actual = pSpotCar.isAvailable();
		assertEquals(expected, actual);
	}

	@Test
	public void testParkingSpotBikeIsAvailableTrue() {
		boolean expected = true;
		pSpotCar.setAvailable(expected);
		boolean actual = pSpotCar.isAvailable();
		assertEquals(expected, actual);
	}

	@Test
	public void testParkingSpotBikeIsAvailableFalse() {
		boolean expected = false;
		pSpotCar.setAvailable(expected);
		boolean actual = pSpotCar.isAvailable();
		assertEquals(expected, actual);
	}

}
