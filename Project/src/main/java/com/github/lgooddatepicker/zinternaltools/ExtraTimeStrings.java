/*
 * The MIT License
 *
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.github.lgooddatepicker.zinternaltools;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.Locale;

/**
 * ExtraTimeStrings, This class holds extra time strings. This includes: # Formats for parsing times
 * in a particular language. All the fields and functions are static.
 */
public class ExtraTimeStrings {

  /**
   * extraParsingFormatsForLanguage_en, This is a constant list of extra parsing formats, which are
   * used for parsing times in an English locale.
   */
  private static final String[] extraParsingFormatsForLanguage_en = {"h:ma", "h.ma", "ha"};

  /**
   * getExtraTimeParsingFormatsForLocale, This will return a list of extra parsing formatters for
   * the specified locale. If no extra formatters are found, then this will return an empty list.
   * (This will never return null.)
   */
  public static ArrayList<DateTimeFormatter> getExtraTimeParsingFormatsForLocale(Locale locale) {
    // Create some variables that we will need.
    String language = locale.getLanguage();
    String[] definedFormats = null;

    // Get the list of extra parsing formats for the language of the locale.
    if ("en".equals(language)) {
      definedFormats = extraParsingFormatsForLanguage_en;
    }

    // If no extra parsing formats were found, then return an empty list.
    ArrayList<DateTimeFormatter> extraParsingFormatters = new ArrayList<>();
    if (definedFormats == null) {
      return extraParsingFormatters;
    }

    // Create the parsing formatters from the defined formats, and add them to the results list.
    DateTimeFormatter formatter;
    for (String formatString : definedFormats) {
      formatter =
          new DateTimeFormatterBuilder()
              .parseLenient()
              .parseCaseInsensitive()
              .appendPattern(formatString)
              .toFormatter(locale);
      extraParsingFormatters.add(formatter);
    }

    // Return the results.
    return extraParsingFormatters;
  }

  public static DateTimeFormatter getDefaultFormatForDisplayTime(Locale locale) {
    DateTimeFormatter format =
        new DateTimeFormatterBuilder()
            .parseLenient()
            .parseCaseInsensitive()
            .appendLocalized(null, FormatStyle.SHORT)
            .toFormatter(locale);
    String language = locale.getLanguage();
    if ("en".equals(language)) {
      format =
          new DateTimeFormatterBuilder()
              .parseLenient()
              .parseCaseInsensitive()
              .appendPattern("h:mma")
              .toFormatter(locale);
    }
    return format;
  }

  public static DateTimeFormatter getDefaultFormatForMenuTimes(Locale locale) {
    return getDefaultFormatForDisplayTime(locale);
  }
}
