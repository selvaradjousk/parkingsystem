package com.parkit.parkingsystem.integration.service;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.NoSuchElementException;

import org.junit.Assert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.parkit.parkingsystem.service.InteractiveShell;

class InteractiveShellTest {

	
	InteractiveShell interactiveShell;
	
    @DisplayName("testing the Message and Menu displayed")
	@Test 
    public void loadInterfaceTest() throws IOException{
	interactiveShell = new InteractiveShell();
	
	// GIVEN
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    System.setOut(new PrintStream(byteArrayOutputStream));
	
	// WHEN
    String input = "3\n";
    InputStream inputStream = new ByteArrayInputStream(input.getBytes());
    System.setIn(inputStream);
    
    InteractiveShell.loadInterface();	
    String outputScreen = null;
    outputScreen = byteArrayOutputStream.toString("UTF-8");
	// THEN
    Assert.assertThat(outputScreen, containsString("Please select an option. Simply enter the number to choose an action"));
    Assert.assertThat(outputScreen, containsString("1 New Vehicle Entering - Allocate Parking Space"));
    Assert.assertThat(outputScreen, containsString("3 Shutdown System"));
    assertNotEquals(outputScreen, containsString("1 Please select an option. Simply enter the number to choose an action"));
    assertNotEquals(outputScreen, containsString("1 Shutdown System"));
    assertNotEquals(outputScreen, containsString("1 Bike"));
    Assert.assertThat(outputScreen, containsString("2 Vehicle Exiting - Generate Ticket Price"));
	
    }
    
//    @DisplayName("testing the Exception for wrong menu choice")
//	@Test 
//    public void loadInterfaceUnsupportOptionTest() throws IOException{
//	interactiveShell = new InteractiveShell();
//	
//	// GIVEN
//    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//    System.setOut(new PrintStream(byteArrayOutputStream));
//	
//	// WHEN
//    String input = "4\n";
//    InputStream inputStream = new ByteArrayInputStream(input.getBytes());
//    System.setIn(inputStream);
////    assertThrows(NoSuchElementException.class, () -> System.setIn(inputStream));// WHEN
//
//   	
//    String outputScreen = null;
//    assertThrows(NoSuchElementException.class, () -> byteArrayOutputStream.toString("UTF-8"));// WHEN  
//    outputScreen = byteArrayOutputStream.toString("UTF-8");
//    assertThrows(NoSuchElementException.class, () -> byteArrayOutputStream.toString("UTF-8"));// WHEN  
//    assertThrows(NoSuchElementException.class, () -> InteractiveShell.loadInterface());// WHEN
//    assertNotEquals(outputScreen, containsString("3 Shutdown System"));
	// THEN

    
//    Assert.assertThat(outputScreen, containsString("Unsupported option. Please enter a number corresponding to the provided menu"));

//    Assert.assertThat(outputScreen, containsString("1 New Vehicle Entering - Allocate Parking Space"));
//    Assert.assertThat(outputScreen, containsString("3 Shutdown System"));
//    assertNotEquals(outputScreen, containsString("1 Please select an option. Simply enter the number to choose an action"));
//    assertNotEquals(outputScreen, containsString("1 Shutdown System"));
//    assertNotEquals(outputScreen, containsString("1 Bike"));
//    Assert.assertThat(outputScreen, containsString("2 Vehicle Exiting - Generate Ticket Price"));
	
//    }
	

}
