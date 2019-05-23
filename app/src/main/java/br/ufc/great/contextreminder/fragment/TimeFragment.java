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
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.Date;

import br.ufc.great.contextreminder.EditTriggerActivity;
import br.ufc.great.contextreminder.R;



public class TimeFragment extends Fragment implements TimePickerDialog.OnTimeSetListener, View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private OnTimeRuleSelected mListener;

    private EditText edtTime;
    private CheckBox[] daysOfWeek;
    private int hourOfDay, minute;
    public TimeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TimeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TimeFragment newInstance(String param1, String param2) {
        TimeFragment fragment = new TimeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public static Fragment newInstance(String method) {


    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

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

        getActivity().findViewById(R.id.btn_ok).setOnClickListener(this);
        getActivity().findViewById(R.id.btn_cancel).setOnClickListener(this);

        //set TimePicker
        edtTime.setOnClickListener(this);


        updateUI();
    }

    private void updateUI() {

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Bundle bundle) {
        if (mListener != null) {
            mListener.onTimeRuleSelected(bundle);
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
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.edt_time:{
                Calendar now = Calendar.getInstance();

                TimePickerDialog dialog = new TimePickerDialog(getContext(), this, now.get(Calendar.HOUR_OF_DAY),now.get(Calendar.MINUTE),true);
                dialog.show();
                break;
            }
            case R.id.btn_ok:{
                boolean[] days = new boolean[7];
                for(int i = 0; i < daysOfWeek.length; i++){
                    days[i] = daysOfWeek[i].isChecked();
                }
                Bundle t = new Bundle();
                t.putInt("hour", hourOfDay);
                t.putInt("minute", minute);
                t.putBooleanArray("daysOfWeek", days);

                mListener.onTimeRuleSelected(t);
                break;
            }
            case R.id.btn_cancel:
                Bundle t = new Bundle();
                t.putBoolean("cancel", true);
                mListener.onTimeRuleSelected(t);
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
