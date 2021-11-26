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

import static android.content.ContentValues.TAG;

public class Driverlogin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driverlogin);
        Button submit;
        EditText VehicleNumber;
        EditText Pass;
        String passw;
        VehicleNumber= (EditText)findViewById(R.id.veh);
        Pass= (EditText)findViewById(R.id.pass);
        submit= (Button) findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference dataref = FirebaseDatabase.getInstance().getReference().child(VehicleNumber.getText().toString().toUpperCase());
                dataref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String passi = snapshot.child("password").getValue().toString();
                        Log.d(TAG, "onDataChange: "+passi);
                             checkpassword(passi);
                             
                        

                    }

                    private void checkpassword(String password) {

                        if (password.equals(Pass.getText().toString())){
                            if (VehicleNumber.getText().toString().equals(null)){
                                Toast toast = Toast.makeText(getApplicationContext(),
                                        "Please enter the vehicle number",
                                        Toast.LENGTH_SHORT);

                                toast.show();
                            }
                            else {
                                Intent intent = new Intent(Driverlogin.this, DriverStartTracking.class);
                                intent.putExtra("VehicleD_number", VehicleNumber.getText().toString().toUpperCase());
                                startActivity(intent);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
    }
}