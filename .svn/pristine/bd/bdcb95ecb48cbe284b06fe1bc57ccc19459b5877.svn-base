package com.teamviking.dev.whysoserious;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class qaActivity extends Activity {
	
	
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
	    

	 		Button displayAnswerBtn = (Button) findViewById(R.id.questionanswer_display_answer_button);
	 		displayAnswerBtn.setOnClickListener(new OnClickListener() {
	 			@Override
	 			public void onClick(View v) {

	 				shortAnswer.setVisibility(View.VISIBLE);

	 			}
	 		});
	}

}
