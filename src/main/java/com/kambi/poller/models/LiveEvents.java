package com.kambi.poller.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import java.io.Serializable;
import java.util.ArrayList;


@JsonInclude(value = Include.NON_NULL)
public class LiveEvents implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    ArrayList<LiveEvent> liveEvents = new ArrayList<LiveEvent>();

    public LiveEvents(ArrayList<LiveEvent> liveEvents) {
        super();
        this.liveEvents = liveEvents;
    }

    public LiveEvents() {
        super();
        // TODO Auto-generated constructor stub
    }

    public ArrayList<LiveEvent> getLiveEvents() {
        return liveEvents;
    }

    public void setLiveEvents(ArrayList<LiveEvent> liveEvents) {
        this.liveEvents = liveEvents;
    }


}
