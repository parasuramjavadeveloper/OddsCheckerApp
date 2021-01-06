package com.kambi.poller.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import java.io.Serializable;

/**
 * Outcome to store labels and odds
 * @author Parasuram
 */
@JsonInclude(value = Include.NON_NULL)
public class Outcome implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String label;
    private float odds;

    public Outcome(Long id, String label, float odds) {
        super();
        this.id = id;
        this.label = label;
        this.odds = odds;
    }

    public Outcome() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public float getOdds() {
        return odds;
    }

    public void setOdds(float odds) {
        this.odds = odds;
    }


}
