package com.happy.radiostation.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.happy.radiostation.R;
import com.happy.radiostation.data.LoadingAnimation;
import com.happy.radiostation.event.RadioEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RadioFragment extends Fragment {

    @BindView(R.id.animationImage)
    ImageView animationImage;
    @BindView(R.id.titleTextView)
    TextView titleTextView;
    @BindView(R.id.typeTextView)
    TextView typeTextView;
    private LoadingAnimation animation;

    public static RadioFragment newInstance() {
        RadioFragment fragment = new RadioFragment();
        return fragment;
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_radio, container, false);
        ButterKnife.bind(this, view);
        animation = new LoadingAnimation(animationImage);
        return view;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(RadioEvent event) {
        if (event.getEvent() == RadioEvent.Event.LOADING) {
            animation.startAnimation();
            titleTextView.setText(event.getData().getName());
            typeTextView.setText(event.getData().getType());

        } else if (event.getEvent() == RadioEvent.Event.PLAY) {
            animation.clearAnimation();
        } else if (event.getEvent() == RadioEvent.Event.ERROR) {
            titleTextView.setText(R.string.welcome_big);
            typeTextView.setText(R.string.welcome_small);
            animation.clearAnimation();
        }
    }
}
