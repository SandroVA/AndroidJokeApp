package com.teamviking.dev.whysoserious;

import java.sql.Timestamp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {

	public static final String TABLE_JOKE = "jokeTable";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_CATEGORY = "category";
	public static final String COLUMN_SUBCATEGORY = "subcategory";
	public static final String COLUMN_JOKETYPECODE = "jokeTypeCode";
	public static final String COLUMN_JOKESHORTDESCRIPTION = "jokeShortDescription";
	public static final String COLUMN_QUESTIONTEXT = "questionText";
	public static final String COLUMN_ANSWERTEXT = "answerText";
	public static final String COLUMN_MONOLOGUETEXT = "monologueText";
	public static final String COLUMN_RATINGSCALE = "ratingScale";
	public static final String COLUMN_COMMENTS = "comments";
	public static final String COLUMN_JOKESOURCE = "jokeSource";
	public static final String COLUMN_RELEASESTATUS = "releaseStatus";
	public static final String COLUMN_CREATEDATE = "createDate";
	public static final String COLUMN_MODIFYDATE = "modifyDate";
	public static final String TABLE_CATEGORY = "categoryTable";
	public static final String CATEGORYCOLUMN_ID = "_id";
	public static final String CATEGORYCOLUMN_CATEGORY = "category";
	public static final String CATEGORYCOLUMN_SUBCATEGORY = "subcategory";
	public static final String CATEGORYCOLUMN_NUMBEROFJOKES = "numberOfJokes";

	private static final String DATABASE_NAME = "jokeList.db";
	// if the version number is increased the onUpdate() will be called
	private static final int DATABASE_VERSION = 1;

	// Database creation raw SQL statement
	private static final String DATABASE_CREATEJOKETABLE = "create table "
			+ TABLE_JOKE + "( " + COLUMN_ID
			+ " integer primary key autoincrement, " + COLUMN_CATEGORY
			+ " text not null, " + COLUMN_SUBCATEGORY + " text not null, "
			+ COLUMN_JOKETYPECODE + " integer not null, "
			+ COLUMN_JOKESHORTDESCRIPTION + " text not null,"
			+ COLUMN_QUESTIONTEXT + " text," + COLUMN_ANSWERTEXT + " text ,"
			+ COLUMN_MONOLOGUETEXT + " text," + COLUMN_RATINGSCALE
			+ " int2 not null," + COLUMN_COMMENTS + " text not null,"
			+ COLUMN_JOKESOURCE + " text not null," + COLUMN_RELEASESTATUS
			+ " tinyint not null," + COLUMN_CREATEDATE + " datetime, "
			+ COLUMN_MODIFYDATE + " datetime not null"

			+ ");";

	// Database creation raw SQL statement
	private static final String DATABASE_CREATECATEGORYTABLE = "create table "
			+ TABLE_CATEGORY + "( " + COLUMN_ID
			+ " integer primary key autoincrement, " + COLUMN_CATEGORY
			+ " text not null, " + COLUMN_SUBCATEGORY + " text not null, "
			+ COLUMN_JOKETYPECODE + " integer not null "

			+ ");";

	/**
	 * Constructor
	 * 
	 * super.SQLiteOpenHelper: public SQLiteOpenHelper (Context context, String
	 * name, SQLiteDatabase.CursorFactory factory, int version)
	 * "Create a helper object to create, open, and/or manage a database."
	 * Remember the database is not actually created or opened until one of
	 * getWritableDatabase() or getReadableDatabase() is called.
	 */
	public DBHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		database.execSQL(DATABASE_CREATEJOKETABLE);
		database.execSQL(DATABASE_CREATECATEGORYTABLE);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(DBHelper.class.getName(), "Upgrading database from version "
				+ oldVersion + " to " + newVersion
				+ ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_JOKE);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORY);
		onCreate(db);

	}


	/*
	 * insertNewJoke()
	 * 
	 * *C*RUD create
	 * 
	 * @param category
	 * @param subCategory
	 * @param jokeTypeCode
	 * @param jokeShortDescription
	 * @param questionText
	 * @param answerText
	 * @param monologueText
	 * @param ratingScale
	 * @param comments
	 * @param jokeSource
	 * @param realeaseStatus
	 * 
	 * @return the return code
	 */
	public long insertNewJoke(String category, String subCategory,
			int jokeTypeCode, String jokeShortDescription, String questionText,
			String answerText, String monologueText, int ratingScale,
			String comments, String jokeSource, boolean realeaseStatus) {
		ContentValues cv = new ContentValues();
		cv.put(COLUMN_CATEGORY, category);
		cv.put(COLUMN_SUBCATEGORY, subCategory);
		cv.put(COLUMN_JOKETYPECODE, jokeTypeCode);
		cv.put(COLUMN_JOKESHORTDESCRIPTION, jokeShortDescription);
		cv.put(COLUMN_QUESTIONTEXT, questionText);
		cv.put(COLUMN_ANSWERTEXT, answerText);
		cv.put(COLUMN_MONOLOGUETEXT, monologueText);
		cv.put(COLUMN_RATINGSCALE, ratingScale);
		cv.put(COLUMN_COMMENTS, comments);
		cv.put(COLUMN_JOKESOURCE, jokeSource);
		cv.put(COLUMN_RELEASESTATUS, realeaseStatus ? 1 : 0);

		java.util.Date date = new java.util.Date();
		Timestamp createDate = new Timestamp(date.getTime());
		cv.put(COLUMN_MODIFYDATE, createDate.getTime());
		cv.put(COLUMN_CREATEDATE, createDate.getTime());

		long code = getWritableDatabase().insert(TABLE_JOKE, null, cv);
		return code;
	}

	
	public int updateJoke(int id,String category, String subCategory, int jokeTypeCode, 
			String jokeShortDescription, String questionText, 
			String answerText, String monologueText, 
			int ratingScale, String comments, String jokeSource, 
			boolean realeaseStatus)
	{
		String strFilter = "_id=" + id;
		ContentValues cv = new ContentValues();
		cv.put(COLUMN_CATEGORY, category);
		cv.put(COLUMN_SUBCATEGORY, subCategory);
		cv.put(COLUMN_JOKETYPECODE, jokeTypeCode);
		cv.put(COLUMN_JOKESHORTDESCRIPTION, jokeShortDescription);
		cv.put(COLUMN_QUESTIONTEXT, questionText);
		cv.put(COLUMN_ANSWERTEXT, answerText);
		cv.put(COLUMN_MONOLOGUETEXT, monologueText);
		cv.put(COLUMN_RATINGSCALE, ratingScale);
		cv.put(COLUMN_COMMENTS, comments);
		cv.put(COLUMN_JOKESOURCE, jokeSource);
		cv.put(COLUMN_RELEASESTATUS, realeaseStatus? 1 : 0);
		java.util.Date date= new java.util.Date();
		Timestamp createDate = new Timestamp(date.getTime());
		cv.put(COLUMN_MODIFYDATE , createDate.getTime());
		int nbOfRows = getWritableDatabase().update("jokeTable", cv, strFilter, null);
		return nbOfRows;
	}
	
	/*
	 * getJokes()
	 * 
	 * C*R*UD Retrieve
	 * 
	 * returns a Cursor of *all* database records
	 */
	public Cursor getJokes() {
		return getWritableDatabase().query(TABLE_JOKE, null, null, null, null,
				null, null);
	}

	public Cursor getFiveRandomJokes() {
		return getWritableDatabase().query(TABLE_JOKE + " Order BY RANDOM() LIMIT 15",
                new String[] { "*" }, null, null, null, null, null);
	}
	
	public Cursor getRandomJoke() {
		return getWritableDatabase().query(TABLE_JOKE + " Order BY RANDOM() LIMIT 1",
                new String[] { "*" }, null, null, null, null, null);
	}
	
	/*
	 * getCategories()
	 * 
	 * C*R*UD Retrieve
	 * 
	 * returns a Cursor of *all* database records
	 */
	public Cursor getCategories() {
		return getWritableDatabase().query(TABLE_CATEGORY, null, null, null,
				null, null, null);
	}

	/*
	 * deleteJoke()
	 * 
	 * CRU*D* Delete
	 * 
	 * @param id database id field
	 */
	public int deleteJoke(int id) {
		return getWritableDatabase().delete(TABLE_JOKE, COLUMN_ID + "=?",
				new String[] { String.valueOf(id) });
	}

	/*
	 * deleteCategory()
	 * 
	 * CRU*D* Delete
	 * 
	 * @param id database id field
	 */
	public void deleteCategory(int id) {
		getWritableDatabase().delete(TABLE_CATEGORY, COLUMN_ID + "=?",
				new String[] { String.valueOf(id) });
	}

}
