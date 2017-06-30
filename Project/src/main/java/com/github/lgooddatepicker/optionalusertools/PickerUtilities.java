package com.github.lgooddatepicker.optionalusertools;

import java.time.*;
import java.time.format.*;
import java.time.chrono.*;
import java.time.temporal.*;
import java.util.Locale;

/**
 * DateUtilities, This is a set of date or date picker related utilities that may be useful to
 * developers using this project.
 */
public class PickerUtilities {

    /**
     * createFormatterFromPatternString, This creates a DateTimeFormatter from the supplied pattern
     * string and supplied locale. The pattern will be created to be "lenient" and "case
     * insensitive", so it can be used for display or for user-friendly parsing. Information about
     * creating a pattern string can be found in the DateTimeFormatter class Javadocs. @see
     * <a href="https://docs.oracle.com/javase/8/docs/api/java/time/format/DateTimeFormatter.html">
     * The DateTimeFormatter Javadocs </a>
     *
     * Note: It is important to use the letter "u" (astronomical year) instead of "y" (year of era)
     * when creating pattern strings for BCE dates. This is because the DatePicker uses ISO 8601,
     * which specifies "Astronomical year numbering". (Additional details: The astronomical year
     * "-1" and "1 BC" are not the same thing. Astronomical years are zero-based, and BC dates are
     * one-based. Astronomical year "0", is the same year as "1 BC", and astronomical year "-1" is
     * the same year as "2 BC", and so forth.)
     */
    public static DateTimeFormatter createFormatterFromPatternString(
            String formatPattern, Locale locale) {
        DateTimeFormatter formatter = new DateTimeFormatterBuilder().parseLenient()
                .parseCaseInsensitive().appendPattern(formatPattern)
                .toFormatter(locale);
        return formatter;
    }

    /**
     * isLocalTimeInRange, This returns true if the specified value is inside of the specified
     * range. This returns false if the specified value is outside of the specified range. If the
     * specified value is null, then this will return false.
     *
     * If optionalMinimum is null, then it will be set to LocalTime.MIN. If optionalMaximum is null,
     * then it will be set to LocalTime.MAX.
     *
     * If inclusiveOfEndpoints is true, then values that equal the minimum or maximum will return
     * true. Otherwise, values that equal the minimum or maximum will return false.
     */
    public static boolean isLocalTimeInRange(LocalTime value,
            LocalTime optionalMinimum, LocalTime optionalMaximum, boolean inclusiveOfEndpoints) {
        // If either bounding time does does not already exist, then set it to the maximum range.
        LocalTime minimum = (optionalMinimum == null) ? LocalTime.MIN : optionalMinimum;
        LocalTime maximum = (optionalMaximum == null) ? LocalTime.MAX : optionalMaximum;
        // Null is never considered to be inside of a range.
        if (value == null) {
            return false;
        }
        // Return false if the range does not contain any times.
        if (maximum.isBefore(minimum) || maximum.equals(minimum)) {
            return false;
        }
        if (inclusiveOfEndpoints) {
            return ((value.isAfter(minimum) || value.equals(minimum))
                    && (value.isBefore(maximum) || value.equals(maximum)));
        } else {
            return (value.isAfter(minimum) && value.isBefore(maximum));
        }
    }

    /**
     * isSameLocalDate, This compares two date variables to see if their values are equal. Returns
     * true if the values are equal, otherwise returns false.
     *
     * More specifically: This returns true if both values are null (an empty date). Or, this
     * returns true if both of the supplied dates contain a date and represent the same date.
     * Otherwise this returns false.
     */
    static public boolean isSameLocalDate(LocalDate first, LocalDate second) {
        // If both values are null, return true.
        if (first == null && second == null) {
            return true;
        }
        // At least one value contains a date. If the other value is null, then return false.
        if (first == null || second == null) {
            return false;
        }
        // Both values contain dates. Return true if the dates are equal, otherwise return false.
        return first.isEqual(second);
    }

    /**
     * isSameYearMonth, This compares two YearMonth variables to see if their values are equal.
     * Returns true if the values are equal, otherwise returns false.
     *
     * More specifically: This returns true if both values are null (an empty YearMonth). Or, this
     * returns true if both of the supplied YearMonths contain a YearMonth and represent the same
     * year and month. Otherwise this returns false.
     */
    public static boolean isSameYearMonth(YearMonth first, YearMonth second) {
        // If both values are null, return true.
        if (first == null && second == null) {
            return true;
        }
        // At least one value contains a YearMonth. If the other value is null, then return false.
        if (first == null || second == null) {
            return false;
        }
        // Both values contain a YearMonth. 
        // Return true if the YearMonth are equal, otherwise return false.
        return first.equals(second);
    }

