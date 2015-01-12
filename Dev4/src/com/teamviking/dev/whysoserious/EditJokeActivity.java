package com.teamviking.dev.whysoserious;

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
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;

/**
 * EditJokeActivity class Phase 1 This class is where you can edit / delete a
 * joke.
 * 
 * @author Christopher Reid 0934402
 * @author Alessandro Rodi 1134337
 * @author Sandro Victoria-Arena 1036757
 * 
 */
public class EditJokeActivity extends Activity {
	private Spinner categorySpinner;
	private Spinner subCategorySpinner;
	private Spinner jokeTypeSpinner;
	private EditText shortDesc;
	private EditText shortAnswer;
	private EditText shortQuestion;
	private EditText monologue;
	private Button updateJokeBtn;
	private Button resetBtn;

	private String categorySpinnerArray[];
	private String jokeTypeSpinnerArray[];
	private String geekSubCategorySpinnerArray[];
	private String holidaySubCategorySpinnerArray[];

	private ArrayAdapter<String> categorySpinnerAdapter;
	private ArrayAdapter<String> subCategoryHolidaySpinnerAdapter;
	private ArrayAdapter<String> subCategoryGeekSpinnerAdapter;

	private int id;
	private DBHelper dbh;
	private Bundle bundle;

	/**
	 * onCreate Method Creates Edit Joke activity
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

		dbh = new DBHelper(getApplicationContext());
		bundle = getIntent().getExtras();

		// Get the ID of the joke passed to the ShowJokeActivity UI
		id = bundle.getInt("id");

		// Find the Joke using the ID
		final Cursor cursor = dbh.getJokeById(id);
		cursor.moveToFirst();

		setContentView(R.layout.activity_editjoke);

		dbh = new DBHelper(getApplicationContext());

		jokeTypeSpinner = (Spinner) findViewById(R.id.editjoke_jokeTypeSpinner);
		categorySpinner = (Spinner) findViewById(R.id.editjoke_category_spinner);
		subCategorySpinner = (Spinner) findViewById(R.id.editjoke_subCategory_spinner);
		shortDesc = (EditText) findViewById(R.id.editjoke_short_desc_editText);
		shortQuestion = (EditText) findViewById(R.id.editjoke_short_question_editText);
		shortAnswer = (EditText) findViewById(R.id.editjoke_short_answer_editText);
		monologue = (EditText) findViewById(R.id.editjoke_short_monologue_editText);

		updateJokeBtn = (Button) findViewById(R.id.editjoke_updatejoke_button);
		resetBtn = (Button) findViewById(R.id.editjoke_reset_button);

		Log.d("debug", "after find views");

		shortDesc.setText(cursor.getString(cursor
				.getColumnIndex(DBHelper.COLUMN_JOKESHORTDESCRIPTION)));
		shortQuestion.setText(cursor.getString(cursor
				.getColumnIndex(DBHelper.COLUMN_QUESTIONTEXT)));
		shortAnswer.setText(cursor.getString(cursor
				.getColumnIndex(DBHelper.COLUMN_ANSWERTEXT)));
		monologue.setText(cursor.getString(cursor
				.getColumnIndex(DBHelper.COLUMN_MONOLOGUETEXT)));

		jokeTypeSpinnerArray = new String[2];
		jokeTypeSpinnerArray[0] = "Q & A";
		jokeTypeSpinnerArray[1] = "Monologue";
		ArrayAdapter<String> jokeTypeSpinnerAdapter = new ArrayAdapter<String>(
				this, android.R.layout.simple_spinner_item,
				jokeTypeSpinnerArray);
		jokeTypeSpinner.setAdapter(jokeTypeSpinnerAdapter);

		jokeTypeSpinner.setSelection(cursor.getInt(cursor
				.getColumnIndex(DBHelper.COLUMN_JOKETYPECODE)) - 1);

		categorySpinnerArray = new String[5];
		categorySpinnerArray[0] = "Geek";
		categorySpinnerArray[1] = "Holiday";
		categorySpinnerArray[2] = "Kids";
		categorySpinnerArray[3] = "Horror";
		categorySpinnerArray[4] = "School";
		categorySpinnerAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, categorySpinnerArray);
		categorySpinner.setAdapter(categorySpinnerAdapter);

		int catSpinnerPosition = categorySpinnerAdapter.getPosition(cursor
				.getString(cursor
						.getColumnIndex(DBHelper.CATEGORYCOLUMN_CATEGORY)));

		categorySpinner.setSelection(catSpinnerPosition);

		geekSubCategorySpinnerArray = new String[2];
		geekSubCategorySpinnerArray[0] = "Science";
		geekSubCategorySpinnerArray[1] = "Computer Science";
		subCategoryGeekSpinnerAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item,
				geekSubCategorySpinnerArray);

		holidaySubCategorySpinnerArray = new String[2];
		holidaySubCategorySpinnerArray[0] = "Halloween";
		holidaySubCategorySpinnerArray[1] = "Thanksgiving";
		subCategoryHolidaySpinnerAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item,
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

							int spinnerPosition = subCategoryGeekSpinnerAdapter.getPosition(cursor.getString(cursor
									.getColumnIndex(DBHelper.CATEGORYCOLUMN_SUBCATEGORY)));
							subCategorySpinner.setSelection(spinnerPosition);

						} else if (categorySpinnerArray[position] == "Holiday") {
							subCategorySpinner.setClickable(true);
							subCategorySpinner
									.setAdapter(subCategoryHolidaySpinnerAdapter);

							int spinnerPosition = subCategoryHolidaySpinnerAdapter.getPosition(cursor.getString(cursor
									.getColumnIndex(DBHelper.CATEGORYCOLUMN_SUBCATEGORY)));
							subCategorySpinner.setSelection(spinnerPosition);
						} else
							subCategorySpinner.setClickable(false);
						subCategorySpinner.setBackgroundColor(2);
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

		// Add OnClickListener to the AddJoke button
		updateJokeBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (jokeTypeSpinner.getSelectedItem().toString() == "Q & A") {

					if (categorySpinner.getSelectedItem().toString() != ""
							&& subCategorySpinner.getSelectedItem().toString()
									.toString() != ""
							&& shortDesc.getText().toString() != ""
							&& shortQuestion.getText().toString() != ""
							&& shortAnswer.getText().toString() != ""
							&& shortAnswer.getText().toString() != null) {

						if (subCategorySpinner.isClickable()) {
							dbh.updateJoke(id, categorySpinner
									.getSelectedItem().toString(),
									subCategorySpinner.getSelectedItem()
											.toString(), 1, shortDesc.getText()
											.toString(), shortQuestion
											.getText().toString(), shortAnswer
											.getText().toString(), monologue
											.getText().toString(), 0,
									"A test joke", "Source of joke here", true);
						} else {
							dbh.updateJoke(id, categorySpinner
									.getSelectedItem().toString(),
									"No SubCategory", 1, shortDesc.getText()
											.toString(), shortQuestion
											.getText().toString(), shortAnswer
											.getText().toString(), monologue
											.getText().toString(), 0,
									"A test joke", "Source of joke here", true);
						}

					} else {
						Context context = getApplicationContext();
						CharSequence text = "Fill in the required values(short description, question and answer) for a valid Q & A Joke.";
						int duration = Toast.LENGTH_SHORT;

						Toast toast = Toast.makeText(context, text, duration);
						toast.show();

					}
				} else {

					if (categorySpinner.getSelectedItem().toString() != ""
							&& subCategorySpinner.getSelectedItem().toString()
									.toString() != ""
							&& shortDesc.getText().toString() != ""
							&& monologue.getText().toString() != "") {
						dbh.insertNewJoke(categorySpinner.getSelectedItem()
								.toString(), subCategorySpinner
								.getSelectedItem().toString(), 2, shortDesc
								.getText().toString(), shortQuestion.getText()
								.toString(), shortAnswer.getText().toString(),
								monologue.getText().toString(), 0,
								"A test joke", "Source of joke here", true);
					} else {
						Context context = getApplicationContext();
						CharSequence text = "Fill in the required values(short description and monologue) for a valid Monologue Joke.";
						int duration = Toast.LENGTH_SHORT;

						Toast toast = Toast.makeText(context, text, duration);
						toast.show();
					}

				}

			}
		});

		// Add OnClickListener to the Reset button
		resetBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// Resets the fields to the values contained in the cursor
				// Joke type
				jokeTypeSpinner.setSelection(cursor.getInt(cursor
						.getColumnIndex(DBHelper.COLUMN_JOKETYPECODE)) - 1);

				// Category
				int position = categorySpinnerAdapter.getPosition(cursor
						.getString(cursor
								.getColumnIndex(DBHelper.COLUMN_CATEGORY)));
				categorySpinner.setSelection(position);

				// Subcategory
				if (position == 0) {
					subCategorySpinner.setSelection(subCategoryGeekSpinnerAdapter.getPosition(cursor.getString(cursor
							.getColumnIndex(DBHelper.COLUMN_SUBCATEGORY))));

				} else if (position == 1) {
					subCategorySpinner.setSelection(subCategoryGeekSpinnerAdapter.getPosition(cursor.getString(cursor
							.getColumnIndex(DBHelper.COLUMN_SUBCATEGORY))));

				}

				// Description
				shortDesc.setText(cursor.getString(cursor
						.getColumnIndex(DBHelper.COLUMN_JOKESHORTDESCRIPTION)));

				// Question
				shortQuestion.setText(cursor.getString(cursor
						.getColumnIndex(DBHelper.COLUMN_QUESTIONTEXT)));

				// Answer
				shortAnswer.setText(cursor.getString(cursor
						.getColumnIndex(DBHelper.COLUMN_ANSWERTEXT)));

				// Monologue
				monologue.setText(cursor.getString(cursor
						.getColumnIndex(DBHelper.COLUMN_MONOLOGUETEXT)));
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
			startActivity(new Intent(this, AboutActivity.class));
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
}
