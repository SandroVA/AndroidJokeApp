package com.teamviking.dev.whysoserious;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

/**
 * MainActivity class Phase 1 This is the Main Activity for the application.
 * 
 * @author Christopher Reid 0934402
 * @author Alessandro Rodi 1134337
 * @author Sandro Victoria-Arena 1036757
 * 
 */
public class MainActivity extends Activity {

	private AppPreferences _appPrefs;
	private DBHelper dbh;
	private SimpleCursorAdapter adapter;
	private String[] shortDescription;
	private ListView lv;

	/**
	 * onCreate Method Creates main activity
	 * 
	 * @param Bundle
	 *            savedInstanceState
	 * @return void
	 * @throws
	 * 
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		dbh = new DBHelper(getApplicationContext());

		// Load the app preferences
		_appPrefs = new AppPreferences(getApplicationContext());

		// This is for testing purposes. Every time this method is run it will
		// add the same jokes into the database.
		// Note: Because of this you will have multiples of the same joke.
		// comment this out to stop adding jokes	
		if(dbh.getNumberOfJokesInDatabase() == 0)
			addJokes();

		// Show random joke
		if (_appPrefs.showRandomJoke()) {

			Cursor random = dbh.getRandomJoke(getCategories());

			if (random.moveToFirst()) {

				Intent i = new Intent(getApplicationContext(),
						ShowJokeActivity.class);
				i.putExtra("id", random.getInt(random
						.getColumnIndex(DBHelper.COLUMN_ID)));
				startActivity(i);
			} else {
				// Launch AlertDialog for no random jokes
				AlertDialog alertDialog = new AlertDialog.Builder(this)
						.create();
				alertDialog.setTitle("Show Random Joke");
				alertDialog
						.setMessage("No jokes available from the category selection (go into Settings and change joke categetory selection)");
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
		}

		// Show splash screen
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
				startActivity(new Intent(getApplicationContext(),
						AddJokeActivity.class));
			}
		});

		// Add OnClickListener to the DBStatus button
		Button dbStatusBtn = (Button) findViewById(R.id.main_dbstatus_button);
		dbStatusBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(getApplicationContext(),
						DatabaseStatusActivity.class));
			}
		});

		// Add OnClickListener to the Search button
		Button searchBtn = (Button) findViewById(R.id.main_search_button);
		searchBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(getApplicationContext(),
						SearchJokeActivity.class));
			}
		});

		// Add OnClickListener to the Web button
		Button webBtn = (Button) findViewById(R.id.main_web_button);
		webBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(getApplicationContext(),
						WebSearchActivity.class));
			}
		});
		
		// Add OnClickListener to the Show Random Jokes button
		Button randomJokesBtn = (Button) findViewById(R.id.main_randomJokes_button);
		randomJokesBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				updateList();
			}
		});
	}

	@Override
	public void onResume() {
		super.onResume();

		updateList();

	}

	/**
	 * onCreateOptionsMenu Method Inflates the menu
	 * 
	 * @param Menu
	 *            menu
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
	 * onOptionsItemSelected Method Handles which option menu item was selected
	 * 
	 * @param MenuItem
	 *            item
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
			startActivity(new Intent(this, AboutActivity.class));
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	/**
	 * onCreateContextMenu Method Creates the context menu
	 * 
	 * @param ContextMenu
	 *            menu
	 * @param View
	 *            v
	 * @param ContextMenuInfo
	 *            menuInfo
	 * @return void
	 * @throws
	 * 
	 */
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.context_menu, menu);
	}

	/**
	 * onContextItemSelected Method Handles which context item was selected
	 * 
	 * @param ContextMenu
	 *            menu
	 * @param View
	 *            v
	 * @param ContextMenuInfo
	 *            menuInfo
	 * @return void
	 * @throws
	 * 
	 */
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		final AdapterContextMenuInfo info = (AdapterContextMenuInfo) item
				.getMenuInfo();
		// info.position

		switch (item.getItemId()) {
		case R.id.edit:
			// Call the edit joke activity with this specific joke

			// Get the cursor for the selected ListView Item
			Cursor cursor = (Cursor) adapter.getItem(info.position);

			// Create the intent
			Intent i = new Intent(getApplicationContext(),
					EditJokeActivity.class);
			i.putExtra("id",
					cursor.getInt(cursor.getColumnIndex(DBHelper.COLUMN_ID)));
			startActivity(i);

			return true;

		case R.id.delete:
			// Launch AlertDialog for confirmation
			AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
			alertDialog.setTitle("Delete...");
			alertDialog.setMessage("Are you sure?");
			alertDialog.setPositiveButton("OK",
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {

							// Delete the joke

							// Get the cursor for the selected ListView Item
							Cursor cursor = (Cursor) adapter
									.getItem(info.position);

							int id = cursor.getInt(cursor
									.getColumnIndex(DBHelper.COLUMN_ID));

							int deleted = dbh.deleteJoke(id);

							if (deleted == 1) {
								Log.d("database", "joke (id=" + id
										+ ") successfully deleted.");

							} else {
								Log.d("database", "error deleting joke (id="
										+ id + ").");
							}

							updateList();
						}
					});
			alertDialog.setNegativeButton("Cancel",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							Toast.makeText(getApplicationContext(),
									"Cancelled!", Toast.LENGTH_SHORT).show();
						}
					});
			alertDialog.show();
			return true;

		default:
			return super.onContextItemSelected(item);
		}
	}

	/**
	 * getCategories Method Used to construct the String array to send to the
	 * DBHelper class for the category query
	 * 
	 * @param
	 * @return String[]
	 * @throws
	 * 
	 */
	private String[] getCategories() {
		// Determine size of array
		int counter = 0;
		if (_appPrefs.showGeekCategory())
			counter++;
		if (_appPrefs.showHolidayCategory())
			counter++;
		if (_appPrefs.showKidsCategory())
			counter++;
		if (_appPrefs.showHorrorCategory())
			counter++;
		if (_appPrefs.showSchoolCategory())
			counter++;

		String categories[] = new String[counter];

		// Determine categories
		counter = 0;
		if (_appPrefs.showGeekCategory()) {
			categories[counter] = "Geek";
			counter++;
		}
		if (_appPrefs.showHolidayCategory()) {
			categories[counter] = "Holiday";
			counter++;
		}
		if (_appPrefs.showKidsCategory()) {
			categories[counter] = "Kids";
			counter++;
		}
		if (_appPrefs.showHorrorCategory()) {
			categories[counter] = "Horror";
			counter++;
		}
		if (_appPrefs.showSchoolCategory()) {
			categories[counter] = "School";
			counter++;
		}
		return categories;
	}

	/**
	 * updateList Method Updates the ListView.
	 * 
	 * @param
	 * @return void
	 * @throws
	 * 
	 */
	private void updateList() {
		// Get the reference to the ListView object
		lv = (ListView) findViewById(R.id.main_list);

		if (dbh.getNumberOfJokesInDatabase() > 0) {
			// Fetch 5 random jokes
			Cursor cursor = dbh.getFiveRandomJokes(getCategories());

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
						.setMessage("No jokes from the categories selected (go into Settings and change joke categetory selection)");
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
	 * addJokes Method Adds the jokes into the db
	 * 
	 * @param
	 * @return void
	 * @throws
	 * 
	 */
	private void addJokes() {
		dbh.insertNewJoke(
				"Geek",
				"Computer Science",
				1,
				"Geek - Computer Science - Joke 1",
				"How do you keep a programmer in the shower all day?",
				"Give him a bottle of shampoo which says: Lather, rinse, repeat.",
				"Test", 10, "String of comments here", "Url of joke here", true);

		dbh.insertNewJoke(
				"Geek",
				"Computer Science",
				1,
				"Geek - Computer Science - Joke 2",
				"How many Object Oriented programmers does it take to change a lightbulb?",
				"None, they send it a message, and it changes itself.", "Test",
				10, "String of comments here", "Url of joke here", true);

		dbh.insertNewJoke("Geek", "Computer Science", 2,
				"Geek - Computer Science - Joke 3 (monologue)", "", "",
				"Knock, knock. |Whos there? |very long pause... |Java.", 10,
				"String of comments here", "Url of joke here", true);

		dbh.insertNewJoke(
				"Geek",
				"Computer Science",
				2,
				"Geek - Computer Science - Joke 4 (monologue)",
				"",
				"",
				"Programming is 10% science... |20% ingenuity... |and 70% getting the ingenuity to work with the science.",
				10, "String of comments here", "Url of joke here", true);

		dbh.insertNewJoke(
				"Geek",
				"Computer Science",
				2,
				"Geek - Computer Science - Joke 5 (monologue)",
				"",
				"",
				"There are only 10 kinds of people in this world: |those who know binary |and those who dont.",
				10, "String of comments here", "Url of joke here", true);

		dbh.insertNewJoke("Geek", "Computer Science", 1,
				"Geek - Computer Science - Joke 6",
				"Have you heard about the new Cray super computer?",
				"Its so fast, it executes an infinite loop in 6 seconds.",
				"Test", 10, "String of comments here", "Url of joke here", true);

		dbh.insertNewJoke("Geek", "Computer Science", 1,
				"Geek - Computer Science - Joke 7",
				"Whats the object-oriented way to become wealthy?",
				"Inheritance", "Test", 10, "String of comments here",
				"Url of joke here", true);

		dbh.insertNewJoke(
				"Geek",
				"Computer Science",
				1,
				"Geek - Computer Science - Joke 8",
				"How many prolog programmers does it take to change a lightbulb?",
				"Yes", "Test", 10, "String of comments here",
				"Url of joke here", true);

		dbh.insertNewJoke(
				"Geek",
				"Computer Science",
				2,
				"Geek - Computer Science - Joke 9 (monologue)",
				"",
				"",
				"To understand what recursion is, |you must first understand recursion.",
				10, "String of comments here", "Url of joke here", true);

		dbh.insertNewJoke(
				"Geek",
				"Computer Science",
				1,
				"Geek - Computer Science - Joke 10",
				"How many computer programmers does it take to change a light bulb?",
				"None, that's a hardware problem.", "Test", 10,
				"String of comments here", "Url of joke here", true);

		dbh.insertNewJoke("Holiday", "Halloween", 1,
				"Holiday - Halloween - Joke 11",
				"Why didn't the skeleton cross the road?",
				"He didn’t have the guts!", "Test", 10,
				"String of comments here", "Url of joke here", true);

		dbh.insertNewJoke("Kids", "", 1, "Kids - - Joke 12",
				"Why is 6 afraid of 7?", "Because 7 8 9!", "Test", 10,
				"String of comments here", "Url of joke here", true);

		dbh.insertNewJoke("Horror", "", 1, "Horror - - Joke 13",
				"Why do mummies have trouble keeping friends?",
				"They're too wrapped up in themselves...", "Test", 10,
				"String of comments here", "Url of joke here", true);

		dbh.insertNewJoke("School", "", 1, "School - -Joke 14",
				"Why was school easier for cave people?",
				"Because there was no history to study!", "Test", 10,
				"String of comments here", "Url of joke here", true);

		dbh.insertNewJoke("Geek", "Computer Science", 1,
				"Geek - Computer Science - Joke 15",
				"What do you call 8 Hobbits?", "A Hobbyte.", "Test", 10,
				"String of comments here", "Url of joke here", true);

		dbh.insertNewJoke("Geek", "Computer Science", 2,
				"Geek - Computer Science - Joke 16 (monologue)", "", "",
				"I would tell you a UDP joke, |but you might not get it.", 10,
				"String of comments here", "Url of joke here", true);
		dbh.insertNewJoke(
				"Geek",
				"Computer Science",
				3,
				"Geek - Computer Science - Joke 17 (Image)",
				"http://1.bp.blogspot.com/-Kf8WXqDGgnc/UWepQL_MZ-I/AAAAAAAAADg/f8ZSJasg9P4/s1600/Robot+Android+yang+Lucu.jpg",
				"", "Test", 10, "String of comments here",
				"Url of joke here", true);
		dbh.insertNewJoke(
				"Geek",
				"Computer Science",
				3,
				"Geek - Computer Science - Joke 18 (Image)",
				"http://www.bestvpn.com/wp-content/uploads/2013/06/android-logo-white.png",
				"", "Test", 10, "String of comments here",
				"Url of joke here", true);
		dbh.insertNewJoke(
				"Geek",
				"Computer Science",
				3,
				"Geek - Computer Science - Joke 19 (Image)",
				"http://crackberry.com/sites/crackberry.com/files/styles/large/public/topic_images/2013/ANDROID.png?itok=xhm7jaxS",
				"", "Test", 10, "String of comments here",
				"Url of joke here", true);
	}

}
