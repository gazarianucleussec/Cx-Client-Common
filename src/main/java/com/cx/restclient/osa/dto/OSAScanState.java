package com.cx.restclient.osa.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by: Dorg.
 * Date: 15/03/2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class OSAScanState {

    private int id;
    private String name;
    private String failureReason;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFailureReason() {
        return failureReason;
    }

    public void setFailureReason(String failureReason) {
        this.failureReason = failureReason;
    }
}
