package smd.ufc.br.easycontext.fence;

import android.annotation.SuppressLint;

import com.google.android.gms.awareness.fence.AwarenessFence;
import com.google.android.gms.awareness.fence.TimeFence;

import java.util.TimeZone;

import smd.ufc.br.easycontext.fence.method.TimeMethod;
import smd.ufc.br.easycontext.fence.parameter.FenceParameter;
import smd.ufc.br.easycontext.fence.parameter.TimeParameter;
import smd.ufc.br.easycontext.fence.type.FenceType;

public class TimeRule implements Rule{
    private TimeMethod method;

    private int timeInstant;
    private long startOffsetMillis;
    private long stopOffsetMillis;

    private TimeZone timeZone;
    private long startTimeOfDayMillis;
    private long stopTimeOfDayMillis;
    private long startTimeMillis;
    private long stopTimeMillis;

    private int dayOfWeek;
    private int timeInterval;

    TimeRule() {

    }

    @SuppressLint("MissingPermission")
    @Override
    public AwarenessFence getAwarenessFence() {
        switch(method){
            case IN_INTERVAL:
                return TimeFence.inInterval(startTimeMillis, stopTimeMillis);
            case IN_TIME_INTERVAL:
                return TimeFence.inTimeInterval(timeInterval);
            case IN_DAILY_INTERVAL:
                return TimeFence.inDailyInterval(timeZone, startTimeOfDayMillis, stopTimeOfDayMillis);
            case IN_INTERVAL_OF_DAY:
                return TimeFence.inIntervalOfDay(dayOfWeek, timeZone, startTimeOfDayMillis, stopTimeOfDayMillis);
            case AROUND_TIME_INSTANT:
                return TimeFence.aroundTimeInstant(timeInstant, startOffsetMillis, stopOffsetMillis);
        }
        return null;

    }

    public static TimeRule aroundTimeInstant(int timeInstant, long startOffsetMillis, long stopOffsetMillis){
        TimeRule timeRule = new TimeRule();
        timeRule.timeInstant = timeInstant;
        timeRule.startOffsetMillis = startOffsetMillis;
        timeRule.stopOffsetMillis = stopOffsetMillis;
        timeRule.method = TimeMethod.AROUND_TIME_INSTANT;
        return timeRule;
    }

    public static TimeRule inDailyInterval(TimeZone timeZone, long startTimeOfDayMillis, long stopTimeOfDayMillis){
        TimeRule timeRule =  new TimeRule();
        timeRule.timeZone = timeZone;
        timeRule.startTimeOfDayMillis = startTimeOfDayMillis;
        timeRule.stopTimeOfDayMillis = stopTimeOfDayMillis;
        timeRule.method = TimeMethod.IN_DAILY_INTERVAL;
        return timeRule;
    }

    public static TimeRule inInterval(long startTimeMillis, long stopTimeMillis){
        TimeRule timeRule =  new TimeRule();
        timeRule.startTimeMillis = startTimeMillis;
        timeRule.stopTimeMillis = stopTimeMillis;
        timeRule.method = TimeMethod.IN_INTERVAL;
        return timeRule;
    }

    public static TimeRule inIntervalOfDay(int dayOfWeek, TimeZone timeZone, long startTimeOfDayMillis, long stopTimeOfDayMillis){
        TimeRule timeRule =  new TimeRule();
        timeRule.dayOfWeek = dayOfWeek;
        timeRule.timeZone = timeZone;
        timeRule.startTimeOfDayMillis = startTimeOfDayMillis;
        timeRule.stopTimeOfDayMillis = stopTimeOfDayMillis;
        timeRule.method = TimeMethod.IN_INTERVAL_OF_DAY;
        return timeRule;
    }


    public static TimeRule inTimeInterval(int timeInterval){
        TimeRule timeRule =  new TimeRule();
        timeRule.timeInterval = timeInterval;
        timeRule.method = TimeMethod.IN_TIME_INTERVAL;
        return timeRule;
    }

}
