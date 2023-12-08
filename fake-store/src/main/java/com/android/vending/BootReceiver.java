package com.android.vending;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class BootReceiver extends BroadcastReceiver {
    private static final String TAG = BootReceiver.class.getSimpleName();
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("BootReceiver", "fake store onReceive: " + context.getPackageName());
        try {
            Intent signatureServiceIntent = new Intent();
            signatureServiceIntent.setAction("com.huawei.signature.diff");
            intent.setPackage(context.getPackageName());
            context.startService(intent);
        } catch (Exception e) {
            Log.d(TAG, "onReceive: ");
        }
    }
}