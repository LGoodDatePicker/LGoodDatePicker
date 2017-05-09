package com.github.lgooddatepicker.zinternaltools;

import com.github.lgooddatepicker.components.TimePicker;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

/**
 * TimeSpinnerTimer, This class implements a timer which can fire an increment event at a rate that
 * accelerates over a set period of time. This is used by the time picker component. The increment
 * event is used to change the time and the time picker while the user is holding a spinner
 * activation key. (The spinner functionality may be activated with the keyboard or the mouse.)
 */
public class TimeSpinnerTimer {

    /**
     * timePicker, This holds the time picker that is associated with this class.
     */
    final private TimePicker timePicker;

    /**
     * timePicker, This holds the number of minutes that should be added or subtracted with each
     * increment of the time picker value. This will typically be -1 or 1.
     */
    final private int changeAmountMinutes;

    /**
     * timer, This holds the timer associated with this class.
     */
    final private Timer timer;

    /**
     * startDelayMillis, This indicates how long the timer should wait before firing its first tick.
     * This value is used to make sure that the user can easily increase or decrease the date picker
     * value by only 1 minute.
     */
    final private int startDelayMillis = 700;
    /**
     * timerRate, This indicates how often the timer should call the tick function, in milliseconds.
     */
    final private int timerRate = 20;
    /**
     * millisForDivisorList, This indicates how long each value in the divisorList should be used,
     * before moving onto the next value in the divisorList.
     */
    final private int[] millisForDivisorList = new int[]{
        1800, 900, 400, 400, 400, 400, 400, 0};
    /**
     * divisorList, For as long as any particular index in this array remains in effect, the
     * currently used number indicates how many tick calls should pass before the time picker value
     * should be changed. For example, the number 3 indicates that the time picker value should be
     * changed only once for every 3 calls to the tick function. Higher numbers make the spinner
     * change slower, lower numbers make the spinner change faster.
     */
    final private int[] divisorList = new int[]{12, 10, 8, 6, 4, 3, 2, 1};
    /**
     * startedIndexTimeStamp, This indicates the time that the currently used index in the
     * divisorList started to be used.
     */
    private long startedIndexTimeStamp = 0;
    /**
     * currentIndex, This indicates the index that is currently in effect for the divisorList and
     * the millisForIndexList.
     */
    private int currentIndex = 0;
    /**
     * ticksSinceIndexChange, This keeps track of the number of ticks that has passed since the last
     * time that the current index was changed. This helps us calculate when we need to change the
     * index to the next value.
     */
    private int ticksSinceIndexChange;

    /**
     * Constructor.
     */
    public TimeSpinnerTimer(TimePicker timePicker, int changeAmountMinutes) {
        this.timePicker = timePicker;
        this.changeAmountMinutes = changeAmountMinutes;
        timer = new Timer(timerRate, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                tick();
            }
        });
        timer.setInitialDelay(startDelayMillis);
    }

    /**
     * tick, This is called once each time that the timer fires. (Every 20 milliseconds).
     *
     * However, the value in the time picker will only be changed once for every certain number of
     * calls to the tick() function. The number of calls which is required is controlled by the
     * "divisorList" array values.
     *
     * The amount of time that is spent using each divisorList array value is controlled by the
     * "millisForIndexList".
     */
    private void tick() {
        if (startedIndexTimeStamp == 0) {
            startedIndexTimeStamp = System.currentTimeMillis();
        }
        long timeElapsedSinceIndexStartMilliseconds = System.currentTimeMillis() - startedIndexTimeStamp;
        int maximumIndex = divisorList.length - 1;
        int currentDivisor = divisorList[currentIndex];
        if (ticksSinceIndexChange % currentDivisor == 0) {
            timePicker.zInternalTryChangeTimeByIncrement(changeAmountMinutes);
            // System.out.println("\nMillis: " + System.currentTimeMillis());
            // System.out.println("Divisor: " + divisor);
            // System.out.println("Value: " + timePicker.getTime());
            if ((currentIndex < maximumIndex)
                    && (timeElapsedSinceIndexStartMilliseconds > millisForDivisorList[currentIndex])) {
                ticksSinceIndexChange = 0;
                ++currentIndex;
                startedIndexTimeStamp = System.currentTimeMillis();
            }
        }
        ++ticksSinceIndexChange;
    }

    /**
     * stop, This is called to stop the timer.
     */
    public void stop() {
        timer.stop();
    }

    /**
     * start, This is called to start the timer, and to initialize the needed variables.
     */
    public void start() {
        startedIndexTimeStamp = 0;
        currentIndex = 0;
        ticksSinceIndexChange = 0;
        timer.start();
    }

}
