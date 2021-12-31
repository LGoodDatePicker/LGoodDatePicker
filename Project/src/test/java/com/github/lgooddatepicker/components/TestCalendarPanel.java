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
package com.github.lgooddatepicker.components;

import static org.junit.Assert.assertTrue;

import com.github.lgooddatepicker.TestHelpers;
import com.github.lgooddatepicker.components.DatePickerSettings.DateArea;
import com.github.lgooddatepicker.zinternaltools.HighlightInformation;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.Locale;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import javax.swing.MenuElement;
import org.junit.Test;

public class TestCalendarPanel {
  @Test(expected = Test.None.class /* no exception expected */)
  public void TestCustomClockCalenderPanel() {
    CalendarPanel panel = new CalendarPanel();
    java.time.YearMonth defaultDateNeverNull = panel.getDisplayedYearMonth();
    assertTrue("displayedYearMonth may never be null", defaultDateNeverNull != null);
    assertTrue(
        "Year must be the year of today",
        defaultDateNeverNull.getYear() == LocalDate.now().getYear());
    assertTrue(
        "Month must be the month of today",
        defaultDateNeverNull.getMonthValue() == LocalDate.now().getMonthValue());

    panel = new CalendarPanel(new DatePicker());
    defaultDateNeverNull = panel.getDisplayedYearMonth();
    assertTrue("displayedYearMonth may never be null", defaultDateNeverNull != null);
    assertTrue(
        "Year must be the year of today",
        defaultDateNeverNull.getYear() == LocalDate.now().getYear());
    assertTrue(
        "Month must be the month of today",
        defaultDateNeverNull.getMonthValue() == LocalDate.now().getMonthValue());

    DatePickerSettings settings = new DatePickerSettings(Locale.ENGLISH);
    settings.setClock(TestHelpers.getClockFixedToInstant(1995, Month.OCTOBER, 31, 0, 0));
    panel = new CalendarPanel(settings);
    defaultDateNeverNull = panel.getDisplayedYearMonth();
    assertTrue("displayedYearMonth may never be null", defaultDateNeverNull != null);
    assertTrue("Year must be the year 1995", defaultDateNeverNull.getYear() == 1995);
    assertTrue("Month must be the month October", defaultDateNeverNull.getMonth() == Month.OCTOBER);
  }

  @Test(expected = Test.None.class /* no exception expected */)
  public void TestMouseHoverCalendarPanel()
      throws NoSuchFieldException, IllegalArgumentException, IllegalAccessException,
          NoSuchMethodException, InvocationTargetException {
    DatePickerSettings settings = new DatePickerSettings(Locale.ENGLISH);
    CalendarPanel panel = new CalendarPanel(settings);

    final Color generalHighlight =
        settings.getColor(DatePickerSettings.DateArea.BackgroundCalendarPanelLabelsOnHover);
    final Color yearMonthBackground =
        settings.getColor(DatePickerSettings.DateArea.BackgroundMonthAndYearMenuLabels);
    final Color yearMonthText =
        settings.getColor(DatePickerSettings.DateArea.TextMonthAndYearMenuLabels);

    verifyLabelHover(
        panel, "labelMonth", yearMonthBackground, yearMonthText, generalHighlight, yearMonthText);
    verifyLabelHover(
        panel, "labelYear", yearMonthBackground, yearMonthText, generalHighlight, yearMonthText);

    final Color todayLabelBackground =
        settings.getColor(DatePickerSettings.DateArea.BackgroundTodayLabel);
    final Color todayLabelText = settings.getColor(DatePickerSettings.DateArea.TextTodayLabel);
    verifyLabelHover(
        panel,
        "labelSetDateToToday",
        todayLabelBackground,
        todayLabelText,
        generalHighlight,
        todayLabelText);

    final Color clearLabelBackground =
        settings.getColor(DatePickerSettings.DateArea.BackgroundClearLabel);
    final Color clearLabelText = settings.getColor(DatePickerSettings.DateArea.TextClearLabel);
    verifyLabelHover(
        panel,
        "labelClearDate",
        clearLabelBackground,
        clearLabelText,
        generalHighlight,
        clearLabelText);
  }

