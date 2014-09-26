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

public class UnarchiveActivity extends Activity {

	//This activity is used to unarchive data.
	
	private ScrollView sView;
	private LinearLayout mainLL;
	ArrayList<ToDoItem> archivedItems;
	
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
        lineBreak.setText("Check items you want to unarchive, click done when complete");
        mainLL.addView(lineBreak);
        
        //Create a new button
        Button okButton = new Button(this);
        okButton.setText("Done");
        // Add listener
        okButton.setOnClickListener(new OnClickListener() {
        	
        	@Override
        	public void onClick(View view)
        	{
        		ToDoItem workingItem;
        		CheckBox checkItem;
        		//Loop through all items currently on screen.
        		for(int i=0; i<archivedItems.size(); i++)
        		{
        			workingItem = archivedItems.get(i);
        			// from http://stackoverflow.com/questions/4830864/android-find-item-by-id
        			checkItem = (CheckBox)findViewById(workingItem.getID());
        			
        			//See if the item is checked:
        			if(checkItem.isChecked()) {
        				//It is. Inform the ToDo handler to move that item into the archive now!
        				ToDoHandler.moveOutOfArchive(workingItem.getID());
        			}
        		}
        		
        		//Done. End the activity. Let mainactivity know this was a success.
        		Intent returnI = new Intent();
        		returnI.putExtra("archivesuccess", true);
        		setResult(RESULT_OK, returnI);
        		finish();
        	}
        	
        });
        
        //Add button
        mainLL.addView(okButton);
        
        
        setContentView(sView);
        
        //Load archived items from to do handler
        archivedItems = ToDoHandler.GetArchivedItems();
        
        //Add them to the screen with assigned ID
        ToDoItem currentItem;
        for(int i=0; i<archivedItems.size(); i++)
        {
        	currentItem = archivedItems.get(i);
        	AddCheckBox(currentItem.getText(), currentItem.getChecked(), currentItem.getID());
        }
        
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
    	
    	//Add checkbox to the screen.
    	CheckBox cb = new CheckBox(this);
    	cb.setText(checkText+itemString);

    	cb.setId(newId);
    	mainLL.addView(cb);
    }
}
