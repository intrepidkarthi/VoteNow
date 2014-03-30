package com.karthi.electionhack.app;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.karthi.electionhack.utils.AppConstants;
import com.karthi.electionhack.utils.ServiceHandler;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by karthi on 30/03/14.
 */
public class BoothActivity extends Activity {

    ProgressDialog pDialog;

    // Google Map
    private GoogleMap googleMap;
    CameraPosition cameraPosition;
    MarkerOptions marker;
    double latitude = 13.08532;
    double longitude = 77.55364;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booth);

        //Setting title
        getActionBar().setTitle("Latest Booth Info");

        // create marker
        marker = new MarkerOptions().position(new LatLng(latitude, longitude)).title("Your Booth Location ");
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
        googleMap.getUiSettings().setMyLocationButtonEnabled(true);
        cameraPosition = new CameraPosition.Builder().target(
                new LatLng(latitude, longitude)).zoom(10).build();
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));





    }




    /**
     * Async task class to get json by making HTTP call
     */
    private class MakeAPICall extends AsyncTask<String, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(BoothActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(String... params) {
            // Creating service handler class instance
            ServiceHandler sh = new ServiceHandler();

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(AppConstants.SERVER_BASE_URL + "&method=" + params[0]+"&booth_id=232&&format=json"
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
            /**
             * Updating parsed JSON data into ListView
             * */
        }

    }





}
