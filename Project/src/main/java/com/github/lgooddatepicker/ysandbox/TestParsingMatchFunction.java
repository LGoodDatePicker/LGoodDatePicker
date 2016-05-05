package com.github.lgooddatepicker.ysandbox;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * testParsingMatchFunction, This class was written to thoroughly test the effectiveness of the
 * DatePickerUtilities.doesParsedDateMatchText() function. This class is not involved with the
 * normal operation of the date pickers.
 */
public class TestParsingMatchFunction {

    /**
     * main, This only exists to run test functions.
     */
    public static void main(String[] args) {
        testDoesParsedDateMatchTextFunction();
    }

    /**
     * testDoesParsedDateMatchTextFunction, Test for the function doesParsedDateMatchText(). This
     * test every valid date between years -10000 and 10000, and also tests all the invalid dates up
     * to the 31st of short months, in the same range. The tests are done with both alphabetic and
     * numeric months, and done with BC dates (BC dates have years that are offset from ISO). As of
     * this writing, the function correctly handles every tested date.
     */
    public static void testDoesParsedDateMatchTextFunction() {
        /**
         * February - 28 days; 29 days in Leap Years, April - 30 days, June - 30 days, September -
         * 30 days, November - 30 days.
         */
        DateTimeFormatter parseFormat = new DateTimeFormatterBuilder().parseLenient().parseCaseInsensitive().
                appendPattern("M d, u").toFormatter(Locale.getDefault());
        DateTimeFormatter parseFormat2 = new DateTimeFormatterBuilder().parseLenient().parseCaseInsensitive().
                appendPattern("MMMM d, u").toFormatter(Locale.getDefault());
        DateTimeFormatter parseFormatBC = new DateTimeFormatterBuilder().parseLenient().parseCaseInsensitive().
                appendPattern("MMMM d, yyyy G").toFormatter(Locale.getDefault());
        Month[] shortMonths = new Month[]{Month.FEBRUARY, Month.APRIL, Month.JUNE, Month.SEPTEMBER, Month.NOVEMBER};
        // Make sure that none of short month dates match when given a nonexistent 31st date.
        for (int year = -10000; year < 10001; ++year) {
            for (int monthIndex = 0; monthIndex < shortMonths.length; ++monthIndex) {
                String nonExistantDateString = shortMonths[monthIndex].getValue() + " 31, " + year;
                LocalDate nonExistantDate = LocalDate.parse(nonExistantDateString, parseFormat);
                if (doesParsedDateMatchText(nonExistantDate, nonExistantDateString, Locale.getDefault())) {
                    System.out.println("invalid match at " + nonExistantDateString);
                }
                String nonExistantDateString2 = shortMonths[monthIndex].name() + " 31, " + year;
                LocalDate nonExistantDate2 = LocalDate.parse(nonExistantDateString2, parseFormat2);
                if (doesParsedDateMatchText(nonExistantDate2, nonExistantDateString2, Locale.getDefault())) {
                    System.out.println("invalid match at " + nonExistantDateString2);
                }
            }
        }
        // Make sure that February never matches when given a nonexistent 30th date.
        for (int year = -10000; year < 10001; ++year) {
            String nonExistantDateString = Month.FEBRUARY.getValue() + " 30, " + year;
            LocalDate nonExistantDate = LocalDate.parse(nonExistantDateString, parseFormat);
            if (doesParsedDateMatchText(nonExistantDate, nonExistantDateString, Locale.getDefault())) {
                System.out.println("invalid match at " + nonExistantDateString);
            }
            String nonExistantDateString2 = Month.FEBRUARY.name() + " 30, " + year;
            LocalDate nonExistantDate2 = LocalDate.parse(nonExistantDateString2, parseFormat2);
            if (doesParsedDateMatchText(nonExistantDate2, nonExistantDateString2, Locale.getDefault())) {
                System.out.println("invalid match at " + nonExistantDateString2);
            }
        }
        // Make sure that February never matches when given a (nonexistent) 29th date which 
        // is not on a leap year.
        for (int year = -10000; year < 10001; ++year) {
            if (!isLeapYear(year)) {
                String nonExistantDateString = Month.FEBRUARY.getValue() + " 29, " + year;
                LocalDate nonExistantDate = LocalDate.parse(nonExistantDateString, parseFormat);
                if (doesParsedDateMatchText(nonExistantDate, nonExistantDateString, Locale.getDefault())) {
                    System.out.println("invalid match at " + nonExistantDateString);
                }
                String nonExistantDateString2 = Month.FEBRUARY.name() + " 29, " + year;
                LocalDate nonExistantDate2 = LocalDate.parse(nonExistantDateString2, parseFormat2);
                if (doesParsedDateMatchText(nonExistantDate2, nonExistantDateString2, Locale.getDefault())) {
                    System.out.println("invalid match at " + nonExistantDateString2);
                }
            }
        }
        // Make sure that all valid dates give a match.
        LocalDate validDate = LocalDate.of(-10000, 1, 1);
        while (validDate.getYear() < 10001) {
            if (!doesParsedDateMatchText(validDate, validDate.toString(), Locale.getDefault())) {
                System.out.println("invalid mismatch at " + validDate.toString());
            }
            String alphabeticDate = validDate.format(parseFormat2);
            if (!doesParsedDateMatchText(validDate, alphabeticDate, Locale.getDefault())) {
                System.out.println("invalid mismatch at " + validDate.toString());
            }
            validDate = validDate.plusDays(1);
        }
        // Make sure that valid BC formatted dates give a match.
        LocalDate validDateBC = LocalDate.of(-10000, 1, 1);
        while (validDateBC.getYear() < 5) {
            String alphabeticDateBC = validDateBC.format(parseFormatBC);
            if (!doesParsedDateMatchText(validDateBC, alphabeticDateBC, Locale.getDefault())) {
                System.out.println("invalid mismatch at " + validDateBC.toString());
            }
            validDateBC = validDateBC.plusDays(1);
        }
        // Indicate that we are finished.
        System.out.println("done.");
    }

