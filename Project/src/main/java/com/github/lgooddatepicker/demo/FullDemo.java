package com.github.lgooddatepicker.demo;

import com.github.lgooddatepicker.components.CalendarPanel;
import com.github.lgooddatepicker.zinternaltools.DemoPanel;
import com.github.lgooddatepicker.components.DatePicker;
import javax.swing.JButton;
import javax.swing.JFrame;
import java.awt.event.ActionEvent;
import java.util.Locale;
import java.awt.Color;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;
import com.github.lgooddatepicker.components.DatePickerSettings;
import com.github.lgooddatepicker.components.DatePickerSettings.DateArea;
import com.github.lgooddatepicker.optionalusertools.DateChangeListener;
import com.github.lgooddatepicker.optionalusertools.PickerUtilities;
import com.github.lgooddatepicker.zinternaltools.DateChangeEvent;
import com.github.lgooddatepicker.zinternaltools.InternalUtilities;
import com.github.lgooddatepicker.zinternaltools.WrapLayout;
import com.github.lgooddatepicker.zinternaltools.CalendarSelectionEvent;
import com.github.lgooddatepicker.zinternaltools.DateTimeChangeEvent;
import com.github.lgooddatepicker.zinternaltools.TimeChangeEvent;
import com.github.lgooddatepicker.optionalusertools.DateVetoPolicy;
import com.github.lgooddatepicker.optionalusertools.DateHighlightPolicy;
import com.github.lgooddatepicker.optionalusertools.TimeChangeListener;
import com.github.lgooddatepicker.optionalusertools.TimeVetoPolicy;
import com.github.lgooddatepicker.components.DateTimePicker;
import com.github.lgooddatepicker.optionalusertools.CalendarBorderProperties;
import com.github.lgooddatepicker.optionalusertools.DateTimeChangeListener;
import com.github.lgooddatepicker.components.TimePicker;
import com.github.lgooddatepicker.components.TimePickerSettings;
import com.github.lgooddatepicker.components.TimePickerSettings.TimeIncrement;
import com.privatejgoodies.forms.factories.CC;
import javax.swing.border.LineBorder;
import com.github.lgooddatepicker.zinternaltools.HighlightInformation;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.net.URL;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import com.github.lgooddatepicker.optionalusertools.CalendarListener;
import com.github.lgooddatepicker.zinternaltools.YearMonthChangeEvent;
import java.time.YearMonth;

/**
 * FullDemo, This class contains a demonstration of various features of the DatePicker library
 * components.
 *
 * Optional features: Most of the features shown in this demo are optional. The simplest usage only
 * requires creating a date picker instance and adding it to a panel or window. The selected date
 * can then be retrieved with the function datePicker.getDate(). For a simpler demo, see
 * "BasicDemo.java".
 *
 * Running the demo: This is an executable demonstration. To run the demo, click "run file" (or the
 * equivalent command) for the class in your IDE.
 */
public class FullDemo {

    // This holds our main frame.
    static JFrame frame;
    // This holds our display panel.
    static DemoPanel panel;
    // These hold date pickers.
    static DatePicker datePicker;
    static DatePicker datePicker1;
    static DatePicker datePicker2;
    // These hold time pickers.
    static TimePicker timePicker;
    static TimePicker timePicker1;
    static TimePicker timePicker2;
    // These hold DateTimePickers.
    static DateTimePicker dateTimePicker1;
    static DateTimePicker dateTimePicker2;
    static DateTimePicker dateTimePicker3;
    static DateTimePicker dateTimePicker4;
    static DateTimePicker dateTimePicker5;
    // Date pickers are placed on the rows at a set interval.
    static final int rowMultiplier = 4;

