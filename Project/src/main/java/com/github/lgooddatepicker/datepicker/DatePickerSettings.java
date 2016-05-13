package com.github.lgooddatepicker.datepicker;

import com.github.lgooddatepicker.calendarpanel.CalendarPanel;
import com.github.lgooddatepicker.optionalusertools.CalendarBorderProperties;
import com.privatejgoodies.forms.layout.ColumnSpec;
import com.privatejgoodies.forms.layout.ConstantSize;
import com.privatejgoodies.forms.layout.FormLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.font.TextAttribute;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Map;
import javax.swing.JTextField;
import com.github.lgooddatepicker.zinternaltools.InternalUtilities;
import com.github.lgooddatepicker.zinternaltools.ExtraDateStrings;
import com.github.lgooddatepicker.zinternaltools.TranslationSource;
import javax.swing.border.Border;
import com.github.lgooddatepicker.optionalusertools.DateVetoPolicy;
import com.github.lgooddatepicker.optionalusertools.DateHighlightPolicy;
import com.github.lgooddatepicker.optionalusertools.PickerUtilities;
import com.github.lgooddatepicker.zinternaltools.InternalConstants;
import java.awt.Point;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.FormatStyle;
import java.time.temporal.WeekFields;

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
     * borderCalendarPopup, This is the (outside) border for the calendar popup window. If this is
     * null, a default border will be provided by the CustomPopup class. The default value is null.
     */
    private Border borderCalendarPopup = null;

    /**
     * borderPropertiesList, This contains the list of border properties objects that specifies the
     * colors and thicknesses of the borders in the CalendarPanel. By default, a default set of
     * border properties is stored in the borderPropertiesList.
     *
     * Passing in "null" when setting this property will restore the default border properties. If
     * you don't want the default border properties but wish to truly "clear" the border properties
     * list, you could pass in an empty ArrayList.
     *
     * To modify the calendar borders (including individual border colors, thickness, or
     * visibility), you can supply your own ArrayList of border properties. Border properties
     * objects are applied in the order that they appear in the ArrayList. To learn how to set the
     * CalendarBorderProperties objects, see the CalendarBorderProperties class javadocs.
     *
     * The default border properties have a one pixel blue border around the date labels, and two
     * vertical light blue borders near the weekday labels (which match the color of the weekday
     * labels). The outer top horizontal border and the outer left vertical border are invisible by
     * default.
     *
     * The default border properties also include borders above and below the "week numbers" column
     * which match the color of the week numbers labels. (However, these borders are only displayed
     * when the week numbers are displayed.)
     *
     * Whenever the border properties list is applied, the borders are first "cleared" by setting
     * all borders to the following settings: Invisible, and black color. This is true of both the
     * default border properties and any programmer supplied border properties.
     *
     * Note: If weekNumbersDisplayed is set to false, then the borders located in the week number
     * columns (Columns 1 and 2) will always be hidden.
     */
    private ArrayList<CalendarBorderProperties> borderPropertiesList;

    /**
     * colorBackgroundCalendarPanel, This is the background color for the entire calendar panel. The
     * default color is a very light gray.
     */
    private Color colorBackgroundCalendarPanel = new Color(240, 240, 240);

    /**
     * colorBackgroundHighlightedDates, This is the calendar background color for dates which are
     * highlighted by a highlight policy. The default color is green.
     */
    private Color colorBackgroundHighlightedDates = Color.green;

    /**
     * colorBackgroundMonthAndYear, This is the background color used by the month and year buttons.
     * The default color is a very light gray.
     */
    private Color colorBackgroundMonthAndYear = new Color(240, 240, 240);

    /**
     * colorBackgroundNavigateYearMonthButtons, This is the background color used by the buttons for
     * navigating "previous year", "previous month", "next year", "next month". The default value is
     * the default java button background.
     */
    private Color colorBackgroundNavigateYearMonthButtons = null;

    /**
     * colorBackgroundTodayAndClear, This is the background color used by the "Today" and "Clear"
     * buttons. The default color is a very light gray.
     */
    private Color colorBackgroundTodayAndClear = new Color(240, 240, 240);

    /**
     * colorBackgroundVetoedDates, This is the calendar background color for dates which are vetoed
     * by a veto policy. The default color is light gray.
     */
    private Color colorBackgroundVetoedDates = Color.lightGray;

    /**
     * colorBackgroundWeekdayLabels, This is the calendar background color for the weekday labels.
     * The default color is a medium sky blue.
     */
    private Color colorBackgroundWeekdayLabels = new Color(184, 207, 229);

    /**
     * colorBackgroundWeekNumberLabels, This is the calendar background color for the week number
     * labels. The default color is a medium sky blue.
     */
    private Color colorBackgroundWeekNumberLabels = new Color(184, 207, 229);

    /**
     * colorBackgroundTopLeftLabel, This is the calendar background color for the label that is used
     * to fill the top left corner of the calendar whenever the week numbers are displayed. The
     * default color is a medium sky blue.
     */
    private Color colorBackgroundTopLeftLabel = new Color(184, 207, 229);

    /**
     * colorTextInvalidDate, This is the text field text color for invalid dates. The default color
     * is red.
     */
    private Color colorTextInvalidDate = Color.red;

    /**
     * colorTextValidDate, This is the text field text color for valid dates. The default color is
     * black.
     */
    private Color colorTextValidDate = Color.black;

    /**
     * colorTextVetoedDate, This is the text field text color for vetoed dates. The default color is
     * black. Note: The default fontVetoedDate setting will draw a line (strikethrough) vetoed
     * dates.
     */
    private Color colorTextVetoedDate = Color.black;

    /**
     * firstDayOfWeek, This holds the day of the week that will be displayed in the far left column
     * of the CalendarPanel, as the "first day of the week". The default value is generated using
     * the locale of the settings instance.
     *
     * By default, If weekNumbersDisplayed is set to true, then the first day of the week will not
     * match the setting. (weekNumbersDisplayed is false by default.) For additional details, see
     * the javadocs for the following settings: "weekNumbersDisplayed", and
     * "weekNumbersForceFirstDayOfWeekToMatch".
     */
    private DayOfWeek firstDayOfWeek;

    /**
     * fontInvalidDate, This is the text field text font for invalid dates. The default font is a
     * normal undecorated font. (Note: The color for invalid dates defaults to Color.red. See also:
     * "colorTextInvalidDate".)
     */
    private Font fontInvalidDate;

    /**
     * fontValidDate, This is the text field text font for valid dates. The default font is a normal
     * undecorated font.
     */
    private Font fontValidDate;

    /**
     * fontVetoedDate, This is the text field text font for vetoed dates. The default font crosses
     * out the vetoed date. (Has a strikethrough font attribute.)
     */
    private Font fontVetoedDate;

    /**
     * formatForDatesCommonEra, This holds the default format that is used to display or parse CE
     * dates in the date picker. The default value is generated using the locale of the settings
     * instance.
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
    private DateTimeFormatter formatForTodayButton;

    /**
     * formatsForParsing, This holds a list of formats that are used to attempt to parse dates that
     * are typed by the user. When parsing a date, these formats are tried in the order that they
     * appear in this list. Note that the formatForDatesCommonEra and formatForDatesBeforeCommonEra
     * are always tried (in that order) before any other parsing formats. The default values for the
     * formatsForParsing are generated using the pickerLocale, using the enum constants in
     * java.time.format.FormatStyle.
     */
    private ArrayList<DateTimeFormatter> formatsForParsing;

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
    private DateHighlightPolicy highlightPolicy = null;

    /**
     * initialDate, This is the date that the parent DatePicker or parent independent CalendarPanel
     * will have when it is created. This can be set to any date, or it can be set to null. The
     * default value for initialDate is null, which represents an empty date. This setting will only
     * have an effect if it is set before the parent component is constructed.
     *
     * For an independent CalendarPanel, this setting will also set the displayed YearMonth of the
     * CalendarPanel to match the initial date. (See also: CalendarPanel.setSelectedDate() and
     * CalendarPanel.setDisplayedYearMonth().)
     *
     * If allowEmptyDates is false, then a null initialDate will be ignored. More specifically: When
     * the parent component is constructed, if allowEmptyDates is false and initialDate is null,
     * then the initialDate will be set to a default value. (The default value is today's date.)
     *
     * Note: This date can not be vetoed, because a veto policy can not be set until after the
     * parent component is constructed.
     */
    private LocalDate initialDate = null;

    /**
     * locale, This holds the picker locale instance that indicates the user's language and culture.
     * The locale is used in translating text and determining default behaviors, for the date picker
     * and the calendar panel. The default values for many of the other settings depend on the
     * locale. The locale can only be set in the settings constructor. The default value for the
     * locale is supplied from Locale.getDefault(), from Java and from the operating system.
     */
    private Locale locale;

    /**
     * parentCalendarPanel, This holds a reference to any parent -independent- calendar panel that
     * is associated with these settings. This variable is only intended to be set from the calendar
     * panel constructor.
     *
     * Note that for any single DatePickerSettings instance, only one of the parentDatePicker and
     * parentCalendarPanel variables will ever contain a parent instance. The other variable will
     * always be null. (A date picker settings instance can be used as the settings for a DatePicker
     * or for an independent CalendarPanel, but not for both at the same time.)
     *
     * This will be null until the CalendarPanel is constructed (using this settings instance).
     */
    private CalendarPanel parentCalendarPanel;

    /**
     * parentDatePicker, This holds a reference to any parent date picker that is associated with
     * these settings. This variable is only intended to be set from the date picker constructor.
     *
     * Note that for any single DatePickerSettings instance, only one of the parentDatePicker and
     * parentCalendarPanel variables will ever contain a parent instance. The other variable will
     * always be null. (A date picker settings instance can be used as the settings for a DatePicker
     * or for an independent CalendarPanel, but not for both at the same time.)
     *
     * This will be null until the DatePicker is constructed (using this settings instance).
     */
    private DatePicker parentDatePicker;

    /**
     * sizeDatePanelMinimumHeight, This specifies the minimum height, in pixels, of the date label
     * area. The date label area is the part of the calendar panel that holds (only) the date
     * labels. The remainder of the calendar panel is automatically resized to encompass the date
     * label area. The default value for this variable is (6 * 18). If the default value is
     * modified, the programmer may also wish to adjust sizeDatePanelPixelsExtraHeight.
     */
    private int sizeDatePanelMinimumHeight;

    /**
     * sizeDatePanelMinimumWidth, This specifies the minimum width, in pixels, of the date label
     * area. The date label area is the part of the calendar panel that holds (only) the date
     * labels. The remainder of the calendar panel is automatically resized to encompass the date
     * label area. The default value for this variable is (7 * 30). If the default value is
     * modified, the programmer may also wish to adjust sizeDatePanelPixelsExtraWidth.
     */
    private int sizeDatePanelMinimumWidth;

    /**
     * sizeTextFieldMinimumWidth, This specifies the minimum width, in pixels, of the DatePicker
     * text field. (The text field is located to the left of the date picker "popup calendar"
     * button, and displays the currently selected date.)
     *
     * The default value for this setting is null. When this is set to null, a default width for the
     * date picker text field will be automatically calculated and applied to fit "the largest
     * possible date" (with a four digit year) that can be displayed with the current date picker
     * settings. The settings that are used to calculate the default text field width include the
     * locale (the language), the fontValidDate, and the formatForDatesCommonEra.
     *
     * See also: "sizeTextFieldMinimumWidthDefaultOverride".
     */
    private Integer sizeTextFieldMinimumWidth = null;

    /**
     * sizeTextFieldMinimumWidthDefaultOverride, This specifies how the date picker should choose
     * the appropriate minimum width for the date picker text field. (As described below.)
     *
     * If this is true, then the applied minimum width will be the largest of either the default, or
     * any programmer supplied, minimum widths.
     *
     * If this is false, then any programmer supplied minimum width will always override the default
     * minimum width. (Even if the programmer supplied width is too small to fit the dates that can
     * be displayed in the DatePicker).
     *
     * The default value for this setting is true. This setting only has an effect if
     * (sizeTextFieldMinimumWidth != null).
     *
     * See also: "sizeTextFieldMinimumWidth".
     */
    private boolean sizeTextFieldMinimumWidthDefaultOverride = true;

    /**
     * translationArrayLongMonthNames, This holds an array of month names to use for the calendar
     * panel header, as translated to the current language. It is not expected that this variable
     * will need to be changed by the programmer. The default values are generated using the locale
     * of the settings instance, by retrieving the translated text for the current language from the
     * class called java.text.DateFormatSymbols. This array is indexed with January = 0. The
     * Calendar class month name constants can also be used for indexing. (Calendar.JANUARY, etc).
     *
     * Expected array contents: This array should never be set to null, and the array should always
     * have a length of 12. Each element should always contain a string that is not null and not
     * empty.
     */
    private String[] translationArrayStandaloneLongMonthNames;

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
    private String[] translationArrayStandaloneShortMonthNames;

    /**
     * translationClear, This holds the text of the calendars "Clear" button, as translated to the
     * current language. It is not expected that this variable will need to be changed by the
     * programmer. If you wish to supply a new translation for the date picker, it would generally
     * be better to add (or edit) your translation in the "TranslationResources.properties" file.
     * The default value is generated using the locale of the settings instance, by retrieving the
     * translated text for the current language from the properties file.
     */
    private String translationClear;

    /**
     * translationToday, This holds the text of the calendars "Today" button, as translated to the
     * current language. It is not expected that this variable will need to be changed by the
     * programmer. If you wish to supply a new translation for the date picker, it would generally
     * be better to add (or edit) your translation in the "TranslationResources.properties" file.
     * The default value is generated from the locale of the settings instance, by retrieving the
     * translated text for the current language from the properties file.
     */
    private String translationToday;

    /**
     * vetoPolicy, If a veto policy is supplied, it will be used to determine which dates can and
     * cannot be selected in the calendar panel. (Vetoed dates are also not accepted into the date
     * picker text field). By default, there is no veto policy. (The default value is null.) See the
     * DateVetoPolicy Javadocs for details regarding when a veto policy is enforced. See the demo
     * class for an example of constructing a veto policy.
     */
    private DateVetoPolicy vetoPolicy = null;

    /**
     * weekNumbersRules, This contains the week number rules that will be used to show the week
     * numbers (the weeks of the year), whenever the week numbers are displayed on this calendar.
     *
     * The definitions for the first day of the week, and for the minimum number of days that
     * constitutes the first week of the year, varies between locales. The Java time WeekFields
     * class specifies those rules.
     *
     * If you set this setting to null, then the weekNumberRules will be set to match the
     * DatePickerSettings locale. By default, the week number rules will match the
     * DatePickerSettings locale.
     *
     * If you would like the week number rules to be the same for all calendars regardless of the
     * locale, then you should consider setting the weekNumberRules to be one of these two commonly
     * used rule sets: WeekFields.ISO or WeekFields.SUNDAY_START.
     */
    private WeekFields weekNumberRules;

    /**
     * weekNumbersDisplayed, This specifies whether or not numbers for the "week of the year" should
     * be displayed on the calendar panel. The week numbers will be shown if this is true, or they
     * will not be shown if this is false. This is false by default.
     *
     * The week numbers are locale sensitive, because the definition of the first day of the week,
     * and the definition for the minimum number of days in the first week of the year, varies
     * between locales.
     *
     * By default, when "weekNumbersDisplayed" is true, the first day of the week used by the
     * calendar will always match the weekNumberRules definition for first day of the week. This
     * behavior can be changed by modifying the setting "weekNumbersForceFirstDayOfWeekToMatch".
     *
     * Note: When weekNumbersDisplayed is set to false, then the borders located in the week number
     * columns (Columns 1 and 2) will always be set to be hidden.
     */
    private boolean weekNumbersDisplayed = false;

    /**
     * weekNumbersForceFirstDayOfWeekToMatch, This setting determines if the first day of the week
     * should be forced to match the weekNumberRules for the first day of the week, whenever
     * weekNumbersDisplayed is true.
     *
     * If weekNumbersDisplayed is false, then the week numbers will not be displayed, and the
     * setting will not have any effect.
     *
     * If this setting is true and weekNumbersDisplayed is true, then the first day of the week that
     * is shown on the calendar will always match the weekNumberRules definition for "first day of
     * the week". (And all the dates in each row will always belong to the displayed week number.)
     *
     * If this setting is false and weekNumbersDisplayed is true, then the week numbers will be
     * determined by a "majority rules" system, which is described below. (In this system, up to 3
     * dates in each row may not belong to the displayed week number.)
     *
     * Description of the "majority rules" system: If this setting is false, and if the first day of
     * the week is set to be different than the first day of the week defined by the
     * weekNumberRules, then the dates in a single row of the calendar may not all belong to the
     * same week number. In this circumstance, the displayed week number for each row will be
     * determined by a "majority rules" system. More specifically, the calendar will count the
     * number of days in each row that belong to each week number. Four or more days in a particular
     * row will always match a particular week number, and that is the week number that will be
     * displayed next to the row.
     */
    private boolean weekNumbersForceFirstDayOfWeekToMatch = true;

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

        // Set the default week number rules.
        weekNumberRules = WeekFields.of(locale);

        // Set the default translations for the locale.
        translationToday = TranslationSource.getTranslation(pickerLocale, "today", "Today");
        translationClear = TranslationSource.getTranslation(pickerLocale, "clear", "Clear");

        // Set the default standalone month names for the current locale.
        translationArrayStandaloneLongMonthNames
                = ExtraDateStrings.getDefaultStandaloneLongMonthNamesForLocale(pickerLocale);
        translationArrayStandaloneShortMonthNames
                = ExtraDateStrings.getDefaultStandaloneShortMonthNamesForLocale(pickerLocale);

        // Create default formatters for displaying the today button, and AD and BC dates, in
        // the specified locale.
        formatForTodayButton = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM).withLocale(pickerLocale);
        formatForDatesCommonEra = InternalUtilities.generateDefaultFormatterCE(pickerLocale);
        formatForDatesBeforeCommonEra = InternalUtilities.generateDefaultFormatterBCE(pickerLocale);

        // Create an array of all the FormatStyle enum values, from short to long.
        FormatStyle[] allFormatStyles = new FormatStyle[]{
            FormatStyle.SHORT, FormatStyle.MEDIUM, FormatStyle.LONG, FormatStyle.FULL};

        // Create a set of default parsing formatters for the specified locale.
        formatsForParsing = new ArrayList<DateTimeFormatter>();
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

        // Set the minimum height, minimum width, for the date panel.
        sizeDatePanelMinimumWidth = (7 * 31);
        sizeDatePanelMinimumHeight = (7 * 19);

        // Initialize the first day of the week.
        firstDayOfWeek = WeekFields.of(Locale.getDefault()).getFirstDayOfWeek();

        // Generate the default fonts and text colors.
        fontValidDate = new JTextField().getFont();
        fontInvalidDate = new JTextField().getFont();
        fontVetoedDate = new JTextField().getFont();
        Map attributes = fontVetoedDate.getAttributes();
        attributes.put(TextAttribute.STRIKETHROUGH, TextAttribute.STRIKETHROUGH_ON);
        fontVetoedDate = new Font(attributes);

        // Set the default border properties.
        // (Setting this to null will create and save, (and apply when needed) a default set of
        // border properties. I don't think there would be any parent to apply them to when called 
        // from the DatePickerSettings constructor though.)
        setBorderPropertiesList(null);
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
     * getBorderCalendarPopup, Returns the value of this setting. See the "set" function for setting
     * information.
     */
    public Border getBorderCalendarPopup() {
        return borderCalendarPopup;
    }

    /**
     * getBorderPropertiesList, Returns the value of this setting. See the "set" function for
     * setting information.
     */
    public ArrayList<CalendarBorderProperties> getBorderPropertiesList() {
        return borderPropertiesList;
    }

    /**
     * getColorBackgroundCalendarPanel, Returns the value of this setting. See the "set" function
     * for setting information.
     */
    public Color getColorBackgroundCalendarPanel() {
        return colorBackgroundCalendarPanel;
    }

    /**
     * getColorBackgroundHighlightedDates, Returns the value of this setting. See the "set" function
     * for setting information.
     */
    public Color getColorBackgroundHighlightedDates() {
        return colorBackgroundHighlightedDates;
    }

    /**
     * getColorBackgroundMonthAndYear, Returns the value of this setting. See the "set" function for
     * setting information.
     */
    public Color getColorBackgroundMonthAndYear() {
        return colorBackgroundMonthAndYear;
    }

    /**
     * getColorBackgroundNavigateYearMonthButtons, Returns the value of this setting. See the "set"
     * function for setting information.
     */
    public Color getColorBackgroundNavigateYearMonthButtons() {
        return colorBackgroundNavigateYearMonthButtons;
    }

    /**
     * getColorBackgroundTodayAndClear, Returns the value of this setting. See the "set" function
     * for setting information.
     */
    public Color getColorBackgroundTodayAndClear() {
        return colorBackgroundTodayAndClear;
    }

    /**
     * getColorBackgroundTopLeftLabel, Returns the value of this setting. See the "set" function for
     * setting information.
     */
    public Color getColorBackgroundTopLeftLabel() {
        return colorBackgroundTopLeftLabel;
    }

    /**
     * getColorBackgroundVetoedDates, Returns the value of this setting. See the "set" function for
     * setting information.
     */
    public Color getColorBackgroundVetoedDates() {
        return colorBackgroundVetoedDates;
    }

    /**
     * getColorBackgroundWeekNumberLabels, Returns the value of this setting. See the "set" function
     * for setting information.
     */
    public Color getColorBackgroundWeekNumberLabels() {
        return colorBackgroundWeekNumberLabels;
    }

    /**
     * getColorBackgroundWeekdayLabels, Returns the value of this setting. See the "set" function
     * for setting information.
     */
    public Color getColorBackgroundWeekdayLabels() {
        return colorBackgroundWeekdayLabels;
    }

    /**
     * getColorTextInvalidDate, Returns the value of this setting. See the "set" function for
     * setting information.
     */
    public Color getColorTextInvalidDate() {
        return colorTextInvalidDate;
    }

    /**
     * getColorTextValidDate, Returns the value of this setting. See the "set" function for setting
     * information.
     */
    public Color getColorTextValidDate() {
        return colorTextValidDate;
    }

    /**
     * getColorTextVetoedDate, Returns the value of this setting. See the "set" function for setting
     * information.
     */
    public Color getColorTextVetoedDate() {
        return colorTextVetoedDate;
    }

    /**
     * getFirstDayOfWeekDisplayedOnCalendar, Returns the first day of the week as it will be
     * displayed on the calendar. Note that this setting may or may not match the first day of the
     * week setting. See also: "weekNumbersDisplayed", and "weekNumbersForceFirstDayOfWeekToMatch".
     */
    public DayOfWeek getFirstDayOfWeekDisplayedOnCalendar() {
        if (weekNumbersDisplayed && weekNumbersForceFirstDayOfWeekToMatch
                && (weekNumberRules != null)) {
            return weekNumberRules.getFirstDayOfWeek();
        }
        return firstDayOfWeek;
    }

    /**
     * getFirstDayOfWeekSetting, Returns the value of of the setting for the first day of the week.
     * Note that this setting may or may not match the first day of the week that is displayed on
     * the calendar. See also: "weekNumbersDisplayed", and "weekNumbersForceFirstDayOfWeekToMatch".
     */
    public DayOfWeek getFirstDayOfWeekSetting() {
        return firstDayOfWeek;
    }

    /**
     * getFontInvalidDate, Returns the value of this setting. See the "set" function for setting
     * information.
     */
    public Font getFontInvalidDate() {
        return fontInvalidDate;
    }

    /**
     * getFontValidDate, Returns the value of this setting. See the "set" function for setting
     * information.
     */
    public Font getFontValidDate() {
        return fontValidDate;
    }

    /**
     * getFontVetoedDate, Returns the value of this setting. See the "set" function for setting
     * information.
     */
    public Font getFontVetoedDate() {
        return fontVetoedDate;
    }

    /**
     * getFormatForDatesBeforeCommonEra, Returns the value of this setting. See the "set" function
     * for setting information.
     */
    public DateTimeFormatter getFormatForDatesBeforeCommonEra() {
        return formatForDatesBeforeCommonEra;
    }

    /**
     * getFormatForDatesCommonEra, Returns the value of this setting. See the "set" function for
     * setting information.
     */
    public DateTimeFormatter getFormatForDatesCommonEra() {
        return formatForDatesCommonEra;
    }

    /**
     * getFormatForTodayButton, Returns the value of this setting. See the "set" function for
     * setting information.
     */
    public DateTimeFormatter getFormatForTodayButton() {
        return formatForTodayButton;
    }

    /**
     * getFormatsForParsing, Returns the value of this setting. See the "set" function for setting
     * information.
     */
    public ArrayList<DateTimeFormatter> getFormatsForParsing() {
        return formatsForParsing;
    }

    /**
     * getGapBeforeButtonPixels, Returns the value of this setting. See the "set" function for
     * setting information.
     */
    public Integer getGapBeforeButtonPixels() {
        return gapBeforeButtonPixels;
    }

    /**
     * getHighlightPolicy, This returns the highlight policy or null.
     */
    public DateHighlightPolicy getHighlightPolicy() {
        return highlightPolicy;
    }

    /**
     * getInitialDate, Returns the value of this setting. See the "set" function for setting
     * information.
     */
    public LocalDate getInitialDate() {
        return initialDate;
    }

    /**
     * getLocale, This returns locale setting of the date picker. The locale can only be set in the
     * DatePickerSettings constructor.
     */
    public Locale getLocale() {
        return locale;
    }

    /**
     * getParentCalendarPanel, Returns the calendar panel that is the parent of the settings, or
     * null if no parent calendar panel has been set.
     */
    public CalendarPanel getParentCalendarPanel() {
        return parentCalendarPanel;
    }

    /**
     * getParentDatePicker, Returns the date picker that is the parent of the settings, or null if
     * no parent date picker has been set.
     */
    public DatePicker getParentDatePicker() {
        return parentDatePicker;
    }

    /**
     * getSizeDatePanelMinimumHeight, Returns the value of this setting. See the "set" function for
     * setting information.
     */
    public int getSizeDatePanelMinimumHeight() {
        return sizeDatePanelMinimumHeight;
    }

    /**
     * getSizeDatePanelMinimumWidth, Returns the value of this setting. See the "set" function for
     * setting information.
     */
    public int getSizeDatePanelMinimumWidth() {
        return sizeDatePanelMinimumWidth;
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
     * getTranslationArrayStandaloneLongMonthNames, Returns the value of this setting. See the "set"
     * function for setting information.
     */
    public String[] getTranslationArrayStandaloneLongMonthNames() {
        return translationArrayStandaloneLongMonthNames;
    }

    /**
     * getTranslationArrayStandaloneShortMonthNames, Returns the value of this setting. See the
     * "set" function for setting information.
     */
    public String[] getTranslationArrayStandaloneShortMonthNames() {
        return translationArrayStandaloneShortMonthNames;
    }

    /**
     * getTranslationClear, Returns the value of this setting. See the "set" function for setting
     * information.
     */
    public String getTranslationClear() {
        return translationClear;
    }

    /**
     * getTranslationToday, Returns the value of this setting. See the "set" function for setting
     * information.
     */
    public String getTranslationToday() {
        return translationToday;
    }

    /**
     * getVetoPolicy, This returns the veto policy or null.
     */
    public DateVetoPolicy getVetoPolicy() {
        return vetoPolicy;
    }

    /**
     * getWeekNumberRules, Returns the value of this setting. See the "set" function for setting
     * information.
     */
    public WeekFields getWeekNumberRules() {
        return weekNumberRules;
    }

    /**
     * getWeekNumbersDisplayed, Returns the value of this setting. See the "set" function for
     * setting information.
     */
    public boolean getWeekNumbersDisplayed() {
        return weekNumbersDisplayed;
    }

    /**
     * getWeekNumbersForceFirstDayOfWeekToMatch, Returns the value of this setting. See the "set"
     * function for setting information.
     */
    public boolean getWeekNumbersForceFirstDayOfWeekToMatch() {
        return weekNumbersForceFirstDayOfWeekToMatch;
    }

    /**
     * hasParent, This returns true if this settings instance has a parent, otherwise returns false.
     * A settings instance will have a parent if the settings instance has already been used to
     * construct a DatePicker, or to construct an independent CalendarPanel. Note that settings
     * instances cannot be reused. They are only expected to ever have one parent.
     */
    public boolean hasParent() {
        boolean hasParentDatePicker = (parentDatePicker != null);
        boolean hasParentCalendarPanel = (parentCalendarPanel != null);
        return (hasParentDatePicker || hasParentCalendarPanel);
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
     * setAllowEmptyDates, This sets whether or not empty dates (null dates) are allowed in the
     * DatePicker or independent CalendarPanel. If this is true, then empty dates will be allowed.
     * If this is false, then empty dates will not be allowed.
     *
     * If setting this function to false, it is recommended to call this function -before- setting a
     * veto policy. This sequence will guarantee that the DatePicker.getDate() (or
     * CalendarPanel.getSelectedDate()) functions will never return a null value, and will guarantee
     * that the setAllowEmptyDates() function will not throw an exception.
     *
     * If the current date is null and you set allowEmptyDates to false, then this function will
     * attempt to initialize the current date to today's date. This function will throw an exception
     * if it fails to initialize a null date. An exception is only possible if a veto policy is set
     * before calling this function, and the veto policy vetoes today's date.
     */
    public void setAllowEmptyDates(boolean allowEmptyDates) {
        this.allowEmptyDates = allowEmptyDates;
        if (hasParent()) {
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
        if (parentDatePicker != null) {
            zApplyAllowKeyboardEditing();
        }
    }

    /**
     * setBorderCalendarPopup, This sets the border for the calendar popup window. If this is null,
     * a default border will be provided by the CustomPopup class. The default value is null. This
     * function only applies if the parent of the settings is a DatePicker. (Not if the parent is an
     * independent CalendarPanel.)
     */
    public void setBorderCalendarPopup(Border borderCalendarPopup) {
        this.borderCalendarPopup = borderCalendarPopup;
    }

    /**
     * setBorderPropertiesList, This sets the list of border properties objects that specifies the
     * colors and thicknesses of the borders in the CalendarPanel. By default, a default set of
     * border properties is stored in the borderPropertiesList.
     *
     * Passing in "null" when setting this property will restore the default border properties. If
     * you don't want the default border properties but wish to truly "clear" the border properties
     * list, you could pass in an empty ArrayList.
     *
     * To modify the calendar borders (including individual border colors, thickness, or
     * visibility), you can supply your own ArrayList of border properties. Border properties
     * objects are applied in the order that they appear in the ArrayList. To learn how to set the
     * CalendarBorderProperties objects, see the CalendarBorderProperties class javadocs.
     *
     * The default border properties have a one pixel blue border around the date labels, and two
     * vertical light blue borders near the weekday labels (which match the color of the weekday
     * labels). The outer top horizontal border and the outer left vertical border are invisible by
     * default.
     *
     * The default border properties also include borders above and below the "week numbers" column
     * which match the color of the week numbers labels. (However, these borders are only displayed
     * when the week numbers are displayed.)
     *
     * Whenever the border properties list is applied, the borders are first "cleared" by setting
     * all borders to the following settings: Invisible, and black color. This is true of both the
     * default border properties and any programmer supplied border properties.
     *
     * Note: If weekNumbersDisplayed is set to false, then the borders located in the week number
     * columns (Columns 1 and 2) will always be hidden.
     */
    public void setBorderPropertiesList(ArrayList<CalendarBorderProperties> borderPropertiesList) {
        if (borderPropertiesList == null) {
            borderPropertiesList = zGetDefaultBorderPropertiesList();
        }
        this.borderPropertiesList = borderPropertiesList;
        zApplyBorderPropertiesList();
    }

    /**
     * setColorBackgroundCalendarPanel, This sets the background color for the entire calendar
     * panel. The default color is a very light gray.
     */
    public void setColorBackgroundCalendarPanel(Color colorBackgroundCalendarPanel) {
        this.colorBackgroundCalendarPanel = colorBackgroundCalendarPanel;
        zDrawIndependentCalendarPanelIfNeeded();
    }

    /**
     * setColorBackgroundHighlightedDates, This sets the calendar background color for dates which
     * are highlighted by a highlight policy. The default color is green.
     */
    public void setColorBackgroundHighlightedDates(Color colorBackgroundHighlightedDates) {
        this.colorBackgroundHighlightedDates = colorBackgroundHighlightedDates;
        zDrawIndependentCalendarPanelIfNeeded();
    }

    /**
     * setColorBackgroundMonthAndYear, This sets the background color used by the month and year
     * buttons. The default color is a very light gray.
     */
    public void setColorBackgroundMonthAndYear(Color colorBackgroundMonthAndYear) {
        this.colorBackgroundMonthAndYear = colorBackgroundMonthAndYear;
        if (parentCalendarPanel != null) {
            parentCalendarPanel.zLabelIndicatorsAllSetColorsToDefaultState();
        }
    }

    /**
     * setColorBackgroundNavigateYearMonthButtons, This sets the background color used by the
     * buttons for navigating "previous year", "previous month", "next year", "next month". The
     * default value is the default java button background.
     */
    public void setColorBackgroundNavigateYearMonthButtons(Color colorBackgroundNavigateYearMonthButtons) {
        this.colorBackgroundNavigateYearMonthButtons = colorBackgroundNavigateYearMonthButtons;
        zDrawIndependentCalendarPanelIfNeeded();
    }

    /**
     * setColorBackgroundTodayAndClear, This sets the background color used by the "Today" and
     * "Clear" buttons. The default color is a very light gray.
     */
    public void setColorBackgroundTodayAndClear(Color colorBackgroundTodayAndClear) {
        this.colorBackgroundTodayAndClear = colorBackgroundTodayAndClear;
        if (parentCalendarPanel != null) {
            parentCalendarPanel.zLabelIndicatorsAllSetColorsToDefaultState();
        }
    }

    /**
     * setColorBackgroundTopLeftLabel, This sets the calendar background color for the label that is
     * used to fill the top left corner of the calendar whenever the week numbers are displayed. The
     * default color is a medium sky blue.
     */
    public void setColorBackgroundTopLeftLabel(Color colorBackgroundTopLeftLabel) {
        this.colorBackgroundTopLeftLabel = colorBackgroundTopLeftLabel;
        zDrawIndependentCalendarPanelIfNeeded();
    }

    /**
     * setColorBackgroundWeekNumberLabels, This sets the calendar background color for the week
     * number labels. The default color is a medium sky blue.
     */
    public void setColorBackgroundWeekNumberLabels(Color colorBackgroundWeekNumberLabels,
            boolean applyMatchingDefaultBorders) {
        this.colorBackgroundWeekNumberLabels = colorBackgroundWeekNumberLabels;
        if (applyMatchingDefaultBorders) {
            setBorderPropertiesList(null);
        }
        if (parentCalendarPanel != null) {
            parentCalendarPanel.zRedrawWeekNumberLabelColors();
        }
    }

    /**
     * setColorBackgroundVetoedDates, This sets the calendar background color for dates which are
     * vetoed by a veto policy. The default color is light gray.
     */
    public void setColorBackgroundVetoedDates(Color colorBackgroundVetoedDates) {
        this.colorBackgroundVetoedDates = colorBackgroundVetoedDates;
        zDrawIndependentCalendarPanelIfNeeded();
    }

    /**
     * setColorBackgroundWeekdayLabels, This sets the calendar background color for the weekday
     * labels. The default color is a medium sky blue.
     *
     * @param applyMatchingDefaultBorders, This determines if this function will update the border
     * label properties to show the appropriate default borders. The default border label settings
     * are different, depending on the "colorBackgroundWeekdayLabels". If you have not customized
     * the border label properties, then it is recommended that you always set the
     * "applyMatchingDefaultBorders" parameter to true. This will ensure that the calendar borders
     * will always use the correct default settings. If you set this parameter to false, than the
     * current border settings will not be changed by this function.
     */
    public void setColorBackgroundWeekdayLabels(Color colorBackgroundWeekdayLabels,
            boolean applyMatchingDefaultBorders) {
        this.colorBackgroundWeekdayLabels = colorBackgroundWeekdayLabels;
        if (applyMatchingDefaultBorders) {
            setBorderPropertiesList(null);
        }
        if (parentCalendarPanel != null) {
            parentCalendarPanel.zRedrawWeekdayLabelColors();
        }
    }

    /**
     * setColorTextInvalidDate, This sets the text field text color for invalid dates. The default
     * color is red.
     */
    public void setColorTextInvalidDate(Color colorTextInvalidDate) {
        this.colorTextInvalidDate = colorTextInvalidDate;
    }

    /**
     * setColorTextValidDate, This sets the text field text color for valid dates. The default color
     * is black.
     */
    public void setColorTextValidDate(Color colorTextValidDate) {
        this.colorTextValidDate = colorTextValidDate;
    }

    /**
     * setColorTextVetoedDate, This sets the text field text color for vetoed dates. The default
     * color is black. Note: The default fontVetoedDate setting will draw a line (strikethrough)
     * vetoed dates.
     */
    public void setColorTextVetoedDate(Color colorTextVetoedDate) {
        this.colorTextVetoedDate = colorTextVetoedDate;
    }

    /**
     * setFirstDayOfWeek, This sets the day of the week that will be displayed in the far left
     * column of the CalendarPanel, as the "first day of the week". The default value is generated
     * using the locale of the settings instance.
     *
     * By default, If weekNumbersDisplayed is set to true, then the first day of the week will not
     * match the setting. (weekNumbersDisplayed is false by default.) For additional details, see
     * the javadocs for the following settings: "weekNumbersDisplayed", and
     * "weekNumbersForceFirstDayOfWeekToMatch".
     */
    public void setFirstDayOfWeek(DayOfWeek firstDayOfWeek) {
        this.firstDayOfWeek = firstDayOfWeek;
        zDrawIndependentCalendarPanelIfNeeded();
    }

    /**
     * setFontInvalidDate, This sets the text field text font for invalid dates. The default font is
     * a normal undecorated font. (Note: The color for invalid dates defaults to Color.red. See
     * also: "colorTextInvalidDate".)
     */
    public void setFontInvalidDate(Font fontInvalidDate) {
        this.fontInvalidDate = fontInvalidDate;
    }

    /**
     * setFontValidDate, This sets the text field text font for valid dates. The default font is a
     * normal undecorated font.
     *
     * Note: Changing the font for valid dates may also change the default minimum size for the date
     * picker. This will not affect the minimum size if it has been overridden by the programmer.
     */
    public void setFontValidDate(Font fontValidDate) {
        this.fontValidDate = fontValidDate;
        // The font for the valid date can change the default minimum size.
        // So we recalculate and set the minimum size, if needed.
        if (parentDatePicker != null) {
            parentDatePicker.zSetAppropriateTextFieldMinimumWidth();
        }
    }

    /**
     * setFontVetoedDate, This sets the text field text font for vetoed dates. The default font
     * crosses out the vetoed date. (Has a strikethrough font attribute.)
     */
    public void setFontVetoedDate(Font fontVetoedDate) {
        this.fontVetoedDate = fontVetoedDate;
    }

    /**
     * setFormatForDatesBeforeCommonEra, This sets the format that is used to display or parse BCE
     * dates in the date picker, from a DateTimeFormatter instance. The default value for the date
     * format is generated using the locale of the settings instance.
     *
     * For most desired date formats, it would probably be easier to use the other "pattern string"
     * version of this function. (which accepts a date pattern string instead of a DateTimeFormatter
     * instance.)
     *
     * Note: It is important to use the letter "u" (astronomical year) instead of "y" (year of era)
     * when creating pattern strings for BCE dates. This is because the DatePicker uses ISO 8601,
     * which specifies "Astronomical year numbering". (Additional details: The astronomical year
     * "-1" and "1 BC" are not the same thing. Astronomical years are zero-based, and BC dates are
     * one-based. Astronomical year "0", is the same year as "1 BC", and astronomical year "-1" is
     * the same year as "2 BC", and so forth.)
     *
     * The various codes for the date pattern string are described at this link:
     * https://docs.oracle.com/javase/8/docs/api/java/time/format/DateTimeFormatter.html
     *
     * If the date picker has already been constructed, then calling this function will cause
     * immediate validation of the text field text.
     */
    public void setFormatForDatesBeforeCommonEra(DateTimeFormatter formatForDatesBeforeCommonEra) {
        this.formatForDatesBeforeCommonEra = formatForDatesBeforeCommonEra;
        if (parentDatePicker != null) {
            parentDatePicker.setTextFieldToValidStateIfNeeded();
        }
    }

    /**
     * setFormatForDatesBeforeCommonEra, This sets the format that is used to display or parse BCE
     * dates in the date picker, from a DateTimeFormatter pattern string. The default value for the
     * date format is generated using the locale of the settings instance.
     *
     * Note: It is important to use the letter "u" (astronomical year) instead of "y" (year of era)
     * when creating pattern strings for BCE dates. This is because the DatePicker uses ISO 8601,
     * which specifies "Astronomical year numbering". (Additional details: The astronomical year
     * "-1" and "1 BC" are not the same thing. Astronomical years are zero-based, and BC dates are
     * one-based. Astronomical year "0", is the same year as "1 BC", and astronomical year "-1" is
     * the same year as "2 BC", and so forth.)
     *
     * The various codes for the date pattern string are described at this link:
     * https://docs.oracle.com/javase/8/docs/api/java/time/format/DateTimeFormatter.html
     *
     * If the date picker has already been constructed, then calling this function will cause
     * immediate validation of the text field text.
     */
    public void setFormatForDatesBeforeCommonEra(String patternString) {
        DateTimeFormatter formatter
                = PickerUtilities.createFormatterFromPatternString(patternString, getLocale());
        setFormatForDatesBeforeCommonEra(formatter);
    }

    /**
     * setFormatForDatesCommonEra, This sets the format that is used to display or parse CE dates in
     * the date picker, from a DateTimeFormatter instance. The default value for the date format is
     * generated using the locale of the settings instance.
     *
     * For most desired date formats, it would probably be easier to use the other "pattern string"
     * version of this function. (which accepts a date pattern string instead of a DateTimeFormatter
     * instance.)
     *
     * The various codes for the date pattern string are described at this link:
     * https://docs.oracle.com/javase/8/docs/api/java/time/format/DateTimeFormatter.html
     *
     * If the date picker has already been constructed, then calling this function will cause
     * immediate validation of the text field text.
     *
     * Note: Changing the format for CE dates may also change the default minimum width of the date
     * picker text field.
     */
    public void setFormatForDatesCommonEra(DateTimeFormatter formatForDatesCommonEra) {
        this.formatForDatesCommonEra = formatForDatesCommonEra;
        if (parentDatePicker != null) {
            parentDatePicker.setTextFieldToValidStateIfNeeded();
        }
        // Changing the format for AD dates can change the default minimum width of the text field.
        // So if needed, set the appropriate minimum width.
        if (parentDatePicker != null) {
            parentDatePicker.zSetAppropriateTextFieldMinimumWidth();
        }
    }

    /**
     * setFormatForDatesCommonEra, This sets the format that is used to display or parse CE dates in
     * the date picker, from a DateTimeFormatter pattern string. The default value for the date
     * format is generated using the locale of the settings instance.
     *
     * The various codes for the date pattern string are described at this link:
     * https://docs.oracle.com/javase/8/docs/api/java/time/format/DateTimeFormatter.html
     *
     * If the date picker has already been constructed, then calling this function will cause
     * immediate validation of the text field text.
     *
     * Note: Changing the format for CE dates may also change the default minimum width of the date
     * picker text field.
     */
    public void setFormatForDatesCommonEra(String patternString) {
        DateTimeFormatter formatter
                = PickerUtilities.createFormatterFromPatternString(patternString, getLocale());
        setFormatForDatesCommonEra(formatter);
    }

    /**
     * setFormatForTodayButton, This sets the format that is used to format today's date into a date
     * string, which is displayed on the today button. The default value is generated using the
     * locale of the settings instance.
     */
    public void setFormatForTodayButton(DateTimeFormatter formatForTodayButton) {
        this.formatForTodayButton = formatForTodayButton;
        zDrawIndependentCalendarPanelIfNeeded();
    }

    /**
     * setFormatsForParsing, This holds a list of formats that are used to attempt to parse dates
     * that are typed by the user. When parsing a date, these formats are tried in the order that
     * they appear in this list. Note that the formatForDatesCommonEra and
     * formatForDatesBeforeCommonEra are always tried (in that order) before any other parsing
     * formats. The default values for the formatsForParsing are generated using the pickerLocale,
     * using the enum constants in java.time.format.FormatStyle.
     */
    public void setFormatsForParsing(ArrayList<DateTimeFormatter> formatsForParsing) {
        this.formatsForParsing = formatsForParsing;
    }

    /**
     * setGapBeforeButtonPixels, This specifies the desired width for the gap between the date
     * picker and the toggle calendar button (in pixels). The default value is null. If this is left
     * at null, then the default value is 3 pixels.
     */
    public void setGapBeforeButtonPixels(Integer gapBeforeButtonPixels) {
        this.gapBeforeButtonPixels = gapBeforeButtonPixels;
        if (parentDatePicker != null) {
            zApplyGapBeforeButtonPixels();
        }
    }

    /**
     * setHighlightPolicy,
     *
     * This sets a highlight policy for the parent DatePicker or parent independent CalendarPanel.
     *
     * If a highlight policy is supplied, it will be used to determine which dates should be
     * highlighted in the calendar panel. The highlight policy can also supply tooltip text for any
     * highlighted dates. See the demo class for an example of constructing a highlight policy. By
     * default, there is no highlight policy. (The default value is null.)
     */
    public void setHighlightPolicy(DateHighlightPolicy highlightPolicy) {
        this.highlightPolicy = highlightPolicy;
        zDrawIndependentCalendarPanelIfNeeded();
    }

    /**
     * setInitialDate, This sets the date that the parent DatePicker or parent independent
     * CalendarPanel will have when it is created. This can be set to any date, or it can be set to
     * null. The default value for initialDate is null, which represents an empty date. This setting
     * will only have an effect if it is set before the parent component is constructed.
     *
     * For an independent CalendarPanel, this setting will also set the displayed YearMonth of the
     * CalendarPanel to match the initial date. (See also: CalendarPanel.setSelectedDate() and
     * CalendarPanel.setDisplayedYearMonth().)
     *
     * If allowEmptyDates is false, then a null initialDate will be ignored. More specifically: When
     * the parent component is constructed, if allowEmptyDates is false and initialDate is null,
     * then the initialDate will be set to a default value. (The default value is today's date.)
     *
     * Note: This date can not be vetoed, because a veto policy can not be set until after the
     * parent component is constructed.
     */
    public void setInitialDate(LocalDate initialDate) {
        this.initialDate = initialDate;
    }

    /**
     * setInitialDateToToday, This is a convenience function to set the initial date of a DatePicker
     * or independent CalendarPanel to today's date.
     */
    public void setInitialDateToToday() {
        initialDate = LocalDate.now();
    }

    /**
     * setSizeDatePanelMinimumHeight, This sets the minimum height, in pixels, of the date label
     * area. The date label area is the part of the calendar panel that holds (only) the date
     * labels. The remainder of the calendar panel is automatically resized to encompass the date
     * label area. The default value for this variable is (6 * 18). If the default value is
     * modified, the programmer may also wish to adjust sizeDatePanelPixelsExtraHeight.
     */
    public void setSizeDatePanelMinimumHeight(int sizeDatePanelMinimumHeight) {
        this.sizeDatePanelMinimumHeight = sizeDatePanelMinimumHeight;
        zDrawIndependentCalendarPanelIfNeeded();
    }

    /**
     * setSizeDatePanelMinimumWidth, This sets the minimum width, in pixels, of the date label area.
     * The date label area is the part of the calendar panel that holds (only) the date labels. The
     * remainder of the calendar panel is automatically resized to encompass the date label area.
     * The default value for this variable is (7 * 30). If the default value is modified, the
     * programmer may also wish to adjust sizeDatePanelPixelsExtraWidth.
     */
    public void setSizeDatePanelMinimumWidth(int sizeDatePanelMinimumWidth) {
        this.sizeDatePanelMinimumWidth = sizeDatePanelMinimumWidth;
        zDrawIndependentCalendarPanelIfNeeded();
    }

    /**
     * setSizeTextFieldMinimumWidth, This sets the minimum width in pixels, of the DatePicker text
     * field. (The text field is located to the left of the date picker "popup calendar" button, and
     * displays the currently selected date.)
     *
     * The default value for this setting is null. When this is set to null, a default width for the
     * date picker text field will be automatically calculated and applied to fit "the largest
     * possible date" (with a four digit year) that can be displayed with the current date picker
     * settings. The settings used to calculate the default text field width include the locale (the
     * language), the fontValidDate, and the formatForDatesCommonEra.
     */
    public void setSizeTextFieldMinimumWidth(Integer minimumWidthInPixels) {
        this.sizeTextFieldMinimumWidth = minimumWidthInPixels;
        if (parentDatePicker != null) {
            parentDatePicker.zSetAppropriateTextFieldMinimumWidth();
        }
    }

    /**
     * setSizeTextFieldMinimumWidthDefaultOverride, This specifies how the date picker should choose
     * the appropriate minimum width for the date picker text field. (As described below.)
     *
     * If this is true, then the applied minimum width will be the largest of either the default, or
     * any programmer supplied, minimum widths.
     *
     * If this is false, then any programmer supplied minimum width will always override the default
     * minimum width. (Even if the programmer supplied width is too small to fit the dates that can
     * be displayed in the DatePicker).
     *
     * The default value for this setting is true. This setting only has an effect if
     * (sizeTextFieldMinimumWidth != null).
     *
     * See also: "setSizeTextFieldMinimumWidth()".
     */
    public void setSizeTextFieldMinimumWidthDefaultOverride(boolean defaultShouldOverrideIfNeeded) {
        this.sizeTextFieldMinimumWidthDefaultOverride = defaultShouldOverrideIfNeeded;
        if (parentDatePicker != null) {
            parentDatePicker.zSetAppropriateTextFieldMinimumWidth();
        }
    }

    /**
     * setTranslationArrayStandaloneLongMonthNames, This sets an array of month names to use for the
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
    public void setTranslationArrayStandaloneLongMonthNames(
            String[] translationArrayStandaloneLongMonthNames) {
        this.translationArrayStandaloneLongMonthNames = translationArrayStandaloneLongMonthNames;
        zDrawIndependentCalendarPanelIfNeeded();
    }

    /**
     * setTranslationArrayStandaloneShortMonthNames, This sets an array of short month names to use
     * for the calendar panel header, as translated to the current language. It is not expected that
     * this variable will need to be changed by the programmer. The default values are generated
     * using the locale of the settings instance, by retrieving the translated text for the current
     * language from the class called java.text.DateFormatSymbols. This array is indexed with
     * January = 0. The Calendar class month name constants can also be used for indexing.
     * (Calendar.JANUARY, etc).
     *
     * Expected array contents: This array should never be set to null, and the array should always
     * have a length of 12. Each element should always contain a string that is not null and not
     * empty.
     */
    public void setTranslationArrayStandaloneShortMonthNames(
            String[] translationArrayStandaloneShortMonthNames) {
        this.translationArrayStandaloneShortMonthNames = translationArrayStandaloneShortMonthNames;
        zDrawIndependentCalendarPanelIfNeeded();
    }

    /**
     * setTranslationClear, This sets the text of the calendars "Clear" button, as translated to the
     * current language. It is not expected that this variable will need to be changed by the
     * programmer. If you wish to supply a new translation for the date picker, it would generally
     * be better to add (or edit) your translation in the "TranslationResources.properties" file.
     * The default value is generated using the locale of the settings instance, by retrieving the
     * translated text for the current language from the properties file.
     */
    public void setTranslationClear(String translationClear) {
        this.translationClear = translationClear;
        zDrawIndependentCalendarPanelIfNeeded();
    }

    /**
     * setTranslationToday, This sets the text of the calendars "Today" button, as translated to the
     * current language. It is not expected that this variable will need to be changed by the
     * programmer. If you wish to supply a new translation for the date picker, it would generally
     * be better to add (or edit) your translation in the "TranslationResources.properties" file.
     * The default value is generated from the locale of the settings instance, by retrieving the
     * translated text for the current language from the properties file.
     */
    public void setTranslationToday(String translationToday) {
        this.translationToday = translationToday;
        zDrawIndependentCalendarPanelIfNeeded();
    }

    /**
     * setVetoPolicy,
     *
     * This sets a veto policy for the parent DatePicker or parent independent CalendarPanel.
     *
     * The DatePicker or independent CalendarPanel associated with this settings instance is known
     * as the "parent component". Important Note: This function can only be called after the parent
     * component is constructed with this settings instance. If this is called before the parent is
     * constructed, then an exception will be thrown.
     *
     * When a veto policy is supplied, it will be used to determine which dates can or can not be
     * selected in the calendar panel. (Vetoed dates are also not accepted into the date picker text
     * field). See the demo class for an example of constructing a veto policy.
     *
     * By default, there is no veto policy. Setting this function to null will clear any veto policy
     * that has been set.
     *
     * It's possible to set a veto policy that vetoes the currently selected date. This function
     * returns true if the selected date is allowed by the new veto policy and the other current
     * settings, or false if the selected date is vetoed or disallowed. Setting a new veto policy
     * does not modify the selected date. Is up to the programmer to resolve any potential conflict
     * between a new veto policy, and the selected date stored in the parent object.
     */
    public boolean setVetoPolicy(DateVetoPolicy vetoPolicy) {
        if (!hasParent()) {
            throw new RuntimeException("DatePickerSettings.setVetoPolicy(), "
                    + "A veto policy can only be set after constructing the parent DatePicker or "
                    + "the parent independent CalendarPanel. (The parent component should be "
                    + "constructed using the DatePickerSettings instance where the veto policy "
                    + "will be applied. The previous sentence is probably simpler than it sounds.)");
        }
        this.vetoPolicy = vetoPolicy;
        // If the parent is an independent calendar panel, redraw the panel to show the new policy.
        zDrawIndependentCalendarPanelIfNeeded();
        // Return true if the selected date is allowed by the new policy, otherwise return false.
        LocalDate parentDate = zGetParentSelectedDate();
        return isDateAllowed(parentDate);
    }

    /**
     * setWeekNumberRules, This sets the week number rules that will be used to show the week
     * numbers (the weeks of the year), whenever the week numbers are displayed on this calendar.
     *
     * The definitions for the first day of the week, and for the minimum number of days that
     * constitutes the first week of the year, varies between locales. The Java time WeekFields
     * class specifies those rules.
     *
     * If you set this setting to null, then the weekNumberRules will be set to match the
     * DatePickerSettings locale. By default, the week number rules will match the
     * DatePickerSettings locale.
     *
     * If you would like the week number rules to be the same for all calendars (regardless of the
     * locale), then you should consider setting the weekNumberRules to one of these two widely used
     * rule sets: WeekFields.ISO or WeekFields.SUNDAY_START.
     */
    public void setWeekNumberRules(WeekFields weekNumberRules) {
        if (weekNumberRules == null) {
            weekNumberRules = WeekFields.of(locale);
        }
        this.weekNumberRules = weekNumberRules;
        zDrawIndependentCalendarPanelIfNeeded();
    }

    /**
     * setWeekNumbersDisplayed, This specifies whether or not numbers for the "week of the year"
     * should be displayed on the calendar panel. The week numbers will be shown if this is true, or
     * they will not be shown if this is false. This is false by default.
     *
     * The week numbers are locale sensitive, because the definition of the first day of the week,
     * and the definition for the minimum number of days in the first week of the year, varies
     * between locales.
     *
     * By default, when "weekNumbersDisplayed" is true, the first day of the week used by the
     * calendar will always match the weekNumberRules definition for first day of the week. This
     * behavior can be changed by modifying the setting "weekNumbersForceFirstDayOfWeekToMatch".
     *
     * @param weekNumbersDisplayed, This determines whether the week numbers should be displayed on
     * the calendar. They will be shown if this is true, or not shown that this is false
     *
     * @param applyMatchingDefaultBorders, This determines if this function will update the border
     * label properties to show the appropriate default borders. The default border label settings
     * are different, depending on "showWeekNumbers". If you have not customized the border label
     * properties, then it is recommended that you always set the "applyMatchingDefaultBorders"
     * parameter to true. This will ensure that the calendar borders will always use the correct
     * default settings. If you set this parameter to false, than the current border settings will
     * not be changed by this function.
     */
    public void setWeekNumbersDisplayed(boolean weekNumbersDisplayed,
            boolean applyMatchingDefaultBorders) {
        this.weekNumbersDisplayed = weekNumbersDisplayed;
        if (applyMatchingDefaultBorders) {
            setBorderPropertiesList(null);
        }
        zDrawIndependentCalendarPanelIfNeeded();
    }

    /**
     * setWeekNumbersForceFirstDayOfWeekToMatch, This setting determines if the first day of the
     * week should be forced to match the weekNumberRules for the first day of the week, whenever
     * weekNumbersDisplayed is true.
     *
     * If weekNumbersDisplayed is false, then the week numbers will not be displayed, and the
     * setting will not have any effect.
     *
     * If this setting is true and weekNumbersDisplayed is true, then the first day of the week that
     * is shown on the calendar will always match the weekNumberRules definition for "first day of
     * the week". (And all the dates in each row will always belong to the displayed week number.)
     *
     * If this setting is false and weekNumbersDisplayed is true, then the week numbers will be
     * determined by a "majority rules" system, which is described below. (In this system, up to 3
     * dates in each row may not belong to the displayed week number.)
     *
     * Description of the "majority rules" system: If this setting is false, and if the first day of
     * the week is set to be different than the first day of the week defined by the
     * weekNumberRules, then the dates in a single row of the calendar may not all belong to the
     * same week number. In this circumstance, the displayed week number for each row will be
     * determined by a "majority rules" system. More specifically, the calendar will count the
     * number of days in each row that belong to each week number. Four or more days in a particular
     * row will always match a particular week number, and that is the week number that will be
     * displayed next to the row.
     */
    public void setWeekNumbersForceFirstDayOfWeekToMatch(boolean forceFirstDayOfWeekToMatch) {
        this.weekNumbersForceFirstDayOfWeekToMatch = forceFirstDayOfWeekToMatch;
        zDrawIndependentCalendarPanelIfNeeded();
    }

    /**
     * yApplyNeededSettingsAtDatePickerConstruction, This is called from the date picker constructor
     * to apply various settings in this settings instance to the date picker. Only the settings
     * that are needed at the time of date picker construction, are applied in this function.
     */
    void yApplyNeededSettingsAtDatePickerConstruction() {
        // Run the needed "apply" functions.
        // Note: The border properties list does not need to be applied here, because it is 
        // applied whenever the date picker calendar panel is opened.
        zApplyGapBeforeButtonPixels();
        zApplyAllowKeyboardEditing();
        zApplyInitialDate();
        zApplyAllowEmptyDates();
    }

    /**
     * yApplyNeededSettingsAtIndependentCalendarPanelConstruction, This is called from the calendar
     * panel constructor to apply various settings in this settings instance to the calendar panel.
     * Only the settings that are needed at the time of calendar panel construction, are applied in
     * this function.
     *
     * This should only be called from the CalendarPanel constructor. (Even though it is public,
     * this should not be called by the programmer.)
     */
    public void yApplyNeededSettingsAtIndependentCalendarPanelConstruction() {
        // Run the needed "apply" functions.
        zApplyInitialDate();
        zApplyAllowEmptyDates();
        zApplyBorderPropertiesList();
    }

    /**
     * zApplyAllowEmptyDates, This applies the named setting to the parent component.
     *
     * Implementation Notes:
     *
     * The zApplyInitialDate() and zApplyAllowEmptyDates() functions may theoretically be called in
     * any order. However, the order is currently zApplyInitialDate() and zApplyAllowEmptyDates()
     * because that is more intuitive.
     *
     * This cannot throw an exception while the parent DatePicker or parent CalendarPanel is being
     * constructed, because a veto policy cannot be set until after the parent component is
     * constructed.
     */
    private void zApplyAllowEmptyDates() {
        // If we don't have a parent, then there is nothing to do here. (So, we return.)
        if (!hasParent()) {
            return;
        }
        // Find out if we need to initialize a null date.
        LocalDate selectedDate = zGetParentSelectedDate();
        if ((!allowEmptyDates) && (selectedDate == null)) {
            // We need to initialize the current date, so find out if today is vetoed.
            LocalDate today = LocalDate.now();
            if (InternalUtilities.isDateVetoed(vetoPolicy, today)) {
                throw new RuntimeException("Exception in DatePickerSettings.zApplyAllowEmptyDates(), "
                        + "Could not initialize a null date to today, because today is vetoed by "
                        + "the veto policy. To prevent this exception, always call "
                        + "setAllowEmptyDates() -before- setting a veto policy.");
            }
            // Initialize the current date.
            zSetParentSelectedDate(today);
        }
    }

    /**
     * zApplyAllowKeyboardEditing, This applies the named setting to the parent component.
     */
    private void zApplyAllowKeyboardEditing() {
        // Set the editability of the date picker text field.
        parentDatePicker.getComponentDateTextField().setEditable(allowKeyboardEditing);
        // Set the text field border color based on whether the text field is editable.
        Color textFieldBorderColor = (allowKeyboardEditing)
                ? InternalConstants.colorEditableTextFieldBorder
                : InternalConstants.colorNotEditableTextFieldBorder;
        parentDatePicker.getComponentDateTextField().setBorder(new CompoundBorder(
                new MatteBorder(1, 1, 1, 1, textFieldBorderColor), new EmptyBorder(1, 3, 2, 2)));
    }

    /**
     * zApplyBorderPropertiesList, This applies the named setting to the parent component.
     */
    private void zApplyBorderPropertiesList() {
        // This only needs to be applied to independent calendar panels. 
        // For the date picker, the setting is automatically applied each time the calendar panel 
        // is opened.
        if (parentCalendarPanel != null) {
            parentCalendarPanel.zApplyBorderPropertiesList();
        }
    }

    /**
     * zApplyGapBeforeButtonPixels, This applies the named setting to the parent component.
     */
    private void zApplyGapBeforeButtonPixels() {
        int gapPixels = (gapBeforeButtonPixels == null) ? 3 : gapBeforeButtonPixels;
        ConstantSize gapSizeObject = new ConstantSize(gapPixels, ConstantSize.PIXEL);
        ColumnSpec columnSpec = ColumnSpec.createGap(gapSizeObject);
        FormLayout layout = ((FormLayout) parentDatePicker.getLayout());
        layout.setColumnSpec(2, columnSpec);
    }

    /**
     * zApplyInitialDate, This applies the named setting to the parent component.
     *
     * Notes:
     *
     * This does not need to check the parent for null, because this is always and only called while
     * the parent component is being constructed.
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
            zSetParentSelectedDate(null);
        }
        if (initialDate != null) {
            zSetParentSelectedDate(initialDate);
        }
    }

    /**
     * zDrawIndependentCalendarPanelIfNeeded, If needed, this will redraw the independent
     * CalendarPanel. This function only has an effect if the parent of this settings instance is an
     * independent CalendarPanel (and not a DatePicker).
     */
    private void zDrawIndependentCalendarPanelIfNeeded() {
        if (parentCalendarPanel != null) {
            parentCalendarPanel.drawCalendar();
        }
    }

    /**
     * zGetParentSelectedDate, Returns the currently selected date from the parent DatePicker or
     * parent independent CalendarPanel (using whichever parent currently exists). If this settings
     * instance does not have a parent, then this will return null. Note that any calling code
     * should generally verify whether or not this settings instance has a parent, before calling
     * this function.
     */
    private LocalDate zGetParentSelectedDate() {
        if (!hasParent()) {
            return null;
        }
        boolean useDatePicker = (parentDatePicker != null);
        return (useDatePicker) ? parentDatePicker.getDate() : parentCalendarPanel.getSelectedDate();
    }

    /**
     * zSetParentCalendarPanel, This sets the parent calendar panel for these settings. This is only
     * intended to be called from the constructor of the CalendarPanel class. (Even though this is
     * public, this should not be called by the programmer.)
     */
    public void zSetParentCalendarPanel(CalendarPanel parentCalendarPanel) {
        if (hasParent()) {
            throw new RuntimeException("DatePickerSettings.setParentCalendarPanel(), "
                    + "A DatePickerSettings instance can only be used as the settings for "
                    + "one parent object. (Settings instances cannot be reused for multiple "
                    + "DatePickers, multiple independent CalendarPanels, or for combinations "
                    + "of both. )");
        }
        this.parentCalendarPanel = parentCalendarPanel;
    }

    /**
     * zGetDefaultBorderPropertiesList, This creates and returns a list of default border properties
     * for the calendar. The default border properties will be slightly different depending on
     * whether or not the week numbers are currently displayed in the calendar.
     *
     * Technical notes: This does not save the default border properties to the date picker settings
     * class. This does not clear the existing borders. This does not apply the border properties
     * list to the calendar. This does not set the upper left corner calendar label to be visible or
     * invisible.
     */
    private ArrayList<CalendarBorderProperties> zGetDefaultBorderPropertiesList() {
        ArrayList<CalendarBorderProperties> results = new ArrayList<CalendarBorderProperties>();
        Color defaultDateBoxBorderColor = new Color(99, 130, 191);
        Color defaultWeekdayEndcapsBorderColor = colorBackgroundWeekdayLabels;
        Color defaultWeekNumberEndcapsBorderColor = colorBackgroundWeekNumberLabels;
        // Set the borders properties for the date box.
        CalendarBorderProperties dateBoxBorderProperties = new CalendarBorderProperties(
                new Point(3, 3), new Point(5, 5), defaultDateBoxBorderColor, 1);
        results.add(dateBoxBorderProperties);
        // Set the borders properties for the weekday label endcaps.
        CalendarBorderProperties weekdayLabelBorderProperties = new CalendarBorderProperties(
                new Point(3, 2), new Point(5, 2), defaultWeekdayEndcapsBorderColor, 1);
        results.add(weekdayLabelBorderProperties);
        // Set the border properties for borders above and below the week numbers.
        // Note that the week number borders are only displayed when the week numbers are also
        // displayed. (This is true of any borders located in columns 1 and 2.)
        CalendarBorderProperties weekNumberBorderProperties = new CalendarBorderProperties(
                new Point(2, 3), new Point(2, 5), defaultWeekNumberEndcapsBorderColor, 1);
        results.add(weekNumberBorderProperties);
        // Return the results.
        return results;
    }

    /**
     * zSetParentDatePicker, This sets the parent date picker for these settings. This is only
     * intended to be called from the constructor of the DatePicker class.
     */
    void zSetParentDatePicker(DatePicker parentDatePicker) {
        if (hasParent()) {
            throw new RuntimeException("DatePickerSettings.setParentCalendarPanel(), "
                    + "A DatePickerSettings instance can only be used as the settings for "
                    + "one parent object. (Settings instances cannot be reused for multiple "
                    + "DatePickers, multiple independent CalendarPanels, or for combinations "
                    + "of both.)");
        }
        this.parentDatePicker = parentDatePicker;
    }

    /**
     * zSetParentSelectedDate, Sets the currently selected date for the parent DatePicker or parent
     * independent CalendarPanel (using whichever parent currently exists). If this settings
     * instance does not have a parent, then this will not do anything. Note that any calling code
     * should generally verify whether or not this settings instance has a parent, before calling
     * this function.
     */
    private void zSetParentSelectedDate(LocalDate dateValue) {
        if (parentDatePicker != null) {
            parentDatePicker.setDate(dateValue);
        }
        if (parentCalendarPanel != null) {
            parentCalendarPanel.setSelectedDate(dateValue);
        }
    }

}
