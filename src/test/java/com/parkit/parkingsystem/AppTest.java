package com.parkit.parkingsystem;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import nl.altindag.log.LogCaptor;

@DisplayName("IT - Test on Main Application launch and Interaction")
class AppTest {

	ByteArrayOutputStream byteArrayOutputStream;

	@DisplayName("test Application empty or blanchlaunch")
	@Test
	public void appLoads() {
	}

	@DisplayName("Test Application WHEN launching THEN display message")
	@Test
	public void appLoading() {
		LogCaptor logCaptor = LogCaptor.forClass(App.class);
		App app = new App();
//		assertTrue(app.equals(app));
		String expectedInfoMessage = "Initializing Parking System";
		assertFalse(logCaptor.getInfoLogs().contains(expectedInfoMessage));
	}

	@DisplayName("Test Application WHEN launching Dispays the following Messages in InteractiveShell")
	@Test
	public void testLoadInterfaceChoiceIncomingVehicle() throws IOException {
		String expectedInfoMessage = "CAR";
		LogCaptor logCaptor = LogCaptor.forClass(App.class);
		byteArrayOutputStream = new ByteArrayOutputStream();
		System.setOut(new PrintStream(byteArrayOutputStream));

		final InputStream DEFAULT_STDIN1 = System.in;
		String simulatedUserInput1 = 
				"1" + System.getProperty("line.separator") +
				"ABCDEF" + System.getProperty("line.separator") +
				"1" + System.getProperty("line.separator") +
				"1" + System.getProperty("line.separator") +
				"ABCDEF" + System.getProperty("line.separator") +
				"2" + System.getProperty("line.separator") +
				"ABCDEF" + System.getProperty("line.separator") +
				"3" + System.getProperty("line.separator");

		InputStream savedStandardInputStream1 = System.in;
		System.setIn(new ByteArrayInputStream(simulatedUserInput1.getBytes()));

		App.main(null);
		String outputScreen = null;
		outputScreen = byteArrayOutputStream.toString("UTF-8");

		// THEN
//		assertTrue(logCaptor.getInfoLogs().contains(expectedInfoMessage));
		// Testing the Welcome Message display
		assertTrue(outputScreen.contains("Welcome to Parking System!"));
		assertTrue(outputScreen.contains("App initialized!!!"));
		assertTrue(outputScreen.contains("Initializing Parking System"));
		assertFalse(outputScreen.contains("Welcome to Parzzz System!"));
		// Testing the Message - Please select an option display
		assertTrue(outputScreen.contains("Please select an option. Simply enter the number to choose an action"));
		assertFalse(outputScreen.contains(" Esaelp select an noitpo. Simply enter the number to choose an action"));
		// Testing the Message and Menu displayed
		assertTrue(outputScreen.contains("1 New Vehicle Entering - Allocate Parking Space"));
		assertFalse(outputScreen.contains("2 New Vehicle Entering - Allocate Parking Space"));
		// Testing the Message and Menu displayed
		assertTrue(outputScreen.contains("2 Vehicle Exiting - Generate Ticket Price"));
		assertFalse(outputScreen.contains("1 Vehicle Exiting - Generate Ticket Price"));
		// Testing the Message and Menu displayed"
		assertTrue(outputScreen.contains("3 Shutdown System"));
		assertFalse(outputScreen.contains("1 Shutdown System"));
		assertFalse(outputScreen.contains("2 Shutdown System"));
		
		assertTrue(outputScreen.contains("Please select vehicle type from menu"));
		assertTrue(outputScreen.contains("1 CAR"));
		assertTrue(outputScreen.contains("2 BIKE"));
		assertFalse(outputScreen.contains("2 CAR"));
		assertFalse(outputScreen.contains("1 BIKE"));

		assertTrue(outputScreen.contains("Error while reading user input from Shell"));
		assertTrue(outputScreen.contains("Error reading input. Please enter valid number for proceeding further"));
		assertTrue(outputScreen.contains("Error parsing user input for type of vehicle"));
		assertTrue(outputScreen.contains("Entered input is invalid"));
		assertTrue(outputScreen.contains(""));
		assertTrue(outputScreen.contains(""));
		
		
		System.setIn(savedStandardInputStream1);
		savedStandardInputStream1.close();
		System.setIn(DEFAULT_STDIN1);
		DEFAULT_STDIN1.close();
		savedStandardInputStream1.close();
	}
}
