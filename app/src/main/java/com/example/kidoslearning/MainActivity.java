package com.example.kidoslearning;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ViewPager viewPager=(ViewPager)findViewById(R.id.viewpager);
        CategoryAdapter categoryadapter=new CategoryAdapter(getSupportFragmentManager(),this);
        viewPager.setAdapter(categoryadapter);
        TextView alphabet=(TextView)findViewById(R.id.alphabet);
        TextView numbers=(TextView)findViewById(R.id.numbers);
        TextView familymembers=(TextView)findViewById(R.id.familymembers);
        TextView weeksname=(TextView)findViewById(R.id.Weeksname);
        TextView colors=(TextView)findViewById(R.id.colors);
        alphabet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
             viewPager.setCurrentItem(0);
            }
        });
        numbers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(1);
            }
        });
        familymembers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(2);
            }
        });
        weeksname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(3);
            }
        });
        colors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(4);
            }
        });





    }
}