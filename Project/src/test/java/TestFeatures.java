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

import static org.junit.Assert.assertTrue;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.lang.reflect.InvocationTargetException;
import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Locale;

import javax.swing.JLabel;

import org.junit.Test;

import com.github.lgooddatepicker.components.CalendarPanel;
import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;
import com.github.lgooddatepicker.components.TimePicker;
import com.github.lgooddatepicker.components.TimePickerSettings;

// add tests for your new features here
public class TestFeatures
{
    @Test( expected = Test.None.class /* no exception expected */ )
    public void TestCustomClockDateSettings() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException
    {
        DatePickerSettings settings = new DatePickerSettings();
        assertTrue("Default clock must be available", settings.getClock() != null);
        assertTrue("Default clock must be in system default time zone", settings.getClock().getZone().equals(ZoneId.systemDefault()));
        settings = new DatePickerSettings(Locale.ENGLISH);
        assertTrue("Default clock must be available", settings.getClock() != null);
        assertTrue("Default clock must be in system default time zone", settings.getClock().getZone().equals(ZoneId.systemDefault()));
        Clock myClock = Clock.systemUTC();
        settings.setClock(myClock);
        assertTrue("Set clock must be returned", settings.getClock() == myClock);
        settings.setClock(getClockFixedToInstant(1993, Month.MARCH, 15, 12, 00));
        settings.setDefaultYearMonth(null);
        YearMonth defaultyearmonth = (YearMonth) TestHelpers.accessPrivateMethod(DatePickerSettings.class, "zGetDefaultYearMonthAsUsed").invoke(settings);
        assertTrue(defaultyearmonth.getYear() == 1993);
        assertTrue(defaultyearmonth.getMonth() == Month.MARCH);
    }

    @Test( expected = Test.None.class /* no exception expected */ )
    public void TestCustomClockTimeSettings() throws NoSuchFieldException, IllegalArgumentException, IllegalAccessException
    {
        TimePickerSettings settings = new TimePickerSettings();
        assertTrue("Default clock must be available", settings.getClock() != null);
        assertTrue("Default clock must be in system default time zone", settings.getClock().getZone().equals(ZoneId.systemDefault()));
        settings = new TimePickerSettings(Locale.ENGLISH);
        assertTrue("Default clock must be available", settings.getClock() != null);
        assertTrue("Default clock must be in system default time zone", settings.getClock().getZone().equals(ZoneId.systemDefault()));
        Clock myClock = Clock.systemUTC();
        settings.setClock(myClock);
        assertTrue("Set clock must be returned", settings.getClock() == myClock);
        LocalTime initialTime = (LocalTime) TestHelpers.readPrivateField(TimePickerSettings.class, settings, "initialTime");
        assertTrue("intialtime is null as long as setInitialTimeToNow() has not been called", initialTime == null);
        settings.setClock(getClockFixedToInstant(2000, Month.JANUARY, 1, 15,55));
        settings.setInitialTimeToNow();
        initialTime = (LocalTime) TestHelpers.readPrivateField(TimePickerSettings.class, settings, "initialTime");
        assertTrue("intialtime is not null after call to as long as setInitialTimeToNow()", initialTime != null);
        assertTrue("intialtime must be 15:55 / 3:55pm", initialTime.equals(LocalTime.of(15, 55)));
    }

    @Test( expected = Test.None.class /* no exception expected */ )
    public void TestCustomClockCalenderPanel()
    {
        CalendarPanel panel = new CalendarPanel();
        java.time.YearMonth defaultDateNeverNull = panel.getDisplayedYearMonth();
        assertTrue("displayedYearMonth may never be null", defaultDateNeverNull != null);
        assertTrue("Year must be the year of today", defaultDateNeverNull.getYear() == LocalDate.now().getYear());
        assertTrue("Month must be the month of today", defaultDateNeverNull.getMonthValue()== LocalDate.now().getMonthValue());

        panel = new CalendarPanel(new DatePicker());
        defaultDateNeverNull = panel.getDisplayedYearMonth();
        assertTrue("displayedYearMonth may never be null", defaultDateNeverNull != null);
        assertTrue("Year must be the year of today", defaultDateNeverNull.getYear() == LocalDate.now().getYear());
        assertTrue("Month must be the month of today", defaultDateNeverNull.getMonthValue()== LocalDate.now().getMonthValue());

        DatePickerSettings settings = new DatePickerSettings(Locale.ENGLISH);
        settings.setClock(getClockFixedToInstant(1995, Month.OCTOBER, 31, 0, 0));
        panel = new CalendarPanel(settings);
        defaultDateNeverNull = panel.getDisplayedYearMonth();
        assertTrue("displayedYearMonth may never be null", defaultDateNeverNull != null);
        assertTrue("Year must be the year 1995", defaultDateNeverNull.getYear() == 1995);
        assertTrue("Month must be the month October", defaultDateNeverNull.getMonth()== Month.OCTOBER);
    }

