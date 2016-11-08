package com.github.lgooddatepicker.durationpicker_underconstruction;

import java.time.temporal.ChronoUnit;

/**
 *
 */
public enum DurationUnit {
    Second(1),
    Minute(60),
    Hour(60 * 60),
    Day(24 * 60 * 60),
    Week(7 * 24 * 60 * 60),
    Month((int) ChronoUnit.MONTHS.getDuration().getSeconds()),
    Year((int) ChronoUnit.YEARS.getDuration().getSeconds());

    final public int inSeconds;
    final public int thirtyMinutesInSeconds = (30 * 60);

    DurationUnit(int secondsConstant) {
        inSeconds = secondsConstant;
    }
}
