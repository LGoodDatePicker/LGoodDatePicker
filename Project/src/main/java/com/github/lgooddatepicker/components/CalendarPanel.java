package com.github.lgooddatepicker.components;

import java.time.*;
import com.privatejgoodies.forms.layout.FormLayout;
import com.privatejgoodies.forms.factories.CC;
import com.github.lgooddatepicker.components.DatePickerSettings.DateArea;
import com.github.lgooddatepicker.optionalusertools.CalendarBorderProperties;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.border.*;
import java.text.DateFormatSymbols;
import com.github.lgooddatepicker.optionalusertools.DateVetoPolicy;
import com.github.lgooddatepicker.optionalusertools.DateHighlightPolicy;
import com.github.lgooddatepicker.zinternaltools.CalendarSelectionEvent;
import com.github.lgooddatepicker.zinternaltools.InternalUtilities;
import com.github.lgooddatepicker.zinternaltools.JIntegerTextField;
import com.github.lgooddatepicker.zinternaltools.JIntegerTextField.IntegerTextFieldNumberChangeListener;
import com.github.lgooddatepicker.zinternaltools.MouseLiberalAdapter;
import com.github.lgooddatepicker.zinternaltools.HighlightInformation;
import com.privatejgoodies.forms.layout.CellConstraints;
import java.time.temporal.WeekFields;
import com.github.lgooddatepicker.optionalusertools.CalendarListener;
import com.github.lgooddatepicker.zinternaltools.YearMonthChangeEvent;

/**
 * CalendarPanel,
 *
 * This implements a swing component that displays and draws a calendar. The CalendarPanel has
 * controls for changing the current month or year, and for selecting dates.
 *
 * In most cases, you will not need to create your own instances of CalendarPanel. The DatePicker
 * class automatically creates its own instances of CalendarPanel whenever the user clicks on the
 * "toggle calendar" button. However, the CalendarPanel can also (optionally) be used as an
 * independent component when desired.
 *
 * The Life cycle of CalendarPanel inside a DatePicker: Each time that the user clicks the toggle
 * calendar button on a date picker, a new CalendarPanel instance is created and displayed, within
 * of a new instance of CustomPopup. The calendar panel instance is closed and disposed each time
 * that the date picker popup is closed.
 */
public class CalendarPanel extends JPanel {

    /**
     * dateLabels, This holds a list of all the date labels in the calendar, including ones that
     * currently have dates or ones that are blank. This will always have exactly 42 labels. Date
     * labels are reused when the currently displayed month or year is changed. The first date label
     * is at index zero.
     */
    private ArrayList<JLabel> dateLabels;

    /**
     * topLeftLabel, This holds the top left label, which lies at the intersection of the weekday
     * labels row and the week number labels column.
     */
    private JLabel topLeftLabel;

    /**
     * weekdayLabels, This holds a list of all the weekday labels in the calendar. This will always
     * have exactly 7 labels. Individual weekday labels can display different days of the week
     * depending on the currently set "first day of the week". Weekday labels are reused when the
     * currently displayed month or year is changed. The first weekday label is at index zero.
     */
    private ArrayList<JLabel> weekdayLabels;

    /**
     * weekNumberLabels, This holds a list of all the week number labels in the calendar. This will
     * always have exactly 6 labels. These are used to display the "week number of the year" for the
     * calendar rows (the calendar weeks). These labels may or may not be visible, depending on the
     * current DatePickerSettings. Week number labels are reused when the currently displayed month
     * or year is changed. The first week number label is at index zero.
     */
    private ArrayList<JLabel> weekNumberLabels;

    /**
     * borderLabels, This holds a two dimensional array of all the border labels in the calendar.
     * Row 0 and Column 0 of the array will always contain null. Some of the other array elements
     * will also always contain null.
     *
     * Below is a visual representation of the location of the border labels inside this array. The
     * character 'X' represents a border label, and 'o' represents null.
     * <pre>
     * ~012345
     * 0oooooo
     * 1oXXXXX
     * 2oXoXoX
     * 3oXXXXX
     * 4oXoXoX
     * 5oXXXXX
     * </pre>
     */
    private JLabel[][] borderLabels;

    /**
     * calendarListeners, This holds a list of calendar listeners that wish to be notified each time
     * that a date is selected in the calendar panel, or the YearMonth is changed in the calendar
     * panel.
     */
    private ArrayList<CalendarListener> calendarListeners = new ArrayList<CalendarListener>();

    /**
     * constantFirstWeekdayLabelCell, This constant indicates the location of the first weekday
     * label inside of the center panel.
     */
    static private final Point constantFirstWeekdayLabelCell = new Point(4, 2);

    /**
     * constantFirstWeekNumberLabelCell, This constant indicates the location of the first week
     * number label inside of the center panel.
     */
    static private final Point constantFirstWeekNumberLabelCell = new Point(2, 6);

    /**
     * constantFirstDateLabelCell, This constant indicates the location of the first date label
     * inside of the center panel.
     */
    static private final Point constantFirstDateLabelCell = new Point(4, 6);

    /**
     * constantSizeOfCenterPanelBorders, This constant indicates the (total) size of all the borders
     * in the center panel, in pixels.
     */
    static private final Dimension constantSizeOfCenterPanelBorders = new Dimension(2, 5);

    /**
     * constantTopLeftLabelCell, This constant indicates the location of the topLeftLabel inside the
     * center panel.
     */
    static private final Point constantTopLeftLabelCell = new Point(2, 2);

    /**
     * constantWeekNumberLabelInsets, This is the size of the border of the week number labels, in
     * pixels.
     */
    static private final Insets constantWeekNumberLabelInsets = new Insets(0, 6, 0, 5);

    /**
     * displayedSelectedDate, This stores a date that will be highlighted in the calendar as the
     * "selected date", or it holds null if no date has been selected. This date is copied from the
     * date picker when the calendar is opened. This should not be confused with the "lastValidDate"
     * of a date picker object. This variable holds the selected date only for display purposes and
     * internal CalendarPanel use.
     */
    private LocalDate displayedSelectedDate = null;

    /**
     * displayedYearMonth, This stores the currently displayed year and month. This defaults to the
     * current year and month. This will never be null.
     */
    private YearMonth displayedYearMonth = YearMonth.now();

    /**
     * isIndependentCalendarPanel, This indicates whether or not this is an independent calendar
     * panel. This is true if this is an independent calendar panel, or false if this is a private
     * calendar panel for a DatePicker instance.
     */
    private boolean isIndependentCalendarPanel;

    /**
     * labelIndicatorEmptyBorder, This stores the empty border and the empty border size for the
     * following labels: Month indicator, Year indicator, Set date to Today, Clear date.
     */
    private EmptyBorder labelIndicatorEmptyBorder = new EmptyBorder(3, 2, 3, 2);

    /**
     * settings, This holds a reference to the date picker settings for this calendar panel.
     *
     * This will never be null after it is initialized, but it could be null before setSettings() is
     * called for the first time. Any functions that rely on the settings should check for null and
     * return if null, before continuing the function.
     */
    private DatePickerSettings settings;

    /**
     * yearTextField, The year text field is displayed any time that the user clicks the ellipsis
     * (...) inside the year selection drop down menu. This field allows the user to type year
     * numbers using the keyboard when desired.
     */
    private JIntegerTextField yearTextField;

    /**
     * JFormDesigner GUI components, These variables are automatically generated by JFormDesigner.
     * This section should not be modified by hand, but only modified from within the JFormDesigner
     * program.
     */
    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JPanel headerControlsPanel;
    private JButton buttonPreviousYear;
    private JButton buttonPreviousMonth;
    private JPanel monthAndYearOuterPanel;
    private JPanel monthAndYearInnerPanel;
    private JLabel labelMonth;
    private JLabel labelYear;
    private JButton buttonNextMonth;
    private JButton buttonNextYear;
    private JPanel centerPanel;
    private JPanel footerPanel;
    private JLabel labelSetDateToToday;
    private JLabel labelClearDate;
    private JPanel yearEditorPanel;
    private JButton doneEditingYearButton;
    // JFormDesigner - End of variables declaration  //GEN-END:variables

    /**
     * Constructor, Independent CalendarPanel with default settings. This creates an independent
     * calendar panel with a default set of DatePickerSettings. The calendar panel will use the
     * default operating system locale and language.
     */
    public CalendarPanel() {
        this(null, true);
    }

    /**
     * Constructor, Independent CalendarPanel with supplied settings. This creates This creates an
     * independent calendar panel with the supplied date picker settings. If the datePickerSettings
     * are null, then a default settings instance will be created and applied to the CalendarPanel.
     */
    public CalendarPanel(DatePickerSettings settings) {
        this(settings, true);
    }

    /**
     * Constructor, Private CalendarPanel For DatePicker. Important Note: This function is only
     * intended to be called from the DatePicker class. This creates a calendar panel from an
     * existing DatePicker, for internal usage by that DatePicker instance. This will use the
     * settings from the DatePicker instance.
     *
     * Technical note: This constructor is only called from the DatePicker.openPopup() function. A
     * new CalendarPanel is created every time the popup is opened. Therefore, any
     * DatePickerSettings variables that are initialized in this constructor are automatically able
     * to correctly handle being set either before or after, a DatePicker is constructed.
     */
    public CalendarPanel(DatePicker parentDatePicker) {
        this(parentDatePicker.getSettings(), false);
    }