    @Test( expected = Test.None.class /* no exception expected */ )
    public void TestCustomClockDatePicker()
    {
        DatePicker picker = new DatePicker();
        assertTrue(picker.getDate() == null);
        DatePickerSettings settings = new DatePickerSettings(Locale.ENGLISH);
        settings.setClock(getClockFixedToInstant(1995, Month.OCTOBER, 31, 14, 33));
        picker = new DatePicker(settings);
        picker.setDateToToday();
        assertTrue("Picker must have set a date of 1995-10-31", picker.getDate().equals(LocalDate.of(1995, Month.OCTOBER, 31)));
    }

    @Test( expected = Test.None.class /* no exception expected */ )
    public void TestCustomClockTimePicker()
    {
        TimePicker picker = new TimePicker();
        assertTrue(picker.getTime() == null);
        TimePickerSettings settings = new TimePickerSettings(Locale.ENGLISH);
        settings.setClock(getClockFixedToInstant(1995, Month.OCTOBER, 31, 14, 33));
        picker = new TimePicker(settings);
        picker.setTimeToNow();
        assertTrue("Picker must have set a time of 14:33 / 2:33pm", picker.getTime().equals(LocalTime.of(14, 33)));
    }

    @Test( expected = Test.None.class /* no exception expected */ )
    public void TestMouseHoverCalendarPanel() throws NoSuchFieldException, IllegalArgumentException, IllegalAccessException, NoSuchMethodException, InvocationTargetException
    {
        DatePickerSettings settings = new DatePickerSettings(Locale.ENGLISH);
        CalendarPanel panel = new CalendarPanel(settings);

        final Color generalHighlight = settings.getColor(
                DatePickerSettings.DateArea.BackgroundCalendarPanelLabelsOnHover);
        final Color yearMonthBackground = settings.getColor(DatePickerSettings.DateArea.BackgroundMonthAndYearMenuLabels);
        final Color yearMonthText = settings.getColor(DatePickerSettings.DateArea.TextMonthAndYearMenuLabels);

        verifyLabelHover(panel, "labelMonth", yearMonthBackground, yearMonthText, generalHighlight,
                yearMonthText);
        verifyLabelHover(panel, "labelYear", yearMonthBackground, yearMonthText, generalHighlight,
                yearMonthText);

        final Color todayLabelBackground = settings.getColor(DatePickerSettings.DateArea.BackgroundTodayLabel);
        final Color todayLabelText = settings.getColor(DatePickerSettings.DateArea.TextTodayLabel);
        verifyLabelHover(panel, "labelSetDateToToday", todayLabelBackground, todayLabelText, generalHighlight,
                todayLabelText);

        final Color clearLabelBackground = settings.getColor(DatePickerSettings.DateArea.BackgroundClearLabel);
        final Color clearLabelText = settings.getColor(DatePickerSettings.DateArea.TextClearLabel);
        verifyLabelHover(panel, "labelClearDate", clearLabelBackground, clearLabelText, generalHighlight,
                clearLabelText);
    }

