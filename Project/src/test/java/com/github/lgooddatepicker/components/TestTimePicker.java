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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import com.github.lgooddatepicker.TestHelpers;
import com.github.lgooddatepicker.components.TimePickerSettings.TimeArea;
import com.github.lgooddatepicker.optionalusertools.TimeChangeListener;
import com.github.lgooddatepicker.zinternaltools.TimeChangeEvent;
import java.awt.Color;
import java.time.Clock;
import java.time.LocalTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Locale;
import org.junit.Test;

/** Tests for the TimePicker component features */
public class TestTimePicker {

  @Test(expected = Test.None.class /* no exception expected */)
  public void TestCustomClockTimeSettings()
      throws NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
    TimePickerSettings settings = new TimePickerSettings();
    assertTrue("Default clock must be available", settings.getClock() != null);
    assertTrue(
        "Default clock must be in system default time zone",
        settings.getClock().getZone().equals(ZoneId.systemDefault()));
    settings = new TimePickerSettings(Locale.ENGLISH);
    assertTrue("Default clock must be available", settings.getClock() != null);
    assertTrue(
        "Default clock must be in system default time zone",
        settings.getClock().getZone().equals(ZoneId.systemDefault()));
    Clock myClock = Clock.systemUTC();
    settings.setClock(myClock);
    assertTrue("Set clock must be returned", settings.getClock() == myClock);
    LocalTime initialTime =
        (LocalTime) TestHelpers.readPrivateField(TimePickerSettings.class, settings, "initialTime");
    assertTrue(
        "intialtime is null as long as setInitialTimeToNow() has not been called",
        initialTime == null);
    settings.setClock(TestHelpers.getClockFixedToInstant(2000, Month.JANUARY, 1, 15, 55));
    settings.setInitialTimeToNow();
    initialTime =
        (LocalTime) TestHelpers.readPrivateField(TimePickerSettings.class, settings, "initialTime");
    assertTrue(
        "intialtime is not null after call to as long as setInitialTimeToNow()",
        initialTime != null);
    assertTrue("intialtime must be 15:55 / 3:55pm", initialTime.equals(LocalTime.of(15, 55)));
  }

  @Test(expected = Test.None.class /* no exception expected */)
  public void TestCustomClockTimePicker() {
    TimePicker picker = new TimePicker();
    assertTrue(picker.getTime() == null);
    TimePickerSettings settings = new TimePickerSettings(Locale.ENGLISH);
    settings.setClock(TestHelpers.getClockFixedToInstant(1995, Month.OCTOBER, 31, 14, 33));
    picker = new TimePicker(settings);
    picker.setTimeToNow();
    assertTrue(
        "Picker must have set a time of 14:33 / 2:33pm",
        picker.getTime().equals(LocalTime.of(14, 33)));
  }

  /** Basic test of the time picker functions */
  @Test(expected = Test.None.class /* no exception expected */)
  public void verifyTimePickerBasics() {
    TimePicker picker = new TimePicker();

    // Test the range of Local times
    picker.setTime(LocalTime.MIN);
    assertEquals("minium local time could not be used", LocalTime.MIN, picker.getTime());
    picker.setTime(LocalTime.NOON);
    assertEquals("noon local time could not be used", LocalTime.NOON, picker.getTime());
    picker.setTime(LocalTime.MAX);
    assertEquals(
        "maximum local time could not be used",
        LocalTime.MAX.truncatedTo(ChronoUnit.MINUTES),
        picker.getTime());

    // test clearing the component by setting the time to null
    picker.setTime(null);
    assertNull("null time could not be used", picker.getTime());

    // reset the the picker back to noon
    picker.setTime(LocalTime.NOON);
    // ensure it can be set again after set to null
    assertEquals("noon local time could not be used", LocalTime.NOON, picker.getTime());

    // clear it again
    picker.clear();
    // ensure that clear also sets time to null
    assertNull("Clear did not make the time null", picker.getTime());
  }

  /** Tests that the various parts of the TimePicker can be enabled and disabled as expected */
  @Test(expected = Test.None.class /* no exception expected */)
  public void verifyTimePickerEnabled() {
    TimePicker picker = new TimePicker();
    picker.setEnableArrowKeys(true);
    assertTrue("Arrow keys not enabled", picker.getEnableArrowKeys());
    picker.setEnableArrowKeys(false);
    assertFalse("Arrow keys not disabled", picker.getEnableArrowKeys());

    assertNotNull("Picker settings were null", picker.getSettings());

    picker.setEnabled(false);
    assertFalse("Picker was not disabled", picker.isEnabled());
    assertFalse(
        "Menu component was not disabled", picker.getComponentToggleTimeMenuButton().isEnabled());
    assertFalse(
        "TextField component was not disabled", picker.getComponentTimeTextField().isEnabled());

    picker.setEnabled(true);
    assertTrue("Picker was not disabled", picker.isEnabled());
    assertTrue(
        "Menu component was not enabled", picker.getComponentToggleTimeMenuButton().isEnabled());
    assertTrue(
        "TextField component was not enabled", picker.getComponentTimeTextField().isEnabled());
  }

  /**
   * Test to ensure that the parsing and strings work as expected. Here Locale.ENGLISH is specified
   * to ensure the test is consistent when run on systems in other Locales.
   */
  @Test(expected = Test.None.class /* no exception expected */)
  public void verifyTimePickerParsingAndStrings() {
    TimePickerSettings settings = new TimePickerSettings(Locale.ENGLISH);
    settings.useLowercaseForDisplayTime = true;
    TimePicker picker = new TimePicker(settings);
    // valid text
    picker.setText("12:22");
    assertTrue("Expected time to be valid", picker.isTextValid("12:22"));
    assertTrue("Expected field to be valid", picker.isTextFieldValid());
    assertEquals("Did not retain user text", "12:22", picker.getText());
    assertEquals(
        "Entered time not translated to local time", LocalTime.of(12, 22), picker.getTime());
    assertEquals(
        "Expected time string for valid time",
        "12:22",
        picker.getTimeStringOrSuppliedString("supply"));

    // invalid text
    picker.setText("44:17");
    assertFalse("Expected time to be invalid", picker.isTextValid("44:17"));
    assertFalse("Expected timefield  to be invalid", picker.isTextFieldValid());
    assertEquals("Did not retain user text", "44:17", picker.getText());
    // because time is invalid the old local time should still be present
    assertEquals(
        "Invalid time was translated to local time", LocalTime.of(12, 22), picker.getTime());

    // null time
    picker.setTime(null);
    assertEquals("Expected empty string for null time", "", picker.getTimeStringOrEmptyString());
    assertEquals(
        "Expected supplied string for null time",
        "supply",
        picker.getTimeStringOrSuppliedString("supply"));

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
    assertEquals(
        "toString should match toTime", picker.getTimeStringOrEmptyString(), picker.toString());
    picker.setTime(LocalTime.of(8, 32, 12));
    assertEquals(
        "toString should match toTime", picker.getTimeStringOrEmptyString(), picker.toString());

    assertTrue("Expect noon to be an allowed time", picker.isTimeAllowed(LocalTime.NOON));
  }

  /** Tests to ensure that the TimeChangeListener works as expected. */
  @Test(expected = Test.None.class /* no exception expected */)
  public void verifyTimeChangeListeners() {
    TimePicker picker = new TimePicker();
    TestableTimeChangeListener listener = new TestableTimeChangeListener();
    picker.addTimeChangeListener(listener);
    assertNull("listener event not null at start", listener.getLastEvent());
    picker.setTime(LocalTime.MIN);
    assertEquals(
        "Listener did not receive new time", LocalTime.MIN, listener.getLastEvent().getNewTime());
    assertNull("Listener did not remember old time", listener.getLastEvent().getOldTime());
    assertEquals(
        "Event did not originate from time picker", picker, listener.getLastEvent().getSource());

    TimeChangeEvent lastEvent = listener.getLastEvent();
    picker.setTime(LocalTime.MIN);
    assertTrue("Event updated when time did not change", lastEvent == listener.getLastEvent());

    picker.setTime(LocalTime.NOON);
    assertEquals(
        "Listener did not remember old time", LocalTime.MIN, listener.getLastEvent().getOldTime());
    assertEquals(
        "Listener did not receive new time", LocalTime.NOON, listener.getLastEvent().getNewTime());

    picker.setTime(null);
    assertNull("Listener did not receive null time", listener.getLastEvent().getNewTime());

    assertTrue(
        "Listener was not in the list of listeners",
        picker.getTimeChangeListeners().contains(listener));

    picker.removeTimeChangeListener(listener);
    picker.setTime(LocalTime.NOON);
    assertNull(
        "Listener received an update after being uninstalled",
        listener.getLastEvent().getNewTime());
  }

  /** Test to ensure that the custom colors for the disabled time picker work as excepcted */
  @Test(expected = Test.None.class /* no exception expected */)
  public void verifyCustomDisabledColors() {
    final Color defaultDisabledText =
        new TimePickerSettings().getColor(TimeArea.TimePickerTextDisabled);
    final Color defaultDisabledBackground =
        new TimePickerSettings().getColor(TimeArea.TextFieldBackgroundDisabled);

    TimePickerSettings settings = new TimePickerSettings(Locale.ENGLISH);
    settings.setColor(TimeArea.TimePickerTextDisabled, Color.yellow);
    settings.setColor(TimeArea.TextFieldBackgroundDisabled, Color.blue);

    TimePicker picker = new TimePicker(settings);

    validateTimePickerDisabledColor(picker, Color.yellow, Color.blue);
    picker.setEnabled(false);
    validateTimePickerDisabledColor(picker, Color.yellow, Color.blue);

    picker = new TimePicker(new TimePickerSettings(Locale.ENGLISH));
    validateTimePickerDisabledColor(picker, defaultDisabledText, defaultDisabledBackground);
    picker.setEnabled(false);
    validateTimePickerDisabledColor(picker, defaultDisabledText, defaultDisabledBackground);

    picker.getSettings().setColor(TimeArea.TimePickerTextDisabled, Color.yellow);
    validateTimePickerDisabledColor(picker, Color.yellow, defaultDisabledBackground);
    picker.getSettings().setColor(TimeArea.TextFieldBackgroundDisabled, Color.blue);
    validateTimePickerDisabledColor(picker, Color.yellow, Color.blue);
    picker.setEnabled(true);
    validateTimePickerDisabledColor(picker, Color.yellow, Color.blue);
  }

  void validateTimePickerDisabledColor(
      TimePicker picker, Color disabledTextColor, Color disabledBackground) {
    final Color validText = new TimePickerSettings().getColor(TimeArea.TimePickerTextValidTime);
    final Color enabledBackground =
        new TimePickerSettings().getColor(TimeArea.TextFieldBackgroundValidTime);

    assertTrue(picker.getComponentTimeTextField().getForeground().equals(validText));
    assertFalse(picker.getComponentTimeTextField().getForeground().equals(disabledTextColor));
    assertTrue(picker.getComponentTimeTextField().getDisabledTextColor().equals(disabledTextColor));
    assertFalse(picker.getComponentTimeTextField().getDisabledTextColor().equals(validText));
    if (picker.isEnabled()) {
      assertTrue(picker.getComponentTimeTextField().getBackground().equals(enabledBackground));
      assertFalse(picker.getComponentTimeTextField().getBackground().equals(disabledBackground));
    } else {
      assertTrue(picker.getComponentTimeTextField().getBackground().equals(disabledBackground));
      assertFalse(picker.getComponentTimeTextField().getBackground().equals(enabledBackground));
    }
  }

  // helper class
  private class TestableTimeChangeListener implements TimeChangeListener {
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
