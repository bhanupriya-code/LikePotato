package com.example.gullutesting3;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.gullutesting3.fragments.AlarmFragment;
import com.example.gullutesting3.fragments.MessageFragment;
import com.example.gullutesting3.fragments.NoteFragment;

public class MyViewPageAdapter extends FragmentStateAdapter {

    public MyViewPageAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new MessageFragment();
            case 1:
                return new AlarmFragment();
            case 2:
                return new NoteFragment();
            default:
                return new MessageFragment();
        }
    }

    @Override
    public int getItemCount() {
        //returning three because there are three fragments
        return 3;

    }
}
