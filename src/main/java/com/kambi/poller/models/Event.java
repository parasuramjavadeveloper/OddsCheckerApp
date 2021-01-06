package com.kambi.poller.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Event to store event details
 * @author Parasuram
 */
@JsonInclude(value = Include.NON_NULL)
public class Event implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private Long id;
    private String name;
    private ArrayList<String> tags = new ArrayList<String>();

    public Event(Long id, String name, ArrayList<String> tags) {
        super();
        this.id = id;
        this.name = name;
        this.tags = tags;
    }

    public Event() {
        super();
        // TODO Auto-generated constructor stub
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getTags() {
        return tags;
    }

    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
    }


}
