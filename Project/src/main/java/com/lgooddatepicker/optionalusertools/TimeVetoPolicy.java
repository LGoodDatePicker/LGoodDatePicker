package com.lgooddatepicker.optionalusertools;

import java.time.LocalTime;

/**
 * TimeVetoPolicy, A TimeVetoPolicy can be implemented to prevent specified non-null times from
 * being selected in your time picker. A vetoed time cannot be selected by typing in the time
 * manually, and cannot be selected by using the mouse. Also, vetoed times will not be added to the
 * time drop down menu. See the demo class for an example of implementing a TimeVetoPolicy.
 *
 * Programmatically set times are processed by the veto policy, just like times that are entered by
 * the user. If a vetoed time is set by using TimePicker.setTime(), then the vetoed time will be
 * displayed in the text field (using the vetoed font), but will not be committed to the "last valid
 * time". This is also true of the TimePickerSettings.initialTime value.
 *
 * When implementing a veto policy, if you wish to check to see if a particular time is inside of a
 * specified range, use the function PickerUtilities.isLocalTimeInRange(). This function can be set
 * to be inclusive or exclusive, of the minimum and maximum values.
 *
 * You can choose whether or not empty times (null times) are allowed by using the variable
 * "TimePickerSettings.allowEmptyTimes".
 */
public interface TimeVetoPolicy {

    /**
     * isTimeAllowed, Implement this function to indicate which times are allowed, and which ones
     * are vetoed. A vetoed time cannot be selected by typing in the time manually, and cannot be
     * selected by using the mouse. Also, vetoed times will not be added to the time drop down menu.
     * Return true to indicate that a time is allowed, or return false to indicate that a time is
     * vetoed.
     *
     * To disallow empty times, set "TimePickerSettings.allowEmptyTimes" to false.
     *
     * The value of null will never be passed to this function, under any case.
     */
    public boolean isTimeAllowed(LocalTime time);
}
