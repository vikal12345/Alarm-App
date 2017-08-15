package com.example.alarmnew1;


import java.util.ArrayList;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;
import android.os.PowerManager;
import android.os.Vibrator;
import android.util.Log;
import android.widget.Toast;

public class RingToneService extends Service {
	
	int start_id;
	boolean isRunning;
	ArrayList<String> l=new ArrayList();
	ArrayList<String> m=new ArrayList();
	ArrayList<String> u=new ArrayList();
	Vibrator v;
	Ringtone rr;
	Cursor cr;
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onDestroy() {
	
		
		super.onDestroy();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		String mus,ic;
		Uri song;
		Log.i("Local service","Received start id"+startId+":"+intent);
		String state=intent.getExtras().getString("Extra");
		String vibb=intent.getExtras().getString("vib");
		int music=Integer.parseInt(intent.getExtras().getString("play"));
		Log.e("Ringtone state:Extra is",state);
		Log.e("gggg",vibb);
		MediaPlayer mp=new MediaPlayer();
		AudioManager am=(AudioManager)getSystemService(Context.AUDIO_SERVICE);
		assert state!=null;
		if(state.equals("alarm on"))
			start_id=1;
		else if(state.equals("alarm off"))
			start_id=0;
		
		else
			start_id=0;
		if(!this.isRunning && start_id==1)
		{
			if(vibb.equals("yes")){
				 v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
				long[] pattern = {0, 100, 1000};
				v.vibrate(pattern, 0);
			}
			Log.e("There is no music ","and you want start");
			RingtoneManager manager = new RingtoneManager(this);
	        manager.setType(RingtoneManager.TYPE_RINGTONE);
	         cr=manager.getCursor();
	         Log.e("sssss ","xcv");
	         while(cr.moveToNext()){
	        	 String notificationTitle = cr.getString(RingtoneManager.TITLE_COLUMN_INDEX);
	             String notificationUri = cr.getString(RingtoneManager.URI_COLUMN_INDEX);
	             String notId=cr.getString(RingtoneManager.ID_COLUMN_INDEX);
	             //Uri uri=RingtoneManager.getDefaultUri(c.getPosition());
	             m.add(notificationUri);
	             u.add(notId);
	             l.add(notificationTitle);
	             
	             //u.add(uri);
	 		}
//	         m.add(0, "ha");
//	  	   u.add(0, "haa");
//	  	   l.add(0, "Select Alarm Ringtone");
	         Log.e("AAya AAya ","challoh");
	          mus=m.get(music);
	          Log.e("AAya hh ","challoh23");
 		      ic=u.get(music);
 		     Log.e("AAya gg ","challoh7676");
 		      song=Uri.parse(mus+"/"+ic);
 		     Log.e("AAya vvvv ","challoh77");
 		    Intent iii=new Intent(RingToneService.this,Gaana.class);
		    iii.putExtra("ringtone-uri", String.valueOf(song));
		    startService(iii);
// 		     try{
// 		    	mp.setDataSource(getApplicationContext(), song);
// 		    	if(am.getStreamVolume(AudioManager.STREAM_ALARM)!=0){
//					mp.setAudioStreamType(AudioManager.STREAM_ALARM);
//				 mp.setLooping(true);
//				 mp.prepare();
//			     mp.start();
// 		     }}
// 		     catch(Exception e){
// 		    	 
// 		     }
//			if(music.equalsIgnoreCase("My Life Be Like"))
//				mp=MediaPlayer.create(this,R.raw.gg);
//			else if(music.equalsIgnoreCase("Wake Up"))
//				mp=MediaPlayer.create(this,R.raw.wake);
//			else if(music.equalsIgnoreCase("John Cena"))
//				mp=MediaPlayer.create(this,R.raw.johncena);
//			else
//				mp=MediaPlayer.create(this,R.raw.mario);
//			mp.setLooping(true);
			
//			mp.start();
			this.isRunning=true;
			this.start_id=0;
			
			Intent dialogIntent = new Intent(this,Startup.class);
			 dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			 this.startActivity(dialogIntent);
			
		}
		
		else if(this.isRunning && start_id==0)
		{
			Log.e("yahi", "sahi");
			try{
			v.cancel();
			}
			catch(Exception ex){
				
			}
			Log.e("There is music ","and you want no start");
			
		 //   Uri songg=Uri.parse(mus+"/"+ic);
		    
		    Intent iii=new Intent(this,Gaana.class);
		    iii.putExtra("ringtone-uri", "av");
		    stopService(iii);
			this.isRunning=false;
			start_id=0;
			

		}
		else if(!this.isRunning && start_id==0)
		{
			
			Log.e("There is no music ","and you want no start");
		}
		else if(this.isRunning && start_id==1)
		{
			Log.e("There is music ","and you want start");
			
		}
		else
		{
			Log.e("Somehow youreached here","here");
		}
		return super.onStartCommand(intent, flags, startId);
	}

}
