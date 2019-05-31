package smd.ufc.br.easycontext.fence;

import com.google.android.gms.awareness.fence.AwarenessFence;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import smd.ufc.br.easycontext.fence.method.AggregateMethod;

public class AggregateRule implements Rule {

    private List<Rule> rules;
    private AggregateMethod method;

    public AggregateRule() {
    }

    public static AggregateRule and(Rule ...rules){
        AggregateRule aggregateFence = new AggregateRule();
        aggregateFence.method = AggregateMethod.AGGREGATE_AND;
        aggregateFence.rules = Arrays.asList(rules);
        return aggregateFence;
    }
    public static AggregateRule and(List<Rule> rules){
        AggregateRule aggregateFence = new AggregateRule();
        aggregateFence.method = AggregateMethod.AGGREGATE_AND;
        aggregateFence.rules = rules;
        return aggregateFence;
    }
    public static AggregateRule or(Rule ...rules){
        AggregateRule aggregateFence = new AggregateRule();
        aggregateFence.method = AggregateMethod.AGGREGATE_OR;
        aggregateFence.rules = Arrays.asList(rules);
        return aggregateFence;
    }
    public static AggregateRule not(Rule rule){
        AggregateRule aggregateFence = new AggregateRule();
        aggregateFence.method = AggregateMethod.AGGREGATE_NOT;
        aggregateFence.rules = Arrays.asList(rule);
        return aggregateFence;
    }
    @Override
    public AwarenessFence getAwarenessFence() {
        List<AwarenessFence> realFences = new ArrayList<>();
        for(Rule r : rules){
            realFences.add(r.getAwarenessFence());
        }
        switch (this.method){
            case AGGREGATE_OR:
                return AwarenessFence.or(realFences);

            case AGGREGATE_AND:
                return AwarenessFence.and(realFences);

            case AGGREGATE_NOT:
                return AwarenessFence.not(realFences.get(0));

            default:
                return null;
        }
    }
}
