/***
 * Project:             Date Processing
 * Created by:          Bao Nguyen (C)
 * Last date modified:  06/18/2017
 *
 * Class: DateProcessing
 *
 * "Behind the scene", "the brain" of the program.
 * Functionality:
 *      1. Calculate the days elapsed between 2 dates
 *      2. Add day(s) to a given date and return the result date
 *      3. Validate date
 *          3a. Valid February for leap years
 *          3b. Correct days and months
 */

package sample;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateProcessing {

    private static final DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
    public static final String DAY = "DAY";
    public static final String MONTH = "MONTH";
    public static final String YEAR = "YEAR";

    public static String getDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return dateFormat.format(calendar.getTime());
    }

    public static Date parseDate(String dateString) throws ParseException {
        return dateFormat.parse(dateString);
    }

    public static String getDate() {
        Calendar calendar = Calendar.getInstance();
        return dateFormat.format(calendar.getTime());
    }

    /***
     * method: getDuration
     * calculate the day(s) elapsed between 2 dates
     *
     * @param date1: first date
     * @param date2: second date
     * @param includeEndDate: if count the end date to the duration
     * @return the days elapsed
     */
    public static int getDuration(Date date1, Date date2, boolean includeEndDate, String timeUnit) {
        Calendar startCalendar = new GregorianCalendar();
        startCalendar.setTime(date1.before(date2) ? date1 : date2);
        Calendar endCalendar = new GregorianCalendar();
        endCalendar.setTime(date2.after(date1) ? date2 : date1);
        if(includeEndDate) {
            endCalendar.add(Calendar.DATE, 1);
        }

        int durationInYears = endCalendar.get(Calendar.YEAR) - startCalendar.get(Calendar.YEAR);
        int durationInMonths = durationInYears * 12 + endCalendar.get(Calendar.MONTH) - startCalendar.get(Calendar.MONTH);
        int durationInDays = (int) ((endCalendar.getTime().getTime() - startCalendar.getTime().getTime())/(1000 * 60 * 60 * 24));

        // final months difference
        if(startCalendar.get(Calendar.DATE) > endCalendar.get(Calendar.DATE)) {
            durationInMonths--;
        }

        // final years difference
        if (startCalendar.get(Calendar.MONTH) > endCalendar.get(Calendar.MONTH) ||
                (startCalendar.get(Calendar.MONTH) == endCalendar.get(Calendar.MONTH) &&
                        startCalendar.get(Calendar.DATE) > endCalendar.get(Calendar.DATE))) {
            durationInYears--;
        }

        switch (timeUnit) {
            case DAY: return durationInDays;
            case MONTH: return durationInMonths;
            case YEAR: return durationInYears;
            default: return 0;  // never execute
        }
    }


    /***
     * method: modifyDate
     * add/subtract day(s) from a given date and return the new date
     *
     * @param date: given date
     * @param duration: days elapsed between 2 dates
     * @param includeEndDate: if count the end date to the duration
     * @return the new date
     */
    public static Date modifyDate(Date date, int duration, boolean includeEndDate, String timeUnit) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        switch (timeUnit) {
            case DAY: calendar.add(Calendar.DATE, duration); break;
            case MONTH: calendar.add(Calendar.MONTH, duration); break;
            case YEAR: calendar.add(Calendar.YEAR, duration); break;
            default: break;     // never execute
        }

        if(includeEndDate) {
            if(duration > 0) {
                calendar.add(Calendar.DATE, -1);
            }
            else if(duration < 0) {
                calendar.add(Calendar.DATE, 1);
            }
        }

        return calendar.getTime();
    }


    /***
     * method: isLeapYear
     * check to see if the given year is leap year.
     * "Every year that is exactly divisible by four is a leap year, except for years that are exactly divisible by 100,
     * but these centurial years are leap years if they are exactly divisible by 400."
     *
     * @param year: year to check
     * @return true if leap year, false otherwise
     */
    private static boolean isLeapYear(int year) {
        return year % 4 == 0 && (year % 100 != 0 || year % 400 == 0);
    }


    /***
     * method: isValidDate
     * check to see if the given date is valid
     *
     * @param dateString: string representation of date
     * @return true of valid date, false otherwise
     * @throws ParseException: wrong date format
     */
    public static boolean isValidDate(String dateString) throws ParseException {
        // check if the date is in the format mm/dd/yyyy
        if(!dateString.matches("\\d{2}/\\d{2}/\\d{4}")) {
            throw new ParseException("Parse exception", 0);
        }

        String[] dateComponents = dateString.split("/");
        int month = Integer.parseInt(dateComponents[0]);
        int day = Integer.parseInt(dateComponents[1]);
        int year = Integer.parseInt(dateComponents[2]);
        boolean validDate = false;

        if(month >= 1 && month <= 12 && day > 0) {
            if(month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {
                if(day <= 31) {
                    validDate = true;
                }
            }
            else if(month == 2) {
                if(isLeapYear(year) && day <= 29) {
                    validDate = true;
                }
                else if(!isLeapYear(year) && day <= 28) {
                    validDate = true;
                }
            }
            else {
                if(day <= 30) {
                    validDate = true;
                }
            }
        }

        return validDate;
    }
}
