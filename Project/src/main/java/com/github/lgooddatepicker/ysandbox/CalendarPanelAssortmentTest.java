package com.github.lgooddatepicker.ysandbox;

import com.github.lgooddatepicker.components.CalendarPanel;
import com.github.lgooddatepicker.components.DatePickerSettings;
import com.github.lgooddatepicker.components.DatePickerSettings.DateArea;
import com.github.lgooddatepicker.optionalusertools.CalendarBorderProperties;
import com.github.lgooddatepicker.optionalusertools.DateHighlightPolicy;
import com.github.lgooddatepicker.optionalusertools.DateVetoPolicy;
import com.github.lgooddatepicker.zinternaltools.HighlightInformation;
import java.awt.Color;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Locale;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * CalendarPanelAssortmentTest.
 */
public class CalendarPanelAssortmentTest {

    public static void main(String[] args) {

        ///////////////////////////////////////////////////////////////////////////////////////////
        // Create a frame, a panel, and our demo buttons.
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panel = new JPanel();
        frame.getContentPane().add(panel);

        ///////////////////////////////////////////////////////////////////////////////////////////
        // This section creates CalendarPanels, with various features. (presetting preferred.)
        //
        int rowMultiplier = 4;
        CalendarPanel calendarPanel;

        // Create a settings variable for repeated use.
        DatePickerSettings dateSettings;
        int row = rowMultiplier;

        // Create a CalendarPanel: With default settings
        calendarPanel = new CalendarPanel();
        panel.add(calendarPanel);

        // Create a CalendarPanel: With highlight policy.
        dateSettings = new DatePickerSettings();
        dateSettings.setHighlightPolicy(new SampleHighlightPolicy());
        dateSettings.setWeekNumbersDisplayed(true, true);
        calendarPanel = new CalendarPanel(dateSettings);
        panel.add(calendarPanel);

        // Create a CalendarPanel: With veto policy.
        // Note: Veto policies can only be set after constructing the CalendarPanel.
        dateSettings = new DatePickerSettings();
        dateSettings.setWeekNumbersDisplayed(true, true);
        calendarPanel = new CalendarPanel(dateSettings);
        dateSettings.setVetoPolicy(new SampleDateVetoPolicy());
        panel.add(calendarPanel);

        // Create a CalendarPanel: With both policies.
        // Note: Veto policies can only be set after constructing the CalendarPanel.
        dateSettings = new DatePickerSettings();
        dateSettings.setHighlightPolicy(new SampleHighlightPolicy());
        dateSettings.setWeekNumbersDisplayed(true, true);
        calendarPanel = new CalendarPanel(dateSettings);
        dateSettings.setVetoPolicy(new SampleDateVetoPolicy());
        panel.add(calendarPanel);

        // Create a CalendarPanel: Change calendar size.
        dateSettings = new DatePickerSettings();
        int newHeight = (int) (dateSettings.getSizeDatePanelMinimumHeight() * 1.7);
        int newWidth = (int) (dateSettings.getSizeDatePanelMinimumWidth() * 1.7);
        dateSettings.setSizeDatePanelMinimumHeight(newHeight);
        dateSettings.setSizeDatePanelMinimumWidth(newWidth);
        dateSettings.setWeekNumbersDisplayed(true, true);
        dateSettings.setWeekNumbersDisplayed(true, true);
        calendarPanel = new CalendarPanel(dateSettings);
        panel.add(calendarPanel);

        // Create a CalendarPanel: Custom color.
        dateSettings = new DatePickerSettings();
        dateSettings.setColor(DateArea.BackgroundOverallCalendarPanel, Color.BLUE);
        dateSettings.setColorBackgroundWeekdayLabels(Color.PINK, true);
        dateSettings.setColorBackgroundWeekNumberLabels(Color.PINK, true);
        dateSettings.setColor(DateArea.BackgroundTopLeftLabelAboveWeekNumbers, Color.PINK);
        dateSettings.setColor(DateArea.BackgroundMonthAndYearMenuLabels, Color.ORANGE);
        dateSettings.setColor(DateArea.BackgroundTodayLabel, Color.CYAN);
        dateSettings.setColor(DateArea.BackgroundClearLabel, Color.GREEN);
        dateSettings.setColor(DateArea.BackgroundMonthAndYearNavigationButtons, Color.MAGENTA);
        dateSettings.setWeekNumbersDisplayed(true, true);
        calendarPanel = new CalendarPanel(dateSettings);
        panel.add(calendarPanel);

        // Create a CalendarPanel: Change first weekday.
        dateSettings = new DatePickerSettings();
        dateSettings.setFirstDayOfWeek(DayOfWeek.THURSDAY);
        calendarPanel = new CalendarPanel(dateSettings);
        panel.add(calendarPanel);

        // Create a CalendarPanel: No empty dates. (aka null)
        dateSettings = new DatePickerSettings();
        dateSettings.setAllowEmptyDates(false);
        dateSettings.setWeekNumbersDisplayed(true, true);
        calendarPanel = new CalendarPanel(dateSettings);
        panel.add(calendarPanel);

        // Create a CalendarPanel: Localized (Greek)
        Locale datePickerLocale = new Locale("el");
        dateSettings = new DatePickerSettings(datePickerLocale);
        dateSettings.setWeekNumbersDisplayed(true, true);
        calendarPanel = new CalendarPanel(dateSettings);
        calendarPanel.setSelectedDate(LocalDate.of(2016, Month.APRIL, 15));
        panel.add(calendarPanel);

        // Create a CalendarPanel: With crazy border properties.
        dateSettings = new DatePickerSettings();
        // Create a list to hold the border properties.
        ArrayList<CalendarBorderProperties> customPropertiesList
                = new ArrayList<CalendarBorderProperties>();
        // Set the borders properties for everything to Blue.
        CalendarBorderProperties weekdayLabelBorderProperties = new CalendarBorderProperties(
                new Point(3, 1), new Point(5, 5), Color.YELLOW, 5);
        customPropertiesList.add(weekdayLabelBorderProperties);
        // Make the top border extra thick.
        // Notice that all borders in the same row or column will be displayed with the same 
        // thickness as the thickest individual border that is in the same line. 
        CalendarBorderProperties topBorderProperties = new CalendarBorderProperties(
                new Point(4, 1), new Point(4, 1), Color.YELLOW, 15);
        customPropertiesList.add(topBorderProperties);
        // Set the borders properties for the date box.
        CalendarBorderProperties dateBoxBorderProperties = new CalendarBorderProperties(
                new Point(3, 3), new Point(5, 5), Color.GREEN, 10);
        customPropertiesList.add(dateBoxBorderProperties);
        // Set borders properties for the week number labels.
        // Note: Week number borders are always hidden unless the the week numbers are displayed.
        // ("Week number borders" are any borders located in columns 1 and 2.)
        CalendarBorderProperties weekNumberBorderProperties = new CalendarBorderProperties(
                new Point(1, 1), new Point(2, 5), Color.ORANGE, 10);
        customPropertiesList.add(weekNumberBorderProperties);
        // Set the corner borders of the date box individually.
        CalendarBorderProperties cornerProp;
        cornerProp = new CalendarBorderProperties(new Point(3, 3), new Point(3, 3), Color.BLUE, 1);
        customPropertiesList.add(cornerProp);
        cornerProp = new CalendarBorderProperties(new Point(3, 5), new Point(3, 5), Color.BLUE, 1);
        customPropertiesList.add(cornerProp);
        cornerProp = new CalendarBorderProperties(new Point(5, 3), new Point(5, 3), Color.BLUE, 1);
        customPropertiesList.add(cornerProp);
        cornerProp = new CalendarBorderProperties(new Point(5, 5), new Point(5, 5), Color.BLUE, 1);
        customPropertiesList.add(cornerProp);
        // Save the border properties to the settings.
        dateSettings.setBorderPropertiesList(customPropertiesList);
        dateSettings.setWeekNumbersDisplayed(true, false);
        calendarPanel = new CalendarPanel(dateSettings);
        panel.add(calendarPanel);

        // Create a CalendarPanel: With week numbers displayed, and custom week number rules.
        dateSettings = new DatePickerSettings();
        dateSettings.setWeekNumbersDisplayed(true, true);
        // dateSettings.setWeekNumbersForceFirstDayOfWeekToMatch(false);
        // dateSettings.setWeekNumberRules(WeekFields.ISO);
        calendarPanel = new CalendarPanel(dateSettings);
        panel.add(calendarPanel);

        ///////////////////////////////////////////////////////////////////////////////////////////
        // This section creates CalendarPanels, with various features. (postsetting preferred.)
        //
        // Create a CalendarPanel: With default settings
        dateSettings.setWeekNumbersDisplayed(true, true);
        calendarPanel = new CalendarPanel();
        panel.add(calendarPanel);

        // Create a CalendarPanel: With highlight policy.
        dateSettings = new DatePickerSettings();
        dateSettings.setWeekNumbersDisplayed(true, true);
        calendarPanel = new CalendarPanel(dateSettings);
        dateSettings.setHighlightPolicy(new SampleHighlightPolicy());
        panel.add(calendarPanel);

        // Create a CalendarPanel: With veto policy.
        // Note: Veto policies can only be set after constructing the CalendarPanel.
        dateSettings = new DatePickerSettings();
        dateSettings.setWeekNumbersDisplayed(true, true);
        calendarPanel = new CalendarPanel(dateSettings);
        dateSettings.setVetoPolicy(new SampleDateVetoPolicy());
        panel.add(calendarPanel);

        // Create a CalendarPanel: With both policies.
        // Note: Veto policies can only be set after constructing the CalendarPanel.
        dateSettings = new DatePickerSettings();
        dateSettings.setWeekNumbersDisplayed(true, true);
        calendarPanel = new CalendarPanel(dateSettings);
        dateSettings.setHighlightPolicy(new SampleHighlightPolicy());
        dateSettings.setVetoPolicy(new SampleDateVetoPolicy());
        panel.add(calendarPanel);

        // Create a CalendarPanel: Change calendar size.
        dateSettings = new DatePickerSettings();
        dateSettings.setWeekNumbersDisplayed(true, true);
        calendarPanel = new CalendarPanel(dateSettings);
        int newHeight2 = (int) (dateSettings.getSizeDatePanelMinimumHeight() * 1.7);
        int newWidth2 = (int) (dateSettings.getSizeDatePanelMinimumWidth() * 1.7);
        dateSettings.setSizeDatePanelMinimumHeight(newHeight2);
        dateSettings.setSizeDatePanelMinimumWidth(newWidth2);
        panel.add(calendarPanel);

        // Create a CalendarPanel: Custom color.
        dateSettings = new DatePickerSettings();
        dateSettings.setWeekNumbersDisplayed(true, true);
        calendarPanel = new CalendarPanel(dateSettings);
        dateSettings.setColor(DateArea.BackgroundOverallCalendarPanel, Color.BLUE);
        dateSettings.setColorBackgroundWeekdayLabels(Color.PINK, true);
        dateSettings.setColorBackgroundWeekNumberLabels(Color.PINK, true);
        dateSettings.setColor(DateArea.BackgroundTopLeftLabelAboveWeekNumbers, Color.PINK);
        dateSettings.setColor(DateArea.BackgroundMonthAndYearMenuLabels, Color.ORANGE);
        dateSettings.setColor(DateArea.BackgroundTodayLabel, Color.CYAN);
        dateSettings.setColor(DateArea.BackgroundClearLabel, Color.GREEN);
        dateSettings.setColor(DateArea.BackgroundMonthAndYearNavigationButtons, Color.MAGENTA);
        panel.add(calendarPanel);

        // Create a CalendarPanel: Change first weekday.
        dateSettings = new DatePickerSettings();
        calendarPanel = new CalendarPanel(dateSettings);
        dateSettings.setFirstDayOfWeek(DayOfWeek.THURSDAY);
        panel.add(calendarPanel);

        // Create a CalendarPanel: No empty dates. (aka null)
        dateSettings = new DatePickerSettings();
        dateSettings.setAllowEmptyDates(false);
        dateSettings.setWeekNumbersDisplayed(true, true);
        calendarPanel = new CalendarPanel(dateSettings);
        panel.add(calendarPanel);

        // Create a CalendarPanel: Localized (Greek)
        Locale datePickerLocale2 = new Locale("el");
        dateSettings = new DatePickerSettings(datePickerLocale2);
        dateSettings.setWeekNumbersDisplayed(true, true);
        calendarPanel = new CalendarPanel(dateSettings);
        calendarPanel.setSelectedDate(LocalDate.of(2016, Month.APRIL, 15));
        panel.add(calendarPanel);

        // Create a CalendarPanel: With crazy border properties.
        dateSettings = new DatePickerSettings();
        dateSettings.setWeekNumbersDisplayed(true, true);
        calendarPanel = new CalendarPanel(dateSettings);
        // Create a list to hold the border properties.
        ArrayList<CalendarBorderProperties> customPropertiesList2
                = new ArrayList<CalendarBorderProperties>();
        // Set the borders properties for everything to Blue.
        CalendarBorderProperties weekdayLabelBorderProperties2 = new CalendarBorderProperties(
                new Point(3, 1), new Point(5, 5), Color.YELLOW, 5);
        customPropertiesList2.add(weekdayLabelBorderProperties2);
        // Make the top border extra thick.
        // Notice that all borders in the same row or column will be displayed with the same 
        // thickness as the thickest individual border that is in the same line. 
        CalendarBorderProperties topBorderProperties2 = new CalendarBorderProperties(
                new Point(4, 1), new Point(4, 1), Color.YELLOW, 15);
        customPropertiesList2.add(topBorderProperties2);
        // Set the borders properties for the date box.
        CalendarBorderProperties dateBoxBorderProperties2 = new CalendarBorderProperties(
                new Point(3, 3), new Point(5, 5), Color.GREEN, 10);
        customPropertiesList2.add(dateBoxBorderProperties2);
        // Set borders properties for the week number labels.
        // Note: Week number borders are always hidden unless the the week numbers are displayed.
        // ("Week number borders" are any borders located in columns 1 and 2.)
        CalendarBorderProperties weekNumberBorderProperties2 = new CalendarBorderProperties(
                new Point(1, 1), new Point(2, 5), Color.ORANGE, 10);
        customPropertiesList2.add(weekNumberBorderProperties2);
        // Set the corner borders of the date box individually.
        CalendarBorderProperties cornerProp2;
        cornerProp2 = new CalendarBorderProperties(new Point(3, 3), new Point(3, 3), Color.BLUE, 1);
        customPropertiesList2.add(cornerProp2);
        cornerProp2 = new CalendarBorderProperties(new Point(3, 5), new Point(3, 5), Color.BLUE, 1);
        customPropertiesList2.add(cornerProp2);
        cornerProp2 = new CalendarBorderProperties(new Point(5, 3), new Point(5, 3), Color.BLUE, 1);
        customPropertiesList2.add(cornerProp2);
        cornerProp2 = new CalendarBorderProperties(new Point(5, 5), new Point(5, 5), Color.BLUE, 1);
        customPropertiesList2.add(cornerProp2);
        // Save the border properties to the settings.
        dateSettings.setBorderPropertiesList(customPropertiesList2);
        panel.add(calendarPanel);

        // Create a CalendarPanel: With week numbers displayed, and custom week number rules.
        dateSettings = new DatePickerSettings();
        calendarPanel = new CalendarPanel(dateSettings);
        dateSettings.setWeekNumbersDisplayed(true, true);
        // dateSettings.setWeekNumbersForceFirstDayOfWeekToMatch(false);
        // dateSettings.setWeekNumberRules(WeekFields.SUNDAY_START);
        panel.add(calendarPanel);

        // Display the frame.
        frame.pack();
        frame.validate();
        int maxWidth = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().width;
        int maxHeight = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().height;
        frame.setSize(640, 480);
        frame.setLocation(maxWidth / 2, maxHeight / 2);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);
    }

    /**
     * SampleDateVetoPolicy, A veto policy is a way to disallow certain dates from being selected in
     * calendar. A vetoed date cannot be selected by using the keyboard or the mouse.
     */
    private static class SampleDateVetoPolicy implements DateVetoPolicy {

        /**
         * isDateAllowed, Return true if a date should be allowed, or false if a date should be
         * vetoed.
         */
        @Override
        public boolean isDateAllowed(LocalDate date) {
            // Disallow days 7 to 11.
            if ((date.getDayOfMonth() >= 7) && (date.getDayOfMonth() <= 11)) {
                return false;
            }
            // Disallow odd numbered saturdays.
            if ((date.getDayOfWeek() == DayOfWeek.SATURDAY) && ((date.getDayOfMonth() % 2) == 1)) {
                return false;
            }
            // Allow all other days.
            return true;
        }
    }

    /**
     * SampleHighlightPolicy, A highlight policy is a way to visually highlight certain dates in the
     * calendar. These may be holidays, or weekends, or other significant dates.
     */
    private static class SampleHighlightPolicy implements DateHighlightPolicy {

        /**
         * getHighlightInformationOrNull, Implement this function to indicate if a date should be
         * highlighted, and what highlighting details should be used for the highlighted date.
         *
         * If a date should be highlighted, then return an instance of HighlightInformation. If the
         * date should not be highlighted, then return null.
         *
         * You may (optionally) fill out the fields in the HighlightInformation class to give any
         * particular highlighted day a unique foreground color, background color, or tooltip text.
         * If the color fields are null, then the default highlighting colors will be used. If the
         * tooltip field is null (or empty), then no tooltip will be displayed.
         *
         * Dates that are passed to this function will never be null.
         */
        @Override
        public HighlightInformation getHighlightInformationOrNull(LocalDate date) {
            // Highlight a chosen date, with a tooltip and a red background color.
            if (date.getDayOfMonth() == 25) {
                return new HighlightInformation(Color.red, null, "It's the 25th!");
            }
            // Highlight all Saturdays with a unique background and foreground color.
            if (date.getDayOfWeek() == DayOfWeek.SATURDAY) {
                return new HighlightInformation(Color.orange, Color.yellow, "It's Saturday!");
            }
            // Highlight all Sundays with default colors and a tooltip.
            if (date.getDayOfWeek() == DayOfWeek.SUNDAY) {
                return new HighlightInformation(null, null, "It's Sunday!");
            }
            // All other days should not be highlighted.
            return null;
        }
    }

}
