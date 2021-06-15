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
	 * ParkingSpot() Parameterized Constructor with three
	 *  parameters number, ParkingType and IsAvailable.
	 *
	 * @param aNumber
	 * @param aParkingType
	 * @param aIsAvailable
	 */
	public ParkingSpot(final int aNumber,
			final ParkingType aParkingType,
			final boolean aIsAvailable) {
		this.number = aNumber;
		this.parkingType = aParkingType;
		this.isAvailable = aIsAvailable;
	}

	/**
	 * getID() getter number.
	 *
	 * @return number - instance parameter attribute value.
	 */
	public int getId() {
		return number;
	}

	/**
	 * setID() setter for ParkingSpot() parameter - number.
	 *
	 * @param aNumber
	 */
	public void setId(final int aNumber) {
		this.number = aNumber;
	}

	/**
	 * getParkingType() getter for ParkingSpot() Constructor parameter.
	 *
	 * @return parkingType instance parameter attribute values.
	 */
	public ParkingType getParkingType() {
		return parkingType;
	}

	/**
	 * setParkingType() setter for ParkingSpot() Constructor parameter.
	 *
	 * @param aParkingType
	 */
	public void setParkingType(final ParkingType aParkingType) {
		this.parkingType = aParkingType;
	}

	/**
	 * isAvailable() method for ParkingSpot() parameter isAvailable.
	 *
	 * @return isAvailable instance parameter attribute value.
	 */
	public boolean isAvailable() {
		return isAvailable;
	}

	/**
	 * setAvailable() setter for ParkingSpot() Constructor parameter.
	 *
	 * @param available
	 */
	public void setAvailable(final boolean available) {
		isAvailable = available;
	}

	/**
	 * equals() equals boolean method with object parameter as input value.
	 *
	 * @return boolean result for the ParkingSpot check.
	 */
	@Override
	public final boolean equals(final Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		ParkingSpot that = (ParkingSpot) o;
		boolean resultNumber = number == that.number;
		return resultNumber;
	}

	/**
	 * hashcode() method.
	 *
	 * @return number
	 */
	@Override
	public final int hashCode() {
		return number;
	}
}
