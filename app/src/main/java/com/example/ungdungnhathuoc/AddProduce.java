package com.example.ungdungnhathuoc;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.ungdungnhathuoc.Activity.BaseActivity;
import com.example.ungdungnhathuoc.Data.SQLiteConnect;
import com.example.ungdungnhathuoc.Model.Thuoc;
import com.example.ungdungnhathuoc.Request.CreateProduceInput;
import com.example.ungdungnhathuoc.Request.UpdateProfileInput;
import com.example.ungdungnhathuoc.Request.UploadFileInput;
import com.example.ungdungnhathuoc.Response.ResponceImageProduce;
import com.google.firebase.storage.FirebaseStorage;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

import java.io.ByteArrayOutputStream;
//import java.io.File;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.Date;
import java.util.stream.Collectors;


public class AddProduce extends BaseActivity {
    public static final String IS_ADD = "IS_ADD";
    public static final String THUOC = "THUOC";
    Button btnUploadFile, btnAdd, btnCancel;
    ImageView imageView;
    EditText edtTenThuoc, edtCongDung, edtDonGia, slThuoc;
    Spinner spinner;
    Uri fileUriG;
    Toolbar toolbar;
    //    String dataImage;
    boolean isAdd = false;
    Thuoc thuocCurrent ;
    int index = 0;
    String path ;
    // Declare ActivityResultLauncher to handle file picking
    ActivityResultLauncher<Intent> filePickerLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK) {
                    Uri fileUri = result.getData().getData();  // Get URI from result data
                    fileUriG =  fileUri;
                    displayImage(fileUri);  // This is your custom method to display the image, if needed
                    imageView.setImageURI(fileUri);  // Set the URI to ImageView
                    getImageMetadata(fileUri);
                }
            }
    );

    @SuppressLint({"SetTextI18n", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_produce);
        SQLiteConnect sqLiteConnect = new SQLiteConnect(this);
        isAdd = getIntent().getBooleanExtra(IS_ADD, true);
        btnUploadFile = findViewById(R.id.uploadFile);
        btnAdd = findViewById(R.id.add);
        btnCancel = findViewById(R.id.cancel);
        imageView = findViewById(R.id.imageView);
        edtTenThuoc = findViewById(R.id.edtTenThuoc);
        edtCongDung = findViewById(R.id.edtCongDung);
        edtDonGia = findViewById(R.id.edtDonGia);
        slThuoc = findViewById(R.id.slThuoc);
        spinner = findViewById(R.id.spinner);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        if (isAdd){
            getSupportActionBar().setTitle("Thêm sản phẩm thuốc");
            btnAdd.setText("Thêm");
        }else {
            getSupportActionBar().setTitle("Sửa sản phẩm thuốc");

            btnAdd.setText("Sửa");
        }

        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                thuocCurrent = bundle.getParcelable(THUOC, Thuoc.class);
            }else {
                thuocCurrent = bundle.getParcelable(THUOC);
            }
        }

        if (thuocCurrent != null){
            edtTenThuoc.setText(thuocCurrent.getTenthuoc());
            edtCongDung.setText(thuocCurrent.getCongdung());
            edtDonGia.setText((int)(thuocCurrent.getDongia())+"");
            slThuoc.setText(thuocCurrent.getSlhientai()+"");
            List<String> dataList = Arrays.stream(getResources().getStringArray(R.array.options_array)).collect(Collectors.toList());
            String loai = thuocCurrent.getLoai();
            index = dataList.indexOf(loai);
            path = thuocCurrent.getHinhanh();
            Log.d("TAG", "onCreate: ===> image "+path);
            imageView.setImageURI(Uri.parse(path));
        }

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.options_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        if (index == -1 ) spinner.setSelection(0);
        else spinner.setSelection(index);
        // get accessToken
        SharedPreferences sharedPref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String accessToken = sharedPref.getString("accessToken", null);

        // Set up edge-to-edge display (ensure this method is defined in your project if needed)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Set the onClickListener to launch the file picker
        btnUploadFile.setOnClickListener(view -> openFileChooser());
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddProduce.this, MainActivity.class);
                startActivity(intent);
            }
        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Log.d("Path", "Path to file: " + pathToFile);
