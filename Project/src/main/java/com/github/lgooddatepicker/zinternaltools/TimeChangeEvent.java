package com.github.lgooddatepicker.zinternaltools;

import com.github.lgooddatepicker.components.TimePicker;
import java.time.LocalTime;

/**
 * TimeChangeEvent, An instance of this event class is passed to each registered TimeChangeListener,
 * each time that the time in a time picker changes.
 */
public class TimeChangeEvent {

    /**
     * Constructor.
     */
    public TimeChangeEvent(TimePicker source, LocalTime oldTime, LocalTime newTime) {
        this.source = source;
        this.oldTime = oldTime;
        this.newTime = newTime;
    }

    /**
     * source, This is the time picker that generated the event.
     */
    private TimePicker source;

    /**
     * oldTime, This holds the value of the TimePicker time, before the time changed.
     */
    private LocalTime oldTime;

    /**
     * newTime, This holds the value of the TimePicker time, after the time changed.
     */
    private LocalTime newTime;

    /**
     * getSource, Returns the time picker that generated the event.
     */
    public TimePicker getSource() {
        return source;
    }

    /**
     * getOldTime, Returns the previous value of the TimePicker time. This is the value that existed
     * before the time was changed.
     */
    public LocalTime getOldTime() {
        return oldTime;
    }

    /**
     * getNewTime, Returns the new value of the TimePicker time. This is the value that currently
     * exists. (Put in another way, this is the value that exists after the time was changed.)
     */
    public LocalTime getNewTime() {
        return newTime;
    }

}
