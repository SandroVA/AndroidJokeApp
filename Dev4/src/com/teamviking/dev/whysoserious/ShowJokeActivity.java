package com.teamviking.dev.whysoserious;

import java.util.Arrays;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * SettingsActivity class 
 * Phase 1 This class either displays Monologue or Q&A
 * jokes.
 * 
 * @author Christopher Reid 0934402
 * @author Alessandro Rodi 1134337
 * @author Sandro Victoria-Arena 1036757
 * 
 */
public class ShowJokeActivity extends Activity {

	private LinearLayout layout;
	private DBHelper dbh;
	private Bundle bundle;
	private int id, jokeType;
	private TextView questionTV, answerTV;
	private ListView monologueLV;
	private ArrayAdapter<String> adapter;
	private String[] displayMonologue;
	private int monologueLineCount;
	private String[] monologueLine;

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
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		dbh = new DBHelper(getApplicationContext());
		bundle = getIntent().getExtras();

		// Get the ID of the joke passed to the ShowJokeActivity UI
		id = bundle.getInt("id");

		// Find the Joke using the ID
		Cursor cursor = dbh.getJokeById(id);

		if (cursor.moveToFirst()) {

			// JokeTypeCode == 1: Q&A || JokeTypeCode == 2: Monologue
			jokeType = cursor.getInt(cursor
					.getColumnIndex(DBHelper.COLUMN_JOKETYPECODE));

			if (jokeType == 1) {
				// Question & Answer:

				// Use activity_question_answer.xml for layout
				setContentView(R.layout.activity_question_answer);

				// Register for context menu
				layout = (LinearLayout) findViewById(R.id.questionanswer_linearlayout);
				registerForContextMenu(layout);

				// Get the reference for the TextViews
				questionTV = (TextView) findViewById(R.id.questionanswer_question);
				answerTV = (TextView) findViewById(R.id.questionanswer_answer);

				// Get the Question from cursor
				String question = cursor.getString(cursor
						.getColumnIndex(DBHelper.COLUMN_QUESTIONTEXT));

				// Get the Answer from cursor
				String answer = cursor.getString(cursor
						.getColumnIndex(DBHelper.COLUMN_ANSWERTEXT));

				// Place the Strings into the TextViews
				questionTV.setText(question);
				answerTV.setText(answer);

				// Set Answer TextView to invisible
				answerTV.setVisibility(View.INVISIBLE);

				// Add OnClickListener to the Display Answer button
				Button displayAnswerBtn = (Button) findViewById(R.id.questionanswer_display_answer_button);
				displayAnswerBtn.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {

						answerTV.setVisibility(View.VISIBLE);
						v.setVisibility(View.INVISIBLE);
					}
				});

			} else {
				// Monologue:

				// Use activity_monologue.xml for layout
				setContentView(R.layout.activity_monologue);

				// Register for context menu
				layout = (LinearLayout) findViewById(R.id.monologue_linearlayout);
				registerForContextMenu(layout);

				monologueLineCount = 1;

				// Get the reference for the TextViews
				monologueLV = (ListView) findViewById(R.id.monologueList);
				registerForContextMenu(monologueLV);

				// Get the Monologue from cursor
				String monologue = cursor.getString(cursor
						.getColumnIndex(DBHelper.COLUMN_MONOLOGUETEXT));
				monologueLine = monologue.split("\\|");
				displayMonologue = new String[monologueLine.length];
				Arrays.fill(displayMonologue, "");
				displayMonologue[0] = monologueLine[0];

				adapter = new ArrayAdapter<String>(this,
						android.R.layout.simple_list_item_1, displayMonologue);
				monologueLV.setAdapter(adapter);
				monologueLV.setTextFilterEnabled(true);

				// Add OnClickListener to the Display Answer button
				Button displayMonologueAnswerBtn = (Button) findViewById(R.id.monologue_next_joke_button);
				displayMonologueAnswerBtn
						.setOnClickListener(new OnClickListener() {
							@Override
							public void onClick(View v) {
								if (monologueLineCount < monologueLine.length) {
									displayMonologue[monologueLineCount] = monologueLine[monologueLineCount];
									monologueLineCount++;
									adapter.notifyDataSetChanged();

									if (monologueLineCount == monologueLine.length) {
										v.setVisibility(View.INVISIBLE);
									}
								}
							}
						});
			}
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
	 * onCreateContextMenu Method
	 * Creates the context menu
	 * 
	 * @param ContextMenu menu
	 * @param View v
	 * @param ContextMenuInfo menuInfo
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
	 * onContextItemSelected Method
	 * Handles which context item was selected
	 * 
	 * @param ContextMenu menu
	 * @param View v
	 * @param ContextMenuInfo menuInfo
	 * @return void
	 * @throws
	 * 
	 */
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		// info.position

		switch (item.getItemId()) {
		case R.id.edit:
			// Call the edit joke activity with this specific joke
			Intent i = new Intent(getApplicationContext(),
					EditJokeActivity.class);
			i.putExtra("id", id);
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

							int deleted = dbh.deleteJoke(id);

							if (deleted == 1) {
								Log.d("database", "joke (id=" + id
										+ ") successfully deleted.");
							} else {
								Log.d("database", "error deleting joke (id="
										+ id + ").");
							}

							// Close the activity
							finish();

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

}
