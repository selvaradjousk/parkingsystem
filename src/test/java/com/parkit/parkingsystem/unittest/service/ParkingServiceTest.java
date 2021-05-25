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
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
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

/**
 * <b>Test Class: </b> {@link ParkingServiceTest} - Performs Integration Test on
 * Parking Services for customer of ParkIt<br>
 * <b>Class Tested:</b> {@link ParkingService}.<br>
 * <b>Project: <b> P3 - parking system - ParkIt.<br>
 * 
 * @see <b>Tests:</b><br>
 *      {@link #testParkingLotExit()}: Parking Service Testing - Parking Lot
 *      Status on Exit<br>
 *      {@link #processExitingVehicleTest()}: Parking Service Testing - Vehicle
 *      Exit Process Test <br>
 *      {@link #getNextParkingNumberIfAvailableTest()}: Parking Service Testing
 *      - Check availability of Next parking spot freely <br>
 *      {@link #processIncomingVehicleTest()}: Parking Service Testing - Test
 *      Incoming process of vehicle <br>
 * @author Senthil
 */
@DisplayName("Vehicle Parking Service - Testing ")
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

	/**
	 * {@link #processIncomingVehicleTest()} Integration Test. <br>
	 * GIVEN: <br>
	 * WHEN: processing of the incoming vehicle<br>
	 * THEN: parking spot alloted ticket saved and <b>availability status.</b><br>
	 * <b>Test Condition <i>PASSED</i>: </b>verify saveTicket and updateParking
	 * <code><b>TRUE</b></code> <br>
	 * <b>Test Condition <i>FAILED</i>: </b>verify saveTicket and updateParking
	 * <code><b>FALSE</b></code>
	 * @throws InstantiationException 
	 * @throws IllegalAccessException 
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	@DisplayName("Parking Service Testing - Incoming process of vehicle ")
	@Test
	void processIncomingVehicleTest() throws IllegalAccessException, InstantiationException, ClassNotFoundException, SQLException {
		// GIVEN

		// WHEN
		parkingService.processIncomingVehicle();

		// THEN
		verify(ticketDAO, Mockito.times(1)).saveTicket(any(Ticket.class));
		verify(parkingSpotDAO, Mockito.times(1)).updateParking(any(ParkingSpot.class));
	}

	/**
	 * {@link #testForMessageTextDisplayIncomingVehicle()} <br>
	 * GIVEN: incoming process<br>
	 * WHEN: executing parking service for incoming vehicle<br>
	 * THEN: finalizes the <b>match entry ticket</b><br>
	 * <b>Test Condition <i>PASSED</i>: </b> display message match
	 * <code><b>TRUE</b></code> <br>
	 * <b>Test Condition <i>FAILED</i>: </b> display message do not match
	 * <code><b>FALSE</b></code>
	 */
	@Test
	@DisplayName("Test - Incoming Vehicle Process Does " + "Checks input for Vehicle Type Entry")
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
	@DisplayName("Test - Incoming Vehicle Process Does Check for " + "the Available Parking Spot")
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
	@DisplayName("Test - Incoming Vehicle Process Does Generated " + "Ticket and Saved in DB")
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
	@DisplayName("Test - Incoming Vehicle Process Does Record the " + "inoming time of Vehicle")
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
	@DisplayName("Test - Incoming Vehicle Process Does it ask for Vehicle " + "registration number")
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

	/**
	 * {@link #getNextParkingNumberIfAvailableTest()} Integration Test <br>
	 * GIVEN: <br>
	 * WHEN: check on the Parking spot availability for Parking<br>
	 * THEN: <b>availability status</b><br>
	 * <b>Test Condition <i>PASSED</i>: </b>verify next parking spot status
	 * availability <code><b>TRUE</b></code> <br>
	 * <b>Test Condition <i>FAILED</i>: </b>verify next parking spot status
	 * availability <code><b>FALSE</b></code>
	 * @throws SQLException 
	 */
	@DisplayName("Parking Service Testing - Availability of Next " + "parking spot ")
	@Test
	void getNextParkingNumberIfAvailableTest() throws SQLException {
		// GIVEN
		// WHEN
		parkingService.getNextParkingNumberIfAvailable();

		// THEN
		verify(parkingSpotDAO, Mockito.times(1)).getNextAvailableSpot(any(ParkingType.class));
	}

	/**
	 * {@link #getNextParkingNumberIfAvailableForCarTest()} Integration Test <br>
	 * GIVEN: <br>
	 * WHEN: check on the CAR Parking spot availability for Parking<br>
	 * THEN: <b>verify availability status</b><br>
	 * <b>Test Condition <i>PASSED</i>: </b> actual expected spots match
	 * <code><b>TRUE</b></code> <br>
	 * <b>Test Condition <i>FAILED</i>: </b>actual expected spots not match
	 * availability <code><b>FALSE</b></code>
	 * @throws SQLException 
	 */
	@DisplayName("CAR Parking Availability of Next parking spot ")
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

	/**
	 * {@link #getNextParkingNumberIfAvailableForBikeTest()}Test. <br>
	 * GIVEN: <br>
	 * WHEN: check on the BIKE Parking spot availability for Parking<br>
	 * THEN: <verify availability status</b><br>
	 * <b>Test Condition <i>PASSED</i>: </b>actual expected spots match
	 * <code><b>TRUE</b></code> <br>
	 * <b>Test Condition <i>FAILED</i>: </b>actual expected spots not match
	 * <code><b>FALSE</b></code> availability <code><b>FALSE</b></code>
	 * @throws SQLException 
	 */
	@DisplayName("BIKE Parking Availability of Next parking spot ")
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

	/**
	 * {@link #testParkingSpotAvailabuilityFull()} <br>
	 * GIVEN: <br>
	 * WHEN: checking for availability of parking is full<br>
	 * THEN: parking spot <b>processing is Full - complete</b><br>
	 * <b>Test Condition <i>PASSED</i>: </b>assertThat error message displayed
	 * <code><b>TRUE</b></code> <br>
	 * <b>Test Condition <i>FAILED</i>: </b>assertThat error message displayed
	 * <code><b>FALSE</b></code>
	 * @throws SQLException 
	 */
	@DisplayName("Parking Service Testing - Parking Full error messsage ")
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

	/**
	 * {@link #testErrorMessageInvalidVehicleTypeOnThrowIllegalException()} <br>
	 * GIVEN: input vehicle type value<br>
	 * WHEN: executing parking verification<br>
	 * THEN: parking service <b>Throws Exception</b><br>
	 * <b>Test Condition <i>PASSED</i>: </b> displays error message
	 * <code><b>TRUE</b></code> <br>
	 * <b>Test Condition <i>FAILED</i>: </b> do not display error message
	 * <code><b>FALSE</b></code>
	 * @throws SQLException 
	 */
	@DisplayName("Error parsing user input - availability Parking Spot")
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

	/**
	 * {@link #testErrorMessageInvalidVehicleTypeOnThrowIllegalException()} <br>
	 * GIVEN: input vehicle type value<br>
	 * WHEN: executing parking verification<br>
	 * THEN: parking service <b>Throws Exception</b><br>
	 * <b>Test Condition <i>PASSED</i>: </b> displays error message
	 * <code><b>TRUE</b></code> <br>
	 * <b>Test Condition <i>FAILED</i>: </b> do not display error message
	 * <code><b>FALSE</b></code>
	 * @throws SQLException 
	 */
	@DisplayName("IllegalArgumentException: display message" + " Vehicle type - Invalid Input")
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

	/**
	 * {@link #testErrorOnWrongVehicleType()} <br>
	 * GIVEN: input non reliant value<br>
	 * WHEN: executing parking verification<br>
	 * THEN: parking service <b>processing fails with error</b><br>
	 * <b>Test Condition <i>PASSED</i>: </b> assertThrows error
	 * <code><b>TRUE</b></code> <br>
	 * <b>Test Condition <i>FAILED</i>: </b> assertThrows no error
	 * <code><b>FALSE</b></code>
	 */
	@DisplayName("IllegalArgumentException thrown for invalid input")
	@Test
	void testErrorOnWrongVehicleType() {

		// GIVEN
		when(inputReaderUtil.readSelection()).thenReturn(4);

		// WHEN
		parkingService = new ParkingService(inputReaderUtil, parkingSpotDAO, ticketDAO);

		// THEN
		assertThrows(IllegalArgumentException.class, () -> parkingService.getVehicleType());
	}

	/**
	 * {@link #testForVehicleTypeIsBike()} <br>
	 * GIVEN: input vehicle type for Bike<br>
	 * WHEN: executing parking service<br>
	 * THEN: identifies <b>match for entry value vehicle type</b><br>
	 * <b>Test Condition <i>PASSED</i>: </b> assertEquals = type Bike
	 * <code><b>TRUE</b></code> <br>
	 * <b>Test Condition <i>FAILED</i>: </b> assertEquals != type Bike
	 * <code><b>FALSE</b></code>
	 */
	@Test
	@DisplayName("Verify and confirm vehicle type bike")
	void testForVehicleTypeIsBike() {
		//
		// GIVEN
		when(inputReaderUtil.readSelection()).thenReturn(2);

		// WHEN
		parkingService = new ParkingService(inputReaderUtil, parkingSpotDAO, ticketDAO);

		// THEN
		assertEquals(ParkingType.BIKE, parkingService.getVehicleType());
	}

	/**
	 * {@link #testForVehicleTypeIsCar()} <br>
	 * GIVEN: input vehicle type fore Car<br>
	 * WHEN: executing parking service<br>
	 * THEN: identifies <b>match for entry value vehicle type</b><br>
	 * <b>Test Condition <i>PASSED</i>: </b> assertEquals = type Car
	 * <code><b>TRUE</b></code> <br>
	 * <b>Test Condition <i>FAILED</i>: </b> assertEquals != type Car
	 * <code><b>FALSE</b></code>
	 */
	@Test
	@DisplayName("Verify and confirm vehicle type bike")
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
	@DisplayName("Test on Screen display message request to pay fare amount")
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

	/**
	 * {@link #testForMessageTextDisplayForTicketUpdateError()} <br>
	 * GIVEN: exiting process<br>
	 * WHEN: executing parking service for fare calculation<br>
	 * THEN: identifies <b>ticket update error</b><br>
	 * <b>Test Condition <i>PASSED</i>: </b> do not display the error message
	 * <code><b>TRUE</b></code> <br>
	 * <b>Test Condition <i>FAILED</i>: </b> do not display the error message
	 * <code><b>FALSE</b></code>
	 */
	@Test
	@DisplayName("Test on Screen display error message on Ticket update")
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

	/**
	 * {@link #testForMessageTextDisplayOnDiscountConfirmation()} <br>
	 * GIVEN: exiting process<br>
	 * WHEN: executing parking service for fare calculation<br>
	 * THEN: confirms <b>discount eligibility</b><br>
	 * <b>Test Condition <i>PASSED</i>: </b> display discount confirmation message
	 * <code><b>TRUE</b></code> <br>
	 * <b>Test Condition <i>FAILED</i>: </b> display discount confirmation message
	 * <code><b>FALSE</b></code>
	 */
	@Test
	@DisplayName("Test for display message confirm 5% discount")
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

	/**
	 * {@link #testForMessageTextDisplayWrongInputVehcileType()} <br>
	 * GIVEN: exiting process<br>
	 * WHEN: retrieving input vehicle type<br>
	 * THEN: identifies <b>error input</b><br>
	 * <b>Test Condition <i>PASSED</i>: </b> display invalid input error
	 * <code><b>TRUE</b></code> <br>
	 * <b>Test Condition <i>FAILED</i>: </b>no display invalid input error
	 * <code><b>FALSE</b></code>
	 */
	@Test
	@DisplayName("Test for display message invalid Vehcile type input")
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

	/**
	 * {@link #testErrorMessageInvalidVehicleTypeOnThrowException()} <br>
	 * GIVEN: input vehicle type value<br>
	 * WHEN: executing parking verification<br>
	 * THEN: parking service <b>Throws Exception</b><br>
	 * <b>Test Condition <i>PASSED</i>: </b> displays error message
	 * <code><b>TRUE</b></code> <br>
	 * <b>Test Condition <i>FAILED</i>: </b> do not display error message
	 * <code><b>FALSE</b></code>
	 * @throws SQLException 
	 */
	@DisplayName("IllegalArgumentException thrown display error message")
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

	/**
	 * {@link #processExitingVehicleTest()} Integration Test <br>
	 * GIVEN: <br>
	 * WHEN: processing of Vehicle Exit Process<br>
	 * THEN: Parking <b>availability status updated</b><br>
	 * <b>Test Condition <i>PASSED</i>: </b>verify updateParking
	 * <code><b>TRUE</b></code> <br>
	 * <b>Test Condition <i>FAILED</i>: </b>verify updateParking
	 * <code><b>FALSE</b></code>
	 * @throws SQLException 
	 */
	@DisplayName("Parking Service Testing - Vehicle Exit Process ")
	@Test
	void processExitingVehicleTest() throws SQLException {
		// GIVEN

		// WHEN
		parkingService.processExitingVehicle();

		// THEN
		verify(parkingSpotDAO, Mockito.times(1)).updateParking(any(ParkingSpot.class));
	}

	/**
	 * {@link #testParkingLotExit()} Integration Test <br>
	 * GIVEN: <br>
	 * WHEN: processing of the parking lot<br>
	 * THEN: parking spot <b>processing exiting vehicle process</b><br>
	 * <b>Test Condition <i>PASSED</i>: </b>assertNull with getOutime
	 * <code><b>TRUE</b></code> <br>
	 * <b>Test Condition <i>FAILED</i>: </b>assertNull with getOutime
	 * <code><b>FALSE</b></code>
	 */
	@DisplayName("Parking Service Testing - Parking Lot Status on Exit ")
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

	@DisplayName("Test - Parking Service - Parking Exit Time is not Null")
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

	@DisplayName("Test - Parking Service - Parking Exit Ticket Is Issued Properly")
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
	@DisplayName("Test - Display Error Update Ticket Information")
	void testDisplayErrorUpdateTicketInformation() throws Exception {

		// GIVEN
		String outputScreen = null;
		byteArrayOutputStream = new ByteArrayOutputStream();
		System.setOut(new PrintStream(byteArrayOutputStream));

		// WHEN
		parkingService.displayErrorUpdateTicketInformation();
		outputScreen = byteArrayOutputStream.toString("UTF-8");
		String out = null;

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
