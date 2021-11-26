package com.example.solidwaste;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.util.Log;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.io.IOException;

public class TrackingVehicleMap extends FragmentActivity implements OnMapReadyCallback {
    String sessionId ;
    Bundle bundle;
    private GoogleMap mMap;
    FirebaseDatabase database = FirebaseDatabase.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracking_vehicle_map);
        Intent i = getIntent();
        sessionId = i.getStringExtra("Vehicle_number");
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    private void showCustomDialog() {
        final Dialog dialog = new Dialog(TrackingVehicleMap.this);
        //We have added a title in the custom layout. So let's disable the default title.
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //The user will be able to cancel the dialog bu clicking anywhere outside the dialog.
        dialog.setCancelable(true);
        //Mention the name of the layout of your custom dialog.
        dialog.setContentView(R.layout.dialogmarker);

        //Initializing the views of the dialog.
        final ImageView nameEt = dialog.findViewById(R.id.image);
        final TextView date = dialog.findViewById(R.id.date);
        DatabaseReference myRef = database.getReference().child("LEH1212");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                date.setText("Date "+snapshot.child("DateTime").child("date")+" /"+snapshot.child("DateTime").child("month")+" /2021");
                if (!snapshot.child("imageUrl").getValue().toString().contains("http")) {
                    try {
                        Bitmap imageBitmap = decodeFromFirebaseBase64(snapshot.child("imageUrl").getValue().toString());
                        nameEt.setImageBitmap(imageBitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    // This block of code should already exist, we're just moving it to the 'else' statement:
                    Picasso.get()
                            .load(snapshot.child("imageUrl").getValue().toString())
//                            .resize(MAX_WIDTH, MAX_HEIGHT)
                            .centerCrop()
                            .into(nameEt);
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




        dialog.show();
    }



    public static Bitmap decodeFromFirebaseBase64(String image) throws IOException {
        byte[] decodedByteArray = android.util.Base64.decode(image, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedByteArray, 0, decodedByteArray.length);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(@NonNull Marker marker) {
                showCustomDialog();
                return true;
            }
        });
        final Handler ha=new Handler();
        ha.postDelayed(new Runnable() {

            @Override
            public void run() {
                   //call function
//                LatLng sydney = new LatLng(-34, 151);
//                mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//                mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
                mMap.clear();
                googleMap.clear();
                getlocation();
                ha.postDelayed(this, 10000);
            }
        }, 10000);
        // Add a marker in Sydney and move the camera

    }

    private void getlocation() {
        Log.d("TAG", sessionId);

        DatabaseReference myRef = database.getReference().child(sessionId);


       myRef.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot snapshot) {
               LatLng driver = new LatLng(Double.valueOf(snapshot.child("lat").getValue().toString()) ,Double.valueOf(snapshot.child("lng").getValue().toString()));
//               LatLng Garbage = new LatLng(Double.valueOf(snapshot.child("markerLat").getValue().toString()) ,Double.valueOf(snapshot.child("markerlng").getValue().toString()));
               MarkerOptions a = new MarkerOptions()
                       .position(new LatLng(Double.valueOf(snapshot.child("lat").getValue().toString()) ,Double.valueOf(snapshot.child("lng").getValue().toString()) ));
               MarkerOptions garbageM = new MarkerOptions()
                       .position(new LatLng(Double.valueOf(snapshot.child("markerLat").getValue().toString()) ,Double.valueOf(snapshot.child("markerlng").getValue().toString()) ))
                       .snippet("Date "+snapshot.child("DateTime").getValue().toString())
                       .title("Click to view detail")
                       .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
               Marker m = mMap.addMarker(a);
               Marker garbage1 = mMap.addMarker(garbageM);

               mMap.moveCamera(CameraUpdateFactory.newLatLng(driver));
               mMap.animateCamera( CameraUpdateFactory.zoomTo( 17.0f ) );
//               m.setPosition(new LatLng((Double) snapshot.child("lat").getValue(), (Double) snapshot.child("lat").getValue()));
//               Log.d("TAG", "onDataChange: "+ snapshot.child("lat").getValue());
           }

           @Override
           public void onCancelled(@NonNull DatabaseError error) {
               Toast toast = Toast.makeText(getApplicationContext(),
                       "PLEASE GO BACK AND ENTER VALID VEHICLE NUMBER",
                       Toast.LENGTH_SHORT);

               toast.show();

           }
       });

    }
}