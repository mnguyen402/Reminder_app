package com.example.remider_app;

import android.os.Bundle;

import com.example.remider_app.Adapter.AssignmentAdapter;
import com.example.remider_app.Model.AssignmentModel;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.core.view.WindowCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.remider_app.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView assignmentRecyclerView;
    private AssignmentAdapter varAssignmentAdapter;
    private List<AssignmentModel> assignmentList;
    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_second);

        assignmentList = new ArrayList<>();

        assignmentRecyclerView = findViewById(R.id.assignmentRecyclerView);
        assignmentRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        varAssignmentAdapter = new AssignmentAdapter(this);
        assignmentRecyclerView.setAdapter(varAssignmentAdapter);

        AssignmentModel assignment = new AssignmentModel();
        assignment.setAssignment("This is a test");
        assignment.setStatus(0);
        assignment.setId(1);

        assignmentList.add(assignment);
        assignmentList.add(assignment);
        assignmentList.add(assignment);
        assignmentList.add(assignment);
        assignmentList.add(assignment);

        varAssignmentAdapter.setAssignment(assignmentList);



    }
}