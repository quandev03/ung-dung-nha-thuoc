package com.example.ungdungnhathuoc.Data;//package com.example.ungdungnhathuoc.Model;

import static java.security.AccessController.getContext;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.ungdungnhathuoc.Model.Thuoc;
import com.example.ungdungnhathuoc.Model.User;

import java.util.ArrayList;
import java.util.List;

public class SQLiteConnect extends SQLiteOpenHelper {
    public static final String DBName = "user.db";
    private Context context;

    // ACCOUNT COLUMN
    public static final String TABLE_USERS = "users";
    public static final String COLUMN_ACCOUNT_USERNAME = "username";
    public static final String COLUMN_ACCOUNT_PASSWORD = "password";
    public static final String COLUMN_ACCOUNT_FULLNAME = "fullname";
    public static final String COLUMN_ACCOUNT_ADDRESS = "address";
    public static final String COLUMN_ACCOUNT_EMAIL = "email";
    public static final String COLUMN_ACCOUNT_PHONE = "phone";
    public static final String COLUMN_ACCOUNT_ROLE = "role";
    private static final String CREATE_TABLE_USERS =
            "CREATE TABLE " + TABLE_USERS + " (" +
                    COLUMN_ACCOUNT_USERNAME + " TEXT PRIMARY KEY, " +
                    COLUMN_ACCOUNT_PASSWORD + " TEXT NOT NULL, " +
                    COLUMN_ACCOUNT_FULLNAME + " TEXT, " +
                    COLUMN_ACCOUNT_ADDRESS + " TEXT, " +
                    COLUMN_ACCOUNT_EMAIL + " TEXT, " +
                    COLUMN_ACCOUNT_PHONE + " TEXT, " +
                    COLUMN_ACCOUNT_ROLE + " BOOLEAN DEFAULT false" +
                    ");";
    // THUOC COLUMN
    public static final String TABLE_THUOC_NAME = "thuoc";
    public static final String COLUMN_THUOC_ID = "id";
    public static final String COLUMN_THUOC_TEN_THUOC = "tenThuoc";
    public static final String COLUMN_THUOC_CONG_DUNG = "congDung";
    public static final String COLUMN_THUOC_TON_KHO = "tonKho";
    public static final String COLUMN_THUOC_DA_BAN = "daBan";
    public static final String COLUMN_THUOC_DON_GIA = "donGia";
    public static final String COLUMN_THUOC_HINH_ANH = "hinhAnh";
    public static final String COLUMN_THUOC_LOAI = "loai";
    public static final String COLUMN_THUOC_CREATE_AT = "createAt";
    private static final String CREATE_TABLE_THUOC =
            "CREATE TABLE " + TABLE_THUOC_NAME + " (" +
                    COLUMN_THUOC_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_THUOC_TEN_THUOC + " TEXT NOT NULL, " +
                    COLUMN_THUOC_CONG_DUNG + " TEXT, " +
                    COLUMN_THUOC_TON_KHO + " INTEGER DEFAULT 0, " +
                    COLUMN_THUOC_DA_BAN + " INTEGER DEFAULT 0, " +
                    COLUMN_THUOC_DON_GIA + " REAL, " +
                    COLUMN_THUOC_HINH_ANH + " TEXT, " +
                    COLUMN_THUOC_LOAI + " TEXT, " +
                    COLUMN_THUOC_CREATE_AT + " DATETIME DEFAULT CURRENT_TIMESTAMP" +
                    ");";

