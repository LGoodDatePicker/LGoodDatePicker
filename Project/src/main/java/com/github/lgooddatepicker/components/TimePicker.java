package com.github.lgooddatepicker.components;

import com.privatejgoodies.forms.layout.FormLayout;
import com.privatejgoodies.forms.factories.CC;
import com.github.lgooddatepicker.zinternaltools.TimeMenuPanel;
import com.github.lgooddatepicker.components.TimePickerSettings.TimeArea;
import java.awt.*;
import javax.swing.border.*;
import com.github.lgooddatepicker.optionalusertools.PickerUtilities;
import com.github.lgooddatepicker.optionalusertools.TimeChangeListener;
import com.github.lgooddatepicker.optionalusertools.TimeVetoPolicy;
import com.github.lgooddatepicker.zinternaltools.CalculateMinimumTimeFieldSize;
import com.github.lgooddatepicker.zinternaltools.CustomPopup;
import com.github.lgooddatepicker.zinternaltools.InternalUtilities;
import com.github.lgooddatepicker.zinternaltools.TimeChangeEvent;
import com.github.lgooddatepicker.zinternaltools.TimeSpinnerTimer;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.Instant;
import java.time.LocalTime;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * TimePicker, This class implements a time picker GUI component.
 *
 * This class supports a complete set of "default functionality" without requiring any
 * TimePickerSettings. However, the settings of a time picker can optionally be customized by
 * creating a TimePickerSettings instance and passing it to the TimePicker constructor. After the
 * time picker is constructed, the settings instance should not be changed.
 *
 * By default, the language and internationalization settings of a time picker are determined from
 * the operating system defaults using Locale.getDefault(). If desired, the locale and language can
 * be modified by passing a locale to the constructor of a TimePickerSettings instance, and passing
 * that instance to the constructor of a TimePicker.
 *
 * Automatic Time Validation: Every time picker stores its current text, and its last valid time.
 * The last valid time is returned when you call TimePicker.getTime(). If a person uses their
 * keyboard to type into the text field, any text that is not a valid time will be displayed in red,
 * any vetoed time will have a strikethrough, and any valid time will be displayed in black. Valid
 * times are automatically committed to the time picker. Any invalid or vetoed text is automatically
 * reverted to the last valid time whenever the the time picker loses focus.
 *
 * <code>
 * // Basic usage example:
 * // Create a time picker.
 * TimePicker timePicker = new TimePicker();
 * // Create a panel, and add the time picker.
 * JPanel panel = new JPanel();
 * panel.add(timePicker);
 * </code>
 */
