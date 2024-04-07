package com.example.smartwake;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

public class Alarm_create extends AppCompatActivity implements LocationPickerFragment.OnLocationSelectedListener {

    private EditText alarmNameEditText;
    private TextView selectedTimeTextView;
    private EditText musicTypeEditText;
    private Button createAlarmButton;
    private Button selectMusicButton;
    private Button selectLocationButton; // Přidáno tlačítko pro výběr lokace
    private static final int REQUEST_CODE_SELECT_MUSIC = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_create);

        alarmNameEditText = findViewById(R.id.alarmNameEditText);
        selectedTimeTextView = findViewById(R.id.selectedTimeTextView);
        musicTypeEditText = findViewById(R.id.musicTypeEditText);
        createAlarmButton = findViewById(R.id.createAlarmButton);
        selectMusicButton = findViewById(R.id.selectMusicButton);
        selectLocationButton = findViewById(R.id.selectLocationButton); // Inicializace tlačítka pro výběr lokace

        Intent intent = getIntent();
        String selectedTime = intent.getStringExtra("selectedTime");
        selectedTimeTextView.setText(selectedTime);

        selectMusicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, REQUEST_CODE_SELECT_MUSIC);
            }
        });

        selectLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Zobrazit fragment pro výběr lokace
                showLocationPickerDialog();
            }
        });

        createAlarmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Získání hodnot z edit textů
                String alarmName = alarmNameEditText.getText().toString();
                String alarmTime = selectedTimeTextView.getText().toString();
                String musicType = musicTypeEditText.getText().toString();

                // Vytvoření intentu pro přenos dat zpět do MainActivity
                Intent resultIntent = new Intent();
                resultIntent.putExtra("alarmName", alarmName);
                resultIntent.putExtra("selectedTime", alarmTime);
                resultIntent.putExtra("musicType", musicType);
                // Přidejte další potřebné informace o vybrané lokaci
                setResult(RESULT_OK, resultIntent);

                finish(); // Ukončení aktivity a návrat do MainActivity
            }
        });
    }

    // Metoda pro zobrazení fragmentu pro výběr lokace
    private void showLocationPickerDialog() {
        FragmentManager fm = getSupportFragmentManager();
        LocationPickerFragment dialogFragment = LocationPickerFragment.newInstance();
        dialogFragment.show(fm, "LocationPickerFragment");
    }

    // Metoda z rozhraní LocationPickerFragment.OnLocationSelectedListener
    @Override
    public void onLocationSelected(String location) {
        // Zde můžete zpracovat vybrané místo a provést další akce
        // Například aktualizovat textové pole nebo ukládat vybrané místo pro další použití
    }
}
