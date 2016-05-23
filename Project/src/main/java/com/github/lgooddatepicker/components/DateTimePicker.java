package com.github.lgooddatepicker.components;

import com.privatejgoodies.forms.layout.FormLayout;
import com.privatejgoodies.forms.layout.ConstantSize;
import com.privatejgoodies.forms.layout.ColumnSpec;
import com.privatejgoodies.forms.factories.CC;
import com.github.lgooddatepicker.optionalusertools.DateChangeListener;
import com.github.lgooddatepicker.optionalusertools.DateTimeChangeListener;
import com.github.lgooddatepicker.optionalusertools.TimeChangeListener;
import com.github.lgooddatepicker.zinternaltools.DateChangeEvent;
import com.github.lgooddatepicker.zinternaltools.DateTimeChangeEvent;
import com.github.lgooddatepicker.zinternaltools.TimeChangeEvent;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import javax.swing.JPanel;

/**
 * DateTimePicker, This class combines a date picker with a time picker. This class provides
 * functionality for creating and manipulating the two components as a single unit. This class also
 * provides functions for getting or setting the combined date and time as a java.time.LocalDateTime
 * object.
 *
 * This class is implemented as a relatively thin wrapper around the DatePicker and TimePicker
 * instances. Those instances can be accessed with the getDatePicker() and getTimePicker()
 * functions.
 *
 * There are a small number of customizable settings that apply specifically to the DateTimePicker
 * class. Each of those settings is contained inside the TimePickerSettings class, and begin with
 * the prefix "zDateTimePicker_".
 *
 * For information on how to use or customize the DatePicker and TimePicker classes, see the
 * javadocs for those two classes.
 *
 * You can create an instance of DateTimePicker using the default constructor, which will create a
 * DatePicker and a TimePicker that has default settings and uses the default locale. Alternatively,
 * you can supply a DatePickerSettings instance and/or a TimePickerSettings instance when you
 * construct this class.
 */
public class DateTimePicker extends JPanel {

    /**
     * datePicker, This holds the date picker component of this DateTimePicker.
     */
    public DatePicker datePicker;

    /**
     * dateTimeChangeListeners, This holds a list of dateTimeChangeListeners that wish to be
     * notified whenever the last valid date or the last valid time has changed.
     */
    private ArrayList<DateTimeChangeListener> dateTimeChangeListeners = 
            new ArrayList<DateTimeChangeListener>();

    /**
     * timePicker, This holds the time picker component of this DateTimePicker.
     */
    public TimePicker timePicker;

    /**
     * Constructor with default settings. This creates a new instance of DateTimePicker, with
     * default DatePicker and TimePicker settings, in the default locale.
     */
    public DateTimePicker() {
        this(null, null);
    }

    /**
     * Constructor with custom settings. This creates a new instance of DateTimePicker, with the
     * supplied DatePicker and TimePicker settings.
     */
    public DateTimePicker(DatePickerSettings datePickerSettingsOrNull,
            TimePickerSettings timePickerSettingsOrNull) {
        initComponents();
        DatePickerSettings dateSettings = (datePickerSettingsOrNull == null)
                ? new DatePickerSettings() : datePickerSettingsOrNull;
        TimePickerSettings timeSettings = (timePickerSettingsOrNull == null)
                ? new TimePickerSettings() : timePickerSettingsOrNull;
        datePicker = new DatePicker(dateSettings);
        timePicker = new TimePicker(timeSettings);
        add(datePicker, CC.xy(1, 1));
        add(timePicker, CC.xy(3, 1));
        // Create a change listener instance, and add it to each component.
        DateTimeChangeListenerImplementation listener
                = new DateTimeChangeListenerImplementation(this);
        datePicker.addDateChangeListener(listener);
        timePicker.addTimeChangeListener(listener);
        // Set the gap size between the date picker and the time picker.
        int gapPixels = (timeSettings.zDateTimePicker_GapBeforeTimePickerPixels == null)
                ? 5 : timeSettings.zDateTimePicker_GapBeforeTimePickerPixels;
        this.setGapSize(gapPixels, ConstantSize.PIXEL);
        // If the user has not changed the gap size for the date picker, then set it to zero.
        if (dateSettings.getGapBeforeButtonPixels() == null) {
            datePicker.getSettings().setGapBeforeButtonPixels(0);
        }
        // If the user has not changed the gap size for the time picker, then set it to zero.
        if (timeSettings.getGapBeforeButtonPixels() == null) {
            timePicker.getSettings().setGapBeforeButtonPixels(0);
        }
    }

