package com.sourcey.materiallogindemo;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Looper;
import android.support.v4.app.ActivityCompat;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import android.util.Log;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class MapActivity extends Activity implements OnMapReadyCallback {
    GpsInfo gi;
    private TimerTask getLocationHandler;
    private Timer mTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        gi = new GpsInfo(this);
        gi.getLocation();

        getLocationHandler = new TimerTask() {
            @Override
            public void run() {
                if(Looper.myLooper() == null)
                    Looper.prepare();
                gi.getLocation();
                LatLng currentLocation = new LatLng(1.1,2.2);
                //LatLng currentLocation = new LatLng(gi.getLatitude(), gi.getLongitude());
                //TODO 위치 업데이트 / 거리 체크 -> DB 쿼리 및 마커 표시
            }
        };
        mTimer = new Timer();
        mTimer.schedule(getLocationHandler, 10000, 10000);
    }

    @Override
    public void onMapReady(GoogleMap map) {
        LatLng currentLocation = new LatLng(gi.getLatitude(), gi.getLongitude());
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        map.setMyLocationEnabled(true);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 23));
    }
}