/*
 * 
 */
package com.nagarro.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.nagarro.datastore.InputConstants;
import com.nagarro.model.Flight;
import com.nagarro.util.HibernateUtilities;

/**
 * The Class FlightDaoImpl.
 */
public class FlightDaoImpl implements FlightDao {

	@Override
	public void setFlightMap(List<Flight> flightList) {
		Session session = HibernateUtilities.getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		for (Flight flight : flightList) {
			Criteria criteria = session.createCriteria(Flight.class);
			criteria.add(Restrictions.eq("flightNo", flight.getFlightNo()));
			criteria.add(Restrictions.eq("flightTime", flight.getFlightTime()));
			@SuppressWarnings("unchecked")
			List<Flight> queryResult = criteria.list();
			if (queryResult.size() == 0) {
				session.save(flight);
			}
		}
		session.getTransaction().commit();
	}

	@Override
	public List<Flight> getFlightList(List<String> userInput, Date date) {
		List<Flight> availableFlights = new ArrayList<>();
		Flight flight = null;		
		Session session = HibernateUtilities.getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();

		Criteria criteria = session.createCriteria(Flight.class);
		criteria.add(Restrictions.eq("depLoc", userInput.get(0)));
		criteria.add(Restrictions.eq("arrLoc", userInput.get(1)));
		criteria.add(Restrictions.like("flightClass", "%" + userInput.get(2) + "%"));
		criteria.addOrder(Order.asc("fare"));
		if (InputConstants.OPTION_B.equalsIgnoreCase(userInput.get(4))) {
			criteria.addOrder(Order.asc("flightDur"));
		}
		@SuppressWarnings("unchecked")
		List<Flight> queryResult = criteria.list();

		for (Iterator<Flight> iterator1 = queryResult.iterator(); iterator1.hasNext();) {
			flight = (Flight) iterator1.next();
			if ((flight.getValidTill().compareTo(date) >= 0)
					&& flight.getSeatAvailable().equalsIgnoreCase("Y")) {
				availableFlights.add(flight);
			}
		}
		session.getTransaction().commit();

		return availableFlights;
	}
}