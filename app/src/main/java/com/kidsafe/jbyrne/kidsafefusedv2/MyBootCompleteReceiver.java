package com.kidsafe.jbyrne.kidsafefusedv2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by jbyrne on 20/03/2015.
 */
public class MyBootCompleteReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent MyActivityStartingService = new Intent(context, MyActivityStartingService.class);
        context.startService(MyActivityStartingService); //Should change this to a service
    }
}