    public SQLiteConnect(@Nullable Context context) {
        super(context, DBName, null, 1);
        this.context = context; // Lưu context vào biến
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_USERS);
        sqLiteDatabase.execSQL(CREATE_TABLE_THUOC);
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
        contentValues.put("role", true); // Mặc định là user
        long result = myDB.insert("users", null, contentValues);
        if(result == -1) return false;
        else return true;
    }
    public boolean createNewThuoc(
            String tenThuoc,
            String congDung,
            int tonKho,
            double donGia,
            String hinhAnh,
            String loai
    ) {
        SQLiteDatabase myDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("tenThuoc", tenThuoc);
        contentValues.put("congDung", congDung);
        contentValues.put("tonKho", tonKho);
        contentValues.put("donGia", donGia);
        contentValues.put("hinhAnh", hinhAnh);
        contentValues.put("loai", loai);
        long result = myDB.insert("thuoc", null, contentValues);
        return result != -1;
    }

    public List<Thuoc> getAllThuoc() {
        SQLiteDatabase myDB = this.getReadableDatabase();
        List<Thuoc> thuocList = new ArrayList<>();

        // Truy vấn tất cả dữ liệu từ bảng "thuoc"
        Cursor cursor = myDB.rawQuery("SELECT * FROM thuoc", null);

        if (cursor.moveToFirst()) {
            do {
                // Lấy dữ liệu từ các cột
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                String tenThuoc = cursor.getString(cursor.getColumnIndexOrThrow("tenThuoc"));
                String congDung = cursor.getString(cursor.getColumnIndexOrThrow("congDung"));
                int tonKho = cursor.getInt(cursor.getColumnIndexOrThrow("tonKho"));
                double donGia = cursor.getDouble(cursor.getColumnIndexOrThrow("donGia"));
                String hinhAnh = cursor.getString(cursor.getColumnIndexOrThrow("hinhAnh"));
                String loai = cursor.getString(cursor.getColumnIndexOrThrow("loai"));

                // Tạo đối tượng Thuoc và thêm vào danh sách
                Thuoc thuoc = new Thuoc(tenThuoc, congDung, hinhAnh, tonKho, 0, (float) donGia, loai, id);
                thuocList.add(thuoc);
            } while (cursor.moveToNext());
        }

        // Đóng con trỏ
        cursor.close();

        return thuocList;
    }
    public Thuoc findOne(String condition) {
        SQLiteDatabase myDB = this.getReadableDatabase();
        Thuoc thuoc = null; // Biến để lưu bản ghi nếu tìm thấy

        // Thực hiện truy vấn với điều kiện
        Cursor cursor = myDB.rawQuery("SELECT * FROM thuoc WHERE " + condition, null);

        if (cursor != null && cursor.moveToFirst()) {
            // Lấy dữ liệu từ các cột
            int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
            String tenThuoc = cursor.getString(cursor.getColumnIndexOrThrow("tenThuoc"));
            String congDung = cursor.getString(cursor.getColumnIndexOrThrow("congDung"));
            int tonKho = cursor.getInt(cursor.getColumnIndexOrThrow("tonKho"));
            double donGia = cursor.getDouble(cursor.getColumnIndexOrThrow("donGia"));
            String hinhAnh = cursor.getString(cursor.getColumnIndexOrThrow("hinhAnh"));
            String loai = cursor.getString(cursor.getColumnIndexOrThrow("loai"));

            // Tạo đối tượng Thuoc từ dữ liệu
            thuoc = new Thuoc(tenThuoc, congDung, hinhAnh, tonKho, 0, (float) donGia, loai, id);
        }

        // Đóng con trỏ
        if (cursor != null) {
            cursor.close();
        }

        return thuoc; // Trả về đối tượng Thuoc (hoặc null nếu không tìm thấy)
    }
    // Hàm thêm tài khoản quản trị viên vào cơ sở dữ liệu
    private void addAdminAccount(SQLiteDatabase sqLiteDatabase) {
//        String adminUsername = "admin";
//        String adminPassword = hashPassword("admin123"); // Mã hóa mật khẩu trước khi lưu
//        String adminFullname = "Admin";
        // Kiểm tra xem tài khoản admin đã có chưa
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM users WHERE username = ?", new String[]{"admin"});
        if (cursor.getCount() == 0) {
            // Thêm tài khoản admin vào cơ sở dữ liệu nếu chưa có
            ContentValues contentValues = new ContentValues();
            contentValues.put("username", "admin");
            contentValues.put("password", "admin123");
            contentValues.put("fullname", "Admin");
            contentValues.put("address", "Admin Address");
            contentValues.put("email", "admin@example.com");
            contentValues.put("phone", "123456789");
            contentValues.put("role", "admin"); // Thiết lập role cho admin
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
            editor.putString("role", "admin");
            editor.apply();
        }
        cursor.close();
    }

    public User userDetail(String username) {
        String query = "SELECT * FROM users WHERE username = ?";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, new String[]{username});

        if (cursor != null && cursor.moveToFirst()) {
            // Lấy dữ liệu từ cursor
            String userUsername = cursor.getString(cursor.getColumnIndexOrThrow("username"));
            String fullname = cursor.getString(cursor.getColumnIndexOrThrow("fullname"));
            String address = cursor.getString(cursor.getColumnIndexOrThrow("address"));
            String email = cursor.getString(cursor.getColumnIndexOrThrow("email"));
            String phone = cursor.getString(cursor.getColumnIndexOrThrow("phone"));
            String role = cursor.getString(cursor.getColumnIndexOrThrow("role"));
            String pass = "ACCC";

            // Đóng cursor
            cursor.close();

            // Trả về đối tượng User
            return new User(userUsername,pass,fullname, address, email, phone);
        }
        // Đóng cursor nếu null
        if (cursor != null) {
            cursor.close();
        }
        return null; // Trả về null nếu không tìm thấy
    }
    public boolean updateUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        // Gán các giá trị mới
        contentValues.put("fullname", user.getFullname());
        contentValues.put("address", user.getAddress());
        contentValues.put("email", user.getEmail());
        contentValues.put("phone", user.getPhone());

        // Thực hiện cập nhật
        int rowsUpdated = db.update("users", contentValues, "username = ?", new String[]{user.getUsername()});

        // Kiểm tra kết quả
        return rowsUpdated > 0; // Trả về true nếu có dòng được cập nhật
    }

    public boolean getUserRole(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT role FROM users WHERE username = ? AND password = ?", new String[]{username, password});
        if (cursor != null && cursor.moveToFirst()) {
            @SuppressLint("Range") String role = cursor.getString(cursor.getColumnIndex("role"));
            cursor.close();
            if (role.equals("admin")) {
                return true;
            }else return false;
        }
        if (cursor != null) cursor.close();
        return false; // Mặc định là user nếu không tìm thấy
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

