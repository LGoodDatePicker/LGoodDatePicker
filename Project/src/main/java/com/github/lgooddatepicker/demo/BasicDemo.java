package com.github.lgooddatepicker.demo;

import com.github.lgooddatepicker.datepicker.DatePicker;
import com.github.lgooddatepicker.timepicker.TimePicker;
import com.github.lgooddatepicker.zinternaltools.InternalUtilities;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 * BasicDemo,
 *
 * This class demonstrates the most basic usage of the date and time picker components. More
 * specifically, this class only demonstrates how to create the components and add them to a form.
 * For a more extensive demonstration of all library components and their various optional settings,
 * see "FullDemo.java". 
 */
public class BasicDemo extends JFrame {

    /**
     * main, This is the entry point for the basic demo.
     */
    public static void main(String[] args) {
        // Use the standard swing code to start this demo inside a swing thread.
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // Create an instance of the demo.
                BasicDemo basicDemo = new BasicDemo();
                // Make the demo visible on the screen.
                basicDemo.setVisible(true);
            }
        });
    }

    /**
     * Default Constructor.
     */
    public BasicDemo() {
        initializeComponents();
    }

    /**
     * initializeComponents, This creates the user interface for the basic demo.
     */
    private void initializeComponents() {
        // Set up the form which holds the date picker components. 
        setTitle("LGoodDatePicker Basic Demo " + InternalUtilities.getProjectVersionString());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension windowSize = new Dimension(640, 480);
        setSize(windowSize);
        setPreferredSize(windowSize);
        setLocationRelativeTo(null);
        setLayout(new FlowLayout());

        // Create a date picker, and add it to the form.
        DatePicker datePicker = new DatePicker();
        add(datePicker);

        // Create a time picker, and add it to the form.
        TimePicker timePicker = new TimePicker();
        add(timePicker);
    }
}
