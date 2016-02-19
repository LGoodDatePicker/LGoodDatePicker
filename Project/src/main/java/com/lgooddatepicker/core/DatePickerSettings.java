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
import com.lgooddatepicker.policies.HighlightPolicy;
import com.lgooddatepicker.policies.VetoPolicy;
import com.lgooddatepicker.utilities.DatePickerUtilities;
import com.lgooddatepicker.utilities.ExtraDateFormats;
import com.lgooddatepicker.utilities.TranslationSource;

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
     * allFormatStyles, This is a constant list of all the FormatStyle values that are available.
     * This is used to generate the default formats for the display and parsing of dates, using the
     * date picker settings locale.
     */
    private final FormatStyle[] allFormatStyles = new FormatStyle[]{
        FormatStyle.SHORT, FormatStyle.MEDIUM, FormatStyle.LONG, FormatStyle.FULL};

    /**
     * backgroundColorHighlighted, This is the calendar background color for dates which are
     * highlighted by a highlight policy. The default color is green.
     */
    public Color backgroundColorHighlighted;

    /**
     * backgroundColorVetoed, This is the calendar background color for dates which are vetoed by a
     * veto policy. The default color is light gray.
     */
    public Color backgroundColorVetoed;

    /**
     * clearTranslation, This holds the text of the calendars "Clear" button, as translated to the
     * current language. It is not expected that this variable will need to be changed by the
     * programmer. If you wish to supply a new translation for the date picker, it would generally
     * be better to add (or edit) your translation in the "TranslationResources.properties" file.
     * The default value is generated using the locale of the settings instance, by retrieving the
     * translated text for the current language from the properties file.
     */
    public String clearTranslation;

    /**
     * colorValidDate, This is the text field text color for invalid dates. The default color is
     * red.
     */
    public Color colorInvalidDate;

    /**
     * colorValidDate, This is the text field text color for valid dates. The default color is
     * black.
     */
    public Color colorValidDate;

    /**
     * colorVetoedDate, This is the text field text color for vetoed dates. The default color is
     * black. Although the default fontVetoedDate setting will draw a line (strikethrough) vetoed
     * dates.
     */
    public Color colorVetoedDate;

    /**
     * displayFormatterAD, This holds the default format that is used to display or parse AD dates
     * in the date picker. The default value is generated using the locale of the settings instance.
     */
    public DateTimeFormatter displayFormatterAD;

    /**
     * displayFormatterBC, This holds the default format that is used to display or parse BC dates
     * in the date picker. The default value is generated using the locale of the settings instance.
     */
    public DateTimeFormatter displayFormatterBC;

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
     * highlightPolicy, If a highlight policy is supplied, it will be used to determine which dates
     * should be highlighted in the calendar panel. The highlight policy can also supply tooltip
     * text for any highlighted dates. See the demo class for an example of constructing a highlight
     * policy. By default, there is no highlight policy. (The default value is null.)
     */
    public HighlightPolicy highlightPolicy;

    /**
     * parsingFormatters, This holds a list of formatters that are used to try to parse dates that
     * are typed by the user. The parsingFormatters are attempted to be used in the order that they
     * appear in this list. Note that the displayFormatterAD and displayFormatterBC are always tried
     * (in that order) before any other parsing formatters are used. The default values for the
     * parsingFormatters are generated using the locale of the settings instance.
     */
    public ArrayList<DateTimeFormatter> parsingFormatters;

    /**
     * pickerLocale, This holds the locale instance that indicates the user's language and culture.
     * The locale is used in translating text and determining default behaviors, for the date picker
     * and the calendar panel. The locale is supplied in the DatePickerSettings constructor because
     * the default values for most of the other settings depend on the locale. The default value for
     * the locale is supplied from Locale.getDefault(), from Java and from the operating system.
     */
    public Locale pickerLocale;

    /**
     * todayFormatter, This formatter is used to format today's date into a date string, which is
     * displayed on the today button. The default value is generated using the locale of the
     * settings instance.
     */
    public DateTimeFormatter todayFormatter;

    /**
     * todayTranslation, This holds the text of the calendars "Today" button, as translated to the
     * current language. It is not expected that this variable will need to be changed by the
     * programmer. If you wish to supply a new translation for the date picker, it would generally
     * be better to add (or edit) your translation in the "TranslationResources.properties" file.
     * The default value is generated from the locale of the settings instance, by retrieving the
     * translated text for the current language from the properties file.
     */
    public String todayTranslation;

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
        this.pickerLocale = pickerLocale;

        // Set the default translations for the locale.
        todayTranslation = TranslationSource.getTranslation(pickerLocale, "today", "Today");
        clearTranslation = TranslationSource.getTranslation(pickerLocale, "clear", "Clear");

        // Create default formatters for displaying the today button, and AD and BC dates, in
        // the specified locale.
        todayFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM).withLocale(pickerLocale);
        displayFormatterAD = DatePickerUtilities.getDefaultDisplayFormatterAD(pickerLocale);
        displayFormatterBC = DatePickerUtilities.getDefaultDisplayFormatterBC(pickerLocale);

        // Initialize the other fields.
        highlightPolicy = null;
        parsingFormatters = new ArrayList<>();
        vetoPolicy = null;
        firstDayOfWeek = WeekFields.of(Locale.getDefault()).getFirstDayOfWeek();

        // Create a set of default parsing formatters for the specified locale.
        DateTimeFormatter parseFormat;
        for (int i = 0; i < allFormatStyles.length; ++i) {
            parseFormat = new DateTimeFormatterBuilder().parseLenient().parseCaseInsensitive().
                    appendLocalized(allFormatStyles[i], null).toFormatter(pickerLocale);
            parsingFormatters.add(parseFormat);
        }
        // Get any common extra parsing formats for the specified locale, and append them to
        // the list of parsingFormatters.
        ArrayList<DateTimeFormatter> extraFormatters = 
                ExtraDateFormats.getExtraParsingFormatsForLocale(pickerLocale);
        parsingFormatters.addAll(extraFormatters);

        // Generate the default fonts and text colors for the text field text.
        colorValidDate = Color.black;
        fontValidDate = new JTextField().getFont();
        colorInvalidDate = Color.red;
        fontInvalidDate = new JTextField().getFont();
        colorVetoedDate = Color.BLACK;
        fontVetoedDate = new JTextField().getFont();
        Map attributes = fontVetoedDate.getAttributes();
        attributes.put(TextAttribute.STRIKETHROUGH, TextAttribute.STRIKETHROUGH_ON);
        fontVetoedDate = new Font(attributes);

        // Generate default colors for highlighted and vetoed dates.
        backgroundColorHighlighted = Color.green;
        backgroundColorVetoed = Color.lightGray;

    }
}
