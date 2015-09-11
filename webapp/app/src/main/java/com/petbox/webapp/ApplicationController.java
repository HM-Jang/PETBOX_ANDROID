package com.petbox.webapp;

import android.app.Application;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;
import com.google.android.gms.analytics.Logger;


import java.util.HashMap;

/**
 * Created by petbox on 2015-09-11.
 */
public class ApplicationController extends Application {

    private static final String PROPERTY_ID = "UA-67477240-1";

    public enum TrackerName{
        APP_TRACKER,
        GLOBAL_TRACKER,
        ECOMMERCE_TRACKER,
    }

    HashMap<TrackerName, Tracker> mTrackers = new HashMap<TrackerName, Tracker>();

    public ApplicationController(){
        super();
    }

    synchronized Tracker getTracker(TrackerName trackerId){


        if(!mTrackers.containsKey(trackerId)){
            GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
            Tracker t = (trackerId == TrackerName.APP_TRACKER) ? analytics.newTracker(PROPERTY_ID)
                    : (trackerId == TrackerName.GLOBAL_TRACKER) ? analytics.newTracker(R.xml.global_tracker)
                    : analytics.newTracker(R.xml.ecommerce_tracker);
            mTrackers.put(trackerId, t);
        }
        return mTrackers.get(trackerId);
    }
}
