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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Locale;
import org.junit.Test;

/**
 * testParsingMatchFunction, This class was written to thoroughly test the effectiveness of the
 * DatePickerUtilities.doesParsedDateMatchText() function. This class is not involved with the
 * normal operation of the date pickers.
 */
public class TestParsingMatchFunction {

  /** main, This only exists to run test functions. */
  public static void main(String[] args) {
    try {
      new TestParsingMatchFunction().testDoesParsedDateMatchTextFunction();
      // Indicate that we are finished.
      System.out.println("done.");
    } catch (java.lang.AssertionError ex) {
      System.out.println("Test failed:");
      System.out.println("Reason: " + ex.getMessage());
    }
  }

  /**
   * testDoesParsedDateMatchTextFunction, Test for the function doesParsedDateMatchText(). This test
   * every valid date between years -10000 and 10000, and also tests all the invalid dates up to the
   * 31st of short months, in the same range. The tests are done with both alphabetic and numeric
   * months, and done with BC dates (BC dates have years that are offset from ISO). As of this
   * writing, the function correctly handles every tested date.
   */
  @Test(expected = Test.None.class /* no exception expected */)
  public void testDoesParsedDateMatchTextFunction() {
    /**
     * February - 28 days; 29 days in Leap Years, April - 30 days, June - 30 days, September - 30
     * days, November - 30 days.
     */
    DateTimeFormatter parseFormat =
        new DateTimeFormatterBuilder()
            .parseLenient()
            .parseCaseInsensitive()
            .appendPattern("M d, u")
            .toFormatter(Locale.getDefault());
    DateTimeFormatter parseFormat2 =
        new DateTimeFormatterBuilder()
            .parseLenient()
            .parseCaseInsensitive()
            .appendPattern("MMMM d, u")
            .toFormatter(Locale.ENGLISH);
    DateTimeFormatter parseFormatBC =
        new DateTimeFormatterBuilder()
            .parseLenient()
            .parseCaseInsensitive()
            .appendPattern("MMMM d, yyyy G")
            .toFormatter(Locale.getDefault());
    Month[] shortMonths = {
      Month.FEBRUARY, Month.APRIL, Month.JUNE, Month.SEPTEMBER, Month.NOVEMBER
    };
    // Make sure that none of short month dates match when given a nonexistent 31st date.
    for (int year = -10000; year < 10001; ++year) {
      for (Month shortMonth : shortMonths) {
        String nonExistantDateString = shortMonth.getValue() + " 31, " + year;
        LocalDate nonExistantDate = LocalDate.parse(nonExistantDateString, parseFormat);
        assertFalse(
            "invalid match at " + nonExistantDateString,
            InternalUtilities.doesParsedDateMatchText(
                nonExistantDate, nonExistantDateString, parseFormat));
        String nonExistantDateString2 = shortMonth.name() + " 31, " + year;
        LocalDate nonExistantDate2 = LocalDate.parse(nonExistantDateString2, parseFormat2);
        assertFalse(
            "invalid match at " + nonExistantDateString2,
            InternalUtilities.doesParsedDateMatchText(
                nonExistantDate2, nonExistantDateString2, parseFormat2));
      }
    }
    // Make sure that February never matches when given a nonexistent 30th date.
    for (int year = -10000; year < 10001; ++year) {
      String nonExistantDateString = Month.FEBRUARY.getValue() + " 30, " + year;
      LocalDate nonExistantDate = LocalDate.parse(nonExistantDateString, parseFormat);
      assertFalse(
          "invalid match at " + nonExistantDateString,
          InternalUtilities.doesParsedDateMatchText(
              nonExistantDate, nonExistantDateString, parseFormat));
      String nonExistantDateString2 = Month.FEBRUARY.name() + " 30, " + year;
      LocalDate nonExistantDate2 = LocalDate.parse(nonExistantDateString2, parseFormat2);
      assertFalse(
          "invalid match at " + nonExistantDateString2,
          InternalUtilities.doesParsedDateMatchText(
              nonExistantDate2, nonExistantDateString2, parseFormat2));
    }
    // Make sure that February never matches when given a (nonexistent) 29th date which
    // is not on a leap year.
    for (int year = -10000; year < 10001; ++year) {
      if (!isLeapYear(year)) {
        String nonExistantDateString = Month.FEBRUARY.getValue() + " 29, " + year;
        LocalDate nonExistantDate = LocalDate.parse(nonExistantDateString, parseFormat);
        assertFalse(
            "invalid match at " + nonExistantDateString,
            InternalUtilities.doesParsedDateMatchText(
                nonExistantDate, nonExistantDateString, parseFormat));
        String nonExistantDateString2 = Month.FEBRUARY.name() + " 29, " + year;
        LocalDate nonExistantDate2 = LocalDate.parse(nonExistantDateString2, parseFormat2);
        assertFalse(
            "invalid match at " + nonExistantDateString2,
            InternalUtilities.doesParsedDateMatchText(
                nonExistantDate2, nonExistantDateString2, parseFormat2));
      }
    }
    // Make sure that all valid dates give a match.
    LocalDate validDate = LocalDate.of(-10000, 1, 1);
    while (validDate.getYear() < 10001) {
      assertTrue(
          "invalid mismatch at " + validDate.toString(),
          InternalUtilities.doesParsedDateMatchText(
              validDate, validDate.toString(), DateTimeFormatter.ISO_DATE));
      String alphabeticDate = validDate.format(parseFormat2);
      assertTrue(
          "invalid mismatch at " + validDate.toString(),
          InternalUtilities.doesParsedDateMatchText(validDate, alphabeticDate, parseFormat2));
      validDate = validDate.plusDays(1);
    }
    // Make sure that valid BC formatted dates give a match.
    LocalDate validDateBC = LocalDate.of(-10000, 1, 1);
    while (validDateBC.getYear() < 5) {
      String alphabeticDateBC = validDateBC.format(parseFormatBC);
      assertTrue(
          "invalid mismatch at " + validDateBC.toString(),
          InternalUtilities.doesParsedDateMatchText(validDateBC, alphabeticDateBC, parseFormatBC));
      validDateBC = validDateBC.plusDays(1);
    }
    // Check that Issue #60 was actually resolved
    checkDate("ddMMyyyy", "30062019", true);
    checkDate("ddMMyyyy", "31062019", false);
    checkDate("ddMMuuuu", "30062019", true);
    checkDate("ddMMuuuu", "31062019", false);
    checkDate("ddMMyyyyG", "30062019BC", true);
    checkDate("ddMMyyyyG", "31062019BC", false);
    checkDate("ddMMyyyyG", "30062019AD", true);
    checkDate("ddMMyyyyG", "31062019AD", false);
    checkDate("ddMMuuuuG", "3006-2019BC", true);
    checkDate("ddMMuuuuG", "3106-2019BC", false);
    checkDate("ddMMuuuuG", "30062019AD", true);
    checkDate("ddMMuuuuG", "31062019AD", false);
  }

  private static boolean isLeapYear(int year) {
    if (year % 4 != 0) {
      return false;
    } else if (year % 400 == 0) {
      return true;
    } else if (year % 100 == 0) {
      return false;
    }
    return true;
  }

  private static void checkDate(String dateformat, String dateValue, boolean isValidDate) {
    final DateTimeFormatter formatter =
        new DateTimeFormatterBuilder().appendPattern(dateformat).toFormatter(Locale.ENGLISH);
    final LocalDate parsedDate = LocalDate.parse(dateValue, formatter);
    final boolean determinedValidity =
        InternalUtilities.doesParsedDateMatchText(parsedDate, dateValue, formatter);
    assertFalse("Invalid match at " + dateValue, determinedValidity && !isValidDate);
    assertFalse("Invalid mismatch at " + dateValue, !determinedValidity && isValidDate);
  }
}