    /**
     * Constructor, Private Full Constructor. This will create the calendar panel instance. This
     * constructor is private and can only be called from other CalendarPanel constructors.
     *
     * Note: None of the functions called from constructor, (or any functions called from those
     * functions) should depend on any settings variables.
     *
     * Exceptions to the above rule: # The setSettings() function. # Targets of listener functions.
     *
     */
    private CalendarPanel(DatePickerSettings datePickerSettings,
        boolean isIndependentCalendarPanelInstance) {
        // Save the information of whether this is an independent calendar panel.
        this.isIndependentCalendarPanel = isIndependentCalendarPanelInstance;
        // Call the JFormDesigner managed initialization function.
        initComponents();
        // Add needed mouse listeners to the today and clear buttons. 
        zAddMouseListenersToTodayAndClearButtons();
        // Create the yearTextField, and add it to the yearEditorPanel.
        yearTextField = new JIntegerTextField(4);
        yearTextField.setMaximumValue(Year.MAX_VALUE);
        yearTextField.setMinimumValue(Year.MIN_VALUE);
        yearTextField.setMargin(new Insets(1, 1, 1, 1));
        yearEditorPanel.add(yearTextField, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 0, 0), 0, 0));
        // Add the text change listener to the yearTextField.
        yearTextField.numberChangeListener = new IntegerTextFieldNumberChangeListener() {
            @Override
            public void integerTextFieldNumberChanged(JIntegerTextField source, int newValue) {
                YearMonth newYearMonth = YearMonth.of(newValue, displayedYearMonth.getMonth());
                drawCalendar(newYearMonth);
            }
        };
        // Initialize the doneEditingYearButton.
        doneEditingYearButton.setMargin(new java.awt.Insets(0, 0, 0, 0));
        doneEditingYearButton.setText("\u2713");
        // Set the calendar panel to be opaque.
        setOpaque(true);
        // Shrink the buttons for previous and next year and month.
        buttonPreviousYear.setMargin(new java.awt.Insets(1, 2, 1, 2));
        buttonNextYear.setMargin(new java.awt.Insets(1, 2, 1, 2));
        buttonPreviousMonth.setMargin(new java.awt.Insets(1, 2, 1, 2));
        buttonNextMonth.setMargin(new java.awt.Insets(1, 2, 1, 2));

        // Generate and add the various calendar panel labels.
        // These are only generated once. 
        addDateLabels();
        addWeekdayLabels();
        addTopLeftLabel();
        addWeekNumberLabels();
        addBorderLabels();
        // Save and apply the supplied settings.
        setSettings(datePickerSettings);
    }

    /**
     * addBorderLabels, This adds the border labels to the calendar panel and to the two dimensional
     * border labels array.
     *
     * Below is a visual representation of the location of the border labels inside this array. The
     * character 'X' represents a border label, and 'o' represents null.
     * <pre>
     * ~012345
     * 0oooooo
     * 1oXXXXX
     * 2oXoXoX
     * 3oXXXXX
     * 4oXoXoX
     * 5oXXXXX
     *
     * This function should not depend on any settings variables.
     */
    private void addBorderLabels() {
        borderLabels = new JLabel[6][6];
        // These two arrays represent the cell location of every border label.
        // Note that some coordinate combinations from these arrays are not used.
        // The array index is based on the border label index (not the cell location).
        int[] labelLocations_X_forColumn = new int[]{0, 1, 2, 3, 4, 11};
        int[] labelLocations_Y_forRow = new int[]{0, 1, 2, 5, 6, 12};
        // These integers represent the dimensions of every border label. 
        // Note that some dimension combinations from these arrays are not used.
        // The array index is based on the border label index (not the cell location).
        int[] labelWidthsInCells_forColumn = new int[]{0, 1, 1, 1, 7, 1};
        int[] labelHeightsInCells_forRow = new int[]{0, 1, 3, 1, 6, 1};
        // These points represent border label indexes that should be created and used.
        Point[] allBorderLabelIndexes = new Point[]{
            new Point(1, 1), new Point(2, 1), new Point(3, 1), new Point(4, 1), new Point(5, 1),
            new Point(1, 2), new Point(3, 2), new Point(5, 2),
            new Point(1, 3), new Point(2, 3), new Point(3, 3), new Point(4, 3), new Point(5, 3),
            new Point(1, 4), new Point(3, 4), new Point(5, 4),
            new Point(1, 5), new Point(2, 5), new Point(3, 5), new Point(4, 5), new Point(5, 5)};
        // Create all the border labels.
        for (Point index : allBorderLabelIndexes) {
            Point labelLocationCell = new Point(labelLocations_X_forColumn[index.x],
                labelLocations_Y_forRow[index.y]);
            Dimension labelSizeInCells = new Dimension(labelWidthsInCells_forColumn[index.x],
                labelHeightsInCells_forRow[index.y]);
            JLabel label = new JLabel();
            // The only properties we need on instantiation are that the label is opaque, and 
            // that it is not visible by default. The other default border properties will be 
            // set later.
            label.setOpaque(true);
            label.setVisible(false);
            borderLabels[index.x][index.y] = label;
            centerPanel.add(label, CC.xywh(labelLocationCell.x, labelLocationCell.y,
                labelSizeInCells.width, labelSizeInCells.height));
        }
    }

    /**
     * addDateLabels, This adds a set of 42 date labels to the calendar, and ties each of those
     * labels to a mouse click event handler. The date labels are reused any time that the calendar
     * is redrawn.
     *
     * This function should not depend on any settings variables.
     */
    private void addDateLabels() {
        dateLabels = new ArrayList<JLabel>();
        for (int i = 0; i < 42; ++i) {
            int dateLabelColumnX = ((i % 7)) + constantFirstDateLabelCell.x;
            int dateLabelRowY = ((i / 7) + constantFirstDateLabelCell.y);
            JLabel dateLabel = new JLabel();
            dateLabel.setHorizontalAlignment(SwingConstants.CENTER);
            dateLabel.setVerticalAlignment(SwingConstants.CENTER);
            dateLabel.setBackground(Color.white);
            dateLabel.setForeground(Color.black);
            dateLabel.setBorder(null);
            dateLabel.setOpaque(true);
            dateLabel.setText("" + i);
            CellConstraints constraints = CC.xy(dateLabelColumnX, dateLabelRowY);
            centerPanel.add(dateLabel, constraints);
            dateLabels.add(dateLabel);
            // Add a mouse click listener for every date label, even the blank ones.
            dateLabel.addMouseListener(new MouseLiberalAdapter() {
                @Override
                public void mouseLiberalClick(MouseEvent e) {
                    dateLabelMousePressed(e);
                }
            });
        }
    }

    /**
     * addWeekNumberLabels, This adds a set of 6 week number labels to the calendar panel. The text
     * of these labels is set with locale sensitive week numbers each time that the calendar is
     * redrawn.
     *
     * This function should not depend on any settings variables.
     */
    private void addWeekNumberLabels() {
        weekNumberLabels = new ArrayList<JLabel>();
        int weekNumberLabelColumnX = constantFirstWeekNumberLabelCell.x;
        int weekNumberLabelWidthInCells = 1;
        int weekNumberLabelHeightInCells = 1;
        for (int i = 0; i < 6; ++i) {
            int weekNumberLabelRowY = (i + constantFirstWeekNumberLabelCell.y);
            JLabel weekNumberLabel = new JLabel();
            weekNumberLabel.setHorizontalAlignment(SwingConstants.CENTER);
            weekNumberLabel.setVerticalAlignment(SwingConstants.CENTER);
            weekNumberLabel.setBorder(new EmptyBorder(constantWeekNumberLabelInsets));
            weekNumberLabel.setOpaque(true);
            weekNumberLabel.setText("3" + i);
            weekNumberLabel.setVisible(false);
            CellConstraints constraints = CC.xywh(weekNumberLabelColumnX, weekNumberLabelRowY,
                weekNumberLabelWidthInCells, weekNumberLabelHeightInCells);
            centerPanel.add(weekNumberLabel, constraints);
            weekNumberLabels.add(weekNumberLabel);
        }
        setSizeOfWeekNumberLabels();
    }

    /**
     * addWeekdayLabels, This adds a set of 7 weekday labels to the calendar panel. The text of
     * these labels is set with locale sensitive weekday names each time that the calendar is
     * redrawn.
     *
     * This function should not depend on any settings variables.
     */
    private void addWeekdayLabels() {
        weekdayLabels = new ArrayList<JLabel>();
        int weekdayLabelRowY = constantFirstWeekdayLabelCell.y;
        int weekdayLabelWidthInCells = 1;
        int weekdayLabelHeightInCells = 3;
        for (int i = 0; i < 7; ++i) {
            int weekdayLabelColumnX = (i + constantFirstWeekdayLabelCell.x);
            JLabel weekdayLabel = new JLabel();
            weekdayLabel.setHorizontalAlignment(SwingConstants.CENTER);
            weekdayLabel.setVerticalAlignment(SwingConstants.CENTER);
            weekdayLabel.setBorder(new EmptyBorder(0, 2, 0, 2));
            weekdayLabel.setOpaque(true);
            weekdayLabel.setText("wd" + i);
            CellConstraints constraints = CC.xywh(weekdayLabelColumnX, weekdayLabelRowY,
                weekdayLabelWidthInCells, weekdayLabelHeightInCells);
            centerPanel.add(weekdayLabel, constraints);
            weekdayLabels.add(weekdayLabel);
        }
    }

    /**
     * addTopLeftLabel, This adds the top left label to the center panel.
     *
     * This function should not depend on any settings variables.
     */
    private void addTopLeftLabel() {
        topLeftLabel = new JLabel();
        topLeftLabel.setOpaque(true);
        topLeftLabel.setVisible(false);
        centerPanel.add(topLeftLabel, CC.xywh(
            constantTopLeftLabelCell.x, constantTopLeftLabelCell.y, 1, 3));
    }

    /**
     * addCalendarListener, This adds a calendar listener to this calendar panel. For additional
     * details, see the CalendarListener class documentation.
     */
    public void addCalendarListener(CalendarListener listener) {
        calendarListeners.add(listener);
    }

    /**
     * buttonNextMonthActionPerformed, This event is called when the next month button is pressed.
     * This sets the YearMonth of the calendar to the next month, and redraws the calendar.
     */
    private void buttonNextMonthActionPerformed(ActionEvent e) {
        // We catch and ignore any exceptions at the minimum and maximum of the local date range.
        try {
            drawCalendar(displayedYearMonth.plusMonths(1));
        } catch (Exception ex) {
        }
    }

    /**
     * buttonNextYearActionPerformed, This event is called when the next year button is pressed.
     * This sets the YearMonth of the calendar to the next year, and redraws the calendar.
     */
    private void buttonNextYearActionPerformed(ActionEvent e) {
        // We catch and ignore any exceptions at the minimum and maximum of the local date range.
        try {
            drawCalendar(displayedYearMonth.plusYears(1));
        } catch (Exception ex) {
        }
    }

    /**
     * buttonPreviousMonthActionPerformed, This event is called when the previous month button is
     * pressed. This sets the YearMonth of the calendar to the previous month, and redraws the
     * calendar.
     */
    private void buttonPreviousMonthActionPerformed(ActionEvent e) {
        // We catch and ignore any exceptions at the minimum and maximum of the local date range.
        try {
            drawCalendar(displayedYearMonth.minusMonths(1));
        } catch (Exception ex) {
        }
    }

    /**
     * buttonPreviousYearActionPerformed, This event is called when the previous year button is
     * pressed. This sets the YearMonth of the calendar to the previous year, and redraws the
     * calendar.
     */
    private void buttonPreviousYearActionPerformed(ActionEvent e) {
        // We catch and ignore any exceptions at the minimum and maximum of the local date range.
        try {
            drawCalendar(displayedYearMonth.minusYears(1));
        } catch (Exception ex) {
        }
    }

    /**
     * dateLabelMousePressed, This event is called any time that the user clicks on a date label in
     * the calendar. This sets the date picker to the selected date, and closes the calendar panel.
     */
    private void dateLabelMousePressed(MouseEvent e) {
        // Get the label that was clicked.
        JLabel label = (JLabel) e.getSource();
        // If the label is empty, do nothing and return.
        String labelText = label.getText();
        if ("".equals(labelText)) {
            return;
        }
        // We have a label with a specific date, so set the date and close the calendar.
        int dayOfMonth = Integer.parseInt(labelText);
        LocalDate clickedDate = LocalDate.of(
            displayedYearMonth.getYear(), displayedYearMonth.getMonth(), dayOfMonth);
        userSelectedADate(clickedDate);
    }

    /**
     * drawCalendar, This can be called to redraw the calendar. The calendar will be drawn with the
     * currently displayed year and month. This function should not normally need to be called by
     * the programmer, because the calendar will automatically redraw itself as needed.
     */
    public void drawCalendar() {
        drawCalendar(displayedYearMonth);
    }

    /**
     * drawCalendar, This is called whenever the calendar needs to be drawn. This takes a year and a
     * month to indicate which month should be drawn in the calendar.
     */
    private void drawCalendar(int year, Month month) {
        drawCalendar(YearMonth.of(year, month));
    }

    /**
     * drawCalendar, This is called whenever the calendar needs to be drawn. This takes a year and a
     * month to indicate which month should be drawn in the calendar.
     */
    private void drawCalendar(YearMonth newYearMonth) {
        drawCalendar(newYearMonth, null);
    }

    /**
     * drawCalendar, This is called whenever the calendar needs to be drawn. This takes a year and a
     * month to indicate which month should be drawn in the calendar. This can optionally take an
     * old YearMonth, to be used in notification of listeners. If an old YearMonth is not supplied,
     * the YearMonth in "this.displayedYearMonth" will be used as the old YearMonth.
     */
    private void drawCalendar(YearMonth newYearMonth, YearMonth oldYearMonthOrNull) {
        // Skip this function if the settings have not been applied.
        if (settings == null) {
            return;
        }
        // Determine and save the old YearMonth, for later notifying any calendar listeners.
        YearMonth oldYearMonth = (oldYearMonthOrNull == null)
            ? this.displayedYearMonth : oldYearMonthOrNull;
        // Save the (new) displayed yearMonth.
        this.displayedYearMonth = newYearMonth;

        // Notify any CalendarListeners of a possible change to the YearMonth.
        // Note: It is intentional that this section of code does not have it's own function, 
        // because this should not be called from anywhere else. 
        // Note: this is placed at the beginning of the drawCalendar function, so that if desired, 
        // a developer could change the appearance of the calendar in response to this event. 
        for (CalendarListener calendarListener : calendarListeners) {
            YearMonthChangeEvent yearMonthChangeEvent = new YearMonthChangeEvent(
                this, this.displayedYearMonth, oldYearMonth);
            calendarListener.yearMonthChanged(yearMonthChangeEvent);
        }

        // Get the displayed month and year.
        Month displayedMonth = newYearMonth.getMonth();
        int displayedYear = newYearMonth.getYear();
        // Get an instance of the calendar symbols for the current locale.
        DateFormatSymbols symbols = DateFormatSymbols.getInstance(settings.getLocale());
        // Get the days of the week in the local language.
        String localShortDaysOfWeek[] = symbols.getShortWeekdays();
        // Get the full month names in the current locale.
        int zeroBasedMonthIndex = (displayedMonth.getValue() - 1);
        String localizedFullMonth = settings.getTranslationArrayStandaloneLongMonthNames()[zeroBasedMonthIndex];
        String localizedShortMonth = settings.getTranslationArrayStandaloneShortMonthNames()[zeroBasedMonthIndex];
        // Get the first day of the month, and the first day of week.
        LocalDate firstDayOfMonth = LocalDate.of(displayedYear, displayedMonth, 1);
        DayOfWeek firstDayOfWeekOfMonth = firstDayOfMonth.getDayOfWeek();
        // Get the last day of the month.
        int lastDateOfMonth = getLastDayOfMonth(displayedYearMonth);
        // Find out if we have a selected date that is inside the currently displayed month.
        boolean selectedDateIsInDisplayedMonth = (displayedSelectedDate != null)
            && (displayedSelectedDate.getYear() == displayedYear)
            && (displayedSelectedDate.getMonth() == displayedMonth);
        // Set the component colors and fonts.
        Color calendarPanelBackgroundColor = settings.getColor(DateArea.BackgroundOverallCalendarPanel);
        setBackground(calendarPanelBackgroundColor);
        headerControlsPanel.setBackground(calendarPanelBackgroundColor);
        monthAndYearOuterPanel.setBackground(calendarPanelBackgroundColor);
        footerPanel.setBackground(calendarPanelBackgroundColor);
        // Set the background of the navigation buttons.
        Color navigationButtonsColor
            = settings.getColor(DateArea.BackgroundMonthAndYearNavigationButtons);
        buttonPreviousYear.setBackground(navigationButtonsColor);
        buttonNextYear.setBackground(navigationButtonsColor);
        buttonPreviousMonth.setBackground(navigationButtonsColor);
        buttonNextMonth.setBackground(navigationButtonsColor);
        // Set the fonts of all buttons. 
        buttonPreviousYear.setFont(settings.getFontMonthAndYearNavigationButtons());
        buttonNextYear.setFont(settings.getFontMonthAndYearNavigationButtons());
        buttonPreviousMonth.setFont(settings.getFontMonthAndYearNavigationButtons());
        buttonNextMonth.setFont(settings.getFontMonthAndYearNavigationButtons());
        // Set the font-colors of all buttons. 
        buttonPreviousYear.setForeground(settings.getColor(DateArea.TextMonthAndYearNavigationButtons));
        buttonNextYear.setForeground(settings.getColor(DateArea.TextMonthAndYearNavigationButtons));
        buttonPreviousMonth.setForeground(settings.getColor(DateArea.TextMonthAndYearNavigationButtons));
        buttonNextMonth.setForeground(settings.getColor(DateArea.TextMonthAndYearNavigationButtons));
        // Set the fonts of all labels. 
        labelMonth.setFont(settings.getFontMonthAndYearMenuLabels());
        labelYear.setFont(settings.getFontMonthAndYearMenuLabels());
        labelSetDateToToday.setFont(settings.getFontTodayLabel());
        labelClearDate.setFont(settings.getFontClearLabel());
        // Set the font-colors of all labels. 
        labelMonth.setForeground(settings.getColor(DateArea.TextMonthAndYearMenuLabels));
        labelYear.setForeground(settings.getColor(DateArea.TextMonthAndYearMenuLabels));
        labelSetDateToToday.setForeground(settings.getColor(DateArea.TextTodayLabel));
        labelClearDate.setForeground(settings.getColor(DateArea.TextClearLabel));
        // Set the month and the year label text values. 
        // Use the short month if the user is currently using the keyboard editor for the year.
        if (monthAndYearInnerPanel.isAncestorOf(yearEditorPanel)) {
            labelMonth.setText(localizedShortMonth);
        } else {
            labelMonth.setText(localizedFullMonth);
        }
        final String displayedYearString = "" + displayedYear;
        labelYear.setText(displayedYearString);
        if (!displayedYearString.equals(yearTextField.getText())) {
            // This invokeLater call fixes a bug where an exception was being thrown if you typed
            // "0X" into the year text field. The exception was: 
            // "IllegalStateException: Attempt to mutate in notification".
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    yearTextField.skipNotificationOfNumberChangeListenerWhileTrue = true;
                    yearTextField.setText(displayedYearString);
                    yearTextField.skipNotificationOfNumberChangeListenerWhileTrue = false;
                }
            });
        }
        // Set the days of the week labels, and create an array to represent the weekday positions.
        ArrayList<DayOfWeek> daysOfWeekAsDisplayed = new ArrayList<DayOfWeek>();
        int isoFirstDayOfWeekValue = settings.getFirstDayOfWeekDisplayedOnCalendar().getValue();
        int isoLastDayOfWeekOverflowed = isoFirstDayOfWeekValue + 6;
        int weekdayLabelArrayIndex = 0;
        for (int dayOfWeek = isoFirstDayOfWeekValue; dayOfWeek <= isoLastDayOfWeekOverflowed; dayOfWeek++) {
            int localShortDaysOfWeekArrayIndex = (dayOfWeek % 7) + 1;
            int isoDayOfWeek = (dayOfWeek > 7) ? (dayOfWeek - 7) : dayOfWeek;
            DayOfWeek currentDayOfWeek = DayOfWeek.of(isoDayOfWeek);
            daysOfWeekAsDisplayed.add(currentDayOfWeek);
            weekdayLabels.get(weekdayLabelArrayIndex)
                .setText(localShortDaysOfWeek[localShortDaysOfWeekArrayIndex]);
            ++weekdayLabelArrayIndex;
        }
        // Set the dates of the month labels.
        // Also save the label for the selected date, if one is present in the current month.
        // Also save the first date in each used row, for later use while displaying week numbers.
        ArrayList<LocalDate> firstDateInEachUsedRow = new ArrayList<LocalDate>();
        boolean insideValidRange = false;
        int dayOfMonth = 1;
        JLabel selectedDateLabel = null;
        DateVetoPolicy vetoPolicy = settings.getVetoPolicy();
        DateHighlightPolicy highlightPolicy = settings.getHighlightPolicy();
        for (int dateLabelArrayIndex = 0; dateLabelArrayIndex < dateLabels.size(); ++dateLabelArrayIndex) {
            // Get the current date label.
            JLabel dateLabel = dateLabels.get(dateLabelArrayIndex);
            // Reset the state of every label to a default state.
            dateLabel.setBackground(settings.getColor(DateArea.CalendarBackgroundNormalDates));
            dateLabel.setForeground(settings.getColor(DateArea.CalendarTextNormalDates));
            dateLabel.setFont(settings.getFontCalendarDateLabels());
            dateLabel.setBorder(new EmptyBorder(1, 1, 1, 1));
            dateLabel.setEnabled(true);
            dateLabel.setToolTipText(null);
            // Calculate the index to use on the daysOfWeekAsDisplayed array.
            int daysOfWeekAsDisplayedArrayIndex = dateLabelArrayIndex % 7;
            // Check to see if we are inside the valid range for days of this month.
            if (daysOfWeekAsDisplayed.get(daysOfWeekAsDisplayedArrayIndex) == firstDayOfWeekOfMonth
                && dateLabelArrayIndex < 7) {
                insideValidRange = true;
            }
            if (dayOfMonth > lastDateOfMonth) {
                insideValidRange = false;
            }
            // While we are inside the valid range, set the date labels with the day of the month.
            if (insideValidRange) {
                // Get a local date object for the current date.
                LocalDate currentDate = LocalDate.of(displayedYear, displayedMonth, dayOfMonth);

                // Save the first date of each used calendar row, for getting week numbers.
                // Notes: The first day of the month will always be in the first row. 
                // The first day of the month will only occur once while inside the valid range.
                if (dayOfMonth == 1) {
                    // The first date of the first row will often be a date from the previous month. 
                    // The first date label will often not have a displayed date.
                    // We encompass this in a try block, so that LocalDate.MIN will not throw an 
                    // exception here.
                    try {
                        LocalDate firstDateOfFirstRow = currentDate.minusDays(dateLabelArrayIndex);
                        firstDateInEachUsedRow.add(firstDateOfFirstRow);
                    } catch (Exception e) {
                        firstDateInEachUsedRow.add(LocalDate.MIN);
                    }
                } else if ((dateLabelArrayIndex % 7) == 0) {
                    // If we are not in the first row, and we are in the first label of a row, then 
                    // add the current date to the firstDateInEachRow array.
                    firstDateInEachUsedRow.add(currentDate);
                }

                // Get the veto policy and highlight policy information for this date.
                boolean dateIsVetoed = InternalUtilities.isDateVetoed(vetoPolicy, currentDate);
                HighlightInformation highlightInfo = null;
                if (highlightPolicy != null) {
                    highlightInfo = highlightPolicy.getHighlightInformationOrNull(currentDate);
                }
                if (dateIsVetoed) {
                    dateLabel.setEnabled(false);
                    dateLabel.setBackground(settings.getColor(DateArea.CalendarBackgroundVetoedDates));
                    // Note, the foreground color of a disabled date label will always be grey.
                    // So it is not easily possible let the programmer customize that color. 
                }
                if ((!dateIsVetoed) && (highlightInfo != null)) {
                    // Get the "modifiable default" highlight and background colors from settings.
                    Color colorBackground = settings.getColor(DateArea.CalendarDefaultBackgroundHighlightedDates);
                    Color colorText = settings.getColor(DateArea.CalendarDefaultTextHighlightedDates);
                    // If needed, modify the highlight and background colors as requested in the
                    // highlight information.
                    if (highlightInfo.colorBackground != null) {
                        colorBackground = highlightInfo.colorBackground;
                    }
                    if (highlightInfo.colorText != null) {
                        colorText = highlightInfo.colorText;
                    }
                    // Set the highlight and background colors for the label.
                    dateLabel.setBackground(colorBackground);
                    dateLabel.setForeground(colorText);
                    // If needed, set the highlight tooltip text.
                    if (highlightInfo.tooltipText != null
                        && (!(highlightInfo.tooltipText.isEmpty()))) {
                        dateLabel.setToolTipText(highlightInfo.tooltipText);
                    }
                }
                // If needed, save the label for the selected date.
                if (selectedDateIsInDisplayedMonth && displayedSelectedDate != null
                    && displayedSelectedDate.getDayOfMonth() == dayOfMonth) {
                    selectedDateLabel = dateLabel;
                }
                // Set the text for the current date.
                dateLabel.setText("" + dayOfMonth);
                ++dayOfMonth;
            } else {
                // We are not inside the valid range, so set this label to an empty string.
                dateLabel.setText("");
            }
        }
        // If needed, change the color of the selected date.
        if (selectedDateLabel != null) {
            selectedDateLabel.setBackground(settings.getColor(DateArea.CalendarBackgroundSelectedDate));
            selectedDateLabel.setBorder(new LineBorder(
                settings.getColor(DateArea.CalendarBorderSelectedDate)));
        }

        // If needed, draw the week numbers.
        boolean showWeekNumbers = settings.getWeekNumbersDisplayed();
        int usedRowCount = firstDateInEachUsedRow.size();
        WeekFields weekNumberRules = settings.getWeekNumberRules();
        topLeftLabel.setVisible(showWeekNumbers);
        for (int weekNumberLabelIndex = 0; weekNumberLabelIndex < weekNumberLabels.size();
            ++weekNumberLabelIndex) {
            JLabel currentLabel = weekNumberLabels.get(weekNumberLabelIndex);
            currentLabel.setVisible(showWeekNumbers);
            currentLabel.setText("");
            // If needed, populate the week number label with a week number.
            if ((showWeekNumbers) && (weekNumberRules != null)
                && (weekNumberLabelIndex < usedRowCount)) {
                LocalDate firstDateInRow = firstDateInEachUsedRow.get(weekNumberLabelIndex);
                int weekNumber = zGetWeekNumberForASevenDayRange(
                    firstDateInRow, weekNumberRules, false);
                currentLabel.setText("" + weekNumber);
            }
        }

        // Set the background color for the topLeftLabel.
        topLeftLabel.setBackground(settings.getColor(DateArea.BackgroundTopLeftLabelAboveWeekNumbers));
        // Set the colors and fonts for the weekday labels.
        for (JLabel weekdayLabel : weekdayLabels) {
            weekdayLabel.setBackground(settings.getColorBackgroundWeekdayLabels());
            weekdayLabel.setForeground(settings.getColor(DateArea.CalendarTextWeekdays));
            weekdayLabel.setFont(settings.getFontCalendarWeekdayLabels());
        }
        // Set the colors and fonts for the week number labels.
        for (JLabel weekNumberLabel : weekNumberLabels) {
            weekNumberLabel.setBackground(settings.getColorBackgroundWeekNumberLabels());
            weekNumberLabel.setForeground(settings.getColor(DateArea.CalendarTextWeekNumbers));
            weekNumberLabel.setFont(settings.getFontCalendarWeekNumberLabels());
        }
        // Set the size of the week number labels.
        setSizeOfWeekNumberLabels();

        // Set the label for the today button.
        String todayDateString = settings.getFormatForTodayButton().format(LocalDate.now());
        String todayLabel = settings.getTranslationToday() + ":  " + todayDateString;
        labelSetDateToToday.setText(todayLabel);
        // If today is vetoed, disable the today button.
        boolean todayIsVetoed = InternalUtilities.isDateVetoed(
            vetoPolicy, LocalDate.now());
        labelSetDateToToday.setEnabled(!todayIsVetoed);

        // Set the visibility of all the calendar control buttons (and button labels).
        zApplyVisibilityOfButtons();

        // Set the label for the clear button.
        labelClearDate.setText(settings.getTranslationClear());

        // Set the size of the month and year panel to be big enough to hold the largest month text.
        setSizeOfMonthYearPanel();

        // Set the size of the cell that contains the date panel.
        setSizeOfDatePanelCell();

        // Repaint the component.
        // This was a supplied fix for a redrawing issue on MacOS, from NicholasQu.
        // https://github.com/LGoodDatePicker/LGoodDatePicker/issues/61
        this.repaint();
    }

    /**
     * getCalendarListeners, This returns a new ArrayList, that contains any calendar listeners that
     * are registered with this CalendarPanel.
     */
    public ArrayList<CalendarListener> getCalendarListeners() {
        return new ArrayList<CalendarListener>(calendarListeners);
    }

    /**
     * getDisplayedYearMonth, This returns the year and month that is currently displayed in the
     * calendar. This will never return null. Note that this is -not- the same as the displayed
     * selected date, and the displayed selected date may or may not be inside the
     * displayedYearMonth. It is expected that this function will be rarely needed by developers.
     */
    public YearMonth getDisplayedYearMonth() {
        // Return the displayedYearMonth.
        // Technical note: YearMonth is an immutable class.
        return displayedYearMonth;
    }

    /**
     * getSelectedDate, This returns the date that is currently marked as "selected" in the
     * calendar. If no date is selected, then this will return null.
     *
     * This should not be confused with the "last valid date" of a DatePicker object. This function
     * would typically only be needed when the CalendarPanel class is being used independently from
     * the DatePicker class.
     */
    public LocalDate getSelectedDate() {
        return displayedSelectedDate;
    }

    /**
     * getSettings, This returns the calendar panel settings instance.
     */
    public DatePickerSettings getSettings() {
        return settings;
    }

    /**
     * getLastDayOfMonth, This returns the last day of the month for the specified year and month.
     *
     * Implementation notes: As of this writing, the below implementation is verified to work
     * correctly for negative years, as those years are to defined in the iso 8601 your format that
     * is used by java.time.YearMonth. This functionality can be tested by by checking to see if to
     * see if the year "-0004" is correctly displayed as a leap year. Leap years have 29 days in
     * February. There should be 29 days in the month of "February 1, -0004".
     */
    private int getLastDayOfMonth(YearMonth yearMonth) {
        LocalDate firstDayOfMonth = LocalDate.of(yearMonth.getYear(), yearMonth.getMonth(), 1);
        int lastDayOfMonth = firstDayOfMonth.lengthOfMonth();
        return lastDayOfMonth;
    }

    /**
     * getMonthOrYearMenuLocation, This calculates the position should be used to set the location
     * of the month or the year popup menus, relative to their source labels. These menus are used
     * to change the current month or current year from within the calendar panel.
     */
    private Point getMonthOrYearMenuLocation(JLabel sourceLabel, JPopupMenu filledPopupMenu) {
        Rectangle labelBounds = sourceLabel.getBounds();
        int menuHeight = filledPopupMenu.getPreferredSize().height;
        int popupX = labelBounds.x + labelBounds.width + 1;
        int popupY = labelBounds.y + (labelBounds.height / 2) - (menuHeight / 2);
        return new Point(popupX, popupY);
    }

    /**
     * labelClearDateMousePressed, This event is called when the "Clear" label is clicked in a date
     * picker. This sets the date picker date to an empty date. (This sets the last valid date to
     * null.)
     */
    private void labelClearDateMousePressed(MouseEvent e) {
        userSelectedADate(null);
    }

    /**
     * labelIndicatorMouseEntered, This event is called when the user move the mouse inside a
     * monitored label. This is used to generate mouse over effects for the calendar panel.
     */
    private void labelIndicatorMouseEntered(MouseEvent e) {
        // Skip this function if the settings have not been applied.
        if (settings == null) {
            return;
        }
        JLabel label = ((JLabel) e.getSource());
        // Do not highlight the today label if today is vetoed.
        if (label == labelSetDateToToday) {
            DateVetoPolicy vetoPolicy = settings.getVetoPolicy();
            boolean todayIsVetoed = InternalUtilities.isDateVetoed(vetoPolicy, LocalDate.now());
            if (todayIsVetoed) {
                return;
            }
        }
        // Do not highlight the month label if the month menu is disabled.
        if ((label == labelMonth) && (settings.getEnableMonthMenu() == false)) {
            return;
        }
        // Do not highlight the year label if the year menu is disabled.
        if ((label == labelYear) && (settings.getEnableYearMenu() == false)) {
            return;
        }
        // Highlight the label.
        label.setBackground(new Color(184, 207, 229));
        label.setBorder(new CompoundBorder(
            new LineBorder(Color.GRAY), labelIndicatorEmptyBorder));
    }

    /**
     * labelIndicatorMouseExited, This event is called when the user move the mouse outside of a
     * monitored label. This is used to generate mouse over effects for the calendar panel.
     */
    private void labelIndicatorMouseExited(MouseEvent e) {
        JLabel label = ((JLabel) e.getSource());
        labelIndicatorSetColorsToDefaultState(label);
    }

    /**
     * labelIndicatorSetColorsToDefaultState, This event is called to set a label indicator to the
     * state it should have when there is no mouse hovering over it.
     */
    private void labelIndicatorSetColorsToDefaultState(JLabel label) {
        if (label == null || settings == null) {
            return;
        }
        if (label == labelMonth || label == labelYear) {
            label.setBackground(settings.getColor(DateArea.BackgroundMonthAndYearMenuLabels));
            monthAndYearInnerPanel.setBackground(settings.getColor(DateArea.BackgroundMonthAndYearMenuLabels));
        }
        if (label == labelSetDateToToday) {
            label.setBackground(settings.getColor(DateArea.BackgroundTodayLabel));
        }
        if (label == labelClearDate) {
            label.setBackground(settings.getColor(DateArea.BackgroundClearLabel));
        }
        label.setBorder(new CompoundBorder(
            new EmptyBorder(1, 1, 1, 1), labelIndicatorEmptyBorder));
    }

    /**
     * labelMonthIndicatorMousePressed, This event is called any time that the user clicks on the
     * month display label in the calendar. This opens a menu that the user can use to select a new
     * month in the same year.
     */
    private void labelMonthIndicatorMousePressed(MouseEvent e) {
        // Skip this function if the settings have not been applied.
        if (settings == null) {
            return;
        }
        // If the month menu is disabled, then return.
        if (settings.getEnableMonthMenu() == false) {
            return;
        }
        // Create and show the month popup menu.
        JPopupMenu monthPopupMenu = new JPopupMenu();
        String[] allLocalMonths = settings.getTranslationArrayStandaloneLongMonthNames();
        for (int i = 0; i < allLocalMonths.length; ++i) {
            final String localMonth = allLocalMonths[i];
            final int localMonthZeroBasedIndexTemp = i;
            if (!localMonth.isEmpty()) {
                monthPopupMenu.add(new JMenuItem(new AbstractAction(localMonth) {
                    int localMonthZeroBasedIndex = localMonthZeroBasedIndexTemp;

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        drawCalendar(displayedYearMonth.getYear(),
                            Month.of(localMonthZeroBasedIndex + 1));
                    }
                }));
            }
        }
        Point menuLocation = getMonthOrYearMenuLocation(labelMonth, monthPopupMenu);
        monthPopupMenu.show(monthAndYearInnerPanel, menuLocation.x, menuLocation.y);
    }

    /**
     * labelSetDateToTodayMousePressed, This event is called when the "Today" label is clicked in a
     * date picker. This sets the date picker date to today.
     */
    private void labelSetDateToTodayMousePressed(MouseEvent e) {
        userSelectedADate(LocalDate.now());
    }

    /**
     * labelYearIndicatorMousePressed, This event is called any time that the user clicks on the
     * year display label in the calendar. This opens a menu that the user can use to select a new
     * year within a chosen range of the previously displayed year.
     */
    private void labelYearIndicatorMousePressed(MouseEvent e) {
        // If the year menu is disabled, then return.
        if (settings.getEnableYearMenu() == false) {
            return;
        }
        // Create and show the year menu.
        int firstYearDifference = -11;
        int lastYearDifference = +11;
        JPopupMenu yearPopupMenu = new JPopupMenu();
        for (int yearDifference = firstYearDifference; yearDifference <= lastYearDifference;
            ++yearDifference) {
            // No special processing is required for the BC to AD transition in the 
            // ISO 8601 calendar system. Year zero does exist in this system.
            // This try block handles exceptions that can occur at LocalDate.MAX.
            try {
                YearMonth choiceYearMonth = displayedYearMonth.plusYears(yearDifference);
                String choiceYearMonthString = "" + choiceYearMonth.getYear();
                yearPopupMenu.add(new JMenuItem(new AbstractAction(choiceYearMonthString) {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String chosenMenuText = ((JMenuItem) e.getSource()).getText();
                        int chosenYear = Integer.parseInt(chosenMenuText);
                        drawCalendar(chosenYear, displayedYearMonth.getMonth());
                    }
                }));
            } catch (Exception ex) {
            }
        }
        String choiceOtherYearString = "( . . . )";
        yearPopupMenu.add(new JMenuItem(new AbstractAction(choiceOtherYearString) {
            @Override
            public void actionPerformed(ActionEvent e) {
                otherYearMenuItemClicked();
            }
        }));
        Point menuLocation = getMonthOrYearMenuLocation(labelYear, yearPopupMenu);
        yearPopupMenu.show(monthAndYearInnerPanel, menuLocation.x, menuLocation.y);
    }

    private void doneEditingYearButtonActionPerformed(ActionEvent e) {
        monthAndYearInnerPanel.remove(yearEditorPanel);
        // This will also set the visibility of the year menu label.
        drawCalendar(displayedYearMonth);
    }

    private void otherYearMenuItemClicked() {
        monthAndYearInnerPanel.add(yearEditorPanel, CC.xy(3, 1));
        // This will also set the visibility of the year menu label.
        drawCalendar(displayedYearMonth);
        yearTextField.requestFocusInWindow();
    }

    /**
     * removeCalendarListener, This removes the specified calendar listener from this CalendarPanel.
     */
    public void removeCalendarListener(CalendarListener listener) {
        calendarListeners.remove(listener);
    }

    /**
     * setSelectedDate, This sets a date that will be marked as "selected" in the calendar, and sets
     * the displayed YearMonth to show that date. If the supplied date is null, then the selected
     * date will be cleared, and the displayed YearMonth will be set to today's year and month.
     *
     * Technical note: This is implemented with the following two functions:
     * setSelectedDateWithoutShowing() and setSelectedYearMonth().
     */
    public void setSelectedDate(LocalDate selectedDate) {
        setSelectedDateWithoutShowing(selectedDate);
        YearMonth yearMonthToShow
            = (selectedDate == null) ? YearMonth.now() : YearMonth.from(selectedDate);
        setDisplayedYearMonth(yearMonthToShow);
    }

    /**
     * setSelectedDateWithoutShowing, This sets a date that will be marked as "selected" in the
     * calendar. The selectedDate will only be visible when the matching YearMonth is being
     * displayed in the calendar. Note that this function does -not- change the currently displayed
     * YearMonth. (This function does not "show" the YearMonth for the supplied date.)
     */
    public void setSelectedDateWithoutShowing(LocalDate selectedDate) {
        // Save the selected date, redraw the calendar, and notify any listeners.
        zInternalChangeSelectedDateProcedure(selectedDate, null);
    }

    /**
     * setDisplayedYearMonth, This sets the year and month that is currently displayed in the
     * calendar. The yearMonth can not be set to null. If the parameter is null, an exception will
     * be thrown. Note that this function does -not- change the displayed selected date.
     */
    public void setDisplayedYearMonth(YearMonth yearMonth) {
        if (yearMonth == null) {
            throw new RuntimeException("CalendarPanel.setDisplayedYearMonth(), "
                + "The displayed year and month cannot be set to null.");
        }
        drawCalendar(yearMonth);
    }

    /**
     * setLocale, The locale for a CalendarPanel should generally be set in the DatePickerSettings.
     * This function only exists to avoid confusion with the swing function Component.setLocale().
     *
     * This forwards any function calls to: CalendarPanel.getSettings().setLocale(). For the
     * complete Javadocs, see DatePickerSettings.setLocale().
     */
    @Override
    public void setLocale(Locale locale) {
        DatePickerSettings currentSettings = getSettings();
        if (currentSettings != null) {
            currentSettings.setLocale(locale);
        }
        super.setLocale(locale);
    }

    /**
     * setSizeOfDatePanelCell, This sets the size of the cell that holds the date panel. By default,
     * the size is set in such a way that the grid layout of the date panel will always touch the
     * inside edges of the cell. At the time of this writing, the cell that contains the date panel
     * is: x=1, y=3, in the calendar panel.
     */
    private void setSizeOfDatePanelCell() {
        // Skip this function if the settings have not been applied.
        if (settings == null) {
            return;
        }
        // Get the minimum desired size of the date panel.
        int minimumHeight = settings.getSizeDatePanelMinimumHeight();
        int minimumWidth = settings.getSizeDatePanelMinimumWidth();
        // Get the layout for the calendar panel.
        GridBagLayout layout = ((GridBagLayout) getLayout());
        // Initialize the minimum height and width of the date panel cell.
        int panelHeight = minimumHeight;
        int panelWidth = minimumWidth;
        // Force the new size to be multiples of 7 for the columns and rows.
        panelHeight += (panelHeight % 7);
        panelWidth += (panelWidth % 7);
        // Set the containing cell to be the desired minimum size.
        layout.rowHeights[3] = panelHeight + constantSizeOfCenterPanelBorders.height;
        layout.columnWidths[1] = panelWidth + constantSizeOfCenterPanelBorders.width;
        // Ask the components to layout and redraw themselves.
        this.doLayout();
        this.validate();
    }

    /**
     * setSizeOfWeekNumberLabels, This sets the minimum and preferred size of the week number
     * labels, to be able to hold largest week number in the current week number label font.
     *
     * Note: The week number labels need to be added to the panel before this method can be called.
     */
    private void setSizeOfWeekNumberLabels() {
        JLabel firstLabel = weekNumberLabels.get(0);
        Font font = firstLabel.getFont();
        FontMetrics fontMetrics = firstLabel.getFontMetrics(font);
        int width = fontMetrics.stringWidth("53 ");
        width += constantWeekNumberLabelInsets.left;
        width += constantWeekNumberLabelInsets.right;
        Dimension size = new Dimension(width, 1);
        for (JLabel currentLabel : weekNumberLabels) {
            currentLabel.setMinimumSize(size);
            currentLabel.setPreferredSize(size);
        }
        topLeftLabel.setMinimumSize(size);
        topLeftLabel.setPreferredSize(size);
    }

    /**
     * setSizeOfMonthYearPanel, This sets the size of the panel at the top of the calendar that
     * holds the month and the year label. The size is calculated from the largest month name (in
     * pixels), that exists in locale and language that is being used by the date picker.
     */
    private void setSizeOfMonthYearPanel() {
        // Skip this function if the settings have not been applied.
        if (settings == null) {
            return;
        }
        // Get the font metrics object.
        Font font = labelMonth.getFont();
        Canvas canvas = new Canvas();
        FontMetrics metrics = canvas.getFontMetrics(font);
        // Calculate the preferred height for the month and year panel.
        int heightNavigationButtons = buttonPreviousYear.getPreferredSize().height;
        int preferredHeightMonthLabel = labelMonth.getPreferredSize().height;
        int preferredHeightYearLabel = labelYear.getPreferredSize().height;
        int monthFontHeight = metrics.getHeight();
        int monthFontHeightWithPadding = monthFontHeight + 2;
        int panelHeight = Math.max(monthFontHeightWithPadding, Math.max(preferredHeightMonthLabel,
            Math.max(preferredHeightYearLabel, heightNavigationButtons)));
        // Get the length of the longest translated month string (in pixels).
        DateFormatSymbols symbols = DateFormatSymbols.getInstance(settings.getLocale());
        String[] allLocalMonths = symbols.getMonths();
        int longestMonthPixels = 0;
        for (String month : allLocalMonths) {
            int monthPixels = metrics.stringWidth(month);
            longestMonthPixels = (monthPixels > longestMonthPixels) ? monthPixels : longestMonthPixels;
        }
        int yearPixels = metrics.stringWidth("_2000");
        // Calculate the size of a box to hold the text with some padding.
        Dimension size = new Dimension(longestMonthPixels + yearPixels + 12, panelHeight);
        // Set the monthAndYearPanel to the appropriate constant size.
        monthAndYearOuterPanel.setMinimumSize(size);
        monthAndYearOuterPanel.setPreferredSize(size);
        // monthAndYearOuterPanel.setMaximumSize(size);
        // Redraw the panel.
        this.doLayout();
        this.validate();
    }

    /**
     * userSelectedADate, This is called any time that the user makes a date selection on the
     * calendar panel, including choosing to clear the date. If this calendar panel is being used
     * inside of a DatePicker, then this will save the selected date and close the calendar. The
     * only time this function will not be called during an exit event, is if the user left focus of
     * the component or pressed escape to cancel choosing a new date.
     */
    private void userSelectedADate(LocalDate selectedDate) {
        // Skip this function if the settings have not been applied.
        if (settings == null) {
            return;
        }
        // If a date was selected and the date is vetoed, do nothing.
        if (selectedDate != null) {
            DateVetoPolicy vetoPolicy = settings.getVetoPolicy();
            if (InternalUtilities.isDateVetoed(vetoPolicy, selectedDate)) {
                return;
            }
        }
        // Save the selected year and month, in case it is needed later.
        YearMonth oldYearMonth = displayedYearMonth;
        if (selectedDate != null) {
            YearMonth selectedDateYearMonth = YearMonth.from(selectedDate);
            displayedYearMonth = selectedDateYearMonth;
        } else {
            // The selected date was cleared, so set the displayed YearMonth to the default value.
            displayedYearMonth = settings.zGetDefaultYearMonthAsUsed();
        }

        // Save the selected date, redraw the calendar, and notify any listeners.
        zInternalChangeSelectedDateProcedure(selectedDate, oldYearMonth);

        // If this calendar panel is associated with a date picker, then set the DatePicker date
        // and close the popup.
        if (settings.getParentDatePicker() != null) {
            DatePicker parent = settings.getParentDatePicker();
            parent.setDate(selectedDate);
            parent.closePopup();
        }
    }

    /**
     * zAddMouseListenersToTodayAndClearButtons, This adds the needed mouse listeners to the today
     * button and the clear button. Any previous mouse listeners will be deleted.
     *
     *
     */
    private void zAddMouseListenersToTodayAndClearButtons() {
        labelSetDateToToday.addMouseListener(new MouseLiberalAdapter() {
            @Override
            public void mouseLiberalClick(MouseEvent e) {
                labelSetDateToTodayMousePressed(e);
            }

            @Override
            public void mouseEnter(MouseEvent e) {
                labelIndicatorMouseEntered(e);
            }

            @Override
            public void mouseExit(MouseEvent e) {
                labelIndicatorMouseExited(e);
            }
        });
        labelClearDate.addMouseListener(new MouseLiberalAdapter() {
            @Override
            public void mouseLiberalClick(MouseEvent e) {
                labelClearDateMousePressed(e);
            }

            @Override
            public void mouseEnter(MouseEvent e) {
                labelIndicatorMouseEntered(e);
            }

            @Override
            public void mouseExit(MouseEvent e) {
                labelIndicatorMouseExited(e);
            }
        });
    }

    /**
     * zInternalChangeSelectedDateProcedure, This should be called whenever we need to change the
     * selected date variable. This will store the supplied selected date and redraw the calendar.
     * If needed, this will notify all calendar listeners that the selected date has been changed.
     * This does not perform any other tasks besides those described here.
     *
     * By intention, this will fire an event even if the user selects the same value twice. This is
     * so that programmers can catch all user interactions of interest to them. Duplicate events can
     * be detected by using the function CalendarSelectionEvent.isDuplicate().
     *
     * Note: Any calendar listeners will be notified of date selection changes before the calendar
     * is redrawn, so that the programmer may optionally update any tightly linked code that may
     * affect this particular redrawing of the calendar. (For example, a programmer might be change
     * a highlight policy based on the currently selected date.)
     */
    private void zInternalChangeSelectedDateProcedure(
        LocalDate newDate, YearMonth oldYearMonthOrNull) {
        LocalDate oldDate = displayedSelectedDate;
        displayedSelectedDate = newDate;
        for (CalendarListener calendarListener : calendarListeners) {
            CalendarSelectionEvent dateSelectionEvent = new CalendarSelectionEvent(
                this, newDate, oldDate);
            calendarListener.selectedDateChanged(dateSelectionEvent);
        }
        drawCalendar(displayedYearMonth, oldYearMonthOrNull);
        // Fire a change event for beans binding.
        firePropertyChange("selectedDate", oldDate, newDate);
    }

    /**
     * zLabelIndicatorsAllSetColorsToDefaultState, This is called to set all label indicators to the
     * state they should have when there is no mouse hovering over them.
     */
    void zSetAllLabelIndicatorColorsToDefaultState() {
        labelIndicatorSetColorsToDefaultState(labelMonth);
        labelIndicatorSetColorsToDefaultState(labelYear);
        labelIndicatorSetColorsToDefaultState(labelSetDateToToday);
        labelIndicatorSetColorsToDefaultState(labelClearDate);
    }

    /**
     * initComponents, This initializes the GUI components in the calendar panel. This function is
     * automatically generated by JFormDesigner. This function should not be modified by hand, it
     * should only be modified from within JFormDesigner.
     */
    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        headerControlsPanel = new JPanel();
        buttonPreviousYear = new JButton();
        buttonPreviousMonth = new JButton();
        monthAndYearOuterPanel = new JPanel();
        monthAndYearInnerPanel = new JPanel();
        labelMonth = new JLabel();
        labelYear = new JLabel();
        buttonNextMonth = new JButton();
        buttonNextYear = new JButton();
        centerPanel = new JPanel();
        footerPanel = new JPanel();
        labelSetDateToToday = new JLabel();
        labelClearDate = new JLabel();
        yearEditorPanel = new JPanel();
        doneEditingYearButton = new JButton();

        //======== this ========
        setLayout(new GridBagLayout());
        ((GridBagLayout) getLayout()).columnWidths = new int[]{5, 0, 5, 0};
        ((GridBagLayout) getLayout()).rowHeights = new int[]{6, 0, 5, 80, 5, 0, 5, 0};
        ((GridBagLayout) getLayout()).columnWeights = new double[]{0.0, 0.0, 0.0, 1.0E-4};
        ((GridBagLayout) getLayout()).rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};

        //======== headerControlsPanel ========
        {
            headerControlsPanel.setLayout(new FormLayout(
                "3*(pref), pref:grow, 3*(pref)",
                "fill:pref:grow"));
            ((FormLayout) headerControlsPanel.getLayout()).setColumnGroups(new int[][]{{1, 2, 6, 7}});

            //---- buttonPreviousYear ----
            buttonPreviousYear.setText("<<");
            buttonPreviousYear.setFocusable(false);
            buttonPreviousYear.setFocusPainted(false);
            buttonPreviousYear.setHorizontalTextPosition(SwingConstants.CENTER);
            buttonPreviousYear.setMargin(new Insets(5, 6, 5, 6));
            buttonPreviousYear.setFont(new Font("Monospaced", Font.BOLD, 12));
            buttonPreviousYear.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    buttonPreviousYearActionPerformed(e);
                }
            });
            headerControlsPanel.add(buttonPreviousYear, CC.xy(1, 1));

            //---- buttonPreviousMonth ----
            buttonPreviousMonth.setText(" < ");
            buttonPreviousMonth.setFocusable(false);
            buttonPreviousMonth.setFocusPainted(false);
            buttonPreviousMonth.setHorizontalTextPosition(SwingConstants.CENTER);
            buttonPreviousMonth.setMargin(new Insets(5, 6, 5, 6));
            buttonPreviousMonth.setFont(new Font("Monospaced", Font.BOLD, 12));
            buttonPreviousMonth.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    buttonPreviousMonthActionPerformed(e);
                }
            });
            headerControlsPanel.add(buttonPreviousMonth, CC.xy(2, 1));

            //======== monthAndYearOuterPanel ========
            {
                monthAndYearOuterPanel.setLayout(new FormLayout(
                    "pref:grow, pref, pref:grow",
                    "fill:pref:grow"));

                //======== monthAndYearInnerPanel ========
                {
                    monthAndYearInnerPanel.setLayout(new FormLayout(
                        "pref, [1px,pref], pref",
                        "fill:pref:grow"));

                    //---- labelMonth ----
                    labelMonth.setText("September");
                    labelMonth.setHorizontalAlignment(SwingConstants.RIGHT);
                    labelMonth.setOpaque(true);
                    labelMonth.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseEntered(MouseEvent e) {
                            labelIndicatorMouseEntered(e);
                        }

                        @Override
                        public void mouseExited(MouseEvent e) {
                            labelIndicatorMouseExited(e);
                        }

                        @Override
                        public void mousePressed(MouseEvent e) {
                            labelMonthIndicatorMousePressed(e);
                        }
                    });
                    monthAndYearInnerPanel.add(labelMonth, CC.xy(1, 1));

                    //---- labelYear ----
                    labelYear.setText("2100");
                    labelYear.setOpaque(true);
                    labelYear.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseEntered(MouseEvent e) {
                            labelIndicatorMouseEntered(e);
                        }

                        @Override
                        public void mouseExited(MouseEvent e) {
                            labelIndicatorMouseExited(e);
                        }

                        @Override
                        public void mousePressed(MouseEvent e) {
                            labelYearIndicatorMousePressed(e);
                        }
                    });
                    monthAndYearInnerPanel.add(labelYear, CC.xy(3, 1));
                }
                monthAndYearOuterPanel.add(monthAndYearInnerPanel, CC.xy(2, 1));
            }
            headerControlsPanel.add(monthAndYearOuterPanel, CC.xy(4, 1));

            //---- buttonNextMonth ----
            buttonNextMonth.setText(" > ");
            buttonNextMonth.setFocusable(false);
            buttonNextMonth.setFocusPainted(false);
            buttonNextMonth.setHorizontalTextPosition(SwingConstants.CENTER);
            buttonNextMonth.setMargin(new Insets(5, 6, 5, 6));
            buttonNextMonth.setFont(new Font("Monospaced", Font.BOLD, 12));
            buttonNextMonth.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    buttonNextMonthActionPerformed(e);
                }
            });
            headerControlsPanel.add(buttonNextMonth, CC.xy(6, 1));

            //---- buttonNextYear ----
            buttonNextYear.setText(">>");
            buttonNextYear.setFocusable(false);
            buttonNextYear.setFocusPainted(false);
            buttonNextYear.setHorizontalTextPosition(SwingConstants.CENTER);
            buttonNextYear.setMargin(new Insets(5, 6, 5, 6));
            buttonNextYear.setFont(new Font("Monospaced", Font.BOLD, 12));
            buttonNextYear.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    buttonNextYearActionPerformed(e);
                }
            });
            headerControlsPanel.add(buttonNextYear, CC.xy(7, 1));
        }
        add(headerControlsPanel, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 0, 0), 0, 0));

        //======== centerPanel ========
        {
            centerPanel.setBackground(new Color(210, 210, 210));
            centerPanel.setLayout(new FormLayout(
                "3*(min), 7*(default:grow), min",
                "fill:min, fill:2px, fill:default:grow, fill:1px, fill:min, 6*(fill:default:grow), fill:min"));
            ((FormLayout) centerPanel.getLayout()).setColumnGroups(new int[][]{{4, 5, 6, 7, 8, 9, 10}});
            ((FormLayout) centerPanel.getLayout()).setRowGroups(new int[][]{{3, 6, 7, 8, 9, 10, 11}});
        }
        add(centerPanel, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 0, 0), 0, 0));

        //======== footerPanel ========
        {
            footerPanel.setLayout(new GridBagLayout());
            ((GridBagLayout) footerPanel.getLayout()).columnWidths = new int[]{6, 0, 0, 0, 6, 0};
            ((GridBagLayout) footerPanel.getLayout()).rowHeights = new int[]{0, 0};
            ((GridBagLayout) footerPanel.getLayout()).columnWeights = new double[]{0.0, 0.0, 1.0, 0.0, 0.0, 1.0E-4};
            ((GridBagLayout) footerPanel.getLayout()).rowWeights = new double[]{1.0, 1.0E-4};

            //---- labelSetDateToToday ----
            labelSetDateToToday.setText("Today: Feb 12, 2016");
            labelSetDateToToday.setHorizontalAlignment(SwingConstants.CENTER);
            labelSetDateToToday.setOpaque(true);
            footerPanel.add(labelSetDateToToday, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 0, 0));

            //---- labelClearDate ----
            labelClearDate.setText("Clear");
            labelClearDate.setOpaque(true);
            footerPanel.add(labelClearDate, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 0, 0));
        }
        add(footerPanel, new GridBagConstraints(1, 5, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 0, 0), 0, 0));

        //======== yearEditorPanel ========
        {
            yearEditorPanel.setLayout(new GridBagLayout());
            ((GridBagLayout) yearEditorPanel.getLayout()).columnWidths = new int[]{40, 1, 26, 0};
            ((GridBagLayout) yearEditorPanel.getLayout()).rowHeights = new int[]{0, 0};
            ((GridBagLayout) yearEditorPanel.getLayout()).columnWeights = new double[]{1.0, 0.0, 0.0, 1.0E-4};
            ((GridBagLayout) yearEditorPanel.getLayout()).rowWeights = new double[]{1.0, 1.0E-4};

            //---- doneEditingYearButton ----
            doneEditingYearButton.setFocusPainted(false);
            doneEditingYearButton.setFocusable(false);
            doneEditingYearButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    doneEditingYearButtonActionPerformed(e);
                }
            });
            yearEditorPanel.add(doneEditingYearButton, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 0, 0));
        }
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    /**
     * zApplyBorderPropertiesInstance, This will apply the supplied border properties instance to
     * this calendar. See the CalendarBorderProperties class for information about the meaning of
     * the border properties instance fields. This function should only be called from the
     * CalendarPanel.zAppliedBorderPropertiesList() function.
     *
     * This function will throw an exception if the border coordinates are outside the valid range
     * (1 to 5), or if the lowerRight point has smaller coordinate values than the upperLeft point.
     */
    private void zApplyBorderPropertiesInstance(CalendarBorderProperties borderProperties) {
        Point ul = borderProperties.upperLeft;
        Point lr = borderProperties.lowerRight;
        Color color = borderProperties.backgroundColor;
        Integer thickness = borderProperties.thicknessInPixels;
        if ((ul == null) || (lr == null) || (ul.x < 1) || (ul.x > 5) || (ul.y < 1) || (ul.y > 5)
            || (lr.x < 1) || (lr.x > 5) || (lr.y < 1) || (lr.y > 5) || (ul.x > lr.x)
            || (ul.y > lr.y)) {
            throw new RuntimeException("CalendarPanel.setBorderProperties(), "
                + "The supplied points cannot be null, and they must have x and y "
                + "coordinates with values in the range of 1 to 5 (inclusive). Additionally, "
                + "the upper left point values must be less than or equal to the lower right "
                + "point values.");
        }
        for (int x = ul.x; x <= lr.x; ++x) {
            for (int y = ul.y; y <= lr.y; ++y) {
                JLabel borderLabel = borderLabels[x][y];
                if (borderLabel == null) {
                    continue;
                }
                if (color != null) {
                    borderLabel.setBackground(color);
                }
                if (thickness != null) {
                    boolean isVisible = (thickness > 0);
                    borderLabel.setVisible(isVisible);
                    Dimension minimumSize = (isVisible)
                        ? new Dimension(thickness, thickness) : new Dimension(1, 1);
                    borderLabel.setMinimumSize(minimumSize);
                }
            }
        }
    }

    /**
     * zApplyBorderPropertiesList, This applies the currently stored border property list to this
     * calendar. The border property list is retrieved from the current date picker settings.
     *
     * Before the Borders properties list is applied, the existing border labels will be cleared.
     * "Clearing" the labels means that the JLabel properties are set as follows: visible = false,
     * color = black, minimumSize = (1,1).
     *
     * Note: If weekNumbersDisplayed is set to false, then the borders located in the week number
     * columns (Columns 1 and 2) will always be set to be hidden.
     */
    void zApplyBorderPropertiesList() {
        // Skip this function if the settings have not been applied.
        if (settings == null) {
            return;
        }
        // Clear the current borders. (Set them to be invisible and black.)
        CalendarBorderProperties clearBordersProperties = new CalendarBorderProperties(
            new Point(1, 1), new Point(5, 5), Color.black, 0);
        zApplyBorderPropertiesInstance(clearBordersProperties);
        // Apply the current borderPropertiesList settings.
        for (CalendarBorderProperties borderProperties : settings.getBorderPropertiesList()) {
            zApplyBorderPropertiesInstance(borderProperties);
        }
        if (!(settings.getWeekNumbersDisplayed())) {
            CalendarBorderProperties hideWeekNumberBorders = new CalendarBorderProperties(
                new Point(1, 1), new Point(2, 5), Color.black, 0);
            zApplyBorderPropertiesInstance(hideWeekNumberBorders);
        }
        drawCalendar();
    }

    /**
     * zApplyVisibilityOfButtons, This sets visibility of button controls for this calendar,
     * according to the current settings.
     */
    void zApplyVisibilityOfButtons() {
        boolean showNextMonth = settings.getVisibleNextMonthButton();
        boolean showNextYear = settings.getVisibleNextYearButton();
        boolean showPreviousMonth = settings.getVisiblePreviousMonthButton();
        boolean showPreviousYear = settings.getVisiblePreviousYearButton();
        boolean showMonthMenu = settings.getVisibleMonthMenuButton();
        boolean showTodayButton = settings.getVisibleTodayButton();

        boolean yearMenuSetting = settings.getVisibleYearMenuButton();
        boolean yearEditorPanelIsDisplayed = (yearEditorPanel.getParent() != null);

        boolean clearButtonSetting = settings.getVisibleClearButton();
        boolean emptyDatesAllowed = settings.getAllowEmptyDates();

        buttonNextMonth.setVisible(showNextMonth);
        buttonNextYear.setVisible(showNextYear);
        buttonPreviousMonth.setVisible(showPreviousMonth);
        buttonPreviousYear.setVisible(showPreviousYear);
        labelMonth.setVisible(showMonthMenu);
        labelSetDateToToday.setVisible(showTodayButton);

        boolean showYearMenu = ((yearMenuSetting) && (!yearEditorPanelIsDisplayed));
        labelYear.setVisible(showYearMenu);

        // Note: I had considered centering the today label in the CalendarPanel whenever the 
        // clear label was hidden. However, it still looks better when it is aligned to the left.
        boolean showClearButton = (clearButtonSetting && emptyDatesAllowed);
        labelClearDate.setVisible(showClearButton);

        boolean showMonthAndYearInnerPanel = (showMonthMenu || showYearMenu
            || yearEditorPanelIsDisplayed);
        boolean showHeaderControlsPanel = (showMonthAndYearInnerPanel
            || showNextMonth || showNextYear || showPreviousMonth || showPreviousYear);
        monthAndYearInnerPanel.setVisible(showMonthAndYearInnerPanel);
        headerControlsPanel.setVisible(showHeaderControlsPanel);

        boolean showFooterPanel = (showTodayButton || showClearButton);
        footerPanel.setVisible(showFooterPanel);
    }

    /**
     * zGetWeekNumberForASevenDayRange, This returns a week number for the specified seven day
     * range, according to the supplied weekFieldRules.
     *
     * If all seven days fall on the same week number, then that week number will be returned.
     *
     * If "requireUnanimousWeekNumber" is true and the supplied range falls across two week numbers,
     * then null is returned.
     *
     * If "requireUnanimousWeekNumber" is false and the supplied range falls across two week
     * numbers, then the "majority rules" system is used for determining the returned week number.
     *
     * The majority rules system means that the most common week number in the seven day range will
     * be returned. For example: If days 1 and 2 are in week 30, and days 3 through 7 are in week
     * 31, then the week number 31 will be returned. There is no possibility of a "tie" week number,
     * because the number of days in the range is seven (an odd number). Additionally, the returned
     * week number will always be the correct week number for a minimum of four days out of the
     * seven day range.
     *
     * To make sure that all seven days in the range -can- fall on the same week number, the caller
     * of the function would need to supply a seven day range that starts on the same "first day of
     * the week" that is used by the supplied weekFieldRules. I don't know if there are any special
     * cases where these matching parameters are supplied, but the result is still not a unanimous
     * week number.
     *
     * The week fields object can be created from a specific locale, (including Locale.ISO if
     * desired), or the week fields can be configured to match any desired week field rules.
     *
     * Examples of creating week field instances: WeekFields weekFields = WeekFields.of(Locale
     * locale); WeekFields weekFields = WeekFields.of(DayOfWeek firstDayOfWeek, int
     * minimalDaysInFirstWeek);
     */
    private Integer zGetWeekNumberForASevenDayRange(LocalDate firstDateInRange,
        WeekFields weekFieldRules, boolean requireUnanimousWeekNumber) {
        // Get the week number for each of the seven days in the range. 
        ArrayList<Integer> weekNumbersList = new ArrayList<Integer>();
        for (int daysIntoTheFuture = 0; daysIntoTheFuture <= 6; ++daysIntoTheFuture) {
            LocalDate currentDateInRange;
            // This try block handles an exception that can occur at LocalDate.MAX
            try {
                currentDateInRange = firstDateInRange.plusDays(daysIntoTheFuture);
                int currentWeekNumber = currentDateInRange.get(weekFieldRules.weekOfWeekBasedYear());
                weekNumbersList.add(currentWeekNumber);
            } catch (Exception ex) {
                return 1;
            }
        }
        // Find out if all the week numbers are the same. 
        // We can check for a unanimous sequence by looking at the first and last number. 
        boolean isUnanimous = (InternalUtilities.areObjectsEqual(
            weekNumbersList.get(0), weekNumbersList.get(6)));
        // If the week numbers are unanimous, then return the unanimous week number. 
        if (isUnanimous) {
            return weekNumbersList.get(0);
        }
        // The week number is not unanimous. 
        // If unanimous week numbers are required, then return null.
        if (requireUnanimousWeekNumber) {
            return null;
        }
        // Otherwise, return the most common week number.
        int mostCommonWeekNumber = InternalUtilities.getMostCommonElementInList(weekNumbersList);
        return mostCommonWeekNumber;
    }

    /**
     * setSettings, This will set the settings instance for this calendar panel. The previous
     * settings will be deleted. Note that calling this function effectively re-initializes the
     * picker component. All aspects of the component will be changed to match the provided
     * settings. Any currently selected date will not be changed by this function.
     */
    public void setSettings(DatePickerSettings datePickerSettings) {
        // If no settings were supplied, then create a default settings instance.
        if (datePickerSettings == null) {
            datePickerSettings = new DatePickerSettings();
        }
        // Save the settings.
        this.settings = datePickerSettings;
        // If this is an independent calendar panel, store the parent calendar panel in the 
        // settings instance.
        if (isIndependentCalendarPanel) {
            settings.zSetParentCalendarPanel(this);
        }

        // If no date is selected, then apply the default displayedYearMonth from settings.
        if (displayedSelectedDate == null) {
            displayedYearMonth = settings.zGetDefaultYearMonthAsUsed();
        }

        // Apply the border properties settings.
        // This is applied regardless of whether the calendar panel was created by a date picker, 
        // or as an independent calendar panel.
        zApplyBorderPropertiesList();
        // Set the label indicators to their default states.
        zSetAllLabelIndicatorColorsToDefaultState();
        // If this is an an independent calendar panel, apply needed settings.
        // At the time of this writing, the "applied needed settings" included:
        // allow empty dates.
        if (isIndependentCalendarPanel) {
            // Note: CalendarPanel.zApplyBorderPropertiesList() is called from the calendar panel 
            // constructor so it will be run both for independent and date picker calendar panels.
            settings.zApplyAllowEmptyDates();
        }
        // Apply the button visibility settings to the buttons.
        zApplyVisibilityOfButtons();
        // Redraw the calendar.
        drawCalendar();
    }

    public JButton getPreviousYearButton() {
        return buttonPreviousYear;
    }

    public JButton getPreviousMonthButton() {
        return buttonPreviousMonth;
    }

    public JButton getNextMonthButton() {
        return buttonNextMonth;
    }

    public JButton getNextYearButton() {
        return buttonNextYear;
    }
}
