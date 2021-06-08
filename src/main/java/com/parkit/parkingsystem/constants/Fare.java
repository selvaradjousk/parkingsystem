package com.parkit.parkingsystem.constants;

/**
 * Class: {@link Fare} - DB constants on Fare.<br>
 * <b>Project: </b> P3 - parking system - ParkIt<br>
 *
 * @see variables: {@link #BIKE_RATE_PER_HOUR},
 *  {@link #CAR_RATE_PER_HOUR}
 *
 * @author Senthil
 */
public final class Fare {

	/**
	 * Constructor class for Fare.
	 */
	private Fare() {
	}

	/**
	 * Parking rate per hour for bike.
	 */
	public static final double BIKE_RATE_PER_HOUR = 1.0;

	/**
	 * Parking rate per hour for car.
	 */
	public static final double CAR_RATE_PER_HOUR = 1.5;

	  /**
	   * Half an hour of free parking time.
	   */
	  public static final double THIRTY_MIN_FREE_PARKING_TIME = 0.5;

	  /**
	   * Discount rate for recurrent user.
	   */
	  public static final double DISCOUNT_RATE = 0.05;

	  /**
	   * Factor to convert millisecond to hour (60.0 * 60.0 * 1000).
	   */
	  public static final double MILLISECONDS_HOURS_CONVERSION_FACTOR
	  = 3600000;
}
