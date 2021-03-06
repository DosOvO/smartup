package de.unibremen.smartup.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import de.unibremen.smartup.fragments.ClockFragment;
import de.unibremen.smartup.fragments.QuestionFragment;

public class TabsPagerAdapter extends FragmentPagerAdapter {

    private int numberOfTabs;

    public TabsPagerAdapter(FragmentManager fm, int numberOfTabs) {
        super(fm);
        this.numberOfTabs = numberOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new ClockFragment();
            case 1:
                return new QuestionFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numberOfTabs;
    }

}
