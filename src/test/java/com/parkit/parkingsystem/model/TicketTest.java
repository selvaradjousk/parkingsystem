/**
 * 
 */
package com.parkit.parkingsystem.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Date;

import org.junit.jupiter.api.Test;

class TicketTest {

	public static final Ticket ticket = new Ticket();

	@Test
	void testTicketCarGetId() {
		int expected = (int) Math.random();
		ticket.setId(expected);
		int actual = ticket.getId();
		assertEquals(expected, actual);
	}

	@Test
	void testTicketBikeGetId() {
		int expected = (int) Math.random();
		ticket.setId(expected);
		int actual = ticket.getId();
		assertEquals(expected, actual);
	}

	@Test
	void testTicketBikeGetVehicleRegNumber() {
		String expected = "AB1243GH";
		ticket.setVehicleRegNumber(expected);
		String actual = ticket.getVehicleRegNumber();
		assertEquals(expected, actual);
	}

	@Test
	void testTicketCarGetVehicleRegNumber() {
		String expected = "ABCDEFGH";
		ticket.setVehicleRegNumber(expected);
		String actual = ticket.getVehicleRegNumber();
		assertEquals(expected, actual);
	}

	@Test
	void testTicketPrice() {
		double expected = (double) Math.random();
		ticket.setPrice(expected);
		double actual = ticket.getPrice();
		assertEquals(expected, actual);
	}

	@Test
	void testTicketInTime() {
		Date expected = new Date();
		ticket.setInTime(expected);
		Date actual = ticket.getInTime();
		assertEquals(expected, actual);
	}

	@Test
	void testTicketOutTime() {
		Date expected = new Date();
		ticket.setOutTime(expected);
		Date actual = ticket.getOutTime();
		assertEquals(expected, actual);
	}

}