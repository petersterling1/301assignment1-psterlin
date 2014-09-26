package com.psterlin.assignment1_psterlin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class AddItemActivity extends Activity {

	//This activity is essentially a textbox and button. Once pushed the activity sends the contents of the textbox back to main activity.
	
	public void AddItem(View view)
	{
		//"Add" button has been checked.
		//Grab the text from the checkbox and put it in an intent to send back to mainActivity, then end this activity.
		//Example on how to do this from http://stackoverflow.com/questions/10407159/how-to-manage-start-activity-for-result-on-android
		EditText item = (EditText) findViewById(R.id.item_field);
		String itemstring = item.getText().toString();
		Intent returnI = new Intent();
		returnI.putExtra("item", itemstring);
		setResult(RESULT_OK,returnI);
		finish();
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_item);
	}
	
}
