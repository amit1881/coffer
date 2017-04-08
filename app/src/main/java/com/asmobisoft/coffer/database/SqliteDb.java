package com.asmobisoft.coffer.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.asmobisoft.coffer.model.ProvidersData;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 7/25/16.
 */
public class SqliteDb extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 3;
    private static final String DATABASE_NAME = "COFFER";
    private static final String TABLE_DATA = "ALL_DATA";

    private static final String PROVIDER_ID ="provider_id";
    private static final String PROVIDER_NAME = "provider_name";
    private static final String PROVIDER_CODE = "provider_code";
    private static final String SERVICE = "service";
    private static final String PROVIDER_IMAGE = "provider_image";
    private static final String STATUS = "status";

   // SQLiteDatabase db;
    private SQLiteDatabase mDB;
    private String TAG = "INSIDE_DB";


    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_DATA_TABLE = "CREATE TABLE " + TABLE_DATA + "(" + PROVIDER_ID + " INTEGER PRIMARY KEY,"
                + PROVIDER_NAME + " TEXT,"
                + PROVIDER_CODE + " TEXT,"
                + SERVICE + " TEXT,"
                + PROVIDER_IMAGE + " TEXT,"
                + STATUS + " TEXT"  + ")";

        db.execSQL(CREATE_DATA_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DATA);
        onCreate(db);
    }
    public SqliteDb(Context context) {
        super(context,DATABASE_NAME, null, DATABASE_VERSION);
        this.mDB = getWritableDatabase();

    }

     public  void deletData(){
         SQLiteDatabase db = this.getWritableDatabase();
         db.delete(TABLE_DATA,null,null);
         db.close();

     }

    public void addProvider(ProvidersData modelData) {
        boolean bn = false;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(PROVIDER_ID, modelData.getProvider_id());
        values.put(PROVIDER_NAME, modelData.getProvider_name());
        values.put(PROVIDER_CODE, modelData.getProvider_code());
        values.put(SERVICE, modelData.getService());
        values.put(PROVIDER_IMAGE, modelData.getProvider_image());
        values.put(STATUS, modelData.getStatus());

//        db.update(TABLE_DATA, values, "provider_id="+modelData.getProvider_id(), null);
        db.insert(TABLE_DATA, null, values);

        bn = true;
        db.close();

        if(bn)
        Log.e("Database","Insert Sucess !");
    }



    public ArrayList<ProvidersData> getALLProvider(String service) {

        Log.e(TAG,"service : "+service);
        ArrayList<ProvidersData> dataList = new ArrayList<ProvidersData>();
        SQLiteDatabase db = this.getWritableDatabase();
        //String selectQuery = "SELECT  * FROM " + TABLE_DATA +"WHERE "+SERVICE +"=?"+service;
        String[] params = new String[]{ service };
        Cursor cursor = db.rawQuery("SELECT * FROM "+TABLE_DATA+" WHERE "+SERVICE+ " = ?",
                params);
//        Log.e(TAG,"VALUE OF OPERATOR : "+cursor.getString(1));
        ProvidersData modelData;
        modelData = new ProvidersData();
        modelData.setProvider_name("Please select provider");
        modelData.setProvider_id(100000);
        dataList.add(modelData);

        if (cursor.moveToFirst()) {
            do {
                modelData = new ProvidersData();
                modelData.setProvider_id(cursor.getInt(0));
                modelData.setProvider_name(cursor.getString(1));
                modelData.setProvider_code(cursor.getString(2));
                modelData.setService(cursor.getString(3));
                modelData.setProvider_image(cursor.getString(4));
                modelData.setStatus(cursor.getString(5));

                Log.e(TAG,"VALUE OF OPERATOR : "+cursor.getString(1));
                dataList.add(modelData);

            } while (cursor.moveToNext());

        }
        return dataList;
    }



}
