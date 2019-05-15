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
import smd.ufc.br.easycontext.Fence;

public class CreateReminderActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_CREATE = 193;
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

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode) {
            case REQUEST_CODE_CREATE:
                break;

        }
    }


}
