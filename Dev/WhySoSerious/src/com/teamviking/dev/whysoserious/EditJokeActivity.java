package com.teamviking.dev.whysoserious;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.app.Activity;
import android.content.Intent;

/**	EditJokeActivity class
 * Phase 1	Description of this class
 * 
 * @author 	Christopher Reid 0934402
 * @author	Alessandro Rodi 1134337 		
 * @author 	Sandro Victoria-Arena 1036757
 *
 */
public class EditJokeActivity extends Activity {
	private EditText jokeId;
	private Spinner  category;
	private EditText subCategory;
	private EditText shortDesc;
	private EditText shortAnswer;
	private EditText shortQuestion;
	private EditText monologue;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_editjoke);
		
		jokeId = (EditText) findViewById(R.id.editjoke_id_editText);
		category = (Spinner) findViewById(R.id.editjoke_category_spinner);
		subCategory = (EditText)findViewById(R.id.editjoke_sub_category_editText);
		shortDesc = (EditText)findViewById(R.id.editjoke_short_desc_editText);
		shortQuestion = (EditText)findViewById(R.id.editjoke_short_question_editText);
		shortAnswer = (EditText)findViewById(R.id.editjoke_short_answer_editText);
		monologue = (EditText)findViewById(R.id.editjoke_short_monologue_editText);
		
		// Add OnClickListener to the AddJoke button
		Button addJokeBtn = (Button) findViewById(R.id.editjoke_update_button);
		addJokeBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// do stuff here
			}
		});
		
		// Add OnClickListener to the Reset button
		Button resetBtn = (Button) findViewById(R.id.editjoke_reset_button);
		resetBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//Don't forget to set a default category later on
				subCategory.setText("");
				shortDesc.setText("");
				shortAnswer.setText("");
				shortQuestion.setText("");
				monologue.setText("");
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
	
}
