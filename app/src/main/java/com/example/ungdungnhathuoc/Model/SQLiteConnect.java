package com.example.ungdungnhathuoc.Model;

import static java.security.AccessController.getContext;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SQLiteConnect extends SQLiteOpenHelper {
    public static final String DBName = "user.db";
    private Context context;

    public SQLiteConnect(@Nullable Context context) {
        super(context, DBName, null, 1);
        this.context = context; // Lưu context vào biến
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table users(username TEXT primary key, password TEXT, fullname TEXT, address TEXT, email TEXT, phone TEXT)");
        addAdminAccount(sqLiteDatabase);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists users");
        onCreate(sqLiteDatabase);
    }



    public boolean insertData(String username, String password, String fullname, String address, String email, String phone){
        SQLiteDatabase myDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", username);
        contentValues.put("password", password);
        contentValues.put("fullname", fullname);
        contentValues.put("address", address);
        contentValues.put("email", email);
        contentValues.put("phone", phone);
        long result = myDB.insert("users", null, contentValues);
        if(result == -1) return false;
        else return true;
    }

    // Hàm thêm tài khoản quản trị viên vào cơ sở dữ liệu
    private void addAdminAccount(SQLiteDatabase sqLiteDatabase) {
        String adminUsername = "admin";
        String adminPassword = hashPassword("admin123"); // Mã hóa mật khẩu trước khi lưu
        String adminFullname = "Admin";
        // Kiểm tra xem tài khoản admin đã có chưa
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM users WHERE username = ?", new String[]{"admin"});
        if (cursor.getCount() == 0) {
            // Thêm tài khoản admin vào cơ sở dữ liệu nếu chưa có
            ContentValues contentValues = new ContentValues();
            contentValues.put("username", adminUsername);
            contentValues.put("password", adminPassword);
            contentValues.put("fullname", adminFullname);
            contentValues.put("address", "Admin Address");
            contentValues.put("email", "admin@example.com");
            contentValues.put("phone", "123456789");
            // Chèn dữ liệu vào bảng
            sqLiteDatabase.insert("users", null, contentValues);

            // Lưu thông tin admin vào SharedPreferences
            SharedPreferences sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("username", "adminUsername");
            editor.putString("email", "admin@example.com");
            editor.putString("fullname", "Administrator");
            editor.putString("address", "Admin Address");
            editor.putString("phone", "123456789");
            editor.apply();
        }
        cursor.close();
    }

    //Kiểm xem username tồn tại chưa
    public boolean checkUsername(String username){
        SQLiteDatabase myDB = this.getWritableDatabase();
        Cursor cursor = myDB.rawQuery("select * from users where username = ?", new String[]{username});
        if(cursor.getCount()>0)
            return true;
        else return false;
    }

    //Kiểm xem password tồn tại chưa
    public boolean checkPassword(String password) {
        SQLiteDatabase myDB = this.getWritableDatabase();
        Cursor cursor = myDB.rawQuery("select * from users where password = ?", new String[]{password});
        if(cursor.getCount() > 0)
            return true;
        else
            return false;
    }

    //Kiểm tra email tồn tại chưa
    public boolean checkEmail(String email) {
        SQLiteDatabase myDB = this.getWritableDatabase();
        Cursor cursor = myDB.rawQuery("select * from users where email = ?", new String[]{email});
        if(cursor.getCount() > 0)
            return true;
        else
            return false;
    }

    //Kiểm tra fullname tồn tại chưa
    public boolean checkFullname(String fullname) {
        SQLiteDatabase myDB = this.getWritableDatabase();
        Cursor cursor = myDB.rawQuery("select * from users where fullname = ?", new String[]{fullname});
        if(cursor.getCount() > 0)
            return true;
        else
            return false;
    }

    //Kiểm tra phone tồn tại chưa
    public boolean checkPhone(String phone) {
        SQLiteDatabase myDB = this.getWritableDatabase();
        Cursor cursor = myDB.rawQuery("select * from users where phone = ?", new String[]{phone});
        if(cursor.getCount() > 0)
            return true;
        else
            return false;
    }

    //Truy vấn fullname tương ứng của từng người dùng
    public String getFullnameByUsernameAndPassword(String username, String pwd) {
        SQLiteDatabase myDB = this.getReadableDatabase();
        Cursor cursor = myDB.rawQuery("select fullname from users where username = ? and password = ?", new String[]{username, pwd});

        if (cursor != null && cursor.moveToFirst()) {
            @SuppressLint("Range") String fullname = cursor.getString(cursor.getColumnIndex("fullname"));
            cursor.close();
            return fullname;
        }
        cursor.close();
        return null;
    }

    //Check tài khoản
    public boolean checkUser(String username, String pwd){
        SQLiteDatabase myDB = this.getWritableDatabase();
        Cursor cursor = myDB.rawQuery("select * from users where username = ? and password = ?", new String[]{username, pwd});
        if(cursor.getCount()>0)
            return true;
        else return false;
    }
    public String hashPassword(String password) {
        try {
            // Sử dụng thuật toán MD5
            MessageDigest digest = MessageDigest.getInstance("MD5");
            // Chuyển chuỗi mật khẩu thành mảng byte và mã hóa
            byte[] hashBytes = digest.digest(password.getBytes());
            // Chuyển đổi mảng byte thành chuỗi hexadecimal
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                hexString.append(String.format("%02x", b));
            }
            // Trả về chuỗi đã mã hóa
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }


//    // Hàm mã hóa mật khẩu
//    public String hashPassword(String password) {
//        try {
//            MessageDigest digest = MessageDigest.getInstance("SHA-256");
//            byte[] hashBytes = digest.digest(password.getBytes());
//            StringBuilder hexString = new StringBuilder();
//            for (byte b : hashBytes) {
//                hexString.append(String.format("%02x", b));
//            }
//            return hexString.toString();
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
}

