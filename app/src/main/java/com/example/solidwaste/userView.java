package com.example.solidwaste;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class userView extends AppCompatActivity {
Button startT;
EditText VehNumber;
FirebaseDatabase database = FirebaseDatabase.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_view);
        VehNumber=(EditText) findViewById(R.id.UserVeh);
        startT=(Button) findViewById(R.id.StartT);
        startT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {

                    DatabaseReference myRef = database.getReference().child(VehNumber.getText().toString().toUpperCase());

                    myRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (!snapshot.exists()) {
                                Toast toast = Toast.makeText(getApplicationContext(),
                                        "Please enter valid Vehicle number",
                                        Toast.LENGTH_SHORT);

                                toast.show();
                            }
                            else {
                                if (snapshot.child("lat").getValue().equals("NULL")) {
                                    Toast toast = Toast.makeText(getApplicationContext(),
                                            "Vehicle is currently offline",
                                            Toast.LENGTH_SHORT);

                                    toast.show();

                                } else {
                                    sendintent();
                                }
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }
                catch(Exception e) {
                    Log.d("TAG", "onClick: ");
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "PLEASE ENTER A VALID VEHICLE NUMBER",
                            Toast.LENGTH_SHORT);

                    toast.show();
                }

            }
        });
    }

    private void sendintent() {
        if (VehNumber.getText().toString().equals(null)){
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Please enter the vehicle number",
                    Toast.LENGTH_SHORT);

            toast.show();
        }
        else {
            Intent intent = new Intent(userView.this, TrackingVehicleMap.class);
            intent.putExtra("Vehicle_number", VehNumber.getText().toString().toUpperCase());
            startActivity(intent);
        }
    }
}