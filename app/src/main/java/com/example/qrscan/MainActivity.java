package com.example.qrscan;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.sql.Connection;

public class MainActivity extends AppCompatActivity {
    //connect database
    dbConnect mDbConnect = new dbConnect();

    //initialize variable
    Button btSCan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //db connect test
        new Thread(new Runnable() {
            @Override
            public void run() {
                Connection connection = mDbConnect.getConnection();
            }
        });


        //Assign variable
        btSCan=findViewById(R.id.bt_scan);
        btSCan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Initialize intent integrator
                IntentIntegrator intentIntegrator = new IntentIntegrator(
                        MainActivity.this
                );
                //Set prompt text
                intentIntegrator.setPrompt("For flash use volume up key");
                //set beep
                intentIntegrator.setBeepEnabled(true);
                //Locked orientation
                intentIntegrator.setOrientationLocked(true);
                //Set capture activity
                intentIntegrator.setCaptureActivity(Capture.class);
                //Initiate scan
                intentIntegrator.initiateScan();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Initialize intent result
        IntentResult intentResult = IntentIntegrator.parseActivityResult(
                requestCode,resultCode,data
        );
        //Check condition
        if(intentResult.getContents()!=null){
            //When the content is not null
            //Initialize alert dialog
            AlertDialog.Builder builder = new AlertDialog.Builder(
                    MainActivity.this
            );
            //set title
            builder.setTitle("Result");
            //set message
            builder.setMessage(intentResult.getContents());
            //set positive button
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            //show alert dialog
            builder.show();
        }else{
            //when result content is null, display toast
            Toast.makeText(getApplicationContext()
                    ,"OOPS...You did not scan anything",Toast.LENGTH_SHORT).show();
        }

    }
}