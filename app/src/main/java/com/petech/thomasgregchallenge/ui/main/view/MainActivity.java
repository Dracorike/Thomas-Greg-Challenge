package com.petech.thomasgregchallenge.ui.main.view;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.petech.thomasgregchallenge.data.entities.User;
import com.petech.thomasgregchallenge.databinding.ActivityMainBinding;
import com.petech.thomasgregchallenge.ui.components.userlist.UserListAdapter;
import com.petech.thomasgregchallenge.ui.components.userlist.click.UserListClickDelete;
import com.petech.thomasgregchallenge.ui.main.viewmodel.MainViewModel;
import com.petech.thomasgregchallenge.ui.main.viewmodel.MainViewModelFactory;
import com.petech.thomasgregchallenge.ui.register.view.RegisterUserActivity;
import com.petech.thomasgregchallenge.utils.AppUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class MainActivity extends AppCompatActivity {
    private List<User> userList = new ArrayList<>();
    private List<User> userListFilter = new ArrayList<>();
    private UserListAdapter userListAdapter;
    private ActivityMainBinding binding;
    private MainViewModel mainViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        userListAdapter = new UserListAdapter(userListFilter, deleteClick());

        setupViewModel();
        setupButtons();
        setupObservables();
        setupRecyclerView();
        setupQueryUser();

        mainViewModel.getUsersListFromDatabase();
    }

    private void setupViewModel() {
        MainViewModelFactory factory = AppUtils.getMainViewModel(getApplicationContext());
        mainViewModel = new ViewModelProvider(this, factory).get(MainViewModel.class);
    }

    private void setupButtons() {
        binding.buttonCreateNewUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToUserRegistration();
            }
        });
    }

    private void setupRecyclerView() {
        binding.recyclerUserList.setAdapter(userListAdapter);
        binding.recyclerUserList.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setupQueryUser() {
        binding.inputTextSearchUser.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                List<User> filteredUsers = userList.stream().filter(new Predicate<User>() {
                    @Override
                    public boolean test(User user) {
                        return user.getName().contains(s) || user.getUserName().contains(s);
                    }
                }).collect(Collectors.toList());

                populateUsersList(filteredUsers);
            }
        });
    }

    private void setupObservables() {
        mainViewModel.getUserList().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                userList.clear();
                userList.addAll(users);
                populateUsersList(userList);
            }
        });
    }

    private void goToUserRegistration() {
        Intent intent = new Intent(this, RegisterUserActivity.class);
        startActivity(intent);
    }

    private void populateUsersList(List<User> users) {
        if (users.isEmpty()) {
            binding.recyclerUserList.setVisibility(View.GONE);
            binding.noUserOnListLayout.setVisibility(View.VISIBLE);
        } else {
            binding.recyclerUserList.setVisibility(View.VISIBLE);
            binding.noUserOnListLayout.setVisibility(View.GONE);
        }

        userListFilter.clear();
        userListFilter.addAll(users);
        userListAdapter.notifyDataSetChanged();
    }

    private UserListClickDelete deleteClick() {
        return new UserListClickDelete() {
            @Override
            public void onClickDelete(int id) {

            }
        };
    }
}