package com.lovely.conzumex.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ActivityListing {

    @SerializedName("activities")
    @Expose
    List<Activity> activity;

    public List<Activity> getActivity() {
        return activity;
    }

    public void setActivity(final List<Activity> activity) {
        this.activity = activity;
    }
}
