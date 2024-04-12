package com.example.lures;

public class LuresSearchRequest {

    private String contains;

    public LuresSearchRequest(String contains) {
        this.contains = contains;
    }

    public String getContains() {
        return contains;
    }
}
