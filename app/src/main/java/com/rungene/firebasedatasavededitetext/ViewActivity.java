package com.rungene.firebasedatasavededitetext;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class ViewActivity extends AppCompatActivity {
    private DatabaseReference mDatabase;
    private ListView mUserList;
    private ArrayList<String> mUsername = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

/*        mTextView = (TextView) findViewById(R.id.name_view);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Name");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String name =dataSnapshot.getValue().toString();

                mTextView.setText("Name "+name);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/
 /*       mDatabase = FirebaseDatabase.getInstance().getReference();

        mUserList = (ListView) findViewById(R.id.user_list);

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,mUsername);

        mUserList.setAdapter(arrayAdapter);


        mDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String value = dataSnapshot.getValue(String.class);
                mUsername.add(value);
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
}
