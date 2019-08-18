package com.github.lgooddatepicker.zinternaltools;

import com.privatejgoodies.forms.layout.FormLayout;
import com.privatejgoodies.forms.factories.CC;
import javax.swing.*;
import com.github.lgooddatepicker.optionalusertools.TimeVetoPolicy;
import com.github.lgooddatepicker.components.TimePicker;
import com.github.lgooddatepicker.components.TimePickerSettings;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class TimeMenuPanel extends JPanel {

    /**
     * minimumMouseReleaseFromToggleButtonMilliseconds, This is how long we should wait, before
     * allowing the time menu panel to be closed during a mouse release event that originates from
     * the toggle button. This is used to prevent accidental premature list closing when pressing
     * the toggle button and moving the mouse downwards. The mouse release event from the toggle
     * button will only be enabled after this amount of time has passed since construction.
     */
    private int minimumMouseReleaseFromToggleButtonMilliseconds = 400;

    /**
     * minimumTimeToEnableMouseReleaseFromToggleButton, This is the "currentTimeMillis()" value that
     * must be passed, to enable the mouse release event from the toggle button. This equals
     * ((System.currentTimeMillis() at construction) + minimumMouseReleaseCloseMilliSeconds). This
     * is calculated at the end of the constructor.
     */
    private long minimumTimeToEnableMouseReleaseFromToggleButton;

    /**
     * parentTimePicker, This holds our parent time picker instance. This is supplied at
     * construction.
     */
    private TimePicker parentTimePicker;

    /**
     * settings, This holds our time picker settings instance. This is supplied at construction.
     */
    private TimePickerSettings settings;

    /**
     * timeListModel, The holds the list model that is used for populating the time list.
     */
    final private DefaultListModel<String> timeListModel;

    public TimeMenuPanel(TimePicker parentTimePicker, TimePickerSettings settings) {
        this.parentTimePicker = parentTimePicker;
        this.settings = settings;
        initComponents();
        timeListModel = new DefaultListModel<>();
        timeList.setModel(timeListModel);

        timeList.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent event) {
                Point mousePositionRelativeToComponent = new Point(event.getX(), event.getY());
                int index = timeList.locationToIndex(mousePositionRelativeToComponent);
                if ((index != -1) && (index != timeList.getSelectedIndex())) {
                    timeList.setSelectedIndex(index);
                }
            }
        });
        timeList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent event) {
                mouseReleasedWhileTimeListIsOpen();
            }
        });
        timeList.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent event) {
                if (event.getKeyCode() == KeyEvent.VK_ENTER) {
                    event.consume();
                    String selectedTimeString = timeList.getSelectedValue();
                    if (selectedTimeString != null) {
                        userSelectedATime(selectedTimeString);
                    }
                }
            }
        });
        // Generate the time entries for the drop down menu.
        generateTimeEntriesFromSettings();
        // Set the maximum number of visible menu rows to the appropriate value.
        int maximumMenuRows = Math.min(settings.maximumVisibleMenuRows, timeListModel.getSize());
        timeList.setVisibleRowCount(maximumMenuRows);

        // Calculate and save the minimum system time, at which to enable the mouse release from
        // toggle button event.
        minimumTimeToEnableMouseReleaseFromToggleButton = System.currentTimeMillis()
                + minimumMouseReleaseFromToggleButtonMilliseconds;
    }

    public void mouseDraggedFromToggleButton() {
        Point mousePositionRelativeToScreen = MouseInfo.getPointerInfo().getLocation();
        Rectangle timeListBounds = timeList.getBounds();
        timeListBounds.setLocation(timeList.getLocationOnScreen());
        if (timeListBounds.contains(mousePositionRelativeToScreen)) {
            SwingUtilities.convertPointFromScreen(mousePositionRelativeToScreen, timeList);
            Point mousePositionRelativeToComponent = (Point) mousePositionRelativeToScreen.clone();
            int index = timeList.locationToIndex(mousePositionRelativeToComponent);
            if ((index != -1) && (index != timeList.getSelectedIndex())) {
                timeList.setSelectedIndex(index);
            }
        }
    }

    public void mouseReleasedFromToggleButtonOutsideButton() {
        // Make sure that the window has been open long enough for the user to read (or see) the
        // time menu, before allowing the popup to be closed from a mouse release event that 
        // originates from the toggle button.
        boolean enableMouseReleaseEventsFromToggleButton
                = (System.currentTimeMillis() > minimumTimeToEnableMouseReleaseFromToggleButton);

        // Check to see if mouse release events from the toggle button are enabled.
        if (!enableMouseReleaseEventsFromToggleButton) {
            return;
        }
        // Call the mouse release event funtion. 
        mouseReleasedWhileTimeListIsOpen();
    }

    private void mouseReleasedWhileTimeListIsOpen() {
        // Get the mouse position and time list bounds. 
        Point mousePositionRelativeToScreen = MouseInfo.getPointerInfo().getLocation();
        Rectangle timeListBounds = timeList.getBounds();
        timeListBounds.setLocation(timeList.getLocationOnScreen());
        // If the release happened outside of the list, close the popup and do nothing.
        if (!timeListBounds.contains(mousePositionRelativeToScreen)) {
            tryClosePopup();
            return;
        }
        Point mousePositionRelativeToComponent = timeList.getMousePosition();
        int index = timeList.locationToIndex(mousePositionRelativeToComponent);
        Rectangle cellBoundsOfIndex = timeList.getCellBounds(index, index);
        if (cellBoundsOfIndex == null || (!cellBoundsOfIndex.contains(mousePositionRelativeToComponent))) {
            tryClosePopup();
            return;
        }
        try {
            String selectedTimeString = timeList.getModel().getElementAt(index);
            if (selectedTimeString != null && !selectedTimeString.isEmpty()) {
                userSelectedATime(selectedTimeString);
            }
        } catch (Exception e) {
            throw new RuntimeException("TimeMenuPanel.mouseReleasedWhileTimeListIsOpen() "
                    + "Time menu list index is out of bounds. This should not happen. "
                    + e.getMessage());
        }
    }

    public void clearParent() {
        parentTimePicker = null;
        settings = null;
    }

    final void generateTimeEntriesFromSettings() {
        timeListModel.clear();
        DateTimeFormatter formatForMenuTimes = settings.getFormatForMenuTimes();
        ArrayList<LocalTime> menuTimes = settings.getPotentialMenuTimes();
        for (LocalTime localTime : menuTimes) {
            if (!InternalUtilities.isTimeVetoed(settings.getVetoPolicy(), localTime)) {
                String localizedTime = formatForMenuTimes.format(localTime);
                if (settings.useLowercaseForMenuTimes) {
                    localizedTime = localizedTime.toLowerCase(settings.getLocale());
                }
                timeListModel.addElement(localizedTime);
            }
        }
    }

    private void userSelectedATime(String selectedTimeString) {
        // Try to parse the selected time string. 
        LocalTime selectedTime = InternalUtilities.getParsedTimeOrNull(selectedTimeString,
                settings.getFormatForDisplayTime(), settings.getFormatForMenuTimes(),
                settings.formatsForParsing, settings.getLocale());
        // Check to see if the time was parsed. The time should always parse successfully.
        if (selectedTime == null) {
            throw new RuntimeException("TimeMenuPanel, "
                    + "Could not parse menu time. This should not happen.");
        }

        // If the selected time is vetoed, do nothing.
        TimeVetoPolicy vetoPolicy = settings.getVetoPolicy();
        if (InternalUtilities.isTimeVetoed(vetoPolicy, selectedTime)) {
            return;
        }
        // We close the popup after the user selects a time.
        if (parentTimePicker != null) {
            parentTimePicker.setTime(selectedTime);
            parentTimePicker.closePopup();
        }
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        timeScrollPane = new JScrollPane();
        timeList = new JList<>();

        //======== this ========
        setBorder(null);
        setLayout(new FormLayout(
                "default:grow",
                "fill:default:grow"));

        //======== timeScrollPane ========
        {
            timeScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

            //---- timeList ----
            timeList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
            timeList.setVisibleRowCount(10);
            timeScrollPane.setViewportView(timeList);
        }
        add(timeScrollPane, CC.xy(1, 1));
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JScrollPane timeScrollPane;
    private JList<String> timeList;
    // JFormDesigner - End of variables declaration  //GEN-END:variables

    private void tryClosePopup() {
        if (parentTimePicker != null) {
            parentTimePicker.closePopup();
        }
    }

    public void requestListFocus() {
        timeList.requestFocusInWindow();
    }

    public void selectFirstEntry() {
        if (timeList.getModel().getSize() > 0) {
            timeList.setSelectedIndex(0);
            timeList.requestFocusInWindow();
        }
    }
}
