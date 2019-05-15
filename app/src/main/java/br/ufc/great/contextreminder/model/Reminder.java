package br.ufc.great.contextreminder.model;

import smd.ufc.br.easycontext.ContextDefinition;
import smd.ufc.br.easycontext.Fence;

public class Reminder {

    private int uid;
    private String text;
    private Fence rule; //Todo: change ruleset
    private boolean repeating;

    private Reminder() {
    }

    public Reminder(int uid, String text, Fence rule) {
        this.uid = uid;
        this.text = text;
        this.rule = rule;
    }

    public boolean isRepeating() {
        return repeating;
    }

    public Reminder setRepeating(boolean repeating) {
        this.repeating = repeating;
        return this;
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

    class Builder{
        Reminder reminder;
        private String text;
        private Fence rule; //Todo: change ruleset
        private boolean repeating;

        public Builder(){
            reminder = new Reminder();
        }

        public Builder setText(String text) {
            reminder.setText(text);
            return this;
        }

        public Builder setRule(Fence rule) {
            reminder.setRule(rule);
            return this;
        }

        public Builder setRepeating(boolean repeating) {
            reminder.setRepeating(repeating);
            return this;
        }

        public Reminder build(){
            return reminder;
        }


    }
}
