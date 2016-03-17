package com.github.lgooddatepicker.ysandbox;

import com.github.lgooddatepicker.datepicker.DatePicker;
import com.github.lgooddatepicker.datepicker.DatePickerSettings;
import com.github.lgooddatepicker.datetimepicker.DateTimePicker;
import com.github.lgooddatepicker.optionalusertools.PickerUtilities;
import com.github.lgooddatepicker.optionalusertools.TimeVetoPolicy;
import com.github.lgooddatepicker.timepicker.TimePicker;
import com.github.lgooddatepicker.timepicker.TimePickerSettings;
import java.awt.Color;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.FormatStyle;
import java.util.Locale;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

/**
 * testStart, This is a class used to test various functions while programming. This class is not
 * involved with the normal operation of the date pickers.
 */
public class TestStart {

    public static void main(String[] args) {

        ///////////////////////////////////////////////////////////////////////////////////////////
        // Create a frame, a panel, and our demo buttons.
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panel = new JPanel();
        frame.getContentPane().add(panel);

        // Locale timePickerLocale = Locale.forLanguageTag("zh");
        Locale timePickerLocale = Locale.forLanguageTag("en");
        TimePickerSettings timeSettings = new TimePickerSettings(timePickerLocale);
        TimePicker timePicker = new TimePicker(timeSettings);
        timePicker.setText("heya");
        panel.add(timePicker);

        Locale datePickerLocale = Locale.forLanguageTag("en");
        DatePickerSettings dateSettings = new DatePickerSettings(datePickerLocale);
        DatePicker datePicker = new DatePicker(dateSettings);
        panel.add(datePicker);

        DateTimePicker dateTimePicker = new DateTimePicker();
        dateTimePicker.getDatePicker().setText("hiya");
        dateTimePicker.getTimePicker().setText("hey");
        panel.add(dateTimePicker);

        JButton button1 = new JButton("Enable or disable the picker");
        panel.add(button1);
        button1.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                dateTimePicker.setEnabled(!dateTimePicker.isEnabled());
            }
        });

        JButton button2 = new JButton("Change display format");
        panel.add(button2);
        button2.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                datePicker.getSettings().setFormatForDatesCommonEra(
                        PickerUtilities.createFormatterFromPatternString("d MMM yyyy", dateSettings.getLocale()));
            }
        });

        // Display the frame.
        frame.pack();
        frame.validate();
        int maxWidth = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().width;
        int maxHeight = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().height;
        frame.setSize(300, 200);
        frame.setLocation(maxWidth / 2, maxHeight / 2);
        frame.setVisible(true);
    }
}
