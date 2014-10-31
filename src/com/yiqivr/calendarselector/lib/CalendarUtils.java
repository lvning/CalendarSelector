package com.yiqivr.calendarselector.lib;

import java.util.ArrayList;
import java.util.Calendar;

import com.yiqivr.calendarselector.lib.Day.DayType;

public class CalendarUtils {

	private static int dayOfMonth, monthOfYear, curYear;

	static {
		dayOfMonth = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
		monthOfYear = Calendar.getInstance().get(Calendar.MONTH);
		curYear = Calendar.getInstance().get(Calendar.YEAR);
	}

	/**
	 * Gets the days in month.
	 *
	 * @param month the month
	 * @param year the year
	 * @return the days in month
	 */
	public static int getDaysInMonth(int month, int year) {
		switch (month) {
		case Calendar.JANUARY:
		case Calendar.MARCH:
		case Calendar.MAY:
		case Calendar.JULY:
		case Calendar.AUGUST:
		case Calendar.OCTOBER:
		case Calendar.DECEMBER:
			return 31;
		case Calendar.APRIL:
		case Calendar.JUNE:
		case Calendar.SEPTEMBER:
		case Calendar.NOVEMBER:
			return 30;
		case Calendar.FEBRUARY:
			return (year % 4 == 0) ? 29 : 28;
		default:
			throw new IllegalArgumentException("Invalid Month");
		}
	}

	/**
	 * Gets the flow month days.
	 *
	 * @param flowMonth the flow month
	 * @return the flow month days
	 */
	public static int getFlowMonthDays(int flowMonth) {
		int totalDays = 0;
		for (int i = 0; i < flowMonth; i++) {
			Calendar c = Calendar.getInstance();
			c.add(Calendar.MONTH, i + 1);
			int days = getDaysInMonth(c.get(Calendar.MONTH), c.get(Calendar.YEAR));
			totalDays += days;
		}
		return totalDays;
	}

	/**
	 * Current month remain days.
	 *
	 * @return the int
	 */
	public static int currentMonthRemainDays() {
		Calendar c = Calendar.getInstance();
		return getDaysInMonth(c.get(Calendar.MONTH), c.get(Calendar.YEAR)) - c.get(Calendar.DAY_OF_MONTH) + 1;
	}

	/**
	 * Through month.
	 *
	 * @param calendar the calendar
	 * @param passDays the pass days
	 * @return the int
	 */
	public static int throughMonth(Calendar calendar, int passDays) {
		Calendar c = (Calendar) calendar.clone();
		int curMonth = c.get(Calendar.MONTH);
		int curYear = c.get(Calendar.YEAR);
		c.add(Calendar.DAY_OF_MONTH, passDays - 1);
		int monthCount = (c.get(Calendar.YEAR) - curYear) * 12 + (c.get(Calendar.MONTH) - curMonth);
		return monthCount;
	}

	/**
	 * Gets the days of month.
	 *
	 * @param calendar the calendar
	 * @return the days of month
	 */
	public static String[] getDaysOfMonth(Calendar calendar) {
		Calendar month = (Calendar) calendar.clone();
		String[] days;
		final int FIRST_DAY_OF_WEEK = 0; // Sunday = 0, Monday = 1
		month.set(Calendar.DAY_OF_MONTH, 1);
		int lastDay = month.getActualMaximum(Calendar.DAY_OF_MONTH);
		int firstDay = (int) month.get(Calendar.DAY_OF_WEEK);

		if (firstDay == 1) {
			days = new String[lastDay + (FIRST_DAY_OF_WEEK * 6)];
		} else {
			days = new String[lastDay + firstDay - (FIRST_DAY_OF_WEEK + 1)];
		}

		int j = FIRST_DAY_OF_WEEK;

		if (firstDay > 1) {
			for (j = 0; j < firstDay - FIRST_DAY_OF_WEEK; j++) {
				days[j] = "";
			}
		} else {
			for (j = 0; j < FIRST_DAY_OF_WEEK * 6; j++) {
				days[j] = "";
			}
			j = FIRST_DAY_OF_WEEK * 6 + 1; // sunday => 1, monday => 7
		}

		int dayNumber = 1;
		for (int i = j - 1; i < days.length; i++) {
			days[i] = "" + dayNumber;
			dayNumber++;
		}
		return days;
	}

