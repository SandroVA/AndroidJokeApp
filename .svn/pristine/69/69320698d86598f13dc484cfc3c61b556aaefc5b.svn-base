package com.teamviking.dev.whysoserious;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.Format;
import java.text.SimpleDateFormat;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;

/**	DatabaseStatusActivity class
 * Phase 1	Description of this class
 * 
 * @author 	Christopher Reid 0934402
 * @author	Alessandro Rodi 1134337 		
 * @author 	Sandro Victoria-Arena 1036757
 *
 */
public class DatabaseStatusActivity extends Activity {

	
	private TextView databasestatus_lastdate;
	private TextView databasestatus_firstdate;
	private TextView geekNbOfJokesTextView;
	private TextView horrorNbOfJokesTextView;
	private TextView schoolNbOfJokesTextView;
	private TextView kidsNbOfJokesTextView;
	private TextView holidayNbOfJokesTextView;
	
	private DBHelper dbh;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_database_status);
		
		
		
		geekNbOfJokesTextView = (TextView)findViewById(R.id.geekNbOfJokesTextView);
		horrorNbOfJokesTextView = (TextView)findViewById(R.id.horrorNbOfJokesTextView);
		schoolNbOfJokesTextView = (TextView)findViewById(R.id.schoolNbOfJokesTextView);
		kidsNbOfJokesTextView = (TextView)findViewById(R.id.kidsNbOfJokesTextView);
		holidayNbOfJokesTextView = (TextView)findViewById(R.id.holidayNbOfJokesTextView);
		
		dbh = new DBHelper(getApplicationContext());
		
		
		
		
		
		Integer nbOfGeekJokes =
		dbh.getNumberOfCategoryJokesInDatabase("Geek");
		geekNbOfJokesTextView.setText(String.valueOf(nbOfGeekJokes));
		int nbOfHorrorJokes = dbh.getNumberOfCategoryJokesInDatabase("Horror");
		horrorNbOfJokesTextView.setText(String.valueOf(nbOfHorrorJokes));
		int nbOfSchoolJokes = dbh.getNumberOfCategoryJokesInDatabase("School");
		schoolNbOfJokesTextView.setText(String.valueOf(nbOfSchoolJokes));
		int nbOfKidsJokes = dbh.getNumberOfCategoryJokesInDatabase("Kids");
		kidsNbOfJokesTextView.setText(String.valueOf(nbOfKidsJokes));
		int nbOfHolidayJokes = dbh.getNumberOfCategoryJokesInDatabase("Holiday");
		holidayNbOfJokesTextView.setText(String.valueOf(nbOfHolidayJokes));
		
		databasestatus_firstdate = (TextView)findViewById(R.id.databasestatus_firstdate);
		databasestatus_firstdate.setText(convertTime(Long.parseLong(dbh.getFirstJokeDate())));
		databasestatus_lastdate = (TextView)findViewById(R.id.databasestatus_lastdate);
		databasestatus_lastdate.setText(convertTime(Long.parseLong(dbh.getLastJokeDate())));
		
		
		
		
	}
	
	public String convertTime(long time){
	    Date date = new Date(time);
	    Format format = new SimpleDateFormat("yyyy MM dd HH:mm:ss");
	    return format.format(date).toString();
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
}
