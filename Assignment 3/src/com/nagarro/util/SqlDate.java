/*
 * 
 */
package com.nagarro.util;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class SqlDate {

	public static Date getSqlDateFormat(String dateString) {
		Date date = null;
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		try {
			date = new java.sql.Date(formatter.parse(dateString).getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
}