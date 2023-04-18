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
package com.github.lgooddatepicker.durationpicker_underconstruction;

import java.time.Duration;
import java.util.ArrayList;
import javax.swing.JComboBox;

/**
 * DurationPicker, This component allows a user to select a duration. Durations can be entered using
 * the mouse or keyboard.
 *
 * <p>The picker can parse multiple formats. For example, when using English language units, all of
 * the following strings would be successfully parsed and stored as a duration: "1 minute", "1m", "2
 * hours", "2.732 hours", "5 days", "4 weeks", "3 months", "2 years". Even though multiple formats
 * can be parsed by the duration picker, all durations are converted to a standard format for
 * display.
 *
 * <p>The duration picker will only display one unit at a time. For example, the duration picker may
 * display "9 days", but it will never display "1 week, 2 days".
 *
 * <p>Actual months vary in length by month-of-year. Within the context of the "Duration" and
 * "DurationPicker" classes, the month and year units are defined using an average, fixed estimated
 * length. These estimates are supplied by the class "java.time.temporal.ChronoUnit". The estimated
 * duration of a year is 365.2425 Days (31556952L seconds). The estimated duration of a month is one
 * twelfth of 365.2425 Days (2629746L seconds).
 *
 * <p>When representing a duration as a string, the duration picker will choose the largest unit
 * that is available for use, that divides evenly into the duration that is being represented. For
 * example, 14 days can display as "2 weeks", but 15 days will display as "15 days" (or as a smaller
 * unit if the settings indicate that the days unit should not be used.) If fractional durations are
 * entered by the user, this can result in large, unwieldy numbers being displayed. For example, if
 * the user entered 3.632 hours, then the duration picker would display this as 13075 seconds.
 */
public class DurationPicker extends JComboBox {

  private DurationConverterSettings converterSettings;
  // Red border when text is in disallowed state.
  private boolean allowEmptyDuration = true;
  private boolean allowZeroDuration = false;
  private Duration allowMinimumDuration = Duration.ofSeconds(0);
  // 4,000,000 days is a little over 10,000 years.
  private Duration allowMaximumDuration = Duration.ofDays(4000000);

  public DurationPicker() {
    this(null);
    converterSettings = new DurationConverterSettings();
  }

  public DurationPicker(DurationConverterSettings converterSettings) {
    if (converterSettings == null) {
      converterSettings = new DurationConverterSettings();
    }
    this.converterSettings = converterSettings;
  }

  public void setDuration(Duration duration) {
    // Todo: implement setDuration().
  }

  public Duration getDuration() {
    return null;
    // Todo: implement getDuration().
  }

  public void setText(String text) {
    // Todo: implement setText().
  }

  public String getText() {
    return null;
    // Todo: implement getText().
  }

  public void setMenuItems(ArrayList<Duration> menuItemDurations) {
    // Todo: implement setMenuItems().
  }

  public ArrayList<Duration> getMenuItems() {
    return null;
    // Todo: implement getMenuItems().
  }
}
