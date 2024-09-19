package com.example.countnum;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassNamebillDAO
 * @Author wuruoheng
 * @Dates 2024/9/19
 * 用于数据的增删改查
 */
public class BillDAO {
    private DBHelper dbHelper;

    public BillDAO(Context context) {
        dbHelper = new DBHelper(context);
    }
    /**
     * 添加账单**/

    public void addBill(Bill bill) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("date", bill.getMonthDate());
        values.put("unit_price", bill.getUnitPrice());
        values.put("number", bill.getNum());
        values.put("user", bill.getUserName());
        values.put("id", bill.getId());
        db.insert("bill", null, values);
        db.close();
    }


    /**
     * 查找所有用户名字**/
    public List<String> getAllUser() {
        List<String> userList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query("bill", null, null, null, "user", null, null);

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String user = cursor.getString(cursor.getColumnIndex("user"));
                userList.add(user);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return userList;
    }
    /**
     * 删除相关用户名字的账单**/
    public void deleteBillByUser(String userName) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("bill", "user=?", new String[]{userName});
        db.close();
    }


    /**
     * 根据user名查询账单**/
    public List<Bill> getBillByUser(String userName) {
        List<Bill> bills = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query("bill", null, "user=?", new String[]{userName}, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String date = cursor.getString(cursor.getColumnIndex("date"));
                @SuppressLint("Range") double unitPrice = cursor.getDouble(cursor.getColumnIndex("unit_price"));
                @SuppressLint("Range") int num = cursor.getInt(cursor.getColumnIndex("number"));
                @SuppressLint("Range") String id = cursor.getString(cursor.getColumnIndex("id"));
                Bill bill = new Bill(id,date, unitPrice + "", num + "", userName);
                bills.add(bill);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return bills;
    }

    /**
     * 根据id查找账单**/
    public Bill getBillById(String id) {
        Bill bill = null;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query("bill", null, "id=?", new String[]{id}, null, null, null);

        if (cursor.moveToFirst()) {
            @SuppressLint("Range") String date = cursor.getString(cursor.getColumnIndex("date"));
            @SuppressLint("Range") double unitPrice = cursor.getDouble(cursor.getColumnIndex("unit_price"));
            @SuppressLint("Range") int num = cursor.getInt(cursor.getColumnIndex("number"));
            @SuppressLint("Range") String user = cursor.getString(cursor.getColumnIndex("user"));
            bill = new Bill(id,date, unitPrice + "", num + "", user);
        }
        cursor.close();
        db.close();
        return bill;
    }

    /**
     * 根据id删除账单**/
    public void deleteBill(String id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("bill", "id=?", new String[]{id + ""});
        db.close();
    }

    /**
     * 查询所有账单**/
    public List<Bill> getAllBill() {
        List<Bill> bills = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query("bill", null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String date = cursor.getString(cursor.getColumnIndex("date"));
                @SuppressLint("Range") double unitPrice = cursor.getDouble(cursor.getColumnIndex("unit_price"));
                @SuppressLint("Range") int num = cursor.getInt(cursor.getColumnIndex("number"));
                @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex("name"));
                @SuppressLint("Range") String id = cursor.getString(cursor.getColumnIndex("id"));
                Bill bill = new Bill(id,date, unitPrice+"", num+"", name);
                bills.add(bill);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return bills;
    }
    /**
     * 根据id改变账单数据**/
    public void updateBill(Bill bill) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        if(bill.getId()!=null) {
            values.put("date", bill.getMonthDate());
            values.put("unit_price", bill.getUnitPrice());
            values.put("number", bill.getNum());
            values.put("user", bill.getUserName());
            db.update("bill", values, "id=?", new String[]{bill.getId()});
        }
        db.close();
    }

    /**
     * 删除所有账单**/
    public void deleteAllBills() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("bill", null, null);
        db.close();
    }
}