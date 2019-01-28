package com.lovely.conzumex.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

import com.lovely.conzumex.model.Activity;
import com.lovely.conzumex.model.ActivityListing;
import com.lovely.conzumex.repository.ActivityDummyRepository;
import com.lovely.conzumex.repository.ActivityRepository;

import java.util.ArrayList;

public class ActivityViewModel extends AndroidViewModel {

    // get repository instance
    private ActivityRepository activityRepository;

    // dummy repo service
    private ActivityDummyRepository activityDummyRepository;

    public ActivityViewModel(@NonNull Application application) {
        super(application);

        // create repository instance
        activityRepository = new ActivityRepository(application);

        // create dummy repository instance
        activityDummyRepository = new ActivityDummyRepository(application);
    }

    public ActivityListing getActivityList() {

        if (activityRepository != null) {

            return activityRepository.getActivityList();
        }

        return null;
    }

    public ArrayList<Activity> getActivityListDummy() {

        if (activityDummyRepository != null) {

            return activityDummyRepository.getActivityList();
        }

        return null;
    }
}
