package com.github.lgooddatepicker.zinternaltools;

import com.github.lgooddatepicker.optionalusertools.DateInterval;
import com.github.lgooddatepicker.optionalusertools.DateVetoPolicy;
import java.time.LocalDate;

/**
 * DateVetoPolicyMinimumMaximumDate, This class implements a veto policy that can set a minimum and
 * a maximum value for the dates allowed in a DatePicker or a CalendarPanel.
 *
 * Pass in the first and the last allowed date to the constructor. If one of the values is null,
 * then there will be no limiting date on the associated side of the date range. Only one of the two
 * limiting dates can be null. If both dates are supplied, then the lastAllowedDate must be greater
 * than or equal to the firstAllowedDate.
 */
public class DateVetoPolicyMinimumMaximumDate
        implements DateVetoPolicy {

    /**
     * firstAllowedDate, This is the first date that will be allowed. If this is null, then there
     * will be no lower bound on the date. Only one of the two limiting dates can be null.
     */
    private LocalDate firstAllowedDate = null;

    /**
     * lastAllowedDate, This is the last date that will be allowed. If this is null, then there will
     * be no upper bound on the date. Only one of the two limiting dates can be null.
     */
    private LocalDate lastAllowedDate = null;

    /**
     * Constructor. Pass in the first and the last allowed date. If one of the values is null, then
     * there will be no limiting date on the associated side of the date range. Only one of the two
     * limiting dates can be null. If both dates are supplied, then the lastAllowedDate must be
     * greater than or equal to the firstAllowedDate.
     */
    public DateVetoPolicyMinimumMaximumDate(LocalDate firstAllowedDate, LocalDate lastAllowedDate) {
        setDateRangeLimits(firstAllowedDate, lastAllowedDate);
    }

    /**
     * getDateRangeLimits, This returns the currently used date limits, as a DateInterval object.
     */
    public DateInterval getDateRangeLimits() {
        return new DateInterval(firstAllowedDate, lastAllowedDate);
    }

    /**
     * isDateAllowed, This implements the DateVetoPolicy interface. This returns true if the date is
     * allowed, otherwise this returns false. The value of null will never be passed to this
     * function, under any case.
     */
    @Override
    public boolean isDateAllowed(LocalDate date) {
        if ((firstAllowedDate != null) && (date.isBefore(firstAllowedDate))) {
            return false;
        }
        if ((lastAllowedDate != null) && (date.isAfter(lastAllowedDate))) {
            return false;
        }
        return true;
    }

    /**
     * setDateRangeLimits, This sets the currently used date limits.
     */
    public void setDateRangeLimits(LocalDate firstAllowedDate, LocalDate lastAllowedDate) {
        if (firstAllowedDate == null && lastAllowedDate == null) {
            throw new RuntimeException("DateVetoPolicyMinimumMaximumDate.setDateRangeLimits(),"
                    + "The variable firstAllowedDate can be null, or lastAllowedDate can be null, "
                    + "but both values cannot be null at the same time. "
                    + "If you wish to clear the veto policy, then call the function: "
                    + "'DatePickerSettings.setVetoPolicy(null)'.");
        }
        if (firstAllowedDate != null && lastAllowedDate != null
                && lastAllowedDate.isBefore(firstAllowedDate)) {
            throw new RuntimeException("\"DateVetoPolicyMinimumMaximumDate.setDateRangeLimits(),"
                    + "The lastAllowedDate must be greater than or equal to the firstAllowedDate.");
        }
        this.firstAllowedDate = firstAllowedDate;
        this.lastAllowedDate = lastAllowedDate;
    }

}