  @Test(expected = Test.None.class /* no exception expected */)
  public void TestCustomMouseHoverColorCalendarPanel()
      throws NoSuchFieldException, IllegalArgumentException, IllegalAccessException,
          NoSuchMethodException, InvocationTargetException {
    DatePickerSettings settings = new DatePickerSettings(Locale.ENGLISH);
    settings.setColor(DatePickerSettings.DateArea.BackgroundCalendarPanelLabelsOnHover, Color.red);
    settings.setColor(DatePickerSettings.DateArea.TextCalendarPanelLabelsOnHover, Color.yellow);
    CalendarPanel panel = new CalendarPanel(settings);

    final Color generalHighlight = Color.red;
    final Color generalHighlightText = Color.yellow;
    final Color yearMonthBackground =
        settings.getColor(DatePickerSettings.DateArea.BackgroundMonthAndYearMenuLabels);
    final Color yearMonthText =
        settings.getColor(DatePickerSettings.DateArea.TextMonthAndYearMenuLabels);

    verifyLabelHover(
        panel,
        "labelMonth",
        yearMonthBackground,
        yearMonthText,
        generalHighlight,
        generalHighlightText);
    verifyLabelHover(
        panel,
        "labelYear",
        yearMonthBackground,
        yearMonthText,
        generalHighlight,
        generalHighlightText);

    final Color todayLabelBackground =
        settings.getColor(DatePickerSettings.DateArea.BackgroundTodayLabel);
    final Color todayLabelText = settings.getColor(DatePickerSettings.DateArea.TextTodayLabel);
    verifyLabelHover(
        panel,
        "labelSetDateToToday",
        todayLabelBackground,
        todayLabelText,
        generalHighlight,
        generalHighlightText);

    final Color clearLabelBackground =
        settings.getColor(DatePickerSettings.DateArea.BackgroundClearLabel);
    final Color clearLabelText = settings.getColor(DatePickerSettings.DateArea.TextClearLabel);
    verifyLabelHover(
        panel,
        "labelClearDate",
        clearLabelBackground,
        clearLabelText,
        generalHighlight,
        generalHighlightText);
  }

  void verifyLabelHover(
      CalendarPanel panel,
      String labelname,
      Color defaultBackground,
      Color defaultText,
      Color highlightBackground,
      Color highlightText)
      throws NoSuchFieldException, IllegalArgumentException, IllegalAccessException,
          NoSuchMethodException, InvocationTargetException {
    JLabel labeltoverify =
        (JLabel) TestHelpers.readPrivateField(CalendarPanel.class, panel, labelname);

    assertTrue(
        labelname + " has wrong background color: " + labeltoverify.getBackground().toString(),
        labeltoverify.getBackground().equals(defaultBackground));
    assertTrue(
        labelname + " has wrong text color: " + labeltoverify.getForeground().toString(),
        labeltoverify.getForeground().equals(defaultText));
    MouseEvent testEvent =
        new MouseEvent(labeltoverify, MouseEvent.MOUSE_ENTERED, 0, 0, 0, 0, 0, false);
    TestHelpers.accessPrivateMethod(
            java.awt.Component.class, "processEvent", java.awt.AWTEvent.class)
        .invoke(labeltoverify, testEvent);
    assertTrue(
        labelname + " has wrong background color: " + labeltoverify.getBackground().toString(),
        labeltoverify.getBackground().equals(highlightBackground));
    assertTrue(
        labelname + " has wrong text color: " + labeltoverify.getForeground().toString(),
        labeltoverify.getForeground().equals(highlightText));
    testEvent = new MouseEvent(labeltoverify, MouseEvent.MOUSE_EXITED, 0, 0, 0, 0, 0, false);
    TestHelpers.accessPrivateMethod(
            java.awt.Component.class, "processEvent", java.awt.AWTEvent.class)
        .invoke(labeltoverify, testEvent);
    assertTrue(
        labelname + " has wrong background color: " + labeltoverify.getBackground().toString(),
        labeltoverify.getBackground().equals(defaultBackground));
    assertTrue(
        labelname + " has wrong text color: " + labeltoverify.getForeground().toString(),
        labeltoverify.getForeground().equals(defaultText));
  }

  @Test(expected = Test.None.class /* no exception expected */)
  public void TestDateHighlightAndVetoPolicy()
      throws NoSuchFieldException, IllegalArgumentException, IllegalAccessException,
          NoSuchMethodException, InvocationTargetException {
    DatePickerSettings settings = new DatePickerSettings(Locale.ENGLISH);
    settings.setHighlightPolicy(
        dateToHighlight -> {
          if (dateToHighlight.get(ChronoField.DAY_OF_MONTH) % 2 == 0) {
            return new HighlightInformation(Color.green, Color.blue, "highlighted");
          }
          return null;
        });
    CalendarPanel panel = new CalendarPanel(settings);
    settings.setVetoPolicy(
        dateToVeto -> {
          final int day = dateToVeto.get(ChronoField.DAY_OF_MONTH);
          return (day % 5 != 0);
        });
    panel.setDisplayedYearMonth(YearMonth.of(2021, Month.MARCH));

    final Color defaultColor = settings.getColor(DateArea.CalendarBackgroundNormalDates);
    final Color defaultTextColor = settings.getColor(DateArea.CalendarTextNormalDates);
    final Color defaultVetoedColor = settings.getColor(DateArea.CalendarBackgroundVetoedDates);

    for (int dayLabelIdx = 0; dayLabelIdx < 42; ++dayLabelIdx) {
      if (dayLabelIdx < 1 || dayLabelIdx > 31) {
        verifyDateLabelColorAndToolTip(panel, dayLabelIdx, defaultColor, defaultTextColor, null);
        continue;
      }
      if (dayLabelIdx % 5 == 0) {
        verifyDateLabelColorAndToolTip(
            panel, dayLabelIdx, defaultVetoedColor, defaultTextColor, null);
        continue;
      }
      if (dayLabelIdx % 2 == 0) {
        verifyDateLabelColorAndToolTip(panel, dayLabelIdx, Color.green, Color.blue, "highlighted");
        continue;
      }
      verifyDateLabelColorAndToolTip(panel, dayLabelIdx, defaultColor, defaultTextColor, null);
    }
  }

