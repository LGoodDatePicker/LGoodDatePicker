package com.lgooddatepicker.support;

import java.awt.Component;
import java.awt.Window;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JWindow;
import javax.swing.Popup;
import com.lgooddatepicker.core.DatePicker;

/**
 * CustomPopup, This class is used by the date picker, to supply the popup window for the calendar
 * panel. This class provides a fine control over when the popup opens and closes. This was created
 * to overcome particular shortcomings of the JPopupMenu class.
 */
public class CustomPopup extends Popup
        implements WindowFocusListener {

    /**
     * displayWindow, This is the visible window that is used with this popup. The popup class does
     * not provide its own visible component. A visible component must be supplied by the
     * implementation.
     */
    public JWindow displayWindow;

    /**
     * parentDatePicker, This is a reference to the parent date picker of this popup.
     */
    private DatePicker parentDatePicker;

    /**
     * Constructor, This creates and initializes instances of this class.
     */
    public CustomPopup(Window base, Component component, DatePicker parentDatePicker) {
        super();
        this.parentDatePicker = parentDatePicker;
        JPanel panel = new JPanel();
        panel.add(component);
        panel.setBorder(new JPopupMenu().getBorder());
        displayWindow = new JWindow(base);
        displayWindow.getContentPane().add(panel);
        displayWindow.setFocusable(true);
        displayWindow.pack();
        displayWindow.validate();
    }

    /**
     * hide, This hides the popup window. This also removes this class from the list of window
     * listeners for the popup window.
     */
    @Override
    public void hide() {
        displayWindow.setVisible(false);
        displayWindow.removeWindowFocusListener(this);
        parentDatePicker = null;
    }

    /**
     * setLocation, This gives external classes the ability to change location of the popup window.
     * (By default, external classes only have access to the functions of the Popup class.)
     */
    public void setLocation(int popupX, int popupY) {
        displayWindow.setLocation(popupX, popupY);
    }

    /**
     * show, This shows the visible component of the popup window. This also registers this class as
     * a window focus listener for the popup window.
     */
    @Override
    public void show() {
        displayWindow.addWindowFocusListener(this);
        displayWindow.setVisible(true);
    }

    @Override
    public void windowGainedFocus(WindowEvent e) {
        // Do nothing here.
    }

    /**
     * windowLostFocus, Whenever the popup window loses focus, the popup will tell the parent date
     * picker to close the popup. Popup closing is handled from a central location so that it is
     * done in a consistent way.
     */
    @Override
    public void windowLostFocus(WindowEvent e) {
        parentDatePicker.closePopup();
    }
}
