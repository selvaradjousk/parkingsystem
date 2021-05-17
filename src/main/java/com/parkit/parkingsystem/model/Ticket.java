package com.parkit.parkingsystem.model;

import java.util.Date;

/**
 * Class: {@link Ticket} - Ticket Data Objects.<br> 
 * <b>Project: <b> P3 - parking system - ParkIt<br> 
 * @package - com.parkit.parkingsystem.model
 * 
 * @author Senthil
 */
public class Ticket {
	private int id;
	private ParkingSpot parkingSpot;
	private String vehicleRegNumber;
	private double price;
	private Date inTime;
	private Date outTime;

	/**
	 * getID() getter for attribute - id getter is used to protect the data during
	 * class instance creation.
	 * 
	 * @return id - returns the instance parameter attribute value.
	 */
	public int getId() {
		return id;
	}

	/**
	 * setId() setter id attribute - setID with parameter id type integer. setter is
	 * used to protect the data during class instance creation.
	 * 
	 * @return id - returns value assigned to its attribute.
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * getID() getter for attribute method- getParkingSpot() with parameter type
	 * ParkingSpot. getter is used to protect the data during class instance
	 * creation.
	 * 
	 * @return parkingSpot - returns the instance parameter attributes and values of
	 *         class ParkingSpot instantiated.
	 */
	public ParkingSpot getParkingSpot() {
		return parkingSpot;
	}

	/**
	 * setParkingSpot() setter for ParkingSpot() Constructor parameter instance of -
	 * ParkingSpot. setter is used to protect the data during class instance
	 * creation.
	 * 
	 * @return parkingSpot - returns value assigned to the attributes of ParkingSpot
	 *         instance.
	 */
	public void setParkingSpot(ParkingSpot parkingSpot) {
		this.parkingSpot = parkingSpot;
	}

	/**
	 * getVehicleRegNumber() getter for attribute - vehicleRegNumber getter is used
	 * to protect the data during class instance creation.
	 * 
	 * @return vehicleRegNumber - returns the instance parameter attribute value.
	 */
	public String getVehicleRegNumber() {
		return vehicleRegNumber;
	}

	/**
	 * setVehicleRegNumber() setter vehicleRegNumber attribute - with parameter
	 * vehicleRegNumber type string. setter is used to protect the data during class
	 * instance creation.
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
	 * setPrice() setter price attribute - with parameter price type double. setter
	 * is used to protect the data during class instance creation.
	 * 
	 * @return price - returns value assigned to its attribute.
	 */
	public void setPrice(double price) {
		this.price = price;
	}

	/**
	 * getPrice() getter for attribute - price getter is used to protect the data
	 * during class instance creation.
	 * 
	 * @return price - returns the instance parameter attribute value.
	 */
	public Date getInTime() {
		return inTime;
	}

	/**
	 * setInTime() setter inTime attribute - with parameter inTime type Date. setter
	 * is used to protect the data during class instance creation.
	 * 
	 * @return inTime - returns value assigned to its attribute.
	 */
	public void setInTime(Date inTime) {
		this.inTime = inTime;
	}

	/**
	 * getOutTime() setter outTime attribute - with parameter outTime type Date.
	 * setter is used to protect the data during class instance creation.
	 * 
	 * @return outTime - returns the instance parameter attribute value.
	 */
	public Date getOutTime() {
		return outTime;
	}

	/**
	 * setOutTime() setter outTime attribute - with parameter outTime type Date.
	 * setter is used to protect the data during class instance creation.
	 * 
	 * @return outTime - returns value assigned to its attribute.
	 */
	public void setOutTime(Date outTime) {
		this.outTime = outTime;
	}
}
