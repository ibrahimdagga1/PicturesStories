package com.example.picturesstories;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.share.Share;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareHashtag;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.model.ShareStoryContent;
import com.facebook.share.widget.MessageDialog;
import com.facebook.share.widget.ShareButton;
import com.facebook.share.widget.ShareDialog;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;

import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

public class Pictuer extends AppCompatActivity {
    private static final int SELECT_PICTURE = 1;
//    private int GALLERY_REQUEST_CODE = 1;

    private CollectionReference collectionReference;
    ImageView imageView,image_facebook,image_instagram,image_download,image_share;
    OutputStream outputStream;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pictuer);

        ActivityCompat.requestPermissions(Pictuer.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
        ActivityCompat.requestPermissions(Pictuer.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);

        imageView = findViewById(R.id.imageView);
        image_facebook = findViewById(R.id.picture_image_facebook);
        image_instagram = findViewById(R.id.picture_image_instagram);
        image_share = findViewById(R.id.picture_image_share);
        image_download = findViewById(R.id.picture_image_download);

        image_facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                ShareDialog shareDialog = new ShareDialog(getParent());

                Bitmap h = BitmapFactory.decodeResource(getResources(),R.id.picture_image_facebook);

                SharePhoto photo = new SharePhoto.Builder()
                        .setBitmap(h)
                        .build();

                SharePhotoContent contentP = new SharePhotoContent.Builder()
                        .addPhoto(photo)
                        .build();
                ShareDialog.show(Pictuer.this,contentP);
                MessageDialog.show(Pictuer.this, contentP);





           /*     ShareHashtag shareHashTag = new ShareHashtag.Builder().setHashtag("#YOUR_HASHTAG").build();
                ShareLinkContent shareLinkContent = new ShareLinkContent.Builder()
                        .setShareHashtag(shareHashTag)
                        .setQuote("Your Description")
                        .setContentUrl(Uri.parse("image or logo [if playstore or app store url then no need of this image url]"))
                        .build();*/

//                shareButton.setShareContent(content);
//                ShareDialog.show(Pictuer.this, content);

            }
        });

        image_instagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



/*
                Uri uri = Uri.fromFile(new File());
                Intent shareIntent = new Intent(android.content.In tent.ACTION_SEND);
                shareIntent.setType("image/*");
                shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
                shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
                shareIntent.setPackage("com.instagram.android"); //Instagram App package
                startActivity(Intent.createChooser(shareIntent, "Share.."));*/

            }

        });

        image_download.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {

//                saveToGallery();


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

    public class Picture extends FragmentActivity {
        CallbackManager callbackManager;
        ShareDialog shareDialog;
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            callbackManager = CallbackManager.Factory.create();
            shareDialog = new ShareDialog(this);
            shareDialog.registerCallback(callbackManager, new FacebookCallback<Sharer.Result>() {
                @Override
                public void onSuccess(Sharer.Result result) {
                    Toast.makeText(Picture.this, "You shared this post", Toast.LENGTH_SHORT).show();

                }

                @Override
                public void onCancel() {

                }

                @Override
                public void onError(FacebookException error) {
                    error.printStackTrace();

                }
            });
        }

        @Override
        protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }



    private void saveToGallery(){


        BitmapDrawable bitmapDrawable = (BitmapDrawable) imageView.getDrawable();
        Bitmap bitmap = bitmapDrawable.getBitmap();
        FileOutputStream outputStream = null ;
        File file = Environment.getExternalStorageDirectory();
        File dir = new File(file.getAbsolutePath() + "/MyPics");
        dir.mkdirs();

        String filename = String.format("%d.png",System.currentTimeMillis());
        File outFile = new File(dir,filename);
        try{
            outputStream = new FileOutputStream(outFile);
            Toast.makeText(this, "تم الإضافة", Toast.LENGTH_SHORT).show();
            bitmap.compress(Bitmap.CompressFormat.PNG,100,outputStream);

        }catch (Exception e){
            e.printStackTrace();
        }
        try{
            outputStream.flush();
        }catch (Exception e){
            e.printStackTrace();
        }
        try{
            outputStream.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }

   /*     File filepath = Environment.getExternalStorageDirectory();
        File dir = new File(filepath.getAbsolutePath()+"/Demo/");
        dir.mkdir();
        File file = new File(dir,System.currentTimeMillis()+".jpg");

        try {
            outputStream = new FileOutputStream(file);
        }catch (FileNotFoundException e ){
            e.printStackTrace();
        }
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,outputStream);
        Toast.makeText(this, "Image save", Toast.LENGTH_SHORT).show();
        try {
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
*/


   }

/*    public static boolean shareBitmap(Context context, Bitmap bitmap, String textToShare, String packageNameOfApp,
                                      String intentMessage) {
        try {
            File file = new File(context.getCacheDir(), "Share.jpg");
            saveBitmapAtLocation(bitmap, Bitmap.CompressFormat.JPEG, 100, file);
            Uri contentUri = FileProvider.getUriForFile(context, context.getPackageName() + ".fileprovider", file);
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_SEND);
            intent.putExtra(Intent.EXTRA_STREAM, contentUri);
            String textToAppend = null;
            textToAppend = context.getString(R.string.shortenUrl);
            if (textToShare != null && !textToShare.isEmpty())
                intent.putExtra(Intent.EXTRA_TEXT, textToShare + " " + textToAppend);
            else
                intent.putExtra(Intent.EXTRA_TEXT, textToAppend);
            intent.setType("image/*");
            List<ResolveInfo> resInfoList = context.getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
            for (ResolveInfo resolveInfo : resInfoList) {
                String packageName = resolveInfo.activityInfo.packageName;
                context.grantUriPermission(packageName, contentUri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
            }
            if (packageNameOfApp != null) {
                if (appInstalledOrNot((Activity) context, packageNameOfApp))
                    intent.setPackage(packageNameOfApp);
                else
                    return false;
            }

            context.startActivity(Intent.createChooser(intent, intentMessage));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    private static void saveBitmapAtLocation(Bitmap bitmap, Bitmap.CompressFormat jpeg, int i, File file) {

    }

    private static boolean appInstalledOrNot(Activity context, String packageNameOfApp) {
            return false;
    }*/
}