package com.example.remider_app;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.remider_app.databinding.FragmentSecondBinding;

public class fragment_fifth extends Fragment {

    private FragmentSecondBinding binding;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentSecondBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.buttonSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController( fragment_fifth.this)
                        .navigate(R.id.action_Fifth_Fragment_to_FirstFragment);
            }
        });
        binding.fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddNewAssignment.newInstance().show(getParentFragmentManager() ,AddNewAssignment.TAG);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}