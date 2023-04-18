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

import com.github.lgooddatepicker.zinternaltools.HighlightInformation;
import java.time.LocalDate;

/**
 * HighlightPolicy, A highlight policy can be implemented to highlight certain dates in your
 * DatePicker or CalendarPanel. A highlight policy might be used to visually indicate holidays, or
 * weekends, or other significant days.
 *
 * <p>Each highlighted date may (optionally) have a unique background color, foreground color, and
 * tooltip text. If unique colors are not supplied, then the default highlighting colors will be
 * used instead. If a tooltip is not supplied, then no tooltip will be displayed. See the demo class
 * for an example of implementing a HighlightPolicy.
 */
public interface DateHighlightPolicy {

  /**
   * getHighlightInformationOrNull, Implement this function to indicate if a date should be
   * highlighted, and what highlighting details should be used for the highlighted date.
   *
   * <p>If a date should be highlighted, then return an instance of HighlightInformation. If the
   * date should not be highlighted, then return null.
   *
   * <p>You may (optionally) fill out the fields in the HighlightInformation class to give any
   * particular highlighted day a unique background color, foreground color, or tooltip text. If the
   * color fields are null, then the default highlighting colors will be used. If the tooltip field
   * is null (or empty), then no tooltip will be displayed.
   *
   * <p>Dates that are passed to this function will never be null.
   */
  public abstract HighlightInformation getHighlightInformationOrNull(LocalDate date);
}
