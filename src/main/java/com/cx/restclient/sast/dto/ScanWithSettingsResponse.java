package com.cx.restclient.sast.dto;

public class ScanWithSettingsResponse {
    int id;

    public ScanWithSettingsResponse(int id) {
        this.id = id;
    }

    public ScanWithSettingsResponse() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
