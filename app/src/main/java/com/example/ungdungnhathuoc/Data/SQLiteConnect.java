package com.example.ungdungnhathuoc.Data;//package com.example.ungdungnhathuoc.Model;

import static java.security.AccessController.getContext;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.ungdungnhathuoc.API.HashPass;
import com.example.ungdungnhathuoc.Activity.ThongTinDonHangNBActivity;
import com.example.ungdungnhathuoc.Model.Order;
import com.example.ungdungnhathuoc.Model.Thuoc;
import com.example.ungdungnhathuoc.Model.User;
import com.example.ungdungnhathuoc.Request.FilterOrder;

import java.util.ArrayList;
import java.util.List;

public class SQLiteConnect extends SQLiteOpenHelper {
    public static final String DBName = "banThuoc.db";
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
            "CREATE TABLE IF NOT EXISTS " + TABLE_USERS + " (" +
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
            "CREATE TABLE IF NOT EXISTS " + TABLE_THUOC_NAME + " (" +
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

    public static final String TABLE_ORDER_NAME = "orderProduce";
    public static final String COLUMN_ORDER_ID = "id";
    public static final String COLUMN_ORDER_ID_THUOC = "id_produce";
    public static final String COLUMN_ORDER_ID_USER = "id_user";
    public static final String COLUMN_ORDER_SL = "so_mua";
    public static final String COLUMN_ORDER_DON_GIA = "don_gia";
    public static final String COLUMN_ORDER_CREATE_AT = "createAt";
    public static final String COLUMN_ORDER_TOTAL = "total";
    public static final String COLUMN_ORDER_STATUS = "status";
    public static final String CREATE_TABLE_ORDER =
            "CREATE TABLE IF NOT EXISTS " + TABLE_ORDER_NAME + " (" +
                    COLUMN_ORDER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_ORDER_ID_THUOC + " INTEGER, " +
                    COLUMN_ORDER_ID_USER + " TEXT, " +
                    COLUMN_ORDER_SL + " INTEGER, " +
                    COLUMN_ORDER_DON_GIA + " FLOAT, " +
                    COLUMN_ORDER_CREATE_AT + " DATETIME DEFAULT CURRENT_TIMESTAMP, " +
                    COLUMN_ORDER_STATUS + " INTEGER DEFAULT 0," +
                    COLUMN_ORDER_TOTAL + " FLOAT)";

    public static final String TABLE_STATISTICS_NAME = "statistics";
    public static final String COLUMN_STATISTICS_ID = "id";
    public static final String COLUMN_STATISTICS_TOTAL_ORDERS = "totalOrders";
    public static final String COLUMN_STATISTICS_DELIVERED_ORDERS = "deliveredOrders";
    public static final String COLUMN_STATISTICS_CANCELLED_ORDERS = "cancelledOrders";
    public static final String COLUMN_STATISTICS_TOTAL_SOLD = "totalSold";
    public static final String COLUMN_STATISTICS_TOTAL_REVENUE = "totalRevenue";
    public static final String COLUMN_STATISTICS_UPDATE_AT = "updateAt";

    private static final String CREATE_TABLE_STATISTICS =
            "CREATE TABLE IF NOT EXISTS " + TABLE_STATISTICS_NAME + " (" +
                    COLUMN_STATISTICS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_STATISTICS_TOTAL_ORDERS + " INTEGER DEFAULT 0, " +
                    COLUMN_STATISTICS_DELIVERED_ORDERS + " INTEGER DEFAULT 0, " +
                    COLUMN_STATISTICS_CANCELLED_ORDERS + " INTEGER DEFAULT 0, " +
                    COLUMN_STATISTICS_TOTAL_SOLD + " INTEGER DEFAULT 0, " +
                    COLUMN_STATISTICS_TOTAL_REVENUE + " REAL DEFAULT 0, " +
                    COLUMN_STATISTICS_UPDATE_AT + " DATETIME DEFAULT CURRENT_TIMESTAMP" +
                    ");";
    private static final String UPDATE_STATISTICS_QUERY =
            "UPDATE " + TABLE_STATISTICS_NAME + " SET " +
                    COLUMN_STATISTICS_TOTAL_ORDERS + " = (SELECT COUNT(*) FROM " + TABLE_ORDER_NAME + "), " +
                    COLUMN_STATISTICS_DELIVERED_ORDERS + " = (SELECT COUNT(*) FROM " + TABLE_ORDER_NAME + " WHERE " + COLUMN_ORDER_STATUS + " = 1), " +
                    COLUMN_STATISTICS_CANCELLED_ORDERS + " = (SELECT COUNT(*) FROM " + TABLE_ORDER_NAME + " WHERE " + COLUMN_ORDER_STATUS + " = 2), " +
                    COLUMN_STATISTICS_TOTAL_SOLD + " = (SELECT SUM(" + COLUMN_ORDER_SL + ") FROM " + TABLE_ORDER_NAME + "), " +
                    COLUMN_STATISTICS_TOTAL_REVENUE + " = (SELECT SUM(" + COLUMN_ORDER_TOTAL + ") FROM " + TABLE_ORDER_NAME + "), " +
                    COLUMN_STATISTICS_UPDATE_AT + " = CURRENT_TIMESTAMP " +
                    "WHERE " + COLUMN_STATISTICS_ID + " = 1";

