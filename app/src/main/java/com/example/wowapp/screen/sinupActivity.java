package com.example.wowapp.screen;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import com.example.wowapp.R;

import com.example.wowapp.screen.model.Profile;
import com.example.wowapp.screen.model.User;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthCredential;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class sinupActivity extends AppCompatActivity {
    private static final String TAG = "EmailSignUpActivity";
    private static final int RC_SIGN_IN = 9001;
    SignInButton btSignIn;
    private  GoogleSignInClient mGoogleSignInClient;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private String msg;
    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sinup);

        btSignIn = findViewById(R.id.bt_sign_in);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("1019786724590-gsr32kti1mqpaaq23oggga3c2pivae8j.apps.googleusercontent.com")
                .requestEmail()
                .build();

        //googleSignInClient = GoogleSignIn.getClient(sinupActivity.this, googleSignInOptions);
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        mGoogleSignInClient.signOut();

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false); // if you want user to wait for some process to finish,
        builder.setView(R.layout.dialog_progress);
        dialog = builder.create();

        signUp();

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount>task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                dialog.show();
                handleSignUp(account);
            } catch (ApiException e) {
                Log.w(TAG, "Google sign in failed", e);
            }
        }

    }
    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG,  "signInWithCredential:success");
                            dialog.dismiss();
                            FirebaseUser firebaseUser = mAuth.getCurrentUser();
                            String id = firebaseUser.getUid();
                            String username = id.substring(0, Math.min(id.length(), 6));
                            User user = new User(id, username, "", firebaseUser.getEmail());
                            writeNewUser(user);
                            Profile profile = new Profile(id, username);
                            writeNewProfile(profile);

                            moveToAnotherActivity(MainActivity.class);
                        } else {
                            dialog.dismiss();
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            moveToAnotherActivity(sinupActivity.class);
                        }
                    }
                });
    }

    private void signUp() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();

        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void writeNewUser(User user) {
        Map<String, Object> userValues = user.toMap();
        Map<String, Object> childUpdates = new HashMap<>();

        db.collection("users").document(user.getUserId())
                .set(userValues)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d(TAG, "DocumentSnapshot successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error writing document", e);
                    }
                });
    }



    private void writeNewProfile(Profile profile) {
        Map<String, Object> userValues = profile.toMap();
        final String TAG = "ADD";
        Map<String, Object> childUpdates = new HashMap<>();
        db.collection("profiles").document(profile.getUserId())
                .set(userValues)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d(TAG, "DocumentSnapshot successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error writing document", e);
                    }
                });

        Map<String, Object> Data1 = new HashMap<>();
        Data1.put("userID","dump");

        db.collection("profiles").document(profile.getUserId())
                .collection("following").document("dump")
                .set(Data1)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d(TAG, "DocumentSnapshot successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error writing document", e);
                    }
                });

             db.collection("profiles").document(profile.getUserId())
                     .collection("followers").document("dump")
                     .set(Data1)
                     .addOnSuccessListener(new OnSuccessListener<Void>() {
                         @Override
                         public void onSuccess(Void unused) {
                             Log.d(TAG, "DocumentSnapshot successfully written!");
                         }
                     })
                     .addOnFailureListener(new OnFailureListener() {
                         @Override
                         public void onFailure(@NonNull Exception e) {
                             Log.w(TAG, "Error writing document", e);
                         }
                     });
    }

    private void moveToAnotherActivity(Class<?> cls) {
        Intent intent = new Intent(sinupActivity.this, cls);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);

        finish();
    }

    private void handleSignUp(GoogleSignInAccount account) {
        db.collection("users")
                .whereEqualTo("email", account.getEmail())
                .get().addOnCompleteListener(task -> {
                    msg = "FALSE";
                    if (task.isSuccessful()) {
                        for (DocumentSnapshot document : task.getResult()) {
                            if (document.exists()) {
                                msg = "TRUE";
                                break;
                            }
                        }

                    } else {
                        Log.d("TAG", "Error getting documents: ", task.getException());
                    }

                    if (msg.equals("FALSE")) {
                        firebaseAuthWithGoogle(account.getIdToken());
                    } else {
                        dialog.dismiss();
                        Toast.makeText(sinupActivity.this, getString(R.string.error_existedEmail), Toast.LENGTH_SHORT).show();
                        moveToAnotherActivity(CameraActivity.class);
                    }
                });

    }


}