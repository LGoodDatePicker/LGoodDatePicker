package com.github.lgooddatepicker.datepicker;

import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.ConstantSize;
import com.jgoodies.forms.layout.FormLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.font.TextAttribute;
import java.time.DayOfWeek;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.FormatStyle;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Map;
import javax.swing.JTextField;
import com.github.lgooddatepicker.zinternaltools.InternalUtilities;
import com.github.lgooddatepicker.zinternaltools.ExtraDateStrings;
import com.github.lgooddatepicker.zinternaltools.TranslationSource;
import java.time.LocalDate;
import javax.swing.border.Border;
import com.github.lgooddatepicker.optionalusertools.DateVetoPolicy;
import com.github.lgooddatepicker.optionalusertools.DateHighlightPolicy;
import com.github.lgooddatepicker.zinternaltools.InternalConstants;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;

/**
 * DatePickerSettings, This holds all the settings that can be customized in a date picker.
 *
 * Some of the fields of this class are public, so that the settings are easier to customize as
 * needed. If a particular field is private, then that setting can be controlled with the matching
 * accessor functions.
 *
 * A DatePickerSettings instance may be (optionally) created, customized, and passed to the date
 * picker constructor. If no settings instance is supplied when a date picker is constructed, then a
 * settings instance with default settings is automatically generated and used by the date picker
 * class.
 *
 * Each and all of the setting fields are set to a default value when a DatePickerSettings object is
 * constructed. This means that the programmer does not need to overwrite all (or any) of the
 * available settings to use this class. They only need to change any particular settings that they
 * wish to customize.
 */
public class DatePickerSettings {

    /**
     * allowEmptyDates, This indicates whether or not empty dates are allowed in the date picker.
     * Empty dates are also called "null dates". The default value is true, which allows empty
     * dates. If empty dates are not allowed, but DatePickerSettings.initialDate is left set to
     * null, then the initial date will be set to today's date.
     */
    private boolean allowEmptyDates = true;

    /**
     * allowKeyboardEditing, This indicates whether or not keyboard editing is allowed for this date
     * picker. If this is true, then dates can be entered into the date picker either by using the
     * keyboard or the mouse. If this is false, then dates can only be selected by using the mouse.
     * The default value is true. It is generally recommended to leave this setting as "true".
     *
     * Accessibility Impact: Disallowing the use of the keyboard, and requiring the use of the
     * mouse, could impact the accessibility of your program for disabled persons.
     *
     * Note: This setting does not impact the automatic enforcement of valid or vetoed dates. To
     * learn about the automatic date validation and enforcement for keyboard entered text, see the
     * javadocs for the DatePicker class.
     */
    private boolean allowKeyboardEditing = true;

    /**
     * borderCalendarPopup, This is the border for the calendar popup window. If this is null, a
     * default border will be provided by the CustomPopup class. The default value is null.
     */
    public Border borderCalendarPopup = null;

    /**
     * colorBackgroundCalendarPanel, This is the background color for the entire calendar panel. The
     * default color is a very light gray.
     */
    public Color colorBackgroundCalendarPanel = new Color(240, 240, 240);

    /**
     * colorBackgroundHighlightedDates, This is the calendar background color for dates which are
     * highlighted by a highlight policy. The default color is green.
     */
    public Color colorBackgroundHighlightedDates = Color.green;

    /**
     * colorBackgroundMonthAndYear, This is the background color used by the month and year buttons.
     * The default color is a very light gray.
     */
    public Color colorBackgroundMonthAndYear = new Color(240, 240, 240);

    /**
     * colorBackgroundNavigateYearMonthButtons, This is the background color used by the buttons for
     * navigating "previous year", "previous month", "next year", "next month". The default value is
     * the default java button background
     */
    public Color colorBackgroundNavigateYearMonthButtons = null;

    /**
     * colorBackgroundTodayAndClear, This is the background color used by the "Today" and "Clear"
     * buttons. The default color is a very light gray.
     */
    public Color colorBackgroundTodayAndClear = new Color(240, 240, 240);

    /**
     * colorBackgroundVetoedDates, This is the calendar background color for dates which are vetoed
     * by a veto policy. The default color is light gray.
     */
    public Color colorBackgroundVetoedDates = Color.lightGray;

