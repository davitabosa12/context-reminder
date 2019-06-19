package br.ufc.great.contextreminder;

import android.content.Context;

import com.google.android.gms.awareness.fence.HeadphoneFence;
import com.google.android.gms.awareness.state.HeadphoneState;

import java.util.ArrayList;
import java.util.List;

import br.ufc.great.contextreminder.model.Reminder;
import smd.ufc.br.easycontext.fence.AggregateRule;
import smd.ufc.br.easycontext.fence.DetectedActivityRule;
import smd.ufc.br.easycontext.fence.Fence;
import smd.ufc.br.easycontext.fence.HeadphoneRule;
import smd.ufc.br.easycontext.fence.Rule;

public class RemindersCollection {
    List<Reminder> singleReminders;
    List<Reminder> compoundReminders;
    private List<Rule> singleRules, compoundRules;

    public RemindersCollection(Context context) {
        singleReminders = new ArrayList<>();
        compoundReminders = new ArrayList<>();

        singleRules = new ArrayList<>();
        compoundRules = new ArrayList<>();

        singleRules.add(HeadphoneRule.pluggingIn()); //Headphone plugging
        singleRules.add(HeadphoneRule.unplugging()); //Headphone unplugging
        singleRules.add(DetectedActivityRule.starting(DetectedActivityRule.STILL)); //Activity Starting Still
        singleRules.add(DetectedActivityRule.starting(DetectedActivityRule.ON_FOOT, DetectedActivityRule.WALKING)); //Activity started walking
        singleRules.add(DetectedActivityRule.starting(DetectedActivityRule.RUNNING)); //activity started running
        singleRules.add(DetectedActivityRule.starting(DetectedActivityRule.IN_VEHICLE)); //activity in vehicle
        singleRules.add(DetectedActivityRule.starting(DetectedActivityRule.ON_BICYCLE)); //activity on bicycle
        singleRules.add(DetectedActivityRule.stopping(DetectedActivityRule.STILL)); //activity stopping still
        singleRules.add(DetectedActivityRule.stopping(DetectedActivityRule.ON_FOOT, DetectedActivityRule.WALKING)); //activity stopping waking
        singleRules.add(DetectedActivityRule.stopping(DetectedActivityRule.IN_VEHICLE)); //activity stopping in vehicle



        compoundRules.add(AggregateRule.and(HeadphoneRule.during(HeadphoneState.PLUGGED_IN), singleRules.get(2))); //plugged & still
        compoundRules.add(AggregateRule.and(HeadphoneRule.during(HeadphoneState.PLUGGED_IN), singleRules.get(3))); //plugged & walking
        compoundRules.add(AggregateRule.and(HeadphoneRule.during(HeadphoneState.PLUGGED_IN), singleRules.get(4))); //plugged & running
        compoundRules.add(AggregateRule.and(HeadphoneRule.during(HeadphoneState.PLUGGED_IN), singleRules.get(5))); //plugged & in vehicle
        compoundRules.add(AggregateRule.and(HeadphoneRule.during(HeadphoneState.PLUGGED_IN), singleRules.get(6))); //plugged & on bicycle
        compoundRules.add(AggregateRule.and(HeadphoneRule.during(HeadphoneState.UNPLUGGED), singleRules.get(2)));  //unplugged & still
        compoundRules.add(AggregateRule.and(HeadphoneRule.during(HeadphoneState.UNPLUGGED), singleRules.get(3)));  //unplugged & walking
        compoundRules.add(AggregateRule.and(HeadphoneRule.during(HeadphoneState.UNPLUGGED), singleRules.get(4)));  //unplugged & running
        compoundRules.add(AggregateRule.and(HeadphoneRule.during(HeadphoneState.UNPLUGGED), singleRules.get(5)));  //unplugged & in vehicle
        compoundRules.add(AggregateRule.and(HeadphoneRule.during(HeadphoneState.UNPLUGGED), singleRules.get(6)));  //unplugged & on bicycle


        String[] singleFenceNames = context.getResources().getStringArray(R.array.single_fence_names);
        String[] compoundFenceNames = context.getResources().getStringArray(R.array.compound_fence_names);
        for(int i = 0; i < 10; i++)
        singleReminders.add(new Reminder.Builder()
                .setText(singleFenceNames[i])
                .setRule(new Fence("Single " + i, getARule(i) , new NotificationAction()))
                .build());
        for(int i = 0; i < 10; i++)
        compoundReminders.add(new Reminder.Builder()
            .setText(compoundFenceNames[i])
            .setRule(new Fence("Compound " + i, getACompoundRule(i), new NotificationAction()))
            .build());

    }

    private Rule getACompoundRule(int i) {

        return compoundRules.get(i % compoundRules.size());
    }

    Rule getARule(int index){

        return singleRules.get(index % singleRules.size());
    }
}
