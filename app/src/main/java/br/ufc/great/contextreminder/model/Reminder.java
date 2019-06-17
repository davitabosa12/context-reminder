package br.ufc.great.contextreminder.model;


import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.Serializable;
import java.util.UUID;

import smd.ufc.br.easycontext.fence.Fence;


public class Reminder implements Serializable {

    private String uid;
    private String text;
    private Fence fence;
    private boolean repeating;

    private Reminder() {
    }

    public Reminder(String uid, String text, Fence fence, boolean repeating) {
        this.uid = uid;
        this.text = text;
        this.fence = fence;
        this.repeating = repeating;
    }

    public static Reminder fromJson(String json) {
        JsonObject rem = new Gson().fromJson(json, JsonObject.class);
        String uid = rem.get("uid").getAsString();
        String text = rem.get("text").getAsString();
        Fence fence = Fence.fromJson(rem.get("fence").toString());
        boolean repeating = rem.get("repeating").getAsBoolean();
        return new Reminder(uid,text,fence,repeating);

    }

    public boolean isRepeating() {
        return repeating;
    }

    public Reminder setRepeating(boolean repeating) {
        this.repeating = repeating;
        return this;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Fence getFence() {
        return fence;
    }

    public void setFence(Fence fence) {
        this.fence = fence;
    }

    @Override
    public String toString() {
        JsonObject object = new JsonObject();
        object.addProperty("uid", uid);
        object.addProperty("text", text);
        object.add("fence", new Gson().fromJson(fence.toString(), JsonObject.class));
        object.addProperty("repeating", repeating);
        return object.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Reminder reminder = (Reminder) o;

        return uid != null ? uid.equals(reminder.uid) : reminder.uid == null;
    }

    @Override
    public int hashCode() {
        return uid != null ? uid.hashCode() : 0;
    }

    public static class Builder{
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
            reminder.setFence(rule);
            return this;
        }

        public Builder setRepeating(boolean repeating) {
            reminder.setRepeating(repeating);
            return this;
        }

        public Reminder build(){
            reminder.setUid(UUID.randomUUID().toString());
            return reminder;
        }


    }
}
