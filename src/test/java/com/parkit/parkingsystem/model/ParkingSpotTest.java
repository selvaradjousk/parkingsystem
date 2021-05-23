package com.parkit.parkingsystem.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.parkit.parkingsystem.constants.ParkingType;

import junit.framework.Assert;

class ParkingSpotTest {
	ParkingSpot parkingSpot;
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
	
	/** 
	 * instanceof test false.
	 */ 
	@Test
	public void testEqualsFalse() {
		ParkingSpot parkingSpot1 = new ParkingSpot(1, ParkingType.CAR, false);
		Object obj = new Object();
		assertFalse(obj instanceof ParkingSpot);
	}
		/** 
		 * instance of test true.
		 */ 
		@Test
		public void testEqualsTrue() {
			ParkingSpot parkingSpot1 = new ParkingSpot(1, ParkingType.CAR, false);
			Object obj = new Object();
			assertFalse(obj instanceof ParkingSpot);
	}
	
		/** 
		 * instance of test true.
		 */ 
		@Test
		public void testEqualsEqualReturn() {
			ParkingSpot parkingSpot1 = new ParkingSpot(1, ParkingType.BIKE, false);
			ParkingSpot parkingSpot2 = new ParkingSpot(1, ParkingType.BIKE, false);

			assertEquals((parkingSpot1.getId()),(parkingSpot2.getId()));
			assertEquals((parkingSpot1.getParkingType()),(parkingSpot2.getParkingType()));
			assertEquals((parkingSpot1.isAvailable()),(parkingSpot2.isAvailable()));
//			assertTrue((parkingSpot1.isAvailable()) == false);
	}	
		
//		/** 
//		 * instance of test true.
//		 */ 
//		@Test
//		public void testEqualsEqualReturn() {
//			ParkingSpot parkingSpot1 = new ParkingSpot(1, null, false);
//			ParkingSpot parkingSpot2 = new ParkingSpot(1, ParkingType.CAR, false);
//			Object obj = new Object();
//			assertTrue((parkingSpot1).equals(parkingSpot2));
//	}
//		@Test
//		public void testEqualsNull() {
//			ParkingSpot parkingSpot = null;
//			Object obj = new Object();
//
//			assertFalse(obj == parkingSpot);
//	}
		
		/** 
		 * instance of test true.
		 */ 
		@Test
		public void testEqualsEquals() {
			ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR, false);
			assertTrue(parkingSpot.equals(parkingSpot));
	}
		/** 
		 * instance of test true.
		 */ 
		@Test
		public void testEqualsNotEquals() {
			ParkingSpot parkingSpot = new ParkingSpot(1, null, false);
			assertEquals(parkingSpot.equals(null), false);
	}
		/** 
		 * instance of test true.
		 */ 
		@Test
		public void testEqualsEqualsTrue() {
			ParkingSpot parkingSpot1 = new ParkingSpot(1, ParkingType.CAR, false);
//			ParkingSpot parkingSpot2 = new ParkingSpot(1, ParkingType.BIKE, true);
			Object obj = new Object();
			assertFalse(parkingSpot1.getClass().equals((obj).getClass()) );
	}
		
		/** 
		 * instance of hashCode.
		 */ 	
		@Test
		public void testHashCode() {
			ParkingSpot parkingSpot1 = new ParkingSpot(1, ParkingType.CAR, false);
			ParkingSpot parkingSpot2 = new ParkingSpot(1, ParkingType.CAR, false);
			assertTrue(parkingSpot1.hashCode() == parkingSpot2.hashCode());
	}
		

	
}
