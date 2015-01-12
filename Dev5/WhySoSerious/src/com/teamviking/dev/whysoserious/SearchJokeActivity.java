package com.teamviking.dev.whysoserious;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * AboutActivity class Phase 1 This is where you will search for jokes offline
 * 
 * @author Christopher Reid 0934402
 * @author Alessandro Rodi 1134337
 * @author Sandro Victoria-Arena 1036757
 * 
 */
public class SearchJokeActivity extends Activity {
	private Spinner categorySpinner;
	private Spinner subCategorySpinner;
	private Spinner jokeTypeSpinner;
	private EditText shortDesc;

	private String categorySpinnerArray[];
	private String jokeTypeSpinnerArray[];
	private String geekSubCategorySpinnerArray[];
	private String holidaySubCategorySpinnerArray[];
	
	private DBHelper dbh;

	/**
	 * onCreate Method Creates about activity
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
		setContentView(R.layout.activity_searchjoke);

		dbh = new DBHelper(getApplicationContext());

		jokeTypeSpinner = (Spinner) findViewById(R.id.searchjoke_jokeTypeSpinner);
		categorySpinner = (Spinner) findViewById(R.id.searchjoke_category_spinner);
		subCategorySpinner = (Spinner) findViewById(R.id.searchjoke_subCategory_spinner);
		shortDesc = (EditText) findViewById(R.id.searchjoke_short_desc_editText);

		subCategorySpinner.setClickable(false);

		jokeTypeSpinnerArray = new String[2];
		jokeTypeSpinnerArray[0] = "Q & A";
		jokeTypeSpinnerArray[1] = "Monologue";
		ArrayAdapter<String> jokeTypeSpinnerAdapter = new ArrayAdapter<String>(
				this, android.R.layout.simple_spinner_item,
				jokeTypeSpinnerArray);
		jokeTypeSpinner.setAdapter(jokeTypeSpinnerAdapter);

		categorySpinnerArray = new String[5];
		categorySpinnerArray[0] = "Geek";
		categorySpinnerArray[1] = "Holiday";
		categorySpinnerArray[2] = "Kids";
		categorySpinnerArray[3] = "Horror";
		categorySpinnerArray[4] = "School";
		ArrayAdapter<String> categorySpinnerAdapter = new ArrayAdapter<String>(
				this, android.R.layout.simple_spinner_item,
				categorySpinnerArray);
		categorySpinner.setAdapter(categorySpinnerAdapter);

		geekSubCategorySpinnerArray = new String[2];
		geekSubCategorySpinnerArray[0] = "Science";
		geekSubCategorySpinnerArray[1] = "Computer Science";
		final ArrayAdapter<String> subCategoryGeekSpinnerAdapter = new ArrayAdapter<String>(
				this, android.R.layout.simple_spinner_item,
				geekSubCategorySpinnerArray);

		holidaySubCategorySpinnerArray = new String[2];
		holidaySubCategorySpinnerArray[0] = "Halloween";
		holidaySubCategorySpinnerArray[1] = "Thanksgiving";
		final ArrayAdapter<String> subCategoryHolidaySpinnerAdapter = new ArrayAdapter<String>(
				this, android.R.layout.simple_spinner_item,
				holidaySubCategorySpinnerArray);

		// Add listener to spinner
		categorySpinner
				.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
					public void onItemSelected(AdapterView<?> parent, View v,
							int position, long id) {
						Log.d("cspinneritem", categorySpinnerArray[position]);
						if (categorySpinnerArray[position] == "Geek") {
							subCategorySpinner.setClickable(true);
							subCategorySpinner
									.setAdapter(subCategoryGeekSpinnerAdapter);
						} else if (categorySpinnerArray[position] == "Holiday") {
							subCategorySpinner.setClickable(true);
							subCategorySpinner
									.setAdapter(subCategoryHolidaySpinnerAdapter);
						}
					}

					@Override
					public void onNothingSelected(AdapterView<?> parent) {
						
					}
				});

		jokeTypeSpinner
				.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
					public void onItemSelected(AdapterView<?> parent, View v,
							int position, long id) {
						Log.d("cspinneritem", jokeTypeSpinnerArray[position]);
						if (jokeTypeSpinnerArray[position] == "Q & A") {
							
						} else if (jokeTypeSpinnerArray[position] == "Monologue") {
							
						}
					}

					@Override
					public void onNothingSelected(AdapterView<?> parent) {
						
					}
				});

		jokeTypeSpinner
				.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
					public void onItemSelected(AdapterView<?> parent, View v,
							int position, long id) {
						Log.d("cspinneritem", jokeTypeSpinnerArray[position]);
						if (jokeTypeSpinnerArray[position] == "Q & A") {

						} else if (jokeTypeSpinnerArray[position] == "Monologue") {

						}
					}

					@Override
					public void onNothingSelected(AdapterView<?> parent) {

					}
				});

		// Add OnClickListener to the Reset button
		Button searchBtn = (Button) findViewById(R.id.searchjoke_search_button);
		searchBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				String joketype = Integer.toString(jokeTypeSpinner.getSelectedItemPosition() + 1);
				String category = categorySpinner.getSelectedItem().toString();
				String subcategory = subCategorySpinner.getSelectedItem().toString();
				String description = shortDesc.getText().toString();
				
				if (description.matches("")) {
					Toast.makeText(getApplicationContext(), "You did not enter a description", Toast.LENGTH_SHORT).show();
				} else {
					// Create the intent
					Intent i = new Intent(getApplicationContext(),
							SearchResultActivity.class);
					i.putExtra("joketype", joketype);
					i.putExtra("category", category);
					i.putExtra("subcategory", subcategory);
					i.putExtra("description", description);
					startActivity(i);
				}
				
				
			}
		});
		
		// Add OnClickListener to the Reset button
		Button resetBtn = (Button) findViewById(R.id.searchjoke_reset_button);
		resetBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				categorySpinner.setSelection(0);
				subCategorySpinner.setClickable(false);
				shortDesc.setText("");
			}
		});
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
			startActivity(new Intent(this, SearchJokeActivity.class));
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

}