    /**
     * main, The application entry point.
     */
    public static void main(String[] args) {
        ///////////////////////////////////////////////////////////////////////////////////////////
        // If desired, set a swing look and feel here. 
        try {
            /*
            // Set a specific look and feel.
            for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
            
            // Set a random look and feel. 
            LookAndFeelInfo[] installedLooks = UIManager.getInstalledLookAndFeels();
            int lookIndex = (int) (Math.random() * installedLooks.length);
            UIManager.setLookAndFeel(installedLooks[lookIndex].getClassName());
            System.out.println(installedLooks[lookIndex].getClassName().toString());
             */
        } catch (Exception e) {
        }

        ///////////////////////////////////////////////////////////////////////////////////////////
        // Create a frame, a panel, and our demo buttons.
        frame = new JFrame();
        frame.setTitle("LGoodDatePicker Demo " + InternalUtilities.getProjectVersionString());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panel = new DemoPanel();
        frame.getContentPane().add(panel);
        createDemoButtons();

        ///////////////////////////////////////////////////////////////////////////////////////////
        // This section creates DatePickers, with various features.
        //
        // Create variables for repeated use.
        DatePickerSettings dateSettings;
        int row = rowMultiplier;
        final LocalDate today = LocalDate.now();
        int pickerNumber = 0;

        // Create a date picker: With default settings
        datePicker1 = new DatePicker();
        panel.panel1.add(datePicker1, getConstraints(1, (row * rowMultiplier), 1));
        panel.addLabel(panel.panel1, 1, (row++ * rowMultiplier), "Date " + (++pickerNumber)
            + ", Default Settings:");

        // Create a date picker: With highlight policy.
        dateSettings = new DatePickerSettings();
        datePicker2 = new DatePicker(dateSettings);
        dateSettings.setHighlightPolicy(new SampleHighlightPolicy());
        panel.panel1.add(datePicker2, getConstraints(1, (row * rowMultiplier), 1));
        panel.addLabel(panel.panel1, 1, (row++ * rowMultiplier), "Date " + (++pickerNumber)
            + ", Highlight Policy:");

        // Create a date picker: With veto policy.
        // Note: Veto policies can only be set after constructing the date picker.
        dateSettings = new DatePickerSettings();
        datePicker = new DatePicker(dateSettings);
        dateSettings.setVetoPolicy(new SampleDateVetoPolicy());
        panel.panel1.add(datePicker, getConstraints(1, (row * rowMultiplier), 1));
        panel.addLabel(panel.panel1, 1, (row++ * rowMultiplier), "Date " + (++pickerNumber)
            + ", Veto Policy:");

        // Create a date picker: With both policies.
        // Note: Veto policies can only be set after constructing the date picker.
        dateSettings = new DatePickerSettings();
        datePicker = new DatePicker(dateSettings);
        dateSettings.setHighlightPolicy(new SampleHighlightPolicy());
        dateSettings.setVetoPolicy(new SampleDateVetoPolicy());
        panel.panel1.add(datePicker, getConstraints(1, (row * rowMultiplier), 1));
        panel.addLabel(panel.panel1, 1, (row++ * rowMultiplier), "Date " + (++pickerNumber)
            + ", Both Policies:");

        // Create a date picker: With Hidden text field (Showing button only).
        // Note: A developer might want to do this if they were providing their own component 
        // for displaying the date.
        dateSettings = new DatePickerSettings();
        dateSettings.setVisibleDateTextField(false);
        dateSettings.setGapBeforeButtonPixels(0);
        datePicker = new DatePicker(dateSettings);
        panel.panel1.add(datePicker, getConstraints(1, (row * rowMultiplier), 1));
        panel.addLabel(panel.panel1, 1, (row++ * rowMultiplier),
            "Date " + (++pickerNumber) + ", Hidden text field (Showing button only):");

        // Create a date picker: With date range limits.
        // Notes: 
        // * If you only want to limit one side of the date range, you can optionally pass in null 
        // for one of the date limits.
        // * Date range limits (and other types of veto policies) can only be set after constructing 
        // the date picker.
        dateSettings = new DatePickerSettings();
        datePicker = new DatePicker(dateSettings);
        dateSettings.setDateRangeLimits(today.minusDays(20), today.plusDays(20));
        panel.panel1.add(datePicker, getConstraints(1, (row * rowMultiplier), 1));
        panel.addLabel(panel.panel1, 1, (row++ * rowMultiplier),
            "Date " + (++pickerNumber) + ", Limit date range (+/- 20 days):");

        // Create a date picker: Custom button icon.
        // You can replace the example image with any image file that is desired.
        // See also: JButton.setPressedIcon() and JButton.setDisabledIcon().
        // Get the example image icon.
        URL dateImageURL = FullDemo.class.getResource("/images/datepickerbutton1.png");
        Image dateExampleImage = Toolkit.getDefaultToolkit().getImage(dateImageURL);
        ImageIcon dateExampleIcon = new ImageIcon(dateExampleImage);
        // Create the date picker, and apply the image icon.
        dateSettings = new DatePickerSettings();
        datePicker = new DatePicker(dateSettings);
        datePicker.setDateToToday();
        JButton datePickerButton = datePicker.getComponentToggleCalendarButton();
        datePickerButton.setText("");
        datePickerButton.setIcon(dateExampleIcon);
        panel.panel1.add(datePicker, getConstraints(1, (row * rowMultiplier), 1));
        panel.addLabel(panel.panel1, 1, (row++ * rowMultiplier), "Date " + (++pickerNumber)
            + ", Custom Icon:");

        // Create a date picker: Change calendar size.
        dateSettings = new DatePickerSettings();
        int newHeight = (int) (dateSettings.getSizeDatePanelMinimumHeight() * 1.6);
        int newWidth = (int) (dateSettings.getSizeDatePanelMinimumWidth() * 1.6);
        dateSettings.setSizeDatePanelMinimumHeight(newHeight);
        dateSettings.setSizeDatePanelMinimumWidth(newWidth);
        datePicker = new DatePicker(dateSettings);
        panel.panel1.add(datePicker, getConstraints(1, (row * rowMultiplier), 1));
        panel.addLabel(panel.panel1, 1, (row++ * rowMultiplier), "Date " + (++pickerNumber)
            + ", Change Calendar Size:");

        // Create a date picker: Show Week Numbers.
        // This will display week numbers on the left side of the calendar.
        // By default, the week number rules are specific to the locale of the settings instance.
        // For custom configuration of week numbers, see also these DatePickerSettings: 
        // setWeekNumberRules(), and setWeekNumbersForceFirstDayOfWeekToMatch();
        dateSettings = new DatePickerSettings();
        dateSettings.setWeekNumbersDisplayed(true, true);
        // If you would like the week numbers to be the same for all calendars, (instead of using
        // the default rules for the settings locale), then you could set the weekNumberRules to
        // one of these two widely used rule sets: WeekFields.ISO or WeekFields.SUNDAY_START.
        // dateSettings.setWeekNumberRules(WeekFields.SUNDAY_START);
        // dateSettings.setWeekNumberRules(WeekFields.ISO);
        datePicker = new DatePicker(dateSettings);
        panel.panel1.add(datePicker, getConstraints(1, (row * rowMultiplier), 1));
        panel.addLabel(panel.panel1, 1, (row++ * rowMultiplier), "Date " + (++pickerNumber)
            + ", Show Week Numbers:");

        // Create a date picker: Change Colors and Fonts.
        dateSettings = new DatePickerSettings();
        dateSettings.setWeekNumbersDisplayed(true, true);
        // Set backgrounds:
        dateSettings.setColor(DateArea.CalendarBackgroundNormalDates, Color.CYAN);
        dateSettings.setColor(DateArea.BackgroundOverallCalendarPanel, Color.GREEN);
        dateSettings.setColor(DateArea.BackgroundMonthAndYearMenuLabels, Color.YELLOW);
        dateSettings.setColor(DateArea.BackgroundTodayLabel, Color.YELLOW);
        dateSettings.setColor(DateArea.BackgroundClearLabel, Color.YELLOW);
        dateSettings.setColor(DateArea.BackgroundMonthAndYearNavigationButtons, Color.CYAN);
        dateSettings.setColor(DateArea.BackgroundTopLeftLabelAboveWeekNumbers, Color.ORANGE);
        dateSettings.setColor(DateArea.CalendarBackgroundSelectedDate, Color.PINK);
        dateSettings.setColor(DateArea.CalendarBorderSelectedDate, Color.WHITE);
        dateSettings.setColorBackgroundWeekdayLabels(Color.ORANGE, true);
        dateSettings.setColorBackgroundWeekNumberLabels(Color.ORANGE, true);
        // Set fonts:
        Font randomFont = new Font("Monospaced", Font.ITALIC | Font.BOLD, 20);
        Font smallerFont = new Font("Serif", Font.BOLD, 18);
        dateSettings.setFontMonthAndYearMenuLabels(randomFont);
        dateSettings.setFontMonthAndYearNavigationButtons(randomFont);
        dateSettings.setFontTodayLabel(randomFont);
        dateSettings.setFontClearLabel(randomFont);
        dateSettings.setFontCalendarDateLabels(randomFont);
        dateSettings.setFontCalendarWeekdayLabels(smallerFont);
        dateSettings.setFontCalendarWeekNumberLabels(smallerFont);
        // Set text colors:
        dateSettings.setColor(DateArea.TextMonthAndYearMenuLabels, Color.BLUE);
        dateSettings.setColor(DateArea.TextMonthAndYearNavigationButtons, Color.BLUE);
        dateSettings.setColor(DateArea.TextTodayLabel, Color.BLUE);
        dateSettings.setColor(DateArea.TextClearLabel, Color.BLUE);
        dateSettings.setColor(DateArea.CalendarTextNormalDates, Color.MAGENTA);
        dateSettings.setColor(DateArea.CalendarTextWeekdays, Color.RED);
        dateSettings.setColor(DateArea.CalendarTextWeekNumbers, Color.RED);
        // Create the date picker. 
        datePicker = new DatePicker(dateSettings);
        panel.panel1.add(datePicker, getConstraints(1, (row * rowMultiplier), 1));
        panel.addLabel(panel.panel1, 1, (row++ * rowMultiplier), "Date " + (++pickerNumber)
            + ", Change Colors and Fonts:");

        // Create a date picker: Custom font.
        dateSettings = new DatePickerSettings();
        dateSettings.setFontValidDate(new Font("Monospaced", Font.ITALIC | Font.BOLD, 17));
        dateSettings.setColor(DateArea.DatePickerTextValidDate, new Color(0, 100, 0));
        datePicker = new DatePicker(dateSettings);
        datePicker.setDateToToday();
        panel.panel1.add(datePicker, getConstraints(1, (row * rowMultiplier), 1));
        panel.addLabel(panel.panel1, 1, (row++ * rowMultiplier), "Date " + (++pickerNumber)
            + ", Custom Font:");

        // Create a date picker: Custom Date Format.
        // When creating a date pattern string for BCE dates, use "u" instead of "y" for the year.
        // For more details about that, see: DatePickerSettings.setFormatForDatesBeforeCommonEra().
        // The various codes for the date pattern string are described at this link:
        // https://docs.oracle.com/javase/8/docs/api/java/time/format/DateTimeFormatter.html
        dateSettings = new DatePickerSettings();
        dateSettings.setFormatForDatesCommonEra("yyyy/MM/dd");
        dateSettings.setFormatForDatesBeforeCommonEra("uuuu/MM/dd");
        datePicker = new DatePicker(dateSettings);
        datePicker.setDateToToday();
        panel.panel1.add(datePicker, getConstraints(1, (row * rowMultiplier), 1));
        panel.addLabel(panel.panel1, 1, (row++ * rowMultiplier), "Date " + (++pickerNumber)
            + ", Custom Date Format:");

        // Create a date picker: Another Custom Date Format.
        // When creating a date pattern string for BCE dates, use "u" instead of "y" for the year.
        // For more details about that, see: DatePickerSettings.setFormatForDatesBeforeCommonEra().
        // The various codes for the date pattern string are described at this link:
        // https://docs.oracle.com/javase/8/docs/api/java/time/format/DateTimeFormatter.html
        dateSettings = new DatePickerSettings();
        dateSettings.setFormatForDatesCommonEra("d MMM yyyy");
        dateSettings.setFormatForDatesBeforeCommonEra("d MMM uuuu");
        datePicker = new DatePicker(dateSettings);
        datePicker.setDateToToday();
        panel.panel1.add(datePicker, getConstraints(1, (row * rowMultiplier), 1));
        panel.addLabel(panel.panel1, 1, (row++ * rowMultiplier), "Date " + (++pickerNumber)
            + ", Another Custom Date Format:");

        // Create a date picker: With a fixed length date format.
        // When creating a date pattern string for BCE dates, use "u" instead of "y" for the year.
        // For more details about that, see: DatePickerSettings.setFormatForDatesBeforeCommonEra().
        // The various codes for the date pattern string are described at this link:
        // https://docs.oracle.com/javase/8/docs/api/java/time/format/DateTimeFormatter.html
        dateSettings = new DatePickerSettings();
        dateSettings.setFormatForDatesCommonEra("yyyyMMdd");
        dateSettings.setFormatForDatesBeforeCommonEra("uuuuMMdd");
        datePicker = new DatePicker(dateSettings);
        datePicker.setDateToToday();
        panel.panel1.add(datePicker, getConstraints(1, (row * rowMultiplier), 1));
        panel.addLabel(panel.panel1, 1, (row++ * rowMultiplier), "Date " + (++pickerNumber)
            + ", With a fixed length date format:");

        // Create a date picker: Change first weekday.
        dateSettings = new DatePickerSettings();
        dateSettings.setFirstDayOfWeek(DayOfWeek.MONDAY);
        datePicker = new DatePicker(dateSettings);
        panel.panel1.add(datePicker, getConstraints(1, (row * rowMultiplier), 1));
        panel.addLabel(panel.panel1, 1, (row++ * rowMultiplier), "Date " + (++pickerNumber)
            + ", Set First Day Of Week (Mon):");

        // Create a date picker: No empty dates. (aka null)
        dateSettings = new DatePickerSettings();
        dateSettings.setAllowEmptyDates(false);
        datePicker = new DatePicker(dateSettings);
        datePicker.addDateChangeListener(
            new SampleDateChangeListener("datePicker16 (Disallow Empty Dates or Null), "));
        panel.panel1.add(datePicker, getConstraints(1, (row * rowMultiplier), 1));
        panel.addLabel(panel.panel1, 1, (row++ * rowMultiplier), "Date " + (++pickerNumber)
            + ", Disallow Empty Dates:");

        // Create a date picker: Disallow keyboard editing.
        dateSettings = new DatePickerSettings();
        dateSettings.setAllowKeyboardEditing(false);
        datePicker = new DatePicker(dateSettings);
        datePicker.setDateToToday();
        panel.panel1.add(datePicker, getConstraints(1, (row * rowMultiplier), 1));
        panel.addLabel(panel.panel1, 1, (row++ * rowMultiplier), "Date " + (++pickerNumber)
            + ", Disallow Keyboard Editing:");

        // Create a Custom Border Properties List.
        // These border properties will be used for the next two date picker examples.
        // Create a list to hold our border properties. Borders properties will be applied to the
        // calendar in the order that they appear this list.
        ArrayList<CalendarBorderProperties> borderProperties
            = new ArrayList<CalendarBorderProperties>();
        // Set all borders to be yellow, and 10 pixels thick. 
        // (Parts of the yellow border will be overwritten by other border settings.)
        borderProperties.add(new CalendarBorderProperties(
            new Point(1, 1), new Point(5, 5), Color.YELLOW, 10));
        // Make the top center border extra thick. Note: All borders in the same row or column will 
        // be displayed with the same thickness as the thickest border in the same line. 
        borderProperties.add(new CalendarBorderProperties(
            new Point(4, 1), new Point(4, 1), Color.YELLOW, 15));
        // Set the borders surrounding the date box to be green.
        borderProperties.add(new CalendarBorderProperties(
            new Point(3, 3), new Point(5, 5), Color.GREEN, 10));
        // Individually set the borders in the top corners of the date box to be blue.
        borderProperties.add(new CalendarBorderProperties(
            new Point(3, 3), new Point(3, 3), Color.BLUE, 1));
        borderProperties.add(new CalendarBorderProperties(
            new Point(5, 3), new Point(5, 3), Color.BLUE, 1));

        // Create a date picker: Custom Borders.
        // Note: Week number borders are always hidden (invisible) unless the the week numbers are 
        // displayed. ("Week number borders" are all borders located in columns 1 and 2.)
        dateSettings = new DatePickerSettings();
        dateSettings.setBorderPropertiesList(borderProperties);
        datePicker = new DatePicker(dateSettings);
        panel.panel1.add(datePicker, getConstraints(1, (row * rowMultiplier), 1));
        panel.addLabel(panel.panel1, 1, (row++ * rowMultiplier), "Date " + (++pickerNumber)
            + ", Custom Borders:");

        // Create a date picker: Custom Borders with Week Numbers.
        // Note: Week number borders are always hidden (invisible) unless the the week numbers are 
        // displayed. ("Week number borders" are all borders located in columns 1 and 2.)
        dateSettings = new DatePickerSettings();
        dateSettings.setWeekNumbersDisplayed(true, true);
        dateSettings.setBorderPropertiesList(borderProperties);
        datePicker = new DatePicker(dateSettings);
        panel.panel1.add(datePicker, getConstraints(1, (row * rowMultiplier), 1));
        panel.addLabel(panel.panel1, 1, (row++ * rowMultiplier),
            "Date " + (++pickerNumber) + ", Custom Borders with Week Numbers:");

        // Create a date picker: With a default year month, and limited date range.
        // Notes: 
        // * When a default YearMonth has not been set, (or is set to null), the default YearMonth
        // will be YearMonth.now().
        // * If you only want to limit one side of the date range, you can optionally pass in null 
        // for one of the date limits.
        // * Date range limits (and other types of veto policies) can only be set after constructing 
        // the date picker.
        dateSettings = new DatePickerSettings();
        datePicker = new DatePicker(dateSettings);
        LocalDate startJuly = today.plusYears(1);
        startJuly = startJuly.withMonth(7);
        startJuly = startJuly.withDayOfMonth(1);
        LocalDate endJuly = startJuly.withDayOfMonth(startJuly.lengthOfMonth());
        YearMonth yearMonthJuly = YearMonth.from(startJuly);
        dateSettings.setDefaultYearMonth(yearMonthJuly);
        dateSettings.setDateRangeLimits(startJuly.minusDays(10), endJuly.plusDays(10));
        panel.panel1.add(datePicker, getConstraints(1, (row * rowMultiplier), 1));
        panel.addLabel(panel.panel1, 1, (row++ * rowMultiplier),
            "<html>Date " + (++pickerNumber) + ", Set Default YearMonth with Range<br>"
                + "(July Next Year + 10 days.):</html>");

        // Create a date picker: Change the text field background color.
        dateSettings = new DatePickerSettings();
        datePicker = new DatePicker(dateSettings);
        dateSettings.setColor(DateArea.TextFieldBackgroundValidDate, Color.cyan);
        panel.panel1.add(datePicker, getConstraints(1, (row * rowMultiplier), 1));
        panel.addLabel(panel.panel1, 1, (row++ * rowMultiplier),
            "Date " + (++pickerNumber) + ", Change the text field background color:");

        //
        ///////////////////////////////////////////////////////////////////////////////////////////
        // This section creates TimePickers. (1 to 5)
        //
        // Create some variables for repeated use.
        TimePickerSettings timeSettings;
        row = rowMultiplier;

        // Create a time picker: With default settings
        timePicker1 = new TimePicker();
        panel.panel2.add(timePicker1, getConstraints(1, (row * rowMultiplier), 1));
        panel.addLabel(panel.panel2, 1, (row++ * rowMultiplier), "Time 1, Default Settings:");

        // Create a time picker: With No Buttons.
        timeSettings = new TimePickerSettings();
        timeSettings.setDisplayToggleTimeMenuButton(false);
        timeSettings.setInitialTimeToNow();
        timePicker2 = new TimePicker(timeSettings);
        panel.panel2.add(timePicker2, getConstraints(1, (row * rowMultiplier), 1));
        panel.addLabel(panel.panel2, 1, (row++ * rowMultiplier), "Time 2, No Buttons:");

        // Create a time picker: With Spinner Buttons.
        timeSettings = new TimePickerSettings();
        timeSettings.setDisplayToggleTimeMenuButton(false);
        timeSettings.setDisplaySpinnerButtons(true);
        timeSettings.setInitialTimeToNow();
        timePicker = new TimePicker(timeSettings);
        panel.panel2.add(timePicker, getConstraints(1, (row * rowMultiplier), 1));
        panel.addLabel(panel.panel2, 1, (row++ * rowMultiplier), "Time 3, With Spinner Buttons:");

        // Create a time picker: With All Buttons.
        timeSettings = new TimePickerSettings();
        timeSettings.setDisplaySpinnerButtons(true);
        timeSettings.setInitialTimeToNow();
        timePicker = new TimePicker(timeSettings);
        panel.panel2.add(timePicker, getConstraints(1, (row * rowMultiplier), 1));
        panel.addLabel(panel.panel2, 1, (row++ * rowMultiplier), "Time 4, With All Buttons:");

        // Create a time picker: 15 minute interval, and 24 hour clock.
        timeSettings = new TimePickerSettings();
        timeSettings.use24HourClockFormat();
        timeSettings.initialTime = LocalTime.of(15, 30);
        timeSettings.generatePotentialMenuTimes(TimeIncrement.FifteenMinutes, null, null);
        timePicker = new TimePicker(timeSettings);
        panel.panel2.add(timePicker, getConstraints(1, (row * rowMultiplier), 1));
        panel.addLabel(panel.panel2, 1, (row++ * rowMultiplier), "Time 5, Interval 15 minutes, and 24 hour clock:");

        // Create a time picker: Localized (Chinese).
        Locale chineseLocale = new Locale("zh");
        timeSettings = new TimePickerSettings(chineseLocale);
        timeSettings.initialTime = LocalTime.now();
        timePicker = new TimePicker(timeSettings);
        panel.panel2.add(timePicker, getConstraints(1, (row * rowMultiplier), 1));
        panel.addLabel(panel.panel2, 1, (row++ * rowMultiplier), "Time 6, Localized (to Chinese):");

        // Create a time picker: Custom button icon.
        // You can replace the example image with any desired image file.
        // See also: JButton.setPressedIcon() and JButton.setDisabledIcon().
        // Get the example image icon.
        URL timeIconURL = FullDemo.class.getResource("/images/timepickerbutton1.png");
        Image timeExampleImage = Toolkit.getDefaultToolkit().getImage(timeIconURL);
        ImageIcon timeExampleIcon = new ImageIcon(timeExampleImage);
        // Create the time picker, and apply the image icon.
        timeSettings = new TimePickerSettings();
        timeSettings.initialTime = LocalTime.of(15, 00);
        timePicker = new TimePicker(timeSettings);
        JButton timePickerButton = timePicker.getComponentToggleTimeMenuButton();
        timePickerButton.setText("");
        timePickerButton.setIcon(timeExampleIcon);
        // Adjust the button size to fit the new icon.
        Dimension newTimeButtonSize = new Dimension(
            timeExampleIcon.getIconWidth() + 4, timeExampleIcon.getIconHeight() + 4);
        timePickerButton.setPreferredSize(newTimeButtonSize);
        panel.panel2.add(timePicker, getConstraints(1, (row * rowMultiplier), 1));
        panel.addLabel(panel.panel2, 1, (row++ * rowMultiplier), "Time 7, Custom Icon:");

        ///////////////////////////////////////////////////////////////////////////////////////////
        // This section creates DateTimePickers. (1 to 5)
        //
        // Create a DateTimePicker: Default settings
        dateTimePicker1 = new DateTimePicker();
        panel.panel2.add(dateTimePicker1, getConstraints(1, (row * rowMultiplier), 1));
        panel.addLabel(panel.panel2, 1, (row++ * rowMultiplier),
            "DateTimePicker 1, Default settings:");

        // Create a DateTimePicker: Disallow empty dates and times.
        dateSettings = new DatePickerSettings();
        timeSettings = new TimePickerSettings();
        dateSettings.setAllowEmptyDates(false);
        timeSettings.setAllowEmptyTimes(false);
        dateTimePicker2 = new DateTimePicker(dateSettings, timeSettings);
        panel.panel2.add(dateTimePicker2, getConstraints(1, (row * rowMultiplier), 1));
        panel.addLabel(panel.panel2, 1, (row++ * rowMultiplier),
            "DateTimePicker 2, Disallow empty dates and times:");

        // Create a DateTimePicker: With change listener.
        dateTimePicker3 = new DateTimePicker();
        dateTimePicker3.addDateTimeChangeListener(new SampleDateTimeChangeListener(
            "dateTimePicker3"));
        panel.panel2.add(dateTimePicker3, getConstraints(1, (row * rowMultiplier), 1));
        panel.addLabel(panel.panel2, 1, (row++ * rowMultiplier),
            "DateTimePicker 3, With Change Listener:");

        ///////////////////////////////////////////////////////////////////////////////////////////
        // This section creates any remaining TimePickers.
        // Create a time picker: Disallow Empty Times.
        timeSettings = new TimePickerSettings();
        timeSettings.setAllowEmptyTimes(false);
        timePicker = new TimePicker(timeSettings);
        panel.panel2.add(timePicker, getConstraints(1, (row * rowMultiplier), 1));
        panel.addLabel(panel.panel2, 1, (row++ * rowMultiplier), "Time 8, Disallow Empty Times:");

        // Create a time picker: With TimeChangeListener.
        timeSettings = new TimePickerSettings();
        timePicker = new TimePicker(timeSettings);
        timePicker.addTimeChangeListener(new SampleTimeChangeListener("timePicker7"));
        panel.panel2.add(timePicker, getConstraints(1, (row * rowMultiplier), 1));
        panel.addLabel(panel.panel2, 1, (row++ * rowMultiplier), "Time 9, With a TimeChangeListener:");

        // Create a time picker: With more visible rows.
        timeSettings = new TimePickerSettings();
        timeSettings.maximumVisibleMenuRows = 20;
        timePicker = new TimePicker(timeSettings);
        panel.panel2.add(timePicker, getConstraints(1, (row * rowMultiplier), 1));
        panel.addLabel(panel.panel2, 1, (row++ * rowMultiplier), "Time 10, With 20 visible menu rows:");

        // Create a time picker: Custom Format.
        timeSettings = new TimePickerSettings();
        timeSettings.setFormatForDisplayTime("ha");
        timeSettings.setFormatForMenuTimes("ha");
        timeSettings.initialTime = LocalTime.of(15, 00);
        timeSettings.generatePotentialMenuTimes(TimeIncrement.OneHour, null, null);
        timePicker = new TimePicker(timeSettings);
        panel.panel2.add(timePicker, getConstraints(1, (row * rowMultiplier), 1));
        panel.addLabel(panel.panel2, 1, (row++ * rowMultiplier), "Time 11, Custom Format:");

        // Create a time picker: With Veto Policy.
        timeSettings = new TimePickerSettings();
        timePicker = new TimePicker(timeSettings);
        timeSettings.setVetoPolicy(new SampleTimeVetoPolicy());
        panel.panel2.add(timePicker, getConstraints(1, (row * rowMultiplier), 1));
        panel.addLabel(panel.panel2, 1, (row++ * rowMultiplier),
            "Time 12, With Veto Policy (Only 9a-5p allowed):");

        // Create a time picker: Seconds precision.
        timeSettings = new TimePickerSettings();
        timeSettings.setFormatForDisplayTime(PickerUtilities.createFormatterFromPatternString(
            "HH:mm:ss", timeSettings.getLocale()));
        timeSettings.setFormatForMenuTimes(PickerUtilities.createFormatterFromPatternString(
            "HH:mm", timeSettings.getLocale()));
        timeSettings.initialTime = LocalTime.of(15, 00, 00);
        timePicker = new TimePicker(timeSettings);
        panel.panel2.add(timePicker, getConstraints(1, (row * rowMultiplier), 1));
        panel.addLabel(panel.panel2, 1, (row++ * rowMultiplier), "Time 13, Seconds precision (ISO format):");

        // Create a time picker: Milliseconds precision.
        timeSettings = new TimePickerSettings();
        timeSettings.setFormatForDisplayTime(PickerUtilities.createFormatterFromPatternString(
            "HH:mm:ss.SSS", timeSettings.getLocale()));
        timeSettings.setFormatForMenuTimes(PickerUtilities.createFormatterFromPatternString(
            "HH:mm", timeSettings.getLocale()));
        timeSettings.initialTime = LocalTime.of(15, 00, 00, 999000000);
        timePicker = new TimePicker(timeSettings);
        panel.panel2.add(timePicker, getConstraints(1, (row * rowMultiplier), 1));
        panel.addLabel(panel.panel2, 1, (row++ * rowMultiplier), "Time 14, Millisecond precision (ISO format):");

        // Create a time picker: Nanoseconds precision.
        timeSettings = new TimePickerSettings();
        DateTimeFormatter displayTimeFormatter = DateTimeFormatter.ISO_LOCAL_TIME;
        timeSettings.setFormatForDisplayTime(displayTimeFormatter);
        timeSettings.setFormatForMenuTimes(PickerUtilities.createFormatterFromPatternString(
            "HH:mm", timeSettings.getLocale()));
        timeSettings.initialTime = LocalTime.of(15, 00, 00, 999999999);
        timePicker = new TimePicker(timeSettings);
        panel.panel2.add(timePicker, getConstraints(1, (row * rowMultiplier), 1));
        panel.addLabel(panel.panel2, 1, (row++ * rowMultiplier),
            "<html>Time 15, Nanosecond precision:<br/>(ISO format. Use \".\" to type nanoseconds.)</html>");

        // Create a time picker: Disallow Keyboard Editing.
        timeSettings = new TimePickerSettings();
        timeSettings.setAllowKeyboardEditing(false);
        timePicker = new TimePicker(timeSettings);
        panel.panel2.add(timePicker, getConstraints(1, (row * rowMultiplier), 1));
        panel.addLabel(panel.panel2, 1, (row++ * rowMultiplier), "Time 16, Disallow Keyboard Editing:");

        ///////////////////////////////////////////////////////////////////////////////////////////
        // This section creates any remaining DateTimePickers.
        // (None here at the moment.)
        //
        ///////////////////////////////////////////////////////////////////////////////////////////
        // This section creates date pickers and labels for demonstrating the language translations.
        int rowMarker = 0;
        addLocalizedPickerAndLabel(++rowMarker, "Arabic:", "ar");
        addLocalizedPickerAndLabel(++rowMarker, "Bulgarian:", "bg");
        addLocalizedPickerAndLabel(++rowMarker, "Chinese:", "zh");
        addLocalizedPickerAndLabel(++rowMarker, "Czech:", "cs");
        addLocalizedPickerAndLabel(++rowMarker, "Danish:", "da");
        addLocalizedPickerAndLabel(++rowMarker, "Dutch:", "nl");
        addLocalizedPickerAndLabel(++rowMarker, "English:", "en");
        addLocalizedPickerAndLabel(++rowMarker, "Finnish:", "fi");
        addLocalizedPickerAndLabel(++rowMarker, "French:", "fr");
        addLocalizedPickerAndLabel(++rowMarker, "German:", "de");
        addLocalizedPickerAndLabel(++rowMarker, "Greek:", "el");
        addLocalizedPickerAndLabel(++rowMarker, "Hindi:", "hi");
        addLocalizedPickerAndLabel(++rowMarker, "Italian:", "it");
        addLocalizedPickerAndLabel(++rowMarker, "Indonesian:", "in");
        addLocalizedPickerAndLabel(++rowMarker, "Japanese:", "ja");
        addLocalizedPickerAndLabel(++rowMarker, "Korean:", "ko");
        addLocalizedPickerAndLabel(++rowMarker, "Norwegian:", "no");
        addLocalizedPickerAndLabel(++rowMarker, "Polish:", "pl");
        addLocalizedPickerAndLabel(++rowMarker, "Portuguese:", "pt");
        addLocalizedPickerAndLabel(++rowMarker, "Romanian:", "ro");
        addLocalizedPickerAndLabel(++rowMarker, "Russian:", "ru");
        addLocalizedPickerAndLabel(++rowMarker, "Spanish:", "es");
        addLocalizedPickerAndLabel(++rowMarker, "Swedish:", "sv");
        addLocalizedPickerAndLabel(++rowMarker, "Turkish:", "tr");
        addLocalizedPickerAndLabel(++rowMarker, "Vietnamese:", "vi");

        // This section creates an independent CalendarPanel.
        // This CalendarPanel includes a calendar selection listener and a border.
        DatePickerSettings settings = new DatePickerSettings();
        CalendarPanel calendarPanel = new CalendarPanel(settings);
        calendarPanel.setSelectedDate(LocalDate.now());
        calendarPanel.addCalendarListener(new SampleCalendarListener());
        calendarPanel.setBorder(new LineBorder(Color.lightGray));
        panel.independentCalendarPanel.add(calendarPanel, CC.xy(2, 2));

        // Display the frame.
        frame.pack();
        frame.validate();
        int maxWidth = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().width;
        int maxHeight = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().height;
        frame.setSize(maxWidth / 4 * 3, maxHeight / 8 * 7);
        frame.setLocation(maxWidth / 8, maxHeight / 16);
        frame.setVisible(true);
    }

