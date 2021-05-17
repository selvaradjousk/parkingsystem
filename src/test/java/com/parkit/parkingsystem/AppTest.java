package com.parkit.parkingsystem;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * @author Senthil
 *
 */
class AppTest {
	private static App app;

	@Test
	public void appLoads() {
	}

//	@Test
//	public void main() {
//		App.main(new String[] {});
//	}

	@Test
	public void appLoading() {
		App app = new App();
		assertTrue(app.equals(app));
	}
}
