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
import android.content.Intent;

/**
 * AddJokeActivity class 
 * Phase 1 This class ads jokes to the database.
 * 
 * @author Christopher Reid 0934402
 * @author Alessandro Rodi 1134337
 * @author Sandro Victoria-Arena 1036757
 * 
 */
public class AddJokeActivity extends Activity {
	private Spinner categorySpinner;
	private Spinner subCategorySpinner;
	private Spinner jokeTypeSpinner;
	private EditText shortDesc;
	private EditText shortAnswer;
	private EditText shortQuestion;
	private EditText monologue;

	private static DBHelper dbh;

	private String categorySpinnerArray[];
	private String jokeTypeSpinnerArray[];
	private String geekSubCategorySpinnerArray[];
	private String holidaySubCategorySpinnerArray[];

	/**
	 * onCreate Method
	 * Creates add joke activity
	 * 
	 * @param Bundle savedInstanceState
	 * @return void
	 * @throws
	 * 
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_addjoke);

		dbh = new DBHelper(getApplicationContext());

		jokeTypeSpinner = (Spinner) findViewById(R.id.addjoke_jokeTypeSpinner);
		categorySpinner = (Spinner) findViewById(R.id.addjoke_category_spinner);
		subCategorySpinner = (Spinner) findViewById(R.id.addjoke_subCategory_spinner);
		shortDesc = (EditText) findViewById(R.id.horrorNbOfJokesTextView);
		shortQuestion = (EditText) findViewById(R.id.addjoke_short_question_editText);
		shortAnswer = (EditText) findViewById(R.id.addjoke_short_answer_editText);
		monologue = (EditText) findViewById(R.id.addjoke_short_monologue_editText);

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

							monologue.setText("");
						} else if (jokeTypeSpinnerArray[position] == "Monologue") {

							shortAnswer.setText("");
							shortQuestion.setText("");
						}
					}

					@Override
					public void onNothingSelected(AdapterView<?> parent) {
						
					}
				});

		// Add OnClickListener to the AddJoke button
		Button addJokeBtn = (Button) findViewById(R.id.addjoke_updatejoke_button);
		addJokeBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				if (jokeTypeSpinner.getSelectedItem().toString() == "Q & A") {
					monologue.setText("");

					if (categorySpinner.getSelectedItem().toString() != ""
							&& subCategorySpinner.getSelectedItem().toString() != ""
							&& shortDesc.getText().toString() != ""
							&& shortQuestion.getText().toString() != ""
							&& shortAnswer.getText().toString() != ""
							&& shortAnswer.getText().toString() != null) {

						dbh.insertNewJoke(categorySpinner.getSelectedItem()
								.toString(), subCategorySpinner
								.getSelectedItem().toString(), 1, shortDesc
								.getText().toString(), shortQuestion.getText()
								.toString(), shortAnswer.getText().toString(),
								monologue.getText().toString(), 0,
								"A test joke", "Source of joke here", true);

						Toast toast = Toast.makeText(getApplicationContext(),
								"Joke successfully added to database.",
								Toast.LENGTH_SHORT);
						toast.show();

						jokeTypeSpinner.setSelection(0);
						categorySpinner.setSelection(0);
						subCategorySpinner.setSelection(0);
						shortDesc.setText("");
						shortQuestion.setText("");
						shortAnswer.setText("");
						monologue.setText("");

					} else {
						Toast toast = Toast
								.makeText(
										getApplicationContext(),
										"You must fill in the required values for a Q&A Joke.",
										Toast.LENGTH_SHORT);
						toast.show();

					}
				} else {
					shortAnswer.setText("");
					shortQuestion.setText("");

					if (categorySpinner.getSelectedItem().toString() != ""
							&& subCategorySpinner.getSelectedItem().toString() != ""
							&& shortDesc.getText().toString() != ""
							&& monologue.getText().toString() != "") {

						dbh.insertNewJoke(categorySpinner.getSelectedItem()
								.toString(), subCategorySpinner
								.getSelectedItem().toString(), 2, shortDesc
								.getText().toString(), shortQuestion.getText()
								.toString(), shortAnswer.getText().toString(),
								monologue.getText().toString(), 0,
								"A test joke", "Source of joke here", true);

						Toast toast = Toast.makeText(getApplicationContext(),
								"Joke successfully added to database.",
								Toast.LENGTH_SHORT);
						toast.show();

						jokeTypeSpinner.setSelection(0);
						categorySpinner.setSelection(0);
						subCategorySpinner.setSelection(0);
						shortDesc.setText("");
						shortQuestion.setText("");
						shortAnswer.setText("");
						monologue.setText("");

					} else {
						Toast toast = Toast
								.makeText(
										getApplicationContext(),
										"You must fill in the required values for a MonologueJoke.",
										Toast.LENGTH_SHORT);
						toast.show();
					}

				}

			}
		});

		// Add OnClickListener to the Reset button
		Button resetBtn = (Button) findViewById(R.id.addjoke_reset_button);
		resetBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				categorySpinner.setSelection(0);
				subCategorySpinner.setClickable(false);
				shortDesc.setText("");
				shortAnswer.setText("");
				shortQuestion.setText("");
				monologue.setText("");
			}
		});

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
}
