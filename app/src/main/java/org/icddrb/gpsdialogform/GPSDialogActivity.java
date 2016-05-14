package org.icddrb.gpsdialogform;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

public class GPSDialogActivity extends Activity implements LocationListener {
    Location currentLocation;
    double currentLatitude, currentLongitude;

    EditText editTextID, editTextLat, editTextLong, editTextLandmarkName, editTextEstablished,
            editTextContact, editTextNote, editTextAccuracy, editTextSatCount;
    String mUserID = "", mLat = "0", mLong = "0", mLandmarkName = "", mEstablished = "", mContact = "",
            mNote = "", mAccuracy = "0", mSatCount = "0";

    RadioGroup radioGroupLocType;
    RadioButton radioButtonBari, radioButtonLandmark;
    Spinner spinnerLandmarkList;
    Button btnSave;
    LocationManager locationManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        turnGPSOn();
        FindLocation();
        locationManager = (LocationManager) this
                .getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            //return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        setContentView(R.layout.activity_gpsdialog);


        editTextID = (EditText) findViewById(R.id.editTextUserID);
        editTextLat = (EditText) findViewById(R.id.editTextLatitude);
        editTextLong = (EditText) findViewById(R.id.editTextLongitude);
        editTextLandmarkName = (EditText) findViewById(R.id.editTextLandName);
        editTextEstablished = (EditText) findViewById(R.id.editTextLandEstablised);
        editTextContact = (EditText) findViewById(R.id.editTextContactNo);
        editTextNote = (EditText) findViewById(R.id.editTextNote);
        editTextAccuracy = (EditText) findViewById(R.id.editTextAccuracy);
        editTextSatCount = (EditText) findViewById(R.id.editTextSatelite);

        radioGroupLocType = (RadioGroup) findViewById(R.id.radioGroupLocType);
        radioButtonBari = (RadioButton) findViewById(R.id.radioButtonBari);
        radioButtonLandmark = (RadioButton) findViewById(R.id.radioButtonLandmark);
        spinnerLandmarkList = (Spinner) findViewById(R.id.spinnerLandType);
        spinnerLandmarkList.setSelection(1);

        btnSave = (Button) findViewById(R.id.buttonSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveGPSFormData();
            }
        });

    }

    private void saveGPSFormData() {
        mUserID = editTextID.getText().toString();
        mLat = editTextLat.getText().toString();
        mLong = editTextLong.getText().toString();
        mLandmarkName = editTextLandmarkName.getText().toString();
        mEstablished = editTextEstablished.getText().toString();
        mContact = editTextContact.getText().toString();
        mNote = editTextNote.getText().toString();
        mAccuracy = editTextAccuracy.getText().toString();
        mSatCount = editTextSatCount.getText().toString();
    }

    //GPS Reading
    //............................................................................................
    public void FindLocation() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission
                (this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

/*        LocationListener locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                updateLocation(location);
            }

            public void onStatusChanged(String provider, int status,
                                        Bundle extras) {
            }

            public void onProviderEnabled(String provider) {
            }

            public void onProviderDisabled(String provider) {
            }
        };*/
      //  locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
    }

    void updateLocation(Location location) {
      //  int nsat=location.getExtras().getInt("satellites", -1);
        mSatCount = String.valueOf(location.getExtras().getInt("satellites", -1));
        currentLocation = location;
        currentLatitude = currentLocation.getLatitude();
        currentLongitude = currentLocation.getLongitude();
        mAccuracy = String.valueOf(currentLocation.getAccuracy());
        editTextLat.setText(String.valueOf(currentLatitude));
        editTextLong.setText(String.valueOf(currentLongitude));
        editTextAccuracy.setText(String.valueOf(mAccuracy));
        editTextSatCount.setText(String.valueOf(mSatCount));
    }

    // Method to turn on GPS
    public void turnGPSOn(){
        try
        {
            String provider = Settings.Secure.getString(getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);

            if(!provider.contains("gps")){ //if gps is disabled
                final Intent poke = new Intent();
                poke.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider");
                poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
                poke.setData(Uri.parse("3"));
                sendBroadcast(poke);
            }
        }
        catch (Exception e) {

        }
    }

    // Method to turn off the GPS
    public void turnGPSOff(){
        String provider = Settings.Secure.getString(getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);

        if(provider.contains("gps")){ //if gps is enabled
            final Intent poke = new Intent();
            poke.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider");
            poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
            poke.setData(Uri.parse("3"));
            sendBroadcast(poke);
        }
    }

    // turning off the GPS if its in on state. to avoid the battery drain.
    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        turnGPSOff();
    }

    @Override
    public void onLocationChanged(Location location) {
        updateLocation(location);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}

