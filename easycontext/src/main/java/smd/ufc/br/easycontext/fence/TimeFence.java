package smd.ufc.br.easycontext.fence;

import com.google.android.gms.awareness.fence.AwarenessFence;

import java.sql.Time;
import java.util.TimeZone;

import smd.ufc.br.easycontext.fence.method.FenceMethod;
import smd.ufc.br.easycontext.fence.method.TimeMethod;
import smd.ufc.br.easycontext.fence.parameter.FenceParameter;
import smd.ufc.br.easycontext.fence.parameter.TimeParameter;
import smd.ufc.br.easycontext.fence.type.FenceType;

public class TimeFence extends Fence {
    private TimeMethod method;
    private TimeParameter parameter;
    int timeInstant;
    long startOffsetMillis;
    long stopOffsetMillis;

    TimeZone timeZone;
    long startTimeOfDayMillis;
    long stopTimeOfDayMillis;
    long startTimeMillis;
    long stopTimeMillis;

    int dayOfWeek;
    int timeInterval;

    TimeFence(String name, FenceType type, FenceAction action, TimeMethod method, FenceParameter params) {
        super(name, FenceType.TIME, action, params);
    }

    @Override
    public void setMethod(FenceMethod method) {

    }

    @Override
    public void setParams(FenceParameter params) {
        Builder b = new Builder();

    }

    @Override
    public AwarenessFence getMethod() {
        return null;
    }

    @Override
    public FenceMethod getMethodName() {
        return method;
    }

    public static class Builder extends Fence.Builder{
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

        private boolean ok = false;
        public Builder(){

        }

        public Builder aroundTimeInstant(int timeInstant, long startOffsetMillis, long stopOffsetMillis){
            this.timeInstant = timeInstant;
            this.startOffsetMillis = startOffsetMillis;
            this.stopOffsetMillis = stopOffsetMillis;
            this.method = TimeMethod.AROUND_TIME_INSTANT;
            ok = true;
            return this;
        }
        public Builder inDailyInterval(TimeZone timeZone, long startTimeOfDayMillis, long stopTimeOfDayMillis){
            this.timeZone = timeZone;
            this.startTimeOfDayMillis = startTimeOfDayMillis;
            this.stopTimeOfDayMillis = stopTimeOfDayMillis;
            this.method = TimeMethod.IN_DAILY_INTERVAL;
            ok = true;
            return this;
        }
        public Builder inInterval(long startTimeMillis, long stopTimeMillis){
            this.startTimeMillis = startTimeMillis;
            this.stopTimeMillis = stopTimeMillis;
            this.method = TimeMethod.IN_INTERVAL;
            ok = true;
            return this;
        }
        public Builder inIntervalOfDay(int dayOfWeek, TimeZone timeZone, long startTimeOfDayMillis, long stopTimeOfDayMillis){
            this.dayOfWeek = dayOfWeek;
            this.timeZone = timeZone;
            this.startTimeOfDayMillis = startTimeOfDayMillis;
            this.stopTimeOfDayMillis = stopTimeOfDayMillis;
            this.method = TimeMethod.IN_INTERVAL_OF_DAY;
            ok = true;
            return this;
        }


        public Builder inTimeInterval(int timeInterval){
            this.timeInterval = timeInterval;
            this.method = TimeMethod.IN_TIME_INTERVAL;
            ok = true;
            return this;
        }

        public TimeFence build(){
            TimeFence time;

            TimeParameter p = new TimeParameter();
            switch(this.method){
                case AROUND_TIME_INSTANT:
                    p.setTimeInstant(this.timeInstant);
                    p.setStartOffsetMillis(this.startOffsetMillis);
                    p.setStopOffsetMillis(this.stopOffsetMillis);
                    break;
                case IN_DAILY_INTERVAL:
                    p.setTimeZone(this.timeZone);
                    p.setStartTimeOfDayMillis(this.startTimeOfDayMillis);
                    p.setStopTimeOfDayMillis(this.stopTimeOfDayMillis);
                case IN_INTERVAL:
                    p.setStartTimeOfDayMillis(this.startTimeOfDayMillis);
                    p.setStopTimeOfDayMillis(this.stopTimeOfDayMillis);
                    break;
                case IN_INTERVAL_OF_DAY:
                    p.setStartTimeMillis(this.startTimeMillis);
                    p.setStopTimeMillis(this.stopTimeMillis);
                    break;
                case IN_TIME_INTERVAL:
                    p.setTimeInterval(this.timeInterval);
                    break;
            }
            time = new TimeFence(name, FenceType.TIME, action, this.method, p);
            return time;
        }
    }


}
