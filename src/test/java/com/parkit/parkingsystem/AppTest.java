package com.parkit.parkingsystem;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

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
		App app = new App();
		assertTrue(app.equals(app));
	}

}
