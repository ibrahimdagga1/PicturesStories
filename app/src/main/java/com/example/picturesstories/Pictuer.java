package com.example.picturesstories;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareDialog;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class Pictuer extends AppCompatActivity {
    private static final int SELECT_PICTURE = 1;
//    private int GALLERY_REQUEST_CODE = 1;

    private CollectionReference collectionReference;
    ImageView imageView,image_facebook,image_instagram,image_download,image_share;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pictuer);

        imageView = findViewById(R.id.imageView);
        image_facebook = findViewById(R.id.picture_image_facebook);
        image_instagram = findViewById(R.id.picture_image_instagram);
        image_share = findViewById(R.id.picture_image_share);
        image_download = findViewById(R.id.picture_image_download);

        image_facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



            }
        });

        image_instagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        image_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        image_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Drawable mDrawable = imageView.getDrawable();
                Bitmap mBitmap = ((BitmapDrawable) mDrawable).getBitmap();

                String path = MediaStore.Images.Media.insertImage(getContentResolver(), mBitmap, "Image Description", null);
                Uri uri = Uri.parse(path);

                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("image/jpeg");
                intent.putExtra(Intent.EXTRA_STREAM, uri);
                startActivity(Intent.createChooser(intent, "Share Image"));

            }
        });

//        collectionReference = db.collection("Picture Project");

//        collectionReference.document(idItem).collection("names").document(idImage).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//
//            }
//        });
    }

/*    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK
                && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            File file = new File(picturePath);
            if (file.exists()) {
                uploadFileName = picturePath;
            }

            ImageView imageView = (ImageView) findViewById(R.id.imgView);
            imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
        }
    }*/
   }