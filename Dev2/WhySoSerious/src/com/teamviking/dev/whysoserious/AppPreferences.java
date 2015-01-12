package com.teamviking.dev.whysoserious;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class AppPreferences {

	private static final String APP_SHARED_PREFS = AppPreferences.class.getSimpleName();
	private static final String KEY_PREFS_SPLASH_SCREEN = "splash_screen";
	private static final String KEY_PREFS_RANDOM_JOKE = "random_joke";
	private static final String KEY_PREFS_ELLIPSIZE_JOKES = "ellipsize_jokes";
	private static final String KEY_PREFS_SHOW_CATEGORY1 = "show_category1";
	private static final String KEY_PREFS_SHOW_CATEGORY2 = "show_category2";
	private static final String KEY_PREFS_SHOW_CATEGORY3 = "show_category3";
	private SharedPreferences _sharedPrefs;
	private Editor _prefsEditor;
	
	public AppPreferences(Context context) {
		this._sharedPrefs = context.getSharedPreferences(APP_SHARED_PREFS, Activity.MODE_PRIVATE);
		this._prefsEditor = _sharedPrefs.edit();
	}
	
	public Boolean showSplashOnStartup() {
		return _sharedPrefs.getBoolean(KEY_PREFS_SPLASH_SCREEN, true);
	}
	
	public void setSplashOnStartup(Boolean value) {
		_prefsEditor.putBoolean(KEY_PREFS_SPLASH_SCREEN, value);
	}
	
	public Boolean showRandomJoke() {
		return _sharedPrefs.getBoolean(KEY_PREFS_RANDOM_JOKE, false);
	}
	
	public void setRandomJoke(Boolean value)  {
		_prefsEditor.putBoolean(KEY_PREFS_RANDOM_JOKE, value);
	}
	
	public Boolean showEllipsizedJokes() {
		return _sharedPrefs.getBoolean(KEY_PREFS_ELLIPSIZE_JOKES, false);
	}
	
	public void setEllipsizedJokes(Boolean value)  {
		_prefsEditor.putBoolean(KEY_PREFS_ELLIPSIZE_JOKES, value);
	}
	
	public Boolean showCategory1() {
		return _sharedPrefs.getBoolean(KEY_PREFS_SHOW_CATEGORY1, true);
	}
	
	public void setCategory1(Boolean value)  {
		_prefsEditor.putBoolean(KEY_PREFS_SHOW_CATEGORY1, value);
	}
	
	public Boolean showCategory2() {
		return _sharedPrefs.getBoolean(KEY_PREFS_SHOW_CATEGORY2, true);
	}
	
	public void setCategory2(Boolean value)  {
		_prefsEditor.putBoolean(KEY_PREFS_SHOW_CATEGORY2, value);
	}
	
	public Boolean showCategory3() {
		return _sharedPrefs.getBoolean(KEY_PREFS_SHOW_CATEGORY3, true);
	}
	
	public void setCategory3(Boolean value)  {
		_prefsEditor.putBoolean(KEY_PREFS_SHOW_CATEGORY3, value);
	}
	
	public boolean updatePreferences() {
		return _prefsEditor.commit();
	}
	
	public boolean resetPreferences() {
		
		// Default values
		setSplashOnStartup(true);
		setRandomJoke(false);
		setEllipsizedJokes(false);
		setCategory1(true);
		setCategory2(true);
		setCategory3(true);
		
		return _prefsEditor.commit();
	}

}
