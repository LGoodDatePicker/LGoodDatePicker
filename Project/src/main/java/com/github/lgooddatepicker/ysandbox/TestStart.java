package com.github.lgooddatepicker.ysandbox;

import com.github.lgooddatepicker.datepicker.DatePickerSettings;
import com.github.lgooddatepicker.calendarpanel.CalendarPanel;
import com.github.lgooddatepicker.optionalusertools.DateSelectionListener;
import com.github.lgooddatepicker.optionalusertools.PickerUtilities;
import com.github.lgooddatepicker.zinternaltools.DateSelectionEvent;
import java.awt.GraphicsEnvironment;
import java.time.LocalDate;
import java.time.Month;
import java.util.Locale;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
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
        DatePickerSettings dateSettings = new DatePickerSettings(datePickerLocale);
        CalendarPanel calendarPanel = new CalendarPanel(dateSettings);
        calendarPanel.setSelectedDate(LocalDate.of(2016, Month.APRIL, 15));
        calendarPanel.addDateSelectionListener(new SampleDateSelectionListener());
        panel.add(calendarPanel);

        // Display the frame.
        frame.pack();
        frame.validate();
        int maxWidth = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().width;
        int maxHeight = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().height;
        frame.setSize(640, 480);
        frame.setLocation(maxWidth / 2, maxHeight / 2);
        frame.setVisible(true);
    }

    /**
     * SampleDateSelectionListener, A date selection listener provides a way for a class to receive
     * notifications whenever a date has been selected in an independent CalendarPanel.
     */
    private static class SampleDateSelectionListener implements DateSelectionListener {

        /**
         * Constructor.
         */
        private SampleDateSelectionListener() {
        }

        /**
         * dateSelected, This function will be called each time that a date is selected in the
         * applicable CalendarPanel. The selected date is supplied in the event object. The selected
         * date may contain null, which represents a cleared or empty date.
         */
        @Override
        public void dateSelected(DateSelectionEvent event) {
            LocalDate oldDate = event.getOldDate();
            LocalDate newDate = event.getNewDate();
            String oldDateString = PickerUtilities.localDateToString(oldDate, "(null)");
            String newDateString = PickerUtilities.localDateToString(newDate, "(null)");
            String messageStart = "\nThe selected date in the CalendarPanel has changed from: ";
            String fullMessage = messageStart + oldDateString + " to: " + newDateString + ".";
            JOptionPane.showMessageDialog(null, fullMessage, "CalendarPanel Date Selected", 
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
