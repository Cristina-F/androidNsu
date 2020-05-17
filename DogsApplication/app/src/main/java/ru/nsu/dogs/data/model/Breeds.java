package ru.nsu.dogs.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.Collections;
import java.util.List;

public class Breeds {
    public Breeds() {
        message = Collections.emptyList();
    }

    @SerializedName("message")
    private List<String> message;

    public List<String> getMessage() {
        return message;
    }

    public void setMessage(List<String> message) {
        this.message = message;
    }
}
