package com.kidsafe.jbyrne.kidsafefusedv2;

import android.app.IntentService;
import android.content.Intent;

/**
 * Created by jbyrne on 20/03/2015.
 */
public class MyActivityStartingService extends IntentService {

    public MyActivityStartingService() {
        super("MyKidSafeService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Intent kidSafeIntent = new Intent(getBaseContext(), MyActivity.class);
        kidSafeIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(kidSafeIntent);

    }
}
