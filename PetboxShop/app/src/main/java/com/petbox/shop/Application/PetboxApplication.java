package com.petbox.shop.Application;

import android.app.Application;

import com.flurry.android.FlurryAgent;
import com.petbox.shop.DB.Constants;

/**
 * Created by petbox on 2015-10-05.
 */
public class PetboxApplication extends Application {

    @Override
    public void onCreate(){
        super.onCreate();

        //FlurryAgent.setLogEnabled(false);
        FlurryAgent.init(this, Constants.FLURRY_APIKEY);

    }


}
