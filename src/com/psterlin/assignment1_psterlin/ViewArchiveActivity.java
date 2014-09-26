package com.psterlin.assignment1_psterlin;

import java.util.ArrayList;
import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class ViewArchiveActivity extends Activity {

	//This activity allows you to view all items currently in the archive.
	
	private ScrollView sView;
	private LinearLayout mainLL;
	ArrayList<ToDoItem> nonArchivedItems;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		//Create a dynamic UI.
		super.onCreate(savedInstanceState);
		
		//Create scrollview and linearlist.
		
        sView = new ScrollView(this);
        sView.setPadding(15, 15, 15, 15);
        mainLL = new LinearLayout(this);
        mainLL.setOrientation(LinearLayout.VERTICAL);
        sView.addView(mainLL);
        
        //Add textview to inform user.
        
        TextView lineBreak = new TextView(this);
        lineBreak.setText("Archived items cannot have their state changed. Unarchive them first if you wish to edit them. \n\n");
        mainLL.addView(lineBreak);
        
        
        setContentView(sView);
        
        //Load archived items from to do handler
        nonArchivedItems = ToDoHandler.GetArchivedItems();
        
        //Add them to the screen
        ToDoItem currentItem;
        for(int i=0; i<nonArchivedItems.size(); i++)
        {
        	currentItem = nonArchivedItems.get(i);
        	AddText(currentItem.getText(), currentItem.getChecked());
        }
        
	}
	
    public void AddText(String itemString, boolean checked)
    {
    	//Adds a textview not a textbox, because the user cant edit archived items.
    	
    	//For the method in this class, inform the user whether the item has been checked or not
    	String checkText;
    	if(checked)
    	{
    		checkText = "[X] ";
    	}else{
    		checkText = "[  ] ";
    	}
    	
    	TextView cb = new TextView(this);
    	cb.setText(checkText+itemString);

    	//Add to display.
    	mainLL.addView(cb);
    }

}
