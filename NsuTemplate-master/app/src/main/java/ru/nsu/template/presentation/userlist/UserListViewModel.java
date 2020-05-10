package ru.nsu.template.presentation.userlist;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import ru.nsu.template.R;
import ru.nsu.template.TemplateApplication;
import ru.nsu.template.data.model.User;
import ru.nsu.template.data.model.UserList;

public class UserListViewModel extends ViewModel {
    private UserList userList;
    private String query;

    public LiveData<List<User>> observeUserListLiveData() { return userListLiveData; }
    private MutableLiveData<List<User>> userListLiveData = new MutableLiveData<>();

    public LiveData<String> observeHeaderLiveData() { return headerLiveData; }
    private MutableLiveData<String> headerLiveData = new MutableLiveData<>();

    UserListViewModel(String query, UserList userList) {
        this.query = query;
        this.userList = userList;

        userListLiveData.setValue(userList.getItems());
        headerLiveData.setValue(TemplateApplication.getInstance().getString(R.string.user_list_header, query, userList.getItems().size()));
    }
}
