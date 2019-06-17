package br.ufc.great.contextreminder;

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

    public RemindersCollection() {
        singleReminders = new ArrayList<>();
        compoundReminders = new ArrayList<>();

        singleRules = new ArrayList<>();
        compoundRules = new ArrayList<>();

        singleRules.add(HeadphoneRule.pluggingIn());
        singleRules.add(HeadphoneRule.unplugging());
        singleRules.add(DetectedActivityRule.starting(DetectedActivityRule.STILL));
        singleRules.add(DetectedActivityRule.starting(DetectedActivityRule.ON_FOOT, DetectedActivityRule.WALKING));
        singleRules.add(DetectedActivityRule.starting(DetectedActivityRule.RUNNING));
        singleRules.add(DetectedActivityRule.starting(DetectedActivityRule.IN_VEHICLE));
        singleRules.add(DetectedActivityRule.starting(DetectedActivityRule.ON_BICYCLE));
        singleRules.add(DetectedActivityRule.stopping(DetectedActivityRule.STILL));
        singleRules.add(DetectedActivityRule.stopping(DetectedActivityRule.ON_FOOT, DetectedActivityRule.WALKING));
        singleRules.add(DetectedActivityRule.stopping(DetectedActivityRule.IN_VEHICLE));



        compoundRules.add(AggregateRule.and(HeadphoneRule.during(HeadphoneState.PLUGGED_IN), singleRules.get(2)));
        compoundRules.add(AggregateRule.and(HeadphoneRule.during(HeadphoneState.PLUGGED_IN), singleRules.get(3)));
        compoundRules.add(AggregateRule.and(HeadphoneRule.during(HeadphoneState.PLUGGED_IN), singleRules.get(4)));
        compoundRules.add(AggregateRule.and(HeadphoneRule.during(HeadphoneState.PLUGGED_IN), singleRules.get(5)));
        compoundRules.add(AggregateRule.and(HeadphoneRule.during(HeadphoneState.PLUGGED_IN), singleRules.get(6)));
        compoundRules.add(AggregateRule.and(HeadphoneRule.during(HeadphoneState.UNPLUGGED), singleRules.get(2)));
        compoundRules.add(AggregateRule.and(HeadphoneRule.during(HeadphoneState.UNPLUGGED), singleRules.get(3)));
        compoundRules.add(AggregateRule.and(HeadphoneRule.during(HeadphoneState.UNPLUGGED), singleRules.get(4)));
        compoundRules.add(AggregateRule.and(HeadphoneRule.during(HeadphoneState.UNPLUGGED), singleRules.get(5)));
        compoundRules.add(AggregateRule.and(HeadphoneRule.during(HeadphoneState.UNPLUGGED), singleRules.get(6)));


        for(int i = 0; i < 10; i++)
        singleReminders.add(new Reminder.Builder()
                .setText("Single " + i)
                .setRule(new Fence("Single " + i, getARule(i) , new NotificationAction()))
                .build());
        for(int i = 0; i < 10; i++)
        compoundReminders.add(new Reminder.Builder()
            .setText("Compound " + i)
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
