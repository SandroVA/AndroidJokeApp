package com.teamviking.dev.whysoserious;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
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
 * AboutActivity class Phase 1 This is where you will search for jokes online
 * 
 * @author Christopher Reid 0934402
 * @author Alessandro Rodi 1134337
 * @author Sandro Victoria-Arena 1036757
 * 
 */
public class WebSearchActivity extends Activity {
	private Spinner categorySpinner;
	private Spinner subCategorySpinner;
	private Spinner jokeTypeSpinner;
	private EditText shortDesc;

	private String categorySpinnerArray[];
	private String jokeTypeSpinnerArray[];
	private String geekSubCategorySpinnerArray[];
	private String holidaySubCategorySpinnerArray[];

//	String joketype = Integer.toString(jokeTypeSpinner.getSelectedItemPosition() + 1);
//	String category = categorySpinner.getSelectedItem().toString();
//	String subcategory = subCategorySpinner.getSelectedItem().toString();
//	String description = shortDesc.getText().toString();
	
	private DBHelper dbh;
	
	private ArrayList<String> jokeList;
	private ArrayList<String> jokeIdList;

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
		setContentView(R.layout.activity_websearch);

		dbh = new DBHelper(getApplicationContext());

		jokeTypeSpinner = (Spinner) findViewById(R.id.websearch_jokeTypeSpinner);
		categorySpinner = (Spinner) findViewById(R.id.websearch_category_spinner);
		subCategorySpinner = (Spinner) findViewById(R.id.websearch_subCategory_spinner);
		shortDesc = (EditText) findViewById(R.id.websearch_short_desc_editText);

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

		// Add OnClickListener to the Search button
		Button searchBtn = (Button) findViewById(R.id.websearch_search_button);
		searchBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
				try {
					
					String description = shortDesc.getText().toString();
					
					if (description.matches("")) {
						Toast.makeText(getApplicationContext(), "You did not enter a description", Toast.LENGTH_SHORT).show();
					} else {
					new RetrieveJokeTask().execute("");
					}
					
					

				}
				catch(Exception e) {
					
				}
			}
		});

		// Add OnClickListener to the Reset button
		Button resetBtn = (Button) findViewById(R.id.websearch_reset_button);
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
			startActivity(new Intent(this, WebSearchActivity.class));
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	/*
	 * findData()
	 * 
	 * 1. given a string to match, set up a URL
	 * 
	 * 2. create and open an HttpURLConnection
	 * 
	 * 3. Check to make sure the connection returned HTTP_OK (200)
	 * 
	 * 4. Read the stream from the connection
	 * 
	 * 5. Close the stream & the connection
	 */

	public String findData() throws MalformedURLException,
			IOException {
		
//		Log.d("Category value:",category);
//		Log.d("subcategory value:",subcategory);
//		Log.d("description value:",description);
//		Log.d("joketype value:",joketype);
		
		String urlString = "http://waldo2.dawsoncollege.qc.ca/1134337/androidHandler.php?category=Geek";
	//	String urlString = String.format("http://waldo2.dawsoncollege.qc.ca/1134337/androidHandler.php?"
//				+ "category=%s"
//				+ "&subcategory=%s"
//				+ "&description=%s"
//				+ "&joketype=%s", category ,subcategory,description,joketype);
		
		StringBuilder response = new StringBuilder();
		
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
	public ArrayList <String> processJSONResponse(String resp) throws IllegalStateException,
			IOException, JSONException, NoSuchAlgorithmException {
		// [] wrapped in array
		JSONObject jobj = new JSONObject(resp);
		JSONArray array = new JSONArray(jobj.getString("jokes"));
		ArrayList <String> jokes = new ArrayList<String>();
		jokeIdList = new ArrayList<String>();
		
		for (int i = 0; i < array.length(); i++) {
			jobj = array.getJSONObject(i);

			// named items in array
			if (jobj.has("joke")) {
				//jokes.add(jobj.getString("joke"));
				
				JSONObject issueObj = new JSONObject(jobj.getString("joke"));

				        jokes.add(issueObj.optString("s_description"));
				        jokeIdList.add(issueObj.optString("_id"));
			} 
		}
		
		return jokes;
	}
	
	private class RetrieveJokeTask extends AsyncTask<String, Void, ArrayList<String>> {

	    private Exception exception;

	    protected ArrayList<String> doInBackground(String... urls) {
	    	ArrayList <String> jokes = null;
	    	
			try {
				String s = findData();
				jokes = processJSONResponse(s);
			}
			catch (Exception e) {
				
			}
			jokeList = jokes;
			
			if(jokeList!=null ||!jokeList.isEmpty())
			{
	    	Intent i = new Intent(WebSearchActivity.this,
					WebResultActivity.class);

			i.putExtra("jokeList", jokeList);
			i.putExtra("jokeIdList", jokeIdList);
			
			startActivity(i);
			}
			else Toast.makeText(getApplicationContext(), "No jokes found", Toast.LENGTH_SHORT).show();
		
			return jokes;
	    }

	    protected void onPostExecute(String string) {
	    
	    }
	}
}
