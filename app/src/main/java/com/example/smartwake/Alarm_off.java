package com.example.smartwake;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class Alarm_off extends AppCompatActivity {

    private AlarmPreferences alarmPreferences;
    private ArrayList<Alarm> alarms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_off);


        // Inicializace instance třídy AlarmPreferences
        alarmPreferences = new AlarmPreferences(this);

        alarms = new ArrayList<>();

        alarms.addAll(alarmPreferences.getSavedAlarms());

        Button buttonTurnOff = findViewById(R.id.buttonTurnOff);

        // Nastavení reakce na kliknutí na tlačítko "Smazat"
        buttonTurnOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Získání indexu vybraného budíku
                int position = getIntent().getIntExtra("position", -1);
                Log.d("Alarm_detail", "Pozice vybraného budíku: " + position);

                if (position != -1) {
                    Log.d("MainActivity", "Saved alarms loaded: " + alarms.size());
                    // Smazání budíku ze seznamu
                    alarms = alarmPreferences.removeAlarmData(position);
                    // Upozornění uživatele

                    Log.d("MainActivity", "Saved alarms loaded: " + alarms.size());
                    setResult(RESULT_OK);
                    finish();
                } else {
                    Log.d("Alarm_detail", "Je to v pytli nestíhám");
                }
            }
        });

    }

}
