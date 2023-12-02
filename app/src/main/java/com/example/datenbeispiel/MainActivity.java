package com.example.datenbeispiel;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class MainActivity extends AppCompatActivity {

    TextView textViewDaten, textViewSpeisekarte, textViewTische;
    Button createButton;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference dbRef = database.getReference("Restaurants");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textViewDaten = findViewById(R.id.textView_daten);
        textViewSpeisekarte = findViewById(R.id.textView_speisekarte);
        textViewTische = findViewById(R.id.textView_tische);
        createButton = findViewById(R.id.button_create);
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newRestaurant();
            }
        });

        dbRef.child("-NkF_dqyroONEdMqgfgC").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Daten daten = dataSnapshot.child("daten").getValue(Daten.class);
                Speisekarte speisekarte = dataSnapshot.child("speisekarte").child("G1").getValue(Speisekarte.class);
                String restaurantDaten =
                        "Name: " + daten.getName() + "\n" +
                        "Ort: " + daten.getOrt() + "\n" +
                        "PLZ: " + daten.getPlz() + "\n" +
                        "Strasse: " + daten.getStrasse() + "\n" +
                        "Hausnummer: " + daten.getHausnr() + "\n" +
                        "Speisekarte: " + (daten.isSpeisekarte() ? "Ja" : "Nein") + "\n";

                StringBuilder restaurantSpeisekarte = new StringBuilder();      //ein String Builder ist Notwendig um Map auszugeben
                restaurantSpeisekarte.append("Gericht 1: ").append("\n")        //bitte fragt mich nicht wie das geht
                        .append("Name: ").append(speisekarte.getGericht()).append("\n")
                        .append("Preis: ").append(speisekarte.getPreis()).append("\n");

                restaurantSpeisekarte.append("Allergien: ").append("\n");
                for (Map.Entry<String, Boolean> entry : speisekarte.getAllergien().entrySet()) {
                    restaurantSpeisekarte.append(entry.getKey()).append(": ").append(entry.getValue() ? "Ja" : "Nein").append("\n");
                }

                restaurantSpeisekarte.append("Zutaten: ").append("\n");
                for (Map.Entry<String, Boolean> entry : speisekarte.getZutaten().entrySet()) {
                    restaurantSpeisekarte.append(entry.getKey()).append(": ").append(entry.getValue() ? "Ja" : "Nein").append("\n");
                }

                textViewDaten.setText(restaurantDaten);
                textViewSpeisekarte.setText(restaurantSpeisekarte);
                //textViewTische.setText(restaurantTische);
            }

            @Override
            public void onCancelled(DatabaseError error) {
            }
        });
    }

    public void newRestaurant() {
        String id = dbRef.push().getKey();
        Restaurant restaurant = new Restaurant();
        restaurant.setDaten(new Daten(id));
        dbRef.child(id).setValue(restaurant);
    }
}