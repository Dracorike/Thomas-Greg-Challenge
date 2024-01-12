package com.petech.thomasgregchallenge.ui.main.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.petech.thomasgregchallenge.R;
import com.petech.thomasgregchallenge.databinding.ActivityMainBinding;
import com.petech.thomasgregchallenge.ui.register.view.RegisterUserActivity;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setupButtons();
    }

    private void setupButtons() {
        binding.buttonCreateNewUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToUserRegistration();
            }
        });
    }

    private void goToUserRegistration() {
        Intent intent = new Intent(this, RegisterUserActivity.class);
        startActivity(intent);
    }
}