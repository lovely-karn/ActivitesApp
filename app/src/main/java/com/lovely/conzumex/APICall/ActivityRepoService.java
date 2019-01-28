package com.lovely.conzumex.APICall;

import com.lovely.conzumex.model.Activity;
import com.lovely.conzumex.model.ActivityListing;
import com.lovely.conzumex.model.CommonResponse;
import com.lovely.conzumex.util.CommonUtil;

import java.util.ArrayList;

public class ActivityRepoService {

    public ActivityListing getActivityList() {

        // ask n/w to get the value if it is not in the db
        return makeNetworkCall();
    }

    /**
     * make Network Call
     *
     * @return ActivityListing
     */
    public ActivityListing makeNetworkCall() {

        // by mocking - pass the response
        // get the list from mocking right now

        // gets a common response
        // then parse it to a pojo
        // then pass the list from it -> can save it to db and then pass it to the user
        CommonResponse commonResponse = (CommonUtil.getMockResponse("document"));

        ActivityListing activityListing;

        activityListing = commonResponse.toResponseModel(ActivityListing.class);

        return activityListing;
    }

    public ArrayList<Activity> dummyActivityListData() {

        ArrayList<Activity> activityArrayList = new ArrayList<>();

        Activity activityItems = new Activity();
        activityItems.setName("walking");
        // HAS TO DO :- since dummy leave for now

        activityArrayList.add(activityItems);

        Activity activityItems1 = new Activity();
        activityItems1.setName("surfing");
        // HAS TO DO :- since dummy leave for now

        activityArrayList.add(activityItems1);

        Activity activityItems2 = new Activity();
        activityItems2.setName("treadmill walking");
        // HAS TO DO :- since dummy leave for now

        activityArrayList.add(activityItems2);

        Activity activityItems11 = new Activity();
        activityItems11.setName("yoga");
        // HAS TO DO :- since dummy leave for now

        activityArrayList.add(activityItems11);

        Activity activityItems3 = new Activity();
        activityItems3.setName("skating");
        // HAS TO DO :- since dummy leave for now

        activityArrayList.add(activityItems3);

        Activity activityItems4 = new Activity();
        activityItems4.setName("biathlon");
        // HAS TO DO :- since dummy leave for now

        activityArrayList.add(activityItems4);

        Activity activityItems5 = new Activity();
        activityItems5.setName("squash");
        // HAS TO DO :- since dummy leave for now

        activityArrayList.add(activityItems5);

        Activity activityItems6 = new Activity();
        activityItems6.setName("treadmill running");
        // HAS TO DO :- since dummy leave for now

        activityArrayList.add(activityItems6);

        Activity activityItems7 = new Activity();
        activityItems7.setName("stationary biking");
        // HAS TO DO :- since dummy leave for now

        activityArrayList.add(activityItems7);

        Activity activityItems8 = new Activity();
        activityItems8.setName("weight training");
        // HAS TO DO :- since dummy leave for now

        activityArrayList.add(activityItems8);

        Activity activityItems9 = new Activity();
        activityItems9.setName("tennis");
        // HAS TO DO :- since dummy leave for now

        activityArrayList.add(activityItems9);

        Activity activityItems10 = new Activity();
        activityItems10.setName("zumba");
        // HAS TO DO :- since dummy leave for now

        activityArrayList.add(activityItems10);

        return activityArrayList;
    }
}