    /**
     * getConstraints, This returns a grid bag constraints object that can be used for placing a
     * component appropriately into a grid bag layout.
     */
    private static GridBagConstraints getConstraints(int gridx, int gridy, int gridwidth) {
        GridBagConstraints gc = new GridBagConstraints();
        gc.fill = GridBagConstraints.NONE;
        gc.anchor = GridBagConstraints.WEST;
        gc.gridx = gridx;
        gc.gridy = gridy;
        gc.gridwidth = gridwidth;
        return gc;
    }

    /**
     * addLocalizedPickerAndLabel, This creates a date picker whose locale is set to the specified
     * language. This also sets the picker to today's date, creates a label for the date picker, and
     * adds the components to the language panel.
     */
    private static void addLocalizedPickerAndLabel(int rowMarker, String labelText,
        String languageCode) {
        // Create the localized date picker and label.
        Locale locale = new Locale(languageCode);
        DatePickerSettings settings = new DatePickerSettings(locale);
        // Set a minimum size for the localized date pickers, to improve the look of the demo.
        settings.setSizeTextFieldMinimumWidth(125);
        settings.setSizeTextFieldMinimumWidthDefaultOverride(true);
        DatePicker localizedDatePicker = new DatePicker(settings);
        localizedDatePicker.setDateToToday();
        panel.panel4.add(localizedDatePicker, getConstraints(1, (rowMarker * rowMultiplier), 1));
        panel.addLabel(panel.panel4, 1, (rowMarker * rowMultiplier), labelText);
    }

