package smd.ufc.br.easycontext.fence;

import android.annotation.SuppressLint;

import com.google.android.gms.awareness.fence.AwarenessFence;

import smd.ufc.br.easycontext.fence.method.FenceMethod;
import smd.ufc.br.easycontext.fence.parameter.FenceParameter;
import smd.ufc.br.easycontext.fence.type.FenceType;
import smd.ufc.br.easycontext.fence.method.LocationMethod;
import smd.ufc.br.easycontext.fence.parameter.LocationParameter;

/**
 * Created by davitabosa on 13/08/2018.
 */

public class LocationFence extends Fence {

    private LocationMethod method;
    private LocationParameter params;

    public LocationFence(String name, LocationMethod method, FenceAction action, LocationParameter params) {
        super(name, FenceType.LOCATION, action, params);
        this.params = params;
        this.method = method;

    }

    @Override
    public void setMethod(FenceMethod method) {
        if(method instanceof LocationMethod){
            this.method = (LocationMethod) method;
        }
    }

    @Override
    public void setParams(FenceParameter params) {

    }

    @SuppressLint("MissingPermission")
    @Override
    public AwarenessFence getMethod() {
        //TODO: Check if params are passed
        switch(method){
            case LOCATION_ENTERING:
                return com.google.android.gms.awareness.fence.LocationFence.entering(params.getLatitude(),params.getLongitude(),params.getRadius());
            case LOCATION_EXITING:
                return com.google.android.gms.awareness.fence.LocationFence.exiting(params.getLatitude(),params.getLongitude(),params.getRadius());
            case LOCATION_IN:
                return com.google.android.gms.awareness.fence.LocationFence.in(params.getLatitude(),params.getLongitude(),params.getRadius(),params.getDwellTimeMillis());
            default:
                return null;
        }
    }

    @Override
    public FenceMethod getMethodName() {
        return method;
    }

    public static class Builder extends Fence.Builder{
        LocationParameter.Builder params;
        LocationMethod method;
        long dwell;
        public Builder(){
            this.params = new LocationParameter.Builder();
        }
        public Builder entering(double latitude, double longitude, double radius){
            params.setLatitude(latitude);
            params.setLongitude(longitude);
            params.setRadius(radius);
            this.method = LocationMethod.LOCATION_ENTERING;
            return this;
        }
        public Builder exiting(double latitude, double longitude, double radius){
            params.setLatitude(latitude);
            params.setLongitude(longitude);
            params.setRadius(radius);
            this.method = LocationMethod.LOCATION_EXITING;
            return this;

        }
        public Builder in(double latitude, double longitude, double radius, long dwell){
            params.setLatitude(latitude);
            params.setLongitude(longitude);
            params.setRadius(radius);
            params.setDwellTimeMillis(dwell);
            this.method = LocationMethod.LOCATION_IN;
            return this;
        }
        @Override
        public Fence build() {
            return new LocationFence(name, method, action, params.build());
        }
    }
}
