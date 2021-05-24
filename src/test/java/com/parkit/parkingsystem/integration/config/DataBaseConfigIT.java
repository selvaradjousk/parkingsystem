package com.parkit.parkingsystem.integration.config;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.parkit.parkingsystem.config.DataBaseConfig;
import com.parkit.parkingsystem.constants.DBConstants;
import com.sun.tools.jconsole.JConsoleContext.ConnectionState;

import nl.altindag.log.LogCaptor;

/**
 * <b>Test Class: </b> {@link DataBaseConfigIT} - Integration Testing For
 * database connection configuration setting <br>
 * <b>Class Tested:</b>{@link DataBaseConfig}.<br>
 * <b>Project: <b> P3 - parking system - ParkIt<br>
 * 
 * @see <b>Tests:</b><br>
 *      {@link #testGetConnection()}}: Parking DB Configuration - Integration
 *      Testing - Get Connection<br>
 *      {@link #testCloseConnection()}: Parking DB Configuration - Integration
 *      Testing - Closing Connection<br>
 *      {@link #testClosePreparedStatement()}: Parking DB Configuration -
 *      Integration Testing - Closing Prepared Statement<br>
 *      {@link #testCloseResultSet()}: Parking DB Configuration - Integration
 *      Testing - Closing ResultSet<br>
 * 
 * @author Senthil
 */
@DisplayName("Test Parking DB Configuration - Integration Testing")
@ExtendWith(MockitoExtension.class)
public class DataBaseConfigIT {

	DataBaseTestConfig dataBaseTestConfig;
	ByteArrayOutputStream byteArrayOutputStream;
	private static Logger logger = LogManager.getLogger(DataBaseTestConfig.class);
	@Mock
	Connection connectionMock;

	@Mock
	PreparedStatement preparedStatementMock;

	@Mock
	ResultSet resultSetMock;

//	@DisplayName("Test Parking DB Configuration - Get Connection")
//	@Test
//	public void testGetConnection()
//			throws SQLException, ClassNotFoundException, IllegalAccessException, InstantiationException {
//		// GIVEN
//		String expectedDatabaseUrl = "jdbc:mysql://localhost:3306/prod";
//		boolean expectedConnectionClosed = false;
//
//		// WHEN
//		Connection testConnection = null;
//		testConnection = dataBaseTestConfig.getConnection();
//
//		// THEN
//		if (testConnection == null)
//			fail("Couldn't retrieve connection");
//
//		assertEquals(expectedConnectionClosed, testConnection.isClosed(),
//				"Result: expected and actual Connection status match");
//
//		DatabaseMetaData metaData = testConnection.getMetaData();
//
//		assertEquals(expectedDatabaseUrl, metaData.getURL(), "Result: expected and actual Database URLs match");
//		testConnection.close();
//	}

