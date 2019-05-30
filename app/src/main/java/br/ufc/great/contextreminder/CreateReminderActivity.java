package br.ufc.great.contextreminder;

import android.content.Intent;
import android.media.Image;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.UUID;

import br.ufc.great.contextreminder.model.Reminder;
import br.ufc.great.contextreminder.model.trigger.ActivityTrigger;
import br.ufc.great.contextreminder.model.trigger.LocationTrigger;
import br.ufc.great.contextreminder.model.trigger.TimeTrigger;
import br.ufc.great.contextreminder.model.trigger.Trigger;
import smd.ufc.br.easycontext.fence.AggregateFence;
import smd.ufc.br.easycontext.fence.DetectedActivityFence;
import smd.ufc.br.easycontext.fence.Fence;
import smd.ufc.br.easycontext.fence.HeadphoneFence;
import smd.ufc.br.easycontext.fence.LocationFence;
import smd.ufc.br.easycontext.fence.TimeFence;


public class CreateReminderActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_CREATE = 193;
    public static final int REQUEST_CODE_EDIT = 15478;
    Reminder reminder;
    Fence rule;
    Button btnCreateContext;
    ImageButton btnEditContext;
    TextView txvSelectedContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_reminder);
        btnCreateContext = findViewById(R.id.btn_create_context);
        btnEditContext = findViewById(R.id.btn_edit_context);
        txvSelectedContext = findViewById(R.id.txv_selected_context);
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

    public void create(View v) {
        startActivityForResult(new Intent(this, SelectContextActivity.class), REQUEST_CODE_CREATE);
    }

    public void edit(View v) {
        if (rule == null) {
            //error
        } else {
            Intent i = new Intent(this, EditTriggerActivity.class);

            startActivityForResult(i, REQUEST_CODE_EDIT);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode) {
            case REQUEST_CODE_CREATE:

                rule = extractFrom(data);
                showEdit();
                updateButtonIcon();
                break;


            case REQUEST_CODE_EDIT:

                updateButtonIcon();
                break;
        }
    }

    private Fence extractFrom(Intent data) throws Exception {
        Provider provider = (Provider) data.getSerializableExtra("provider");

        switch (provider){
            case LOCATION: {
                LocationTrigger trigger = (LocationTrigger) data.getSerializableExtra("trigger");
                LocationFence.Builder builder = new LocationFence.Builder();
                builder.setName(UUID.randomUUID().toString());
                builder.setAction(new NotificationAction());
                double lat = data.getDoubleExtra("latitude", 0);
                double lon = data.getDoubleExtra("longitude", 0);
                double radius = data.getDoubleExtra("radius", 0);
                switch (trigger) {
                    case IN:
                        long dwell = data.getLongExtra("dwell_time",0);
                        builder.in(lat, lon, radius, dwell);
                        break;
                    case EXITING:
                        builder.exiting(lat, lon, radius);
                        break;
                    case ENTERING:
                        builder.entering(lat, lon, radius);
                        break;
                }
                return builder.build();
                break;
            }
            case HEADPHONE:

                break;
            case ACTIVITY: {
                ActivityTrigger trigger = (ActivityTrigger) data.getSerializableExtra("trigger");
                DetectedActivityFence.Builder builder = new DetectedActivityFence.Builder();
                builder.setName(UUID.randomUUID().toString());
                builder.setAction(new NotificationAction());
                ArrayList<Integer> fromData;
                fromData = data.getIntegerArrayListExtra("activities");
                int[] activities = new int[fromData.size()];
                int counter = 0;
                for(Integer i : fromData){
                    activities[counter++] = i;
                }


                switch(trigger){
                    case STARTING:
                        builder.starting(activities);
                        break;
                    case STOPPING:
                        builder.stopping(activities);
                        break;
                    case DURING:
                        builder.during(activities);
                        break;
                }
                return builder.build();
                break;
            }
            case TIME:{
                TimeTrigger trigger = (TimeTrigger) data.getSerializableExtra("trigger");
                TimeFence.Builder builder = new TimeFence.Builder();
                int hour = data.getIntExtra("hour", 0);
                int minute = data.getIntExtra("minute", 0);

                builder.setName(UUID.randomUUID().toString());
                builder.setAction(new NotificationAction());
                switch(trigger){
                    case EVERY_DAY_OF_THE_WEEK_AT:
                        //TODO: THE GREAT REFACTOR EASYCONTEXT
                        builder.inIntervalOfDay()
                        break;
                    case EVERY_MONTH_ON_THE:
                        throw new Exception("lol no");
                        break;
                    case EVERY_HOUR_AT:
                        throw new Exception("lol no");
                        break;
                    case EVERY_DAY_AT:
                        long calc = hour * 3600 * 1000 + minute * 60 * 1000;
                        long end = 15*60*1000; //15 minutes
                        builder.inDailyInterval(null, calc, calc+ end);
                        break;

                }
            }

                break;
        }
        return null;
    }

    private void updateButtonIcon() {
        if(rule instanceof HeadphoneFence){

            btnEditContext.setImageResource(R.drawable.ic_headset_gray_24dp);
        } else if(rule instanceof LocationFence){
            btnEditContext.setImageResource(R.drawable.ic_gps_fixed_gray_24dp);
        } else if(rule instanceof DetectedActivityFence){
            btnEditContext.setImageResource(R.drawable.ic_activity_gray_24dp);
        } else if(rule instanceof TimeFence){
            btnEditContext.setImageResource(R.drawable.ic_access_time_black_24dp);
        } else if(rule instanceof AggregateFence){
            btnEditContext.setImageResource(R.drawable.ic_compound_gray_24dp);
        }
    }


}
