package com.lgooddatepicker.timepicker;

import com.lgooddatepicker.optionalusertools.PickerUtilities;
import com.lgooddatepicker.optionalusertools.TimeVetoPolicy;
import com.lgooddatepicker.zinternaltools.ExtraTimeStrings;
import java.awt.Color;
import java.awt.Font;
import java.awt.font.TextAttribute;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Map;
import java.util.TreeSet;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

/**
 * TimePickerSettings, This holds all the settings that can be customized in a time picker. Most of
 * the fields of this class are public, so that the settings are easier to customize as needed. The
 * fields that are not public can be set using the class functions.
 *
 * A TimePickerSettings instance may be (optionally) created, customized, and passed to the time
 * picker constructor. If no settings instance is supplied when a time picker is constructed, then a
 * settings instance with default settings is automatically generated and used by the time picker
 * class.
 *
 * Each and all of the setting fields are set to a default value when a TimePickerSettings object is
 * constructed. This means that the programmer does not need to overwrite all (or any) of the
 * available settings to use this class. They only need to change any particular settings that they
 * wish to customize.
 *
 * This class also contains a small number of settings for the DateTimePicker class. (The
 * DateTimePicker class combines a date picker with a time picker.) All of those settings begin with
 * the prefix "zDateTimePicker_".
 */
public class TimePickerSettings {

    /**
     * allowEmptyTimes, This indicates whether or not empty times are allowed in the time picker.
     * Empty times are also called "null times". The default value is true, which allows empty
     * times. If empty times are not allowed, but TimePickerSettings.initialTime is left set to
     * null, then the initial time will be set to a default value.
     */
    public boolean allowEmptyTimes = true;

    /**
     * allowKeyboardEditing, This indicates whether or not keyboard editing is allowed for this time
     * picker. If this is true, then times can be entered by either using the keyboard or the mouse.
     * If this is false, then times can only be selected by using the mouse. When this is false, the
     * user will be limited to choosing times which have been added to the time drop down menu. The
     * default value is true. It is generally recommended to leave this setting as "true".
     *
     * Accessibility Impact: Disallowing the use of the keyboard, and requiring the use of the
     * mouse, could impact the accessibility of your program for disabled persons.
     *
     * Note: This setting does not impact the automatic enforcement of valid or vetoed times. To
     * learn about the automatic time validation and enforcement for keyboard entered text, see the
     * javadocs for the TimePicker class.
     */
    public boolean allowKeyboardEditing = true;

    /**
     * borderTimePopup, This allows you to set a custom border for the time picker popup menu. By
     * default, a simple border is drawn.
     */
    public Border borderTimePopup;

    /**
     * colorTextInvalidTime, This is the text field text color for invalid times. The default color
     * is red.
     */
    public Color colorTextInvalidTime = Color.red;

    /**
     * colorTextValidTime, This is the text field text color for valid times. The default color is
     * black.
     */
    public Color colorTextValidTime = Color.black;

    /**
     * colorTextVetoedTime, This is the text field text color for vetoed times. The default color is
     * black. Note: The default fontVetoedTime setting will draw a line (strikethrough) vetoed
     * times.
     */
    public Color colorTextVetoedTime = Color.black;

    /**
     * fontInvalidTime, This is the text field text font for invalid times. The default font is
     * normal.
     */
    public Font fontInvalidTime;

    /**
     * fontValidTime, This is the text field text font for valid times. The default font is normal.
     */
    public Font fontValidTime;

    /**
     * fontVetoedTime, This is the text field text font for vetoed times. The default font crosses
     * out the vetoed time. (Has a strikethrough font attribute.)
     */
    public Font fontVetoedTime;

    /**
     * formatForDisplayTime, This is used to format and display the time values in the main text
     * field of the time picker. By default, a format is generated from the time picker locale.
     */
    public DateTimeFormatter formatForDisplayTime;

