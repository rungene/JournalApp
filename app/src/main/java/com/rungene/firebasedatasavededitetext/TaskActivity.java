package com.rungene.firebasedatasavededitetext;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
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
    private FirebaseAuth mAuth;



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
        mAuth = FirebaseAuth.getInstance();


        buttonAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTask();

            }
        });

        listViewTask.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {


                Task task = taskList.get(position);
                showUpdateDialog(task.getTaskId(),task.getTaskName());

                return false;
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


    private void showUpdateDialog(final String taskId, String taskName){

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);

        LayoutInflater layoutInflater = getLayoutInflater();
        final View dialogView = layoutInflater.inflate(R.layout.update_dialog,null);
        dialogBuilder.setView(dialogView);


        final EditText editTextUpdate = (EditText)dialogView.findViewById(R.id.editTextUpdate);
        final  Button buttonUpdate = (Button)dialogView.findViewById(R.id.buttonUpdate);
        final  Spinner spinnerUpdate = (Spinner)dialogView.findViewById(R.id.spinnerUpdate);


        dialogBuilder.setTitle("Updating task"+taskName);

        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = editTextUpdate.getText().toString().trim();

                String type = spinnerUpdate.getSelectedItem().toString();

                if (TextUtils.isEmpty(name)){
                    editTextUpdate.setError("Name Required");

                    return;
                }
                updateArtist(taskId,name,type);

                alertDialog.dismiss();

            }
        });



    }
    private boolean updateArtist(String id,String name, String type){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("task").child(id);

        Task task = new Task(id,name,type);

        databaseReference.setValue(task);

        Toast.makeText(this, "Task Updated", Toast.LENGTH_SHORT).show();

        return true;


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
            mAuth.signOut();


            startActivity(new Intent(TaskActivity.this, MainActivity.class));
            finish();
            //NavUtils.navigateUpFromSameTask(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
