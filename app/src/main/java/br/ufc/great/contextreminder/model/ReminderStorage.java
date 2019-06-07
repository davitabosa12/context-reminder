package br.ufc.great.contextreminder.model;

import android.content.Context;
import android.content.SharedPreferences;

public class ReminderStorage {

    private static SharedPreferences sharedPrefs;
    private static ReminderStorage instance;

    private ReminderStorage(Context context){
        sharedPrefs = context.getSharedPreferences("Reminder", Context.MODE_PRIVATE);
    }

    public static ReminderStorage getInstance(Context context) {
        if (instance == null) {
            instance = new ReminderStorage(context);
        }
        return instance;
    }

    public Reminder getFromId(String id){
        String rem = sharedPrefs.getString(id, "nope");
        if(rem.equals("nope")){
            return null;
        } else {
            return Reminder.fromJson(rem);
        }
    }



}
