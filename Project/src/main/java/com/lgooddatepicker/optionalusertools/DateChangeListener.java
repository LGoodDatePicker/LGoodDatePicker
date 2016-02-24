package com.lgooddatepicker.optionalusertools;

import com.lgooddatepicker.support.DateChangeEvent;

/**
 * DateChangeListener, This interface can be implemented to create a date change listener. Any date
 * change listeners that are registered with a DatePicker will be notified each time that the date
 * is changed. Note that there is a difference between a DatePicker's date, and its text. The text
 * may or may not contain a valid date string. The date will always either contain a valid LocalDate
 * object, or contain null.
 */
public interface DateChangeListener {

    /**
     * dateChanged, This function will be called each time that the date in the applicable date
     * picker has changed. Both the old date, and the new date, are supplied in the event object.
     * Note that either parameter may contain null, which represents a cleared or empty date.
     */
    public void dateChanged(DateChangeEvent event);
}
