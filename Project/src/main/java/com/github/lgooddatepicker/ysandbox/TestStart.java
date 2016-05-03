package com.github.lgooddatepicker.ysandbox;

import com.github.lgooddatepicker.datepicker.DatePickerSettings;
import com.github.lgooddatepicker.datepicker.DatePicker;
import com.github.lgooddatepicker.zinternaltools.CalculateMinimumDateFieldSize;
import java.awt.GraphicsEnvironment;
import java.time.LocalDate;
import java.time.Month;
import java.util.Locale;
import javax.swing.JFrame;
import javax.swing.JPanel;

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

        Locale datePickerLocale = Locale.forLanguageTag("en");
        DatePickerSettings settings = new DatePickerSettings(datePickerLocale);
        settings.initialDate = LocalDate.of(2016, Month.APRIL, 15);
        int longestDateInPixels = CalculateMinimumDateFieldSize.getFormattedDateWidthInPixels(settings.getFormatForDatesCommonEra(), settings.getLocale(), settings.getFontValidDate(), 0);
        
        
        DatePicker datePicker = new DatePicker(settings);
        panel.add(datePicker);

        // Display the frame.
        frame.pack();
        frame.validate();
        int maxWidth = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().width;
        int maxHeight = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().height;
        frame.setSize(640, 480);
        frame.setLocation(maxWidth / 2, maxHeight / 2);
        frame.setVisible(true);
    }
}
