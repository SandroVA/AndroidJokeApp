package com.teamviking.dev.whysoserious;

import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemLongClickListener;

/**	MainActivity class
 * Phase 1	Description of this class
 * 
 * @author 	Christopher Reid 0934402
 * @author	Alessandro Rodi 1134337 		
 * @author 	Sandro Victoria-Arena 1036757
 *
 */
public class MainActivity extends Activity {

	private AppPreferences _appPrefs;
	private DBHelper dbh;
	private SimpleCursorAdapter scAdapter;
	private ListView lv;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		dbh = new DBHelper(getApplicationContext());
	
		//OR USE THIS
			

//	
				dbh.insertNewJoke("Geek", "Science", 1, "Funny joke by alessandro", "Is sandro heterosexual?", "No", "Test", 10, "String of comments here", "Url of joke here", true);
				dbh.insertNewJoke("Geek", "Science", 1, "Funny joke by alessandro", "Is sandro sexy?", "No", "Test", 10, "String of comments here", "Url of joke here", true);
				dbh.insertNewJoke("Geek", "Science", 1, "Funny joke by alessandro", "Is sandro gay?", "No", "Test", 10, "String of comments here", "Url of joke here", true);
				dbh.insertNewJoke("Geek", "Science", 1, "Funny joke by alessandro", "Is sandro a boy?", "No", "Test", 10, "String of comments here", "Url of joke here", true);
				dbh.insertNewJoke("Geek", "Science", 1, "Funny joke by alessandro", "Is sandro a girl?", "No", "Test", 10, "String of comments here", "Url of joke here", true);
				dbh.insertNewJoke("Geek", "Science", 1, "Funny joke by alessandro", "Is sandro SANDRO?", "No", "Test", 10, "String of comments here", "Url of joke here", true);
				dbh.insertNewJoke("Geek", "Science", 1, "Funny joke by alessandro", "Is sandro ?", "No", "Test", 10, "String of comments here", "Url of joke here", true);
				dbh.insertNewJoke("Geek", "Science", 1, "Funny joke by alessandro", "Is sandro really sandro?", "No", "Test", 10, "String of comments here", "Url of joke here", true);
				dbh.insertNewJoke("Geek", "Science", 1, "Funny joke by alessandro", "Is sandro alive?", "No", "Test", 10, "String of comments here", "Url of joke here", true);
				dbh.insertNewJoke("Geek", "Science", 1, "Funny joke by alessandro", "Is sandro dead?", "No", "Test", 10, "String of comments here", "Url of joke here", true);
				dbh.insertNewJoke("Geek", "Science", 1, "Funny joke by alessandro", "Is sandro java?", "No", "Test", 10, "String of comments here", "Url of joke here", true);
				dbh.insertNewJoke("Geek", "Science", 1, "Funny joke by alessandro", "Is sandro c#?", "No", "Test", 10, "String of comments here", "Url of joke here", true);
				dbh.insertNewJoke("Geek", "Science", 1, "Funny joke by alessandro", "Is sandro buff?", "No", "Test", 10, "String of comments here", "Url of joke here", true);
				dbh.insertNewJoke("Geek", "Science", 1, "Funny joke by alessandro", "Is sandro there", "No", "Test", 10, "String of comments here", "Url of joke here", true);
		
		
				Cursor cursor = dbh.getFiveRandomJokes();
				
				//USE THE METHOD HERE TO ADD SOME JOKES
				scAdapter = new SimpleCursorAdapter(
					    this, android.R.layout.simple_list_item_1, 
					    cursor, 
					    new String[] { DBHelper.COLUMN_JOKESHORTDESCRIPTION }, 
					    new int[] {android.R.id.text1},
					    	1);
				
				
				//USE THE METHOD HERE TO GET THE JOKES YOU WANT WITH THE COLUMN YOU WANT
//				while(cursor.moveToNext())
//				{
//					data.add(cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_QUESTIONTEXT)));
//				}
				

				// get a reference to the listview object
				lv = (ListView) findViewById(R.id.list);

				// set the item click Listener for the ListView
				lv.setOnItemClickListener(clickListener);
//


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
		
		// Load the app preferences
		_appPrefs = new AppPreferences(getApplicationContext());
		
		if (_appPrefs.showSplashOnStartup()) {
			Intent intent = new Intent(this, AboutActivity.class);
			intent.putExtra("splashclick", true);
			startActivity(intent);
		}
		
		// Add OnClickListener to the AddJoke button
		Button addJokeBtn = (Button) findViewById(R.id.main_addjoke_button);
		addJokeBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(getApplicationContext(), AddJokeActivity.class));
			}
		});
		
		// Add OnClickListener to the DBStatus button
		Button dbStatusBtn = (Button) findViewById(R.id.main_dbstatus_button);
		dbStatusBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(getApplicationContext(), DatabaseStatusActivity.class));
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		
		super.onCreateOptionsMenu(menu);
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch(item.getItemId()) {
		case R.id.settings:
			startActivity(new Intent(this, SettingsActivity.class));
			return true;
		case R.id.about:
			startActivity(new Intent(this, AboutActivity.class));
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	


	private OnItemClickListener clickListener = new OnItemClickListener() {
		

		@Override
		public void onItemClick(AdapterView<?> parent, View view,
				int position, long id) {
			Toast.makeText(getApplicationContext(),
					"Click position " + position, Toast.LENGTH_SHORT)
					.show();
			Cursor cursor = (Cursor) scAdapter.getItem(position);
			Log.d("getting joke id",cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_JOKETYPECODE)));
			if(cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_JOKETYPECODE)).equals(String.valueOf(1)))
			{
			Intent qaIntent = new Intent(getApplicationContext(),qaActivity.class);
			
			
			cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_QUESTIONTEXT));
			
			qaIntent.putExtra("shortQuestionText",cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_QUESTIONTEXT)));
			qaIntent.putExtra("shortAnswerText",cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_ANSWERTEXT)));
			startActivity(qaIntent);
			}
			else
				{
			Intent monologueIntent = new Intent(getApplicationContext(),qaActivity.class);
				
				
			cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_QUESTIONTEXT));
				
			monologueIntent.putExtra("monologueText",cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_MONOLOGUETEXT)));
		
			startActivity(monologueIntent);
				}
			/*
			 * data has not changed so no need for lv.invalidateViews(); or
			 * aa.notifyDataSetChanged();
			 */
			
			
		}
	}; // longClick

}
