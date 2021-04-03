 package com.example.groupproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

 public class MainActivity extends AppCompatActivity {

    ImageView mImageView;
    Button mChooseBtn,snap;

    private static final int IMAGE_PICK_CODE=1000;
     private static final int PERMISSION_CODE=1001;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mImageView = findViewById(R.id.image_view);
        mChooseBtn = findViewById(R.id.choose_image_btn);
snap=findViewById(R.id.btn_snap);
snap.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
Intent cameraIntent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
startActivityForResult(cameraIntent,1888);
    }
});
        mChooseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                        String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
                        requestPermissions(permissions, PERMISSION_CODE);
                    } else {
                        pickImageFromGallery();
                    }
                } else {

                }
            }


        });

    }
        private void pickImageFromGallery(){
        Intent intent=new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_PICK_CODE);

        }

     @Override
     protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
         super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK && requestCode==IMAGE_PICK_CODE){
            mImageView.setImageURI(data.getData());
        }
        else if(requestCode == 1888) {
                 Bitmap picture = (Bitmap) data.getExtras().get("data");
            mImageView.setImageBitmap(picture);

        }

    }

     @Override
     public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
         super.onRequestPermissionsResult(requestCode, permissions, grantResults);
         switch(requestCode){
             case PERMISSION_CODE:{
                 if(grantResults.length>0&& grantResults[0]== PackageManager.PERMISSION_GRANTED)
                     pickImageFromGallery();
                 else{
                     Toast.makeText(this,"Permission denied",Toast.LENGTH_LONG).show();
                 }
             }
         }
     }
 }
