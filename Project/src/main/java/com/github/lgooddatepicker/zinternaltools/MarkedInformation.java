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

import java.awt.*;

/**
 * MarkedInformation, Instances of this class are returned from a HighlightPolicy to indicate
 * that a date should be marked. When a date should not be marked, then the
 * HighlightPolicy should return null.
 *
 * <p>You may (optionally) fill out the fields in the MarkedInformation class to give any
 * particular marked day a unique color, or tooltip text. If the
 * color fields are null, then the default colors will be used. If the tooltip field is
 * null (or empty), then no tooltip will be displayed.
 */
public class MarkedInformation {

  /**
   * color, This will be used as the background on the marked date. If this field is
   * null, then a default color will be used. The default color is retrieved from
   * "DatePickerSettings.colorBackgroundMarkedDates". (If the default has not been changed, it
   * will be "Color.ORANGE".)
   */
  public Color color = null;

  /**
   * tooltipText, This text will be displayed as a "hover tooltip" on the highlighted date. If this
   * field is null or empty, then no tooltip will be displayed.
   */
  public String tooltipText = null;

  /** Constructor, Default. */
  public MarkedInformation() {
    this(null, null);
  }

  /** Constructor, With a background color only. */
  public MarkedInformation(Color color) {
    this(color, null);
  }


  /** Constructor, With all fields. */
  public MarkedInformation(Color color, String tooltipText) {
    this.color = color;
    this.tooltipText = tooltipText;
  }
}
