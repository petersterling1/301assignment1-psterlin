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
import android.widget.Toast;

public class EmailActivity extends Activity {

	private ScrollView sView;
	private LinearLayout mainLL;
	ArrayList<ToDoItem> currentItems;
	boolean onArchived;
	
	//This activity handles emailing doto items.
	
	private void RemakeUI(boolean archived)
	{
		//Remakes the UI. If archived is true it will show archived items, otherwise not.
		
		//Create a new scrollview and linearlayout
		sView = new ScrollView(this);
		onArchived = archived;
		sView.setPadding(15, 15, 15, 15);
		mainLL = new LinearLayout(this);
		mainLL.setOrientation(LinearLayout.VERTICAL);
		sView.addView(mainLL);
		
		//Depending if it's archived or not, the text will look different and load different items from todohandler.
		if(archived)
		{
	        TextView lineBreak = new TextView(this);
	        lineBreak.setText("Check archived items you want to email. Click email when finished.");
	        mainLL.addView(lineBreak);
	        currentItems = ToDoHandler.GetArchivedItems();
		}else{
	        TextView lineBreak = new TextView(this);
	        lineBreak.setText("Check unarchived items you want to email. Click email when finished.");
	        mainLL.addView(lineBreak);
	        currentItems = ToDoHandler.GetNonarchivedItems();
		}
		
		//Add a button.
		AddDoneButton();
		
		//Loop through all the items and put them on the screen.
        ToDoItem currentItem;
        for(int i=0; i<currentItems.size(); i++)
        {
        	currentItem = currentItems.get(i);
        	AddCheckBox(currentItem.getText(), currentItem.getChecked(), currentItem.getID());
        }
        
        setContentView(sView);
        
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
    	
    	//Create new checkbox and put it on the screen.
    	CheckBox cb = new CheckBox(this);
    	cb.setText(checkText+itemString);

    	cb.setId(newId);
    	mainLL.addView(cb);
    }
	

	public void EmailAll(View view)
	{
		//The "Email All" button was checked.
		
		//Grab both archived and nonarchived from ToDoHandler.
		ArrayList<ToDoItem> nonArchived = ToDoHandler.GetNonarchivedItems();
		ArrayList<ToDoItem> archived = ToDoHandler.GetArchivedItems();
		
		//Use EmailHandler to generate an email subject and body.
		String emailSubject = EmailHandler.generateSubject(3);
		String emailBody = EmailHandler.GenerateEmailBodyFromTwo(archived, nonArchived);
		
		//Send an email.
		
		//Code taken from http://stackoverflow.com/questions/2197741/how-can-i-send-emails-from-my-android-application
		//By fiXedd
		//Creative commons license
		Intent i = new Intent(Intent.ACTION_SEND);
		i.setType("message/rfc822");
		i.putExtra(Intent.EXTRA_EMAIL  , new String[]{}); //No recipients, let the user fill on their own
		i.putExtra(Intent.EXTRA_SUBJECT, emailSubject);
		i.putExtra(Intent.EXTRA_TEXT   , emailBody);
		try {
		    startActivity(Intent.createChooser(i, "Send mail..."));
		} catch (android.content.ActivityNotFoundException ex) {
		    Toast.makeText(EmailActivity.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
		}
		//Done code
		
	}
    
    public void SendEmail(ArrayList<ToDoItem> cItems)
    {
    	//The user has selected their own items.
    	
    	//Depending if the user is sending archived or not, set the headerstyle.
    	int headerstyle;
    	headerstyle = 0;
    	if(onArchived) headerstyle = 1;
    	
    	//Generate an emailBody or emailSubject.
    	String emailBody = EmailHandler.GenerateEmailBodyFromOne(cItems, onArchived);
    	String emailSubject = EmailHandler.generateSubject(headerstyle);
    	
	//Code taken from http://stackoverflow.com/questions/2197741/how-can-i-send-emails-from-my-android-application
    	//By fiXedd
    	//Creative commons license
	Intent i = new Intent(Intent.ACTION_SEND);
	i.setType("message/rfc822");
	i.putExtra(Intent.EXTRA_EMAIL  , new String[]{}); //No recipients, let the user fill on their own
	i.putExtra(Intent.EXTRA_SUBJECT, emailSubject);
	i.putExtra(Intent.EXTRA_TEXT   , emailBody);
	try {
	    startActivity(Intent.createChooser(i, "Send mail..."));
	} catch (android.content.ActivityNotFoundException ex) {
	    Toast.makeText(EmailActivity.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
	}
	//Done code
    }
    
	public void AddDoneButton()
	{
		//Create an email button.
        Button okButton = new Button(this);
        okButton.setText("Email");
        // Dynamic event.
        okButton.setOnClickListener(new OnClickListener() {
        	
        	@Override
        	public void onClick(View view)
        	{
        		//Create a workingList.
        		ArrayList<ToDoItem> newList = new ArrayList<ToDoItem>();
        		ToDoItem workingItem;
        		CheckBox checkItem;
        		//Loop through all items pulled from ToDoHandler.
        		for(int i=0; i<currentItems.size(); i++)
        		{
        			workingItem = currentItems.get(i);
        			// from http://stackoverflow.com/questions/4830864/android-find-item-by-id
        			checkItem = (CheckBox)findViewById(workingItem.getID());
        			
        			//See if the item is checked:
        			if(checkItem.isChecked()) {
        				//It is. Add it to a working list to send in an email.
        				newList.add(workingItem);
        			}
        		}
        		//We have what we need. Prepare an email with the newly formed list.
        		SendEmail(newList);
        		
        	}
        	
        });
        
        mainLL.addView(okButton);
	}
	
	public void EmailArchived(View view)
	{
		//Email archived button has been checked. Remake the UI.
		this.RemakeUI(true);
	}
	
	public void EmailUnarchived(View view)
	{
		//Email unarchived button has been checked. Remake the UI.
		this.RemakeUI(false);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// This activity starts out with the XML UI found in activity_email.xml.
		// Once one of the two buttons are pushed the UI is replaced.
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_email);
	}
}
