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
package com.github.lgooddatepicker;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.github.lgooddatepicker.TestHelpers.ExceptionInfo;
import com.github.lgooddatepicker.components.CalendarPanel;
import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;
import java.awt.AWTException;
import java.awt.Color;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.MenuElement;
import javax.swing.WindowConstants;
import org.junit.Test;

public class TestGithubIssues {

  @Test(expected = Test.None.class /* no exception expected */)
  public void TestIssue82() throws InterruptedException {
    if (!TestHelpers.isUiAvailable()) {
      // don't run under CI
      System.out.println("TestIssue82 requires UI to run and was skipped");
    }
    org.junit.Assume.assumeTrue(TestHelpers.isUiAvailable());

    // The exception that might be thrown by the date picker control
    // will be thrown in an AWT-EventQueue thread. To be able to detect
    // these exceptions we register an UncaughtExceptionHandler that
    // writes all occurring exceptions into exInfo.
    // As a result we always have access to the latest thrown exception
    // from any running thread
    final ExceptionInfo exInfo = new ExceptionInfo();
    try {
      TestHelpers.registerUncaughtExceptionHandlerToAllThreads(
          (thread, throwable) -> exInfo.set(thread.getName(), throwable));
      try (AutoDisposeFrame testWin = new AutoDisposeFrame()) {
        DatePicker date_picker = new DatePicker(new DatePickerSettings(Locale.ENGLISH));
        testWin.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        testWin.add(date_picker);
        testWin.pack();
        testWin.setVisible(true);
        Thread.sleep(100);
        assertFalse("DatePicker must not have an open popup.", date_picker.isPopupOpen());
        Thread.sleep(10);
        date_picker.openPopup();
        Thread.sleep(100);
        assertTrue("DatePicker must have an open popup.", date_picker.isPopupOpen());
        testWin.dispatchEvent(new WindowEvent(testWin, WindowEvent.WINDOW_CLOSING));
        Thread.sleep(100);
        assertFalse(
            "Exception in antother Thread triggered:\n"
                + "ThreadName: "
                + exInfo.getThreadName()
                + "\n"
                + "Exception: "
                + exInfo.getExceptionMessage()
                + "\nStacktrace:\n"
                + exInfo.getStackTrace(),
            exInfo.wasSet());
      }
    } finally {
      TestHelpers.registerUncaughtExceptionHandlerToAllThreads(null);
    }
  }

