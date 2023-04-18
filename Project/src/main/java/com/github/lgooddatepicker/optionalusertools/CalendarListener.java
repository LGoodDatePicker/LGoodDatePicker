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

import com.github.lgooddatepicker.zinternaltools.CalendarSelectionEvent;
import com.github.lgooddatepicker.zinternaltools.YearMonthChangeEvent;

/**
 * CalendarListener, This interface can be implemented to create a calendar listener. Any calendar
 * listeners that are registered with a CalendarPanel will be notified each time that a calendar
 * date is selected, or if the YearMonth is changed.
 */
public interface CalendarListener {

  /**
   * selectedDateChanged, This function will be called each time that a date is selected in the
   * applicable CalendarPanel. The selected date is supplied in the event object. Note that the
   * selected date may contain null, which represents a cleared or empty date.
   *
   * <p>This function will be called each time that the user selects any date or the clear button in
   * the calendar, even if the same date value is selected twice in a row. All duplicate selection
   * events are marked as duplicates in the event object.
   */
  public void selectedDateChanged(CalendarSelectionEvent event);

  /**
   * yearMonthChanged, This function will be called each time that the YearMonth may have changed in
   * the applicable CalendarPanel. The selected YearMonth is supplied in the event object.
   *
   * <p>This function will be called each time that the user makes any change that requires
   * redrawing the calendar, even if the same YearMonth value is displayed twice in a row. All
   * duplicate yearMonthChanged events are marked as duplicates in the event object.
   *
   * <p>Implementation Note: This is called each time the calendar is redrawn.
   */
  public void yearMonthChanged(YearMonthChangeEvent event);
}
