package ru.nsu.template.presentation.userlist.list;

import android.media.Image;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import ru.nsu.template.R;
import ru.nsu.template.data.model.User;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.ViewHolder> {
    private List<User> items;
    private OnUserClickListener listener;

    public UserListAdapter(OnUserClickListener listener) {
        this.listener = listener;
    }

    public void setItems(List<User> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final User user = items.get(position);
        holder.tvLogin.setText(user.getLogin());

        Glide.with(holder.itemView.getContext())
                .load(Uri.parse(user.getAvatarUrl()))
                .into(holder.ivAvatar);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onUserClick(user);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivAvatar;
        TextView tvLogin;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            ivAvatar = itemView.findViewById(R.id.ivAvatar);
            tvLogin = itemView.findViewById(R.id.tvLogin);
        }
    }
}
