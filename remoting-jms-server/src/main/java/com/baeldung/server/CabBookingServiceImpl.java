package com.baeldung.server;

import static java.lang.Math.random;
import static java.util.UUID.randomUUID;

import com.baeldung.api.Booking;
import com.baeldung.api.BookingException;
import com.baeldung.api.CabBookingService;

// TODO: Auto-generated Javadoc
/**
 * The Class CabBookingServiceImpl.
 */
public class CabBookingServiceImpl implements CabBookingService {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.baeldung.api.CabBookingService#bookRide(java.lang.String)
	 */
	public Booking bookRide(String pickUpLocation) throws BookingException {
		if (random() < 0.3)
			throw new BookingException("Cab unavailable");
		return new Booking(randomUUID().toString());
	}
}
