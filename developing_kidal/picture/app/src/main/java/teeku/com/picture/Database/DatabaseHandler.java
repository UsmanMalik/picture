package teeku.com.picture.Database;

/**
 * Created by usman on 11/24/15.
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

//import com.usman.gcm.crowdapps.Movie;

import java.util.ArrayList;
import java.util.List;

import teeku.com.picture.Model.Item;
import teeku.com.picture.Model.ItemChild;

/**
 * Created by usman on 5/20/15.
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 11;

    // Database Name
    private static final String DATABASE_NAME = "Crowd19";

    // Contacts table name
    private static final String TABLE_CONTACTS = "contacts";

    // Create Table Category
    private static final String TABLE_CATEGORY = "category";

    // Create Table Box
    private static final String TABLE_BOX = "box";



    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String TITLE = "title";
    private static final String IMAGE = "image";

    // Category Table Columns names
    private static final String CATEGORY_KEY_ID = "id";
    private static final String CATEGORY_TITLE = "title";
    private static final String CATEGORY_DESCRIPTION = "description";
    private static final String CATEGORY_IMAGE = "image";


    // Box Table Columns names
    private static final String BOX_KEY_ID = "id";
    private static final String BOX_CATEGORY_ID = "category_id";
    private static final String BOX_TITLE = "title";
    private static final String BOX_DESCRIPTION = "description";
    private static final String BOX_IMAGE = "image";




    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {


        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + TITLE + " TEXT,"
                + IMAGE + " TEXT" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);


        String CREATE_BOX_TABLE = "CREATE TABLE " + TABLE_BOX + "("
                + BOX_KEY_ID + " INTEGER PRIMARY KEY," + BOX_CATEGORY_ID + " INTEGER,"
                + BOX_TITLE + " TEXT," + BOX_DESCRIPTION + " TEXT,"
                + BOX_IMAGE + " TEXT" + ")";
        db.execSQL(CREATE_BOX_TABLE);

        String CREATE_CATEGORY_TABLE = "CREATE TABLE " + TABLE_CATEGORY + "("
                + CATEGORY_KEY_ID + " INTEGER PRIMARY KEY,"
                + CATEGORY_TITLE + " TEXT," + CATEGORY_DESCRIPTION + " TEXT,"
                + CATEGORY_IMAGE + " TEXT" + ")";
        db.execSQL(CREATE_CATEGORY_TABLE);


    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BOX);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORY);

        // Create tables again
        onCreate(db);
    }

    /////////////// CRUD operations

    // Adding new contact


   /* public void addContact(Movie contact) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TITLE, contact.getTitle()); // Contact Name
        values.put(IMAGE, contact.getThumbnailUrl()); // Contact Phone Number

        // Inserting Row
        db.insert(TABLE_CONTACTS, null, values);
        db.close(); // Closing database connection
    }*/

    public void addCategory(Item category) {

        if(isCategoryAlreadyExists(category.getId()) == false) {
            SQLiteDatabase db = this.getWritableDatabase();

            ContentValues values = new ContentValues();

            values.put(CATEGORY_KEY_ID, category.getId());
            values.put(CATEGORY_TITLE, category.getTitle());
            values.put(CATEGORY_DESCRIPTION, category.getDescription());
            values.put(CATEGORY_IMAGE, category.getImage_path());

            // Inserting Row
            db.insert(TABLE_CATEGORY, null, values);
            db.close(); // Closing database connection
        }else{
            Log.e("Already exist category", category.getId()+"");
        }
    }

    public Boolean isCategoryAlreadyExists(Integer id){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_CATEGORY, new String[] { CATEGORY_KEY_ID, CATEGORY_TITLE,
                        CATEGORY_DESCRIPTION,CATEGORY_IMAGE}, CATEGORY_KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);

        if(cursor.moveToFirst()) {
            cursor.close();
            return true; //row exists
        }else{
            cursor.close();
            return false;
        }
    }

    public Boolean isBoxAlreadyExists(Integer id){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_BOX, new String[] { BOX_KEY_ID, BOX_CATEGORY_ID,
                        BOX_TITLE,BOX_DESCRIPTION,BOX_IMAGE }, BOX_CATEGORY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);

        if(cursor.moveToFirst()) {
            cursor.close();
            return true; //row exists
        }else{
            cursor.close();
            return false;
        }
    }

    public void addBox(ItemChild box) {

        if(isBoxAlreadyExists(box.getId()) == false) {

            SQLiteDatabase db = this.getWritableDatabase();

            ContentValues values = new ContentValues();

            values.put(BOX_KEY_ID, box.getId());
            values.put(BOX_CATEGORY_ID, box.getItem_id());
            values.put(BOX_TITLE, box.getTitle());
            values.put(BOX_DESCRIPTION, box.getDescription());
            values.put(BOX_IMAGE, box.getImage_path());

            // Inserting Row
            db.insert(TABLE_BOX, null, values);
            db.close(); // Closing database connection
        }else{
            Log.e("Already exist box", box.getId() + "");
        }
    }


    // Getting single contact
   /* public Movie getContact(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_CONTACTS, new String[] { KEY_ID,
                        TITLE, IMAGE }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Movie contact = new Movie(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2));
        cursor.close();
        // return contact
        return contact;
    }*/

    // Getting single contact
    public List<ItemChild> getCategoryBoxes(int id) {
        List<ItemChild> boxList = new ArrayList<ItemChild>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_BOX, new String[] { BOX_KEY_ID, BOX_CATEGORY_ID,
                        BOX_TITLE,BOX_DESCRIPTION,BOX_IMAGE }, BOX_CATEGORY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {

                ItemChild box = new ItemChild(Integer.parseInt(cursor.getString(0)),
                        Integer.parseInt(cursor.getString(1)), cursor.getString(2),cursor.getString(3),cursor.getString(4));


                boxList.add(box);
            } while (cursor.moveToNext());
            cursor.close(); // Cursor was not closed earlier
        }else{
            cursor.close();
        }




        // return contact
        return boxList;
    }

    // Getting All Contacts
   /* public List<Movie> getAllContacts() {
        List<Movie> contactList = new ArrayList<Movie>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Movie contact = new Movie();
                contact.setTitle(cursor.getString(1));
                contact.setThumbnailUrl(cursor.getString(2));
                // Adding contact to list
                contactList.add(contact);
            } while (cursor.moveToNext());
            cursor.close(); // Cursor was not closed earlier
        }else{
            cursor.close();
        }

        // return contact list
        return contactList;
    }*/


    // Getting All Categories
    public List<Item> getAllCategories() {
        List<Item> categoryList = new ArrayList<Item>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_CATEGORY;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Item category = new Item();
                category.setId(cursor.getInt(0));
                category.setTitle(cursor.getString(1));
                category.setDescription(cursor.getString(2));
                category.setImage_path(cursor.getString(3));
                // Adding contact to list
                categoryList.add(category);
            } while (cursor.moveToNext());
            cursor.close(); // Cursor was not closed earlier
        }else {
            cursor.close();
        }

        // return contact list
        return categoryList;
    }

    // Getting All Boxes

    public List<ItemChild> getAllBoxes() {
        List<ItemChild> boxList = new ArrayList<ItemChild>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_BOX;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                ItemChild box = new ItemChild();
                box.setId(cursor.getInt(0));
                Log.e("**** box id: ", cursor.getInt(0) + "");
                box.setItem_id(cursor.getInt(1));
                Log.e("**** box item id: ", cursor.getInt(1) + "");
                box.setTitle(cursor.getString(2));
                Log.e("**** box title: ", cursor.getString(2) + "");
                box.setDescription(cursor.getString(3));
                Log.e("**** box description: ", cursor.getString(3) + "");
                box.setImage_path(cursor.getString(4));
                Log.e("**** box Image Path: ", cursor.getString(4) + "");
                // Adding contact to list
                boxList.add(box);
            } while (cursor.moveToNext());
            cursor.close(); // Cursor was not closed earlier
        }else{
            cursor.close();
        }

        // return boxes list
        return boxList;
    }


    // Updating single contact
   /* public int updateContact(Movie contact) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(TITLE, contact.getTitle());
        values.put(IMAGE, contact.getThumbnailUrl());

        // updating row
        return db.update(TABLE_CONTACTS, values, KEY_ID + " = ?",
                new String[] { String.valueOf(-1) });
    }

    // Deleting single contact
    public void deleteContact(Movie contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CONTACTS, KEY_ID + " = ?",
                new String[]{String.valueOf(-1)});
        db.close();
    } */


    // Getting contacts Count
    public int getContactsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_CONTACTS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int totalContacts = cursor.getCount();
        cursor.close();
        db.close();
        // return count
        return totalContacts;
    }

    // Getting category Count
    public int getCategoryCount() {
        String countQuery = "SELECT  * FROM " + TABLE_CATEGORY;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int totalCategories = cursor.getCount();
        cursor.close();
        db.close();
        // return count
        return totalCategories;
    }


    // Getting boxes Count
    public int getBoxesCount() {
        String countQuery = "SELECT  * FROM " + TABLE_BOX;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int totalBoxes = cursor.getCount();
        cursor.close();
        db.close();
        // return count
        return totalBoxes;
    }

    public void truncateTables(){

        SQLiteDatabase db = this.getWritableDatabase();
        Log.e("Deleting Records", "Truncate table");

        db.execSQL("delete from "+ TABLE_CATEGORY);
        db.execSQL("delete from "+ TABLE_BOX);
        db.close();
    }




}