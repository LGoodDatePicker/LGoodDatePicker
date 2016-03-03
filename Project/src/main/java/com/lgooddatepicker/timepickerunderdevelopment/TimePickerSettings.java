package com.lgooddatepicker.timepickerunderdevelopment;

import com.lgooddatepicker.optionalusertools.TimeVetoPolicy;
import java.awt.Color;
import java.awt.Font;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.Locale;
import java.util.TreeSet;

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
 */
public class TimePickerSettings {

    /**
     * allowEmptyTimes, This indicates whether or not empty times are allowed in the time picker.
     * Empty times are also called "null times". The default value is true, which allows empty
     * times. If empty times are not allowed, but TimePickerSettings.initialTime is left set to
     * null, then the initial time will be set to a default value.
     */
    public boolean allowEmptyTimes;

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
     * dates.
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
     * field of the time picker.
     */
    public DateTimeFormatter formatForDisplayTime;

    /**
     * formatForMenuTimes, This is used to format and display the time values in the menu of the
     * time picker.
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
     * initialTime, This is the time that the time picker will have when it is created. This can be
     * set to any time, or it can be set to null. If you would like the starting time be "Now", then
     * you can use the convenience function TimePickerSettings.setInitialTimeToNow(). The default
     * value for initialTime is null, which represents an empty time.
     *
     * If allowEmptyTime is false, then a null initialTime value will be ignored. More specifically:
     * When a TimePicker is constructed, if allowEmptyTime is false and initialTime is null, then
     * the initialTime will be set to a default value.
     */
    public LocalTime initialTime;

    /**
     * menuTimes, This holds a list of time values that are used to populate the drop down menu. By
     * default, this contains a list of menu times going from Midnight to 11:30PM, in 30 minute
     * increments. This variable is private to ensure the validity of the menuTimes list. To
     * customize the menuTimes, call one of the generateMenuTimes() functions with your desired
     * parameters.
     */
    private ArrayList<LocalTime> menuTimes;

    /**
     * timeLocale, This is the locale of the time picker, which is used to generate some default
     * values such as the default time formats.
     */
    public Locale timeLocale;

    /**
     * timeVetoPolicy, If a veto policy is supplied, it will be used to determine which times cannot
     * be selected in the time picker. (Vetoed times are also not accepted into the time picker text
     * field). See the demo class for an example of constructing a veto policy. By default, there is
     * no veto policy. (The default value is null.)
     */
    public TimeVetoPolicy timeVetoPolicy;

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
        this.timeLocale = timeLocale;

        // Generate default menu times.
        generateMenuTimes(TimeIncrement.ThirtyMinutes);

        // Generate a default display format.
        formatForDisplayTime = DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT).withLocale(timeLocale);

        // Generate default parsing formats.
        FormatStyle[] allFormatStyles = new FormatStyle[]{
            FormatStyle.SHORT, FormatStyle.MEDIUM, FormatStyle.LONG, FormatStyle.FULL};
        formatsForParsing = new ArrayList<>();
        formatsForParsing.add(DateTimeFormatter.ISO_LOCAL_TIME);
        for (FormatStyle formatStyle : allFormatStyles) {
            DateTimeFormatter parseFormat = DateTimeFormatter.ofLocalizedTime(formatStyle).
                    withLocale(timeLocale);
            formatsForParsing.add(parseFormat);
        }

    }

    /**
     * generateMenuTimes, This will generate a list of menu times for populating the combo box menu,
     * using a TimePickerSettings.TimeIncrement value. The menu times will always start at Midnight,
     * and increase according to the increment until the last time before 11:59pm.
     *
     * If you wish to supply a timeVetoPolicy, you should set the timeVetoPolicy variable before
     * calling this function.
     *
     * Example usage: generateMenuTimes(TimeIncrement.FifteenMinutes);
     *
     * Number of entries: The number of entries in the drop down menu is determined by the size of
     * the increment, as follows; FiveMinutes has 288 entries. TenMinutes has 144 entries.
     * FifteenMinutes has 96 entries. TwentyMinutes has 72 entries. ThirtyMinutes has 48 entries.
     * OneHour has 24 entries.
     */
    public void generateMenuTimes(TimeIncrement timeIncrement) {
        menuTimes = new ArrayList<>();
        int increment = timeIncrement.minutes;
        LocalTime entry = LocalTime.MIDNIGHT;
        menuTimes.add(entry);
        entry = entry.plusMinutes(increment);
        while (!(LocalTime.MIDNIGHT.equals(entry))) {
            menuTimes.add(entry);
            entry = entry.plusMinutes(increment);
        }
        System.out.println();
    }

    /**
     * generateMenuTimes, This will generate the menu times for populating the combo box menu, using
     * the items from a list of LocalTime instances. The list will be sorted and cleaned of
     * duplicates before use. Null values and duplicate values will not be added. When this function
     * is complete, the menu will contain one instance of each unique LocalTime that was supplied to
     * this function, in ascending order going from Midnight to 11.59pm. The drop down menu will not
     * contain any time values except those supplied in the desiredTimes list.
     *
     * If you wish to supply a timeVetoPolicy, you should set the timeVetoPolicy variable before
     * calling this function.
     */
    public void generateMenuTimes(ArrayList<LocalTime> desiredTimes) {
        menuTimes = new ArrayList<>();
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
            menuTimes.add(timeSetEntry);
        }
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
        }
    }

}
