package br.ufc.great.contextreminder.model;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.awareness.fence.AwarenessFence;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.Serializable;
import java.util.UUID;

public class Reminder implements Serializable {

    private String uid;
    private String text;
    private AwarenessFence fence;
    private boolean repeating;

    private Reminder() {
    }

    public Reminder(String uid, String text, AwarenessFence fence, boolean repeating) {
        this.uid = uid;
        this.text = text;
        this.fence = fence;
        this.repeating = repeating;
    }

    public static Reminder fromJson(String json) {
        return new Gson().fromJson(json, Reminder.class);
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

    public AwarenessFence getFence() {
        return fence;
    }

    public void setFence(AwarenessFence fence) {
        this.fence = fence;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
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
        private AwarenessFence rule;
        private boolean repeating;

        public Builder(){
            reminder = new Reminder();
        }

        public Builder setText(String text) {
            reminder.setText(text);
            return this;
        }

        public Builder setRule(AwarenessFence rule) {
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
