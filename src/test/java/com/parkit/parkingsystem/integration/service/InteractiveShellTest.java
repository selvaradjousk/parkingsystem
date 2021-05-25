package com.parkit.parkingsystem.integration.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.StringWriter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.parkit.parkingsystem.App;
import com.parkit.parkingsystem.service.InteractiveShell;

import nl.altindag.log.LogCaptor;

class InteractiveShellTest {
//	private InteractiveShell interactiveShell;
//
//	ByteArrayOutputStream byteArrayOutputStream;
//
//	@BeforeEach
//	public void setUp() {
//		interactiveShell = new InteractiveShell();
//	}

//	@DisplayName("Testing the Message Dispays in InteractiveShell")
//	@Test
//	public void testLoadInterfaceWelcomeMessage() throws IOException {
//		// GIVEN
//		String expectedInfoMessage = "App initialized!!!";
//		LogCaptor logCaptor = LogCaptor.forClass(App.class);
//		byteArrayOutputStream = new ByteArrayOutputStream();
//		System.setOut(new PrintStream(byteArrayOutputStream));
//		String input = "3\n";
//		InputStream inputStream = new ByteArrayInputStream(input.getBytes());
//		System.setIn(inputStream);
//
//		// WHEN
//		InteractiveShell.loadInterface();
//		String outputScreen = null;
//		outputScreen = byteArrayOutputStream.toString("UTF-8");
//
//		// THEN
//		assertFalse(logCaptor.getInfoLogs().contains(expectedInfoMessage));
//		// Testing the Welcome Message display
//		assertTrue(outputScreen.contains("Welcome to Parking System!"));
//		assertFalse(outputScreen.contains("Welcome to Parzzz System!"));
//		// Testing the Message - Please select an option display
//		assertTrue(outputScreen.contains("Please select an option. Simply enter the number to choose an action"));
//		assertFalse(outputScreen.contains(" Esaelp select an noitpo. Simply enter the number to choose an action"));
//		// Testing the Message and Menu displayed
//		assertTrue(outputScreen.contains("1 New Vehicle Entering - Allocate Parking Space"));
//		assertFalse(outputScreen.contains("2 New Vehicle Entering - Allocate Parking Space"));
//		// Testing the Message and Menu displayed
//		assertTrue(outputScreen.contains("2 Vehicle Exiting - Generate Ticket Price"));
//		assertFalse(outputScreen.contains("1 Vehicle Exiting - Generate Ticket Price"));
//		// Testing the Message and Menu displayed"
//		assertTrue(outputScreen.contains("3 Shutdown System"));
//		assertFalse(outputScreen.contains("1 Shutdown System"));
//		assertFalse(outputScreen.contains("2 Shutdown System"));
//
//		byteArrayOutputStream.close();
//		inputStream.close();
//
//	}

//	@DisplayName("Testing the Message Dispays in InteractiveShell")
//	@Test
//	public void testLoadInterfaceChoiceIncomingVehicle() throws IOException {
//		final InputStream DEFAULT_STDIN = System.in;
//		String simulatedUserInput = 
//				"1" + System.getProperty("line.separator") + 
//				"1" + System.getProperty("line.separator") + 
//				"ABCDEF" + System.getProperty("line.separator")+ 
//				"3" + System.getProperty("line.separator");
//		InputStream savedStandardInputStream = System.in;
//		System.setIn(new ByteArrayInputStream(simulatedUserInput.getBytes()));
//		InteractiveShell.loadInterface();
//		System.setIn(savedStandardInputStream);
//		savedStandardInputStream.close();
//		System.setIn(DEFAULT_STDIN);
////		long minRunningMemory = (1024*1024);
////		Runtime runtime = Runtime.getRuntime();
////		if(runtime.freeMemory()<minRunningMemory)
////		 System.gc();
//		DEFAULT_STDIN.close();
//		savedStandardInputStream.close();
//		
//		
//	}
	
//	@DisplayName("Testing the Message Dispays in InteractiveShell")
//	@Test
//	public void testLoadInterfaceChoiceExitingVehicle() throws IOException {
//		final InputStream DEFAULT_STDIN1 = System.in;
//		String simulatedUserInput1 = 
//				"2" + System.getProperty("line.separator") + 
////				"1" + System.getProperty("line.separator") + 
//				"ABCDEF" + System.getProperty("line.separator")+ 
//				"3" + System.getProperty("line.separator");
//		InputStream savedStandardInputStream1 = System.in;
//		System.setIn(new ByteArrayInputStream(simulatedUserInput1.getBytes()));
//		InteractiveShell.loadInterface();
//		System.setIn(savedStandardInputStream1);
//		savedStandardInputStream1.close();
//		System.setIn(DEFAULT_STDIN1);
//		DEFAULT_STDIN1.close();
//		savedStandardInputStream1.close();
//	}
}
