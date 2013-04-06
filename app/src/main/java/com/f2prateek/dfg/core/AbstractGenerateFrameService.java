/*
 * Copyright 2013 Prateek Srivastava (@f2prateek)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 	http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.f2prateek.dfg.core;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import com.f2prateek.dfg.AppConstants;
import com.f2prateek.dfg.R;
import com.f2prateek.dfg.model.Device;

public abstract class AbstractGenerateFrameService extends IntentService implements DeviceFrameGenerator.Callback {

    protected static final int DFG_NOTIFICATION_ID = 789;
    protected NotificationManager mNotificationManager;
    protected NotificationCompat.Builder mNotificationBuilder;
    protected Device mDevice;

    public AbstractGenerateFrameService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        mDevice = (Device) intent.getParcelableExtra(AppConstants.KEY_EXTRA_DEVICE);
    }

    /**
     * Notify the user of a error.
     *
     * @param failed_text  Text for notification.
     * @param failed_title Title for notification.
     */
    @Override
    public void failedImage(String failed_title, String failed_text) {
        Log.d("AbstractService", "failedImage");

        Notification notification = new NotificationCompat.Builder(this)
                .setTicker(failed_title)
                .setContentTitle(failed_title)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(failed_text).setBigContentTitle(failed_title).setSummaryText(mDevice.getName()))
                .setContentText(failed_text)
                .setSmallIcon(R.drawable.ic_action_error)
                .setWhen(System.currentTimeMillis())
                .setAutoCancel(true)
                .build();
        mNotificationManager.notify(DFG_NOTIFICATION_ID, notification);
    }
}