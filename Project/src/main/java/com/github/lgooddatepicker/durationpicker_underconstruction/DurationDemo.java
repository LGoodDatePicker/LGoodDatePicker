package com.github.lgooddatepicker.durationpicker_underconstruction;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;
import com.github.lgooddatepicker.components.DateTimePicker;
import com.github.lgooddatepicker.components.TimePicker;
import com.github.lgooddatepicker.components.TimePickerSettings;
import com.github.lgooddatepicker.components.TimePickerSettings.TimeArea;
import com.github.lgooddatepicker.zinternaltools.InternalUtilities;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.time.DayOfWeek;
import java.time.LocalTime;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 * DurationDemo
 */
public class DurationDemo extends JFrame {

    /**
     * main, This is the entry point for the demo.
     */
    public static void main(String[] args) {
        // Use the standard swing code to start this demo inside a swing thread.
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // Create an instance of the demo.
                DurationDemo demo = new DurationDemo();
                // Make the demo visible on the screen.
                demo.setVisible(true);
            }
        });
    }

    /**
     * Default Constructor.
     */
    public DurationDemo() {
        initializeComponents();
    }

    /**
     * initializeComponents, This creates the user interface for the basic demo.
     */
    private void initializeComponents() {
        // Set up the form which holds the date picker components. 
        setTitle("LGoodDatePicker Basic Demo " + InternalUtilities.getProjectVersionString());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());
        setSize(new Dimension(640, 480));
        setLocationRelativeTo(null);
        
        // Create a duration picker, and add it to the form.
        DurationPicker durationPicker1 = new DurationPicker();
        add(durationPicker1);

        /**
         * The code below shows: 1) How to create a DateTimePicker (with default settings), 2) How
         * to create a DatePicker with some custom settings, and 3) How to create a TimePicker with
         * some custom settings. To keep the Basic Demo interface simpler, the lines for adding
         * these components to the form have been commented out.
         */
        // Create a DateTimePicker. (But don't add it to the form).
        DateTimePicker dateTimePicker1 = new DateTimePicker();
        // To display this picker, uncomment this line.
        // add(dateTimePicker1);

        // Create a date picker with some custom settings. 
        DatePickerSettings dateSettings = new DatePickerSettings();
        dateSettings.setFirstDayOfWeek(DayOfWeek.MONDAY);
        DatePicker datePicker2 = new DatePicker(dateSettings);
        // To display this picker, uncomment this line.
        // add(datePicker2);

        // Create a time picker with some custom settings.
        TimePickerSettings timeSettings = new TimePickerSettings();
        timeSettings.setColor(TimeArea.TimePickerTextValidTime, Color.blue);
        timeSettings.initialTime = LocalTime.now();
        TimePicker timePicker2 = new TimePicker(timeSettings);
        // To display this picker, uncomment this line.
        // add(timePicker2);
    }
}
