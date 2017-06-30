package com.github.lgooddatepicker.ysandbox;

import com.github.lgooddatepicker.components.DatePickerSettings;
import com.github.lgooddatepicker.components.CalendarPanel;
import com.github.lgooddatepicker.optionalusertools.PickerUtilities;
import com.github.lgooddatepicker.zinternaltools.CalendarSelectionEvent;
import com.github.lgooddatepicker.zinternaltools.InternalUtilities;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagLayout;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.time.LocalDate;
import com.github.lgooddatepicker.optionalusertools.CalendarListener;
import com.github.lgooddatepicker.zinternaltools.YearMonthChangeEvent;
import java.time.YearMonth;

/**
 * CalendarPanelTest,
 *
 * Programmers who only wish to create a DatePicker, a TimePicker, or a DateTimePicker, should not
 * use this demo. See the "BasicDemo" or "FullDemo" classes instead.
 *
 * The CalendarPanel class is normally created (automatically), and used by the DatePicker
 * component. This demo shows how to instantiate a CalendarPanel as an -independent component-. This
 * demo only applies to programmers with specialized requirements. The majority of programmers would
 * never need to directly instantiate the CalendarPanel class.
 *
 * Note: Using the CalendarPanel class is not required to create a DatePicker or a DateTimePicker.
 * The DatePicker component automatically creates its own CalendarPanel instances whenever they are
 * needed.
 */
public class CalendarPanelTest {

    /**
     * frame, This is our main frame.
     */
    static JFrame frame = new JFrame();

    /**
     * informationLabel, This is a label for displaying messages to the user.
     */
    static JLabel informationLabel = new JLabel();

    /**
     * container, This is a container panel for laying out the components.
     */
    static JPanel container = new JPanel();

    /**
     * main, This is the entry point for the demo.
     */
    public static void main(String[] args) {
        // Initialize the form components.
        initializeComponents();

        // Create a calendar panel.
        DatePickerSettings settings = new DatePickerSettings();
        CalendarPanel calendarPanel = new CalendarPanel(settings);

        // Add a calendar selection listener to the CalendarPanel.
        calendarPanel.addCalendarListener(new SampleCalendarListener());

        // Add the CalendarPanel to the form.
        container.add(calendarPanel);

        // Display the frame.
        frame.setVisible(true);
    }

    /**
     * initializeComponents, This will set up our form, panels, and the layout managers. Most of
     * this code only exists to make the demo program look pretty.
     */
    private static void initializeComponents() {
        // Set up our main frame.
        frame.setTitle("LGoodDatePicker Independent Calendar Panel Demo "
                + InternalUtilities.getProjectVersionString());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridBagLayout());

        // Set up our panels and layouts. 
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        frame.getContentPane().add(mainPanel);
        container.setLayout(new GridBagLayout());
        mainPanel.add(informationLabel);
        mainPanel.add(new JLabel(" "));
        mainPanel.add(new JLabel(" "));
        mainPanel.add(container);

        // Set up an information label to display the selected date.
        informationLabel.setOpaque(true);
        informationLabel.setBackground(Color.white);
        informationLabel.setBorder(new CompoundBorder(
                new LineBorder(Color.black), new EmptyBorder(2, 4, 2, 4)));
        informationLabel.setText("The selected date will be displayed here.");
        informationLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Pack and resize the frame, and center it on the screen.
        frame.pack();
        frame.validate();
        frame.setSize(640, 480);
        frame.setLocationRelativeTo(null);
    }

    /**
     * SampleCalendarListener, A calendar listener provides a way for a class to receive
     * notifications whenever a date or YearMonth has been selected in an independent CalendarPanel.
     */
    private static class SampleCalendarListener implements CalendarListener {

        /**
         * selectedDateChanged, This function will be called each time that a date is selected in the
         * applicable CalendarPanel. The new and old selected dates are supplied in the event
         * object. These parameters may contain null, which represents a cleared or empty date.
         */
        @Override
        public void selectedDateChanged(CalendarSelectionEvent event) {
            LocalDate oldDate = event.getOldDate();
            LocalDate newDate = event.getNewDate();
            String oldDateString = PickerUtilities.localDateToString(oldDate, "(null)");
            String newDateString = PickerUtilities.localDateToString(newDate, "(null)");
            String messageStart = "The selected date has changed from: ";
            String fullMessage = messageStart + oldDateString + " to: " + newDateString + ".";
            fullMessage += (event.isDuplicate()) ? "(Event marked as duplicate.)" : "";
            informationLabel.setText(fullMessage);
        }

        @Override
        public void yearMonthChanged(YearMonthChangeEvent event) {
            YearMonth oldYearMonth = event.getOldYearMonth();
            YearMonth newYearMonth = event.getNewYearMonth();
            String oldYearMonthString = oldYearMonth.toString();
            String newYearMonthString = newYearMonth.toString();
            String messageStart = "The displayed YearMonth has changed from: '";
            String fullMessage = messageStart
                    + oldYearMonthString + "' to '" + newYearMonthString + "'. ";
            fullMessage += (event.isDuplicate()) ? "(Event marked as duplicate.)" : "";
            // This is commented out so you can see all "dateSelectionChanged" events in the label. 
            // informationLabel.setText(fullMessage);
        }
    }
}
