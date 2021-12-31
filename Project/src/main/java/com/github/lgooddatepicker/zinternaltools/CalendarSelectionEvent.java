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
import java.time.LocalDate;

/**
 * CalendarSelectionEvent, An instance of this event class is passed to each registered
 * CalendarListener, each time that a date is selected in a CalendarPanel.
 */
public class CalendarSelectionEvent {

  /** Constructor. */
  public CalendarSelectionEvent(CalendarPanel source, LocalDate newDate, LocalDate oldDate) {
    this.source = source;
    this.newDate = newDate;
    this.oldDate = oldDate;
  }

  /** source, This is the calendar panel that generated the event. */
  private CalendarPanel source;

  /** newDate, This holds the value of the new selected date. */
  private LocalDate newDate;

  /** oldDate, This holds the value of the old selected date. */
  private LocalDate oldDate;

  /** getSource, Returns the calendar panel that generated the event. */
  public CalendarPanel getSource() {
    return source;
  }

  /** getNewDate, Returns the new selected date. */
  public LocalDate getNewDate() {
    return newDate;
  }

  /** getOldDate, Returns the old selected date. */
  public LocalDate getOldDate() {
    return oldDate;
  }

  /**
   * isDuplicate, Returns true if the new date is the same as the old date, or if both values are
   * null. Otherwise returns false.
   */
  public boolean isDuplicate() {
    return (PickerUtilities.isSameLocalDate(newDate, oldDate));
  }
}
