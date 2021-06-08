package com.parkit.parkingsystem.constants;

/**
 * Class: {@link DBConstants} - store DB constant values.<br>
 * <b>Project: </b> P3 - parking system - ParkIt<br>
 *
 * @see variables: {@link #GET_NEXT_PARKING_SPOT}, {@link #GET_TICKET},
 *      {@link #GET_VEHICLE_OCCURENCES}, {@link #SAVE_TICKET},
 *      {@link #UPDATE_PARKING_SPOT}, {@link #UPDATE_TICKET}
 *
 * @author Senthil
 */
public final class DBConstants {

	/**
	 * Private Constructor of DBConstants.
	 */
	private DBConstants() {
	}

	/**
	 * SQL query to get available parking spot.
	 */
	  public static final String GET_NEXT_PARKING_SPOT = "select "
		      + "min(PARKING_NUMBER) from parking where AVAILABLE = true and TYPE = ?";

	/**
	 * SQL query to update available parking spot.
	 */
		public static final String UPDATE_PARKING_SPOT = "update "
				 + "parking set available = ? where PARKING_NUMBER = ?";

	/**
	 * SQL query to save ticket.
	 */
		public static final String SAVE_TICKET = "insert into "
				 + "ticket(PARKING_NUMBER, VEHICLE_REG_NUMBER, PRICE, "
						+ "IN_TIME, OUT_TIME) values(?,?,?,?,?)";

	/**
	 * SQL query to update ticket.
	 */
		public static final String UPDATE_TICKET = "update ticket "
				 + "set PRICE=?, OUT_TIME=? where ID=?";

	/**
	 * SQL query to get ticket.
	 */
		public static final String GET_TICKET = "select t.PARKING_NUMBER, "
				+ " t.ID, t.PRICE, t.IN_TIME, t.OUT_TIME, p.TYPE from "
				+ "ticket t,parking p where p.parking_number = t.parking_number "
				+ " and t.VEHICLE_REG_NUMBER=? order by t.IN_TIME  limit 1";

	/**
	 * SQL query to get vehicle recurrence frequency.
	 */
		public static final String GET_VEHICLE_OCCURENCES = "select "
				+ "count(VEHICLE_REG_NUMBER) from ticket where VEHICLE_REG_NUMBER = ?";
}
