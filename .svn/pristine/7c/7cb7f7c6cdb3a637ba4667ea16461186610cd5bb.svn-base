package com.teamviking.dev.whysoserious;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class SearchResultActivity extends Activity {


	private ListView lv;
	private DBHelper dbh;
	private SimpleCursorAdapter adapter;
	private Bundle bundle;
	private String joketype, category, subcategory, description;
	private String[] shortDescription;
	
	/**
	 * onCreate Method
	 * Creates about activity
	 * 
	 * @param Bundle savedInstanceState
	 * @return void
	 * @throws
	 * 
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_webresult);
		

		dbh = new DBHelper(getApplicationContext());
		bundle = getIntent().getExtras();

		// Get the ID of the joke passed to the ShowJokeActivity UI
		joketype = bundle.getString("joketype");
		category = bundle.getString("category");
		subcategory = bundle.getString("subcategory");
		description = bundle.getString("description");
		
		Log.d("wtf", joketype);
		Log.d("wtf", category);
		Log.d("wtf", subcategory);
		Log.d("wtf", description);

		// Get the reference to the ListView object
		lv = (ListView) findViewById(R.id.webresult_list);
		
		if (dbh.getNumberOfJokesInDatabase() > 0) {
			// Fetch jokes
			Cursor cursor = dbh.searchForJokes(joketype, category, subcategory, description);

			if (cursor.getCount() > 0) {

				// Instantiate the SimpleCursorAdapter with the Cursor
				shortDescription = new String[] { DBHelper.COLUMN_JOKESHORTDESCRIPTION };
				adapter = new SimpleCursorAdapter(this,
						android.R.layout.simple_list_item_1, cursor,
						shortDescription, new int[] { android.R.id.text1 }, 1);

				// Attach the adapter to the ListView
				lv.setAdapter(adapter);

				// Attach the ContextMenu to the ListView
				registerForContextMenu(lv);

				// Add OnItemClickListener to the ListView
				lv.setOnItemClickListener(new OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {

						Cursor cursor = (Cursor) adapter.getItem(position);
						Intent i = new Intent(getApplicationContext(),
								ShowJokeActivity.class);
						i.putExtra("id", cursor.getInt(cursor
								.getColumnIndex(DBHelper.COLUMN_ID)));
						startActivity(i);
					}
				});

			} else {
				// No jokes match the categories selected in settings

				// Launch AlertDialog for categories
				AlertDialog alertDialog = new AlertDialog.Builder(this)
						.create();
				alertDialog.setTitle("Categories");
				alertDialog
						.setMessage("No jokes match the search criteria");
				alertDialog.setButton(-1, "OK",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								Log.d("debug", "user click on ok");
							}
						});
				alertDialog.show();
			}
		} else {
			// database is empty

			// Launch AlertDialog for database
			AlertDialog alertDialog = new AlertDialog.Builder(this).create();
			alertDialog.setTitle("Database");
			alertDialog.setMessage("The database is empty");
			alertDialog.setButton(-1, "OK",
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							Log.d("debug", "user click on ok");
						}
					});
			alertDialog.show();
		}
	}

	/**
	 * onCreateOptionsMenu Method
	 * Inflates the menu
	 * 
	 * @param Menu menu
	 * @return boolean
	 * @throws
	 * 
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		super.onCreateOptionsMenu(menu);
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	/**
	 * onOptionsItemSelected Method
	 * Handles which option menu item was selected
	 * 
	 * @param MenuItem item
	 * @return boolean
	 * @throws
	 * 
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
		case R.id.settings:
			startActivity(new Intent(this, SettingsActivity.class));
			return true;
		case R.id.about:
			startActivity(new Intent(this, WebResultActivity.class));
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
}
