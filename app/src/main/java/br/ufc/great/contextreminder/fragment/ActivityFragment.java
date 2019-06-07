package br.ufc.great.contextreminder.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import br.ufc.great.contextreminder.Provider;
import br.ufc.great.contextreminder.R;
import br.ufc.great.contextreminder.model.trigger.ActivityTrigger;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnActivityRuleSelected} interface
 * to handle interaction events.
 * Use the {@link ActivityFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ActivityFragment extends Fragment implements View.OnClickListener {

    private OnActivityRuleSelected mListener;
    private ActivityTrigger trigger;
    private Map<CheckBox, Integer> activities;
    private Button ok, cancel;

    public ActivityFragment() {
        // Required empty public constructor
    }


    public static Fragment newInstance(ActivityTrigger trigger) {
        ActivityFragment fragment = new ActivityFragment();
        Bundle args = new Bundle();
        args.putSerializable("trigger", trigger);
        fragment.setArguments(args);
        return fragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            trigger = (ActivityTrigger) getArguments().getSerializable("trigger");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_activity, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        activities = new HashMap<>();
        activities.put((CheckBox) getActivity().findViewById(R.id.cb_vehicle), 0);
        activities.put((CheckBox) getActivity().findViewById(R.id.cb_bicycle), 1);
        activities.put((CheckBox) getActivity().findViewById(R.id.cb_foot), 2);
        activities.put((CheckBox) getActivity().findViewById(R.id.cb_running), 8);
        activities.put((CheckBox) getActivity().findViewById(R.id.cb_still), 3);
        activities.put((CheckBox) getActivity().findViewById(R.id.cb_walking), 7);

        ok = getActivity().findViewById(R.id.btn_ok);
        cancel = getActivity().findViewById(R.id.btn_cancel);

        ok.setOnClickListener(this);
        cancel.setOnClickListener(this);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnActivityRuleSelected) {
            mListener = (OnActivityRuleSelected) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnActivityRuleSelected");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btn_ok:
                Bundle activity = new Bundle();
                activity.putSerializable("trigger", trigger);
                activity.putSerializable("provider", Provider.ACTIVITY);
                ArrayList<Integer> activitySelected = new ArrayList<>();

                for(Map.Entry<CheckBox, Integer> pair : activities.entrySet()) {
                    if(pair.getKey().isChecked()){
                        activitySelected.add(pair.getValue());
                    }
                }
                activity.putIntegerArrayList("activities", activitySelected);
                mListener.onActivityRuleSelected(activity);
                getActivity().finish();
                break;
            case R.id.btn_cancel:
                Bundle c = new Bundle();
                c.putBoolean("cancel", true);
                mListener.onActivityRuleSelected(c);
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
    public interface OnActivityRuleSelected {
        // TODO: Update argument type and name
        void onActivityRuleSelected(Bundle activity);
    }
}
