package com.github.lgooddatepicker.optionalusertools;

import com.github.lgooddatepicker.zinternaltools.TimeChangeEvent;

/**
 * TimeChangeListener, This interface can be implemented to create a time change listener. Any time
 * change listeners that are registered with a TimePicker will be notified each time that the time
 * is changed. Note that there is a difference between a TimePicker's time value, and its text. This
 * class listens for changes to the "last valid time" value, but does not listen for changes in the
 * text.
 */
public interface TimeChangeListener {

    /**
     * timeChanged, This function will be called whenever the time in the applicable time picker has
     * changed. Both the old time, and the new time, are supplied in the event object. Note that
     * either parameter may contain null, which represents a cleared or empty time.
     */
    public void timeChanged(TimeChangeEvent event);
}
