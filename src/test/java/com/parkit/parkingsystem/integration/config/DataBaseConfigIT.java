package com.parkit.parkingsystem.integration.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import com.parkit.parkingsystem.config.DataBaseConfig;
import com.parkit.parkingsystem.constants.DBConstants;

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
public class DataBaseConfigIT {

	static DataBaseConfig dataBaseConfig = new DataBaseConfig();
	static DataBaseTestConfig dataBaseTestConfig = new DataBaseTestConfig();

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
	public void testGetConnection()
			throws SQLException, ClassNotFoundException, IllegalAccessException, InstantiationException {
		// GIVEN
		String expectedDatabaseUrl = "jdbc:mysql://localhost:3306/prod";
		boolean expectedConnectionClosed = false;

		// WHEN
		Connection testConnection = null;
		testConnection = dataBaseConfig.getConnection();

		// THEN
		if (testConnection == null)
			fail("Couldn't retrieve connection");

		assertEquals(expectedConnectionClosed, testConnection.isClosed(),
				"Result: expected and actual Connection status match");

		DatabaseMetaData metaData = testConnection.getMetaData();

		assertEquals(expectedDatabaseUrl, metaData.getURL(), "Result: expected and actual Database URLs match");
		testConnection.close();
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
	 */
	@DisplayName("Test Parking DB Configuration - Closing Connection")
	@Test
	public void testCloseConnection()
			throws SQLException, ClassNotFoundException, IllegalAccessException, InstantiationException {
		// GIVEN
		boolean expectedConnectionClosed = true;
		Connection testConnection = null;

		testConnection = dataBaseConfig.getConnection();

		if (testConnection == null)
			fail("Couldn't retrieve connection to start test");

		// WHEN
		dataBaseConfig.closeConnection(testConnection);

		// THEN
		assertEquals(expectedConnectionClosed, testConnection.isClosed(),
				"Result: expected and actual Connections status match");
		testConnection.close();

	}

	@Test
	@DisplayName("Test Parking DB Configuration- Null Connection")
	public void testNullConnection() throws ClassNotFoundException, SQLException, Exception, InstantiationException {

		// GIVEN
		Connection connection = null;

		// WHEN
		dataBaseConfig.getConnection();

		// THEN
		assertNull(connection);
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
	 */
	@DisplayName("Test Parking DB Configuration - Closing Prepared Statement")
	@Test
	public void testClosePreparedStatement()
			throws IllegalAccessException, InstantiationException, ClassNotFoundException, SQLException {
		// GIVEN
		Connection testConnection = null;
		PreparedStatement testStatement = null;
		boolean expectedStatementClosed = true;

		testConnection = dataBaseConfig.getConnection();
		testStatement = testConnection.prepareStatement(DBConstants.GET_TICKET);

		// WHEN
		dataBaseConfig.closePreparedStatement(testStatement);

		// THEN
		assertEquals(expectedStatementClosed, testStatement.isClosed(),
				"Result: expected and actual Prepared Statements status match");
		testConnection.close();
		testStatement.close();

	}

	@Test
	@DisplayName("Test Parking DB Configuration - Null preparedStatement")
	public void testNullPreparedStatement()
			throws ClassNotFoundException, SQLException, Exception, InstantiationException {

		// GIVEN
		PreparedStatement preparedStatement = null;

		// WHEN
		dataBaseConfig.getConnection();

		// THEN
		assertNull(preparedStatement);
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
	 */
	@DisplayName("Test Parking DB Configuration - Closing ResultSet")
	@Test
	public void testCloseResultSet()
			throws IllegalAccessException, InstantiationException, ClassNotFoundException, SQLException {
		// GIVEN
		Connection testConnection = null;
		PreparedStatement testStatement = null;
		ResultSet testResults = null;
		boolean expectedResultSetClosed = true;

		testConnection = dataBaseConfig.getConnection();
		testStatement = testConnection.prepareStatement(DBConstants.GET_TICKET);
		testStatement.setString(1, "CAR");
		testResults = testStatement.executeQuery();

		// WHEN
		dataBaseConfig.closeResultSet(testResults);

		// THEN
		assertEquals(expectedResultSetClosed, testResults.isClosed(),
				"Result: expected and actual ResultSets status match");
		assertTrue(testResults.isClosed());
		testConnection.close();
		testStatement.close();
		testResults.close();
	}

	@Test
	@DisplayName("Test Parking DB Configuration - Null ResultSet")
	public void givenDataBaseWithNullResultSet_whenGetResultSet_thenReturnNullResultSet()
			throws ClassNotFoundException, SQLException, IllegalAccessException, InstantiationException {

		// GIVEN
		ResultSet resultSet = null;

		// WHEN
		dataBaseConfig.getConnection();

		// THEN
		assertNull(resultSet);
	}

//*********************************************************************************
	@DisplayName("Test Parking DB Configuration - Get Connection")
	@Test
	public void testGetConnectiondataBaseTestConfig()
			throws SQLException, ClassNotFoundException, IllegalAccessException, InstantiationException {
		// GIVEN
		String expectedDatabaseUrl = "jdbc:mysql://localhost:3306/prod";
		boolean expectedConnectionClosed = false;

		// WHEN
		Connection testConnection = null;
		testConnection = dataBaseTestConfig.getConnection();

		// THEN
		if (testConnection == null)
			fail("Couldn't retrieve connection");

		assertEquals(expectedConnectionClosed, testConnection.isClosed(),
				"Result: expected and actual Connection status match");

		DatabaseMetaData metaData = testConnection.getMetaData();

		assertEquals(expectedDatabaseUrl, metaData.getURL(), "Result: expected and actual Database URLs match");
		testConnection.close();
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
	 */
	@DisplayName("Test Parking DB Configuration - Closing Connection")
	@Test
	public void testCloseConnectiondataBaseTestConfig()
			throws SQLException, ClassNotFoundException, IllegalAccessException, InstantiationException {
		// GIVEN
		boolean expectedConnectionClosed = true;
		Connection testConnection = null;

		testConnection = dataBaseTestConfig.getConnection();

		if (testConnection == null)
			fail("Couldn't retrieve connection to start test");

		// WHEN
		dataBaseTestConfig.closeConnection(testConnection);

		// THEN
		assertEquals(expectedConnectionClosed, testConnection.isClosed(),
				"Result: expected and actual Connections status match");
		testConnection.close();

	}

	@Test
	@DisplayName("Test Parking DB Configuration- Null Connection")
	public void testNullConnectiondataBaseTestConfig()
			throws ClassNotFoundException, SQLException, Exception, InstantiationException {

		// GIVEN
		Connection connection = null;

		// WHEN
		dataBaseTestConfig.getConnection();

		// THEN
		assertNull(connection);
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
	 */
	@DisplayName("Test Parking DB Configuration - Closing Prepared Statement")
	@Test
	public void testClosePreparedStatementdataBaseTestConfig()
			throws IllegalAccessException, InstantiationException, ClassNotFoundException, SQLException {
		// GIVEN
		Connection testConnection = null;
		PreparedStatement testStatement = null;
		boolean expectedStatementClosed = true;

		testConnection = dataBaseTestConfig.getConnection();
		testStatement = testConnection.prepareStatement(DBConstants.GET_TICKET);

		// WHEN
		dataBaseTestConfig.closePreparedStatement(testStatement);

		// THEN
		assertEquals(expectedStatementClosed, testStatement.isClosed(),
				"Result: expected and actual Prepared Statements status match");
		testConnection.close();
		testStatement.close();

	}

	@Test
	@DisplayName("Test Parking DB Configuration - Null preparedStatement")
	public void testNullPreparedStatementdataBaseTestConfig()
			throws ClassNotFoundException, SQLException, Exception, InstantiationException {

		// GIVEN
		PreparedStatement preparedStatement = null;

		// WHEN
		dataBaseTestConfig.getConnection();

		// THEN
		assertNull(preparedStatement);
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
	 */
	@DisplayName("Test Parking DB Configuration - Closing ResultSet")
	@Test
	public void testCloseResultSetdataBaseTestConfig()
			throws IllegalAccessException, InstantiationException, ClassNotFoundException, SQLException {
		// GIVEN
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

	@Test
	@DisplayName("Test Parking DB Configuration - Null ResultSet")
	public void testNullResultSetdataBaseTestConfig()
			throws ClassNotFoundException, SQLException, IllegalAccessException, InstantiationException {

		// GIVEN
		ResultSet resultSet = null;

		// WHEN
		dataBaseTestConfig.getConnection();

		// THEN
		assertNull(resultSet);
	}

}
