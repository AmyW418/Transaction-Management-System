/**
 * The Date class defines Date item and contains compareTo method
 * @author Junhao Shen, Amy Wang
 */
public class Date implements Comparable<Date> {

	private int year;
	private int month;
	private int day;

	/**
	 * Constructor for a new Date object
	 * @param month date's month
	 * @param day   date's day
	 * @param year  date's year
	 */
	public Date(int month, int day, int year) {
		this.month = month;
		this.day = day;
		this.year = year;
	}

	/**
	 * The compareTo function compares two given dates.
	 * @return -1 if before, 0 if equal, 1 if after
	 */
	@Override
	public int compareTo(Date date) {
		if (this.year < date.getYear()) {
			return -1;
		} else if (this.year == date.getYear()) {
			if (this.month < date.getMonth()) {
				return -1;
			} else if (this.month == date.month) {
				if (this.day < date.day) {
					return -1;
				} else if (this.day == date.day) {
					return 0;
				}
			}
		}
		return 1;
	}

	/**
	 * Rewrites date information as a string
	 * @return date
	 */
	@Override
	public String toString() {
		return this.month + "/" + this.day + "/" + this.year;
	}

	/**
	 * Gets year of date
	 * @return year
	 */
	public int getYear() {
		return year;
	}

	/**
	 * Sets year of date
	 * @param year to set
	 */
	public void setYear(int year) {
		this.year = year;
	}

	/**
	 * Gets month of date
	 * @return month
	 */
	public int getMonth() {
		return month;
	}

	/**
	 * Sets month of date
	 * @param month to set
	 */
	public void setMonth(int month) {
		this.month = month;
	}

	/**
	 * Gets day of date
	 * @return day
	 */
	public int getDay() {
		return day;
	}

	/**
	 * Sets day of date
	 * @param day to set
	 */
	public void setDay(int day) {
		this.day = day;
	}

	/**
	 * Checks if the date is a valid date.
	 * @return true if valid, false otherwise
	 */
	public boolean isValid() {
		if (this.year <= 0 || this.day <= 0 || this.month <= 0 || this.month > Month.NUMMONTHS) {
			return false;
		}
		if (this.month == Month.JAN 
				|| this.month == Month.MAR 
				|| this.month == Month.MAY 
				|| this.month == Month.JUL
				|| this.month == Month.AUG 
				|| this.month == Month.OCT 
				|| this.month == Month.DEC) {
			if (this.day > Month.ODD_DAYS) {
				return false;
			}
		} else if (this.month == Month.APR 
				|| this.month == Month.JUN 
				|| this.month == Month.SEP
				|| this.month == Month.NOV) {
			if (this.day > Month.EVEN_DAYS) {
				return false;
			}
		} else {
			if ((this.year % Month.QUADRENNIAL == Month.DIVISIBLE && this.year % Month.CENTENNIAL != Month.DIVISIBLE)
					|| this.year % Month.QUARTERCENTENNIAL == Month.DIVISIBLE) {
				if (this.day > Month.LEAP_FEB_DAYS) {
					return false;
				}
			} else {
				if (this.day > Month.FEB_DAYS) {
					return false;
				}
			}
		}
		return true;
	}
}