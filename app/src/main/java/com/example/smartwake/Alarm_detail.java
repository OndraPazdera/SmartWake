package com.example.smartwake;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Alarm_detail extends AppCompatActivity {

    private EditText musicTypeEditText;
    private EditText locationEditText;
    private EditText alarmNameEditText;
    private EditText alarmTimeEditText;
    private Button editAlarmButton;
    private Button saveAlarmButton;
    private Button deleteAlarmButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_detail);

        musicTypeEditText = findViewById(R.id.musicTypeEditText);
        locationEditText = findViewById(R.id.locationEditText);
        alarmNameEditText = findViewById(R.id.alarmNameEditText);
        alarmTimeEditText = findViewById(R.id.alarmTimeEditText);
        editAlarmButton = findViewById(R.id.editAlarmButton);
        saveAlarmButton = findViewById(R.id.saveAlarmButton);
        deleteAlarmButton = findViewById(R.id.deleteAlarmButton);

        // Nastavení reakce na kliknutí na tlačítko "Upravit"
        editAlarmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Zobrazení textových polí pro úpravu
                musicTypeEditText.setEnabled(true);
                locationEditText.setEnabled(true);
                alarmNameEditText.setEnabled(true);
                alarmTimeEditText.setEnabled(true);

                // Skrytí tlačítek "Upravit" a "Smazat", zobrazení tlačítka "Uložit"
                editAlarmButton.setVisibility(View.GONE);
                deleteAlarmButton.setVisibility(View.GONE);
                saveAlarmButton.setVisibility(View.VISIBLE);
            }
        });

        // Nastavení reakce na kliknutí na tlačítko "Uložit"
        saveAlarmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Získání dat z textových polí
                String musicType = musicTypeEditText.getText().toString();
                String location = locationEditText.getText().toString();
                String alarmName = alarmNameEditText.getText().toString();
                String alarmTime = alarmTimeEditText.getText().toString();

                // Vytvoření intentu pro přenos dat zpět do MainActivity
                Intent resultIntent = new Intent();
                resultIntent.putExtra("musicType", musicType);
                resultIntent.putExtra("location", location);
                resultIntent.putExtra("alarmName", alarmName);
                resultIntent.putExtra("alarmTime", alarmTime);
                setResult(RESULT_OK, resultIntent);

                // Ukončení aktivity a návrat na MainActivity
                finish();
            }
        });

        // Nastavení reakce na kliknutí na tlačítko "Smazat"
        deleteAlarmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Zde by mělo dojít k odstranění budíku
                Toast.makeText(Alarm_detail.this, "Budík byl smazán", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}
