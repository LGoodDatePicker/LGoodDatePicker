package com.github.lgooddatepicker.ysandbox;

import com.privatejgoodies.forms.layout.FormLayout;
import com.privatejgoodies.forms.factories.CC;
import java.awt.Color;
import java.awt.Font;
import javax.swing.*;

public class ComboBoxTesting extends JPanel {

    public ComboBoxTesting() {
        initComponents();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        textField1 = new JTextField();
        textField2 = new JTextField();
        comboBox1 = new JComboBox<>();
        spinner1 = new JSpinner();

        //======== this ========
        setLayout(new FormLayout(
                "default:grow, $lcgap, default, $lcgap, default:grow, 2*($lcgap, default)",
                "default:grow, $lgap, default, $lgap, default:grow, $lgap, default"));

        //---- textField1 ----
        textField1.setEditable(false);
        textField1.setBackground(Color.white);
        textField1.setText("ddttddt");
        add(textField1, CC.xy(3, 1));

        //---- textField2 ----
        textField2.setEnabled(false);
        textField2.setText("ssssssss__--***###");
        textField2.setFont(new Font("Segoe UI", Font.BOLD, 18));
        add(textField2, CC.xy(5, 1));

        //---- comboBox1 ----
        comboBox1.setModel(new DefaultComboBoxModel<>(new String[]{
            "beeef123",
            "2",
            "3",
            "4",
            "5",
            "6",
            "7",
            "8",
            "9",
            "0",
            "4",
            "4",
            "3",
            "3",
            "2",
            "2",
            "2",
            "2",
            "2",
            "2",
            "22",
            "2",
            "2",
            "2",
            "2",
            "2"
        }));
        comboBox1.setSelectedIndex(-1);
        add(comboBox1, CC.xy(3, 3));
        add(spinner1, CC.xy(5, 7));
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JTextField textField1;
    private JTextField textField2;
    private JComboBox<String> comboBox1;
    private JSpinner spinner1;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
