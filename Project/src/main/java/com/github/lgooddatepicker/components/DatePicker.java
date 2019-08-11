package com.github.lgooddatepicker.components;

import com.privatejgoodies.forms.layout.FormLayout;
import com.privatejgoodies.forms.factories.CC;
import java.awt.event.*;
import javax.swing.border.*;
import com.github.lgooddatepicker.zinternaltools.*;
import com.github.lgooddatepicker.components.DatePickerSettings.DateArea;
import com.github.lgooddatepicker.optionalusertools.DateChangeListener;
import com.github.lgooddatepicker.optionalusertools.PickerUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.util.ArrayList;
import com.github.lgooddatepicker.optionalusertools.DateVetoPolicy;
import com.github.lgooddatepicker.zinternaltools.CalculateMinimumDateFieldSize;
import com.github.lgooddatepicker.zinternaltools.CustomPopup.CustomPopupCloseListener;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.Window;
import java.time.Instant;
import java.time.LocalDate;
import java.time.chrono.IsoEra;
import java.util.Locale;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

/**
 * DatePicker, This class implements a date picker GUI component.
 *
 * This class supports a complete set of "default functionality" without requiring any
 * DatePickerSettings. However, the settings of a date picker can optionally be customized by
 * creating a DatePickerSettings instance and passing it to the DatePicker constructor. After the
 * date picker is constructed, the settings instance should not be changed.
 *
 * By default, the language and internationalization settings of a date picker are determined from
 * the operating system defaults using Locale.getDefault(). If desired, the locale and language can
 * be modified by passing a locale to the constructor of a DatePickerSettings instance, and passing
 * that instance to the constructor of a DatePicker.
 *
 * Automatic Date Validation: Every date picker stores its current text, and its last valid date.
 * The last valid date is returned when you call DatePicker.getDate(). If a person uses their
 * keyboard to type into the text field, any text that is not a valid date will be displayed in red,
 * any vetoed date will have a strikethrough, and any valid date will be displayed in black. Valid
 * dates are automatically committed to the date picker. Any invalid or vetoed text is automatically
 * reverted to the last valid date whenever the the date picker loses focus.
 *
 * <code>
 * // Basic usage example:
 * // Create a date picker.
 * DatePicker datePicker = new DatePicker();
 * // Create a panel, and add the date picker.
 * JPanel panel = new JPanel();
 * panel.add(datePicker);
 * </code>
 */
public class DatePicker extends JPanel implements CustomPopupCloseListener {

    /**
     * calendarPanel, This holds the calendar panel GUI component of this date picker. This should
     * be null when the date picker calendar is closed, and hold a calendar panel instance when the
     * date picker calendar is opened.
     */
    private CalendarPanel calendarPanel = null;

    /**
     * componentListeners, This holds any component listeners for the DatePicker.
     */
    private ArrayList<ComponentListener> componentListeners;

    /**
     * convert, This utility class instance is used to get or set the DatePicker java.time.LocalDate
     * value, using other common date related data types.
     */
    private Convert convert;

    /**
     * dateChangeListeners, This holds a list of date change listeners that wish to be notified each
     * time that the last valid date is changed.
     */
    private ArrayList<DateChangeListener> dateChangeListeners = new ArrayList<DateChangeListener>();

    /**
     * lastPopupCloseTime, This holds a timestamp that indicates when the calendar was last closed.
     * This is used to implement a workaround for event behavior that was causing the date picker
     * class to erroneously re-open a calendar when the user was clicking on the show calendar
     * button in an attempt to close the previous calendar.
     */
    private Instant lastPopupCloseTime = Instant.now();

    /**
     * lastValidDate, This holds the last valid date that was entered into the date picker. This
     * value is returned from the function DatePicker.getDate();
     *
     * Implementation note: After initialization, variable should never be -set- directly. Instead,
     * use the date setting function that will notify the list of dateChangeListeners each time that
     * this value is changed.
     */
    private LocalDate lastValidDate = null;

    /**
     * popup, This is the custom popup instance for this date picker. This should remain null until
     * a popup is opened. Creating a custom popup class allowed us to control the details of when
     * the popup menu should be open or closed.
     */
    private CustomPopup popup = null;

    /**
     * settings, This holds the settings instance for this date picker. Default settings are
     * generated automatically. Custom settings may optionally be supplied in the DatePicker
     * constructor.
     *
     * This will never be null after it is initialized, but it could be null before setSettings() is
     * called for the first time. Any functions that rely on the settings should check for null and
     * return if null, before continuing the function.
     */
    private DatePickerSettings settings;

    /**
     * skipTextFieldChangedFunctionWhileTrue, While this is true, the function
     * "zTextFieldChangedSoIndicateIfValidAndStoreWhenValid()" will not be executed in response to
     * date text field text change events.
     */
    private boolean skipTextFieldChangedFunctionWhileTrue = false;

    /**
     * JFormDesigner GUI components, These variables are automatically generated by JFormDesigner.
     * This section should not be modified by hand, but only modified from within the JFormDesigner
     * program.
     */
    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JTextField dateTextField;
    private JButton toggleCalendarButton;
    // JFormDesigner - End of variables declaration  //GEN-END:variables

