package com.example.neven.fragmentsv2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.ContactsContract;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Neven on 7.5.2016..
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "contactsdb";
    public static final int DATABASE_VERSION = 1;

    public static final String TABLE_CONTACTS = "contacts";
    public static final String COLUMN_ROWID = "_id";
    public static final String COLUMN_FIRST_NAME = "firstname";
    public static final String COLUMN_LAST_NAME = "lastname";
    public static final String COLUMN_PHONE_NUMBER = "phonenumber";
    public static final String COLUMN_EMAIL = "email";


    public DatabaseHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String query = "CREATE TABLE contacts " +
                "(_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "firstname TEXT, " +
                "lastname TEXT, " +
                "phonenumber TEXT, " +
                "email TEXT)";

        db.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);

        onCreate(db);

    }

    public void insertContact(ContactsObjects contact) {

        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_FIRST_NAME, contact.getFirstName());
        cv.put(COLUMN_LAST_NAME, contact.getLastName());
        cv.put(COLUMN_PHONE_NUMBER, contact.getPhoneNumber());
        cv.put(COLUMN_EMAIL, contact.getEmail());
        db.insert(TABLE_CONTACTS, null, cv);
        db.close();


    }

    public ContactsObjects getContact(long id) {

        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_CONTACTS;
        String columns[] = {COLUMN_ROWID, COLUMN_FIRST_NAME, COLUMN_LAST_NAME, COLUMN_PHONE_NUMBER, COLUMN_EMAIL};
        String selectionArgs[] = {String.valueOf(id)};
        Cursor cursor = db.query(TABLE_CONTACTS, columns, COLUMN_ROWID + " = ? ", selectionArgs, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        ContactsObjects contact = new ContactsObjects(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4));

        return contact;


    }


    public List<ContactsObjects> getAllContacts() {
        List<ContactsObjects> contactList = new ArrayList<ContactsObjects>();
        // Select All Query
        String selectQuery = "SELECT  * FROM contacts ORDER BY firstname ASC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                ContactsObjects contact = new ContactsObjects();
                contact.set_id(Integer.parseInt(cursor.getString(cursor.getColumnIndex("_id"))));
                contact.setFirstName(cursor.getString(cursor.getColumnIndex("firstname")));
                contact.setLastName(cursor.getString(cursor.getColumnIndex("lastname")));
                contact.setPhoneNumber(cursor.getString(cursor.getColumnIndex("phonenumber")));
                contact.setEmail(cursor.getString(cursor.getColumnIndex("email")));
                // Adding contact to list
                contactList.add(contact);
            } while (cursor.moveToNext());
        }

        // return contact list
        return contactList;
    }


    public void deleteContact(ContactsObjects contact) {

        SQLiteDatabase db = getWritableDatabase();
        String[] args = {String.valueOf(contact.get_id())};
        db.delete(TABLE_CONTACTS, COLUMN_ROWID + " =? ", args);
        db.close();


    }

    public int updateContact(ContactsObjects contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_FIRST_NAME, contact.getFirstName());
        values.put(COLUMN_LAST_NAME, contact.getLastName());
        values.put(COLUMN_PHONE_NUMBER, contact.getPhoneNumber());
        values.put(COLUMN_EMAIL, contact.getEmail());

        return db.update(TABLE_CONTACTS, values, COLUMN_ROWID + " = ?",
                new String[]{String.valueOf(contact.get_id())});
    }



}
