package com.teamviking.dev.whysoserious;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class QuestionAnswerActivity extends Activity {
	
	
	private TextView shortAnswer;
	private TextView shortQuestion;
	
	

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    
	    setContentView(R.layout.activity_question_answer);

	    shortQuestion = (TextView) findViewById(R.id.questionanswer_question);
	    shortAnswer = (TextView) findViewById(R.id.questionanswer_answer);
	    shortAnswer.setVisibility(View.INVISIBLE);
	    
	    Intent myIntent= getIntent(); // gets the previously created intent
	    String shortQuestionText = myIntent.getStringExtra("shortQuestionText"); 
	    String shortAnswerText= myIntent.getStringExtra("shortAnswerText"); 
	    
	    shortQuestion.setText(shortQuestionText);
	    shortAnswer.setText(shortAnswerText);
	    
	    // Add OnClickListener to the Reset button
 		Button displayAnswerBtn = (Button) findViewById(R.id.questionanswer_display_answer_button);
 		displayAnswerBtn.setOnClickListener(new OnClickListener() {
 			@Override
 			public void onClick(View v) {
 				// Don't forget to set a default category later on
 				shortAnswer.setVisibility(View.VISIBLE);
 				v.setVisibility(View.INVISIBLE);
 				// monologue.setFocusable(false);
 				// shortQuestion.setFocusable(false);
 				// shortAnswer.setFocusable(false);
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
