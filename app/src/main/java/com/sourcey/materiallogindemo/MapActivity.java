package com.sourcey.materiallogindemo;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.games.snapshot.Snapshots;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MapActivity extends Activity implements OnMapReadyCallback {
    GpsInfo gi;
    GoogleMap gmap;
    AQuery aq = new AQuery(this);
    String token;
    CommunicationClass comm;
    JSONObject thread_object;
    JSONArray thread_array;
    JSONArray markerInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        Intent intent = getIntent();
        token = intent.getStringExtra("token");
        comm = new CommunicationClass(token, this);
        markerInfo = new JSONArray();

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

        map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            public boolean onMarkerClick(Marker marker) {
                // TODO Auto-generated method stub

                Toast.makeText(MapActivity.this, "Oh my god!!", Toast.LENGTH_SHORT).show();
                return false;
            }
        });

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

                Thread thread = new Thread() {
                    public void run() {
                        String abc = comm.getContent("http://layer7.kr:8282/memorys/getMemoryTree?lat=" + gi.getLatitude() + "&lng=" + gi.getLongitude(), "", "GET");
                        Log.e("w", abc);
                    }
                };
                thread.start();
                try {
                    thread.join();
                    for(int i = 0; i < thread_array.length(); i ++)
                    {
                        MarkerOptions mak = new MarkerOptions();
                        mak.position(new LatLng(Float.parseFloat(thread_array.getJSONObject(i).getString("lat")),Float.parseFloat(thread_array.getJSONObject(i).getString("lng"))));
                        mak.title(thread_array.getJSONObject(i).getString("title"));
                        gmap.addMarker(mak);
                        markerInfo.put(thread_array.getJSONObject(i));
                    }


                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                return true;
            }
        });
    }
}