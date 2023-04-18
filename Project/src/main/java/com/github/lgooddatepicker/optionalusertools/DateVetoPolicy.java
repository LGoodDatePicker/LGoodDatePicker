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

import java.time.LocalDate;

/**
 * DateVetoPolicy,
 *
 * <p>A DateVetoPolicy can be implemented to prevent specified non-null dates from being selected in
 * your date picker. A vetoed date cannot be selected by typing in the date manually, and cannot be
 * selected by using the mouse. See the demo class for an example of implementing a DateVetoPolicy.
 *
 * <p>You can choose whether or not empty dates (null dates) are allowed by using the setting called
 * "DatePickerSettings.allowEmptyDates".
 *
 * <p>If a programmer sets a date using DatePicker.setDate(), that date will be checked against the
 * veto policy just like a date that is entered by the user. If a vetoed date is set by the
 * programmer, then the vetoed date will be displayed in the text field (using the vetoed font), but
 * that date will not be committed to the "last valid date". A similar behavior is applied for the
 * DatePickerSettings.initialDate value.
 *
 * <p>Veto policies are only enforced at the moment that the user or the programmer sets the date
 * picker to a new date. Veto policies are specifically -not- enforced at the time that
 * DatePicker.getDate() is called.
 *
 * <p>Additional details:
 *
 * <p>In certain situations, it is possible for the "last valid date" value which is stored in the
 * date picker, to be considered "vetoed" by a currently set current veto policy. This can happen if
 * the veto policy is replaced after the date is set. This can also happen if a veto policy is
 * written with rules that can be modified at runtime, and those rules are changed after the date is
 * set.
 *
 * <p>If you wish to verify (at any particular moment) that a date is allowed by the current rules
 * of the date picker, you can call the function DatePicker.isDateAllowed(someDate). To check the
 * value returned by getDate() against the current rules, you can call isDateAllowed(getDate()).
 */
public interface DateVetoPolicy {

  /**
   * isDateAllowed, Implement this function to indicate which dates are allowed, and which ones are
   * vetoed. Vetoed dates can not be selected with the keyboard or mouse. Return true to indicate
   * that a date is allowed, or return false to indicate that a date is vetoed.
   *
   * <p>To disallow empty dates, set "DatePickerSettings.allowEmptyDates" to false.
   *
   * <p>The value of null will never be passed to this function, under any case.
   */
  public boolean isDateAllowed(LocalDate date);
}
