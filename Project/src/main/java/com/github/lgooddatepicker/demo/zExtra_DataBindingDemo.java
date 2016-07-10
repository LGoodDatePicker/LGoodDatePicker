package com.github.lgooddatepicker.demo;

/**
 * zExtraDemos_DataBindingDemo, This is a demonstration of how JavaBeans data binding can be used
 * with the date and time picker components.
 *
 * Usage notes:
 *
 * This demo requires "beansbinding-1.2.1.jar" (org.jdesktop.beansbinding). BeansBinding is open
 * source and not part of the standard Java distribution. To avoid having to add the beansbinding
 * dependency to the entire LGoodDatePicker library, the body of this demo class is commented out.
 * To run the demo, you would need to uncomment the class first.
 *
 * If you are building the LGoodDatePicker library yourself, you could uncomment the "BeansBinding"
 * dependency section in the pom file, which would automatically include the needed dependency.
 *
 * This demo was created with the NetBeans visual form designer, and should probably be viewed from
 * the visual designer instead of reading the source code.
 *
 * If you are using NetBeans, you can open this class in "design mode". If you click on any
 * component in design mode, and look at its properties, you will see a "binding" tab under the
 * properties. The binding tab allows you to set up or change any automatic data binding between the
 * components.
 */
public class zExtra_DataBindingDemo extends javax.swing.JFrame {

