package ru.nsu.dogs.data.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Avatar  implements Serializable {
    @SerializedName("message")
    private String url;

    public String getUrl() {
        return url;
    }
}
