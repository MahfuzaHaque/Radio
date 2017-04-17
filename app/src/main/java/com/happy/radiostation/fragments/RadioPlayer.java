package com.happy.radiostation.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Handler;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveVideoTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import com.happy.radiostation.R;
import com.happy.radiostation.data.RadioData;
import com.happy.radiostation.event.NetConfection;

import java.lang.ref.WeakReference;

public class RadioPlayer {

    private SimpleExoPlayer player;
    private DataSource.Factory dataSourceFactory;
    private ExtractorsFactory extractorsFactory;
    private String error;
    private RadioData radioData;
    private static RadioPlayer radioPlayer;
    private WeakReference<Context> mContext;
    private WeakReference<OnRadioListener> mListener;

    public static RadioPlayer getRadioPlayer(Context context) {
        if (radioPlayer == null) {
            radioPlayer = new RadioPlayer(context);
        }
        return radioPlayer;
    }

    private RadioPlayer(Context context) {
        DefaultBandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        TrackSelector trackSelector = new DefaultTrackSelector(new Handler(), new AdaptiveVideoTrackSelection.Factory(bandwidthMeter));
        player = ExoPlayerFactory.newSimpleInstance(context, trackSelector, new DefaultLoadControl(), context.getString(R.string.item_purchase_code));
        dataSourceFactory = new DefaultDataSourceFactory(context, Util.getUserAgent(context, "streamradio"), bandwidthMeter);
        extractorsFactory = new DefaultExtractorsFactory();
        error = context.getString(R.string.radio_error);
        mContext = new WeakReference<>(context);
    }

    public void setListener(OnRadioListener listener) {
        mListener = new WeakReference<>(listener);
    }

    public void stop() {
        player.stop();
        if (radioData != null && mListener.get() != null) {
            mListener.get().onStop(radioData);
        }
    }

    public void setMute(boolean isMute) {
        if (isMute) {
            player.setVolume(0f);
        } else {
            float currentVolume = 1f;
            player.setVolume(currentVolume);
        }
    }

    public void play() {
        if (mContext.get() != null) {
            if (NetConfection.isNetworkAvailable(mContext.get())) {
                if (radioData != null) {
                    playMusic(radioData);
                } else {
                    if (mListener.get() != null) {
                        mListener.get().onPlayError(null, "Select radio station");
                    }
                }
            } else {
                if (mListener.get() != null) {
                    mListener.get().onPlayError(radioData, mContext.get().getString(R.string.network_error));
                }
            }
        }
    }

    public void playMusic(final RadioData data) {
        if (mListener.get() != null) {
            mListener.get().onInitialData(data);
        }
        try {
            stop();
            this.radioData = data;
            Uri uri = Uri.parse(data.getUrl());
            MediaSource audioSource = new ExtractorMediaSource(uri, dataSourceFactory, extractorsFactory, null, null);
            player.prepare(audioSource);
            player.setPlayWhenReady(true);
            player.addListener(new ExoPlayer.EventListener() {
                @Override
                public void onLoadingChanged(boolean b) {
                }

                @Override
                public void onPlayerStateChanged(boolean b, int i) {
                    if (ExoPlayer.STATE_READY == i) {
                        if (mListener.get() != null) {
                            mListener.get().onPlaying(data);
                        }
                    }
                }

                @Override
                public void onTimelineChanged(Timeline timeline, Object o) {
                }

                @Override
                public void onPlayerError(ExoPlaybackException e) {
                    if (mListener.get() != null) {
                        mListener.get().onPlayError(data, error);
                    }
                }

                @Override
                public void onPositionDiscontinuity() {
                }
            });

        } catch (Exception e) {
            if (mListener.get() != null) {
                mListener.get().onPlayError(data, error);
            }
        }
    }

    public RadioData getRadioData() {
        return radioData;
    }

    public interface OnRadioListener {

        void onInitialData(RadioData data);

        void onPlayError(RadioData data, String mgs);

        void onPlaying(RadioData data);

        void onStop(RadioData data);
    }
}
