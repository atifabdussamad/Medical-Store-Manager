package com.delaroystudios.studentmgt;

import java.security.PublicKey;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.delaroystudios.studentmgt.R;

public class StudentMainActivity extends Activity {
	EditText ename,eserial_no,equantity;
	Button add,view,viewall,Show1,delete,modify;
	SQLiteDatabase db;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_student_main);
		ename=(EditText)findViewById(R.id.name);
		eserial_no=(EditText)findViewById(R.id.serial_no);
		equantity=(EditText)findViewById(R.id.quantity);
		add=(Button)findViewById(R.id.addbtn);
		view=(Button)findViewById(R.id.viewbtn);
		viewall=(Button)findViewById(R.id.viewallbtn);
		delete=(Button)findViewById(R.id.deletebtn);
		Show1=(Button)findViewById(R.id.showbtn);
		modify=(Button)findViewById(R.id.modifybtn);
		
		db=openOrCreateDatabase("Medical_Shop_Manager", Context.MODE_PRIVATE, null);
		db.execSQL("CREATE TABLE IF NOT EXISTS student(serialno INTEGER,name VARCHAR,quantity INTEGER);");
	
		
		add.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(eserial_no.getText().toString().trim().length()==0||
			    		   ename.getText().toString().trim().length()==0||
			    		   equantity.getText().toString().trim().length()==0)
			    		{
			    			showMessage("Error", "Please enter all values");
			    			return;
			    		}
			    		db.execSQL("INSERT INTO student VALUES('"+eserial_no.getText()+"','"+ename.getText()+
			    				   "','"+equantity.getText()+"');");
			    		showMessage("Success", "Record added successfully");
			    		clearText();
			}
		});
		delete.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(ename.getText().toString().trim().length()==0)
	    		{
	    			showMessage("Error", "Please enter Medicine Name");
	    			return;
	    		}
	    		Cursor c=db.rawQuery("SELECT * FROM student WHERE name='"+ename.getText()+"'", null);
	    		if(c.moveToFirst())
	    		{
	    			db.execSQL("DELETE FROM student WHERE name='"+ename.getText()+"'");
	    			showMessage("Success", "Record Deleted");
	    		}
	    		else
	    		{
	    			showMessage("Error", "Wrong Name");
	    		}
	    		clearText();
			}
		});
		modify.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(ename.getText().toString().trim().length()==0)
	    		{
	    			showMessage("Error", "Please Medicine Name");
	    			return;
	    		}
	    		Cursor c=db.rawQuery("SELECT * FROM student WHERE name='"+ename.getText()+"'", null);
	    		if(c.moveToFirst())
	    		{
	    			db.execSQL("UPDATE student SET name='"+ename.getText()+"',quantity='"+equantity.getText()+
	    					"' WHERE serialno='"+eserial_no.getText()+"'");
	    			showMessage("Success", "Record Modified");
	    		}
	    		else
	    		{
	    			showMessage("Error", "Wrong Name");
	    		}
	    		clearText();
			}
		});
		view.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(ename.getText().toString().trim().length()==0)
	    		{
	    			showMessage("Error", "Please enter Medicine Name");
	    			return;
	    		}
	    		Cursor c=db.rawQuery("SELECT * FROM student WHERE name='"+ename.getText()+"'", null);
	    		if(c.moveToFirst())
	    		{
	    			ename.setText(c.getString(1));
	    			equantity.setText(c.getString(2));
	    		}
	    		else
	    		{
	    			showMessage("Error", "Medicine Not Present");
	    			clearText();
	    		}
			}
		});
		viewall.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Cursor c=db.rawQuery("SELECT * FROM student", null);
	    		if(c.getCount()==0)
	    		{
	    			showMessage("Error", "No records found");
	    			return;
	    		}
	    		StringBuffer buffer=new StringBuffer();
	    		while(c.moveToNext())
	    		{
	    			buffer.append("Serial No: "+c.getString(0)+"\n");
	    			buffer.append("Name: "+c.getString(1)+"\n");
	    			buffer.append("Quantity: "+c.getString(2)+"\n\n");
	    		}
	    		showMessage("Medicine Details", buffer.toString());
			}
		});
		Show1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showMessage("Medical Shop Manager Application", "Developed By Rahul Prasad");


			}
		});
		
	}
	public void showMessage(String title,String message)
    {
    	Builder builder=new Builder(this);
    	builder.setCancelable(true);
    	builder.setTitle(title);
    	builder.setMessage(message);
    	builder.show();
	}
    public void clearText()
    {
    	eserial_no.setText("");
    	ename.setText("");
    	equantity.setText("");
    	ename.requestFocus();
    }
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.student_main, menu);
		return true;
	}

}