    public static boolean isSameLocalTime(LocalTime first, LocalTime second) {
        // If both values are null, return true.
        if (first == null && second == null) {
            return true;
        }
        // At least one value contains a time. If the other value is null, then return false.
        if (first == null || second == null) {
            return false;
        }
        // Both values contain times. Return true if the times are equal, otherwise return false.
        return first.equals(second);
    }

    /**
     * localDateTimeToString, This will return the supplied LocalDateTime as a string. If the value
     * is null, this will return the value of emptyTimeString. Time values will be output in the
     * same format as LocalDateTime.toString().
     *
     * Javadocs from LocalDateTime.toString():
     *
     * Outputs this date-time as a {@code String}, such as {@code 2007-12-03T10:15:30}.
     * <p>
     * The output will be one of the following ISO-8601 formats:
     * <ul>
     * <li>{@code uuuu-MM-dd'T'HH:mm}</li>
     * <li>{@code uuuu-MM-dd'T'HH:mm:ss}</li>
     * <li>{@code uuuu-MM-dd'T'HH:mm:ss.SSS}</li>
     * <li>{@code uuuu-MM-dd'T'HH:mm:ss.SSSSSS}</li>
     * <li>{@code uuuu-MM-dd'T'HH:mm:ss.SSSSSSSSS}</li>
     * </ul>
     * The format used will be the shortest that outputs the full value of the time where the
     * omitted parts are implied to be zero.
     */
    public static String localDateTimeToString(LocalDateTime value, String emptyTimeString) {
        return (value == null) ? emptyTimeString : value.toString();
    }

    /**
     * localDateTimeToString, This will return the supplied LocalDateTime as a string. If the value
     * is null, this will return an empty string (""). Time values will be output in the same format
     * as LocalDateTime.toString().
     *
     * Javadocs from LocalDateTime.toString():
     *
     * Outputs this date-time as a {@code String}, such as {@code 2007-12-03T10:15:30}.
     * <p>
     * The output will be one of the following ISO-8601 formats:
     * <ul>
     * <li>{@code uuuu-MM-dd'T'HH:mm}</li>
     * <li>{@code uuuu-MM-dd'T'HH:mm:ss}</li>
     * <li>{@code uuuu-MM-dd'T'HH:mm:ss.SSS}</li>
     * <li>{@code uuuu-MM-dd'T'HH:mm:ss.SSSSSS}</li>
     * <li>{@code uuuu-MM-dd'T'HH:mm:ss.SSSSSSSSS}</li>
     * </ul>
     * The format used will be the shortest that outputs the full value of the time where the
     * omitted parts are implied to be zero.
     */
    public static String localDateTimeToString(LocalDateTime value) {
        return (value == null) ? "" : value.toString();
    }

    /**
     * localDateToString, This returns the supplied date in the ISO-8601 format (uuuu-MM-dd). For
     * any CE years that are between 0 and 9999 inclusive, the output will have a fixed length of 10
     * characters. Years before or after that range will output longer strings. If the date is null,
     * this will return an empty string ("").
     */
    static public String localDateToString(LocalDate date) {
        return localDateToString(date, "");
    }

    /**
     * localDateToString, This returns the supplied date in the ISO-8601 format (uuuu-MM-dd). For
     * any CE years that are between 0 and 9999 inclusive, the output will have a fixed length of 10
     * characters. Years before or after that range will output longer strings. If the date is null,
     * this will return the value of emptyDateString.
     */
    static public String localDateToString(LocalDate date, String emptyDateString) {
        return (date == null) ? emptyDateString : date.toString();
    }

    /**
     * localTimeToString, This will return the supplied time as a string. If the time is null, this
     * will return an empty string ("").
     *
     * Time values will be output in one of the following ISO-8601 formats: "HH:mm", "HH:mm:ss",
     * "HH:mm:ss.SSS", "HH:mm:ss.SSSSSS", "HH:mm:ss.SSSSSSSSS".
     *
     * The format used will be the shortest that outputs the full value of the time where the
     * omitted parts are implied to be zero.
     * </code>
     */
    public static String localTimeToString(LocalTime time) {
        return (time == null) ? "" : time.toString();
    }

    /**
     * localTimeToString, This will return the supplied time as a string. If the time is null, this
     * will return the value of emptyTimeString.
     *
     * Time values will be output in one of the following ISO-8601 formats: "HH:mm", "HH:mm:ss",
     * "HH:mm:ss.SSS", "HH:mm:ss.SSSSSS", "HH:mm:ss.SSSSSSSSS".
     *
     * The format used will be the shortest that outputs the full value of the time where the
     * omitted parts are implied to be zero.
     * </code>
     */
    public static String localTimeToString(LocalTime time, String emptyTimeString) {
        return (time == null) ? emptyTimeString : time.toString();
    }

}
