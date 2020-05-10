package ru.nsu.template.presentation.userlist;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ru.nsu.template.R;
import ru.nsu.template.data.model.User;
import ru.nsu.template.data.model.UserList;
import ru.nsu.template.presentation.repos.ReposActivity;
import ru.nsu.template.presentation.userlist.list.OnUserClickListener;
import ru.nsu.template.presentation.userlist.list.UserListAdapter;

import static ru.nsu.template.presentation.repos.ReposActivity.USER_KEY;

public class UserListActivity extends AppCompatActivity implements OnUserClickListener {
    public static String USER_LIST_KEY = "user_list_key";
    public static String QUERY_KEY = "query_key";

    private RecyclerView rvUserList;
    private UserListAdapter userListAdapter;
    private TextView tvHeader;
    private UserListViewModel viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getIntent().getExtras();

        setContentView(R.layout.activity_user_list);

        rvUserList = findViewById(R.id.rvUsers);
        tvHeader = findViewById(R.id.tvHeader);
        userListAdapter = new UserListAdapter(this);

        UserList list = new UserList();
        String query = "";
        if (args != null) {
            list = (UserList) args.getSerializable(USER_LIST_KEY);
            query = args.getString(QUERY_KEY);
        }

        initList();

        viewModel = ViewModelProviders.of(this, new UserListViewModelFactory(query, list)).get(UserListViewModel.class);

        viewModel.observeHeaderLiveData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                tvHeader.setText(s);
            }
        });

        viewModel.observeUserListLiveData().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                userListAdapter.setItems(users);
            }
        });

    }

    @Override
    public void onUserClick(User user) {
        Intent intent = new Intent(UserListActivity.this, ReposActivity.class);
        intent.putExtra(USER_KEY, user);

        startActivity(intent);
    }

    private void initList() {
        rvUserList.setLayoutManager(new LinearLayoutManager(this));
        rvUserList.setAdapter(userListAdapter);
    }
}
