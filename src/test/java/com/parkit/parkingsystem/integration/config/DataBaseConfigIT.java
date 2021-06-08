package com.parkit.parkingsystem.integration.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import com.parkit.parkingsystem.config.DataBaseConfig;
import com.parkit.parkingsystem.constants.DBConstants;

import nl.altindag.log.LogCaptor;


/**
 * <b>Test Class: </b> {@link DataBaseConfigIT} - Performs Integration Test on
 * Database configuration <br>
 * <b>Class Tested:</b> {@link DataBaseConfig}.<br>
 * <b>Project: </b> P3 - parking system - ParkIt<br>
 * 
 * @author Senthil
 */
@DisplayName("IT - Test Parking DB Configuration")
public class DataBaseConfigIT {

	DataBaseConfig dataBaseTestConfig;
	ByteArrayOutputStream byteArrayOutputStream;

	Connection connection;
	PreparedStatement statement;
	ResultSet resultSet;


	// ********************************************************************************************
	//                                GET CONNECTION TESTS	
	// ********************************************************************************************

	@DisplayName("Test Parking DB Configuration - Get Connection")
	@Test
	public void testGetConnectionStatusConnected() // OK DONE
			throws SQLException, ClassNotFoundException, IllegalAccessException, InstantiationException {

		// GIVEN
		boolean expectedConnectionNotConnected = false;
		dataBaseTestConfig = new DataBaseConfig();
		connection = dataBaseTestConfig.getConnection();

		assertNotNull(connection); // Connection is not null
		assertEquals(expectedConnectionNotConnected, connection.isClosed(),
				"Result: Not connected when closed, so there was a connection before");
	}

	// ********************************************************************************************
	@DisplayName("Test Parking DB Configuration - Get Connection") // OK DONE
	@Test
	public void testGetConnectionDatabaseUrl()
			throws SQLException, ClassNotFoundException, IllegalAccessException, InstantiationException {

		// GIVEN
		String expectedDatabaseUrl = "jdbc:mysql://localhost:3306/prod";
		dataBaseTestConfig = new DataBaseConfig();
		connection = dataBaseTestConfig.getConnection();
		DatabaseMetaData metaData = connection.getMetaData();

		// THEN
		assertEquals(expectedDatabaseUrl, metaData.getURL(), "Result: expected and actual Database URLs match");
		connection.close();
	}

	// ********************************************************************************************

	@DisplayName("Test Parking DB Configuration - Get Connection") // OK DONE
	@Test
	public void testGetConnectionReturnConnectionWhenConnected()
			throws SQLException, ClassNotFoundException, IllegalAccessException, InstantiationException {

		// GIVEN
		String expectedConnectionConnected = "com.mysql.cj.jdbc.ConnectionImpl@";
		dataBaseTestConfig = new DataBaseConfig();

		// WHEN
		connection = dataBaseTestConfig.getConnection();

		// THEN
		assertTrue(connection.toString().contains(expectedConnectionConnected));
		assertNotNull(connection);
	}

	// ********************************************************************************************
	@Test
	public void testGetConnectionLoggerMessageCreateDBconnection()
			throws SQLException, ClassNotFoundException, IllegalAccessException, InstantiationException, IOException {
		String outputScreen = null;
		// GIVEN
		String expectedInfoMessage = "Create DB connection";
		LogCaptor logCaptor = LogCaptor.forClass(DataBaseConfig.class);

		dataBaseTestConfig = new DataBaseConfig();
		assertFalse(logCaptor.getErrorLogs().contains(expectedInfoMessage));

		byteArrayOutputStream = new ByteArrayOutputStream();
		System.setOut(new PrintStream(byteArrayOutputStream));

		// WHEN
		connection = dataBaseTestConfig.getConnection();
		outputScreen = byteArrayOutputStream.toString("UTF-8");

		// THEN
		assertTrue(outputScreen.toString().trim().contains(expectedInfoMessage));

		connection.close();
	}

	// ********************************************************************************************
	//                                CLOSING CONNECTION TESTS	
	// ********************************************************************************************
	@DisplayName("Test Parking DB Configuration - Closing Connection")
	@Test
	public void testCloseConnection()
			throws SQLException, ClassNotFoundException, IllegalAccessException, InstantiationException, IOException {

		// GIVEN
		String expectedInfoMessage = "Closing DB connection";
		LogCaptor logCaptor = LogCaptor.forClass(DataBaseConfig.class);
		String expectedConnectionConnected = "com.mysql.cj.jdbc.ConnectionImpl@";
		dataBaseTestConfig = new DataBaseConfig();
		boolean expectedConnectionClosed = true;

		connection = dataBaseTestConfig.getConnection();
		assertTrue(connection.toString().contains("com.mysql.cj.jdbc.ConnectionImpl@"));
//		if (connection == null)
//			fail("Couldn't retrieve connection to start test");

		assertFalse(logCaptor.getErrorLogs().contains(expectedInfoMessage));
		// WHEN
		String outputScreen = null;
		byteArrayOutputStream = new ByteArrayOutputStream();
		System.setOut(new PrintStream(byteArrayOutputStream));
		dataBaseTestConfig.closeConnection(connection);

		outputScreen = byteArrayOutputStream.toString("UTF-8");
		// THEN

//		assertEquals(outputScreen, "sdfs");
		assertEquals(expectedConnectionClosed, connection.isClosed(),
				"Result: expected and actual Connections status match");
		assertFalse(outputScreen.toString().trim().contains("PLEASE" + " PARK your VEHICLE in spot number:"));
		assertTrue(outputScreen.toString().trim().contains(expectedInfoMessage));
		assertNotNull(connection);
	}

