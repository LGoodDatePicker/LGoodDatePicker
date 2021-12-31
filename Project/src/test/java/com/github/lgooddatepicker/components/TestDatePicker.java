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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.github.lgooddatepicker.TestHelpers;
import java.awt.Color;
import java.lang.reflect.InvocationTargetException;
import java.time.Clock;
import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.time.ZoneId;
import java.util.Locale;
import org.junit.Test;

public class TestDatePicker {

  @Test(expected = Test.None.class /* no exception expected */)
  public void TestCustomClockDateSettings()
      throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
    DatePickerSettings settings = new DatePickerSettings();
    assertTrue("Default clock must be available", settings.getClock() != null);
    assertTrue(
        "Default clock must be in system default time zone",
        settings.getClock().getZone().equals(ZoneId.systemDefault()));
    settings = new DatePickerSettings(Locale.ENGLISH);
    assertTrue("Default clock must be available", settings.getClock() != null);
    assertTrue(
        "Default clock must be in system default time zone",
        settings.getClock().getZone().equals(ZoneId.systemDefault()));
    Clock myClock = Clock.systemUTC();
    settings.setClock(myClock);
    assertTrue("Set clock must be returned", settings.getClock() == myClock);
    settings.setClock(TestHelpers.getClockFixedToInstant(1993, Month.MARCH, 15, 12, 00));
    settings.setDefaultYearMonth(null);
    YearMonth defaultyearmonth =
        (YearMonth)
            TestHelpers.accessPrivateMethod(DatePickerSettings.class, "zGetDefaultYearMonthAsUsed")
                .invoke(settings);
    assertTrue(defaultyearmonth.getYear() == 1993);
    assertTrue(defaultyearmonth.getMonth() == Month.MARCH);
  }

  @Test(expected = Test.None.class /* no exception expected */)
  public void TestCustomClockDatePicker() {
    DatePicker picker = new DatePicker();
    assertTrue(picker.getDate() == null);
    DatePickerSettings settings = new DatePickerSettings(Locale.ENGLISH);
    settings.setClock(TestHelpers.getClockFixedToInstant(1995, Month.OCTOBER, 31, 14, 33));
    picker = new DatePicker(settings);
    picker.setDateToToday();
    assertTrue(
        "Picker must have set a date of 1995-10-31",
        picker.getDate().equals(LocalDate.of(1995, Month.OCTOBER, 31)));
  }

  @Test(expected = Test.None.class /* no exception expected */)
  public void TestCustomDisabledPickerColor() {
    final Color defaultDisabledText =
        new DatePickerSettings().getColor(DatePickerSettings.DateArea.DatePickerTextDisabled);
    final Color defaultDisabledBackground =
        new DatePickerSettings().getColor(DatePickerSettings.DateArea.TextFieldBackgroundDisabled);

    DatePickerSettings settings = new DatePickerSettings(Locale.ENGLISH);
    settings.setColor(DatePickerSettings.DateArea.DatePickerTextDisabled, Color.yellow);
    settings.setColor(DatePickerSettings.DateArea.TextFieldBackgroundDisabled, Color.blue);

    DatePicker picker = new DatePicker(settings);

    validateDatePickerDisabledColor(picker, Color.yellow, Color.blue);
    picker.setEnabled(false);
    validateDatePickerDisabledColor(picker, Color.yellow, Color.blue);

    picker.setSettings(new DatePickerSettings(Locale.ENGLISH));
    validateDatePickerDisabledColor(picker, defaultDisabledText, defaultDisabledBackground);

    picker.getSettings().setColor(DatePickerSettings.DateArea.DatePickerTextDisabled, Color.yellow);
    validateDatePickerDisabledColor(picker, Color.yellow, defaultDisabledBackground);
    picker
        .getSettings()
        .setColor(DatePickerSettings.DateArea.TextFieldBackgroundDisabled, Color.blue);
    validateDatePickerDisabledColor(picker, Color.yellow, Color.blue);
    picker.setEnabled(true);
    validateDatePickerDisabledColor(picker, Color.yellow, Color.blue);

    picker = new DatePicker(new DatePickerSettings(Locale.ENGLISH));
    validateDatePickerDisabledColor(picker, defaultDisabledText, defaultDisabledBackground);
  }

  void validateDatePickerDisabledColor(
      DatePicker picker, Color disabledTextColor, Color disabledBackground) {
    final Color validText =
        new DatePickerSettings().getColor(DatePickerSettings.DateArea.DatePickerTextValidDate);
    final Color enabledBackground =
        new DatePickerSettings().getColor(DatePickerSettings.DateArea.TextFieldBackgroundValidDate);

    assertTrue(picker.getComponentDateTextField().getForeground().equals(validText));
    assertFalse(picker.getComponentDateTextField().getForeground().equals(disabledTextColor));
    assertTrue(picker.getComponentDateTextField().getDisabledTextColor().equals(disabledTextColor));
    assertFalse(picker.getComponentDateTextField().getDisabledTextColor().equals(validText));
    if (picker.isEnabled()) {
      assertTrue(picker.getComponentDateTextField().getBackground().equals(enabledBackground));
      assertFalse(picker.getComponentDateTextField().getBackground().equals(disabledBackground));
    } else {
      assertTrue(picker.getComponentDateTextField().getBackground().equals(disabledBackground));
      assertFalse(picker.getComponentDateTextField().getBackground().equals(enabledBackground));
    }
  }
}
