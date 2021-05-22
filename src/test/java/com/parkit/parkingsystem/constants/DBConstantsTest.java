/**
 * 
 */
package com.parkit.parkingsystem.constants;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Test on DBConstants.
 * 
 * @author Senthil
 *
 */
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

	/**
	 * test SQL query to get available parking spot.
	 */
	@DisplayName("Test GET_NEXT_PARKING_SPOT")
	@Test
	void testGetNextParkingSpotDbConstanst() {
		assertEquals(dbConstants.GET_NEXT_PARKING_SPOT, GET_NEXT_PARKING_SPOT_expected);
	}

	/**
	 * Test SQL query to update available parking spot.
	 */
	@DisplayName("Test UPDATE_PARKING_SPOT")
	@Test
	void testUpdateParkingSpotDbConstanst() {
		assertEquals(dbConstants.UPDATE_PARKING_SPOT, UPDATE_PARKING_SPOT_expected);
	}

	/**
	 * Test SQL query to save ticket.
	 */
	@DisplayName("Test SAVE_TICKET")
	@Test
	void testSaveTickettDbConstanst() {
		assertEquals(dbConstants.SAVE_TICKET, SAVE_TICKET_expected);
	}

	/**
	 * Test SQL query to update ticket.
	 */
	@DisplayName("Test UPDATE_TICKET")
	@Test
	void testUpdateTicketbConstanst() {
		assertEquals(dbConstants.UPDATE_TICKET, UPDATE_TICKET_expected);
	}

	/**
	 * Test SQL query to get ticket.
	 */
	@DisplayName("Test GET_TICKET")
	@Test
	void testGetTickettDbConstanst() {
		assertEquals(dbConstants.GET_TICKET, GET_TICKET_expected);
	}

	/**
	 * Test SQL query to get vehicle recurrence frequency.
	 */
	@DisplayName("Test GET_VEHICLE_OCCURENCES")
	@Test
	void testGetVehicleOccurencesDbConstanst() {
		assertEquals(dbConstants.GET_VEHICLE_OCCURENCES, GET_VEHICLE_OCCURENCES_expected);
	}

}
