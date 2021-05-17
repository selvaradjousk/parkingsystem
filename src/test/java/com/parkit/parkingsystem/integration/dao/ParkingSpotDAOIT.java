package com.parkit.parkingsystem.integration.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.dao.ParkingSpotDAO;
import com.parkit.parkingsystem.integration.config.DataBaseTestConfig;
import com.parkit.parkingsystem.integration.service.DataBasePrepareService;
import com.parkit.parkingsystem.model.ParkingSpot;

/**
 * <b>Test Class: </b> {@link ParkingSpotDAOIT} - Parking Spot Data Access
 * Object - Tests on Functions and Methods for dealing with availability
 * management of parking spots / slots <br>
 * <b>Class Tested:</b>{@link ParkingSpotDAO}.<br>
 * <b>Project: <b> P3 - parking system - ParkIt<br>
 * 
 * @see <b>Tests:</b><br>
 *      {@link #testGetNextAvailableSpotForCar()}: Parking Spot - Integration
 *      Testing - Car Spot availability<br>
 *      {@link #testNextAvailableSpotForBike()}: Parking Spot - Integration
 *      Testing - Bike Spot availability<br>
 *      {@link #testUpdateParkingForCar()}: Parking Spot - Integration Testing -
 *      CAR Update Spot status<br>
 *      {@link #testUpdateParkingForBike()} Parking Spot - Integration Testing -
 *      BIKE Update Spot status<br>
 * 
 * @author Senthil
 */
@DisplayName("Vehicle Parking Spot Data Access Object - Integration Testing ")
public class ParkingSpotDAOIT {
	static DataBaseTestConfig testDB = new DataBaseTestConfig();
	static ParkingSpotDAO parkingSpotDAO = new ParkingSpotDAO();
	static DataBasePrepareService dBPrepareService = new DataBasePrepareService();

	@BeforeAll
	public static void setupTests() {
		parkingSpotDAO.dataBaseConfig = testDB;
	}

	@BeforeEach
	public void setupPerTest() {
		dBPrepareService.clearDBEntries();
	}

	/**
	 * {@link #testGetNextAvailableSpotForCar()} Integration Test on
	 * {@link ParkingSpotDAO#getNextAvailableSpot()}<br>
	 * GIVEN: expected parking spot value as 2 <br>
	 * WHEN: executing method to get next available parking spot <br>
	 * THEN: verify the spot actual with expected values <b>resultSet
	 * checked</b><br>
	 * <b>Test Condition <i>PASSED</i>: </b>assertEquals expected = 1
	 * <code><b>TRUE</b></code> <br>
	 * <b>Test Condition <i>FAILED</i>: </b>assertEquals expected != 1
	 * <code><b>FALSE</b></code>
	 */
	@DisplayName("Parking Spot - Integration Testing - CAR Spot availability ")
	@Test
	public void testGetNextAvailableSpotForCar() {
		// GIVEN
		final int expectedSpot = 1;

		// WHEN
		int selectedSpot = parkingSpotDAO.getNextAvailableSpot(ParkingType.CAR);

		// THEN
		assertEquals(expectedSpot, selectedSpot, "Result: Both spot not similar");
	}

	/**
	 * {@link #testNextAvailableSpotForBike()} Integration Test on
	 * {@link ParkingSpotDAO#getNextAvailableSpot()}<br>
	 * GIVEN: expected parking spot value as 1 <br>
	 * WHEN: executing method to get next available parking spot <br>
	 * THEN: verify the spot actual with expected values <b>resultSet
	 * checked</b><br>
	 * <b>Test Condition <i>PASSED</i>: </b>assertEquals expected = 4
	 * <code><b>TRUE</b></code> <br>
	 * <b>Test Condition <i>FAILED</i>: </b>assertEquals expected != 4
	 * <code><b>FALSE</b></code>
	 */
	@DisplayName("Parking Spot - Integration Testing - BIKE Spot availability ")
	@Test
	public void testNextAvailableSpotForBike() {
		// GIVEN
		final int expectedSpot = 4;

		// WHEN
		int selectedSpot = parkingSpotDAO.getNextAvailableSpot(ParkingType.BIKE);

		// THEN
		assertEquals(expectedSpot, selectedSpot, "Result: Both spot not similar");
	}

	/**
	 * {@link #testUpdateParkingForCar()} Integration Test on
	 * {@link ParkingSpotDAO#updateParking()}<br>
	 * GIVEN: updating parking spot values <br>
	 * WHEN: executing method for updating parking spot parking spot <br>
	 * THEN: verify the getNextAvailableSpot(ParkingType.CAR) with expected values
	 * <b>resultSet checked</b><br>
	 * <b>Test Condition <i>PASSED</i>: </b>assertEquals expected =
	 * getNextAvailableSpot(ParkingType.CAR) <code><b>TRUE</b></code> <br>
	 * <b>Test Condition <i>FAILED</i>: </b>assertEquals expected !=
	 * getNextAvailableSpot(ParkingType.CAR) <code><b>FALSE</b></code>
	 */
	@DisplayName("Parking Spot - Integration Testing - CAR Update Spot status ")
	@Test
	public void testUpdateParkingForCar() {
		// GIVEN
		ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR, false);

		// WHEN
		boolean updated = parkingSpotDAO.updateParking(parkingSpot);

		// THEN
		if (updated)
			assertEquals(parkingSpotDAO.getNextAvailableSpot(ParkingType.CAR), parkingSpot.getId() + 1,
					"Result: Both spot not similar");
		else
			fail("Failed to update ticket");
	}

	/**
	 * {@link #testUpdateParkingForBike()} Integration Test on
	 * {@link ParkingSpotDAO#updateParking()}<br>
	 * GIVEN: updating parking spot values <br>
	 * WHEN: executing method for updating parking spot parking spot <br>
	 * THEN: verify the getNextAvailableSpot(ParkingTypeBIKE) with expected values
	 * <b>resultSet checked</b><br>
	 * <b>Test Condition <i>PASSED</i>: </b>assertEquals expected =
	 * getNextAvailableSpot(ParkingType.BIKE) <code><b>TRUE</b></code> <br>
	 * <b>Test Condition <i>FAILED</i>: </b>assertEquals expected !=
	 * getNextAvailableSpot(ParkingType.BIKE) <code><b>FALSE</b></code>
	 */
	@DisplayName("Parking Spot - Integration Testing - BIKE Update Spot status ")
	@Test
	public void testUpdateParkingForBike() {
		// GIVEN
		ParkingSpot parkingSpot = new ParkingSpot(4, ParkingType.BIKE, false);

		// WHEN
		boolean updated = parkingSpotDAO.updateParking(parkingSpot);

		// THEN
		if (updated)
			assertEquals(parkingSpotDAO.getNextAvailableSpot(ParkingType.BIKE), parkingSpot.getId() + 1,
					"Result: Both spot not similar");
		else
			fail("Failed to update ticket");
	}
}
