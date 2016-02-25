package com.lgooddatepicker.support;

import java.text.DateFormatSymbols;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;
import java.util.Locale;

/**
 * ExtraDateStrings, This class holds extra date strings. This includes: # Formats for parsing dates
 * in a particular language. # Overridden month names for particular locales. All the fields and
 * functions are static.
 */
public class ExtraDateStrings {

    /**
     * extraParsingFormatsForLanguage_en, This is a constant list of extra parsing formats, which
     * are used for parsing dates in an English locale.
     */
    final static private String[] extraParsingFormatsForLanguage_en = new String[]{
        "M/d/u", "dMMMuu", "dMMMuuuu", "d MMM uu", "d MMM uuuu", "MMM d, u", "MMM d u",
        "MMM d, yyyy G"};

    /**
     * extraParsingFormatsForLanguage_ru, This is a constant list of extra parsing formats, which
     * are used for parsing dates in a Russian locale.
     */
    final static private String[] extraParsingFormatsForLanguage_ru = new String[]{
        "d MMM uuuu"};

    /**
     * monthsNamesForLanguage_ru, This is a constant list of month names, which are used as the
     * default month names in a Russian locale. These strings were supplied by a user who stated
     * that the default Russian month names from DateFormatSymbols had incorrect grammar for the
     * calendar title usage. (The default month names were incorrectly using the genitive case
     * instead of the nominative case.)
     */
    final static private String[] monthsNamesForLanguage_ru = new String[]{"январь", "февраль",
        "март", "апрель", "май", "июнь", "июль", "август", "сентябрь", "октябрь", "ноябрь", "декабрь"};

    /**
     * getExtraParsingFormatsForLocale, This will return a list of extra parsing formatters for the
     * specified locale. If no extra formatters are found, then this will return an empty list.
     * (This will never return null.)
     */
    public static ArrayList<DateTimeFormatter> getExtraParsingFormatsForLocale(Locale locale) {
        // Create some variables that we will need.
        String language = locale.getLanguage();
        String[] definedFormats = null;

        // Get the list of extra parsing formats for the language of the locale.
        if ("en".equals(language)) {
            definedFormats = extraParsingFormatsForLanguage_en;
        }
        if ("ru".equals(language)) {
            definedFormats = extraParsingFormatsForLanguage_ru;
        }

        // If no extra parsing formats were found, then return an empty list.
        ArrayList<DateTimeFormatter> extraParsingFormatters = new ArrayList<>();
        if (definedFormats == null) {
            return extraParsingFormatters;
        }

        // Create the parsing formatters from the defined formats, and add them to the results list.
        DateTimeFormatter formatter;
        for (String formatString : definedFormats) {
            formatter = new DateTimeFormatterBuilder().parseLenient().parseCaseInsensitive().
                    appendPattern(formatString).toFormatter(locale);
            extraParsingFormatters.add(formatter);
        }

        // Return the results.
        return extraParsingFormatters;
    }

    /**
     * getDefaultMonthNamesForLocale, This will return a list of translated month names for the
     * specified locale. Usually, these translations come from java.text.DateFormatSymbols. For some
     * languages, this function may return a list of default override strings instead. This function
     * will always return a list with 12 elements, and each element will always contain a string.
     * This will never return a null array, or any null elements.
     */
    public static String[] getDefaultMonthNamesForLocale(Locale locale) {
        // Use the DateFormatSymbols class to get default month names for the specified language.
        DateFormatSymbols dateSymbols = DateFormatSymbols.getInstance(locale);
        String[] monthNames = dateSymbols.getMonths();

        // When needed, replace the array with overridden translations for particular languages.
        String language = locale.getLanguage();
        if ("ru".equals(language)) {
            monthNames = monthsNamesForLanguage_ru;
            for (int i = 0; i < monthNames.length; ++i) {
                monthNames[i] = DatePickerInternalUtilities.capitalizeFirstLetterOfString(
                        monthNames[i], locale);
            }
        }
        // Return the array of month names.
        return monthNames;
    }
}
