package com.example.solidwaste;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreateNewVehicle extends AppCompatActivity {
    EditText vehnumber;
    EditText vehpass;
    Button sub;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_vehicle);
        vehnumber= (EditText) findViewById(R.id.VehNew);
        vehpass= (EditText) findViewById(R.id.vpass);
        sub= (Button) findViewById(R.id.subv);
        sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference myRef = database.getReference();
                myRef.child(vehnumber.getText().toString().toUpperCase()).child("password").setValue(vehpass.getText().toString());
                Toast toast = Toast.makeText(getApplicationContext(),
                        "New Vehicle is been added!",
                        Toast.LENGTH_SHORT);

                toast.show();
            }
        });

    }
}