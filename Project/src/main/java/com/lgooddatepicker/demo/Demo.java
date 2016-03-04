package com.lgooddatepicker.demo;

import com.lgooddatepicker.datepicker.DatePicker;
import javax.swing.JButton;
import javax.swing.JFrame;
import java.awt.event.ActionEvent;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.util.Locale;
import com.lgooddatepicker.datepicker.DatePickerSettings;
import com.lgooddatepicker.optionalusertools.DateChangeListener;
import com.lgooddatepicker.optionalusertools.PickerUtilities;
import com.lgooddatepicker.zinternaltools.DateChangeEvent;
import com.lgooddatepicker.zinternaltools.InternalUtilities;
import com.lgooddatepicker.zinternaltools.WrapLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import com.lgooddatepicker.optionalusertools.DateVetoPolicy;
import com.lgooddatepicker.optionalusertools.DateHighlightPolicy;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

/**
 * Demo, This class contains a demonstration of various features of the DatePicker class.
 *
 * Optional features: Most of the DatePicker features shown in this demo are optional. The simplest
 * usage only requires creating a date picker instance and adding it to a panel or window. The
 * selected date can then be retrieved with the function datePicker.getDate().
 *
 * DatePicker Basic Usage Example:
 * <pre>
 * // Create a new date picker.
 * DatePicker datePicker = new DatePicker();
 *
 * // Add the date picker to a panel. (Or to another window container).
 * JPanel panel = new JPanel();
 * panel.add(datePicker);
 *
 * // Get the selected date.
 * LocalDate date = datePicker.getDate();
 * </pre>
 *
 * Running the demo: This is an executable demonstration. To run the demo, click "run file" (or the
 * equivalent command) for the Demo class in your IDE.
 */
public class Demo {

    // This holds our main frame.
    static JFrame frame;
    // This holds our display panel.
    static DemoPanel panel;
    // These hold date pickers.
    static DatePicker datePicker1;
    static DatePicker datePicker2;
    static DatePicker datePicker3;
    static DatePicker datePicker4;
    static DatePicker datePicker5;
    static DatePicker datePicker6;
    static DatePicker datePicker7;
    static DatePicker datePicker8;
    static DatePicker datePicker9;
    static DatePicker datePicker10;
    // Date pickers are placed on even rows.
    static final int rowMultiplier = 2;

