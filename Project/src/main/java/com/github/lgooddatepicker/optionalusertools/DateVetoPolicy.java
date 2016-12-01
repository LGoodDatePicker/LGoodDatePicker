package com.github.lgooddatepicker.optionalusertools;

import java.time.*;

/**
 * DateVetoPolicy,
 *
 * A DateVetoPolicy can be implemented to prevent specified non-null dates from being selected in
 * your date picker. A vetoed date cannot be selected by typing in the date manually, and cannot be
 * selected by using the mouse. See the demo class for an example of implementing a DateVetoPolicy.
 *
 * You can choose whether or not empty dates (null dates) are allowed by using the setting called
 * "DatePickerSettings.allowEmptyDates".
 *
 * If a programmer sets a date using DatePicker.setDate(), that date will be checked against the
 * veto policy just like a date that is entered by the user. If a vetoed date is set by the
 * programmer, then the vetoed date will be displayed in the text field (using the vetoed font), but
 * that date will not be committed to the "last valid date". A similar behavior is applied for the
 * DatePickerSettings.initialDate value.
 *
 * Veto policies are only enforced at the moment that the user or the programmer sets the date
 * picker to a new date. Veto policies are specifically -not- enforced at the time that
 * DatePicker.getDate() is called.
 *
 * Additional details:
 *
 * In certain situations, it is possible for the "last valid date" value which is stored in the date
 * picker, to be considered "vetoed" by a currently set current veto policy. This can happen if the
 * veto policy is replaced after the date is set. This can also happen if a veto policy is written
 * with rules that can be modified at runtime, and those rules are changed after the date is set.
 *
 * If you wish to verify (at any particular moment) that a date is allowed by the current rules of
 * the date picker, you can call the function DatePicker.isDateAllowed(someDate). To check the value
 * returned by getDate() against the current rules, you can call isDateAllowed(getDate()).
 *
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
