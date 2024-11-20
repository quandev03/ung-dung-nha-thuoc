package com.example.ungdungnhathuoc;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
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
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.ungdungnhathuoc.Request.CreateProduceInput;
import com.example.ungdungnhathuoc.Request.UpdateProfileInput;
import com.example.ungdungnhathuoc.Request.UploadFileInput;
import com.example.ungdungnhathuoc.Response.ResponceImageProduce;
import com.google.firebase.storage.FirebaseStorage;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

import java.io.ByteArrayOutputStream;
//import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;



public class AddProduce extends AppCompatActivity {
    Button btnUploadFile, btnAdd, btnCancel;
    ImageView imageView;
    EditText edtTenThuoc, edtCongDung, edtDonGia, slThuoc;
    Spinner spinner;
    Uri fileUriG;
    String dataImage;


    // Declare ActivityResultLauncher to handle file picking
    ActivityResultLauncher<Intent> filePickerLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK) {
                    Uri fileUri = result.getData().getData();  // Get URI from result data
                    fileUriG =  fileUri;
                    Log.d("URI", fileUri.toString());
//                    ContentResolver contentResolver = context.getContentResolver();

                    // Log the file path retrieved from URI
//                    String filePath = getFilePathFromUri(AddProduce.this, fileUri);
//                    Log.d("Path", filePath);  // Logs the file path

                    // Display the image
                    displayImage(fileUri);  // This is your custom method to display the image, if needed
                    imageView.setImageURI(fileUri);  // Set the URI to ImageView

                    // Optional: Get additional metadata about the file (e.g., MIME type)
                    getImageMetadata(fileUri);
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_produce);

        btnUploadFile = findViewById(R.id.uploadFile);
        btnAdd = findViewById(R.id.add);
        btnCancel = findViewById(R.id.cancel);
        imageView = findViewById(R.id.imageView);
        edtTenThuoc = findViewById(R.id.edtTenThuoc);
        edtCongDung = findViewById(R.id.edtCongDung);
        edtDonGia = findViewById(R.id.edtDonGia);
        slThuoc = findViewById(R.id.slThuoc);
        spinner = findViewById(R.id.spinner);

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

                uploadImageToServer(fileUriG, accessToken);

                Log.d("Data", dataImage);

                Log.d("AddProduce", "Add button clicked");
                String tenThuoc = edtTenThuoc.getText().toString().trim();
                String congDung = edtCongDung.getText().toString().trim();
                String gia = edtDonGia.getText().toString();
                String sl = slThuoc.getText().toString();
                Integer soLuong = Integer.parseInt(sl);
                String loaiThuoc = spinner.getSelectedItem() != null ? spinner.getSelectedItem().toString() : "";
                Integer donGia = Integer.parseInt(gia);

                CreateProduceInput createProduceInput = new CreateProduceInput(tenThuoc, donGia, soLuong, congDung, null, loaiThuoc);


                Moshi moshi = new Moshi.Builder().build();
                JsonAdapter<CreateProduceInput> jsonAdapter = moshi.adapter(CreateProduceInput.class);
                JsonAdapter<ResponceImageProduce> jsonAdapterRes = moshi.adapter(ResponceImageProduce.class);
                String jsonData = jsonAdapter.toJson(createProduceInput);
//
                MediaType JSON = MediaType.parse("application/json; charset=utf-8");
                RequestBody bodyFile = RequestBody.create(JSON, jsonData);

                Request requestUploadFile = new Request.Builder()
                        .url("http://10.0.2.2:3000/produce/create-produce")
                        .addHeader("Authorization", "Bearer " + accessToken)
                        .post(bodyFile)
                        .build();
