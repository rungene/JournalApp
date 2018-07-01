package com.rungene.firebasedatasavededitetext;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class TaskActivity extends AppCompatActivity {

    EditText editTextName;
    Button buttonAddTask;
    Spinner spinnerTask;

    DatabaseReference databaseTask;

    ListView listViewTask;
    List<Task> taskList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);


        databaseTask = FirebaseDatabase.getInstance().getReference("task");
        editTextName = (EditText) findViewById(R.id.editTextName);
        buttonAddTask = (Button) findViewById(R.id.buttonAddTask);
        spinnerTask = (Spinner) findViewById(R.id.spinnerTask);
        listViewTask = (ListView) findViewById(R.id.listViewTask);
        taskList = new ArrayList<>();


        buttonAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTask();

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        databaseTask.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                taskList.clear();
                for (DataSnapshot taskSnapShot: dataSnapshot.getChildren()){
                    Task task = taskSnapShot.getValue(Task.class);
                    taskList.add(task);

                }
                TaskList adapter = new TaskList(TaskActivity.this,taskList);
                listViewTask.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void addTask(){

        String name = editTextName.getText().toString().trim();
        String type = spinnerTask.getSelectedItem().toString();

        if (!TextUtils.isEmpty(name)){
           String id = databaseTask.push().getKey();

           Task task = new Task(id,name,type);

           databaseTask.child(id).setValue(task);

            Toast.makeText(this, "Task Added", Toast.LENGTH_LONG).show();


        }else {
            Toast.makeText(this, "Enter a name", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will

        int id = item.getItemId();

        if (id == R.id.action_logout) {
            // launch MainActivityAdditions activity
            startActivity(new Intent(TaskActivity.this, AccountActivity.class));
            //NavUtils.navigateUpFromSameTask(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
