package com.github.lgooddatepicker.zinternaltools;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import javax.swing.JPanel;
import javax.swing.JWindow;
import javax.swing.Popup;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.BorderFactory;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.KeyStroke;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

/**
 * CustomPopup, This is a custom popup class, which provides a fine level of control over when the
 * popup opens and closes. This was created to overcome particular shortcomings of the JPopupMenu
 * class.
 *
 * WindowFocusListener notes: This class listens for focus change events in the popup displayWindow.
 * This class will close the popup when the popup displayWindow loses focus. This class handles its
 * own registration and de-registration of the focus change listener.
 *
 * ComponentListener notes: This class listens for movement in the top window of the component which
 * initiated the popup. If the topWindow moves, then the popup will be closed. This class handles
 * its own registration and de-registration of the listener with the top window component.
 */
public class CustomPopup extends Popup
        implements WindowFocusListener, ComponentListener {

    /**
     * displayWindow, This is the visible window that is used with this popup. The "Popup" class
     * does not provide its own visible component. A visible component must be supplied by the
     * implementation. This is set to null in the hide() function.
     */
    private JWindow displayWindow;

    /**
     * CustomPopupCloseListener, If this has been set to something besides null, then this listener
     * will be notified whenever this popup is closed, regardless of whether that close was
     * initiated internally or externally.
     */
    private CustomPopupCloseListener optionalCustomPopupCloseListener;

    /**
     * topWindow, This is a reference to the top window of the component which initiated the popup.
     * This is used for registering and deregistering the window movement listener with the
     * topWindow. If the top window moves, the popup is closed. This is set to null in the hide()
     * function
     */
    private Window topWindow;

    /**
     * enableHideWhenFocusIsLost, This is used to prevent a previous intermittent bug where blank
     * popup windows could occur in linux. This is set to true during the "WindowOpen" event. This
     * is never set to false after initialization. This is checked whenever the window loses focus.
     * The window can only be hidden when the focus is lost, if this has been set to true.
     */
    private boolean enableHideWhenFocusIsLost = false;

    /**
     * Constructor, This creates and initializes instances of this class.
     *
     * @param contentsComponent This is the component that you wish to display inside this popup.
     *
     * @param topWindow When the window that surrounds a popup is moved, the popup will be
     * automatically closed. The topWindow is the window that should be watched for movement. The
     * window that is needed is usually the top window of the component hierarchy, of the component
     * that initiated the popup. The function SwingUtilities.getWindowAncestor() can be useful for
     * getting the topWindow.
     *
     * @param optionalCustomPopupCloseListener If this is supplied, it will be notified when the
     * hide() function is called on this popup. This will occur regardless of whether the hide()
     * function was called internally or externally.
     *
     * @param optionalBorder If this is supplied, it will be used as the border for the popup
     * window. If no border is supplied, then a default border will be used.
     */
    public CustomPopup(Component contentsComponent, Window topWindow,
            CustomPopupCloseListener optionalCustomPopupCloseListener, Border optionalBorder) {
        // Call the constructor of the ancestor Popup class.
        super();
        // Save the initialization variables for future use.
        this.topWindow = topWindow;
        this.optionalCustomPopupCloseListener = optionalCustomPopupCloseListener;
        // Create the panel that will be added to the display window.
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(contentsComponent, BorderLayout.CENTER);
        // Add the appropriate border to the main panel.
        if (optionalBorder == null) {
            // This creates and uses a default popup border.
            // The design of this border was based on the JPopupMenu border, but this border
            // behaves better. It does not show any of the main panel through transparent areas.
            Border outsideBorder = new LineBorder(new Color(99, 130, 191));
            Border insideBorder = BorderFactory.createMatteBorder(1, 0, 0, 0, Color.white);
            Border compoundBorder = BorderFactory.createCompoundBorder(outsideBorder, insideBorder);
            mainPanel.setBorder(compoundBorder);
        } else {
            // This uses the supplied border.
            mainPanel.setBorder(optionalBorder);
        }
        // Create the display window.
        displayWindow = new JWindow(topWindow);
        // This is part of the bug fix for blank popup windows in linux.
        displayWindow.addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                enableHideWhenFocusIsLost = true;
            }
        });

        displayWindow.getContentPane().add(mainPanel);
        displayWindow.setFocusable(true);
        
        // Bug Fix for Github Issue #49
        // "Date selection and navigation not working on Mac OS X 10.12.4 under Java 8 update 121."
        displayWindow.setAlwaysOnTop(true);
        
        displayWindow.pack();
        displayWindow.validate();
        // Add the action that is needed to close the popup when the escape key is pressed.
        String cancelName = "cancel";
        InputMap inputMap = mainPanel.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), cancelName);
        ActionMap actionMap = mainPanel.getActionMap();
        actionMap.put(cancelName, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                hide();
            }
        });
        // Register this class as a listener with the appropriate components.
        // De-registration is handled in the hide() function.
        registerListeners();
    }

    /**
     * componentHidden, Part of ComponentListener. Whenever the topWindow is hidden, the popup will
     * be hidden.
     */
    @Override
    public void componentHidden(ComponentEvent e) {
        hide();
    }

    /**
     * componentMoved, Part of ComponentListener. Whenever the topWindow is moved, the popup will be
     * hidden.
     */
    @Override
    public void componentMoved(ComponentEvent e) {
        hide();
    }

    /**
     * componentResized, Part of ComponentListener. Whenever the topWindow is resized, the popup
     * will be hidden.
     */
    @Override
    public void componentResized(ComponentEvent e) {
        hide();
    }

    /**
     * componentShown, Part of ComponentListener. This does nothing, it is included because all
     * listener functions must be implemented.
     */
    @Override
    public void componentShown(ComponentEvent e) {
        // Do nothing here.
    }

    /**
     * getBounds, This returns the bounds of the CustomPopup displayWindow in the form of a
     * Rectangle object. The bounds specify this component's width, height, and location relative to
     * its parent.
     */
    public Rectangle getBounds() {
        return displayWindow.getBounds();
    }

    /**
     * hide, This hides the popup window. This removes this class from the list of window focus
     * listeners for the popup window, and removes this class from the list of window movement
     * listeners for the top window. This can be called internally or externally. If this is called
     * multiple times, then only the first call will have an effect.
     */
    @Override
    public void hide() {
        if (displayWindow != null) {
            displayWindow.setVisible(false);
            displayWindow.removeWindowFocusListener(this);
            displayWindow = null;
        }
        if (topWindow != null) {
            topWindow.removeComponentListener(this);
            topWindow = null;
        }
        if (optionalCustomPopupCloseListener != null) {
            optionalCustomPopupCloseListener.zEventCustomPopupWasClosed(this);
            optionalCustomPopupCloseListener = null;
        }
    }

    /**
     * registerListeners, This function registers this class as a listener with the appropriate
     * components. De-registration is handled in the hide() function.
     */
    private void registerListeners() {
        // Register this class as a focus listener with the display window.
        displayWindow.addWindowFocusListener(this);
        // Register this class as a window movement listener with the top window.
        topWindow.addComponentListener(this);
    }

    /**
     * setLocation, This changes the location of the popup window.
     */
    public void setLocation(int popupX, int popupY) {
        displayWindow.setLocation(popupX, popupY);
    }

    /**
     * show, This shows the visible component of the popup window.
     */
    @Override
    public void show() {
        displayWindow.setVisible(true);
    }

    /**
     * windowGainedFocus, Part of WindowFocusListener. This does nothing, it is included because all
     * listener functions must be implemented.
     */
    @Override
    public void windowGainedFocus(WindowEvent e) {
        // Do nothing here.
    }

    /**
     * windowLostFocus, Part of WindowFocusListener. Whenever the popup window loses focus, it will
     * be hidden.
     */
    @Override
    public void windowLostFocus(WindowEvent e) {
        // This section is part of the bug fix for blank popup windows in linux.
        if (!enableHideWhenFocusIsLost) {
            e.getWindow().requestFocus();
            return;
        }
        // This fixes a linux-specific behavior where the focus can be "lost" by clicking a child
        // component (inside the same panel!).
        if (InternalUtilities.isMouseWithinComponent(displayWindow)) {
            return;
        }
        hide();
    }

    public void setMinimumSize(Dimension minimumSize) {
        displayWindow.setMinimumSize(minimumSize);
    }

    /**
     * CustomPopupCloseListener, Any class that uses a CustomPopup (or any other class), may
     * implement this interface to be notified when the CustomPopup is closed. The implementing
     * class should pass itself into the CustomPopup constructor.
     */
    static public interface CustomPopupCloseListener {

        /**
         * zEventCustomPopupWasClosed, This will be called whenever the CustomPopup is closed,
         * either internally or externally. More specifically, this is called by the
         * CustomPopup.hide() function.
         */
        public void zEventCustomPopupWasClosed(CustomPopup popup);
    }
}
