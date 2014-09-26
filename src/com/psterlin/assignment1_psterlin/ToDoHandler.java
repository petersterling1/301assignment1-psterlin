package com.psterlin.assignment1_psterlin;

import java.io.IOException;
import java.util.ArrayList;
import android.content.Context;
import android.view.View;

public class ToDoHandler {

	//This class stores all todoitems and all methods necessary to modify them. It is the backbone of the application.
	
	public static ArrayList<ToDoItem> iList = new ArrayList<ToDoItem>();
	
	private static void assignNewList(ArrayList<ToDoItem> newList)
	{
		//This is called as soon as a new list is loaded.
		
		//Assign ilist to the new list.
		iList = newList;
		
		//Unfortunately, if we just added these in when we call generateViewId() the IDs won't be unique and will overlap and cause all sorts of problems.
		//We neeed to loop through and assign them new IDs.
		for(int i=0; i<iList.size(); i++)
		{
			iList.get(i).assignID(View.generateViewId());

		}
	}
	
	public static void AddToDoItem(String toDoString, boolean checked, int itemID)
	{
		//Adds a new item.
		//Create a new ToDoItem and stick it in the list.
		ToDoItem newItem = new ToDoItem(toDoString, checked, false, itemID);
		iList.add(newItem);
	}
	
	public static void loadList(Context context)
	{
		//Load items.
		//Taken from Dr. Hindle's StudentPicker video
		//https://www.youtube.com/watch?v=gmNfc6u1qk0
		//GPL2 license.
		
		//Create a new instance of LoadSaveManager to handle the file stuff.
		LoadSaveManager lsm = new LoadSaveManager(context);
		try {
			ArrayList<ToDoItem> cItems = lsm.LoadItems();
			assignNewList(cItems);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void saveList(Context context)
	{
		//Save items
		//Taken from Dr. Hindle's StudentPicker video
		//https://www.youtube.com/watch?v=gmNfc6u1qk0
		//GPL2 license.
		
		//Create a new instance of LoadSaveManager to handle the file stuff.
		LoadSaveManager lsm = new LoadSaveManager(context);
		try {
			lsm.SaveItems(ToDoHandler.iList);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void setItemChecked(int itemID)
	{
		//Set an item as checked.
		
		//Loop through the list until found, mark as checked.
		ToDoItem cItem;
		for(int i=0; i<iList.size(); i++)
		{
			cItem = iList.get(i);
			if(cItem.getID() == itemID)
			{
				iList.get(i).setChecked(true);
			}
		}
	}
	
	public static void setItemUnchecked(int itemID)
	{
		//Set an item as unchecked.
		
		//Loop through the list until found, mark as unchecked.
		ToDoItem cItem;
		for(int i=0; i<iList.size(); i++)
		{
			cItem = iList.get(i);
			if(cItem.getID() == itemID)
			{
				iList.get(i).setChecked(false);
			}
		}
	}
	
	public static void deleteItem(int itemID)
	{
		//Delete an item.
		
		//Loop through until found, remove it.
		ToDoItem cItem;
		for(int i=0; i<iList.size(); i++)
		{
			cItem = iList.get(i);
			if(cItem.getID() == itemID)
			{
				iList.remove(i);
				return;
			}
		}
	}
	
	public static void moveIntoArchive(int itemID)
	{
		//Archive an item.
		
		//Loop through until found, mark it as archived.
		ToDoItem cItem;
		for(int i=0; i<iList.size(); i++)
		{
			cItem = iList.get(i);
			if(cItem.getID() == itemID)
			{
				iList.get(i).setArchive(true);
				return;
			}
		}
	}
	
	public static void moveOutOfArchive(int itemID)
	{
		//Unarchive an item.
		
		//Loop through until found, mark it as unarchived.
		ToDoItem cItem;
		for(int i=0; i<iList.size(); i++)
		{
			cItem = iList.get(i);
			if(cItem.getID() == itemID)
			{
				iList.get(i).setArchive(false);
				return;
			}
		}
	}
	
	public static ArrayList<ToDoItem> GetNonarchivedItems()
	{
		//This function returns a list of nonarchived items.
		
		//Loop through them all, putting nonarchived into their own list and return.
		ArrayList<ToDoItem> workingList = new ArrayList<ToDoItem>();
		ToDoItem cItem;
		
		for(int i=0; i<iList.size(); i++)
		{
			cItem = iList.get(i);
			if(cItem.getArchived() == false)
			{
				workingList.add(cItem);
			}
		}
		
		return workingList;
	}
	
	public static ArrayList<ToDoItem> GetArchivedItems()
	{
		//This function returns a list of archived items.
		
		//Loop through them all, putting archived into their own list and return.
		ArrayList<ToDoItem> workingList = new ArrayList<ToDoItem>();
		ToDoItem cItem;
		
		for(int i=0; i<iList.size(); i++)
		{
			cItem = iList.get(i);
			if(cItem.getArchived() == true)
			{
				workingList.add(cItem);
			}
		}
		
		return workingList;
	}
	
}
