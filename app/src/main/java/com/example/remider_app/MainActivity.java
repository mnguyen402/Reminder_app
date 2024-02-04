package com.example.remider_app;

import android.content.DialogInterface;
import android.os.Bundle;

import com.example.remider_app.Adapter.AssignmentAdapter;
import com.example.remider_app.Model.AssignmentModel;
import com.example.remider_app.Utils.DatabaseHandler;
import com.example.remider_app.databinding.FragmentFirstBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;

import androidx.core.view.WindowCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.remider_app.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity implements DialogCloseListener{

    private AssignmentAdapter varAssignmentAdapter;
    private FloatingActionButton fab2;
    private Button ButtonSecond;
    private List<AssignmentModel> assignmentList;
    private AppBarConfiguration appBarConfiguration;
    private DatabaseHandler db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        db = new DatabaseHandler(this);
        db.openDatabase();


        assignmentList = new ArrayList<>();
        RecyclerView assignmentRecyclerView = findViewById(R.id.assignmentRecyclerView);
        assignmentRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        varAssignmentAdapter = new AssignmentAdapter(db, this);
        assignmentRecyclerView.setAdapter(varAssignmentAdapter);

        ItemTouchHelper itemTouchHelper = new
                ItemTouchHelper(new RecyclerItemTouchHelper(varAssignmentAdapter));
        itemTouchHelper.attachToRecyclerView(assignmentRecyclerView);

        fab2 = findViewById(R.id.fab2);


        assignmentList = db.getAllAssignments();
        Collections.reverse(assignmentList);
        varAssignmentAdapter.setAssignment(assignmentList);

        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddNewAssignment.newInstance().show(getSupportFragmentManager() ,AddNewAssignment.TAG);
            }
        });
    }

    @Override
    public void handleDialogClose(DialogInterface dialog) {

        assignmentList = db.getAllAssignments();
        Collections.reverse(assignmentList);
        varAssignmentAdapter.setAssignment(assignmentList);
        varAssignmentAdapter.notifyDataSetChanged();


    }
}