//                byte[] imageData = readFileToBufferFromPath(pathToFile);
//                UploadFileInput uploadFile = new UploadFileInput(fileBuffer);
                Log.d("DATA IMAGE", "DATA IMAGE: " + fileUriG);
                if (isAdd) {
                    String Path = saveImageFromUri(AddProduce.this, fileUriG, generateRandomFileName());
                    Log.d("AddProduce", "Add button clicked");
                    String tenThuoc = edtTenThuoc.getText().toString().trim();
                    String congDung = edtCongDung.getText().toString().trim();
                    String gia = edtDonGia.getText().toString();
                    String sl = slThuoc.getText().toString();
                    Integer soLuong = Integer.parseInt(sl);
                    String loaiThuoc = spinner.getSelectedItem() != null ? spinner.getSelectedItem().toString() : "";
                    Integer donGia = Integer.parseInt(gia);

                    boolean result = sqLiteConnect.createNewThuoc(tenThuoc, congDung, soLuong, donGia, Path, loaiThuoc);
                    Toast.makeText(AddProduce.this, (result)?"Cập nhật thành công" : "Cập nhật thất bại", Toast.LENGTH_SHORT).show();

                }else {
                    if (fileUriG != null && !fileUriG.toString().isEmpty()) {
                        path = saveImageFromUri(AddProduce.this, fileUriG, generateRandomFileName());
                    }
                    Log.d("AddProduce", "Add button clicked");
                    String tenThuoc = edtTenThuoc.getText().toString().trim();
                    String congDung = edtCongDung.getText().toString().trim();
                    String gia = edtDonGia.getText().toString();
                    String sl = slThuoc.getText().toString();
                    Integer soLuong = Integer.parseInt(sl);
                    String loaiThuoc = spinner.getSelectedItem() != null ? spinner.getSelectedItem().toString() : "";
                    Integer donGia = Integer.parseInt(gia);

                    boolean result = sqLiteConnect.updateThuocById(thuocCurrent.getId(),tenThuoc,congDung,soLuong,donGia,path,loaiThuoc);
                    Toast.makeText(AddProduce.this, (result)?"Cập nhật thành công" : "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void resetUI(){
        edtTenThuoc.setText("");
        edtDonGia.setText("");
        edtCongDung.setText("");
        slThuoc.setText("");
        spinner.setSelection(0);
    }

    // Method to trigger file chooser
    private void openFileChooser() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");  // Allow any file type
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        filePickerLauncher.launch(intent);  // Use the ActivityResultLauncher
    }
    private void displayImage(Uri imageUri) {
        try {
            // Use ContentResolver to get InputStream from the URI
            ContentResolver resolver = getContentResolver();
            InputStream inputStream = resolver.openInputStream(imageUri);

            // Decode the InputStream to a Bitmap
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            imageView.setImageBitmap(bitmap);  // Set the Bitmap to the ImageView
        } catch (Exception e) {
            Log.e("Error", "Failed to load image", e);
        }
    }

    // Optional: Get additional metadata (like MIME type or file size)
    private void getImageMetadata(Uri imageUri) {
        try {
            ContentResolver resolver = getContentResolver();

            // Get MIME type (e.g., image/jpeg, image/png)
            String mimeType = resolver.getType(imageUri);
            Log.d("Image Metadata", "MIME type: " + mimeType);

            // Get the file path (this can be tricky because not all URIs have a direct file path)
            String filePath = imageUri.getPath();
//            pathToFile = filePath;
            Log.d("Image Metadata", "File path: " + filePath);
        } catch (Exception e) {
            Log.e("Error", "Failed to get metadata", e);
        }
    }



    // Method to send CreateProduce request
    public String saveImageFromUri(Context context, Uri imageUri, String imageName) {
        String savedImagePath = null;

        // Tạo thư mục trong bộ nhớ trong
        File storageDir = new File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "MyImages");
        if (!storageDir.exists()) {
            storageDir.mkdirs(); // Tạo thư mục nếu chưa tồn tại
        }

        // Tạo tệp tin ảnh
        File imageFile = new File(storageDir, imageName + ".jpg");

        try (InputStream inputStream = context.getContentResolver().openInputStream(imageUri);
             FileOutputStream outputStream = new FileOutputStream(imageFile)) {

            // Ghi dữ liệu từ InputStream vào FileOutputStream
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }

            savedImagePath = imageFile.getAbsolutePath();
            Log.d("SaveImage", "Image saved at: " + savedImagePath);

        } catch (IOException e) {
            e.printStackTrace();
            Log.e("SaveImage", "Error saving image: " + e.getMessage());
        }

        return savedImagePath;
    }
    public static String generateRandomFileName() {
        // Tạo dấu thời gian hiện tại
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

        // Tạo chuỗi ngẫu nhiên
        String randomString = getRandomString(5);

        // Kết hợp dấu thời gian và chuỗi ngẫu nhiên làm tên tệp
        return "file_" + timeStamp + "_" + randomString ;
    }

    private static String getRandomString(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder result = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            result.append(characters.charAt(random.nextInt(characters.length())));
        }
        return result.toString();
    }
    @Override
    protected void handleNavigation(int itemId) {
        super.handleNavigation(itemId);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}