package com.petech.thomasgregchallenge.ui.components.userlist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.petech.thomasgregchallenge.R;
import com.petech.thomasgregchallenge.data.entities.User;
import com.petech.thomasgregchallenge.ui.components.userlist.click.UserListClickDelete;

import java.util.List;

public class UserListAdapter extends RecyclerView.Adapter<UserListHolder> {
    private final List<User> userList;
    private final UserListClickDelete userListClickDelete;

    public UserListAdapter(List<User> userList, UserListClickDelete userListClickDelete) {
        this.userList = userList;
        this.userListClickDelete = userListClickDelete;
    }

    @NonNull
    @Override
    public UserListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_list_holder_layout, parent, false);
        return new UserListHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull UserListHolder holder, int position) {
        holder.populateView(userList.get(position));
        holder.setClickDelete(userListClickDelete, userList.get(position));
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }
}
