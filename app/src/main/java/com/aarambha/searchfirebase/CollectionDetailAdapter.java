package com.aarambha.searchfirebase;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class CollectionDetailAdapter extends FragmentStatePagerAdapter {


    public int totalTabs;
     String syllabus;
     String notes;
     String questionBank;


    public CollectionDetailAdapter( FragmentManager fm, int totalTabs, String syllabus, String notes, String questionBank) {
        super(fm);
        this.totalTabs = totalTabs;
        this.syllabus = syllabus;
        this.notes = notes;
        this.questionBank = questionBank;
    }



    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                CollectionDescriptionFragment productDescriptionFragment1 = new CollectionDescriptionFragment();
                Log.e("shyam",""+syllabus);
                productDescriptionFragment1.body = syllabus;
                return productDescriptionFragment1;

            case 1:
                CollectionDescriptionFragment productDescriptionFragment2 = new CollectionDescriptionFragment();
                productDescriptionFragment2.body = notes;
                return productDescriptionFragment2;
            case 2:
                CollectionDescriptionFragment productDescriptionFragment3 = new CollectionDescriptionFragment();
                productDescriptionFragment3.body = questionBank;
                return productDescriptionFragment3;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return totalTabs;
    }
}
