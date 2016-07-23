package com.github.lgooddatepicker.tableeditors;

import com.github.lgooddatepicker.components.DatePickerSettings;
import com.github.lgooddatepicker.components.DateTimePicker;
import com.github.lgooddatepicker.components.TimePickerSettings;
import com.github.lgooddatepicker.zinternaltools.InternalUtilities;
import com.privatejgoodies.forms.layout.ConstantSize;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.MouseEvent;
import java.time.LocalDateTime;
import java.util.EventObject;
import javax.swing.AbstractCellEditor;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

/**
 * DateTimeTableEditor, This class is used to add a DateTimePicker to cells (or entire columns) of a
 * Swing JTable or a SwingX JXTable component. This class can be used as a table cell "Editor", or
 * as a "Renderer", or as both editor and renderer. Separate class instances should be used for the
 * editor and the renderer.
 *
 * <code>
 * // Usage example:
 * // Create a table.
 * JTable table = new JTable();
 * //Set up a renderer and editor for the LocalDateTime type.
 * table.setDefaultRenderer(LocalDateTime.class, new DateTimeTableEditor());
 * table.setDefaultEditor(LocalDateTime.class, new DateTimeTableEditor());
 * </code>
 *
 * The DateTimePicker and Settings: The DateTimePicker and picker settings can be accessed with the
 * supplied getter functions. Note that most of the settings for the DateTimePicker are left at
 * their default values, but a few (mostly cosmetic) settings are changed in the DateTimeTableEditor
 * constructor. See the DateTimeTableEditor constructor implementation to learn which default
 * settings were changed.
 *
 * Auto adjust row height: By default, this class will automatically adjust the minimum height of
 * all table rows. This occurs during the first time that any cell with an editor or render is
 * displayed. The row height is only adjusted if the current table row height value is below the
 * minimum value that is needed to display the DateTimePicker component. This auto adjust behavior
 * can be turned off from the constructor.
 *
 */
