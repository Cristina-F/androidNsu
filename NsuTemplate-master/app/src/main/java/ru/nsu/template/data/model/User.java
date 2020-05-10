package ru.nsu.template.data.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class User implements Serializable {
    @SerializedName("name")
    private String name;

    @SerializedName("avatar_url")
    private String avatarUrl;

    @SerializedName("bio")
    private String bio;

    @SerializedName("login")
    private String login;

    @SerializedName("repos_url")
    private String reposUrl;

    public String getName() {
        return name;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public String getBio() {
        return bio;
    }

    public String getLogin() {
        return login;
    }

    public String getReposUrl() {
        return reposUrl;
    }
}