    /**
     * formatForMenuTimes, This is used to format and display the time values in the menu of the
     * time picker. By default, a format is generated from the time picker locale.
     */
    public DateTimeFormatter formatForMenuTimes;

    /**
     * formatsForParsing, This holds a list of formats that are used to attempt to parse times that
     * are typed by the user. When parsing a time, these formats are tried in the order that they
     * appear in this list. Note that the formatForDisplayTime and formatForMenuTimes are always
     * tried (in that order) before any other parsing formats. The default values for the
     * formatsForParsing are generated using the timeLocale, using the enum constants in
     * java.time.format.FormatStyle.
     */
    public ArrayList<DateTimeFormatter> formatsForParsing;

    /**
     * gapBeforeButtonPixels, This specifies the desired width for the gap between the time picker
     * and the toggle time menu button (in pixels). The default value is null. If this is left at
     * null, then the gap will set to 0 pixels in the time picker constructor.
     */
    public Integer gapBeforeButtonPixels = null;

    /**
     * initialTime, This is the time that the time picker will have when it is created. This can be
     * set to any time, or it can be set to null. The default value for initialTime is null, which
     * represents an empty time.
     *
     * If allowEmptyTimes is false, then a null initialTime will be ignored. More specifically: When
     * a TimePicker is constructed, if allowEmptyTimes is false and initialTime is null, then the
     * initialTime will be set to a default value. (The default value is currently 7:00 am.)
     *
     * If a TimeVetoPolicy exists, and the supplied time is vetoed, then the time will be entered
     * into the text field (and displayed with the "fontVetoedTime"), but it will not be committed
     * to the "last valid time".
     *
     * See TimePicker.setTime() to read about the automatic validation of set times.
     */
    public LocalTime initialTime = null;

    /**
     * maximumVisibleMenuRows, This is the maximum number of rows that can be displayed in the time
     * selection menu without using a scroll bar. In other words, this specifies the default maximum
     * height of the time selection menu (in rows). The default value is 10 rows.
     *
     * If this allows a greater number of rows than the actual number of time entries in the time
     * drop down menu, then the menu will be made smaller to fit the number of time entries.
     */
    public int maximumVisibleMenuRows = 10;

    /**
     * potentialMenuTimes, This is a list of candidate time values for populating the drop down
     * menu. All of these times that are not vetoed will be added to the drop down menu. Any vetoed
     * times will be ignored. By default, this contains a list of LocalTime values going from
     * Midnight to 11:30PM, in 30 minute increments. This variable is private to ensure the validity
     * of the menu times list. To customize the menu times, call one of the
     * generatePotentialMenuTimes() functions with your desired parameters.
     */
    private ArrayList<LocalTime> potentialMenuTimes;

    /**
     * timeLocale, This is the locale of the time picker, which is used to generate some of the
     * other default values, such as the default time formats.
     */
    public Locale timePickerLocale;

    /**
     * useLowercaseForDisplayTime, This indicates if the display time should always be shown in
     * lowercase. The default value is true. If this is true, the display time will always be shown
     * in lowercase text. If this is false, then the text case that is used will be determined by
     * the default time symbols and default format for the locale.
     */
    public boolean useLowercaseForDisplayTime = true;

    /**
     * useLowercaseForMenuTimes, This indicates if the menu times should always be shown in
     * lowercase. The default value is true. If this is true, the menu times will always be shown in
     * lowercase text. If this is false, then the text case that is used will be determined by the
     * default time symbols and default format for the locale.
     */
    public boolean useLowercaseForMenuTimes = true;

    /**
     * vetoPolicy, If a veto policy is supplied, it will be used to determine which times are
     * allowed or not allowed to be selected in the time picker. Vetoed times are not accepted by
     * the time picker text field. Vetoed times will not be displayed or available as options in the
     * drop down menu. See the demo class for an example of constructing a veto policy. By default,
     * there is no veto policy. (The default value is null.)
     */
    public TimeVetoPolicy vetoPolicy = null;

