package com.teamviking.dev.whysoserious;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MonologueActivity extends Activity {
	
	
	private TextView monologue;
	
	
	

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    
	    setContentView(R.layout.activity_monologue);

	    monologue = (TextView) findViewById(R.id.monologueMultilineText);

	    
	    Intent myIntent= getIntent(); // gets the previously created intent
	    String monologueText = myIntent.getStringExtra("monologueText"); 
	    
	    monologue.setText(monologueText);

	    

	 		Button displayNextLineBtn = (Button) findViewById(R.id.monologue_next_joke_button);
	 		displayNextLineBtn.setOnClickListener(new OnClickListener() {
	 			@Override
	 			public void onClick(View v) {


	 			}
	 		});
	}

}
