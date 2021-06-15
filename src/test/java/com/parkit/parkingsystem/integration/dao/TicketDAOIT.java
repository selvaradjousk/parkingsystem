package com.parkit.parkingsystem.integration.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.parkit.parkingsystem.constants.DBConstants;
import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.dao.TicketDAO;
import com.parkit.parkingsystem.integration.config.DataBaseTestConfig;
import com.parkit.parkingsystem.integration.service.DataBasePrepareService;
import com.parkit.parkingsystem.model.ParkingSpot;
import com.parkit.parkingsystem.model.Ticket;

@DisplayName("IT - Vehicle Ticket Data Access Object")
public class TicketDAOIT {
	static TicketDAO testTicketDAO = new TicketDAO();
	static DataBaseTestConfig testDB = new DataBaseTestConfig();
	static DataBasePrepareService prepareDBService = new DataBasePrepareService();

	@BeforeAll
	public static void setupTests() {
		testTicketDAO.setDataBaseConfig(testDB);
	}

	@BeforeEach
	public void setupPerTest() throws SQLException {
		prepareDBService.clearDBEntries();
	}

	@AfterAll
	public static void cleanTests() throws SQLException {
		prepareDBService.clearDBEntries();
	}

	@Test
	@DisplayName("Test Ticket WHEN save ticket THEN Ticket saved")
	public void testSaveTicket() throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException {
		// GIVEN
		String testVehicleRegNumber = "ABCEDFGH";

		Connection testConn = null;
		PreparedStatement testStmt = null;
		ResultSet testRs = null;

		Ticket testTicket = createTestingTicket(testVehicleRegNumber);
		boolean expectedStatus = true;

		// WHEN
		testTicketDAO.saveTicket(testTicket);

		// THEN
		try {
			testConn = testDB.getConnection();
			testStmt = testConn.prepareStatement("Select * from ticket where VEHICLE_REG_NUMBER=?");
			testStmt.setString(1, testVehicleRegNumber);

			testRs = testStmt.executeQuery();
			assertEquals(expectedStatus, testRs.next(), "Result: Both Ticket status matches");
		} catch (SQLException e) {
			System.out.println(e);
		} finally {
			testDB.closeConnection(testConn);
			testDB.closePreparedStatement(testStmt);
			testDB.closeResultSet(testRs);
		}
	}

	@DisplayName("Test Ticket WHEN get ticket THEN retrieves Ticket information")
	@Test
	public void testGetTicket() throws ClassNotFoundException, SQLException {
		// GIVEN
		String testVehicleRegNumber = "MLKJHG";
		Ticket testTicket = createTestingTicket(testVehicleRegNumber);
		testTicketDAO.saveTicket(testTicket);

		// WHEN
		Ticket gettingTicket = testTicketDAO.getTicket(testVehicleRegNumber);

		// THEN
		assertNotEquals(null, gettingTicket, "Result: Actual Ticket status not null");
		assertEquals(testVehicleRegNumber, gettingTicket.getVehicleRegNumber(),
				"Result: Both Ticket Registration elements matches");
	}

	@DisplayName("Test Ticket WHEN update ticket THEN Ticket information updated in database")
	@Test
	public void testUpdateTicket() throws ClassNotFoundException, SQLException {
		// GIVEN
		String testVehicleRegNumber = "SDFGHJK";
		Ticket testTicket = createTestingTicket(testVehicleRegNumber);
		testTicketDAO.saveTicket(testTicket);

		testTicket = testTicketDAO.getTicket(testVehicleRegNumber);
		testTicket.setPrice(10);
		testTicket.setOutTime(new Date());

		// WHEN
		boolean updateRequest = testTicketDAO.updateTicket(testTicket);

		// THEN
		if (!updateRequest)
			fail("Failed ticket update");

		assertEquals(testTicketDAO.getTicket(testVehicleRegNumber).getPrice(), testTicket.getPrice(),
				"Result: Both Fare Price values matches");
	}

	@DisplayName("Test Ticket WHEN retrieving vehicle reccurence THEN client recurrence visit confirmed")
	@Test
	public void testGetVehicleOccurence() throws ClassNotFoundException, SQLException {
		// GIVEN
		final String vehicleRegNumber = "ZYXWVU";
		int ticketCount = 0, targetCount = 3;

		while (ticketCount < targetCount) {
			ticketCount++;

			Ticket testDataSet = new Ticket();
			testDataSet.setVehicleRegNumber(vehicleRegNumber);
			testDataSet.setInTime(new Date(System.currentTimeMillis() - (5 * 60 * 1000) * (ticketCount + 1)));
			testDataSet.setOutTime(new Date());
			testDataSet.setParkingSpot(new ParkingSpot(ticketCount + 1, ParkingType.CAR, false));

			
			Connection connection = null;
			
			DataBaseTestConfig dataBaseTestConfig = new DataBaseTestConfig();
			Ticket ticket = testDataSet;
					try {
						connection = dataBaseTestConfig.getConnection();
			
						PreparedStatement ps = connection.prepareStatement(DBConstants.SAVE_TICKET);
						ps.setInt(1, ticket.getParkingSpot().getId());
						ps.setString(2, ticket.getVehicleRegNumber());
						ps.setDouble(3, ticket.getPrice());
						ps.setTimestamp(4, new Timestamp(ticket.getInTime().getTime()));
						ps.setTimestamp(5,
								(ticket.getOutTime() == null) ? null : (new Timestamp(ticket.getOutTime().getTime())));
			
						ps.execute();
					} catch (Exception exception) {
						exception.printStackTrace();
					}
				

//			prepareDBService.insertTestTicket(testDataSet);
		}

		// WHEN
		boolean occurences = testTicketDAO.getVehicleOccurence(vehicleRegNumber);

		// THEN
		assertEquals(true, occurences, "Result: Both client reccurence status matches");
	}

	/**
	 *Test ticket is created as template to use in test cases
	 */
	Ticket createTestingTicket(String vehicleRegNumber) {
		Ticket testingTicket = new Ticket();
		testingTicket.setParkingSpot(new ParkingSpot(1, ParkingType.CAR, false));
		testingTicket.setVehicleRegNumber(vehicleRegNumber);
		testingTicket.setPrice(0);
		testingTicket.setInTime(new Date(new Date().getTime() - 6 * 1000 * 60));
		testingTicket.setOutTime(null);

		return testingTicket;
	}
}