    /**
     * addDateTimeChangeListener, This adds a change listener to this DateTimePicker. For additional
     * details, see the DateTimeChangeListener class documentation.
     */
    public void addDateTimeChangeListener(DateTimeChangeListener listener) {
        dateTimeChangeListeners.add(listener);
    }

    /**
     * clear, This will clear the date picker and the time picker. This will clear both the text and
     * the last valid value of each component.
     */
    public void clear() {
        datePicker.clear();
        timePicker.clear();
    }

    /**
     * getDatePicker, This returns the date picker component of this DateTimePicker.
     */
    public DatePicker getDatePicker() {
        return datePicker;
    }

    /**
     * removeDateTimeChangeListener, This removes the specified change listener from this
     * DateTimePicker.
     */
    public void removeDateTimeChangeListener(DateTimeChangeListener listener) {
        dateTimeChangeListeners.remove(listener);
    }

    /**
     * getDateTime, This attempts to retrieve the date from the date picker, and the time from the
     * time picker, as a single LocalDateTime value.
     *
     * This will return null if the date picker or the time picker contains null (an empty value) as
     * its last valid value.
     *
     * This can return null if "allowEmptyDates" is true for either the date or time picker. If
     * allowEmptyDates is false for both components, then this can never return null.
     *
     * Note: If the automatic validation of the date picker text or the time picker text has not yet
     * occurred, then the value returned from this function may not always match the current text.
     *
     * See the DatePicker.getDate() and TimePicker.getTime() functions for additional details.
     */
    public LocalDateTime getDateTime() {
        LocalDate dateValue = datePicker.getDate();
        LocalTime timeValue = timePicker.getTime();
        if (dateValue == null || timeValue == null) {
            return null;
        }
        return LocalDateTime.of(dateValue, timeValue);
    }

    /**
     * getDateTimeAndAllowEmptyTimes, This attempts to retrieve the date from the date picker, and
     * the time from the time picker, as a single LocalDateTime value. If the time picker is empty,
     * the time portion of the returned value will be set to LocalTime.MIDNIGHT.
     *
     * If the date picker contains a valid date but the time picker contains null (is empty), then a
     * LocalDateTime will be returned with its time components set to LocalTime.MIDNIGHT. If the
     * date picker contains null, then this function will return null.
     *
     * This can return null if "allowEmptyDates" is true for the date picker. If allowEmptyDates is
     * false for the date picker, then this can never return null.
     *
     * Note: If the automatic validation of the date picker text or the time picker text has not yet
     * occurred, then the value returned from this function may not always match the current text.
     *
     * See the DatePicker.getDate() and TimePicker.getTime() functions for additional details.
     */
    public LocalDateTime getDateTimeAndAllowEmptyTimes() {
        LocalDate dateValue = datePicker.getDate();
        LocalTime timeValue = timePicker.getTime();
        timeValue = (timeValue == null) ? LocalTime.MIDNIGHT : timeValue;
        if (dateValue == null) {
            return null;
        }
        return LocalDateTime.of(dateValue, timeValue);
    }

    /**
     * getDateTimeChangeListeners, This returns a new ArrayList, that contains any change listeners
     * that are registered with this DateTimePicker.
     */
    public ArrayList<DateTimeChangeListener> getDateTimeChangeListeners() {
        return new ArrayList<DateTimeChangeListener>(dateTimeChangeListeners);
    }

    /**
     * getTimePicker, This returns the time picker component of this DateTimePicker.
     */
    public TimePicker getTimePicker() {
        return timePicker;
    }

