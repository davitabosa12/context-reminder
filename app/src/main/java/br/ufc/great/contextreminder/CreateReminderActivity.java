package br.ufc.great.contextreminder;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import br.ufc.great.contextreminder.model.Reminder;
import br.ufc.great.contextreminder.model.trigger.ActivityTrigger;
import br.ufc.great.contextreminder.model.trigger.HeadphoneTrigger;
import br.ufc.great.contextreminder.model.trigger.LocationTrigger;
import br.ufc.great.contextreminder.model.trigger.TimeTrigger;
import smd.ufc.br.easycontext.fence.AggregateRule;
import smd.ufc.br.easycontext.fence.DetectedActivityRule;
import smd.ufc.br.easycontext.fence.Fence;
import smd.ufc.br.easycontext.fence.HeadphoneRule;
import smd.ufc.br.easycontext.fence.LocationRule;
import smd.ufc.br.easycontext.fence.Rule;
import smd.ufc.br.easycontext.fence.TimeRule;


public class CreateReminderActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_CREATE = 193;
    public static final int REQUEST_CODE_EDIT = 15478;
    private static final String TAG = "CreateReminderActivity";
    Reminder reminder;
    Fence fence;
    Button btnCreateContext;
    ImageButton btnEditContext;
    TextView txvSelectedContext;
    EditText edtReminderText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_reminder);
        btnCreateContext = findViewById(R.id.btn_create_context);
        btnEditContext = findViewById(R.id.btn_edit_context);
        txvSelectedContext = findViewById(R.id.txv_selected_context);
        edtReminderText = findViewById(R.id.edt_text_reminder);
        hideEdit();
    }

    private void showEdit() {
        //show the context button and text
        btnEditContext.setVisibility(View.VISIBLE);
        txvSelectedContext.setVisibility(View.VISIBLE);
        //hide create context button
        btnCreateContext.setVisibility(View.GONE);
    }

    private void hideEdit() {
        //hide the context button and text
        btnEditContext.setVisibility(View.GONE);
        txvSelectedContext.setVisibility(View.GONE);
        //show create context button
        btnCreateContext.setVisibility(View.VISIBLE);
    }

    public void ok(View v){
        String reminderText = edtReminderText.getText().toString();
        if (fence == null) {
            //user did not selected a fence...
            Toast.makeText(this, "Please select a trigger", Toast.LENGTH_SHORT).show();
        } else if(reminderText.isEmpty()){
            Toast.makeText(this, "Text is empty", Toast.LENGTH_SHORT).show();
        } else{
            reminder = new Reminder.Builder()
                    .setText(reminderText)
                    .setRule(fence)
                    .setRepeating(true)
                    .build();
            Log.d(TAG, "ok: reminder->" + reminder.toString());
            Intent i = new Intent();
            i.putExtra("reminder", reminder);
            setResult(RESULT_OK, i);
            finish();

        }
    }

    public void cancel(View v){
        setResult(RESULT_CANCELED);
        finish();
    }

    public void create(View v) {
        startActivityForResult(new Intent(this, SelectContextActivity.class), REQUEST_CODE_CREATE);
    }

    public void edit(View v) {
        create(v);
        return;
        /*if (fence == null) {
            //error
        } else {
            Intent i = new Intent(this, EditTriggerActivity.class);
            i.putExtra("fence", fence);
            startActivityForResult(i, REQUEST_CODE_EDIT);
        }*/
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode) {
            case REQUEST_CODE_CREATE:

                try {
                    fence = extractFrom(data);
                } catch (Exception e) {
                    Log.e("CreateReminderActivity", "onActivityResult: DATA IS NULL", e);
                }
                if (fence == null) {
                    Log.e("CreateReminderActivity", "onActivityResult: FENCE IS NULL");
                } else {

                    showEdit();
                    updateButtonIcon();
                }
                break;


            case REQUEST_CODE_EDIT:

                updateButtonIcon();
                break;
        }
    }

    private Fence extractFrom(Intent data) throws Exception {
        Bundle extras = data.getExtras();
        Log.d(TAG, "extractFrom: " + extras.toString());
        Provider provider = (Provider) data.getSerializableExtra("provider");

        switch (provider){
            case LOCATION: {
                LocationTrigger trigger = (LocationTrigger) data.getSerializableExtra("trigger");
                LocationRule locationRule = null;
                String name = UUID.randomUUID().toString();

                double lat = data.getDoubleExtra("latitude", 0);
                double lon = data.getDoubleExtra("longitude", 0);
                double radius = data.getDoubleExtra("radius", 0);
                switch (trigger) {
                    case IN:
                        long dwell = data.getLongExtra("dwell_time",0);
                        locationRule = LocationRule.in(lat, lon, radius, dwell);
                        break;
                    case EXITING:
                        locationRule = LocationRule.exiting(lat, lon, radius);
                        break;
                    case ENTERING:
                        locationRule = LocationRule.entering(lat, lon, radius);
                        break;
                }
                Fence location;
                location = new Fence(name, locationRule, new NotificationAction());
                return location;
            }
            case HEADPHONE:{

                HeadphoneTrigger trigger = (HeadphoneTrigger) data.getSerializableExtra("trigger");
                HeadphoneRule rule = null;
                switch (trigger){
                    case PLUGGING_IN:
                        rule = HeadphoneRule.pluggingIn();
                        break;
                    case UNPLUGGING:
                        rule = HeadphoneRule.unplugging();
                        break;
                }
                return new Fence(UUID.randomUUID().toString(), rule, new NotificationAction());
            }
            case ACTIVITY: {
                ActivityTrigger trigger = (ActivityTrigger) data.getSerializableExtra("trigger");
                DetectedActivityRule daRule = null;
                ArrayList<Integer> fromData;
                fromData = data.getIntegerArrayListExtra("activities");
                int[] activities = new int[fromData.size()];
                int counter = 0;
                for(Integer i : fromData){
                    activities[counter++] = i;
                }
                switch(trigger){
                    case STARTING:
                        daRule = DetectedActivityRule.starting(activities);
                        break;
                    case STOPPING:
                        daRule = DetectedActivityRule.stopping(activities);
                        break;
                    case DURING:
                        daRule = DetectedActivityRule.during(activities);
                        break;
                }
                return new Fence(UUID.randomUUID().toString(), daRule, new NotificationAction());
            }
            case TIME: {
                TimeTrigger trigger = (TimeTrigger) data.getSerializableExtra("trigger");
                TimeRule timeRule = null;
                Fence fence;
                int hour = data.getIntExtra("hour", 0);
                int minute = data.getIntExtra("minute", 0);

                switch (trigger) {
                    case EVERY_DAY_OF_THE_WEEK_AT:
                        boolean[] daysOfWeek = data.getBooleanArrayExtra("daysOfWeek");
                        List<Rule> rules = new ArrayList<>();
                        for (int i = 0; i < daysOfWeek.length; i++) {
                            if (daysOfWeek[i]) {
                                long calc = hour * 3600 * 1000 + minute * 60 * 1000;
                                long end = 15 * 60 * 1000; //15 minutes
                                rules.add(TimeRule.inIntervalOfDay(i + 1, null, calc, calc + end));
                            }
                        }
                        fence = new Fence(UUID.randomUUID().toString(), AggregateRule.or(rules), new NotificationAction());
                        return fence;
                    case EVERY_MONTH_ON_THE:
                        throw new Exception("lol no");
                    case EVERY_HOUR_AT:
                        throw new Exception("lol no");
                    case EVERY_DAY_AT:
                        long calc = hour * 3600 * 1000 + minute * 60 * 1000;
                        long end = 15 * 60 * 1000; //15 minutes
                        timeRule = TimeRule.inDailyInterval(null, calc, calc + end);
                        break;
                }
                return new Fence(UUID.randomUUID().toString(), timeRule, new NotificationAction());
            }
        }
        return null;
    }

    private void updateButtonIcon() {
        Log.d(TAG, "updateButtonIcon: " + fence.toString());
        if(fence.getRule() instanceof HeadphoneRule){

            btnEditContext.setImageResource(R.drawable.ic_headset_gray_24dp);
        } else if(fence.getRule() instanceof LocationRule){
            btnEditContext.setImageResource(R.drawable.ic_gps_fixed_gray_24dp);
        } else if(fence.getRule() instanceof DetectedActivityRule){
            btnEditContext.setImageResource(R.drawable.ic_activity_gray_24dp);
        } else if(fence.getRule() instanceof TimeRule){
            btnEditContext.setImageResource(R.drawable.ic_access_time_black_24dp);
        } else if(fence.getRule() instanceof AggregateRule){
            btnEditContext.setImageResource(R.drawable.ic_compound_gray_24dp);
        }
    }


}