//
                OkHttpClient client = new OkHttpClient();
                client.newCall(requestUploadFile).enqueue(new Callback() {
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {
                        Log.e("Error", "Network Error");
                    }

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                        if (!response.isSuccessful()) {
                            Log.e("Error", "Unexpected response code: " + response.code());
                            return;
                        }
                        if (response.isSuccessful()) {
                            // Xử lý phản hồi thành công
                            String responseBody = response.body().string();
                            Log.d("Data", responseBody);
                            ResponceImageProduce responceImageProduce = jsonAdapterRes.fromJson(responseBody);
                            Log.i("Data", responceImageProduce.toString());
                            uploadImageToServer(fileUriG, accessToken);

                            runOnUiThread(() -> {
                                // Hiển thị thông báo khi thành công
                                Toast.makeText(AddProduce.this, "Profile updated successfully", Toast.LENGTH_SHORT).show();
                            });
                        } else {
                            // Xử lý nếu mã phản hồi không phải 2xx
                            runOnUiThread(() -> Toast.makeText(AddProduce.this, "Failed to update profile", Toast.LENGTH_SHORT).show());
                        }
                    }
                });


            }
        });
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

    private void uploadImageToServer(Uri fileUri, String accessToken) {
        if (fileUri == null) {
            Log.e("Upload", "File URI is null, cannot upload");
            Toast.makeText(AddProduce.this, "No file selected to upload!", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            // Resolve InputStream
            ContentResolver contentResolver = getContentResolver();
            InputStream inputStream = contentResolver.openInputStream(fileUri);

            if (inputStream == null) {
                Log.e("Upload", "InputStream is null");
                return;
            }

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) != -1) {
                byteArrayOutputStream.write(buffer, 0, length);
            }
            byte[] fileBytes = byteArrayOutputStream.toByteArray();
            inputStream.close();

            // Log file details
            String fileName = getFileNameFromUri(contentResolver, fileUri);
            if (fileName == null) fileName = "image.jpg";
            Log.d("Upload", "File name: " + fileName);
            Log.d("Upload", "File size: " + fileBytes.length);

            // Create OkHttpClient
            OkHttpClient client = new OkHttpClient();

            // Create RequestBody for file upload
            RequestBody fileBody = RequestBody.create(MediaType.parse("image/jpeg"), fileBytes);
            MultipartBody requestBody = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("file", fileName, fileBody)
                    .build();

            // Build Request
            Request request = new Request.Builder()
                    .url("http://10.0.2.2:3000/produce/upload-image-produce")
                    .post(requestBody)
                    .addHeader("Authorization", "Bearer " + accessToken)
                    .build();

            // Log Request Details
            Log.d("Upload", "Request URL: " + request.url());
            Log.d("Upload", "Authorization: Bearer " + accessToken);

            // Send Request
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.e("Upload", "Upload failed: " + e.getMessage());
                    runOnUiThread(() -> Toast.makeText(AddProduce.this, "Upload thất bại!", Toast.LENGTH_SHORT).show());
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.isSuccessful()) {
                        String responseBody = response.body().string();  // Get response body
                        Log.d("Upload", "Upload thành công: " + responseBody);

                        // Parse the response body (if required, assuming JSON response)
                        Moshi moshi = new Moshi.Builder().build();
                        JsonAdapter<ResponceImageProduce> jsonAdapterRes = moshi.adapter(ResponceImageProduce.class);
                        ResponceImageProduce responseData = jsonAdapterRes.fromJson(responseBody);

                        if (responseData != null) {
                            Log.i("Data", "File ID: " + responseData.getFileInfo().getId());
                            Log.i("Data", "File Name: " + responseData.getFileInfo().getName());
                            Log.i("Data", "File Link: " + responseData.getLink());
                        }

                        runOnUiThread(() -> {
                            Toast.makeText(AddProduce.this, "Upload thành công!", Toast.LENGTH_SHORT).show();
                        });
                    } else {
                        Log.e("Upload", "Server error: " + response.code());
                        runOnUiThread(() -> Toast.makeText(AddProduce.this, "Server error: " + response.code(), Toast.LENGTH_SHORT).show());
                    }
                }
            });

        } catch (IOException e) {
            Log.e("Upload", "Error: " + e.getMessage());
            Toast.makeText(AddProduce.this, "Error uploading file: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    // Phương thức để lấy tên tệp từ URI
    private String getFileNameFromUri(ContentResolver contentResolver, Uri uri) {
        Cursor cursor = contentResolver.query(uri, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            int nameIndex = cursor.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME);
            if (nameIndex != -1) {
                String fileName = cursor.getString(nameIndex);
                cursor.close();
                return fileName;
            }
            cursor.close();
        }
        return null;
    }



    }