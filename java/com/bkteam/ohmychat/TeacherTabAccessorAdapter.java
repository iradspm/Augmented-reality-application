package com.bkteam.ohmychat;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class TeacherTabAccessorAdapter extends FragmentPagerAdapter {


    public TeacherTabAccessorAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        switch (i){
            /*case 0:
                registerCourse registerCourse = new registerCourse();
                return registerCourse;*/
            case 0:
                registerUnits registerUnits = new registerUnits();
                return  registerUnits;
            case 1:
                studentsFragment studentsFragment = new studentsFragment();
                return studentsFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public  CharSequence getPageTitle(int position){
        switch (position){
           /* case 0:
                return "Courses";*/
            case 0:
                return  "Units";
            case 1:
                return "Students";
            default:
                return null;
        }
    }
}
