package br.ufc.great.contextreminder;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.awareness.Awareness;
import com.google.android.gms.awareness.FenceClient;
import com.google.android.gms.awareness.fence.AwarenessFence;
import com.google.android.gms.awareness.fence.FenceUpdateRequest;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.List;

import br.ufc.great.contextreminder.model.Reminder;
import br.ufc.great.contextreminder.model.ReminderStorage;

public class MainActivity extends AppCompatActivity implements ReminderView.OnReminderDeletePressed {

    private static final int MAIN_REQUEST_CODE = 357;
    private static final String TAG = "MainActivity";
    List<Reminder> reminderList;
    LinearLayout linearLayout;
    ReminderStorage storage;
    RemindersCollection remindersTest;
    FenceClient client;
    NotificationAction receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        remindersTest = new RemindersCollection(this);
        client = Awareness.getFenceClient(this);
        storage = ReminderStorage.getInstance(this);
        reminderList = storage.getAll();
        linearLayout = findViewById(R.id.ll_scroll);
        receiver = new NotificationAction();
        storage.deleteAll();
        registerSavedFences();
        updateUI();
    }

    private void updateUI(){
        linearLayout.removeAllViews();
        for (Reminder r :
                reminderList) {
            ReminderView reminderView = new ReminderView(this);
            reminderView.setReminder(r);
            reminderView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            reminderView.setVisibility(View.VISIBLE);
            reminderView.setOnReminderDeletePressed(this);
            linearLayout.addView(reminderView);
        }

    }
    private void registerSavedFences() {
        for (Reminder r :
                reminderList) {
            registerReminder(r);
        }
    }


    public void fabPressed(View view){
        Toast.makeText(this, "FAB PRESSED!", Toast.LENGTH_SHORT).show();
        startActivityForResult(new Intent(this, CreateReminderActivity.class), MAIN_REQUEST_CODE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_tests, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.item_test_5_single:
                registerFiveSingleReminders();
                break;
            case R.id.item_test_5_compound:
                registerFiveCompoundReminders();
                break;
            case R.id.item_test_10_single:
                registerTenSingleReminders();
                break;
            case R.id.item_test_10_compound:
                registerTenCompoundReminders();
                break;
        }
        return true;
    }

    private void registerTenCompoundReminders() {
        for(int i = 0; i < 10; i++){
            Reminder r = remindersTest.compoundReminders.get(i);
            registerReminder(r);
            saveInStorage(r);
            registerReminder(r);
            reminderList.add(r);

        }
        updateUI();
    }

    private void registerTenSingleReminders() {
        for(int i = 0; i < 10; i++){
            Reminder r = remindersTest.singleReminders.get(i);
            registerReminder(r);
            saveInStorage(r);
            registerReminder(r);
            reminderList.add(r);
        }
        updateUI();
    }

    private void registerFiveCompoundReminders() {
        for(int i = 0; i < 5; i++){
            Reminder r = remindersTest.compoundReminders.get(i);
            registerReminder(r);
            saveInStorage(r);
            registerReminder(r);
            reminderList.add(r);
        }
        updateUI();
    }

    private void registerFiveSingleReminders() {
        for(int i = 0; i < 5; i++){
            Reminder r = remindersTest.singleReminders.get(i);
            registerReminder(r);
            saveInStorage(r);
            registerReminder(r);
            reminderList.add(r);
        }
        updateUI();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == MAIN_REQUEST_CODE){
            if(resultCode == RESULT_OK){
                Reminder r = (Reminder) data.getSerializableExtra("reminder");
                saveInStorage(r);
                registerReminder(r);
                reminderList.add(r);

            }
        }
        updateUI();
    }

    private void registerReminder(final Reminder r) {

        IntentFilter filter = new IntentFilter(r.getUid());
        registerReceiver(receiver, filter);

        Intent intent = new Intent(r.getUid());
        Bundle bundle = new Bundle();
        bundle.putString("text", r.getText());
        intent.putExtra("extra", bundle);
        PendingIntent pi = PendingIntent.getBroadcast(this, 123, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        client.updateFences(new FenceUpdateRequest.Builder()
        .addFence(r.getUid(), r.getFence(), pi).build()).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d(TAG, "onSuccess: Fence registered. " + r.getText());
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e(TAG, "onFailure: ", e);
            }
        });
    }

    private void saveInStorage(Reminder r) {
        ReminderStorage storage = ReminderStorage.getInstance(this);
        storage.saveReminder(r);
    }


    @Override
    public void onReminderDeletePressed(Reminder reminder) {
        storage.delete(reminder.getUid());

        client.updateFences(new FenceUpdateRequest.Builder()
                .removeFence(reminder.getUid())
                .build());
        reminderList.remove(reminder);
        Toast.makeText(this, "Deleted " + reminder.getText(), Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                updateUI();
                Log.d(TAG, "run: post-delayed");
            }
        }, 1000L);
    }
}
