package com.example.lures;

public class LureNotFoundException extends RuntimeException {

    public LureNotFoundException(String message) {
        super(message);
    }
}
