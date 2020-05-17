package ru.nsu.dogs.presentation.breeds;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import ru.nsu.dogs.R;
import ru.nsu.dogs.data.model.Breed;
import ru.nsu.dogs.presentation.breed.BreedActivity;
import ru.nsu.dogs.presentation.adapters.BreedsAdapter;
import ru.nsu.dogs.presentation.listeners.IOnBreedClickListener;

import static ru.nsu.dogs.presentation.breed.BreedActivity.BREED_KEY;

public class BreedsActivity extends AppCompatActivity implements IOnBreedClickListener {

    private RecyclerView rvBreeds;
    private ProgressBar pbLoading;
    private TextView tvHeader;
    private TextView tvErrorMessage;
    private BreedsAdapter breedsAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_breeds);
        rvBreeds = findViewById(R.id.rvBreeds);
        tvHeader = findViewById(R.id.tvHeader);
        pbLoading = findViewById(R.id.pbLoading);
        tvErrorMessage = findViewById(R.id.tvErrorMessage);
        breedsAdapter = new BreedsAdapter(this);

        initList();

        BreedsViewModel viewModel = ViewModelProviders.of(this).get(BreedsViewModel.class);

        viewModel.observeHeaderLiveData().observe(this, s -> tvHeader.setText(s));

        viewModel.observeBreedsLiveData().observe(this, breedsList -> breedsAdapter.setItems(breedsList));

        viewModel.observeIsLoadingLiveData().observe(this,
                aBoolean -> pbLoading.setVisibility(aBoolean ? View.VISIBLE : View.GONE)
        );

        viewModel.observeErrorLiveData().observe(this, s -> {
            tvErrorMessage.setText(s);
            tvErrorMessage.setVisibility(s.equals("") ? View.GONE : View.VISIBLE);
        });

    }

    private void initList() {
        rvBreeds.setLayoutManager(new LinearLayoutManager(this));
        rvBreeds.setAdapter(breedsAdapter);
    }

    @Override
    public void onBreedClick(Breed breed) {
        Intent intent = new Intent(BreedsActivity.this, BreedActivity.class);
        intent.putExtra(BREED_KEY, breed);

        startActivity(intent);
    }
}
