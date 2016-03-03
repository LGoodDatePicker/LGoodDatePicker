package com.lgooddatepicker.optionalusertools;

import java.time.LocalDate;

/**
 * HighlightPolicy, A highlight policy can be implemented to highlight certain dates in your date
 * picker. A highlight policy might be used to visually indicate holidays, or weekends, or other
 * significant days. See the demo class for an example of implementing a HighlightPolicy.
 */
public interface DateHighlightPolicy {

    /**
     * getHighlightStringOrNull, Implement this function to indicate if a date should be
     * highlighted, or optionally also display tool tip in the calendar when the mouse pointer is
     * over that date.
     *
     * To give a date a tooltip, return the desired tooltip text. To highlight a date without giving
     * it a tooltip, return an empty string (""). If a date should not be highlighted, return null.
     *
     * Dates that are passed to this function will never be null.
     */
    abstract public String getHighlightStringOrNull(LocalDate date);

}
