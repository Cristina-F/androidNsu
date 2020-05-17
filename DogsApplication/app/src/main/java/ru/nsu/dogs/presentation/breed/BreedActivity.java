package ru.nsu.dogs.presentation.breed;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;

import java.util.Objects;

import ru.nsu.dogs.R;
import ru.nsu.dogs.data.model.Breed;
import ru.nsu.dogs.presentation.adapters.BreedsAdapter;
import ru.nsu.dogs.presentation.listeners.IOnBreedClickListener;
import ru.nsu.dogs.presentation.subbreed.SubBreedActivity;

import static ru.nsu.dogs.presentation.subbreed.SubBreedActivity.SUB_BREED_KEY;

public class BreedActivity extends AppCompatActivity implements IOnBreedClickListener {
    public static String BREED_KEY = "BREED_KEY";

    private RecyclerView rvBreeds;
    private ProgressBar pbLoading;
    private TextView tvSubBreedsHeader;

    private BreedsAdapter breedsAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_breed);
        rvBreeds = findViewById(R.id.rvBreeds);
        TextView tvHeader = findViewById(R.id.tvHeader);
        pbLoading = findViewById(R.id.pbLoading);
        ImageView ivAvatar = findViewById(R.id.ivAvatar);
        tvSubBreedsHeader = findViewById(R.id.tvSubBreedHeader);

        breedsAdapter = new BreedsAdapter(this);

        initList();

        Breed breed = (Breed) getIntent().getSerializableExtra(BREED_KEY);

        BreedViewModel viewModel = ViewModelProviders.of(this, new BreedViewModelFactory(Objects.requireNonNull(breed).getName())).get(BreedViewModel.class);

        Glide.with(ivAvatar.getContext())
                .load(Uri.parse(breed.getAvatar().getUrl()))
                .into(ivAvatar);


       tvHeader.setText(breed.getName());

        viewModel.observeSubBreedsLiveData().observe(this, breedsList -> {
            if( breedsList.isEmpty()) {
                tvSubBreedsHeader.setVisibility(View.GONE);
            }else {
                tvSubBreedsHeader.setVisibility(View.VISIBLE);
                breedsAdapter.setItems(breedsList);
            }
        });

        viewModel.observeIsLoadingLiveData().observe(this,
                aBoolean -> pbLoading.setVisibility(aBoolean ? View.VISIBLE : View.GONE)
        );

//        viewModel.observeErrorLiveData().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(String s) {
//                tvHeader.setText(s);
//            }
//        });

    }

    private void initList() {
        rvBreeds.setLayoutManager(new LinearLayoutManager(this));
        rvBreeds.setAdapter(breedsAdapter);
    }


    @Override
    public void onBreedClick(Breed breed) {
        Intent intent = new Intent(BreedActivity.this, SubBreedActivity.class);
        intent.putExtra(SUB_BREED_KEY, breed);

        startActivity(intent);
    }
}

