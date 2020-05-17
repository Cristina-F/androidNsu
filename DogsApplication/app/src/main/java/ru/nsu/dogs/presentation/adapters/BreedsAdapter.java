package ru.nsu.dogs.presentation.adapters;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.Collections;
import java.util.List;

import ru.nsu.dogs.R;
import ru.nsu.dogs.data.model.Avatar;
import ru.nsu.dogs.data.model.Breed;
import ru.nsu.dogs.presentation.listeners.IOnBreedClickListener;

public class BreedsAdapter extends RecyclerView.Adapter<BreedsAdapter.ViewHolder> {
    private List<Breed> items;
    private IOnBreedClickListener listener;

    public BreedsAdapter(IOnBreedClickListener listener) {
        this.listener = listener;
    }

    public void setItems(List<Breed> items) {
        this.items = items;
        Collections.sort(this.items, (o1, o2) -> o1.getName().compareTo(o2.getName()));
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_breed, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Breed breed = items.get(position);
        final Avatar avatar = breed.getAvatar();
        holder.tvName.setText(breed.getName());

        Glide.with(holder.itemView.getContext())
                .load(Uri.parse(avatar.getUrl()))
                .into(holder.ivAvatar);

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onBreedClick(breed);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items == null ? 0 : items.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivAvatar;
        TextView tvName;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            ivAvatar = itemView.findViewById(R.id.ivAvatar);
            tvName = itemView.findViewById(R.id.tvName);
        }
    }
}
