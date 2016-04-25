package com.github.lgooddatepicker.zinternaltools;

import java.awt.Color;

/**
 * HighlightInformation, Instances of this class are returned from a HighlightPolicy to indicate
 * that a date should be highlighted. When a date should not be highlighted, then the
 * HighlightPolicy should return null.
 *
 * You may (optionally) fill out the fields in the HighlightInformation class to give any particular
 * highlighted day a unique background color, foreground color, or tooltip text. If the color fields
 * are null, then the default highlighting colors will be used. If the tooltip field is null (or
 * empty), then no tooltip will be displayed.
 */
public class HighlightInformation {

    /**
     * colorBackground, This will be used as the background on the highlight date. If this field is
     * null, then a default color will be used. The default color is retrieved from
     * "DatePickerSettings.colorBackgroundHighlightedDates". (If the default has not been changed,
     * it will be "Color.green".)
     */
    public Color colorBackground = null;

    /**
     * colorText, This will be used to color the text (the date number) on the highlight date. If
     * this field is null, then a default color will be used. The default is "Color.black".
     */
    public Color colorText = null;

    /**
     * tooltipText, This text will be displayed as a "hover tooltip" on the highlighted date. If
     * this field is null or empty, then no tooltip will be displayed.
     */
    public String tooltipText = null;

    /**
     * Constructor, Default.
     */
    public HighlightInformation() {
        this(null, null, null);
    }

    /**
     * Constructor, With a background color only.
     */
    public HighlightInformation(Color colorBackground) {
        this(colorBackground, null, null);
    }

    /**
     * Constructor, With all color fields.
     */
    public HighlightInformation(Color colorBackground, Color colorText) {
        this(colorBackground, colorText, null);
    }

    /**
     * Constructor, With all fields.
     */
    public HighlightInformation(Color colorBackground, Color colorText, String tooltipText) {
        this.colorBackground = colorBackground;
        this.colorText = colorText;
        this.tooltipText = tooltipText;
    }
}
