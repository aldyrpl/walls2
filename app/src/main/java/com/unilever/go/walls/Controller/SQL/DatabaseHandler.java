package com.unilever.go.walls.Controller.SQL;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    // static variable
    public static final int DATABASE_VERSION = 1;

    // Database name
    public static final String DATABASE_NAME = "db_gowalls";

    // table name
    public static final String TABLE_USER = "tbl_user";

    // column tables
    private static final String KEY_tbl_user_id = "id";
    private static final String KEY_tbl_user_id_user_group = "id_user_group";
    private static final String KEY_tbl_user_group_name = "group_name";
    private static final String KEY_tbl_user_fullname = "fullname";
    private static final String KEY_tbl_user_img = "img";

    public DatabaseHandler(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //Create table
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_USER + "("
                + KEY_tbl_user_id + " TEXT," + KEY_tbl_user_id_user_group + " TEXT,"
                + KEY_tbl_user_group_name + " TEXT," + KEY_tbl_user_fullname + " TEXT,"+ KEY_tbl_user_img+" TEXT)";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    // on Upgrade database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        onCreate(db);
    }

    public void insertUser(user_model user){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_tbl_user_id, user.getId());
        values.put(KEY_tbl_user_id_user_group, user.getId_user_group());
        values.put(KEY_tbl_user_group_name, user.getGroup_name());
        values.put(KEY_tbl_user_fullname, user.getFullname());
        values.put(KEY_tbl_user_img, user.getImg());

        db.insert(TABLE_USER, null, values);
        db.close();
    }

    public void deleteUser(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_USER,"",null);
        db.close();
    }

    public Boolean checkUser() {
        SQLiteDatabase db = this.getReadableDatabase();
        long count = DatabaseUtils.queryNumEntries(db, TABLE_USER);
        db.close();
        if(count == 1){
            return true;
        }else{
            return false;
        }
    }

    public List<user_model> getUser(){
        List<user_model> listUser = new ArrayList<user_model>();
        String query="SELECT * FROM "+TABLE_USER;

        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery(query, null);

        if(cursor.moveToFirst()){
            do{
                user_model user = new user_model();
                user.setId(cursor.getString(cursor.getColumnIndex(KEY_tbl_user_id)));
                user.setId_user_group(cursor.getString(cursor.getColumnIndex(KEY_tbl_user_id_user_group)));
                user.setGroup_name(cursor.getString(cursor.getColumnIndex(KEY_tbl_user_group_name)));
                user.setFullname(cursor.getString(cursor.getColumnIndex(KEY_tbl_user_fullname)));
                user.setImg(cursor.getString(cursor.getColumnIndex(KEY_tbl_user_img)));
                listUser.add(user);
            }while(cursor.moveToNext());
        }

        return listUser;
    }
}