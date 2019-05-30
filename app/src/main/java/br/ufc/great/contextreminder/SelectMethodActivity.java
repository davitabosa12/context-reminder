package br.ufc.great.contextreminder;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import br.ufc.great.contextreminder.model.trigger.ActivityTrigger;
import br.ufc.great.contextreminder.model.trigger.LocationTrigger;
import br.ufc.great.contextreminder.model.trigger.TimeTrigger;
import br.ufc.great.contextreminder.model.trigger.Trigger;

public class SelectMethodActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    public static final int REQUEST_CODE = 3211;
    ListView dynamic;
    String[] options;
    ArrayAdapter<String> adapter;
    Provider selectedContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_method);
        selectedContext = (Provider) getIntent().getSerializableExtra("selected_context");
        if(selectedContext == null){
            Log.e("SelectMethodActivity", "No selected context provided");
            throw new RuntimeException("ERROR");
        }
        int resource = -1;
        switch(selectedContext){
            case LOCATION:
                resource = R.array.location_actions;
                break;
            case TIME:
                resource = R.array.time_actions;
                break;
            case ACTIVITY:
                resource = R.array.activity_actions;
                break;
            case HEADPHONE:
                resource = R.array.headphone_actions;
                break;
        }
        options = getResources().getStringArray(resource);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, options);

        dynamic = findViewById(R.id.dynamic);
        dynamic.setOnItemClickListener(this);
        dynamic.setAdapter(adapter);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(this, options[position], Toast.LENGTH_SHORT).show();
        Intent i = new Intent(this, EditTriggerActivity.class);
        i.putExtra("provider", selectedContext);
        i.putExtra("trigger", extractTrigger(position));
        startActivityForResult(i, REQUEST_CODE);
    }


    private Trigger extractTrigger(int position) {
        //TODO: Read from Strings.xml and return the appropriate enum.
        switch(selectedContext){
            case LOCATION:
                if(position == 0){
                    //entering
                    return LocationTrigger.ENTERING;
                } else if(position == 1){
                    //exiting
                    return LocationTrigger.EXITING;

                } else if(position == 2){
                    //in
                    return LocationTrigger.IN;
                }
                break;
            case TIME:
                if(position == 0){
                    //every day at
                    return TimeTrigger.EVERY_DAY_AT;
                } else if(position == 1){
                    //Every hour at
                    return TimeTrigger.EVERY_HOUR_AT;

                } else if(position == 2){
                    //Every day of the week at
                    return TimeTrigger.EVERY_DAY_OF_THE_WEEK_AT;
                } else if(position == 3){
                    //Every month on the
                    return TimeTrigger.EVERY_MONTH_ON_THE;
                }

                break;
            case ACTIVITY:
                if(position == 0){
                    //during
                    return ActivityTrigger.DURING;
                } else if(position == 1){
                    //starting
                    return  ActivityTrigger.STARTING;

                } else if(position == 2){
                    //stopping
                    return ActivityTrigger.STOPPING;
                }
                break;
            case HEADPHONE:
                //do nothing
                return null;
        }
        return null;

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == REQUEST_CODE){
            if(resultCode == RESULT_OK){
                setResult(RESULT_OK, data);
            } else{
                setResult(resultCode);
            }

        }
        finish();
    }


}
