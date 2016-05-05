package com.github.lgooddatepicker.zinternaltools;

import java.text.DateFormatSymbols;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.TextStyle;
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
     * monthsNamesForLanguage_ru, This is a constant list of "standalone" month names, for the
     * Russian locale. This was previously used to supply the Russian month names, but now that this
     * class has a generalized solution for getting the standalone month names in all languages,
     * this array should only be used for visual reference. This can be used for comparison to
     * ensure that the general solution is functioning correctly.
     */
    final static public String[] monthsNamesForLanguage_ru = new String[]{"январь", "февраль",
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
        ArrayList<DateTimeFormatter> extraParsingFormatters = new ArrayList<DateTimeFormatter>();
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
     * getDefaultStandaloneLongMonthNamesForLocale, This will return a list of capitalized,
     * translated, standalone month names for the specified locale. This function will always return
     * a list with 12 elements, and each element will always contain a string. This will never
     * return a null array, or any null elements.
     *
     * Implementation note: it was previously required to override the month names in certain
     * languages such as Russian, to get the proper grammar. At this point, a generalized solution
     * has been implemented for all languages. It is assumed that this solution is working
     * correctly, unless and until someone reports that it is not working correctly for their
     * language.
     */
    public static String[] getDefaultStandaloneLongMonthNamesForLocale(Locale locale) {
        // Get the standalone version of the month names for the specified language.
        String[] monthNames = getStandaloneMonthNamesArray(locale, true, false);
        // Return the array of month names.
        return monthNames;
    }

    public static String[] getDefaultStandaloneShortMonthNamesForLocale(Locale locale) {
        // Get the standalone version of the month names for the specified language.
        String[] monthNames = getStandaloneMonthNamesArray(locale, true, true);
        // Return the array of month names.
        return monthNames;
    }

    /**
     * getStandaloneMonthName, This returns a "standalone version" month name for the specified
     * month, in the specified locale. In some languages, including Russian and Czech, the
     * standalone version of the month name is different from the version of the month name you
     * would use as part of a full date. (Is different from the formatting version).
     *
     * This tries to get the standalone version first. If no mapping is found for a standalone
     * version (Presumably because the supplied language has no standalone version), then this will
     * return the formatting version of the month name.
     */
    private static String getStandaloneMonthName(Month month, Locale locale, boolean capitalize,
            boolean shortVersion) {
        // Attempt to get the standalone version of the month name.
        TextStyle style = (shortVersion) ? TextStyle.SHORT_STANDALONE : TextStyle.FULL_STANDALONE;
        String monthName = month.getDisplayName(style, locale);
        String monthNumber = "" + month.getValue();
        // If no mapping was found, then get the "formatting version" of the month name.
        if (monthName.equals(monthNumber)) {
            DateFormatSymbols dateSymbols = DateFormatSymbols.getInstance(locale);
            if (shortVersion) {
                monthName = dateSymbols.getShortMonths()[month.getValue() - 1];
            } else {
                monthName = dateSymbols.getMonths()[month.getValue() - 1];
            }
        }
        // If needed, capitalize the month name.
        if ((capitalize) && (monthName != null) && (monthName.length() > 0)) {
            monthName = monthName.substring(0, 1).toUpperCase(locale) + monthName.substring(1);
        }
        return monthName;
    }

    /**
     * getStandaloneMonthNamesArray, This returns an array with the standalone version of all the
     * full month names.
     */
    private static String[] getStandaloneMonthNamesArray(Locale locale, boolean capitalize,
            boolean shortVersion) {
        Month[] monthEnums = Month.values();
        ArrayList<String> monthNamesArrayList = new ArrayList<String>();
        for (Month monthEnum : monthEnums) {
            monthNamesArrayList.add(getStandaloneMonthName(monthEnum, locale, capitalize, shortVersion));
        }
        // Convert the arraylist to a string array, and return the array.
        String[] monthNames = monthNamesArrayList.toArray(new String[]{});
        return monthNames;
    }

    /**
     * getFormattingMonthName, This returns a "formatting version" month name for the specified
     * month, in the specified locale. In some languages, including Russian and Czech, the
     * standalone version of the month name is different from the version of the month name you
     * would use as part of a full date. (Is different from the formatting version).
     */
    private static String getFormattingMonthName(Month month, Locale locale, boolean capitalize,
            boolean shortVersion) {
        // Get the "formatting version" of the month name.
        DateFormatSymbols dateSymbols = DateFormatSymbols.getInstance(locale);
        String monthName;
        if (shortVersion) {
            monthName = dateSymbols.getShortMonths()[month.getValue() - 1];
        } else {
            monthName = dateSymbols.getMonths()[month.getValue() - 1];
        }
        // If needed, capitalize the month name.
        if ((capitalize) && (monthName != null) && (monthName.length() > 0)) {
            monthName = monthName.substring(0, 1).toUpperCase(locale) + monthName.substring(1);
        }
        return monthName;
    }

    /**
     * getFormattingMonthNamesArray, This returns an array with the translated, formatting version
     * of the month names for the specified locale. (The "formatting version" of the month names can
     * be different from the standalone version in some locales, including Russian and Czech. The
     * formatting version would be used in a formatted date, and the standalone version would be
     * used if the month is being specified by itself.)
     *
     * capitalize: This specifies whether the month name should be capitalized or not capitalized.
     * shortVersion: This specifies whether the month names should be the short, or long versions of
     * the formatting month names.
     */
    public static String[] getFormattingMonthNamesArray(Locale locale, boolean capitalize,
            boolean shortVersion) {
        Month[] monthEnums = Month.values();
        ArrayList<String> monthNamesArrayList = new ArrayList<String>();
        for (Month monthEnum : monthEnums) {
            monthNamesArrayList.add(getFormattingMonthName(
                    monthEnum, locale, capitalize, shortVersion));
        }
        // Convert the arraylist to a string array, and return the array.
        String[] monthNames = monthNamesArrayList.toArray(new String[]{});
        return monthNames;
    }
}
