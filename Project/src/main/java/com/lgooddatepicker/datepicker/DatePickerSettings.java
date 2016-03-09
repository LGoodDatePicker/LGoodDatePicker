package com.lgooddatepicker.datepicker;

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
import com.lgooddatepicker.zinternaltools.InternalUtilities;
import com.lgooddatepicker.zinternaltools.ExtraDateStrings;
import com.lgooddatepicker.zinternaltools.TranslationSource;
import java.time.LocalDate;
import javax.swing.border.Border;
import com.lgooddatepicker.optionalusertools.DateVetoPolicy;
import com.lgooddatepicker.optionalusertools.DateHighlightPolicy;

/**
 * DatePickerSettings, This holds all the settings that can be customized in a date picker. All of
 * the fields of this class are public, so that the settings are easier to customize as needed.
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
    public boolean allowEmptyDates = true;

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
    public boolean allowKeyboardEditing = true;

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
    public DateTimeFormatter formatForDatesCommonEra;

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
    public DateTimeFormatter formatForDatesBeforeCommonEra;

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
     * null, then the gap will set to 3 pixels in the date picker constructor.
     */
    public Integer gapBeforeButtonPixels = null;

    /**
     * highlightPolicy, If a highlight policy is supplied, it will be used to determine which dates
     * should be highlighted in the calendar panel. The highlight policy can also supply tooltip
     * text for any highlighted dates. See the demo class for an example of constructing a highlight
     * policy. By default, there is no highlight policy. (The default value is null.)
     */
    public DateHighlightPolicy highlightPolicy = null;

    /**
     * initialDate, This is the date that the date picker will have when it is created. This can be
     * set to any date, or it can be set to null. If you would like the starting date be today, then
     * you can use the convenience function DatePickerSettings.setInitialDateToToday(). The default
     * value for initialDate is null, which represents an empty date.
     *
     * If allowEmptyDates is false, then a null initialDate value will be ignored. More
     * specifically: When a DatePicker is constructed, if allowEmptyDates is false and initialDate
     * is null, then the initialDate value will be set to today's date.
     */
    

    /**
     * initialDate, This is the date that the date picker will have when it is created. This can be
     * set to any date, or it can be set to null. The default value for initialDate is null, which
     * represents an empty date.
     *
     * If allowEmptyDates is false, then a null initialDate will be ignored. More specifically:
     * When a DatePicker is constructed, if allowEmptyDates is false and initialDate is null, then
     * the initialDate will be set to a default value. (The default value is today's date.)
     *
     * If a DateVetoPolicy exists, and the supplied date is vetoed, then the date will be entered
     * into the text field (and displayed with the "fontVetoedDate"), but it will not be committed
     * to the "last valid date".
     *
     * See DatePicker.setDate() to read about the automatic validation of set dates.
     */
    public LocalDate initialDate = null;

    /**
     * pickerLocale, This holds the locale instance that indicates the user's language and culture.
     * The locale is used in translating text and determining default behaviors, for the date picker
     * and the calendar panel. The locale is supplied in the DatePickerSettings constructor because
     * the default values for most of the other settings depend on the locale. The default value for
     * the locale is supplied from Locale.getDefault(), from Java and from the operating system.
     */
    public Locale pickerLocale;

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
     * vetoPolicy, If a veto policy is supplied, it will be used to determine which dates cannot be
     * selected in the calendar panel. (Vetoed dates are also not accepted into the date picker text
     * field). See the demo class for an example of constructing a veto policy. By default, there is
     * no veto policy. (The default value is null.)
     */
    public DateVetoPolicy vetoPolicy = null;

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
        this.pickerLocale = pickerLocale;

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
     * setInitialDateToToday, This is a convenience function to set the initial date of a DatePicker
     * to today's date.
     */
    public void setInitialDateToToday() {
        initialDate = LocalDate.now();
    }
}
