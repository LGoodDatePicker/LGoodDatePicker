package com.lgooddatepicker.optionalusertools;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
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
     * localDateToString, This returns the supplied date in the ISO-8601 format (uuuu-MM-dd).
     * Non-empty AD dates are a fixed length of 10 characters. Any BC dates will have 11 characters,
     * due to the addition of a minus sign before the year. If the date is null, this will return an
     * empty string ("").
     */
    static public String localDateToString(LocalDate date) {
        return localDateToString(date, "");
    }

    /**
     * localDateToString, This returns the supplied date in the ISO-8601 format (uuuu-MM-dd).
     * Non-empty AD dates are a fixed length of 10 characters. Any BC dates will have 11 characters,
     * due to the addition of a minus sign before the year. If the date is null, this will return
     * the value of emptyDateString.
     */
    static public String localDateToString(LocalDate date, String emptyDateString) {
        return (date == null) ? emptyDateString : date.toString();
    }

}