    public SQLiteConnect(@Nullable Context context) {
        super(context, DBName, null, 1);
        this.context = context; // Lưu context vào biến
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_USERS);
        sqLiteDatabase.execSQL(CREATE_TABLE_THUOC);
        sqLiteDatabase.execSQL(CREATE_TABLE_ORDER);
        addAdminAccount(sqLiteDatabase);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Xóa bảng cũ (nếu cần)
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_THUOC_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ORDER_NAME);

        // Tạo lại bảng mới
        db.execSQL(TABLE_USERS);
        db.execSQL(TABLE_THUOC_NAME);
        db.execSQL(TABLE_ORDER_NAME);
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
        contentValues.put("role", false); // Mặc định là user
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
    public boolean updateThuocById(
            int id,
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
        int rowsAffected = myDB.update("thuoc", contentValues, "id = ?", new String[]{String.valueOf(id)});
        return rowsAffected > 0;
    }
    public ArrayList<Thuoc> getAllThuoc() {
        SQLiteDatabase myDB = this.getReadableDatabase();
        ArrayList<Thuoc> thuocList = new ArrayList<>();

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

        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM users WHERE username = ?", new String[]{"admin"});
        HashPass pass = new HashPass("admin123");
        if (cursor.getCount() == 0) {
            // Thêm tài khoản admin vào cơ sở dữ liệu nếu chưa có
            ContentValues contentValues = new ContentValues();
            contentValues.put("username", "admin");
            contentValues.put("password", pass.getHashPass());
            contentValues.put("fullname", "Admin");
            contentValues.put("address", "Admin Address");
            contentValues.put("email", "admin@example.com");
            contentValues.put("phone", "123456789");
            contentValues.put("role", true); // Thiết lập role cho admin
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
            @SuppressLint("Range") int role = cursor.getInt(cursor.getColumnIndex("role"));
            cursor.close();
            return role == 1;
        }
        if (cursor != null) cursor.close();
//        return false; // Mặc định là user nếu không tìm thấy
        return false;
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
    public boolean createOrder(int idProduce, String idUser, int soLuong) {
        SQLiteDatabase myDB = this.getWritableDatabase();
        Cursor produce = null;

        try {
            // Query the 'thuoc' table for the specified product id
            produce = myDB.rawQuery("SELECT * FROM thuoc WHERE id = ?", new String[]{String.valueOf(idProduce)});


            // Check if the Cursor contains any data
            if (produce != null && produce.moveToFirst()) {
                int priceIndex = produce.getColumnIndex("donGia");
                String price = produce.getString(priceIndex);
                Float priceFloat = Float.parseFloat(price);
                Float total = priceFloat * soLuong;
                int tonKho = produce.getColumnIndex("tonKho");
                int tonKhoValue = produce.getInt(tonKho);
                if (tonKhoValue < soLuong) {
                    return false;
                }
                int currentTonKho = tonKhoValue - soLuong;
                myDB.execSQL("UPDATE thuoc SET tonKho = ? WHERE id = ?", new Object[]{currentTonKho, idProduce});
                // Prepare the ContentValues for inserting the order
                ContentValues contentValues = new ContentValues();
                contentValues.put("id_produce", idProduce);
                contentValues.put("id_user", idUser);
                contentValues.put("so_mua", soLuong);
                contentValues.put("don_gia", priceFloat);
                contentValues.put("total", total);

                // Start transaction
                myDB.beginTransaction();
                try {
                    long result = myDB.insert("orderProduce", null, contentValues);

                    if (result == -1) {
                        return false;  // Insert failed
                    }
                    myDB.setTransactionSuccessful();
                    return true;
                } catch (Exception e) {
                    return false;
                } finally {
                    myDB.endTransaction();
                }
            }

        } catch (Exception e) {
            // Handle the exception (e.g., log the error)
            return false;
        } finally {
            if (produce != null) {
                produce.close();  // Always close the cursor when done
            }
        }

        return false;  // Return false if no matching product was found
    }
    public boolean confirmOrder(int orderId) {
        SQLiteDatabase myDB = this.getWritableDatabase();
        Cursor cursor = null;

        try {
            cursor = myDB.rawQuery("SELECT status FROM orderProduce WHERE id = ?", new String[]{String.valueOf(orderId)});

            if (cursor != null && cursor.moveToFirst()) {
                int statusIndex = cursor.getColumnIndex("status");
                int status = cursor.getInt(statusIndex);

                // Check if the current status is 0 (unconfirmed)
                if (status == 0) {
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("status", 1); // Update status to 1 (confirmed)

                    int rowsUpdated = myDB.update("orderProduce", contentValues, "id = ?", new String[]{String.valueOf(orderId)});

                    if (rowsUpdated > 0) {
                        return true; // Successfully confirmed
                    } else {
                        Log.e("Database", "Order update failed, rows updated: " + rowsUpdated);
                        return false; // Update failed
                    }
                } else {
                    Log.e("Database", "Order cannot be confirmed, current status: " + status);
                    return false; // Status is not 0, order cannot be confirmed
                }
            }
            return false; // Order not found
        } catch (Exception e) {
            Log.e("DatabaseError", "Error confirming order", e);
            return false;
        } finally {
            if (cursor != null) cursor.close();
        }
    }

    public boolean cancelOrder(int orderId) {
        SQLiteDatabase myDB = this.getWritableDatabase();
        Cursor cursor = null;

        try {
            cursor = myDB.rawQuery("SELECT status, so_mua, id_produce FROM orderProduce WHERE id = ?", new String[]{String.valueOf(orderId)});

            if (cursor != null && cursor.moveToFirst()) {
                int statusIndex = cursor.getColumnIndex("status");
                int status = cursor.getInt(statusIndex);

                int soMuaIndex = cursor.getColumnIndex("so_mua");
                int soLuong = cursor.getInt(soMuaIndex);

                int idProduceIndex = cursor.getColumnIndex("id_produce");
                int idThuoc = cursor.getInt(idProduceIndex);

                Cursor produce = myDB.rawQuery("SELECT tonKho FROM thuoc WHERE id = ?", new String[]{String.valueOf(idThuoc)});
                if (produce != null && produce.moveToFirst()) {
                    int tonKhoIndex = produce.getColumnIndex("tonKho");
                    int tonKhoValue = produce.getInt(tonKhoIndex);

                    int currentTonKho = tonKhoValue + soLuong;
                    myDB.execSQL("UPDATE thuoc SET tonKho = ? WHERE id = ?", new Object[]{currentTonKho, idThuoc});

                    produce.close(); // Close the cursor after use
                }

                if (status == 0) {
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("status", 2); // Update status to 2 (canceled)

                    int rowsUpdated = myDB.update("orderProduce", contentValues, "id = ?", new String[]{String.valueOf(orderId)});

                    if (rowsUpdated > 0) {
                        return true; // Successfully canceled
                    } else {
                        Log.e("Database", "Order update failed, rows updated: " + rowsUpdated);
                        return false; // Update failed
                    }
                } else {
                    Log.e("Database", "Order cannot be canceled, current status: " + status);
                    return false; // Status is not 0, order cannot be canceled
                }
            }
            return false; // Order not found
        } catch (Exception e) {
            Log.e("DatabaseError", "Error canceling order", e);
            return false;
        } finally {
            if (cursor != null) cursor.close();
        }
    }

    public boolean complateOrder(int orderId) {
        SQLiteDatabase myDB = this.getWritableDatabase();
        Cursor cursor = null;
        Cursor produce = null;

        try {
            // Query to get the current status of the order
            cursor = myDB.rawQuery("SELECT status, so_mua, id_produce FROM orderProduce WHERE id = ?",
                    new String[]{String.valueOf(orderId)});

            if (cursor != null && cursor.moveToFirst()) {
                int statusIndex = cursor.getColumnIndex("status");
                int status = cursor.getInt(statusIndex);
                int soMuaIndex = cursor.getColumnIndex("so_mua");
                int soLuong = cursor.getInt(soMuaIndex);
                int idProduceIndex = cursor.getColumnIndex("id_produce");
                int idThuoc = cursor.getInt(idProduceIndex);

                // Check if the status is 1
                if (status == 1) {
                    // Fetch current daBan value
                    produce = myDB.rawQuery("SELECT daBan FROM thuoc WHERE id = ?",
                            new String[]{String.valueOf(idThuoc)});
                    if (produce != null && produce.moveToFirst()) {
                        int daBanIndex = produce.getColumnIndex("daBan");
                        int daBanValue = produce.getInt(daBanIndex);
                        int currentDaBan = daBanValue + soLuong;

                        ContentValues contentValues = new ContentValues();
                        contentValues.put("status", 3); // Update status to 3 (completed)

                        // Update the order status
                        int rowsUpdated = myDB.update("orderProduce", contentValues, "id = ?",
                                new String[]{String.valueOf(orderId)});
                        myDB.execSQL("UPDATE thuoc SET daBan = ? WHERE id = ?",
                                new Object[]{currentDaBan, idThuoc});

                        Log.d("Database", "Order " + orderId + " completed successfully.");

                        return rowsUpdated > 0;
                    } else {
                        Log.e("Database", "Thuoc not found for id: " + idThuoc);
                    }
                } else {
                    Log.e("Database", "Order cannot be completed, current status: " + status);
                }
            } else {
                Log.e("Database", "Order not found for id: " + orderId);
            }
            return false;
        } catch (Exception e) {
            // Handle any exceptions (e.g., log the error)
            e.printStackTrace();
            return false;
        } finally {
            if (cursor != null) {
                cursor.close(); // Close the cursor to free resources
            }
            if (produce != null) {
                produce.close(); // Close the second cursor
            }
        }
    }
    public void updateStatistics() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(UPDATE_STATISTICS_QUERY);
        db.close();
    }
    public ArrayList<Order> getAllOrderDetails() {
        SQLiteDatabase myDB = this.getReadableDatabase();
        ArrayList<Order> orderDetailsList = new ArrayList<>();

        // Truy vấn kết hợp dữ liệu từ các bảng
        String query = "SELECT o.id AS orderId, o.createAt AS orderDate, o.status, o.total AS totalAmount, " +
                "o.so_mua AS quantity, t.tenThuoc AS productName, t.donGia AS donGiaThuoc, t.id AS productId, t.hinhAnh AS productImage, " +
                "u.username AS customerName, u.phone AS customerPhone, u.fullname AS customerFullName "  +
                "FROM orderProduce o " +
                "INNER JOIN thuoc t ON o.id_produce = t.id " +
                "INNER JOIN users u ON o.id_user = u.username";

        Cursor cursor = myDB.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                try {
                    // Lấy thông tin đơn hàng
                    int orderId = cursor.getInt(cursor.getColumnIndexOrThrow("orderId"));
                    String orderDate = cursor.getString(cursor.getColumnIndexOrThrow("orderDate"));
                    int status = cursor.getInt(cursor.getColumnIndexOrThrow("status"));
                    int quantity = cursor.getInt(cursor.getColumnIndexOrThrow("quantity"));

                    // Lấy thông tin đơn giá thuốc
                    double donGiaThuoc = cursor.getDouble(cursor.getColumnIndexOrThrow("donGiaThuoc"));

                    // Tính tổng tiền (nếu chưa có giá trị)
                    double totalAmount = cursor.getDouble(cursor.getColumnIndexOrThrow("totalAmount"));
                    if (totalAmount == 0) {
                        totalAmount = donGiaThuoc * quantity;
                    }

                    // Lấy thông tin thuốc
                    int productId = cursor.getInt(cursor.getColumnIndexOrThrow("productId"));
                    String productName = cursor.getString(cursor.getColumnIndexOrThrow("productName"));
                    String productImage = cursor.getString(cursor.getColumnIndexOrThrow("productImage"));
                    Thuoc thuoc = new Thuoc(productName, "", productImage, 0, quantity, (float) donGiaThuoc, "", productId);

                    // Lấy thông tin người dùng
                    String customerName = cursor.getString(cursor.getColumnIndexOrThrow("customerName"));
                    String customerPhone = cursor.getString(cursor.getColumnIndexOrThrow("customerPhone"));
                    String customerFullName = cursor.getString(cursor.getColumnIndexOrThrow("customerFullName"));
                    User user = new User(customerName, "", customerFullName, "", "", customerPhone);

                    // Chuyển đổi trạng thái từ số nguyên sang chuỗi
                    String statusString = getStatusString(status);

                    // Tạo đối tượng Order và thêm vào danh sách
                    Order orderDetails = new Order(orderId, statusString, totalAmount, orderDate, user, thuoc);
                    Log.d("ORDER", "Data: " + orderDetails.toString());
                    orderDetailsList.add(orderDetails);

                } catch (Exception e) {
                    Log.e("getAllOrderDetails", "Error parsing order details", e);
                }
            } while (cursor.moveToNext());
        }

        // Đóng con trỏ
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }

        return orderDetailsList;
    }


    // Chuyển đổi trạng thái đơn hàng từ số nguyên sang chuỗi
    private String getStatusString(int status) {
        switch (status) {
            case 0:
                return "Đang xử lý";
            case 3:
                return "Đã giao";
            case 2:
                return "Đã hủy";
            case 1:
                return "Đã xác nhận";
            default:
                return "Không xác định";
        }
    }


    // Chuyển đổi trạng thái đơn hàng từ int sang String
    public Order getOrderDetailsById(int orderId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Order order = null;
        Cursor cursor = null;

        try {
            // Truy vấn SQL đã sửa
            String query = "SELECT o.id AS orderId, o.status, o.createAt AS orderDate, o.total AS totalPrice, " +
                    "o.so_mua AS quantity, t.tenThuoc AS productName, t.hinhAnh AS productImage, " +
                    "u.fullname AS customerName, u.phone, u.address, u.fullname AS customerFullName " +
                    "FROM orderProduce o " +
                    "LEFT JOIN thuoc t ON o.id_produce = t.id " +
                    "LEFT JOIN users u ON o.id_user = u.username " +
                    "WHERE o.id = ?";

            cursor = db.rawQuery(query, new String[]{String.valueOf(orderId)});

            // Kiểm tra nếu có dữ liệu
            if (cursor != null && cursor.moveToFirst()) {
                int orderID = cursor.getInt(cursor.getColumnIndexOrThrow("orderId"));
                String status = cursor.getString(cursor.getColumnIndexOrThrow("status"));
                String orderDate = cursor.getString(cursor.getColumnIndexOrThrow("orderDate"));
                double totalPrice = cursor.getDouble(cursor.getColumnIndexOrThrow("totalPrice"));
                int quantity = cursor.getInt(cursor.getColumnIndexOrThrow("quantity"));

                // Tạo đối tượng sản phẩm
                String productName = cursor.getString(cursor.getColumnIndexOrThrow("productName"));
                String productImage = cursor.getString(cursor.getColumnIndexOrThrow("productImage"));
                Thuoc thuoc = new Thuoc(productName, "", productImage, 0, quantity, (float) totalPrice, productImage, orderID);

                // Tạo đối tượng khách hàng
                String customerName = cursor.getString(cursor.getColumnIndexOrThrow("customerName"));
                String customerPhone = cursor.getString(cursor.getColumnIndexOrThrow("phone"));
                String address = cursor.getString(cursor.getColumnIndexOrThrow("address"));
                String customerFullName = cursor.getString(cursor.getColumnIndexOrThrow("customerFullName"));
                User user = new User(customerName, "", customerFullName, address, "", customerPhone);

                // Tạo đối tượng Order
                order = new Order(orderID, getStatusString(Integer.parseInt(status)), totalPrice, orderDate, user, thuoc);
            }
        } catch (Exception e) {
            Log.e("ThongTinDonHangActivity", "Error fetching order details", e);
        } finally {
            if (cursor != null) cursor.close();
        }

        return order;
    }

    public ArrayList<Order> getOrderUser (String username, int status) {
        FilterOrder filterOrder = new FilterOrder(username, status);
        return getDataOrder(filterOrder);

    }
    public ArrayList<Order> getOrderUser (String username) {
        FilterOrder filterOrder = new FilterOrder(username);
        return getDataOrder(filterOrder);
    }
    public ArrayList<Order> getDataOrder(FilterOrder filterOrder) {
        String condition = filterOrder.buildCondition();
        SQLiteDatabase myDB = this.getReadableDatabase();
        ArrayList<Order> orderDetailsList = new ArrayList<>();

        String query =
                "SELECT o.id AS orderId," +
                        " o.createAt AS orderDate," +
                        " o.status, o.total AS totalAmount, " +
                        "o.so_mua AS quantity, " +
                        "t.tenThuoc AS productName, " +
                        "t.donGia AS donGiaThuoc, " +
                        "t.id AS productId, " +
                        "t.hinhAnh AS productImage, " +
                        "u.username AS customerName, " +
                        "u.phone AS customerPhone, " +
                        "u.fullname AS customerFullName " +
                        "FROM orderProduce o " +
                        "INNER JOIN thuoc t ON o.id_produce = t.id " +
                        "INNER JOIN users u ON o.id_user = u.username WHERE "
                        + condition;

        Cursor cursor = myDB.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                try {
                    // Lấy thông tin đơn hàng
                    int orderId = cursor.getInt(cursor.getColumnIndexOrThrow("orderId"));
                    String orderDate = cursor.getString(cursor.getColumnIndexOrThrow("orderDate"));
                    int status = cursor.getInt(cursor.getColumnIndexOrThrow("status"));
                    int quantity = cursor.getInt(cursor.getColumnIndexOrThrow("quantity"));

                    // Lấy thông tin đơn giá thuốc
                    double donGiaThuoc = cursor.getDouble(cursor.getColumnIndexOrThrow("donGiaThuoc"));

                    // Tính tổng tiền (nếu chưa có giá trị)
                    double totalAmount = cursor.getDouble(cursor.getColumnIndexOrThrow("totalAmount"));
                    if (totalAmount == 0) {
                        totalAmount = donGiaThuoc * quantity;
                    }

                    // Lấy thông tin thuốc
                    int productId = cursor.getInt(cursor.getColumnIndexOrThrow("productId"));
                    String productName = cursor.getString(cursor.getColumnIndexOrThrow("productName"));
                    String productImage = cursor.getString(cursor.getColumnIndexOrThrow("productImage"));
                    Thuoc thuoc = new Thuoc(productName, "", productImage, 0, quantity, (float) donGiaThuoc, "", productId);

                    // Lấy thông tin người dùng
                    String customerName = cursor.getString(cursor.getColumnIndexOrThrow("customerName"));
                    String customerPhone = cursor.getString(cursor.getColumnIndexOrThrow("customerPhone"));
                    String customerFullName = cursor.getString(cursor.getColumnIndexOrThrow("customerFullName"));
                    User user = new User(customerName, "", customerFullName, "", "", customerPhone);

                    // Chuyển đổi trạng thái từ số nguyên sang chuỗi
                    String statusString = getStatusString(status);

                    // Tạo đối tượng Order và thêm vào danh sách
                    Order orderDetails = new Order(orderId, statusString, totalAmount, orderDate, user, thuoc);
                    Log.d("ORDER", "Data: " + orderDetails.toString());
                    orderDetailsList.add(orderDetails);

                } catch (Exception e) {
                    Log.e("getAllOrderDetails", "Error parsing order details", e);
                }
            } while (cursor.moveToNext());
        }

        // Đóng con trỏ
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }

        return orderDetailsList;

    }
    public boolean deleteThuocById(int id) {
        SQLiteDatabase myDB = this.getWritableDatabase();
        int rowsDeleted = myDB.delete("thuoc", "id = ?", new String[]{String.valueOf(id)});
        return rowsDeleted > 0;
    }
}


