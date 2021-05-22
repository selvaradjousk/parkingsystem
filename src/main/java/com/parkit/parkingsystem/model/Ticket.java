package com.parkit.parkingsystem.model;

import java.util.Date;
import java.util.Optional;

/**
 * Class: {@link Ticket} - Ticket Data Objects.<br>
 * <b>Project: <b> P3 - parking system - ParkIt<br>
 * 
 * @package - com.parkit.parkingsystem.model
 * 
 * @author Senthil
 */
public class Ticket {

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
	
	private boolean occurences;
	
	

	/**
	 * getID() getter for attribute - id getter.
	 * 
	 * @return id - returns the instance parameter attribute value.
	 */
	public int getId() {
		return id;
	}

	/**
	 * setId() setter id attribute.
	 * 
	 * @return id - returns value assigned to its attribute.
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * getID() getter for attribute method.
	 * 
	 * @param spot parking spot.
	 * @return parkingSpot - returns the instance parameter attributes.
	 */
	public ParkingSpot getParkingSpot() {
		return parkingSpot;
	}

	/**
	 * setParkingSpot() setter for ParkingSpot() Constructor.
	 * 
	 * @return parkingSpot - returns value assigned instance.
	 */
	public void setParkingSpot(ParkingSpot parkingSpot) {
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
	 * @return vehicleRegNumber - returns value assigned to its attribute.
	 */
	public void setVehicleRegNumber(String vehicleRegNumber) {
		this.vehicleRegNumber = vehicleRegNumber;
	}

	/**
	 * getPrice() getter for attribute - price getter is used to protect the data
	 * during class instance creation.
	 * 
	 * @return price - returns the instance parameter attribute value.
	 */
	public double getPrice() {
		return price;
	}

	/**
	 * setPrice() setter price attribute - with parameter price type double.
	 * 
	 * @return price - returns value assigned to its attribute.
	 */
	public void setPrice(double price) {
		this.price = price;
	}

	/**
	 * getPrice() getter for attribute - price value getter.
	 * 
	 * @return price - returns the instance parameter attribute value.
	 */
	public Date getInTime() {
		return this.inTime == null ? null : new Date(this.inTime.getTime());
	}

	/**
	 * setInTime() setter inTime attribute - with parameter inTime type Date.
	 * 
	 * @return inTime - returns value assigned to its attribute.
	 */
	public void setInTime(Date inTime) {
		this.inTime = Optional.ofNullable(inTime).map(Date::getTime).map(Date::new).orElse(null); // new
																									// Date(inTime.getTime());
	}

	/**
	 * getOutTime() setter outTime attribute - with parameter outTime type Date.
	 * setter is used to protect the data during class instance creation.
	 * 
	 * @return outTime - returns the instance parameter attribute value.
	 */
	public Date getOutTime() {
		return this.outTime == null ? null : new Date(this.outTime.getTime());
	}

	/**
	 * setOutTime() setter outTime attribute - with parameter outTime type Date.
	 * setter is used to protect the data during class instance creation.
	 * 
	 * @return outTime - returns value assigned to its attribute.
	 */
	public void setOutTime(Date outTime) {
		this.outTime = Optional.ofNullable(outTime).map(Date::getTime).map(Date::new).orElse(null);// new
																									// Date(outTime.getTime());
	}
	
	public boolean occurences() {
		return occurences;
	}

	public void setOccurences(boolean occurences) {
		this.occurences = occurences;
	}
	
}
