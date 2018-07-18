package com.example.demo;

public enum Size {
    X(1),
    Y(2);

    private final int id;

    Size(int i) {
        this.id = i;
    }

    public int getId() {
        return id;
    }
}
