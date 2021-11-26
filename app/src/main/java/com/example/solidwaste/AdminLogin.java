package com.example.solidwaste;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AdminLogin extends AppCompatActivity {
EditText username;
EditText password;
Button sub;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);
        username= (EditText) findViewById(R.id.username);
        password= (EditText) findViewById(R.id.password);
        sub= (Button) findViewById(R.id.subi);
        sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (username.getText().toString().equals("admin")&&password.getText().toString().equals("superadmin")){
                    Intent intent = new Intent(AdminLogin.this, AdminPanel.class);
                    startActivity(intent);
                }
                else {
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Wrong password or username",
                            Toast.LENGTH_SHORT);

                    toast.show();
                }
            }
        });

    }
}