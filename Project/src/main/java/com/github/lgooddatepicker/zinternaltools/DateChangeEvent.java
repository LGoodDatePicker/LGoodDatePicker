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

import com.github.lgooddatepicker.components.DatePicker;
import java.time.LocalDate;

/**
 * DateChangeEvent, An instance of this event class is passed to each registered DateChangeListener,
 * each time that the date in a date picker changes.
 */
public class DateChangeEvent {

  /** Constructor. */
  public DateChangeEvent(DatePicker source, LocalDate oldDate, LocalDate newDate) {
    this.source = source;
    this.oldDate = oldDate;
    this.newDate = newDate;
  }

  /** source, This is the date picker that generated the event. */
  private DatePicker source;

  /** oldDate, This holds the value of the DatePicker date, before the date changed. */
  private LocalDate oldDate;

  /** newDate, This holds the value of the DatePicker date, after the date changed. */
  private LocalDate newDate;

  /** getSource, Returns the date picker that generated the event. */
  public DatePicker getSource() {
    return source;
  }

  /**
   * getOldDate, Returns the previous value of the DatePicker date. This is the value that existed
   * before the date was changed.
   */
  public LocalDate getOldDate() {
    return oldDate;
  }

  /**
   * getNewDate, Returns the new value of the DatePicker date. This is the value that currently
   * exists. (Put in another way, this is the value that exists after the date was changed.)
   */
  public LocalDate getNewDate() {
    return newDate;
  }
}
