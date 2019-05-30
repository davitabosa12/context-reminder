package smd.ufc.br.easycontext.fence;

import com.google.android.gms.awareness.fence.AwarenessFence;

import smd.ufc.br.easycontext.fence.method.DAMethod;
import smd.ufc.br.easycontext.fence.parameter.DetectedActivityParameter;
import smd.ufc.br.easycontext.fence.method.FenceMethod;
import smd.ufc.br.easycontext.fence.parameter.FenceParameter;
import smd.ufc.br.easycontext.fence.type.FenceType;

/**
 * Created by davitabosa on 13/08/2018.
 */

public class DetectedActivityFence extends Fence {
    private final DetectedActivityParameter params;
    private final DAMethod method;

    public static int IN_VEHICLE = com.google.android.gms.awareness.fence.DetectedActivityFence.IN_VEHICLE;
    public static int RUNNING = com.google.android.gms.awareness.fence.DetectedActivityFence.RUNNING;

    public static int ON_FOOT = com.google.android.gms.awareness.fence.DetectedActivityFence.ON_FOOT;

    public static int ON_BICYCLE = com.google.android.gms.awareness.fence.DetectedActivityFence.ON_BICYCLE;

    public static int STILL = com.google.android.gms.awareness.fence.DetectedActivityFence.STILL;

    public static int WALKING = com.google.android.gms.awareness.fence.DetectedActivityFence.WALKING;

    public static int UNKNOWN = com.google.android.gms.awareness.fence.DetectedActivityFence.UNKNOWN;

    public DetectedActivityFence(String name, DAMethod method, FenceAction action, DetectedActivityParameter params) {
        super(name, FenceType.DETECTED_ACTIVITY, action, params);
        this.params = params;
        this.method = method;
    }


    @Override
    public void setMethod(FenceMethod method) {
        if(method instanceof DAMethod){

        }
    }

    @Override
    public void setParams(FenceParameter params) {

    }

    @Override
    public AwarenessFence getMethod() {
        int size = params.getActivityTypeList().size();
        int[] types = new int[size];
        for (int i = 0; i < size; i++) {
            types[i] = params.getActivityTypeList().get(i);
        }
        switch(method){
            case DA_STOPPING:
                return com.google.android.gms.awareness.fence.DetectedActivityFence.stopping(types);
            case DA_STARTING:
                return com.google.android.gms.awareness.fence.DetectedActivityFence.starting(types);
            case DA_DURING:
                return com.google.android.gms.awareness.fence.DetectedActivityFence.during(types);
            default:
                return null;
        }
    }

    @Override
    public FenceMethod getMethodName() {
        return null;
    }

    public static class Builder extends Fence.Builder{

        DetectedActivityParameter.Builder params;
        DAMethod method;
        public Builder(){
            params = new DetectedActivityParameter.Builder();
        }

        public Builder addActivityType(int activityType){
            params.addActivityType(activityType);

            return this;
        }
        public Builder during(int ...activityTypes){
            method = DAMethod.DA_DURING;
            for(int i = 0; i < activityTypes.length; i++){
                params.addActivityType(activityTypes[i]);
            }
            return this;
        }
        public Builder starting(int ...activityTypes){
            method = DAMethod.DA_STARTING;
            for(int i = 0; i < activityTypes.length; i++){
                params.addActivityType(activityTypes[i]);
            }
            return this;
        }
        public Builder stopping(int ...activityTypes){
            method = DAMethod.DA_STOPPING;
            for(int i = 0; i < activityTypes.length; i++){
                params.addActivityType(activityTypes[i]);
            }
            return this;
        }



        @Override
        public Fence build() {
            return new DetectedActivityFence(name, method, action, params.build());
        }
    }
}
