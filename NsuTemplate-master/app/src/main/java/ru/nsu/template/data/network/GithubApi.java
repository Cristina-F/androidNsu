package ru.nsu.template.data.network;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import ru.nsu.template.data.model.Repo;
import ru.nsu.template.data.model.User;
import ru.nsu.template.data.model.UserList;

public interface GithubApi {
    @GET("/search/users")
    Single<UserList> getUserList(@Query("q") String filter);

    @GET("/users/{username}/repos")
    Single<List<Repo>> getRepos(@Path("username") String username);
}
