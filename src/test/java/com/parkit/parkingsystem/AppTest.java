package com.parkit.parkingsystem;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class AppTest {

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
