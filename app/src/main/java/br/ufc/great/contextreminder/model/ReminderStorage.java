package br.ufc.great.contextreminder.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    public void saveReminder(Reminder reminder){
        sharedPrefs.edit()
                .putString(String.valueOf(reminder.getUid()), reminder.toString())
                .apply();
    }

    public List<Reminder> getAll(){
        List<Reminder> reminders = new ArrayList<>();
        Set<String> keys = new HashSet<>();
        keys = sharedPrefs.getAll().keySet();

        for (String key :
                keys) {
            String remJson = sharedPrefs.getString(key, "");
            if(!remJson.isEmpty()){
                try{

                    reminders.add(Reminder.fromJson(remJson));

                } catch (Exception e ){
                    Log.e("Reminder", "getAll: maybe not a reminder...\n " + remJson + "\n\n\n", e);
                }
            }
        }
        return reminders;


    }



}
