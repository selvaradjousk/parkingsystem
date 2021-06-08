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
 * settings.<br>
 * <b>Project: </b> P3 - parking system - ParkIt<br>
 *
 * @see Methods: {@link #getConnection()},
 *  {@link #closeConnection(Connection)},
 *      {@link #closePreparedStatement(PreparedStatement)},
 *      {@link #closeResultSet(ResultSet)}
 * 
 * @author Senthil
 */
public class DataBaseConfig {
	/**
	 * Logger for DatabaseConfig class.
	 */
	private static final Logger LOGGER
	= LogManager.getLogger("DataBaseConfig");

	/**
	 * getConnection() This method establishes DB connection.
	 *
	 * @return Instance variable - returns connection values
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public Connection getConnection()
			throws SQLException, ClassNotFoundException {
		Connection con = null;
		LOGGER.info("Create DB connection");
			Class.forName("com.mysql.cj.jdbc.Driver");

			Properties info = new Properties();
			info.put("user", "root");
			info.put("password", "rootroot");

			con = DriverManager
					.getConnection("jdbc:mysql://localhost:"
							+ "3306/prod", info);

		return con;
	}

	/**
	 * closeConnection() Closes the DB connection
	 *  prevents connection leaks.
	 *
	 * @param con - Connection instance value
	 * @exception SQLException
	 */
	public void closeConnection(final Connection con)
			throws SQLException {
		if (con != null) {
			try {
				con.close();
				LOGGER.info("Closing DB connection");
			} catch (SQLException e) {
				LOGGER.error("Error while closing"
						+ " connection", e);
			}
		}
	}

	/**
	 * closePreparedStatement() This method closes
	 *  the DB PreparedStatement object.
	 *
	 * @param ps - PreparedStatement instance value input parameter
	 * @exception SQLException
	 */
	public void closePreparedStatement(final PreparedStatement ps) {
		if (ps != null) {
			try {
				ps.close();
				LOGGER.info("Closing Prepared Statement");
			} catch (SQLException e) {
				LOGGER.error("Error while closing"
						+ " prepared statement", e);
			}
		}
	}

	/**
	 * closeResultSet() This method closes the DB ResultSet object.
	 *
	 * @param rs - ResultSet instance value is supplied
	 *  as a input parameter
	 * @exception SQLException
	 */
	public void closeResultSet(final ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
				LOGGER.info("Closing Result Set");
			} catch (SQLException e) {
				LOGGER.error("Error while closing"
						+ " result set", e);
			}
		}
	}
}
