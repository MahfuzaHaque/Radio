package com.happy.radiostation.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;


public class DataHelper {
    private static DataHelper dataHelper;
    private SharedPreferences appSharedPrefs;
    private final String RADIO_PREF = "radio_pref";
    private SharedPreferences.Editor prefsEditor;
    private Gson gson;

    public static DataHelper getDataHelper(Context context) {
        if (dataHelper == null) {
            dataHelper = new DataHelper(context.getApplicationContext());
        }
        return dataHelper;
    }

    private DataHelper(Context context) {
        appSharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        prefsEditor = appSharedPrefs.edit();
        gson = new Gson();
        final ArrayList<RadioData> radioDatas = getRadioDatas();
        if (radioDatas.size() <= 0) {
            DefaultData defaultData = new DefaultData();
            saveRadioData(defaultData.getDefaultData());
        } else {
            ArrayList<RadioData> tempData = new ArrayList<>();
            for (RadioData data : radioDatas) {
                data.setPlay(false);
                tempData.add(data);
            }
            saveRadioData(tempData);
        }
    }

    public void addNewRadio(RadioData data) {
        List<RadioData> radioDatas = getRadioDatas();
        if (radioDatas == null)
            radioDatas = new ArrayList<RadioData>();
        radioDatas.add(0, data);
        saveRadioData(radioDatas);
    }

    public void removeRadioData(Context context, RadioData data) {
        ArrayList<RadioData> datas = getRadioDatas();
        if (data != null) {
            datas.remove(data);
            saveRadioData(datas);
        }
    }

    public ArrayList<RadioData> getRadioDatas() {
        ArrayList<RadioData> radioDatas = new ArrayList<>();

        if (appSharedPrefs.contains(RADIO_PREF)) {
            String json = appSharedPrefs.getString(RADIO_PREF, null);
            Gson gson = new Gson();
            RadioData[] datas = gson.fromJson(json, RadioData[].class);
            for (RadioData data : datas) {
                radioDatas.add(data);
            }
        }
        return radioDatas;
    }

    public void saveRadioData(List<RadioData> datas) {
        String jsonFavorites = gson.toJson(datas);
        prefsEditor.putString(RADIO_PREF, jsonFavorites);
        prefsEditor.commit();
    }

    public ArrayList<RadioData> getFavRadioDatas() {
        ArrayList<RadioData> radioDatas = getRadioDatas();
        ArrayList<RadioData> fav = new ArrayList<>();
        for (RadioData data : radioDatas) {
            if (data.isFavourite()) {
                fav.add(data);
            }
        }
        return fav;
    }

    public RadioData getNext(RadioData data) {
        if (data == null) {
            return getRadioDatas().get(0);
        }
        ArrayList<RadioData> radioDatas = getRadioDatas();
        RadioData preData = null;
        String name = data.getName();
        String type = data.getType();
        final int size = radioDatas.size();
        for (int i = 0; i < size; i++) {
            RadioData mData = radioDatas.get(i);
            if (mData.getName().equals(name) && mData.getType().equals(type)) {
                int index = i + 1;
                if (index < size) {
                    preData = radioDatas.get(index);
                    break;
                } else {
                    break;
                }
            }
        }
        return preData;
    }


    public RadioData getPrevious(RadioData data) {
        if (data == null) {
            return getRadioDatas().get(0);
        }
        ArrayList<RadioData> radioDatas = getRadioDatas();
        RadioData preData = null;
        String name = data.getName();
        String type = data.getType();

        final int size = radioDatas.size();
        for (int i = 0; i < size; i++) {
            RadioData mData = radioDatas.get(i);
            if (mData.getName().equals(name) && mData.getType().equals(type)) {
                int index = i - 1;
                if (index >= 0) {
                    preData = radioDatas.get(index);
                    break;
                } else {
                    break;
                }
            }
        }
        return preData;
    }

    public void setFavourite(RadioData data, boolean isFavorite) {
        if (data != null) {
            ArrayList<RadioData> radioDatas = getRadioDatas();
            String name = data.getName();
            String type = data.getType();
            final int size = radioDatas.size();
            for (int i = 0; i < size; i++) {
                RadioData mData = radioDatas.get(i);
                if (mData.getName().equals(name) && mData.getType().equals(type)) {
                    mData.setFavourite(isFavorite);
                    radioDatas.set(i, mData);
                    saveRadioData(radioDatas);
                    break;
                }
            }
        }
    }
}
