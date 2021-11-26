package com.example.solidwaste;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class login extends AppCompatActivity {
//    EditText username;
//    EditText password;
//    Button sub;
//    TextView reg;
//    FirebaseDatabase database = FirebaseDatabase.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                Intent mainIntent = new Intent(login.this,MainActivity.class);
                login.this.startActivity(mainIntent);
                login.this.finish();
            }
        }, 1000);
    }
//        String Databaseusername;
//        String DatabasePassword;
//        username= (EditText) findViewById(R.id.uName);
//        password= (EditText) findViewById(R.id.uPass);
//        sub= (Button) findViewById(R.id.Usubmit);


//        reg= (TextView) findViewById(R.id.UReg);
//    reg.setOnClickListener(new View.OnClickListener() {
//    @Override
//    public void onClick(View view) {
//        Intent intent = new Intent(login.this, Registeruser.class);
//        startActivity(intent);
//    }
//});
//        sub.setOnClickListener(new View.OnClickListener() {
//           @Override
//            public void onClick(View view) {
//               DatabaseReference dataref = FirebaseDatabase.getInstance().getReference().child(username.getText().toString().toUpperCase());
//
//
////                Log.d(" ","onClick:"+myRef.toString());
//              dataref.addValueEventListener(new ValueEventListener() {
//                  @Override
//                  public void onDataChange(@NonNull DataSnapshot snapshot) {
//                      String passi = snapshot.child("password").getValue().toString();
//                      checkpassword(passi);
//
//                  }
//
//                  @Override
//                  public void onCancelled(@NonNull DatabaseError error) {
//                      Toast toast = Toast.makeText(getApplicationContext(),
//                              "Wrong password or username",
//                              Toast.LENGTH_SHORT);
//
//                      toast.show();
//                  }
//              });
//            }
//        });
//    }
//
//    private void checkpassword(String passi) {
//        if (passi.equals(password.getText().toString())){
//            if (username.getText().toString().equals(null)){
//                Toast toast = Toast.makeText(getApplicationContext(),
//                        "Please enter the correct Username or password",
//                        Toast.LENGTH_SHORT);
//
//                toast.show();
//            }
//            else {
//                Intent intent = new Intent(login.this, MainActivity.class);
//                startActivity(intent);
//            }
//        }

    }