    /**
     * setTimeOneWithTimeTwoButtonClicked, This sets the time in time picker one, to whatever time
     * is currently set in time picker two.
     */
    private static void setTimeOneWithTimeTwoButtonClicked(ActionEvent e) {
        LocalTime timePicker2Time = timePicker2.getTime();
        timePicker1.setTime(timePicker2Time);
        // Display message.
        String message = "The timePicker1 value was set using the timePicker2 value!\n\n";
        String timeString = timePicker1.getTimeStringOrSuppliedString("(null)");
        String messageAddition = ("The timePicker1 value is currently set to: " + timeString + ".");
        panel.messageTextArea.setText(message + messageAddition);
    }

    /**
     * setTwoWithY2KButtonClicked, This sets the date in date picker two, to New Years Day 2000.
     */
    private static void setTwoWithY2KButtonClicked(ActionEvent e) {
        // Set date picker date.
        LocalDate dateY2K = LocalDate.of(2000, Month.JANUARY, 1);
        datePicker2.setDate(dateY2K);
        // Display message.
        String dateString = datePicker2.getDateStringOrSuppliedString("(null)");
        String message = "The datePicker2 date was set to New Years 2000!\n\n";
        message += ("The datePicker2 date is currently set to: " + dateString + ".");
        panel.messageTextArea.setText(message);
    }

