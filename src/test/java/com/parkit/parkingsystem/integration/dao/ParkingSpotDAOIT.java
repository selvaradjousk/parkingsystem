package com.parkit.parkingsystem.integration.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.any;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.dao.ParkingSpotDAO;
import com.parkit.parkingsystem.dao.TicketDAO;
import com.parkit.parkingsystem.integration.config.DataBaseTestConfig;
import com.parkit.parkingsystem.integration.service.DataBasePrepareService;
import com.parkit.parkingsystem.model.ParkingSpot;
import com.parkit.parkingsystem.model.Ticket;
import com.parkit.parkingsystem.service.ParkingService;
import com.parkit.parkingsystem.util.InputReaderUtil;

/**
 * <b>Test Class: </b> {@link ParkingSpotDAOIT} - Parking Spot Data Access
 * Object - Tests on Functions and Methods for dealing with availability
 * management of parking spots / slots <br>
 * <b>Class Tested:</b>{@link ParkingSpotDAO}.<br>
 * <b>Project: </b> P3 - parking system - ParkIt<br>
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
@DisplayName("IT - Vehicle Parking Spot Data Access Object")
@ExtendWith(MockitoExtension.class)
public class ParkingSpotDAOIT {

	private static DataBaseTestConfig dataBaseTestConfig = new DataBaseTestConfig();
	private static ParkingSpotDAO parkingSpotDAO = new ParkingSpotDAO();
	private static DataBasePrepareService dataBasePrepareService = new DataBasePrepareService();
	private static TicketDAO ticketDAO;
	private static ParkingSpot parkingSpot;
	PreparedStatement preparedStatement;
	ByteArrayOutputStream byteArrayOutputStream;
	Connection connection;
	ResultSet resultSet;
	@Mock
	private static InputReaderUtil inputReaderUtil;

	private static final Logger LOGGER 
	= LogManager.getLogger("ParkingSpotDAOIT");
	
	@BeforeAll
	public static void setupTests() {
		parkingSpotDAO.setDataBaseConfig(dataBaseTestConfig);
		parkingSpotDAO = new ParkingSpotDAO();
		ticketDAO = new TicketDAO();
		ticketDAO.setDataBaseConfig(dataBaseTestConfig);
		dataBasePrepareService = new DataBasePrepareService();
	}

	@BeforeEach
	public void setupPerTest() throws SQLException {

		dataBasePrepareService.clearDBEntries();
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
	 * @throws SQLException 
	 */
	@DisplayName("Parking Spot - CAR Spot availability ")
	@Test
	public void testGetNextAvailableSpotForCar() throws SQLException {
		// GIVEN
		final int expectedSpot = 1;

		// WHEN
		int selectedSpot = parkingSpotDAO.getNextAvailableSpot(ParkingType.CAR);

		// THEN
		assertEquals(expectedSpot, selectedSpot, "Result: Both spot not similar");
	}
	
	@Test
	void testGetNextAvailableSpotForCarThroughDbConnection() throws SQLException, Exception {
		connection = dataBaseTestConfig.getConnection();
		preparedStatement = connection.prepareStatement("Select 2");
		resultSet = preparedStatement.executeQuery();
		boolean expected = resultSet.next();

		boolean result = (parkingSpotDAO.getNextAvailableSpot(ParkingType.CAR) == 1);

		assertEquals(expected, result); // 
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
	 * @throws SQLException 
	 */
	@DisplayName("Parking Spot - BIKE Spot availability ")
	@Test
	public void testNextAvailableSpotForBike() throws SQLException {
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
	 * @throws SQLException 
	 */
	@DisplayName("Parking Spot - CAR Update Spot status")
	@Test
	public void testUpdateParkingForCar() throws SQLException {
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
	 * @throws SQLException 
	 */
	@DisplayName("Parking Spot - BIKE Update Spot status ")
	@Test
	public void testUpdateParkingForBike() throws SQLException {
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

	/**
	 * {@link #testUpdateParkingInvalidParkingSpot()} Integration Test on
	 * {@link ParkingSpotDAO#updateParking()}<br>
	 * GIVEN: invalid input parking spot <br>
	 * WHEN: setting expected value<br>
	 * THEN: verify the invalid input is <b>rejected</b><br>
	 * <b>Test Condition <i>PASSED</i>: </b>assertEquals expected = actual
	 * <code><b>TRUE</b></code> <br>
	 * <b>Test Condition <i>FAILED</i>: </b>assertEquals expected != actual
	 * <code><b>FALSE</b></code>
	 * @throws SQLException 
	 */
	@DisplayName("Parking Spot with Invalid ParkingSpot values ")
	@Test
	public void testUpdateParkingInvalidParkingSpot() throws SQLException {
		// GIVEN
		ParkingSpot parkingSpot = new ParkingSpot(7, ParkingType.BIKE, false);

		// WHEN
		boolean expected = false;

		boolean actual = parkingSpotDAO.updateParking(parkingSpot);
		// THEN
		assertEquals(actual, expected);
	}

	/**
	 * {@link #testUpdateParkingValidParkingSpot()} Integration Test on
	 * {@link ParkingSpotDAO#updateParking()}<br>
	 * GIVEN: Valid input parking spot <br>
	 * WHEN: setting expected value<br>
	 * THEN: verify the invalid input is <b> accepted</b><br>
	 * <b>Test Condition <i>PASSED</i>: </b>assertEquals expected = actual
	 * <code><b>TRUE</b></code> <br>
	 * <b>Test Condition <i>FAILED</i>: </b>assertEquals expected != actual
	 * <code><b>FALSE</b></code>
	 * @throws SQLException 
	 */
	@DisplayName("Parking Spot with Valid ParkingSpot values ")
	@Test
	public void testUpdateParkingValidParkingSpot() throws SQLException {
		// GIVEN
		ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.BIKE, false);

		// WHEN
		boolean expected = true;

		boolean actual = parkingSpotDAO.updateParking(parkingSpot);
		// THEN
		assertEquals(actual, expected);
	}

	/**
	 * {@link #testsNextAvailableSpotStatustWhenAllOccupied()} Integration Test on
	 * {@link ParkingSpotDAO#updateParking()}<br>
	 * GIVEN: Parking spot with all three vehicles<br>
	 * WHEN: setting expected for update <br>
	 * THEN: verify the <b>availability and identify no free spot available<br>
	 * <b>Test Condition <i>PASSED</i>: </b>assertEquals expected = actual
	 * <code><b>TRUE</b></code> <br>
	 * <b>Test Condition <i>FAILED</i>: </b>assertEquals expected != actual
	 * <code><b>FALSE</b></code>
	 * @throws SQLException 
	 */
	@Test
	@DisplayName("Tests of if all parking spots are occupied")
	public void testsNextAvailableSpotStatustWhenAllOccupied() throws SQLException {
		// GIVEN
		ParkingSpot parkingSpot1 = new ParkingSpot(1, ParkingType.CAR, false);
		ParkingSpot parkingSpot2 = new ParkingSpot(2, ParkingType.CAR, false);
		ParkingSpot parkingSpot3 = new ParkingSpot(3, ParkingType.CAR, false);

		// WHEN
		parkingSpotDAO.updateParking(parkingSpot1);
		parkingSpotDAO.updateParking(parkingSpot2);
		parkingSpotDAO.updateParking(parkingSpot3);

		int actual = parkingSpotDAO.getNextAvailableSpot(ParkingType.CAR);

		// THEN
		assertEquals(0, actual);

//		when(inputReaderUtil.readSelection()).thenReturn(1);
//		when(inputReaderUtil.readVehicleRegistrationNumber()).thenReturn("ABCDEF");
//		ParkingService parkingService = new ParkingService(inputReaderUtil, parkingSpotDAO, ticketDAO);
//
//		parkingService.processIncomingVehicle();
//		parkingService.processIncomingVehicle();
//		parkingService.processIncomingVehicle();
//
//		assertEquals(0, parkingSpotDAO.getNextAvailableSpot(ParkingType.CAR));
	}

	/**
	 * {@link #testNextAvailableSlotWhenPenutimateIsLeftFree()} Integration Test on
	 * {@link ParkingSpotDAO#updateParking()}<br>
	 * GIVEN: When two vehicles occupied<br>
	 * WHEN: setting expected for update <br>
	 * THEN: verify the <b>availability and allocated the third to incoming
	 * vehicle<br>
	 * <b>Test Condition <i>PASSED</i>: </b>assertEquals expected = actual
	 * <code><b>TRUE</b></code> <br>
	 * <b>Test Condition <i>FAILED</i>: </b>assertEquals expected != actual
	 * <code><b>FALSE</b></code>
	 * @throws SQLException 
	 */
	@Test
	@DisplayName("Tests CAR for penultimate parking spot available to be alloted")
	public void testNextAvailableSlotWhenPenutimateIsLeftFree() throws SQLException {
		// GIVEN
		ParkingSpot parkingSpot1 = new ParkingSpot(1, ParkingType.CAR, false);
		ParkingSpot parkingSpot2 = new ParkingSpot(2, ParkingType.CAR, false);

		// WHEN
		parkingSpotDAO.updateParking(parkingSpot1);
		parkingSpotDAO.updateParking(parkingSpot2);

		int actual = parkingSpotDAO.getNextAvailableSpot(ParkingType.CAR);

		// THEN
		assertEquals(3, actual);
	}

	/**
	 * {@link #testBikeNextAvailableSlotWhenPenutimateIsLeftFree()} Integration Test
	 * on {@link ParkingSpotDAO#updateParking()}<br>
	 * GIVEN: When two vehicles occupied<br>
	 * WHEN: setting expected for update <br>
	 * THEN: verify the <b>availability and allocated the third to incoming
	 * vehicle<br>
	 * <b>Test Condition <i>PASSED</i>: </b>assertEquals expected = actual
	 * <code><b>TRUE</b></code> <br>
	 * <b>Test Condition <i>FAILED</i>: </b>assertEquals expected != actual
	 * <code><b>FALSE</b></code>
	 * @throws SQLException 
	 */
	@Test
	@DisplayName("Tests BIKE for penultimate parking spot available to be alloted")
	public void testBikeNextAvailableSlotWhenPenutimateIsLeftFree() throws SQLException {
		// GIVEN
		ParkingSpot parkingSpot1 = new ParkingSpot(1, ParkingType.BIKE, false);
		ParkingSpot parkingSpot2 = new ParkingSpot(2, ParkingType.BIKE, false);

		// WHEN
		parkingSpotDAO.updateParking(parkingSpot1);
		parkingSpotDAO.updateParking(parkingSpot2);

		int actual = parkingSpotDAO.getNextAvailableSpot(ParkingType.BIKE);

		// THEN
		assertEquals(4, actual);
	}

	@Test
	public void TestParkingIn2TimesAndExitAll() throws Exception {
		when(inputReaderUtil.readSelection()).thenReturn(1);
		when(inputReaderUtil.readVehicleRegistrationNumber()).thenReturn("ABCDEF");
		ParkingService parkingService = new ParkingService(inputReaderUtil, parkingSpotDAO, ticketDAO);

		parkingService.processIncomingVehicle();
		Thread.sleep(500);// wait 1 sec before the exiting

		parkingService.processIncomingVehicle();
		Thread.sleep(500);// wait 1 sec before the exiting

		parkingService.processExitingVehicle();
		Thread.sleep(500);// wait 1 sec before the exiting
		parkingService.processExitingVehicle();

		Ticket ticket = ticketDAO.getTicket("ABCDEF");
		assertNotNull(ticket.getOutTime());
	}

	@Test
	public void testCarParkingSpotGetAllotedTwoWhenTwoIncomingVehiculesButOneOfThemExiting() throws SQLException {
		parkingSpot = new ParkingSpot(1, ParkingType.CAR, false);
		ParkingSpot parkingSpot2 = new ParkingSpot(2, ParkingType.CAR, false);

		parkingSpotDAO.updateParking(parkingSpot);
		parkingSpotDAO.updateParking(parkingSpot2);
		parkingSpot.setAvailable(true);
		parkingSpotDAO.updateParking(parkingSpot);
		int result = parkingSpotDAO.getNextAvailableSpot(ParkingType.CAR);


		assertEquals(1, result);
	}

	@Test
	public void testBikeParkingSpotGetAllotedTwoWhenTwoIncomingVehiculesButOneOfThemExiting() throws SQLException {
		parkingSpot = new ParkingSpot(1, ParkingType.CAR, false);
		ParkingSpot parkingSpot2 = new ParkingSpot(2, ParkingType.BIKE, false);

		parkingSpotDAO.updateParking(parkingSpot);
		parkingSpotDAO.updateParking(parkingSpot2);
		parkingSpot.setAvailable(true);
		parkingSpotDAO.updateParking(parkingSpot);
		int result = parkingSpotDAO.getNextAvailableSpot(ParkingType.CAR);

		assertEquals(1, result);
	}
}

	


