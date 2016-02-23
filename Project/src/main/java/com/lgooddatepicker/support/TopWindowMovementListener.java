package com.lgooddatepicker.support;

import java.awt.Window;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import com.lgooddatepicker.core.DatePicker;

/**
 * TopWindowMovementListener, This class is used to listen for movement in the top window of a date
 * pickers GUI component tree. If the window moves, the popup calendar for the date picker will be
 * closed. This class handles its own registration of the listener instances with the top window
 * component, but not the de-registration. New instances of this class are generated with the
 * addNewTopWindowMovementListener() function.
 *
 * Implementation note: This class cannot handle its own de-registration from the top window,
 * because a popup may be closed from many different sources (not exclusively from the events in
 * this class). For that reason, the de-registration of this class from the top window is handled by
 * the parentDatePicker.closePopup() function.
 */
public class TopWindowMovementListener implements ComponentListener {

    private DatePicker parentDatePicker;

    private TopWindowMovementListener(DatePicker parentDatePicker) {
        this.parentDatePicker = parentDatePicker;
    }

    public static void addNewTopWindowMovementListener(DatePicker parentDatePicker, Window topWindow) {
        topWindow.addComponentListener(new TopWindowMovementListener(parentDatePicker));
    }

    @Override
    public void componentResized(ComponentEvent e) {
        closePopupAndDispose();
    }

    @Override
    public void componentMoved(ComponentEvent e) {
        closePopupAndDispose();
    }

    @Override
    public void componentHidden(ComponentEvent e) {
        closePopupAndDispose();
    }

    @Override
    public void componentShown(ComponentEvent e) {
    }

    private void closePopupAndDispose() {
        parentDatePicker.closePopup();
        parentDatePicker = null;
    }
}