    /**
     * isDateTimeAllowed, This checks to see if the specified value is allowed by any currently set
     * veto policies and "allowEmptyValues" settings of both the DatePicker and TimePicker
     * components.
     *
     * If the specified value would be allowed by both components, then this returns true. If the
     * specified value would not be allowed by either or both components, then this returns false.
     *
     * For additional details, see: DatePicker.isDateAllowed() and TimePicker.isTimeAllowed().
     */
    public boolean isDateTimeAllowed(LocalDateTime value) {
        LocalDate datePortion = (value == null) ? null : value.toLocalDate();
        LocalTime timePortion = (value == null) ? null : value.toLocalTime();
        boolean isDateAllowed = datePicker.isDateAllowed(datePortion);
        boolean isTimeAllowed = timePicker.isTimeAllowed(timePortion);
        return (isDateAllowed && isTimeAllowed);
    }

    /**
     * isEnabled, Returns true if this component is enabled, otherwise returns false.
     */
    @Override
    public boolean isEnabled() {
        return super.isEnabled();
    }

    /**
     * setDateTime, This uses the supplied LocalDateTime to set the value of the DatePicker and the
     * TimePicker. Values that are set from this function are processed through the same validation
     * procedures as values that are typed by the user.
     *
     * More specifically:
     *
     * Date validation and time validation are performed separately. Date validation is handled by
     * the DatePicker, and time validation is handled by the TimePicker. The veto policy and
     * "allowEmptyValues" settings are used to determine whether or not a particular value is
     * "allowed".
     *
     * Allowed values will be set in the text field, and also committed to the "last valid value" of
     * the applicable component. Disallowed values will be set in the text field (with a disallowed
     * indicator font), but will not be committed to the "last valid value" of the applicable
     * component.
     *
     * It is possible for a particular LocalDateTime value to be allowed by one component (and
     * therefore committed to that component), but not allowed by the other component.
     *
     * A value can be checked against any current veto policies, and against the allowEmptyTimes
     * settings, by calling isDateTimeAllowed(). This can be used to determine (in advance), if a
     * particular value would be allowed by both components.
     *
     * Note: If empty values are allowed by both components, and there is no veto policy on either
     * component, then all possible values will (always) be allowed. These are the default settings
     * of the components.
     *
     * For additional details about the automatic date and time validation, see:
     * DatePicker.setDate() and TimePicker.setTime().
     */
    public void setDateTime(LocalDateTime optionalDateTime) {
        if (optionalDateTime == null) {
            datePicker.setDate(null);
            timePicker.setTime(null);
            return;
        }
        datePicker.setDate(optionalDateTime.toLocalDate());
        timePicker.setTime(optionalDateTime.toLocalTime());
    }

