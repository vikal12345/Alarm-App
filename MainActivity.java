package com.example.alarmnew1;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

//import com.example.guessnumber.R;

/*import com.example.alarmdemo.AlarmReceiver;
import com.example.alarmdemo.First;
import com.example.alarmdemo.MainActivity;
import com.example.alarmdemo.R;*/

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.support.v4.app.NavUtils;

public class MainActivity extends Activity {
	public AlarmManager alarmManager;
	RadioGroup rg;
	RadioButton rb;
	TimePicker alarmTimePicker;
	CheckBox c;
	Cursor cu;
	Spinner s;
	Ringtone e;
	
	MediaPlayer mp;
	RingtoneManager manager;
	public long tym;
	private int dd=0;
	ArrayList<String> l=new ArrayList();
	ArrayList<String> m=new ArrayList();
	ArrayList<String> u=new ArrayList();
	Button turnOn,can,show;
	public static int id=0;
	public PendingIntent pi;
	Context context;
	int song;
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.context=this;
        
        alarmManager=(AlarmManager)getSystemService(Context.ALARM_SERVICE);
        alarmTimePicker=(TimePicker)findViewById(R.id.timePicker1);
        rg=(RadioGroup)findViewById(R.id.radioGroup1);
        final Calendar calendar=Calendar.getInstance();
        final Intent i=new Intent(MainActivity.this,AlarmReceiver.class);
        turnOn=(Button)findViewById(R.id.button1 );
		can=(Button)findViewById(R.id.button2);
        c=(CheckBox) findViewById(R.id.checkBox1);
        s=(Spinner) findViewById(R.id.spinner1);
       // ArrayAdapter<CharSequence> add=ArrayAdapter.createFromResource(this,R.array.ringtones, android.R.layout.simple_spinner_item);
		//add.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		//s.setAdapter(add);
		 manager = new RingtoneManager(this);
	     manager.setType(RingtoneManager.TYPE_RINGTONE);
	     cu=manager.getCursor();
	     while(cu.moveToNext()){
        	 String notificationTitle = cu.getString(RingtoneManager.TITLE_COLUMN_INDEX);
             String notificationUri = cu.getString(RingtoneManager.URI_COLUMN_INDEX);
             String notId=cu.getString(RingtoneManager.ID_COLUMN_INDEX);
             //Uri uri=RingtoneManager.getDefaultUri(c.getPosition());
             m.add(notificationUri);
             u.add(notId);
             l.add(notificationTitle);
             //u.add(uri);
 		}
//	   m.add(0, "ha");
//	   u.add(0, "haa");
//	   l.add(0, "Select Alarm Ringtone");
       ArrayAdapter<String> ad =new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_spinner_item,l);
       ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
       s.setAdapter(ad);
       s.setOnItemSelectedListener(new OnItemSelectedListener() {

		@Override
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
			int ind=s.getSelectedItemPosition();
			String mus=m.get(ind);
		    String ic=u.get(ind);
		    Uri songg=Uri.parse(mus+"/"+ic);
			try{
				Intent ii=new Intent(MainActivity.this,SerTest.class);
			    ii.putExtra("ringtone-uri", String.valueOf(songg));
			    stopService(ii);
			}
			catch(Exception exx){
				
			}
			if(dd!=0){
		    Intent ii=new Intent(MainActivity.this,SerTest.class);
		    ii.putExtra("ringtone-uri", String.valueOf(songg));
		    startService(ii);
		     
			}
			else
				dd++;
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub
			
		}
	});
        turnOn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				int ind=s.getSelectedItemPosition();
				String mus=m.get(ind);
			    String ic=u.get(ind);
			    Uri songg=Uri.parse(mus+"/"+ic);
			    
			     
			    Intent ii=new Intent(MainActivity.this,SerTest.class);
			    ii.putExtra("ringtone-uri", String.valueOf(songg));
			    stopService(ii);

			
				int sid=rg.getCheckedRadioButtonId();
				rb=(RadioButton)findViewById(sid);
				String r=rb.getText().toString();
				tym=System.currentTimeMillis();
				calendar.set(Calendar.HOUR_OF_DAY,alarmTimePicker.getCurrentHour());
				calendar.set(Calendar.MINUTE,alarmTimePicker.getCurrentMinute());
				calendar.set(Calendar.SECOND,0);
				long hl=alarmTimePicker.getCurrentHour();
				long ml=alarmTimePicker.getCurrentMinute();
				song=s.getSelectedItemPosition();
				String h=String.valueOf(hl);
				String m=String.valueOf(ml);
				if(h.length()==1)
					h="0"+h;
				if(m.length()==1)
					m="0"+m;

				i.putExtra("Extra","alarm on");
				i.putExtra("idd", String.valueOf(tym));
				i.putExtra("play", String.valueOf(song));
				String vibration="no";
				if(c.isChecked()){
					i.putExtra("vib","yes");
					vibration="yes";
				}
				else
					i.putExtra("vib","no");
				pi=PendingIntent.getBroadcast(MainActivity.this,(int) tym,i,PendingIntent.FLAG_UPDATE_CURRENT);
	

				long time=System.currentTimeMillis();
				long t=calendar.getTimeInMillis();
				if(t>time){
					if(r.equals("No Repeat"))
						alarmManager.set(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pi);
					else if(r.equals("Daily"))
						alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),60*1000*24*60,pi);
					else
						alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),60*1000*24*60*7,pi);
					Toast.makeText(getApplicationContext(), "Alarm set at "+h+":"+m+".", Toast.LENGTH_LONG).show();
				}
					else{
						if(r.equals("Once"))
							alarmManager.set(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis()+86400000,pi);
						else if(r.equals("Daily"))
							alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis()+86400000,60*1000*24*60,pi);
						else
							alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis()+86400000,60*1000*24*60*7,pi);
					Toast.makeText(getApplicationContext(), "Alarm set at "+h+":"+m+"  tomorrow.", Toast.LENGTH_LONG).show();
				}
	
				
	
				First.db.execSQL("INSERT INTO alarm VALUES"+"('"+id+"','alarm','"+h+":"+m+"','"+tym+"','"+r+"','"+vibration+"','"+song+"')");
				Intent back=new Intent(MainActivity.this,First.class);
				back.putExtra("S",h+":"+m);
				startActivity(back);
			}
		});

		can.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent kkkkk=new Intent(MainActivity.this,SerTest.class);
				kkkkk.putExtra("ringtone-uri", String.valueOf(2));
				stopService(kkkkk);
				Intent off = new Intent(MainActivity.this,First.class);
				startActivity(off);
			}
		});

    }

	@Override
	public void onBackPressed() {
		Intent kkkk=new Intent(MainActivity.this,SerTest.class);
		kkkk.putExtra("ringtone-uri", String.valueOf(2));
		stopService(kkkk);
		super.onBackPressed();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		dd=0;
		super.onDestroy();
	}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    
}
