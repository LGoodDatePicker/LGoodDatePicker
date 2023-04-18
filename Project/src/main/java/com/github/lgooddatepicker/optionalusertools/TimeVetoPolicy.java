/*
 * The MIT License
 *
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.github.lgooddatepicker.optionalusertools;

import java.time.LocalTime;

/**
 * TimeVetoPolicy,
 *
 * <p>A TimeVetoPolicy can be implemented to prevent specified non-null times from being selected in
 * your time picker. A vetoed time cannot be selected by typing in the time manually, and cannot be
 * selected by using the mouse. Also, vetoed times will not be added to the time drop down menu. See
 * the demo class for an example of implementing a TimeVetoPolicy.
 *
 * <p>When implementing a veto policy, if you wish to check to see if a particular time is inside of
 * a specified range, use the function PickerUtilities.isLocalTimeInRange(). This function can be
 * set to be inclusive or exclusive, of the minimum and maximum values.
 *
 * <p>You can choose whether or not empty times (null times) are allowed by using the setting called
 * "TimePickerSettings.allowEmptyTimes".
 *
 * <p>If a programmer sets a time using TimePicker.setTime(), that time will be checked against the
 * veto policy just like a time that is entered by the user. If a vetoed time is set by the
 * programmer, then the vetoed time will be displayed in the text field (using the vetoed font), but
 * that time will not be committed to the "last valid time". A similar behavior is applied for the
 * TimePickerSettings.initialTime value.
 *
 * <p>Veto policies are only enforced at the moment that the user or the programmer sets the time
 * picker to a new time. Veto policies are specifically -not- enforced at the time that
 * TimePicker.getTime() is called.
 *
 * <p>Additional details:
 *
 * <p>In certain situations, it is possible for the "last valid time" value which is stored in the
 * time picker, to be considered "vetoed" by a currently set current veto policy. This can happen if
 * the veto policy is replaced after the time is set. This can also happen if a veto policy is
 * written with rules that can be modified at runtime, and those rules are changed after the time is
 * set.
 *
 * <p>If you wish to verify (at any particular moment) that a time is allowed by the current rules
 * of the time picker, you can call the function TimePicker.isTimeAllowed(someTime). To check the
 * value returned by getTime() against the current rules, you can call isTimeAllowed(getTime()).
 */
public interface TimeVetoPolicy {

  /**
   * isTimeAllowed, Implement this function to indicate which times are allowed, and which ones are
   * vetoed. A vetoed time cannot be selected by typing in the time manually, and cannot be selected
   * by using the mouse. Also, vetoed times will not be added to the time drop down menu. Return
   * true to indicate that a time is allowed, or return false to indicate that a time is vetoed.
   *
   * <p>To disallow empty times, set "TimePickerSettings.allowEmptyTimes" to false.
   *
   * <p>The value of null will never be passed to this function, under any case.
   */
  public boolean isTimeAllowed(LocalTime time);
}
