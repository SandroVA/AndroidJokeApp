package com.teamviking.dev.whysoserious;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
	private ProgressBar pb;
    private int prg = 0;
    private ImageView iv;
    private String url;

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

		// Find the Joke using the ID
		Cursor cursor = dbh.getJokeById(id);

		if (cursor.moveToFirst()) {

			// JokeTypeCode == 1: Q&A || JokeTypeCode == 2: Monologue
			// || JokeTypeCode == 3: Images Jokes
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

			} else if (jokeType == 2) {
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
			} else {
				// Image Joke:

				// Use activity_monologue.xml for layout
				setContentView(R.layout.activity_imagejoke);

				pb = (ProgressBar) findViewById(R.id.imagejoke_progressbar);
				
				// Register for context menu
				layout = (LinearLayout) findViewById(R.id.imagejokeTopic_linearlayout);
				registerForContextMenu(layout);

				iv = (ImageView) findViewById(R.id.imagejoke_imageview);
				url = cursor.getString(cursor
						.getColumnIndex(DBHelper.COLUMN_QUESTIONTEXT));
				LoadImageFromWebOperations();

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
		}
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

	private void LoadImageFromWebOperations() {
		try {
			prg = 0;
			new GetImage().execute(url);
			new Thread(myThread).start();
			
		} catch (Exception e) {
			System.out.println("Exception=" + e);
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
	    
		private class GetImage extends AsyncTask<String, Void, Bitmap> {
			HttpURLConnection httpConnection = null;

			// background task
			@Override
			protected Bitmap doInBackground(String... urls) {
				Bitmap map = downloadImage(url);
				// return -> onPostExecute(map)
				return map;
			}

			// on UI thread after background task
			@Override
			protected void onPostExecute(Bitmap map) {
				if (map == null)
					Toast.makeText(getApplicationContext(),
							"Unable to read bitmap", Toast.LENGTH_LONG).show();
				else
					iv.setImageBitmap(map);
				if (httpConnection != null)
					httpConnection.disconnect();
				
				prg = 101;
			}

			// Make HttpURLConnection and return InputStream
			// we do not read the stream here
			private InputStream getHttpConnection(String urlString)
					throws IOException {
				InputStream stream = null;
				if (!netIsUp()) {
					return null;
				}
				URL url = new URL(urlString);
				URLConnection connection = url.openConnection();

				try {
					httpConnection = (HttpURLConnection) connection;
					// default: httpConnection.setRequestMethod("GET");
					httpConnection.connect();

					if (httpConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
						stream = httpConnection.getInputStream();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				return stream;
			}

			// Creates Bitmap from InputStream and returns it
			// must be called from doInBackground()
			// network I/O must be done in background thread
			private Bitmap downloadImage(String url) {
				Bitmap bitmap = null;
				InputStream stream = null;
				/*
				 * android.graphicsBitmapFactory creates Bitmap objects from files,
				 * streams, byte arrays
				 */

				BitmapFactory.Options bmOptions = new BitmapFactory.Options();
				// use image original size, not smaller image
				bmOptions.inSampleSize = 1;

				try {
					//
					stream = getHttpConnection(url);
					if (stream != null) {
						// use the BitmapFactory to read the stream and create a
						// bitmap
						bitmap = BitmapFactory
								.decodeStream(stream, null, bmOptions);
						stream.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
				return bitmap;
			} // downloadImage()
		} // AsyncTask GetImage()

		public boolean netIsUp() {
			ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
			// getActiveNetworkInfo() each time as the network may swap as the
			// device moves
			NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
			// ALWAYS check isConnected() before initiating network traffic
			if (networkInfo != null)
				return networkInfo.isConnected();
			else
				return false;
		} // netIsUp()
}
