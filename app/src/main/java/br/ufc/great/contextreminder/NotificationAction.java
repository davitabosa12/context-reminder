package br.ufc.great.contextreminder;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import
        android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import com.google.android.gms.awareness.fence.FenceState;

import smd.ufc.br.easycontext.fence.FenceAction;

public class NotificationAction implements FenceAction {
    private static final String CHANNEL_ID = "lol wtf bbq";
    private  static  String TAG = "NotificationAction";
    @Override
    public void doOperation(Context context, FenceState state, Bundle data) {
        if(state.getCurrentState() == FenceState.TRUE){
            String s = data.getString("text");
            pushNotification(context, s);
            Log.d(TAG, "doOperation: " + s);
        } else {
            String k = state.getFenceKey();
            Log.d(TAG, "doOperation: " + k + " is false or unknown");
        }
    }

    private void pushNotification(Context context, String s){

        createNotificationChannel(context);
        NotificationCompat.Builder builder;
        builder = new NotificationCompat.Builder(context, CHANNEL_ID);
        builder.setSmallIcon(R.mipmap.ic_launcher_round);
        builder.setChannelId(CHANNEL_ID);
        builder.setContentTitle("Reminding you!");
        builder.setContentText(s);


        NotificationManagerCompat manager = NotificationManagerCompat.from(context);
        manager.notify(145, builder.build());
    }

    private void createNotificationChannel(Context context) {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Awareness Messages";
            String description = "Messages pushed using Awareness API";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            //if notification channel exists..
            if(notificationManager.getNotificationChannel(CHANNEL_ID) != null)
                return;
            notificationManager.createNotificationChannel(channel);
        }
    }


}
