package com.kidsafe.jbyrne.kidsafefusedv2;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class MyActivity extends Activity implements
        LocationListener, GoogleApiClient.ConnectionCallbacks {

    //// CONSTANTS
    //Update URL
    //private final String UPDATE_URL = "http://192.168.56.1/KidSafe/receive_location_update.php";
    //private final String UPDATE_URL = "http://192.168.101.10/kidsafe/receive_location_update.php";
    private final String UPDATE_URL = "http://www.kidsafe.hostoi.com/receive_location_update.php";
    // JSON Node names
    private static final String TAG_SUCCESS = "success";

    //JSONParser jsonParser = new JSONParser();

    //// VARIABLES
    private GoogleApiClient apiClient;
    private String latitude;
    private String longitude;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        GoogleApiClient.Builder builder = new GoogleApiClient.Builder(this);
        //Add the location api
        builder.addApi(LocationServices.API);
        //Tell it what to call back to when it has connected to the Google Service
        builder.addConnectionCallbacks(this);
        apiClient = builder.build();
        apiClient.connect();
        System.out.println("kid***** GOT HERE OnCreate ***************");

        setContentView(R.layout.activity_my);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onConnected(Bundle bundle) {
        System.out.println("kid***** GOT HERE OnConnected ***************");
        LocationRequest locnReq = LocationRequest.create();
        LocationServices.FusedLocationApi.requestLocationUpdates(
                apiClient, locnReq, this);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onLocationChanged(Location location) {
        TextView txtGpsDump = (TextView) this.findViewById(R.id.txtGpsDump);
        txtGpsDump.setText("kid***" +
                location.getLatitude() + " \n" +
                location.getLongitude() + " \n" +
                location.getAccuracy() + " \n" +
                location.getAltitude() + " \n" +
                location.getSpeed() + " \n" +
                location.getTime() + " \n" +
                location.getBearing());

        this.latitude = String.valueOf(location.getLatitude());
        this.longitude = String.valueOf(location.getLongitude());

        System.out.println("kid***** GOT HERE OnLocationChanged ***************");
/*        System.out.println("kid***" +
                location.getLatitude() + " \n" +
                location.getLongitude() + " \n" +
                location.getAccuracy() + " \n" +
                location.getAltitude() + " \n" +
                location.getSpeed() + " \n" +
                location.getTime() + " \n" +
                location.getBearing());
               */

       /* TelephonyManager telephonyManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        String phone_id = InternalController.sha1Hash(telephonyManager.getDeviceId());

        String phone_lat = String.valueOf(latitude);
        String phone_lng = String.valueOf(longitude);

        //Build my record
        ArrayList<NameValuePair> recordBuild = new ArrayList<>();
        recordBuild.add(new BasicNameValuePair("phone_id", phone_id));
        recordBuild.add(new BasicNameValuePair("phone_lat", phone_lat));
        recordBuild.add(new BasicNameValuePair("phone_lng", phone_lng));

        //Create JSON Object to generate my HTTP request.
        //JSONObject json = InternalController.makeHttpRequest(UPDATE_URL, "POST", recordBuild);
        JSONObject json = InternalController.makeHttpRequest(UPDATE_URL, "POST", recordBuild);
        txtGpsDump.setText(txtGpsDump.getText() + "\n" + json);
        //Check log cat for update string
        //Log.d("KidSafe Update", json.toString());

        try {
            int success = 1;//json.getInt(TAG_SUCCESS);

            if (success == 1) {
                // successfully sent an update
                System.out.println("kid**********************************");
                System.out.println("kid*********** Success !!! **********");
                System.out.println("kid**********************************");

                //finish();
            } else {
                System.out.println("kid**********************************");
                System.out.println("kid*********** FAILURE !!! **********");
                System.out.println("kid**********************************");
            }
        } catch (Exception e){//JSONException e) {
            e.printStackTrace();
        }

*/
        new UpdateLocation().execute();
        //finish();

    }


   public class UpdateLocation extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {

            TelephonyManager telephonyManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
            String phone_id = InternalController.sha1Hash(telephonyManager.getDeviceId());

            String phone_lat = String.valueOf(latitude);
            String phone_lng = String.valueOf(longitude);

            //Build my record
            ArrayList<NameValuePair> recordBuild = new ArrayList<>();
            recordBuild.add(new BasicNameValuePair("phone_id", phone_id));
            recordBuild.add(new BasicNameValuePair("phone_lat", phone_lat));
            recordBuild.add(new BasicNameValuePair("phone_lng", phone_lng));

            //Create JSON Object to generate my HTTP request.
            //JSONObject json = jsonParser.makeHttpRequest(UPDATE_URL, "POST", recordBuild);
            JSONObject json = InternalController.makeHttpRequest(UPDATE_URL, "POST", recordBuild);

            //TextView txtGpsDump = (TextView) findViewById(R.id.txtGpsDump);
            //txtGpsDump.setText(txtGpsDump.getText() + "\n" + json);

            //Check log cat for update string
            //Log.d("KidSafe Update", json.toString());

            try {
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    // successfully sent an update
                    System.out.println("kid**********************************");
                    System.out.println("kid*********** Success !!! **********");
                    System.out.println("kid**********************************");

                    finish();
                } else {
                    // failed to create update
                }
            } catch (Exception e){//JSONException e) {
                e.printStackTrace();
            }

            return null;
        }
    }
}