    /**
     * main, The application entry point.
     */
    public static void main(String[] args) {
        ///////////////////////////////////////////////////////////////////////////////////////////
        // If desired, set a swing look and feel here. 
        try {
        /*
            // Set a specific look and feel.
            for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
            
            // Set a random look and feel. 
            LookAndFeelInfo[] installedLooks = UIManager.getInstalledLookAndFeels();
            int lookIndex = (int) (Math.random() * installedLooks.length);
            UIManager.setLookAndFeel(installedLooks[lookIndex].getClassName());
            System.out.println(installedLooks[lookIndex].getClassName().toString());
        */
        } catch (Exception e) {
        }

        ///////////////////////////////////////////////////////////////////////////////////////////
        // Create a frame, a panel, and our demo buttons.
        frame = new JFrame();
        frame.setTitle("LGoodDatePicker Demo " + InternalUtilities.getProjectVersionString());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panel = new DemoPanel();
        frame.getContentPane().add(panel);
        createDemoButtons();

        ///////////////////////////////////////////////////////////////////////////////////////////
        // This section creates date pickers that demonstrate various features.
        //
        // Create a settings variable for repeated use.
        DatePickerSettings settings;

        // Create a date picker: With default settings
        datePicker1 = new DatePicker();
        panel.featuresPanel.add(datePicker1, getConstraints(3, (1 * rowMultiplier)));

        // Create a date picker: With highlight policy.
        settings = new DatePickerSettings();
        settings.highlightPolicy = new SampleHighlightPolicy();
        datePicker2 = new DatePicker(settings);
        panel.featuresPanel.add(datePicker2, getConstraints(3, (2 * rowMultiplier)));

        // Create a date picker: With veto policy.
        settings = new DatePickerSettings();
        settings.vetoPolicy = new SampleVetoPolicy();
        datePicker3 = new DatePicker(settings);
        panel.featuresPanel.add(datePicker3, getConstraints(3, (3 * rowMultiplier)));

        // Create a date picker: With both policies.
        settings = new DatePickerSettings();
        settings.highlightPolicy = new SampleHighlightPolicy();
        settings.vetoPolicy = new SampleVetoPolicy();
        datePicker4 = new DatePicker(settings);
        panel.featuresPanel.add(datePicker4, getConstraints(3, (4 * rowMultiplier)));

        // Create a date picker: Change first weekday.
        settings = new DatePickerSettings();
        settings.firstDayOfWeek = DayOfWeek.MONDAY;
        datePicker5 = new DatePicker(settings);
        panel.featuresPanel.add(datePicker5, getConstraints(3, (5 * rowMultiplier)));

        // Create a date picker: Change calendar size.
        settings = new DatePickerSettings();
        settings.sizeDatePanelMinimumHeight *= 1.6;
        settings.sizeDatePanelMinimumWidth *= 1.6;
        datePicker6 = new DatePicker(settings);
        panel.featuresPanel.add(datePicker6, getConstraints(3, (6 * rowMultiplier)));

        // Create a date picker: Custom color.
        settings = new DatePickerSettings();
        settings.colorBackgroundCalendarPanel = Color.green;
        settings.colorBackgroundWeekdayLabels = Color.orange;
        settings.colorBackgroundMonthAndYear = Color.yellow;
        settings.colorBackgroundTodayAndClear = Color.yellow;
        settings.colorBackgroundNavigateYearMonthButtons = Color.cyan;
        datePicker7 = new DatePicker(settings);
        panel.featuresPanel.add(datePicker7, getConstraints(3, (7 * rowMultiplier)));

        // Create a date picker: Custom date format.
        // When creating a date pattern string for BCE dates, use "u" instead of "y" for the year.
        // For more details about that, see: DatePickerSettings.formatDatesBeforeCommonEra.
        settings = new DatePickerSettings();
        settings.initialDate = LocalDate.now();
        settings.formatForDatesCommonEra = PickerUtilities.createFormatterFromPatternString(
                "d MMM yyyy", settings.pickerLocale);
        settings.formatForDatesBeforeCommonEra = PickerUtilities.createFormatterFromPatternString(
                "d MMM uuuu", settings.pickerLocale);
        datePicker8 = new DatePicker(settings);
        panel.featuresPanel.add(datePicker8, getConstraints(3, (8 * rowMultiplier)));

        // Create a date picker: No empty dates. (aka null)
        settings = new DatePickerSettings();
        settings.allowEmptyDates = false;
        datePicker9 = new DatePicker(settings);
        datePicker9.addDateChangeListener(new SampleDateChangeListener("datePicker9 (Disallow Empty Dates or Null), "));
        panel.featuresPanel.add(datePicker9, getConstraints(3, (9 * rowMultiplier)));

        // Create a date picker: Custom font.
        settings = new DatePickerSettings();
        settings.initialDate = LocalDate.now();
        settings.fontValidDate = new Font("Monospaced", Font.ITALIC | Font.BOLD, 17);
        settings.colorTextValidDate = new Color(0, 100, 0);
        datePicker10 = new DatePicker(settings);
        panel.featuresPanel.add(datePicker10, getConstraints(3, (10 * rowMultiplier)));

        ///////////////////////////////////////////////////////////////////////////////////////////
        // This section creates date pickers and labels for demonstrating the language translations.
        int rowMarker = 0;
        addLocalizedPickerAndLabel(++rowMarker, "Arabic:", "ar");
        addLocalizedPickerAndLabel(++rowMarker, "Chinese:", "zh");
        addLocalizedPickerAndLabel(++rowMarker, "Czech:", "cs");
        addLocalizedPickerAndLabel(++rowMarker, "Danish:", "da");
        addLocalizedPickerAndLabel(++rowMarker, "Dutch:", "nl");
        addLocalizedPickerAndLabel(++rowMarker, "English:", "en");
        addLocalizedPickerAndLabel(++rowMarker, "French:", "fr");
        addLocalizedPickerAndLabel(++rowMarker, "German:", "de");
        addLocalizedPickerAndLabel(++rowMarker, "Greek:", "el");
        addLocalizedPickerAndLabel(++rowMarker, "Hindi:", "hi");
        addLocalizedPickerAndLabel(++rowMarker, "Italian:", "it");
        addLocalizedPickerAndLabel(++rowMarker, "Indonesian:", "in");
        addLocalizedPickerAndLabel(++rowMarker, "Japanese:", "ja");
        addLocalizedPickerAndLabel(++rowMarker, "Korean:", "ko");
        addLocalizedPickerAndLabel(++rowMarker, "Polish:", "pl");
        addLocalizedPickerAndLabel(++rowMarker, "Portuguese:", "pt");
        addLocalizedPickerAndLabel(++rowMarker, "Romanian:", "ro");
        addLocalizedPickerAndLabel(++rowMarker, "Russian:", "ru");
        addLocalizedPickerAndLabel(++rowMarker, "Spanish:", "es");
        addLocalizedPickerAndLabel(++rowMarker, "Swedish:", "sv");
        addLocalizedPickerAndLabel(++rowMarker, "Turkish:", "tr");
        addLocalizedPickerAndLabel(++rowMarker, "Vietnamese:", "vi");
        // Add a few empty rows at the bottom.
        for (int i = 0; i < 5; ++i) {
            addLocalizedPickerAndLabel(++rowMarker, "", "");
        }

        // Display the frame.
        frame.pack();
        frame.validate();
        int maxWidth = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().width;
        int maxHeight = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().height;
        frame.setSize(maxWidth / 4 * 3, maxHeight / 8 * 7);
        frame.setLocation(maxWidth / 8, maxHeight / 16);
        frame.setVisible(true);
    }

