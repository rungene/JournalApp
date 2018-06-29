package com.rungene.firebasedatasavededitetext;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class MainActivity extends AppCompatActivity {
 /*   private Button mFirebaseBtn;
    private Button savedData;
    private DatabaseReference mDatabase;
    private EditText mNameField;
    private EditText mEmailFiled;*/
 private SignInButton mGoogleBtn;
    private static final int RC_SIGN_IN = 1;
    private GoogleApiClient mGoogleApiClient;
    private FirebaseAuth mAuth;
    private static final String TAG = "MAIN_ACTIVITY";
    private FirebaseAuth.AuthStateListener mAuthListener;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mGoogleBtn = (SignInButton) findViewById(R.id.googleBtn);
        mAuth = FirebaseAuth.getInstance();
        progressDialog =new ProgressDialog(this);
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() != null){
                    startActivity(new Intent(MainActivity.this,AccountActivity.class));

                }
            }
        };

        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(getApplicationContext())
                .enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

                        Toast.makeText(MainActivity.this, "You got an error", Toast.LENGTH_SHORT).show();
                    }
                }).addApi(Auth.GOOGLE_SIGN_IN_API,gso)
                .build();

        mGoogleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });





/*
        mFirebaseBtn = (Button) findViewById(R.id.firebase_btn);
        savedData = (Button) findViewById(R.id.savedData);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mNameField = (EditText) findViewById(R.id.name_field);
        mEmailFiled = (EditText) findViewById(R.id.email_filed);

        savedData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,ViewActivity.class);
                startActivity(intent);

            }
        });


        mFirebaseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //1-create a child in root object
                //2-assign a value to that child object


                final String name = mNameField.getText().toString().trim();
                String email = mEmailFiled.getText().toString().trim();
                if (name.length()<1){
                    mNameField.setError("Enter name");
                }else if (email.length()<1){
                    mEmailFiled.setError("Enter email");
                }
                else {

                final HashMap<String,String> dataMap = new HashMap<String, String>();
                dataMap.put("Name",name);
                dataMap.put("Email",email);
                mDatabase.push().setValue(dataMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(MainActivity.this, "Stored", Toast.LENGTH_SHORT).show();

                        }else {
                            Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
                        }

                    }
                });}

              mNameField.setText("");
                mEmailFiled.setText("");

            }

        });*/


    }


    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }
    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);
            } else {
                // Google Sign In failed, update UI appropriately
                // ...
            }
        }
    }


    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                           // updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }

                        // ...
                    }
                });

    }

}