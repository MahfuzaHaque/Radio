package com.happy.radiostation.event;


import com.happy.radiostation.data.RadioData;

public class RadioEvent {

    public enum Event {
        FAVORITE_LIST, ALL_LIST, LIST_REFRESH, PLAY, STOP, LOADING, ERROR
    }

    private Event event;
    private RadioData data;

    public RadioEvent(Event event) {
        this.event = event;
    }

    public Event getEvent() {
        return event;
    }


    public RadioData getData() {
        return data;
    }

    public void setData(RadioData data) {
        this.data = data;
    }

    public void setEvent(Event event) {
        this.event = event;
    }
}
