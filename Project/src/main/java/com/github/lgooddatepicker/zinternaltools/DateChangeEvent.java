package com.github.lgooddatepicker.zinternaltools;

import com.github.lgooddatepicker.components.DatePicker;
import java.time.LocalDate;

/**
 * DateChangeEvent, An instance of this event class is passed to each registered DateChangeListener,
 * each time that the date in a date picker changes.
 */
public class DateChangeEvent {

    /**
     * Constructor.
     */
    public DateChangeEvent(DatePicker source, LocalDate oldDate, LocalDate newDate) {
        this.source = source;
        this.oldDate = oldDate;
        this.newDate = newDate;
    }

    /**
     * source, This is the date picker that generated the event.
     */
    private DatePicker source;

    /**
     * oldDate, This holds the value of the DatePicker date, before the date changed.
     */
    private LocalDate oldDate;

    /**
     * newDate, This holds the value of the DatePicker date, after the date changed.
     */
    private LocalDate newDate;

    /**
     * getSource, Returns the date picker that generated the event.
     */
    public DatePicker getSource() {
        return source;
    }

    /**
     * getOldDate, Returns the previous value of the DatePicker date. This is the value that existed
     * before the date was changed.
     */
    public LocalDate getOldDate() {
        return oldDate;
    }

    /**
     * getNewDate, Returns the new value of the DatePicker date. This is the value that currently
     * exists. (Put in another way, this is the value that exists after the date was changed.)
     */
    public LocalDate getNewDate() {
        return newDate;
    }

}