    /**
     * getConstraints, This returns a grid bag constraints object that can be used for placing a
     * component appropriately into a grid bag layout.
     */
    private static GridBagConstraints getConstraints(int gridx, int gridy) {
        GridBagConstraints gc = new GridBagConstraints();
        gc.fill = GridBagConstraints.BOTH;
        gc.gridx = gridx;
        gc.gridy = gridy;
        return gc;
    }

    /**
     * addLocalizedPickerAndLabel, This creates a date picker whose locale is set to the specified
     * language. This also sets the picker to today's date, creates a label for the date picker, and
     * adds the components to the language panel.
     */
    private static void addLocalizedPickerAndLabel(int rowMarker, String labelText, String languageCode) {
        // Create an empty spacer label, and add it to the previous row.
        JLabel spacerLabel = new JLabel(" ");
        panel.languagePanel.add(spacerLabel, new GridBagConstraints(3, ((rowMarker * rowMultiplier) - 1),
                1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 0, 0));
        // If the language code is empty, we only add the spacer label and nothing else. 
        if (languageCode == null || "".equals(languageCode)) {
            return;
        }
        // Create the localized date picker.
        DatePickerSettings settings = new DatePickerSettings(new Locale(languageCode));
        settings.initialDate = LocalDate.now();
        DatePicker datePicker = new DatePicker(settings);
        panel.languagePanel.add(datePicker, getConstraints(3, (rowMarker * rowMultiplier)));
        // Create the label for the localized date picker.
        JLabel languageLabel = new JLabel(labelText);
        panel.languagePanel.add(languageLabel, new GridBagConstraints(1, (rowMarker * rowMultiplier),
                1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.VERTICAL,
                new Insets(0, 0, 0, 0), 0, 0));
    }

    /**
     * setTwoWithY2KButtonClicked, This sets the date in date picker two, to New Years Day 2000.
     */
    private static void setTwoWithY2KButtonClicked(ActionEvent e) {
        // Set date picker date.
        LocalDate dateY2K = LocalDate.of(2000, Month.JANUARY, 1);
        datePicker2.setDate(dateY2K);
        // Display message.
        String dateString = datePicker2.getISODateStringOrSuppliedString("(null)");
        String message = "The datePicker2 date was set to New Years 2000!\n\n";
        message += ("The datePicker2 date is currently set to: " + dateString + ".");
        panel.messageTextArea.setText(message);
    }

    /**
     * setOneWithTwoButtonClicked, This sets the date in date picker one, to whatever date is
     * currently set in date picker two.
     */
    private static void setOneWithTwoButtonClicked(ActionEvent e) {
        // Set date from date picker 2.
        LocalDate datePicker2Date = datePicker2.getDate();
        datePicker1.setDate(datePicker2Date);
        // Display message.
        String message = "The datePicker1 date was set using the datePicker2 date!\n\n";
        message += getDatePickerOneDateText();
        panel.messageTextArea.setText(message);
    }

    /**
     * setOneWithFeb31ButtonClicked, This sets the text in date picker one, to a nonexistent date
     * (February 31st). The last valid date in a date picker is always saved. This is a programmatic
     * demonstration of what happens when the user enters invalid text.
     */
    private static void setOneWithFeb31ButtonClicked(ActionEvent e) {
        // Set date picker text.
        datePicker1.setText("February 31, 1950");
        // Display message.
        String message = "The datePicker1 text was set to: \"" + datePicker1.getText() + "\".\n";
        message += "Note: The stored date (the last valid date), did not change because"
                + " February never has 31 days.\n\n";
        message += getDatePickerOneDateText();
        panel.messageTextArea.setText(message);
    }

