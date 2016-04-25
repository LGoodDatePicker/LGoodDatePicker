package com.github.lgooddatepicker.optionalusertools;

import com.github.lgooddatepicker.zinternaltools.CalendarSelectionEvent;

/**
 * CalendarSelectionListener, This interface can be implemented to create a calendar selection
 * listener. Any calendar selection listeners that are registered with a CalendarPanel will be
 * notified each time that a calendar date is selected.
 */
public interface CalendarSelectionListener {

    /**
     * dateSelectedInCalendar, This function will be called each time that a date is selected in the
     * applicable CalendarPanel. The selected date is supplied in the event object. Note that the
     * selected date may contain null, which represents a cleared or empty date.
     */
    public void dateSelectedInCalendar(CalendarSelectionEvent event);
}