public class DateTimeTableEditor extends AbstractCellEditor
        implements TableCellEditor, TableCellRenderer {

    /**
     * autoAdjustMinimumTableRowHeight, This indicates whether the minimum table row height (for all
     * rows) should be modified when an editor or render is displayed. The row height is only
     * adjusted if it is below the minimum value needed to display the DateTimePicker component.
     */
    private boolean autoAdjustMinimumTableRowHeight = true;

    /**
     * clickCountToEdit, An integer specifying the number of clicks needed to start editing. Even if
     * clickCountToEdit is defined as zero, it will not initiate until a click occurs.
     */
    public int clickCountToEdit = 1;

    /**
     * matchTableBackgroundColor, This indicates whether this table editor should set the picker
     * text area background color to match the background color of the table. The default value is
     * true.
     */
    private boolean matchTableBackgroundColor = true;

    /**
     * matchTableSelectionBackgroundColor, This indicates whether this table editor should set the
     * picker text area background color to match the background color of the table selection (when
     * selected). The default value is true.
     */
    private boolean matchTableSelectionBackgroundColor = true;

    /**
     * borderFocusedCell, This holds the border that is used when a cell has focus.
     */
    private Border borderFocusedCell;

    /**
     * borderUnfocusedCell, This holds the border that is used when a cell does not have focus.
     */
    private Border borderUnfocusedCell;

    /**
     * dateTimepicker, This holds the DateTimePicker instance.
     */
    private DateTimePicker dateTimePicker;

    /**
     * minimumRowHeight, This holds the minimum row height needed to display the DateTimePicker.
     */
    private int minimumRowHeightInPixels;

    /**
     * Constructor, default.
     */
    public DateTimeTableEditor() {
        this(true, true, true);
    }

    /**
     * Constructor, with options.
     *
     * @param autoAdjustMinimumTableRowHeight Set this to true to have this class automatically
     * adjust the the minimum row height of all rows in the table, the first time that a
     * DateTimeTableEditor is displayed. Set this to false to turn off any row height adjustments.
     * The default value is true.
     */
    public DateTimeTableEditor(boolean autoAdjustMinimumTableRowHeight,
            boolean matchTableBackgroundColor, boolean matchTableSelectionBackgroundColor) {
        // Save the constructor parameters.
        this.autoAdjustMinimumTableRowHeight = autoAdjustMinimumTableRowHeight;
        this.matchTableBackgroundColor = matchTableBackgroundColor;
        this.matchTableSelectionBackgroundColor = matchTableSelectionBackgroundColor;
        // Create the borders that should be used for focused and unfocused cells.
        JLabel exampleDefaultRenderer = (JLabel) new DefaultTableCellRenderer().
                getTableCellRendererComponent(new JTable(), "", true, true, 0, 0);
        borderFocusedCell = exampleDefaultRenderer.getBorder();
        borderUnfocusedCell = new EmptyBorder(1, 1, 1, 1);
        // Create and set up the DateTimePicker.
        dateTimePicker = new DateTimePicker();
        dateTimePicker.timePicker.setEnableArrowKeys(false);
        dateTimePicker.setBorder(borderUnfocusedCell);
        dateTimePicker.setGapSize(2, ConstantSize.PIXEL);
        dateTimePicker.setBackground(Color.white);
        dateTimePicker.datePicker.setBackground(Color.white);
        dateTimePicker.timePicker.setBackground(Color.white);
        dateTimePicker.datePicker.getComponentDateTextField().setBorder(null);
        dateTimePicker.timePicker.getComponentTimeTextField().setBorder(null);
        // Adjust any needed picker settings.
        DatePickerSettings dateSettings = dateTimePicker.datePicker.getSettings();
        dateSettings.setGapBeforeButtonPixels(0);
        dateSettings.setSizeTextFieldMinimumWidthDefaultOverride(false);
        dateSettings.setSizeTextFieldMinimumWidth(20);
        TimePickerSettings timeSettings = dateTimePicker.timePicker.getSettings();
        timeSettings.setGapBeforeButtonPixels(0);
        timeSettings.setSizeTextFieldMinimumWidthDefaultOverride(false);
        timeSettings.setSizeTextFieldMinimumWidth(20);
        // Calculate and store the minimum row height needed to display the DateTimePicker.
        minimumRowHeightInPixels = (dateTimePicker.getPreferredSize().height + 1);
    }

    /**
     * getCellEditorValue, This returns the value contained in the editor. This is required by the
     * CellEditor interface.
     */
    @Override
    public Object getCellEditorValue() {
        return dateTimePicker.getDateTimePermissive();
    }

    /**
     * getDateTimePicker, Returns the DateTimePicker which is used by this class.
     */
    public DateTimePicker getDateTimePicker() {
        return dateTimePicker;
    }

    /**
     * getDatePickerSettings, Returns the DatePicker settings for the DateTimePicker used by this
     * class. These settings can be adjusted as desired by the programmer.
     */
    public DatePickerSettings getDatePickerSettings() {
        return dateTimePicker.datePicker.getSettings();
    }

    /**
     * getTimePickerSettings, Returns the TimePicker settings for the DateTimePicker used by this
     * class. These settings can be adjusted as desired by the programmer.
     */
    public TimePickerSettings getTimePickerSettings() {
        return dateTimePicker.timePicker.getSettings();
    }

    /**
     * getTableCellEditorComponent, this returns the editor that is used for editing the cell. This
     * is required by the TableCellEditor interface.
     *
     * For additional details, see the Javadocs for the function:
     * TableCellEditor.getTableCellEditorComponent().
     */
    @Override
    public Component getTableCellEditorComponent(JTable table, Object value,
            boolean isSelected, int row, int column) {
        // Save the supplied value to the DateTimePicker.
        setCellEditorValue(value);
        // If needed, adjust the minimum row height for the table.
        zAdjustTableRowHeightIfNeeded(table);
        // This fixes a bug where the picker text could "move around" during a table resize event.
        dateTimePicker.datePicker.getComponentDateTextField().setScrollOffset(0);
        dateTimePicker.timePicker.getComponentTimeTextField().setScrollOffset(0);
        // Return the DateTimepicker component.
        return dateTimePicker;
    }

    /**
     * getTableCellRendererComponent, Returns the renderer that is used for drawing the cell. This
     * is required by the TableCellRenderer interface.
     *
     * For additional details, see the Javadocs for the function:
     * TableCellRenderer.getTableCellRendererComponent().
     */
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
        // Save the supplied value to the DateTimePicker.
        setCellEditorValue(value);
        // Draw the appropriate background colors to indicate a selected or unselected state.
        if (isSelected) {
            if (matchTableSelectionBackgroundColor) {
                Color selectionBackground = table.getSelectionBackground();
                dateTimePicker.setBackground(selectionBackground);
                dateTimePicker.datePicker.setBackground(selectionBackground);
                dateTimePicker.timePicker.setBackground(selectionBackground);
                dateTimePicker.datePicker.getComponentDateTextField().setBackground(selectionBackground);
                dateTimePicker.timePicker.getComponentTimeTextField().setBackground(selectionBackground);
            } else {
                dateTimePicker.datePicker.zDrawTextFieldIndicators();
                dateTimePicker.timePicker.zDrawTextFieldIndicators();
            }
        }
        if (!isSelected) {
            if (matchTableBackgroundColor) {
                Color tableBackground = table.getBackground();
                dateTimePicker.setBackground(tableBackground);
                dateTimePicker.datePicker.setBackground(tableBackground);
                dateTimePicker.timePicker.setBackground(tableBackground);
                dateTimePicker.datePicker.getComponentDateTextField().setBackground(tableBackground);
                dateTimePicker.timePicker.getComponentTimeTextField().setBackground(tableBackground);
            } else {
                dateTimePicker.datePicker.zDrawTextFieldIndicators();
                dateTimePicker.timePicker.zDrawTextFieldIndicators();
            }
        }
        // Draw the appropriate borders to indicate a focused or unfocused state.
        if (hasFocus) {
            dateTimePicker.setBorder(borderFocusedCell);
        } else {
            dateTimePicker.setBorder(borderUnfocusedCell);
        }
        // If needed, adjust the minimum row height for the table.
        zAdjustTableRowHeightIfNeeded(table);
        // This fixes a bug where the picker text could "move around" during a table resize event.
        dateTimePicker.datePicker.getComponentDateTextField().setScrollOffset(0);
        dateTimePicker.timePicker.getComponentTimeTextField().setScrollOffset(0);
        // Return the DateTimePicker component.
        return dateTimePicker;
    }

    /**
     * isCellEditable, Returns true if anEvent is not a MouseEvent. Otherwise, it returns true if
     * the necessary number of clicks have occurred, and returns false otherwise.
     */
    @Override
    public boolean isCellEditable(EventObject anEvent) {
        if (anEvent instanceof MouseEvent) {
            return ((MouseEvent) anEvent).getClickCount() >= clickCountToEdit;
        }
        return true;
    }

    /**
     * setCellEditorValue, This sets the picker to an appropriate value for the supplied object. If
     * the value is null, then picker will be cleared. If the value is a LocalDateTime instance,
     * then the picker will be set to that value. All other types (including strings) will be read
     * or converted to a string with a maximum length of the first 100 characters. The date picker
     * text will be set with the resulting string.
     */
    public void setCellEditorValue(Object value) {
        dateTimePicker.clear();
        if (value == null) {
            return;
        }
        if (value instanceof LocalDateTime) {
            LocalDateTime nativeValue = (LocalDateTime) value;
            dateTimePicker.setDateTimePermissive(nativeValue);
        } else {
            String text = value.toString();
            String shorterText = InternalUtilities.safeSubstring(text, 0, 100);
            dateTimePicker.datePicker.setText(shorterText);
        }
    }

    /**
     * zAdjustTableRowHeightIfNeeded, If needed, this will adjust the row height for all rows in the
     * supplied table to fit the minimum row height that is needed to display the DateTimePicker
     * component.
     *
     * If autoAdjustMinimumTableRowHeight is false, this will do nothing. If the row height of the
     * table is already greater than or equal to "minimumRowHeightInPixels", then this will do
     * nothing.
     */
    private void zAdjustTableRowHeightIfNeeded(JTable table) {
        if (!autoAdjustMinimumTableRowHeight) {
            return;
        }
        if ((table.getRowHeight() < minimumRowHeightInPixels)) {
            table.setRowHeight(minimumRowHeightInPixels);
        }
    }

}
