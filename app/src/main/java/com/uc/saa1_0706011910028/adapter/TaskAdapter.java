package com.uc.saa1_0706011910028.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.uc.saa1_0706011910028.R;
import com.uc.saa1_0706011910028.model.Task;

import java.util.ArrayList;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.CardViewViewHolder>{

    private Context context;
    String type;
    private ArrayList<Task> listTask;
    private ArrayList<Task> getlistTask() {
        return listTask;
    }
    public void setListTask(ArrayList<Task> listTask) {
        this.listTask = listTask;
    }
    public TaskAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public TaskAdapter.CardViewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_adapter, parent, false);
        return new TaskAdapter.CardViewViewHolder(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull final TaskAdapter.CardViewViewHolder holder, int position) {
        final Task task = getlistTask().get(position);
        holder.tasktitle.setText(task.getTitle());
        holder.taskduedate.setText(task.getDuedate());
        holder.tasktype.setText(task.getTasktype());
        holder.taskdesc.setText(task.getDesc());
    }

    @Override
    public int getItemCount() {
        return getlistTask().size();
    }

    class CardViewViewHolder extends RecyclerView.ViewHolder{
        TextView tasktitle, taskduedate, tasktype, taskdesc;

        CardViewViewHolder(View itemView) {
            super(itemView);
            tasktitle = itemView.findViewById(R.id.taskTitle);
            taskduedate = itemView.findViewById(R.id.taskDueDate);
            tasktype = itemView.findViewById(R.id.taskType);
            taskdesc = itemView.findViewById(R.id.taskDesc);

        }
    }
}