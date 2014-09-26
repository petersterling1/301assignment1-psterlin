package com.psterlin.assignment1_psterlin;

import java.util.ArrayList;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class ArchiveActivity extends Activity {

	//This activity handles archiving data.
	
	private ScrollView sView;
	private LinearLayout mainLL;
	ArrayList<ToDoItem> nonArchivedItems;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
		//Create a dynamic UI (see mainActivity's refreshUI function to see better comments, it's like a direct copy
		
        sView = new ScrollView(this);
        sView.setPadding(15, 15, 15, 15);
        mainLL = new LinearLayout(this);
        mainLL.setOrientation(LinearLayout.VERTICAL);
        sView.addView(mainLL);
        
        TextView lineBreak = new TextView(this);
        lineBreak.setText("Check items you want to archive, click done when complete");
        mainLL.addView(lineBreak);
        
        //Create a new button
        Button okButton = new Button(this);
        okButton.setText("Done");
        // Add an onclick event.
        okButton.setOnClickListener(new OnClickListener() {
        	
        	@Override
        	public void onClick(View view)
        	{
        		ToDoItem workingItem;
        		CheckBox checkItem;
        		//Loop through all items currently on screen.
        		for(int i=0; i<nonArchivedItems.size(); i++)
        		{
        			workingItem = nonArchivedItems.get(i);
        			// from http://stackoverflow.com/questions/4830864/android-find-item-by-id
        			checkItem = (CheckBox)findViewById(workingItem.getID());
        			
        			//See if the item is checked:
        			if(checkItem.isChecked()) {
        				//It is. Inform the ToDo handler to move that item into the archive now!
        				ToDoHandler.moveIntoArchive(workingItem.getID());
        			}
        		}
        		
        		//Done. End the activity and inform mainActivity there were successful operations
        		Intent returnI = new Intent();
        		returnI.putExtra("archivesuccess", true);
        		setResult(RESULT_OK, returnI);
        		finish();
        	}
        	
        });
        
        mainLL.addView(okButton);
        
        
        setContentView(sView);
        
        //Load non archived items from to do handler
        nonArchivedItems = ToDoHandler.GetNonarchivedItems();
        
        //Add them to the screen with assigned ID
        ToDoItem currentItem;
        for(int i=0; i<nonArchivedItems.size(); i++)
        {
        	currentItem = nonArchivedItems.get(i);
        	AddCheckBox(currentItem.getText(), currentItem.getChecked(), currentItem.getID());
        }
        
	}
	
    public void AddCheckBox(String itemString, boolean checked, int newId)
    {
    	//Very similar to MainActivity's AddCheckBox function.
    	//For the method in this class, inform the user whether the item has been checked or not
    	String checkText;
    	if(checked)
    	{
    		checkText = "[X] ";
    	}else{
    		checkText = "[  ] ";
    	}
    	
    	CheckBox cb = new CheckBox(this);
    	cb.setText(checkText+itemString);

    	cb.setId(newId);
    	mainLL.addView(cb);
    }
}
