package com.github.lgooddatepicker.optionalusertools;

import java.time.LocalDate;

/**
 * DateInterval, This class represents an interval between two dates.
 */
public class DateInterval {

    /**
     * firstDate, This is the first date in the interval.
     */
    public LocalDate firstDate = null;

    /**
     * lastDate, This is the last date in the interval.
     */
    public LocalDate lastDate = null;

    /**
     * Constructor (Empty), This will create an empty DateInterval instance. An empty date interval
     * has both dates set to null.
     */
    public DateInterval() {
    }

    /**
     * Constructor (Normal), This will create a date interval using the supplied dates.
     */
    public DateInterval(LocalDate intervalStart, LocalDate intervalEnd) {
        this.firstDate = intervalStart;
        this.lastDate = intervalEnd;
    }

    /**
     * isEmpty, This will return true if both dates are null. Otherwise, this returns false.
     */
    public boolean isEmpty() {
        return ((firstDate == null) && (lastDate == null));
    }
}
