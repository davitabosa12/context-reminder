package smd.ufc.br.easycontext.fence;

import com.google.android.gms.awareness.fence.AwarenessFence;

import java.io.Serializable;

import smd.ufc.br.easycontext.fence.method.FenceMethod;
import smd.ufc.br.easycontext.fence.parameter.FenceParameter;
import smd.ufc.br.easycontext.fence.type.FenceType;

public final class Fence implements Serializable {
    private String name;
    private Rule rule;
    private FenceAction action;

    public Fence(String name, Rule rule, FenceAction action){
		this.name = name;
		this.rule = rule;
		this.action = action;
	}


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Rule getRule() {
        return rule;
    }

    public void setRule(Rule rule) {
        this.rule = rule;
    }

    public FenceAction getAction() {
        return action;
    }

    public void setAction(FenceAction action) {
        this.action = action;
    }
}
