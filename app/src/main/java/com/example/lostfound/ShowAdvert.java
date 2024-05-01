package com.example.lostfound;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ShowAdvert extends AppCompatActivity {
    RecyclerView recyclerView;
    MyAdapter adapter;
    FirebaseFirestore db;
    ArrayList<String> type, desc, location, date, phone, name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_show_advert);

        db = FirebaseFirestore.getInstance();


        type = new ArrayList<>();
        desc = new ArrayList<>();
        location = new ArrayList<>();
        date = new ArrayList<>();
        phone = new ArrayList<>();
        name = new ArrayList<>();

        recyclerView = findViewById(R.id.recyclerview);

        adapter = new MyAdapter(this, type, desc, location, date, phone, name, new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        DisplayData();
    }

    void DisplayData() {
//        Toast.makeText(ShowAdvert.this, "Reading Database..", Toast.LENGTH_SHORT).show();
        db.collection("lostfound")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        Toast.makeText(ShowAdvert.this, "Reading Document..", Toast.LENGTH_SHORT).show();
//                        Toast.makeText(ShowAdvert.this, task.getResult().getDocuments().get(0).get("Type").toString(), Toast.LENGTH_SHORT).show();

                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String typeData = document.getString("Type").toUpperCase();
                                String descData = document.getString("Description").toUpperCase();
                                String nameData = document.getString("Name").toUpperCase();
                                String phoneData = document.getString("Phone").toUpperCase();
                                String locationData = document.getString("Latitude").toUpperCase();
                                String dateData = document.getString("Date").toUpperCase();

                                type.add(typeData);
                                desc.add(descData);
                                name.add(nameData);
                                phone.add(phoneData);
                                location.add(locationData);
                                date.add(dateData);
                            }

                            adapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(ShowAdvert.this, "Error getting documents: " + task.getException(), Toast.LENGTH_SHORT).show();
                        }
                }});
    }
}