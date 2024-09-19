package com.example.countnum;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;

/**
 * @ClassNameDBHelper
 * @Author wuruoheng
 * @Dates 2024/9/19
 * 用于管理数据库的创建和版本升级
 */
public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "mydb.db";
    private static final int DATABASE_VERSION = 1;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // 创建数据表的SQL语句
        String createTableQuery = "CREATE TABLE bill (id varchar(64) PRIMARY KEY, date varchar(64), unit_price varchar(64), number varchar(64), user varchar(64))";
        db.execSQL(createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // 数据库升级操作
        if (oldVersion < newVersion) {
            db.execSQL("DROP TABLE IF EXISTS bill");
            onCreate(db);
        }
    }
}