package com.teamviking.dev.whysoserious;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * AppPreferences class 
 * Phase 1 This class is used to set preferences
 * 
 * @author Christopher Reid 0934402
 * @author Alessandro Rodi 1134337
 * @author Sandro Victoria-Arena 1036757
 * 
 */
public class AppPreferences {

	private static final String APP_SHARED_PREFS = AppPreferences.class
			.getSimpleName();
	private static final String KEY_PREFS_SPLASH_SCREEN = "splash_screen";
	private static final String KEY_PREFS_RANDOM_JOKE = "random_joke";
	private static final String KEY_PREFS_ELLIPSIZE_JOKES = "ellipsize_jokes";
	private static final String KEY_PREFS_SHOW_GEEK = "show_geek";
	private static final String KEY_PREFS_SHOW_HOLIDAY = "show_holiday";
	private static final String KEY_PREFS_SHOW_KIDS = "show_kids";
	private static final String KEY_PREFS_SHOW_HORROR = "show_horror";
	private static final String KEY_PREFS_SHOW_SCHOOL = "show_school";
	private SharedPreferences _sharedPrefs;
	private Editor _prefsEditor;

	/**
	 * AppPreferences Method
	 * Creates the shared prefs
	 * 
	 * @param Context context
	 * @return 
	 * @throws
	 * 
	 */
	public AppPreferences(Context context) {
		this._sharedPrefs = context.getSharedPreferences(APP_SHARED_PREFS,
				Activity.MODE_PRIVATE);
		this._prefsEditor = _sharedPrefs.edit();
	}

	/**
	 * showSplashOnStartup Methods
	 * Show splash screen on start up
	 * 
	 * @param 
	 * @return boolean
	 * @throws
	 * 
	 */
	public boolean showSplashOnStartup() {
		return _sharedPrefs.getBoolean(KEY_PREFS_SPLASH_SCREEN, true);
	}

	/**
	 * setSplashOnStartup Methods
	 * Set if its going to show splash screen on start up or not
	 * 
	 * @param Boolean value
	 * @return void
	 * @throws
	 * 
	 */
	public void setSplashOnStartup(Boolean value) {
		_prefsEditor.putBoolean(KEY_PREFS_SPLASH_SCREEN, value);
	}

	/**
	 * showRandomJoke Methods
	 * Shows a random joke on start up
	 * 
	 * @param 
	 * @return boolean
	 * @throws
	 * 
	 */
	public Boolean showRandomJoke() {
		return _sharedPrefs.getBoolean(KEY_PREFS_RANDOM_JOKE, false);
	}

	/**
	 * setRandomJoke Methods
	 * Set if its going to show a random joke on start up or not
	 * 
	 * @param Boolean value
	 * @return void
	 * @throws
	 * 
	 */
	public void setRandomJoke(Boolean value) {
		_prefsEditor.putBoolean(KEY_PREFS_RANDOM_JOKE, value);
	}

	/**
	 * showEllipsizedJokes Methods
	 * Shows ellipsized jokes
	 * 
	 * @param 
	 * @return boolean
	 * @throws
	 * 
	 */
	public Boolean showEllipsizedJokes() {
		return _sharedPrefs.getBoolean(KEY_PREFS_ELLIPSIZE_JOKES, false);
	}

	/**
	 * setEllipsizedJokes Methods
	 * Set if its going to show ellipsized jokes or not
	 * 
	 * @param Boolean value
	 * @return void
	 * @throws
	 * 
	 */
	public void setEllipsizedJokes(Boolean value) {
		_prefsEditor.putBoolean(KEY_PREFS_ELLIPSIZE_JOKES, value);
	}

	/**
	 * showGeekCategory Methods
	 * Shows Geek jokes
	 * 
	 * @param 
	 * @return boolean
	 * @throws
	 * 
	 */
	public Boolean showGeekCategory() {
		return _sharedPrefs.getBoolean(KEY_PREFS_SHOW_GEEK, true);
	}

