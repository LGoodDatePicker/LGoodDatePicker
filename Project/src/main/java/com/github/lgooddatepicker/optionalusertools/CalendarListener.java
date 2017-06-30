package com.github.lgooddatepicker.optionalusertools;

import com.github.lgooddatepicker.zinternaltools.CalendarSelectionEvent;
import com.github.lgooddatepicker.zinternaltools.YearMonthChangeEvent;

/**
 * CalendarListener, This interface can be implemented to create a calendar listener. Any calendar
 * listeners that are registered with a CalendarPanel will be notified each time that a calendar
 * date is selected, or if the YearMonth is changed.
 */
public interface CalendarListener {

    /**
     * selectedDateChanged, This function will be called each time that a date is selected in the
     * applicable CalendarPanel. The selected date is supplied in the event object. Note that the
     * selected date may contain null, which represents a cleared or empty date.
     *
     * This function will be called each time that the user selects any date or the clear button in
     * the calendar, even if the same date value is selected twice in a row. All duplicate selection
     * events are marked as duplicates in the event object.
     */
    public void selectedDateChanged(CalendarSelectionEvent event);

    /**
     * yearMonthChanged, This function will be called each time that the YearMonth may have changed
     * in the applicable CalendarPanel. The selected YearMonth is supplied in the event object.
     *
     * This function will be called each time that the user makes any change that requires redrawing
     * the calendar, even if the same YearMonth value is displayed twice in a row. All duplicate
     * yearMonthChanged events are marked as duplicates in the event object.
     *
     * Implementation Note: This is called each time the calendar is redrawn.
     */
    public void yearMonthChanged(YearMonthChangeEvent event);
}