    /**
     * colorBackgroundWeekdayLabels, This is the calendar background color for the weekday labels.
     * The default color is a medium sky blue.
     */
    public Color colorBackgroundWeekdayLabels = new Color(184, 207, 229);

    /**
     * colorTextInvalidDate, This is the text field text color for invalid dates. The default color
     * is red.
     */
    public Color colorTextInvalidDate = Color.red;

    /**
     * colorTextValidDate, This is the text field text color for valid dates. The default color is
     * black.
     */
    public Color colorTextValidDate = Color.black;

    /**
     * colorTextVetoedDate, This is the text field text color for vetoed dates. The default color is
     * black. Note: The default fontVetoedDate setting will draw a line (strikethrough) vetoed
     * dates.
     */
    public Color colorTextVetoedDate = Color.black;

    /**
     * firstDayOfWeek, This holds the day of the week that will be displayed in the far left column
     * of the CalendarPanel, as the "first day of the week". The default value is generated using
     * the locale of the settings instance.
     */
    public DayOfWeek firstDayOfWeek;

    /**
     * fontInvalidDate, This is the text field text font for invalid dates. The default font is
     * normal.
     */
    public Font fontInvalidDate;

    /**
     * fontValidDate, This is the text field text font for valid dates. The default font is normal.
     */
    public Font fontValidDate;

    /**
     * fontVetoedDate, This is the text field text font for vetoed dates. The default font crosses
     * out the vetoed date. (Has a strikethrough font attribute.)
     */
    public Font fontVetoedDate;

    /**
     * formatForDatesCommonEra, This holds the default format that is used to display or parse CE
     * dates in the date picker. The default value is generated using the locale of the settings
     * instance. A DateTimeFormatter can be created from a pattern string with the convenience
     * function DateUtilities.createFormatterFromPatternString();
     */
    private DateTimeFormatter formatForDatesCommonEra;

    /**
     * formatForDatesBeforeCommonEra, This holds the default format that is used to display or parse
     * BCE dates in the date picker. The default value is generated using the locale of the settings
     * instance. A DateTimeFormatter can be created from a pattern string with the convenience
     * function DateUtilities.createFormatterFromPatternString();
     *
     * Note: It is important to use the letter "u" (astronomical year) instead of "y" (year of era)
     * when creating pattern strings for BCE dates. This is because the DatePicker uses ISO 8601,
     * which specifies "Astronomical year numbering". (Additional details: The astronomical year
     * "-1" and "1 BC" are not the same thing. Astronomical years are zero-based, and BC dates are
     * one-based. Astronomical year "0", is the same year as "1 BC", and astronomical year "-1" is
     * the same year as "2 BC", and so forth.)
     */
    private DateTimeFormatter formatForDatesBeforeCommonEra;

    /**
     * formatTodayButton, This format is used to format today's date into a date string, which is
     * displayed on the today button. The default value is generated using the locale of the
     * settings instance.
     */
    public DateTimeFormatter formatForTodayButton;

    /**
     * formatsForParsing, This holds a list of formats that are used to attempt to parse dates that
     * are typed by the user. When parsing a date, these formats are tried in the order that they
     * appear in this list. Note that the formatForDatesCommonEra and formatForDatesBeforeCommonEra
     * are always tried (in that order) before any other parsing formats. The default values for the
     * formatsForParsing are generated using the pickerLocale, using the enum constants in
     * java.time.format.FormatStyle.
     */
    public ArrayList<DateTimeFormatter> formatsForParsing;

    /**
     * gapBeforeButtonPixels, This specifies the desired width for the gap between the date picker
     * and the toggle calendar button (in pixels). The default value is null. If this is left at
     * null, then the default value is three pixels.
     */
    private Integer gapBeforeButtonPixels = null;

    /**
     * highlightPolicy, If a highlight policy is supplied, it will be used to determine which dates
     * should be highlighted in the calendar panel. The highlight policy can also supply tooltip
     * text for any highlighted dates. See the demo class for an example of constructing a highlight
     * policy. By default, there is no highlight policy. (The default value is null.)
     */
    public DateHighlightPolicy highlightPolicy = null;