    /* 
    // Uncomment the class to run the beansbinding demo. 

    public zExtra_DataBindingDemo() {
        initComponents();
        getContentPane().setBackground(new java.awt.Color(153, 204, 255));
        setTitle("LGoodDatePicker Data Binding Demo "
                + com.github.lgooddatepicker.zinternaltools.InternalUtilities.getProjectVersionString());
        setLocationRelativeTo(null);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        bindingGroup = new org.jdesktop.beansbinding.BindingGroup();

        calendarPanel_r4_1 = new com.github.lgooddatepicker.components.CalendarPanel();
        timePicker_r2_1 = new com.github.lgooddatepicker.components.TimePicker();
        dateTimePicker_r3_1 = new com.github.lgooddatepicker.components.DateTimePicker();
        datePicker_r1_1 = new com.github.lgooddatepicker.components.DatePicker();
        jTextField_r1_1 = new javax.swing.JTextField();
        datePicker_r1_2 = new com.github.lgooddatepicker.components.DatePicker();
        timePicker_r2_2 = new com.github.lgooddatepicker.components.TimePicker();
        jTextField_r2_1 = new javax.swing.JTextField();
        dateTimePicker_r3_2 = new com.github.lgooddatepicker.components.DateTimePicker();
        calendarPanel_r4_2 = new com.github.lgooddatepicker.components.CalendarPanel();
        datePicker_r4_1 = new com.github.lgooddatepicker.components.DatePicker();
        jTextField_r3_1 = new javax.swing.JTextField();
        jTextField_r3_2 = new javax.swing.JTextField();
        jLabelRow2 = new javax.swing.JLabel();
        jLabelRow1 = new javax.swing.JLabel();
        jLabelRow3 = new javax.swing.JLabel();
        jLabelRow4 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, datePicker_r1_1, org.jdesktop.beansbinding.ELProperty.create("${text}"), jTextField_r1_1, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        jTextField_r1_1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField_r1_1ActionPerformed(evt);
            }
        });

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, datePicker_r1_1, org.jdesktop.beansbinding.ELProperty.create("${date}"), datePicker_r1_2, org.jdesktop.beansbinding.BeanProperty.create("date"));
        bindingGroup.addBinding(binding);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, timePicker_r2_1, org.jdesktop.beansbinding.ELProperty.create("${time}"), timePicker_r2_2, org.jdesktop.beansbinding.BeanProperty.create("time"));
        bindingGroup.addBinding(binding);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, timePicker_r2_1, org.jdesktop.beansbinding.ELProperty.create("${text}"), jTextField_r2_1, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, dateTimePicker_r3_1, org.jdesktop.beansbinding.ELProperty.create("${dateTimePermissive}"), dateTimePicker_r3_2, org.jdesktop.beansbinding.BeanProperty.create("dateTimeStrict"));
        bindingGroup.addBinding(binding);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, calendarPanel_r4_1, org.jdesktop.beansbinding.ELProperty.create("${selectedDate}"), calendarPanel_r4_2, org.jdesktop.beansbinding.BeanProperty.create("selectedDate"));
        bindingGroup.addBinding(binding);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, calendarPanel_r4_1, org.jdesktop.beansbinding.ELProperty.create("${selectedDate}"), datePicker_r4_1, org.jdesktop.beansbinding.BeanProperty.create("date"));
        bindingGroup.addBinding(binding);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, dateTimePicker_r3_1, org.jdesktop.beansbinding.ELProperty.create("${datePicker.text}"), jTextField_r3_1, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, dateTimePicker_r3_1, org.jdesktop.beansbinding.ELProperty.create("${timePicker.text}"), jTextField_r3_2, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        jLabelRow2.setText("TimePicker, bound row 2:");

        jLabelRow1.setText("DatePicker, bound row 1:");

        jLabelRow3.setText("<html>DateTimePicker, bound row 3:<br/>(Fill both the Date and Time)</html>");

        jLabelRow4.setText("CalendarPanel, bound row 4:");
        jLabelRow4.setToolTipText("");

        jLabel1.setText("<html><b>The date and time pickers (and text fields) in each row are bound together with JavaBeans data binding.<br/>If you change the value of any one component, you will change the value for all the components in the row.</b></html>");
        jLabel1.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 204)), javax.swing.BorderFactory.createEmptyBorder(3, 5, 3, 5)));
        jLabel1.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabelRow2)
                        .addGap(18, 18, 18)
                        .addComponent(timePicker_r2_1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(timePicker_r2_2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jTextField_r2_1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabelRow3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(dateTimePicker_r3_1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(dateTimePicker_r3_2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jTextField_r3_1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jTextField_r3_2, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabelRow4)
                        .addGap(18, 18, 18)
                        .addComponent(calendarPanel_r4_1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(calendarPanel_r4_2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(datePicker_r4_1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabelRow1)
                        .addGap(18, 18, 18)
                        .addComponent(datePicker_r1_1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(datePicker_r1_2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jTextField_r1_1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelRow1)
                    .addComponent(datePicker_r1_1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField_r1_1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(datePicker_r1_2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelRow2)
                    .addComponent(timePicker_r2_1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(timePicker_r2_2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField_r2_1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelRow3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dateTimePicker_r3_1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dateTimePicker_r3_2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jTextField_r3_1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField_r3_2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelRow4)
                    .addComponent(datePicker_r4_1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(calendarPanel_r4_1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(calendarPanel_r4_2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(76, Short.MAX_VALUE))
        );

        bindingGroup.bind();

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField_r1_1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField_r1_1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField_r1_1ActionPerformed

    // main.    
    public static void main(String args[]) {
        // Create and display the form.
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new zExtra_DataBindingDemo().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.github.lgooddatepicker.components.CalendarPanel calendarPanel_r4_1;
    private com.github.lgooddatepicker.components.CalendarPanel calendarPanel_r4_2;
    private com.github.lgooddatepicker.components.DatePicker datePicker_r1_1;
    private com.github.lgooddatepicker.components.DatePicker datePicker_r1_2;
    private com.github.lgooddatepicker.components.DatePicker datePicker_r4_1;
    private com.github.lgooddatepicker.components.DateTimePicker dateTimePicker_r3_1;
    private com.github.lgooddatepicker.components.DateTimePicker dateTimePicker_r3_2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabelRow1;
    private javax.swing.JLabel jLabelRow2;
    private javax.swing.JLabel jLabelRow3;
    private javax.swing.JLabel jLabelRow4;
    private javax.swing.JTextField jTextField_r1_1;
    private javax.swing.JTextField jTextField_r2_1;
    private javax.swing.JTextField jTextField_r3_1;
    private javax.swing.JTextField jTextField_r3_2;
    private com.github.lgooddatepicker.components.TimePicker timePicker_r2_1;
    private com.github.lgooddatepicker.components.TimePicker timePicker_r2_2;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables

    
    // Uncomment the class to run the beansbinding demo. 
     */
}
