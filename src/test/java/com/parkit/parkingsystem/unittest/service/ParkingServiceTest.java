package com.parkit.parkingsystem.unittest.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.math.RoundingMode;
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

/**
 * <b>Test Class: </b> {@link ParkingServiceTest} - Performs Integration Test on
 * Parking Services for customer of ParkIt<br>
 * <b>Class Tested:</b> {@link ParkingService}
 * 
 * @package - com.parkit.parkingsystem
 * @project - P4 - parking system - ParkIt
 * @see <b>Tests:</b><br>
 *      {@link #testParkingLotExit()}: Parking Service Testing - Parking Lot
 *      Status on Exit<br>
 *      {@link #processExitingVehicleTest()}: Parking Service Testing - Vehicle
 *      Exit Process Test <br>
 *      {@link #getNextParkingNumberIfAvailableTest()}: Parking Service Testing
 *      - Check availability of Next parking spot freely <br>
 *      {@link #processIncomingVehicleTest()}: Parking Service Testing - Test
 *      Incoming process of vehicle <br>
 * 
 * @author Senthil
 */
@DisplayName("Vehicle Parking Service - Testing ")
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class ParkingServiceTest {

	private static ParkingService parkingService;

	@Mock
	private static InputReaderUtil inputReaderUtil;
	@Mock
	private static ParkingSpotDAO parkingSpotDAO;
	@Mock
	private static TicketDAO ticketDAO;

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
			throw new RuntimeException("Failed to set up test mock objects");
		}
	}

	/**
	 * {@link #processIncomingVehicleTest()} Integration Test <br>
	 * GIVEN: <br>
	 * WHEN: processing of the incoming vehicle<br>
	 * THEN: parking spot alloted ticket saved and <b>availability status</b><br>
	 * <b>Test Condition <i>PASSED</i>: </b>verify saveTicket and updateParking
	 * <code><b>TRUE</b></code> <br>
	 * <b>Test Condition <i>FAILED</i>: </b>verify saveTicket and updateParking
	 * <code><b>FALSE</b></code>
	 */
	@DisplayName("Parking Service Testing - Incoming process of vehicle ")
	@Test
	public void processIncomingVehicleTest() {
		// GIVEN

		// WHEN
		parkingService.processIncomingVehicle();

		// THEN
		verify(ticketDAO, Mockito.times(1)).saveTicket(any(Ticket.class));
		verify(parkingSpotDAO, Mockito.times(1)).updateParking(any(ParkingSpot.class));
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
	 */
	@DisplayName("Parking Service Testing - Availability of Next parking spot ")
	@Test
	public void getNextParkingNumberIfAvailableTest() {
		// GIVEN
		// WHEN
		parkingService.getNextParkingNumberIfAvailable();

		// THEN
		verify(parkingSpotDAO, Mockito.times(1)).getNextAvailableSpot(any(ParkingType.class));
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
	 */
	@DisplayName("Parking Service Testing - Vehicle Exit Process ")
	@Test
	public void processExitingVehicleTest() {
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
	public void testParkingLotExit() throws Exception {
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
		BigDecimal bd = new BigDecimal(ticket.getPrice()).setScale(3, RoundingMode.HALF_UP);
		double ticketGetPrice = bd.doubleValue();
		assertNotNull(ticket.getOutTime(), "Return: null");
		assertNotNull(ticket, "Return null: No Ticket");
		assertNotEquals(ticket.getPrice(), null);
		assertNotEquals(ticket.getPrice(), 0);
		assertNotNull(ticket.getPrice());
		assertEquals(Fare.CAR_RATE_PER_HOUR, ticketGetPrice, "Return: defaule per hour price");
		assertTrue(ticket instanceof Ticket, "Return: No Ticket");
		assertTrue(ticketDAO.updateTicket(ticket), "Return: not updated");
//		System.out.println("*******************");
//		System.out.println("Vehichle " + ticket.getVehicleRegNumber() + " Entry-time:" + ticket.getInTime());
//		System.out.println("*******************");		
		ParkingSpot parkingSpot = ticket.getParkingSpot();
		int availableParkingSpotNumber = parkingSpotDAO.getNextAvailableSpot(ParkingType.CAR);
		assertNotNull(parkingSpot, "Return: parking spot null value");
		assertTrue(parkingSpot instanceof ParkingSpot, "Return: !=parking Spot");
		assertEquals(parkingSpot.getId(), availableParkingSpotNumber, "Return: availability-YES updated");
		assertTrue(parkingSpot.isAvailable(), "Return: availability-YES updated");

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