    /**
     * initialDate, This is the date that the date picker will have when it is created. This can be
     * set to any date, or it can be set to null. The default value for initialDate is null, which
     * represents an empty date. This setting will only have an effect if it is set before the date
     * picker is constructed.
     *
     * If allowEmptyDates is false, then a null initialDate will be ignored. More specifically: When
     * a DatePicker is constructed, if allowEmptyDates is false and initialDate is null, then the
     * initialDate will be set to a default value. (The default value is today's date.)
     *
     * Note: This date can not be vetoed, because a veto policy can not be set until after the
     * DatePicker is constructed.
     */
    public LocalDate initialDate = null;

    /**
     * locale, This holds the picker locale instance that indicates the user's language and culture.
     * The locale is used in translating text and determining default behaviors, for the date picker
     * and the calendar panel. The default values for many of the other settings depend on the
     * locale. The locale can only be set in the settings constructor. The default value for the
     * locale is supplied from Locale.getDefault(), from Java and from the operating system.
     */
    private Locale locale;

    /**
     * parent, This holds a reference to the parent date picker that is associated with these
     * settings. This variable is only intended to be set from the date picker constructor.
     *
     * This will be null until the DatePicker is constructed (using this settings instance).
     */
    private DatePicker parent;

    /**
     * sizeDatePanelMinimumHeight, This specifies the minimum height, in pixels, of the date label
     * area. The date label area is the part of the calendar panel that holds (only) the date
     * labels. The remainder of the calendar panel is automatically resized to encompass the date
     * label area. The default value for this variable is (6 * 18). If the default value is
     * modified, the programmer may also wish to adjust sizeDatePanelPixelsExtraHeight.
     */
    public int sizeDatePanelMinimumHeight;

    /**
     * sizeDatePanelMinimumWidth, This specifies the minimum width, in pixels, of the date label
     * area. The date label area is the part of the calendar panel that holds (only) the date
     * labels. The remainder of the calendar panel is automatically resized to encompass the date
     * label area. The default value for this variable is (7 * 30). If the default value is
     * modified, the programmer may also wish to adjust sizeDatePanelPixelsExtraWidth.
     */
    public int sizeDatePanelMinimumWidth;

    /**
     * sizeDatePanelPixelsExtraHeight, This value should not need to be adjusted unless
     * "datePanelMinimumHeight" has been modified. This specifies a number of "extra pixels" to add
     * to the date panel height. By default, these extra pixels are used to force the date labels to
     * fit exactly to the edges of the date label area. Setting this variable for a date panel size
     * other than the default size, is generally a matter of trial and error.
     */
    public int sizeDatePanelPixelsExtraHeight;

    /**
     * sizeDatePanelPixelsExtraWidth, This value should not need to be adjusted unless
     * "datePanelMinimumWidth" has been modified. This specifies a number of "extra pixels" to add
     * to the date panel width. By default, these extra pixels are used to force the date labels to
     * fit exactly to the edges of the date label area. Setting this variable for a date panel size
     * other than the default size, is generally a matter of trial and error.
     */
    public int sizeDatePanelPixelsExtraWidth;

    /**
     * translationArrayMonthNames, This holds an array of month names to use for the calendar panel
     * header, as translated to the current language. It is not expected that this variable will
     * need to be changed by the programmer. The default values are generated using the locale of
     * the settings instance, by retrieving the translated text for the current language from the
     * class called java.text.DateFormatSymbols. This array is indexed with January = 0. The
     * Calendar class month name constants can also be used for indexing. (Calendar.JANUARY, etc).
     *
     * Expected array contents: This array should never be set to null, and the array should always
     * have a length of 12. Each element should always contain a string that is not null and not
     * empty.
     */
    public String[] translationArrayMonthNames;

    /**
     * translationArrayShortMonthNames, This holds an array of short month names to use for the
     * calendar panel header, as translated to the current language. It is not expected that this
     * variable will need to be changed by the programmer. The default values are generated using
     * the locale of the settings instance, by retrieving the translated text for the current
     * language from the class called java.text.DateFormatSymbols. This array is indexed with
     * January = 0. The Calendar class month name constants can also be used for indexing.
     * (Calendar.JANUARY, etc).
     *
     * Expected array contents: This array should never be set to null, and the array should always
     * have a length of 12. Each element should always contain a string that is not null and not
     * empty.
     */
    public String[] translationArrayShortMonthNames;

