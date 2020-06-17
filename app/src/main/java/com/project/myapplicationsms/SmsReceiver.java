package com.project.myapplicationsms;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

public  class SmsReceiver extends BroadcastReceiver {
    public static final String SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";
    @Override
    public void onReceive(Context context, Intent intent) {
        // Get SMS map from Intent
        Bundle extras = intent.getExtras();
        if (extras != null) {
            // Get received SMS array
            Object[] smsExtra = (Object[]) extras.get("pdus");
            String address = "";
            String body = "";
            for (int i = 0; i < smsExtra.length; ++i) {
                SmsMessage sms = SmsMessage.createFromPdu((byte[]) smsExtra[i]);
                body += sms.getMessageBody().toString();
                address = sms.getOriginatingAddress();
            }
            Log.d("====", body);
            Toast.makeText(context, body, Toast.LENGTH_LONG)
                    .show();
        }
    }

    /*Cursor cursor = null;
        try {
        if (SMS_RECEIVED.equals(intent.getAction())) {
            Log.d("====", "XXXX");
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                Object[] pdus = (Object[]) bundle.get("pdus");
                final SmsMessage[] messages = new SmsMessage[pdus.length];
                for (int i = 0; i < pdus.length; i++) {
                    messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                }
                if (messages.length > 0) {
                    String content = messages[0].getMessageBody();
                    String sender = messages[0].getOriginatingAddress();
                    long msgDate = messages[0].getTimestampMillis();
                    String smsToast = "New SMS received from : "
                            + sender + "\n'"
                            + content + "'";
                    Toast.makeText(context, smsToast, Toast.LENGTH_LONG)
                            .show();
                    Log.d("====", "message from: " + sender + ", message body: " + content
                            + ", message date: " + msgDate);
                    //自己的逻辑
                }
            }
            cursor = context.getContentResolver().query(Uri.parse("content://sms"), new String[]{"_id", "address", "read", "body", "date"}, "read = ? ", new String[]{"0"}, "date desc");
            if (null == cursor) {
                return;
            }
            Log.i("====", "m cursor count is " + cursor.getCount());
            Log.i("====", "m first is " + cursor.moveToFirst());
        }
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        if (cursor != null) {
            cursor.close();
            cursor = null;
        }
    }*/
}