    /**
     * getOneAndShowButtonClicked, This retrieves and displays whatever date is currently set in
     * date picker one.
     */
    private static void getOneAndShowButtonClicked(ActionEvent e) {
        // Get and display date picker text.
        panel.messageTextArea.setText(getDatePickerOneDateText());
    }

    /**
     * clearOneAndTwoButtonClicked, This clears date picker one.
     */
    private static void clearOneAndTwoButtonClicked(ActionEvent e) {
        // Clear the date pickers.
        datePicker1.clear();
        datePicker2.clear();
        // Display message.
        String message = "The datePicker1 and datePicker2 dates were cleared!\n\n";
        message += getDatePickerOneDateText() + "\n";
        String date2String = datePicker2.getISODateStringOrSuppliedString("(null)");
        message += ("The datePicker2 date is currently set to: " + date2String + ".");
        panel.messageTextArea.setText(message);
    }

    /**
     * getDatePickerOneDateText, This returns a string indicating the current date stored in date
     * picker one.
     */
    private static String getDatePickerOneDateText() {
        // Create date string for date picker 1.
        String dateString = datePicker1.getISODateStringOrSuppliedString("(null)");
        return ("The datePicker1 date is currently set to: " + dateString + ".");
    }

    /**
     * createDemoButtons, This creates the buttons for the demo, adds an action listener to each
     * button, and adds each button to the display panel.
     */
    private static void createDemoButtons() {
        JPanel buttonPanel = new JPanel(new WrapLayout());
        panel.scrollPaneForButtons.setViewportView(buttonPanel);
        // Create each demo button, and add it to the panel.
        // Add an action listener to link it to its appropriate function.
        JButton showIntro = new JButton("Show Introduction Message");
        showIntro.addActionListener(e -> showIntroductionClicked(e));
        buttonPanel.add(showIntro);
        JButton setTwoWithY2K = new JButton("Set DatePicker Two with New Years Day 2000");
        setTwoWithY2K.addActionListener(e -> setTwoWithY2KButtonClicked(e));
        buttonPanel.add(setTwoWithY2K);
        JButton setOneWithTwo = new JButton("Set DatePicker One with the date in Two");
        setOneWithTwo.addActionListener(e -> setOneWithTwoButtonClicked(e));
        buttonPanel.add(setOneWithTwo);
        JButton setOneWithFeb31 = new JButton("Set Text in DatePicker One to February 31, 1950");
        setOneWithFeb31.addActionListener(e -> setOneWithFeb31ButtonClicked(e));
        buttonPanel.add(setOneWithFeb31);
        JButton getOneAndShow = new JButton("Get and show the date in DatePicker One");
        getOneAndShow.addActionListener(e -> getOneAndShowButtonClicked(e));
        buttonPanel.add(getOneAndShow);
        JButton clearOneAndTwo = new JButton("Clear DatePickers One and Two");
        clearOneAndTwo.addActionListener(e -> clearOneAndTwoButtonClicked(e));
        buttonPanel.add(clearOneAndTwo);
        JButton toggleButton = new JButton("Toggle DatePicker One");
        toggleButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                toggleOneButtonClicked();
            }
        });
        buttonPanel.add(toggleButton);
    }

    /**
     * showIntroductionClicked, This displays an introduction message about the date picker.
     */
    private static void showIntroductionClicked(ActionEvent e) {
        panel.messageTextArea.setText("Interface: \nMost items in a date picker are clickable. "
                + "These include... The buttons for previous and next month, the buttons for "
                + "previous and next year, the \"today\" text, the \"clear\" text, and individual "
                + "dates. A click on the month or year label (at the top), will open a menu for "
                + "changing the month or year.\n\nGeneral features: \n* Automatic "
                + "internationalization. \n* Relatively compact source code.\n* Creating a "
                + "DatePicker requires only one line of code.\n* Open source code base.\n\n"
                + "Data types: \nThe standard Java 8 time library is used to store dates, "
                + "and they are convertible to other data types. \n(The Java 8 time package "
                + "is also called \"java.time\" or \"JSR-310\", and was developed by the author "
                + "of Joda Time.)\n\nVeto and Highlight Policies: \nThese policies are optional. "
                + "A veto policy restricts the dates that can be selected. A highlight policy "
                + "provides a visual highlight on desired dates, with optional tooltips. If today "
                + "is vetoed, the \"today\" button will be grey and disabled.\n\nDate values and "
                + "automatic validation: \nEvery date picker stores its current text, and its last "
                + "valid date. The last valid date is returned when you call DatePicker.getDate(). "
                + "If the user types into the text field, any text that is not a valid date will "
                + "be displayed in red, any vetoed date will have a strikethrough, and valid "
                + "dates will display in black. When the focus on a date picker is lost, the text "
                + "is always set to match the last valid date.\n\n\n");
        panel.messageTextArea.setCaretPosition(0);
    }

    /**
     * toggleOneButtonClicked, This toggles (opens or closes) date picker one.
     */
    private static void toggleOneButtonClicked() {
        datePicker1.togglePopup();
        String message = "The datePicker1 calendar popup is ";
        message += (datePicker1.isPopupOpen()) ? "open!" : "closed!";
        panel.messageTextArea.setText(message);
    }

    /**
     * SampleVetoPolicy, A veto policy is a way to disallow certain dates from being selected in
     * calendar. A vetoed date cannot be selected by using the keyboard or the mouse.
     */
    private static class SampleVetoPolicy implements DateVetoPolicy {

        /**
         * isDateAllowed, Return true if a date should be allowed, or false if a date should be
         * vetoed.
         */
        @Override
        public boolean isDateAllowed(LocalDate date) {
            // Disallow days 7 to 11.
            if ((date.getDayOfMonth() >= 7) && (date.getDayOfMonth() <= 11)) {
                return false;
            }
            // Disallow odd numbered saturdays.
            if ((date.getDayOfWeek() == DayOfWeek.SATURDAY) && ((date.getDayOfMonth() % 2) == 1)) {
                return false;
            }
            // Allow all other days.
            return true;
        }
    }

    /**
     * SampleHighlightPolicy, A highlight policy is a way to visually highlight certain dates in the
     * calendar. These may be holidays, or weekends, or other significant dates.
     */
    private static class SampleHighlightPolicy implements DateHighlightPolicy {

        /**
         * getHighlightStringOrNull, This indicates if a date should be highlighted, or have a tool
         * tip in the calendar. Possible return values are: Return the desired tooltip text to give
         * a date a tooltip. Return an empty string to highlight a date without giving that date a
         * tooltip. Return null if a date should not be highlighted.
         */
        @Override
        public String getHighlightStringOrNull(LocalDate date) {
            // Highlight and give a tooltip to a chosen date.
            if (date.getDayOfMonth() == 25) {
                return "It's the 25th!";
            }
            // Highlight all weekend days.
            if (date.getDayOfWeek() == DayOfWeek.SATURDAY
                    || date.getDayOfWeek() == DayOfWeek.SUNDAY) {
                return "It's the weekend.";
            }
            // All other days should not be highlighted.
            return null;
        }
    }

    /**
     * SampleDateChangeListener, A date change listener provides a way for a class to receive
     * notifications whenever the date has changed in a DatePicker.
     */
    private static class SampleDateChangeListener implements DateChangeListener {

        /**
         * datePickerName, This holds a chosen name for the date picker that we are listening to,
         * for generating date change messages in the demo.
         */
        public String datePickerName;

        /**
         * Constructor.
         */
        private SampleDateChangeListener(String datePickerName) {
            this.datePickerName = datePickerName;
        }

        /**
         * dateChanged, This function will be called each time that the date in the applicable date
         * picker has changed. Both the old date, and the new date, are supplied in the event
         * object. Note that either parameter may contain null, which represents a cleared or empty
         * date.
         */
        @Override
        public void dateChanged(DateChangeEvent event) {
            LocalDate oldDate = event.getOldDate();
            LocalDate newDate = event.getNewDate();
            String oldDateString = PickerUtilities.localDateToString(oldDate, "(null)");
            String newDateString = PickerUtilities.localDateToString(newDate, "(null)");
            String messageStart = "\nThe date in " + datePickerName + " has changed from: ";
            String fullMessage = messageStart + oldDateString + " to: " + newDateString + ".";
            if (!panel.messageTextArea.getText().startsWith(messageStart)) {
                panel.messageTextArea.setText("");
            }
            panel.messageTextArea.append(fullMessage);
        }

    }

}
