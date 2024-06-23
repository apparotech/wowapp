package com.example.wowapp.screen;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.example.wowapp.R;
import com.example.wowapp.screen.adapters.ViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;

public class FollowListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follow_list);

        Bundle i = getIntent().getExtras();
        int pageIndex = i.getInt("pageIndex");
        ActionBar actionBar = getSupportActionBar();

        if (actionBar!=null) {
            actionBar.hide();
        }

        TabLayout tabLayout =  (TabLayout) findViewById(R.id.tabs);
        ViewPager2 viewPager2 = (ViewPager2) findViewById(R.id.view_pager);

        ViewPagerAdapter adapter = new ViewPagerAdapter(this);
        viewPager2.setAdapter(adapter);



    }
}