public class TimePicker
        extends JPanel implements CustomPopup.CustomPopupCloseListener {

    /**
     * enableArrowKeys, This determines if the arrow keys will be handled by this time picker. If
     * this is true, then the up and down arrow keys can be used as spinner controls, and the right
     * arrow will open the time picker menu. If this is false, then the arrow keys will not be
     * handled. This is set to true by default.
     */
    private boolean enableArrowKeys = true;

    /**
     * lastPopupCloseTime, This holds a timestamp that indicates when the popup menu was last
     * closed. This is used to implement a workaround for event behavior that was causing the time
     * picker class to erroneously re-open the menu when the user was clicking on the show menu
     * button in an attempt to close the previous menu.
     */
    private Instant lastPopupCloseTime = Instant.now();

    /**
     * lastValidTime, This holds the last valid time that was entered into the time picker. This
     * value is returned from the function TimePicker.getTime();
     *
     * Implementation note: After initialization, variable should never be -set- directly. Instead,
     * use the time setting function that will notify the list of timeChangeListeners each time that
     * this value is changed.
     */
    private LocalTime lastValidTime = null;

    /**
     * popup, This is the custom popup instance for this time picker. This should remain null until
     * a popup is opened. Creating a custom popup class allowed us to control the details of when
     * the popup menu should be open or closed.
     */
    private CustomPopup popup = null;

    /**
     * settings, This holds the settings instance for this time picker. Default settings are
     * generated automatically. Custom settings may optionally be supplied in the TimePicker
     * constructor.
     */
    private TimePickerSettings settings;

    /**
     * skipTextFieldChangedFunctionWhileTrue, While this is true, the function
     * "zTextFieldChangedSoIndicateIfValidAndStoreWhenValid()" will not be executed in response to
     * time text field text change events.
     */
    private boolean skipTextFieldChangedFunctionWhileTrue = false;

    /**
     * timeChangeListeners, This holds a list of time change listeners that wish to be notified
     * whenever the last valid time is changed.
     */
    private ArrayList<TimeChangeListener> timeChangeListeners = new ArrayList<TimeChangeListener>();

    /**
     * timeMenuPanel, This holds the menu panel GUI component of this time picker. This should be
     * null when the time picker menu is closed, and hold a time menu panel instance when the time
     * picker menu is opened.
     */
    private TimeMenuPanel timeMenuPanel;
    private TimeSpinnerTimer decreaseTimer = zCreateTimeSpinnerTimer(-1);
    private TimeSpinnerTimer increaseTimer = zCreateTimeSpinnerTimer(1);

    /**
     * JFormDesigner GUI components, These variables are automatically generated by JFormDesigner.
     * This section should not be modified by hand, but only modified from within the JFormDesigner
     * program.
     */
    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JTextField timeTextField;
    private JButton toggleTimeMenuButton;
    private JPanel spinnerPanel;
    private JButton increaseButton;
    private JButton decreaseButton;
    // JFormDesigner - End of variables declaration  //GEN-END:variables

    /**
     * Constructor with Default Values, Create a time picker instance using the default operating
     * system locale and language, and default time picker settings.
     */
    public TimePicker() {
        this(null);
    }

    /**
     * Constructor with Custom Settings, Create a time picker instance using the supplied time
     * picker settings.
     */
    public TimePicker(TimePickerSettings settings) {
        settings = (settings == null) ? new TimePickerSettings() : settings;
        settings.setParentTimePicker(this);
        this.settings = settings;
        initComponents();
        // Set the down arrow on the toggle menu button.
        toggleTimeMenuButton.setText("\u25BC");
        // Shrink the toggle menu button to a reasonable size.
        toggleTimeMenuButton.setMargin(new java.awt.Insets(4, 4, 4, 4));

        // Set up the spinner buttons.
        decreaseButton.setBorder(new MatteBorder(1, 1, 1, 1, new Color(122, 138, 153)));
        increaseButton.setBorder(new MatteBorder(1, 1, 1, 1, new Color(122, 138, 153)));
        decreaseButton.setMargin(new java.awt.Insets(0, 0, 0, 0));
        increaseButton.setMargin(new java.awt.Insets(0, 0, 0, 0));
        zInstallSpinnerButtonListener(decreaseButton);
        zInstallSpinnerButtonListener(increaseButton);

        // Add a change listener to the text field.
        zAddTextChangeListener();
        // Apply the settings instance to this time picker.
        settings.yApplyNeededSettingsAtTimePickerConstruction();

        // Add the arrow key listeners and any other key listeners to the text field.
        zAddKeyListenersToTextField();

        // Create a toggleTimeMenuButton listener for mouse dragging events.
        // Note: The toggleTimeMenuButton listeners should be created here in the constructor, 
        // because they do not require the timeMenuPanel to exist. These listeners are never 
        // deregistered. They will continue to exist for as long as the time picker exists.
        toggleTimeMenuButton.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent event) {
                if (timeMenuPanel != null) {
                    timeMenuPanel.mouseDraggedFromToggleButton();
                }
            }
        });

        // Create a toggleTimeMenuButton listener for mouse release events.
        toggleTimeMenuButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent event) {
                // Do nothing if the mouse was released inside the toggle button.
                Point mousePositionOnScreen = MouseInfo.getPointerInfo().getLocation();
                Rectangle toggleBoundsOnScreen = getComponentToggleTimeMenuButton().getBounds();
                toggleBoundsOnScreen.setLocation(getComponentToggleTimeMenuButton().getLocationOnScreen());
                if (toggleBoundsOnScreen.contains(mousePositionOnScreen)) {
                    return;
                }
                if (timeMenuPanel != null) {
                    timeMenuPanel.mouseReleasedFromToggleButtonOutsideButton();
                }
            }
        });

        // Draw the text field attributes, because they may not have been drawn if the initialTime
        // was null. (This is because the text would not have changed in that case.)
        zDrawTextFieldIndicators();

        // Set an appropriate minimum width for the time picker text field.
        // This may use a default calculated minimum width, or a programmer supplied minimum width,
        // as specified in the time picker settings.
        zSetAppropriateTextFieldMinimumWidth();
    }

    /**
     * addTimeChangeListener, This adds a time change listener to this time picker. For additional
     * details, see the TimeChangeListener class documentation.
     */
    public void addTimeChangeListener(TimeChangeListener listener) {
        timeChangeListeners.add(listener);
    }

    /**
     * clear, This will clear the time picker text. If the time picker is set to allow empty times,
     * then the last valid time will also be cleared. If the time picker is set to disallow empty
     * times, then the last valid time will not be changed by this function.
     */
    public void clear() {
        // Calling this function with null clears the time picker text. 
        // If empty times are allowed, this will also clear the last valid time.
        setTime(null);
    }

    /**
     * closePopup, This closes the menu popup. The popup can close itself automatically, so this
     * function does not generally need to be called programmatically.
     *
     * Notes: The popup can be automatically closed for various reasons. 1) The user may press
     * escape. 2) The popup may lose focus. 3) The window may be moved. 4) The user may toggle the
     * menu with the "toggle menu" button. 5) The user may select a time in the menu.
     */
    public void closePopup() {
        if (popup != null) {
            // The popup.hide() function handles the de-registration of the various listeners
            // associated with the popup window. This also initiates a callback to the
            // TimePicker.zEventcustomPopupWasClosed() function.
            popup.hide();
        }
    }

    /**
     * getBaseline, This returns the baseline value of the timeTextField.
     */
    @Override
    public int getBaseline(int width, int height) {
        if (timeTextField.isVisible()) {
            return timeTextField.getBaseline(width, height);
        }
        return super.getBaseline(width, height);
    }

    /**
     * getComponentDecreaseSpinnerButton, Returns the decrease spinner button that is used by this
     * time picker. The spinner buttons are only visible if the programmer has enabled them in the
     * TimePickerSettings.
     *
     * Note: Direct access to the time picker components is provided so the programmer can perform
     * advanced or unusual modifications of the time picker's appearance or behavior. However,
     * directly accessing the components should be used only as a last resort, to implement
     * functionality that is not available from other functions or settings.
     */
    public JButton getComponentDecreaseSpinnerButton() {
        return decreaseButton;
    }

    /**
     * getComponentIncreaseSpinnerButton, Returns the increase spinner button that is used by this
     * time picker. The spinner buttons are only visible if the programmer has enabled them in the
     * TimePickerSettings.
     *
     * Note: Direct access to the time picker components is provided so the programmer can perform
     * advanced or unusual modifications of the time picker's appearance or behavior. However,
     * directly accessing the components should be used only as a last resort, to implement
     * functionality that is not available from other functions or settings.
     */
    public JButton getComponentIncreaseSpinnerButton() {
        return increaseButton;
    }

    /**
     * getComponentSpinnerPanel, Returns the panel that holds the spinner buttons in this time
     * picker. The spinner buttons (and panel) are only visible if the programmer has enabled them
     * in the TimePickerSettings.
     *
     * Note: Direct access to the time picker components is provided so the programmer can perform
     * advanced or unusual modifications of the time picker's appearance or behavior. However,
     * directly accessing the components should be used only as a last resort, to implement
     * functionality that is not available from other functions or settings.
     */
    public JPanel getComponentSpinnerPanel() {
        return spinnerPanel;
    }

    /**
     * getComponentTimeTextField, Returns the time text field that is used by this time picker. It
     * is not expected that most programmers will need or want to use this function.
     *
     * Important Note: Direct access to the text field is provided so the programmer can perform
     * advanced or unusual modifications of the time picker's appearance or behavior. However, this
     * function should be used only as a last resort, to implement functionality that is not
     * available from other functions or settings.
     *
     * The TimePickerSettings class provides multiple ways to customize the time picker, and those
     * functions should be preferred for the needs they address. It is suggested that the programmer
     * become familiar with the functions and settings in TimePickerSettings class before directly
     * accessing the time picker text field.
     *
     * The TimePickerSettings class can customize the following text field attributes: The
     * background and foreground colors, the fonts, font size, and font colors, and the time
     * formats.
     *
     * Warning: To ensure proper behavior of the time picker, the text field should not be used to
     * get or set the the time (or text) values of the time picker. Instead, the programmer should
     * use the TimePicker.getTime() and TimePicker.getText() (and Set) functions for those purposes.
     * The TimeChangeListener interface can be used to listen for changes to the time value.
     */
    public JTextField getComponentTimeTextField() {
        return timeTextField;
    }

    /**
     * getComponentToggleTimeMenuButton, Returns the toggle time menu button that is used by this
     * time picker. Direct access to the time menu button is provided so that the programmer can
     * optionally assign an image to the button, or perform advanced or unusual modifications to the
     * time picker's appearance and behavior.
     *
     * Note: This should not be used to programmatically open or close the time picker menu. The
     * following functions are provided for that purpose: openPopup(), closePopup(), and
     * togglePopup().
     */
    public JButton getComponentToggleTimeMenuButton() {
        return toggleTimeMenuButton;
    }

    /**
     * getEnableArrowKeys, Returns the value of this setting. See the set function for additional
     * information.
     */
    public boolean getEnableArrowKeys() {
        return enableArrowKeys;
    }

    /**
     * getTime, This returns the last valid time, or returns null to represent an empty time.
     *
     * If "TimePickerSettings.allowEmptyTimes" is true, then this can return null. If allow empty
     * times is false, then this can never return null.
     *
     * Note: If the automatic validation of time picker text has not yet occurred, then the the last
     * valid time may not always match the current text.
     *
     * <pre>
     * Additional Text Validation Details:
     * Whenever the current time picker text is not valid, the value returned by getTime()
     * will generally not match the time picker text. The time picker can contain invalid text
     * whenever both items (1) and (2) below are true:
     *
     * 1) The user has manually typed text that cannot be parsed by the parsing formats into a valid
     * time, or the user has typed a time that is vetoed by a current veto policy, or the user has
     * cleared (or left only whitespace) in the text when allowEmptyTimes is false.
     *
     * 2) The time picker text field has continued to have focus, and therefore the automatic
     * validation (revert/commit) process has not yet occurred.
     * </pre>
     */
    public LocalTime getTime() {
        return lastValidTime;
    }

    /**
     * getTimeChangeListeners, This returns a new ArrayList, that contains any time change listeners
     * that are registered with this TimePicker.
     */
    public ArrayList<TimeChangeListener> getTimeChangeListeners() {
        return new ArrayList<TimeChangeListener>(timeChangeListeners);
    }

    /**
     * getTimeStringOrEmptyString, This will return the last valid time as a string. If the last
     * valid time is empty, this will return an empty string ("").
     *
     * Time values will be output in one of the following ISO-8601 formats: "HH:mm", "HH:mm:ss",
     * "HH:mm:ss.SSS", "HH:mm:ss.SSSSSS", "HH:mm:ss.SSSSSSSSS".
     *
     * The format used will be the shortest that outputs the full value of the time where the
     * omitted parts are implied to be zero.
     */
    public String getTimeStringOrEmptyString() {
        LocalTime time = getTime();
        return (time == null) ? "" : time.toString();
    }

    /**
     * getTimeStringOrSuppliedString, This will return the last valid time as a string. If the last
     * valid time is empty, this will return the value of emptyTimeString.
     *
     * Time values will be output in one of the following ISO-8601 formats: "HH:mm", "HH:mm:ss",
     * "HH:mm:ss.SSS", "HH:mm:ss.SSSSSS", "HH:mm:ss.SSSSSSSSS".
     *
     * The format used will be the shortest that outputs the full value of the time where the
     * omitted parts are implied to be zero.
     */
    public String getTimeStringOrSuppliedString(String emptyTimeString) {
        LocalTime time = getTime();
        return (time == null) ? emptyTimeString : time.toString();
    }

    /**
     * getSettings, This returns the time picker settings instance.
     */
    public TimePickerSettings getSettings() {
        return settings;
    }

    /**
     * getText, This returns the current text that is present in the time picker text field. This
     * text can contain anything that was written by the user. It is specifically not guaranteed to
     * contain a valid time. This should not be used to retrieve the time picker time. Instead, use
     * TimePicker.getTime() for retrieving the time.
     */
    public String getText() {
        return timeTextField.getText();
    }

    /**
     * isEnabled, Returns true if this component is enabled, otherwise returns false.
     */
    @Override
    public boolean isEnabled() {
        return super.isEnabled();
    }

    /**
     * isPopupOpen, This returns true if the time menu popup is open. This returns false if the time
     * menu popup is closed
     */
    public boolean isPopupOpen() {
        return (popup != null);
    }

    /**
     * isTextFieldValid, This returns true if, and only if, the text field contains a parsable time
     * or a valid empty string. Note that this does not guarantee that the text in the text field is
     * in a standard format. Valid times can be in any one of the parsingFormats that are accepted
     * by the time picker.
     *
     * More specifically, this returns true if: 1) the text field contains a parsable time that
     * exists, and that has not been vetoed by a current veto policy, OR 2) (allowEmptyTime == true)
     * and timeTextField.getText().trim() contains an empty string. Otherwise returns false.
     */
    public boolean isTextFieldValid() {
        return isTextValid(timeTextField.getText());
    }

    /**
     * isTextValid, This function can be used to see if the supplied text represents a "valid time"
     * according to the settings of this time picker.
     *
     * More specifically, this returns true if: 1) the text contains a parsable time that exists,
     * and that has not been vetoed by a current veto policy, OR 2) (allowEmptyTimes == true) and
     * text.trim() contains an empty string. Otherwise returns false.
     */
    public boolean isTextValid(String text) {
        // If the text is null, return false.
        if (text == null) {
            return false;
        }
        // If the text is empty, return the value of allowEmptyTimes.
        text = text.trim();
        if (text.isEmpty()) {
            return settings.getAllowEmptyTimes();
        }
        // Try to get a parsed time.
        LocalTime parsedTime = InternalUtilities.getParsedTimeOrNull(text, settings.getFormatForDisplayTime(), settings.getFormatForMenuTimes(),
                settings.formatsForParsing, settings.getLocale());

        // If the time could not be parsed, return false.
        if (parsedTime == null) {
            return false;
        }
        // If the time is vetoed, return false.
        TimeVetoPolicy vetoPolicy = settings.getVetoPolicy();
        if (InternalUtilities.isTimeVetoed(vetoPolicy, parsedTime)) {
            return false;
        }
        // The time is valid, so return true.
        return true;
    }

    /**
     * isTimeAllowed, This checks to see if the specified time is allowed by any currently set veto
     * policy, and allowed by the current setting of allowEmptyTimes.
     *
     * If allowEmptyTimes is false, and the specified time is null, then this returns false.
     *
     * If a veto policy exists, and the specified time is vetoed, then this returns false.
     *
     * If the time is not vetoed, or if empty times are allowed and the time is null, then this
     * returns true.
     */
    public boolean isTimeAllowed(LocalTime time) {
        return settings.isTimeAllowed(time);
    }

    /**
     * openPopup, This creates and shows the menu popup.
     *
     * This function creates a new menu panel and a new custom popup instance each time that it is
     * called. The associated object instances are automatically disposed and set to null when a
     * popup is closed.
     */
    public void openPopup() {
        // If the component is disabled, do nothing.
        if (!isEnabled()) {
            return;
        }
        // If the show popup menu button is not shown, do nothing.
        if (settings.getDisplayToggleTimeMenuButton() == false) {
            return;
        }
        // If this function was called programmatically, we may need to change the focus to this
        // popup.
        if (!timeTextField.hasFocus()) {
            timeTextField.requestFocusInWindow();
        }
        // Create a new time menu.
        timeMenuPanel = new TimeMenuPanel(this, settings);

        // Create a new custom popup.
        popup = new CustomPopup(timeMenuPanel, SwingUtilities.getWindowAncestor(this),
                this, settings.borderTimePopup);
        popup.setMinimumSize(new Dimension(
                this.getSize().width + 1, timeMenuPanel.getSize().height));
        // Calculate the default origin for the popup.
        int defaultX = timeTextField.getLocationOnScreen().x;
        int defaultY = timeTextField.getLocationOnScreen().y + timeTextField.getSize().height - 1;
        // Set the popup location. (Shared function.)
        DatePicker.zSetPopupLocation(popup, defaultX, defaultY, this, timeTextField, -1, 1);
        // Show the popup and request focus.
        popup.show();
        timeMenuPanel.requestListFocus();
    }

    /**
     * removeTimeChangeListener, This removes the specified time change listener from this time
     * picker.
     */
    public void removeTimeChangeListener(TimeChangeListener listener) {
        timeChangeListeners.remove(listener);
    }

    /**
     * setEnableArrowKeys, This sets the variable that determines if the arrow keys will be handled
     * by this time picker. If this is set to true, then the up and down arrow keys can be used as
     * spinner controls, and the right arrow will open the time picker menu. If this is set to
     * false, then the arrow keys will not be handled. This is set to true by default.
     */
    public void setEnableArrowKeys(boolean enableArrowKeys) {
        this.enableArrowKeys = enableArrowKeys;
    }

    /**
     * setEnabled, This enables or disables the time picker. When the time picker is enabled, times
     * can be selected by the user using any methods that are allowed by the current settings. When
     * the time picker is disabled, there is no way for the user to interact with the component.
     * More specifically, times cannot be selected with the mouse, or with the keyboard, and the
     * time picker components change their color scheme to indicate the disabled state. Any
     * currently stored text and last valid time values are retained while the component is
     * disabled.
     */
    @Override
    public void setEnabled(boolean enabled) {
        if (enabled == false) {
            closePopup();
        }
        setTextFieldToValidStateIfNeeded();
        super.setEnabled(enabled);
        toggleTimeMenuButton.setEnabled(enabled);
        timeTextField.setEnabled(enabled);
        zDrawTextFieldIndicators();
    }

    /**
     * setText, This sets the text of the time picker text field to the supplied value. This will
     * have the same effect on the last valid time as if the user was typing into the text field. In
     * other words, it may or may not change the last valid time. This should not be used to set the
     * time of the time picker, instead use TimePicker.setTime().
     */
    public void setText(String text) {
        zInternalSetTimeTextField(text);
        timeTextField.requestFocusInWindow();
    }

    /**
     * setTextFieldToValidStateIfNeeded,
     *
     * This function will check the contents of the text field, and when needed, will set the text
     * to match the "last valid time" in a standardized valid format. This function is automatically
     * called whenever the time picker text field loses focus, or at other times when the text must
     * be set to a valid state. It is not expected that the programmer will normally need to call
     * this function directly.
     *
     * This function has two possible effects: 1) If the current text is already valid and is in the
     * standard format, then this will do nothing. If the text is not valid, or if the text is not
     * in the standard format, then: 2) This will replace the invalid text in the text field with a
     * standard time field text string that matches the last valid time.
     */
    public void setTextFieldToValidStateIfNeeded() {
        // Find out if the text field needs to be set to the last valid time or not.
        // The text field needs to be set whenever its text does not match the standard format
        // for the last valid time.
        String standardTimeString = zGetStandardTextFieldTimeString(lastValidTime);
        String textFieldString = timeTextField.getText();
        if (!standardTimeString.equals(textFieldString)) {
            // Overwrite the text field with the last valid time.
            // This will clear the text field if the last valid time is null.
            setTime(lastValidTime);
        }
    }

    /**
     * setTime, This sets this time picker to the specified time. Times that are set from this
     * function are processed through the same validation procedures as times that are entered by
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
     * A value can be checked against any current veto policy, and against the allowEmptyTimes
     * setting, by calling isTimeAllowed(). This can be used to determine (in advance), if a
     * particular value would be allowed.
     *
     * Note: If empty times are allowed, and the component does not have a veto policy, then all
     * possible values will (always) be allowed. These are the default settings of this component.
     *
     * Implementation Note: Whenever the text field changes to a valid time string, the
     * lastValidTime is also automatically set (unless the time is vetoed). This occurs through the
     * DocumentListener which is registered on the timeTextField.
     */
    public void setTime(LocalTime optionalTime) {
        // Set the text field to the supplied time, using the standard format for null, or a time.
        String standardTimeString = zGetStandardTextFieldTimeString(optionalTime);
        String textFieldString = timeTextField.getText();
        // We will only change the time, when the text of the last valid time does not match the
        // supplied time. This will prevent any registered time change listeners from receiving
        // any events unless the time actually changes.
        if ((!standardTimeString.equals(textFieldString))) {
            zInternalSetTimeTextField(standardTimeString);
        }
    }

    /**
     * setTimeToNow, This sets the time to the current time. This function is subject to the same
     * validation behaviors as if a user typed the current time into the time picker. See setTime()
     * for additional details.
     */
    public void setTimeToNow() {
        setTime(LocalTime.now());
    }

    /**
     * toString, This will return the last valid time as a string. If the last valid time is empty,
     * this will return an empty string ("").
     *
     * Time values will be output in one of the following ISO-8601 formats: "HH:mm", "HH:mm:ss",
     * "HH:mm:ss.SSS", "HH:mm:ss.SSSSSS", "HH:mm:ss.SSSSSSSSS".
     *
     * The format used will be the shortest that outputs the full value of the time where the
     * omitted parts are implied to be zero.
     *
     * This returns the same value as getTimeStringOrEmptyString()
     */
    @Override
    public String toString() {
        return getTimeStringOrEmptyString();
    }

    /**
     * togglePopup, This creates and shows a menu popup. If the popup is already open, then this
     * will close the popup.
     *
     * This is called when the user clicks on the toggle menu button of the time picker. This
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
     * zAddKeyListenersToTextField, This adds key listeners to the text field. The right arrow key
     * will open the drop-down menu. The up and down arrow keys will activate the spinner abilities
     * of the time picker, and increase or decrease the time.
     */
    private void zAddKeyListenersToTextField() {
        timeTextField.addKeyListener(new KeyAdapter() {
            /**
             * upPressed, This indicates whether or not the up arrow has already been pressed. This
             * is used to make sure that we do not "react" to the up arrow multiple times when the
             * keyboard auto repeat function begins. We will only react to the up arrow once, until
             * the up arrow is released.
             */
            boolean upPressed = false;
            /**
             * downPressed, This indicates whether or not the down arrow has already been pressed.
             * This is used to make sure that we do not "react" to the down arrow multiple times
             * when the keyboard auto repeat function begins. We will only react to the down arrow
             * once, until the down arrow is released.
             */
            boolean downPressed = false;

            /**
             * keyPressed, This is called when a key is pressed inside the text field.
             */
            @Override
            public void keyPressed(KeyEvent e) {
                // Handled the right arrow key, which opens the pop-up menu.
                if (enableArrowKeys && e.isActionKey() && e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    e.consume();
                    openPopup();
                    if (popup != null) {
                        timeMenuPanel.selectFirstEntry();
                    }
                }
                // Handled the up arrow key, which activates the spinner function to increase 
                // the time value.
                if (enableArrowKeys && e.isActionKey() && e.getKeyCode() == KeyEvent.VK_UP) {
                    e.consume();
                    if (upPressed || !isEnabled()) {
                        return;
                    }
                    upPressed = true;
                    if (getTime() == null) {
                        setTime(LocalTime.NOON);
                    }
                    zInternalTryChangeTimeByIncrement(1);
                    increaseTimer.start();
                }
                // Handled the down arrow key, which activates the spinner function to decrease 
                // the time value.
                if (enableArrowKeys && e.isActionKey() && e.getKeyCode() == KeyEvent.VK_DOWN) {
                    e.consume();
                    if (downPressed || !isEnabled()) {
                        return;
                    }
                    downPressed = true;
                    if (getTime() == null) {
                        setTime(LocalTime.NOON);
                    }
                    zInternalTryChangeTimeByIncrement(-1);
                    decreaseTimer.start();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (e.isActionKey() && e.getKeyCode() == KeyEvent.VK_UP) {
                    e.consume();
                    upPressed = false;
                    increaseTimer.stop();
                }
                if (e.isActionKey() && e.getKeyCode() == KeyEvent.VK_DOWN) {
                    e.consume();
                    decreaseTimer.stop();
                    downPressed = false;
                }
            }

        });
    }

    /**
     * zAddTextChangeListener, This add a text change listener to the time text field, so that we
     * can respond to text as it is typed.
     */
    private void zAddTextChangeListener() {
        timeTextField.getDocument().addDocumentListener(new DocumentListener() {
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
     * zCreateTimeSpinnerTimer, This creates a time spinner timer for this time picker, which is set
     * to the specified amount of changeAmountMinutes. The changeAmountMinutes indicates how many
     * minutes will be added to the currently set time with each increment. Negative values are
     * accepted. The changeAmountMinutes parameter will normally be set to -1 or 1.
     */
    private TimeSpinnerTimer zCreateTimeSpinnerTimer(int changeAmountMinutes) {
        return new TimeSpinnerTimer(this, changeAmountMinutes);
    }

    /**
     * zDrawTextFieldIndicators, This will draw the text field indicators, to indicate to the user
     * the state of any text in the text field, including the validity of any time that has been
     * typed. The text field indicators include the text field background color, foreground color,
     * font color, and font.
     *
     * Note: This function is called automatically by the time picker. Under most circumstances, the
     * programmer will not need to call this function.
     *
     * List of possible text field states: DisabledComponent, ValidFullOrEmptyValue,
     * UnparsableValue, VetoedValue, DisallowedEmptyValue.
     */
    public void zDrawTextFieldIndicators() {
        if (!isEnabled()) {
            // (Possibility: DisabledComponent)
            // Note: The time should always be validated (as if the component lost focus), before
            // the component is disabled.
            timeTextField.setBackground(new Color(240, 240, 240));
            timeTextField.setForeground(new Color(109, 109, 109));
            timeTextField.setFont(settings.fontValidTime);
            return;
        }
        // Reset all atributes to normal before going further.
        // (Possibility: ValidFullOrEmptyValue)
        timeTextField.setBackground(settings.getColor(TimeArea.TextFieldBackgroundValidTime));
        timeTextField.setForeground(settings.getColor(TimeArea.TimePickerTextValidTime));
        timeTextField.setFont(settings.fontValidTime);
        // Get the text, and check to see if it is empty.
        String timeText = timeTextField.getText();
        boolean textIsEmpty = timeText.trim().isEmpty();
        // Handle the various possibilities.
        if (textIsEmpty) {
            if (settings.getAllowEmptyTimes()) {
                // (Possibility: ValidFullOrEmptyValue)
            } else {
                // (Possibility: DisallowedEmptyValue)
                timeTextField.setBackground(settings.getColor(TimeArea.TextFieldBackgroundDisallowedEmptyTime));
            }
            return;
        }
        // The text is not empty.
        LocalTime parsedTime = InternalUtilities.getParsedTimeOrNull(timeText, settings.getFormatForDisplayTime(), settings.getFormatForMenuTimes(),
                settings.formatsForParsing, settings.getLocale());
        if (parsedTime == null) {
            // (Possibility: UnparsableValue)
            timeTextField.setBackground(settings.getColor(TimeArea.TextFieldBackgroundInvalidTime));
            timeTextField.setForeground(settings.getColor(TimeArea.TimePickerTextInvalidTime));
            timeTextField.setFont(settings.fontInvalidTime);
            return;
        }
        // The text was parsed to a value.
        TimeVetoPolicy vetoPolicy = settings.getVetoPolicy();
        boolean isTimeVetoed = InternalUtilities.isTimeVetoed(vetoPolicy, parsedTime);
        if (isTimeVetoed) {
            // (Possibility: VetoedValue)
            timeTextField.setBackground(settings.getColor(TimeArea.TextFieldBackgroundVetoedTime));
            timeTextField.setForeground(settings.getColor(TimeArea.TimePickerTextVetoedTime));
            timeTextField.setFont(settings.fontVetoedTime);
        }
    }

    /**
     * zEventCustomPopupWasClosed, This is called automatically whenever the CustomPopup that is
     * associated with this time picker is closed. This should be called regardless of the type of
     * event which caused the popup to close.
     *
     * Notes: The popup can be automatically closed for various reasons. 1) The user may press
     * escape. 2) The popup may lose focus. 3) The window may be moved. 4) The user may toggle the
     * popup menu with the "toggle time menu" button. 5) The user may select a time in the popup
     * menu.
     */
    @Override
    public void zEventCustomPopupWasClosed(CustomPopup popup) {
        popup = null;
        if (timeMenuPanel != null) {
            timeMenuPanel.clearParent();
        }
        timeMenuPanel = null;
        lastPopupCloseTime = Instant.now();
    }

    /**
     * zGetStandardTextFieldTimeString, This returns a string for the supplied time (or null), in
     * the standard format which could be used for displaying that time in the text field.
     */
    private String zGetStandardTextFieldTimeString(LocalTime time) {
        String standardTimeString = "";
        if (time == null) {
            return standardTimeString;
        }
        standardTimeString = time.format(settings.getFormatForDisplayTime());
        return standardTimeString;
    }

    /**
     * zInstallSpinnerButtonListener, This installs the mouse listeners on the spinner buttons.
     * These most listeners will listen for mouse pressed and mouse released the events, to start
     * and stop the spinner timers. The spinner timers will increase or decrease the time contained
     * in this time picker at a rate that accelerates over a set range of time.
     */
    private void zInstallSpinnerButtonListener(Component spinnerButton) {
        spinnerButton.addMouseListener(new MouseAdapter() {
            /**
             * mouseReleased, This will be called when the spinner button is pressed down. This is
             * only called once before release no matter how long the mouse button is held down.
             */
            @Override
            public void mousePressed(MouseEvent event) {
                if (!isEnabled()) {
                    return;
                }
                if (getTime() == null) {
                    setTime(LocalTime.NOON);
                }
                if (event.getSource() == getComponentDecreaseSpinnerButton()) {
                    setTime(getTime().plusMinutes(-1));
                    decreaseTimer.start();
                } else {
                    setTime(getTime().plusMinutes(1));
                    increaseTimer.start();
                }
            }

            /**
             * mouseReleased, This will be called when the spinner button is released.
             */
            @Override
            public void mouseReleased(MouseEvent event) {
                if (event.getSource() == getComponentDecreaseSpinnerButton()) {
                    decreaseTimer.stop();
                } else {
                    increaseTimer.stop();
                }
            }
        });
    }

    /**
     * zInternalSetLastValidTimeAndNotifyListeners, This should be called whenever we need to change
     * the last valid time variable. This will store the supplied last valid time. If needed, this
     * will notify all time change listeners that the time has been changed. This does not perform
     * any other tasks besides those described here.
     */
    private void zInternalSetLastValidTimeAndNotifyListeners(LocalTime newTime) {
        LocalTime oldTime = lastValidTime;
        lastValidTime = newTime;
        if (!PickerUtilities.isSameLocalTime(oldTime, newTime)) {
            for (TimeChangeListener timeChangeListener : timeChangeListeners) {
                TimeChangeEvent timeChangeEvent = new TimeChangeEvent(this, oldTime, newTime);
                timeChangeListener.timeChanged(timeChangeEvent);
            }
            // Fire a change event for beans binding.
            firePropertyChange("time", oldTime, newTime);
        }
    }

    /**
     * zInternalSetTimeTextField, This is called whenever we need to programmatically change the
     * time text field. The purpose of this function is to make sure that text field change events
     * only occur once per programmatic text change, instead of occurring twice. The default
     * behavior is that the text change event will fire twice. (By default, it changes once to clear
     * the text, and changes once to change it to new text.)
     */
    private void zInternalSetTimeTextField(String text) {
        skipTextFieldChangedFunctionWhileTrue = true;
        if (settings.useLowercaseForDisplayTime) {
            text = text.toLowerCase(settings.getLocale());
        }
        timeTextField.setText(text);
        skipTextFieldChangedFunctionWhileTrue = false;
        zEventTextFieldChanged();
    }

    public void zInternalTryChangeTimeByIncrement(int changeAmountMinutes) {
        LocalTime timeToTry = getTime().plusMinutes(changeAmountMinutes);
        if (!InternalUtilities.isTimeVetoed(settings.getVetoPolicy(), timeToTry)) {
            setTime(timeToTry);
        }
    }

    /**
     * zSetAppropriateTextFieldMinimumWidth, This sets the minimum (and preferred) width of the time
     * picker text field. The width will be determined (or calculated) from the current time picker
     * settings, as described below.
     *
     * If the programmer has not supplied a setting for the minimum size, then a default minimum
     * size will be calculated from the current display format (which includes the locale
     * information), and the font for valid times.
     *
     * If the programmer has supplied a setting for the text field minimum size, then the programmer
     * supplied value will be used instead, unless a default override is allowed. (In this case, the
     * default value will only be used if the default setting is larger than the programmer supplied
     * setting).
     *
     * For additional details, see the javadoc for this function:
     * TimePickerSettings.setSizeTextFieldMinimumWidthDefaultOverride().
     */
    public void zSetAppropriateTextFieldMinimumWidth() {
        Integer programmerSuppliedWidth = settings.getSizeTextFieldMinimumWidth();
        // Determine the appropriate minimum width for the text field.
        int minimumWidthPixels = CalculateMinimumTimeFieldSize.getFormattedTimeWidthInPixels(
                settings.getFormatForDisplayTime(), settings.fontValidTime, 0);
        if (programmerSuppliedWidth != null) {
            if (settings.getSizeTextFieldMinimumWidthDefaultOverride()) {
                minimumWidthPixels = Math.max(programmerSuppliedWidth, minimumWidthPixels);
            } else {
                minimumWidthPixels = programmerSuppliedWidth;
            }
        }
        // Apply the minimum and preferred width to the text field.
        // Note that we only change the width, not the height.
        Dimension newMinimumSize = timeTextField.getMinimumSize();
        newMinimumSize.width = minimumWidthPixels;
        timeTextField.setMinimumSize(newMinimumSize);
        Dimension newPreferredSize = timeTextField.getPreferredSize();
        newPreferredSize.width = minimumWidthPixels;
        timeTextField.setPreferredSize(newPreferredSize);
        this.validate();
    }

    /**
     * zEventTextFieldChanged, This is called whenever the text in the time picker text field has
     * changed, whether programmatically or by the user.
     *
     * If the current text contains a valid time, it will be stored in the variable lastValidTime.
     * Otherwise, the lastValidTime will not be changed.
     *
     * This will also call the function to indicate to the user if the currently text is a valid
     * time, invalid text, or a vetoed time. These indications are created by using font, color, and
     * background changes of the text field.
     */
    private void zEventTextFieldChanged() {
        // Skip this function if it should not be run.
        if (skipTextFieldChangedFunctionWhileTrue) {
            return;
        }
        // Gather some variables that we will need.
        String timeText = timeTextField.getText();
        boolean textIsEmpty = timeText.trim().isEmpty();
        TimeVetoPolicy vetoPolicy = settings.getVetoPolicy();
        boolean nullIsAllowed = settings.getAllowEmptyTimes();
        // If the text is not empty, then try to parse the time.
        LocalTime parsedTime = null;
        if (!textIsEmpty) {
            parsedTime = InternalUtilities.getParsedTimeOrNull(timeText, settings.getFormatForDisplayTime(), settings.getFormatForMenuTimes(),
                    settings.formatsForParsing, settings.getLocale());
        }
        // If the time was parsed successfully, then check it against the veto policy.
        boolean timeIsVetoed = false;
        if (parsedTime != null) {
            timeIsVetoed = InternalUtilities.isTimeVetoed(vetoPolicy, parsedTime);
        }
        // If the time is a valid empty time, then set the last valid time to null.
        if (textIsEmpty && nullIsAllowed) {
            zInternalSetLastValidTimeAndNotifyListeners(null);
        }
        // If the time is a valid parsed time, then store the last valid time.
        if ((!textIsEmpty) && (parsedTime != null) && (timeIsVetoed == false)) {
            zInternalSetLastValidTimeAndNotifyListeners(parsedTime);
        }
        // Draw the time status indications for the user.
        zDrawTextFieldIndicators();
        // Fire the text change event for beans binding.
        firePropertyChange("text", null, timeTextField.getText());
    }

    /**
     * zEventToggleTimeMenuButtonMousePressed, This is called when the user clicks on the "toggle
     * time menu" button of the time picker.
     *
     * This will create a time menu panel and a popup, and display them to the user. If a time menu
     * panel is already opened, it will be closed instead.
     */
    private void zEventToggleTimeMenuButtonMousePressed(MouseEvent e) {
        togglePopup();
    }

    /**
     * initComponents, This initializes the components of the JFormDesigner panel. This function is
     * automatically generated by JFormDesigner from the JFD form design file, and should not be
     * modified by hand. This function can be modified, if needed, by using JFormDesigner.
     */
    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        timeTextField = new JTextField();
        toggleTimeMenuButton = new JButton();
        spinnerPanel = new JPanel();
        increaseButton = new JButton();
        decreaseButton = new JButton();

        //======== this ========
        setLayout(new FormLayout(
                "pref:grow, 3*(pref)",
                "fill:pref:grow"));

        //---- timeTextField ----
        timeTextField.setMargin(new Insets(1, 3, 2, 2));
        timeTextField.setBorder(new CompoundBorder(
                new MatteBorder(1, 1, 1, 1, new Color(122, 138, 153)),
                new EmptyBorder(1, 3, 2, 2)));
        timeTextField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                setTextFieldToValidStateIfNeeded();
            }
        });
        add(timeTextField, CC.xy(1, 1));

        //---- toggleTimeMenuButton ----
        toggleTimeMenuButton.setText("v");
        toggleTimeMenuButton.setFocusPainted(false);
        toggleTimeMenuButton.setFocusable(false);
        toggleTimeMenuButton.setFont(new Font("Segoe UI", Font.PLAIN, 8));
        toggleTimeMenuButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                zEventToggleTimeMenuButtonMousePressed(e);
            }
        });
        add(toggleTimeMenuButton, CC.xy(3, 1));

        //======== spinnerPanel ========
        {
            spinnerPanel.setLayout(new FormLayout(
                    "default",
                    "fill:pref:grow, fill:default:grow"));
            ((FormLayout) spinnerPanel.getLayout()).setRowGroups(new int[][]{{1, 2}});

            //---- increaseButton ----
            increaseButton.setFocusPainted(false);
            increaseButton.setFocusable(false);
            increaseButton.setFont(new Font("Arial", Font.PLAIN, 8));
            increaseButton.setText("+");
            spinnerPanel.add(increaseButton, CC.xy(1, 1));

            //---- decreaseButton ----
            decreaseButton.setFocusPainted(false);
            decreaseButton.setFocusable(false);
            decreaseButton.setFont(new Font("Arial", Font.PLAIN, 8));
            decreaseButton.setText("-");
            spinnerPanel.add(decreaseButton, CC.xy(1, 2));
        }
        add(spinnerPanel, CC.xy(4, 1));
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

}