    /**
     * zDateTimePicker_GapBeforeTimePickerPixels, This setting only applies to the DateTimePicker
     * class. This specifies the desired width for the gap between the date picker and the time
     * picker (in pixels). The default value is null. If this is left at null, then the gap will set
     * to 5 pixels in the DateTimePicker constructor.
     */
    public Integer zDateTimePicker_GapBeforeTimePickerPixels = null;

    /**
     * Constructor with Default Locale, This constructs a time picker settings instance using the
     * system default locale and language. The constructor populates all the settings with default
     * values.
     */
    public TimePickerSettings() {
        this(Locale.getDefault());
    }

    /**
     * Constructor with Custom Locale, This constructs a time picker settings instance using the
     * supplied locale and language. The constructor populates all the settings with default values.
     */
    public TimePickerSettings(Locale timeLocale) {
        // Save the locale.
        this.timePickerLocale = timeLocale;

        // Generate default menu times.
        generatePotentialMenuTimes(TimeIncrement.ThirtyMinutes, null, null);

        // Generate a default display and menu formats.
        formatForDisplayTime = ExtraTimeStrings.getDefaultFormatForDisplayTime(timeLocale);
        formatForMenuTimes = ExtraTimeStrings.getDefaultFormatForMenuTimes(timeLocale);

        // Generate default parsing formats.
        FormatStyle[] allFormatStyles = new FormatStyle[]{
            FormatStyle.SHORT, FormatStyle.MEDIUM, FormatStyle.LONG, FormatStyle.FULL};
        formatsForParsing = new ArrayList<>();
        formatsForParsing.add(DateTimeFormatter.ISO_LOCAL_TIME);
        for (FormatStyle formatStyle : allFormatStyles) {
            DateTimeFormatter parseFormat = new DateTimeFormatterBuilder().parseLenient().
                    parseCaseInsensitive().appendLocalized(null, formatStyle).
                    toFormatter(timeLocale);
            formatsForParsing.add(parseFormat);
        }

        // Get any common extra parsing formats for the specified locale, and append them to
        // the list of parsingFormatters.
        ArrayList<DateTimeFormatter> extraFormatters
                = ExtraTimeStrings.getExtraTimeParsingFormatsForLocale(timeLocale);
        formatsForParsing.addAll(extraFormatters);

        // This will use a default border provided by the CustomPopup class.
        borderTimePopup = new EmptyBorder(0, 0, 0, 0);

        // Generate the default fonts and text colors.
        fontValidTime = new JTextField().getFont();
        fontInvalidTime = new JTextField().getFont();
        fontVetoedTime = new JTextField().getFont();
        Map attributes = fontVetoedTime.getAttributes();
        attributes.put(TextAttribute.STRIKETHROUGH, TextAttribute.STRIKETHROUGH_ON);
        fontVetoedTime = new Font(attributes);
    }

    /**
     * generatePotentialMenuTimes, This will generate a list of menu times for populating the combo
     * box menu, using a TimePickerSettings.TimeIncrement value. The menu times will always start at
     * Midnight, and increase according to the increment until the last time before 11:59pm.
     *
     * Note: This function can be called before or after setting an optional veto policy. Vetoed
     * times will never be added to the time picker menu, regardless of whether they are generated
     * by this function.
     *
     * Example usage: generatePotentialMenuTimes(TimeIncrement.FifteenMinutes);
     *
     * Number of entries: If no veto policy has been created, the number of entries in the drop down
     * menu would be determined by the size of the increment as follows; FiveMinutes has 288
     * entries. TenMinutes has 144 entries. FifteenMinutes has 96 entries. TwentyMinutes has 72
     * entries. ThirtyMinutes has 48 entries. OneHour has 24 entries.
     */
    public void generatePotentialMenuTimes(TimeIncrement timeIncrement,
            LocalTime optionalStartTime, LocalTime optionalEndTime) {
        // If either bounding time does does not already exist, then set it to the maximum range.
        LocalTime startTime = (optionalStartTime == null) ? LocalTime.MIN : optionalStartTime;
        LocalTime endTime = (optionalEndTime == null) ? LocalTime.MAX : optionalEndTime;
        // Initialize our needed variables.
        potentialMenuTimes = new ArrayList<>();
        int increment = timeIncrement.minutes;
        // Start at midnight, which is the earliest time of day for LocalTime values.
        LocalTime entry = LocalTime.MIDNIGHT;
        boolean continueLoop = true;
        while (continueLoop) {
            if (PickerUtilities.isLocalTimeInRange(entry, startTime, endTime, true)) {
                potentialMenuTimes.add(entry);
            }
            entry = entry.plusMinutes(increment);
            // Note: This stopping criteria works as long as as ((60 % increment) == 0).
            continueLoop = (!(LocalTime.MIDNIGHT.equals(entry)));
        }
    }

