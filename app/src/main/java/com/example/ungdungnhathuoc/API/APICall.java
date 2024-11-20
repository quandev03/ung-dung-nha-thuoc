//import android.net.Uri;
//import android.os.Bundle;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.google.android.gms.tasks.OnFailureListener;
//import com.google.android.gms.tasks.OnSuccessListener;
//import com.google.firebase.storage.FirebaseStorage;
//import com.google.firebase.storage.StorageReference;
//import com.google.firebase.storage.UploadTask;
//
//public class MainActivity extends AppCompatActivity {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        // Uri của ảnh cần tải lên (bạn có thể lấy Uri này từ bộ chọn ảnh hoặc camera)
//        Uri fileUri = Uri.parse("android.resource://your.package.name/" + R.drawable.your_image);
//
//        uploadImageToFirebase(fileUri);
//    }
//
//    private void uploadImageToFirebase(Uri fileUri) {
//        // Tạo tham chiếu đến Firebase Storage
//        FirebaseStorage storage = FirebaseStorage.getInstance();
//        StorageReference storageRef = storage.getReference();
//        StorageReference imagesRef = storageRef.child("images/" + fileUri.getLastPathSegment());
//
//        // Tải file lên Firebase Storage
//        UploadTask uploadTask = imagesRef.putFile(fileUri);
//
//        // Đăng ký các listeners để lắng nghe khi tải xong hoặc khi tải thất bại
//        uploadTask.addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception exception) {
//                // Xử lý khi tải thất bại
//                Toast.makeText(MainActivity.this, "Tải ảnh lên thất bại!", Toast.LENGTH_SHORT).show();
//            }
//        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//            @Override
//            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                // Xử lý khi tải thành công
//                Toast.makeText(MainActivity.this, "Tải ảnh lên thành công!", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//}