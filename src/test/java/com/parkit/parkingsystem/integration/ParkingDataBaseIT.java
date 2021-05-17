package com.parkit.parkingsystem.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import com.parkit.parkingsystem.ParkingServiceTest;
import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.dao.ParkingSpotDAO;
import com.parkit.parkingsystem.dao.TicketDAO;
import com.parkit.parkingsystem.integration.config.DataBaseTestConfig;
import com.parkit.parkingsystem.integration.service.DataBasePrepareService;
import com.parkit.parkingsystem.model.ParkingSpot;
import com.parkit.parkingsystem.model.Ticket;
import com.parkit.parkingsystem.service.ParkingService;
import com.parkit.parkingsystem.util.InputReaderUtil;

/**
 * Class {@link ParkingServiceTest} - Performs Integration Test on Parking
 * Services for customer of ParkIt Class Tested: {@link ParkingService}
 * 
 * @package - com.parkit.parkingsystem
 * @project - P4 - parking system - ParkIt
 * @see <b>Tests:</b><br>
 *      {@link #testParkingLotExitIT()}: Parking Database Testing - Parking Lot
 *      Status on Exit<br>
 *      {@link #testParkingACar()}}: Parking Database Testing - Availability of
 *      alternate parking spot <br>
 * 
 * @author Senthil
 */
@DisplayName("Vehicle Parking Database - Testing ")
@ExtendWith(MockitoExtension.class)
public class ParkingDataBaseIT {

	private static DataBaseTestConfig dataBaseTestConfig = new DataBaseTestConfig();
	@Spy
	private static ParkingSpotDAO parkingSpotDAO;
	@Spy
	private static TicketDAO ticketDAO;
	private static DataBasePrepareService dataBasePrepareService;

	@Mock
	private static InputReaderUtil inputReaderUtil;

	@BeforeAll
	private static void setUp() throws Exception {
		parkingSpotDAO = new ParkingSpotDAO();
		parkingSpotDAO.dataBaseConfig = dataBaseTestConfig;
		ticketDAO = new TicketDAO();
		ticketDAO.dataBaseConfig = dataBaseTestConfig;
		dataBasePrepareService = new DataBasePrepareService();
	}

	@BeforeEach
	private void setUpPerTest() throws Exception {
		when(inputReaderUtil.readSelection()).thenReturn(1);
		when(inputReaderUtil.readVehicleRegistrationNumber()).thenReturn("ABCDEF");
		dataBasePrepareService.clearDBEntries();
	}

	@AfterAll
	private static void tearDown() {

	}

	/**
	 * {@link #testParkingACar()} Integration Test <br>
	 * GIVEN: <br>
	 * WHEN: check on the alternate Parking spot availability for Parking<br>
	 * THEN: <b>availability status</b><br>
	 * <b>Test Condition <i>PASSED</i>: </b>verify alternate available parking spot
	 * are not same <code><b>TRUE</b></code> <br>
	 * <b>Test Condition <i>FAILED</i>: </b>verify alternate available parking spot
	 * are not same <code><b>FALSE</b></code>
	 */
	@DisplayName("Parking Database Testing - Availability of alternate parking spot ")
	@Test
	public void testParkingACar() {

		// GIVEN
		int firstAlternateParkingSlot = parkingSpotDAO.getNextAvailableSpot(ParkingType.CAR);
		ParkingService parkingService = new ParkingService(inputReaderUtil, parkingSpotDAO, ticketDAO);

		// WHEN
		parkingService.processIncomingVehicle();

		// *******************TASK COMPLETED*****************************************
		// TODO: check that a ticket is actually saved in DB and Parking table is
		// updated with availability
		// *******************TASK COMPLETED*****************************************

		Ticket ticket = ticketDAO.getTicket("ABCDEF");

		// THEN
		assertNotNull(ticket);
		verify(parkingSpotDAO, Mockito.times(1)).updateParking(any(ParkingSpot.class));
		assertFalse(ticket.getParkingSpot().isAvailable());

		// GIVEN WHEN
		int nextAlternateParkingSlot = parkingSpotDAO.getNextAvailableSpot(ParkingType.CAR);

		// THEN
		assertNotEquals(firstAlternateParkingSlot, nextAlternateParkingSlot, "Result: Both spot not similar");
	}

	/**
	 * {@link #testParkingLotExitIT()} Integration Test <br>
	 * GIVEN: <br>
	 * WHEN: processing of the parking Lot<br>
	 * THEN: parking spot <b>processing exiting vehicle process</b><br>
	 * <b>Test Condition <i>PASSED</i>: </b>assertNull with getOutime
	 * <code><b>TRUE</b></code> <br>
	 * <b>Test Condition <i>FAILED</i>: </b>assertNull with getOutime
	 * <code><b>FALSE</b></code>
	 */
	@DisplayName("Parking Service Testing - Parking Lot Status on Exit ")
	@Test
	public void testParkingLotExitIT() throws InterruptedException {

		// GIVEN
		testParkingACar();
		ParkingService parkingService = new ParkingService(inputReaderUtil, parkingSpotDAO, ticketDAO);

		// *******************TASK COMPLETED*****************************************
		// TODO: check that the fare generated and out time are populated correctly in
		// the database
		// *******************TASK COMPLETED*****************************************

		Ticket ticket = ticketDAO.getTicket("ABCDEF");

		// THEN
		assertNull(ticket.getOutTime());
		assertEquals(ticket.getPrice(), 0.0);

		// GIVEN
		TimeUnit.SECONDS.sleep(1);

		// WHEN
		parkingService.processExitingVehicle();

		// THEN
		assertNotNull(ticket, "Return: Ticket issued");

	}

}
