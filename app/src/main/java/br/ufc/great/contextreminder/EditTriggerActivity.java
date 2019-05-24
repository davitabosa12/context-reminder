package br.ufc.great.contextreminder;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.Serializable;

import br.ufc.great.contextreminder.fragment.ActivityFragment;
import br.ufc.great.contextreminder.fragment.LocationFragment;
import br.ufc.great.contextreminder.fragment.TimeFragment;
import smd.ufc.br.easycontext.fence.Fence;
import smd.ufc.br.easycontext.fence.HeadphoneFence;
import smd.ufc.br.easycontext.fence.type.FenceType;

public class EditTriggerActivity extends AppCompatActivity implements TimeFragment.OnTimeRuleSelected {

    private static final String TAG ="EditTriggerActivity";
    FragmentManager fm;

    String method;
    Provider provider;
    Fence fenceToBeEdited;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_trigger);
        fenceToBeEdited = (Fence) getIntent().getSerializableExtra("fence");
        if (fenceToBeEdited == null) {
            //user is creating new fence...
            provider = (Provider) getIntent().getSerializableExtra("provider");
            //TODO: trigger provided is a string from strings.xml. Extract accordingly.
            method = getIntent().getStringExtra("trigger");
            updateFragment();
        } else {
            //fence already exists, get provider and method from info
            provider = extractFenceRules(fenceToBeEdited.getType());
            //method = extractMethodName(provider, fenceToBeEdited.getMethodName());
        }



    }

    private void updateFragment() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment fragment;
        switch(provider){
            case LOCATION:
                fragment = LocationFragment.newInstance(method);
                ft.replace(R.id.frame, fragment);
                break;
            case TIME:
                fragment = TimeFragment.newInstance(method);
                ft.replace(R.id.frame, fragment);
                break;
            case ACTIVITY:
                fragment = ActivityFragment.newInstance(method);
                ft.replace(R.id.frame, fragment);
                break;
            case HEADPHONE:
                HeadphoneFence.Builder builder = new HeadphoneFence.Builder();
                //just return the fence...
                if(method.equalsIgnoreCase("plugging in"))
                    builder.pluggingIn();
                else if(method.equalsIgnoreCase("unplugging"))
                    builder.unplugging();
                Intent i = new Intent();
                i.putExtra("fence", (Serializable) builder.build());
                setResult(RESULT_OK, i);
                break;
        }

    }

    @Override
    public void onTimeRuleSelected(Bundle time) {
        if(time.getBoolean("cancel"))
            Log.d(TAG, "onTimeRuleSelected: USER CANCELED");
        else {
            time.getString("");
        }
    }

    public Provider extractFenceRules(FenceType ft){
        switch (ft){
            case LOCATION:
                return Provider.LOCATION;
            case HEADPHONE:
                return Provider.HEADPHONE;
            case DETECTED_ACTIVITY:
                return Provider.ACTIVITY;
            case TIME:
                return Provider.TIME;
                default:
                    throw new RuntimeException("Extract fence rule failed, not location, headphone, activity or time");
        }


    }
}
