package com.petech.thomasgregchallenge.ui.components.userlist;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.petech.thomasgregchallenge.data.entities.User;
import com.petech.thomasgregchallenge.databinding.UserListHolderLayoutBinding;
import com.petech.thomasgregchallenge.ui.components.userlist.click.UserListClicks;

public class UserListHolder extends RecyclerView.ViewHolder {
    private final UserListHolderLayoutBinding binding;

    public UserListHolder(@NonNull View itemView) {
        super(itemView);
        binding = UserListHolderLayoutBinding.bind(itemView);
    }

    public void populateView(User user) {
        binding.textUserFullName.setText(user.getName());
        binding.textUserNickName.setText(user.getUserName());
    }

    public void setClickDelete(UserListClicks userListClicks, User user) {
        binding.buttonDeleteUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userListClicks.onClickDelete(user.get_id());
            }
        });

        binding.userListHolderCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userListClicks.onCardClick(user.get_id());
            }
        });
    }
}

