package com.parkit.parkingsystem.model;

import java.util.Date;
import java.util.Optional;

/**
 * Class: {@link Ticket} - Ticket Data Objects.<br>
 * <b>Project: </b> P3 - parking system - ParkIt<br>
 * 
 * @package - com.parkit.parkingsystem.model
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
	 * @param id
	 * @return id - returns value assigned to its attribute.
	 */
	public void setId(final int id) {
		this.id = id;
	}

	/**
	 * getID() getter for attribute method.
	 * 
	 * @return parkingSpot - 
	 */
	public ParkingSpot getParkingSpot() {
		return parkingSpot;
	}

	/**
	 * setParkingSpot() setter for ParkingSpot() Constructor.
	 * 
	 * @param parkingSpot
	 * @return parkingSpot - returns value assigned instance.
	 */
	public void setParkingSpot(final ParkingSpot parkingSpot) {
		this.parkingSpot = parkingSpot;
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
	 * @param vehicleRegNumber
	 * @return vehicleRegNumber - returns value assigned 
	 * to its attribute.
	 */
	public void setVehicleRegNumber(final String vehicleRegNumber) {
		this.vehicleRegNumber = vehicleRegNumber;
	}

	/**
	 * getPrice() getter for attribute - price getter 
	 * is used to protect the data during class instance creation.
	 * 
	 * @return price - returns the instance parameter 
	 * attribute value.
	 */
	public double getPrice() {
		return price;
	}

	/**
	 * setPrice() setter price attribute - with parameter 
	 * price type double.
	 * 
	 * @param price
	 * @return price - returns value.
	 */
	public void setPrice(final double price) {
		this.price = price;
	}

	/**
	 * getPrice() getter for attribute - price value getter.
	 * 
	 * @return incoming time.
	 */
	public Date getInTime() {
		return this.inTime == null ? 
				null : new Date(this.inTime.getTime());
	}

	/**
	 * setInTime() setter inTime attribute - with 
	 * parameter inTime type Date.
	 * 
	 * @param inTime
	 * @return inTime - returns value assigned to its attribute.
	 */
	public void setInTime(final Date inTime) {
		this.inTime = Optional.ofNullable(inTime)
				.map(Date::getTime).map(Date::new).orElse(null); 
	}

	/**
	 * getOutTime() setter outTime attribute - with 
	 * parameter outTime type Date.
	 * setter is used to protect the data during 
	 * class instance creation.
	 * 
	 * @return outTime - returns attribute value.
	 */
	public Date getOutTime() {
		return this.outTime == null ? 
				null : new Date(this.outTime.getTime());
	}

	/**
	 * setOutTime() setter outTime attribute - with 
	 * parameter outTime type Date.
	 * setter is used to protect the data during 
	 * class instance creation.
	 * 
	 * @param outTime
	 * @return outTime - returns value assigned to its attribute.
	 */
	public void setOutTime(final Date outTime) {
		this.outTime = Optional.ofNullable(outTime)
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
	 * @param occurences
	 */
	public void setOccurences(boolean occurences) {
		this.occurences = occurences;
	}
}
