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

/**
 * <b>Test Class: </b> {@link TicketDAOIT} - Vehicle Ticket Data Access Object -
 * Integration Testing on Functions and Methods for dealing with Ticket
 * management for customers parking <br>
 * <b>Class Tested:</b> {@link TicketDAO}.<br>
 * <b>Project: <b> P3 - parking system - ParkIt<br>
 * 
 * @see <b>Tests:</b><br>
 *      {@link #testSaveTicket()}: Ticket DAO - Integration Testing - Save
 *      Ticket function<br>
 *      {@link #testGetTicket()}: Ticket DAO - Integration Testing - getting
 *      Ticket function<br>
 *      {@link #testUpdateTicket()}: Ticket DAO - Integration Testing - Ticket
 *      update function<br>
 *      {@link #testGetVehicleOccurence()}: Ticket DAO - Integration Testing -
 *      Client recurrence check function<br>
 *      {@link #createTestingTicket(String vehicleRegNumber)}: Support method
 *      for testing methods<br>
 * 
 * @author Senthil
 */
@DisplayName("Vehicle Ticket Data Access Object - Integration Testing ")
public class TicketDAOIT {
	static TicketDAO testTicketDAO = new TicketDAO();
	static DataBaseTestConfig testDB = new DataBaseTestConfig();
	static DataBasePrepareService prepareDBService = new DataBasePrepareService();

	@BeforeAll
	public static void setupTests() {
		testTicketDAO.setDataBaseConfig(testDB);
	}

	@BeforeEach
	public void setupPerTest() {
		prepareDBService.clearDBEntries();
	}

	@AfterAll
	public static void cleanTests() {
		prepareDBService.clearDBEntries();
	}

	/**
	 * {@link #testSaveTicket()} Integration Test on
	 * {@link TicketDAO#saveTicket()}<br>
	 * GIVEN: creating a testing ticket<br>
	 * WHEN: saving ticket <br>
	 * THEN: connection, prepared statement, resultSet <b>resultSet checked</b><br>
	 * <b>Test Condition <i>PASSED</i>: </b>assertEquals expected = result in DB
	 * found<code><b>TRUE</b></code> <br>
	 * <b>Test Condition <i>FAILED</i>: </b>assertEquals expected != result in DB
	 * found<code><b>FALSE</b></code>
	 * @throws SQLException 
	 */
	@Test
	@DisplayName("Ticket DAO - Integration Testing - Save Ticket function")
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

	/**
	 * {@link #testGetTicket()} Integration Test on
	 * {@link TicketDAO#getTicket()}<br>
	 * GIVEN: getting the testing ticket<br>
	 * WHEN: getting ticket set values<br>
	 * THEN:recover the ticket value<b>retrieve ticket info</b><br>
	 * <b>Test Condition <i>PASSED</i>: </b>assertEquals expected =
	 * getVehicleRegNumber() <code><b>TRUE</b></code> <br>
	 * <b>Test Condition <i>FAILED</i>: </b>assertEquals expected !=
	 * getVehicleRegNumber() <code><b>FALSE</b></code>
	 * @throws InstantiationException 
	 * @throws IllegalAccessException 
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	@DisplayName("Ticket DAO - Integration Testing - getting Ticket function")
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

	/**
	 * {@link #testUpdateTicket()} Integration Test on
	 * {@link TicketDAO#updateTicket()}<br>
	 * GIVEN: on create and save ticket<br>
	 * WHEN: update request executed<br>
	 * THEN:test<b>retrieve ticket info</b><br>
	 * <b>Test Condition <i>PASSED</i>: </b>assertEquals expected = getPrice()
	 * <code><b>TRUE</b></code> <br>
	 * <b>Test Condition <i>FAILED</i>: </b>assertEquals expected != getPrice()
	 * <code><b>FALSE</b></code>
	 * 
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	@DisplayName("Ticket DAO - Integration Testing - Ticket update function")
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

	/**
	 * {@link #testGetVehicleOccurence()} Integration Test on
	 * {@link TicketDAO#getVehicleOccurence}<br>
	 * GIVEN: set count value for vehicle registration number<br>
	 * WHEN: inserting test tickets into DB update<br>
	 * THEN:test<b>getting vehicle occurrence frequency value</b><br>
	 * <b>Test Condition <i>PASSED</i>: </b>assertEquals expected = occurrences
	 * frequency <code><b>TRUE</b></code> <br>
	 * <b>Test Condition <i>FAILED</i>: </b>assertEquals expected != occurrences
	 * frequency <code><b>FALSE</b></code>
	 * @throws InstantiationException 
	 * @throws IllegalAccessException 
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	@DisplayName("Ticket DAO - Integration Testing - Client reccurence check function")
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
	 * {@link #createTestingTicket(String vehicleRegNumber)} Test ticket is created
	 * as template to use in test cases
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
