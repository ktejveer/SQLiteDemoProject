package suntec.com.sqlitedemoproject.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import suntec.com.sqlitedemoproject.models.ContactModel;

/**
 * Created by suntec on 14/12/17.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION=1;
    private static final String DATABASE_NAME="ContactManager";

    private class TABLE_CONTACTS{
        public static final String TABLE_NAME="Contact";
        public static final String KEY_ID="id";
        public static final String KEY_NAME="name";
        public static final String KEY_PHONE_NUMBER="phone_number";
    }

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_CONTACT_TABLE="Create table "+TABLE_CONTACTS.TABLE_NAME+"("
                +TABLE_CONTACTS.KEY_ID+" INTEGER PRIMARY KEY AUTOINCREMENT , "
                +TABLE_CONTACTS.KEY_NAME+" TEXT , "
                +TABLE_CONTACTS.KEY_PHONE_NUMBER+" TEXT )";

        db.execSQL(CREATE_CONTACT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        if(oldVersion!=newVersion){
            db.execSQL("DROP TABLE IF EXISTS "+TABLE_CONTACTS.TABLE_NAME);
            onCreate(db);
        }
    }

    //Method to add contact into the Table
    public void addContact(ContactModel contact){
        SQLiteDatabase db=this.getWritableDatabase();

        ContentValues values=new ContentValues();
        values.put(TABLE_CONTACTS.KEY_NAME,contact.getName());
        values.put(TABLE_CONTACTS.KEY_PHONE_NUMBER,contact.getPhoneNumber());
        db.insert(TABLE_CONTACTS.TABLE_NAME,null,values);
        db.close();
    }

    //Method to delete Contact from Table
    public void deleteContact(ContactModel contactModel){
        SQLiteDatabase db=this.getWritableDatabase();
        db.delete(TABLE_CONTACTS.TABLE_NAME,TABLE_CONTACTS.KEY_ID+" = ? ",
        new String[]{String.valueOf(contactModel.getId())});
        db.close();
    }

    //Method to retrieve Contacts from Table
    public ArrayList<ContactModel> getAllContacts(){
        ArrayList<ContactModel> contacts=new ArrayList<>();
        String selectQuery="SELECT * FROM "+TABLE_CONTACTS.TABLE_NAME;
        SQLiteDatabase db=this.getWritableDatabase();

        Cursor cursor=db.rawQuery(selectQuery,null);

        if(cursor.moveToFirst()){
            do{
                ContactModel model=new ContactModel();

                model.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(TABLE_CONTACTS.KEY_ID))));
                model.setName(cursor.getString(cursor.getColumnIndex(TABLE_CONTACTS.KEY_NAME)));
                model.setPhoneNumber(cursor.getString(cursor.getColumnIndex(TABLE_CONTACTS.KEY_PHONE_NUMBER)));
                contacts.add(model);
            }while (cursor.moveToNext());
        }
        return contacts;
    }
}
