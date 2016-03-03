package com.lgooddatepicker.timepickerunderdevelopment;

import java.awt.*;
import javax.swing.*;

public class TimePicker extends JPanel {
    
    private TimePickerSettings timePickerSettings;
    
    
    
    public TimePicker() {
        this(new TimePickerSettings());
    }
    

    public TimePicker(TimePickerSettings timePickerSettings) {
        this.timePickerSettings = timePickerSettings;
        initComponents();
    }

    private void zPopulateDropDownList() {
        
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		timeComboBox = new JComboBox<>();

		//======== this ========
		setLayout(new GridBagLayout());
		((GridBagLayout)getLayout()).columnWidths = new int[] {80, 0};
		((GridBagLayout)getLayout()).rowHeights = new int[] {0, 0};
		((GridBagLayout)getLayout()).columnWeights = new double[] {1.0, 1.0E-4};
		((GridBagLayout)getLayout()).rowWeights = new double[] {1.0, 1.0E-4};

		//---- timeComboBox ----
		timeComboBox.setEditable(true);
		timeComboBox.setModel(new DefaultComboBoxModel<>(new String[] {
			"12:00am",
			"12:30am"
		}));
		add(timeComboBox, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
			GridBagConstraints.CENTER, GridBagConstraints.BOTH,
			new Insets(0, 0, 0, 0), 0, 0));
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	private JComboBox<String> timeComboBox;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
