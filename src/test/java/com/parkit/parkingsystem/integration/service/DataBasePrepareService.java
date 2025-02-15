package com.parkit.parkingsystem.integration.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.parkit.parkingsystem.integration.config.DataBaseTestConfig;
import com.parkit.parkingsystem.model.Ticket;

/**
 * <b>Test Class: </b> {@link DataBasePrepareService} - Functions and Methods
 * for dealing with parking availability management service.<br>
 * <b>Project: <b> P3 - parking system - ParkIt<br>
 * 
 * @see <b>Tests:</b><br>
 *      {@link #clearDBEntries()}: method does the insert function in the DB for
 *      test ticket values<br>
 *      {@link #insertTestTicket(Ticket testData)}: This method set parking
 *      entries to available & clear ticket entries<br>
 * 
 * @author Senthil
 */
public class DataBasePrepareService {

	DataBaseTestConfig dataBaseTestConfig = new DataBaseTestConfig();

	/**
	 * {@link #clearDBEntries()} This method set parking entries to available &
	 * clear ticket entries
	 * @throws SQLException 
	 *
	 * @exception Exception
	 */
	public void clearDBEntries() throws SQLException {
		Connection connection = null;
		 PreparedStatement preparedStatement = null;
		try {
			connection = dataBaseTestConfig.getConnection();

			// set parking entries to available
			connection.prepareStatement("update parking set available = true").execute();

			// clear ticket entries;
			connection.prepareStatement("truncate table ticket").execute();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dataBaseTestConfig.closeConnection(connection);
			dataBaseTestConfig.closePreparedStatement(preparedStatement);
		}
	}
}
