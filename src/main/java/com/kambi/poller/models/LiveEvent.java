package com.kambi.poller.models;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(value = Include.NON_NULL)
public class LiveEvent implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Event event;
	private MainBetOffer mainBetOffer;
	
	public LiveEvent() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public LiveEvent(Event event, MainBetOffer mainBetOffer) {
		super();
		this.event = event;
		this.mainBetOffer = mainBetOffer;
	}
	
	public Event getEvent() {
		return event;
	}
	public void setEvent(Event event) {
		this.event = event;
	}
	public MainBetOffer getMainBetOffer() {
		return mainBetOffer;
	}
	public void setMainBetOffer(MainBetOffer mainBetOffer) {
		this.mainBetOffer = mainBetOffer;
	}
	
	
}
