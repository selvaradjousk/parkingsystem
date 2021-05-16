package com.parkit.parkingsystem.integration.service;

import com.parkit.parkingsystem.constants.DBConstants;
import com.parkit.parkingsystem.dao.TicketDAO;
import com.parkit.parkingsystem.integration.config.DataBaseTestConfig;
import com.parkit.parkingsystem.model.Ticket;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * Class: {@link DataBasePrepareService} - Functions and Methods to update the
 * availability of the parking spots on customer exit of parking in the DB
 * 
 * @package - com.parkit.parkingsystem.integration.service
 * @project - P4 - parking system - ParkIt
 * @see Methods: {@link #clearDBEntries()},
 *      {@link #insertTestTicket(Ticket testData)},
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
