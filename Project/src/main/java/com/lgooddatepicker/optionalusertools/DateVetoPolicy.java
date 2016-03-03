package com.lgooddatepicker.optionalusertools;

import java.time.LocalDate;

/**
 * DateVetoPolicy, A DateVetoPolicy can be implemented to prevent specified non-null dates from
 * being selected in your date picker. A vetoed date cannot be selected by typing in the date
 * manually, and cannot be selected by using the mouse. See the demo class for an example of
 * implementing a VetoPolicy.
 *
 * You can choose whether or not empty dates (null dates) are allowed by using the variable
 * "DatePickerSettings.allowEmptyDates".
 */
public interface DateVetoPolicy {

    /**
     * isDateAllowed, Implement this function to indicate which dates are allowed, and which ones
     * are vetoed. Vetoed dates can not be selected with the keyboard or mouse. Return true to
     * indicate that a date is allowed, or return false to indicate that a date is vetoed.
     *
     * The initial date contained in a date picker is not influenced by the veto policy. The initial
     * date can be set with "DatePickerSettings.initialDate".
     *
     * To disallow empty dates, set "DatePickerSettings.allowEmptyDates" to false.
     *
     * The value of null will never be passed to this function, under any case.
     */
    public boolean isDateAllowed(LocalDate date);

}
