package com.karthi.electionhack.app;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings.Secure;
import android.support.v4.app.NotificationCompat;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class LauncherActivity extends Activity {

    //Variable declarations
    String deviceId;
    RelativeLayout bottomLayout;
    String myBoothId = null;
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


    JSONArray booths;
    HashMap<Marker, String> markers;
    MarkerOptions myLocationMarker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
        getDeviceId();

        bottomLayout = (RelativeLayout) findViewById(R.id.bottomLayout);
        //Initiate shared preferences
        sharedPreferences = getSharedPreferences("Preferences", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        myBoothId = sharedPreferences.getString("boothId", null);

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

        // create marker
        myLocationMarker = new MarkerOptions().position(new LatLng(latitude, longitude)).title("Your Location ");

        try {
            // Loading map
            initilizeMap();

        } catch (Exception e) {
            e.printStackTrace();
        }


        googleMap.getUiSettings().setMyLocationButtonEnabled(true);

        cameraPosition = new CameraPosition.Builder().target(
                new LatLng(latitude, longitude)).zoom(10).build();
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {

                //Make API call to neem service. Currently marker value hardcoded
                String boothId = markers.get(marker);
                new UpdateBoothId().execute("createPerson", deviceId, "Bangalore", String.valueOf(latitude), String.valueOf(longitude), boothId);
                bottomLayout.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(), "Thanks for choosing your booth!", Toast.LENGTH_LONG).show();

                editor = sharedPreferences.edit();
                editor.putString("boothId", boothId);
                editor.commit();

                //Calling booth activity
                Intent newIntent = new Intent(LauncherActivity.this, BoothActivity.class);
                startActivity(newIntent);
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                return false;
            }
        });

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
//                Intent mainIntent = new Intent(LauncherActivity.this, BoothActivity.class);
//                LauncherActivity.this.startActivity(mainIntent);
//                LauncherActivity.this.finish();


                NotificationCompat.Builder builder =
                        new NotificationCompat.Builder(LauncherActivity.this)
                                .setSmallIcon(R.drawable.ic_launcher)
                                .setTicker("Time to vote!")
                                .setContentTitle("Update on your polling booth")
                                .setContentText("The crowd seems to be less on your polling booth. You can start from your home now");

                Intent notificationIntent = new Intent(LauncherActivity.this, BoothActivity.class);
                PendingIntent contentIntent = PendingIntent.getActivity(LauncherActivity.this, 0, notificationIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT);
                builder.setContentIntent(contentIntent);

                // Add as notification
                NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                manager.notify(1, builder.build());



            }
        }, 3000);




        new FetchNearbyBooths().execute();

        if (myBoothId != null) {
            //Calling booth activity
            Intent newIntent = new Intent(LauncherActivity.this, BoothActivity.class);
            startActivity(newIntent);
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        }


    }

    /**
     * function to load map. If map is not created it will create it for you
     */
    private void initilizeMap() {
        if (googleMap == null) {
            googleMap = ((MapFragment) getFragmentManager().findFragmentById(
                    R.id.map)).getMap();
            // adding marker on the user's current location
            googleMap.addMarker(myLocationMarker);

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
            MarkerOptions marker = new MarkerOptions().position(new LatLng(latitude, longitude)).title("Your Location ");


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
            editor = sharedPreferences.edit();
            editor.remove("boothId");
            editor.commit();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    /**
     * Async task class to get json by making HTTP call
     */
    private class UpdateBoothId extends AsyncTask<String, Void, Void> {

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
    }

    private class FetchNearbyBooths extends AsyncTask<String, Void, Void> {

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
            String jsonStr = sh.makeServiceCall(AppConstants.SERVER_BASE_URL + "&method=fetchAllPollingBooths" +
                    "&my_lat=" + latitude + "&my_long=" + longitude + "&format=json"
                    , ServiceHandler.GET);
            Log.d("Response: ", "> " + jsonStr);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    booths = (JSONArray) jsonObj.get("Result");

                    LauncherActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                markers = new HashMap<Marker, String>();

                                for (int i = 0; i < booths.length(); i++) {
                                    JSONObject booth = (JSONObject) booths.get(i);
                                    String boothId = booth.getString("name");
                                    double poll_lat = Double.parseDouble(booth.getString("poll_lat"));
                                    double poll_long = Double.parseDouble(booth.getString("poll_long"));
                                    String poll_address = booth.getString("address");

                                    MarkerOptions markerOpts = new MarkerOptions().position(new LatLng(poll_lat, poll_long)).title(poll_address);

                                    // GREEN color icon
                                    markerOpts.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                                    Marker m = googleMap.addMarker(markerOpts);
                                    markers.put(m, boothId);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });

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
