package com.lgooddatepicker.optionalusertools;

import java.time.LocalDate;

/**
 * DateVetoPolicy, A DateVetoPolicy can be implemented to prevent specified non-null dates from
 * being selected in your date picker. A vetoed date cannot be selected by typing in the date
 * manually, and cannot be selected by using the mouse. See the demo class for an example of
 * implementing a DateVetoPolicy.
 *
 * Programmatically set dates are processed by the veto policy, just like dates that are entered by
 * the user. If a vetoed date is set by using DatePicker.setDate(), then the vetoed date will be
 * displayed in the text field (using the vetoed font), but will not be committed to the "last valid
 * date". This is also true of the DatePickerSettings.initialDate value.
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
     * To disallow empty dates, set "DatePickerSettings.allowEmptyDates" to false.
     *
     * The value of null will never be passed to this function, under any case.
     */
    public boolean isDateAllowed(LocalDate date);

}
