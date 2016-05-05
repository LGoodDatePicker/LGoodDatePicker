package com.github.lgooddatepicker.zinternaltools;

import java.awt.GridBagLayout;
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

/**
 * JIntegerTextField,
 *
 * This implements a text field where it is only possible to type numbers into the field. The field
 * will contain a valid integer at all times. The component has methods to get or set the value of
 * the field as an integer, which will never throw a parsing exception. This component does not
 * allow the field to be empty. The range of allowed numbers can be set by the programmer, within
 * limits that are described below.
 *
 * The default range for this component is (Integer.MIN_VALUE to Integer.MAX_VALUE). You may
 * optionally set a different minimum and maximum value for the number, within certain limits.
 * Specifically, the chosen range must include all of the single-digit numbers from 1 through 9. For
 * details on why this requirement is necessary for this component, see the "Single Digit
 * Requirement Notes" below. If your usage requires a range outside of these specifications, then a
 * JSpinner might be a possible alternative to consider.
 *
 * Single Digit Requirement Notes: The minimum and maximum values for this component must include
 * the numbers 1 through 9, because otherwise this component would require a "commit or revert" type
 * of functionality to handle all cases. For example, imagine the field is blank, the minimum value
 * is 100, and a user types a "5". A JSpinner handles this situation by implementing a focus
 * listener, and "reverting" to the minimum value of 100 if the component loses focus while it is in
 * an invalid state. This component prevents the invalid state from being created in the first
 * place. To summarize, allowing invalid states (or commit and revert) is outside the intended scope
 * of this component.
 */
public class JIntegerTextField extends JTextField {

    private int maximumValue = Integer.MAX_VALUE;
    private int minimumValue = Integer.MIN_VALUE;
    public IntegerTextFieldNumberChangeListener numberChangeListener = null;
    public boolean skipNotificationOfNumberChangeListenerWhileTrue = false;

    public JIntegerTextField() {
        this(10);
    }

    public JIntegerTextField(int preferredWidthFromColumnCount) {
        super(preferredWidthFromColumnCount);
        setText("" + getDefaultValue());
        selectAll();
        AbstractDocument document = (AbstractDocument) this.getDocument();
        document.setDocumentFilter(new IntegerFilter(this));
        getDocument().addDocumentListener(new NumberListener());
    }

    private boolean allowNegativeNumbers() {
        return (minimumValue < 0);
    }

    public int getDefaultValue() {
        return (minimumValue > 0) ? 1 : 0;
    }

    public int getMaximumValue() {
        return maximumValue;
    }

    public int getMinimumValue() {
        return minimumValue;
    }

    public int getValue() {
        String text = getText();
        if (text == null || text.isEmpty()) {
            return 0;
        }
        int number;
        try {
            number = Integer.parseInt(text);
        } catch (Exception e) {
            throw new RuntimeException("JIntegerTextField.getValue(), "
                    + "The text value could not be parsed. This should never happen.");
        }
        return number;
    }

