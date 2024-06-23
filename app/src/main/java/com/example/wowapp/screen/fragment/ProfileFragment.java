package com.example.wowapp.screen.fragment;

import static android.content.ContentValues.TAG;

import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wowapp.R;
import com.example.wowapp.screen.EditProfileActivity;
import com.example.wowapp.screen.FollowListActivity;
import com.example.wowapp.screen.FullScreenAvatarActivity;
import com.example.wowapp.screen.MainActivity;
import com.example.wowapp.screen.SettingsAndPrivacyActivity;
import com.example.wowapp.screen.sinupActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class ProfileFragment extends Fragment   implements View.OnClickListener {

 final String USERNAME_LABEL = "username";
 private Context context ;
    private TextView txvFollowing, txvFollowers, txvLikes, txvUserName, txvMenu;
    private EditText edtBio;
    private Button btn,btnEditProfile, btnUpdateBio, btnCancelUpdateBio;
    private LinearLayout llFollowing, llFollowers;
    ImageView imvAvatarProfile;
    Uri avatarUri;
    FirebaseFirestore db;
    FirebaseAuth mAuth;
    FirebaseUser user;
    FirebaseStorage storage;
    StorageReference storageReference;
    Bitmap bitmap;
    String userId;
    DocumentReference docRef;
    String oldBioText, currentUserID;
   LinearLayout layout;



    public ProfileFragment() {
        // Required empty public constructor
    }

  public static  ProfileFragment newInstance(String strArg,  String profileLinkId) {
       ProfileFragment fragment = new ProfileFragment();
       Bundle args = new Bundle();
       args.putString("name", strArg);
       args.putString("id", profileLinkId);
       fragment.setArguments(args);
       return fragment;
}



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

       LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.fragment_profile, null);
       // return  layout;


       txvFollowing = (TextView)layout.findViewById(R.id.text_following);
       txvFollowers  = (TextView)layout.findViewById(R.id.text_followers);
       txvLikes = (TextView)layout.findViewById(R.id.text_likes);
       txvUserName = (TextView)layout.findViewById(R.id.txv_username);
       txvMenu = (TextView)layout.findViewById(R.id.text_menu);
       edtBio = (EditText)layout.findViewById(R.id.edt_bio);
       btnEditProfile =(Button)layout.findViewById(R.id.button_edit_profile);
       imvAvatarProfile = (ImageView) layout.findViewById(R.id.imvAvatarProfile);
       llFollowers = (LinearLayout) layout.findViewById(R.id.ll_followers);
       llFollowing = (LinearLayout) layout.findViewById(R.id.ll_following);
       //recVideoSummary = (RecyclerView)layout.findViewById(R.id.recycle_view_video_summary);
       btnUpdateBio = (Button) layout.findViewById(R.id.btn_update_bio);
       btnCancelUpdateBio = (Button) layout.findViewById(R.id.btn_cancel_update_bio);

       btnUpdateBio.setOnClickListener(this);
      // btnUpdateBio.setOnClickListener(this);
       //btnCancelUpdateBio.setOnClickListener(this);
    imvAvatarProfile.setOnClickListener(this);
       llFollowers.setOnClickListener(this);
       llFollowing.setOnClickListener(this);
        btnEditProfile.setOnClickListener(this);
     imvAvatarProfile.setImageURI(avatarUri);
       txvMenu.setOnClickListener(this);
        imvAvatarProfile.setOnClickListener(this);


    return  layout;


    }
