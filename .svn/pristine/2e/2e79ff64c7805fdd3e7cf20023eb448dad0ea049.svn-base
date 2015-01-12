package com.teamviking.dev.whysoserious;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Locale;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.app.Activity;
import android.content.Intent;

/**
 * DatabaseStatusActivity 
 * class Phase 1 This class gives information on the
 * Database status.
 * 
 * @author Christopher Reid 0934402
 * @author Alessandro Rodi 1134337
 * @author Sandro Victoria-Arena 1036757
 * 
 */
public class DatabaseStatusActivity extends Activity {

	/**
	 * onCreate Method
	 * Creates Database Status activity
	 * 
	 * @param Bundle savedInstanceState
	 * @return void
	 * @throws
	 * 
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_database_status);

		TextView databasestatus_firstdate = (TextView) findViewById(R.id.databasestatus_firstdate);
		TextView databasestatus_lastdate = (TextView) findViewById(R.id.databasestatus_lastdate);
		TextView geekNbOfJokesTextView = (TextView) findViewById(R.id.geekNbOfJokesTextView);
		TextView horrorNbOfJokesTextView = (TextView) findViewById(R.id.horrorNbOfJokesTextView);
		TextView schoolNbOfJokesTextView = (TextView) findViewById(R.id.schoolNbOfJokesTextView);
		TextView kidsNbOfJokesTextView = (TextView) findViewById(R.id.kidsNbOfJokesTextView);
		TextView holidayNbOfJokesTextView = (TextView) findViewById(R.id.holidayNbOfJokesTextView);

		DBHelper dbh = new DBHelper(getApplicationContext());

		geekNbOfJokesTextView.setText(String.valueOf(dbh
				.getNumberOfCategoryJokesInDatabase("Geek")));
		horrorNbOfJokesTextView.setText(String.valueOf(dbh
				.getNumberOfCategoryJokesInDatabase("Horror")));
		schoolNbOfJokesTextView.setText(String.valueOf(dbh
				.getNumberOfCategoryJokesInDatabase("School")));
		kidsNbOfJokesTextView.setText(String.valueOf(dbh
				.getNumberOfCategoryJokesInDatabase("Kids")));
		holidayNbOfJokesTextView.setText(String.valueOf(dbh
				.getNumberOfCategoryJokesInDatabase("Holiday")));

		if (dbh.getNumberOfJokesInDatabase() > 0) {
			databasestatus_firstdate.setText(convertTime(Long.parseLong(dbh
					.getFirstJokeDate())));
			databasestatus_lastdate.setText(convertTime(Long.parseLong(dbh
					.getLastJokeDate())));
		} else {
			databasestatus_firstdate.setText("none");
			databasestatus_lastdate.setText("none");
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
			startActivity(new Intent(this, AboutActivity.class));
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	/**
	 * convertTime Method
	 * Converts the time into a string.
	 * 
	 * @param long time
	 * @return String
	 * @throws
	 * 
	 */
	private String convertTime(long time) {
		Date date = new Date(time);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
				Locale.US);
		return format.format(date).toString();
	}
}
