package br.ufc.great.contextreminder;

import com.google.android.gms.awareness.state.HeadphoneState;

import org.junit.Test;

import br.ufc.great.contextreminder.model.Reminder;
import smd.ufc.br.easycontext.fence.AggregateRule;
import smd.ufc.br.easycontext.fence.Fence;
import smd.ufc.br.easycontext.fence.HeadphoneRule;
import smd.ufc.br.easycontext.fence.LocationRule;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {

        assertEquals(4, 2 + 2);
    }

    @Test
    public void testJson(){
        LocationRule rule1 = LocationRule.entering(10,0,12);
        HeadphoneRule rule2 = HeadphoneRule.during(HeadphoneState.PLUGGED_IN);
        AggregateRule and = AggregateRule.and(rule1, rule2);
        Reminder r = new Reminder.Builder()
                .setRepeating(false)
                .setText("Teste de lembrete")
                .setRule(new Fence("whatever",and , new NotificationAction()))
                .build();
        System.out.println(r.toString());
        assert true;
    }
}