package com.parkit.parkingsystem.unittest.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import com.parkit.parkingsystem.constants.Fare;
import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.dao.ParkingSpotDAO;
import com.parkit.parkingsystem.dao.TicketDAO;
import com.parkit.parkingsystem.model.ParkingSpot;
import com.parkit.parkingsystem.model.Ticket;
import com.parkit.parkingsystem.service.ParkingService;
import com.parkit.parkingsystem.util.InputReaderUtil;

@DisplayName("Test Vehicle Parking Service")
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class ParkingServiceTest {

	/**
	 * ParkingService instance.
	 */
	private static ParkingService parkingService;

	/**
	 * InputReaderUtil instance.
	 */
	@Mock
	private static InputReaderUtil inputReaderUtil;
	/**
	 * ParkingSpotDAO instance.
	 */
	@Mock
	private static ParkingSpotDAO parkingSpotDAO;
	/**
	 * TicketDAO instance.
	 */
	@Mock
	private static TicketDAO ticketDAO;
	/**
	 * ByteArrayOutputStream instance
	 */
	ByteArrayOutputStream byteArrayOutputStream;

	@BeforeEach
	private void setUpPerTest() {
		try {
			when(inputReaderUtil.readVehicleRegistrationNumber()).thenReturn("ABCDEF");
			when(inputReaderUtil.readSelection()).thenReturn(1);

			ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR, false);
			Ticket ticket = new Ticket();
			ticket.setInTime(new Date(System.currentTimeMillis() - (60 * 60 * 1000)));
			ticket.setParkingSpot(parkingSpot);
			ticket.setVehicleRegNumber("ABCDEF");

			when(ticketDAO.getTicket(anyString())).thenReturn(ticket);
			when(ticketDAO.updateTicket(any(Ticket.class))).thenReturn(true);

			when(parkingSpotDAO.updateParking(any(ParkingSpot.class))).thenReturn(true);
			when(parkingSpotDAO.getNextAvailableSpot(any(ParkingType.class))).thenReturn(1);

			parkingService = new ParkingService(inputReaderUtil, parkingSpotDAO, ticketDAO);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Failed to set up test " + "mock objects");
		}
	}

	@DisplayName("Test parking service WHEN incoming process of vehicle THEN confirms vehicle entry ")
	@Test
	void processIncomingVehicleTest() throws IllegalAccessException, InstantiationException, ClassNotFoundException, SQLException {
		// GIVEN

		// WHEN
		parkingService.processIncomingVehicle();

		// THEN
		verify(ticketDAO, Mockito.times(1)).saveTicket(any(Ticket.class));
		verify(parkingSpotDAO, Mockito.times(1)).updateParking(any(ParkingSpot.class));
	}

	@Test
	@DisplayName("Test parking service WHEN incoming Vehicle Process THEN Does Checks input for Vehicle Type Entry")
	void testForMessageTextDisplayIncomingVehicle() throws Exception {

		// GIVEN
		String outputScreen = null;
		byteArrayOutputStream = new ByteArrayOutputStream();
		System.setOut(new PrintStream(byteArrayOutputStream));

		// WHEN
		parkingService.processIncomingVehicle();
		outputScreen = byteArrayOutputStream.toString("UTF-8");


		// THEN

		assertTrue(outputScreen.toString().trim().contains("1 CAR"));
		assertTrue(outputScreen.toString().trim().contains("2 BIKE"));
		assertFalse(outputScreen.toString().trim().contains("2 CAR"));
		assertFalse(outputScreen.toString().trim().contains("1 BIKE"));
//		assertEquals(outputScreen.toString().trim(),"  ");
		byteArrayOutputStream.close();
	}

	@Test
	@DisplayName("Test parking service WHEN incoming Vehicle Process THEN Does Check for the Available Parking Spot")
	void testIncomingVehicleProcessChecksForAvailableParkingSpot() throws Exception {

		// GIVEN
		String outputScreen = null;
		byteArrayOutputStream = new ByteArrayOutputStream();
		System.setOut(new PrintStream(byteArrayOutputStream));

		// WHEN
		parkingService.processIncomingVehicle();
		outputScreen = byteArrayOutputStream.toString("UTF-8");


		// THEN
		assertTrue(outputScreen.toString().trim().contains("Please park" + " your vehicle in spot number:"));
		assertFalse(outputScreen.toString().trim().contains("PLEASE" + " PARK your VEHICLE in spot number:"));
		byteArrayOutputStream.close();

	}

	@Test
	@DisplayName("Test parking service WHEN incoming Vehicle Process THEN confirms check Generated Ticket and Saved in DB")
	void testIncomingVehicleProcessSuccessfullyGenerateTicket() throws Exception {

		// GIVEN
		String outputScreen = null;
		byteArrayOutputStream = new ByteArrayOutputStream();
		System.setOut(new PrintStream(byteArrayOutputStream));

		// WHEN
		parkingService.processIncomingVehicle();
		outputScreen = byteArrayOutputStream.toString("UTF-8");


		// THEN
		assertTrue(outputScreen.toString().trim().contains("Generated " + "Ticket and saved in DB"));
		assertFalse(outputScreen.toString().trim().contains("Generated " + "xxxxx and saved in DB"));
		byteArrayOutputStream.close();
	}

	@Test
	@DisplayName("Test parking service WHEN incoming Vehicle Process THEN check Record the inoming time of Vehicle")
	void testIncomingVehicleProcessSuccessfullyREcordInComingTimeOfVehicle() throws Exception {

		// GIVEN
		String outputScreen = null;
		byteArrayOutputStream = new ByteArrayOutputStream();
		System.setOut(new PrintStream(byteArrayOutputStream));

		// WHEN
		parkingService.processIncomingVehicle();
		outputScreen = byteArrayOutputStream.toString("UTF-8");


		// THEN
		assertTrue(outputScreen.toString().trim().contains("Recorded in-time" + " for vehicle number:"));
		byteArrayOutputStream.close();
	}

	@Test
	@DisplayName("Test parking service WHEN incoming Vehicle Process THEN check does it ask for Vehicle registration number")
	void testIncomingVehicleProcessAsksForVehicleREgistrationNumber() throws Exception {

		// GIVEN
		String outputScreen = null;
		byteArrayOutputStream = new ByteArrayOutputStream();
		System.setOut(new PrintStream(byteArrayOutputStream));

		// WHEN
		parkingService.processIncomingVehicle();
		outputScreen = byteArrayOutputStream.toString("UTF-8");


		// THEN
		assertTrue(outputScreen.toString().trim()
				.contains("Please type the vehicle registration number " + "and press enter key"));
		byteArrayOutputStream.close();
	}

	@DisplayName("Test parking service WHEN incoming Vehicle Process THEN check done for Availability of Next parking spot ")
	@Test
	void getNextParkingNumberIfAvailableTest() throws SQLException {
		// GIVEN
		// WHEN
		parkingService.getNextParkingNumberIfAvailable();

		// THEN
		verify(parkingSpotDAO, Mockito.times(1)).getNextAvailableSpot(any(ParkingType.class));
	}

	@DisplayName("Test parking service WHEN CAR Parking Availability of Next parking spot THEN confirms availability")
	@Test
	void getNextParkingNumberIfAvailableForcarTest() throws SQLException {
		// GIVEN
		ParkingSpot expectedParkingSpot = new ParkingSpot(1, ParkingType.CAR, true);
		when(inputReaderUtil.readSelection()).thenReturn(1);
		when(parkingSpotDAO.getNextAvailableSpot(ParkingType.CAR)).thenReturn(1);

		// WHEN
		ParkingSpot parkingSpot = parkingService.getNextParkingNumberIfAvailable();

		// THEN
		verify(parkingSpotDAO).getNextAvailableSpot(ParkingType.CAR);
		assertEquals(parkingSpot, expectedParkingSpot);
	}

	@DisplayName("Test parking service WHEN BIKE Parking Availability of Next parking spot THEN confirms availability")
	@Test
	void getNextParkingNumberIfAvailableForBikeTest() throws SQLException {
		// GIVEN
		ParkingSpot expectedParkingSpot = new ParkingSpot(9, ParkingType.BIKE, true);
		when(inputReaderUtil.readSelection()).thenReturn(2);
		when(parkingSpotDAO.getNextAvailableSpot(ParkingType.BIKE)).thenReturn(9);

		// WHEN
		ParkingSpot parkingSpot = parkingService.getNextParkingNumberIfAvailable();

		// THEN
		verify(parkingSpotDAO).getNextAvailableSpot(ParkingType.BIKE);
		assertEquals(parkingSpot, expectedParkingSpot);
	}

	@DisplayName("Test parking service WHEN parking Full CAR Parking Availability of Next parking spot THEN error messsage")
	@Test
	void testParkingSpotAvailabuilityFullThrowErrorMessage() throws SQLException {
		// GIVEN
		when(inputReaderUtil.readSelection()).thenReturn(1);
		parkingService = new ParkingService(inputReaderUtil, parkingSpotDAO, ticketDAO);
		ParkingType parkingType = ParkingType.CAR;
		when(parkingSpotDAO.getNextAvailableSpot(parkingType)).thenReturn(0);

		// WHEN
		try {
			parkingService.getNextParkingNumberIfAvailable();

		} catch (Exception e) {
			String ex = e.getMessage();
			// THEN
			assertTrue(ex.contains("Error fetching next available parking slot"));
		}
	}

	@DisplayName("Test parking service WHEN Error parsing user input - availability Parking Spot THEN error message")
	@Test
	void testErrorMessageInvalidVehicleTypeOnThrowIllegalException() throws SQLException {
		// GIVEN
		when(inputReaderUtil.readSelection()).thenReturn(1);
		parkingService = new ParkingService(inputReaderUtil, parkingSpotDAO, ticketDAO);
		ParkingType parkingType = ParkingType.CAR;
		when(parkingSpotDAO.getNextAvailableSpot(parkingType)).thenReturn(0);

		// WHEN
		try {
			parkingService.getNextParkingNumberIfAvailable();

		} catch (IllegalArgumentException e) {
			String ex = e.getMessage();
			// THEN
			assertTrue(ex.contains("Error parsing user input for type of vehicle"));
		}
	}

	@DisplayName("Test parking service WHEN Illegal Argument Exception THEN display message Vehicle type - Invalid Input")
	@Test
	void testErrorMessageInputVechileTypeOnThrowIllegalException() throws SQLException {
		// GIVEN
		when(inputReaderUtil.readSelection()).thenReturn(4);
		parkingService = new ParkingService(inputReaderUtil, parkingSpotDAO, ticketDAO);
		ParkingType parkingType = ParkingType.CAR;
		when(parkingSpotDAO.getNextAvailableSpot(parkingType)).thenReturn(0);

		// WHEN
		try {
			parkingService.getVehicleType();

		} catch (IllegalArgumentException e) {
			String ex = e.getMessage();
			// THEN
			assertTrue(ex.contains("Entered input is invalid"));
		}
	}

	@DisplayName("Test parking service WHEN invalid input THEN Illegal Argument Exception")
	@Test
	void testErrorOnWrongVehicleType() {

		// GIVEN
		when(inputReaderUtil.readSelection()).thenReturn(4);

		// WHEN
		parkingService = new ParkingService(inputReaderUtil, parkingSpotDAO, ticketDAO);

		// THEN
		assertThrows(IllegalArgumentException.class, () -> parkingService.getVehicleType());
	}

	@Test
	@DisplayName("Test parking service WHEN get vehicle type bike THEN Verify and confirm vehicle type bike")
	void testForVehicleTypeIsBike() {
		//
		// GIVEN
		when(inputReaderUtil.readSelection()).thenReturn(2);

		// WHEN
		parkingService = new ParkingService(inputReaderUtil, parkingSpotDAO, ticketDAO);

		// THEN
		assertEquals(ParkingType.BIKE, parkingService.getVehicleType());
	}

	@Test
	@DisplayName("Test parking service WHEN get vehicle type car THEN Verify and confirm vehicle type car")
	void testForVehicleTypeIsCar() {
		//
		// GIVEN
		when(inputReaderUtil.readSelection()).thenReturn(1);

		// WHEN
		parkingService = new ParkingService(inputReaderUtil, parkingSpotDAO, ticketDAO);

		// THEN
		assertEquals(ParkingType.CAR, parkingService.getVehicleType());
	}

	/**
	 * {@link #testForMessageTextDisplayForPaymentAmount()} <br>
	 * GIVEN: exiting process<br>
	 * WHEN: executing parking service for fare calculation<br>
	 * THEN: finalizes the <b>match fare amount</b><br>
	 * <b>Test Condition <i>PASSED</i>: </b> display message match
	 * <code><b>TRUE</b></code> <br>
	 * <b>Test Condition <i>FAILED</i>: </b> display message do not match
	 * <code><b>FALSE</b></code>
	 */
	@Test
	@DisplayName("Test parking service WHEN out going vehicle THEN display Screen message request to pay fare amount")
	void testForMessageTextDisplayForPaymentAmount() throws Exception {

		// GIVEN
		ParkingService parkingService = new ParkingService(inputReaderUtil, parkingSpotDAO, ticketDAO);
		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outContent));

		// WHEN
		parkingService.processIncomingVehicle();
		parkingService.processExitingVehicle();

		String out = null;
		try {
			out = outContent.toString("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		// THEN
		assertTrue(out.contains("Please pay the parking fare:"));
		assertTrue(out.contains("Recorded out-time for vehicle number:"));
	}

	@Test
	@DisplayName("Test parking service WHEN out going vehicle ticket update error THEN display error message on Ticket update")
	void testForMessageTextDisplayForTicketUpdateError() throws Exception {

		// GIVEN
		ParkingService parkingService = new ParkingService(inputReaderUtil, parkingSpotDAO, ticketDAO);
		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outContent));

		// WHEN
		parkingService.processIncomingVehicle();
		parkingService.processExitingVehicle();
		parkingService.displayErrorUpdateTicketInformation();
		String out = null;
		try {
			out = outContent.toString("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		// THEN
		assertTrue(out.contains("Unable to update ticket information."));
	}

	@Test
	@DisplayName("Test parking service WHEN out going vehicle reccurent user THEN display message confirm 5% discount")
	void testForMessageTextDisplayOnDiscountConfirmation() throws Exception {

		// GIVEN
		ParkingService parkingService = new ParkingService(inputReaderUtil, parkingSpotDAO, ticketDAO);
		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outContent));

		// WHEN
		parkingService.processIncomingVehicle();
		parkingService.processExitingVehicle();
		parkingService.confirmOfferForDiscount(true);
		String out = null;
		try {
			out = outContent.toString("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		// THEN
		assertTrue(out.contains("As a recurrent user your have 5% discount!"));
	}

	@Test
	@DisplayName("Test parking service WHEN out going vehicle invalid Vehcile type input THEN display message invalid Vehcile type input")
	void testForMessageTextDisplayWrongInputVehcileType() throws Exception {

		// GIVEN
		when(inputReaderUtil.readSelection()).thenReturn(4);

		// WHEN
		parkingService = new ParkingService(inputReaderUtil, parkingSpotDAO, ticketDAO);
		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outContent));
		// THEN
		assertThrows(IllegalArgumentException.class, () -> parkingService.getVehicleType());

		String out = null;
		try {
			out = outContent.toString("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		// THEN
		assertTrue(out.contains("Incorrect input provided"));
	}

	@DisplayName("Test parking service WHEN outgoing vechicle input invalid THEN Illegal Argument Exception error message")
	@Test
	void testErrorMessageInvalidVehicleTypeOnThrowException() throws SQLException {
		// GIVEN
		when(inputReaderUtil.readSelection()).thenReturn(4);
		parkingService = new ParkingService(inputReaderUtil, parkingSpotDAO, ticketDAO);
		ParkingType parkingType = ParkingType.CAR;
		when(parkingSpotDAO.getNextAvailableSpot(parkingType)).thenReturn(0);

		// WHEN
		try {
			parkingService.getVehicleType();

		} catch (IllegalArgumentException e) {
			String ex = e.getMessage();
			// THEN
			assertTrue(ex.contains("Entered input is invalid"));
		}
	}

	@DisplayName("Test parking service WHEN Vehicle Exit Process THEN confirms process executed ")
	@Test
	void processExitingVehicleTest() throws SQLException {
		// GIVEN

		// WHEN
		parkingService.processExitingVehicle();

		// THEN
		verify(parkingSpotDAO, Mockito.times(1)).updateParking(any(ParkingSpot.class));
	}

	@DisplayName("Test parking service WHEN leaving vehicle THEN parking Lot Status on Exit checked for made free for availability")
	@Test
	void testParkingLotExit() throws Exception {
		// GIVEN
		ParkingService parkingService = new ParkingService(inputReaderUtil, parkingSpotDAO, ticketDAO);

		// inserts test ticket in database
		Ticket testTicket = new Ticket();
		testTicket.setParkingSpot(parkingService.getNextParkingNumberIfAvailable());
		testTicket.setVehicleRegNumber(inputReaderUtil.readVehicleRegistrationNumber());
		Date insertedInTime = new Date();
		insertedInTime.setTime(System.currentTimeMillis() - (1000 * 60 * 60));
		testTicket.setInTime(insertedInTime);
		Ticket ticket = getVehicileRegistrationNumber();
		ticketDAO.saveTicket(testTicket);

		// WHEN
		parkingService.processExitingVehicle();

		// THEN
		ParkingSpot parkingSpot = ticket.getParkingSpot();
		int availableParkingSpotNumber = parkingSpotDAO.getNextAvailableSpot(ParkingType.CAR);
		assertNotNull(parkingSpot, "Confirms parkingSpot for Ticket is not null, So isAvailable");
		assertTrue(parkingSpot.isAvailable(), "Return: availability-YES updated");
		assertEquals(parkingSpot.getId(), availableParkingSpotNumber,
				"Leaving Vehicle ParkingSpot matches with isAvailable ParkingSpot");

	}

	@DisplayName("Test parking service WHEN outgoing vehicle THE check Parking Exit Time is not Null")
	@Test
	void testParkingExitProcessTicketOutTimeNotNull() throws Exception {
		// GIVEN
		ParkingService parkingService = new ParkingService(inputReaderUtil, parkingSpotDAO, ticketDAO);

		// inserts test ticket in database
		Ticket testTicket = new Ticket();
		testTicket.setParkingSpot(parkingService.getNextParkingNumberIfAvailable());
		testTicket.setVehicleRegNumber(inputReaderUtil.readVehicleRegistrationNumber());
		Date insertedInTime = new Date();
		insertedInTime.setTime(System.currentTimeMillis() - (1000 * 60 * 60));
		testTicket.setInTime(insertedInTime);
		ticketDAO.saveTicket(testTicket);

		// WHEN
		parkingService.processExitingVehicle();

		// THEN

		Ticket ticket = getVehicileRegistrationNumber();
		assertNotNull(ticket.getOutTime(), "Return: not null, exit time is recorded");
	}

	@DisplayName("Test parking service WHEN outgoing vehicle THEN check Parking Exit Ticket Is Issued Properly")
	@Test
	void testParkingExitProcessTicketIsIssuedProperly() throws Exception {
		// GIVEN
		ParkingService parkingService = new ParkingService(inputReaderUtil, parkingSpotDAO, ticketDAO);

		// inserts test ticket in database
		Ticket testTicket = new Ticket();
		testTicket.setParkingSpot(parkingService.getNextParkingNumberIfAvailable());
		testTicket.setVehicleRegNumber(inputReaderUtil.readVehicleRegistrationNumber());
		Date insertedInTime = new Date();
		insertedInTime.setTime(System.currentTimeMillis() - (1000 * 60 * 60));
		testTicket.setInTime(insertedInTime);
		ticketDAO.saveTicket(testTicket);

		// WHEN
		parkingService.processExitingVehicle();

		// THEN
		Ticket ticket = getVehicileRegistrationNumber();
		BigDecimal FareCAR_RATE_PER_HOUR = new BigDecimal(Fare.CAR_RATE_PER_HOUR).setScale(2,
				RoundingMode.HALF_UP);
		BigDecimal ticketGetPrice = new BigDecimal(ticket.getPrice()).setScale(2, RoundingMode.HALF_UP);
		assertNotEquals(null, ticket.getPrice(), "Fare price of the Ticket is not a null value");
		assertNotEquals(0, ticket.getPrice());
		assertEquals(FareCAR_RATE_PER_HOUR, ticketGetPrice, "Return: defaule per hour price");
	}
	

	@Test
	@DisplayName("Test parking service WHEN outgoing vehicle in case update error THEN display Error Update Ticket Information")
	void testDisplayErrorUpdateTicketInformation() throws Exception {

		// GIVEN
		String outputScreen = null;
		byteArrayOutputStream = new ByteArrayOutputStream();
		System.setOut(new PrintStream(byteArrayOutputStream));

		// WHEN
		parkingService.displayErrorUpdateTicketInformation();
		outputScreen = byteArrayOutputStream.toString("UTF-8");

		// THEN
		assertTrue(outputScreen.toString().trim().contains("Unable to update ticket information."));
		assertFalse(outputScreen.toString().trim().contains("Unable to UPDATe ticket information"));
		byteArrayOutputStream.close();
	}

	static Ticket getVehicileRegistrationNumber() throws Exception {
		Ticket ticket = null;
		String registrationNumber = new String();
		registrationNumber = inputReaderUtil.readVehicleRegistrationNumber();

		ticket = ticketDAO.getTicket(registrationNumber);

		if (ticket == null)
			fail("Couldn't retrieve ticket from DB");

		return ticket;
	}
}
