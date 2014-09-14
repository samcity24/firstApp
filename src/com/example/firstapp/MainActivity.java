package com.example.firstapp;


import android.os.Bundle;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    
    public void doSomething(View view){
    	//Toast.makeText(this, "pressed" , Toast.LENGTH_SHORT).show();
    	
    	String time1 = String.valueOf(System.currentTimeMillis()); 
    	
    	Toast.makeText(this.getApplicationContext(), time1 , Toast.LENGTH_SHORT).show(); 
    	
    	setAlarm((System.currentTimeMillis() + 5000), view, new Intent(MainActivity.this, AlarmReceiverActivity.class)); 
    	
    	
    }
    
    //sets alarm 
    public void setAlarm(long time, View v, Intent newIntent){
    	try{
    		
    		Intent intent = newIntent;
			PendingIntent  pendingIntent =  PendingIntent.getActivity(MainActivity.this, 0, intent, 0);
			AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
			am.set(AlarmManager.RTC_WAKEUP, time , pendingIntent);
    		
    	}catch (NumberFormatException e){
    		Toast.makeText(this.getApplicationContext(), "something went wrong" , Toast.LENGTH_SHORT).show(); 
    	}
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