    /**
     * setOneWithTwoButtonClicked, This sets the date in date picker one, to whatever date is
     * currently set in date picker two.
     */
    private static void setOneWithTwoButtonClicked(ActionEvent e) {
        // Set date from date picker 2.
        LocalDate datePicker2Date = datePicker2.getDate();
        datePicker1.setDate(datePicker2Date);
        // Display message.
        String message = "The datePicker1 date was set using the datePicker2 date!\n\n";
        message += getDatePickerOneDateText();
        panel.messageTextArea.setText(message);
    }

    /**
     * setOneWithFeb31ButtonClicked, This sets the text in date picker one, to a nonexistent date
     * (February 31st). The last valid date in a date picker is always saved. This is a programmatic
     * demonstration of what happens when the user enters invalid text.
     */
    private static void setOneWithFeb31ButtonClicked(ActionEvent e) {
        // Set date picker text.
        datePicker1.setText("February 31, 1950");
        // Display message.
        String message = "The datePicker1 text was set to: \"" + datePicker1.getText() + "\".\n";
        message += "Note: The stored date (the last valid date), did not change because"
            + " February never has 31 days.\n\n";
        message += getDatePickerOneDateText();
        panel.messageTextArea.setText(message);
    }

    /**
     * getOneAndShowButtonClicked, This retrieves and displays whatever date is currently set in
     * date picker one.
     */
    private static void getOneAndShowButtonClicked(ActionEvent e) {
        // Get and display date picker text.
        panel.messageTextArea.setText(getDatePickerOneDateText());
    }

