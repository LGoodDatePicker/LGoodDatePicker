package com.lgooddatepicker.optionalusertools;

import java.time.LocalDate;

/**
 * DateUtilities, This is a set of date or date picker related utilities that may be useful to
 * developers using this project.
 */
public class DateUtilities {

    /**
     * isSameLocalDate, Compares two date variables to see if their values are equal. Returns true
     * if they are equal, false if they are not equal.
     *
     * More specifically: This returns true if both of the supplied dates represent the same date.
     * This returns true if both values are null (an empty date). Otherwise this returns false.
     */
    static public boolean isSameLocalDate(LocalDate first, LocalDate second) {
        if (first == null) {
            return (second == null);
        }
        return first.isEqual(second);
    }
}
