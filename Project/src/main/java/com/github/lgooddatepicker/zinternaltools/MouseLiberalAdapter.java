package com.github.lgooddatepicker.zinternaltools;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

/**
 * MouseLiberalAdapter.
 *
 * This class extends the MouseAdapter class, to include two additional events. The added events are
 * the mouseLiberalClick() and the mouseLiberalDoubleClick(). By default, the mouseClick() event in
 * the MouseAdapter has a limitation. The mouseClick() event cannot register a click if the mouse
 * pointer moves even slightly, between the mouse press and mouse release events. By contrast, the
 * mouseLiberalClick() will register a "liberal mouse click" even if the mouse moves (by any amount)
 * during the click event, as long as the mouse pointer does not leave the boundaries of the
 * component which is generating the mouse events. (This "liberal mouse click" behavior duplicates
 * the "actionPerformed()" functionality that exists in the JButton class.)
 *
 * Note: This class is frequently used to detect clicks in a JLabel, but it can be used in any swing
 * component that will accept a MouseAdapter.
 *
 * Using this class is similar to using the MouseAdapter class. (See also: The MouseAdapter
 * javadocs.) To use this class, you would extend this class and override any (non-final) event
 * methods that are of interest.
 *
 * The original MouseAdapter functions have been marked as final, and cannot be overridden. However,
 * the class still provides all the original functions (with slightly modified function names). The
 * two new functions are also provided: mouseLiberalClick() and mouseLiberalDoubleClick(). A usage
 * example is shown below.
 *
 * Usage example:  <code>
 * JLabel labelSingleClick = new JLabel("Single click me.");
 * JLabel labelDoubleClick = new JLabel("Double click me.");
 * labelSingleClick.addMouseListener(new MouseLiberalAdapter() {
 *
 * public void mouseLiberalClick(MouseEvent e) {
 * JOptionPane.showMessageDialog(null, "Single click detected.");
 * }
 * });
 * labelDoubleClick.addMouseListener(new MouseLiberalAdapter() {
 *
 * public void mouseLiberalDoubleClick(MouseEvent e) {
 * JOptionPane.showMessageDialog(null, "Double click detected.");
 * }
 * });
 * </code>
 */
public abstract class MouseLiberalAdapter extends MouseAdapter {

    /**
     * isComponentPressedDown, This indicates whether or not the component is currently
     * (conceptually) "pressed down". To understand the meaning of "pressed down", consider the
     * behavior of a JButton. When you press the mouse inside a button, the button redraws itself to
     * indicate a "press down" state. If you release the mouse while inside the button, a button
     * click will be registered, and the button will switch to a "not press down" state. The button
     * can also become "not pressed down" if the mouse pointer leaves the boundaries of the button
     * without first releasing the mouse.
     */
    private boolean isComponentPressedDown = false;
    /**
     * lastUnusedLiberalSingleClickTimeStamp, This stores a timestamp for the mouse release of the
     * last unused liberal single click. If a single click is "used" as part of a double click, then
     * it's timestamp will no longer be stored here. If there is no liberal single click which fits
     * the above description, then this will contain the value zero.
     */
    private long lastUnusedLiberalSingleClickTimeStamp = 0;
    /**
     * slowestDoubleClickMilliseconds, This constant indicates the maximum time window in which a
     * liberal double click can occur. More specifically, this indicates the maximum time, in
     * milliseconds, between liberal single click mouse releases, that will be considered to
     * constitute a liberal double click.
     */
    private final int slowestDoubleClickMilliseconds = 1800;

    /**
     * mouseLiberalClick, Override this function to catch liberal single click events.
     *
     * Note: The mouse event which is passed to this function will be the mouse event that was
     * received from the "mouseRelease" event at the end of the liberal single click.
     */
    public void mouseLiberalClick(MouseEvent e) {
    }

    /**
     * mouseLiberalDoubleClick, Override this function to catch liberal double click events.
     *
     * Note: The mouse event which is passed to this function will be the mouse event that was
     * received from the "mouseRelease" event at the end of the liberal double click.
     */
    public void mouseLiberalDoubleClick(MouseEvent e) {
    }

