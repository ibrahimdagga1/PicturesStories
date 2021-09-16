package com.example.picturesstories;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    private static final String TAG = "MainActivity";
    private static final String KEY_TITLE = "title";
    private static final String KEY_IMAGE = "image";
    private FirebaseFirestore db;
    private CollectionReference collectionReference;
    private DocumentReference noteRef ;
    ImageView image;
    EditText et_title;
    String uriToneReq = "";
    private FirebaseAuth auth;
    private Uri mImageUri;
    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;
    private StorageTask mUploadTask;
    private List<AddImage> mAddImage;

    private RecyclerView mRecyclerView;
    private MainAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private GridLayoutManager layoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db  = FirebaseFirestore.getInstance();
        collectionReference = db.collection("Picture Project");
        mRecyclerView = findViewById(R.id.recycler);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAddImage = new ArrayList<>();

        auth = FirebaseAuth.getInstance();

        mStorageRef = FirebaseStorage.getInstance().getReference("uploads");
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("uploads");

        collectionReference.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                List<AddImage> mAddImages = new ArrayList<>();
                if (task.isSuccessful()){
                    for (QueryDocumentSnapshot snapshots : task.getResult()){
                        AddImage addImage = snapshots.toObject(AddImage.class);
                        mAddImages.add(addImage);


                    }
                    mAdapter = new MainAdapter(MainActivity.this, mAddImages, new MainAdapter.setOnClickListenerMainPicture() {
                        @Override
                        public void onClickItem(String idItem , String titleGroup) {
                            Intent intent = new Intent(getBaseContext(), PicturesGroup.class);
                            intent.putExtra("idItem",idItem);
                            intent.putExtra("titleGroup",titleGroup);
                            startActivity(intent);
                        }
                    });
                    mRecyclerView.setAdapter(mAdapter);
                }
            }
        });


        mLayoutManager = new GridLayoutManager(this,2);
        mRecyclerView.setLayoutManager(mLayoutManager);
//        mRecyclerView.addItemDecoration( new LayoutMarginDecoration( 2, 10 ) );


    }
}