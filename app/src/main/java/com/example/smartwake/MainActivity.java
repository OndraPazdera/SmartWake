package com.example.smartwake;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TimePicker;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private ArrayList<String> alarms;
    private ArrayAdapter<String> adapter;

    private static final int REQUEST_CODE_ALARM_CREATE = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button addAlarmButton = findViewById(R.id.addAlarmButton);
        ListView alarmListView = findViewById(R.id.alarmListView);

        Log.d("MainActivity", "zde to ještě běží 1");

        alarms = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, alarms);
        alarmListView.setAdapter(adapter);

        addAlarmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog();
                Log.d("MainActivity", "zde to ještě běží 2");
            }
        });

        alarmListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedAlarm = alarms.get(position);
                Intent intent = new Intent(MainActivity.this, Alarm_create.class);
                intent.putExtra("selectedAlarm", selectedAlarm);
                startActivityForResult(intent, REQUEST_CODE_ALARM_CREATE);

            }

        });
    }

    private void showAlarmDetailsDialog(String time) {
        Intent intent = new Intent(MainActivity.this, Alarm_detail.class);
        intent.putExtra("alarmTime", time);
        startActivityForResult(intent, REQUEST_CODE_ALARM_CREATE);
    }

    private void showTimePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(MainActivity.this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String time = String.format("%02d:%02d", hourOfDay, minute);
                        Intent intent = new Intent(MainActivity.this, Alarm_create.class);
                        intent.putExtra("selectedTime", time);
                        startActivityForResult(intent, REQUEST_CODE_ALARM_CREATE); // Změna na startActivityForResult
                    }

                }, hour, minute, true);

        timePickerDialog.show();
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_ALARM_CREATE && resultCode == RESULT_OK) {


            if (data != null) {
                String alarmName = data.getStringExtra("alarmName");
                String selectedTime = data.getStringExtra("selectedTime");
                String musicType = data.getStringExtra("musicType");
                String location = data.getStringExtra("location");

                Log.d("MainActivity", "Received data: alarmName = " + alarmName + ", selectedTime = " + selectedTime + ", musicType = " + musicType + ", location = " + location);

                String newAlarm = alarmName + " - " + selectedTime;
                alarms.add(newAlarm);
                adapter.notifyDataSetChanged();
            }
        }
    }


}
