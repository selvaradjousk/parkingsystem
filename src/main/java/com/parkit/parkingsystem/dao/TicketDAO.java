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
 * <b>Project: <b> P3 - parking system - ParkIt<br>
 * 
 * @see Methods: {@link #getTicket(String)}, {@link #saveTicket(Ticket)},
 *      {@link #getVehicleOccurence(String)}, {@link #updateTicket(Ticket)}
 * 
 * @author Senthil
 */
public class TicketDAO {
	private static final Logger logger = LogManager.getLogger("TicketDAO");
	public DataBaseConfig dataBaseConfig = new DataBaseConfig();

	/**
	 * {@link #saveTicket()} This method helps to save the parking spot made
	 * available for parking of vehicle by customer
	 * 
	 * @return boolean - returns boolean value to confirm saving process implemented
	 *         successfully or not.
	 * @param ticket - instance variable of {@link #Ticket}
	 * @exception SQLException
	 */
	public boolean saveTicket(Ticket ticket) {
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = dataBaseConfig.getConnection();
			ps = con.prepareStatement(DBConstants.SAVE_TICKET);
			// ID, PARKING_NUMBER, VEHICLE_REG_NUMBER, PRICE, IN_TIME, OUT_TIME)
			// ps.setInt(1,ticket.getId());
			ps.setInt(1, ticket.getParkingSpot().getId());
			ps.setString(2, ticket.getVehicleRegNumber());
			ps.setDouble(3, ticket.getPrice());
			ps.setTimestamp(4, new Timestamp(ticket.getInTime().getTime()));
			ps.setTimestamp(5, (ticket.getOutTime() == null) ? null : (new Timestamp(ticket.getOutTime().getTime())));
			return ps.execute();
		} catch (SQLException ex) {
			logger.error("Error fetching next available slot", ex);
		} finally {
			dataBaseConfig.closeConnection(con);
			dataBaseConfig.closePreparedStatement(ps);
			return false;
		}
	}

	/**
	 * {@link #getTicket()} This method helps to assign ticket for the parking spot
	 * made available for parking of vehicle by customer
	 * 
	 * @return ticket - returns object value basically instantiated from the class
	 *         Ticket.
	 * @param vehicleRegNumber - This the input information provided by client on
	 *                         the vehicles registration number
	 * @exception SQLException
	 */
	public Ticket getTicket(String vehicleRegNumber) {
		Connection con = null;
		PreparedStatement ps = null;
		Ticket ticket = null;
		try {
			con = dataBaseConfig.getConnection();
			ps = con.prepareStatement(DBConstants.GET_TICKET);
			// ID, PARKING_NUMBER, VEHICLE_REG_NUMBER, PRICE, IN_TIME, OUT_TIME)
			ps.setString(1, vehicleRegNumber);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				ticket = new Ticket();
				ParkingSpot parkingSpot = new ParkingSpot(rs.getInt(1), ParkingType.valueOf(rs.getString(6)), false);
				ticket.setParkingSpot(parkingSpot);
				ticket.setId(rs.getInt(2));
				ticket.setVehicleRegNumber(vehicleRegNumber);
				ticket.setPrice(rs.getDouble(3));
				ticket.setInTime(rs.getTimestamp(4));
				ticket.setOutTime(rs.getTimestamp(5));
			}
			dataBaseConfig.closeResultSet(rs);
		} catch (SQLException ex) {
			logger.error("Error fetching next available slot", ex);
		} finally {
			dataBaseConfig.closePreparedStatement(ps);
			dataBaseConfig.closeConnection(con);
			return ticket;
		}
	}

	/**
	 * getVehicleOccurence() This method helps to calculate the number of parking
	 * visits of the vehicle by customer
	 * 
	 * @return occurrences - returns value on the number of recurrent visits made by
	 *         customer.
	 * @param vehicleRegNumber - This the input information provided by client on
	 *                         the vehicles registration number
	 * @exception SQLException
	 */
	public int getVehicleOccurence(String vehicleRegNumber) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int occurences = 0;

		try {
			con = dataBaseConfig.getConnection();
			ps = con.prepareStatement(DBConstants.GET_VEHICLE_OCCURENCES);
			ps.setString(1, vehicleRegNumber);
			rs = ps.executeQuery();

			if (rs.next())
				occurences = rs.getInt(1);
		} catch (SQLException ex) {
			logger.error("Error fetching vehicle occurence", ex);
		} finally {
			dataBaseConfig.closeConnection(con);
			dataBaseConfig.closePreparedStatement(ps);
			dataBaseConfig.closeResultSet(rs);
			return occurences;
		}
	}

	/**
	 * updateTicket() This method helps to update the ticket information for the
	 * parking spot made available for parking of vehicle by customer
	 * 
	 * @return boolean - returns object value basically instantiated from the class
	 *         Ticket.
	 * @param ticket - instance variable of {@link #Ticket}
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @exception SQLException
	 */
	public boolean updateTicket(Ticket ticket) throws IllegalAccessException, InstantiationException {
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = dataBaseConfig.getConnection();
			ps = con.prepareStatement(DBConstants.UPDATE_TICKET);
			ps.setDouble(1, ticket.getPrice());
			ps.setTimestamp(2, new Timestamp(ticket.getOutTime().getTime()));
			ps.setInt(3, ticket.getId());
			ps.execute();
			return true;
		} catch (SQLException ex) {
			logger.error("Error saving ticket info", ex);
		} finally {
			dataBaseConfig.closeConnection(con);
		}
		return false;
	}
}
