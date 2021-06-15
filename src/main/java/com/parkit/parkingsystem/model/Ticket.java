package com.parkit.parkingsystem.model;

import java.util.Date;
import java.util.Optional;

/**
 * Class: {@link Ticket} - Ticket Data Objects.<br>
 * <b>Project: </b> P3 - parking system - ParkIt<br>
 *
 * @author Senthil
 */
public class Ticket {

	  /**
	   * Ticket class constructor.
	   */
	  public Ticket() {
	  }

	/**
	 * Ticket Identifier.
	 */
	private int id;

	/**
	 * Parking Spot for the vehicle.
	 */
	private ParkingSpot parkingSpot;

	/**
	 * Vehicle registration number.
	 */
	private String vehicleRegNumber;

	/**
	 * Fare price of parking.
	 */
	private double price;

	/**
	 * Entry Time.
	 */
	private Date inTime;

	/**
	 * Exit Time.
	 */
	private Date outTime;

	/**
	 * Occurrence to represent recurrent user.
	 */
	private boolean occurences;

	/**
	 * getID() getter for attribute - id getter.
	 *
	 * @return id - returns id.
	 */
	public int getId() {
		return id;
	}

	/**
	 * setId() setter id attribute.
	 *
	 * @param aId
	 */
	public void setId(final int aId) {
		this.id = aId;
	}

	/**
	 * getID() getter for attribute method.
	 *
	 * @return parkingSpot
	 */
	public ParkingSpot getParkingSpot() {
		return parkingSpot;
	}

	/**
	 * setParkingSpot() setter for ParkingSpot() Constructor.
	 *
	 * @param aParkingSpot
	 */
	public void setParkingSpot(final ParkingSpot aParkingSpot) {
		this.parkingSpot = aParkingSpot;
	}

	/**
	 * getVehicleRegNumber() getter for attribute - vehicleRegNumber.
	 *
	 * @return vehicleRegNumber - returns the attribute value.
	 */
	public String getVehicleRegNumber() {
		return vehicleRegNumber;
	}

	/**
	 * setVehicleRegNumber() setter vehicle Reg Number attribute.
	 *
	 * @param aVehicleRegNumber
	 */
	public void setVehicleRegNumber(final String aVehicleRegNumber) {
		this.vehicleRegNumber = aVehicleRegNumber;
	}

	/**
	 * getPrice() getter for attribute - price getter
	 *  is used to protect the data during class
	 *  instance creation.
	 *
	 * @return price - returns the instance parameter
	 * attribute value.
	 */
	public double getPrice() {
		return price;
	}

	/**
	 * setPrice() setter price attribute - with parameter
	 *  price type double.
	 *
	 * @param aPrice
	 */
	public void setPrice(final double aPrice) {
		this.price = aPrice;
	}

	/**
	 * getPrice() getter for attribute - price value getter.
	 *
	 * @return incoming time.
	 */
	public Date getInTime() {
		return this.inTime == null
				? null : new Date(this.inTime.getTime());
	}

	/**
	 * setInTime() setter inTime attribute - with
	 *  parameter inTime type Date.
	 *
	 * @param aInTime
	 */
	public void setInTime(final Date aInTime) {
		this.inTime = Optional.ofNullable(aInTime)
				.map(Date::getTime).map(Date::new)
				.orElse(null); 
	}

	/**
	 * getOutTime() setter outTime attribute - with
	 *  parameter outTime type Date.
	 * setter is used to protect the data during
	 *  class instance creation.
	 *
	 * @return outTime - returns attribute value.
	 */
	public Date getOutTime() {
		return this.outTime == null
				? null : new Date(this.outTime.getTime());
	}

	/**
	 * setOutTime() setter outTime attribute - with
	 *  parameter outTime type Date.
	 * setter is used to protect the data during
	 *  class instance creation.
	 *
	 * @param aOutTime
	 */
	public void setOutTime(final Date aOutTime) {
		this.outTime = Optional.ofNullable(aOutTime)
				.map(Date::getTime).map(Date::new).orElse(null);
	}

	/**
	 * Occurrences represents recurrent user.
	 * @return occurrences
	 */
	public boolean occurences() {
		return occurences;
	}

	/**
	 * Setter for the occurrences.
	 * @param aOccurences
	 */
	public void setOccurences(final boolean aOccurences) {
		this.occurences = aOccurences;
	}
}
