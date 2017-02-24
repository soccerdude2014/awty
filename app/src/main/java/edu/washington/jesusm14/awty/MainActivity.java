package edu.washington.jesusm14.awty;

import android.Manifest;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {
    private EditText messageEdit;
    private EditText phoneNumberEdit;
    private EditText intervalEdit;
    private Button button;
    private AlarmManager alarmManager;
    private Intent intent;
    private PendingIntent pIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.SEND_SMS},1);

        messageEdit = (EditText) findViewById(R.id.message_id);
        phoneNumberEdit = (EditText) findViewById(R.id.phone_id);
        intervalEdit = (EditText) findViewById(R.id.number_id);
        button = (Button) findViewById(R.id.start_button);



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(messageEdit.getText().toString())) {
                    Toast.makeText(MainActivity.this, "Application can't start: Missing message text ", Toast.LENGTH_SHORT).show();
                    return;
                } else if(TextUtils.isEmpty(phoneNumberEdit.getText().toString()) || phoneNumberEdit.getText().toString().length() > 11 ||
                        phoneNumberEdit.getText().toString().length() < 10) {
                    Toast.makeText(MainActivity.this, "Application can't start: Missing/Incorrect Phone Number ", Toast.LENGTH_SHORT).show();
                    return;
                } else if(TextUtils.isEmpty(intervalEdit.getText().toString()))  {
                    Toast.makeText(MainActivity.this, "Application can't start: Missing interval ", Toast.LENGTH_SHORT).show();
                    return;
                }  else if (Integer.parseInt(intervalEdit.getText().toString()) < 1) {
                    Toast.makeText(MainActivity.this, "Application can't start: Number must be greater than 0 ", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    if (button.getText().toString().equals("Start")) {
                        Context context = MainActivity.this;
                        alarmManager =(AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
                        intent = new Intent(context, AlarmReceiver.class);
                        intent.putExtra("message", messageEdit.getText().toString());
                        intent.putExtra("phone_number", phoneNumberEdit.getText().toString());
                        pIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
                        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,System.currentTimeMillis(),Integer.parseInt(intervalEdit.getText().toString()) * 60000,
                                pIntent);
                        button.setText("Stop");
                    } else {
                        if(alarmManager != null) {
                         alarmManager.cancel(pIntent);
                        }
                        button.setText("Start");
                    }
                }
            }
        });
    }
}
