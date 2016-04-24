package com.github.lgooddatepicker.zinternaltools;

import com.github.lgooddatepicker.calendarpanel.CalendarPanel;
import java.time.LocalDate;

/**
 * DateSelectionEvent, An instance of this event class is passed to each registered
 * DateSelectionListener, each time that a date is selected in a CalendarPanel. 
 */
public class DateSelectionEvent {

    /**
     * Constructor.
     */
    public DateSelectionEvent(CalendarPanel source, LocalDate newDate, LocalDate oldDate) {
        this.source = source;
        this.newDate = newDate;
        this.oldDate = oldDate;
    }

    /**
     * source, This is the calendar panel that generated the event.
     */
    private CalendarPanel source;

    /**
     * newDate, This holds the value of the new selected date.
     */
    private LocalDate newDate;

    /**
     * oldDate, This holds the value of the old selected date.
     */
    private LocalDate oldDate;

    /**
     * getSource, Returns the calendar panel that generated the event.
     */
    public CalendarPanel getSource() {
        return source;
    }

    /**
     * getNewDate, Returns the new selected date.
     */
    public LocalDate getNewDate() {
        return newDate;
    }

    /**
     * getOldDate, Returns the old selected date.
     */
    public LocalDate getOldDate() {
        return oldDate;
    }

}