    /**
     * mouseClick, Override this function to catch standard mouse click events.
     */
    public void mouseClick(MouseEvent e) {
    }

    /**
     * mousePress, Override this function to catch standard mouse press events.
     */
    public void mousePress(MouseEvent e) {
    }

    /**
     * mouseRelease, Override this function to catch standard mouse release events.
     */
    public void mouseRelease(MouseEvent e) {
    }

    /**
     * mouseEnter, Override this function to catch standard mouse enter events.
     */
    public void mouseEnter(MouseEvent e) {
    }

    /**
     * mouseExit, Override this function to catch standard mouse exit events.
     */
    public void mouseExit(MouseEvent e) {
    }

    /**
     * mouseWheelMove, Override this function to catch standard mouse wheel move events.
     */
    public void mouseWheelMove(MouseWheelEvent e) {
    }

    /**
     * mouseDrag, Override this function to catch standard mouse drag events.
     */
    public void mouseDrag(MouseEvent e) {
    }

    /**
     * mouseMove, Override this function to catch standard mouse move events.
     */
    public void mouseMove(MouseEvent e) {
    }

    /**
     * mousePressed, Final function. Handles mouse pressed events.
     */
    @Override
    final public void mousePressed(MouseEvent e) {
        // Record that the component is "pressed down".
        isComponentPressedDown = true;
        // Call the mouse press event.
        mousePress(e);
    }

    /**
     * mouseReleased, Final function. Handles mouse released events. This function also detects
     * liberal single clicks, and liberal double clicks.
     */
    @Override
    final public void mouseReleased(MouseEvent e) {
        // Check to see if this mouse release completes a liberal single click.
        if (isComponentPressedDown) {
            // A liberal single click has occurred.
            mouseLiberalClick(e);
            // Check to see if we had two liberal single clicks within the double click time window.
            long now = System.currentTimeMillis();
            long timeBetweenUnusedClicks = now - lastUnusedLiberalSingleClickTimeStamp;
            if (timeBetweenUnusedClicks <= slowestDoubleClickMilliseconds) {
                // A liberal double click has occurred.
                mouseLiberalDoubleClick(e);
                // Mark the single click timestamp as "used" by this double click.
                lastUnusedLiberalSingleClickTimeStamp = 0;
            } else {
                // Save the single click timestamp as part of a possible future double click.
                lastUnusedLiberalSingleClickTimeStamp = System.currentTimeMillis();
            }
        }
        // Record the mouse release.
        isComponentPressedDown = false;
        // Call the mouse release event.
        mouseRelease(e);
    }

    /**
     * mouseEntered, Final function. Handles mouse entered events.
     */
    @Override
    final public void mouseEntered(MouseEvent e) {
        // Call the mouse enter event.
        mouseEnter(e);
    }

    /**
     * mouseExited, Final function. Handles mouse exited events.
     */
    @Override
    final public void mouseExited(MouseEvent e) {
        // Since the mouse left the component, the component is no longer considered "press down".
        isComponentPressedDown = false;
        // Call the mouse exit event.
        mouseExit(e);
    }

    /**
     * mouseClicked, Final function. Handles mouse clicked events.
     */
    @Override
    final public void mouseClicked(MouseEvent e) {
        // Call the mouse click event.
        mouseClick(e);
    }

    /**
     * mouseWheelMoved, Final function. Handles mouse wheel moved events.
     */
    @Override
    final public void mouseWheelMoved(MouseWheelEvent e) {
        // Call the mouse wheel move event.
        mouseWheelMove(e);
    }

    /**
     * mouseDragged, Final function. Handles mouse dragged events.
     */
    @Override
    final public void mouseDragged(MouseEvent e) {
        // Call the mouse drag event.
        mouseDrag(e);
    }

    /**
     * mouseMoved, Final function. Handles mouse moved events.
     */
    @Override
    final public void mouseMoved(MouseEvent e) {
        // Call the mouse move event.
        mouseMove(e);
    }
}