    /**
     * translationClear, This holds the text of the calendars "Clear" button, as translated to the
     * current language. It is not expected that this variable will need to be changed by the
     * programmer. If you wish to supply a new translation for the date picker, it would generally
     * be better to add (or edit) your translation in the "TranslationResources.properties" file.
     * The default value is generated using the locale of the settings instance, by retrieving the
     * translated text for the current language from the properties file.
     */
    public String translationClear;

    /**
     * translationToday, This holds the text of the calendars "Today" button, as translated to the
     * current language. It is not expected that this variable will need to be changed by the
     * programmer. If you wish to supply a new translation for the date picker, it would generally
     * be better to add (or edit) your translation in the "TranslationResources.properties" file.
     * The default value is generated from the locale of the settings instance, by retrieving the
     * translated text for the current language from the properties file.
     */
    public String translationToday;

    /**
     * vetoPolicy, If a veto policy is supplied, it will be used to determine which dates can and
     * cannot be selected in the calendar panel. (Vetoed dates are also not accepted into the date
     * picker text field). By default, there is no veto policy. (The default value is null.) See the
     * DateVetoPolicy Javadocs for details regarding when a veto policy is enforced. See the demo
     * class for an example of constructing a veto policy.
     */
    private DateVetoPolicy vetoPolicy = null;

    /**
     * Constructor with Default Locale, This constructs a date picker settings instance using the
     * system default locale and language. The constructor populates all the settings with default
     * values.
     */
    public DatePickerSettings() {
        this(Locale.getDefault());
    }

    /**
     * Constructor with Custom Locale, This constructs a date picker settings instance using the
     * supplied locale and language. The constructor populates all the settings with default values.
     */
    public DatePickerSettings(Locale pickerLocale) {
        // Fix a problem where the Hindi locale is not recognized by language alone.
        if ("hi".equals(pickerLocale.getLanguage()) && (pickerLocale.getCountry().isEmpty())) {
            pickerLocale = new Locale("hi", "IN");
        }

        // Save the date picker locale.
        this.locale = pickerLocale;

        // Set the default translations for the locale.
        translationToday = TranslationSource.getTranslation(pickerLocale, "today", "Today");
        translationClear = TranslationSource.getTranslation(pickerLocale, "clear", "Clear");

        // Set the default month names for the current locale.
        translationArrayMonthNames = ExtraDateStrings.getDefaultMonthNamesForLocale(pickerLocale);
        translationArrayShortMonthNames = ExtraDateStrings.getDefaultShortMonthNamesForLocale(pickerLocale);

        // Create default formatters for displaying the today button, and AD and BC dates, in
        // the specified locale.
        formatForTodayButton = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM).withLocale(pickerLocale);
        formatForDatesCommonEra = InternalUtilities.generateDefaultFormatterCE(pickerLocale);
        formatForDatesBeforeCommonEra = InternalUtilities.generateDefaultFormatterBCE(pickerLocale);

        // Create an array of all the FormatStyle enum values, from short to long.
        FormatStyle[] allFormatStyles = new FormatStyle[]{
            FormatStyle.SHORT, FormatStyle.MEDIUM, FormatStyle.LONG, FormatStyle.FULL};

        // Create a set of default parsing formatters for the specified locale.
        formatsForParsing = new ArrayList<>();
        DateTimeFormatter parseFormat;
        for (int i = 0; i < allFormatStyles.length; ++i) {
            parseFormat = new DateTimeFormatterBuilder().parseLenient().parseCaseInsensitive().
                    appendLocalized(allFormatStyles[i], null).toFormatter(pickerLocale);
            formatsForParsing.add(parseFormat);
        }

        // Get any common extra parsing formats for the specified locale, and append them to
        // the list of parsingFormatters.
        ArrayList<DateTimeFormatter> extraFormatters
                = ExtraDateStrings.getExtraParsingFormatsForLocale(pickerLocale);
        formatsForParsing.addAll(extraFormatters);

        // Set the minimum height, minimum width, and extra pixels for the date panel.
        sizeDatePanelMinimumHeight = (6 * 18);
        sizeDatePanelMinimumWidth = (7 * 30);
        sizeDatePanelPixelsExtraHeight = 2;
        sizeDatePanelPixelsExtraWidth = 2;

