package com.example.alarmmanagerapp;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Calendar;
public class MainActivity extends AppCompatActivity {
    private TimePicker timePicker;
    private Button btnSetAlarm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        timePicker = findViewById(R.id.timePicker);
        btnSetAlarm = findViewById(R.id.btnSetAlarm);
        btnSetAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAlarm();
            }
        });
    }
    private void setAlarm() {
        int hour = timePicker.getCurrentHour();
        int minute = timePicker.getCurrentMinute();
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);
        AlarmManager alarmManager = (AlarmManager)
                getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(MainActivity.this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        Toast.makeText(MainActivity.this, "Alarm set for " + hour + ":" + minute,
                Toast.LENGTH_SHORT).show();
    }

    public static class AlarmReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
// Handle the alarm when it goes off
            Toast.makeText(context, "Alarm is triggered!", Toast.LENGTH_SHORT).show();
// Play a sound when the alarm is triggered
            Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
            Ringtone ringtone = RingtoneManager.getRingtone(context, alarmSound);
            ringtone.play();
        }
    }
}
