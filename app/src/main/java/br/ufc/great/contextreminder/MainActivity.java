package br.ufc.great.contextreminder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import smd.ufc.br.easycontext.Snapshot;

public class MainActivity extends AppCompatActivity {

    private static final int MAIN_REQUEST_CODE = 357;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void fabPressed(View view){
        Toast.makeText(this, "FAB PRESSED!", Toast.LENGTH_SHORT).show();
        startActivityForResult(new Intent(this, CreateReminderActivity.class), MAIN_REQUEST_CODE);
    }
}