	/**
	 * setGeekCategory Methods
	 * Set if its going to show Geek jokes or not
	 * 
	 * @param Boolean value
	 * @return void
	 * @throws
	 * 
	 */
	public void setGeekCategory(Boolean value) {
		_prefsEditor.putBoolean(KEY_PREFS_SHOW_GEEK, value);
	}

	/**
	 * showHolidayCategory Methods
	 * Shows Holiday jokes
	 * 
	 * @param 
	 * @return boolean
	 * @throws
	 * 
	 */
	public Boolean showHolidayCategory() {
		return _sharedPrefs.getBoolean(KEY_PREFS_SHOW_HOLIDAY, true);
	}

	/**
	 * setHolidayCategory Methods
	 * Set if its going to show Holiday jokes or not
	 * 
	 * @param Boolean value
	 * @return void
	 * @throws
	 * 
	 */
	public void setHolidayCategory(Boolean value) {
		_prefsEditor.putBoolean(KEY_PREFS_SHOW_HOLIDAY, value);
	}

	/**
	 * showKidsCategory Methods
	 * Shows Kids jokes
	 * 
	 * @param 
	 * @return boolean
	 * @throws
	 * 
	 */
	public Boolean showKidsCategory() {
		return _sharedPrefs.getBoolean(KEY_PREFS_SHOW_KIDS, true);
	}

	/**
	 * setKidsCategory Methods
	 * Set if its going to show Kids jokes or not
	 * 
	 * @param Boolean value
	 * @return void
	 * @throws
	 * 
	 */
	public void setKidsCategory(Boolean value) {
		_prefsEditor.putBoolean(KEY_PREFS_SHOW_KIDS, value);
	}

	/**
	 * showHorrorCategory Methods
	 * Shows Horror jokes
	 * 
	 * @param 
	 * @return boolean
	 * @throws
	 * 
	 */
	public Boolean showHorrorCategory() {
		return _sharedPrefs.getBoolean(KEY_PREFS_SHOW_HORROR, true);
	}

	/**
	 * setHorrorCategory Methods
	 * Set if its going to show Horror jokes or not
	 * 
	 * @param Boolean value
	 * @return void
	 * @throws
	 * 
	 */
	public void setHorrorCategory(Boolean value) {
		_prefsEditor.putBoolean(KEY_PREFS_SHOW_HORROR, value);
	}

	/**
	 * showSchoolCategory Methods
	 * Shows School jokes
	 * 
	 * @param 
	 * @return boolean
	 * @throws
	 * 
	 */
	public Boolean showSchoolCategory() {
		return _sharedPrefs.getBoolean(KEY_PREFS_SHOW_SCHOOL, true);
	}

	/**
	 * setSchoolCategory Methods
	 * Set if its going to show School jokes or not
	 * 
	 * @param Boolean value
	 * @return void
	 * @throws
	 * 
	 */
	public void setSchoolCategory(Boolean value) {
		_prefsEditor.putBoolean(KEY_PREFS_SHOW_SCHOOL, value);
	}

	
	/**
	 * updatePreferences Methods
	 * Updates the preferences
	 * 
	 * @param 
	 * @return boolean
	 * @throws
	 * 
	 */
	public boolean updatePreferences() {
		return _prefsEditor.commit();
	}

	/**
	 * resetPreferences Methods
	 * Resets the preferences to their default values
	 * 
	 * @param 
	 * @return boolean
	 * @throws
	 * 
	 */
	public boolean resetPreferences() {

		// Default values
		setSplashOnStartup(true);
		setRandomJoke(false);
		setEllipsizedJokes(false);
		setGeekCategory(true);
		setHolidayCategory(true);
		setKidsCategory(true);
		setHorrorCategory(true);
		setSchoolCategory(true);

		return _prefsEditor.commit();
	}

}
