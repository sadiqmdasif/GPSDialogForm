package org.icddrb.gpsdialogform;

import android.app.Activity;
import android.location.Location;
import android.os.Bundle;
import android.widget.EditText;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

public class GPSDialogActivityGG extends Activity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener {

    EditText editTextLat, editTextLong, editTextAccuracy, editTextSatCount;
    Location mLastLocation;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    String lat, lon;
    private String mAccuracy;
    private String mSatCount;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gpsdialog_gg_activity);

        editTextLat = (EditText) findViewById(R.id.editTextLatitude1);
        editTextLong = (EditText) findViewById(R.id.editTextLongitude1);
        editTextAccuracy = (EditText) findViewById(R.id.editTextAccuracy1);
        editTextSatCount = (EditText) findViewById(R.id.editTextSatelite1);

        buildGoogleApiClient();
    }


    @Override
    public void onConnected(Bundle bundle) {


        mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(100); // Update location every second

        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);


        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        if (mLastLocation != null) {
            lat = String.valueOf(mLastLocation.getLatitude());
            lon = String.valueOf(mLastLocation.getLongitude());

        }
        updateUI();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onLocationChanged(Location location) {
        lat = String.valueOf(location.getLatitude());
        lon = String.valueOf(location.getLongitude());
        mAccuracy = String.valueOf(location.getAccuracy());
//        mSatCount = String.valueOf(location.getExtras().getInt("satellites", -1));

        editTextLat.setText(String.valueOf(lat));
        editTextLong.setText(String.valueOf(lon));
        editTextAccuracy.setText(String.valueOf(mAccuracy));
        editTextSatCount.setText(String.valueOf(mSatCount));
      // updateUI();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        buildGoogleApiClient();
    }

    synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();


    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mGoogleApiClient.disconnect();
    }

    void updateUI() {

    }
}