package rs.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Date;

import rs.modelo.Gender;

public class Validation {

	public static Double isDouble(String str) {
		try {
			return Double.parseDouble(str);
		} catch (NumberFormatException e) {
			return null;
		}
	}

	public static int isInteger(String str) {
		try {
			return Integer.parseInt(str);
		} catch (NumberFormatException e) {
			return -1;
		}
	}

	public static LocalDate isDate(String date) {
		try {
			return LocalDate.parse(date);
		} catch (DateTimeParseException e) {
			return null;
		}
	}

	public static Gender isGenero(String g) {
		try {
			return Gender.valueOf(g);
		} catch (IllegalArgumentException e) {
			return null;
		}
	}
	public static Date isDate(String dateStr, String dateFormat) {
		DateFormat sdf = new SimpleDateFormat(dateFormat);

		sdf.setLenient(false);
		try {
			return sdf.parse(dateStr);
		} catch (ParseException e) {
			return null;
		}
		
	}
}
