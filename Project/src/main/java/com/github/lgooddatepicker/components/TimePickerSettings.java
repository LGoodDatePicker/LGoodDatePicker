package com.github.lgooddatepicker.components;

import com.privatejgoodies.forms.layout.ColumnSpec;
import com.privatejgoodies.forms.layout.ConstantSize;
import com.privatejgoodies.forms.layout.FormLayout;
import com.github.lgooddatepicker.optionalusertools.PickerUtilities;
import com.github.lgooddatepicker.optionalusertools.TimeVetoPolicy;
import com.github.lgooddatepicker.zinternaltools.ExtraTimeStrings;
import com.github.lgooddatepicker.zinternaltools.InternalConstants;
import com.github.lgooddatepicker.zinternaltools.InternalUtilities;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.font.TextAttribute;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TreeSet;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;

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
     * TimeArea, These enumerations represent areas of the components whose color can be changed.
     * These values are used with the setColor() function, to set the color of various areas of the
     * TimePicker. The default color for each area is also defined here.
     *
     * Note: A default color of "null" means that the default color for that element is supplied by
     * the swing component.
     */
    public enum TimeArea {
        TimePickerTextValidTime(Color.black),
        TimePickerTextInvalidTime(Color.red),
        TimePickerTextVetoedTime(Color.black),
        TextFieldBackgroundValidTime(Color.white),
        TextFieldBackgroundInvalidTime(Color.white),
        TextFieldBackgroundVetoedTime(Color.white),
        TextFieldBackgroundDisallowedEmptyTime(Color.pink);

        TimeArea(Color defaultColor) {
            this.defaultColor = defaultColor;
        }
        public Color defaultColor;
    }

    /**
     * allowEmptyTimes, This indicates whether or not empty times are allowed in the time picker.
     * Empty times are also called "null times". The default value is true, which allows empty
     * times. If empty times are not allowed, but TimePickerSettings.initialTime is left set to
     * null, then the initial time will be set to a default value. (The default value is 7:00am.)
     */
    private boolean allowEmptyTimes = true;

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
    private boolean allowKeyboardEditing = true;

    /**
     * borderTimePopup, This allows you to set a custom border for the time picker popup menu. By
     * default, a simple border is drawn.
     */
    public Border borderTimePopup;

    /**
     * colors, This hash map holds the current color settings for different areas of the TimePicker.
     * These colors can be set with the setColor() function, or retrieved with the getColor()
     * function. By default, this map is populated with a set of default colors. The default colors
     * for each area are defined the "Area" enums.
     */
    private HashMap<TimePickerSettings.TimeArea, Color> colors;

    /**
     * displayToggleTimeMenuButton, This controls whether or not the toggle menu button is displayed
     * (and enabled), on the time picker. The default value is true.
     */
    private boolean displayToggleTimeMenuButton = true;

    /**
     * displaySpinnerButtons, This controls whether or not the spinner buttons are displayed (and
     * enabled), on the time picker. The default value is false.
     */
    private boolean displaySpinnerButtons = false;

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
    private DateTimeFormatter formatForDisplayTime;

    /**
     * formatForMenuTimes, This is used to format and display the time values in the menu of the
     * time picker. By default, a format is generated from the time picker locale.
     */
    private DateTimeFormatter formatForMenuTimes;

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
    private Integer gapBeforeButtonPixels = null;

    /**
     * initialTime, This is the time that the time picker will have when it is created. This can be
     * set to any time, or it can be set to null. The default value for initialTime is null, which
     * represents an empty time. This setting will only have an effect if it is set before the date
     * picker is constructed.
     *
     * If allowEmptyTimes is false, then a null initialTime will be ignored. More specifically: When
     * a TimePicker is constructed, if allowEmptyTimes is false and initialTime is null, then the
     * initialTime will be set to a default value. (The default value is currently 7:00 am.)
     *
     * Note: This time can not be vetoed, because a veto policy can not be set until after the
     * TimePicker is constructed.
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
     * minimumSpinnerButtonWidthInPixels, This sets the minimum width of the spinner buttons. The
     * default value is 20 pixels.
     */
    private int minimumSpinnerButtonWidthInPixels = 20;

    /**
     * minimumToggleTimeMenuButtonWidthInPixels, This sets the minimum width of the toggle menu
     * button. The default value is 26 pixels.
     */
    private int minimumToggleTimeMenuButtonWidthInPixels = 26;

    /**
     * parent, This holds a reference to the parent time picker that is associated with these
     * settings. This variable is only intended to be set from the time picker constructor.
     *
     * This will be null until the TimePicker is constructed (using this settings instance).
     */
    private TimePicker parent;

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
     * sizeTextFieldMinimumWidth, This specifies the minimum width, in pixels, of the TimePicker
     * text field. (The text field is located to the left of the time picker "open time menu"
     * button, and displays the currently selected time.)
     *
     * The default value for this setting is null. When this is set to null, a default width for the
     * time picker text field will be automatically calculated and applied to fit "the largest
     * possible time" that can be displayed with the current time picker settings. The settings that
     * are used to calculate the default text field width include the locale (the language), the
     * fontValidTime, and the format for valid times.
     *
     * See also: "sizeTextFieldMinimumWidthDefaultOverride".
     */
    private Integer sizeTextFieldMinimumWidth = null;

    /**
     * sizeTextFieldMinimumWidthDefaultOverride, This specifies how the time picker should choose
     * the appropriate minimum width for the time picker text field. (As described below.)
     *
     * If this is true, then the applied minimum width will be the largest of either the default, or
     * any programmer supplied, minimum widths.
     *
     * If this is false, then any programmer supplied minimum width will always override the default
     * minimum width. (Even if the programmer supplied width is too small to fit the times that can
     * be displayed in the TimePicker).
     *
     * The default value for this setting is true. This setting only has an effect if
     * (sizeTextFieldMinimumWidth != null).
     *
     * See also: "sizeTextFieldMinimumWidth".
     */
    private boolean sizeTextFieldMinimumWidthDefaultOverride = true;

    /**
     * timeLocale, This is the locale of the time picker, which is used to generate some of the
     * other default values, such as the default time formats.
     */
    private Locale locale;

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
     * vetoPolicy, If a veto policy is supplied, it will be used to determine which times can and
     * cannot be selected in the time picker. Vetoed times can not be selected using the keyboard or
     * the mouse. By default, there is no veto policy. (The default value is null.) See the
     * TimeVetoPolicy Javadocs for details regarding when a veto policy is enforced. See the demo
     * class for an example of constructing a veto policy.
     */
    private TimeVetoPolicy vetoPolicy = null;

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
        // Add all the default colors to the colors map.
        colors = new HashMap< TimeArea, Color>();
        for (TimeArea area : TimeArea.values()) {
            colors.put(area, area.defaultColor);
        }
        // Save the locale.
        this.locale = timeLocale;

        // Generate default menu times.
        generatePotentialMenuTimes(TimeIncrement.ThirtyMinutes, null, null);

        // Generate a default display and menu formats.
        formatForDisplayTime = ExtraTimeStrings.getDefaultFormatForDisplayTime(timeLocale);
        formatForMenuTimes = ExtraTimeStrings.getDefaultFormatForMenuTimes(timeLocale);

        // Generate default parsing formats.
        FormatStyle[] allFormatStyles = new FormatStyle[]{
            FormatStyle.SHORT, FormatStyle.MEDIUM, FormatStyle.LONG, FormatStyle.FULL};
        formatsForParsing = new ArrayList<DateTimeFormatter>();
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

        // Set the default popup border. This can be overridden by the user if they desire. 
        borderTimePopup = new EmptyBorder(0, 0, 0, 0);

        // Generate the default fonts and text colors.
        fontValidTime = new JTextField().getFont();
        fontInvalidTime = new JTextField().getFont();
        fontVetoedTime = new JTextField().getFont();
        Map<TextAttribute, Boolean> additionalAttributes = new HashMap<>();
        additionalAttributes.put(TextAttribute.STRIKETHROUGH, TextAttribute.STRIKETHROUGH_ON);
        fontVetoedTime = fontVetoedTime.deriveFont(additionalAttributes);
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
        potentialMenuTimes = new ArrayList<LocalTime>();
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
        potentialMenuTimes = new ArrayList<LocalTime>();
        if (desiredTimes == null || desiredTimes.isEmpty()) {
            return;
        }
        TreeSet<LocalTime> timeSet = new TreeSet<LocalTime>();
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
     * getAllowEmptyTimes, Returns the value of this setting. See the "set" function for setting
     * information.
     */
    public boolean getAllowEmptyTimes() {
        return allowEmptyTimes;
    }

    /**
     * getAllowKeyboardEditing, Returns the value of this setting. See the "set" function for
     * setting information.
     */
    public boolean getAllowKeyboardEditing() {
        return allowKeyboardEditing;
    }

    /**
     * getColor, This returns the currently set color for the specified area.
     */
    public Color getColor(TimeArea area) {
        return colors.get(area);
    }

    /**
     * getDisplaySpinnerButtons, Returns the value of this setting. See the "set" function for
     * setting information.
     */
    public boolean getDisplaySpinnerButtons() {
        return displaySpinnerButtons;
    }

    /**
     * getDisplayToggleTimeMenuButton, Returns the value of this setting. See the "set" function for
     * setting information.
     */
    public boolean getDisplayToggleTimeMenuButton() {
        return displayToggleTimeMenuButton;
    }

    /**
     * getFormatForDisplayTime, Returns the value this setting. See the "set" function for setting
     * information.
     */
    public DateTimeFormatter getFormatForDisplayTime() {
        return formatForDisplayTime;
    }

    /**
     * getFormatForMenuTimes, Returns the value this setting. See the "set" function for setting
     * information.
     */
    public DateTimeFormatter getFormatForMenuTimes() {
        return formatForMenuTimes;
    }

    /**
     * getGapBeforeButtonPixels, Returns the value of this setting. See the "set" function for
     * setting information.
     */
    public Integer getGapBeforeButtonPixels() {
        return gapBeforeButtonPixels;
    }

    /**
     * getLocale, This returns locale setting of the time picker. The locale can only be set in the
     * TimePickerSettings constructor.
     */
    public Locale getLocale() {
        return locale;
    }

    /**
     * getMinimumSpinnerButtonWidthInPixels, This returns the minimum width of the spinner buttons.
     */
    public int getMinimumSpinnerButtonWidthInPixels() {
        return minimumSpinnerButtonWidthInPixels;
    }

    /**
     * getMinimumSpinnerButtonWidthInPixels, This returns the minimum width of the toggle menu
     * button.
     */
    public int getMinimumToggleTimeMenuButtonWidthInPixels() {
        return minimumToggleTimeMenuButtonWidthInPixels;
    }

    /**
     * getPotentialMenuTimes, This returns a copy of the list of potential menu times. For
     * additional details, see TimePickerSettings.potentialMenuTimes.
     */
    public ArrayList<LocalTime> getPotentialMenuTimes() {
        return new ArrayList<>(potentialMenuTimes);
    }

    /**
     * getSizeTextFieldMinimumWidth, Returns the value of this setting. See the "set" function for
     * setting information.
     */
    public Integer getSizeTextFieldMinimumWidth() {
        return sizeTextFieldMinimumWidth;
    }

    /**
     * getSizeTextFieldMinimumWidthDefaultOverride, Returns the value of this setting. See the "set"
     * function for setting information.
     */
    public boolean getSizeTextFieldMinimumWidthDefaultOverride() {
        return sizeTextFieldMinimumWidthDefaultOverride;
    }

    /**
     * getVetoPolicy, This returns the veto policy.
     */
    public TimeVetoPolicy getVetoPolicy() {
        return vetoPolicy;
    }

    /**
     * isTimeAllowed, This checks to see if the specified time is allowed by any currently set veto
     * policy, and allowed by the current setting of allowEmptyTimes.
     *
     * If allowEmptyTimes is false, and the specified time is null, then this returns false.
     *
     * If a veto policy exists, and the specified time is vetoed, then this returns false.
     *
     * If the time is not vetoed, or if empty times are allowed and the time is null, then this
     * returns true.
     */
    public boolean isTimeAllowed(LocalTime time) {
        if (time == null) {
            return allowEmptyTimes;
        }
        return (!(InternalUtilities.isTimeVetoed(vetoPolicy, time)));
    }

    /**
     * setAllowEmptyTimes, This sets whether or not empty times (null times) are allowed in the time
     * picker. If this is true, then empty times will be allowed in the time picker. If this is
     * false, then empty times will not be allowed.
     *
     * If setting this function to false, it is recommended to call this function -before- setting a
     * veto policy. This sequence will guarantee that the TimePicker.getTime() function will never
     * return a null value, and will guarantee that the setAllowEmptyTimes() function will not throw
     * an exception.
     *
     * If the current time is null and you set allowEmptyTimes to false, then this function will
     * attempt to initialize the current time to 7am. This function will throw an exception if it
     * fails to initialize a null time. An exception is only possible if a veto policy is set before
     * calling this function, and the veto policy vetoes the time "7:00 am".
     */
    public void setAllowEmptyTimes(boolean allowEmptyTimes) {
        this.allowEmptyTimes = allowEmptyTimes;
        if (parent != null) {
            zApplyAllowEmptyTimes();
        }
    }

    /**
     * setAllowKeyboardEditing, This sets whether or not keyboard editing is allowed for this time
     * picker. If this is true, then times can be entered into the time picker either by using the
     * keyboard or the mouse. If this is false, then times can only be selected by using the mouse.
     * The default value is true. It is generally recommended to leave this setting as "true".
     *
     * Accessibility Impact: Disallowing the use of the keyboard, and requiring the use of the
     * mouse, could impact the accessibility of your program for disabled persons.
     *
     * Note: This setting does not impact the automatic enforcement of valid or vetoed times. To
     * learn about the automatic time validation and enforcement for keyboard entered text, see the
     * javadocs for the TimePicker class.
     */
    public void setAllowKeyboardEditing(boolean allowKeyboardEditing) {
        this.allowKeyboardEditing = allowKeyboardEditing;
        if (parent != null) {
            zApplyAllowKeyboardEditing();
        }
    }

    /**
     * setColor, This sets a color for the specified area. Setting an area to null will restore the
     * default color for that area.
     */
    public void setColor(TimeArea area, Color color) {
        // If null was supplied, then use the default color.
        if (color == null) {
            color = area.defaultColor;
        }
        // Save the color to the color map.
        colors.put(area, color);

        // Call any "updating functions" that are appropriate for the specified area.
        if (parent != null) {
            parent.zDrawTextFieldIndicators();
        }
    }

    /**
     * setDisplaySpinnerButtons, This sets whether or not the spinner buttons will be displayed (and
     * enabled), on the time picker. The default value is false.
     */
    public void setDisplaySpinnerButtons(boolean displaySpinnerButtons) {
        this.displaySpinnerButtons = displaySpinnerButtons;
        zApplyDisplaySpinnerButtons();
    }

    /**
     * setDisplayToggleTimeMenuButton, This sets whether or not the toggle menu button will be
     * displayed (and enabled), on the time picker. The default value is true.
     */
    public void setDisplayToggleTimeMenuButton(boolean showToggleTimeMenuButton) {
        this.displayToggleTimeMenuButton = showToggleTimeMenuButton;
        zApplyDisplayToggleTimeMenuButton();
    }

    /**
     * setFormatForDisplayTime, This sets the format that is used to display or parse the text field
     * time times in the time picker, using a DateTimeFormatter instance. The default format is
     * generated using the locale of the settings instance. For most formats, it may be easier to
     * use the version of this function that accepts a pattern string.
     *
     * Available pattern strings can be found in the Javadocs for the DateTimeFormatter class, at
     * this URL: https://docs.oracle.com/javase/8/docs/api/java/time/format/DateTimeFormatter.html
     *
     * If the time picker has already been constructed, then calling this function will cause
     * immediate validation of the text field text.
     */
    public void setFormatForDisplayTime(DateTimeFormatter formatForDisplayTime) {
        this.formatForDisplayTime = formatForDisplayTime;
        if (parent != null) {
            parent.setTextFieldToValidStateIfNeeded();
        }
    }

    /**
     * setFormatForDisplayTime, This sets the format that is used to display or parse the text field
     * time times in the time picker, using a pattern string. The default format is generated using
     * the locale of the settings instance.
     *
     * Available pattern strings can be found in the Javadocs for the DateTimeFormatter class, at
     * this URL: https://docs.oracle.com/javase/8/docs/api/java/time/format/DateTimeFormatter.html
     *
     * If the time picker has already been constructed, then calling this function will cause
     * immediate validation of the text field text.
     */
    public void setFormatForDisplayTime(String patternString) {
        DateTimeFormatter formatter
                = PickerUtilities.createFormatterFromPatternString(patternString, locale);
        setFormatForDisplayTime(formatter);
    }

    /**
     * setFormatForMenuTimes, This sets the format that is used to display or parse menu times in
     * the time picker, using a DateTimeFormatter instance. The default format is generated using
     * the locale of the settings instance. For most formats, it may be easier to use the version of
     * this function that accepts a pattern string.
     *
     * Available pattern strings can be found in the Javadocs for the DateTimeFormatter class, at
     * this URL: https://docs.oracle.com/javase/8/docs/api/java/time/format/DateTimeFormatter.html
     *
     * If the time picker has already been constructed, then calling this function will cause
     * immediate validation of the text field text.
     */
    public void setFormatForMenuTimes(DateTimeFormatter formatForMenuTimes) {
        this.formatForMenuTimes = formatForMenuTimes;
        if (parent != null) {
            parent.setTextFieldToValidStateIfNeeded();
        }
    }

    /**
     * setFormatForMenuTimes, This sets the format that is used to display or parse menu times in
     * the time picker, using a pattern string. The default format is generated using the locale of
     * the settings instance.
     *
     * Available pattern strings can be found in the Javadocs for the DateTimeFormatter class, at
     * this URL: https://docs.oracle.com/javase/8/docs/api/java/time/format/DateTimeFormatter.html
     *
     * If the time picker has already been constructed, then calling this function will cause
     * immediate validation of the text field text.
     */
    public void setFormatForMenuTimes(String patternString) {
        DateTimeFormatter formatter
                = PickerUtilities.createFormatterFromPatternString(patternString, locale);
        setFormatForMenuTimes(formatter);
    }

    /**
     * setGapBeforeButtonPixels, This specifies the desired width for the gap between the time
     * picker and the toggle menu button (in pixels). The default value is null. If this is left at
     * null, then the default value is 0 pixels.
     */
    public void setGapBeforeButtonPixels(Integer gapBeforeButtonPixels) {
        this.gapBeforeButtonPixels = gapBeforeButtonPixels;
        if (parent != null) {
            zApplyGapBeforeButtonPixels();
        }
    }

    /**
     * setInitialTimeToNow, This sets the initial time for the time picker to the current time. This
     * function only has an effect before the time picker is constructed.
     */
    public void setInitialTimeToNow() {
        initialTime = LocalTime.now();
    }

    /**
     * setMinimumSpinnerButtonWidthInPixels, This sets the minimum width of the spinner buttons.
     */
    public void setMinimumSpinnerButtonWidthInPixels(int pixels) {
        this.minimumSpinnerButtonWidthInPixels = pixels;
        zApplyMinimumSpinnerButtonWidthInPixels();
    }

    /**
     * setMinimumToggleTimeMenuButtonWidthInPixels, This sets the minimum width of the toggle menu
     * button.
     */
    public void setMinimumToggleTimeMenuButtonWidthInPixels(int pixels) {
        this.minimumToggleTimeMenuButtonWidthInPixels = pixels;
        zApplyMinimumToggleTimeMenuButtonWidthInPixels();
    }

    /**
     * setParentTimePicker, This sets the parent time picker for these settings. This is only
     * intended to be called from the constructor of the time picker class.
     */
    void setParentTimePicker(TimePicker parentTimePicker) {
        this.parent = parentTimePicker;
    }

    /**
     * setSizeTextFieldMinimumWidth, This sets the minimum width in pixels, of the TimePicker text
     * field.
     *
     * The default value for this setting is null. When this is set to null, a default width for the
     * time picker text field will be automatically calculated and applied to fit "the largest
     * possible time" that can be displayed with the current time picker settings. The settings used
     * to calculate the default text field width include the locale (the language), the
     * fontValidTime, and the format for valid times.
     */
    public void setSizeTextFieldMinimumWidth(Integer minimumWidthInPixels) {
        this.sizeTextFieldMinimumWidth = minimumWidthInPixels;
        if (parent != null) {
            parent.zSetAppropriateTextFieldMinimumWidth();
        }
    }

    /**
     * setSizeTextFieldMinimumWidthDefaultOverride, This specifies how the time picker should choose
     * the appropriate minimum width for the time picker text field. (As described below.)
     *
     * If this is true, then the applied minimum width will be the largest of either the default, or
     * any programmer supplied, minimum widths.
     *
     * If this is false, then any programmer supplied minimum width will always override the default
     * minimum width. (Even if the programmer supplied width is too small to fit the times that can
     * be displayed in the TimePicker).
     *
     * The default value for this setting is true. This setting only has an effect if
     * (sizeTextFieldMinimumWidth != null).
     *
     * See also: "sizeTextFieldMinimumWidth".
     */
    public void setSizeTextFieldMinimumWidthDefaultOverride(boolean defaultShouldOverrideIfNeeded) {
        this.sizeTextFieldMinimumWidthDefaultOverride = defaultShouldOverrideIfNeeded;
        if (parent != null) {
            parent.zSetAppropriateTextFieldMinimumWidth();
        }
    }

    /**
     * setVetoPolicy,
     *
     * This sets a veto policy for the time picker. Note: This function can only be called after the
     * time picker is constructed. If this is called before the TimePicker is constructed, then an
     * exception will be thrown.
     *
     * When a veto policy is supplied, it will be used to determine which times can or can not be
     * selected in the calendar panel. (Vetoed times are also not accepted into the time picker text
     * field). See the demo class for an example of constructing a veto policy.
     *
     * By default, there is no veto policy on a time picker. Setting this function to null will
     * clear any veto policy that has been set.
     *
     * It's possible to set a veto policy that vetoes the current "last valid time". This function
     * returns true if the last valid time is allowed by the new veto policy and the time picker
     * settings, or false if the last valid time is vetoed or disallowed. Setting a new veto policy
     * does not modify the last valid time. Is up to the programmer to resolve any potential
     * conflict between a new veto policy, and the last valid time stored in the time picker.
     */
    public boolean setVetoPolicy(TimeVetoPolicy vetoPolicy) {
        if (parent == null) {
            throw new RuntimeException("TimePickerSettings.setVetoPolicy(), "
                    + "A veto policy can only be set after constructing the TimePicker.");
        }
        this.vetoPolicy = vetoPolicy;
        return isTimeAllowed(parent.getTime());
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
        formatForDisplayTime = PickerUtilities.createFormatterFromPatternString("HH:mm", locale);
        formatForMenuTimes = formatForDisplayTime;
    }

    /**
     * yApplyNeededSettingsAtTimePickerConstruction, This is called from the time picker constructor
     * to apply various settings in this settings instance to the time picker. Only the settings
     * that are needed at the time of time picker construction, are applied in this function.
     */
    void yApplyNeededSettingsAtTimePickerConstruction() {
        // Run the needed "apply" functions.
        zApplyGapBeforeButtonPixels();
        zApplyAllowKeyboardEditing();
        zApplyInitialTime();
        zApplyAllowEmptyTimes();
        zApplyMinimumToggleTimeMenuButtonWidthInPixels();
        zApplyMinimumSpinnerButtonWidthInPixels();
        zApplyDisplayToggleTimeMenuButton();
        zApplyDisplaySpinnerButtons();
    }

    /**
     * zApplyAllowEmptyTimes, This applies the named setting to the parent component.
     *
     * Notes:
     *
     * The zApplyInitialTime() and zApplyAllowEmptyTimes() functions may theoretically be called in
     * any order. However, the order is currently zApplyInitialTime() and zApplyAllowEmptyTimes()
     * because that is more intuitive.
     *
     * This cannot throw an exception while the time picker is being constructed, because a veto
     * policy cannot be set until after the time picker is constructed.
     */
    private void zApplyAllowEmptyTimes() {
        // Find out if we need to initialize a null time.
        if ((!allowEmptyTimes) && (parent.getTime() == null)) {
            // We need to initialize the current time, so find out if the default time is vetoed.
            LocalTime defaultTime = LocalTime.of(7, 0);
            if (InternalUtilities.isTimeVetoed(vetoPolicy, defaultTime)) {
                throw new RuntimeException("Exception in TimePickerSettings.zApplyAllowEmptyTimes(), "
                        + "Could not initialize a null time to 7am, because 7am is vetoed by "
                        + "the veto policy. To prevent this exception, always call "
                        + "setAllowEmptyTimes() -before- setting a veto policy.");
            }
            // Initialize the current time.
            parent.setTime(defaultTime);
        }
    }

    /**
     * zApplyAllowKeyboardEditing, This applies the named setting to the parent component.
     */
    private void zApplyAllowKeyboardEditing() {
        // Set the editability of the time picker text field.
        parent.getComponentTimeTextField().setEditable(allowKeyboardEditing);
        // Set the text field border color based on whether the text field is editable.
        Color textFieldBorderColor = (allowKeyboardEditing)
                ? InternalConstants.colorEditableTextFieldBorder
                : InternalConstants.colorNotEditableTextFieldBorder;
        parent.getComponentTimeTextField().setBorder(new CompoundBorder(
                new MatteBorder(1, 1, 1, 1, textFieldBorderColor), new EmptyBorder(1, 3, 2, 2)));
    }

    /**
     * zApplyDisplaySpinnerButtons, This applies the specified setting to the time picker.
     */
    void zApplyDisplaySpinnerButtons() {
        if (parent == null) {
            return;
        }
        parent.getComponentDecreaseSpinnerButton().setEnabled(displaySpinnerButtons);
        parent.getComponentDecreaseSpinnerButton().setVisible(displaySpinnerButtons);
        parent.getComponentIncreaseSpinnerButton().setEnabled(displaySpinnerButtons);
        parent.getComponentIncreaseSpinnerButton().setVisible(displaySpinnerButtons);
    }

    /**
     * zApplyDisplayToggleTimeMenuButton, This applies the specified setting to the time picker.
     */
    void zApplyDisplayToggleTimeMenuButton() {
        if (parent == null) {
            return;
        }
        parent.getComponentToggleTimeMenuButton().setEnabled(displayToggleTimeMenuButton);
        parent.getComponentToggleTimeMenuButton().setVisible(displayToggleTimeMenuButton);
    }

    /**
     * zApplyGapBeforeButtonPixels, This applies the named setting to the parent component.
     */
    private void zApplyGapBeforeButtonPixels() {
        int gapPixels = (gapBeforeButtonPixels == null) ? 0 : gapBeforeButtonPixels;
        ConstantSize gapSizeObject = new ConstantSize(gapPixels, ConstantSize.PIXEL);
        ColumnSpec columnSpec = ColumnSpec.createGap(gapSizeObject);
        FormLayout layout = ((FormLayout) parent.getLayout());
        layout.setColumnSpec(2, columnSpec);
    }

    /**
     * zApplyInitialTime, This applies the named setting to the parent component.
     *
     * Notes:
     *
     * This does not need to check the parent for null, because this is always and only called while
     * the time picker is being constructed.
     *
     * This does not need to handle (allowEmptyTimes == false && initialTime == null). That
     * situation is handled by the zApplyAllowEmptyTimes() function.
     *
     * There is no possibility that this can conflict with a veto policy at the time that it is set,
     * because a veto policy cannot be set until after the construction of a time picker.
     *
     * The zApplyInitialTime() and zApplyAllowEmptyTimes() functions may theoretically be called in
     * any order. However, the order is currently zApplyInitialTime() and zApplyAllowEmptyTimes()
     * because that is more intuitive.
     */
    private void zApplyInitialTime() {
        if (allowEmptyTimes == true && initialTime == null) {
            parent.setTime(null);
        }
        if (initialTime != null) {
            parent.setTime(initialTime);
        }
    }

    /**
     * zApplyMinimumSpinnerButtonWidthInPixels, The applies the specified setting to the time
     * picker.
     */
    void zApplyMinimumSpinnerButtonWidthInPixels() {
        if (parent == null) {
            return;
        }
        Dimension decreaseButtonPreferredSize = parent.getComponentDecreaseSpinnerButton().getPreferredSize();
        Dimension increaseButtonPreferredSize = parent.getComponentIncreaseSpinnerButton().getPreferredSize();
        int width = Math.max(decreaseButtonPreferredSize.width, increaseButtonPreferredSize.width);
        int height = Math.max(decreaseButtonPreferredSize.height, increaseButtonPreferredSize.height);
        int minimumWidth = minimumSpinnerButtonWidthInPixels;
        width = (width < minimumWidth) ? minimumWidth : width;
        Dimension newSize = new Dimension(width, height);
        parent.getComponentDecreaseSpinnerButton().setPreferredSize(newSize);
        parent.getComponentIncreaseSpinnerButton().setPreferredSize(newSize);
    }

    /**
     * zApplyMinimumToggleTimeMenuButtonWidthInPixels, This applies the specified setting to the
     * time picker.
     */
    void zApplyMinimumToggleTimeMenuButtonWidthInPixels() {
        if (parent == null) {
            return;
        }
        Dimension menuButtonPreferredSize = parent.getComponentToggleTimeMenuButton().getPreferredSize();
        int width = menuButtonPreferredSize.width;
        int height = menuButtonPreferredSize.height;
        int minimumWidth = minimumToggleTimeMenuButtonWidthInPixels;
        width = (width < minimumWidth) ? minimumWidth : width;
        Dimension newSize = new Dimension(width, height);
        parent.getComponentToggleTimeMenuButton().setPreferredSize(newSize);
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