    public static void main(String[] args) {
        final JIntegerTextField integerTextField = new JIntegerTextField();
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                integerTextField.runDemo();
            }
        });
        // SwingUtilities.invokeLater(integerTextField::runDemo);
    }

    private void notifyListenerIfNeeded() {
        if (skipNotificationOfNumberChangeListenerWhileTrue) {
            return;
        }
        if (numberChangeListener != null) {
            Integer integer = getValidIntegerOrNull(getText());
            if (integer != null) {
                numberChangeListener.integerTextFieldNumberChanged(this, integer);
            }
        }
    }

    private void runDemo() {
        JFrame frame = new JFrame();
        frame.setLayout(new GridBagLayout());
        frame.setSize(300, 300);
        frame.add(this);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationByPlatform(true);
        frame.setVisible(true);
    }

    public void setMaximumValue(int maximumValue) {
        this.maximumValue = (maximumValue >= 9) ? maximumValue : 9;
    }

    public void setMinimumValue(int minimumValue) {
        this.minimumValue = (minimumValue <= 1) ? minimumValue : 1;
    }

    public void setValue(int value) {
        value = (value < minimumValue) ? minimumValue : value;
        value = (value > maximumValue) ? maximumValue : value;
        setText("" + value);
    }

    private boolean isValidInteger(String text) {
        return (getValidIntegerOrNull(text) != null);
    }

    private Integer getValidIntegerOrNull(String text) {
        int number;
        try {
            number = Integer.parseInt(text);
        } catch (NumberFormatException e) {
            return null;
        }
        if (number < minimumValue || number > maximumValue) {
            return null;
        }
        if (!allowNegativeNumbers() && text.contains("-")) {
            return null;
        }
        return number;
    }

    private class NumberListener implements DocumentListener {

        @Override
        public void insertUpdate(DocumentEvent e) {
            notifyListenerIfNeeded();
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            notifyListenerIfNeeded();
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            notifyListenerIfNeeded();
        }

    }

    private class IntegerFilter extends DocumentFilter {

        public IntegerFilter(JIntegerTextField parentField) {
            if (parentField == null) {
                throw new RuntimeException("IntegerTextField.IntegerFilter, "
                        + "The parent text field cannot be null.");
            }
            this.parentField = parentField;
        }
        private JIntegerTextField parentField;
        private boolean skipFiltersWhileTrue = false;

        @Override
        public void remove(DocumentFilter.FilterBypass fb, int offset, int length)
                throws BadLocationException {
            if (skipFiltersWhileTrue) {
                super.remove(fb, offset, length);
                return;
            }
            String oldText = fb.getDocument().getText(0, fb.getDocument().getLength());
            StringBuilder newTextBuilder = new StringBuilder(oldText);
            newTextBuilder.delete(offset, (offset + length));
            String newText = newTextBuilder.toString();
            if (newText.trim().isEmpty() || oldText.equals("-1")) {
                setFieldToDefaultValue();
            } else if (allowNegativeNumbers() && newText.trim().equals("-")) {
                setFieldToNegativeOne();
            } else if (isValidInteger(newText)) {
                super.remove(fb, offset, length);
            } else {
                Toolkit.getDefaultToolkit().beep();
            }
        }

        @Override
        public void replace(FilterBypass fb, int offset, int length, String newChars, AttributeSet a)
                throws BadLocationException {
            if (skipFiltersWhileTrue) {
                super.replace(fb, offset, length, newChars, a);
                return;
            }
            int oldTextLength = fb.getDocument().getLength();
            String oldText = fb.getDocument().getText(0, oldTextLength);
            StringBuilder newTextBuilder = new StringBuilder(oldText);
            newTextBuilder.delete(offset, (offset + length));
            newTextBuilder.insert(offset, newChars);
            String newText = newTextBuilder.toString();
            if (newText.trim().isEmpty()) {
                setFieldToDefaultValue();
            } else if (allowNegativeNumbers() && newText.trim().equals("-")) {
                setFieldToNegativeOne();
            } else if (length == oldTextLength && isValidInteger(newText.trim())) {
                // If the entire document is being replaced, allow a trimmed replacement of 
                // integers that originally included surrounding whitespace.
                // (This makes it easier to paste a number from the clipboard.)
                super.replace(fb, 0, length, newText.trim(), a);
            } else if (isValidInteger(newText)) {
                super.replace(fb, offset, length, newChars, a);
            } else {
                Toolkit.getDefaultToolkit().beep();
            }
        }

        @Override
        public void insertString(FilterBypass fb, int offset, String newChars,
                AttributeSet a) throws BadLocationException {
            if (skipFiltersWhileTrue) {
                super.insertString(fb, offset, newChars, a);
                return;
            }
            String oldText = fb.getDocument().getText(0, fb.getDocument().getLength());
            StringBuilder newTextBuilder = new StringBuilder(oldText);
            newTextBuilder.insert(offset, newChars);
            String newText = newTextBuilder.toString();
            if (newText.trim().isEmpty()) {
                setFieldToDefaultValue();
            } else if (allowNegativeNumbers() && newText.trim().equals("-")) {
                setFieldToNegativeOne();
            } else if (isValidInteger(newText)) {
                super.insertString(fb, offset, newChars, a);
            } else {
                Toolkit.getDefaultToolkit().beep();
            }
        }

        private void setFieldToDefaultValue() {
            skipFiltersWhileTrue = true;
            String defaultValue = "" + parentField.getDefaultValue();
            parentField.setText(defaultValue);
            parentField.selectAll();
            skipFiltersWhileTrue = false;
        }

        private void setFieldToNegativeOne() {
            skipFiltersWhileTrue = true;
            parentField.setText("-1");
            parentField.select(1, 2);
            skipFiltersWhileTrue = false;
        }
    }

    public interface IntegerTextFieldNumberChangeListener {

        public void integerTextFieldNumberChanged(JIntegerTextField source, int newValue);
    }

}
