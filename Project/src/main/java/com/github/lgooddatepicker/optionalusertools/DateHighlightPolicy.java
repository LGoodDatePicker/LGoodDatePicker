package com.github.lgooddatepicker.optionalusertools;

import java.time.*;
import com.github.lgooddatepicker.zinternaltools.HighlightInformation;

/**
 * HighlightPolicy, A highlight policy can be implemented to highlight certain dates in your
 * DatePicker or CalendarPanel. A highlight policy might be used to visually indicate holidays, or
 * weekends, or other significant days.
 *
 * Each highlighted date may (optionally) have a unique background color, foreground color, and
 * tooltip text. If unique colors are not supplied, then the default highlighting colors will be
 * used instead. If a tooltip is not supplied, then no tooltip will be displayed. See the demo class
 * for an example of implementing a HighlightPolicy.
 */
public interface DateHighlightPolicy {

    /**
     * getHighlightInformationOrNull, Implement this function to indicate if a date should be
     * highlighted, and what highlighting details should be used for the highlighted date.
     *
     * If a date should be highlighted, then return an instance of HighlightInformation. If the date
     * should not be highlighted, then return null.
     *
     * You may (optionally) fill out the fields in the HighlightInformation class to give any
     * particular highlighted day a unique background color, foreground color, or tooltip text. If
     * the color fields are null, then the default highlighting colors will be used. If the tooltip
     * field is null (or empty), then no tooltip will be displayed.
     *
     * Dates that are passed to this function will never be null.
     */
    abstract public HighlightInformation getHighlightInformationOrNull(LocalDate date);

}
