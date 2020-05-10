package ru.nsu.template.presentation.repos;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableMaybeObserver;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import ru.nsu.template.TemplateApplication;
import ru.nsu.template.data.model.Repo;
import ru.nsu.template.data.model.User;
import ru.nsu.template.data.network.GithubApi;
import ru.nsu.template.data.network.GithubApiClient;

public class ReposViewModel extends ViewModel {
    private User user;

    private GithubApi api;

    public LiveData<List<Repo>> observeReposLiveData() { return reposLiveData; }
    private MutableLiveData<List<Repo>> reposLiveData = new MutableLiveData<>();

    public LiveData<User> observeUserLiveData() { return userLiveData; }
    private MutableLiveData<User> userLiveData = new MutableLiveData<>();

    public LiveData<Boolean> observeIsLoadingLiveData() { return isLoadingLiveData; }
    private MutableLiveData<Boolean> isLoadingLiveData = new MutableLiveData<>(false);

    public ReposViewModel(User user) {
        this.user = user;

        // todo make api singleton
        api = GithubApiClient.getClient(TemplateApplication.getInstance()).create(GithubApi.class);

        init();
    }

    private void init() {
        userLiveData.setValue(user);

        isLoadingLiveData.setValue(true);
        api.getRepos(user.getLogin())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<List<Repo>>() {
                    @Override
                    public void onSuccess(List<Repo> repos) {
                        isLoadingLiveData.setValue(false);
                        reposLiveData.setValue(repos);
                    }

                    @Override
                    public void onError(Throwable e) {
                        isLoadingLiveData.setValue(false);
                    }
                });
    }
}
