package com.karthi.electionhack.app;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.karthi.electionhack.utils.GPSTracker;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * Created by karthi on 30/03/14.
 */
public class HeatMapActivity extends Activity {

    // Google Map
    GoogleMap googleMap;
    // GPSTracker class
    GPSTracker gps;
    //MarkerOptions myLocationMarker;
    CameraPosition cameraPosition;


    JSONArray booths;
    HashMap<Marker, String> markers;
    MarkerOptions myLocationMarker;
    double latitude;
    double longitude;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_path);

        if (googleMap == null) {
            googleMap = ((MapFragment) getFragmentManager().findFragmentById(
                    R.id.map)).getMap();
            // adding marker on the user's current location
           // googleMap.addMarker(myLocationMarker);

            // check if map is created successfully or not
            if (googleMap == null) {
                Toast.makeText(getApplicationContext(),
                        "Sorry! unable to create maps", Toast.LENGTH_SHORT)
                        .show();
            }
        }


        googleMap.getUiSettings().setMyLocationButtonEnabled(true);

        cameraPosition = new CameraPosition.Builder().target(
                new LatLng(latitude, longitude)).zoom(10).build();
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {


                //Calling booth activity
                //Intent newIntent = new Intent(HeatMapActivity.this, BoothActivity.class);
                //startActivity(newIntent);
                //overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                return false;
            }
        });


    }



    private void addHeatMap() {
        List<LatLng> list = null;

        // Get the data: latitude/longitude positions of police stations.
        try {
            list = new ArrayList<LatLng>();
            list.add(new LatLng(13.08532,77.55364));
            list.add(new LatLng(13.08632,77.56364));
            list.add(new LatLng(13.08132,77.55864));
            list.add(new LatLng(13.09532,77.51364));
        } catch (Exception e) {
            Toast.makeText(this, "Problem reading list of locations.", Toast.LENGTH_LONG).show();
        }
    }


}
