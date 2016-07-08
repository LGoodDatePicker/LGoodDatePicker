package com.github.lgooddatepicker.optionalusertools;

import java.awt.Color;
import java.awt.Point;

/**
 * CalendarBorderProperties,
 *
 * This class is used to specify properties of the border labels in the calendar panel. This class
 * can be used to change border colors, or border thicknesses (in pixels), or both. A single border
 * label or a group of border labels can be changed at the same time. The border labels are
 * individually identified using a "border coordinate system", which is described below.
 *
 * The borders in the calendar panel are implemented with colored labels. (Instances of JLabel).
 * Each border label is assigned a two-dimensional border label coordinate. Below is a visual
 * representation of all the border label coordinates. The character 'X' represents a coordinate
 * with a border label, and the character 'o' represents a coordinate that does not have a border
 * label (is empty).
 * <pre>
 * ~12345
 * 1XXXXX
 * 2XoXoX
 * 3XXXXX
 * 4XoXoX
 * 5XXXXX
 * </pre>
 *
 * For example: Row 1 of the border coordinate system contains the topmost border in the calendar
 * panel. (The horizontal border above the weekday labels. This border is invisible by default.)
 * Column 5 contains the rightmost possible border, which is the vertical border to the right of the
 * calendar dates.
 *
 * Note that the border labels at "corner" or "intersection" coordinates can be visually very small,
 * as small as 1x1 pixel. Also note that there is no border label at the following four border label
 * coordinates: ((2,2) (4,2) (2,4) (4,4)). Including these coordinates in your specified range is
 * acceptable and normal, but will have no effect on the empty spaces.
 *
 * Technical note: The border label coordinates do not refer to date labels, (or layout cells, or
 * screen coordinates). They refer only to the array indexes of the JLabel elements, in the
 * two-dimensional "borderLabels" array inside the CalendarPanel class.
 */
public class CalendarBorderProperties {

    /**
     * Constructor, default.
     */
    public CalendarBorderProperties() {
    }

    /**
     * Constructor, with properties.
     */
    public CalendarBorderProperties(Point upperLeft, Point lowerRight,
            Color backgroundColor, Integer thicknessInPixels) {
        this.upperLeft = upperLeft;
        this.lowerRight = lowerRight;
        this.backgroundColor = backgroundColor;
        this.thicknessInPixels = thicknessInPixels;
    }

    /**
     * upperLeft, This is the upper left point (in the border coordinate system), of the "square" of
     * border coordinates that you would like to modify.
     *
     * This must be between (1,1) and (5,5), and should not be null. In exception will be thrown if
     * this null or if this is not in the valid range.
     */
    public Point upperLeft;

    /**
     * lowerRight, This is the lower right point (in the border coordinate system), of the "square"
     * of border coordinates that you would like to modify.
     *
     * This must be between (1,1) and (5,5), and should not be null. To specify a single border
     * coordinate, or to specify a single row or column, the X and Y values can optionally be the
     * same as the values in the "upperLeft" point. An exception will be thrown if this is null, or
     * if this is not in the valid range, or if the values in the lowerRight point are smaller than
     * the values in the upperLeft point.
     */
    public Point lowerRight;

    /**
     * backgroundColor, This can specify a color for the borders, or null if you don't want to
     * change the color.
     */
    public Color backgroundColor;

    /**
     * thicknessInPixels, This can specify a thickness for the borders (in pixels), or null if you
     * don't want to change the thickness. Setting the thickness of a border to zero will make it
     * invisible, and setting a thickness of 1 or higher will make the border visible.
     */
    public Integer thicknessInPixels;

    /**
     * clone, This function creates and returns a deep copy of this CalendarBorderProperties
     * instance.
     */
    @Override
    public CalendarBorderProperties clone() {
        CalendarBorderProperties result = new CalendarBorderProperties();
        result.backgroundColor = this.backgroundColor;
        result.lowerRight = (this.lowerRight == null) ? null : new Point(this.lowerRight);
        result.thicknessInPixels = this.thicknessInPixels;
        result.upperLeft = (this.upperLeft == null) ? null : new Point(this.upperLeft);
        return result;
    }
}