	// ********************************************************************************************
	/**
	 * {@link #testGetConnection()} Integration Test on
	 * {@link DataBaseConfig#getConnection()()}<br>
	 * GIVEN: connection values set<br>
	 * WHEN: executing get Connection <br>
	 * THEN: expected Connection status <b>checked</b><br>
	 * <b>Test Condition <i>PASSED</i>: </b>assertEquals expected =
	 * testConnection.isClosed() <code><b>TRUE</b></code> <br>
	 * <b>Test Condition <i>FAILED</i>: </b>assertEquals expected !=
	 * testConnection.isClosed() <code><b>FALSE</b></code>
	 * 
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	@DisplayName("Test Parking DB Configuration - Get Connection")
	@Test
	public void testGetConnectionStatusConnected() // OK DONE
			throws SQLException, ClassNotFoundException, IllegalAccessException, InstantiationException {

		// GIVEN
		boolean expectedConnectionNotConnected = false;
		dataBaseTestConfig = new DataBaseTestConfig();
		Connection connection;

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
		dataBaseTestConfig = new DataBaseTestConfig();
		Connection connection;

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
		dataBaseTestConfig = new DataBaseTestConfig();
		// GIVEN
		String expectedConnectionConnected = "com.mysql.cj.jdbc.ConnectionImpl@";
		dataBaseTestConfig = new DataBaseTestConfig();
		Connection connection;

		// WHEN
		connection = dataBaseTestConfig.getConnection();

		// THEN
		assertTrue(connection.toString().contains(expectedConnectionConnected));
	}
	// ********************************************************************************************
//@Test
//	public void testGetConnectionSqlException()
//			throws SQLException, ClassNotFoundException, IllegalAccessException, InstantiationException, IOException {
//		// GIVEN
//
//		// GIVEN
//		String expectedInfoMessage = "Exception occured : SQLException : ";
//		LogCaptor logCaptor = LogCaptor.forClass(DataBaseConfig.class);
//		dataBaseTestConfig = new DataBaseTestConfig();
//		Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/prod", "root", "rootroot");
//
//		assertFalse(logCaptor.getErrorLogs().contains(expectedInfoMessage));
//		
//		when(dataBaseTestConfig.getConnection()).thenThrow(SQLException.class);
//
//		byteArrayOutputStream = new ByteArrayOutputStream();
//		System.setOut(new PrintStream(byteArrayOutputStream));
//		connection = dataBaseTestConfig.getConnection();
//		assertThrows(SQLException.class, () -> dataBaseTestConfig.getConnection());
//		String outputScreen = null;
//		outputScreen = byteArrayOutputStream.toString("UTF-8");
//		assertTrue(outputScreen.toString().trim().contains(expectedInfoMessage));
//
//	}
	// ********************************************************************************************
	@Test
	public void testGetConnectionLoggerMessageCreateDBconnection()
			throws SQLException, ClassNotFoundException, IllegalAccessException, InstantiationException, IOException {
		String outputScreen = null;
		// GIVEN
		String expectedInfoMessage = "Create DB connection";
		LogCaptor logCaptor = LogCaptor.forClass(DataBaseConfig.class);

		dataBaseTestConfig = new DataBaseTestConfig();
		Connection connection;
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

	/**
	 * {@link #testCloseConnection()} Integration Test on
	 * {@link DataBaseConfig#closeConnection()()}<br>
	 * GIVEN: connection values set<br>
	 * WHEN: executing close Connection <br>
	 * THEN: expected Connection status <b>checked</b><br>
	 * <b>Test Condition <i>PASSED</i>: </b>assertEquals expected = Connection
	 * closed<code><b>TRUE</b></code> <br>
	 * <b>Test Condition <i>FAILED</i>: </b>assertEquals expected != Connection
	 * closed<code><b>FALSE</b></code>
	 * 
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws IOException
	 */
	// ********************************************************************************************
	@DisplayName("Test Parking DB Configuration - Closing Connection")
	@Test
	public void testCloseConnection()
			throws SQLException, ClassNotFoundException, IllegalAccessException, InstantiationException, IOException {

		// GIVEN
		String expectedInfoMessage = "Closing DB connection";
		LogCaptor logCaptor = LogCaptor.forClass(DataBaseConfig.class);
		String expectedConnectionConnected = "com.mysql.cj.jdbc.ConnectionImpl@";
		dataBaseTestConfig = new DataBaseTestConfig();
		Connection connection;
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

		assertEquals(expectedConnectionClosed, connection.isClosed(),
				"Result: expected and actual Connections status match");
//		assertNull(connection);
		assertFalse(outputScreen.toString().trim().contains("PLEASE" + " PARK your VEHICLE in spot number:"));
		assertTrue(outputScreen.toString().trim().contains(expectedInfoMessage));
//		System.out.println(outputScreen);
//		byteArrayOutputStream.close();
	}

	// ********************************************************************************************
	@Test
	@DisplayName("Test Parking DB Configuration- Null Connection")
	public void testGetNullConnection() throws ClassNotFoundException, SQLException, Exception, InstantiationException {

		// GIVEN
		Connection connection = null;

		// WHEN
		assertThrows(NullPointerException.class, () -> dataBaseTestConfig.getConnection());

//		// THEN
//		assertNull(connection);
	}


	/**
	 * {@link #testClosePreparedStatement()} Integration Test on
	 * {@link DataBaseConfig#closePreparedStatement()()}<br>
	 * GIVEN: connection values set<br>
	 * WHEN: executing close PreparedStatement <br>
	 * THEN: expected PreparedStatement status <b>checked</b><br>
	 * <b>Test Condition <i>PASSED</i>: </b>assertEquals expected =
	 * PreparedStatement closed<code><b>TRUE</b></code> <br>
	 * <b>Test Condition <i>FAILED</i>: </b>assertEquals expected !=
	 * PreparedStatement closed<code><b>FALSE</b></code>
	 * 
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws IOException
	 */
	@DisplayName("Test Parking DB Configuration - Closing Prepared Statement")
	@Test
	public void testClosePreparedStatement()
			throws IllegalAccessException, InstantiationException, ClassNotFoundException, SQLException, IOException {
		String outputScreen = null;
		// GIVEN
		String expectedInfoMessage = "Closing Prepared Statement";
		LogCaptor logCaptor = LogCaptor.forClass(DataBaseConfig.class);
		dataBaseTestConfig = new DataBaseTestConfig();
		Connection testConnection = null;
		PreparedStatement testStatement = null;
		boolean expectedStatementClosed = true;
		dataBaseTestConfig = new DataBaseTestConfig();
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
//			testStatement.close();

	}

	@Test
	@DisplayName("Test Parking DB Configuration - SQL Exception preparedStatement")
	public void testSQLExceptionPreparedStatement()
			throws NullPointerException, ClassNotFoundException, SQLException, Exception, InstantiationException {

		// GIVEN
		dataBaseTestConfig = new DataBaseTestConfig();

		final Connection testConnection = dataBaseTestConfig.getConnection();

		// THEN
		assertThrows(SQLException.class, () -> dataBaseTestConfig.closePreparedStatement(testConnection.prepareStatement(null)));		// WHEN
		}
	
