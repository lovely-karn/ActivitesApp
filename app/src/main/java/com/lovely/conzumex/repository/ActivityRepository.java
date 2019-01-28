package com.lovely.conzumex.repository;

import android.app.Application;
import android.support.annotation.NonNull;

import com.lovely.conzumex.APICall.ActivityRepoService;
import com.lovely.conzumex.model.ActivityListing;

public class ActivityRepository {

    // get service instance
    private ActivityRepoService activityRepoService;

    public ActivityRepository(@NonNull Application application) {

        // later on application object will be used by room database
        activityRepoService = new ActivityRepoService();
    }

    public ActivityListing getActivityList() {

        // over here to db will be inquired , if not in db then from n/w call db
        // will be updated & since db gives livedata ... all the updation & syncing will be done
        // automatically as code has been written as wired

        ActivityListing activityListingResponse = activityRepoService.getActivityList();

        return activityListingResponse;
    }
}
