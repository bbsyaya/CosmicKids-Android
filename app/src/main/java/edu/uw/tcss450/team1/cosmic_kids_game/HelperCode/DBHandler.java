/**
 * @Class DBHandler
 * @Version 1.0.0
 * @Author Justin Burch
 * @Author Brandon Chambers
 *
 * This class provides a convenient way to handle calls to the SQLiteDatabase that is used to store
 * the words used in the Spelling Bee.
 */
package edu.uw.tcss450.team1.cosmic_kids_game.HelperCode;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DBHandler {

    private DatabaseHelper dbHelper;
    private Context context;
    private SQLiteDatabase sqliteDB;

    /**
     * constructor
     * @param c
     */
    public DBHandler(Context c) {
        context = c;
    }

    /**
     *open the SQLiteDatabase object
     *
     * @return
     * @throws SQLException
     */
    public DBHandler open() throws SQLException {
        dbHelper = new DatabaseHelper(context);
        sqliteDB = dbHelper.getWritableDatabase();
        return this;
    }

    /**
     * add words / grades to table by insert with ContentValues
     *
     * @param word
     * @param grade
     */
    public void insert(String word, int grade) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.COL_WORD, word);
        contentValues.put(DatabaseHelper.COL_GRADE, grade);
        sqliteDB.insert(DatabaseHelper.TABLE_NAME, null, contentValues);
    }

    /**
     * Use the Cursor class to iterate through our DB tables, returning a cursor
     * for further iteration
     *
     * @return
     */
    public Cursor fetch() {
        String[] columns = new String[]
                {DatabaseHelper.KEY_ID, DatabaseHelper.COL_WORD, DatabaseHelper.COL_GRADE};
        Cursor cursor =
                sqliteDB.query(DatabaseHelper.TABLE_NAME, columns, null, null, null, null, null);

        if(cursor != null) {
            cursor.moveToFirst();
        }

        return cursor;
    }

    public Cursor fetch(int startGrade, int endGrade) {
        String sb = "SELECT " +
                DatabaseHelper.COL_WORD +
                ", " +
                DatabaseHelper.COL_GRADE +
                " FROM " +
                DatabaseHelper.TABLE_NAME +
                " WHERE " +
                DatabaseHelper.COL_GRADE +
                " BETWEEN ? AND ?;";
        Cursor cursor = sqliteDB.rawQuery(
                sb,
                new String[] {
                        Integer.toString(startGrade),
                        Integer.toString(endGrade)
                }
        );
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    /**
     * Edit records by changing a row/tuple
     * @param id
     * @param word
     * @param grade
     * @return
     */
    public int update(long id, String word, int grade) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.COL_WORD, word);
        contentValues.put(DatabaseHelper.COL_GRADE, grade);

        int i = sqliteDB.update(
                DatabaseHelper.TABLE_NAME,
                contentValues,
                DatabaseHelper.KEY_ID + " = " + id,
                null);

        return i;
    }

    /**
     * Delete specific row via its ID primary key
     * @param id
     */
    public void deleteByID(long id) {
        sqliteDB.delete(DatabaseHelper.TABLE_NAME, DatabaseHelper.KEY_ID + "=" + id, null);
    }

    /**
     * Delete entire Table and return a count of rows deleted
     * @return
     *          count of rows removed
     */
    public int deleteAllRows() {
        return sqliteDB.delete(DatabaseHelper.TABLE_NAME, "1", null);

    }


    /**
     * Delete the entire database
     */
    public void deleteDatabase(){
        context.deleteDatabase(DatabaseHelper.DB_NAME);
    }

}
