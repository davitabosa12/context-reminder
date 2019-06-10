package br.ufc.great.contextreminder.fragment;

import android.app.TimePickerDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.Date;

import br.ufc.great.contextreminder.EditTriggerActivity;
import br.ufc.great.contextreminder.Provider;
import br.ufc.great.contextreminder.R;
import br.ufc.great.contextreminder.model.trigger.TimeTrigger;


public class TimeFragment extends Fragment implements TimePickerDialog.OnTimeSetListener, View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    private OnTimeRuleSelected mListener;
    private TimeTrigger trigger;
    private EditText edtTime;
    private CheckBox[] daysOfWeek;
    private String[] timeMethods;
    private Spinner spDaysOfMonth, spTimeMinutes;
    private int hourOfDay, minute;
    TimePickerDialog dialog;

    public TimeFragment() {
        // Required empty public constructor
    }

    public static Fragment newInstance(TimeTrigger trigger) {
        TimeFragment fragment = new TimeFragment();
        Bundle args = new Bundle();
        args.putSerializable("trigger", trigger);
        fragment.setArguments(args);
        return fragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        timeMethods = getContext().getResources().getStringArray(R.array.time_actions);
        if (getArguments() != null) {
            this.trigger = (TimeTrigger) getArguments().getSerializable("trigger");
        } else {
            //throw new RuntimeException("A TimeTrigger was not provided. Always initialize with newInstance()");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        daysOfWeek = new CheckBox[7];
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_time, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        edtTime = getActivity().findViewById(R.id.edt_time);
        daysOfWeek[0] = getActivity().findViewById(R.id.cb_sunday);
        daysOfWeek[1] = getActivity().findViewById(R.id.cb_monday);
        daysOfWeek[2] = getActivity().findViewById(R.id.cb_tuesday);
        daysOfWeek[3] = getActivity().findViewById(R.id.cb_wednesday);
        daysOfWeek[4] = getActivity().findViewById(R.id.cb_thursday);
        daysOfWeek[5] = getActivity().findViewById(R.id.cb_friday);
        daysOfWeek[6] = getActivity().findViewById(R.id.cb_saturday);

        spDaysOfMonth = getActivity().findViewById(R.id.sp_days_month);
        spTimeMinutes = getActivity().findViewById(R.id.sp_time_minutes);

        getActivity().findViewById(R.id.btn_ok).setOnClickListener(this);
        getActivity().findViewById(R.id.btn_cancel).setOnClickListener(this);

        //set TimePicker
        edtTime.setOnClickListener(this);


        updateUI();
    }

    private void updateUI() {

        switch (trigger){
            case EVERY_DAY_AT:
                setDaysOfWeekVisibility(false);
                spTimeMinutes.setVisibility(View.GONE);
                spDaysOfMonth.setVisibility(View.GONE);
                break;
            case EVERY_HOUR_AT:
                setDaysOfWeekVisibility(false);
                spDaysOfMonth.setVisibility(View.GONE);
                edtTime.setVisibility(View.GONE);

                spTimeMinutes.setVisibility(View.VISIBLE);
                break;
            case EVERY_MONTH_ON_THE:
                setDaysOfWeekVisibility(false);
                spTimeMinutes.setVisibility(View.GONE);

                spDaysOfMonth.setVisibility(View.VISIBLE);
                break;
            case EVERY_DAY_OF_THE_WEEK_AT:
                spTimeMinutes.setVisibility(View.GONE);
                spDaysOfMonth.setVisibility(View.GONE);

                setDaysOfWeekVisibility(true);
                break;
        }

    }

    private void setDaysOfWeekVisibility(boolean show){
        for(int i = 0; i < daysOfWeek.length; i++){
            daysOfWeek[i].setVisibility(show? View.VISIBLE : View.GONE);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnTimeRuleSelected) {
            mListener = (OnTimeRuleSelected) context;
        } else {
            throw new RuntimeException(context.toString()
                   + " must implement OnTimeRuleSelected");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        StringBuilder sb = new StringBuilder();
        sb.append(hourOfDay < 10 ? "0" + hourOfDay : hourOfDay);
        sb.append(":");
        sb.append(minute < 10 ? "0" + minute : minute);
        edtTime.setText(sb.toString());
        this.hourOfDay = hourOfDay;
        this.minute = minute;
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (dialog != null) {
            dialog.dismiss();
            dialog = null;
        }
    }
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.edt_time:{
                Calendar now = Calendar.getInstance();

                dialog = new TimePickerDialog(getContext(), this, now.get(Calendar.HOUR_OF_DAY),now.get(Calendar.MINUTE),true);

                dialog.show();
                break;
            }
            case R.id.btn_ok:{
                boolean[] days = new boolean[7];
                for(int i = 0; i < daysOfWeek.length; i++){
                    days[i] = daysOfWeek[i].isChecked();
                }
                int daysMonth = spDaysOfMonth.getSelectedItemPosition();
                int timeMinutes = spTimeMinutes.getSelectedItemPosition();
                Bundle t = new Bundle();
                t.putSerializable("provider", Provider.TIME);
                t.putSerializable("trigger", trigger);
                //put info about the trigger
                t.putInt("hour", hourOfDay);
                t.putInt("minute", minute);
                t.putBooleanArray("daysOfWeek", days);
                t.putInt("time_minutes", 15* timeMinutes);
                t.putInt("days_month", daysMonth);

                mListener.onTimeRuleSelected(t);
                getActivity().finish();
                break;
            }
            case R.id.btn_cancel:
                Bundle t = new Bundle();
                t.putBoolean("cancel", true);
                mListener.onTimeRuleSelected(t);
                getActivity().finish();
                break;
        }

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnTimeRuleSelected {
        // TODO: Update argument type and name
        void onTimeRuleSelected(Bundle time);
    }
}
