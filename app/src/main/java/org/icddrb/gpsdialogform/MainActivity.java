package org.icddrb.gpsdialogform;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

    Button btnGPS;
    Button btnGPS1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnGPS = (Button) findViewById(R.id.buttonGPS);
        btnGPS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),GPSDialogActivity.class);
                startActivity(intent);
            }
        });

        btnGPS1 = (Button) findViewById(R.id.buttonGPS1);
        btnGPS1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),GPSDialogActivityGG.class);
                startActivity(intent);
            }
        });


    }
}