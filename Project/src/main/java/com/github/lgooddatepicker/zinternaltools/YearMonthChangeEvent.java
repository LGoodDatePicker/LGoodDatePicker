package com.github.lgooddatepicker.zinternaltools;

import com.github.lgooddatepicker.components.CalendarPanel;
import com.github.lgooddatepicker.optionalusertools.PickerUtilities;
import java.time.YearMonth;

/**
 * YearMonthChangeEvent, An instance of this event class is passed to each registered
 * CalendarListener, each time that the YearMonth may have been changed in a CalendarPanel.
 * Developers should call isDuplicate() to find out if the new YearMonth, is the same or different
 * from the old YearMonth.
 */
public class YearMonthChangeEvent {

    /**
     * Constructor.
     */
    public YearMonthChangeEvent(CalendarPanel source,
            YearMonth newYearMonth, YearMonth oldYearMonth) {
        this.source = source;
        this.newYearMonth = newYearMonth;
        this.oldYearMonth = oldYearMonth;
    }

    /**
     * source, This is the calendar panel that generated the event.
     */
    private CalendarPanel source;

    /**
     * newYearMonth, This holds the value of the new YearMonth.
     */
    private YearMonth newYearMonth;

    /**
     * oldYearMonth, This holds the value of the old YearMonth.
     */
    private YearMonth oldYearMonth;

    /**
     * getSource, Returns the calendar panel that generated the event.
     */
    public CalendarPanel getSource() {
        return source;
    }

    /**
     * getNewYearMonth, Returns the new YearMonth. This will never return null.
     */
    public YearMonth getNewYearMonth() {
        return newYearMonth;
    }

    /**
     * getOldYearMonth, Returns the old YearMonth. This will never return null.
     */
    public YearMonth getOldYearMonth() {
        return oldYearMonth;
    }

    /**
     * isDuplicate, Returns true if the new YearMonth is the same as the old YearMonth. Otherwise
     * returns false.
     */
    public boolean isDuplicate() {
        return (PickerUtilities.isSameYearMonth(newYearMonth, oldYearMonth));
    }

}
