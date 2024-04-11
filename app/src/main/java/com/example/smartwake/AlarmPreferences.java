package com.example.smartwake;

import android.content.Context;
import android.content.SharedPreferences;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import android.util.Log;

public class AlarmPreferences {

    private static final String PREF_NAME = "AlarmPrefs";
    private static final String KEY_ALARMS = "alarms";

    private SharedPreferences preferences;
    private Gson gson;

    public AlarmPreferences(Context context) {
        preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        gson = new Gson();
    }

    // Uložení dat budíku
    public void saveAlarmData(Alarm alarm) {
        ArrayList<Alarm> savedAlarms = getSavedAlarms();
        savedAlarms.add(alarm);

        String alarmsJson = gson.toJson(savedAlarms);

        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(KEY_ALARMS, alarmsJson);
        editor.apply();

        // Přidání logu pro kontrolu uloženého JSON řetězce
        Log.d("AlarmPreferences", "Saved alarms JSON: " + alarmsJson);

    }

    // Načtení uložených budíků
    public ArrayList<Alarm> getSavedAlarms() {
        String alarmsJson = preferences.getString(KEY_ALARMS, null);
        Type type = new TypeToken<List<Alarm>>(){}.getType();

        if (alarmsJson != null) {
            return gson.fromJson(alarmsJson, type);
        } else {
            return new ArrayList<>();
        }
    }

    public ArrayList<Alarm> removeAlarmData(int position) {
        ArrayList<Alarm> savedAlarms = getSavedAlarms();
        savedAlarms.remove(position);

        String alarmsJson = gson.toJson(savedAlarms);

        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(KEY_ALARMS, alarmsJson);
        editor.apply();

        // Přidání logu pro kontrolu aktualizovaného JSON řetězce
        Log.d("AlarmPreferences", "Updated alarms JSON: " + alarmsJson);

        return savedAlarms;
    }

}
