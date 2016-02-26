package com.lgooddatepicker.support;

import com.lgooddatepicker.optionalusertools.VetoPolicy;
import java.awt.GridBagConstraints;
import java.time.LocalDate;
import java.time.chrono.IsoChronology;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * DatePickerInternalUtilities, This class contains static functions that are used by the date
 * picker or the calendar panel. Some of these functions are large, and were separated out of the
 * date picker class or calendar panel class in order to improve the readability of those classes.
 */
public class DatePickerInternalUtilities {

    /**
     * doesParsedDateMatchText, This compares the numbers in a parsed date, to the original text
     * from which the date was parsed. Specifically this compares the day of the month and the year
     * of the parsed date to the text. On a technical note, this function is not aware of which
     * field is which, but that ambiguity does not prevent it from performing a successful
     * comparison on all tested cases. This will return true if the dates are a match, or otherwise
     * return false.
     *
     * Testing note: This function has been thoroughly tested and gives the proper result with all
     * valid and invalid dates in the years between -10000 and 10000 inclusive. Valid dates are
     * defined as those dates that are returned from the LocalDate class, when using the
     * localDate.plusDays(1) function. Invalid dates are defined as any of the following: The 31st
     * day of February, April, June, September, or November. The 30th day of February. Or the 29th
     * day of February on any year that is not a leap year.
     */
    static public boolean doesParsedDateMatchText(LocalDate parsedDate, String text,
            Locale formatLocale) {
        if (parsedDate == null || text == null) {
            return false;
        }
        text = text.toLowerCase();
        // This only matches numbers, and it does not include any hyphen "-".
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(text);
        ArrayList<String> unsignedNumbersFound = new ArrayList<>();
        while (matcher.find()) {
            String foundString = matcher.group();
            foundString = forceNumberStringToTwoDigits(foundString);
            unsignedNumbersFound.add(foundString);
        }
        String parsedDayOfMonth = "" + parsedDate.getDayOfMonth();
        parsedDayOfMonth = forceNumberStringToTwoDigits(parsedDayOfMonth);
        boolean dayOfMonthFound = unsignedNumbersFound.remove(parsedDayOfMonth);

        DateTimeFormatter formatBC = DateTimeFormatter.ofPattern("G", formatLocale);
        String eraBCString = LocalDate.of(-100, 1, 1).format(formatBC).toLowerCase();
        if (parsedDate.getYear() < 1 && text.contains(eraBCString)) {
            String parsedYearForBC = "" + (parsedDate.getYear() - 1);
            parsedYearForBC = parsedYearForBC.replace("-", "");
            parsedYearForBC = forceNumberStringToTwoDigits(parsedYearForBC);
            boolean yearFoundForBC = unsignedNumbersFound.remove(parsedYearForBC);
            return yearFoundForBC && dayOfMonthFound;
        } else {
            String parsedYear = "" + parsedDate.getYear();
            parsedYear = parsedYear.replace("-", "");
            parsedYear = forceNumberStringToTwoDigits(parsedYear);
            boolean yearFound = unsignedNumbersFound.remove(parsedYear);
            return yearFound && dayOfMonthFound;
        }
    }

    /**
     * forceNumberStringToTwoDigits, This takes a string of digits, and forces it to be two digits
     * long. First any extra leftmost digits are truncated, leaving only the right one digit or
     * right two digits. If there is only one digit, it is zero padded to enforce a two digit string
     * result. This function is used by the DatePickerUtilities.doesParsedDateMatchText() function.
     */
    private static String forceNumberStringToTwoDigits(String text) {
        while (text.length() < 2) {
            text = "0" + text;
        }
        if (text.length() > 2) {
            text = text.substring(text.length() - 2, text.length());
        }
        return text;
    }

