package com.sourcey.materiallogindemo;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MapActivity extends Activity implements OnMapReadyCallback {
    GpsInfo gi;
    GoogleMap gmap;
    AQuery aq = new AQuery(this);
    JSONArray result;
    String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        Intent intent = getIntent();
        token = intent.getStringExtra("token");
        Toast.makeText(getApplicationContext(), token, Toast.LENGTH_SHORT).show();

        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        gi = new GpsInfo(this);
        gi.getLocation();
    }

    @Override
    public void onMapReady(GoogleMap map) {
        LatLng currentLocation = new LatLng(gi.getLatitude(), gi.getLongitude());
        //LatLng currentLocation = new LatLng(gi.getLatitude(), gi.getLongitude());
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        map.setMyLocationEnabled(true);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 18));
        gmap = map;
        map.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
            @Override
            public boolean onMyLocationButtonClick() {
                LatLng currentLocation = new LatLng(gi.getLatitude(), gi.getLongitude());
                gmap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 18));
                AjaxCallback<JSONArray> cb = new AjaxCallback<JSONArray>() {
                    @Override
                    public void callback(String url, JSONArray object, AjaxStatus status) {
                        if (object != null) {
                            result = object;
                        }
                    }
                };
                aq.ajax("http://layer7.kr:8282/getMemoryTree?lat=" + gi.getLatitude() + "&lng=" + gi.getLongitude(), JSONArray.class, cb);
                Toast.makeText(getApplicationContext(), result.toString(), Toast.LENGTH_SHORT).show();

                return true;
            }
        });
    }
}