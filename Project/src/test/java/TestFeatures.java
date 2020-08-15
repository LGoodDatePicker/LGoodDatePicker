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
import com.github.lgooddatepicker.components.TimePicker;
import com.github.lgooddatepicker.components.TimePickerSettings;
import com.github.lgooddatepicker.optionalusertools.TimeChangeListener;
import com.github.lgooddatepicker.zinternaltools.TimeChangeEvent;

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
import java.time.temporal.ChronoUnit;
import java.util.Locale;
import javax.swing.JLabel;
import org.junit.Test;
import static org.junit.Assert.*;

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
        YearMonth defaultyearmonth = (YearMonth)accessPrivateMethod(DatePickerSettings.class, "zGetDefaultYearMonthAsUsed").invoke(settings);
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
        LocalTime initialTime = (LocalTime)readPrivateField(TimePickerSettings.class, settings, "initialTime");
        assertTrue("intialtime is null as long as setInitialTimeToNow() has not been called", initialTime == null);
        settings.setClock(getClockFixedToInstant(2000, Month.JANUARY, 1, 15,55));
        settings.setInitialTimeToNow();
        initialTime = (LocalTime)readPrivateField(TimePickerSettings.class, settings, "initialTime");
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
        // Allow access to private methods of Component, only needed if compiled for java > 1.8
        //if (!TestFeatures.class.getModule().isNamed()) {
        //  java.awt.Component.class.getModule().addOpens(java.awt.Component.class.getPackageName(), TestFeatures.class.getModule());
        //}

        DatePickerSettings settings = new DatePickerSettings(Locale.ENGLISH);
        CalendarPanel panel = new CalendarPanel(settings);

        final Color generalHighlight = new Color(184,207,229);
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

    void verifyLabelHover(CalendarPanel panel, String labelname, Color defaultBackground, Color defaultText, Color highlightBackground, Color highlightText) throws NoSuchFieldException, IllegalArgumentException, IllegalAccessException, NoSuchMethodException, InvocationTargetException
    {
        JLabel labeltoverify = (JLabel)readPrivateField(CalendarPanel.class, panel, labelname);

        assertTrue(labelname+" has wrong background color: "+labeltoverify.getBackground().toString(), labeltoverify.getBackground().equals(defaultBackground));
        assertTrue(labelname+" has wrong text color: "+labeltoverify.getForeground().toString(), labeltoverify.getForeground().equals(defaultText));
        MouseEvent testEvent = new MouseEvent(labeltoverify, MouseEvent.MOUSE_ENTERED, 0, 0, 0, 0, 0, false);
        accessPrivateMethod(java.awt.Component.class, "processEvent", java.awt.AWTEvent.class).invoke(labeltoverify, testEvent);
        assertTrue(labelname+" has wrong background color: "+labeltoverify.getBackground().toString(), labeltoverify.getBackground().equals(highlightBackground));
        assertTrue(labelname+" has wrong text color: "+labeltoverify.getForeground().toString(), labeltoverify.getForeground().equals(highlightText));
        testEvent = new MouseEvent(labeltoverify, MouseEvent.MOUSE_EXITED, 0, 0, 0, 0, 0, false);
        accessPrivateMethod(java.awt.Component.class, "processEvent", java.awt.AWTEvent.class).invoke(labeltoverify, testEvent);
        assertTrue(labelname+" has wrong background color: "+labeltoverify.getBackground().toString(), labeltoverify.getBackground().equals(defaultBackground));
        assertTrue(labelname+" has wrong text color: "+labeltoverify.getForeground().toString(), labeltoverify.getForeground().equals(defaultText));
    }

    @Test( expected = Test.None.class /* no exception expected */ )
    public void verifyTimePicker()
    {
        TimePicker picker = new TimePicker();
        picker.setTime(LocalTime.MIN);
        assertEquals("minium local time could not be used", LocalTime.MIN, picker.getTime());
        picker.setTime(LocalTime.NOON);
        assertEquals("noon local time could not be used", LocalTime.NOON, picker.getTime());
        picker.setTime(LocalTime.MAX);
        assertEquals("maximum local time could not be used", LocalTime.MAX.truncatedTo(ChronoUnit.MINUTES), picker.getTime());
        picker.setTime(null);
        assertNull("null time could not be used", picker.getTime());
        picker.setEnableArrowKeys(true);
        assertTrue("Arrow keys not enabled", picker.getEnableArrowKeys());
        picker.setEnableArrowKeys(false);
        assertFalse("Arrow keys not disabled", picker.getEnableArrowKeys());
        
        assertNotNull("Picker settings were null", picker.getSettings());       
        
        picker.setEnabled(false);
        assertFalse("Picker was not disabled", picker.isEnabled());
        assertFalse("Menu component was not disabled", picker.getComponentToggleTimeMenuButton().isEnabled());
        assertFalse("TextField component was not disabled", picker.getComponentTimeTextField().isEnabled());
        
        picker.setEnabled(true);
        assertTrue("Picker was not disabled", picker.isEnabled());
        assertTrue("Menu component was not enabled", picker.getComponentToggleTimeMenuButton().isEnabled());
        assertTrue("TextField component was not enabled", picker.getComponentTimeTextField().isEnabled());
        
        picker.setTime(LocalTime.NOON);
        assertEquals("noon local time could not be used", LocalTime.NOON, picker.getTime());
        picker.clear();
        assertNull("Clear did not make the time null", picker.getTime());
        
        // valid text
        picker.setText("12:22");
        assertTrue("Expected time to be valid", picker.isTextValid("12:22"));
        assertTrue("Expected field to be valid", picker.isTextFieldValid());
        assertEquals("Did not retain user text", "12:22", picker.getText());
        assertEquals("Entered time not translated to local time", LocalTime.of(12, 22), picker.getTime());
        assertEquals("Expected time string for valid time", "12:22", picker.getTimeStringOrSuppliedString("supply"));
        
        // invalid text
        picker.setText("44:17");
        assertFalse("Expected time to be invalid", picker.isTextValid("44:17"));
        assertFalse("Expected timefield  to be invalid", picker.isTextFieldValid());
        assertEquals("Did not retain user text", "44:17", picker.getText());
        // because time is invalid the old local time should still be present
        assertEquals("Invalid time was translated to local time", LocalTime.of(12, 22), picker.getTime());
        
        // null time
        picker.setTime(null);
        assertEquals("Expected empty string for null time", "", picker.getTimeStringOrEmptyString());
        assertEquals("Expected supplied string for null time", "supply", picker.getTimeStringOrSuppliedString("supply"));
        
        // null text
        assertFalse("null text was considered valid", picker.isTextValid(null));
        picker.setText(null);
        assertEquals("null text did not become blank text", "", picker.getText());
        
        // empty text
        assertTrue("spaces only text was considered valid", picker.isTextValid("  "));
        picker.setText("  ");
        assertEquals("spaces text was not returned", "  ", picker.getText());
        
        // toString
        picker.setTime(LocalTime.of(8, 32));
        assertEquals("toString should match toTime", picker.getTimeStringOrEmptyString(), picker.toString());
        picker.setTime(LocalTime.of(8, 32, 12));
        assertEquals("toString should match toTime", picker.getTimeStringOrEmptyString(), picker.toString());
        
        assertTrue("Expect noon to be an allowed time", picker.isTimeAllowed(LocalTime.NOON));
        
    }

    @Test( expected = Test.None.class /* no exception expected */ )
    public void verifyTimeChangeListeners()
    {
        TimePicker picker = new TimePicker();
        TestableTimeChangeListener listener = new TestableTimeChangeListener();
        picker.addTimeChangeListener(listener);
        assertNull("listener event not null at start", listener.getLastEvent());
        picker.setTime(LocalTime.MIN);
        assertEquals("Listener did not receive new time", LocalTime.MIN, listener.getLastEvent().getNewTime());
        assertNull("Listener did not remember old time", listener.getLastEvent().getOldTime());
        assertEquals("Event did not originate from time picker", picker, listener.getLastEvent().getSource());

        TimeChangeEvent lastEvent = listener.getLastEvent();
        picker.setTime(LocalTime.MIN);
        assertTrue("Event updated when time did not change", lastEvent == listener.getLastEvent());

        picker.setTime(LocalTime.NOON);
        assertEquals("Listener did not remember old time", LocalTime.MIN, listener.getLastEvent().getOldTime());
        assertEquals("Listener did not receive new time", LocalTime.NOON, listener.getLastEvent().getNewTime());

        picker.setTime(null);
        assertNull("Listener did not receive null time", listener.getLastEvent().getNewTime());
        
        assertTrue("Listener was not in the list of listeners", picker.getTimeChangeListeners().contains(listener));
        
        picker.removeTimeChangeListener(listener);
        picker.setTime(LocalTime.NOON);
        assertNull("Listener received an update after being uninstalled", listener.getLastEvent().getNewTime());
        
    }

    // helper functions
    Clock getClockFixedToInstant(int year, Month month, int day, int hours, int minutes)
    {
        LocalDateTime fixedInstant = LocalDateTime.of(LocalDate.of(year, month, day), LocalTime.of(hours,minutes));
        return Clock.fixed(fixedInstant.toInstant(ZoneOffset.UTC), ZoneId.of("Z"));
    }

    Object readPrivateField(Class<?> clazz, Object instance, String field) throws NoSuchFieldException, IllegalArgumentException, IllegalAccessException
    {
        java.lang.reflect.Field private_field =  clazz.getDeclaredField(field);
        private_field.setAccessible(true);
        return private_field.get(instance);
    }

    java.lang.reflect.Method accessPrivateMethod(Class<?> clazz, String method, Class<?>... argclasses) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException
    {
        java.lang.reflect.Method private_method =  clazz.getDeclaredMethod(method, argclasses);
        private_method.setAccessible(true);
        return private_method;
    }

    //helper class
    private class TestableTimeChangeListener implements TimeChangeListener
    {
        TimeChangeEvent lastEvent;

        @Override
        public void timeChanged(TimeChangeEvent event) {
            lastEvent = event;
        }

        TimeChangeEvent getLastEvent() {
            return lastEvent;
        }
    }
}