    /**
     * clearOneAndTwoButtonClicked, This clears date picker one.
     */
    private static void clearOneAndTwoButtonClicked(ActionEvent e) {
        // Clear the date pickers.
        datePicker1.clear();
        datePicker2.clear();
        // Display message.
        String message = "The datePicker1 and datePicker2 dates were cleared!\n\n";
        message += getDatePickerOneDateText() + "\n";
        String date2String = datePicker2.getDateStringOrSuppliedString("(null)");
        message += ("The datePicker2 date is currently set to: " + date2String + ".");
        panel.messageTextArea.setText(message);
    }

    /**
     * getDatePickerOneDateText, This returns a string indicating the current date stored in date
     * picker one.
     */
    private static String getDatePickerOneDateText() {
        // Create date string for date picker 1.
        String dateString = datePicker1.getDateStringOrSuppliedString("(null)");
        return ("The datePicker1 date is currently set to: " + dateString + ".");
    }

    /**
     * createDemoButtons, This creates the buttons for the demo, adds an action listener to each
     * button, and adds each button to the display panel.
     */
    private static void createDemoButtons() {
        JPanel buttonPanel = new JPanel(new WrapLayout());
        panel.scrollPaneForButtons.setViewportView(buttonPanel);
        // Create each demo button, and add it to the panel.
        // Add an action listener to link it to its appropriate function.
        JButton showIntro = new JButton("Show Introduction Message");
        showIntro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showIntroductionClicked(e);
            }
        });
        buttonPanel.add(showIntro);
        JButton setTwoWithY2K = new JButton("Set DatePicker Two with New Years Day 2000");
        setTwoWithY2K.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setTwoWithY2KButtonClicked(e);
            }
        });
        buttonPanel.add(setTwoWithY2K);
        JButton setDateOneWithTwo = new JButton("Set DatePicker One with the date in Two");
        setDateOneWithTwo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setOneWithTwoButtonClicked(e);
            }
        });
        buttonPanel.add(setDateOneWithTwo);
        JButton setOneWithFeb31 = new JButton("Set Text in DatePicker One to Feb 31, 1950");
        setOneWithFeb31.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setOneWithFeb31ButtonClicked(e);
            }
        });
        buttonPanel.add(setOneWithFeb31);
        JButton getOneAndShow = new JButton("Get and show the date in DatePicker One");
        getOneAndShow.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getOneAndShowButtonClicked(e);
            }
        });
        buttonPanel.add(getOneAndShow);
        JButton clearOneAndTwo = new JButton("Clear DatePickers One and Two");
        clearOneAndTwo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearOneAndTwoButtonClicked(e);
            }
        });
        buttonPanel.add(clearOneAndTwo);
        JButton toggleButton = new JButton("Toggle DatePicker One");
        toggleButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                toggleDateOneButtonClicked();
            }
        });
        buttonPanel.add(toggleButton);
        JButton setTimeOneWithTwo = new JButton("TimePickers: Set TimePicker One with the time in Two");
        setTimeOneWithTwo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setTimeOneWithTimeTwoButtonClicked(e);
            }
        });
        buttonPanel.add(setTimeOneWithTwo);
        JButton timeToggleButton = new JButton("Toggle TimePicker One");
        timeToggleButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                toggleTimeOneButtonClicked();
            }
        });
        buttonPanel.add(timeToggleButton);

        // Add a button for showing the table editors demo.
        JButton tableEditorsDemoButton = new JButton("Show TableEditorsDemo");
        tableEditorsDemoButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                showTableEditorsDemoButtonClicked();
            }
        });
        buttonPanel.add(tableEditorsDemoButton);

        // Add a button for showing system information.
        JButton showSystemInformationButton = new JButton("JDK Versions");
        showSystemInformationButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                showSystemInformationButtonClicked();
            }
        });
        buttonPanel.add(showSystemInformationButton);
    }

    /**
     * showIntroductionClicked, This displays an introduction message about the date picker.
     */
    private static void showIntroductionClicked(ActionEvent e) {
        panel.messageTextArea.setText("Interface: \nMost items in a date picker are clickable. "
            + "These include... The buttons for previous and next month, the buttons for "
            + "previous and next year, the \"today\" text, the \"clear\" text, and individual "
            + "dates. A click on the month or year label (at the top), will open a menu for "
            + "changing the month or year.\n\nGeneral features: \n* Automatic "
            + "internationalization. \n* Relatively compact source code.\n* Creating a "
            + "DatePicker, TimePicker, or DateTimePicker requires only one line of code.\n"
            + "* Open source code base.\n\n"
            + "Data types: \nThe standard Java 8 time library is used to store dates, "
            + "and they are convertible to other data types. \n(The Java 8 time package "
            + "is also called \"java.time\" or \"JSR-310\", and was developed by the author "
            + "of Joda Time.)\n\nVeto and Highlight Policies: \nThese policies are optional. "
            + "A veto policy restricts the dates that can be selected. A highlight policy "
            + "provides a visual highlight on desired dates, with optional tooltips. If today "
            + "is vetoed, the \"today\" button will be grey and disabled.\n\nDate values and "
            + "automatic validation: \nEvery date picker stores its current text, and its last "
            + "valid date. The last valid date is returned when you call DatePicker.getDate(). "
            + "If the user types into the text field, any text that is not a valid date will "
            + "be displayed in red, any vetoed date will have a strikethrough, and valid "
            + "dates will display in black. When the focus on a date picker is lost, the text "
            + "is always set to match the last valid date.\n\nTimePicker basic features: \n"
            + "Pressing the up or down arrow keys will change the displayed time by one "
            + "minute. Holding down the arrow keys, or holding the (optional) timespinner "
            + "buttons will change the time at an accelerating rate. Clicking the time drop "
            + "down button (or pressing the right arrow key) will open a time selection menu. "
            + "The default intervals and range in the time drop down menu may optionally be "
            + "changed by the programmer (in the TimePickerSettings class)."
            + "\n\n\n");
        panel.messageTextArea.setCaretPosition(0);
    }

    /**
     * toggleDateOneButtonClicked, This toggles (opens or closes) date picker one.
     */
    private static void toggleDateOneButtonClicked() {
        datePicker1.togglePopup();
        String message = "The datePicker1 calendar popup is ";
        message += (datePicker1.isPopupOpen()) ? "open!" : "closed!";
        panel.messageTextArea.setText(message);
    }

    /**
     * toggleTimeOneButtonClicked, This toggles (opens or closes) time picker one.
     */
    private static void toggleTimeOneButtonClicked() {
        timePicker1.togglePopup();
        String message = "The timePicker1 menu popup is ";
        message += (timePicker1.isPopupOpen()) ? "open!" : "closed!";
        panel.messageTextArea.setText(message);
    }

    /**
     * showSystemInformationButtonClicked, This shows the current system information.
     */
    private static void showSystemInformationButtonClicked() {
        String runningJavaVersion = InternalUtilities.getJavaRunningVersionAsString();
        String targetJavaVersion = InternalUtilities.getJavaTargetVersionFromPom();
        String projectVersion = InternalUtilities.getProjectVersionString();
        boolean isBackport = ("1.6".equals(targetJavaVersion));
        String message = "";
        message += "## Current configuration ##";
        message += "\nLGoodDatePicker version: \"LGoodDatePicker ";
        message += (isBackport) ? ("Backport " + projectVersion) : (projectVersion + " (Standard)");
        message += "\".";
        message += "\nJava target version: Java " + targetJavaVersion;
        message += "\nJava running version: " + runningJavaVersion;
        message += "\n\nMinimum Requirements:"
            + "\n\"LGoodDatePicker Standard\" requires Java 1.8 (or above). "
            + "\n\"LGoodDatePicker Backport\" requires Java 1.6 or 1.7.";
        panel.messageTextArea.setText(message);
    }

    /**
     * showTableEditorsDemoButtonClicked, This shows the table editors demo.
     */
    private static void showTableEditorsDemoButtonClicked() {
        TableEditorsDemo.createAndShowTableDemoFrame();
    }

    /**
     * SampleDateChangeListener, A date change listener provides a way for a class to receive
     * notifications whenever the date has changed in a DatePicker.
     */
    private static class SampleDateChangeListener implements DateChangeListener {

        /**
         * datePickerName, This holds a chosen name for the date picker that we are listening to,
         * for generating date change messages in the demo.
         */
        public String datePickerName;

        /**
         * Constructor.
         */
        private SampleDateChangeListener(String datePickerName) {
            this.datePickerName = datePickerName;
        }

        /**
         * dateChanged, This function will be called each time that the date in the applicable date
         * picker has changed. Both the old date, and the new date, are supplied in the event
         * object. Note that either parameter may contain null, which represents a cleared or empty
         * date.
         */
        @Override
        public void dateChanged(DateChangeEvent event) {
            LocalDate oldDate = event.getOldDate();
            LocalDate newDate = event.getNewDate();
            String oldDateString = PickerUtilities.localDateToString(oldDate, "(null)");
            String newDateString = PickerUtilities.localDateToString(newDate, "(null)");
            String messageStart = "\nThe date in " + datePickerName + " has changed from: ";
            String fullMessage = messageStart + oldDateString + " to: " + newDateString + ".";
            if (!panel.messageTextArea.getText().startsWith(messageStart)) {
                panel.messageTextArea.setText("");
            }
            panel.messageTextArea.append(fullMessage);
        }
    }

    /**
     * SampleDateTimeChangeListener, A DateTimeChangeListener provides a way for a class to receive
     * notifications whenever the date or time has changed in a DateTimePicker.
     */
    private static class SampleDateTimeChangeListener implements DateTimeChangeListener {

        /**
         * dateTimePickerName, This holds a chosen name for the component that we are listening to,
         * for generating time change messages in the demo.
         */
        public String dateTimePickerName;

        /**
         * Constructor.
         */
        private SampleDateTimeChangeListener(String dateTimePickerName) {
            this.dateTimePickerName = dateTimePickerName;
        }

        /**
         * dateOrTimeChanged, This function will be called whenever the in date or time in the
         * applicable DateTimePicker has changed.
         */
        @Override
        public void dateOrTimeChanged(DateTimeChangeEvent event) {
            // Report on the overall DateTimeChangeEvent.
            String messageStart = "\n\nThe LocalDateTime in " + dateTimePickerName + " has changed from: (";
            String fullMessage = messageStart + event.getOldDateTimeStrict() + ") to (" + event.getNewDateTimeStrict() + ").";
            if (!panel.messageTextArea.getText().startsWith(messageStart)) {
                panel.messageTextArea.setText("");
            }
            panel.messageTextArea.append(fullMessage);
            // Report on any DateChangeEvent, if one exists.
            DateChangeEvent dateEvent = event.getDateChangeEvent();
            if (dateEvent != null) {
                String dateChangeMessage = "\nThe DatePicker value has changed from (" + dateEvent.getOldDate()
                    + ") to (" + dateEvent.getNewDate() + ").";
                panel.messageTextArea.append(dateChangeMessage);
            }
            // Report on any TimeChangeEvent, if one exists.
            TimeChangeEvent timeEvent = event.getTimeChangeEvent();
            if (timeEvent != null) {
                String timeChangeMessage = "\nThe TimePicker value has changed from ("
                    + timeEvent.getOldTime() + ") to (" + timeEvent.getNewTime() + ").";
                panel.messageTextArea.append(timeChangeMessage);
            }
        }
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

    /**
     * SampleTimeChangeListener, A time change listener provides a way for a class to receive
     * notifications whenever the time has changed in a TimePicker.
     */
    private static class SampleTimeChangeListener implements TimeChangeListener {

        /**
         * timePickerName, This holds a chosen name for the time picker that we are listening to,
         * for generating time change messages in the demo.
         */
        public String timePickerName;

        /**
         * Constructor.
         */
        private SampleTimeChangeListener(String timePickerName) {
            this.timePickerName = timePickerName;
        }

        /**
         * timeChanged, This function will be called whenever the time in the applicable time picker
         * has changed. Note that the value may contain null, which represents a cleared or empty
         * time.
         */
        @Override
        public void timeChanged(TimeChangeEvent event) {
            LocalTime oldTime = event.getOldTime();
            LocalTime newTime = event.getNewTime();
            String oldTimeString = PickerUtilities.localTimeToString(oldTime, "(null)");
            String newTimeString = PickerUtilities.localTimeToString(newTime, "(null)");
            String messageStart = "\nThe time in " + timePickerName + " has changed from: ";
            String fullMessage = messageStart + oldTimeString + " to: " + newTimeString + ".";
            if (!panel.messageTextArea.getText().startsWith(messageStart)) {
                panel.messageTextArea.setText("");
            }
            panel.messageTextArea.append(fullMessage);
        }
    }

    /**
     * SampleTimeVetoPolicy, A veto policy is a way to disallow certain times from being selected in
     * the time picker. A vetoed time cannot be added to the time drop down menu. A vetoed time
     * cannot be selected by using the keyboard or the mouse.
     */
    private static class SampleTimeVetoPolicy implements TimeVetoPolicy {

        /**
         * isTimeAllowed, Return true if a time should be allowed, or false if a time should be
         * vetoed.
         */
        @Override
        public boolean isTimeAllowed(LocalTime time) {
            // Only allow times from 9a to 5p, inclusive.
            return PickerUtilities.isLocalTimeInRange(
                time, LocalTime.of(9, 00), LocalTime.of(17, 00), true);
        }
    }

    /**
     * SampleCalendarListener, A calendar listener provides a way for a class to receive
     * notifications whenever a selected date or displayed YearMonth has been changed in an
     * -independent- CalendarPanel.
     */
    private static class SampleCalendarListener implements CalendarListener {

        /**
         * selectedDateChanged, This function will be called each time that a date is selected in
         * the independent CalendarPanel. The new and old selected dates are supplied in the event
         * object. These parameters may contain null, which represents a cleared or empty date.
         *
         * By intention, this function will be called even if the user selects the same date value
         * twice in a row. This is so that the programmer can catch all events of interest.
         * Duplicate events can optionally be detected with the function
         * CalendarSelectionEvent.isDuplicate().
         */
        @Override
        public void selectedDateChanged(CalendarSelectionEvent event) {
            LocalDate oldDate = event.getOldDate();
            LocalDate newDate = event.getNewDate();
            String oldDateString = PickerUtilities.localDateToString(oldDate, "(null)");
            String newDateString = PickerUtilities.localDateToString(newDate, "(null)");
            String messageStart = "\nIndependent Calendar Panel:";
            String messagePartTwo = " The selected date has changed from '";
            String fullMessage = messageStart + messagePartTwo
                + oldDateString + "' to '" + newDateString + "'. ";
            fullMessage += (event.isDuplicate()) ? "(Event marked as duplicate.)" : "";
            if (!panel.messageTextArea.getText().startsWith(messageStart)) {
                panel.messageTextArea.setText("");
            }
            panel.messageTextArea.append(fullMessage);
        }

        /**
         * yearMonthChanged, This function will be called each time that the displayed YearMonth is
         * changed in the independent CalendarPanel. The new and old selected YearMonths are
         * supplied in the event object. These parameters will never be null.
         *
         * By intention, this function will be called even if the user selects the same YearMonth
         * value twice in a row. This is so that the programmer can catch all events of interest.
         * Duplicate events can optionally be detected with the function
         * YearMonthChangeEvent.isDuplicate().
         */
        @Override
        public void yearMonthChanged(YearMonthChangeEvent event) {
            YearMonth oldYearMonth = event.getOldYearMonth();
            YearMonth newYearMonth = event.getNewYearMonth();
            String oldYearMonthString = oldYearMonth.toString();
            String newYearMonthString = newYearMonth.toString();
            String messageStart = "\nIndependent Calendar Panel:";
            String messagePartTwo = " The displayed YearMonth has changed from '";
            String fullMessage = messageStart + messagePartTwo
                + oldYearMonthString + "' to '" + newYearMonthString + "'. ";
            fullMessage += (event.isDuplicate()) ? "(Event marked as duplicate.)" : "";
            if (!panel.messageTextArea.getText().startsWith(messageStart)) {
                panel.messageTextArea.setText("");
            }
            panel.messageTextArea.append(fullMessage);
        }
    }

}
