package com.example.solidwaste;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class AdminPanel extends AppCompatActivity {
CardView update;
CardView add;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_panel);
        update=(CardView) findViewById(R.id.updateDriver);
        add=(CardView) findViewById(R.id.CreateNewDriver);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast toast = Toast.makeText(getApplicationContext(),
                        "Update is under development",
                        Toast.LENGTH_LONG);

                toast.show();
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminPanel.this, CreateNewVehicle.class);
                startActivity(intent);
            }
        });


    }

}