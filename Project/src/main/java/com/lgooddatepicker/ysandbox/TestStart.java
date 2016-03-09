package com.lgooddatepicker.ysandbox;

import com.lgooddatepicker.optionalusertools.TimeVetoPolicy;
import com.lgooddatepicker.timepicker.TimePicker;
import com.lgooddatepicker.timepicker.TimePickerSettings;
import java.awt.Color;
import java.awt.GraphicsEnvironment;
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

        Locale timeLocale = Locale.getDefault();
        DateTimeFormatter defaultDisplayFormatter = new DateTimeFormatterBuilder().parseLenient().parseCaseInsensitive().
                appendLocalized(null, FormatStyle.SHORT).toFormatter(timeLocale);
        LocalTime nowLocalTime = LocalTime.now();
        String nowString = defaultDisplayFormatter.format(nowLocalTime);
        LocalTime nowLocalTimeFromString = LocalTime.parse(nowString, defaultDisplayFormatter);

        ///////////////////////////////////////////////////////////////////////////////////////////
        // Create a frame, a panel, and our demo buttons.
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panel = new JPanel();
        frame.getContentPane().add(panel);

        // Locale timePickerLocale = Locale.forLanguageTag("zh");
        Locale timePickerLocale = Locale.forLanguageTag("en");
        TimePickerSettings timeSettings = new TimePickerSettings(timePickerLocale);
        timeSettings.allowEmptyTimes = false;
        timeSettings.initialTime = LocalTime.of(7, 0);
        timeSettings.borderTimePopup = new LineBorder(Color.red);
        /*
        timeSettings.vetoPolicy = new TimeVetoPolicy() {
            @Override
            public boolean isTimeAllowed(LocalTime time) {
                return (time.getHour() > 6 && time.getHour() < 23);
            }
        };
         */
        TimePicker timePicker = new TimePicker(timeSettings);
        panel.add(timePicker);
        panel.add(new JButton("Hi"));

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
