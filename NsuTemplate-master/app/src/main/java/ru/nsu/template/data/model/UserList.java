package ru.nsu.template.data.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

public class UserList implements Serializable {
    public UserList() {
        items = Collections.emptyList();
    }

    @SerializedName("items")
    private List<User> items;

    public List<User> getItems() {
        return items;
    }

    public void setItems(List<User> items) {
        this.items = items;
    }
}
