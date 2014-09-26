package com.psterlin.assignment1_psterlin;

import java.io.Serializable;

public class ToDoItem implements Serializable {

	//This class holds all information for each todo item.
	
	/**
	 * SERIALIZABLE VERSION UID
	 */
	private static final long serialVersionUID = 5790434921649335364L;
	private boolean inArchive;
	private boolean checked;
	private String itemText;
	private int id;
	
	public ToDoItem(String cItemText, boolean cChecked, boolean cArchived, int itemID)
	{
		//Constructor.
		//Assign whether it's archived, checked, assign it's text and ID.
		this.inArchive = cArchived;
		this.checked = cChecked;
		this.itemText = cItemText;
		this.id = itemID;
	}
	
	public String getText()
	{
		//Return the text of the item.
		return this.itemText;
	}
	
	public int getID()
	{
		//Return the ID of the item.
		return this.id;
	}
	
	public void assignID(int newID)
	{
		//Assign an ID for an item (used when items are loaded)
		this.id = newID;
	}
	
	public boolean getArchived()
	{
		//Return whether this item is archived.
		return this.inArchive;
	}
	
	public void setArchive(boolean cValue)
	{
		//Put or remove the item from the archive.
		this.inArchive = cValue;
	}
	
	public boolean getChecked()
	{
		//Return whether it is checked.
		return this.checked;
	}
	
	public void setChecked(boolean cValue)
	{
		//Set the checked value.
		this.checked = cValue;
	}
}

