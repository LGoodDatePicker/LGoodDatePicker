package com.github.lgooddatepicker.optionalusertools;

import com.github.lgooddatepicker.zinternaltools.CalendarSelectionEvent;

/**
 * CalendarSelectionListener, This interface can be implemented to create a calendar selection
 * listener. Any calendar selection listeners that are registered with a CalendarPanel will be
 * notified each time that a calendar date is selected.
 */
public interface CalendarSelectionListener {

    /**
     * selectionChanged, This function will be called each time that a date is selected in the
     * applicable CalendarPanel. The selected date is supplied in the event object. Note that the
     * selected date may contain null, which represents a cleared or empty date.
     *
     * This function will be called each time that the user selects any date or the clear button in
     * the calendar, even if the same value is selected twice in a row. All duplicate selection
     * events are marked as duplicates in the event object.
     */
    public void selectionChanged(CalendarSelectionEvent event);
}
