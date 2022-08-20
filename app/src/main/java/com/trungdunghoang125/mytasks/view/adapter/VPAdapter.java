package com.trungdunghoang125.mytasks.view.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.trungdunghoang125.mytasks.view.fragment.TaskDoneFragment;
import com.trungdunghoang125.mytasks.view.fragment.TasksFragment;

public class VPAdapter extends FragmentStateAdapter {


    public VPAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 1:
                return new TaskDoneFragment();
            default:
                return new TasksFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