        @Test( expected = Test.None.class /* no exception expected */ )
    public void TestCustomMouseHoverColorCalendarPanel() throws NoSuchFieldException, IllegalArgumentException, IllegalAccessException, NoSuchMethodException, InvocationTargetException
    {
        DatePickerSettings settings = new DatePickerSettings(Locale.ENGLISH);
        settings.setColor(DatePickerSettings.DateArea.BackgroundCalendarPanelLabelsOnHover, Color.red);
        settings.setColor(DatePickerSettings.DateArea.TextCalendarPanelLabelsOnHover, Color.yellow);
        CalendarPanel panel = new CalendarPanel(settings);

        final Color generalHighlight = Color.red;
        final Color generalHighlightText = Color.yellow;
        final Color yearMonthBackground = settings.getColor(DatePickerSettings.DateArea.BackgroundMonthAndYearMenuLabels);
        final Color yearMonthText = settings.getColor(DatePickerSettings.DateArea.TextMonthAndYearMenuLabels);

        verifyLabelHover(panel, "labelMonth", yearMonthBackground, yearMonthText, generalHighlight,
                generalHighlightText);
        verifyLabelHover(panel, "labelYear", yearMonthBackground, yearMonthText, generalHighlight,
                generalHighlightText);

        final Color todayLabelBackground = settings.getColor(DatePickerSettings.DateArea.BackgroundTodayLabel);
        final Color todayLabelText = settings.getColor(DatePickerSettings.DateArea.TextTodayLabel);
        verifyLabelHover(panel, "labelSetDateToToday", todayLabelBackground, todayLabelText, generalHighlight,
                generalHighlightText);

        final Color clearLabelBackground = settings.getColor(DatePickerSettings.DateArea.BackgroundClearLabel);
        final Color clearLabelText = settings.getColor(DatePickerSettings.DateArea.TextClearLabel);
        verifyLabelHover(panel, "labelClearDate", clearLabelBackground, clearLabelText, generalHighlight,
                generalHighlightText);
    }

    void verifyLabelHover(CalendarPanel panel, String labelname, Color defaultBackground, Color defaultText, Color highlightBackground, Color highlightText) throws NoSuchFieldException, IllegalArgumentException, IllegalAccessException, NoSuchMethodException, InvocationTargetException
    {
        TestHelpers.suppressIllegalReflectiveAccessWarning(java.awt.Component.class, TestFeatures.class);
        JLabel labeltoverify = (JLabel) TestHelpers.readPrivateField(CalendarPanel.class, panel, labelname);

        assertTrue(labelname+" has wrong background color: "+labeltoverify.getBackground().toString(), labeltoverify.getBackground().equals(defaultBackground));
        assertTrue(labelname+" has wrong text color: "+labeltoverify.getForeground().toString(), labeltoverify.getForeground().equals(defaultText));
        MouseEvent testEvent = new MouseEvent(labeltoverify, MouseEvent.MOUSE_ENTERED, 0, 0, 0, 0, 0, false);
        TestHelpers.accessPrivateMethod(java.awt.Component.class, "processEvent", java.awt.AWTEvent.class).invoke(labeltoverify, testEvent);
        assertTrue(labelname+" has wrong background color: "+labeltoverify.getBackground().toString(), labeltoverify.getBackground().equals(highlightBackground));
        assertTrue(labelname+" has wrong text color: "+labeltoverify.getForeground().toString(), labeltoverify.getForeground().equals(highlightText));
        testEvent = new MouseEvent(labeltoverify, MouseEvent.MOUSE_EXITED, 0, 0, 0, 0, 0, false);
        TestHelpers.accessPrivateMethod(java.awt.Component.class, "processEvent", java.awt.AWTEvent.class).invoke(labeltoverify, testEvent);
        assertTrue(labelname+" has wrong background color: "+labeltoverify.getBackground().toString(), labeltoverify.getBackground().equals(defaultBackground));
        assertTrue(labelname+" has wrong text color: "+labeltoverify.getForeground().toString(), labeltoverify.getForeground().equals(defaultText));
    }



    // helper functions
    Clock getClockFixedToInstant(int year, Month month, int day, int hours, int minutes)
    {
        LocalDateTime fixedInstant = LocalDateTime.of(LocalDate.of(year, month, day), LocalTime.of(hours,minutes));
        return Clock.fixed(fixedInstant.toInstant(ZoneOffset.UTC), ZoneId.of("Z"));
    }

}
