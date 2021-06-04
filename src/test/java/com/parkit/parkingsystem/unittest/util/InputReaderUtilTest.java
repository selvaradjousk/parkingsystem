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

	@DisplayName("Test ReadSelection - REGULAR selection")
	@ParameterizedTest(name = "Tested Value: {0} on read return {0}")
	@ValueSource(strings = { "1", "2", "3" })
	void testReadSelectionRegularSelection(String arg) {

		// GIVEN - NumericalInput
		when(scanner.nextLine()).thenReturn(arg);

		// WHEN
		int selection = inputReaderUtil.readSelection();

		// THEN
		assertEquals(selection, Integer.parseInt(arg));
	}

	@DisplayName("Test ReadSelection - NEGATIVE values selection")
	@ParameterizedTest(name = "Tested Value: {0} on read return -1")
	@ValueSource(strings = { "-1", "-2", "-3", "-4", "-9" })
	void testReadSelectionNegativeValues(String arg) {

		// GIVEN
		when(scanner.nextLine()).thenReturn(arg);

		// WHEN
		int selection = inputReaderUtil.readSelection();

		// THEN
		assertEquals(selection, -1);
	}

	@DisplayName("Test ReadSelection - >3 values selection")
	@ParameterizedTest(name = "Tested Value: {0} on read return -1")
	@ValueSource(strings = { "4", "5", "9", "10", "100" })
	void testReadSelectionAboveValueThree(String arg) {

		// GIVEN
		when(scanner.nextLine()).thenReturn(arg);

		// WHEN
		int selection = inputReaderUtil.readSelection();

		// THEN
		assertEquals(selection, -1);
	}

	@DisplayName("Test ReadSelection - ALPHABET characters selection")
	@ParameterizedTest(name = "Tested Value: {0} on read return -1")
	@ValueSource(strings = { "a", "b", "c", "x", "z" })
	void testReadSelectionAlphabets(String arg) {

		// GIVEN
		when(scanner.nextLine()).thenReturn(arg);

		// WHEN
		int selection = inputReaderUtil.readSelection();

		// THEN
		assertEquals(selection, -1);
	}

	@DisplayName("Test ReadSelection - SPECIAL characters selection")
	@ParameterizedTest(name = "Tested Value: {0} on read return -1")
	@ValueSource(strings = { "&", "#", "!", "=", "~" })
	void testReadSelectionSpecialCharacters(String arg) {

		// GIVEN
		when(scanner.nextLine()).thenReturn(arg);

		// WHEN
		int selection = inputReaderUtil.readSelection();

		// THEN
		assertEquals(selection, -1);
	}

	@DisplayName("Test readVehicleRegistrationNumber - VALID registration number inputs")
	@ParameterizedTest(name = "Tested Value: {0} on read return {0}")
	@ValueSource(strings = { "AB123CD", "BC456DE", "123456", "AB1234YZ", "XY9874OP" })
	void testReadVehicleRegistrationNumberValidType(String arg) throws Exception {

		// GIVEN
		when(scanner.nextLine()).thenReturn(arg);

		// WHEN
		String regNumber = inputReaderUtil.readVehicleRegistrationNumber();

		// THEN
		assertEquals(regNumber, arg);
	}

	@DisplayName("Test readVehicleRegistrationNumber - INVALID registration number input throws exception")
	@ParameterizedTest(name = "Tested Value: {0} on read throws exception")
	@ValueSource(strings = { "WOOOO WOOOO", "BC4sdfs56DE", "123sdfq456", "HAAAAHHAAAAA", "XY9IUYT&874OP" })
	void testReadVehicleRegistrationNumberInvalid(String arg) {

		// GIVEN
		when(scanner.nextLine()).thenReturn(arg);

		// THEN
		assertThrows(IllegalArgumentException.class, () -> inputReaderUtil.readVehicleRegistrationNumber()); // WHEN
	}

	@DisplayName("Test readVehicleRegistrationNumber -EMPTY input value throws exception")
	@ParameterizedTest(name = "Tested Value: {0} on read throws exception")
	@ValueSource(strings = { "", " ", "   ", "     ", "     ", "             " })
	void testReadVehicleRegistrationNumberEmpty(String arg) {

		// GIVEN
		when(scanner.nextLine()).thenReturn(arg);

		// THEN
		assertThrows(IllegalArgumentException.class, () -> inputReaderUtil.readVehicleRegistrationNumber()); // WHEN
	}

	@Test
	@DisplayName("Test readVehicleRegistrationNumber - null input value: {0} throws exception")
	void testReadVehicleRegistrationNumberNull() {

		// GIVEN
		when(scanner.nextLine()).thenReturn(null);

		// THEN
		assertThrows(IllegalArgumentException.class, () -> inputReaderUtil.readVehicleRegistrationNumber()); // WHEN
	}
	

	@Test
	@DisplayName("Test read Vehicle Registration Number Illegal Argument Exception Message")
	void testReadVehicleRegistrationNumberIllegalArgumentExceptionMessageTest() {
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
}
