package ru.nsu.dogs.presentation.subbreed;

import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;

import java.util.Objects;

import ru.nsu.dogs.R;
import ru.nsu.dogs.data.model.Breed;

public class SubBreedActivity extends AppCompatActivity{
    public static String SUB_BREED_KEY = "SUB_BREED_KEY";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_breed);
        TextView tvHeader = findViewById(R.id.tvHeader);
        ImageView ivAvatar = findViewById(R.id.ivAvatar);

        Breed breed = (Breed) getIntent().getSerializableExtra(SUB_BREED_KEY);

        Glide.with(ivAvatar.getContext())
                .load(Uri.parse(Objects.requireNonNull(breed).getAvatar().getUrl()))
                .into(ivAvatar);


        tvHeader.setText(breed.getName());


    }

}

