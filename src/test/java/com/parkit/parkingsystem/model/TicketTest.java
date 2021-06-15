/**
 * 
 */
package com.parkit.parkingsystem.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Date;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Test Ticket Model Class")
class TicketTest {

	public static final Ticket ticket = new Ticket();

	@DisplayName("Test Ticket WHEN get Ticket id for car THEN gets id")
	@Test
	void testTicketCarGetId() {
		int expected = 5;
		ticket.setId(expected);
		int actual = ticket.getId();
		assertEquals(expected, actual);
	}

	@DisplayName("Test Ticket WHEN get Ticket id for bike THEN gets id")
	@Test
	void testTicketBikeGetId() {
		int expected = 6;
		ticket.setId(expected);
		int actual = ticket.getId();
		assertEquals(expected, actual);
	}

	@DisplayName("Test Ticket WHEN get vehicle registration number for bike THEN gets registration number")
	@Test
	void testTicketBikeGetVehicleRegNumber() {
		String expected = "AB1243GH";
		ticket.setVehicleRegNumber(expected);
		String actual = ticket.getVehicleRegNumber();
		assertEquals(expected, actual);
	}

	@DisplayName("Test Ticket WHEN get vehicle registration number for car THEN gets registration number")
	@Test
	void testTicketCarGetVehicleRegNumber() {
		String expected = "ABCDEFGH";
		ticket.setVehicleRegNumber(expected);
		String actual = ticket.getVehicleRegNumber();
		assertEquals(expected, actual);
	}

	@DisplayName("Test Ticket WHEN get ticket price at random THEN gets ticket price")
	@Test
	void testTicketPrice() {
		double expected = (double) Math.random();
		ticket.setPrice(expected);
		double actual = ticket.getPrice();
		assertEquals(expected, actual);
	}

	@DisplayName("Test Ticket WHEN get ticket in time THEN gets in time")
	@Test
	void testTicketInTime() {
		Date expected = new Date();
		ticket.setInTime(expected);
		Date actual = ticket.getInTime();
		assertEquals(expected, actual);
	}
	
	@DisplayName("Test Ticket WHEN get ticket in time null THEN confirms get in time null")
	@Test
	void testgetTicketInTimeNull() {
		Date expected = null;
		ticket.setInTime(expected);
		Date actual = ticket.getInTime();
		assertEquals(expected, actual);
	}

	@DisplayName("Test Ticket WHEN get ticket out time THEN gets out time")
	@Test
	void testTicketOutTime() {
		Date expected = new Date();
		ticket.setOutTime(expected);
		Date actual = ticket.getOutTime();
		assertEquals(expected, actual);
	}
	
	@DisplayName("Test Ticket WHEN get ticket out time null THEN confirms get out time null")
	@Test
	void testTicketOutTimeNull() {
		Date expected = null;
		ticket.setOutTime(expected);
		Date actual = ticket.getOutTime();
		assertEquals(expected, actual);
	}
	
	@DisplayName("Test Ticket WHEN get ticket occurences in database THEN gets recurrence value")
	@Test
	void testOccurences() {
		boolean occurencesExpected = true;
		ticket.setOccurences(occurencesExpected);
		boolean actual = ticket.occurences();
		assertEquals(occurencesExpected, actual);
	}

}