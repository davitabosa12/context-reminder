package br.ufc.great.contextreminder;

import android.content.Context;

import com.google.android.gms.awareness.fence.AwarenessFence;
import com.google.android.gms.awareness.fence.DetectedActivityFence;
import com.google.android.gms.awareness.fence.HeadphoneFence;
import com.google.android.gms.awareness.state.HeadphoneState;

import java.util.ArrayList;
import java.util.List;

import br.ufc.great.contextreminder.model.Reminder;

public class RemindersCollection {
    List<Reminder> singleReminders;
    List<Reminder> compoundReminders;
    private List<AwarenessFence> singleRules, compoundRules;

    public RemindersCollection(Context context) {
        singleReminders = new ArrayList<>();
        compoundReminders = new ArrayList<>();

        singleRules = new ArrayList<>();
        compoundRules = new ArrayList<>();

        singleRules.add(HeadphoneFence.pluggingIn()); //Headphone plugging
        singleRules.add(HeadphoneFence.unplugging()); //Headphone unplugging
        singleRules.add(DetectedActivityFence.starting(DetectedActivityFence.STILL)); //Activity Starting Still
        singleRules.add(DetectedActivityFence.starting(DetectedActivityFence.ON_FOOT, DetectedActivityFence.WALKING)); //Activity started walking
        singleRules.add(DetectedActivityFence.starting(DetectedActivityFence.RUNNING)); //activity started running
        singleRules.add(DetectedActivityFence.starting(DetectedActivityFence.IN_VEHICLE)); //activity in vehicle
        singleRules.add(DetectedActivityFence.starting(DetectedActivityFence.ON_BICYCLE)); //activity on bicycle
        singleRules.add(DetectedActivityFence.stopping(DetectedActivityFence.STILL)); //activity stopping still
        singleRules.add(DetectedActivityFence.stopping(DetectedActivityFence.ON_FOOT, DetectedActivityFence.WALKING)); //activity stopping waking
        singleRules.add(DetectedActivityFence.stopping(DetectedActivityFence.IN_VEHICLE)); //activity stopping in vehicle



        compoundRules.add(AwarenessFence.and(HeadphoneFence.during(HeadphoneState.PLUGGED_IN), singleRules.get(2))); //plugged & still
        compoundRules.add(AwarenessFence.and(HeadphoneFence.during(HeadphoneState.PLUGGED_IN), singleRules.get(3))); //plugged & walking
        compoundRules.add(AwarenessFence.and(HeadphoneFence.during(HeadphoneState.PLUGGED_IN), singleRules.get(4))); //plugged & running
        compoundRules.add(AwarenessFence.and(HeadphoneFence.during(HeadphoneState.PLUGGED_IN), singleRules.get(5))); //plugged & in vehicle
        compoundRules.add(AwarenessFence.and(HeadphoneFence.during(HeadphoneState.PLUGGED_IN), singleRules.get(6))); //plugged & on bicycle
        compoundRules.add(AwarenessFence.and(HeadphoneFence.during(HeadphoneState.UNPLUGGED), singleRules.get(2)));  //unplugged & still
        compoundRules.add(AwarenessFence.and(HeadphoneFence.during(HeadphoneState.UNPLUGGED), singleRules.get(3)));  //unplugged & walking
        compoundRules.add(AwarenessFence.and(HeadphoneFence.during(HeadphoneState.UNPLUGGED), singleRules.get(4)));  //unplugged & running
        compoundRules.add(AwarenessFence.and(HeadphoneFence.during(HeadphoneState.UNPLUGGED), singleRules.get(5)));  //unplugged & in vehicle
        compoundRules.add(AwarenessFence.and(HeadphoneFence.during(HeadphoneState.UNPLUGGED), singleRules.get(6)));  //unplugged & on bicycle


        String[] singleFenceNames = context.getResources().getStringArray(R.array.single_fence_names);
        String[] compoundFenceNames = context.getResources().getStringArray(R.array.compound_fence_names);
        for(int i = 0; i < 10; i++)
        singleReminders.add(new Reminder.Builder()
                .setText(singleFenceNames[i])
                .setRule(getARule(i))
                .build());
        for(int i = 0; i < 10; i++)
        compoundReminders.add(new Reminder.Builder()
            .setText(compoundFenceNames[i])
            .setRule(getACompoundRule(i))
            .build());

    }

    private AwarenessFence getACompoundRule(int i) {

        return compoundRules.get(i % compoundRules.size());
    }

    AwarenessFence getARule(int index){

        return singleRules.get(index % singleRules.size());
    }
}
