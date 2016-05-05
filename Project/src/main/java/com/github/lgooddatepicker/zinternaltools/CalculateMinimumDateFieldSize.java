package com.github.lgooddatepicker.zinternaltools;

import java.awt.Font;
import java.awt.FontMetrics;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import javax.swing.JTextField;

/**
 * CalculateMinimumDateFieldSize, This class is used to calculate the minimum horizontal size needed
 * for text fields that hold dates. (Such as the text field in the DatePicker component.)
 *
 * The size that is returned is designed to be the smallest size that will hold the longest
 * displayable date under current DatePickerSettings, without cropping the text. The "longest date",
 * needs to take into account the following factors: The longest (in pixels) month name in the
 * current locale, the font object for valid dates (including the font type and size), and the
 * currently set display format for AD dates.
 */
public class CalculateMinimumDateFieldSize {

    /**
     * getFormattedDateWidthInPixels, This returns the width (in pixels) of the longest formatted
     * date, using the supplied DateTimeFormatter instance, locale, and font.
     *
     * The month that will be used for the length calculation will be the "longest text month"
     * according to the function getLongestTextMonthInLocale().
     *
     * You may optionally add extra characters to the longestDateString that is used in the
     * calculation, by supplying a nonzero value for the parameter numberOfExtraCharacters.
     *
     * formatCE: This is the date format that should be used in the calculation. Longer (wider)
     * formats will result in wider printed dates.
     *
     * locale: This is the locale that you wish to use in the calculation. The width of the date
     * string (and the width of the month names) will be different depending on the locale that is
     * used to translate and format the date.
     *
     * fontValidDate: This will be used to generate the font metrics for the calculation. Larger
     * font types, and larger font sizes, will result in wider printed dates.
     *
     * numberOfExtraCharacters: This is the number of "extra" characters that you want to have used
     * in calculating the width of the longest formatted date. If you don't wish for any extra
     * characters to be used in the calculation, please supply zero for this value. If you wish to
     * -shorten- the default date string, you may also supply a negative number of characters for
     * this value.
     *
     * Implementation details: Note that any formatted date could be made longer by using years
     * greater than four digits, or by using BC years. This function assumes four digit Common Era
     * years, in performing its calculations.
     */
    static public int getFormattedDateWidthInPixels(DateTimeFormatter formatCE, Locale locale,
            Font fontValidDate, int numberOfExtraCharacters) {
        // Create the font metrics that will be used in the calculation.
        JTextField textField = new JTextField();
        FontMetrics fontMetrics = textField.getFontMetrics(fontValidDate);
        // Calculate the "longest text month".
        Month longestTextMonth = getLongestTextMonthInLocale(locale, fontMetrics);
        // Create the longest dates with a text month format, and a numeric month format.
        // Both dates will have a four digit year, and a two digit day of the month.
        LocalDate longestTextDate = LocalDate.of(2000, longestTextMonth, 28);
        LocalDate longestNumericDate = LocalDate.of(2000, Month.DECEMBER, 28);
        // Generate the long date strings. Note, The locale is built into the formatter instance.
        String longestTextDateString = longestTextDate.format(formatCE);
        String longestNumericDateString = longestNumericDate.format(formatCE);
        // Get the width of the longest date string (in pixels), using the supplied font metrics.
        int textDateWidth = fontMetrics.stringWidth(longestTextDateString);
        int numericDateWidth = fontMetrics.stringWidth(longestNumericDateString);
        int longestDateWidth = Math.max(textDateWidth, numericDateWidth);
        // Add space for two characters, because one character appears to be needed, and one more
        // allows room for BC dates.
        int singleNumericCharacterWidth = fontMetrics.stringWidth("8");
        longestDateWidth += (2 * singleNumericCharacterWidth);
        // If requested, pad the result with space for any (programmer specified) extra characters.
        longestDateWidth += (numberOfExtraCharacters * singleNumericCharacterWidth);
        // Return the width of the longest formatted date, in pixels.
        return longestDateWidth;
    }

    /**
     * getLongestTextMonthInLocale,
     *
     * For the supplied locale, this returns the month that has the longest translated, "formatting
     * version", "long text version" month name. The version of the month name string that is used
     * for comparison is further defined below.
     *
     * Note that this does not return the longest month for numeric month date formats. The longest
     * month in entirely numeric formats is always December. (Month number 12).
     *
     * The compared month names are the "formatting version" of the translated month names (not the
     * standalone version). In some locales such as Russian and Czech, the formatting version can be
     * different from the standalone version. The "formatting version" is the name of the month that
     * would be used in a formatted date. The standalone version is only used when the month is
     * displayed by itself.
     *
     * The month names that are used for comparison are also the "long version" of the month names,
     * not the short (abbreviated) version.
     *
     * The translated month names are compared using the supplied font metrics.
     *
     * This returns the longest month, as defined by the above criteria. If two or more months are
     * "tied" as the "longest month", then the longest month that is closest to the end of the year
     * will be the one that is returned.
     */
    static private Month getLongestTextMonthInLocale(Locale locale, FontMetrics fontMetrics) {
        // Get the "formatting names" of all the months for this locale.
        // Request the capitalized long version of the translated month names.
        String[] formattingMonthNames = ExtraDateStrings.getFormattingMonthNamesArray(
                locale, true, false);
        // Find out which month is longest, using the supplied font metrics.
        int longestMonthWidth = 0;
        Month longestMonth = Month.JANUARY;
        for (int i = 0; i < formattingMonthNames.length; ++i) {
            int currentMonthWidth = fontMetrics.stringWidth(formattingMonthNames[i]);
            if (currentMonthWidth >= longestMonthWidth) {
                int oneBasedMonthIndex = (i + 1);
                longestMonth = Month.of(oneBasedMonthIndex);
                longestMonthWidth = currentMonthWidth;
            }
        }
        return longestMonth;
    }

}
