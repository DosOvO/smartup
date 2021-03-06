package de.unibremen.smartup;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import de.unibremen.smartup.adapter.TabsPagerAdapter;

public class Layout extends AppCompatActivity {

    private TabsPagerAdapter tabsPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout);

        TabLayout tabLayout = (TabLayout)findViewById(R.id.sliding_tabs);
        ViewPager viewPager = (ViewPager)findViewById(R.id.viewpager);


        tabsPagerAdapter = new TabsPagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(tabsPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.getTabAt(0).setIcon(R.drawable.clock);
        tabLayout.getTabAt(0).setText(R.string.alarm);
        tabLayout.getTabAt(1).setIcon(R.drawable.question_mark);
        tabLayout.getTabAt(1).setText(R.string.question);
        final Intent intent = getIntent();
        if (intent.hasExtra("tabNumber")) {
            final int tab = intent.getExtras().getInt("tabNumber");
            viewPager.setCurrentItem(tab);
        }

        if (AudioPlay.isAudioPlaying) {
            AudioPlay.stopAudio();
        }

    }

    public void startAlarmActivity(View view) {
        Intent intent = new Intent(this, AddAlarmActivity.class);
        startActivity(intent);
    }

    public void startQuestionActivity(View view) {
        Intent intent = new Intent(this, AddQuestionActivity.class);
        startActivity(intent);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

}
