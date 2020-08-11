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
import java.util.Locale;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

// the tests in this class test basic requirements that always have to be fulfilled
// these are not complete, feel free to extend them
public class TestRequirements
{

    @Test( expected = Test.None.class /* no exception expected */ )
    public void CalendarPanelConstructors()
    {
      CalendarPanel panel;
      panel = new CalendarPanel();
      assertTrue(panel.getDisplayedYearMonth() != null);
      panel = new CalendarPanel(new DatePickerSettings());
      assertTrue(panel.getDisplayedYearMonth() != null);
      panel = new CalendarPanel(new DatePicker());
      assertTrue(panel.getDisplayedYearMonth() != null);
    }

    @Test( expected = Test.None.class /* no exception expected */ )
    public void DatePickerSettingsConstructors()
    {
      DatePickerSettings settings;
      settings = new DatePickerSettings();
      assertTrue(settings.getLocale().equals(Locale.getDefault()));
      settings = new DatePickerSettings(Locale.ENGLISH);
      assertTrue(settings.getLocale().equals(Locale.ENGLISH));
    }

    @Test( expected = Test.None.class /* no exception expected */ )
    public void DatePickerConstructors()
    {
      DatePicker picker;
      picker = new DatePicker();
      assertTrue(picker.getSettings().getLocale().equals(Locale.getDefault()));
      picker = new DatePicker(new DatePickerSettings(Locale.ENGLISH));
      assertTrue(picker.getSettings().getLocale().equals(Locale.ENGLISH));
    }

    @Test( expected = Test.None.class /* no exception expected */ )
    public void TimePickerSettingsConstructors()
    {
      TimePickerSettings settings;
      settings = new TimePickerSettings();
      assertTrue(settings.getLocale().equals(Locale.getDefault()));
      settings = new TimePickerSettings(Locale.ENGLISH);
      assertTrue(settings.getLocale().equals(Locale.ENGLISH));
    }

    @Test( expected = Test.None.class /* no exception expected */ )
    public void TimePickerConstructors()
    {
      TimePicker picker;
      picker = new TimePicker();
      assertTrue(picker.getSettings().getLocale().equals(Locale.getDefault()));
      picker = new TimePicker(new TimePickerSettings(Locale.ENGLISH));
      assertTrue(picker.getSettings().getLocale().equals(Locale.ENGLISH));
    }

}
