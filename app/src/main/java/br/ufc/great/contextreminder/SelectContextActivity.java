package br.ufc.great.contextreminder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class SelectContextActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_context);
    }

    public void headphone(View v){
        startActivityForResult(new Intent(this, SelectMethodActivity.class), 987);
    }
    public void location(View v){

    }
    public void activity(View v){

    }
    public void time(View v){

    }

}
