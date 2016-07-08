package com.github.lgooddatepicker.demo;

import com.github.lgooddatepicker.tableeditors.DateTableEditor;
import com.github.lgooddatepicker.tableeditors.DateTimeTableEditor;
import com.github.lgooddatepicker.tableeditors.TimeTableEditor;
import com.github.lgooddatepicker.zinternaltools.InternalUtilities;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

/**
 * TableEditorsDemo, This class demonstrates how to use the picker classes inside of the cells of a
 * JTable. (Note: The table editor classes can also work with a "SwingX" JXTable.)
 *
 * <code>
 * // Here is a "quick reference" example, for using the DatePicker table editor.
 * // Create a table.
 * JTable table = new JTable(new DemoTableModel());
 *
 * // Add the DateTableEditor as the default editor and renderer for the LocalDate data type.
 * table.setDefaultEditor(LocalDate.class, new DateTableEditor());
 * table.setDefaultRenderer(LocalDate.class, new DateTableEditor());
 *
 * // Explicitly set the default editor and renderer for column index 0.
 * // It's best to explicitly set the editors and renderers for the table columns, rather than
 * // letting the JTable class decide which columns should have which editors and renders.
 * TableColumn column = table.getColumnModel().getColumn(0);
 * column.setCellEditor(table.getDefaultEditor(LocalDate.class));
 * column.setCellRenderer(table.getDefaultRenderer(LocalDate.class));
 *
 * // That's it.
 * // The cells in column 0 will now use a date picker for editing and rendering values.
 * </code>
 */
public class TableEditorsDemo extends JPanel {

    /**
     * Constructor, Set up the table instance, and add the table editors to the table.
     */
    public TableEditorsDemo() {
        // Use a grid layout for the panel.
        super(new GridLayout(1, 0));

        // This decides how many clicks are required to edit a cell in the table editors demo.
        // (Set this to 1 or 2 clicks, as desired.)
        int clickCountToEdit = 1;

        // Create the table and the scroll pane.
        JTable table = new JTable(new DemoTableModel());
        JScrollPane scrollPane = new JScrollPane(table);
        this.add(scrollPane);

        // Set a background color for the table. Note that by default, the picker table editors 
        // will match the table background color. This is expected behavior for table editors.
        // Color matching can be turned off if desired, from the table editor class constructors.
        table.setBackground(new Color(190, 240, 255));

        // Set all the default table editors to start with the desired number of clicks.
        // The default table editors are the ones supplied by the JTable class.
        InternalUtilities.setDefaultTableEditorsClicks(table, clickCountToEdit);

        // Add table renderers and editors for the LocalDate, LocalTime, and LocalDateTime types.
        //
        // Note: The editors and renders for each type should be separate instances of the 
        // matching table editor class. Don't use the same instance as both a renderer and editor.
        // If you did, it would be immediately obvious because the cells would not render properly.
        table.setDefaultRenderer(LocalDate.class, new DateTableEditor());
        DateTableEditor dateEdit = new DateTableEditor();
        dateEdit.clickCountToEdit = clickCountToEdit;
        table.setDefaultEditor(LocalDate.class, dateEdit);

        table.setDefaultRenderer(LocalTime.class, new TimeTableEditor());
        TimeTableEditor timeEdit = new TimeTableEditor();
        timeEdit.clickCountToEdit = clickCountToEdit;
        table.setDefaultEditor(LocalTime.class, timeEdit);

        table.setDefaultRenderer(LocalDateTime.class, new DateTimeTableEditor());
        DateTimeTableEditor dateTimeEdit = new DateTimeTableEditor();
        dateTimeEdit.clickCountToEdit = clickCountToEdit;
        table.setDefaultEditor(LocalDateTime.class, dateTimeEdit);

        // Explicitly set the default editor instance (data type) for each column, by looking at 
        // the most common data type found in each column.
        zSetAllColumnEditorsAndRenderers(table);

        // Set the width of the DateTime column to be a bit bigger than the rest.
        table.getColumnModel().getColumn(DemoTableModel.dateTimeColumnIndex).setPreferredWidth(180);
    }

