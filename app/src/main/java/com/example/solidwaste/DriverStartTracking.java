package com.example.solidwaste;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import static android.service.controls.ControlsProviderService.TAG;

public class DriverStartTracking extends AppCompatActivity {

    private FusedLocationProviderClient fusedLocationClient;
    private LocationRequest locationRequest;
    private LocationCallback locationCallback;
    Button Starttrack;
    Button Stoptrack;
    double latitude;
    String sessionIdD ;
    double longitude;
    Date currentTime ;
    FloatingActionButton fab1;
    ImageView pictest;
    private static final int REQUEST_IMAGE_CAPTURE = 111;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    final Handler ha = new Handler();
    Context mContext;
    LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_start_tracking);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        fab1 = (FloatingActionButton) findViewById(R.id.fab);
      pictest =(ImageView) findViewById(R.id.pictest);
        Starttrack = (Button) findViewById(R.id.starttrack);
        Stoptrack = (Button) findViewById(R.id.stoptrack);
        Intent i = getIntent();
        sessionIdD = i.getStringExtra("VehicleD_number");
        testpic();
        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    if(ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {
                        ActivityCompat.requestPermissions(DriverStartTracking.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 101);}
               Task<Location> location = fusedLocationClient.getLastLocation();
                    location.addOnSuccessListener(new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if (location!=null) {
                                latitude=location.getLatitude();
                                longitude=location.getLongitude();
                                                                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);

                            }
                        else {
                                Toast toast = Toast.makeText(getApplicationContext(),
                                        "Kindly open your location services!",
                                        Toast.LENGTH_SHORT);
                                toast.show();
                            }
                        }
                    });


            }
        });
        Stoptrack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast toast = Toast.makeText(getApplicationContext(),
                        "Location sharing has been stopped",
                        Toast.LENGTH_SHORT);

                toast.show();
                DatabaseReference myRef = database.getReference().child(sessionIdD.toUpperCase());

//                Log.d(" ","onClick:"+myRef.toString());
                myRef.child("lat").setValue("NULL");
                myRef.child("lng").setValue("NULL");
                finishAffinity();
                System.exit(0);
//                Intent intent = new Intent(DriverStartTracking.this, MainActivity.class);
//                startActivity(intent);
//                finishAffinity();
//                ha.removeCallbacksAndMessages(null);
//                startActivity(new Intent(getBaseContext(),MainActivity.class));
//                finish();
//                startActivity(getIntent());
            }
        });
        Starttrack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast toast = Toast.makeText(getApplicationContext(),
                        "Vehicle location Sharing is been started",
                        Toast.LENGTH_SHORT);

                toast.show();

                ha.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        buildLocationRequest();
                        buildLocationCallBack();
                        startlocationupdate();        //call function

                        ha.postDelayed(this, 10000);
                    }
                }, 10000);
//                ha.removeCallbacks();
            }
        });
    }





    private void testpic() {
        DatabaseReference myRef = database.getReference().child(sessionIdD.toUpperCase());
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (!snapshot.child("imageUrl").getValue().toString().contains("http")) {
                    try {
                        Bitmap imageBitmap = decodeFromFirebaseBase64(snapshot.child("imageUrl").getValue().toString());
                        pictest.setImageBitmap(imageBitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    // This block of code should already exist, we're just moving it to the 'else' statement:
                    Picasso.get()
                            .load(snapshot.child("imageUrl").getValue().toString())
//                            .resize(MAX_WIDTH, MAX_HEIGHT)
                            .centerCrop()
                            .into(pictest);
//                    nameTextView.setText(restaurant.getName());
//                    categoryTextView.setText(restaurant.getCategories().get(0));
//                    ratingTextView.setText("Rating: " + restaurant.getRating() + "/5");
                }
//                mNameTextView.setText(restaurant.getName());
//                mCategoryTextView.setText(restaurant.getCategories().get(0));
//                mRatingTextView.setText("Rating: " + restaurant.getRating() + "/5");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public static Bitmap decodeFromFirebaseBase64(String image) throws IOException {
        byte[] decodedByteArray = android.util.Base64.decode(image, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedByteArray, 0, decodedByteArray.length);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == this.RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
//            mImageLabel.setImageBitmap(imageBitmap);
            encodeBitmapAndSaveToFirebase(imageBitmap);
        }
    }

    private void encodeBitmapAndSaveToFirebase(Bitmap imageBitmap) {
        Date currentTime = Calendar.getInstance().getTime();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        String imageEncoded = Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT);
        DatabaseReference ref = FirebaseDatabase.getInstance()
                .getReference()
                .child(sessionIdD.toUpperCase())
//                .child(mRestaurant.getPushId())
                ;
        ref.child("imageUrl").setValue(imageEncoded);
        ref.child("markerLat").setValue(latitude);
        ref.child("markerlng").setValue(longitude);
        ref.child("DateTime").setValue(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date()));
    }

    private void buildLocationRequest() {
        locationRequest = LocationRequest.create()
                .setInterval(100)
                .setFastestInterval(100)
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setMaxWaitTime(100)
                .setSmallestDisplacement(1);
        Log.d("TAG", "buildLocationRequest: ");


    }

    //Build the location callback object and obtain the location results //as demonstrated below:
    private void buildLocationCallBack() {
        Log.d("TAG", "buildLocationCallBack: ");
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                for (Location location : locationResult.getLocations()) {
                    String latitude = String.valueOf(location.getLatitude());
                    String longitude = String.valueOf(location.getLongitude());
                    Log.d("TAG", "onLocationResult:" + latitude + " lng " + longitude);
                    DatabaseReference myRef = database.getReference().child(sessionIdD.toUpperCase());

//                Log.d(" ","onClick:"+myRef.toString());
                    myRef.child("lat").setValue(latitude);
                    myRef.child("lng").setValue(longitude);

                }
            }
        };
    }

    private void startlocationupdate() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 101);

        }
        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null);
    }
}