    /**
     * setEnabled, This enables or disables the DateTimePicker. When the component is enabled, dates
     * and times can be selected by the user using any methods that are allowed by the current
     * settings. When the component is disabled, there is no way for the user to interact with the
     * components. More specifically, dates and times cannot be selected with the mouse, or with the
     * keyboard, and the date and time pickers change their color scheme to indicate the disabled
     * state. Any currently stored text and last valid values are retained while the component is
     * disabled.
     */
    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        datePicker.setEnabled(enabled);
        timePicker.setEnabled(enabled);
    }

    /**
     * setGapSize, This sets the size of the gap between the date picker and the time picker.
     */
    public void setGapSize(int gapSize, ConstantSize.Unit units) {
        ConstantSize gapSizeObject = new ConstantSize(gapSize, units);
        ColumnSpec columnSpec = ColumnSpec.createGap(gapSizeObject);
        FormLayout layout = (FormLayout) getLayout();
        layout.setColumnSpec(2, columnSpec);
    }

    /**
     * toString, This returns a string representation of the values which are currently stored in
     * the date and time picker. If the last valid value of the date picker it is null, or the last
     * valid value of the time picker is null, then this will return an empty string ("").
     *
     * Javadocs from LocalDateTime.toString():
     *
     * Outputs this date-time as a {@code String}, such as {@code 2007-12-03T10:15:30}.
     * <p>
     * The output will be one of the following ISO-8601 formats:
     * <ul>
     * <li>{@code uuuu-MM-dd'T'HH:mm}</li>
     * <li>{@code uuuu-MM-dd'T'HH:mm:ss}</li>
     * <li>{@code uuuu-MM-dd'T'HH:mm:ss.SSS}</li>
     * <li>{@code uuuu-MM-dd'T'HH:mm:ss.SSSSSS}</li>
     * <li>{@code uuuu-MM-dd'T'HH:mm:ss.SSSSSSSSS}</li>
     * </ul>
     * The format used will be the shortest that outputs the full value of the time where the
     * omitted parts are implied to be zero.
     */
    @Override
    public String toString() {
        LocalDateTime dateTime = getDateTime();
        String text = (dateTime == null) ? "" : dateTime.toString();
        return text;
    }

    /**
     * toStringAndAllowEmptyTimes, This returns a string representation of the values which are
     * currently stored in the date and time picker. If the last valid value of the date picker is
     * null, then this will return an empty string (""). If the last valid value of the time picker
     * is null, then that portion of the time will be replaced with LocalTime.MIDNIGHT.
     *
     * Javadocs from LocalDateTime.toString():
     *
     * Outputs this date-time as a {@code String}, such as {@code 2007-12-03T10:15:30}.
     * <p>
     * The output will be one of the following ISO-8601 formats:
     * <ul>
     * <li>{@code uuuu-MM-dd'T'HH:mm}</li>
     * <li>{@code uuuu-MM-dd'T'HH:mm:ss}</li>
     * <li>{@code uuuu-MM-dd'T'HH:mm:ss.SSS}</li>
     * <li>{@code uuuu-MM-dd'T'HH:mm:ss.SSSSSS}</li>
     * <li>{@code uuuu-MM-dd'T'HH:mm:ss.SSSSSSSSS}</li>
     * </ul>
     * The format used will be the shortest that outputs the full value of the time where the
     * omitted parts are implied to be zero.
     */
    public String toStringAndAllowEmptyTimes() {
        LocalDateTime dateTime = getDateTimeAndAllowEmptyTimes();
        String text = (dateTime == null) ? "" : dateTime.toString();
        return text;
    }

    /**
     * initComponents, This is automatically generated code that is supplied by JFormDesigner. This
     * should not be modified directly, it should only be modified from within JFormDesigner. This
     * initializes the components of the panel.
     */
    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents

		//======== this ========
		setLayout(new FormLayout(
			"pref:grow, 5px, pref:grow(0.6)",
			"fill:pref:grow"));
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    /**
     * JFormDesigner variables, This is automatically generated code that is supplied by
     * JFormDesigner. This should not be modified directly, it should only be modified from within
     * JFormDesigner. These are any variables which are contained in the panel.
     */
    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // JFormDesigner - End of variables declaration  //GEN-END:variables
    //
    /**
     * DateTimeChangeListenerImplementation, This private class implements the listener interfaces
     * for the DatePicker and the TimePicker. An instance of this class is added to the DatePicker
     * and the TimePicker, so that the DateTimePicker can listen for changes to those components.
     */
    private class DateTimeChangeListenerImplementation
            implements DateChangeListener, TimeChangeListener {

        /**
         * source, This holds a reference to the parent DateTimePicker.
         */
        private final DateTimePicker source;

        /**
         * Constructor.
         */
        public DateTimeChangeListenerImplementation(DateTimePicker source) {
            this.source = source;
        }

        /**
         * dateChanged, This is called by the DatePicker component when the date has changed.
         */
        @Override
        public void dateChanged(DateChangeEvent dateChangeEvent) {
            reportDateOrTimeChange(dateChangeEvent, null);
        }

        /**
         * timeChanged, This is called by the TimePicker component when the time has changed.
         */
        @Override
        public void timeChanged(TimeChangeEvent timeChangeEvent) {
            reportDateOrTimeChange(null, timeChangeEvent);
        }

        /**
         * reportDateOrTimeChange, This notifies any DateTimePicker change listeners of the
         * specified change events.
         */
        private void reportDateOrTimeChange(DateChangeEvent dateEvent, TimeChangeEvent timeEvent) {
            DateTimeChangeEvent summaryEvent = new DateTimeChangeEvent(source,
                    source.datePicker, source.timePicker, dateEvent, timeEvent);
            for (DateTimeChangeListener listener : dateTimeChangeListeners) {
                listener.dateOrTimeChanged(summaryEvent);
            }
        }

    }
}
