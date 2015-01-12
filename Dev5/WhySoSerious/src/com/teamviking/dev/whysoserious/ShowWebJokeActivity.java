package com.teamviking.dev.whysoserious;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

/**
 * SettingsActivity class Phase 1 This class either displays Monologue or Q&A
 * jokes.
 * 
 * @author Christopher Reid 0934402
 * @author Alessandro Rodi 1134337
 * @author Sandro Victoria-Arena 1036757
 * 
 */
public class ShowWebJokeActivity extends Activity {

	private LinearLayout layout;
	private DBHelper dbh;
	private Bundle bundle;
	private int id;
	private String jokeType;
	private TextView questionTV, answerTV;
	private ListView monologueLV;
	private ArrayAdapter<String> adapter;
	private String[] displayMonologue;
	private int monologueLineCount;
	private String[] monologueLine;
	private ProgressBar pb;
    private int prg = 0;

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
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		dbh = new DBHelper(getApplicationContext());
		bundle = getIntent().getExtras();

		// Get the ID of the joke passed to the ShowJokeActivity UI
		id = bundle.getInt("id");

		new RetrieveJokeByIdTask().execute("");
		Log.d("started task","started");
		
//		
//		
//		
//		
//		
//		
//		
//		
//		
//		
//		
//		
//		
//		
//		
//		
//		
//		
//		
//		
//		
//		
//		// Find the Joke using the ID
//		Cursor cursor = dbh.getJokeById(id);
//
//		if (cursor.moveToFirst()) {
//
//			// JokeTypeCode == 1: Q&A || JokeTypeCode == 2: Monologue
//			// || JokeTypeCode == 3: Images Jokes
//			jokeType = cursor.getInt(cursor
//					.getColumnIndex(DBHelper.COLUMN_JOKETYPECODE));
//
//			if (jokeType == 1) {

//
//			} else if (jokeType == 2) {
//				
//			} else {