  void verifyDateLabelColorAndToolTip(
      CalendarPanel panel, int labelIdx, Color bgColor, Color textColor, String tooltip)
      throws NoSuchFieldException, IllegalArgumentException, IllegalAccessException,
          NoSuchMethodException, InvocationTargetException {
    ArrayList<?> labelList =
        (ArrayList<?>) TestHelpers.readPrivateField(CalendarPanel.class, panel, "dateLabels");
    JLabel labeltoverify = (JLabel) labelList.get(labelIdx);
    String labelname = "DateLabel_" + labelIdx;
    String labelToolTip = labeltoverify.getToolTipText();
    assertTrue(
        labelname + " has wrong tool tip text: " + labelToolTip,
        labelToolTip != null ? labelToolTip.equals(tooltip) : tooltip == null);
    assertTrue(
        labelname + " has wrong background color: " + labeltoverify.getBackground().toString(),
        labeltoverify.getBackground().equals(bgColor));
    assertTrue(
        labelname + " has wrong text color: " + labeltoverify.getForeground().toString(),
        labeltoverify.getForeground().equals(textColor));
  }

  @Test(expected = Test.None.class /* no exception expected */)
  public void TestYearEditor()
      throws NoSuchFieldException, IllegalAccessException, NoSuchMethodException,
          InvocationTargetException {
    verifyYearEditor(YearEditorFinalizer.doneEditingButton);
    verifyYearEditor(YearEditorFinalizer.yearTextField);
  }

  enum YearEditorFinalizer {
    doneEditingButton,
    yearTextField
  }

  void verifyYearEditor(YearEditorFinalizer editorFinalizer)
      throws NoSuchFieldException, IllegalAccessException, NoSuchMethodException,
          InvocationTargetException {
    DatePickerSettings dateSettings = new DatePickerSettings(Locale.ENGLISH);
    CalendarPanel testPanel = new CalendarPanel(dateSettings);

    JPanel monthAndYearInnerPanel =
        (JPanel)
            TestHelpers.readPrivateField(CalendarPanel.class, testPanel, "monthAndYearInnerPanel");
    JPanel yearEditorPanel =
        (JPanel) TestHelpers.readPrivateField(CalendarPanel.class, testPanel, "yearEditorPanel");

    // monthAndYearInnerPanel invisible as default
    assertTrue(!monthAndYearInnerPanel.isAncestorOf(yearEditorPanel));

    final String currentYear = String.valueOf(LocalDate.now().getYear());
    JLabel labelYear =
        (JLabel) TestHelpers.readPrivateField(CalendarPanel.class, testPanel, "labelYear");
    assertTrue(
        "Wrong text in labelYear: " + labelYear.getText(), labelYear.getText().equals(currentYear));
    assertTrue(
        "Wrong text in yearTextField: " + labelYear.getText(),
        labelYear.getText().equals(currentYear));

    TestHelpers.accessPrivateMethod(CalendarPanel.class, "populateYearPopupMenu").invoke(testPanel);

    JPopupMenu popupYear =
        (JPopupMenu) TestHelpers.readPrivateField(CalendarPanel.class, testPanel, "popupYear");

    boolean found = false;
    for (MenuElement elem : popupYear.getSubElements()) {
      JMenuItem menuItem = (JMenuItem) elem.getComponent();
      if (menuItem.getText().equals("( . . . )")) {
        menuItem.doClick();
        found = true;
        break;
      }
    }
    assertTrue(found);
    // monthAndYearInnerPanel now visible
    assertTrue(monthAndYearInnerPanel.isAncestorOf(yearEditorPanel));

    JTextField yearTextField =
        (JTextField) TestHelpers.readPrivateField(CalendarPanel.class, testPanel, "yearTextField");
    yearTextField.setText("2005");
    assertTrue(
        "Wrong text in labelYear: " + labelYear.getText(), labelYear.getText().equals("2005"));
    assertTrue(
        "Wrong text in labelYear: " + labelYear.getText(),
        !labelYear.getText().equals(currentYear));

    switch (editorFinalizer) {
      case doneEditingButton:
        JButton doneEditingYearButton =
            (JButton)
                TestHelpers.readPrivateField(
                    CalendarPanel.class, testPanel, "doneEditingYearButton");
        doneEditingYearButton.doClick();
        break;
      case yearTextField:
        yearTextField.postActionEvent();
        break;
      default:
        assertTrue("Unknown finalizer value " + editorFinalizer, false);
    }

    // monthAndYearInnerPanel invisible after finishing editing
    assertTrue(!monthAndYearInnerPanel.isAncestorOf(yearEditorPanel));
  }
}
