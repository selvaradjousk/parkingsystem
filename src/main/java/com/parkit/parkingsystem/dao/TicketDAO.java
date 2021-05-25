package com.parkit.parkingsystem.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.parkit.parkingsystem.config.DataBaseConfig;
import com.parkit.parkingsystem.constants.DBConstants;
import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.model.ParkingSpot;
import com.parkit.parkingsystem.model.Ticket;

/**
 * Class: {@link TicketDAO} - Ticket Data Access Objects.<br>
 * <b>Project: </b> P3 - parking system - ParkIt<br>
 * 
 * @see Methods: {@link #getTicket(String)}, {@link #saveTicket(Ticket)},
 *      {@link #getVehicleOccurence(String)}, {@link #updateTicket(Ticket)}
 * 
 * @author Senthil
 */
public class TicketDAO {

	/**
	 * Logger for TicketDao.
	 */
	private static final Logger LOGGER = LogManager.getLogger("TicketDAO");

	/**
	 * dataBaseConfig instance to connect with DataBase.
	 */
	private DataBaseConfig dataBaseConfig = new DataBaseConfig();

	/**
	 * DataBaseConfig setter.
	 *
	 * @param dataBaseConfig set instance
	 */
	public void setDataBaseConfig(final DataBaseConfig dataBaseConfig) {
		this.dataBaseConfig = dataBaseConfig;
	}
	
	 public static final int NO_MAGIC_PARAMETER_VALUE_ZERO = 0;
	 public static final int NO_MAGIC_PARAMETER_VALUE_ONE = 1;
	 public static final int NO_MAGIC_PARAMETER_VALUE_TWO = 2;
	 public static final int NO_MAGIC_PARAMETER_VALUE_THREE = 3;
	 public static final int NO_MAGIC_PARAMETER_VALUE_FOUR = 4;
	 public static final int NO_MAGIC_PARAMETER_VALUE_FIVE = 5;
	 public static final int NO_MAGIC_PARAMETER_VALUE_SIX = 6;

