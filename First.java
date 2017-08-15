package com.example.alarmnew1;

import java.util.ArrayList;

/*import com.example.alarmdemo.First;
import com.example.alarmdemo.MainActivity;
import com.example.alarmdemo.R;*/

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class First extends MainActivity {
	Button add,about;
	String str;
	public static SQLiteDatabase db,db1;
	public ArrayList<String> al= new ArrayList<String>();
	ArrayAdapter<String> ad;
	ListView l;
	final int EDIT=1;
	final int DELETE=2;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.first);
		add=(Button) findViewById(R.id.button1);
        about=(Button)findViewById(R.id.button2);
		l=(ListView)findViewById(R.id.list);
		
	    about.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent abt = new Intent(First.this,About.class);
                startActivity(abt);
            }
        });
	
		try
		{
			
			First.db=openOrCreateDatabase("AlarmDB",Context.MODE_PRIVATE,null);
		    First.db.execSQL("CREATE TABLE IF NOT EXISTS alarm"+"(id VARCHAR,name VARCHAR,time VARCHAR,mainid VARCHAR,rep VARCHAR,vib VARCHAR,musicc VARCHAR);");
			
		    First.db1=openOrCreateDatabase("DeletedAlarmDB",Context.MODE_PRIVATE,null);
		    First.db1.execSQL("CREATE TABLE IF NOT EXISTS del (delid VARCHAR);");
		    Cursor c=db.rawQuery("SELECT * FROM alarm", null);
			if(c.moveToFirst())
			{
				do
				{
					String id=c.getString(1)+" "+c.getString(2)+"   "+c.getString(4);
					al.add(id);
				}while(c.moveToNext());
			}
			ad=new ArrayAdapter<String>(getApplicationContext(), R.layout.dat,R.id.textView1,al);
			
			l.setAdapter(ad);
			registerForContextMenu(l);
			
		}
		
		catch(NullPointerException ex)
		{
			Toast.makeText(getApplicationContext(), "Null pointer"+str,Toast.LENGTH_LONG).show();
		}
		add.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
			Intent i=new Intent(First.this,MainActivity.class);
			startActivity(i);
			
			}
		});

l.setOnItemClickListener(new AdapterView.OnItemClickListener() {
	@Override
	public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
		Intent f22e=new Intent(First.this,Edit.class);
		String p=String.valueOf(i);
		f22e.putExtra("loh",p);
		startActivity(f22e);
	}
});
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		// TODO Auto-generated method stub
		if(v.getId()==R.id.list){
			AdapterView.AdapterContextMenuInfo info=(AdapterContextMenuInfo) menuInfo;
			menu.setHeaderTitle("Alarm Options");
			menu.add(Menu.NONE,EDIT,Menu.NONE,"Edit");
			menu.add(Menu.NONE,DELETE,Menu.NONE,"Delete");
			
		}
		
		
	
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		// TODO Auto-generated method stub

		AdapterView.AdapterContextMenuInfo info=(AdapterContextMenuInfo) item.getMenuInfo();
		
		switch(item.getItemId()){
		case EDIT:Intent jay=new Intent(First.this,Edit.class);
					jay.putExtra("loh",String.valueOf(info.position));
					startActivity(jay);
				   break;
				   
		case DELETE:
			
					Cursor cs=db.rawQuery("SELECT * FROM alarm", null);
					String deleted=l.getItemAtPosition(info.position).toString();
					int mmm=0;
					if(cs.moveToFirst()){
						do{
							String str=cs.getString(1)+" "+cs.getString(2)+"   "+cs.getString(4);
							String ttr=cs.getString(3);
							String check=cs.getString(2);
							String idd=cs.getString(3);
							if(str.equals(deleted)&&mmm==info.position){
								
								delete(info.position);
								db.execSQL("DELETE FROM alarm WHERE time='"+check+"' AND mainid='"+ttr+"'");
								db1.execSQL("INSERT INTO del VALUES('"+idd+"')");
								Cursor y=db1.rawQuery("SELECT * FROM del WHERE delid='"+idd+"'", null);
								
								String sreu="xyz";

								//Toast.makeText(getApplicationContext(), "Alarm scheduled at "+check+" is deleted.", Toast.LENGTH_LONG).show();
								break;
							}mmm++;
						}while(cs.moveToNext());
					}
					
					
		   		   break;
		}
		return super.onContextItemSelected(item);
	}

	private void delete(int pos) {
		// TODO Auto-generated method stub
		al.remove(pos);

		l.invalidate();
		l.setAdapter(ad);
		
		
	}
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		final AlertDialog dialog = new AlertDialog.Builder(First.this).create();
		dialog.setTitle("Thank You for using Wake Me Up!");
		dialog.setMessage("Would you like to exit now?");
		dialog.setButton("YES", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialogInterface, int i) {
				Intent homeIntent = new Intent(Intent.ACTION_MAIN);
				homeIntent.addCategory( Intent.CATEGORY_HOME );
				homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(homeIntent);
				dialog.dismiss();
			}
		});
		dialog.show();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		db.close();
		super.onDestroy();
	}
	
}
