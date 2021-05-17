package com.parkit.parkingsystem.integration.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import com.parkit.parkingsystem.config.DataBaseConfig;

/**
 * <b>Test Class: </b> {@link DataBaseConfigIT} - Integration Testing For
 * database connection configuration setting <br>
 * <b>Class Tested:</b>{@link DataBaseConfig}
 * 
 * @package - com.parkit.parkingsystem.integration.config
 * @project - P4 - parking system - ParkIt
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
public class DataBaseConfigIT {

	static DataBaseConfig dataBaseConfig = new DataBaseConfig();

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
	@DisplayName("Parking DB Configuration - Integration Testing - Get Connection")
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
	@DisplayName("Parking DB Configuration - Integration Testing - Closing Connection")
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
	@Test
	@DisplayName("Parking DB Configuration - Integration Testing - Closing Prepared Statement")
	public void testClosePreparedStatement()
			throws IllegalAccessException, InstantiationException, ClassNotFoundException, SQLException {
		// GIVEN
		Connection testConnection = null;
		PreparedStatement testStatement = null;
		boolean expectedStatementClosed = true;

		testConnection = dataBaseConfig.getConnection();
		testStatement = testConnection.prepareStatement("SELECT 5");

		// WHEN
		dataBaseConfig.closePreparedStatement(testStatement);

		// THEN
		assertEquals(expectedStatementClosed, testStatement.isClosed(),
				"Result: expected and actual Prepared Statements status match");
		testConnection.close();
		testStatement.close();

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
	@DisplayName("Parking DB Configuration - Integration Testing - Closing ResultSet")
	@Test
	public void testCloseResultSet()
			throws IllegalAccessException, InstantiationException, ClassNotFoundException, SQLException {
		// GIVEN
		Connection testConnection = null;
		PreparedStatement testStatement = null;
		ResultSet testResults = null;
		boolean expectedResultSetClosed = true;

		testConnection = dataBaseConfig.getConnection();
		testStatement = testConnection.prepareStatement("Select 2");
		testResults = testStatement.executeQuery();

		// WHEN
		dataBaseConfig.closeResultSet(testResults);

		// THEN

		assertEquals(expectedResultSetClosed, testResults.isClosed(),
				"Result: expected and actual ResultSets status match");

		testConnection.close();
		testStatement.close();
		testResults.close();
	}
}