package com.psterlin.assignment1_psterlin;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Base64;

public class LoadSaveManager {

	//This class handles loading and saving data.
	//It is taken from Dr. Hindle's StudentPicker.
	//Video found at https://www.youtube.com/watch?v=gmNfc6u1qk0
	//GPL2 License.
	
	Context context;
	static String pFile = "todoList";
	static String slKey = "todolist";
	
	public LoadSaveManager(Context ct)
	{
		this.context = ct;
	}
	
	public ArrayList<ToDoItem> LoadItems() throws ClassNotFoundException, IOException
	{
		SharedPreferences settings = context.getSharedPreferences(pFile, Context.MODE_PRIVATE);
		String todoData = settings.getString(slKey, "");
		if(todoData.equals(""))
		{
			ArrayList<ToDoItem> cList = new ArrayList<ToDoItem>();
			return cList;
		} else {
			return this.returnToDoList(todoData);
		}
	}
	
	@SuppressWarnings("unchecked")
	private ArrayList<ToDoItem> returnToDoList(String cString) throws ClassNotFoundException, IOException
	{
		ByteArrayInputStream bi = new ByteArrayInputStream(Base64.decode(cString, Base64.DEFAULT));
		ObjectInputStream oi = new ObjectInputStream(bi);
		
		return (ArrayList<ToDoItem>)oi.readObject();
	}

	private String toDoListToString(ArrayList<ToDoItem> saveList) throws IOException {
		// TODO Auto-generated method stub
		ByteArrayOutputStream bo = new ByteArrayOutputStream();
		ObjectOutputStream oo = new ObjectOutputStream(bo);
		oo.writeObject(saveList);
		oo.close();
		byte bytes[] = bo.toByteArray();
		return Base64.encodeToString(bytes, Base64.DEFAULT);
	}
	
	public void SaveItems(ArrayList<ToDoItem> saveList) throws IOException
	{
		SharedPreferences settings = context.getSharedPreferences(pFile, Context.MODE_PRIVATE);
		Editor editor = settings.edit();
		editor.putString(slKey, toDoListToString(saveList));
		editor.commit();
	}
	
}
