package com.teamviking.dev.whysoserious;

import java.util.ArrayList;
import java.util.List;

import com.teamviking.dev.whysoserious.DBHelper;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;
import android.database.Cursor;
/*
 * Example 2 for ListView
 * ListView is an AbsListView, AbsListView is an AdapterView,  AdapterView is a ViewGroup, ViewGroup is a View
 * ArrayAdapter is a BaseAdapter
 * 
 * Second  example of a ListView, populated by an array created in line,
 * the ListView responds to item click and long click events, we dynamically modify the data  
 */
public class SimpleLV extends Activity {
	
	private List<String> data;
	private ListView lv;
	
	private SimpleCursorAdapter scAdapter;
	
	private static DBHelper dbh;
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_database_test);
		
		//OR USE THIS
		dbh = new DBHelper(getApplicationContext());

		// create and populate our array
		data = new ArrayList<String>();
		
//		dbh.insertNewJoke("Geek", "Science", 1, "Funny joke by alessandro", "Is sandro heterosexual?", "No", "Test", 10, "String of comments here", "Url of joke here", true);
//		dbh.insertNewJoke("Geek", "Science", 1, "Funny joke by alessandro", "Is sandro heterosexual?", "No", "Test", 10, "String of comments here", "Url of joke here", true);
//		dbh.insertNewJoke("Geek", "Science", 1, "Funny joke by alessandro", "Is sandro heterosexual?", "No", "Test", 10, "String of comments here", "Url of joke here", true);
//		dbh.insertNewJoke("Geek", "Science", 1, "Funny joke by alessandro", "Is sandro heterosexual?", "No", "Test", 10, "String of comments here", "Url of joke here", true);
//		dbh.insertNewJoke("Geek", "Science", 1, "Funny joke by alessandro", "Is sandro heterosexual?", "No", "Test", 10, "String of comments here", "Url of joke here", true);
//		dbh.insertNewJoke("Geek", "Science", 1, "Funny joke by alessandro", "Is sandro heterosexual?", "No", "Test", 10, "String of comments here", "Url of joke here", true);
//		dbh.insertNewJoke("Geek", "Science", 1, "Funny joke by alessandro", "Is sandro heterosexual?", "No", "Test", 10, "String of comments here", "Url of joke here", true);
		
		
		Cursor cursor = dbh.getJokes();
		
		//USE THE METHOD HERE TO ADD SOME JOKES
		scAdapter = new SimpleCursorAdapter(
			    this, android.R.layout.simple_list_item_1, 
			    cursor, 
			    new String[] { "questionText"}, 
			    new int[] {android.R.id.text1},
			    	1);
		
		
		//USE THE METHOD HERE TO GET THE JOKES YOU WANT WITH THE COLUMN YOU WANT
		while(cursor.moveToNext())
		{
			data.add(cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_QUESTIONTEXT)));
		}
		

		// get a reference to the listview object
		lv = (ListView) findViewById(R.id.list);

		// set the item click Listener for the ListView
		lv.setOnItemClickListener(delOnClick);

		// set the item long click listener for the ListView
		lv.setOnItemLongClickListener(longClick);

		/*
		 * ListView, GridView, GalleryView containers are types of ArrayAdapter
		 * controls ArrayAdapter is a BaseAdapter, Adapters bind data to View
		 * objects
		 * 
		 * the layout xml template is builtin: R.Layout.simple_list_item_1
		 * 
		 * documentation:
		 * http://developer.android.com/reference/android/R.layout.html
		 * 
		 * source:
		 * https://github.com/android/platform_frameworks_base/blob/master
		 * /core/res/layout/simple_list_item_1.xml
		 * 
		 * the data in this case is a String array that we just created
		 */

		/*
		 * setAdapter() sets the data & layout behind the ListView
		 */
		lv.setAdapter(scAdapter);

		

	} // onCreate()



	/*
	 * delOnClick() this listener is attached to the ListView when an item is
	 * clicked we delete the item in the list
	 */
	private OnItemClickListener delOnClick = new OnItemClickListener() {
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			/*
			 * delete the list item, we could also use ArrayAdapter.remove(T
			 * object);
			 */
			data.remove(position);
			// notify the observer
			scAdapter.notifyDataSetChanged();
		}
	}; // delOnClick
	
	/*
	 * longClick() this listener is attached to the ListView when an item is
	 * held on long click we do not modify the data we Toast the position
	 */

	private OnItemLongClickListener longClick = new OnItemLongClickListener() {
		public boolean onItemLongClick(AdapterView<?> parent, View view,
				int position, long id) {
			Toast.makeText(getApplicationContext(),
					"Long Click position " + position, Toast.LENGTH_SHORT)
					.show();
			/*
			 * data has not changed so no need for lv.invalidateViews(); or
			 * aa.notifyDataSetChanged();
			 */
			// Return true to consume the click event.
			return true;
		}
	}; // longClick

} // SimpleLV class
