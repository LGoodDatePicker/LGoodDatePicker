package com.github.lgooddatepicker.ysandbox;

import com.github.lgooddatepicker.components.CalendarPanel;
import com.github.lgooddatepicker.components.DatePickerSettings;
import com.github.lgooddatepicker.optionalusertools.DateHighlightPolicy;
import com.github.lgooddatepicker.optionalusertools.DateVetoPolicy;
import com.github.lgooddatepicker.zinternaltools.CalendarSelectionEvent;
import com.github.lgooddatepicker.zinternaltools.HighlightInformation;
import java.awt.BorderLayout;
import java.awt.Color;
import java.time.LocalDate;
import java.time.temporal.WeekFields;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import com.github.lgooddatepicker.optionalusertools.CalendarListener;
import com.github.lgooddatepicker.zinternaltools.YearMonthChangeEvent;

/**
 * TestUpdateHighlightPolicy, This class tests the library to see if a highlight policy can be
 * dynamically and immediately updated based on a calendar selection change event.
 */
public class TestUpdateHighlightPolicy {

    /**
     * main.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (Exception e) {
                }
                createUI();
            }
        });
    }

    public static void createUI() {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.getContentPane().add(new ProcedureTest());
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private static class ProcedureTest extends JPanel implements CalendarListener {

        private CalendarPanel picker;
        private LocalDate selectedDate;

        public ProcedureTest() {
            this.setLayout(new BorderLayout(5, 5));
            picker = createDatePicker();
            this.add(picker, BorderLayout.CENTER);
        }

        private CalendarPanel createDatePicker() {
            DatePickerSettings settings = new DatePickerSettings();
            settings.setWeekNumberRules(WeekFields.ISO);
            settings.setWeekNumbersDisplayed(true, true);
            settings.setAllowEmptyDates(false);
            settings.setHighlightPolicy(new DynamicHighlightPolicy());
            CalendarPanel customizedPicker = new CalendarPanel(settings);
            customizedPicker.addCalendarListener(this);
            settings.setVetoPolicy(new VetoPolicy());
            return customizedPicker;
        }

        @Override
        public void selectedDateChanged(CalendarSelectionEvent event) {
            selectedDate = event.getNewDate();
            System.out.println(selectedDate);
        }

        @Override
        public void yearMonthChanged(YearMonthChangeEvent event) {
            // Not needed.
        }

        private class DynamicHighlightPolicy implements DateHighlightPolicy {

            @Override
            public HighlightInformation getHighlightInformationOrNull(LocalDate someDate) {
                if (selectedDate == null) {
                    return null;
                }

                if ((someDate.isAfter(LocalDate.now()) || someDate.isEqual(LocalDate.now()))
                        && (someDate.isBefore(selectedDate) || someDate.isEqual(selectedDate))) {
                    return new HighlightInformation(Color.GREEN, Color.BLACK, "selected period");
                }
                return null;
            }
        }

        private class VetoPolicy implements DateVetoPolicy {

            @Override
            public boolean isDateAllowed(LocalDate someDate) {
                return someDate.isAfter(LocalDate.now()) || someDate.isEqual(LocalDate.now());
            }
        }
    }

}