    /**
     * Constructor with Default Values, Create a date picker instance using the default operating
     * system locale and language, and default date picker settings.
     */
    public DatePicker() {
        this(null);
    }

    /**
     * Constructor with Custom Settings, Create a date picker instance using the supplied date
     * picker settings.
     */
    public DatePicker(DatePickerSettings settings) {
        initComponents();
        this.convert = new Convert(this);
        // Shrink the toggle calendar button to a reasonable size.
        toggleCalendarButton.setMargin(new java.awt.Insets(1, 2, 1, 2));
        // Add a change listener to the text field.
        zAddTextChangeListener();
        // Save and apply the supplied settings.
        setSettings(settings);
    }

    /**
     * setSettings, This will set the settings instance for this date picker. The previous settings
     * will be deleted. Note that calling this function effectively re-initializes the picker
     * component. All aspects of the component will be changed to match the provided settings. Any
     * currently selected date will not be changed by this function.
     *
     * If the popup calendar panel is open up the time that this function is called, the open
     * calendar will not be changed by this function. Calendar panels showing the new settings will
     * be displayed only after a new popup calendar panel is opened.
     */
    public void setSettings(DatePickerSettings settings) {
        settings = (settings == null) ? new DatePickerSettings() : settings;
        settings.zSetParentDatePicker(this);
        this.settings = settings;

        // Apply needed settings from the settings instance to this date picker.
        // Note: CalendarPanel.zApplyBorderPropertiesList() is called from the calendar panel 
        // constructor so it will be run both for independent and date picker calendar panels.
        settings.zApplyGapBeforeButtonPixels();
        settings.zApplyAllowKeyboardEditing();
        settings.zApplyAllowEmptyDates();
        // Draw the text field indicators, because they may not have been drawn if the initialDate
        // was null. (This is because the text would not have changed in that case.)
        // This should be called after the DatePickerSettings have been saved.
        zDrawTextFieldIndicators();
        // Set an appropriate minimum width for the date picker text field.
        // This may use a calculated minimum width (default), or a programmer supplied minimum
        // width, as specified in the date picker settings.
        zSetAppropriateTextFieldMinimumWidth();
        // Redraw the date picker text field.
        settings.zDrawDatePickerTextFieldIfNeeded();
        // Apply the visibility of date picker components.
        zApplyVisibilityOfComponents();
    }

    /**
     * addDateChangeListener, This adds a date change listener to this date picker. For additional
     * details, see the DateChangeListener class documentation.
     */
    public void addDateChangeListener(DateChangeListener listener) {
        dateChangeListeners.add(listener);
    }

    /**
     * clear, This will clear the date picker text. If the date picker is set to allow empty dates,
     * then the last valid date will also be cleared. If the date picker is set to disallow empty
     * dates, then the last valid date will not be changed by this function.
     */
    public void clear() {
        // Calling this function with null clears the date picker text. 
        // If empty dates are allowed, this will also clear the last valid date.
        setDate(null);
    }

    /**
     * closePopup, This closes the calendar popup. The popup can close itself automatically, so this
     * function does not generally need to be called programmatically.
     *
     * Notes: The popup can be automatically closed for various reasons. 1) The user may press
     * escape. 2) The popup may lose focus. 3) The window may be moved. 4) The user may toggle the
     * calendar with the "toggle calendar" button. 5) The user may select a date in the calendar.
     */
    public void closePopup() {
        if (popup != null) {
            // The popup.hide() function handles the de-registration of the various listeners
            // associated with the popup window. This also initiates a callback to the
            // DatePicker.zEventcustomPopupWasClosed() function.
            popup.hide();
        }
    }

    /**
     * convert, This is used to access the Convert class instance. The convert class allows the
     * programmer to get or set the date picker java.time.LocalDate value using other common data
     * types, such as java.util.Date. Example usage: datePicker.convert().getDateWithDefaultZone();
     * See the documentation of the Convert class for additional information and usage examples.
     */
    public Convert convert() {
        return convert;
    }

    /**
     * getComponentToggleCalendarButton, Returns the "toggle calendar" button for this date picker.
     * Direct access to the calendar button is provided so that the programmer can optionally assign
     * an image to the button, or perform advanced or unusual modifications to the date picker's
     * appearance and behavior.
     *
     * Note: This function should not be used to programmatically open or close the date picker
     * calendar. The following functions are provided for that purpose: openPopup(), closePopup(),
     * and togglePopup().
     */
    public JButton getComponentToggleCalendarButton() {
        return toggleCalendarButton;
    }

    /**
     * getBaseline, This returns the baseline value of the dateTextField.
     */
    @Override
    public int getBaseline(int width, int height) {
        if (dateTextField.isVisible()) {
            return dateTextField.getBaseline(width, height);
        }
        return super.getBaseline(width, height);
    }

