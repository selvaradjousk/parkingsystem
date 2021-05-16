package com.parkit.parkingsystem.integration.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.parkit.parkingsystem.config.DataBaseConfig;

/**
 * Class {@link DataBaseTestConfig} - Integration Tests for database connection setting
 *      configuration {@link DataBaseConfig}
 * 
 * @package - com.parkit.parkingsystem.integration.dao
 * @project - P4 - parking system - ParkIt
 * @see Tests: {@link #getConnection()},
 *      {@link #closeConnection(Connection con)},
 *      {@link #closePreparedStatement(PreparedStatement ps)}, {@link #closeResultSet(ResultSet rs)},
 * 
 * @author Senthil
 */
public class DataBaseTestConfig extends DataBaseConfig {

	private static final Logger logger = LogManager.getLogger("DataBaseConfig");

	public Connection getConnection() throws IllegalAccessException, InstantiationException{
		Connection con =null;
		logger.info("Create DB connection");
		try {
		Class.forName("com.mysql.cj.jdbc.Driver");
		con = DriverManager.getConnection("jdbc:mysql://localhost:3306/prod", "root", "rootroot");
    } catch (SQLException e) {
        System.err.println("Exception occured : SQLException : "
                + e.getMessage());
        e.printStackTrace();
    } catch (ClassNotFoundException e) {
        System.err.println("Exception occured : ClassNotFoundException : "
                + e.getMessage());
        e.printStackTrace();
    }
		
		return con;
	}


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
