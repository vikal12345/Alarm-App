package com.example.alarmnew1;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Random;
import java.util.Stack;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MathQuestion extends First {
EditText ed;
TextView tv;
long ans1;
Button b;
int ji=0;

ArrayList<String> q=new ArrayList<String>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
		
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_math_question);
        ed=(EditText) findViewById(R.id.editText1);
        tv=(TextView) findViewById(R.id.textView2);
        b=(Button) findViewById(R.id.button1);
        
        for(int i=1;i<=5;i++){

        	
        	Random r1=new Random();
        	int x=r1.nextInt(18)+2;

        	q.add(Integer.toString(x));

        	
        	if(i!=5){
        	Random r2=new Random();
        	int y=r2.nextInt(3);
        	switch(y){
        	case 0:
        			q.add("+");
        			break;
        	case 1:
        			q.add("-");
        			break;
        	case 2:
        			q.add("*");
        			break;
        	
        	}
        	
        }
       }
        String nques="";
        for(int i=0;i<q.size();i++)
        	nques+=q.get(i);
        tv.setText(nques);
        
        b.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				try{
				ans1=getAnswer();
				
				Intent xy=new Intent(MathQuestion.this,AlarmReceiver.class);
				
				String val=ed.getText().toString();
				if(Long.parseLong(val)==ans1){
			
					Toast.makeText(getApplicationContext(), "You got it right.",Toast.LENGTH_LONG).show();
					alarmManager.cancel(pi);
					xy.putExtra("Extra","alarm off");
					if(c.isChecked()){
						xy.putExtra("vib","yes");
						//Toast.makeText(getApplicationContext(), "checked hai", Toast.LENGTH_LONG).show();
					}
					else
						xy.putExtra("vib","no");
					xy.putExtra("play", "-1");
					sendBroadcast(xy);
					Startup.nm.cancel(Startup.not_id);
					Intent last=new Intent(MathQuestion.this,First.class);
					startActivity(last);
					Intent homeIntent = new Intent(Intent.ACTION_MAIN);
				    homeIntent.addCategory( Intent.CATEGORY_HOME );
				    homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);  
				    startActivity(homeIntent); 
				}
				else{
					Toast.makeText(getApplicationContext(), "Incorrect answer!!!!",Toast.LENGTH_LONG).show();
					ed.setText("");
				}
			}
			catch(Exception ex){
				Toast.makeText(getApplicationContext(), "Enter a valid number.", Toast.LENGTH_LONG).show();
				ed.setText("");
			}
		}});
    }

  

	protected long getAnswer() {
		// TODO Auto-generated method stub
			try{
	
			for(int i=0;i<q.size();i++){
				
				while((q.get(i+1)).equals("*")){
					q.set(i, String.valueOf(Integer.parseInt(q.get(i))*Integer.parseInt(q.get(i+2))));
					
					q.subList(i+1,i+3).clear();
			}
			}
			}
			catch(Exception ex){
				
			}
	String nques="";
        for(int i=0;i<q.size();i++)
        	nques+=q.get(i);
        Log.e("xyz",nques);
			long ans=0;
		Log.e("xy",String.valueOf(q.size()));
	  	ans=Long.parseLong(q.get(0));
			for(int i=1;i<q.size()-1;i+=2){
				if((q.get(i)).equals("+")){
					ans+=Long.parseLong(q.get(i+1));
					Log.e("My+",String.valueOf(ans));
					
				}
				else{
					ans-=Long.parseLong(q.get(i+1));
					Log.e("My-",String.valueOf(ans));
				}
			}
			
		return ans;
	}



	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		
		super.onStop();
	}



	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		Toast.makeText(getApplicationContext(), "Solve the problem to dismiss alarm", Toast.LENGTH_LONG).show();
	}



	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_math_question, menu);
        return true;
    }

    
}
