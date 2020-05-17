package ru.nsu.dogs.presentation.breed;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class BreedViewModelFactory implements ViewModelProvider.Factory {
    private String breed;

    BreedViewModelFactory(String breed) {
        this.breed = breed;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new BreedViewModel(breed);
    }
}