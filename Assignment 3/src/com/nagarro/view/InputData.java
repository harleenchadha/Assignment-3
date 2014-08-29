/*
 * 
 */
package com.nagarro.view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.nagarro.datastore.FlightConstants;
import com.nagarro.datastore.InputConstants;

/**
 * The Class InputData.
 */
public class InputData {

	/** The br. */
	private BufferedReader br;

	/**
	 * Instantiates a new input data.
	 */
	public InputData() {
		br = new BufferedReader(new InputStreamReader(System.in));
	}

	/**
	 * User input.
	 *
	 * @return the list
	 */
	public List<String> userInput() {
		List<String> input = new ArrayList<>();
		String sortingChoice, dateString, inputData, inputResult;
		try {
			while (true) {
				while (true) {
					System.out.println("Enter Departure Location: ");
					if (checkDeparture(inputData = br.readLine())) {
						input.add(inputData.toUpperCase());
						break;
					}
				}
				while (true) {
					System.out.println("Enter Arrival Location: ");
					inputResult = checkArrival(inputData = br.readLine(),
							input.get(0));
					if (inputResult.equals("correct")
							|| inputResult.equals("same")) {
						break;
					}
				}
				if (inputResult.equals("correct")) {
					input.add(inputData.toUpperCase());
					break;
				}
			}
			while (true) {
				System.out.println("Enter Date in this format dd-mm-yyyy: ");
				dateString = br.readLine();
				if (checkDateFormat(dateString)) {
					break;
				}
			}
			while (true) {
				System.out.println("Enter Flight Class E/B: ");
				if (checkFlightClass(inputData = br.readLine())) {
					input.add(inputData.toUpperCase());
					break;
				}
			}
			while (true) {
				System.out
						.println("Enter Output Preference a or b\n a)Only by Fare \n b)Both by Fare and Flight Duration: ");
				sortingChoice = br.readLine();
				if (checkOutputPreference(sortingChoice)) {
					break;
				}
			}
			input.add(dateString);
			input.add(sortingChoice.toUpperCase());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return input;
	}

	/**
	 * Check departure.
	 *
	 * @param depLocation
	 *            the dep location
	 * @return true, if successful
	 */
	private boolean checkDeparture(String depLocation) {
		boolean valid = true;
		if (!(depLocation.matches("[a-zA-Z]+") && depLocation.length() == 3)) {
			valid = false;
			System.out
					.println("Incorrect departure location entered. Please enter again.");
		}
		return valid;
	}

	/**
	 * Check arrival.
	 *
	 * @param arrLocation
	 *            the arr location
	 * @return the string
	 */
	private String checkArrival(String arrLocation, String depLocation) {
		String result = "correct";
		if (arrLocation.matches("[a-zA-Z]+") && arrLocation.length() == 3) {
			if (arrLocation.equalsIgnoreCase(depLocation)) {
				System.out
						.println("Arrival and Departure locations cannot be same. Please enter again.");
				result = "same";
			}
		} else {
			System.out
					.println("Incorrect arrival location entered. Please enter again.");
			result = "incorrect";
		}
		return result;
	}

	/**
	 * Check date format.
	 *
	 * @param dateString
	 *            the date string
	 * @return true, if successful
	 */
	private boolean checkDateFormat(String dateString) {
		boolean valid = true;
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		formatter.setLenient(false);
		try {
			formatter.parse(dateString);
		} catch (ParseException e) {
			System.out.println("Incorrect date format entered.");
			valid = false;
		}
		return valid;
	}

	/**
	 * Check flight class.
	 *
	 * @param flightClass
	 *            the flight class
	 * @return true, if successful
	 */
	private boolean checkFlightClass(String flightClass) {
		boolean valid = true;
		if (!(flightClass.equalsIgnoreCase(FlightConstants.CLASS_ECONOMIC) || flightClass
				.equalsIgnoreCase(FlightConstants.CLASS_BUSINESS))) {
			System.out.println("Incorrect flight class. Please enter again.");
			valid = false;
		}
		return valid;
	}

	/**
	 * Check output preference.
	 *
	 * @param outputPreference
	 *            the output preference
	 * @return true, if successful
	 */
	private boolean checkOutputPreference(String outputPreference) {
		boolean valid = true;
		if (!(outputPreference.equalsIgnoreCase(InputConstants.OPTION_A) || outputPreference
				.equalsIgnoreCase(InputConstants.OPTION_B))) {
			{
				System.out
						.println("Incorrect output preference. Please enter again.");
				valid = false;
			}
		}
		return valid;
	}

	/**
	 * Enter choice.
	 *
	 * @return true, if successful
	 */
	public boolean enterChoice() {
		String ch = "";
		Boolean valid = true;
		System.out.println("Do you wish to search more (y/n):");
		try {
			ch = br.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (ch.equalsIgnoreCase(InputConstants.OPTION_NO)) {
			valid = false;
		} else if (!ch.equalsIgnoreCase(InputConstants.OPTION_YES)) {
			System.out
					.println("Wrong Input entered. Please enter either y or n.");
			valid = enterChoice();
		}
		return valid;
	}

	/**
	 * Close stream.
	 */
	public void closeStream() {
		try {
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}