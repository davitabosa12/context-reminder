package br.ufc.great.contextreminder;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;

public class EditTriggerActivity extends AppCompatActivity {

    FrameLayout frame;
    FragmentManager fm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_trigger);
        frame = findViewById(R.id.frame);
        fm = getSupportFragmentManager();
        for(Fragment f : fm.getFragments()){
            Log.d("EditTrigger", f.toString());
        }

    }
}
