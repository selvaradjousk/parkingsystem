package com.parkit.parkingsystem.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.parkit.parkingsystem.config.DataBaseConfig;
import com.parkit.parkingsystem.constants.DBConstants;
import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.model.ParkingSpot;

/**
 * ParkingSpotDAO - Functions and Methods for dealing with availability
 * management of parking spots /slots
 * 
 * @package - com.parkit.parkingsystem.dao
 * @project - P3 - parking system - ParkIt
 * @see Methods: {@link #ParkingSpotDAO()}, {@link #updateParking(ParkingSpot)}
 * 
 * @author Senthil
 */
public class ParkingSpotDAO {
	private static final Logger logger = LogManager.getLogger("ParkingSpotDAO");

	public DataBaseConfig dataBaseConfig = new DataBaseConfig();

	/**
	 * getNextAvailableSlot() This method helps to identify the next parking spot
	 * available for parking of vehicle
	 * 
	 * @return result - returns parking spot identification number
	 * @param parkingType - instance variable of {@link #ParkingType}
	 * @exception Exception - java.lang.Exception
	 */
	public int getNextAvailableSlot(ParkingType parkingType) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int result = -1;
		try {
			con = dataBaseConfig.getConnection();
			ps = con.prepareStatement(DBConstants.GET_NEXT_PARKING_SPOT);
			ps.setString(1, parkingType.toString());
			rs = ps.executeQuery();
			if (rs.next()) {
				result = rs.getInt(1);
				;
			}

		} catch (Exception ex) {
			logger.error("Error fetching next available slot", ex);
		} finally {
			dataBaseConfig.closeConnection(con);
			dataBaseConfig.closeResultSet(rs);
			dataBaseConfig.closePreparedStatement(ps);
		}
		return result;
	}

	/**
	 * updateParking() This method helps to update the parking spot provided to the
	 * customer for parking of vehicle
	 * 
	 * @param parkingSpot - instance variable of {@link #ParkingSpot}
	 * @exception Exception - java.lang.Exception
	 */
	public boolean updateParking(ParkingSpot parkingSpot) {
		// update the availability of that parking slot
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = dataBaseConfig.getConnection();
			ps = con.prepareStatement(DBConstants.UPDATE_PARKING_SPOT);
			ps.setBoolean(1, parkingSpot.isAvailable());
			ps.setInt(2, parkingSpot.getId());
			int updateRowCount = ps.executeUpdate();
			return (updateRowCount == 1);
		} catch (Exception ex) {
			logger.error("Error updating parking info", ex);
			return false;
		} finally {
			dataBaseConfig.closeConnection(con);
			dataBaseConfig.closePreparedStatement(ps);
		}
	}

}
