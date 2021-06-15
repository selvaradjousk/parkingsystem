package com.parkit.parkingsystem.unittest.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.io.ByteArrayOutputStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.parkit.parkingsystem.util.InputReaderUtil;
import com.parkit.parkingsystem.util.ScannerWrapper;

@DisplayName("Test Input Reader Util class")
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

	@DisplayName("Test input read selection WHEN regular selection THEN test pass")
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

	@DisplayName("Test input read selection WHEN negative values selection THEN should return -1")
	@ParameterizedTest(name = "Tested Value: {0} on read return -1")
	@ValueSource(strings = { "-1", "-2", "-9" })
	void testReadSelectionNegativeValues(String arg) {

		// GIVEN
		when(scanner.nextLine()).thenReturn(arg);

		// WHEN
		int selection = inputReaderUtil.readSelection();

		// THEN
		assertEquals(selection, -1);
	}

	@DisplayName("Test input read selection WHEN more than value 3 THEN should return -1")
	@ParameterizedTest(name = "Tested Value: {0} on read return -1")
	@ValueSource(strings = { "4", "5", "100" })
	void testReadSelectionAboveValueThree(String arg) {

		// GIVEN
		when(scanner.nextLine()).thenReturn(arg);

		// WHEN
		int selection = inputReaderUtil.readSelection();

		// THEN
		assertEquals(selection, -1);
	}

	@DisplayName("Test input read selection WHEN alphabet characters selection THEN should return -1")
	@ParameterizedTest(name = "Tested Value: {0} on read return -1")
	@ValueSource(strings = { "a", "x", "z" })
	void testReadSelectionAlphabets(String arg) {

		// GIVEN
		when(scanner.nextLine()).thenReturn(arg);

		// WHEN
		int selection = inputReaderUtil.readSelection();

		// THEN
		assertEquals(selection, -1);
	}

	@DisplayName("Test input read selection WHEN special characters selection")
	@ParameterizedTest(name = "Tested Value: {0} on read return -1")
	@ValueSource(strings = { "&", "#", "!", "~" })
	void testReadSelectionSpecialCharacters(String arg) {

		// GIVEN
		when(scanner.nextLine()).thenReturn(arg);

		// WHEN
		int selection = inputReaderUtil.readSelection();

		// THEN
		assertEquals(selection, -1);
	}

	@DisplayName("Test input read vehicle registration number WHEN valid registration number inputs THEN returns TRUE")
	@ParameterizedTest(name = "Tested Value: {0} on read return {0}")
	@ValueSource(strings = { "AB123CD", "123456", "XY9874OP" })
	void testReadVehicleRegistrationNumberValidType(String arg) throws Exception {

		// GIVEN
		when(scanner.nextLine()).thenReturn(arg);

		// WHEN
		String regNumber = inputReaderUtil.readVehicleRegistrationNumber();

		// THEN
		assertEquals(regNumber, arg);
	}

	@DisplayName("Test input read vehicle registration number WHEN invalid registration number inputs THEN throws Illegal Argument Exception")
	@ParameterizedTest(name = "Tested Value: {0} on read throws exception")
	@ValueSource(strings = { "BC4sdfs56DE", "123sdfq456", "XY9IUYT&874OP" })
	void testReadVehicleRegistrationNumberInvalid(String arg) {

		// GIVEN
		when(scanner.nextLine()).thenReturn(arg);

		// THEN
		assertThrows(IllegalArgumentException.class, () -> inputReaderUtil.readVehicleRegistrationNumber()); // WHEN
	}

	@DisplayName("Test input read vehicle registration number WHEN empty input value THEN throws Illegal Argument Exception")
	@ParameterizedTest(name = "Tested Value: {0} on read throws exception")
	@ValueSource(strings = { "", " ", "             " })
	void testReadVehicleRegistrationNumberEmpty(String arg) {

		// GIVEN
		when(scanner.nextLine()).thenReturn(arg);

		// THEN
		assertThrows(IllegalArgumentException.class, () -> inputReaderUtil.readVehicleRegistrationNumber()); // WHEN
	}

	@Test
	@DisplayName("Test input read vehicle registration number WHEN null input value THEN throws Illegal Argument exception")
	void testReadVehicleRegistrationNumberNull() {

		// GIVEN
		when(scanner.nextLine()).thenReturn(null);

		// THEN
		assertThrows(IllegalArgumentException.class, () -> inputReaderUtil.readVehicleRegistrationNumber()); // WHEN
	}
	

	@Test
	@DisplayName("Test read Vehicle Registration Number WHEN Illegal Argument Exception THEN displays error Message")
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
