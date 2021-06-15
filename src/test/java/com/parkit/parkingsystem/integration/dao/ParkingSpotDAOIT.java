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

	@DisplayName("Test Parking Spot WHEN get next CAR Spot availability THEN confirms spot availability")
	@Test
	public void testGetNextAvailableSpotForCar() throws SQLException {
		// GIVEN
		final int expectedSpot = 1;

		// WHEN
		int selectedSpot = parkingSpotDAO.getNextAvailableSpot(ParkingType.CAR);

		// THEN
		assertEquals(expectedSpot, selectedSpot, "Result: Both spot not similar");
	}
	
	@DisplayName("Test Parking Spot using DB connection method WHEN get next CAR Spot availability THEN confirms spot availability")
	@Test
	void testGetNextAvailableSpotForCarThroughDbConnection() throws SQLException, Exception {
		connection = dataBaseTestConfig.getConnection();
		preparedStatement = connection.prepareStatement("Select 2");
		resultSet = preparedStatement.executeQuery();
		boolean expected = resultSet.next();

		boolean result = (parkingSpotDAO.getNextAvailableSpot(ParkingType.CAR) == 1);

		assertEquals(expected, result); // 
	}

	@DisplayName("Test Parking Spot WHEN get next BIKE Spot availability THEN confirms spot availability")
	@Test
	public void testNextAvailableSpotForBike() throws SQLException {
		// GIVEN
		final int expectedSpot = 4;

		// WHEN
		int selectedSpot = parkingSpotDAO.getNextAvailableSpot(ParkingType.BIKE);

		// THEN
		assertEquals(expectedSpot, selectedSpot, "Result: Both spot not similar");
	}

	@DisplayName("Test Parking Spot WHEN Update CAR Spot status THEN confirms update by spot availaibilty")
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

	@DisplayName("Test Parking Spot WHEN Update BIKE Spot status THEN confirms update by spot availaibilty")
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

	@DisplayName("Test Parking Spot WHEN with Invalid ParkingSpot values THEN Unvalid Parking Spot")
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

	@DisplayName("Test Parking Spot WHEN with valid ParkingSpot values THEN validates Parking Spot")
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

	@Test
	@DisplayName("Tests Parking Spot WHEN all parking spots are occupied THEN confirms no spot availability")
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


	@Test
	@DisplayName("Tests Parking Spot WHEN penultimate parking spot available for CAR is free THEN spot available to be alloted")
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

	@Test
	@DisplayName("Tests Parking Spot WHEN penultimate parking spot available for BIKE is free THEN spot available to be alloted")
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

	@DisplayName("Tests Parking Spot WHEN parking two times and exit all THEN spot availability and ticket updated")
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

	@DisplayName("Tests Parking Spot WHEN two CAR parking on entry and one leaving THEN spot availability available for alloting for new one entering")
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

	@DisplayName("Tests Parking Spot WHEN two BIKE parking on entry and one leaving THEN spot availability available for alloting for new one entering")
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

	