/*
       private void handleFollow(){
       btn = (Button)layout.findViewById(R.id.button_follow);
       btn.setVisibility(View.VISIBLE);
       db = FirebaseFirestore.getInstance();
       docRef = db.collection("profiles").document(userId);
       docRef.get().addOnCompleteListener(task ->  {
          if (task.isSuccessful()) {
             DocumentSnapshot document = task.getResult();
             if (document.exists()) {
                txvFollowing.setText(((Long)document.get("following")).toString());

                txvFollowers.setText(((Long)document.get("followers")).toString());
                txvLikes.setText(((Long)document.get("likes")).toString());
                txvUserName.setText("@" + document.getString(USERNAME_LABEL));


             } else {}

          } else {}
       });
       if (user !=null) {
          DocumentReference docRef = db.collection("profiles").document(currentUserID)
                  .collection("following").document(userId);
          docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
             @Override
             public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                   DocumentSnapshot document = task.getResult();
                   if (document.exists()) {
                      Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                      //handleFollowed();
                   } else  {
                      Log.d(TAG, "No such document");
                     // handleUnfollowed();
                   }
                } else {
                   Log.d(TAG, "get failed with ", task.getException());
                }
             }
          });
       }
       else {
          btn.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                Intent intentMain = new Intent(context, sinupActivity.class);
                startActivity(intentMain);
             }
          });
       }
       }

 */

   @Override
   public void onClick(View v) {
     if(v.getId() == R.id.text_menu){
         showDialog();
         return;
     }
     if (v.getId()==R.id.imvAvatarProfile){
         showShareAccountDialog();
         return;
     }
     /*
     if (v.getId() == R.id.btn_temporary) {
         Intent intent = new Intent(context, MainActivity.class);
         startActivity(intent);
         return;
     }

      */

    if (v.getId() == btnEditProfile.getId()) {
       // Toast.makeText(this, "YYY", Toast.LENGTH_SHORT).show();
        moveToAnotherActivity(EditProfileActivity.class);
    }
    if(v.getId() == llFollowers.getId()) {
        Intent intent = new Intent(context,FollowListActivity.class);
        intent.putExtra("pageIndex", 1);
        startActivity(intent);
    }
    if (v.getId() == llFollowing.getId()) {
        Intent intent = new Intent(context, FollowListActivity.class);
        intent.putExtra("pageIndex", 0);

        startActivity(intent);
    }



   }

   private  void showShareAccountDialog(){
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
       dialog.setContentView(R.layout.share_account_layout);

       TextView  txvUsernameInSharedPlace = dialog.findViewById(R.id.txvUsernameInSharedPlace);
       ImageView imvAvatarInSharedPlace = dialog.findViewById(R.id.imvAvatarInSharedPlace);
       Button btnCopyURL = dialog.findViewById(R.id.btnCopyURL);
       TextView txvCancelInSharedPlace = dialog.findViewById(R.id.txvCancelInSharedPlace);

       imvAvatarInSharedPlace.setImageBitmap(bitmap);

       txvUsernameInSharedPlace.setText(txvUserName.getText());

       btnCopyURL.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

           }
       });
     imvAvatarInSharedPlace.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             Intent intent = new Intent(context, FullScreenAvatarActivity.class);
             startActivity(intent);
         }
     });

     txvCancelInSharedPlace.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             dialog.cancel();
         }
     });
     dialog.show();
     dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
     dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
     //dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
       dialog.getWindow().setGravity(Gravity.BOTTOM);


   }

   public  void  onAttach(@NonNull Context context) {
       super.onAttach(context);
       this.context = context;
   }
   private void showDialog (){
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
       dialog.setContentView(R.layout.bottom_sheet_layout);

       LinearLayout llSetting = dialog.findViewById(R.id.llSetting);
       LinearLayout llSignOut = dialog.findViewById(R.id.llSignOut);

       llSetting.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent = new Intent(context, SettingsAndPrivacyActivity.class);
               startActivity(intent);
           }
       });
/*
       llSignOut.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
              signOut(view);

               getActivity().finish();
           }
       });

 */

       dialog.show();
       dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
       dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
       //dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
       dialog.getWindow().setGravity(Gravity.BOTTOM);
   }

   public void signOut (View v) {
        FirebaseAuth.getInstance().signOut();
        if (user.getPhoneNumber() == null) {
            GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken("1019786724590-gsr32kti1mqpaaq23oggga3c2pivae8j.apps.googleusercontent.com")
                    .requestEmail()
                    .build();

            GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(getActivity(), gso);
            mGoogleSignInClient.signOut();
        }

       Intent intent = new Intent(context, MainActivity.class);
       startActivity(intent);

       getActivity().finish();
   }

   private void moveToAnotherActivity(Class<?> cls) {
       Intent intent = new Intent(context, cls);

       startActivity(intent);
   }
    void updateBio() {
        docRef.update("bio", edtBio.getText().toString());
        oldBioText = edtBio.getText().toString();
    }
}