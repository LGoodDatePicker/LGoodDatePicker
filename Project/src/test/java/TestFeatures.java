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
      YearMonth defaultyearmonth = (YearMonth)invokePrivateMethod(DatePickerSettings.class, settings, "zGetDefaultYearMonthAsUsed");
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

    Object invokePrivateMethod(Class<?> clazz, Object instance, String method, Object... args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException
    {
        java.lang.reflect.Method private_method =  clazz.getDeclaredMethod(method);
        private_method.setAccessible(true);
        return private_method.invoke(instance, args);
    }
}
