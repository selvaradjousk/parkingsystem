package com.parkit.parkingsystem.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
 *  {@link #updateParking(ParkingSpot)}
 * @author Senthil
 */
public class ParkingSpotDAO {

	/**
	 * Constant value for one.
	 */
	public static final int NO_MAGIC_PARAMETER_VALUE_ONE = 1;

	 /**
	 * Constant value for Two.
	 */
	 public static final int NO_MAGIC_PARAMETER_VALUE_TWO = 2;

	 /**
	 * Constant value for negative value One.
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
	   * @param aDataBaseConfig set instance
	   */
	public void setDataBaseConfig(final DataBaseConfig aDataBaseConfig) {
	    this.dataBaseConfig = aDataBaseConfig;
	  }

	/**
	 * getNextAvailableSlot() Identify next parking spot available.
	 *
	 * @return result - returns parking spot identification number
	 * @param parkingType - instance variable of {@link #ParkingType}
	 * @throws SQLException
	 * @exception Exception - java.lang.Exception
	 */
	public int getNextAvailableSpot(final ParkingType parkingType)
			throws SQLException {
		int result = NO_MAGIC_PARAMETER_VALUE_MINUS_ONE;
		try (Connection con = dataBaseConfig.getConnection();
			PreparedStatement ps = con.prepareStatement(DBConstants
					.GET_NEXT_PARKING_SPOT)) {
			ps.setString(NO_MAGIC_PARAMETER_VALUE_ONE,
					parkingType.toString());
			try (ResultSet rs = ps.executeQuery()) {
			if (rs.next()) {
				result = rs
					.getInt(NO_MAGIC_PARAMETER_VALUE_ONE);
				return result;
			}
			}
		} catch (Exception ex) {
			LOGGER.error("Error fetching next available slot", ex);
		}
		return result;
	}

	/**
	 * updateParking() Update the parking spot provided.
	 *
	 * @param parkingSpot - instance variable
	 *  of {@link #ParkingSpot}
	 * @return boolean update parking status
	 * @throws SQLException
	 * @exception Exception - java.lang.Exception
	 */
	public boolean updateParking(final ParkingSpot parkingSpot)
			throws SQLException {
		// update the availability of that parking slot
		try (Connection con = dataBaseConfig.getConnection();
				PreparedStatement ps
				= con.prepareStatement(DBConstants
						.UPDATE_PARKING_SPOT)) {
			ps.setBoolean(NO_MAGIC_PARAMETER_VALUE_ONE,
					parkingSpot.isAvailable());
			ps.setInt(NO_MAGIC_PARAMETER_VALUE_TWO,
					parkingSpot.getId());
			int updateRowCount = ps.executeUpdate();

			if (updateRowCount == NO_MAGIC_PARAMETER_VALUE_ONE) {
				return true;
			}
//			return (updateRowCount == NO_MAGIC_PARAMETER_VALUE_ONE);
		} catch (Exception ex) {
			LOGGER.error("Error updating parking info", ex);
//			return false;
//		} finally {
//			dataBaseConfig.closeConnection(con);
//			dataBaseConfig.closePreparedStatement(ps);
		}
		return false;
	}
}
