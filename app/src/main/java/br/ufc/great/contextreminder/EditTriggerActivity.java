package br.ufc.great.contextreminder;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.io.Serializable;

import br.ufc.great.contextreminder.fragment.ActivityFragment;
import br.ufc.great.contextreminder.fragment.LocationFragment;
import br.ufc.great.contextreminder.fragment.TimeFragment;
import br.ufc.great.contextreminder.model.trigger.ActivityTrigger;
import br.ufc.great.contextreminder.model.trigger.HeadphoneTrigger;
import br.ufc.great.contextreminder.model.trigger.LocationTrigger;
import br.ufc.great.contextreminder.model.trigger.TimeTrigger;
import br.ufc.great.contextreminder.model.trigger.Trigger;

public class EditTriggerActivity extends FragmentActivity{

    private static final String TAG ="EditTriggerActivity";
    FragmentManager fm;

    String method;
    Provider provider;
    private Trigger trigger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_trigger);
        /*fenceToBeEdited = (Fence) getIntent().getSerializableExtra("fence");
        if (fenceToBeEdited == null) {
            //user is creating new fence...
            provider = (Provider) getIntent().getSerializableExtra("provider");
            //TODO: trigger provided is a string from strings.xml. Extract accordingly.
            trigger = (Trigger) getIntent().getSerializableExtra("trigger");
            updateFragment();
        } else {
            //fence already exists, get provider and method from info
            provider = extractFenceRules(fenceToBeEdited.getRule());
            //method = extractMethodName(provider, fenceToBeEdited.getMethodName());
        }
*/


    }
/*
    private void updateFragment() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment fragment;
        switch(provider){
            case LOCATION:
                fragment = LocationFragment.newInstance((LocationTrigger) trigger);
                ft.replace(R.id.frame, fragment);
                break;
            case TIME:
                fragment = TimeFragment.newInstance((TimeTrigger) trigger);
                ft.replace(R.id.frame, fragment);
                break;
            case ACTIVITY:
                fragment = ActivityFragment.newInstance((ActivityTrigger) trigger);
                ft.replace(R.id.frame, fragment);
                break;
            case HEADPHONE:
                //just return the fence...
                Bundle b = new Bundle();
                b.putSerializable("provider", Provider.HEADPHONE);
                Trigger trigger = (Trigger) getIntent().getExtras().getSerializable("trigger");
                b.putSerializable("trigger", trigger);
                if(trigger == HeadphoneTrigger.PLUGGING_IN){
                    b.putSerializable("method", HeadphoneTrigger.PLUGGING_IN);
                }
                else if(trigger == HeadphoneTrigger.UNPLUGGING)
                    b.putSerializable("method", HeadphoneTrigger.UNPLUGGING);
                Intent i = new Intent();
                i.putExtras(b);
                setResult(RESULT_OK, i);
                finish();
                break;
        }
        ft.commit();

    }

    @Override
    public void onTimeRuleSelected(Bundle time) {
        if(time.getBoolean("cancel")){
            Log.d(TAG, "onTimeRuleSelected: USER CANCELED");
            setResult(RESULT_CANCELED);
        }
        else {
            Intent response = new Intent();
            response.putExtras(time);
            setResult(RESULT_OK, response);
        }
    }



    public Provider extractFenceRules(Rule rule){

            if(rule instanceof LocationRule)
                return Provider.LOCATION;
            else if(rule instanceof HeadphoneRule)
                return Provider.HEADPHONE;
            else if(rule instanceof DetectedActivityRule)
                return Provider.ACTIVITY;
            else if(rule instanceof TimeRule)
                return Provider.TIME;
            else
                throw new RuntimeException("Extract fence fence failed, not location, headphone, activity or time");



    }

    @Override
    public void onActivityRuleSelected(Bundle activity) {
        if(activity.getBoolean("cancel")){
            Toast.makeText(this, "USER CANCELED", Toast.LENGTH_SHORT).show();
            setResult(RESULT_CANCELED);
        } else {
            Intent response = new Intent();
            response.putExtras(activity);
            setResult(RESULT_OK, response);
        }
        finish();
    }

    @Override
    public void onLocationRuleSelected(Bundle location) {
        if(location.getBoolean("cancel")){
            Toast.makeText(this, "USER CANCELED", Toast.LENGTH_SHORT).show();
            setResult(RESULT_CANCELED);
        } else {
            Intent response = new Intent();
            response.putExtras(location);
            setResult(RESULT_OK, response);
        }
    }
    */
}