    /**
     * getComponentDateTextField, Returns the date text field for this date picker. It is not
     * expected that most programmers will need or want to use this function.
     *
     * Important Note: Direct access to the text field is provided so the programmer can perform
     * advanced or unusual modifications of the date picker's appearance or behavior. However, this
     * function should be used only as a last resort, to implement functionality that is not
     * available from other functions or settings.
     *
     * The DatePickerSettings class provides multiple ways to customize the date picker text field,
     * and those functions should be preferred for the needs they address. It is suggested that the
     * programmer become familiar with the functions and settings in DatePickerSettings class before
     * directly accessing the date picker text field.
     *
     * The DatePickerSettings class can customize the following text field attributes: The text
     * field width, the background and foreground colors, the fonts, font size, and font colors, and
     * the date formats.
     *
     * Warning: To ensure proper behavior of the date picker, the text field should not be used to
     * get or set the the date (or text) values of the date picker. Instead, the programmer should
     * use the DatePicker.getDate() and DatePicker.getText() (and Set) functions for those purposes.
     * The DateChangeListener interface can be used to listen for changes to the date value.
     */
    public JTextField getComponentDateTextField() {
        return dateTextField;
    }

    /**
     * getDate, This returns the last valid date, or returns null to represent an empty date.
     *
     * If "DatePickerSettings.allowEmptyDates" is true, then this can return null. If allow empty
     * dates is false, then this can never return null.
     *
     * Note: If the automatic validation of date picker text has not yet occurred, then the the last
     * valid date may not always match the current text.
     *
     * <pre>
     * Additional Text Validation Details:
     * Whenever the current date picker text is not valid, the value returned by getDate()
     * will generally not match the date picker text. The date picker can contain invalid text
     * whenever both items (1) and (2) below are true:
     *
     * 1) The user has manually typed text that cannot be parsed by the parsing formats into a valid
     * date, or the user has typed a date that is vetoed by a current veto policy, or the user has
     * cleared (or left only whitespace) in the text when allowEmptyDates is false.
     *
     * 2) The date picker text field has continued to have focus, and therefore the automatic
     * validation (revert/commit) process has not yet occurred.
     * </pre>
     */
    public LocalDate getDate() {
        return lastValidDate;
    }

    /**
     * getDateChangeListeners, This returns a new ArrayList, that contains any date change listeners
     * that are registered with this DatePicker.
     */
    public ArrayList<DateChangeListener> getDateChangeListeners() {
        return new ArrayList<DateChangeListener>(dateChangeListeners);
    }

    /**
     * getDateStringOrEmptyString, This returns the last valid date in an ISO-8601 formatted string
     * "uuuu-MM-dd". For any CE years that are between 0 and 9999 inclusive, the output will have a
     * fixed length of 10 characters. Years before or after that range will output longer strings.
     * If the last valid date is empty, this will return an empty string ("").
     */
    public String getDateStringOrEmptyString() {
        LocalDate date = getDate();
        return (date == null) ? "" : date.toString();
    }

    /**
     * getDateStringOrSuppliedString, This returns the last valid date in an ISO-8601 formatted
     * string "uuuu-MM-dd". For any CE years that are between 0 and 9999 inclusive, the output will
     * have a fixed length of 10 characters. Years before or after that range will output longer
     * strings. If the last valid date is empty, this will return the value of emptyDateString.
     */
    public String getDateStringOrSuppliedString(String emptyDateString) {
        LocalDate date = getDate();
        return (date == null) ? emptyDateString : date.toString();
    }

    /**
     * getSettings, This returns the date picker settings instance.
     */
    public DatePickerSettings getSettings() {
        return settings;
    }

    /**
     * getText, This returns the current text that is present in the date picker text field. This
     * text can contain anything that was written by the user. It is specifically not guaranteed to
     * contain a valid date. This should not be used to retrieve the date picker date. Instead, use
     * DatePicker.getDate() for retrieving the date.
     */
    public String getText() {
        return dateTextField.getText();
    }

    /**
     * isDateAllowed, This checks to see if the specified date is allowed by any currently set veto
     * policy, and allowed by the current setting of allowEmptyDates.
     *
     * If allowEmptyDates is false, and the specified date is null, then this returns false.
     *
     * If a veto policy exists, and the specified date is vetoed, then this returns false.
     *
     * If the date is not vetoed, or if empty dates are allowed and the date is null, then this
     * returns true.
     */
    public boolean isDateAllowed(LocalDate date) {
        if (settings == null) {
            return true;
        }
        return settings.isDateAllowed(date);
    }

    /**
     * isEnabled, Returns true if this component is enabled, otherwise returns false.
     */
    @Override
    public boolean isEnabled() {
        return super.isEnabled();
    }

    /**
     * isPopupOpen, This returns true if the calendar popup is open. This returns false if the
     * calendar popup is closed
     */
    public boolean isPopupOpen() {
        return (popup != null);
    }

    /**
     * isTextFieldValid, This returns true if, and only if, the text field contains a parsable date
     * or a valid empty string. Note that this does not guarantee that the text in the text field is
     * in a standard format. Valid dates can be in any one of the parsingFormats that are accepted
     * by the date picker.
     *
     * More specifically, this returns true if: 1) the text field contains a parsable date that
     * exists, and that has not been vetoed by a current veto policy, OR 2) (allowEmptyDates ==
     * true) and dateTextField.getText().trim() contains an empty string. Otherwise returns false.
     */
    public boolean isTextFieldValid() {
        return isTextValid(dateTextField.getText());
    }

