package com.karthi.electionhack.app;

import android.app.Activity;
import android.os.Bundle;

import com.google.android.gms.maps.GoogleMap;

/**
 * Created by karthi on 30/03/14.
 */
public class PathActivity extends Activity {

    // Google Map
    GoogleMap googleMap;
    public final static String MODE_DRIVING = "driving";
    public final static String MODE_WALKING = "walking";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_path);

    }


}
