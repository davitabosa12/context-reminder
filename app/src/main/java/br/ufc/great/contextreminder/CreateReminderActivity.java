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

import br.ufc.great.contextreminder.model.Reminder;
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
                rule = (Fence) data.getSerializableExtra("fence");
                showEdit();
                updateButtonIcon();
                break;


            case REQUEST_CODE_EDIT:
                rule = (Fence) data.getSerializableExtra("fence");
                updateButtonIcon();
                break;
        }
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
