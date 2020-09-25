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

    public String getMessage(){
        return className +" = " + id+ " not found";
    }

    public NotFoundException(String id, String className) {
        this.id = id;
        this.className = className;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }
}
