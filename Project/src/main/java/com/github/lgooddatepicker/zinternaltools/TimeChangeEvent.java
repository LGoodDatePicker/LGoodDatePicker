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

import com.github.lgooddatepicker.components.TimePicker;
import java.time.LocalTime;

/**
 * TimeChangeEvent, An instance of this event class is passed to each registered TimeChangeListener,
 * each time that the time in a time picker changes.
 */
public class TimeChangeEvent {

  /** Constructor. */
  public TimeChangeEvent(TimePicker source, LocalTime oldTime, LocalTime newTime) {
    this.source = source;
    this.oldTime = oldTime;
    this.newTime = newTime;
  }

  /** source, This is the time picker that generated the event. */
  private TimePicker source;

  /** oldTime, This holds the value of the TimePicker time, before the time changed. */
  private LocalTime oldTime;

  /** newTime, This holds the value of the TimePicker time, after the time changed. */
  private LocalTime newTime;

  /** getSource, Returns the time picker that generated the event. */
  public TimePicker getSource() {
    return source;
  }

  /**
   * getOldTime, Returns the previous value of the TimePicker time. This is the value that existed
   * before the time was changed.
   */
  public LocalTime getOldTime() {
    return oldTime;
  }

  /**
   * getNewTime, Returns the new value of the TimePicker time. This is the value that currently
   * exists. (Put in another way, this is the value that exists after the time was changed.)
   */
  public LocalTime getNewTime() {
    return newTime;
  }
}
