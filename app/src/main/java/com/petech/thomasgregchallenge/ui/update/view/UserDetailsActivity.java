package com.petech.thomasgregchallenge.ui.update.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.petech.thomasgregchallenge.databinding.ActivityUserDetailsBinding;
import com.petech.thomasgregchallenge.ui.update.view.fragment.UserDetailsFragment;
import com.petech.thomasgregchallenge.ui.update.viewmodel.UserDetailsErrors;
import com.petech.thomasgregchallenge.ui.update.viewmodel.UserDetailsViewModel;
import com.petech.thomasgregchallenge.ui.update.viewmodel.UserDetailsViewModelFactory;
import com.petech.thomasgregchallenge.utils.AppUtils;

public class UserDetailsActivity extends AppCompatActivity {
    private static final String TAG = "UserDetailsActivity: ";
    private static final String TAG_EXTRA_USER_ID = TAG + "extra-user-id";

    private ActivityUserDetailsBinding binding;
    private UserDetailsViewModel viewModel;

    public static Intent newInstance(Context context, int userIdExtra) {
        Intent intent = new Intent(context, UserDetailsActivity.class);
        intent.putExtra(TAG_EXTRA_USER_ID, userIdExtra);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setupViewModel();
        getUserExtra();
        setupObservables();
    }

    private void setupViewModel() {
        UserDetailsViewModelFactory factory = AppUtils.getUserDetailsViewModel(getApplicationContext());
        viewModel = new ViewModelProvider(this, factory).get(UserDetailsViewModel.class);
    }

    private void getUserExtra() {
        Intent intent = getIntent();

        if (intent != null && intent.hasExtra(TAG_EXTRA_USER_ID)) {
            int extraId = intent.getIntExtra(TAG_EXTRA_USER_ID, -1);
            viewModel.findSelectedUser(extraId);
        }
    }

    private void setupObservables() {
        viewModel.getUserDetailsErrors().observe(this, new Observer<UserDetailsErrors>() {
            @Override
            public void onChanged(UserDetailsErrors userDetailsErrors) {
                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
            }
        });

        viewModel.getUserFound().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean userFound) {
                if (userFound) {
                    placeFragmentOnFrame(new UserDetailsFragment());
                }
            }
        });
    }

    private void placeFragmentOnFrame(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(binding.frameFragmentContainer.getId(), fragment);
        transaction.commit();
    }
}