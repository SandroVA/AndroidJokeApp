package com.teamviking.dev.whysoserious;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.app.Activity;
import android.content.Intent;

/**
 * SettingsActivity class 
 * Phase 1 Description of this class
 * 
 * @author Christopher Reid 0934402
 * @author Alessandro Rodi 1134337
 * @author Sandro Victoria-Arena 1036757
 * 
 */
public class SettingsActivity extends Activity {

	private AppPreferences _appPrefs;
	private Button updateBtn, resetBtn;
	private ToggleButton splashTB, randomJokeTB, ellipsizeTB, cat1TB, cat2TB,
			cat3TB;
	private boolean changesWereMade;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);

		_appPrefs = new AppPreferences(getApplicationContext());

		loadStuff();
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

	private void loadStuff() {

		// Instantiate buttons
		splashTB = (ToggleButton) findViewById(R.id.settings_splash_toggle);
		randomJokeTB = (ToggleButton) findViewById(R.id.settings_randomize_toggle);
		ellipsizeTB = (ToggleButton) findViewById(R.id.settings_ellipsize_toggle);
		cat1TB = (ToggleButton) findViewById(R.id.settings_category1_toggle);
		cat2TB = (ToggleButton) findViewById(R.id.settings_category2_toggle);
		cat3TB = (ToggleButton) findViewById(R.id.settings_category3_toggle);
		updateBtn = (Button) findViewById(R.id.settings_update_button);
		resetBtn = (Button) findViewById(R.id.settings_reset_button);

		// Use the shared preferences to change button values
		modifyToggleStates();

		// Add the action listeners to the toggle buttons
		splashTB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {

				_appPrefs.setSplashOnStartup(isChecked);
				changesWereMade = true;
			}
		});

		randomJokeTB
				.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {

						_appPrefs.setRandomJoke(isChecked);
						changesWereMade = true;
					}
				});

		ellipsizeTB
				.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {

						_appPrefs.setEllipsizedJokes(isChecked);
						changesWereMade = true;
					}
				});

		cat1TB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {

				_appPrefs.setCategory1(isChecked);
				changesWereMade = true;
			}
		});

		cat2TB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {

				_appPrefs.setCategory2(isChecked);
				changesWereMade = true;
			}
		});

		cat3TB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {

				_appPrefs.setCategory3(isChecked);
				changesWereMade = true;
			}
		});

		updateBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
				if (changesWereMade) {
					if (_appPrefs.updatePreferences()) {
						modifyToggleStates();
						Toast toast = Toast
								.makeText(getApplicationContext(),
										"Settings saved.",
										Toast.LENGTH_SHORT);
						toast.show();
					}
					debugLogger();
				}
			}
		});

		resetBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				if (_appPrefs.resetPreferences()) {
					modifyToggleStates();
					Toast toast = Toast
							.makeText(getApplicationContext(),
									"Settings reset to default.",
									Toast.LENGTH_SHORT);
					toast.show();
				}
				debugLogger();
			}
		});

	}

	private void modifyToggleStates() {

		// Ensures that the buttons correspond to the SharedPreferences values

		if (splashTB.isChecked() != _appPrefs.showSplashOnStartup())
			splashTB.setChecked(_appPrefs.showSplashOnStartup());
		if (randomJokeTB.isChecked() != _appPrefs.showRandomJoke())
			randomJokeTB.setChecked(_appPrefs.showRandomJoke());
		if (ellipsizeTB.isChecked() != _appPrefs.showEllipsizedJokes())
			ellipsizeTB.setChecked(_appPrefs.showEllipsizedJokes());
		if (cat1TB.isChecked() != _appPrefs.showCategory1())
			cat1TB.setChecked(_appPrefs.showCategory1());
		if (cat2TB.isChecked() != _appPrefs.showCategory2())
			cat2TB.setChecked(_appPrefs.showCategory2());
		if (cat3TB.isChecked() != _appPrefs.showCategory3())
			cat3TB.setChecked(_appPrefs.showCategory3());
		
		changesWereMade = false;

	}

	private void debugLogger() {
		Log.d("SharedPreferences", "debugLogger()");
		Log.d("Splash", "" + _appPrefs.showSplashOnStartup());
		Log.d("Randomized", "" + _appPrefs.showRandomJoke());
		Log.d("Ellipsized", "" + _appPrefs.showEllipsizedJokes());
		Log.d("Cat1", "" + _appPrefs.showCategory1());
		Log.d("Cat2", "" + _appPrefs.showCategory2());
		Log.d("Cat3", "" + _appPrefs.showCategory3());
	}
	
	

}
