package com.example.technology_app.activities.profile;

import static com.example.technology_app.utils.GlobalVariable.storage_permissions_33;
import static com.example.technology_app.utils.GlobalVariable.storage_permissons;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.technology_app.R;
import com.example.technology_app.activities.main.MainActivity;
import com.example.technology_app.models.EditProfile.UserModel;
import com.example.technology_app.retrofit.Api;
import com.example.technology_app.retrofit.RetrofitClient;
import com.example.technology_app.utils.GlobalVariable;
import com.example.technology_app.utils.RealPathUtil;

import java.io.File;
import java.io.IOException;

import io.paperdb.Paper;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {
    Button btnEditAvt, btnSave;
    ImageView imgAvt;
    EditText edtName, edtDateOfBirth, edtGender, editAddress, edtMobile;
    Api api;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    private Uri mUri;
    private ProgressDialog mProgressDialog;
    public  static final int MY_REQUEST_CODE = 100;
    public static final String TAG = MainActivity.class.getName();
    String userId, accessToken, userName, dateOfBirth, address, mobile, gender;
    private final ActivityResultLauncher<Intent> mActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>(){
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode() == Activity.RESULT_OK){
                        Intent data = result.getData();
                        if(data == null){
                            return;
                        }
                        Uri uri = data.getData();
                        mUri = uri;
                        try{
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                            imgAvt.setImageBitmap(bitmap);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
    );
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile);
        initView();
        mProgressDialog = new ProgressDialog(ProfileActivity.this);
        mProgressDialog.setMessage("Please wait for upload");
        initControl();
    }

    private void initControl() {
        btnEditAvt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPermission();
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mUri != null){
                    UploadImage(userId, accessToken, edtName.getText().toString(), edtGender.getText().toString(), edtDateOfBirth.getText().toString(), editAddress.getText().toString(), edtMobile.getText().toString());
                }
            }
        });
    }

    private void getInfoUser() {
        userId = Paper.book().read("userId");
         accessToken = Paper.book().read("accessToken");
        assert accessToken != null;
        compositeDisposable.add(api.getUserInfo(userId, accessToken)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        userInfoModel -> {
                            if (userInfoModel.getStatus() == 200) {
                                if (userInfoModel.getMetadata() != null) {
                                    Log.v("check","123" + userInfoModel.getMetadata().user.name);
                                    userName = userInfoModel.getMetadata().getUser().getName();
                                    dateOfBirth = userInfoModel.getMetadata().getUser().getDateOfBirth();
                                    address = userInfoModel.getMetadata().getUser().getAddress();
                                    mobile = userInfoModel.getMetadata().getUser().getMobile();

                                    edtName.setText(userInfoModel.getMetadata().getUser().getName());
                                    editAddress.setText(userInfoModel.getMetadata().getUser().getAddress());
                                    edtGender.setText(userInfoModel.getMetadata().getUser().getGender());
                                    edtMobile.setText(userInfoModel.getMetadata().getUser().getMobile());
                                    edtDateOfBirth.setText(userInfoModel.getMetadata().getUser().getDateOfBirth());

                                    Glide.with(getApplicationContext()).load(userInfoModel.getMetadata().user.getAvatar()).into(imgAvt);


                                } else {
                                    Toast.makeText(getApplicationContext(), "No categories available", Toast.LENGTH_SHORT).show();
                                }
                            }
                        },
                        throwable -> {
                            Log.d( "Log","123"+ throwable.getMessage());
                            Toast.makeText(getApplicationContext(), "Loi!!!" + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                )
        );
    }

    private void UploadImage(String userId, String accessToken, String name, String gender, String dateOfBirth, String address, String mobile) {
        assert userId != null;
        assert accessToken != null;
        RequestBody requestBodyName = RequestBody.create(MediaType.parse("multipart/form-data"), name);
        RequestBody requestBodyGender = RequestBody.create(MediaType.parse("multipart/form-data"), gender);
        RequestBody requestBodyDateOfBirth = RequestBody.create(MediaType.parse("multipart/form-data"), dateOfBirth);
        RequestBody requestBodyAddress = RequestBody.create(MediaType.parse("multipart/form-data"), address);
        RequestBody requestBodyMobile = RequestBody.create(MediaType.parse("multipart/form-data"), mobile);


        mProgressDialog.show();
        String imagePath = RealPathUtil.getRealPath(this, mUri);
        String mimeType = "image/jpeg";
        File file = new File(imagePath);
//        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        RequestBody requestFile = RequestBody.create(MediaType.parse(mimeType), file);
        MultipartBody.Part part = MultipartBody.Part.createFormData("avatar", file.getName(), requestFile);
        api.editProfile(userId, accessToken, requestBodyName, requestBodyGender, requestBodyDateOfBirth, part, requestBodyAddress, requestBodyMobile).enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(@NonNull Call<UserModel> call, @NonNull Response<UserModel> response) {
                mProgressDialog.dismiss();
                if (response.isSuccessful() && response.body() != null) {
                    UserModel userModel = response.body();
                    if (userModel.getStatus() == 200) {
                        Glide.with(ProfileActivity.this).load(userModel.getMetadata().getUser().getAvatar()).into(imgAvt);
                        Toast.makeText(ProfileActivity.this, "Upload successful", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(ProfileActivity.this, "Upload failed", Toast.LENGTH_LONG).show();
                    }
                } else {
                    try {
                        Log.e("API_ERROR", "Response code: " + response.code());
                        assert response.errorBody() != null;
                        Log.e("API_ERROR", "Error body: " + response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(ProfileActivity.this, "Response body is null", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(@NonNull Call<UserModel> call, @NonNull Throwable t) {
                mProgressDialog.dismiss();
                Log.e( "TAG", t.toString());
                Toast.makeText( ProfileActivity.this, "Gọi API thất bại", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void initView() {
        gender = "Nam";
        Paper.init(getApplicationContext());
        api = RetrofitClient.getInstance(GlobalVariable.BASE_URL).create(Api.class);

        btnEditAvt = findViewById(R.id.btnChangeAvt);
        btnSave = findViewById(R.id.btnSaveEditProfile);
        imgAvt = findViewById(R.id.imgAvtProfile);
        edtName = findViewById(R.id.editProfileName);
        edtMobile = findViewById(R.id.editProfilePhoneNumber);
        edtGender = findViewById(R.id.editProfileGender);
        editAddress = findViewById(R.id.editProfileAddress);
        edtDateOfBirth = findViewById(R.id.editProfileDateOfBirth);
        getInfoUser();

    }

    public static String[] permissions(){
        String[] p;
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
            p = storage_permissions_33;
        }else{
            p = storage_permissons;
        }
        return p;
    }

    private void checkPermission(){
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M){
            openGallery();
            return;
        }
        if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
            openGallery();
        }else{
            requestPermissions(permissions(), MY_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == MY_REQUEST_CODE){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                openGallery();
            }
        }
    }

    private void openGallery(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        mActivityResultLauncher.launch(Intent.createChooser(intent, "Select Picture"));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }
}