package com.parkit.parkingsystem.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.SQLException;
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

import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.dao.ParkingSpotDAO;
import com.parkit.parkingsystem.dao.TicketDAO;
import com.parkit.parkingsystem.integration.config.DataBaseTestConfig;
import com.parkit.parkingsystem.integration.service.DataBasePrepareService;
import com.parkit.parkingsystem.model.ParkingSpot;
import com.parkit.parkingsystem.model.Ticket;
import com.parkit.parkingsystem.service.ParkingService;
import com.parkit.parkingsystem.util.InputReaderUtil;

@DisplayName("IT - testing Vehicle Parking Database")
@ExtendWith(MockitoExtension.class)
public class ParkingDataBaseIT {

	private static DataBaseTestConfig dataBaseTestConfig = new DataBaseTestConfig();
	@Spy
	private static ParkingSpotDAO parkingSpotDAO;
	@Spy
	private static TicketDAO ticketDAO;
	private static DataBasePrepareService dataBasePrepareService;
    private ParkingService parkingService;

    @Mock
    private ParkingSpot parkingSpot;
	
	@Mock
	private static InputReaderUtil inputReaderUtil;

	@BeforeAll
	private static void setUp() throws Exception {
		parkingSpotDAO = new ParkingSpotDAO();
		parkingSpotDAO.setDataBaseConfig(dataBaseTestConfig);
		ticketDAO = new TicketDAO();
		ticketDAO.setDataBaseConfig(dataBaseTestConfig);
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

	@DisplayName("Test on Parking Database WHEN check parking a car THEN confirms parking done")
	@Test
	public void testParkingACar() throws SQLException {

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
	
	@DisplayName("Test on Parking Database WHEN check parking lot "
			+ "status on exit THEN return ticket issued")
	@Test
	public void testParkingLotExitIT() throws InterruptedException, SQLException {

		// GIVEN
		testParkingACar();
		ParkingService parkingService = new ParkingService(inputReaderUtil, parkingSpotDAO, ticketDAO);
		Ticket ticket = ticketDAO.getTicket("ABCDEF");

		// THEN
		assertNull(ticket.getOutTime());
		assertEquals(0.0, ticket.getPrice());

		// GIVEN
		TimeUnit.SECONDS.sleep(1);

		// WHEN
		parkingService.processExitingVehicle();

		// THEN
		assertNotNull(ticket, "Return: Ticket issued");

	}
	
    @Test
    @DisplayName("Test on Parking Database WHEN exiting vehicle THEN the fare generated and out time are populated correctly in the DB")
    public void testProcessExitingVehicleFareIsGeneratedAndOutTimePopulated() throws InterruptedException, SQLException{
    	ParkingService parkingService = new ParkingService(inputReaderUtil, parkingSpotDAO, ticketDAO);
    	parkingService.processIncomingVehicle();
        Thread.sleep(500);
        parkingService.processExitingVehicle();

		// *******************TASK COMPLETED*****************************************
		// TODO: check that the fare generated and out time are populated correctly in
		// the database
		// *******************TASK COMPLETED*****************************************
        Ticket ticket = ticketDAO.getTicket("ABCDEF");
        double fare = ticket.getPrice();

        // Checks fare is populated
        assertEquals(0, fare);
        assertNotNull(fare);
        // Checks out time is populated
        assertNotNull(ticket.getOutTime());
    }
    
    @Test
    @DisplayName("Test on Parking Database WHEN exiting vehicle invalid registration number THEN the fare not generated and out time are not populated correctly in the DB")
    public void testProcessExitingVehicleFareNotGeneratedAndOutTimePopulatedForInvalidRegistrationNumber() throws InterruptedException, SQLException{
    	ParkingService parkingService = new ParkingService(inputReaderUtil, parkingSpotDAO, ticketDAO);
    	parkingService.processIncomingVehicle();
        Thread.sleep(500);
        parkingService.processExitingVehicle();

		// *******************TASK COMPLETED*****************************************
		// TODO: check that the fare generated and out time are populated correctly in
		// the database
		// *******************TASK COMPLETED*****************************************
        Ticket ticket = ticketDAO.getTicket("ABCDEFSSSSS");
        assertThrows(NullPointerException.class, () -> ticket.getPrice()); // WHEN
    }
	
    @DisplayName("Test on Parking Database WHEN save Ticket and update "
    		+ "availability of parkingSpot THEN confirms database update")
    @Test
    public void testDbAvailabilityUpdatedAndTicketSaveCar() throws Exception {

    	// GIVEN
    	when(inputReaderUtil.readSelection()).thenReturn(1);
        when(inputReaderUtil.readVehicleRegistrationNumber()).thenReturn("ABCDEF");
        dataBasePrepareService.clearDBEntries();
        parkingService = new ParkingService(inputReaderUtil, parkingSpotDAO, ticketDAO);
        
        // WHEN
    	parkingService.processIncomingVehicle();

    	// THEN
        assertNotNull(ticketDAO.getTicket("ABCDEF"));
        assertTrue(parkingSpotDAO.getNextAvailableSpot(ParkingType.CAR) > 1);
        assertFalse(parkingSpot.isAvailable());
    }
   
    @DisplayName("Test on Parking Database WHEN save Ticket and update "
    		+ "availability of parkingSpot THEN confirms database update")
    @Test
    public void testDbAvailabilityUpdatedAndTicketSaveBike() throws Exception {

    	// GIVEN
    	when(inputReaderUtil.readSelection()).thenReturn(1);
        when(inputReaderUtil.readVehicleRegistrationNumber()).thenReturn("ABCDEF");
        dataBasePrepareService.clearDBEntries();
        parkingService = new ParkingService(inputReaderUtil, parkingSpotDAO, ticketDAO);
        
        // WHEN
    	parkingService.processIncomingVehicle();

    	// THEN
        assertNotNull(ticketDAO.getTicket("ABCDEF"));
        assertTrue(parkingSpotDAO.getNextAvailableSpot(ParkingType.BIKE) > 1);
        assertFalse(parkingSpot.isAvailable());
    }
    
	


}
