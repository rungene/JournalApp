package com.rungene.firebasedatasavededitetext;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AccountActivity extends AppCompatActivity {
    private Button btnLogout;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference mDatabase;
    private ListView mUserList;
   // private ArrayList<String> mUsernames = new ArrayList<>();
    private RecyclerView recyclerView;
    //progress dialog
    private ProgressDialog progressDialog;
    //list to hold all the uploaded images
    private List<Upload> uploads;

    //adapter object
    private RecyclerView.Adapter adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                if (firebaseAuth.getCurrentUser() == null){
                    startActivity(new Intent(AccountActivity.this,MainActivity.class));
                }
            }
        };

        mAuth = FirebaseAuth.getInstance();
        btnLogout = (Button) findViewById(R.id.btnLogout);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        progressDialog = new ProgressDialog(this);

        uploads = new ArrayList<>();
        //displaying progress dialog while fetching images
        progressDialog.setMessage("Please wait...");
        progressDialog.show();

        //intitializing
        mDatabase = FirebaseDatabase.getInstance().getReference();

        //adding an event listener to fetch values
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                //dismissing the progress dialog
                progressDialog.dismiss();

                //iterating through all the values in database
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    Upload upload = postSnapshot.getValue(Upload.class);
                    uploads.add(upload);
                }
                //creating adapter
                adapter = new MyAdapter(getApplicationContext(), uploads);

                //adding adapter to recyclerview
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                progressDialog.dismiss();
            }
        });




        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();

            }
        });



       /* mUserList = (ListView) findViewById(R.id.user_list);


        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,mUsernames);

        mUserList.setAdapter(arrayAdapter);*/
   /*     mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String value = dataSnapshot.getValue(String.class);

                mUsernames.add(value);

                arrayAdapter.notifyDataSetChanged();




            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/
/*        mDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String value = dataSnapshot.getValue(String.class);

                mUsernames.add(value);

                arrayAdapter.notifyDataSetChanged();





            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/



    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }
}
