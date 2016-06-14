package com.videri.helloworldselfie;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Ayalus on 6/7/16.
 */
public class StaticBroadcastReceiver extends BroadcastReceiver {
    static final String vCameraView = "vCameraView";
    @Override
    public void onReceive(Context context, Intent intent) {
        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
            Intent startOnBootIntent = new Intent(context, MainActivity.class);
            if (startOnBootIntent != null) {
            }
            else {
                startOnBootIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(startOnBootIntent);
            }
        }
    }
}