    /**
     * isTextValid, This function can be used to see if the supplied text represents a "valid date"
     * according to the settings of this date picker.
     *
     * More specifically, this returns true if: 1) the text contains a parsable date that exists,
     * and that has not been vetoed by a current veto policy, OR 2) (allowEmptyDates == true) and
     * text.trim() contains an empty string. Otherwise returns false.
     */
    public boolean isTextValid(String text) {
        // If the text is null or the settings are null, return false.
        if (text == null || settings == null) {
            return false;
        }
        // If the text is empty, return the value of allowEmptyDates.
        text = text.trim();
        if (text.isEmpty()) {
            return settings.getAllowEmptyDates();
        }
        // Try to get a parsed date.
        LocalDate parsedDate = InternalUtilities.getParsedDateOrNull(
                text, settings.getFormatForDatesCommonEra(),
                settings.getFormatForDatesBeforeCommonEra(),
                settings.getFormatsForParsing());

        // If the date could not be parsed, return false.
        if (parsedDate == null) {
            return false;
        }
        // If the date is vetoed, return false.
        DateVetoPolicy vetoPolicy = settings.getVetoPolicy();
        if (InternalUtilities.isDateVetoed(vetoPolicy, parsedDate)) {
            return false;
        }
        // The date is valid, so return true.
        return true;
    }

    /**
     * openPopup, This opens the calendar popup.
     *
     * If the date picker contains a lastValidDate, the calendar will be opened to the month and
     * year of the lastValidDate, with the lastValidDate marked as selected.
     *
     * If the lastValidDate is null, then the calendar will be opened with today's month and year
     * displayed, and no date will be selected.
     *
     * This function creates a new calendar and a new custom popup instance each time that it is
     * called. The associated object instances are automatically disposed and set to null when a
     * popup is closed.
     */
    public void openPopup() {
        if (isPopupOpen()) {
            closePopup();
            return;
        }

        if (settings == null) {
            return;
        }
        // If the component is disabled, do nothing.
        if (!isEnabled()) {
            return;
        }
        // If this function was called programmatically, we may need to change the focus to this
        // popup.
        if (!dateTextField.hasFocus()) {
            dateTextField.requestFocusInWindow();
        }
        // Get the last valid date, to pass to the calendar if needed.
        LocalDate selectedDateForCalendar = lastValidDate;
        // Create a new calendar panel. 
        // Use the CalendarPanel constructor that is made for the DatePicker class.
        DatePicker thisDatePicker = this;
        calendarPanel = new CalendarPanel(thisDatePicker);

        fireComponentEvent(new ComponentEvent(ComponentEvent.PREVIOUS_YEAR, calendarPanel.getPreviousYearButton()));
        fireComponentEvent(new ComponentEvent(ComponentEvent.PREVIOUS_MONTH, calendarPanel.getPreviousMonthButton()));
        fireComponentEvent(new ComponentEvent(ComponentEvent.NEXT_MONTH, calendarPanel.getNextMonthButton()));
        fireComponentEvent(new ComponentEvent(ComponentEvent.NEXT_YEAR, calendarPanel.getNextYearButton()));

        // If needed, apply the selected date to the calendar.
        if (selectedDateForCalendar != null) {
            calendarPanel.setSelectedDate(selectedDateForCalendar);
        }
        // Create a new custom popup.
        popup = new CustomPopup(calendarPanel, SwingUtilities.getWindowAncestor(this),
                this, settings.getBorderCalendarPopup());
        // Calculate the default origin for the popup.
        int defaultX = toggleCalendarButton.getLocationOnScreen().x
                + toggleCalendarButton.getBounds().width - popup.getBounds().width - 2;
        int defaultY = toggleCalendarButton.getLocationOnScreen().y
                + toggleCalendarButton.getBounds().height + 2;
        // Determine which component to use as the vertical flip reference component.
        JComponent verticalFlipReference = (settings.getVisibleDateTextField())
                ? dateTextField : toggleCalendarButton;
        // Set the popup location.
        zSetPopupLocation(popup, defaultX, defaultY, this, verticalFlipReference, 2, 6);
        // Show the popup and focus the calendar.
        popup.show();
        calendarPanel.requestFocus();
    }

    /**
     * removeDateChangeListener, This removes the specified date change listener from this date
     * picker.
     */
    public void removeDateChangeListener(DateChangeListener listener) {
        dateChangeListeners.remove(listener);
    }

