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

import com.github.lgooddatepicker.zinternaltools.TimeChangeEvent;

/**
 * TimeChangeListener, This interface can be implemented to create a time change listener. Any time
 * change listeners that are registered with a TimePicker will be notified each time that the time
 * is changed. Note that there is a difference between a TimePicker's time value, and its text. This
 * class listens for changes to the "last valid time" value, but does not listen for changes in the
 * text.
 */
public interface TimeChangeListener {

  /**
   * timeChanged, This function will be called whenever the time in the applicable time picker has
   * changed. Both the old time, and the new time, are supplied in the event object. Note that
   * either parameter may contain null, which represents a cleared or empty time.
   */
  public void timeChanged(TimeChangeEvent event);
}
