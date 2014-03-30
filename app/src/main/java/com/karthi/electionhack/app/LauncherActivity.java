package com.karthi.electionhack.app;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings.Secure;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.karthi.electionhack.utils.AppConstants;
import com.karthi.electionhack.utils.GPSTracker;
import com.karthi.electionhack.utils.ServiceHandler;

import org.json.JSONException;
import org.json.JSONObject;

public class LauncherActivity extends Activity {

    //Variable declarations
    String deviceId;
    RelativeLayout bottomLayout;
    // GPSTracker class
    GPSTracker gps;

    ProgressDialog pDialog;
    double latitude;
    double longitude;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    // Google Map
    GoogleMap googleMap;
    CameraPosition cameraPosition;
    MarkerOptions marker;


    //Hardcoded lat, lan values
    double[] latitudes_array = new double[]{13.08532, 13.09706, 13.19854, 12.92263, 13.13436};
    double[] longitudes_array = new double[]{77.55364, 77.81748, 77.69942, 77.60779, 77.39919};
    String[] titleArray = new String[]{"Govt Higher Primary School, Jalahalli,Yelahanka", "Govt. Higher Primary School - 10, Hosakote", "Govt, Lower Primary School, Kote", "Anganawadi A kendra, Anekal", "Government Higher Primary School, Mylanahalli"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
        getDeviceId();

        bottomLayout = (RelativeLayout) findViewById(R.id.bottomLayout);
        //Initiate shared preferences
        sharedPreferences = getSharedPreferences("Preferences", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        boolean haveWeShownPreferences = sharedPreferences.getBoolean("firstTime", false);


        // create class object
        gps = new GPSTracker(LauncherActivity.this);

        // check if GPS enabled
        if (gps.canGetLocation()) {
            latitude = gps.getLatitude();
            longitude = gps.getLongitude();
        } else {

            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
            gps.showSettingsAlert();
        }


//        if (!haveWeShownPreferences) {
//            //launch the app for the first time
//
//        } else {
//            //opening the app from second time onwards
//            // new MakeAPICall().execute("createPollingStatus", deviceId, "Bangalore", String.valueOf(latitude), String.valueOf(longitude));
//
//        }

        editor.putBoolean("firstTime", true);
        editor.commit();


        // create marker
        marker = new MarkerOptions().position(new LatLng(latitude, longitude)).title("Your Location ");


        try {
            // Loading map
            initilizeMap();

        } catch (Exception e) {
            e.printStackTrace();
        }


        googleMap.getUiSettings().setMyLocationButtonEnabled(true);


        //Setting polling booths around.
        //Hard coding right now :(


        for (int i = 0; i < 5; i++) {
            marker = new MarkerOptions().position(new LatLng(latitudes_array[i], longitudes_array[i])).title(titleArray[i]);

            // GREEN color icon
            marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
            googleMap.addMarker(marker);


        }


        cameraPosition = new CameraPosition.Builder().target(
                new LatLng(latitude, longitude)).zoom(10).build();
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));


        googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {

                //Make API call to neem service. Currently marker value hardcoded
                new MakeAPICall().execute("createPerson", deviceId, "Bangalore", String.valueOf(latitude), String.valueOf(longitude), "2");
                bottomLayout.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(), "Thanks for choosing your booth!", Toast.LENGTH_LONG).show();
                //Calling booth activity
                Intent newIntent = new Intent(LauncherActivity.this, BoothActivity.class);
                startActivity(newIntent);
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                return false;
            }
        });

    }

    /**
     * function to load map. If map is not created it will create it for you
     */
    private void initilizeMap() {
        if (googleMap == null) {
            googleMap = ((MapFragment) getFragmentManager().findFragmentById(
                    R.id.map)).getMap();
            // adding marker on the user's current location
            googleMap.addMarker(marker);


            // check if map is created successfully or not
            if (googleMap == null) {
                Toast.makeText(getApplicationContext(),
                        "Sorry! unable to create maps", Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        initilizeMap();
    }

    private final LocationListener locationListener = new LocationListener() {
        public void onLocationChanged(Location location) {
            longitude = location.getLongitude();
            latitude = location.getLatitude();


            // create marker
            marker = new MarkerOptions().position(new LatLng(latitude, longitude)).title("Your Location ");


            cameraPosition = new CameraPosition.Builder().target(
                    new LatLng(latitude, longitude)).zoom(12).build();

            googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));


        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {
            //System.out.println("network "+"onstatus changed"+ s);
        }

        @Override
        public void onProviderEnabled(String s) {
            //System.out.println("network "+"on provider enabled"+ s);
        }

        @Override
        public void onProviderDisabled(String s) {
            //System.out.println("network "+"on provider disabled"+ s);
        }
    };


    public String getDeviceId() {

        try {
            deviceId = Secure.getString(getApplicationContext().getContentResolver(),
                    Secure.ANDROID_ID);
        } catch (Exception e) {
            return null;
        }
        return deviceId;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.launcher, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    /**
     * Async task class to get json by making HTTP call
     */
    private class MakeAPICall extends AsyncTask<String, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(LauncherActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(String... params) {
            // Creating service handler class instance
            ServiceHandler sh = new ServiceHandler();

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(AppConstants.SERVER_BASE_URL + "&method=" + params[0] + "&name=" + params[1] +
                    "&home_lat=" + latitude + "&home_long=" + longitude + "&booth_id=" + params[5] + "&format=json"
                    , ServiceHandler.GET);

            Log.d("Response: ", "> " + jsonStr);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Log.e("ServiceHandler", "Couldn't get any data from the url");
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();
          
        }

    }


}