	@Test
	@DisplayName("Test Parking DB Configuration - SQL Exception preparedStatement")
	public void testSQLExceptionMessagePreparedStatement()
			throws NullPointerException, ClassNotFoundException, SQLException, Exception, InstantiationException {
		String outputScreen = null;
		// GIVEN
		String expectedInfoMessage = "Error while closing prepared statement";
		LogCaptor logCaptor = LogCaptor.forClass(DataBaseConfig.class);
		dataBaseTestConfig = new DataBaseTestConfig();
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



	/**
	 * {@link #testCloseResultSet()} Integration Test on
	 * {@link DataBaseConfig#closeResultSet()()}<br>
	 * GIVEN: connection values set<br>
	 * WHEN: executing close ResultSet <br>
	 * THEN: expected ResulSet status <b>checked</b><br>
	 * <b>Test Condition <i>PASSED</i>: </b>assertEquals expected = resultset
	 * closed<code><b>TRUE</b></code> <br>
	 * <b>Test Condition <i>FAILED</i>: </b>assertEquals expected != resultset
	 * closed<code><b>FALSE</b></code>
	 * 
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws IOException 
	 */
	@DisplayName("Test Parking DB Configuration - Closing ResultSet")
	@Test
	public void testCloseResultSet()
			throws IllegalAccessException, InstantiationException, ClassNotFoundException, SQLException, IOException {

		// GIVEN
		dataBaseTestConfig = new DataBaseTestConfig();
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
	
	@DisplayName("Test Parking DB Configuration - Closing ResultSet")
	@Test
	public void testCloseResultSetLoggerMessage()
			throws IllegalAccessException, InstantiationException, ClassNotFoundException, SQLException, IOException {
		String outputScreen = null;
		String expectedInfoMessage = "Closing Result Set";
		LogCaptor logCaptor = LogCaptor.forClass(DataBaseConfig.class);

		// GIVEN
		dataBaseTestConfig = new DataBaseTestConfig();
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

	@Test
	@DisplayName("Test DB Configuration - SQL Exception ResultSet")
	public void testSQLExceptionResultSet()
			throws NullPointerException, ClassNotFoundException, SQLException, Exception, InstantiationException {

		// GIVEN
		dataBaseTestConfig = new DataBaseTestConfig();

		final Connection testConnection = dataBaseTestConfig.getConnection();

		// THEN
		assertThrows(SQLException.class, () -> dataBaseTestConfig.closeResultSet(((testConnection.prepareStatement(null))).executeQuery()));		// WHEN
		}
	
	@Test
	@DisplayName("Test DB Configuration - SQL Exception close ResultSet Logger Message")
	public void testSQLExceptionResultSetLoggerMessage()
			throws NullPointerException, ClassNotFoundException, SQLException, Exception, InstantiationException {

		String outputScreen = null;
		// GIVEN
		String expectedInfoMessage = "Error while closing result set";
		LogCaptor logCaptor = LogCaptor.forClass(DataBaseConfig.class);
		dataBaseTestConfig = new DataBaseTestConfig();

		assertFalse(logCaptor.getErrorLogs().contains(expectedInfoMessage));
		
		final Connection testConnection = dataBaseTestConfig.getConnection();

		byteArrayOutputStream = new ByteArrayOutputStream();
		System.setOut(new PrintStream(byteArrayOutputStream));
		
		// THEN
		try {
			byteArrayOutputStream = new ByteArrayOutputStream();
			System.setOut(new PrintStream(byteArrayOutputStream));
			dataBaseTestConfig.closeResultSet(((testConnection.prepareStatement(null))).executeQuery());	
			outputScreen = byteArrayOutputStream.toString("UTF-8");
			assertTrue(outputScreen.toString().trim().contains(expectedInfoMessage));

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

	
//	@DisplayName("Test DataBaseTestConfig - Get Connection")
//	@Test
//	public void testGetConnectionStatusConnectedDataBaseTestConfig() // OK DONE
//			throws SQLException, ClassNotFoundException, IllegalAccessException, InstantiationException {
//
//		// GIVEN
//		boolean expectedConnectionNotConnected = false;
//		dataBaseTestConfig = new DataBaseTestConfig();
//		Connection connection;
//
//		connection = dataBaseTestConfig.getConnection();
//
//		assertNotNull(connection); // Connection is not null
//		assertEquals(expectedConnectionNotConnected, connection.isClosed(),
//				"Result: Not connected when closed, so there was a connection before");
//	}
//
//	// ********************************************************************************************
//	@DisplayName("Test v - Get Connection") // OK DONE
//	@Test
//	public void testGetConnectionDatabaseUrlDataBaseTestConfig()
//			throws SQLException, ClassNotFoundException, IllegalAccessException, InstantiationException {
//
//		// GIVEN
//		String expectedDatabaseUrl = "jdbc:mysql://localhost:3306/prod";
//		dataBaseTestConfig = new DataBaseTestConfig();
//		Connection connection;
//
//		connection = dataBaseTestConfig.getConnection();
//		DatabaseMetaData metaData = connection.getMetaData();
//
//		// THEN
//		assertEquals(expectedDatabaseUrl, metaData.getURL(), "Result: expected and actual Database URLs match");
//		connection.close();
//	}
//	// ********************************************************************************************
//
//	@DisplayName("Test DataBaseTestConfig - Get Connection") // OK DONE
//	@Test
//	public void testGetConnectionReturnConnectionWhenConnectedDataBaseTestConfig()
//			throws SQLException, ClassNotFoundException, IllegalAccessException, InstantiationException {
//		dataBaseTestConfig = new DataBaseTestConfig();
//		// GIVEN
//		String expectedConnectionConnected = "com.mysql.cj.jdbc.ConnectionImpl@";
//		dataBaseTestConfig = new DataBaseTestConfig();
//		Connection connection;
//
//		// WHEN
//		connection = dataBaseTestConfig.getConnection();
//
//		// THEN
//		assertTrue(connection.toString().contains(expectedConnectionConnected));
//	}
//	// ********************************************************************************************
////@Test
////	public void testGetConnectionSqlExceptionDataBaseTestConfig()
////			throws SQLException, ClassNotFoundException, IllegalAccessException, InstantiationException, IOException {
////		// GIVEN
////
////		// GIVEN
////		String expectedInfoMessage = "Exception occured : SQLException : ";
////		LogCaptor logCaptor = LogCaptor.forClass(DataBaseConfig.class);
////		dataBaseTestConfig = new DataBaseTestConfig();
////		Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/prod", "root", "rootroot");
////
////		assertFalse(logCaptor.getErrorLogs().contains(expectedInfoMessage));
////		
////		when(dataBaseTestConfig.getConnection()).thenThrow(SQLException.class);
////
////		byteArrayOutputStream = new ByteArrayOutputStream();
////		System.setOut(new PrintStream(byteArrayOutputStream));
////		connection = dataBaseTestConfig.getConnection();
////		assertThrows(SQLException.class, () -> dataBaseTestConfig.getConnection());
////		String outputScreen = null;
////		outputScreen = byteArrayOutputStream.toString("UTF-8");
////		assertTrue(outputScreen.toString().trim().contains(expectedInfoMessage));
////
////	}
//	// ********************************************************************************************
//	@Test
//	public void testGetConnectionLoggerMessageCreateDBconnectionDataBaseTestConfig()
//			throws SQLException, ClassNotFoundException, IllegalAccessException, InstantiationException, IOException {
//		String outputScreen = null;
//		// GIVEN
//		String expectedInfoMessage = "Create DB connection";
//		LogCaptor logCaptor = LogCaptor.forClass(DataBaseConfig.class);
//
//		dataBaseTestConfig = new DataBaseTestConfig();
//		Connection connection;
//		assertFalse(logCaptor.getErrorLogs().contains(expectedInfoMessage));
//
//		byteArrayOutputStream = new ByteArrayOutputStream();
//		System.setOut(new PrintStream(byteArrayOutputStream));
//
//		// WHEN
//		connection = dataBaseTestConfig.getConnection();
//		outputScreen = byteArrayOutputStream.toString("UTF-8");
//
//		// THEN
//		assertTrue(outputScreen.toString().trim().contains(expectedInfoMessage));
//
//		connection.close();
//	}
//
//	/**
//	 * {@link #testCloseConnection()} Integration Test on
//	 * {@link DataBaseConfig#closeConnection()()}<br>
//	 * GIVEN: connection values set<br>
//	 * WHEN: executing close Connection <br>
//	 * THEN: expected Connection status <b>checked</b><br>
//	 * <b>Test Condition <i>PASSED</i>: </b>assertEquals expected = Connection
//	 * closed<code><b>TRUE</b></code> <br>
//	 * <b>Test Condition <i>FAILED</i>: </b>assertEquals expected != Connection
//	 * closed<code><b>FALSE</b></code>
//	 * 
//	 * @throws InstantiationException
//	 * @throws IllegalAccessException
//	 * @throws ClassNotFoundException
//	 * @throws SQLException
//	 * @throws IOException
//	 */
//	// ********************************************************************************************
//	@DisplayName("Test DataBaseTestConfig - Closing Connection")
//	@Test
//	public void testCloseConnectionDataBaseTestConfig()
//			throws SQLException, ClassNotFoundException, IllegalAccessException, InstantiationException, IOException {
//
//		// GIVEN
//		String expectedInfoMessage = "Closing DB connection";
//		LogCaptor logCaptor = LogCaptor.forClass(DataBaseConfig.class);
//		String expectedConnectionConnected = "com.mysql.cj.jdbc.ConnectionImpl@";
//		dataBaseTestConfig = new DataBaseTestConfig();
//		Connection connection;
//		boolean expectedConnectionClosed = true;
//
//		connection = dataBaseTestConfig.getConnection();
//		assertTrue(connection.toString().contains("com.mysql.cj.jdbc.ConnectionImpl@"));
////		if (connection == null)
////			fail("Couldn't retrieve connection to start test");
//
//		assertFalse(logCaptor.getErrorLogs().contains(expectedInfoMessage));
//		// WHEN
//		String outputScreen = null;
//		byteArrayOutputStream = new ByteArrayOutputStream();
//		System.setOut(new PrintStream(byteArrayOutputStream));
//		dataBaseTestConfig.closeConnection(connection);
//
//		outputScreen = byteArrayOutputStream.toString("UTF-8");
//		// THEN
//
//		assertEquals(expectedConnectionClosed, connection.isClosed(),
//				"Result: expected and actual Connections status match");
////		assertNull(connection);
//		assertFalse(outputScreen.toString().trim().contains("PLEASE" + " PARK your VEHICLE in spot number:"));
//		assertTrue(outputScreen.toString().trim().contains(expectedInfoMessage));
////		System.out.println(outputScreen);
////		byteArrayOutputStream.close();
//	}
//
//	// ********************************************************************************************
//	@Test
//	@DisplayName("Test PDataBaseTestConfig- Null Connection")
//	public void testGetNullConnectionDataBaseTestConfig() throws ClassNotFoundException, SQLException, Exception, InstantiationException {
//
//		// GIVEN
//		Connection connection = null;
//
//		// WHEN
//		assertThrows(NullPointerException.class, () -> dataBaseTestConfig.getConnection());
//
////		// THEN
////		assertNull(connection);
//	}
//
//
//	/**
//	 * {@link #testClosePreparedStatement()} Integration Test on
//	 * {@link DataBaseConfig#closePreparedStatement()()}<br>
//	 * GIVEN: connection values set<br>
//	 * WHEN: executing close PreparedStatement <br>
//	 * THEN: expected PreparedStatement status <b>checked</b><br>
//	 * <b>Test Condition <i>PASSED</i>: </b>assertEquals expected =
//	 * PreparedStatement closed<code><b>TRUE</b></code> <br>
//	 * <b>Test Condition <i>FAILED</i>: </b>assertEquals expected !=
//	 * PreparedStatement closed<code><b>FALSE</b></code>
//	 * 
//	 * @throws InstantiationException
//	 * @throws IllegalAccessException
//	 * @throws ClassNotFoundException
//	 * @throws SQLException
//	 * @throws IOException
//	 */
//	@DisplayName("Test DataBaseTestConfig - Closing Prepared Statement")
//	@Test
//	public void testClosePreparedStatementDataBaseTestConfig()
//			throws IllegalAccessException, InstantiationException, ClassNotFoundException, SQLException, IOException {
//		String outputScreen = null;
//		// GIVEN
//		String expectedInfoMessage = "Closing Prepared Statement";
//		LogCaptor logCaptor = LogCaptor.forClass(DataBaseConfig.class);
//		dataBaseTestConfig = new DataBaseTestConfig();
//		Connection testConnection = null;
//		PreparedStatement testStatement = null;
//		boolean expectedStatementClosed = true;
//		dataBaseTestConfig = new DataBaseTestConfig();
//		testConnection = dataBaseTestConfig.getConnection();
//
//		assertTrue(testConnection.toString().contains("com.mysql.cj.jdbc.ConnectionImpl@"));
//
//		assertFalse(logCaptor.getErrorLogs().contains(expectedInfoMessage));
//		// WHEN
//
//		byteArrayOutputStream = new ByteArrayOutputStream();
//		System.setOut(new PrintStream(byteArrayOutputStream));
//
//		testStatement = testConnection.prepareStatement(DBConstants.GET_TICKET);
//		dataBaseTestConfig.closePreparedStatement(testStatement);
//
//		outputScreen = byteArrayOutputStream.toString("UTF-8");
//		// THEN
//
//		assertEquals(expectedStatementClosed, testStatement.isClosed(),
//				"Result: expected and actual Prepared Statements status match");
//		assertFalse(outputScreen.toString().trim().contains("PLEASE" + " PARK your VEHICLE in spot number:"));
//		assertTrue(outputScreen.toString().trim().contains(expectedInfoMessage));
//
//		testConnection.close();
////			testStatement.close();
//
//	}
//
//	@Test
//	@DisplayName("Test DataBaseTestConfig - SQL Exception preparedStatement")
//	public void testSQLExceptionPreparedStatementDataBaseTestConfig()
//			throws NullPointerException, ClassNotFoundException, SQLException, Exception, InstantiationException {
//
//		// GIVEN
//		dataBaseTestConfig = new DataBaseTestConfig();
//
//		final Connection testConnection = dataBaseTestConfig.getConnection();
//
//		// THEN
//		assertThrows(SQLException.class, () -> dataBaseTestConfig.closePreparedStatement(testConnection.prepareStatement(null)));		// WHEN
//		}
//	
//	@Test
//	@DisplayName("Test DataBaseTestConfig - SQL Exception preparedStatement")
//	public void testSQLExceptionMessagePreparedStatementDataBaseTestConfig()
//			throws NullPointerException, ClassNotFoundException, SQLException, Exception, InstantiationException {
//		String outputScreen = null;
//		// GIVEN
//		String expectedInfoMessage = "Error while closing prepared statement";
//		LogCaptor logCaptor = LogCaptor.forClass(DataBaseConfig.class);
//		dataBaseTestConfig = new DataBaseTestConfig();
//		assertFalse(logCaptor.getErrorLogs().contains(expectedInfoMessage));
//
//		// WHEN
//		final Connection testConnection = dataBaseTestConfig.getConnection();
//
//		// THEN
//		try {
//			dataBaseTestConfig.closePreparedStatement(testConnection.prepareStatement(null));
//			
//			byteArrayOutputStream = new ByteArrayOutputStream();
//			System.setOut(new PrintStream(byteArrayOutputStream));
//			outputScreen = byteArrayOutputStream.toString("UTF-8");
//			assertTrue(outputScreen.toString().trim().contains(expectedInfoMessage));
//		} catch (SQLException e) {
//			SQLException errorStatement = e;
//			byteArrayOutputStream = new ByteArrayOutputStream();
//			System.setOut(new PrintStream(byteArrayOutputStream));
//			outputScreen = byteArrayOutputStream.toString("UTF-8");
//			errorStatement = e;
//			assertEquals(errorStatement.toString(), ("java.sql.SQLException: SQL String cannot be NULL"));
//			assertFalse(outputScreen.toString().trim().contains(expectedInfoMessage));
//		}
//	}
//
//
//
//	/**
//	 * {@link #testCloseResultSet()} Integration Test on
//	 * {@link DataBaseConfig#closeResultSet()()}<br>
//	 * GIVEN: connection values set<br>
//	 * WHEN: executing close ResultSet <br>
//	 * THEN: expected ResulSet status <b>checked</b><br>
//	 * <b>Test Condition <i>PASSED</i>: </b>assertEquals expected = resultset
//	 * closed<code><b>TRUE</b></code> <br>
//	 * <b>Test Condition <i>FAILED</i>: </b>assertEquals expected != resultset
//	 * closed<code><b>FALSE</b></code>
//	 * 
//	 * @throws InstantiationException
//	 * @throws IllegalAccessException
//	 * @throws ClassNotFoundException
//	 * @throws SQLException
//	 * @throws IOException 
//	 */
//	@DisplayName("Test DataBaseTestConfig - Closing ResultSet")
//	@Test
//	public void testCloseResultSetDataBaseTestConfig()
//			throws IllegalAccessException, InstantiationException, ClassNotFoundException, SQLException, IOException {
//
//		// GIVEN
//		dataBaseTestConfig = new DataBaseTestConfig();
//		Connection testConnection = null;
//		PreparedStatement testStatement = null;
//		ResultSet testResults = null;
//		boolean expectedResultSetClosed = true;
//		
//		testConnection = dataBaseTestConfig.getConnection();
//		testStatement = testConnection.prepareStatement(DBConstants.GET_TICKET);
//		testStatement.setString(1, "CAR");
//		testResults = testStatement.executeQuery();
//
//		// WHEN
//		dataBaseTestConfig.closeResultSet(testResults);
//
//		// THEN
//		assertEquals(expectedResultSetClosed, testResults.isClosed(),
//				"Result: expected and actual ResultSets status match");
//		assertTrue(testResults.isClosed());
//		
//		testConnection.close();
//		testStatement.close();
//		testResults.close();
//	}
//	
//	@DisplayName("Test DataBaseTestConfig - Closing ResultSet")
//	@Test
//	public void testCloseResultSetLoggerMessageDataBaseTestConfig()
//			throws IllegalAccessException, InstantiationException, ClassNotFoundException, SQLException, IOException {
//		String outputScreen = null;
//		String expectedInfoMessage = "Closing Result Set";
//		LogCaptor logCaptor = LogCaptor.forClass(DataBaseConfig.class);
//
//		// GIVEN
//		dataBaseTestConfig = new DataBaseTestConfig();
//		Connection testConnection = null;
//		PreparedStatement testStatement = null;
//		ResultSet testResults = null;
//		assertFalse(logCaptor.getErrorLogs().contains(expectedInfoMessage));
//		
//		testConnection = dataBaseTestConfig.getConnection();
//		testStatement = testConnection.prepareStatement(DBConstants.GET_TICKET);
//		testStatement.setString(1, "CAR");
//		testResults = testStatement.executeQuery();
//		byteArrayOutputStream = new ByteArrayOutputStream();
//
//
//		byteArrayOutputStream = new ByteArrayOutputStream();
//		System.setOut(new PrintStream(byteArrayOutputStream));
//		// WHEN
//		dataBaseTestConfig.closeResultSet(testResults);
//		System.setOut(new PrintStream(byteArrayOutputStream));
//
//		outputScreen = byteArrayOutputStream.toString("UTF-8");
//		
//		// THEN
//		assertTrue(outputScreen.toString().trim().contains(expectedInfoMessage));
//		
//		testConnection.close();
//		testStatement.close();
//		testResults.close();
//	}
//
//	@Test
//	@DisplayName("Test DataBaseTestConfig - SQL Exception ResultSet")
//	public void testSQLExceptionResultSetDataBaseTestConfig()
//			throws NullPointerException, ClassNotFoundException, SQLException, Exception, InstantiationException {
//
//		// GIVEN
//		dataBaseTestConfig = new DataBaseTestConfig();
//
//		final Connection testConnection = dataBaseTestConfig.getConnection();
//
//		// THEN
//		assertThrows(SQLException.class, () -> dataBaseTestConfig.closeResultSet(((testConnection.prepareStatement(null))).executeQuery()));		// WHEN
//		}
//	
//	@Test
//	@DisplayName("Test DataBaseTestConfig - SQL Exception close ResultSet Logger Message")
//	public void testSQLExceptionResultSetLoggerMessageDataBaseTestConfig()
//			throws NullPointerException, ClassNotFoundException, SQLException, Exception, InstantiationException {
//
//		String outputScreen = null;
//		// GIVEN
//		String expectedInfoMessage = "Error while closing result set";
//		LogCaptor logCaptor = LogCaptor.forClass(DataBaseConfig.class);
//		dataBaseTestConfig = new DataBaseTestConfig();
//
//		assertFalse(logCaptor.getErrorLogs().contains(expectedInfoMessage));
//		
//		final Connection testConnection = dataBaseTestConfig.getConnection();
//
//		byteArrayOutputStream = new ByteArrayOutputStream();
//		System.setOut(new PrintStream(byteArrayOutputStream));
//		
//		// THEN
//		try {
//			byteArrayOutputStream = new ByteArrayOutputStream();
//			System.setOut(new PrintStream(byteArrayOutputStream));
//			dataBaseTestConfig.closeResultSet(((testConnection.prepareStatement(null))).executeQuery());	
//			outputScreen = byteArrayOutputStream.toString("UTF-8");
//			assertTrue(outputScreen.toString().trim().contains(expectedInfoMessage));
//
//	} catch (SQLException e) {
//		SQLException errorStatement = e;
//		byteArrayOutputStream = new ByteArrayOutputStream();
//		System.setOut(new PrintStream(byteArrayOutputStream));
//		outputScreen = byteArrayOutputStream.toString("UTF-8");
//		errorStatement = e;
//		assertEquals((errorStatement.toString()), ("java.sql.SQLException: SQL String cannot be NULL"));
//		assertFalse(outputScreen.toString().trim().contains(expectedInfoMessage));
//	}
//		}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
//
////*********************************************************************************
//	@DisplayName("Test Parking DB Configuration - Get Connection")
//	@Test
//	public void testGetConnectiondataBaseTestConfig()
//			throws SQLException, ClassNotFoundException, IllegalAccessException, InstantiationException {
//		// GIVEN
//		String expectedDatabaseUrl = "jdbc:mysql://localhost:3306/prod";
//		boolean expectedConnectionClosed = false;
//
//		// WHEN
//		Connection testConnection = null;
//		testConnection = dataBaseTestConfig.getConnection();
//
//		// THEN
//		if (testConnection == null)
//			fail("Couldn't retrieve connection");
//
//		assertEquals(expectedConnectionClosed, testConnection.isClosed(),
//				"Result: expected and actual Connection status match");
//
//		DatabaseMetaData metaData = testConnection.getMetaData();
//
//		assertEquals(expectedDatabaseUrl, metaData.getURL(), "Result: expected and actual Database URLs match");
//		testConnection.close();
//	}
//
//	/**
//	 * {@link #testCloseConnection()} Integration Test on
//	 * {@link DataBaseConfig#closeConnection()()}<br>
//	 * GIVEN: connection values set<br>
//	 * WHEN: executing close Connection <br>
//	 * THEN: expected Connection status <b>checked</b><br>
//	 * <b>Test Condition <i>PASSED</i>: </b>assertEquals expected = Connection
//	 * closed<code><b>TRUE</b></code> <br>
//	 * <b>Test Condition <i>FAILED</i>: </b>assertEquals expected != Connection
//	 * closed<code><b>FALSE</b></code>
//	 * 
//	 * @throws InstantiationException
//	 * @throws IllegalAccessException
//	 * @throws ClassNotFoundException
//	 * @throws SQLException
//	 */
//	@DisplayName("Test Parking DB Configuration - Closing Connection")
//	@Test
//	public void testCloseConnectiondataBaseTestConfig()
//			throws SQLException, ClassNotFoundException, IllegalAccessException, InstantiationException {
//		// GIVEN
//		boolean expectedConnectionClosed = true;
//		Connection testConnection = null;
//
//		testConnection = dataBaseTestConfig.getConnection();
//
//		if (testConnection == null)
//			fail("Couldn't retrieve connection to start test");
//
//		// WHEN
//		dataBaseTestConfig.closeConnection(testConnection);
//
//		// THEN
//		assertEquals(expectedConnectionClosed, testConnection.isClosed(),
//				"Result: expected and actual Connections status match");
//		testConnection.close();
//
//	}
//
//	@Test
//	@DisplayName("Test Parking DB Configuration- Null Connection")
//	public void testNullConnectiondataBaseTestConfig()
//			throws ClassNotFoundException, SQLException, Exception, InstantiationException {
//
//		// GIVEN
//		Connection connection = null;
//
//		// WHEN
//		dataBaseTestConfig.getConnection();
//
//		// THEN
//		assertNull(connection);
//	}
//
//	/**
//	 * {@link #testClosePreparedStatement()} Integration Test on
//	 * {@link DataBaseConfig#closePreparedStatement()()}<br>
//	 * GIVEN: connection values set<br>
//	 * WHEN: executing close PreparedStatement <br>
//	 * THEN: expected PreparedStatement status <b>checked</b><br>
//	 * <b>Test Condition <i>PASSED</i>: </b>assertEquals expected =
//	 * PreparedStatement closed<code><b>TRUE</b></code> <br>
//	 * <b>Test Condition <i>FAILED</i>: </b>assertEquals expected !=
//	 * PreparedStatement closed<code><b>FALSE</b></code>
//	 * 
//	 * @throws InstantiationException
//	 * @throws IllegalAccessException
//	 * @throws ClassNotFoundException
//	 * @throws SQLException
//	 */
//	@DisplayName("Test Parking DB Configuration - Closing Prepared Statement")
//	@Test
//	public void testClosePreparedStatementdataBaseTestConfig()
//			throws IllegalAccessException, InstantiationException, ClassNotFoundException, SQLException {
//		// GIVEN
//		Connection testConnection = null;
//		PreparedStatement testStatement = null;
//		boolean expectedStatementClosed = true;
//
//		testConnection = dataBaseTestConfig.getConnection();
//		testStatement = testConnection.prepareStatement(DBConstants.GET_TICKET);
//
//		// WHEN
//		dataBaseTestConfig.closePreparedStatement(testStatement);
//
//		// THEN
//		assertEquals(expectedStatementClosed, testStatement.isClosed(),
//				"Result: expected and actual Prepared Statements status match");
//		testConnection.close();
//		testStatement.close();
//
//	}
//
//	@Test
//	@DisplayName("Test Parking DB Configuration - Null preparedStatement")
//	public void testNullPreparedStatementdataBaseTestConfig()
//			throws ClassNotFoundException, SQLException, Exception, InstantiationException {
//
//		// GIVEN
//		PreparedStatement preparedStatement = null;
//
//		// WHEN
//		dataBaseTestConfig.getConnection();
//
//		// THEN
//		assertNull(preparedStatement);
//	}
//
//	/**
//	 * {@link #testCloseResultSet()} Integration Test on
//	 * {@link DataBaseConfig#closeResultSet()()}<br>
//	 * GIVEN: connection values set<br>
//	 * WHEN: executing close ResultSet <br>
//	 * THEN: expected ResulSet status <b>checked</b><br>
//	 * <b>Test Condition <i>PASSED</i>: </b>assertEquals expected = resultset
//	 * closed<code><b>TRUE</b></code> <br>
//	 * <b>Test Condition <i>FAILED</i>: </b>assertEquals expected != resultset
//	 * closed<code><b>FALSE</b></code>
//	 * 
//	 * @throws InstantiationException
//	 * @throws IllegalAccessException
//	 * @throws ClassNotFoundException
//	 * @throws SQLException
//	 */
//	@DisplayName("Test Parking DB Configuration - Closing ResultSet")
//	@Test
//	public void testCloseResultSetdataBaseTestConfig()
//			throws IllegalAccessException, InstantiationException, ClassNotFoundException, SQLException {
//		// GIVEN
//		Connection testConnection = null;
//		PreparedStatement testStatement = null;
//		ResultSet testResults = null;
//		boolean expectedResultSetClosed = true;
//
//		testConnection = dataBaseTestConfig.getConnection();
//		testStatement = testConnection.prepareStatement(DBConstants.GET_TICKET);
//		testStatement.setString(1, "CAR");
//		testResults = testStatement.executeQuery();
//
//		// WHEN
//		dataBaseTestConfig.closeResultSet(testResults);
//
//		// THEN
//		assertEquals(expectedResultSetClosed, testResults.isClosed(),
//				"Result: expected and actual ResultSets status match");
//		assertTrue(testResults.isClosed());
//		testConnection.close();
//		testStatement.close();
//		testResults.close();
//	}
//
//	@Test
//	@DisplayName("Test Parking DB Configuration - Null ResultSet")
//	public void testNullResultSetdataBaseTestConfig()
//			throws ClassNotFoundException, SQLException, IllegalAccessException, InstantiationException {
//
//		// GIVEN
//		ResultSet resultSet = null;
//
//		// WHEN
//		dataBaseTestConfig.getConnection();
//
//		// THEN
//		assertNull(resultSet);
//	}

}
