package com.example.remider_app;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.example.remider_app.Model.AssignmentModel;
import com.example.remider_app.Utils.DatabaseHandler;
import com.example.remider_app.databinding.AssignmentLayoutBinding;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddNewAssignment extends BottomSheetDialogFragment {

    public static final String TAG = "ActionBottomDialog";
    private EditText newAssignmentText;

    private Button newAssignmentSaveButton;
    private Button addDateButton;
    private EditText dateText;

    private CalendarView calendarView;
    Calendar calendar = Calendar.getInstance();
    private DatabaseHandler db;

    public static AddNewAssignment newInstance() {
        return new AddNewAssignment();
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.new_assignment_layout, container, false);
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        return view;

    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        newAssignmentText = requireView().findViewById(R.id.newAssignmentText);

        newAssignmentSaveButton = requireView().findViewById(R.id.newAssignmentButton);

        dateText = view.getRootView().findViewById(R.id.dateText);

        calendar = Calendar.getInstance();

        addDateButton = requireView().findViewById(R.id.addDatebutton);

        boolean isUpdate = false;
        final Bundle bundle = getArguments();
        if (bundle != null) {
            isUpdate = true;
            String assignment = bundle.getString("assignment");
            newAssignmentText.setText(assignment);
            if (assignment == null || assignment.length() == 0) {
                newAssignmentSaveButton.setTextColor(ContextCompat.getColor(requireContext(), R.color.purple));
            } else {
                newAssignmentSaveButton.setTextColor(ContextCompat.getColor(requireContext(), R.color.purple));
            }
        }

        db = new DatabaseHandler(getActivity());
        db.openDatabase();

        newAssignmentText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().equals("")){
                    newAssignmentSaveButton.setEnabled(false);
                    newAssignmentSaveButton.setTextColor(Color.GRAY);

                }
                else {
                    newAssignmentSaveButton.setEnabled(true);
                    newAssignmentSaveButton.setTextColor(ContextCompat.getColor(requireContext(),R.color.purple));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        boolean finalIsUpdate = isUpdate;
        newAssignmentSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = newAssignmentText.getText().toString();
                if(finalIsUpdate) {
                    db.updateTask(bundle.getInt("id"), text);
                }
                else {
                    AssignmentModel assignment = new AssignmentModel();
                    assignment.setAssignment(text);
                    assignment.setStatus(0);
                    db.insertAssignment(assignment);

                }
                dismiss();
            }
        });
        addDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DateFormat df = new SimpleDateFormat("dd/ MM/ yyyy");
                String dateToday = df.format(Calendar.getInstance().getTime());
                dateText.setText(dateToday);
            }
        });
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {

        super.onDismiss(dialog);
        Activity activity = getActivity();
        if (activity instanceof DialogCloseListener) {
            ((DialogCloseListener) activity).handleDialogClose(dialog);
        }
    }

}

