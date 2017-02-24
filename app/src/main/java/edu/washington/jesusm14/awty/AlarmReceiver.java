package edu.washington.jesusm14.awty;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.telephony.SmsManager;
import android.widget.Toast;

import static android.R.id.message;

/**
 * Created by Jesus Moreno on 2/20/2017.
 */

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        String number = intent.getStringExtra("phone_number");

        String message = intent.getStringExtra("message");
        String formattedNumber = "(" + number.substring(0, 3) + ") " + number.substring(3, 6) + "-" + number.substring(6);
        Toast.makeText(context, formattedNumber + ": " + message, Toast.LENGTH_SHORT).show();
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(number, null, message, null, null);
    }
}
