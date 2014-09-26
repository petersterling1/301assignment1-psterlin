package com.psterlin.assignment1_psterlin;

import java.util.ArrayList;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class MainActivity extends Activity {

	//These two variables here are used to generate the interface dynamically
	private ScrollView sView;
	private LinearLayout mainLL;
	final Context context = this;
	
	public boolean onCreateOptionsMenu(Menu mE) {
		//Created by eclipse.
        MenuInflater mI = getMenuInflater();
        mI.inflate(R.layout.options_menu, mE);
		return true;
	}
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        //Call ToDoHandler to load any saved items.
        ToDoHandler.loadList(getApplicationContext());
        
        //Refresh the UI (recreate it)
        this.refreshUI();
    }
    
    public void AddCheckBox(String itemString, boolean checked, int newId)
    {
    	//This function adds a checkbox to the screen.
    	
    	//Declare a new checkbox, set text, and mark it as checked.
    	CheckBox cb = new CheckBox(this);
    	cb.setText(itemString);
    	if(checked) cb.setChecked(true);
    	cb.setId(newId);
    	
    	//Set dynamic listener to react if the checkbox is checked.
    	//Example for check boxes taken from http://stackoverflow.com/questions/8386832/android-checkbox-listener
    	//By primax79 (Chris)
    	cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
    		
    		@Override
    		public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
    			
    			if(isChecked){
    				//Tell ToDoHandler to set the item to checked.
    				ToDoHandler.setItemChecked(buttonView.getId());
    			}else{
    				//Tell ToDoHandler to set the item to unchecked.
    				ToDoHandler.setItemUnchecked(buttonView.getId());
    			}
    			
    			//Save the list (it's changed)
    			ToDoHandler.saveList(getApplicationContext());
    			
    		}
    		
    	});
    	
    	//Add the checkbox to the main view.
    	mainLL.addView(cb);
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
    	
    	//Check to see what menu items were checked.
        int id = item.getItemId();
        if (id == R.id.add_item) {
        	//Add item has been checked. Start the add item activity and wait for a response.
        	Intent intent = new Intent(this, AddItemActivity.class);
        	startActivityForResult(intent, 1);
        	return true;
        }
        if(id == R.id.archive_item)
        {
        	//Archive item has been checked. Start the archive item activity and wait for a response.
        	Intent intent = new Intent(this, ArchiveActivity.class);
        	startActivityForResult(intent, 2);
        	return true;
        }
        if(id == R.id.unarchive_item)
        {
        	//Unarchive item has been checked. Start the unarchive item activity and wait for a response.
        	Intent intent = new Intent(this, UnarchiveActivity.class);
        	startActivityForResult(intent, 3);
        	return true;
        }
        if(id == R.id.summarize)
        {
        	//Summarize has been checked. Start the summary activity.
        	Intent intent = new Intent(this, SummaryActivity.class);
        	startActivity(intent);
        	return true;
        }
        if(id == R.id.delete_item)
        {
        	//Delete has been checked. Start the delete activity and wait for a response.
        	Intent intent = new Intent(this, DeleteActivity.class);
        	startActivityForResult(intent, 4);
        	return true;
        }
        if(id == R.id.email_item)
        {
        	//Email has been checked. Start the email activity.
        	Intent intent = new Intent(this, EmailActivity.class);
        	startActivity(intent);
        	return true;
        }
        if(id == R.id.view_archive)
        {
        	//The user wants to view the archive. Start up the view archive activity.
        	Intent intent = new Intent(this, ViewArchiveActivity.class);
        	startActivity(intent);
        	return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    private void refreshUI()
    {
    	//This redraws the UI on the screen.
    	
    	//Idea on how to dynamically update an UI is taken from http://stackoverflow.com/questions/9361744/dynamically-add-ui-content-in-android
    	//How to use a scrollview taken from http://stackoverflow.com/questions/6674341/how-to-use-scrollview-in-android
        //By Kartik
    	
    	//Declare a scrollview, and add a new linearlayout and put in in the scrollview.
    	
        sView = new ScrollView(this);
        sView.setPadding(15, 15, 15, 15);
        mainLL = new LinearLayout(this);
        mainLL.setOrientation(LinearLayout.VERTICAL);
        sView.addView(mainLL);
        
        //Add a caption saying "Todo" and add it to the linearlayout.
        TextView lineBreak = new TextView(this);
        lineBreak.setText("To do:");
        mainLL.addView(lineBreak);

        //Grab nonarchiveditems from ToDoHandler.
        ArrayList<ToDoItem> nonArchivedItems = ToDoHandler.GetNonarchivedItems();
        ToDoItem currentItem;
        
        //Loop through all the items and pass them to AddCheckbox.
        for(int i=0; i<nonArchivedItems.size(); i++)
        {
        	currentItem = nonArchivedItems.get(i);
        	this.AddCheckBox(currentItem.getText(), currentItem.getChecked(), currentItem.getID());
        }
        
        //Tell the activity to show the scroll view.
        setContentView(sView);
    }
    
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
    	//An activity has returned a result.
    	if(resultCode == RESULT_OK)
		{
    		if (requestCode == 1)
    		{
    			//RequestCode 1 is from "AddItem"
    			
    			//It returned one item with it which is the todo item we need to add.
    			//Pull it out.
    			//Learned how to do this from http://stackoverflow.com/questions/10407159/how-to-manage-start-activity-for-result-on-android
    		        //By Peter Mortensen
    			
    			String result = data.getStringExtra("item");
    			
    			//Generate a new ID for the new checkbox and inform ToDoHandler there is a new item.
    			int newID = View.generateViewId();
    			ToDoHandler.AddToDoItem(result, false, newID);
    			
    			//Since there is a new item, save.
    			ToDoHandler.saveList(getApplicationContext());
    			
    			//Refresh the UI (items have changed).
    			this.refreshUI();
    		}
    		if (requestCode == 2 || requestCode == 3 || requestCode == 4)
    		{	
    			//These request codes inform there was a successful delete, archive or unarchive.
    			//Save items since there were changes.
    			ToDoHandler.saveList(getApplicationContext());
    			
    			//Items have been changed successfully. Refresh UI.
    			this.refreshUI();
    		}
    	}
    }
}
