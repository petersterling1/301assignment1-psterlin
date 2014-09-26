package com.psterlin.assignment1_psterlin;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class SummaryActivity extends Activity {

	//This activity shows a summary of existing TO DO items.
	
	public void DoneButton(View view)
	{
		//Done button has been clicked. End the activity.
		finish();
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		//Set contentview to xml file.
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_summary);
		
		//Grab two arrays of archived and unarchived items from todohandler.
		ArrayList<ToDoItem> archivedItems = ToDoHandler.GetArchivedItems();
		ArrayList<ToDoItem> unarchivedItems = ToDoHandler.GetNonarchivedItems();
		
		//Integers to hold the values.
		int todochecked = 0, todounchecked = 0, todoarchived = 0, todoarchivedchecked = 0, todoarchivedunchecked = 0;
		
		//Loop through archived items, incrementing the counters when necessary.
		for(int i=0; i<archivedItems.size(); i++)
		{
			todoarchived++;
			if(archivedItems.get(i).getChecked())
			{
				todoarchivedchecked++;
			}else{
				todoarchivedunchecked++;
			}
		}
		
		//Loop through unarchived items, incrementing the counters when necessary.
		for(int i=0; i<unarchivedItems.size(); i++)
		{
			if(unarchivedItems.get(i).getChecked())
			{
				todochecked++;
			}else{
				todounchecked++;
			}
		}
		
		TextView textv;
		
		//Grab all 5 textviews by ID and change their text to the new values.
		
		textv = (TextView) findViewById(R.id.totalchecked);
		textv.setText("Total TODOs checked: "+Integer.toString(todochecked));

		textv = (TextView) findViewById(R.id.totalunchecked);
		textv.setText("Total TODOs unchecked: "+Integer.toString(todounchecked));
		
		textv = (TextView) findViewById(R.id.totalarchived);
		textv.setText("Total TODOs archived: "+Integer.toString(todoarchived));
		
		textv = (TextView) findViewById(R.id.totalarchivedchecked);
		textv.setText("Total archives checked: "+Integer.toString(todoarchivedchecked));
		
		textv = (TextView) findViewById(R.id.totalarchivedunchecked);
		textv.setText("Total archives unchecked: "+Integer.toString(todoarchivedunchecked));
		
	}
}
