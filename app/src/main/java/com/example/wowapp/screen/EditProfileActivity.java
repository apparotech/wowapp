package com.example.wowapp.screen;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wowapp.R;
import com.example.wowapp.screen.helper.StaticVariable;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class EditProfileActivity extends AppCompatActivity implements View.OnClickListener{
    private TextView tvUsername, tvPhone, tvEmail, tvBirthdate;
    private ImageButton imbPhoto, imbSelect,  imbUsername, imbBirthdate;
    private LinearLayout llEditProfile, llChangePhoto, llPhone, llEmail;
    private FirebaseFirestore db;
    private Uri avatarUri;
    private final int SELECT_IMAGE_CODE = 10;
    private ImageView imvBackToProfile;
    private Dialog dialog;
    private FirebaseStorage storage;
    private StorageReference storageReference;

    private final String TAG = "EditProfileActivity";

    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        llEditProfile = (LinearLayout) findViewById(R.id.llEditProfile);
        llChangePhoto = (LinearLayout) llEditProfile.findViewById(R.id.llChangePhoto);
        llPhone = (LinearLayout) llEditProfile.findViewById(R.id.llPhone);
        llEmail = (LinearLayout) llEditProfile.findViewById(R.id.llEmail);
        tvUsername = (TextView) llEditProfile.findViewById(R.id.tvUsername);
        tvPhone = (TextView) llEditProfile.findViewById(R.id.tvPhone);
        tvEmail = (TextView) llEditProfile.findViewById(R.id.tvEmail);
        tvBirthdate = (TextView) llEditProfile.findViewById(R.id.tvBirthdate);
        imbPhoto = (ImageButton) llEditProfile.findViewById(R.id.imbPhoto);
        imbSelect = (ImageButton) llEditProfile.findViewById(R.id.imbSelect);
        imbUsername = (ImageButton) llEditProfile.findViewById(R.id.imbUsername);
        imbBirthdate = (ImageButton) llEditProfile.findViewById(R.id.imbBirthdate);
        imvBackToProfile = (ImageView) findViewById(R.id.imvBackToProfile);
        imbSelect.setVisibility(View.GONE);

        imbPhoto.setOnClickListener(this);
        imbSelect.setOnClickListener(this);
        imvBackToProfile.setOnClickListener(this);
        imbUsername.setOnClickListener(this);
        imbBirthdate.setOnClickListener(this);

        db = FirebaseFirestore.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
       builder.setView(R.layout.dialog_progress);
        dialog = builder.create();
      dialog.show();
    }

    protected void onStart(){
        super.onStart();
        if (user != null) {
            if (user.getPhoneNumber().isEmpty()) {
                llPhone.setVisibility(View.GONE);
                llEmail.setVisibility(View.VISIBLE);
            } else {
                llPhone.setVisibility(View.VISIBLE);
                llEmail.setVisibility(View.GONE);
            }
            DocumentReference docRef = db.collection("users").document(user.getUid().toString());
            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            tvUsername.setText(getData(document.get("username")));
                            tvPhone.setText(getData(document.get("phone")));
                            tvEmail.setText(getData(document.get("email")));
                            tvBirthdate.setText(getData(document.get("birthdate")));
                            dialog.dismiss();
                            Log.d(TAG, "DocumentSnapshot data: " + document.get("following"));
                        } else {
                            Log.d(TAG, "No such document");
                        }
                    } else {
                        Log.d(TAG, "get failed with ", task.getException());
                    }
                }
            });
        }
    }
    private String getData(Object data) {
        return data == null ? "" : data.toString();
    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SELECT_IMAGE_CODE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            avatarUri = data.getData();
            uploadAvatar();
        }
    }

    private void uploadAvatar(){
        ProgressDialog progress = new ProgressDialog(EditProfileActivity.this);
        progress.setTitle("Loading");
        progress.setMessage("Please Wait...");
        progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
        progress.show();

        StorageReference upload = storageReference.child("/user_avatars").child(user.getUid().toString());
        upload.putFile(avatarUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        progress.dismiss();

                        Toast.makeText(EditProfileActivity.this, "Image Uploaded", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(EditProfileActivity.this, "Image Failed", Toast.LENGTH_SHORT).show();
                        progress.dismiss();
                    }
                });
    }




    public void onClick(View v) {
        if (v.getId() == imbPhoto.getId()) {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Title"), SELECT_IMAGE_CODE);
        }
        if (v.getId()== imvBackToProfile.getId()) {
            finish();
        }
        if (v.getId() == imbUsername.getId()) {

            moveToEdit(StaticVariable.USERNAME, tvUsername.getText().toString());
        }
        if(v.getId() == imbBirthdate.getId()) {
            moveToEdit(StaticVariable.BIRTHDATE, tvBirthdate.getText().toString());
        }
    }
    private void moveToEdit(String mode, String content) {
        Intent intent = new Intent(this,EditActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("mode", mode);
        bundle.putString("content", content);
        intent.putExtras(bundle);
        startActivity(intent);
        finish();

    }
}