    /**
     * generateDefaultDisplayFormatterAD, This returns a default formatter for the specified locale,
     * that can be used for displaying or parsing AD dates. The formatter is generated from the
     * default FormatStyle.LONG formatter in the specified locale.
     */
    public static DateTimeFormatter generateDefaultDisplayFormatterAD(Locale pickerLocale) {
        return DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG).withLocale(pickerLocale);
    }

    /**
     * generateDefaultDisplayFormatterBC, This returns a default formatter for the specified locale,
     * that can be used for displaying or parsing BC dates. The formatter is generated from the
     * default FormatStyle.LONG formatter in the specified locale. The resulting format is intended
     * to be nearly identical to the default formatter used for AD dates.
     */
    public static DateTimeFormatter generateDefaultDisplayFormatterBC(Locale pickerLocale) {
        // This is verified to work for the following locale languages:
        // en, de, fr, pt, ru, it, nl, es, pl, da, ro, sv, zh.
        String displayFormatterBCPattern = DateTimeFormatterBuilder.getLocalizedDateTimePattern(
                FormatStyle.LONG, null, IsoChronology.INSTANCE, pickerLocale);
        displayFormatterBCPattern = displayFormatterBCPattern.replace("y", "u");
        DateTimeFormatter displayFormatterBC = new DateTimeFormatterBuilder().parseLenient()
                .parseCaseInsensitive().appendPattern(displayFormatterBCPattern)
                .toFormatter(pickerLocale);
        return displayFormatterBC;
    }

    /**
     * getParsedDateOrNull, This takes text from the date picker text field, and tries to parse it
     * into a java.time.LocalDate instance. If the text cannot be parsed, this will return null.
     *
     * Implementation note: The DateTimeFormatter parsing class was accepting invalid dates like
     * February 31st, and returning the last valid date of the month, like Feb 28. This could be
     * seen as an attempt to be lenient, but in the context of the date picker class it is
     * considered a mistake or a bug. There was no setting to disable that functionality. So, this
     * function calls another function called doesParsedDateMatchText(), to analyze and reject those
     * kinds of mistakes. If the parsed text does not match the day of the month (and year) of the
     * parsed date, then this function will return null.
     */
    static public LocalDate getParsedDateOrNull(String text, DateTimeFormatter displayFormatterAD,
            DateTimeFormatter displayFormatterBC, ArrayList<DateTimeFormatter> parsingFormatters,
            Locale formatLocale) {
        if (text == null || text.trim().isEmpty()) {
            return null;
        }
        text = text.trim().toLowerCase();
        LocalDate parsedDate = null;
        if (parsedDate == null) {
            try {
                parsedDate = LocalDate.parse(text, displayFormatterAD);
            } catch (Exception ex) {
            }
        }
        if (parsedDate == null) {
            try {
                // Note: each parse attempt must have its own try/catch block. 
                parsedDate = LocalDate.parse(text, displayFormatterBC);
            } catch (Exception ex) {
            }
        }
        for (int i = 0; ((parsedDate == null) && (i < parsingFormatters.size())); ++i) {
            try {
                parsedDate = LocalDate.parse(text, parsingFormatters.get(i));
            } catch (Exception ex) {
            }
        }
        // Check for any "successfully" parsed but nonexistent dates like Feb 31.
        // Note, this function has been thoroughly tested. See the function docs for details.
        if ((parsedDate != null) && (!DatePickerInternalUtilities.doesParsedDateMatchText(
                parsedDate, text, formatLocale))) {
            return null;
        }
        return parsedDate;
    }

    /**
     * capitalizeFirstLetterOfString, This capitalizes the first letter of the supplied string, in a
     * way that is sensitive to the specified locale.
     */
    static String capitalizeFirstLetterOfString(String text, Locale locale) {
        if (text == null || text.length() < 1) {
            return text;
        }
        String textCapitalized = text.substring(0, 1).toUpperCase(locale) + text.substring(1);
        return textCapitalized;
    }

    /**
     * getConstraints, This returns a grid bag constraints object that can be used for placing a
     * component appropriately into a grid bag layout.
     */
    static public GridBagConstraints getConstraints(int gridx, int gridy) {
        GridBagConstraints gc = new GridBagConstraints();
        gc.fill = GridBagConstraints.BOTH;
        gc.gridx = gridx;
        gc.gridy = gridy;
        return gc;
    }

    /**
     * isDateVetoed, This is a convenience function for checking whether or not a particular date is
     * vetoed.
     */
    static public boolean isDateVetoed(VetoPolicy policy, LocalDate date) {
        if (policy == null) {
            return false;
        }
        return (!policy.isDateAllowed(date));
    }

}
