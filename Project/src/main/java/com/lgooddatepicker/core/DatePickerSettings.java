package com.lgooddatepicker.core;

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
import com.lgooddatepicker.optionalusertools.HighlightPolicy;
import com.lgooddatepicker.optionalusertools.VetoPolicy;
import com.lgooddatepicker.support.DatePickerInternalUtilities;
import com.lgooddatepicker.support.ExtraDateStrings;
import com.lgooddatepicker.support.TranslationSource;
import java.time.LocalDate;

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
    public boolean allowEmptyDates;

    /**
     * colorBackgroundHighlighted, This is the calendar background color for dates which are
     * highlighted by a highlight policy. The default color is green.
     */
    public Color colorBackgroundHighlighted;

    /**
     * colorBackgroundVetoed, This is the calendar background color for dates which are vetoed by a
     * veto policy. The default color is light gray.
     */
    public Color colorBackgroundVetoed;

    /**
     * colorBackgroundWeekdayLabels, This is the calendar background color for the weekday labels.
     * The default color is a medium sky blue.
     */
    public Color colorBackgroundWeekdayLabels;

    /**
     * colorTextInvalidDate, This is the text field text color for invalid dates. The default color
     * is red.
     */
    public Color colorTextInvalidDate;

    /**
     * colorTextValidDate, This is the text field text color for valid dates. The default color is
     * black.
     */
    public Color colorTextValidDate;

    /**
     * colorTextVetoedDate, This is the text field text color for vetoed dates. The default color is
     * black. Note: The default fontVetoedDate setting will draw a line (strikethrough) vetoed
     * dates.
     */
    public Color colorTextVetoedDate;

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
     * out the veto date. (Has a strikethrough font attribute.)
     */
    public Font fontVetoedDate;

    /**
     * formatDatesCommonEra, This holds the default format that is used to display or parse CE dates
     * in the date picker. The default value is generated using the locale of the settings instance.
     */
    public DateTimeFormatter formatDatesCommonEra;

    /**
     * formatDatesBeforeCommonEra, This holds the default format that is used to display or parse
     * BCE dates in the date picker. The default value is generated using the locale of the settings
     * instance.
     */
    public DateTimeFormatter formatDatesBeforeCommonEra;

    /**
     * formatTodayButton, This format is used to format today's date into a date string, which is
     * displayed on the today button. The default value is generated using the locale of the
     * settings instance.
     */
    public DateTimeFormatter formatTodayButton;

    /**
     * formatsForParsing, This holds a list of formats that are used to attempt to parse dates that
     * are typed by the user. When parsing a date, these formats are tried in the order that they
     * appear in this list. Note that the formatDatesCommonEra and formatDatesBeforeCommonEra are
     * always tried (in that order) before any other parsing formats. The default values for the
     * formatsForParsing are generated using the pickerLocale, using the enum constants in
     * java.time.format.FormatStyle.
     */
    public ArrayList<DateTimeFormatter> formatsForParsing;

    /**
     * highlightPolicy, If a highlight policy is supplied, it will be used to determine which dates
     * should be highlighted in the calendar panel. The highlight policy can also supply tooltip
     * text for any highlighted dates. See the demo class for an example of constructing a highlight
     * policy. By default, there is no highlight policy. (The default value is null.)
     */
    public HighlightPolicy highlightPolicy;

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
    public LocalDate initialDate;

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
    public VetoPolicy vetoPolicy;

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
        // Save the date picker locale.
        this.pickerLocale = pickerLocale;

        // Set the default value for allowing empty dates.
        allowEmptyDates = true;

        // Set the default value for the initial date.
        // Note that if allowEmptyDates is false, and initialDate is still null when a date picker
        // is constructed, then this would be set to today's date in the DatePicker constructor.
        initialDate = null;

        // Set the default translations for the locale.
        translationToday = TranslationSource.getTranslation(pickerLocale, "today", "Today");
        translationClear = TranslationSource.getTranslation(pickerLocale, "clear", "Clear");

        // Set the default month names for the current locale.
        translationArrayMonthNames = ExtraDateStrings.getDefaultMonthNamesForLocale(pickerLocale);

        // Create default formatters for displaying the today button, and AD and BC dates, in
        // the specified locale.
        formatTodayButton = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM).withLocale(pickerLocale);
        formatDatesCommonEra = DatePickerInternalUtilities.generateDefaultFormatterCE(pickerLocale);
        formatDatesBeforeCommonEra = DatePickerInternalUtilities.generateDefaultFormatterBCE(pickerLocale);

        // Set the minimum height, minimum width, and extra pixels for the date panel.
        sizeDatePanelMinimumHeight = (6 * 18);
        sizeDatePanelMinimumWidth = (7 * 30);
        sizeDatePanelPixelsExtraHeight = 2;
        sizeDatePanelPixelsExtraWidth = 2;

        // Initialize the other fields.
        highlightPolicy = null;
        formatsForParsing = new ArrayList<>();
        vetoPolicy = null;
        firstDayOfWeek = WeekFields.of(Locale.getDefault()).getFirstDayOfWeek();

        // Create an array of all the FormatStyle enum values, from short to long.
        FormatStyle[] allFormatStyles = new FormatStyle[]{
            FormatStyle.SHORT, FormatStyle.MEDIUM, FormatStyle.LONG, FormatStyle.FULL};

        // Create a set of default parsing formatters for the specified locale.
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

        // Generate the default fonts and text colors.
        colorBackgroundWeekdayLabels = new Color(184, 207, 229);
        colorTextValidDate = Color.black;
        fontValidDate = new JTextField().getFont();
        colorTextInvalidDate = Color.red;
        fontInvalidDate = new JTextField().getFont();
        colorTextVetoedDate = Color.BLACK;
        fontVetoedDate = new JTextField().getFont();
        Map attributes = fontVetoedDate.getAttributes();
        attributes.put(TextAttribute.STRIKETHROUGH, TextAttribute.STRIKETHROUGH_ON);
        fontVetoedDate = new Font(attributes);

        // Generate default colors for highlighted and vetoed dates.
        colorBackgroundHighlighted = Color.green;
        colorBackgroundVetoed = Color.lightGray;

    }

    /**
     * setInitialDateToToday, This is a convenience function to set the initial date of a DatePicker
     * to today's date.
     */
    public void setInitialDateToToday() {
        initialDate = LocalDate.now();
    }
}
