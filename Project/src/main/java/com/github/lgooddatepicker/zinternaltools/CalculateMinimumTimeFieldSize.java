package com.github.lgooddatepicker.zinternaltools;

import java.awt.Font;
import java.awt.FontMetrics;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import javax.swing.JTextField;

/**
 * CalculateMinimumTimeFieldSize, This class is used to calculate the minimum horizontal size needed
 * for text fields that hold times. (Such as the text field in the TimePicker component.)
 *
 * The size that is returned is designed to be the smallest size that will hold the longest
 * displayable time under current TimePickerSettings, without cropping the text. The "longest time",
 * needs to take into account the following factors: The longest (in pixels) time in the current
 * display format, and the font object for valid times (including the font type and size). Note that
 * the locale information is built into the display format.
 */
public class CalculateMinimumTimeFieldSize {

    /**
     * getFormattedDateWidthInPixels, This returns the width (in pixels) of the longest formatted
     * time, using the supplied DateTimeFormatter instance and font. Note that the locale
     * information is built into the display format.
     *
     * You may optionally add extra characters to the longestTimeString that is used in the
     * calculation, by supplying a nonzero value for the parameter numberOfExtraCharacters. This
     * parameter can also be used with a negative value to reduce the size that is returned.
     */
    static public int getFormattedTimeWidthInPixels(DateTimeFormatter formatForDisplayTime,
            Font fontValidTime, int numberOfExtraCharacters) {
        // Create the font metrics that will be used in the calculation.
        JTextField textField = new JTextField();
        FontMetrics fontMetrics = textField.getFontMetrics(fontValidTime);
        // Create the longest possible time.
        LocalTime longestTime = LocalTime.of(22, 59, 59, 999999999);
        // Generate the longest time string. Note, The locale is built into the formatter instance.
        String longestTimeString = longestTime.format(formatForDisplayTime);
        // Get the width of the longest time string (in pixels), using the supplied font metrics.
        int longestTimeWidth = fontMetrics.stringWidth(longestTimeString);
        // Add space for two characters.
        int singleNumericCharacterWidth = fontMetrics.stringWidth("8");
        longestTimeWidth += (2 * singleNumericCharacterWidth);
        // Don't return return anything less than 50 pixels by default.
        longestTimeWidth = (longestTimeWidth < 50) ? 50 : longestTimeWidth;
        // If requested, pad the result with space for any (programmer specified) extra characters.
        longestTimeWidth += (numberOfExtraCharacters * singleNumericCharacterWidth);
        // Return the width of the longest formatted time, in pixels.
        return longestTimeWidth;
    }

}
