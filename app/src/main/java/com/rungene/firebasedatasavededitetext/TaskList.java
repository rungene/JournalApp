package com.rungene.firebasedatasavededitetext;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class TaskList extends ArrayAdapter<Task>{

    private Activity context;
    private List<Task>  taskList;

    public TaskList(Activity context,List<Task> taskList) {
        super(context,R.layout.list_layout,taskList);

        this.context = context;
        this.taskList = taskList;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater =context.getLayoutInflater();

        View listItemView = layoutInflater.inflate(R.layout.list_layout,null,true);

        TextView textViewName = (TextView)listItemView.findViewById(R.id.textViewName);

        TextView textViewType = (TextView)listItemView.findViewById(R.id.textViewType);

        Task task = taskList.get(position);

        textViewName.setText(task.getTaskName());
        textViewType.setText(task.getTaskName());

        return listItemView;
    }
}
