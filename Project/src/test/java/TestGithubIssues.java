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

import com.github.lgooddatepicker.components.CalendarPanel;
import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;
import com.github.lgooddatepicker.zinternaltools.Pair;
import java.awt.AWTException;
import java.awt.Color;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.lang.reflect.InvocationTargetException;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPopupMenu;
import javax.swing.WindowConstants;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

public class TestGithubIssues {
    DatePicker date_picker;

    @Before
    public void setUp()
    {
        date_picker = new DatePicker();
        date_picker.getSettings().setLocale(Locale.ENGLISH);
        date_picker.getSettings().getFormatsForParsing().clear();
    }

    @Test( expected = Test.None.class /* no exception expected */ )
    public void TestIssue82() throws InterruptedException
    {
      if (!isUiAvailable())
      {
        // don't run under CI
        System.out.println("TestIssue82 requires UI to run and was skipped");
      }
      org.junit.Assume.assumeTrue(isUiAvailable());

        // The exception that might be thrown by the date picker control
        // will be thrown in an AWT-EventQueue thread. To be able to detect
        // these exceptions we register an UncaughtExceptionHandler that
        // writes all occurring exceptions into exInfo.
        // As a result we always have access to the latest thrown exception
        // from any running thread
        final ExceptionInfo exInfo = new ExceptionInfo();
        try {
            RegisterUncaughtExceptionHandlerToAllThreads(new Thread.UncaughtExceptionHandler() {
                @Override
                public void uncaughtException( Thread t, Throwable e ) {
                    exInfo.set(t.getName(), e);
                }
            });
            JFrame testWin = new JFrame();
            testWin.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            testWin.add(date_picker);
            testWin.pack();
            testWin.setVisible(true);
            Thread.sleep(10);
            assertFalse("DatePicker must not have an open popup.", date_picker.isPopupOpen());
            Thread.sleep(10);
            date_picker.openPopup();
            Thread.sleep(30);
            assertTrue("DatePicker must have an open popup.", date_picker.isPopupOpen());
            testWin.dispatchEvent(new WindowEvent(testWin, WindowEvent.WINDOW_CLOSING));
            Thread.sleep(50);
            assertFalse("Exception in antother Thread triggered:\n"
                    +"ThreadName: "+exInfo.getThreadName()+"\n"
                    +"Exception: "+exInfo.getExceptionMessage()
                    +"\nStacktrace:\n"+exInfo.getStackTrace()
                    , exInfo.wasSet());
            } finally {
                RegisterUncaughtExceptionHandlerToAllThreads(null);
        }
    }

