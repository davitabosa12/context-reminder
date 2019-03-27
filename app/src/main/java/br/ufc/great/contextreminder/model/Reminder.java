package br.ufc.great.contextreminder.model;

import smd.ufc.br.easycontext.ContextDefinition;
import smd.ufc.br.easycontext.Fence;

public class Reminder {

    private int uid;
    private String text;
    private Fence rule; //Todo: change ruleset

    public Reminder() {
    }

    public Reminder(int uid, String text, Fence rule) {
        this.uid = uid;
        this.text = text;
        this.rule = rule;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Fence getRule() {
        return rule;
    }

    public void setRule(Fence rule) {
        this.rule = rule;
    }
}
