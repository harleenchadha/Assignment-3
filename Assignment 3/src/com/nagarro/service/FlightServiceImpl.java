/*
 * 
 */
package com.nagarro.service;

import java.sql.Date;
import java.util.List;

import com.nagarro.dao.FlightDao;
import com.nagarro.dao.FlightDaoImpl;
import com.nagarro.model.Flight;
import com.nagarro.util.SqlDate;

/**
 * The Class FlightServiceImpl.
 */
public class FlightServiceImpl implements FlightService {

	/** The flight dao. */
	private FlightDao flightDao;

	/**
	 * Instantiates a new flight service impl.
	 */
	public FlightServiceImpl() {
		flightDao = new FlightDaoImpl();
	}

	@Override
	public void setFlightMap(List<Flight> flightList) {
		flightDao.setFlightMap(flightList);
	}

	@Override
	public List<Flight> searchFlights(List<String> userInput) {
		Date date = SqlDate.getSqlDateFormat(userInput.get(3));
		return flightDao.getFlightList(userInput, date);
	}
}