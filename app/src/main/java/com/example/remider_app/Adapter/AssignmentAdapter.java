package com.example.remider_app.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.recyclerview.widget.RecyclerView;

import com.example.remider_app.AddNewAssignment;
import com.example.remider_app.MainActivity;
import com.example.remider_app.Model.AssignmentModel;
import com.example.remider_app.R;
import com.example.remider_app.Utils.DatabaseHandler;

import java.util.List;

public class AssignmentAdapter extends RecyclerView.Adapter<AssignmentAdapter.ViewHolder> {
    private List<AssignmentModel> assignmentList;
    private MainActivity activity;
    private DatabaseHandler db;

    public AssignmentAdapter(DatabaseHandler db, MainActivity activity) {
        this.db = db;
        this.activity = activity;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.assignment_layout, parent, false);
        return new ViewHolder(itemView);
    }

    public void onBindViewHolder(final ViewHolder holder, int position) {
        db.openDatabase();
        final AssignmentModel item = assignmentList.get(position);
        holder.assignment.setText(item.getAssignment());
        holder.assignment.setChecked(toBoolean(item.getStatus()));
        holder.assignment.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    db.updateStatus(item.getId(), 1);
                }
                else {
                    db.updateStatus(item.getId(),0);
                }
            }
        });
    }

    public int getItemCount() {
        return assignmentList.size();
    }
    private boolean toBoolean(int n) {
        return n!=0;
    }

    public void setAssignment(List<AssignmentModel> assignmentList) {
        this.assignmentList = assignmentList;
        notifyDataSetChanged();
    }
    public void deleteItem(int position) {
        AssignmentModel item = assignmentList.get(position);
        db.deleteTask(item.getId());
        assignmentList.remove(position);
        notifyItemRemoved(position);

    }



    public void editItem(int position) {
        AssignmentModel item = assignmentList.get(position);
        Bundle bundle = new Bundle();
        bundle.putInt("id", item.getId());
        bundle.putString("assignment_task", item.getAssignment());
        AddNewAssignment fragment = new AddNewAssignment();
        fragment.setArguments(bundle);
        fragment.show(activity.getSupportFragmentManager(), AddNewAssignment.TAG);
    }

    public Context getContext(){
        return activity;
    }



    public static class ViewHolder extends RecyclerView.ViewHolder {
        CheckBox assignment;

        ViewHolder(View view) {
            super(view);
            assignment = view.findViewById(R.id.assignmentCheckBox);
        }
    }
}
