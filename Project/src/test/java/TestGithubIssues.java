/*
 * The MIT License
 *
 * Copyright 2019 Restricted.
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

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;
import com.github.lgooddatepicker.zinternaltools.Pair;
import java.awt.Color;
import java.awt.event.WindowEvent;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import javax.swing.JFrame;
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
    }

    @Test( expected = Test.None.class /* no exception expected */ )
    public void TestIssue82() throws InterruptedException
    {
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
            Thread.sleep(10);
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
        DatePickerSettings dateSettingsPgmDate = new DatePickerSettings();
        dateSettingsPgmDate.setAllowEmptyDates(true);
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
        date_picker.setText("sun, 11 Aug 2019");
        AssertDateTextValidity(false);
        date_picker.setText("Sun, 11 Aug 2019");
        AssertDateTextValidity(true);
        date_picker.setText("Mon, 11 Aug 2019");
        AssertDateTextValidity(false);
        date_picker.setText("Tue, 30 Apr 2019");
        AssertDateTextValidity(true);
        date_picker.setText("Wed, 31 Apr 2019");
        AssertDateTextValidity(false);
    }

    @Test( expected = Test.None.class /* no exception expected */ )
    public void TestIssue74()
    {
        DateTimeFormatter era_date = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        date_picker.getSettings().setFormatForDatesCommonEra(era_date);
        date_picker.setText("12/31/2019");
        AssertDateTextValidity(false);
        date_picker.setText("31/12/2019");
        AssertDateTextValidity(true);
        date_picker.setText("31/04/2019");
        AssertDateTextValidity(false);
        date_picker.setText("30/04/2019");
        AssertDateTextValidity(true);
    }

    @Test( expected = Test.None.class /* no exception expected */ )
    public void TestIssue60()
    {
        DateTimeFormatter era_date = DateTimeFormatter.ofPattern("ddMMyyyy");
        date_picker.getSettings().setFormatForDatesCommonEra(era_date);
        date_picker.setText("30 04 2019");
        AssertDateTextValidity(false);
        date_picker.setText("30042019");
        AssertDateTextValidity(true);
        date_picker.setText("31042019");
        AssertDateTextValidity(false);
        date_picker.setText(" 30042019 ");
        AssertDateTextValidity(true);
    }

    // helper functions

    private void AssertDateTextValidity(boolean isDateTexValid)
    {
        final Color invalidDate = date_picker.getSettings().getColor(DatePickerSettings.DateArea.DatePickerTextInvalidDate);
        final Color validDate = date_picker.getSettings().getColor(DatePickerSettings.DateArea.DatePickerTextValidDate);
        assertTrue("Foreground colors must be different for this test to work", invalidDate != validDate);
        final Color textfieldcolor = date_picker.getComponentDateTextField().getForeground();
        if (isDateTexValid) {
            assertTrue("False negative! Text should be accpeted: "+date_picker.getText(), textfieldcolor == validDate);
            assertTrue("False negative! Text should be accepted: "+date_picker.getText(), textfieldcolor != invalidDate);
        }
        else {
            assertTrue("False positive! Text should not be accepted: "+date_picker.getText(), textfieldcolor == invalidDate);
            assertTrue("False positive! Text should not be accepted: "+date_picker.getText(), textfieldcolor != validDate);
        }
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
