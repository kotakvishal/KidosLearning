package com.example.kidoslearning;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class CategoryAdapter extends FragmentPagerAdapter
{
    public CategoryAdapter(@NonNull FragmentManager fm, Context mContext) {
        super(fm);
        this.mContext = mContext;
    }
    private Context mContext;
    public CategoryAdapter(FragmentManager fm)
    {
        super(fm);
    }




    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment=null;
       switch (position)
       {
           case 0:
              fragment= new AlphabetFragment();
               break;
           case 1:
               fragment= new NumbersFragment();
               break;
           case 2:
               fragment= new FamilyMembersFragment();
               break;
           case 3:
               fragment=new WeekNameFragment();
               break;
           case 4:
               return new ColorsFragment();

       }
       return fragment;

    }



    @Override
    public int getCount() {
        return 5;
    }


}
