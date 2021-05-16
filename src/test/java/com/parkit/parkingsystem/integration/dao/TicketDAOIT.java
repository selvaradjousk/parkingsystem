package com.parkit.parkingsystem.integration.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import com.parkit.parkingsystem.ParkingServiceTest;
import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.dao.TicketDAO;
import com.parkit.parkingsystem.integration.config.DataBaseTestConfig;
import com.parkit.parkingsystem.integration.service.DataBasePrepareService;
import com.parkit.parkingsystem.model.ParkingSpot;
import com.parkit.parkingsystem.model.Ticket;
import com.parkit.parkingsystem.service.ParkingService;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * Class {@link TicketDAOIT} - Functions and Methods for dealing with Ticket
 * management for customers parking {@link TicketDAO}
 * 
 * @package - com.parkit.parkingsystem.integration.dao
 * @project - P4 - parking system - ParkIt
 * @see Tests: {@link #testSaveTicket()}, {@link #testGetTicket()},
 *      {@link #testUpdateTicket()},
 *      {@link #createTestingTicket(String vehicleRegNumber)},
 *      {@link #testGetVehicleOccurence()}
 * 
 * @author Senthil
 */
public class TicketDAOIT {
	static TicketDAO testTicketDAO = new TicketDAO();
	static DataBaseTestConfig testDB = new DataBaseTestConfig();
	static DataBasePrepareService prepareDBService = new DataBasePrepareService();

	@BeforeAll
	public static void setupTests() {
		testTicketDAO.dataBaseConfig = testDB;
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
	 */
	@Test
	public void testSaveTicket() throws ClassNotFoundException, IllegalAccessException, InstantiationException {
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
			assertEquals(expectedStatus, testRs.next());
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
	 */
	@Test
	public void testGetTicket() {
		// GIVEN
		String testVehicleRegNumber = "MLKJHG";
		Ticket testTicket = createTestingTicket(testVehicleRegNumber);
		testTicketDAO.saveTicket(testTicket);

		// WHEN
		Ticket gettingTicket = testTicketDAO.getTicket(testVehicleRegNumber);

		// THEN
		assertNotEquals(null, gettingTicket);
		assertEquals(testVehicleRegNumber, gettingTicket.getVehicleRegNumber());
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
	 */
	@Test
	public void testUpdateTicket() throws IllegalAccessException, InstantiationException {
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

		assertEquals(testTicketDAO.getTicket(testVehicleRegNumber).getPrice(), testTicket.getPrice());
	}

	/**
	 * {@link #testGetVehicleOccurence()} Integration Test on
	 * {@link TicketDAO#getVehicleOccurence}<br>
	 * GIVEN: set count value for vehicle registration number<br>
	 * WHEN: inserting test tickets into DB update<br>
	 * THEN:test<b>getting vehicle occurence value</b><br>
	 * <b>Test Condition <i>PASSED</i>: </b>assertEquals expected = occurrences
	 * <code><b>TRUE</b></code> <br>
	 * <b>Test Condition <i>FAILED</i>: </b>assertEquals expected != occurrences
	 * <code><b>FALSE</b></code>
	 */
	@Test
	public void testGetVehicleOccurence() {
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

			prepareDBService.insertTestTicket(testDataSet);
		}

		// WHEN
		int occurences = testTicketDAO.getVehicleOccurence(vehicleRegNumber);

		// THEN
		assertEquals(targetCount, occurences);
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