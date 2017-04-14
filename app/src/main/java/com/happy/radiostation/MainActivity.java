package com.happy.radiostation;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.SeekBar;

import com.happy.radiostation.data.DataHelper;
import com.happy.radiostation.data.RadioData;
import com.happy.radiostation.event.RadioEvent;
import com.happy.radiostation.fragments.EventListener;
import com.happy.radiostation.fragments.MainAdapter;
import com.happy.radiostation.fragments.RadioPlayer;

import com.happy.radiostation.R;
import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, EventListener, RadioPlayer.OnRadioListener, CompoundButton.OnCheckedChangeListener {

    @BindView(R.id.view_pager)
    ViewPager viewPager;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.nav_view)
    NavigationView navigationView;

    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;


    @BindView(R.id.previousImageButton)
    ImageButton previousImageButton;

    @BindView(R.id.playCheckBox)
    CheckBox playCheckBox;

    @BindView(R.id.nextImageBtton)
    ImageButton nextImageBtton;

    @BindView(R.id.favoriteCheckBox)
    CheckBox favoriteCheckBox;

    @BindView(R.id.volume_seek_bar)
    SeekBar volumeSeekBar;

    @BindView(R.id.volumeCheckBox)
    CheckBox volumeCheckBox;

    @BindView(R.id.radio_control_layout)
    LinearLayout radioControlLayout;

    @BindView(R.id.shareImageButton)
    ImageButton shareImageButton;

    private MainAdapter adapter;
    private RadioPlayer player;
    private DataHelper helper;
    private AudioManager audioManager;
    private int maxVolume;


    @Override
    protected void onStart() {
        super.onStart();
        helper = DataHelper.getDataHelper(this);
        player = RadioPlayer.getRadioPlayer(this);
        player.setListener(this);
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        adapter = new MainAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(adapter.getCount());
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        viewPager.setCurrentItem(0);
        volumeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                float percent = (float) progress / 100;
                int vul = (int) (maxVolume * percent);
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, vul, 0);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        playCheckBox.setOnCheckedChangeListener(this);
        volumeCheckBox.setOnCheckedChangeListener(this);
    }


    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_power) {
            player.stop();
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_home) {
            viewPager.setCurrentItem(0);
        } else if (id == R.id.nav_statation_list) {
            filterRadioList(false);
        } else if (id == R.id.nav_favorite) {
            filterRadioList(true);
        } else if (id == R.id.nav_like) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/")));// your facebook page link in this app ok.
        } else if (id == R.id.nav_rate) {
            rate();
        } else if (id == R.id.nav_about) {
            startActivity(new Intent(this, AboutActivity.class));
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void filterRadioList(boolean isFavoriteList) {
        if (isFavoriteList) {
            EventBus.getDefault().post(new RadioEvent(RadioEvent.Event.FAVORITE_LIST));
        } else {
            EventBus.getDefault().post(new RadioEvent(RadioEvent.Event.ALL_LIST));
        }
        viewPager.setCurrentItem(1);
    }

    private void rate() {
        final String appName = getPackageName();
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appName)));
        } catch (ActivityNotFoundException anfe) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + appName)));
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.playCheckBox:
                if (isChecked) {
                    player.play();
                } else {
                    player.stop();
                }
                break;
            case R.id.favoriteCheckBox:
                helper.setFavourite(player.getRadioData(), isChecked);
                EventBus.getDefault().post(new RadioEvent(RadioEvent.Event.LIST_REFRESH));
                break;
            case R.id.volumeCheckBox:
                player.setMute(!isChecked);
                break;
        }
    }

    @Override
    public void onInitialData(RadioData data) {
        RadioEvent event = new RadioEvent(RadioEvent.Event.LOADING);
        event.setData(data);
        EventBus.getDefault().post(event);
    }

    @Override
    public void onPlayError(RadioData data, String mgs) {
        //   showMgs(mgs);
        setPlayCheckBox(false);
        EventBus.getDefault().post(new RadioEvent(RadioEvent.Event.ERROR));
    }

    @Override
    public void onPlaying(RadioData data) {
        RadioEvent radioEvent = new RadioEvent(RadioEvent.Event.PLAY);
        radioEvent.setData(data);
        EventBus.getDefault().post(radioEvent);
    }

    @Override
    public void onStop(RadioData data) {
        RadioEvent radioEvent = new RadioEvent(RadioEvent.Event.STOP);
        radioEvent.setData(data);
        EventBus.getDefault().post(radioEvent);
    }


    @OnClick({R.id.previousImageButton, R.id.nextImageBtton})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.previousImageButton:
                playPrevious();
                break;
            case R.id.nextImageBtton:
                playNext();
                break;
        }
    }

    private void playPrevious() {
        RadioData newData = helper.getPrevious(player.getRadioData());
        if (newData != null) {
            onPrepareRadioPlay(newData);
        }
    }

    private void playNext() {
        RadioData newData = helper.getNext(player.getRadioData());
        if (newData != null) {
            onPrepareRadioPlay(newData);
        }
    }

    private void onPrepareRadioPlay(RadioData data) {
        setPlayCheckBox(true);
        player.playMusic(data);
        favoriteCheckBox.setOnCheckedChangeListener(null);
        favoriteCheckBox.setChecked(data.isFavourite());
        favoriteCheckBox.setOnCheckedChangeListener(this);
    }

    private void setPlayCheckBox(boolean isChecked) {
        playCheckBox.setOnCheckedChangeListener(null);
        playCheckBox.setChecked(isChecked);
        playCheckBox.setOnCheckedChangeListener(this);
    }

    private void showMgs(String mgs) {
        View view = findViewById(android.R.id.content);
        Snackbar snackbar = Snackbar.make(view, mgs, Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    @Override
    protected void onDestroy() {
        player.setListener(null);
        super.onDestroy();
    }

    @Override
    public void onNewItemPlay(RadioData data) {
        viewPager.setCurrentItem(0);
        onPrepareRadioPlay(data);
    }

    @OnClick(R.id.shareImageButton)
    public void onClick() {
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        String shareBody = "Listen Hungarian Most popular Radio Staions on your phone. Download the app here:" +
                "your apk line in play store ";
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Hungary  Radio Stations");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        startActivity(Intent.createChooser(sharingIntent, "Share the App"));
    }
}
