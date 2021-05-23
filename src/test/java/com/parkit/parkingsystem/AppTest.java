package com.parkit.parkingsystem;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import nl.altindag.log.LogCaptor;

/**
 * @author Senthil
 *
 */
class AppTest {

	/**
	 * Test appLoads.
	 */
	@Test
	public void appLoads() {
	}

	/**
	 * Test appLoading.
	 */
	@Test
	public void appLoading() {
		LogCaptor logCaptor = LogCaptor.forClass(App.class);
		App app = new App();
		assertTrue(app.equals(app));
		String expectedInfoMessage = "Initializing Parking System";
		assertFalse(logCaptor.getInfoLogs().contains(expectedInfoMessage));
	}
}
