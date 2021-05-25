package com.parkit.parkingsystem.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Class: {@link DataBaseConfig} Deals with DB connection configuration
 * settings..<br>
 * <b>Project: </b> P3 - parking system - ParkIt<br>
 * 
 * @see Methods: {@link #getConnection()}, {@link #closeConnection(Connection)}
 *      {@link #closePreparedStatement(PreparedStatement)},
 *      {@link #closeResultSet(ResultSet)}
 * 
 * @author Senthil
 */
public class DataBaseConfig {
	/**
	 * Logger for DatabaseConfig class.
	 */
	private static final Logger logger = LogManager.getLogger("DataBaseConfig");

	/**
	 * getConnection() This method establishes DB connection
	 * 
	 * @return Instance variable - returns connection values
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @exception SQLException
	 * @exception ClassNotFoundException
	 */
	public Connection getConnection() throws SQLException, ClassNotFoundException {
		Connection con = null;
		logger.info("Create DB connection");
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			Properties info = new Properties();
		    info.put("user", "root");
		    info.put("password", "rootroot");
		    
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/prod", info);
		} catch (SQLException e) {
			logger.error("Exception occured : SQLException : ", e);
		} catch (ClassNotFoundException e) {
			logger.error("Exception occured : ClassNotFoundException : ", e);
		}
		return con;
	}

	/**
	 * closeConnection() This method closes the DB connection object - prevents
	 * connection leaks
	 * 
	 * @param con - Connection object value is supplied as a input parameter to this
	 *            method
	 * @exception SQLException
	 */
	public void closeConnection(Connection con) {
		if (con != null) {
			try {
				con.close();
				logger.info("Closing DB connection");
			} catch (SQLException e) {
				logger.error("Error while closing connection", e);
			}
		}
	}

	/**
	 * closePreparedStatement() This method closes the DB PreparedStatement object -
	 * prevents memory leakage, deals with performance issues or even stability
	 * issues
	 * 
	 * @param ps - PreparedStatement object value is supplied as a input parameter
	 *           to this method
	 * @exception SQLException
	 */
	public void closePreparedStatement(PreparedStatement ps) {
		if (ps != null) {
			try {
				ps.close();
				logger.info("Closing Prepared Statement");
			} catch (SQLException e) {
				logger.error("Error while closing prepared statement", e);
			}
		}
	}

	/**
	 * closePreparedStatement() This method closes the DB ResultSet object -
	 * prevents memory leakage, deals with performance issues or even stability
	 * issues
	 * 
	 * @param rs - ResultSet object value is supplied as a input parameter to this
	 *           method
	 * @exception SQLException
	 */
	public void closeResultSet(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
				logger.info("Closing Result Set");
			} catch (SQLException e) {
				logger.error("Error while closing result set", e);
			}
		}
	}
}
