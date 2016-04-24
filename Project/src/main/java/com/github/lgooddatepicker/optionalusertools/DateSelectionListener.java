package com.github.lgooddatepicker.optionalusertools;

import com.github.lgooddatepicker.zinternaltools.DateSelectionEvent;

/**
 * DateSelectionListener, This interface can be implemented to create a date selection listener. Any
 * date selection listeners that are registered with a CalendarPanel will be notified each time that
 * a date is selected. 
 */
public interface DateSelectionListener {

    /**
     * dateSelected, This function will be called each time that a date is selected in the
     * applicable CalendarPanel. The selected date is supplied in the event object. Note that the
     * selected date may contain null, which represents a cleared or empty date.
     */
    public void dateSelected(DateSelectionEvent event);
}
