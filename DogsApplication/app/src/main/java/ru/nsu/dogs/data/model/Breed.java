package ru.nsu.dogs.data.model;

import java.io.Serializable;

public class Breed implements Serializable {
    private Avatar avatar;
    private String name;

    public Breed(String name, Avatar avatar) {
        this.name = name;
        this.avatar = avatar;
    }

    public Avatar getAvatar() {
        return avatar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
