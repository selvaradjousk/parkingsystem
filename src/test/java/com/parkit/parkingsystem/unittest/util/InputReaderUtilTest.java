package com.parkit.parkingsystem.unittest.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.parkit.parkingsystem.util.InputReaderUtil;
import com.parkit.parkingsystem.util.ScannerWrapper;

@ExtendWith(MockitoExtension.class)
class InputReaderUtilTest {

	private InputReaderUtil inputReaderUtil;
	ByteArrayOutputStream byteArrayOutputStream;

	@Mock
	ScannerWrapper scanner;

	@BeforeEach
	public void setUp() {
		inputReaderUtil = new InputReaderUtil(scanner);
	}

	@Tag("ReadSelection")
	@DisplayName("Testing read REGULAR selection")
	@ParameterizedTest(name = "Tested Value: {0} on read return {0}")
	@ValueSource(strings = { "1", "2", "3" })
	public void readSelectionTest(String arg) {

		// GIVEN - NumericalInput
		when(scanner.nextLine()).thenReturn(arg);

		// WHEN
		int selection = inputReaderUtil.readSelection();

		// THEN
		assertEquals(selection, Integer.parseInt(arg));
	}

	@Tag("ReadSelection")
	@DisplayName("Testing read NEGATIVE values selection")
	@ParameterizedTest(name = "Tested Value: {0} on read return -1")
	@ValueSource(strings = { "-1", "-2", "-3", "-4", "-9" })
	public void readSelectionNegativeValues(String arg) {

		// GIVEN
		when(scanner.nextLine()).thenReturn(arg);

		// WHEN
		int selection = inputReaderUtil.readSelection();

		// THEN
		assertEquals(selection, -1);
	}

	@Tag("ReadSelection")
	@DisplayName("Testing read >3 values selection")
	@ParameterizedTest(name = "Tested Value: {0} on read return -1")
	@ValueSource(strings = { "4", "5", "9", "10", "100" })
	public void readSelectionAboveValueThree(String arg) {

		// GIVEN
		when(scanner.nextLine()).thenReturn(arg);

		// WHEN
		int selection = inputReaderUtil.readSelection();

		// THEN
		assertEquals(selection, -1);
	}

	@Tag("ReadSelection")
	@DisplayName("Testing read ALPHABET characters selection")
	@ParameterizedTest(name = "Tested Value: {0} on read return -1")
	@ValueSource(strings = { "a", "b", "c", "x", "z" })
	public void readSelectionAlphabets(String arg) {

		// GIVEN
		when(scanner.nextLine()).thenReturn(arg);

		// WHEN
		int selection = inputReaderUtil.readSelection();

		// THEN
		assertEquals(selection, -1);
	}

	@Tag("ReadSelection")
	@DisplayName("Testing read SPECIAL characters selection")
	@ParameterizedTest(name = "Tested Value: {0} on read return -1")
	@ValueSource(strings = { "&", "#", "!", "=", "~" })
	public void readSelectionSpecialCharacters(String arg) {

		// GIVEN
		when(scanner.nextLine()).thenReturn(arg);

		// WHEN
		int selection = inputReaderUtil.readSelection();

		// THEN
		assertEquals(selection, -1);
	}

	@Tag("ReadVehicleRegistrationNumber")
	@DisplayName("Testing VALID registration number inputs")
	@ParameterizedTest(name = "Tested Value: {0} on read return {0}")
	@ValueSource(strings = { "AB123CD", "BC456DE", "123456", "AB1234YZ", "XY9874OP" })
	public void readVehicleRegistrationNumberValidType(String arg) throws Exception {

		// GIVEN
		when(scanner.nextLine()).thenReturn(arg);

		// WHEN
		String regNumber = inputReaderUtil.readVehicleRegistrationNumber();

		// THEN
		assertEquals(regNumber, arg);
	}

	@Tag("ReadVehicleRegistrationNumber")
	@DisplayName("Testing read INVALID registration number input throws exception")
	@ParameterizedTest(name = "Tested Value: {0} on read throws exception")
	@ValueSource(strings = { "WOOOO WOOOO", "BC4sdfs56DE", "123sdfq456", "HAAAAHHAAAAA", "XY9IUYT&874OP" })
	public void readVehicleRegistrationNumberInvalid(String arg) {

		// GIVEN
		when(scanner.nextLine()).thenReturn(arg);

		// THEN
		assertThrows(IllegalArgumentException.class, () -> inputReaderUtil.readVehicleRegistrationNumber()); // WHEN
	}

	@Tag("ReadVehicleRegistrationNumber")
	@DisplayName("Tested read EMPTY input value throws exception")
	@ParameterizedTest(name = "Tested Value: {0} on read throws exception")
	@ValueSource(strings = { "", " ", "   ", "     ", "     ", "             " })
	public void readVehicleRegistrationNumberEmpty(String arg) {

		// GIVEN
		when(scanner.nextLine()).thenReturn(arg);

		// THEN
		assertThrows(IllegalArgumentException.class, () -> inputReaderUtil.readVehicleRegistrationNumber()); // WHEN
	}

	@Test
	@Tag("ReadVehicleRegistrationNumber")
	@DisplayName("Tested read null input value: {0} throws exception")
	public void readVehicleRegistrationNumberNull() {

		// GIVEN
		when(scanner.nextLine()).thenReturn(null);

		// THEN
		assertThrows(IllegalArgumentException.class, () -> inputReaderUtil.readVehicleRegistrationNumber()); // WHEN
	}

	@Test
	@Tag("DisplayMessageTest")
	@DisplayName("Tested Vehicle Registration Number Illegal Argument Exception Message")
	public void testReadVehicleRegistrationNumberIllegalArgumentExceptionMessageTest() {
		// GIVEN
		when(scanner.nextLine()).thenReturn(null);

		// WHEN
		try {
			inputReaderUtil.readVehicleRegistrationNumber();
		} catch (Exception e) {
			String ex = e.getMessage();
			// THEN
			assertTrue(ex.contains("Invalid input provided"));
			assertFalse(
					ex.contains("Error reading input. Please enter a valid string for vehicle registration number"));
		}
	}

	@Test
	@Tag("DisplayMessageTest")
	@DisplayName("Tested Vehicle Registration Number Illegal Argument Exception Message")
	public void testReadVehicleRegistrationNumberIExceptionMessageTest() throws Exception {
		// GIVEN
		String outputScreen = null;
		byteArrayOutputStream = new ByteArrayOutputStream();
		System.setOut(new PrintStream(byteArrayOutputStream));
		when(scanner.nextLine()).thenReturn(null);
		// WHEN
		try {
			inputReaderUtil.readVehicleRegistrationNumber();
			outputScreen = byteArrayOutputStream.toString("UTF-8");

		} catch (IllegalArgumentException e) {
			String ex = e.getMessage();
			assertFalse(ex.contains("Error reading input"));
			outputScreen = byteArrayOutputStream.toString("UTF-8");
			assertEquals("Error reading input. Please enter a valid string for vehicle registration number",
					outputScreen.toString().trim());
			assertNotEquals(outputScreen.toString().trim(), "Invalid input provided");
			assertNotEquals(ex, "Error reading input. Please enter a valid string for vehicle registration number");
			// THEN
			byteArrayOutputStream.close();
		}
		byteArrayOutputStream.close();
	}
}