	/**
	 * {@link #saveTicket()} save the parking spot available alloted.
	 * 
	 * @return boolean - returns confirm saving
	 * @param ticket - instance variable of {@link #Ticket}
	 * @throws InstantiationException 
	 * @throws IllegalAccessException 
	 * @exception SQLException
	 * @throws ClassNotFoundException 
	 */
	public boolean saveTicket(final Ticket ticket) throws SQLException, ClassNotFoundException {
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = dataBaseConfig.getConnection();
			ps = con.prepareStatement(DBConstants.SAVE_TICKET);
			// ID, PARKING_NUMBER, VEHICLE_REG_NUMBER, PRICE, IN_TIME, OUT_TIME)
			// ps.setInt(1,ticket.getId())
			ps.setInt(NO_MAGIC_PARAMETER_VALUE_ONE, ticket.getParkingSpot().getId());
			ps.setString(NO_MAGIC_PARAMETER_VALUE_TWO, ticket.getVehicleRegNumber());
			ps.setDouble(NO_MAGIC_PARAMETER_VALUE_THREE, ticket.getPrice());
			ps.setTimestamp(NO_MAGIC_PARAMETER_VALUE_FOUR, 
					new Timestamp(ticket.getInTime().getTime()));
			ps.setTimestamp(NO_MAGIC_PARAMETER_VALUE_FIVE, (ticket.getOutTime() == null) ? 
					null : (new Timestamp(ticket.getOutTime().getTime())));
			if (ps.execute()) {
				LOGGER.info("Ticket Saved");
				return true;
			}
		} catch (SQLException ex) {
			LOGGER.error("Error fetching next available slot", ex);
		} finally {
			dataBaseConfig.closeConnection(con);
			dataBaseConfig.closePreparedStatement(ps);
		}
		return false;
	}

	/**
	 * {@link #getTicket()} Assign ticket for the parking spot alloted.
	 * 
	 * @return ticket - returns instance value of class Ticket.
	 * @param vehicleRegNumber - input vehicles registration number
	 * @exception SQLException
	 */
	public Ticket getTicket(final String vehicleRegNumber) {
		Connection con = null;
		PreparedStatement ps = null;
		Ticket ticket = null;
		ResultSet rs = null;
		try {
			con = dataBaseConfig.getConnection();
			ps = con.prepareStatement(DBConstants.GET_TICKET);
			// ID, PARKING_NUMBER, VEHICLE_REG_NUMBER, PRICE, IN_TIME, OUT_TIME)
			ps.setString(NO_MAGIC_PARAMETER_VALUE_ONE, vehicleRegNumber);

			rs = ps.executeQuery();
			if (rs.next()) {
				ticket = new Ticket();
				ParkingSpot parkingSpot = new ParkingSpot(rs.getInt(NO_MAGIC_PARAMETER_VALUE_ONE), 
						ParkingType.valueOf(rs.getString(NO_MAGIC_PARAMETER_VALUE_SIX)), false);
				ticket.setParkingSpot(parkingSpot);
				ticket.setId(rs.getInt(NO_MAGIC_PARAMETER_VALUE_TWO));
				ticket.setVehicleRegNumber(vehicleRegNumber);
				ticket.setPrice(rs.getDouble(NO_MAGIC_PARAMETER_VALUE_THREE));
				ticket.setInTime(rs.getTimestamp(NO_MAGIC_PARAMETER_VALUE_FOUR));
				ticket.setOutTime(rs.getTimestamp(NO_MAGIC_PARAMETER_VALUE_FIVE));
			}
			dataBaseConfig.closeResultSet(rs);
		} catch (SQLException ex) {
			LOGGER.error("Error fetching next available slot", ex);
		} finally {
			dataBaseConfig.closePreparedStatement(ps);
			dataBaseConfig.closeConnection(con);
			return ticket;
		}
	}

	/**
	 * getVehicleOccurence() gets the recurrence visit of customer.
	 * 
	 * @return occurrences - returns value on the number of recurrence
	 * @param vehicleRegNumber - input information vehicles Reg number
	 * @return occurrences representing recurrence user
	 * @throws InstantiationException 
	 * @throws IllegalAccessException 
	 * @exception SQLException
	 * @throws ClassNotFoundException 
	 */
	public boolean getVehicleOccurence(final String vehicleRegNumber) 
			throws SQLException, ClassNotFoundException {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean occurences = false;

		try {
			con = dataBaseConfig.getConnection();
			ps = con.prepareStatement(DBConstants.GET_VEHICLE_OCCURENCES);
			ps.setString(NO_MAGIC_PARAMETER_VALUE_ONE, vehicleRegNumber);
			rs = ps.executeQuery();

			if (rs.next() && rs.getInt(1) > NO_MAGIC_PARAMETER_VALUE_ONE) {
				occurences = true;
			} else {
				LOGGER.debug("No ticket found with this registration number");
			}

		} catch (SQLException ex) {
			LOGGER.error("Error fetching vehicle occurence", ex);
		} finally {
			dataBaseConfig.closePreparedStatement(ps);
			dataBaseConfig.closeResultSet(rs);
		}
		return occurences;
	}

	/**
	 * updateTicket() Update the ticket information for the customer.
	 * 
	 * @return boolean - true if updated successful else false
	 * @param ticket - instance variable of {@link #Ticket}
	 * @return boolean updata ticket status
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @exception SQLException
	 * @throws ClassNotFoundException 
	 */
	public boolean updateTicket(final Ticket ticket) 
			throws SQLException, ClassNotFoundException {
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = dataBaseConfig.getConnection();
			ps = con.prepareStatement(DBConstants.UPDATE_TICKET);
			ps.setDouble(NO_MAGIC_PARAMETER_VALUE_ONE, ticket.getPrice());
			ps.setTimestamp(NO_MAGIC_PARAMETER_VALUE_TWO, 
					new Timestamp(ticket.getOutTime().getTime()));
			ps.setInt(NO_MAGIC_PARAMETER_VALUE_THREE, ticket.getId());
			if (ps.executeUpdate() == NO_MAGIC_PARAMETER_VALUE_ONE) {
				return true;
			}
		} catch (SQLException ex) {
			LOGGER.error("Error saving ticket info", ex);
		} finally {
			dataBaseConfig.closeConnection(con);
			dataBaseConfig.closePreparedStatement(ps);
		}
		return false;
	}
}
