package com.example.easystartup.Startup;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.easystartup.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.HashMap;

public class startupIdea extends AppCompatActivity implements View.OnClickListener {

    private static final int PICK_IMAGE_REQUEST = 234;
    EditText ideaTitle, ideaSubtitle, ideadesc;
    Button btnChoose, btnSave;
    ImageView choosedImage;

    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    DatabaseReference mDataBaseRef = FirebaseDatabase.getInstance().getReference();

    private Uri filePath;

    FirebaseStorage storage;
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startup_idea);

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        ideaTitle = (EditText)findViewById(R.id.ideaTitle);
        ideaSubtitle = (EditText)findViewById(R.id.ideaSubtitle);
        ideadesc = (EditText)findViewById(R.id.ideaDesc);
        btnChoose = (Button) findViewById(R.id.btnChoose);
        btnSave = (Button) findViewById(R.id.btnSave);
        choosedImage = (ImageView)findViewById(R.id.choosedImage);

        btnChoose.setOnClickListener(this);
        btnSave.setOnClickListener(this);

    }


    private void showFileChooser()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"select an image"),PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==PICK_IMAGE_REQUEST && resultCode==RESULT_OK && data!=null && data.getData()!=null)
        {
            filePath = data.getData();
            Log.d("imgelink","this is"+filePath);
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),filePath);
                choosedImage.setImageBitmap(bitmap);
            }
            catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    private void uploadImage() {

        final String userUniqueId = firebaseAuth.getCurrentUser().getUid();

        final String title = ideaTitle.getText().toString();
        final String subTitle = ideaSubtitle.getText().toString();
        final String Desc = ideadesc.getText().toString();
        final HashMap<String, String> map = new HashMap<>();

        map.put("Title", title);
        map.put("Subtitle", subTitle);
        map.put("Description", Desc);

        if (TextUtils.isEmpty(title))
        {
            Toast.makeText(this, "Pls enter title",Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(subTitle))
        {
            Toast.makeText(this, "Pls enter subTitle", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(Desc))
        {
            Toast.makeText(this,"Pls enter description",Toast.LENGTH_LONG).show();
            return;
        }

        if (filePath!=null)
        {
            final StorageReference ref = storageReference.child("Enterpreneur").child(userUniqueId).child("Details");
            ref.putFile(filePath).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    // get the image Url of the file uploaded
                    ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            // getting image uri and converting into string
                            Uri downloadUrl = uri;
                            String fileUrl = downloadUrl.toString();
                                                   map.put("image", fileUrl);

                            mDataBaseRef.child("Enterpreneur").child(userUniqueId).child("Details").setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(startupIdea.this, "data is saved..", Toast.LENGTH_SHORT).show();
                                        firebaseAuth.signOut();
                                        startActivity(new Intent(startupIdea.this, StartUptSignIn.class));
                                        finish();
                                    } else {
                                        Toast.makeText(startupIdea.this, "Failed..", Toast.LENGTH_SHORT).show();
                                    }
                                    finish();
                                }
                            });
                        }
                    });
                }
            });
        }

    }

    @Override
    public void onClick(View v) {
        if (v==btnChoose)
        {
            showFileChooser();
        }
        else if (v==btnSave)
        {
            uploadImage();
        }

    }
}