	// ********************************************************************************************
	@Test
	@DisplayName("Test Parking DB Configuration- Null Connection")
	public void testGetNullConnection() throws ClassNotFoundException, SQLException, Exception, InstantiationException {

		// GIVEN
		Connection connection = null;
		// THEN
		assertThrows(NullPointerException.class, () -> dataBaseTestConfig.getConnection()); // WHEN
	}


	// ********************************************************************************************
	//                                PREPARED STATEMENT TESTS	
	// ********************************************************************************************

	@DisplayName("Test Parking DB Configuration - Closing Prepared Statement")
	@Test
	public void testClosePreparedStatement()
			throws IllegalAccessException, InstantiationException, ClassNotFoundException, SQLException, IOException {
		String outputScreen = null;
		// GIVEN
		String expectedInfoMessage = "Closing Prepared Statement";
		LogCaptor logCaptor = LogCaptor.forClass(DataBaseConfig.class);
		dataBaseTestConfig = new DataBaseConfig();
		Connection testConnection = null;
		PreparedStatement testStatement = null;
		boolean expectedStatementClosed = true;
		dataBaseTestConfig = new DataBaseConfig();
		testConnection = dataBaseTestConfig.getConnection();

		assertTrue(testConnection.toString().contains("com.mysql.cj.jdbc.ConnectionImpl@"));

		assertFalse(logCaptor.getErrorLogs().contains(expectedInfoMessage));
		// WHEN

		byteArrayOutputStream = new ByteArrayOutputStream();
		System.setOut(new PrintStream(byteArrayOutputStream));

		testStatement = testConnection.prepareStatement(DBConstants.GET_TICKET);
		dataBaseTestConfig.closePreparedStatement(testStatement);

		outputScreen = byteArrayOutputStream.toString("UTF-8");
		// THEN

		assertEquals(expectedStatementClosed, testStatement.isClosed(),
				"Result: expected and actual Prepared Statements status match");
		assertFalse(outputScreen.toString().trim().contains("PLEASE" + " PARK your VEHICLE in spot number:"));
		assertTrue(outputScreen.toString().trim().contains(expectedInfoMessage));

		testConnection.close();
	}

	// ********************************************************************************************
	@Test
	@DisplayName("Test Parking DB Configuration - SQL Exception preparedStatement")
	public void testSQLExceptionPreparedStatement()
			throws NullPointerException, ClassNotFoundException, SQLException, Exception, InstantiationException {

		// GIVEN
		dataBaseTestConfig = new DataBaseConfig();

		final Connection testConnection = dataBaseTestConfig.getConnection();

		// THEN
		assertThrows(SQLException.class,
				() -> dataBaseTestConfig.closePreparedStatement(testConnection.prepareStatement(null))); // WHEN
	}
	// ********************************************************************************************

	@Test
	@DisplayName("Test Parking DB Configuration - SQL Exception preparedStatement")
	public void testSQLExceptionMessagePreparedStatement()
			throws NullPointerException, ClassNotFoundException, SQLException, Exception, InstantiationException {
		String outputScreen = null;
		// GIVEN
		String expectedInfoMessage = "Error while closing prepared statement";
		LogCaptor logCaptor = LogCaptor.forClass(DataBaseConfig.class);
		dataBaseTestConfig = new DataBaseConfig();
		assertFalse(logCaptor.getErrorLogs().contains(expectedInfoMessage));

		// WHEN
		final Connection testConnection = dataBaseTestConfig.getConnection();

		// THEN
		try {
			dataBaseTestConfig.closePreparedStatement(testConnection.prepareStatement(null));

			byteArrayOutputStream = new ByteArrayOutputStream();
			System.setOut(new PrintStream(byteArrayOutputStream));
			outputScreen = byteArrayOutputStream.toString("UTF-8");
			assertTrue(outputScreen.toString().trim().contains(expectedInfoMessage));
		} catch (SQLException e) {
			SQLException errorStatement = e;
			byteArrayOutputStream = new ByteArrayOutputStream();
			System.setOut(new PrintStream(byteArrayOutputStream));
			outputScreen = byteArrayOutputStream.toString("UTF-8");
			errorStatement = e;
			assertEquals(errorStatement.toString(), ("java.sql.SQLException: SQL String cannot be NULL"));
			assertFalse(outputScreen.toString().trim().contains(expectedInfoMessage));
		}
	}
	
	
	// ********************************************************************************************
	//                                RESULTSET TESTS	
	// ********************************************************************************************

