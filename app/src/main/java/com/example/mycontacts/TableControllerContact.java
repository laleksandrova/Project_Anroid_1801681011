package com.example.mycontacts;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class TableControllerContact extends DatabaseHandler {

    public TableControllerContact(Context context) {
        super(context);
    }

    public boolean create(ObjectContact objectContact) {

        ContentValues values = new ContentValues();

        values.put("firstName", objectContact.firstName);
        values.put("lastName", objectContact.lastName);
        values.put("phone", objectContact.phone);
        values.put("email", objectContact.email);
        values.put("address", objectContact.address);

        SQLiteDatabase db = this.getWritableDatabase();

        boolean createSuccessful = db.insert("contacts", null, values) > 0;
        db.close();

        return createSuccessful;
    }

    public int count() {

        SQLiteDatabase db = this.getWritableDatabase();

        String sql = "SELECT * FROM contacts";
        int recordCount = db.rawQuery(sql, null).getCount();
        db.close();

        return recordCount;

    }

    public List<ObjectContact> read() {

        List<ObjectContact> recordsList = new ArrayList<ObjectContact>();

        String sql = "SELECT * FROM contacts ORDER BY id DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sql, null);

        if (cursor.moveToFirst()) {
            do {

                int id = Integer.parseInt(cursor.getString(cursor.getColumnIndex("id")));
                String contactFirstName = cursor.getString(cursor.getColumnIndex("firstName"));
                String contactLastName = cursor.getString(cursor.getColumnIndex("lastName"));
                String contactPhone = cursor.getString(cursor.getColumnIndex("phone"));
                String contactEmail = cursor.getString(cursor.getColumnIndex("email"));
                String contactAddress = cursor.getString(cursor.getColumnIndex("address"));

                ObjectContact objectContact = new ObjectContact();
                objectContact.id = id;
                objectContact.firstName = contactFirstName;
                objectContact.lastName = contactLastName;
                objectContact.phone = contactPhone;
                objectContact.email = contactEmail;
                objectContact.address = contactAddress;

                recordsList.add(objectContact);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return recordsList;
    }

    public ObjectContact readSingleRecord(int contactId) {

        ObjectContact objectContact = null;

        String sql = "SELECT * FROM contacts WHERE id = " + contactId;

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(sql, null);

        if (cursor.moveToFirst()) {

            int id = Integer.parseInt(cursor.getString(cursor.getColumnIndex("id")));
            String contactFirstName = cursor.getString(cursor.getColumnIndex("firstName"));
            String contactLastName = cursor.getString(cursor.getColumnIndex("lastName"));
            String contactPhone = cursor.getString(cursor.getColumnIndex("phone"));
            String contactEmail = cursor.getString(cursor.getColumnIndex("email"));
            String contactAddress = cursor.getString(cursor.getColumnIndex("address"));

            objectContact = new ObjectContact();
            objectContact.id = id;
            objectContact.firstName = contactFirstName;
            objectContact.lastName = contactLastName;
            objectContact.phone = contactPhone;
            objectContact.email = contactEmail;
            objectContact.address = contactAddress;

        }

        cursor.close();
        db.close();

        return objectContact;

    }

    public boolean update(ObjectContact objectContact) {

        ContentValues values = new ContentValues();

        values.put("firstName", objectContact.firstName);
        values.put("lastName", objectContact.lastName);
        values.put("phone", objectContact.phone);
        values.put("email", objectContact.email);
        values.put("address", objectContact.address);

        String where = "id = ?";

        String[] whereArgs = { Integer.toString(objectContact.id) };

        SQLiteDatabase db = this.getWritableDatabase();

        boolean updateSuccessful = db.update("contacts", values, where, whereArgs) > 0;
        db.close();

        return updateSuccessful;

    }

    public boolean delete(int id) {
        boolean deleteSuccessful = false;

        SQLiteDatabase db = this.getWritableDatabase();
        deleteSuccessful = db.delete("contacts", "id ='" + id + "'", null) > 0;
        db.close();

        return deleteSuccessful;

    }

}
