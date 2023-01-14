package com.example.education.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.education.fragment.FragmentForTest;
import com.example.education.model.Event;

public class AdapterForTest extends FragmentStateAdapter {
    private final Event event;
    private final FragmentForTest.Click click;
    public AdapterForTest(@NonNull FragmentActivity fragmentActivity, Event event, FragmentForTest.Click click) {
        super(fragmentActivity);
        this.event = event;
        this.click = click;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return FragmentForTest.newInstance(event.getTests().get(position),click,event.getTests().size());
    }

    @Override
    public int getItemCount() {
        return event.getTests().size();
    }
}
