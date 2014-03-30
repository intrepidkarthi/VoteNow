package com.karthi.electionhack.app;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
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

        getActionBar().setTitle("Survey Plotter");
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

//        cameraPosition = new CameraPosition.Builder().target(
//                new LatLng(latitude, longitude)).zoom(10).build();
//        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        MarkerOptions markerOpts1 = new MarkerOptions().position(new LatLng(13.08532,77.55364)).title("Smriti");
        markerOpts1.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW));
        googleMap.addMarker(markerOpts1);

        MarkerOptions markerOpts2 = new MarkerOptions().position(new LatLng(13.08632,77.56364)).title("Karthik");
        markerOpts2.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
        googleMap.addMarker(markerOpts2);

        MarkerOptions markerOpts3 = new MarkerOptions().position(new LatLng(13.08132,77.55864)).title("Vasan");
        markerOpts3.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
        googleMap.addMarker(markerOpts3);

        MarkerOptions markerOpts4 = new MarkerOptions().position(new LatLng(13.09532,77.55364)).title("Subhendu");
        markerOpts4.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));
        googleMap.addMarker(markerOpts4);

        MarkerOptions markerOpts5 = new MarkerOptions().position(new LatLng(13.09432,77.55364)).title("Saroj");
        markerOpts5.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW));
        googleMap.addMarker(markerOpts5);

        MarkerOptions markerOpts6 = new MarkerOptions().position(new LatLng(13.09132,77.55364)).title("Microsoft");
        markerOpts6.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
        googleMap.addMarker(markerOpts6);


        MarkerOptions markerOpts7 = new MarkerOptions().position(new LatLng(13.09582,77.55364)).title("Prasanna");
        markerOpts7.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW));
        googleMap.addMarker(markerOpts7);


        MarkerOptions markerOpts8 = new MarkerOptions().position(new LatLng(13.09532,77.55264)).title("Prasanna");
        markerOpts8.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));
        googleMap.addMarker(markerOpts8);

        MarkerOptions markerOpts9 = new MarkerOptions().position(new LatLng(13.09532,77.55164)).title("Prasanna");
        markerOpts9.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
        googleMap.addMarker(markerOpts9);

        MarkerOptions markerOpts10 = new MarkerOptions().position(new LatLng(13.09532,77.55324)).title("Prasanna");
        markerOpts10.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
        googleMap.addMarker(markerOpts10);

        MarkerOptions markerOpts11 = new MarkerOptions().position(new LatLng(13.09522,77.55394)).title("Prasanna");
        markerOpts11.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));
        googleMap.addMarker(markerOpts11);

        MarkerOptions markerOpts12 = new MarkerOptions().position(new LatLng(13.09562,77.55764)).title("Prasanna");
        markerOpts12.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
        googleMap.addMarker(markerOpts12);

        MarkerOptions markerOpts13 = new MarkerOptions().position(new LatLng(13.09432,77.55164)).title("Prasanna");
        markerOpts13.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW));
        googleMap.addMarker(markerOpts13);

        MarkerOptions markerOpts14 = new MarkerOptions().position(new LatLng(13.09232,77.55364)).title("Prasanna");
        markerOpts14.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));
        googleMap.addMarker(markerOpts14);

        MarkerOptions markerOpts15 = new MarkerOptions().position(new LatLng(13.09512,77.55384)).title("Prasanna");
        markerOpts15.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));
        googleMap.addMarker(markerOpts15);

        MarkerOptions markerOpts16 = new MarkerOptions().position(new LatLng(13.09539,77.55374)).title("Prasanna");
        markerOpts16.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));
        googleMap.addMarker(markerOpts16);

        MarkerOptions markerOpts17 = new MarkerOptions().position(new LatLng(13.09892,77.55674)).title("Prasanna");
        markerOpts17.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));
        googleMap.addMarker(markerOpts17);



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