    /**
     * createAndShowTableDemoFrame, This creates and displays a frame with the table demo.
     */
    public static void createAndShowTableDemoFrame() {
        // Create and set up the frame and the table demo panel.
        JFrame frame = new JFrame("LGoodDatePicker Table Editors Demo "
                + InternalUtilities.getProjectVersionString());
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        TableEditorsDemo tableDemoPanel = new TableEditorsDemo();
        frame.setContentPane(tableDemoPanel);
        tableDemoPanel.setOpaque(true);

        //Display the frame.
        frame.pack();
        frame.setSize(new Dimension(1000, 700));
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    /**
     * main, The table demo can be run independently from this main method, or the table demo can be
     * run by pressing the matching button in the "FullDemo" program.
     */
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                createAndShowTableDemoFrame();
            }
        });
    }

    /**
     * zSetAllColumnEditorsAndRenderers,
     *
     * Note: This function is a bit complex, and it's not necessary to understand the source of this
     * function to understand this demo. The most important sections of the demo are located in the
     * constructor and the DemoTableModel class. The reader may wish to skip reading this function
     * until the other parts of the demo are read.
     *
     * This sets the default editors and renderers for each column, by looking at the most common
     * data types found in the columns. Before this function is called, any needed custom editors
     * and renderers should already be added to the table instance for the matching data types. This
     * can be done using the functions "table.setDefaultRenderer()" and "table.setDefaultEditor()".
     *
     * The values in the table columns will be examined to determine which editor should be used for
     * each column. By default, the standard JTable class will set the default column editors and
     * renders using only the data in the first row. In this case, the wrong editor can be assigned
     * if the first row happens to contain a null value (or contains an unexpected value). In
     * contrast, this function can examine any desired number of rows to find non-null values, until
     * a chosen sample size is reached.
     *
     * By default, this function will read the first 30 starting rows to find non-null values. After
     * the starting rows are read, this function will sample up to 70 of the remaining rows ("bulk"
     * rows) in the table at regularly spaced intervals. The bulk rows will sampled from all the
     * remaining rows in the table.
     *
     * Up to the first 21 non-null values ("maxFoundSamplesToExamine") that are encountered will
     * determine which editor will be used for a particular column. The data type that is used will
     * be whichever data type is most frequently encountered within those values. If no non-null
     * values are found in the sampled rows, then the "generic editor" (the string editor) will be
     * used for that column.
     */
    private void zSetAllColumnEditorsAndRenderers(JTable table) {
        // These variables decide how many samples to look at in each column.
        int maxStartRowsToRead = 30;
        int maxBulkRowsToRead = 70;
        int maxFoundSamplesToExamine = 21;
        // Gather some variables that we will need..
        TableModel model = table.getModel();
        int columnCount = model.getColumnCount();
        int rowCount = model.getRowCount();
        // Do nothing if the table is empty.
        if (columnCount < 1 || rowCount < 1) {
            return;
        }
        // Calculate the increment for looping through the bulk rows.
        int bulkRowIncrement = Math.max(1, (rowCount / maxBulkRowsToRead));
        // Loop through all the columns.
        columnLoop:
        for (int columnIndex = 0; columnIndex < columnCount; ++columnIndex) {
            TableColumn column = table.getColumnModel().getColumn(columnIndex);
            ArrayList<Class> nonNullTypes = new ArrayList<Class>();
            // Loop through all the rows that should be sampled.
            rowLoop:
            for (int rowIndex = 0; (rowIndex < rowCount);
                    rowIndex += ((rowIndex < maxStartRowsToRead) ? 1 : bulkRowIncrement)) {
                // Get the value in each row.
                Object value = model.getValueAt(rowIndex, columnIndex);
                if (value == null) {
                    continue;
                }
                // Save any found non-null types.
                nonNullTypes.add(value.getClass());
                // If we have already found "maxFoundSamplesToExamine" types, then use those 
                // samples to determine the column type.
                if (nonNullTypes.size() >= maxFoundSamplesToExamine) {
                    Class mostCommonType = InternalUtilities.getMostCommonElementInList(nonNullTypes);
                    column.setCellRenderer(table.getDefaultRenderer(mostCommonType));
                    column.setCellEditor(table.getDefaultEditor(mostCommonType));
                    continue columnLoop;
                }
            } // End: rowLoop
            // There are no more rows to examine.
            // If we found any non-null types at all, then use those to choose the column type.
            if (nonNullTypes.size() > 0) {
                Class mostCommonType = InternalUtilities.getMostCommonElementInList(nonNullTypes);
                column.setCellRenderer(table.getDefaultRenderer(mostCommonType));
                column.setCellEditor(table.getDefaultEditor(mostCommonType));
            } else {
                // When no types are found in a column, we will use the generic editor.
                column.setCellRenderer(table.getDefaultRenderer(Object.class));
                column.setCellEditor(table.getDefaultEditor(Object.class));
            }
        } // End: columnLoop
    }

    /**
     * DemoTableModel, This model supplies the table data for the demo.
     */
    static class DemoTableModel extends AbstractTableModel {

        /**
         * columnNames, These are the names of the table columns.
         */
        private String[] columnNames = {"Date", "Time", "DateTime", "String", "Integer", "Boolean"};

        /**
         * dateTimeColumnIndex, This specifies the index of the date time column.
         */
        static public int dateTimeColumnIndex = 2;

        /**
         * data, This is the initial data that is contained in the table cells.
         */
        private Object[][] data = {
            {LocalDate.now(), LocalTime.now(), LocalDateTime.now(), "Jane", 5, false},
            {LocalDate.now().plusDays(1), LocalTime.now().plusHours(1), LocalDateTime.now(), "Alison", 3, true},
            {LocalDate.now().plusDays(2), LocalTime.now().plusHours(2), LocalDateTime.now(), "Kathy", -2, false},
            {LocalDate.now().plusDays(3), LocalTime.now().plusHours(3), LocalDateTime.now(), "Sharon", 2, true},
            {LocalDate.now().plusDays(4), LocalTime.now().plusHours(4), LocalDateTime.now(), "Philip", 10, false},
            {"randomtext", "randomtext", "randomtext", "randomtext", "randomtext", false},
            {null, null, null, null, null, null}
        };

        /**
         * getColumnCount, This returns the number of columns in the table.
         */
        @Override
        public int getColumnCount() {
            return columnNames.length;
        }

        /**
         * getRowCount, This returns the number of rows in the table.
         */
        @Override
        public int getRowCount() {
            return data.length;
        }

        /**
         * getColumnName, This returns the name of the column for the supplied column index.
         */
        @Override
        public String getColumnName(int columnIndex) {
            return columnNames[columnIndex];
        }

        /**
         * getValueAt, This returns the current value of the specified cell.
         */
        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            return data[rowIndex][columnIndex];
        }

        /**
         * getColumnClass, This function is required by the TableModel interface, but we will be
         * using a different method to determine the specific data type of each column. See also:
         * zSetColumnEditors().
         */
        @Override
        public Class getColumnClass(int columnIndex) {
            return Object.class;
        }

        /**
         * isCellEditable, This determines if any particular cell is editable
         */
        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return true;
        }

        /**
         * setValueAt, This sets the value of the specified cell.
         */
        @Override
        public void setValueAt(Object value, int rowIndex, int columnIndex) {
            data[rowIndex][columnIndex] = value;
            fireTableCellUpdated(rowIndex, columnIndex);
        }
    }

}
