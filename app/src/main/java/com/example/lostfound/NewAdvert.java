package com.example.lostfound;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class NewAdvert extends AppCompatActivity {
    TextInputEditText name, phone, description, date, latitude, longitude;
    RadioGroup radioGroup;
    Button submit;
    FirebaseFirestore db;
    String type = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_new_advert);

        name = findViewById(R.id.name);
        phone = findViewById(R.id.phone);
        description = findViewById(R.id.description);
        date = findViewById(R.id.date);
        latitude = findViewById(R.id.latitude);
        longitude = findViewById(R.id.longitude);
        radioGroup = findViewById(R.id.radioGrp);
        submit = findViewById(R.id.submit);


        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton radioButton = radioGroup.findViewById(i);
                type =  radioButton.getText().toString().trim();
            }
        });



        db = FirebaseFirestore.getInstance();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String, Object> details = new HashMap<>();
                details.put("Name", name.getText().toString().trim());
                details.put("Description", description.getText().toString().trim());
                details.put("Date", date.getText().toString().trim());
                details.put("Latitude", latitude.getText().toString().trim());
                details.put("Longitude", longitude.getText().toString().trim());
                details.put("Phone", phone.getText().toString().trim());
                details.put("Type", type);

                db.collection("lostfound")
                        .document(phone.getText().toString().trim())
                        .set(details)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(NewAdvert.this, "Data Stored Successfully", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        });
            }
        });

    }
}