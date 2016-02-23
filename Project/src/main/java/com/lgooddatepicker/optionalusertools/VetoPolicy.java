package com.lgooddatepicker.optionalusertools;

import java.time.LocalDate;

/**
 * VetoPolicy, A VetoPolicy can be implemented to prevent certain dates from being selected in your
 * date picker. A vetoed date cannot be selected by typing in the date manually, and cannot be
 * selected by using the mouse. See the demo class for an example of implementing a VetoPolicy.
 */
public interface VetoPolicy {

    /**
     * isDateVetoed, Implement this function to indicate which dates are vetoed. (Which dates can
     * not be selected). Return true to indicate that a date is vetoed. Return false to indicate a
     * date that is not vetoed.
     *
     * Dates that are passed to this function will never be null.
     */
    public boolean isDateVetoed(LocalDate date);

}
