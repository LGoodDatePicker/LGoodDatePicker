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

import com.github.lgooddatepicker.zinternaltools.DateTimeChangeEvent;

/**
 * DateTimeChangeListener, This interface can be implemented to create a DateTimeChangeListener. Any
 * DateTimeChangeListeners that are registered with a DateTimePicker will be notified each time that
 * there is a change in the date value, the time value, or both values. Note that there is a
 * difference between the values stored in a DatePicker and TimePicker, and their text. This class
 * listens for changes to the "last valid date" and "last valid time" values, but does not listen
 * for changes in the text.
 */
public interface DateTimeChangeListener {

  /**
   * dateOrTimeChanged, This function will be called each time that there is a change in the
   * DateTimePicker date value, the time value, or both values.
   */
  public void dateOrTimeChanged(DateTimeChangeEvent event);
}
