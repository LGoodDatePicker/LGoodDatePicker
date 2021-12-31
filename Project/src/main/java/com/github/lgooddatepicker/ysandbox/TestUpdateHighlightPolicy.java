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

import com.github.lgooddatepicker.components.CalendarPanel;
import com.github.lgooddatepicker.components.DatePickerSettings;
import com.github.lgooddatepicker.optionalusertools.CalendarListener;
import com.github.lgooddatepicker.optionalusertools.DateHighlightPolicy;
import com.github.lgooddatepicker.optionalusertools.DateVetoPolicy;
import com.github.lgooddatepicker.zinternaltools.CalendarSelectionEvent;
import com.github.lgooddatepicker.zinternaltools.HighlightInformation;
import com.github.lgooddatepicker.zinternaltools.YearMonthChangeEvent;
import java.awt.BorderLayout;
import java.awt.Color;
import java.time.LocalDate;
import java.time.temporal.WeekFields;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 * TestUpdateHighlightPolicy, This class tests the library to see if a highlight policy can be
 * dynamically and immediately updated based on a calendar selection change event.
 */
public class TestUpdateHighlightPolicy {

  /** main. */
  public static void main(String[] args) {
    SwingUtilities.invokeLater(
        () -> {
          try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
          } catch (Exception e) {
          }
          createUI();
        });
  }

  public static void createUI() {
    JFrame frame = new JFrame();
    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    frame.getContentPane().add(new ProcedureTest());
    frame.pack();
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);
  }

  private static class ProcedureTest extends JPanel implements CalendarListener {

    private CalendarPanel picker;
    private LocalDate selectedDate;

    public ProcedureTest() {
      this.setLayout(new BorderLayout(5, 5));
      picker = createDatePicker();
      this.add(picker, BorderLayout.CENTER);
    }

    private CalendarPanel createDatePicker() {
      DatePickerSettings settings = new DatePickerSettings();
      settings.setWeekNumberRules(WeekFields.ISO);
      settings.setWeekNumbersDisplayed(true, true);
      settings.setAllowEmptyDates(false);
      settings.setHighlightPolicy(new DynamicHighlightPolicy());
      CalendarPanel customizedPicker = new CalendarPanel(settings);
      customizedPicker.addCalendarListener(this);
      settings.setVetoPolicy(new VetoPolicy());
      return customizedPicker;
    }

    @Override
    public void selectedDateChanged(CalendarSelectionEvent event) {
      selectedDate = event.getNewDate();
      System.out.println(selectedDate);
    }

    @Override
    public void yearMonthChanged(YearMonthChangeEvent event) {
      // Not needed.
    }

    private class DynamicHighlightPolicy implements DateHighlightPolicy {

      @Override
      public HighlightInformation getHighlightInformationOrNull(LocalDate someDate) {
        if (selectedDate == null) {
          return null;
        }

        if ((someDate.isAfter(LocalDate.now()) || someDate.isEqual(LocalDate.now()))
            && (someDate.isBefore(selectedDate) || someDate.isEqual(selectedDate))) {
          return new HighlightInformation(Color.GREEN, Color.BLACK, "selected period");
        }
        return null;
      }
    }

    private class VetoPolicy implements DateVetoPolicy {

      @Override
      public boolean isDateAllowed(LocalDate someDate) {
        return someDate.isAfter(LocalDate.now()) || someDate.isEqual(LocalDate.now());
      }
    }
  }
}
