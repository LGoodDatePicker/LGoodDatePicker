package com.github.lgooddatepicker.optionalusertools;

import com.github.lgooddatepicker.zinternaltools.DateTimeChangeEvent;

/**
 * DateTimeChangeListener, This interface can be implemented to create a DateTimeChangeListener. Any
 * DateTimeChangeListeners that are registered with a DateTimePicker will be notified each time that
 * there is a change in the date value, the time value, or both values. Note that there is a
 * difference between the values stored in a DatePicker and TimePicker, and their text. This class
 * listens for changes to the "last valid date" and "last valid time" values, but does not listen
 * for changes in the text.
 */
public interface DateTimeChangeListener {

    /**
     * dateOrTimeChanged, This function will be called each time that there is a change in the
     * DateTimePicker date value, the time value, or both values.
     */
    public void dateOrTimeChanged(DateTimeChangeEvent event);
}
