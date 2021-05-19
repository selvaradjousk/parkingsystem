package com.parkit.parkingsystem.model;

import com.parkit.parkingsystem.constants.ParkingType;

/**
 * Class: {@link ParkingSpot} - Model data on Parking spot.<br>
 * <b>Project: </b> P3 - parking system - ParkIt<br>
 * 
 * @see Methods: {@link #ParkingSpot(int, ParkingType, boolean)},
 *      {@link #getId()}, {@link #getId()}, {@link #setId(int)},
 *      {@link #number}, {@link #parkingType},
 *      {@link #setParkingType(ParkingType)}, {@link #getParkingType()},
 *      {@link #isAvailable()}, {@link #setAvailable(boolean)},
 *      {@link #equals(Object)}, {@link #hashCode()},
 * 
 * @author Senthil
 */
public class ParkingSpot {

	/**
	 * Parking spot number.
	 */
	private int number;

	/**
	 * The vehicle parking type.
	 */
	private ParkingType parkingType;

	/**
	 * Availability of a parking spot.
	 */
	private boolean isAvailable;

	/**
	 * ParkingSpot() Parameterized Constructor with three parameters number,
	 * ParkingType and IsAvailable
	 * 
	 * @return boolean - returns object value basically instantiated from the class
	 *         Ticket.
	 * @param num          Parking spot number
	 * @param the          vehicle parking type
	 * @param availability of a parking spot
	 */
	public ParkingSpot(int number, ParkingType parkingType, boolean isAvailable) {
		this.number = number;
		this.parkingType = parkingType;
		this.isAvailable = isAvailable;
	}

	/**
	 * getID() getter for ParkingSpot() parameter - number. getter is used to
	 * protect the data during class instance creation.
	 * 
	 * @return number - returns the instance parameter attribute value.
	 */
	public int getId() {
		return number;
	}

	/**
	 * setID() setter for ParkingSpot() parameter - number. setter is used to
	 * protect the data during class instance creation.
	 * 
	 * @return number - returns value assigned to its attribute.
	 */
	public void setId(int number) {
		this.number = number;
	}

	/**
	 * getParkingType() getter for ParkingSpot() Constructor parameter - parkingType
	 * - getter is used to protect the data during class instance creation.
	 * 
	 * @return parkingType - returns the instance parameter attribute values.
	 */
	public ParkingType getParkingType() {
		return parkingType;
	}

	/**
	 * setParkingType() setter for ParkingSpot() Constructor parameter -
	 * parkingType. setter is used to protect the data during class instance
	 * creation.
	 * 
	 * @return parkingType - returns value assigned to its parameter attributes.
	 */
	public void setParkingType(ParkingType parkingType) {
		this.parkingType = parkingType;
	}

	/**
	 * isAvailable() method for ParkingSpot() parameter - isAvailable. protect the
	 * data during class instance creation.
	 * 
	 * @return isAvailable - returns the instance parameter attribute value.
	 */
	public boolean isAvailable() {
		return isAvailable;
	}

	/**
	 * setAvailable() setter for ParkingSpot() Constructor parameter - setAvailable.
	 * setter is used to protect the data during class instance creation.
	 * 
	 * @return setAvailable - returns value assigned to its attribute.
	 */
	public void setAvailable(boolean available) {
		isAvailable = available;
	}

	/**
	 * equals() equals boolean method with object parameter as input value. Checks
	 * for null value and duplicity.
	 * 
	 * @return boolean - returns boolean result for the ParkingSpot check.
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		ParkingSpot that = (ParkingSpot) o;
		return number == that.number;
	}

	/**
	 * hashcode() method
	 * 
	 * @return number - returns the instance parameter attribute value.
	 */
	@Override
	public int hashCode() {
		return number;
	}
}
