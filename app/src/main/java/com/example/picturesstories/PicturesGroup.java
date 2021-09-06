package com.example.picturesstories;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
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
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.thekhaeng.recyclerviewmargin.LayoutMarginDecoration;

import java.util.ArrayList;
import java.util.List;

public class PicturesGroup extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    private static final String TAG = "PicturesGroup";
    private static final String KEY_IMAGE = "image";
    private FirebaseFirestore db;
    private CollectionReference collectionReference;
    private DocumentReference noteRef ;
    ImageView image;
    String uriToneReq = "";
    private FirebaseAuth auth;
    private Uri mImageUri;
    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;
    private StorageTask mUploadTask;
    private List<ViewImage> mViewImage;

    private RecyclerView mRecyclerView;
    private GroupAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private GridLayoutManager layoutManager;
    private String idItem , titleGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_group);


        if (!getIntent().getStringExtra("idItem").equals(null)&&!getIntent().getStringExtra("titleGroup").equals(null) ){
            idItem = getIntent().getStringExtra("idItem");
            titleGroup = getIntent().getStringExtra("titleGroup");
        }
        db  = FirebaseFirestore.getInstance();
        collectionReference = db.collection("Picture Project");
        mRecyclerView = findViewById(R.id.group_image_recycler);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mViewImage = new ArrayList<>();

        auth = FirebaseAuth.getInstance();

        mStorageRef = FirebaseStorage.getInstance().getReference("uploads");
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("uploads");

        if (idItem != null) {
            collectionReference.document(idItem).collection(titleGroup).get().
                    addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    List<ViewImage> mViewImage = new ArrayList<>();
                    if (task.isSuccessful()) {
                        for (DocumentSnapshot snapshots : task.getResult()) {
                            ViewImage viewImage = snapshots.toObject(ViewImage.class);
                            mViewImage.add(viewImage);


                        }
                        mAdapter = new GroupAdapter(PicturesGroup.this, mViewImage, new GroupAdapter.setOnClickListenerGroupPicture() {

                            @Override
                            public void onClickItem(String idItem) {
                                Intent intent = new Intent(getBaseContext(), Pictuer.class);
                                intent.putExtra("idItem",idItem);
                                startActivity(intent);
                            }

                        });
                        mRecyclerView.setAdapter(mAdapter);
                    }
                }
            });
        }else {
//            progress
        }

        mLayoutManager = new GridLayoutManager(this,2,RecyclerView.VERTICAL,false);
        mRecyclerView.setLayoutManager(mLayoutManager);
//        mRecyclerView.addItemDecoration( new LayoutMarginDecoration( 2, 40 ) );
    }
}