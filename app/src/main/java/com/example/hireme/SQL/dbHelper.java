package com.example.hireme.SQL;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.hireme.model.User;

import java.util.ArrayList;
import java.util.List;

public class dbHelper extends SQLiteOpenHelper {
private static final int DATABASE_VERSION = 1;
// Database Name
private static final String DATABASE_NAME = "UserManager.db";
// User table name
private static final String TABLE_USER = "user";
// User Table Columns names
private static final String COLUMN_USER_ID = "user_id";
private static final String COLUMN_USER_FIRST_NAME = "user_first_name";
    private static final String COLUMN_USER_LAST_NAME = "user_last_name";
private static final String COLUMN_USER_EMAIL = "user_email";
private static final String COLUMN_USER_PASSWORD = "user_password";
private static final String COLUMN_USER_Mobile = "user_mobile";

// create table sql query
private String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + "("
        + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_USER_FIRST_NAME + " TEXT,"
        + COLUMN_USER_LAST_NAME + " TEXT," + COLUMN_USER_Mobile + " TEXT,"
        + COLUMN_USER_EMAIL + " TEXT," + COLUMN_USER_PASSWORD + " TEXT" + ")";
// create movie table sql query

// drop user table sql query
private String DROP_USER_TABLE = "DROP TABLE IF EXISTS " + TABLE_USER;
/**
 * Constructor
 *
 * @param context
 */
public dbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }
@Override
public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER_TABLE);

        }
@Override
public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Drop User Table if exist
        db.execSQL(DROP_USER_TABLE);
        // Create tables again
        onCreate(db);
        }
/**
 * This method is to create user record
 *
 * @param user
 */
public void addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_FIRST_NAME, user.getFirstName());
        values.put(COLUMN_USER_LAST_NAME, user.getLastName());
        values.put(COLUMN_USER_EMAIL, user.getEmail());
        values.put(COLUMN_USER_PASSWORD, user.getPassword());
        values.put(COLUMN_USER_Mobile, user.getMobileNo());
        // Inserting Row
        db.insert(TABLE_USER, null, values);
        db.close();
        }
/**
 * This method is to create user record
 *
 * @param movie
 */

/**
 * This method is to fetch all user and return the list of user records
 *
 * @return list
 */
public List<User> getAllUser() {
        // array of columns to fetch
        String[] columns = {
        COLUMN_USER_ID,
        COLUMN_USER_EMAIL,
        COLUMN_USER_FIRST_NAME,
        COLUMN_USER_LAST_NAME,
        COLUMN_USER_PASSWORD,
                COLUMN_USER_Mobile
        };
        // sorting orders
        String sortOrder =
        COLUMN_USER_FIRST_NAME + " ASC";
        List<User> userList = new ArrayList<User>();
        SQLiteDatabase db = this.getReadableDatabase();
        // query the user table
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id,user_name,user_email,user_password FROM user ORDER BY user_name;
         */
        Cursor cursor = db.query(TABLE_USER, //Table to query
        columns,    //columns to return
        null,        //columns for the WHERE clause
        null,        //The values for the WHERE clause
        null,       //group the rows
        null,       //filter by row groups
        sortOrder); //The sort order
        // Traversing through all rows and adding to list
        if (cursor.moveToFirst()) {
        do {
        User user = new User();
        user.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_USER_ID))));
        user.setFirstName(cursor.getString(cursor.getColumnIndex(COLUMN_USER_FIRST_NAME)));
            user.setLasstName(cursor.getString(cursor.getColumnIndex(COLUMN_USER_LAST_NAME)));
        user.setEmail(cursor.getString(cursor.getColumnIndex(COLUMN_USER_EMAIL)));
        user.setPassword(cursor.getString(cursor.getColumnIndex(COLUMN_USER_PASSWORD)));
            user.setMobileNo(cursor.getString(cursor.getColumnIndex(COLUMN_USER_Mobile)));
        // Adding user record to list
        userList.add(user);
        } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        // return user list
        return userList;
        }

/**
 * This method to update user record
 *
 * @param user
 */
public void updateUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_FIRST_NAME, user.getFirstName());
    values.put(COLUMN_USER_LAST_NAME, user.getLastName());
        values.put(COLUMN_USER_EMAIL, user.getEmail());
        values.put(COLUMN_USER_PASSWORD, user.getPassword());
    values.put(COLUMN_USER_Mobile, user.getMobileNo());

        // updating row
        db.update(TABLE_USER, values, COLUMN_USER_ID + " = ?",
        new String[]{String.valueOf(user.getId())});
        db.close();
        }

/**
 * This method is to delete user record
 *
 * @param user
 */
public void deleteUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        // delete user record by id
        db.delete(TABLE_USER, COLUMN_USER_ID + " = ?",
        new String[]{String.valueOf(user.getId())});
        db.close();
        }

/**
 * This method to check user exist or not
 *
 * @param email
 * @return true/false
 */
public boolean checkUser(String email) {
        // array of columns to fetch
        String[] columns = {
        COLUMN_USER_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();
        // selection criteria
        String selection = COLUMN_USER_EMAIL + " = ?";
        // selection argument
        String[] selectionArgs = {email};
        // query user table with condition
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id FROM user WHERE user_email = 'jack@androidtutorialshub.com';
         */
        Cursor cursor = db.query(TABLE_USER, //Table to query
        columns,                    //columns to return
        selection,                  //columns for the WHERE clause
        selectionArgs,              //The values for the WHERE clause
        null,                       //group the rows
        null,                      //filter by row groups
        null);                      //The sort order
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();
        if (cursorCount > 0) {
        return true;
        }
        return false;
        }
/**
 * This method to check user exist or not
 *
 * @param email
 * @param password
 * @return true/false
 */
public boolean checkUser(String email, String password) {
        // array of columns to fetch
        String[] columns = {
        COLUMN_USER_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();

        String selection = COLUMN_USER_EMAIL + " = ?" + " AND " + COLUMN_USER_PASSWORD + " = ?";
        // selection arguments
        String[] selectionArgs = {email, password};

        Cursor cursor = db.query(TABLE_USER, //Table to query
        columns,                    //columns to return
        selection,                  //columns for the WHERE clause
        selectionArgs,              //The values for the WHERE clause
        null,                       //group the rows
        null,                       //filter by row groups
        null);                      //The sort order
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();
        if (cursorCount > 0) {
        return true;
        }
        return false;
        }
}
