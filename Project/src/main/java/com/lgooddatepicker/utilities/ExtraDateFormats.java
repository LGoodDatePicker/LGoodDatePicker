package com.lgooddatepicker.utilities;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;
import java.util.Locale;

/**
 * ExtraDateFormats, This class holds extra date formats for parsing dates in a particular language.
 * All the fields and functions are static.
 */
public class ExtraDateFormats {

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
}
