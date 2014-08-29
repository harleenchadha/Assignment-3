/*
 * 
 */
package com.nagarro.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.nagarro.datastore.InputConstants;
import com.nagarro.model.Flight;
import com.nagarro.util.JdbcUtilities;

/**
 * The Class FlightDaoImpl.
 */
public class FlightDaoJdbcImpl implements FlightDao {

	public FlightDaoJdbcImpl() {
	}

	@Override
	public void setFlightMap(List<Flight> flightList) {
		for (Flight flight : flightList) {
			String sql;
			Connection connection = null;
			Statement statement = null;
			try {
				connection = JdbcUtilities.getConnection();
				statement = connection.createStatement();
				sql = "SELECT * FROM flight where flight_no='"
						+ flight.getFlightNo() + "' and flight_time='"
						+ flight.getFlightTime() + "'";

				ResultSet rs = statement.executeQuery(sql);
				if (!rs.next()) {
					sql = "INSERT INTO flight(flight_no, dep_loc, arr_loc, valid_till, flight_time, flight_dur, fare, seat_available, flight_class, flight_name) VALUES ('"
							+ flight.getFlightNo()
							+ "', '"
							+ flight.getDepLoc()
							+ "', '"
							+ flight.getArrLoc()
							+ "', '"
							+ flight.getValidTill()
							+ "', '"
							+ flight.getFlightTime()
							+ "', "
							+ flight.getFlightDur()
							+ ", "
							+ flight.getFare()
							+ ", '"
							+ flight.getSeatAvailable()
							+ "', '"
							+ flight.getFlightClass()
							+ "', '"
							+ flight.getFlightName() + "')";
					statement.executeUpdate(sql);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				// finally block used to close resources
				try {
					if (statement != null)
						statement.close();
				} catch (SQLException se2) {
				}// nothing we can do
			}
		}
	}

	@Override
	public List<Flight> getFlightList(List<String> userInput, Date date) {
		List<Flight> availableFlights = new ArrayList<>();
		Connection connection = null;
		Statement statement = null;
		try {
			connection = JdbcUtilities.getConnection();
			statement = connection.createStatement();
			String sql;
			if (InputConstants.OPTION_A.equalsIgnoreCase(userInput.get(4))) {
				sql = "SELECT * FROM flight where dep_loc='" + userInput.get(0)
						+ "' and arr_loc='" + userInput.get(1)
						+ "' and flight_class LIKE '%" + userInput.get(2) + "%' ORDER BY fare";
			} else {
				sql = "SELECT * FROM flight where dep_loc='" + userInput.get(0)
						+ "' and arr_loc='" + userInput.get(1)
						+ "' and flight_class LIKE '%" + userInput.get(2) + "%' ORDER BY fare, flight_dur";
			}

			ResultSet rs = statement.executeQuery(sql);
			while (rs.next()) {
				if ((rs.getDate("valid_till").compareTo(date) >= 0)
						&& rs.getString("seat_available").equalsIgnoreCase("Y")) {
					availableFlights.add(getFlightDetails(rs));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (statement != null)
					statement.close();
			} catch (SQLException se2) {
			}
		}
		return availableFlights;
	}

	private Flight getFlightDetails(ResultSet resultSet) {
		Flight flight = new Flight();
		try {
			flight.setFlightNo(resultSet.getString("flight_no"));
			flight.setDepLoc(resultSet.getString("dep_loc"));
			flight.setArrLoc(resultSet.getString("arr_loc"));
			flight.setValidTill(resultSet.getDate("valid_till"));
			flight.setFlightTime(resultSet.getString("flight_time"));
			flight.setFlightDur(resultSet.getDouble("flight_dur"));
			flight.setFare(resultSet.getDouble("fare"));
			flight.setSeatAvailable(resultSet.getString("seat_available"));
			flight.setFlightClass(resultSet.getString("flight_class"));
			flight.setFlightName(resultSet.getString("flight_name"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return flight;
	}
}
