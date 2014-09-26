package com.psterlin.assignment1_psterlin;

import java.util.ArrayList;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class DeleteActivity extends Activity {

	//This activity handles deleting data.
	
	private ScrollView sView;
	private LinearLayout mainLL;
	ArrayList<ToDoItem> currentItems;
	boolean onArchived;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// This activity starts out with the XML UI found in activity_delete.xml.
		// Once one of the two buttons are pushed the UI is replaced.
		setContentView(R.layout.activity_delete);
	}
	
	public void DeleteArchived(View view)
	{
		//The button "delete archived" was clicked.
		
		//Archived was clicked. Set the UI to show archived items
		//Create a scrollview and linearlayout (comments found in refreshUI in mainActivity)
		sView = new ScrollView(this);
		onArchived = true;
		sView.setPadding(15, 15, 15, 15);
		mainLL = new LinearLayout(this);
		mainLL.setOrientation(LinearLayout.VERTICAL);
		sView.addView(mainLL);
		
		//Inform the user text.
        TextView lineBreak = new TextView(this);
        lineBreak.setText("Check archived items you want to delete. Click done when finished\n\nArchived items:");
        mainLL.addView(lineBreak);
        
        //Add a button.
        AddDoneButton();
        
        //Get Archived items from ToDohandler.
        currentItems = ToDoHandler.GetArchivedItems();
        
        //Add them all to the screen as checkboxes.
        ToDoItem currentItem;
        for(int i=0; i<currentItems.size(); i++)
        {
        	currentItem = currentItems.get(i);
        	AddCheckBox(currentItem.getText(), currentItem.getChecked(), currentItem.getID());
        }
        
        //Refresh the UI.
        setContentView(sView);
		
	}
	
	public void DeleteUnarchived(View view)
	{
		//The button "delete unarchived" was clicked.
		
		//Unarchived was clicked. Set the UI to show unarchived items
		//Create a scrollview and linearlayout (comments found in refreshUI in mainActivity)
		sView = new ScrollView(this);
		onArchived = false;
		sView.setPadding(15, 15, 15, 15);
		mainLL = new LinearLayout(this);
		mainLL.setOrientation(LinearLayout.VERTICAL);
		sView.addView(mainLL);
		
		//Text to inform the user.
        TextView lineBreak = new TextView(this);
        lineBreak.setText("Check unarchived items you want to delete. Click done when finished\n\nUnarchived items:");
        mainLL.addView(lineBreak);
        
        //Add a done button
        AddDoneButton();
        
        //Grab nonarchived items from todohandler.
        currentItems = ToDoHandler.GetNonarchivedItems();
		
        //Add them all to the screen.
        ToDoItem currentItem;
        for(int i=0; i<currentItems.size(); i++)
        {
        	currentItem = currentItems.get(i);
        	AddCheckBox(currentItem.getText(), currentItem.getChecked(), currentItem.getID());
        }
        
        //Refresh the UI.
        setContentView(sView);
        
	}
	
	public void AddDoneButton()
	{
		//Adds a button with a listener.
        Button okButton = new Button(this);
        okButton.setText("Done");
        // Add listener.
        okButton.setOnClickListener(new OnClickListener() {
        	
        	@Override
        	public void onClick(View view)
        	{
        		//Loop through all items (either archived/unarchived, depends which original button was clicked)
        		ToDoItem workingItem;
        		CheckBox checkItem;
        		for(int i=0; i<currentItems.size(); i++)
        		{
        			workingItem = currentItems.get(i);
        			// from http://stackoverflow.com/questions/4830864/android-find-item-by-id
        			checkItem = (CheckBox)findViewById(workingItem.getID());
        			
        			//See if the item is checked:
        			if(checkItem.isChecked()) {
        				//It is. Inform the ToDo handler to delete that item now.
        				ToDoHandler.deleteItem(workingItem.getID());
        			}
        		}
        		
        		//Done. End the activity. Inform mainActivity the operation was a success
        		Intent returnI = new Intent();
        		returnI.putExtra("archivesuccess", true);
        		setResult(RESULT_OK, returnI);
        		finish();
        	}
        	
        });
        
        //Add button to screen.
        mainLL.addView(okButton);
	}
	
    public void AddCheckBox(String itemString, boolean checked, int newId)
    {
    	//For the method in this class, inform the user whether the item has been checked or not
    	String checkText;
    	if(checked)
    	{
    		checkText = "[X] ";
    	}else{
    		checkText = "[  ] ";
    	}
    	
    	//Create new checkbox, add to screen.
    	CheckBox cb = new CheckBox(this);
    	cb.setText(checkText+itemString);

    	cb.setId(newId);
    	mainLL.addView(cb);
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.delete, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
