package br.ufc.great.contextreminder;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class SelectContextActivity extends AppCompatActivity {

    public static final int SELECT_CONTEXT_REQUEST_CODE = 987;
    private Intent i;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_context);
        i = new Intent(this, SelectMethodActivity.class);
    }

    public void headphone(View v){
        i.putExtra("selected_context", Provider.HEADPHONE);
        startActivityForResult(i, SELECT_CONTEXT_REQUEST_CODE);
    }
    public void location(View v){
        i.putExtra("selected_context", Provider.LOCATION);
        startActivityForResult(i, SELECT_CONTEXT_REQUEST_CODE);
    }
    public void activity(View v){
        i.putExtra("selected_context", Provider.ACTIVITY);
        startActivityForResult(i, SELECT_CONTEXT_REQUEST_CODE);
    }
    public void time(View v){
        i.putExtra("selected_context", Provider.TIME);
        startActivityForResult(i, SELECT_CONTEXT_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == SELECT_CONTEXT_REQUEST_CODE){

        }
    }
}
