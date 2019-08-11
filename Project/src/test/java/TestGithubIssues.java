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
import java.awt.Color;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
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
    
}