        // Initialize the first day of the week.
        firstDayOfWeek = WeekFields.of(Locale.getDefault()).getFirstDayOfWeek();

        // Generate the default fonts and text colors.
        fontValidDate = new JTextField().getFont();
        fontInvalidDate = new JTextField().getFont();
        fontVetoedDate = new JTextField().getFont();
        Map attributes = fontVetoedDate.getAttributes();
        attributes.put(TextAttribute.STRIKETHROUGH, TextAttribute.STRIKETHROUGH_ON);
        fontVetoedDate = new Font(attributes);
    }

    /**
     * getAllowEmptyDates, Returns the value of this setting. See the "set" function for setting
     * information.
     */
    public boolean getAllowEmptyDates() {
        return allowEmptyDates;
    }

    /**
     * getAllowKeyboardEditing, Returns the value of this setting. See the "set" function for
     * setting information.
     */
    public boolean getAllowKeyboardEditing() {
        return allowKeyboardEditing;
    }

    /**
     * getFormatForDatesBeforeCommonEra, Returns the value this setting. See the "set" function for
     * setting information.
     */
    public DateTimeFormatter getFormatForDatesBeforeCommonEra() {
        return formatForDatesBeforeCommonEra;
    }

    /**
     * getFormatForDatesCommonEra, Returns the value this setting. See the "set" function for
     * setting information.
     */
    public DateTimeFormatter getFormatForDatesCommonEra() {
        return formatForDatesCommonEra;
    }

    /**
     * getGapBeforeButtonPixels, Returns the value of this setting. See the "set" function for
     * setting information.
     */
    public Integer getGapBeforeButtonPixels() {
        return gapBeforeButtonPixels;
    }

    /**
     * getLocale, This returns locale setting of the date picker. The locale can only be set in the
     * DatePickerSettings constructor.
     */
    public Locale getLocale() {
        return locale;
    }

    /**
     * getVetoPolicy, This returns the veto policy.
     */
    public DateVetoPolicy getVetoPolicy() {
        return vetoPolicy;
    }

    /**
     * isDateAllowed, This checks to see if the specified date is allowed by any currently set veto
     * policy, and allowed by the current setting of allowEmptyDates.
     *
     * If allowEmptyDates is false, and the specified date is null, then this returns false.
     *
     * If a veto policy exists, and the specified date is vetoed, then this returns false.
     *
     * If the date is not vetoed, or if empty dates are allowed and the date is null, then this
     * returns true.
     */
    public boolean isDateAllowed(LocalDate date) {
        if (date == null) {
            return allowEmptyDates;
        }
        return (!(InternalUtilities.isDateVetoed(vetoPolicy, date)));
    }

    /**
     * setAllowEmptyDates, This sets whether or not empty dates (null dates) are allowed in the date
     * picker. If this is true, then empty dates will be allowed in the date picker. If this is
     * false, then empty dates will not be allowed.
     *
     * If setting this function to false, it is recommended to call this function -before- setting a
     * veto policy. This sequence will guarantee that the DatePicker.getDate() function will never
     * return a null value, and will guarantee that the setAllowEmptyDates() function will not throw
     * an exception.
     *
     * If the current date is null and you set allowEmptyDates to false, then this function will
     * attempt to initialize the current date to today's date. This function will throw an exception
     * if it fails to initialize a null date. An exception is only possible if a veto policy is set
     * before calling this function, and the veto policy vetoes today's date.
     */
    public void setAllowEmptyDates(boolean allowEmptyDates) {
        this.allowEmptyDates = allowEmptyDates;
        if (parent != null) {
            zApplyAllowEmptyDates();
        }
    }

    /**
     * setAllowKeyboardEditing, This sets whether or not keyboard editing is allowed for this date
     * picker. If this is true, then dates can be entered into the date picker either by using the
     * keyboard or the mouse. If this is false, then dates can only be selected by using the mouse.
     * The default value is true. It is generally recommended to leave this setting as "true".
     *
     * Accessibility Impact: Disallowing the use of the keyboard, and requiring the use of the
     * mouse, could impact the accessibility of your program for disabled persons.
     *
     * Note: This setting does not impact the automatic enforcement of valid or vetoed dates. To
     * learn about the automatic date validation and enforcement for keyboard entered text, see the
     * javadocs for the DatePicker class.
     */
    public void setAllowKeyboardEditing(boolean allowKeyboardEditing) {
        this.allowKeyboardEditing = allowKeyboardEditing;
        if (parent != null) {
            zApplyAllowKeyboardEditing();
        }
    }

    /**
     * setFormatForDatesBeforeCommonEra, This sets the default format that is used to display or
     * parse BCE dates in the date picker. The default value is generated using the locale of the
     * settings instance. A DateTimeFormatter can be created from a pattern string with the
     * convenience function DateUtilities.createFormatterFromPatternString();
     *
     * Note: It is important to use the letter "u" (astronomical year) instead of "y" (year of era)
     * when creating pattern strings for BCE dates. This is because the DatePicker uses ISO 8601,
     * which specifies "Astronomical year numbering". (Additional details: The astronomical year
     * "-1" and "1 BC" are not the same thing. Astronomical years are zero-based, and BC dates are
     * one-based. Astronomical year "0", is the same year as "1 BC", and astronomical year "-1" is
     * the same year as "2 BC", and so forth.)
     *
     * If the date picker has already been constructed, then calling this function will cause
     * immediate validation of the text field text.
     */
    public void setFormatForDatesBeforeCommonEra(DateTimeFormatter formatForDatesBeforeCommonEra) {
        this.formatForDatesBeforeCommonEra = formatForDatesBeforeCommonEra;
        if (parent != null) {
            parent.setTextFieldToValidStateIfNeeded();
        }
    }

    /**
     * setFormatForDatesCommonEra, This sets the default format that is used to display or parse CE
     * dates in the date picker. The default value is generated using the locale of the settings
     * instance. If desired, a DateTimeFormatter can be created from a pattern string by using the
     * convenience function PickerUtilities.createFormatterFromPatternString();
     *
     * If the date picker has already been constructed, then calling this function will cause
     * immediate validation of the text field text.
     */
    public void setFormatForDatesCommonEra(DateTimeFormatter formatForDatesCommonEra) {
        this.formatForDatesCommonEra = formatForDatesCommonEra;
        if (parent != null) {
            parent.setTextFieldToValidStateIfNeeded();
        }
    }

    /**
     * setGapBeforeButtonPixels, This specifies the desired width for the gap between the date
     * picker and the toggle calendar button (in pixels). The default value is null. If this is left
     * at null, then the default value is 3 pixels.
     */
    public void setGapBeforeButtonPixels(Integer gapBeforeButtonPixels) {
        this.gapBeforeButtonPixels = gapBeforeButtonPixels;
        if (parent != null) {
            zApplyGapBeforeButtonPixels();
        }
    }

    /**
     * setInitialDateToToday, This is a convenience function to set the initial date of a DatePicker
     * to today's date.
     */
    public void setInitialDateToToday() {
        initialDate = LocalDate.now();
    }

    /**
     * setParentDatePicker, This sets the parent date picker for these settings. This is only
     * intended to be called from the constructor of the date picker class.
     */
    void setParentDatePicker(DatePicker parentDatePicker) {
        this.parent = parentDatePicker;
    }

    /**
     * setVetoPolicy,
     *
     * This sets a veto policy for the date picker. Note: This function can only be called after the
     * date picker is constructed. If this is called before the DatePicker is constructed, then an
     * exception will be thrown.
     *
     * When a veto policy is supplied, it will be used to determine which dates can or can not be
     * selected in the calendar panel. (Vetoed dates are also not accepted into the date picker text
     * field). See the demo class for an example of constructing a veto policy.
     *
     * By default, there is no veto policy on a date picker. Setting this function to null will
     * clear any veto policy that has been set.
     *
     * It's possible to set a veto policy that vetoes the current "last valid date". This function
     * returns true if the last valid date is allowed by the new veto policy and the date picker
     * settings, or false if the last valid date is vetoed or disallowed. Setting a new veto policy
     * does not modify the last valid date. Is up to the programmer to resolve any potential
     * conflict between a new veto policy, and the last valid date stored in the date picker.
     */
    public boolean setVetoPolicy(DateVetoPolicy vetoPolicy) {
        if (parent == null) {
            throw new RuntimeException("DatePickerSettings.setVetoPolicy(), "
                    + "A veto policy can only be set after constructing the DatePicker.");
        }
        this.vetoPolicy = vetoPolicy;
        return isDateAllowed(parent.getDate());
    }

    /**
     * yApplyNeededSettingsAtDatePickerConstruction, This is called from the date picker constructor
     * to apply various settings in this settings instance to the date picker. Only the settings
     * that are needed at the time of date picker construction, are applied in this function.
     */
    void yApplyNeededSettingsAtDatePickerConstruction() {
        // Run the needed "apply" functions.
        zApplyGapBeforeButtonPixels();
        zApplyAllowKeyboardEditing();
        zApplyInitialDate();
        zApplyAllowEmptyDates();
    }

    /**
     * zApplyAllowEmptyDates, This applies the named setting to the parent component.
     *
     * Notes:
     *
     * The zApplyInitialDate() and zApplyAllowEmptyDates() functions may theoretically be called in
     * any order. However, the order is currently zApplyInitialDate() and zApplyAllowEmptyDates()
     * because that is more intuitive.
     *
     * This cannot throw an exception while the date picker is being constructed, because a veto
     * policy cannot be set until after the date picker is constructed.
     */
    private void zApplyAllowEmptyDates() {
        // Find out if we need to initialize a null date.
        if ((!allowEmptyDates) && (parent.getDate() == null)) {
            // We need to initialize the current date, so find out if today is vetoed.
            LocalDate today = LocalDate.now();
            if (InternalUtilities.isDateVetoed(vetoPolicy, today)) {
                throw new RuntimeException("Exception in DatePickerSettings.zApplyAllowEmptyDates(), "
                        + "Could not initialize a null date to today, because today is vetoed by "
                        + "the veto policy. To prevent this exception, always call "
                        + "setAllowEmptyDates() -before- setting a veto policy.");
            }
            // Initialize the current date.
            parent.setDate(today);
        }
    }

    /**
     * zApplyAllowKeyboardEditing, This applies the named setting to the parent component.
     */
    private void zApplyAllowKeyboardEditing() {
        // Set the editability of the date picker text field.
        parent.dateTextField.setEditable(allowKeyboardEditing);
        // Set the text field border color based on whether the text field is editable.
        Color textFieldBorderColor = (allowKeyboardEditing)
                ? InternalConstants.colorEditableTextFieldBorder
                : InternalConstants.colorNotEditableTextFieldBorder;
        parent.dateTextField.setBorder(new CompoundBorder(
                new MatteBorder(1, 1, 1, 1, textFieldBorderColor), new EmptyBorder(1, 3, 2, 2)));
    }

    /**
     * zApplyGapBeforeButtonPixels, This applies the named setting to the parent component.
     */
    private void zApplyGapBeforeButtonPixels() {
        int gapPixels = (gapBeforeButtonPixels == null) ? 3 : gapBeforeButtonPixels;
        ConstantSize gapSizeObject = new ConstantSize(gapPixels, ConstantSize.PIXEL);
        ColumnSpec columnSpec = ColumnSpec.createGap(gapSizeObject);
        FormLayout layout = ((FormLayout) parent.getLayout());
        layout.setColumnSpec(2, columnSpec);
    }

    /**
     * zApplyInitialDate, This applies the named setting to the parent component.
     *
     * Notes:
     *
     * This does not need to check the parent for null, because this is always and only called while
     * the date picker is being constructed.
     *
     * This does not need to handle (allowEmptyDates == false && initialDate == null). That
     * situation is handled by the zApplyAllowEmptyDates() function.
     *
     * There is no possibility that this can conflict with a veto policy at the time that it is set,
     * because a veto policy cannot be set until after the construction of a date picker.
     *
     * The zApplyInitialDate() and zApplyAllowEmptyDates() functions may theoretically be called in
     * any order. However, the order is currently zApplyInitialDate() and zApplyAllowEmptyDates()
     * because that is more intuitive.
     */
    private void zApplyInitialDate() {
        if (allowEmptyDates == true && initialDate == null) {
            parent.setDate(null);
        }
        if (initialDate != null) {
            parent.setDate(initialDate);
        }
    }

}
