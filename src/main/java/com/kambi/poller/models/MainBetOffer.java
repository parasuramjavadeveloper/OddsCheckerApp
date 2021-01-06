package com.kambi.poller.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * MainBetOffer to store event outcomes
 * @author Parasuram
 */
@JsonInclude(value = Include.NON_NULL)
public class MainBetOffer implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    ArrayList<Outcome> outcomes = new ArrayList<Outcome>();

    public MainBetOffer() {
        super();
        // TODO Auto-generated constructor stub
    }

    public MainBetOffer(String id, ArrayList<Outcome> outcomes) {
        super();
        this.id = id;
        this.outcomes = outcomes;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Outcome> getOutcomes() {
        return outcomes;
    }

    public void setOutcomes(ArrayList<Outcome> outcomes) {
        this.outcomes = outcomes;
    }


}
