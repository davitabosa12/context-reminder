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

public class LocationRule implements Rule{
    private LocationMethod method;

    private double latitude;
    private double longitude;
    private double radius;
    private long dwellTimeMillis;

    public LocationRule() {

    }

    public LocationRule entering(double latitude, double longitude, double radius){
        LocationRule locationRule = new LocationRule();
        locationRule.setLatitude(latitude);
        locationRule.setLongitude(longitude);
        locationRule.setRadius(radius);
        this.method = LocationMethod.LOCATION_ENTERING;
        return this;
    }
    public LocationRule exiting(double latitude, double longitude, double radius){
        LocationRule locationRule = new LocationRule();
        locationRule.setLatitude(latitude);
        locationRule.setLongitude(longitude);
        locationRule.setRadius(radius);
        this.method = LocationMethod.LOCATION_EXITING;
        return this;

    }
    public LocationRule in(double latitude, double longitude, double radius, long dwell){
        LocationRule locationRule = new LocationRule();
        locationRule.setLatitude(latitude);
        locationRule.setLongitude(longitude);
        locationRule.setRadius(radius);
        locationRule.setDwellTimeMillis(dwell);
        this.method = LocationMethod.LOCATION_IN;
        return this;
    }

    @SuppressLint("MissingPermission")
    @Override
    public AwarenessFence getAwarenessFence() {
        switch(method){
            case LOCATION_ENTERING:
                return com.google.android.gms.awareness.fence.LocationFence.entering(latitude,longitude,radius);
            case LOCATION_EXITING:
                return com.google.android.gms.awareness.fence.LocationFence.exiting(latitude,longitude,radius);
            case LOCATION_IN:
                return com.google.android.gms.awareness.fence.LocationFence.in(latitude,longitude,radius,dwellTimeMillis);
            default:
                return null;
        }
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }
    public long getDwellTimeMillis() {
        return dwellTimeMillis;
    }
    public void setDwellTimeMillis(long dwellTimeMillis) {
        this.dwellTimeMillis = dwellTimeMillis;
    }

}
