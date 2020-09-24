package ru.geek.controller;

public class NotFoundException extends RuntimeException {
    private String id;
    private String className;

    public NotFoundException() {
    }

    public void setMessage(String id, String className) {
        this.id = id;
        this.className = className;
    }

}
