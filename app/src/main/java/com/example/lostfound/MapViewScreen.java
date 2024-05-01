package com.example.lostfound;

import static androidx.constraintlayout.widget.ConstraintLayoutStates.TAG;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

public class MapViewScreen extends FragmentActivity implements OnMapReadyCallback {

    FirebaseFirestore db;
//    ArrayList<String> latitude, longitude;

    private SupportMapFragment mapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//
//        latitude = new ArrayList<>();
//        longitude = new ArrayList<>();
        db = FirebaseFirestore.getInstance();

        // Set the content view to just a simple FrameLayout or use any base layout
        setContentView(R.layout.activity_map_view_screen);

        // Programmatically insert the Map Fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        mapFragment = SupportMapFragment.newInstance();
        fragmentTransaction.add(R.id.map_container, mapFragment);
        fragmentTransaction.commit();

        // After committing the transaction, set the callback
        mapFragment.getMapAsync(this);
    }

//    @Override
//    public void onMapReady(GoogleMap googleMap) {
//        // Add a marker and move the camera
//        LatLng sydney = new LatLng(-34, 151);
//        googleMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
//    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("lostfound")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String latitude = document.getString("Latitude");
                            String longitude = document.getString("Longitude");
                            String title = document.getString("Description");
                            if (latitude != null && longitude != null) {
                                LatLng location = new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude));
                                googleMap.addMarker(new MarkerOptions().position(location).title(title));
                                googleMap.moveCamera(CameraUpdateFactory.newLatLng(location));
                            }
                        }
                    } else {
                        Log.d("MapViewScreen", "Error getting documents: ", task.getException());
                    }
                });
    }

}