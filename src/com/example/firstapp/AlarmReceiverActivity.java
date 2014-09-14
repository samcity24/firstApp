package com.example.firstapp;




import java.io.IOException;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.PowerManager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;

public class AlarmReceiverActivity extends Activity{
	private MediaPlayer mMediaPlayer; // to play audio
	private PowerManager.WakeLock mWakeLock; // to wake up sleeping device
	
	private long time; 


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        time = System.currentTimeMillis();
        
        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        mWakeLock = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK, "My Wake Log");
        mWakeLock.acquire(); // to acquire Wake Lock
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN | 
        		WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED | 
        		WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON, 
        		WindowManager.LayoutParams.FLAG_FULLSCREEN |
        		WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED | 
        		WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
        
        
        //show the alarm screen 
        setContentView(R.layout.alarm_page);
        
        
        
        
        Button stopAlarm = (Button) findViewById(R.id.alarmStop);
        stopAlarm.setOnClickListener(
        		new OnClickListener() {

					public void onClick(View v) {
						mMediaPlayer.stop();
						finish(); 
					} 
    
        });
        playSound(this,getAlarmUri());
        
        
    }
    
    

    private void playSound(Context context, Uri alert)
    {
    	mMediaPlayer = new MediaPlayer();
    	try
    	{
    		mMediaPlayer.setDataSource(context, alert); // get data to play sound throws exception
    		final AudioManager  audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
    		if (audioManager.getStreamVolume(AudioManager.STREAM_ALARM) != 0) // if volume not mute.
    		{
    			mMediaPlayer.setAudioStreamType(AudioManager.STREAM_ALARM);
    			mMediaPlayer.prepare();
    			mMediaPlayer.start();
    			
    		}
    		
    	}catch (IOException e)
    	{
    		Log.i("Alarm Reciever", "No audio File Are found DAWG!");
    	}
    }
    
    private Uri getAlarmUri() // try to find any kind of sound for our alarm
    {
    	Uri alert = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM); // look for alarms if none jump to TYPE_NOTIFICATION if not jump to TYPE_RINGTONE
    	if (alert == null)
    	{
    		alert = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
    		if (alert == null)
    		{
    			alert = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
    		}
    	}return alert; // if nothing found return nothing
    }
    
    protected void onStop() // before activity is destroyed to release the Lock
    {
    	super.onStop();
    	mWakeLock.release();
    }
}
