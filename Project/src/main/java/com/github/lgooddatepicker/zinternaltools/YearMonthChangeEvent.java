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
package com.github.lgooddatepicker.zinternaltools;

import com.github.lgooddatepicker.components.CalendarPanel;
import com.github.lgooddatepicker.optionalusertools.PickerUtilities;
import java.time.YearMonth;

/**
 * YearMonthChangeEvent, An instance of this event class is passed to each registered
 * CalendarListener, each time that the YearMonth may have been changed in a CalendarPanel.
 * Developers should call isDuplicate() to find out if the new YearMonth, is the same or different
 * from the old YearMonth.
 */
public class YearMonthChangeEvent {

  /** Constructor. */
  public YearMonthChangeEvent(
      CalendarPanel source, YearMonth newYearMonth, YearMonth oldYearMonth) {
    this.source = source;
    this.newYearMonth = newYearMonth;
    this.oldYearMonth = oldYearMonth;
  }

  /** source, This is the calendar panel that generated the event. */
  private CalendarPanel source;

  /** newYearMonth, This holds the value of the new YearMonth. */
  private YearMonth newYearMonth;

  /** oldYearMonth, This holds the value of the old YearMonth. */
  private YearMonth oldYearMonth;

  /** getSource, Returns the calendar panel that generated the event. */
  public CalendarPanel getSource() {
    return source;
  }

  /** getNewYearMonth, Returns the new YearMonth. This will never return null. */
  public YearMonth getNewYearMonth() {
    return newYearMonth;
  }

  /** getOldYearMonth, Returns the old YearMonth. This will never return null. */
  public YearMonth getOldYearMonth() {
    return oldYearMonth;
  }

  /**
   * isDuplicate, Returns true if the new YearMonth is the same as the old YearMonth. Otherwise
   * returns false.
   */
  public boolean isDuplicate() {
    return (PickerUtilities.isSameYearMonth(newYearMonth, oldYearMonth));
  }
}
