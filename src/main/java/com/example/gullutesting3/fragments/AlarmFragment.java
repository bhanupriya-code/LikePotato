package com.example.gullutesting3.fragments;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gullutesting3.AlarmReciever;
import com.example.gullutesting3.R;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;

import org.w3c.dom.Text;

import java.util.Calendar;


public class AlarmFragment extends Fragment {
    private MaterialTimePicker picker;
    private Calendar calendar;
    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;
    ImageView button1;
    ImageView button2;
    ImageView button3;
    TextView textView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        createNotificationChannel();
        View view = inflater.inflate(R.layout.fragment_alarm, container, false);
        button1 = view.findViewById(R.id.setTime);
        button2 = view.findViewById(R.id.setAlarm);
        button3 = view.findViewById(R.id.cancelAlarm);
        textView = view.findViewById(R.id.selectedTime);

        button1.setOnClickListener(view1 -> showTimePicker());
        button2.setOnClickListener(view12 -> {
            try{
                setAlarm();
            }
            catch (Exception e){
                Toast.makeText(getActivity(), "First Set The Alarm", Toast.LENGTH_SHORT).show();
            }


        });
        button3.setOnClickListener(view13 -> cancelAlarm());

    return view;
    }

    private void cancelAlarm() {
        //this pending intent should exactly match with the pending intent you have used in the setalarm() method
        Intent intent = new Intent(getActivity(), AlarmReciever.class);
        pendingIntent = PendingIntent.getBroadcast(getActivity(),0,intent,0);
        if(alarmManager == null){
            alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
        }
        alarmManager.cancel(pendingIntent);
        Toast.makeText(getActivity(), "Alarm Cancelled", Toast.LENGTH_SHORT).show();
    }

    private void setAlarm() {
        alarmManager = (AlarmManager) getActivity().getSystemService(getActivity().ALARM_SERVICE);
        Intent intent = new Intent(getActivity(), AlarmReciever.class);
        pendingIntent = PendingIntent.getBroadcast(getActivity(),0,intent,0);

        //alarm.set will set it only once but alarmInExaxtRepeating will repeat the alarm after every particular interval of time
        //but this setInexactRepeating sometimes show some delay or sometime earlier but it will not longer than 1 min
        //it is better to use this method because the android sdk
        //RTC = Real tome clock
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        Toast.makeText(getActivity(), "Reminder Set Successfully", Toast.LENGTH_SHORT).show();

    }

    private void showTimePicker() {
        Log.d("picktime", "picktime is working fine");
        picker = new MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_12H)
                .setHour(12)
                .setMinute(0)
                .setTitleText("Select Alarm Time")
                .build();
        picker.show(getActivity().getSupportFragmentManager(), "cutie");
        picker.addOnPositiveButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("picktime", "picktime is working fine2");
                if(picker.getHour() > 12){
                    Log.d("picktime", "picktime is working fine3");

                    //I change the this copying the AM and -12, the error is resolved
                    //error: app crashing whenever a PM timer was set
                    textView.setText(picker.getHour()-12 +" : "+ picker.getMinute()+ " PM ");
//                    textView.setText(String.format("%02d",(picker.getHour())+ " : "+String.format("%02d",picker.getMinute()) +" PM "));
                }else {
                    Log.d("picktime", "picktime is working fine4");
                    textView.setText(picker.getHour()+" : "+ picker.getMinute()+ " AM ");
                }
                calendar = Calendar.getInstance();
                calendar.set(Calendar.HOUR_OF_DAY, picker.getHour());
                calendar.set(Calendar.MINUTE, picker.getMinute());
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MILLISECOND, 0);
            }
        });
    }

    private void createNotificationChannel() {
//this is a notification channel
//argument for what you have created in the alarmreciever
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            CharSequence name = "AlarmRemiderChannel";
            String description = "Channel for alarm manager";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel("Cutie", name, importance);
            channel.setDescription(description);
//channel id should match the channel id given in the AlarmReciver
            //I changed the below method from :
            //changes I made - casting and getActiving() and inside the getSystemService(), it was NotificationManager.class I change that to getActivity().NOTIFICATION_SERVICE
            NotificationManager notificationManager = (NotificationManager) getActivity().getSystemService(getActivity().NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(channel);

        }
    }

}