	/**
	 * Gets the days of month.
	 *
	 * @param calendar the calendar
	 * @param passDays the pass days
	 * @param orderDay the order day
	 * @return the days of month
	 */
	public static ArrayList<Day> getDaysOfMonth(Calendar calendar, int passDays, String orderDay) {
		String[] orderInfo = null;
		boolean isOrdered = false;
		if (orderDay != null)
			orderInfo = orderDay.split("#");
		Calendar month = (Calendar) calendar.clone();
		final int FIRST_DAY_OF_WEEK = 0; // Sunday = 0, Monday = 1
		month.set(Calendar.DAY_OF_MONTH, 1);
		int lastDay = month.getActualMaximum(Calendar.DAY_OF_MONTH);
		int firstDay = (int) month.get(Calendar.DAY_OF_WEEK);

		ArrayList<Day> days = new ArrayList<Day>();
		int size;
		if (firstDay == 1) {
			size = lastDay + (FIRST_DAY_OF_WEEK * 6);
		} else {
			size = lastDay + firstDay - (FIRST_DAY_OF_WEEK + 1);
		}

		for (int i = 0; i < size; i++) {
			days.add(new Day("", DayType.NOT_ENABLE, isOrdered));
		}

		int j = FIRST_DAY_OF_WEEK;

		if (firstDay > 1) {
			for (j = 0; j < firstDay - FIRST_DAY_OF_WEEK; j++) {
				days.set(j, new Day("", DayType.NOT_ENABLE, isOrdered));
			}
		} else {
			for (j = 0; j < FIRST_DAY_OF_WEEK * 6; j++) {
				days.set(j, new Day("", DayType.NOT_ENABLE, isOrdered));
			}
			j = FIRST_DAY_OF_WEEK * 6 + 1; // sunday => 1, monday => 7
		}

		int dayNumber = 1;

		for (int i = j - 1; i < days.size(); i++) {
			DayType type;
			if (month.get(Calendar.YEAR) == curYear && month.get(Calendar.MONTH) == monthOfYear) {
				if (dayNumber >= dayOfMonth && dayNumber < dayOfMonth + passDays) {
					type = DayType.ENABLE;
					if (dayNumber == dayOfMonth) {
						type = DayType.TODAY;
						if (orderDay == null) {
							isOrdered = true;
						} else {
							isOrdered = false;
						}
					} else if (dayNumber == dayOfMonth + 1) {
						type = DayType.TOMORROW;
					} else if (dayNumber == dayOfMonth + 2) {
						type = DayType.T_D_A_T;
					}
				} else {
					type = DayType.NOT_ENABLE;
				}
			} else {
				if (dayNumber <= passDays) {
					type = DayType.ENABLE;
					// 明天/后天在下个月
					int remainDays = getDaysInMonth(monthOfYear, curYear) - dayOfMonth;
					if (remainDays < 2 && dayNumber <= 2 && Math.abs(month.get(Calendar.MONTH) - monthOfYear) == 1
							&& month.get(Calendar.YEAR) == curYear) {
						if (remainDays == 1) {
							if (dayNumber == 1) {
								type = DayType.T_D_A_T;
							}
						} else if (remainDays == 0) {
							if (dayNumber == 1) {
								type = DayType.TOMORROW;
							} else if (dayNumber == 2) {
								type = DayType.T_D_A_T;
							}
						}
					}
				} else {
					type = DayType.NOT_ENABLE;
				}
			}
			if (orderInfo != null && orderInfo.length == 3 && Integer.valueOf(orderInfo[0]) == month.get(Calendar.YEAR)
					&& Integer.valueOf(orderInfo[1]) == (month.get(Calendar.MONTH) + 1)
					&& Integer.valueOf(orderInfo[2]) == dayNumber) {
				isOrdered = true;
			} else {
				isOrdered = false;
			}

			days.set(i, new Day("" + dayNumber, type, isOrdered));
			dayNumber++;
		}
		return days;
	}
}