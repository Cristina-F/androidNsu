package ru.nsu.template.presentation.start;

import android.annotation.SuppressLint;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import ru.nsu.template.TemplateApplication;
import ru.nsu.template.data.model.User;
import ru.nsu.template.data.model.UserList;
import ru.nsu.template.data.network.GithubApi;
import ru.nsu.template.data.network.GithubApiClient;

public class StartViewModel extends ViewModel {

    public LiveData<String> observeErrorLiveData() { return errorLiveData; }
    private MutableLiveData<String> errorLiveData = new MutableLiveData<>("");

    public LiveData<UserList> observeUserListLiveData() { return userListLiveData; }
    private MutableLiveData<UserList> userListLiveData = new MutableLiveData<>();

    public LiveData<Boolean> observeSearchButtonEnabled() { return searchButtonEnabled; }
    private MutableLiveData<Boolean> searchButtonEnabled = new MutableLiveData<>(false);

    public LiveData<Boolean> observeIsLoadingLiveData() { return isLoadingLiveData; }
    private MutableLiveData<Boolean> isLoadingLiveData = new MutableLiveData<>(false);

    private GithubApi api;

    public StartViewModel() {
        api = GithubApiClient.getClient(TemplateApplication.getInstance()).create(GithubApi.class);
    }

    public void validateUsername(String username) {
        searchButtonEnabled.setValue(!username.equals(""));
    }

    @SuppressLint("CheckResult")
    public void search(String username) {
        isLoadingLiveData.setValue(true);
        api.getUserList(username)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<UserList>() {
                    @Override
                    public void onSuccess(UserList userList) {
                        isLoadingLiveData.setValue(false);
                        userListLiveData.setValue(userList);
                    }

                    @Override
                    public void onError(Throwable e) {
                        isLoadingLiveData.setValue(false);
                        errorLiveData.setValue(e.getMessage());
                    }
                });
    }
}
