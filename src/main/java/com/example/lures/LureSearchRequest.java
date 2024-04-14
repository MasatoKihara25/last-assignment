package com.example.lures;

public class LureSearchRequest {

    private String contains;

    public LureSearchRequest(String contains) {
        this.contains = contains;
    }

    public String getContains() {
        return contains;
    }
}
