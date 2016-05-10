package com.github.lgooddatepicker.zinternaltools;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

/**
 * MouseLiberalAdapter. This class allows mouse clicks to be detected inside of a swing component
 * even if the mouse moves during the click event. This class is especially useful to properly
 * detect clicks in a JLabel.
 *
 * To use this class, override any non-final methods of interest, just like you would with the
 * MouseAdapter class. The mouseLiberalClick() method is the method to override if you wish to
 * detect mouse clicks that may have mouse movement between the press and release events.
 */
public abstract class MouseLiberalAdapter extends MouseAdapter {

    private boolean isComponentPressedDown = false;

    @Override
    final public void mousePressed(MouseEvent e) {
        isComponentPressedDown = true;
        mousePress(e);
    }

    @Override
    final public void mouseReleased(MouseEvent e) {
        if (isComponentPressedDown) {
            mouseLiberalClick(e);
        }
        isComponentPressedDown = false;
        mouseRelease(e);
    }

    @Override
    final public void mouseEntered(MouseEvent e) {
        mouseEnter(e);
    }

    @Override
    final public void mouseExited(MouseEvent e) {
        isComponentPressedDown = false;
        mouseExit(e);
    }

    @Override
    final public void mouseClicked(MouseEvent e) {
        mouseClick(e);
    }

    @Override
    final public void mouseWheelMoved(MouseWheelEvent e) {
        mouseWheelMove(e);
    }

    @Override
    final public void mouseDragged(MouseEvent e) {
        mouseDrag(e);
    }

    @Override
    final public void mouseMoved(MouseEvent e) {
        mouseMove(e);
    }

    public void mouseLiberalClick(MouseEvent e) {
    }

    public void mouseClick(MouseEvent e) {
    }

    public void mousePress(MouseEvent e) {
    }

    public void mouseRelease(MouseEvent e) {
    }

    public void mouseEnter(MouseEvent e) {
    }

    public void mouseExit(MouseEvent e) {
    }

    public void mouseWheelMove(MouseWheelEvent e) {
    }

    public void mouseDrag(MouseEvent e) {
    }

    public void mouseMove(MouseEvent e) {
    }
}
