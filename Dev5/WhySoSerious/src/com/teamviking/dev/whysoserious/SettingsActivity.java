package com.teamviking.dev.whysoserious;

import android.os.Bundle;
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
 *  Phase 1 This is where you set the settings. 
 * 
 * @author Christopher Reid 0934402
 * @author Alessandro Rodi 1134337
 * @author Sandro Victoria-Arena 1036757
 * 
 */
public class SettingsActivity extends Activity {

	private AppPreferences _appPrefs;
	private Button updateBtn, resetBtn;
	private ToggleButton splash, randomJoke, ellipsize, geek, holiday, kids,
			horror, school;
	private boolean changesWereMade;

	/**
	 * onCreate Method
	 * Creates Settings activity
	 * 
	 * @param Bundle savedInstanceState
	 * @return void
	 * @throws
	 * 
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);

		_appPrefs = new AppPreferences(getApplicationContext());

		loadStuff();
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
	 * loadStuff Method
	 * Gets a handle to all the Buttons inside Settings Activity 
	 * and adds on click listeners to them.
	 * 
	 * @param
	 * @return void
	 * @throws
	 * 
	 */
	private void loadStuff() {
		// Instantiate buttons
		splash = (ToggleButton) findViewById(R.id.settings_splash_toggle);
		randomJoke = (ToggleButton) findViewById(R.id.settings_randomize_toggle);
		ellipsize = (ToggleButton) findViewById(R.id.settings_ellipsize_toggle);
		geek = (ToggleButton) findViewById(R.id.settings_geek_toggle);
		holiday = (ToggleButton) findViewById(R.id.settings_holiday_toggle);
		kids = (ToggleButton) findViewById(R.id.settings_kids_toggle);
		horror = (ToggleButton) findViewById(R.id.settings_horror_toggle);
		school = (ToggleButton) findViewById(R.id.settings_school_toggle);
		updateBtn = (Button) findViewById(R.id.settings_update_button);
		resetBtn = (Button) findViewById(R.id.settings_reset_button);

		// Use the shared preferences to change button values
		modifyToggleStates();

		// Add the action listeners to the toggle buttons
		splash.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {

				_appPrefs.setSplashOnStartup(isChecked);
				changesWereMade = true;
			}
		});

		randomJoke
				.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {

						_appPrefs.setRandomJoke(isChecked);
						changesWereMade = true;
					}
				});

		ellipsize
				.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {

						_appPrefs.setEllipsizedJokes(isChecked);
						changesWereMade = true;
					}
				});

		geek.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {

				_appPrefs.setGeekCategory(isChecked);
				changesWereMade = true;
			}
		});

		holiday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {

				_appPrefs.setHolidayCategory(isChecked);
				changesWereMade = true;
			}
		});

		kids.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {

				_appPrefs.setKidsCategory(isChecked);
				changesWereMade = true;
			}
		});

		horror.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {

				_appPrefs.setHorrorCategory(isChecked);
				changesWereMade = true;
			}
		});

		school.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {

				_appPrefs.setSchoolCategory(isChecked);
				changesWereMade = true;
			}
		});

		updateBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				if (changesWereMade) {
					if (_appPrefs.updatePreferences()) {
						modifyToggleStates();
						Toast toast = Toast.makeText(getApplicationContext(),
								"Settings saved.", Toast.LENGTH_SHORT);
						toast.show();
					}
				}
			}
		});

		resetBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				if (_appPrefs.resetPreferences()) {
					modifyToggleStates();
					Toast toast = Toast.makeText(getApplicationContext(),
							"Settings reset to default.", Toast.LENGTH_SHORT);
					toast.show();
				}
			}
		});

	}

	/**
	 * modifyToggleStates Method
	 * Ensures that the buttons correspond to the SharedPreferences values
	 * 
	 * @param
	 * @return void
	 * @throws
	 * 
	 */
	private void modifyToggleStates() {
		// 
		if (splash.isChecked() != _appPrefs.showSplashOnStartup())
			splash.setChecked(_appPrefs.showSplashOnStartup());
		
		if (randomJoke.isChecked() != _appPrefs.showRandomJoke())
			randomJoke.setChecked(_appPrefs.showRandomJoke());
		
		if (ellipsize.isChecked() != _appPrefs.showEllipsizedJokes())
			ellipsize.setChecked(_appPrefs.showEllipsizedJokes());
		
		if (geek.isChecked() != _appPrefs.showGeekCategory())
			geek.setChecked(_appPrefs.showGeekCategory());
		
		if (holiday.isChecked() != _appPrefs.showHolidayCategory())
			holiday.setChecked(_appPrefs.showHolidayCategory());
		
		if (kids.isChecked() != _appPrefs.showKidsCategory())
			kids.setChecked(_appPrefs.showKidsCategory());
		
		if (horror.isChecked() != _appPrefs.showHorrorCategory())
			horror.setChecked(_appPrefs.showHorrorCategory());
		
		if (school.isChecked() != _appPrefs.showSchoolCategory())
			school.setChecked(_appPrefs.showSchoolCategory());

		changesWereMade = false;
	}

}