    /**
     * setDate, This sets this date picker to the specified date. Dates that are set from this
     * function are processed through the same validation procedures as dates that are entered by
     * the user.
     *
     * More specifically:
     *
     * The "veto policy" and "allowEmptyTimes" settings are used to determine whether or not a
     * particular value is "allowed".
     *
     * Allowed values will be set in the text field, and also committed to the "last valid value".
     * Disallowed values will be set in the text field (with a disallowed indicator font), but will
     * not be committed to the "last valid value".
     *
     * A date value can be checked against any current veto policy, and against the allowEmptyDates
     * setting, by calling isDateAllowed(). This can be used to determine (in advance), if a
     * particular value would be allowed.
     *
     * Note: If empty dates are allowed, and the component does not have a veto policy, then all
     * possible values will (always) be allowed. These are the default settings of this component.
     *
     * Implementation Note: Whenever the text field changes to a valid date string, the
     * lastValidDate is also automatically set (unless the date is vetoed). This occurs through the
     * DocumentListener which is registered on the dateTextField.
     */
    public final void setDate(LocalDate optionalDate) {
        // Set the text field to the supplied date, using the standard format for null, AD, or BC.
        String standardDateString = zGetStandardTextFieldDateString(optionalDate);
        String textFieldString = dateTextField.getText();
        // We will only change the date, when the text or the last valid date does not match the
        // supplied date. This will prevent any registered date change listeners from receiving
        // any events unless the date actually changes.
        if ((!standardDateString.equals(textFieldString))) {
            zInternalSetDateTextField(standardDateString);
        }
    }

    /**
     * setDateToToday, This sets the date to today's date. This function is subject to the same
     * validation behaviors as if a user typed today's date into the date picker. See setDate() for
     * additional details.
     */
    public void setDateToToday() {
        setDate(LocalDate.now());
    }

    /**
     * setEnabled, This enables or disables the date picker. When the date picker is enabled, dates
     * can be selected by the user using any methods that are allowed by the current settings. When
     * the date picker is disabled, there is no way for the user to interact with the component.
     * More specifically, dates cannot be selected with the mouse, or with the keyboard, and the
     * date picker components change their color scheme to indicate the disabled state. Any
     * currently stored text and last valid date values are retained while the component is
     * disabled.
     */
    @Override
    public void setEnabled(boolean enabled) {
        if (enabled == false) {
            closePopup();
        }
        setTextFieldToValidStateIfNeeded();
        super.setEnabled(enabled);
        toggleCalendarButton.setEnabled(enabled);
        dateTextField.setEnabled(enabled);
        zDrawTextFieldIndicators();
    }

    /**
     * setLocale, The locale for a DatePicker should generally be set in the DatePickerSettings.
     * This function only exists to avoid confusion with the swing function Component.setLocale().
     *
     * This forwards any function calls to: DatePicker.getSettings().setLocale(). For the complete
     * Javadocs, see DatePickerSettings.setLocale().
     */
    @Override
    public void setLocale(Locale locale) {
        DatePickerSettings currentSettings = getSettings();
        if (currentSettings != null) {
            currentSettings.setLocale(locale);
        }
        super.setLocale(locale);
    }

    /**
     * setText, This sets the text of the date picker text field to the supplied value. This will
     * have the same effect on the last valid date as if the user was typing into the text field. In
     * other words, it may or may not change the last valid date. This should not be used to set the
     * date of the date picker, instead use DatePicker.setDate().
     */
    public void setText(String text) {
        zInternalSetDateTextField(text);
        dateTextField.requestFocusInWindow();
    }

    /**
     * setTextFieldToValidStateIfNeeded,
     *
     * This function will check the contents of the text field, and when needed, will set the text
     * to match the "last valid date" in a standardized valid format. This function is automatically
     * called whenever the date picker text field loses focus, or at other times when the text must
     * be set to a valid state. It is not expected that the programmer will normally need to call
     * this function directly.
     *
     * This function has two possible effects: 1) If the current text is already valid and is in the
     * standard format, then this will do nothing. If the text is not valid, or if the text is not
     * in the standard format, then: 2) This will replace the invalid text in the text field with a
     * standard date field text string that matches the last valid date.
     */
    public void setTextFieldToValidStateIfNeeded() {
        // Find out if the text field needs to be set to the last valid date or not.
        // The text field needs to be set whenever its text does not match the standard format
        // for the last valid date.
        String standardDateString = zGetStandardTextFieldDateString(lastValidDate);
        String textFieldString = dateTextField.getText();
        if (!standardDateString.equals(textFieldString)) {
            // Overwrite the text field with the last valid date.
            // This will clear the text field if the last valid date is null.
            setDate(lastValidDate);
        }
    }

    /**
     * toString, This returns the last valid date in an ISO-8601 formatted string "uuuu-MM-dd". For
     * any CE years that are between 0 and 9999 inclusive, the output will have a fixed length of 10
     * characters. Years before or after that range will output longer strings. If the last valid
     * date is empty, this will return an empty string (""). This returns the same value as
     * getDateStringOrEmptyString().
     */
    @Override
    public String toString() {
        return getDateStringOrEmptyString();
    }

    /**
     * togglePopup, This creates and shows a calendar popup. If the popup is already open, then this
     * will close the popup.
     *
     * This is called when the user clicks on the toggle calendar button of the date picker. This
     * function does not generally need to be called programmatically.
     */
    public void togglePopup() {
        // If a popup calendar was closed in the last 200 milliseconds, then do not open a new one.
        // This is a workaround for a problem where the toggle calendar button would erroneously
        // reopen a calendar after closing one.
        if ((Instant.now().toEpochMilli() - lastPopupCloseTime.toEpochMilli()) < 200) {
            return;
        }
        openPopup();
    }

