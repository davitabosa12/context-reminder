package smd.ufc.br.easycontext.fence;

import com.google.gson.Gson;

import java.io.Serializable;

public final class Fence implements Serializable, JsonSerializer {
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

    @Override
    public String serialize() {
        return new Gson().toJson(this);
    }
}
