package com.project.myapplicationsms;

import android.app.Activity;
import android.database.ContentObservable;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;

public  class SmsContent extends ContentObserver {
    private static final String TAG = SmsContent.class.getSimpleName();
    private static final String MARKER = "YOUR_KEYWORD";
    private Cursor cursor = null;
    private Activity mActivity;


    public SmsContent(Handler handler, Activity activity) {
        super(handler);
        this.mActivity = activity;
    }
    @Override
    public boolean deliverSelfNotifications() {
        return true;
    }

    @Override
    public void onChange(boolean selfChange) {
        super.onChange(selfChange);
        Log.d(TAG, "onChange(boolean selfChange). selfChange=" + selfChange);
        onChange(selfChange, null);
    }

    @Override
    public void onChange(boolean selfChange, Uri uri) {
        super.onChange(selfChange, uri);
        if (uri == null) {
            uri = Uri.parse("content://sms/inbox");
        }
        if (uri.toString().equals("content://sms/raw")) {
            return;
        }
        cursor = this.mActivity.getContentResolver().query(uri, null, null, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                int id = cursor.getInt(cursor.getColumnIndex("_id"));
                String body = cursor.getString(cursor.getColumnIndex("body"));
                Log.d(TAG, "sms id: " + id + "\nsms body: " + body);
                cursor.close();

            }
        }
    }
}
