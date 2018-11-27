package com.example.kalomidin.topic;

import android.app.IntentService;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.util.ArrayList;


public class Background extends IntentService {
    private static final String TAG = "com.example.kalomidin.topic";
    public Background(){
        super("Background");
    }
    @Override
    protected void onHandleIntent(Intent intent) {
        Log.i(TAG, "Service has started!!!!!!!!!!!!!!!");
        int index = 0;
        try {
            Thread.sleep(10000); //sleeps for 1 minute before going to next value
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        while(index<100){
            //for the nth popularity value
            Log.d("sdsd", "Entering LikedTTTTTTTTT");
            ArrayList<String> topic_list = MainActivity.get_Topics();
            for(int i=0; i<topic_list.size(); i++) {
                if(!topic_list.get(i).equals("")){
                    //for each topic chosen
                    Log.d("sdsd", "Entering Liked");
                    Log.d("sdsd", "Entering 2");
                    String [] values =getRel(topic_list.get(i));
                    for(int j=0;j<values.length;j++){
                        if(values[j]!=null){
                            if(Integer.parseInt(values[j]) >= 50) {
                                MainActivity a=new MainActivity();
                                a.setResult(topic_list.get(i));
                                Intent myIntent = new Intent(this, TopicPage.class);
                                showNotification(this, topic_list.get(i), "This topic is trending", myIntent);
                            }}}}}
            index++;
            try {
                Thread.sleep(2000000); //sleeps for 1 minute before going to next value
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    public static void showNotification(Context context, String title, String body, Intent intent) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        int notificationId = 1;
        String channelId = "channel-01";
        String channelName = "Channel Name";
        int importance = NotificationManager.IMPORTANCE_HIGH;

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(
                    channelId, channelName, importance);
            notificationManager.createNotificationChannel(mChannel);
        }

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, channelId)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(body);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addNextIntent(intent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(
                0,
                PendingIntent.FLAG_UPDATE_CURRENT
        );
        mBuilder.setContentIntent(resultPendingIntent);

        notificationManager.notify(notificationId, mBuilder.build());
    }
    public String[] getRel(String x){
        String [] y=new String[52];
        String [] z=new String[]{"",""};
        if(x.equals("Cristiano Ronaldo"))
            y=new String[] {"11","26","15","14","20","17","18","15","8","16","16","17","12","16","13","10","12","11","11","12","12","43","19","12","16","13","14","12","25","25","21","100","88","89","69","79","33","21","14","12","16","19","12","10","11","18","17","53","20","11","14","10"};
        else if(x.equals("Coldplay"))
            y=new String []{"61","68","74","67","65","60","58","65","58","57","57","61","58","62","100","69","58","64","55","56","54","60","62","64","58","58","58","52","52","51","50","50","52","46","51","50","49","52","48","52","53","66","53","53","47","46","50","47","52","49","47","51"};
        if(x.equals(""))
            return z;
        return y;
    }
}