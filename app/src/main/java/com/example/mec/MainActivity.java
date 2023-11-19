package com.example.mec;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private TextView progressText;

    private ProgressBar progressBar1;
    private TextView progressText1;
    private Button goToLocation;

    private TextView heartRateText;
    private TextView temperatureText;
    int i=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar=findViewById(R.id.progress_bar);
        progressText=findViewById(R.id.progress_text);

        progressBar1=findViewById(R.id.progress_bar1);
        progressText1=findViewById(R.id.progress_text1);
        goToLocation=findViewById(R.id.btnLocation);

        heartRateText=findViewById(R.id.text_1);
        temperatureText=findViewById(R.id.text_2);



        goToLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(), MapsActivity.class);
                startActivity(intent);
            }
        });

        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("update");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
//                String value = dataSnapshot.getValue(String.class);
                String heartRate=dataSnapshot.child("heartRate").getValue(String.class).replaceAll("\"", "");
                String temperature=dataSnapshot.child("temperature").getValue(String.class).replaceAll("\"", "");
//                Log.d(TAG, "Value is: " + value);
//                System.out.println(heartRate);
//                System.out.println(temperature);
                progressText.setText("Heart");
                progressText1.setText("Temperature");
               int heartRateValue=(int) Double.parseDouble(heartRate);
                if (heartRateText != null) {
                    heartRateText.setText(heartRate);}
               if(heartRateValue<=100)
                progressBar.setProgress(heartRateValue);
               else
                   progressBar.setProgress(100);
                int temperatureRateValue=(int) Double.parseDouble(temperature);
                if (temperatureText != null) {
                    temperatureText.setText(temperature);}
                if(temperatureRateValue<=100)
                progressBar1.setProgress(temperatureRateValue);
                else
                    progressBar1.setProgress(100);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
//                Log.w(TAG, "Failed to read value.", error.toException());
                progressText.setText("Loading...");
                progressText1.setText("Loading...");
            }
        });

//        final Handler handler=new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                if(i<=100){
//                    progressText.setText("Heart");
//                    progressBar.setProgress(i);
//                    i++;
//                    handler.postDelayed(this,200);
//                }
//                else{
//                    handler.removeCallbacks(this);
//                }
//            }
//        },200);
//
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                if(i<=100){
//                    progressText1.setText("Temperature");
//                    progressBar1.setProgress(i);
//                    i++;
//                    handler.postDelayed(this,200);
//                }
//                else{
//                    handler.removeCallbacks(this);
//                }
//            }
//        },200);
    }
}
