/**
 * 
 */
package com.parkit.parkingsystem.constants;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Test on DataBase Constants")
class DBConstantsTest {
	/**
	 * Instance of DBConstants class
	 */
	DBConstants dbConstants;

	/**
	 * Test SQL query to get available parking spot.
	 */
	final String GET_NEXT_PARKING_SPOT_expected = "select "
			+ "min(PARKING_NUMBER) from parking where AVAILABLE = true and TYPE = ?";

	/**
	 * Test SQL query to update available parking spot.
	 */
	final String UPDATE_PARKING_SPOT_expected = "update " + "parking set available = ? where PARKING_NUMBER = ?";

	/**
	 * Test SQL query to save ticket.
	 */
	final String SAVE_TICKET_expected = "insert into " + "ticket(PARKING_NUMBER, VEHICLE_REG_NUMBER, PRICE, "
			+ "IN_TIME, OUT_TIME) values(?,?,?,?,?)";

	/**
	 * Test SQL query to update ticket.
	 */
	final String UPDATE_TICKET_expected = "update ticket " + "set PRICE=?, OUT_TIME=? where ID=?";

	/**
	 * Test SQL query to get ticket.
	 */
	final String GET_TICKET_expected = "select t.PARKING_NUMBER, "
			+ " t.ID, t.PRICE, t.IN_TIME, t.OUT_TIME, p.TYPE from "
			+ "ticket t,parking p where p.parking_number = t.parking_number "
			+ " and t.VEHICLE_REG_NUMBER=? order by t.IN_TIME  limit 1";

	/**
	 * Test SQL query to get vehicle recurrence frequency.
	 */
	final String GET_VEHICLE_OCCURENCES_expected = "select "
			+ "count(VEHICLE_REG_NUMBER) from ticket where VEHICLE_REG_NUMBER = ?";

	@DisplayName("Test database query WHEN next parking Spot THEN return next parking spot")
	@Test
	void testGetNextParkingSpotDbConstanst() {
		assertEquals(dbConstants.GET_NEXT_PARKING_SPOT, GET_NEXT_PARKING_SPOT_expected);
	}

	@DisplayName("Test database query WHEN update available parking spot"
			+ " THEN updates available parking spot")
	@Test
	void testUpdateParkingSpotDbConstanst() {
		assertEquals(dbConstants.UPDATE_PARKING_SPOT, UPDATE_PARKING_SPOT_expected);
	}

	@DisplayName("Test database query WHEN save ticket THEN ticket saved")
	@Test
	void testSaveTickettDbConstanst() {
		assertEquals(dbConstants.SAVE_TICKET, SAVE_TICKET_expected);
	}

	@DisplayName("Test SQL query WHEN update ticket THEN update ticket done")
	@Test
	void testUpdateTicketbConstanst() {
		assertEquals(dbConstants.UPDATE_TICKET, UPDATE_TICKET_expected);
	}

	@DisplayName("Test SQL query WHEN get ticket THEN get ticket done")
	@Test
	void testGetTickettDbConstanst() {
		assertEquals(dbConstants.GET_TICKET, GET_TICKET_expected);
	}

	@DisplayName("Test SQL query WHEN to get vehicle recurrence "
			+ "frequency THEN gets vehicle recurrence frequency value")
	@Test
	void testGetVehicleOccurencesDbConstanst() {
		assertEquals(dbConstants.GET_VEHICLE_OCCURENCES, GET_VEHICLE_OCCURENCES_expected);
	}

}
