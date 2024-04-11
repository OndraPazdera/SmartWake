package com.example.smartwake;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class AlarmService extends Service {
    private static final String TAG = "AlarmService";
    public static final String ACTION_SET_ALARMS = "com.example.smartwake.SET_ALARMS";
    private Timer timer;
    private static ArrayList<Alarm> alarms;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "Service created");
        alarms = new ArrayList<>();
        startTimer();
    }

    // Zde můžete přidat metodu pro nastavení seznamu budíků
    public static void setAlarms(ArrayList<Alarm> alarmsList) {
        alarms = alarmsList;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            String action = intent.getAction();
            if (action != null && action.equals(ACTION_SET_ALARMS)) {
                alarms = (ArrayList<Alarm>) intent.getSerializableExtra("alarms");
                if (alarms != null) {
                    // Zde můžeme aktualizovat seznam budíků a provést další akce
                    Log.d(TAG, "Received new alarms: " + alarms.size());
                }
            }
        }
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopTimer();
        Log.d(TAG, "Service destroyed");
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void startTimer() {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                checkAlarms();
            }
        }, 0, 60000); // Kontrola každou půlminutu
    }

    private void stopTimer() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    private void checkAlarms() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
        String currentTime = sdf.format(new Date());

        Log.d(TAG, "Current time: " + currentTime);

        for (Alarm alarm : alarms) {
            Log.d("service", "Alarm details: " +
                    "Name: " + alarm.getName() +
                    ", Time: " + alarm.getTime() +
                    ", Music Type: " + alarm.getMusicType() +
                    ", Location: " + alarm.getLocation() +
                    ", Music File Path: " + alarm.getMusicFilePath());


            if (alarm.getTime().equals(currentTime)) {

                startAlarm(alarm.getMusicFilePath());
                // Upravte podle vaší implementace alarmu
            }
        }
    }

    private void startAlarm(String musicFilePath) {

        Intent intent = new Intent(this, Alarm_off.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // Přidejte tento příznak pro spuštění aktivity z kontextu služby
        startActivity(intent);
        Log.d(TAG, "Alarm triggered");

        MediaPlayer mediaPlayer = new MediaPlayer();
        boolean isPlaying = mediaPlayer.isPlaying();
        boolean isLooping = mediaPlayer.isLooping();
        int currentPosition = mediaPlayer.getCurrentPosition();


        Log.d(TAG, "Is playing: " + isPlaying);
        Log.d(TAG, "Is looping: " + isLooping);
        Log.d(TAG, "Current position: " + currentPosition);

        mediaPlayer.reset();

        try {
            if (musicFilePath != null && !musicFilePath.isEmpty()) {
                mediaPlayer.setDataSource(musicFilePath);
                mediaPlayer.prepareAsync();
            } else {
                // Pokud není k dispozici cesta k uloženému zvuku, použijte defaultní zvuk
                mediaPlayer = MediaPlayer.create(this, R.raw.default_alarm_sound);
                mediaPlayer.setLooping(true);
                mediaPlayer.start();
            }
        } catch (Exception e) {
            Log.e(TAG, "Failed to start alarm: " + e.getMessage());
        }
    }

}
