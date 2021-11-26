package com.example.solidwaste;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Registeruser extends AppCompatActivity {
    EditText Newvehnumber;
    EditText Newvehpass;
    Button Newsub;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registeruser);
        Newvehnumber= (EditText) findViewById(R.id.NewUsername);
        Newvehpass= (EditText) findViewById(R.id.NewPass);
        Newsub= (Button) findViewById(R.id.Newsubv);
        Newsub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference myRef = database.getReference();
                myRef.child(Newvehnumber.getText().toString().toUpperCase()).child("password").setValue(Newvehpass.getText().toString());
                Toast toast = Toast.makeText(getApplicationContext(),
                        "New User is been added!",
                        Toast.LENGTH_SHORT);

                toast.show();
            }
        });
    }
}