package br.ufc.great.contextreminder;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import br.ufc.great.contextreminder.model.Reminder;
import br.ufc.great.contextreminder.model.ReminderStorage;

/**
 * TODO: document your custom view class.
 */
public class ReminderView extends ConstraintLayout implements DialogInterface.OnClickListener {
    Reminder reminder;
    TextView txvReminderText;
    ImageButton btnDelete;
    OnReminderDeletePressed mCallback;


    public ReminderView(Context context) {
        super(context);
        init(context);
    }

    public ReminderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ReminderView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.reminder_view, this);
        onFinishInflate();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        txvReminderText = findViewById(R.id.txv_reminder_text);
        btnDelete = findViewById(R.id.btn_delete);
        btnDelete.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteButton();
            }
        });
    }

    public void deleteButton(){
        new AlertDialog.Builder(getContext())
                .setTitle("Deleting " + reminder.getText())
                .setMessage("Are you sure ?")
                .setPositiveButton("Yes",this)
                .setNegativeButton("No", null)
                .show();
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        if(which == DialogInterface.BUTTON_POSITIVE){
            if (mCallback != null) {
                mCallback.onReminderDeletePressed(reminder);
            }
        } else {
            Toast.makeText(getContext(), "Canceled", Toast.LENGTH_SHORT).show();
        }
    }

    public void setOnReminderDeletePressed(OnReminderDeletePressed callback){
        this.mCallback = callback;
    }

    public Reminder getReminder() {
        return reminder;
    }

    public void setReminder(Reminder reminder) {
        this.reminder = reminder;
        txvReminderText.setText(reminder.getText());
    }

    public interface OnReminderDeletePressed{
        void onReminderDeletePressed(Reminder reminder);
    }
}