    static private boolean isLeapYear(int year) {
        if (year % 4 != 0) {
            return false;
        } else if (year % 400 == 0) {
            return true;
        } else if (year % 100 == 0) {
            return false;
        }
        return true;
    }

    /**
     * Note, this function has been thoroughly tested and gives the proper result with all valid and
     * invalid dates in the years between -10000 and 10000 inclusive. Invalid dates are defined as:
     * The 31st day of February, April, June, September, or November. The 30th day of February. Or
     * the 29th day of February on any year that is not a leap year.
     */
    static private boolean doesParsedDateMatchText(LocalDate parsedDate, String text,
            Locale formatLocale) {
        if (parsedDate == null || text == null) {
            return false;
        }
        text = text.toLowerCase();
        // This only matches numbers, and it leaves off any hyphen "-".
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(text);
        ArrayList<String> unsignedNumbersFound = new ArrayList<String>();
        while (matcher.find()) {
            String foundString = matcher.group();
            foundString = forceTwoDigitNumberString(foundString);
            unsignedNumbersFound.add(foundString);
        }
        String parsedDayOfMonth = "" + parsedDate.getDayOfMonth();
        parsedDayOfMonth = forceTwoDigitNumberString(parsedDayOfMonth);
        boolean dayOfMonthFound = unsignedNumbersFound.remove(parsedDayOfMonth);

        DateTimeFormatter formatBC = DateTimeFormatter.ofPattern("G", formatLocale);
        String eraBCString = LocalDate.of(-100, 1, 1).format(formatBC).toLowerCase();
        if (parsedDate.getYear() < 1 && text.contains(eraBCString)) {
            String parsedYearForBC = "" + (parsedDate.getYear() - 1);
            parsedYearForBC = parsedYearForBC.replace("-", "");
            parsedYearForBC = forceTwoDigitNumberString(parsedYearForBC);
            boolean yearFoundForBC = unsignedNumbersFound.remove(parsedYearForBC);
            return yearFoundForBC && dayOfMonthFound;
        } else {
            String parsedYear = "" + parsedDate.getYear();
            parsedYear = parsedYear.replace("-", "");
            parsedYear = forceTwoDigitNumberString(parsedYear);
            boolean yearFound = unsignedNumbersFound.remove(parsedYear);
            return yearFound && dayOfMonthFound;
        }
    }

    private static String forceTwoDigitNumberString(String text) {
        while (text.length() < 2) {
            text = "0" + text;
        }
        if (text.length() > 2) {
            text = text.substring(text.length() - 2, text.length());
        }
        return text;
    }
}