    /**
     * zAddTextChangeListener, This add a text change listener to the date text field, so that we
     * can respond to text as it is typed.
     */
    private void zAddTextChangeListener() {
        dateTextField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                zEventTextFieldChanged();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                zEventTextFieldChanged();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                zEventTextFieldChanged();
            }
        });
    }

    /**
     * zApplyVisibilityOfButtons, This applies any settings that control visibility of components on
     * this date picker.
     */
    void zApplyVisibilityOfComponents() {
        boolean showDateTextField = settings.getVisibleDateTextField();
        dateTextField.setVisible(showDateTextField);
    }

    /**
     * zSetAppropriateTextFieldMinimumWidth, This sets the minimum (and preferred) width of the date
     * picker text field. The width will be determined (or calculated) from the current date picker
     * settings, as described below.
     *
     * If the programmer has not supplied a setting for the minimum size, then a default minimum
     * size will be calculated from the AD date format, the locale, and the font for valid dates.
     *
     * If the programmer has supplied a setting for the text field minimum size, then the programmer
     * supplied value will be used instead, unless a default override is allowed. (In this case, the
     * default value will only be used if the default setting is larger than the programmer supplied
     * setting).
     *
     * For additional details, see the javadoc for this function:
     * DatePickerSettings.setSizeTextFieldMinimumWidthDefaultOverride().
     */
    void zSetAppropriateTextFieldMinimumWidth() {
        if (settings == null) {
            return;
        }
        Integer programmerSuppliedWidth = settings.getSizeTextFieldMinimumWidth();
        // Determine the appropriate minimum width for the text field.
        int minimumWidthPixels = CalculateMinimumDateFieldSize.getFormattedDateWidthInPixels(
                settings.getFormatForDatesCommonEra(), settings.getLocale(),
                settings.getFontValidDate(), 0);
        if (programmerSuppliedWidth != null) {
            if (settings.getSizeTextFieldMinimumWidthDefaultOverride()) {
                minimumWidthPixels = Math.max(programmerSuppliedWidth, minimumWidthPixels);
            } else {
                minimumWidthPixels = programmerSuppliedWidth;
            }
        }
        // Apply the minimum and preferred width to the text field.
        // Note that we only change the width, not the height.
        Dimension newMinimumSize = dateTextField.getMinimumSize();
        newMinimumSize.width = minimumWidthPixels;
        dateTextField.setMinimumSize(newMinimumSize);
        Dimension newPreferredSize = dateTextField.getPreferredSize();
        newPreferredSize.width = minimumWidthPixels;
        dateTextField.setPreferredSize(newPreferredSize);
        this.validate();
    }

    /**
     * zSetPopupLocation, This calculates and sets the appropriate location for the popup windows,
     * for both the DatePicker and the TimePicker.
     */
    static void zSetPopupLocation(CustomPopup popup, int defaultX, int defaultY, JComponent picker,
            JComponent verticalFlipReference, int verticalFlipDistance, int bottomOverlapAllowed) {
        // Gather some variables that we will need.
        Window topWindowOrNull = SwingUtilities.getWindowAncestor(picker);
        Rectangle workingArea = InternalUtilities.getScreenWorkingArea(topWindowOrNull);
        int popupWidth = popup.getBounds().width;
        int popupHeight = popup.getBounds().height;
        // Calculate the default rectangle for the popup.
        Rectangle popupRectangle = new Rectangle(defaultX, defaultY, popupWidth, popupHeight);
        // If the popup rectangle is below the bottom of the working area, then move it upwards by 
        // the minimum amount which will ensure that it will never cover the picker component.
        if (popupRectangle.getMaxY() > (workingArea.getMaxY() + bottomOverlapAllowed)) {
            popupRectangle.y = verticalFlipReference.getLocationOnScreen().y - popupHeight
                    - verticalFlipDistance;
        }
        // Confine the popup to be within the working area.
        if (popupRectangle.getMaxX() > (workingArea.getMaxX())) {
            popupRectangle.x -= (popupRectangle.getMaxX() - workingArea.getMaxX());
        }
        if (popupRectangle.getMaxY() > (workingArea.getMaxY() + bottomOverlapAllowed)) {
            popupRectangle.y -= (popupRectangle.getMaxY() - workingArea.getMaxY());
        }
        if (popupRectangle.x < workingArea.x) {
            popupRectangle.x += (workingArea.x - popupRectangle.x);
        }
        if (popupRectangle.y < workingArea.y) {
            popupRectangle.y += (workingArea.y - popupRectangle.y);
        }
        // Set the location of the popup.
        popup.setLocation(popupRectangle.x, popupRectangle.y);
    }

    /**
     * zEventToggleCalendarButtonMousePressed, This is called when the user clicks on the "toggle
     * calendar" button of the date picker.
     *
     * This will create a calendar panel and a popup, and display them to the user. If a calendar
     * panel is already opened, it will be closed instead.
     */
    private void zEventToggleCalendarButtonMousePressed(MouseEvent event) {
        togglePopup();
    }

    /**
     * zGetStandardTextFieldDateString, This returns a string for the supplied date (or null), in
     * the standard format which could be used for displaying that date in the text field.
     */
    private String zGetStandardTextFieldDateString(LocalDate date) {
        if (settings == null) {
            return "";
        }
        String standardDateString = "";
        if (date == null) {
            return standardDateString;
        }
        if (date.getEra() == IsoEra.CE) {
            standardDateString = date.format(settings.getFormatForDatesCommonEra());
        } else {
            standardDateString = date.format(settings.getFormatForDatesBeforeCommonEra());
        }
        return standardDateString;
    }

    /**
     * zInternalSetDateTextField, This is called whenever we need to programmatically change the
     * date text field. The purpose of this function is to make sure that text field change events
     * only occur once per programmatic text change, instead of occurring twice. The default
     * behavior is that the text change event will fire twice. (By default, it changes once to clear
     * the text, and changes once to change it to new text.)
     */
    private void zInternalSetDateTextField(String text) {
        skipTextFieldChangedFunctionWhileTrue = true;
        dateTextField.setText(text);
        skipTextFieldChangedFunctionWhileTrue = false;
        zEventTextFieldChanged();
    }

    /**
     * zInternalSetLastValidDateAndNotifyListeners, This should be called whenever we need to change
     * the last valid date variable. This will store the supplied last valid date. If needed, this
     * will notify all date change listeners that the date has been changed. This does -not- update
     * the displayed calendar, and does not perform any other tasks besides those described here.
     */
    private void zInternalSetLastValidDateAndNotifyListeners(LocalDate newDate) {
        LocalDate oldDate = lastValidDate;
        lastValidDate = newDate;
        if (!PickerUtilities.isSameLocalDate(oldDate, newDate)) {
            for (DateChangeListener dateChangeListener : dateChangeListeners) {
                DateChangeEvent dateChangeEvent = new DateChangeEvent(this, oldDate, newDate);
                dateChangeListener.dateChanged(dateChangeEvent);
            }
            // Fire a change event for beans binding.
            firePropertyChange("date", oldDate, newDate);
        }
    }

    /**
     * zEventTextFieldChanged, This is called whenever the text in the date picker text field has
     * changed, whether programmatically or by the user.
     *
     * If the current text contains a valid date, it will be stored in the variable lastValidDate.
     * Otherwise, the lastValidDate will not be changed.
     *
     * This will also call the function to indicate to the user if the currently text is a valid
     * date, invalid text, or a vetoed date. These indications are created by using font, color, and
     * background changes of the text field.
     */
    private void zEventTextFieldChanged() {
        if (settings == null) {
            return;
        }
        // Skip this function if it should not be run.
        if (skipTextFieldChangedFunctionWhileTrue) {
            return;
        }
        // Gather some variables that we will need.
        String dateText = dateTextField.getText();
        boolean textIsEmpty = dateText.trim().isEmpty();
        DateVetoPolicy vetoPolicy = settings.getVetoPolicy();
        boolean nullIsAllowed = settings.getAllowEmptyDates();
        // If the text is not empty, then try to parse the date.
        LocalDate parsedDate = null;
        if (!textIsEmpty) {
            parsedDate = InternalUtilities.getParsedDateOrNull(dateText,
                    settings.getFormatForDatesCommonEra(),
                    settings.getFormatForDatesBeforeCommonEra(),
                    settings.getFormatsForParsing());
        }
        // If the date was parsed successfully, then check it against the veto policy.
        boolean dateIsVetoed = false;
        if (parsedDate != null) {
            dateIsVetoed = InternalUtilities.isDateVetoed(vetoPolicy, parsedDate);
        }
        // If the date is a valid empty date, then set the last valid date to null.
        if (textIsEmpty && nullIsAllowed) {
            zInternalSetLastValidDateAndNotifyListeners(null);
        }
        // If the date is a valid parsed date, then store the last valid date.
        if ((!textIsEmpty) && (parsedDate != null) && (dateIsVetoed == false)) {
            zInternalSetLastValidDateAndNotifyListeners(parsedDate);
        }
        // Draw the date status indications for the user.
        zDrawTextFieldIndicators();
        // Fire a change event for beans binding.
        firePropertyChange("text", null, dateTextField.getText());
    }

    /**
     * initComponents, This initializes the components of the JFormDesigner panel. This function is
     * automatically generated by JFormDesigner from the JFD form design file, and should not be
     * modified by hand. This function can be modified, if needed, by using JFormDesigner.
     *
     * Implementation notes regarding JTextField: This class uses a JTextField instead of a
     * JFormattedTextField as its text input component, because a date-formatted JFormattedTextField
     * stores its value as a java.util.Date (Date) object, which is not ideal for this project. Date
     * objects represent a specific instant in time instead of a "local date" value. Date objects
     * also require time zone calculations to convert them to a java.time.LocalDate. This class
     * displays and stores "local dates" only, and is designed to function independently of any time
     * zone differences. See java.time.LocalDate for details on the meaning of a LocalDate. To gain
     * the validation functionality of a JFormattedTextField, this class implements a similar
     * "commit" and "revert" capability as the JFormattedTextField class.
     */
    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        dateTextField = new JTextField();
        toggleCalendarButton = new JButton();

        //======== this ========
        setLayout(new FormLayout(
                "pref:grow, [3px,pref], [26px,pref]",
                "fill:pref:grow"));

        //---- dateTextField ----
        dateTextField.setMargin(new Insets(1, 3, 2, 2));
        dateTextField.setBorder(new CompoundBorder(
                new MatteBorder(1, 1, 1, 1, new Color(122, 138, 153)),
                new EmptyBorder(1, 3, 2, 2)));
        dateTextField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                setTextFieldToValidStateIfNeeded();
            }
        });
        add(dateTextField, CC.xy(1, 1));

        //---- toggleCalendarButton ----
        toggleCalendarButton.setText("...");
        toggleCalendarButton.setFocusPainted(false);
        toggleCalendarButton.setFocusable(false);
        toggleCalendarButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                zEventToggleCalendarButtonMousePressed(e);
            }
        });
        add(toggleCalendarButton, CC.xy(3, 1));
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    /**
     * zDrawTextFieldIndicators, This will draw the text field indicators, to indicate to the user
     * the state of any text in the text field, including the validity of any date that has been
     * typed. The text field indicators include the text field background color, foreground color,
     * font color, and font.
     *
     * Note: This function is called automatically by the date picker. Under most circumstances, the
     * programmer will not need to call this function.
     *
     * List of possible text field states: DisabledComponent, ValidFullOrEmptyValue,
     * UnparsableValue, VetoedValue, DisallowedEmptyValue.
     */
    public void zDrawTextFieldIndicators() {
        if (settings == null) {
            return;
        }
        if (!isEnabled()) {
            // (Possibility: DisabledComponent)
            // Note: The date should always be validated (as if the component lost focus), before
            // the component is disabled.
            dateTextField.setBackground(new Color(240, 240, 240));
            dateTextField.setForeground(new Color(109, 109, 109));
            dateTextField.setFont(settings.getFontValidDate());
            return;
        }
        // Reset all atributes to normal before going further.
        // (Possibility: ValidFullOrEmptyValue)
        dateTextField.setBackground(settings.getColor(DateArea.TextFieldBackgroundValidDate));
        dateTextField.setForeground(settings.getColor(DateArea.DatePickerTextValidDate));
        dateTextField.setFont(settings.getFontValidDate());
        // Get the text, and check to see if it is empty.
        String dateText = dateTextField.getText();
        boolean textIsEmpty = dateText.trim().isEmpty();
        // Handle the various possibilities.
        if (textIsEmpty) {
            if (!settings.getAllowEmptyDates()) {
                // (Possibility: DisallowedEmptyValue)
                // Note, there is no text to reflect a foreground or font. 
                dateTextField.setBackground(settings.getColor(DateArea.TextFieldBackgroundDisallowedEmptyDate));
            }
            return;
        }
        // The text is not empty.
        LocalDate parsedDate = InternalUtilities.getParsedDateOrNull(dateText,
                settings.getFormatForDatesCommonEra(),
                settings.getFormatForDatesBeforeCommonEra(),
                settings.getFormatsForParsing());
        if (parsedDate == null) {
            // (Possibility: UnparsableValue)
            dateTextField.setBackground(settings.getColor(DateArea.TextFieldBackgroundInvalidDate));
            dateTextField.setForeground(settings.getColor(DateArea.DatePickerTextInvalidDate));
            dateTextField.setFont(settings.getFontInvalidDate());
            return;
        }
        // The text was parsed to a value.
        DateVetoPolicy vetoPolicy = settings.getVetoPolicy();
        boolean isDateVetoed = InternalUtilities.isDateVetoed(vetoPolicy, parsedDate);
        if (isDateVetoed) {
            // (Possibility: VetoedValue)
            dateTextField.setBackground(settings.getColor(DateArea.TextFieldBackgroundVetoedDate));
            dateTextField.setForeground(settings.getColor(DateArea.DatePickerTextVetoedDate));
            dateTextField.setFont(settings.getFontVetoedDate());
        }
    }

    /**
     * zEventCustomPopupWasClosed, This is called automatically whenever the CustomPopup that is
     * associated with this date picker is closed. This should be called regardless of the type of
     * event which caused the popup to close.
     *
     * Notes: The popup can be automatically closed for various reasons. 1) The user may press
     * escape. 2) The popup may lose focus. 3) The window may be moved. 4) The user may toggle the
     * calendar with the "toggle calendar" button. 5) The user may select a date in the calendar.
     */
    @Override
    public void zEventCustomPopupWasClosed(CustomPopup popup) {
        this.popup = null;
        calendarPanel = null;
        lastPopupCloseTime = Instant.now();
    }

    public void addComponentListener(ComponentListener listener) {
        if (componentListeners == null) {
            componentListeners = new ArrayList<ComponentListener>();
        }
        componentListeners.add(listener);
    }

    public void removeComponentListener(ComponentListener listener) {
        if (componentListeners == null) {
            return;
        }
        componentListeners.remove(listener);
    }

    protected void fireComponentEvent(ComponentEvent e) {
        if (componentListeners == null || e == null) {
            return;
        }
        for (ComponentListener listener : componentListeners) {
            listener.componentCreated(e);
        }
    }
}