    @Test( expected = Test.None.class /* no exception expected */ )
    public void TestIssue76()
    {
        DatePickerSettings dateSettingsPgmDate = new DatePickerSettings(Locale.ENGLISH);
        dateSettingsPgmDate.setAllowEmptyDates(true);
        dateSettingsPgmDate.getFormatsForParsing().clear();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("EEE, dd MMM yyyy").withLocale(Locale.ENGLISH);
        dateSettingsPgmDate.setFormatForDatesCommonEra(dateFormatter);
        date_picker.setSettings(dateSettingsPgmDate);
        date_picker.getComponentDateTextField().setEditable(false);
        date_picker.getComponentDateTextField().setToolTipText("The earliest date for booking to display.");
        date_picker.getComponentToggleCalendarButton().setToolTipText("The earliest date for booking to display.");
        date_picker.getComponentToggleCalendarButton().setText("+");
        date_picker.getComponentToggleCalendarButton().setFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 12));
        date_picker.getComponentDateTextField().setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        date_picker.getComponentDateTextField().setFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 12));
        date_picker.getComponentDateTextField().setDisabledTextColor(Color.BLACK);
        date_picker.setBounds(115, 50, 160, 20);
        date_picker.setDateToToday();
        AssertDateTextValidity("sun, 11 Aug 2019", false);
        AssertDateTextValidity("Sun, 11 Aug 2019", true);
        AssertDateTextValidity("Mon, 11 Aug 2019", false);
        AssertDateTextValidity("Tue, 30 Apr 2019", true);
        AssertDateTextValidity("Wed, 31 Apr 2019", false);
    }

    @Test( expected = Test.None.class /* no exception expected */ )
    public void TestIssue74()
    {
        DateTimeFormatter era_date = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        date_picker.getSettings().setFormatForDatesCommonEra(era_date);
        AssertDateTextValidity("12/31/2019", false);
        AssertDateTextValidity("31/12/2019", true);
        AssertDateTextValidity("31/04/2019", false);
        AssertDateTextValidity("30/04/2019", true);
    }

    @Test( expected = Test.None.class /* no exception expected */ )
    public void TestIssue60()
    {
        DateTimeFormatter era_date = DateTimeFormatter.ofPattern("ddMMyyyy");
        date_picker.getSettings().setFormatForDatesCommonEra(era_date);
        AssertDateTextValidity("30 04 2019", false);
        AssertDateTextValidity("30042019", true);
        AssertDateTextValidity("31042019", false);
        AssertDateTextValidity(" 30042019 ", true);
    }

    @Test( expected = Test.None.class /* no exception expected */ )
    public void TestIssue110() throws NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InvocationTargetException, AWTException
    {
        if (!isUiAvailable())
        {
          // don't run under CI
          System.out.println("TestIssue110 requires UI to run and was skipped");
        }
        org.junit.Assume.assumeTrue(isUiAvailable());


        DatePickerSettings dateSettings = new DatePickerSettings(Locale.ENGLISH);
        CalendarPanel testPanel = new CalendarPanel(dateSettings);

        JFrame testWin = new JFrame();
        testWin.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        testWin.add(testPanel);
        testWin.pack();
        testWin.setVisible(true);

        java.awt.Robot bot = new java.awt.Robot();
        bot.delay(100);

        JLabel labelMonth = (JLabel) TestHelpers.readPrivateField(CalendarPanel.class, testPanel, "labelMonth");
        final java.awt.Point monthScreenLoc = labelMonth.getLocationOnScreen();
        bot.mouseMove(monthScreenLoc.x+10, monthScreenLoc.y+5);

        boolean popupVisible = false;

        JPopupMenu popupMonth = (JPopupMenu) TestHelpers.readPrivateField(CalendarPanel.class, testPanel, "popupMonth");
        assertTrue(popupMonth.isVisible() == popupVisible);
        // Verify that clicking on labalMonth opens and closes its popup in an alternating fashion
        for( int i = 0; i < 8; ++i)
        {
            bot.mouseMove(monthScreenLoc.x+10, monthScreenLoc.y+5);
            bot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
            bot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
            bot.delay(50);
            popupVisible = !popupVisible;
            assertTrue(popupMonth.isVisible() == popupVisible);
            bot.delay(51);
        }

        JLabel labelYear = (JLabel) TestHelpers.readPrivateField(CalendarPanel.class, testPanel, "labelYear");
        final java.awt.Point yearScreenLoc = labelYear.getLocationOnScreen();
        bot.mouseMove(yearScreenLoc.x+10, yearScreenLoc.y+5);

        popupVisible = false;

        JPopupMenu popupYear = (JPopupMenu) TestHelpers.readPrivateField(CalendarPanel.class, testPanel, "popupYear");
        assertTrue(popupYear.isVisible() == popupVisible);
        // Verify that clicking on labelYear opens and closes its popup in an alternating fashion
        for( int i = 0; i < 8; ++i)
        {
            bot.mouseMove(yearScreenLoc.x+10, yearScreenLoc.y+5);
            bot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
            bot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
            bot.delay(50);
            popupVisible = !popupVisible;
            assertTrue(popupYear.isVisible() == popupVisible);
            bot.delay(51);
        }

        boolean yearPopupSelected = true;

        // Verify that if clicking is alternated between labelYear and labelMonth their popup menus open everytime
        for( int i = 0; i < 8; ++i)
        {
            if (yearPopupSelected)
            {
                bot.mouseMove(yearScreenLoc.x+10, yearScreenLoc.y+5);
                bot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                bot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                bot.delay(50);
                assertTrue(popupYear.isVisible() == true);
                assertTrue(popupMonth.isVisible() == false);
                bot.delay(51);
            }
            else
            {
                bot.mouseMove(monthScreenLoc.x+10, monthScreenLoc.y+5);
                bot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                bot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                bot.delay(50);
                assertTrue(popupYear.isVisible() == false);
                assertTrue(popupMonth.isVisible() == true);
                bot.keyPress(KeyEvent.VK_ESCAPE);
                bot.delay(51);
            }
            yearPopupSelected = !yearPopupSelected;
        }

        testWin.dispatchEvent(new WindowEvent(testWin, WindowEvent.WINDOW_CLOSING));
    }

    // helper functions

    private void AssertDateTextValidity(String dateString, boolean isDateTexValid)
    {
        final boolean dateValid = date_picker.isTextValid(dateString);
        if (isDateTexValid) {
            assertTrue("False negative in isTextValid! Text should be accepted: "+dateString, dateValid);
        }
        else {
            assertFalse("False positive in isTextValid! Text should not be accepted: "+dateString, dateValid);
        }

        date_picker.setText(dateString);
        assertTrue("Date text field value was not successfully set", date_picker.getText().equals(dateString));
        if (date_picker.isTextFieldValid()) {
            assertTrue("False negative in isTextFieldValid! Text should be accepted: "+dateString, dateValid);
        }
        else {
            assertFalse("False positive in isTextFieldValid! Text should not be accepted: "+dateString, dateValid);
        }

        final Color invalidDate = date_picker.getSettings().getColor(DatePickerSettings.DateArea.DatePickerTextInvalidDate);
        final Color validDate = date_picker.getSettings().getColor(DatePickerSettings.DateArea.DatePickerTextValidDate);
        assertTrue("Foreground colors must be different for this test to work", invalidDate != validDate);
        final Color textfieldcolor = date_picker.getComponentDateTextField().getForeground();
        if (isDateTexValid) {
            assertTrue("False negative! Text should have valid color: "+date_picker.getText(), textfieldcolor == validDate);
            assertTrue("False negative! Text should not have invalid color: "+date_picker.getText(), textfieldcolor != invalidDate);
        }
        else {
            assertTrue("False positive! Text should have invalid color: "+date_picker.getText(), textfieldcolor == invalidDate);
            assertTrue("False positive! Text should not have valid color: "+date_picker.getText(), textfieldcolor != validDate);
        }
    }

    // detect funcionality of UI, which is not available on most CI systems
    boolean isUiAvailable()
    {
      return !java.awt.GraphicsEnvironment.isHeadless();
    }

    static private void RegisterUncaughtExceptionHandlerToAllThreads(Thread.UncaughtExceptionHandler handler)
    {
        Thread.setDefaultUncaughtExceptionHandler(handler);
        //activeCount is only an estimation
        int activeCountOversize = 1;
        Thread[] threads;
        do {
          threads = new Thread[Thread.activeCount() + activeCountOversize];
          Thread.enumerate(threads);
          activeCountOversize++;
        } while (threads[threads.length-1] != null);
        for (Thread thread : threads) {
          if (thread != null) {
              thread.setUncaughtExceptionHandler(handler);
          }
        }
    }

    private class ExceptionInfo
    {
        Pair<String, Throwable> info = new Pair<>("", null);

        synchronized boolean wasSet() {
            return !info.first.isEmpty() || info.second != null;
        }
        synchronized void set(String threadname, Throwable ex) {
            info.first = threadname;
            info.second = ex;
        }
        synchronized String getThreadName() {
            return info.first;
        }
        synchronized String getExceptionMessage() {
            return info.second != null ? info.second.getMessage() : "";
        }
        synchronized String getStackTrace()
        {
            String result = "";
            if (info.second != null) {
                for (StackTraceElement elem : info.second.getStackTrace()) {
                    result += elem.toString()+"\n";
                }
            }
            return result;
        }
    }

}