	@DisplayName("Test Parking DB Configuration - Closing ResultSet")
	@Test
	public void testCloseResultSet()
			throws IllegalAccessException, InstantiationException, ClassNotFoundException, SQLException, IOException {

		// GIVEN
		dataBaseTestConfig = new DataBaseConfig();
		Connection testConnection = null;
		PreparedStatement testStatement = null;
		ResultSet testResults = null;
		boolean expectedResultSetClosed = true;

		testConnection = dataBaseTestConfig.getConnection();
		testStatement = testConnection.prepareStatement(DBConstants.GET_TICKET);
		testStatement.setString(1, "CAR");
		testResults = testStatement.executeQuery();

		// WHEN
		dataBaseTestConfig.closeResultSet(testResults);

		// THEN
		assertEquals(expectedResultSetClosed, testResults.isClosed(),
				"Result: expected and actual ResultSets status match");
		assertTrue(testResults.isClosed());

		testConnection.close();
		testStatement.close();
		testResults.close();
	}
	// ********************************************************************************************

	@DisplayName("Test Parking DB Configuration - Closing ResultSet")
	@Test
	public void testCloseResultSetLoggerMessage()
			throws IllegalAccessException, InstantiationException, ClassNotFoundException, SQLException, IOException {
		String outputScreen = null;
		String expectedInfoMessage = "Closing Result Set";
		LogCaptor logCaptor = LogCaptor.forClass(DataBaseConfig.class);

		// GIVEN
		dataBaseTestConfig = new DataBaseConfig();
		Connection testConnection = null;
		PreparedStatement testStatement = null;
		ResultSet testResults = null;
		assertFalse(logCaptor.getErrorLogs().contains(expectedInfoMessage));

		testConnection = dataBaseTestConfig.getConnection();
		testStatement = testConnection.prepareStatement(DBConstants.GET_TICKET);
		testStatement.setString(1, "CAR");
		testResults = testStatement.executeQuery();
		byteArrayOutputStream = new ByteArrayOutputStream();

		byteArrayOutputStream = new ByteArrayOutputStream();
		System.setOut(new PrintStream(byteArrayOutputStream));
		// WHEN
		dataBaseTestConfig.closeResultSet(testResults);
		System.setOut(new PrintStream(byteArrayOutputStream));

		outputScreen = byteArrayOutputStream.toString("UTF-8");

		// THEN
		assertTrue(outputScreen.toString().trim().contains(expectedInfoMessage));

		testConnection.close();
		testStatement.close();
		testResults.close();
	}
	// ********************************************************************************************

	@Test
	@DisplayName("Test DB Configuration - SQL Exception ResultSet")
	public void testSQLExceptionResultSet()
			throws NullPointerException, ClassNotFoundException, SQLException, Exception, InstantiationException {

		// GIVEN
		dataBaseTestConfig = new DataBaseConfig();

		final Connection testConnection = dataBaseTestConfig.getConnection();

		// THEN
		assertThrows(SQLException.class,
				() -> dataBaseTestConfig.closeResultSet(((testConnection.prepareStatement(null))).executeQuery())); // WHEN
	}
	// ********************************************************************************************

	@Test
	@DisplayName("Test DB Configuration - SQL Exception close ResultSet Logger Message")
	public void testSQLExceptionResultSetLoggerMessage()
			throws NullPointerException, ClassNotFoundException, SQLException, Exception, InstantiationException {

		String outputScreen = null;
		// GIVEN
		String expectedInfoMessage = "Error while closing result set";
		LogCaptor logCaptor = LogCaptor.forClass(DataBaseConfig.class);
		dataBaseTestConfig = new DataBaseConfig();

		assertFalse(logCaptor.getErrorLogs().contains(expectedInfoMessage));

//		final Connection testConnection = dataBaseTestConfig.getConnection();

		// THEN
		try {
			byteArrayOutputStream = new ByteArrayOutputStream();
			System.setOut(new PrintStream(byteArrayOutputStream));

			final Connection testConnection = dataBaseTestConfig.getConnection();
			dataBaseTestConfig.closeResultSet(((testConnection.prepareStatement(null))).executeQuery());

			outputScreen = byteArrayOutputStream.toString("UTF-8");
			assertTrue(outputScreen.toString().trim().contains(expectedInfoMessage));
			fail();
		} catch (SQLException e) {
			SQLException errorStatement = e;
			byteArrayOutputStream = new ByteArrayOutputStream();
			System.setOut(new PrintStream(byteArrayOutputStream));
			outputScreen = byteArrayOutputStream.toString("UTF-8");
			errorStatement = e;
			assertEquals((errorStatement.toString()), ("java.sql.SQLException: SQL String cannot be NULL"));
			assertFalse(outputScreen.toString().trim().contains(expectedInfoMessage));
		} 
	}
	
	
	
	// ********************************************************************************************
}
