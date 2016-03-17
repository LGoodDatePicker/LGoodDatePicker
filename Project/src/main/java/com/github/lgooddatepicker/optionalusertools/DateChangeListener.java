package com.github.lgooddatepicker.optionalusertools;

import com.github.lgooddatepicker.zinternaltools.DateChangeEvent;

/**
 * DateChangeListener, This interface can be implemented to create a date change listener. Any date
 * change listeners that are registered with a DatePicker will be notified each time that the date
 * is changed. Note that there is a difference between a DatePicker's date, and its text. This class
 * listens for changes to the "last valid date" value, but does not listen for changes in the text.
 */
public interface DateChangeListener {

    /**
     * dateChanged, This function will be called each time that the date in the applicable date
     * picker has changed. Both the old date, and the new date, are supplied in the event object.
     * Note that either parameter may contain null, which represents a cleared or empty date.
     */
    public void dateChanged(DateChangeEvent event);
}
