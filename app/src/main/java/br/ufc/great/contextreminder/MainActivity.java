package br.ufc.great.contextreminder;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import br.ufc.great.contextreminder.model.Reminder;
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == MAIN_REQUEST_CODE){
            if(resultCode == RESULT_OK){
                Reminder r = (Reminder) data.getSerializableExtra("reminder");
                saveInStorage(r);
                registerFence(r);
            }
        }
    }

    private void registerFence(Reminder r) {

    }

    private void saveInStorage(Reminder r) {

    }
}
