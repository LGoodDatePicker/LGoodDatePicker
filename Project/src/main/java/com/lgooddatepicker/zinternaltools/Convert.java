package com.lgooddatepicker.zinternaltools;

import com.lgooddatepicker.datepicker.DatePicker;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * Convert, This class allows the programmer to get or set a date picker date, using some other
 * popular data types besides the default java.time.LocalDate.
 *
 * Example Usage:
 * <pre>
 * // Create a date picker.
 * DatePicker datePicker = new DatePicker();
 *
 * // Set the date picker date, from a java.util.Date instance, using the default time zone.
 * java.util.Date date = new java.util.Date();
 * datePicker.convert().setDateWithDefaultZone(date);
 *
 * // Get the date picker date, as a java.util.Date instance, using the default time zone.
 * date = datePicker.convert().getDateWithDefaultZone();
 * </pre>
 *
 * Implementation note: For code clarity, only the java.time packages should use import statements
 * in this class. All other date related data types should be fully qualified in this class.
 */
public class Convert {

    /**
     * parentDatePicker, This holds the date picker that acts as the data source of all "get"
     * functions, or the data destination of all "put" functions.
     */
    private DatePicker parentDatePicker;

    /**
     * Default Constructor, Supply the date picker that should be associated with this converter.
     */
    public Convert(DatePicker parentDatePicker) {
        this.parentDatePicker = parentDatePicker;
    }

    /**
     * getDateWithDefaultZone, Returns the date picker value as a java.util.Date that was created
     * using the system default time zone, or returns null. This will return null when the date
     * picker has no value.
     */
    public java.util.Date getDateWithDefaultZone() {
        LocalDate pickerDate = parentDatePicker.getDate();
        if (pickerDate == null) {
            return null;
        }
        Instant instant = pickerDate.atStartOfDay(ZoneId.systemDefault()).toInstant();
        java.util.Date javaUtilDate = java.util.Date.from(instant);
        return javaUtilDate;
    }

    /**
     * getDateWithZone, Returns the date picker value as a java.util.Date that was created using the
     * specified time zone, or returns null. This will return null if either the date picker has no
     * value, or if the supplied time zone was null.
     */
    public java.util.Date getDateWithZone(ZoneId timezone) {
        LocalDate pickerDate = parentDatePicker.getDate();
        if (pickerDate == null || timezone == null) {
            return null;
        }
        Instant instant = pickerDate.atStartOfDay(timezone).toInstant();
        java.util.Date javaUtilDate = java.util.Date.from(instant);
        return javaUtilDate;
    }

    /**
     * setDateWithDefaultZone, Sets the date picker value from a java.util.Date using the system
     * default time zone. If either the date or the time zone are null, the date picker will be
     * cleared.
     */
    public void setDateWithDefaultZone(java.util.Date javaUtilDate) {
        if (javaUtilDate == null) {
            parentDatePicker.setDate(null);
            return;
        }
        Instant instant = Instant.ofEpochMilli(javaUtilDate.getTime());
        ZonedDateTime zonedDateTime = instant.atZone(ZoneId.systemDefault());
        LocalDate localDate = zonedDateTime.toLocalDate();
        parentDatePicker.setDate(localDate);
    }

    /**
     * setDateWithZone, Sets the date picker value from a java.util.Date using the specified time
     * zone. If either the date or the time zone are null, the date picker will be cleared.
     */
    public void setDateWithZone(java.util.Date javaUtilDate, ZoneId timezone) {
        if (javaUtilDate == null || timezone == null) {
            parentDatePicker.setDate(null);
            return;
        }
        Instant instant = Instant.ofEpochMilli(javaUtilDate.getTime());
        ZonedDateTime zonedDateTime = instant.atZone(timezone);
        LocalDate localDate = zonedDateTime.toLocalDate();
        parentDatePicker.setDate(localDate);
    }

}
