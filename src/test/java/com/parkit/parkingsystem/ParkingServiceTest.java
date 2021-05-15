package com.parkit.parkingsystem;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.dao.ParkingSpotDAO;
import com.parkit.parkingsystem.dao.TicketDAO;
import com.parkit.parkingsystem.model.ParkingSpot;
import com.parkit.parkingsystem.model.Ticket;
import com.parkit.parkingsystem.service.FareCalculatorService;
import com.parkit.parkingsystem.service.ParkingService;
import com.parkit.parkingsystem.util.InputReaderUtil;

/**
 * Class {@link ParkingServiceTest} - Performs Unit Testing on Parking
 * Services for customer of ParkIt Class Tested:
 * {@link ParkingService}
 * 
 * @package - com.parkit.parkingsystem
 * @project - P3 - parking system - ParkIt
 * @see Tests: {@link #processIncomingVehicleTest()}
 * 
 * @author Senthil
 */
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
			when(parkingSpotDAO.getNextAvailableSlot(any(ParkingType.class))).thenReturn(1);

			parkingService = new ParkingService(inputReaderUtil, parkingSpotDAO, ticketDAO);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Failed to set up test mock objects");
		}
	}


	/**
	 * {@link #processIncomingVehicleTest()} Unit Test <br>
	 * GIVEN: <br>
	 * WHEN: processing of the parking spot availability <br>
	 * THEN: parking spot alloted ticket saved and <b>availability status</b><br>
	 * <b>Test Condition <i>PASSED</i>: </b>verify saveTicket and updateParking <code><b>TRUE</b></code>
	 * <br>
	 * <b>Test Condition <i>FAILED</i>: </b>verify saveTicket and updateParking <code><b>FALSE</b></code>
	 */
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
	 * {@link #getNextParkingNumberIfAvailableTest()} Unit Test <br> 
	 * GIVEN: <br>
	 * WHEN:  check on the Parking spot availability for Parking<br>
	 * THEN:  <b>availability status</b><br>
	 * <b>Test Condition <i>PASSED</i>: </b>verify status available <code><b>TRUE</b></code>
	 * <br>
	 * <b>Test Condition <i>FAILED</i>: </b>verify status available <code><b>FALSE</b></code>
	 */
	@Test
	public void getNextParkingNumberIfAvailableTest() {
		// GIVEN

		// WHEN
		parkingService.getNextParkingNumberIfAvailable();

		// THEN
		verify(parkingSpotDAO, Mockito.times(1)).getNextAvailableSlot(any(ParkingType.class));
	}


	/**
	 * {@link #processExitingVehicleTest()} Unit Test <br>
	 * GIVEN: <br>
	 * WHEN: processing of the parking spot status<br>
	 * THEN: parking spot <b>availability status updated</b><br>
	 * <b>Test Condition <i>PASSED</i>: </b>verify updateParking <code><b>TRUE</b></code>
	 * <br>
	 * <b>Test Condition <i>FAILED</i>: </b>verify updateParking <code><b>FALSE</b></code>
	 */
	@Test
	public void processExitingVehicleTest() {
		// GIVEN

		// WHEN
		parkingService.processExitingVehicle();
		
		// THEN
		verify(parkingSpotDAO, Mockito.times(1)).updateParking(any(ParkingSpot.class));
	}
}
