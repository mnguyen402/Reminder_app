package com.example.remider_app.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.recyclerview.widget.RecyclerView;

import com.example.remider_app.MainActivity;
import com.example.remider_app.Model.AssignmentModel;
import com.example.remider_app.R;

import java.util.List;

public class AssignmentAdapter extends RecyclerView.Adapter<AssignmentAdapter.ViewHolder> {
    private List<AssignmentModel> assignmentList;
    private MainActivity activity;

    public AssignmentAdapter(MainActivity activity) {
        this.activity = activity;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.assignment_layout, parent, false);
        return new ViewHolder(itemView);
    }

    public void onBindViewHolder(ViewHolder holder, int position) {
        AssignmentModel item = assignmentList.get(position);
        holder.assignment.setText(item.getAssignment());
        holder.assignment.setChecked(toBoolean(item.getStatus()));
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

    public static class ViewHolder extends RecyclerView.ViewHolder {
        CheckBox assignment;

        ViewHolder(View view) {
            super(view);
            assignment = view.findViewById(R.id.assignmentCheckBox);
        }
    }
}
