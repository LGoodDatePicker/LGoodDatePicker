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
package com.github.lgooddatepicker.ysandbox;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;
import com.github.lgooddatepicker.optionalusertools.DateHighlightPolicy;
import com.github.lgooddatepicker.optionalusertools.DateVetoPolicy;
import com.github.lgooddatepicker.zinternaltools.HighlightInformation;
import java.awt.Color;
import java.awt.GraphicsEnvironment;
import java.time.DayOfWeek;
import java.time.LocalDate;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

/**
 * testStart, This is a class used to test various functions while programming. This class is not
 * involved with the normal operation of the date pickers.
 */
public class TestStart {

  public static void main(String[] args) {

    ///////////////////////////////////////////////////////////////////////////////////////////
    // Create a frame, a panel, and our demo buttons.
    JFrame frame = new JFrame();
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    JPanel panel = new JPanel();
    frame.getContentPane().add(panel);

    DatePicker datePicker;
    DatePickerSettings dateSettings;

    // Create a CalendarPanel: With highlight policy.
    dateSettings = new DatePickerSettings();
    datePicker = new DatePicker(dateSettings);
    dateSettings.setVisibleDateTextField(false);
    dateSettings.setGapBeforeButtonPixels(0);
    datePicker.setBorder(LineBorder.createBlackLineBorder());
    panel.add(datePicker);
    /*
    datePicker = new DatePicker(dateSettings);
    panel.add(datePicker);

    datePicker.setDateToToday();
    datePicker.setLocale(Locale.CANADA_FRENCH);


    DatePickerSettings dateSettings2 = new DatePickerSettings(Locale.CHINESE);
    datePicker.setSettings(dateSettings2);

    CalendarPanel calendarPanel;
    calendarPanel = new CalendarPanel(dateSettings);
    panel.add(calendarPanel);

    calendarPanel.setSelectedDate(LocalDate.now());
    dateSettings.setLocale(Locale.CANADA_FRENCH);

    DatePickerSettings dateSettings2 = new DatePickerSettings(Locale.CHINESE);
    calendarPanel.setSettings(dateSettings2);
         */

    // Display the frame.
    frame.pack();
    frame.validate();
    int maxWidth = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().width;
    int maxHeight =
        GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().height;
    frame.setSize(640, 480);
    frame.setLocation(maxWidth / 2, maxHeight / 2);
    frame.setVisible(true);
  }

  /**
   * SampleDateVetoPolicy, A veto policy is a way to disallow certain dates from being selected in
   * calendar. A vetoed date cannot be selected by using the keyboard or the mouse.
   */
  private static class SampleDateVetoPolicy implements DateVetoPolicy {

    /**
     * isDateAllowed, Return true if a date should be allowed, or false if a date should be vetoed.
     */
    @Override
    public boolean isDateAllowed(LocalDate date) {
      // Disallow days 7 to 11.
      if ((date.getDayOfMonth() >= 7) && (date.getDayOfMonth() <= 11)) {
        return false;
      }
      // Disallow odd numbered saturdays.
      if ((date.getDayOfWeek() == DayOfWeek.SATURDAY) && ((date.getDayOfMonth() % 2) == 1)) {
        return false;
      }
      // Allow all other days.
      return true;
    }
  }

  /**
   * SampleHighlightPolicy, A highlight policy is a way to visually highlight certain dates in the
   * calendar. These may be holidays, or weekends, or other significant dates.
   */
  private static class SampleHighlightPolicy implements DateHighlightPolicy {

    /**
     * getHighlightInformationOrNull, Implement this function to indicate if a date should be
     * highlighted, and what highlighting details should be used for the highlighted date.
     *
     * <p>If a date should be highlighted, then return an instance of HighlightInformation. If the
     * date should not be highlighted, then return null.
     *
     * <p>You may (optionally) fill out the fields in the HighlightInformation class to give any
     * particular highlighted day a unique foreground color, background color, or tooltip text. If
     * the color fields are null, then the default highlighting colors will be used. If the tooltip
     * field is null (or empty), then no tooltip will be displayed.
     *
     * <p>Dates that are passed to this function will never be null.
     */
    @Override
    public HighlightInformation getHighlightInformationOrNull(LocalDate date) {
      // Highlight a chosen date, with a tooltip and a red background color.
      if (date.getDayOfMonth() == 25) {
        return new HighlightInformation(Color.red, null, "It's the 25th!");
      }
      // Highlight all Saturdays with a unique background and foreground color.
      if (date.getDayOfWeek() == DayOfWeek.SATURDAY) {
        return new HighlightInformation(Color.orange, Color.yellow, "It's Saturday!");
      }
      // Highlight all Sundays with default colors and a tooltip.
      if (date.getDayOfWeek() == DayOfWeek.SUNDAY) {
        return new HighlightInformation(null, null, "It's Sunday!");
      }
      // All other days should not be highlighted.
      return null;
    }
  }
}
