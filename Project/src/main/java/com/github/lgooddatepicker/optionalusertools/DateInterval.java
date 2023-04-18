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

/** DateInterval, This class represents an interval between two dates. */
public class DateInterval {

  /** firstDate, This is the first date in the interval. */
  public LocalDate firstDate = null;

  /** lastDate, This is the last date in the interval. */
  public LocalDate lastDate = null;

  /**
   * Constructor (Empty), This will create an empty DateInterval instance. An empty date interval
   * has both dates set to null.
   */
  public DateInterval() {}

  /** Constructor (Normal), This will create a date interval using the supplied dates. */
  public DateInterval(LocalDate intervalStart, LocalDate intervalEnd) {
    this.firstDate = intervalStart;
    this.lastDate = intervalEnd;
  }

  /** isEmpty, This will return true if both dates are null. Otherwise, this returns false. */
  public boolean isEmpty() {
    return ((firstDate == null) && (lastDate == null));
  }
}