  @Test(expected = Test.None.class /* no exception expected */)
  public void TestIssue76() {
    DatePickerSettings dateSettingsPgmDate = new DatePickerSettings(Locale.ENGLISH);
    dateSettingsPgmDate.setAllowEmptyDates(true);
    dateSettingsPgmDate.getFormatsForParsing().clear();
    DateTimeFormatter dateFormatter =
        DateTimeFormatter.ofPattern("EEE, dd MMM yyyy").withLocale(Locale.ENGLISH);
    dateSettingsPgmDate.setFormatForDatesCommonEra(dateFormatter);
    DatePicker date_picker = new DatePicker(dateSettingsPgmDate);
    date_picker.getComponentDateTextField().setEditable(false);
    date_picker
        .getComponentDateTextField()
        .setToolTipText("The earliest date for booking to display.");
    date_picker
        .getComponentToggleCalendarButton()
        .setToolTipText("The earliest date for booking to display.");
    date_picker.getComponentToggleCalendarButton().setText("+");
    date_picker
        .getComponentToggleCalendarButton()
        .setFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 12));
    date_picker.getComponentDateTextField().setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
    date_picker
        .getComponentDateTextField()
        .setFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 12));
    date_picker.getComponentDateTextField().setDisabledTextColor(Color.BLACK);
    date_picker.setBounds(115, 50, 160, 20);
    date_picker.setDateToToday();
    AssertDateTextValidity(date_picker, "sun, 11 Aug 2019", false);
    AssertDateTextValidity(date_picker, "Sun, 11 Aug 2019", true);
    AssertDateTextValidity(date_picker, "Mon, 11 Aug 2019", false);
    AssertDateTextValidity(date_picker, "Tue, 30 Apr 2019", true);
    AssertDateTextValidity(date_picker, "Wed, 31 Apr 2019", false);
  }

  @Test(expected = Test.None.class /* no exception expected */)
  public void TestIssue74() {
    DateTimeFormatter era_date = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    DatePicker date_picker = new DatePicker(new DatePickerSettings(Locale.ENGLISH));
    date_picker.getSettings().getFormatsForParsing().clear();
    date_picker.getSettings().setFormatForDatesCommonEra(era_date);
    AssertDateTextValidity(date_picker, "12/31/2019", false);
    AssertDateTextValidity(date_picker, "31/12/2019", true);
    AssertDateTextValidity(date_picker, "31/04/2019", false);
    AssertDateTextValidity(date_picker, "30/04/2019", true);
  }

  @Test(expected = Test.None.class /* no exception expected */)
  public void TestIssue60() {
    DateTimeFormatter era_date = DateTimeFormatter.ofPattern("ddMMyyyy");
    DatePicker date_picker = new DatePicker(new DatePickerSettings(Locale.ENGLISH));
    date_picker.getSettings().getFormatsForParsing().clear();
    date_picker.getSettings().setFormatForDatesCommonEra(era_date);
    AssertDateTextValidity(date_picker, "30 04 2019", false);
    AssertDateTextValidity(date_picker, "30042019", true);
    AssertDateTextValidity(date_picker, "31042019", false);
    AssertDateTextValidity(date_picker, " 30042019 ", true);
  }

  @Test(expected = Test.None.class /* no exception expected */)
  public void TestIssue110()
      throws NoSuchFieldException, IllegalAccessException, NoSuchMethodException,
          InvocationTargetException, AWTException {
    if (!TestHelpers.isUiAvailable()) {
      // don't run under CI
      System.out.println("TestIssue110 requires UI to run and was skipped");
    }
    org.junit.Assume.assumeTrue(TestHelpers.isUiAvailable());

    DatePickerSettings dateSettings = new DatePickerSettings(Locale.ENGLISH);
    CalendarPanel testPanel = new CalendarPanel(dateSettings);

    try (AutoDisposeFrame testWin = new AutoDisposeFrame()) {
      testWin.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
      testWin.add(testPanel);
      testWin.pack();
      testWin.setVisible(true);

      java.awt.Robot bot = new java.awt.Robot();
      bot.waitForIdle();
      bot.delay(100);

      JLabel labelMonth =
          (JLabel) TestHelpers.readPrivateField(CalendarPanel.class, testPanel, "labelMonth");
      final java.awt.Point monthScreenLoc = labelMonth.getLocationOnScreen();
      bot.mouseMove(monthScreenLoc.x + 10, monthScreenLoc.y + 5);

      boolean popupVisible = false;

      JPopupMenu popupMonth =
          (JPopupMenu) TestHelpers.readPrivateField(CalendarPanel.class, testPanel, "popupMonth");
      assertTrue(popupMonth.isVisible() == popupVisible);
      // Verify that clicking on labalMonth opens and closes its popup in an alternating fashion
      for (int i = 0; i < 8; ++i) {
        bot.mouseMove(monthScreenLoc.x + 10, monthScreenLoc.y + 5);
        bot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        bot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
        bot.waitForIdle();
        bot.delay(50);
        popupVisible = !popupVisible;
        assertTrue(
            "Iteration "
                + String.valueOf(i)
                + " popup visiblity must be "
                + String.valueOf(popupVisible),
            popupMonth.isVisible() == popupVisible);
        bot.delay(80);
      }

      JLabel labelYear =
          (JLabel) TestHelpers.readPrivateField(CalendarPanel.class, testPanel, "labelYear");
      final java.awt.Point yearScreenLoc = labelYear.getLocationOnScreen();
      bot.mouseMove(yearScreenLoc.x + 10, yearScreenLoc.y + 5);

      popupVisible = false;

      JPopupMenu popupYear =
          (JPopupMenu) TestHelpers.readPrivateField(CalendarPanel.class, testPanel, "popupYear");
      assertTrue(popupYear.isVisible() == popupVisible);
      // Verify that clicking on labelYear opens and closes its popup in an alternating fashion
      for (int i = 0; i < 8; ++i) {
        bot.mouseMove(yearScreenLoc.x + 10, yearScreenLoc.y + 5);
        bot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        bot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
        bot.waitForIdle();
        bot.delay(50);
        popupVisible = !popupVisible;
        assertTrue(
            "Iteration "
                + String.valueOf(i)
                + " popup visiblity must be "
                + String.valueOf(popupVisible),
            popupYear.isVisible() == popupVisible);
        bot.delay(80);
      }

      boolean yearPopupSelected = true;

      // Verify that if clicking is alternated between labelYear and labelMonth their popup menus
      // open everytime
      for (int i = 0; i < 8; ++i) {
        if (yearPopupSelected) {
          bot.mouseMove(yearScreenLoc.x + 10, yearScreenLoc.y + 5);
          bot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
          bot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
          bot.waitForIdle();
          bot.delay(50);
          assertTrue("YearPopup is not visible, iteration " + i, popupYear.isVisible() == true);
          assertTrue(
              "MonthPopup is not invisible, iteration " + i, popupMonth.isVisible() == false);
          bot.delay(51);
        } else {
          bot.mouseMove(monthScreenLoc.x + 10, monthScreenLoc.y + 5);
          bot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
          bot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
          bot.waitForIdle();
          bot.delay(50);
          assertTrue("YearPopup is not invisible, iteration " + i, popupYear.isVisible() == false);
          assertTrue("MonthPopup is not visible, iteration " + i, popupMonth.isVisible() == true);
          bot.keyPress(KeyEvent.VK_ESCAPE);
          bot.delay(51);
        }
        yearPopupSelected = !yearPopupSelected;
      }
    }
  }

  // helper functions

  private void AssertDateTextValidity(
      DatePicker date_picker, String dateString, boolean isDateTexValid) {
    final boolean dateValid = date_picker.isTextValid(dateString);
    if (isDateTexValid) {
      assertTrue(
          "False negative in isTextValid! Text should be accepted: " + dateString, dateValid);
    } else {
      assertFalse(
          "False positive in isTextValid! Text should not be accepted: " + dateString, dateValid);
    }

    date_picker.setText(dateString);
    assertTrue(
        "Date text field value was not successfully set", date_picker.getText().equals(dateString));
    if (date_picker.isTextFieldValid()) {
      assertTrue(
          "False negative in isTextFieldValid! Text should be accepted: " + dateString, dateValid);
    } else {
      assertFalse(
          "False positive in isTextFieldValid! Text should not be accepted: " + dateString,
          dateValid);
    }

    final Color invalidDate =
        date_picker.getSettings().getColor(DatePickerSettings.DateArea.DatePickerTextInvalidDate);
    final Color validDate =
        date_picker.getSettings().getColor(DatePickerSettings.DateArea.DatePickerTextValidDate);
    assertTrue(
        "Foreground colors must be different for this test to work", invalidDate != validDate);
    final Color textfieldcolor = date_picker.getComponentDateTextField().getForeground();
    if (isDateTexValid) {
      assertTrue(
          "False negative! Text should have valid color: " + date_picker.getText(),
          textfieldcolor == validDate);
      assertTrue(
          "False negative! Text should not have invalid color: " + date_picker.getText(),
          textfieldcolor != invalidDate);
    } else {
      assertTrue(
          "False positive! Text should have invalid color: " + date_picker.getText(),
          textfieldcolor == invalidDate);
      assertTrue(
          "False positive! Text should not have valid color: " + date_picker.getText(),
          textfieldcolor != validDate);
    }
  }

  @Test(expected = Test.None.class /* no exception expected */)
  public void TestIssue113()
      throws NoSuchFieldException, IllegalAccessException, NoSuchMethodException,
          InvocationTargetException, AWTException {
    if (!TestHelpers.isUiAvailable()) {
      // don't run under CI
      System.out.println("TestIssue113 requires UI to run and was skipped");
    }
    org.junit.Assume.assumeTrue(TestHelpers.isUiAvailable());

    // Verify year editing is finihed when enter key is pressed while focusing

    DatePickerSettings dateSettings = new DatePickerSettings(Locale.ENGLISH);
    CalendarPanel testPanel = new CalendarPanel(dateSettings);

    try (AutoDisposeFrame testWin = new AutoDisposeFrame()) {
      testWin.add(testPanel);
      testWin.pack();
      testWin.setVisible(true);

      java.awt.Robot bot = new java.awt.Robot();
      bot.waitForIdle();
      bot.delay(100);

      JPanel monthAndYearInnerPanel =
          (JPanel)
              TestHelpers.readPrivateField(
                  CalendarPanel.class, testPanel, "monthAndYearInnerPanel");
      JPanel yearEditorPanel =
          (JPanel) TestHelpers.readPrivateField(CalendarPanel.class, testPanel, "yearEditorPanel");

      // monthAndYearInnerPanel invisible as default
      assertTrue(!monthAndYearInnerPanel.isAncestorOf(yearEditorPanel));

      JLabel labelYear =
          (JLabel) TestHelpers.readPrivateField(CalendarPanel.class, testPanel, "labelYear");
      final java.awt.Point yearScreenLoc = labelYear.getLocationOnScreen();
      bot.mouseMove(yearScreenLoc.x + 10, yearScreenLoc.y + 5);

      JPopupMenu popupYear =
          (JPopupMenu) TestHelpers.readPrivateField(CalendarPanel.class, testPanel, "popupYear");
      assertTrue(popupYear.isVisible() == false);

      // Open year popup
      bot.mouseMove(yearScreenLoc.x + 10, yearScreenLoc.y + 5);
      bot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
      bot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
      bot.waitForIdle();
      bot.delay(80);

      // monthAndYearInnerPanel still invisible
      assertTrue(!monthAndYearInnerPanel.isAncestorOf(yearEditorPanel));

      assertTrue(popupYear.isVisible() == true);
      boolean found = false;
      for (MenuElement elem : popupYear.getSubElements()) {
        JMenuItem menuItem = (JMenuItem) elem.getComponent();
        if (menuItem.getText().equals("( . . . )")) {
          final java.awt.Point dotLabelPos = menuItem.getLocationOnScreen();
          bot.mouseMove(dotLabelPos.x + 10, dotLabelPos.y + 5);
          found = true;
          break;
        }
      }
      assertTrue(found);
      bot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
      bot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
      bot.waitForIdle();
      bot.delay(80);

      // monthAndYearInnerPanel now visible
      assertTrue(monthAndYearInnerPanel.isAncestorOf(yearEditorPanel));

      // do not access yearTextField directly here, as events triggered by the text
      // change will result in deadlock due to concurrent access to the UI
      bot.keyPress(KeyEvent.VK_BACK_SPACE);
      bot.waitForIdle();
      bot.keyPress(KeyEvent.VK_BACK_SPACE);
      bot.waitForIdle();
      bot.keyPress(KeyEvent.VK_BACK_SPACE);
      bot.waitForIdle();
      bot.keyPress(KeyEvent.VK_BACK_SPACE);
      bot.waitForIdle();
      bot.keyPress(KeyEvent.VK_BACK_SPACE);
      bot.waitForIdle();
      bot.keyPress(KeyEvent.VK_2);
      bot.waitForIdle();
      bot.keyPress(KeyEvent.VK_0);
      bot.waitForIdle();
      bot.keyPress(KeyEvent.VK_0);
      bot.waitForIdle();
      bot.keyPress(KeyEvent.VK_5);

      bot.waitForIdle();
      bot.delay(80);

      bot.keyPress(KeyEvent.VK_ENTER);
      bot.waitForIdle();
      bot.delay(80);

      // monthAndYearInnerPanel now invisible
      assertTrue(!monthAndYearInnerPanel.isAncestorOf(yearEditorPanel));
      assertTrue(
          "labelYear has wrong text: " + labelYear.getText(), labelYear.getText().equals("2005"));
      assertTrue(
          "labelYear has wrong text: " + labelYear.getText(),
          !labelYear.getText().equals(String.valueOf(LocalDate.now().getYear())));
    }
  }
}
