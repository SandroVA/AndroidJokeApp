package com.teamviking.dev.whysoserious;

import java.sql.Timestamp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * DBHelper class Phase 1 This class is used the create / modify the DB
 * 
 * @author Christopher Reid 0934402
 * @author Alessandro Rodi 1134337
 * @author Sandro Victoria-Arena 1036757
 * 
 */
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

	/**
	 * onCreate Method Creates Joke and Category Table.
	 * 
	 * @param SQLiteDatabase
	 *            database
	 * @return void
	 * @throws
	 * 
	 */
	@Override
	public void onCreate(SQLiteDatabase database) {
		database.execSQL(DATABASE_CREATEJOKETABLE);
		database.execSQL(DATABASE_CREATECATEGORYTABLE);
	}

	/**
	 * onUpgrade Method Upgrades DB version
	 * 
	 * @param SQLiteDatabase
	 *            db
	 * @param int oldVersion
	 * @param int newVersion
	 * @return void
	 * @throws
	 * 
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(DBHelper.class.getName(), "Upgrading database from version "
				+ oldVersion + " to " + newVersion
				+ ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_JOKE);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORY);
		onCreate(db);

	}

	/**
	 * insertNewJoke Method Inserts a new joke into the Db
	 * 
	 * @param String
	 *            category
	 * @param String
	 *            subCategory
	 * @param int jokeTypeCode
	 * @param String
	 *            jokeShortDescription
	 * @param String
	 *            questionText
	 * @param String
	 *            answerText
	 * @param String
	 *            monologueText
	 * @param int ratingScale
	 * @param String
	 *            comments
	 * @param String
	 *            jokeSource
	 * @param boolean realeaseStatus
	 * @return long
	 * @throws
	 * 
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

	/**
	 * updateJoke Method Updates a joke in the Db
	 * 
	 * @param String
	 *            category
	 * @param String
	 *            subCategory
	 * @param int jokeTypeCode
	 * @param String
	 *            jokeShortDescription
	 * @param String
	 *            questionText
	 * @param String
	 *            answerText
	 * @param String
	 *            monologueText
	 * @param int ratingScale
	 * @param String
	 *            comments
	 * @param String
	 *            jokeSource
	 * @param boolean realeaseStatus
	 * @return int
	 * @throws
	 * 
	 */
	public int updateJoke(int id, String category, String subCategory,
			int jokeTypeCode, String jokeShortDescription, String questionText,
			String answerText, String monologueText, int ratingScale,
			String comments, String jokeSource, boolean realeaseStatus) {

		String where = COLUMN_ID + "=" + id;

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
		int nbOfRows = getWritableDatabase()
				.update(TABLE_JOKE, cv, where, null);

		return nbOfRows;
	}

	/**
	 * getAllJokes Method C*R*UD Retrieve returns a Cursor of *all* database
	 * records
	 * 
	 * @param
	 * @return Cursor
	 * @throws
	 * 
	 */
	public Cursor getAllJokes() {
		return getWritableDatabase().query(TABLE_JOKE, null, null, null, null,
				null, null);
	}

	/**
	 * getJokeById Method returns a Cursor of database record that matches the
	 * id
	 * 
	 * @param int id
	 * @return Cursor
	 * @throws
	 * 
	 */
	public Cursor getJokeById(int id) {
		return getWritableDatabase().query(TABLE_JOKE, new String[] { "*" },
				COLUMN_ID + " = ?", new String[] { Integer.toString(id) },
				null, null, null);
	}

	/**
	 * getRandomJoke Method returns a cursor of a random joke in the database
	 * 
	 * @param String
	 *            [] categories
	 * @return Cursor
	 * @throws
	 * 
	 */
	public Cursor getRandomJoke(String[] categories) {

		if (categories.length > 0) {

			String where = "";
			for (String s : categories) {
				where += COLUMN_CATEGORY + "=? OR ";
			}
			where = where.substring(0, where.length() - 4);

			return getWritableDatabase().query(TABLE_JOKE, null, where,
					categories, null, null, "RANDOM()", "1");
		} else {
			// No categories in selection
			return null;
		}
	}

	/**
	 * getFiveRandomJokes Method returns a cursor of five random jokes in the
	 * database
	 * 
	 * @param String
	 *            [] categories
	 * @return Cursor
	 * @throws
	 * 
	 */
	public Cursor getFiveRandomJokes(String[] categories) {

		if (categories.length > 0) {

			String where = "";
			for (String s : categories) {
				where += COLUMN_CATEGORY + "=? OR ";
			}
			where = where.substring(0, where.length() - 4);

			return getWritableDatabase().query(TABLE_JOKE, null, where,
					categories, null, null, "RANDOM()", "5");
		} else {
			// No categories in selection
			return null;
		}
	}

	/**
	 * getFirstJokeDate Method Returns the date of the first joke to be inserted
	 * into the database
	 * 
	 * @param
	 * @return String
	 * @throws
	 * 
	 */
	public String getFirstJokeDate() {
		Cursor cursor = getReadableDatabase().rawQuery(
				"SELECT createDate FROM jokeTable ORDER BY ROWID ASC LIMIT 1",
				null);
		cursor.moveToFirst();
		String firstJokeDate = cursor.getString(cursor
				.getColumnIndex(DBHelper.COLUMN_CREATEDATE));
		cursor.close();
		return firstJokeDate;
	}

	/**
	 * getLastJokeDate Method Returns the date of the last joke to be inserted
	 * into the database
	 * 
	 * @param
	 * @return String
	 * @throws
	 * 
	 */
	public String getLastJokeDate() {
		Cursor cursor = getReadableDatabase().rawQuery(
				"SELECT createDate FROM jokeTable ORDER BY ROWID DESC LIMIT 1",
				null);
		cursor.moveToFirst();
		String lastJokeDate = cursor.getString(cursor
				.getColumnIndex(DBHelper.COLUMN_CREATEDATE));
		cursor.close();

		return lastJokeDate;
	}

	/**
	 * getNumberOfCategoryJokesInDatabase Method Returns how many jokes are in
	 * the category
	 * 
	 * @param String
	 *            category
	 * @return Integer
	 * @throws
	 * 
	 */
	public Integer getNumberOfCategoryJokesInDatabase(String category) {
		Cursor mCount = getReadableDatabase().rawQuery(
				"select count(*) from jokeTable where category = '" + category
						+ "'", null);
		mCount.moveToFirst();
		Integer count = mCount.getInt(0);
		mCount.close();

		if (count != null)
			return count;
		else
			return 0;
	}

	/**
	 * getNumberOfJokesInDatabase Method Returns how many jokes are in the
	 * database
	 * 
	 * @param
	 * @return Integer
	 * @throws
	 * 
	 */
	public Integer getNumberOfJokesInDatabase() {
		Cursor mCount = getReadableDatabase().rawQuery(
				"select count(*) from jokeTable", null);
		mCount.moveToFirst();
		Integer count = mCount.getInt(0);
		mCount.close();

		if (count != null)
			return count;
		else
			return 0;
	}

	/**
	 * getCategories Method C*R*UD Retrieve returns a Cursor of *all* database
	 * records
	 * 
	 * @param
	 * @return Cursor
	 * @throws
	 * 
	 */
	public Cursor getCategories() {
		return getWritableDatabase().query(TABLE_CATEGORY, null, null, null,
				null, null, null);
	}

	/**
	 * deleteJoke Method CRU*D* Delete Deletes a record based on its ID
	 * 
	 * @param int id
	 * @return int
	 * @throws
	 * 
	 */
	public int deleteJoke(int id) {
		return getWritableDatabase().delete(TABLE_JOKE, COLUMN_ID + "=?",
				new String[] { String.valueOf(id) });
	}

	/**
	 * deleteCategory Method CRU*D* Delete Deletes a category based on its ID
	 * 
	 * @param int id
	 * @return int
	 * @throws
	 * 
	 */
	public int deleteCategory(int id) {
		return getWritableDatabase().delete(TABLE_CATEGORY, COLUMN_ID + "=?",
				new String[] { String.valueOf(id) });
	}

	public Cursor searchForJokes(String joketype, String category,
			String subcategory, String description) {

		description = "%" + description + "%";
		String[] params = { joketype, category, subcategory, description };
		String where = "";

		where += COLUMN_JOKETYPECODE + "=? AND ";
		where += COLUMN_CATEGORY + "=? AND ";
		where += COLUMN_SUBCATEGORY + "=? and ";
		where += COLUMN_JOKESHORTDESCRIPTION + " LIKE ? ";

		// return result
		return getWritableDatabase().query(TABLE_JOKE, null, where, params,
				null, null, null);

	}

}
