package com.github.lgooddatepicker.zinternaltools;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DateTimePicker;
import com.github.lgooddatepicker.components.TimePicker;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * DateTimeChangeEvent, An instance of this event class is passed to each registered
 * DateTimeChangeListener, whenever the date or the time in a DateTimePicker has changed.
 *
 * Note that this class will always contain either one dateChangeEvent, or one timeChangeEvent, but
 * never both events at the same time.
 */
public class DateTimeChangeEvent {

    /**
     * Constructor.
     */
    public DateTimeChangeEvent(DateTimePicker source, DatePicker datePicker, TimePicker timePicker,
            DateChangeEvent dateChangeEvent, TimeChangeEvent timeChangeEvent) {
        this.source = source;
        this.datePicker = datePicker;
        this.timePicker = timePicker;
        this.dateChangeEvent = dateChangeEvent;
        this.timeChangeEvent = timeChangeEvent;
    }

    /**
     * source, This is the DateTimePicker that generated the event.
     */
    private final DateTimePicker source;

    /**
     * datePicker, This is a reference to the date picker component of the DateTimePicker.
     */
    private final DatePicker datePicker;

    /**
     * timePicker, This is a reference to the time picker component of the DateTimePicker.
     */
    private final TimePicker timePicker;

    /**
     * dateChangeEvent, If the date changed, then this contains the date change event. Otherwise
     * this contains null.
     */
    private final DateChangeEvent dateChangeEvent;

    /**
     * timeChangeEvent, If the time changed, then this contains the time change event. Otherwise
     * this contains null.
     */
    private final TimeChangeEvent timeChangeEvent;

    /**
     * getSource, Returns the DateTimePicker that generated the event.
     */
    public DateTimePicker getSource() {
        return source;
    }

    /**
     * getDatePicker, Returns a reference to the date picker component of the DateTimePicker.
     */
    public DatePicker getDatePicker() {
        return datePicker;
    }

    /**
     * getTimePicker, Returns a reference to the time picker component of the DateTimePicker.
     */
    public TimePicker getTimePicker() {
        return timePicker;
    }

    /**
     * getDateChangeEvent, If the date changed, then this returns the date change event. Otherwise
     * this returns null.
     */
    public DateChangeEvent getDateChangeEvent() {
        return dateChangeEvent;
    }

    /**
     * getTimeChangeEvent, If the time changed, then this returns the time change event. Otherwise
     * this returns null.
     */
    public TimeChangeEvent getTimeChangeEvent() {
        return timeChangeEvent;
    }

    /**
     * getNewDateTimeStrict, This returns the new LocalDateTime value from the DateTimePicker, as it
     * would be reported by getSource().getDateTimeStrict().
     *
     * For additional details, see DateTimePicker.getDateTimeStrict().
     */
    public LocalDateTime getNewDateTimeStrict() {
        return getSource().getDateTimeStrict();
    }

    /**
     * getNewDateTimePermissive, This returns the new LocalDateTime value from the DateTimePicker,
     * as it would be reported by getSource().getDateTimePermissive().
     *
     * For additional details, see DateTimePicker.getDateTimePermissive().
     */
    public LocalDateTime getNewDateTimePermissive() {
        return getSource().getDateTimePermissive();
    }

    /**
     * getOldDateTimeStrict, This returns the old LocalDateTime value from the DateTimePicker, as it
     * would have been reported by getSource().getDateTimeStrict(), before this change event
     * occurred.
     *
     * For additional details, see DateTimePicker.getDateTimeStrict().
     */
    public LocalDateTime getOldDateTimeStrict() {
        // If there is no change event, then the old value is the same as the current value.
        LocalDate oldDateValue = datePicker.getDate();
        LocalTime oldTimeValue = timePicker.getTime();
        // If a change event exists, then the old value can be retrieved from the change event.
        oldDateValue = (dateChangeEvent != null) ? dateChangeEvent.getOldDate() : oldDateValue;
        oldTimeValue = (timeChangeEvent != null) ? timeChangeEvent.getOldTime() : oldTimeValue;
        // If either will value is null, then this function will return null.
        if (oldDateValue == null || oldTimeValue == null) {
            return null;
        }
        // Return the combination of the two values.
        return LocalDateTime.of(oldDateValue, oldTimeValue);
    }

    /**
     * getOldDateTimePermissive, This returns the old LocalDateTime value from the DateTimePicker,
     * as it would have been reported by getSource().getDateTimePermissive(), before this change
     * event occurred.
     *
     * For additional details, see DateTimePicker.getDateTimePermissive().
     */
    public LocalDateTime getOldDateTimePermissive() {
        // If there is no change event, then the old value is the same as the current value.
        LocalDate oldDateValue = datePicker.getDate();
        LocalTime oldTimeValue = timePicker.getTime();
        // If a change event exists, then the old value can be retrieved from the change event.
        oldDateValue = (dateChangeEvent != null) ? dateChangeEvent.getOldDate() : oldDateValue;
        oldTimeValue = (timeChangeEvent != null) ? timeChangeEvent.getOldTime() : oldTimeValue;
        // This function allows the old time value to be null, and be replaced with midnight.
        oldTimeValue = (oldTimeValue == null) ? LocalTime.MIDNIGHT : oldTimeValue;
        // If there was no old date value, then return null.
        if (oldDateValue == null) {
            return null;
        }
        // Return the combination of the two values.
        return LocalDateTime.of(oldDateValue, oldTimeValue);
    }
}
