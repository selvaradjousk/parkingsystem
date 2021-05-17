package com.parkit.parkingsystem.integration.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Timestamp;

import com.parkit.parkingsystem.constants.DBConstants;
import com.parkit.parkingsystem.integration.config.DataBaseTestConfig;
import com.parkit.parkingsystem.model.Ticket;

/**
 * <b>Test Class: </b> {@link DataBasePrepareService} - Functions and Methods
 * for dealing with parking availability management service
 * 
 * @package - com.parkit.parkingsystem.integration.service
 * @project - P4 - parking system - ParkIt
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
	 *
	 * @exception Exception
	 */
	public void clearDBEntries() {
		Connection connection = null;
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
		}
	}

	/**
	 * {@link #insertTestTicket(Ticket testData)} This method does the insert
	 * function in the DB for test ticket values
	 *
	 * @exception Exception
	 */
	public void insertTestTicket(Ticket testData) {
		Connection connection = null;

		try {
			connection = dataBaseTestConfig.getConnection();

			PreparedStatement ps = connection.prepareStatement(DBConstants.SAVE_TICKET);
			ps.setInt(1, testData.getParkingSpot().getId());
			ps.setString(2, testData.getVehicleRegNumber());
			ps.setDouble(3, testData.getPrice());
			ps.setTimestamp(4, new Timestamp(testData.getInTime().getTime()));
			ps.setTimestamp(5,
					(testData.getOutTime() == null) ? null : (new Timestamp(testData.getOutTime().getTime())));

			ps.execute();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}

}
