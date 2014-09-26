package com.psterlin.assignment1_psterlin;

import java.util.ArrayList;

public class EmailHandler {
	//This class generates email subjects and bodies.
	
	public static String generateSubject(int headerStyle)
	{
		//This function generates an email subject. Depending on the headerStyle passed it will format a different subject and return it.
		String subject;
		if(headerStyle == 0)
		{
			subject = "TODO Items (Unarchived)";
		}
		else if(headerStyle==1)
		{
			subject = "TODO Items (Archived)";
		}
		else
		{
			subject = "TODO Items (All)";
		}
		return subject;
	}
	
	public static String GenerateEmailBodyFromTwo(ArrayList<ToDoItem> archivedItems, ArrayList<ToDoItem> unArchivedItems)
	{
		//This function is intended when somebody pushes the "Email All" button. It'll format two lists of archived and unarchived items.
		String emailMessage;
		emailMessage = "Unarchived TODO items: \n\n";
		
		//Loop through all hte items and add them to the body.
		
		for(int i=0; i<unArchivedItems.size(); i++)
		{
			emailMessage = emailMessage + generateItemString(unArchivedItems.get(i))+"\n";
		}
		
		emailMessage = emailMessage + "\nArchived TODO items: \n\n";
		
		for(int i=0; i<archivedItems.size(); i++)
		{
			emailMessage = emailMessage + generateItemString(archivedItems.get(i))+"\n";
		}
		
		return emailMessage;
	}
	
	public static String GenerateEmailBodyFromOne(ArrayList<ToDoItem> cList, boolean archive)
	{
		//This is used to create an email body from one array (either archived or unarchived), indicated by the boolean.
		String emailMessage;
		if(archive)
		{
			emailMessage = "Archived TODO items: \n\n";
		}else{
			emailMessage = "Unarchived TODO items: \n\n";
		}
		
		//Loop through all hte items and add them to the body.
		
		for(int i=0; i<cList.size(); i++)
		{
			emailMessage = emailMessage + generateItemString(cList.get(i))+"\n";
		}
		
		return emailMessage;
	}
	
	private static String generateItemString(ToDoItem cItem)
	{
		//This function turns a ToDoItem into a string, indicating whether it was checked or not.
		String wString;
		if(cItem.getChecked())
		{
			wString = "[X] "+cItem.getText();
		}else{
			wString = "[  ] "+cItem.getText();
		}
		return wString;
	}
}