    /**
     * generatePotentialMenuTimes, This will generate the menu times for populating the combo box
     * menu, using the items from a list of LocalTime instances. The list will be sorted and cleaned
     * of duplicates before use. Null values and duplicate values will not be added. When this
     * function is complete, the menu will contain one instance of each unique LocalTime that was
     * supplied to this function, in ascending order going from Midnight to 11.59pm. The drop down
     * menu will not contain any time values except those supplied in the desiredTimes list.
     *
     * Note: This function can be called before or after setting an optional veto policy. Vetoed
     * times will never be added to the time picker menu, regardless of whether they are generated
     * by this function.
     */
    public void generatePotentialMenuTimes(ArrayList<LocalTime> desiredTimes) {
        potentialMenuTimes = new ArrayList<>();
        if (desiredTimes == null || desiredTimes.isEmpty()) {
            return;
        }
        TreeSet<LocalTime> timeSet = new TreeSet<>();
        for (LocalTime desiredTime : desiredTimes) {
            if (desiredTime != null) {
                timeSet.add(desiredTime);
            }
        }
        for (LocalTime timeSetEntry : timeSet) {
            potentialMenuTimes.add(timeSetEntry);
        }
    }

    /**
     * getPotentialMenuTimes, This returns a copy of the list of potential menu times. For
     * additional details, see TimePickerSettings.potentialMenuTimes.
     */
    public ArrayList<LocalTime> getPotentialMenuTimes() {
        return (ArrayList<LocalTime>) potentialMenuTimes.clone();
    }

    /**
     * use24HourClockFormat, This can be called to set the TimePicker to use a 24-hour clock format.
     * This will replace the settings called formatForDisplayTime, and formatForMenuTimes, with the
     * commonly used 24-hour clock format ("HH:mm"). Any single digit hours will be zero padded in
     * this format.
     *
     * Localization Note: It is not currently known if the 24 hour clock format is the same for all
     * locales. (Though it is considered likely that this format is the same in most places.) If
     * this format should be set to a different pattern for a particular locale that is familiar to
     * you, then please inform the developers.
     */
    public void use24HourClockFormat() {
        formatForDisplayTime = PickerUtilities.createFormatterFromPatternString(
                "HH:mm", timePickerLocale);
        formatForMenuTimes = formatForDisplayTime;
    }

    /**
     * TimeIncrement, This is a list of increments that can be used with the generateMenuTimes()
     * function.
     */
    public enum TimeIncrement {
        FiveMinutes(5), TenMinutes(10), FifteenMinutes(15), TwentyMinutes(20), ThirtyMinutes(30),
        OneHour(60);

        /**
         * minutes, This holds the number of minutes represented by the time increment.
         */
        final public int minutes;

        /**
         * Constructor. This takes the number of minutes represented by the time increment.
         */
        TimeIncrement(int minutes) {
            this.minutes = minutes;
            if ((60 % minutes) != 0) {
                throw new RuntimeException("TimePickerSettings.TimeIncrement Constructor, "
                        + "All time increments must divide evenly into 60.");
            }
        }
    }

}
