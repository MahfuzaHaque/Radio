package com.happy.radiostation.data;

public class RadioData {

    private String name;
    private String type;
    private String url;
    private boolean favourite;
    private boolean isPlay;

    public RadioData(String name, String type, String url) {
        this.name = name;
        this.type = type;
        this.url = url;
        this.favourite = false;
        this.isPlay = false;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isFavourite() {
        return favourite;
    }

    public void setFavourite(boolean favourite) {
        this.favourite = favourite;
    }

    public boolean isPlay() {
        return isPlay;
    }

    public void setPlay(boolean play) {
        isPlay = play;
    }
}
