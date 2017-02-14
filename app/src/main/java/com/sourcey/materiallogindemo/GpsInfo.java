package com.sourcey.materiallogindemo;

/**
 * Created by Sim on 2017-02-14.
 */

public class GpsInfo {

    private double longitude; //경도
    private double latitude;  //위도
    private float accuracy;  //정확도

    public double getLongitude(){
        return longitude;
    }
    public double getLatitude(){
        return latitude;
    }
    public float getAccuracy(){
        return accuracy;
    }

    public void setLongitude(double longitude){
        this.longitude = longitude;
    }
    public void setLatitude(double latitude){
        this.latitude = latitude;
    }
    public void setAccuracy(float accuracy){
        this.accuracy = accuracy;
    }
}