		//	}
	//	}
	}
	
	public void initializeQandA(String questionText, String answerText)
	{
		Log.d("Initialize Q and A with  ","after the initiliaze");
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
//		String question = cursor.getString(cursor
//				.getColumnIndex(DBHelper.COLUMN_QUESTIONTEXT));

		// Get the Answer from cursor
//		String answer = cursor.getString(cursor
//				.getColumnIndex(DBHelper.COLUMN_ANSWERTEXT));

		// Place the Strings into the TextViews
		questionTV.setText(questionText);
		answerTV.setText(answerText);

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
		// Find the Joke using the ID
				Cursor cursor = dbh.getJokeById(id);
		final Cursor dbCursor = cursor;
		Button addToDBBtn = (Button) findViewById(R.id.questionanswer_add_to_dB_button);
		addToDBBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// Add to SQLite DB
				dbh.insertNewJoke(
						dbCursor.getString(dbCursor
								.getColumnIndex(DBHelper.COLUMN_CATEGORY)),
						dbCursor.getString(dbCursor
								.getColumnIndex(DBHelper.COLUMN_SUBCATEGORY)),
						Integer.parseInt(dbCursor.getString(dbCursor
								.getColumnIndex(DBHelper.COLUMN_JOKETYPECODE))),
						dbCursor.getString(dbCursor
								.getColumnIndex(DBHelper.COLUMN_JOKESHORTDESCRIPTION)),
						dbCursor.getString(dbCursor
								.getColumnIndex(DBHelper.COLUMN_QUESTIONTEXT)),
						dbCursor.getString(dbCursor
								.getColumnIndex(DBHelper.COLUMN_ANSWERTEXT)),
						dbCursor.getString(dbCursor
								.getColumnIndex(DBHelper.COLUMN_MONOLOGUETEXT)),
						Integer.parseInt(dbCursor.getString(dbCursor
								.getColumnIndex(DBHelper.COLUMN_RATINGSCALE))),
						dbCursor.getString(dbCursor
								.getColumnIndex(DBHelper.COLUMN_COMMENTS)),
						dbCursor.getString(dbCursor
								.getColumnIndex(DBHelper.COLUMN_JOKESOURCE)),
						Boolean.valueOf(dbCursor.getString(dbCursor
								.getColumnIndex(DBHelper.COLUMN_RELEASESTATUS))));
				
						Toast toast = Toast
								.makeText(
										getApplicationContext(),
										"Added joke to Database",
										Toast.LENGTH_SHORT);
						toast.show();
			}
		
		});
	}

		public void initializeMonologue(String monologue)
		{
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
//							String monologue = cursor.getString(cursor
//									.getColumnIndex(DBHelper.COLUMN_MONOLOGUETEXT));
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
							Cursor cursor = dbh.getJokeById(id);
							final Cursor dbCursor = cursor;
							Button addToDBBtn = (Button) findViewById(R.id.monologue_add_to_dB_button);
							addToDBBtn.setOnClickListener(new OnClickListener() {
								@Override
								public void onClick(View v) {
									// Add to SQLite DB
									// Add to SQLite DB
									dbh.insertNewJoke(
											dbCursor.getString(dbCursor
													.getColumnIndex(DBHelper.COLUMN_CATEGORY)),
											dbCursor.getString(dbCursor
													.getColumnIndex(DBHelper.COLUMN_SUBCATEGORY)),
											Integer.parseInt(dbCursor.getString(dbCursor
													.getColumnIndex(DBHelper.COLUMN_JOKETYPECODE))),
											dbCursor.getString(dbCursor
													.getColumnIndex(DBHelper.COLUMN_JOKESHORTDESCRIPTION)),
											dbCursor.getString(dbCursor
													.getColumnIndex(DBHelper.COLUMN_QUESTIONTEXT)),
											dbCursor.getString(dbCursor
													.getColumnIndex(DBHelper.COLUMN_ANSWERTEXT)),
											dbCursor.getString(dbCursor
													.getColumnIndex(DBHelper.COLUMN_MONOLOGUETEXT)),
											Integer.parseInt(dbCursor.getString(dbCursor
													.getColumnIndex(DBHelper.COLUMN_RATINGSCALE))),
											dbCursor.getString(dbCursor
													.getColumnIndex(DBHelper.COLUMN_COMMENTS)),
											dbCursor.getString(dbCursor
													.getColumnIndex(DBHelper.COLUMN_JOKESOURCE)),
											Boolean.valueOf(dbCursor.getString(dbCursor
													.getColumnIndex(DBHelper.COLUMN_RELEASESTATUS))));
									
											Toast toast = Toast
													.makeText(
															getApplicationContext(),
															"Added joke to Database",
															Toast.LENGTH_SHORT);
											toast.show();
								}
							});
		}
		
		public void initializeImageJoke(String urlQuestionText)
		{
			// Image Joke:

			// Use activity_monologue.xml for layout
			setContentView(R.layout.activity_imagejoke);

			pb = (ProgressBar) findViewById(R.id.imagejoke_progressbar);
			
			// Register for context menu
			layout = (LinearLayout) findViewById(R.id.imagejokeTopic_linearlayout);
			registerForContextMenu(layout);

			ImageView iv = (ImageView) findViewById(R.id.imagejoke_imageview);
			//String url = cursor.getString(cursor
					//.getColumnIndex(DBHelper.COLUMN_QUESTIONTEXT));
			Drawable drawable = LoadImageFromWebOperations(urlQuestionText);
			iv.setImageDrawable(drawable);
			
			
			Cursor cursor = dbh.getJokeById(id);
			final Cursor dbCursor = cursor;
			Button addToDBBtn = (Button) findViewById(R.id.imagejoke_add_to_dB_button);
			addToDBBtn.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// Add to SQLite DB
					dbh.insertNewJoke(
							dbCursor.getString(dbCursor
									.getColumnIndex(DBHelper.COLUMN_CATEGORY)),
							dbCursor.getString(dbCursor
									.getColumnIndex(DBHelper.COLUMN_SUBCATEGORY)),
							Integer.parseInt(dbCursor.getString(dbCursor
									.getColumnIndex(DBHelper.COLUMN_JOKETYPECODE))),
							dbCursor.getString(dbCursor
									.getColumnIndex(DBHelper.COLUMN_JOKESHORTDESCRIPTION)),
							dbCursor.getString(dbCursor
									.getColumnIndex(DBHelper.COLUMN_QUESTIONTEXT)),
							dbCursor.getString(dbCursor
									.getColumnIndex(DBHelper.COLUMN_ANSWERTEXT)),
							dbCursor.getString(dbCursor
									.getColumnIndex(DBHelper.COLUMN_MONOLOGUETEXT)),
							Integer.parseInt(dbCursor.getString(dbCursor
									.getColumnIndex(DBHelper.COLUMN_RATINGSCALE))),
							dbCursor.getString(dbCursor
									.getColumnIndex(DBHelper.COLUMN_COMMENTS)),
							dbCursor.getString(dbCursor
									.getColumnIndex(DBHelper.COLUMN_JOKESOURCE)),
							Boolean.valueOf(dbCursor.getString(dbCursor
									.getColumnIndex(DBHelper.COLUMN_RELEASESTATUS))));

							Toast toast = Toast
									.makeText(
											getApplicationContext(),
											"Added joke to Database",
											Toast.LENGTH_SHORT);
							toast.show();
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

	/**
	 * onCreateContextMenu Method Creates the context menu
	 * 
	 * @param ContextMenu
	 *            menu
	 * @param View
	 *            v
	 * @param ContextMenuInfo
	 *            menuInfo
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
	 * onContextItemSelected Method Handles which context item was selected
	 * 
	 * @param ContextMenu
	 *            menu
	 * @param View
	 *            v
	 * @param ContextMenuInfo
	 *            menuInfo
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

	private Drawable LoadImageFromWebOperations(String url) {
		try {
			prg = 0;
			new Thread(myThread).start();
			InputStream is = (InputStream) new URL(url).getContent();
			Drawable drawable = Drawable.createFromStream(is, "src name");
			prg = 101;
			return drawable;
		} catch (Exception e) {
			System.out.println("Exception=" + e);
			return null;
		}
	}

	  private Runnable myThread = new Runnable()
	    { 
	        @Override
	        public void run() 
	        {
	            while (prg < 100)
	            {
	                try
	                {
	                    hnd.sendMessage(hnd.obtainMessage());
	                    Thread.sleep(100);
	                }
	                catch(InterruptedException e) 
	                {  
	                    Log.e("ERROR", "Thread was Interrupted");
	                }
	            }

	            runOnUiThread(new Runnable() { 
	                public void run() {
	                    pb.setVisibility(View.GONE); 
	                }
	            });          
	        }
	    
	        Handler hnd = new Handler()
	        {    
	            @Override
	            public void handleMessage(Message msg) 
	            {
	                prg++;
	                pb.setProgress(prg);
	            }
	        };
	    };
	    
	    
	    
public String findData() throws MalformedURLException,
		IOException {
	String urlString = "http://www.gymever.com/phpTest/androidHandleFormRyan.php?searchById=yes&id=" + id;
	StringBuilder response = new StringBuilder();
	
	Log.d("Find data","in the find data");
	
	// 1. set up the URL
	URL url = new URL(urlString);
	// 2. create and open the http communications
	HttpURLConnection httpconn = (HttpURLConnection) url.openConnection();
	httpconn.connect();
	// 3. check if connection ok
	if (httpconn.getResponseCode() == HttpURLConnection.HTTP_OK) {
		// 4. read the stream from the connection
		BufferedReader input = new BufferedReader(new InputStreamReader(
				httpconn.getInputStream()));
		String strLine = null;
		while ((strLine = input.readLine()) != null) {
			response.append(strLine);
		}
		// 5. close the stream & the connection
		if (input != null)
			input.close();
		if (httpconn != null)
			httpconn.disconnect();
	}
	return response.toString();
}

/*
 * processJSONResponse()
 * 
 * Parameter is a JSON stream. Parsed using JSONArray [] JSONObject {}
 * Populate a struct array with the data.
 * 
 * To see an example of the results
 * http://waldo.dawsoncollege.qc.ca/pcampbell/wherelike.php?name=at
 * http://waldo.dawsoncollege.qc.ca/pcampbell/bornafter2.php?year=1970
 * http://jsoneditoronline.org/
 */
public String processJSONResponse(String resp) throws IllegalStateException,
		IOException, JSONException, NoSuchAlgorithmException {
	// [] wrapped in array
	String joke = null;
	
//	Log.d("Find reponse","in the process response");
	
	JSONObject jobj = new JSONObject(resp);
	if (jobj.has("joke")) {
	final JSONObject issueObj = new JSONObject(jobj.getString("joke"));
	
	joke = jobj.getString("joke");
	
	jokeType = issueObj.optString("joketype");
	
	Log.d("Joke :",joke);
	Log.d("jktype:",jokeType);
//	

	
	//jokeType = 1;
	
	if (jokeType.trim().equals("1"))
	{
		// Get a handler that can be used to post to the main thread
		Handler mainHandler = new Handler(getApplicationContext().getMainLooper());

		Runnable myRunnable = new Runnable(){public void run(){initializeQandA(issueObj.optString("question_text"),issueObj.optString("answer_text"));}}; // This is your code
		mainHandler.post(myRunnable);
		
		
	}
	else if(jokeType.trim().equals("2"))
	{// Get a handler that can be used to post to the main thread
				Handler mainHandler = new Handler(getApplicationContext().getMainLooper());

				Runnable myRunnable = new Runnable(){public void run(){initializeMonologue(issueObj.optString("monologue_text"));}}; // This is your code
				mainHandler.post(myRunnable);
				
				
	}
	else if(jokeType.trim().equals("3"))
	{
		Handler mainHandler = new Handler(getApplicationContext().getMainLooper());

		Runnable myRunnable = new Runnable(){public void run(){initializeImageJoke(issueObj.optString("question_text"));}}; // This is your code
		mainHandler.post(myRunnable);
		
	
	}
	//else if(jokeType == 3)
//	jokeIdList = new ArrayList<String>();
	
	
	
	
	
	
//	for (int i = 0; i < array.length(); i++) {
//		jobj = array.getJSONObject(i);
//
//		// named items in array
//		if (jobj.has("joke")) {
//			//jokes.add(jobj.getString("joke"));
//			
//			JSONObject issueObj = new JSONObject(jobj.getString("joke"));
//
//			        jokes.add(issueObj.optString("s_description"));
//			       // jokeIdList.add(issueObj.optString("_id"));
	//	} 
//	}
	
	
	}
	return joke;

}



class RetrieveJokeByIdTask extends AsyncTask<String, Void, String> {

    private Exception exception;

    protected String doInBackground(String... urls) {
    	String joke = null;
    	
		try {
			String s = findData();
			joke = processJSONResponse(s);
		}
		catch (Exception e) {
			
		}
	
		
	
		return joke;
    }

    protected void onPostExecute(String string) {
    
    }


}}


