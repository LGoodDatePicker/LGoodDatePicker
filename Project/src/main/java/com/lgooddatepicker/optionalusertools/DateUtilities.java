package com.lgooddatepicker.optionalusertools;

import java.time.LocalDate;

/**
 * DateUtilities, This is a set of date or date picker related utilities that may be useful to
 * developers using this project.
 */
public class DateUtilities {

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
}
