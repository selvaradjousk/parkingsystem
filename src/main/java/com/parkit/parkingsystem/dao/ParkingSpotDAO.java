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
 * Class: {@link ParkingSpotDAO} - Parking spot Data Access Objects.<br>
 * <b>Project: </b> P3 - parking system - ParkIt<br>
 * 
 * @see Methods: {@link #ParkingSpotDAO()}, 
 * {@link #updateParking(ParkingSpot)}
 * 
 * @author Senthil
 */
public class ParkingSpotDAO {

	 /**
	 * Constant value for one
	 */
	public static final int NO_MAGIC_PARAMETER_VALUE_ONE = 1;

	 /**
	 * Constant value for Two
	 */
	 public static final int NO_MAGIC_PARAMETER_VALUE_TWO = 2;

	 /**
	 * Constant value for one
	 */
	public static final int NO_MAGIC_PARAMETER_VALUE_MINUS_ONE = -1;

	/**
	 * Logger for ParkingSpotDao.
	 */
	private static final Logger LOGGER 
	= LogManager.getLogger("ParkingSpotDAO");

	/**
	 * Instance dataBaseConfig of DataBaseConfig 
	 * to connect to DataBase.
	 */
	 private DataBaseConfig dataBaseConfig = new DataBaseConfig();

	  /**
	   * Setter of a DataBaseConfig.
	   *
	   * @param dataBaseConfig set instance
	   */
	public void setDataBaseConfig(final DataBaseConfig dataBaseConfig) {
	    this.dataBaseConfig = dataBaseConfig;
	  }

	/**
	 * getNextAvailableSlot() Identify next parking spot available.
	 * 
	 * @return result - returns parking spot identification number
	 * @param parkingType - instance variable of {@link #ParkingType}
	 * @exception Exception - java.lang.Exception
	 */
	public int getNextAvailableSpot(final ParkingType parkingType) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int result = NO_MAGIC_PARAMETER_VALUE_MINUS_ONE;
		try {
			con = dataBaseConfig.getConnection();
			ps = con.prepareStatement(DBConstants.GET_NEXT_PARKING_SPOT);
			ps.setString(NO_MAGIC_PARAMETER_VALUE_ONE, 
					parkingType.toString());
			rs = ps.executeQuery();
			if (rs.next()) {
				result = rs.getInt(NO_MAGIC_PARAMETER_VALUE_ONE);
			}
		} catch (Exception ex) {
			LOGGER.error("Error fetching next available slot", ex);
		} finally {
			dataBaseConfig.closeConnection(con);
			dataBaseConfig.closeResultSet(rs);
			dataBaseConfig.closePreparedStatement(ps);
		}
		return result;
	}

	/**
	 * updateParking() Update the parking spot provided.
	 * 
	 * @param parkingSpot - instance variable of {@link #ParkingSpot}
	 * @return boolean update parking status
	 * @exception Exception - java.lang.Exception
	 */
	public boolean updateParking(final ParkingSpot parkingSpot) {
		// update the availability of that parking slot
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = dataBaseConfig.getConnection();
			ps = con.prepareStatement(DBConstants.UPDATE_PARKING_SPOT);
			ps.setBoolean(NO_MAGIC_PARAMETER_VALUE_ONE, 
					parkingSpot.isAvailable());
			ps.setInt(NO_MAGIC_PARAMETER_VALUE_TWO, parkingSpot.getId());
			int updateRowCount = ps.executeUpdate();
			return (updateRowCount == NO_MAGIC_PARAMETER_VALUE_ONE);
		} catch (Exception ex) {
			LOGGER.error("Error updating parking info", ex);
			return false;
		} finally {
			dataBaseConfig.closeConnection(con);
			dataBaseConfig.closePreparedStatement(ps);
		}
	}
}
