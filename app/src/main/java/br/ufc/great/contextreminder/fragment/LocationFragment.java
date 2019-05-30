package br.ufc.great.contextreminder.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import br.ufc.great.contextreminder.Provider;
import br.ufc.great.contextreminder.R;
import br.ufc.great.contextreminder.model.trigger.LocationTrigger;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnLocationRuleSelected} interface
 * to handle interaction events.
 * Use the {@link LocationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LocationFragment extends Fragment implements View.OnClickListener {

    private OnLocationRuleSelected mListener;
    private String method;
    private LocationTrigger trigger;
    private Button ok, cancel;

    private EditText edtLatitude, edtLongitude, edtRadius, edtDwellTime;

    public LocationFragment() {
        // Required empty public constructor
    }

    public static Fragment newInstance(LocationTrigger trigger) {
        LocationFragment fragment = new LocationFragment();
        Bundle args = new Bundle();
        args.putSerializable("trigger", trigger);
        fragment.setArguments(args);
        return fragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
           trigger = (LocationTrigger) getArguments().getSerializable("trigger");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_location, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        edtDwellTime = getActivity().findViewById(R.id.edt_dwelltime);
        edtLatitude = getActivity().findViewById(R.id.edt_latitude);
        edtLongitude = getActivity().findViewById(R.id.edt_longitude);
        edtRadius = getActivity().findViewById(R.id.edt_radius);
        ok = getActivity().findViewById(R.id.btn_ok);
        cancel = getActivity().findViewById(R.id.btn_cancel);

        ok.setOnClickListener(this);
        cancel.setOnClickListener(this);

        switch (trigger){
            case ENTERING:
            case EXITING:
                edtDwellTime.setVisibility(View.GONE);
                break;
            case IN:
                edtDwellTime.setVisibility(View.VISIBLE);
                break;

        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnLocationRuleSelected) {
            mListener = (OnLocationRuleSelected) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnLocationRuleSelected");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_ok:
                double latitude = 0, longitude = 0, radius = 0;
                long dwell;
                try {
                    latitude = Double.parseDouble(edtLatitude.getText().toString());
                    longitude = Double.parseDouble(edtLongitude.getText().toString());
                    radius = Double.parseDouble(edtRadius.getText().toString());

                } catch (Exception e){
                    Log.e("TimeFragment", "onClick: not a number provider", e);
                }
                try{

                    dwell = Long.parseLong(edtDwellTime.getText().toString());
                } catch (Exception e){
                    dwell = 0;
                }
                Bundle location = new Bundle();
                location.putSerializable("trigger", trigger);
                location.putSerializable("provider", Provider.LOCATION);
                location.putDouble("latitude", latitude);
                location.putDouble("longitude", longitude);
                location.putDouble("radius", radius);
                location.putLong("dwell_time", dwell);
                mListener.onLocationRuleSelected(location);
                break;
            case R.id.btn_cancel:
                Bundle c = new Bundle();
                c.putBoolean("cancel", true);
                mListener.onLocationRuleSelected(c);
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
    public interface OnLocationRuleSelected {
        // TODO: Update argument type and name
        void onLocationRuleSelected(Bundle location);
    }
}
