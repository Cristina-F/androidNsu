package ru.nsu.dogs.presentation.breed;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import ru.nsu.dogs.DogsApplication;
import ru.nsu.dogs.data.model.Avatar;
import ru.nsu.dogs.data.model.Breed;
import ru.nsu.dogs.data.model.Breeds;
import ru.nsu.dogs.data.network.DogsApiClient;
import ru.nsu.dogs.data.network.IDogsApi;

public class BreedViewModel extends ViewModel {
    private IDogsApi dogsApi;

    private MutableLiveData<List<Breed>> subBreedsLiveData = new MutableLiveData<>();
    private MutableLiveData<String> errorLiveData = new MutableLiveData<>("");
    private MutableLiveData<Boolean> isLoadingLiveData = new MutableLiveData<>(false);

    public LiveData<String> observeErrorLiveData() {
        return errorLiveData;
    }

    LiveData<List<Breed>> observeSubBreedsLiveData() {
        return subBreedsLiveData;
    }

    LiveData<Boolean> observeIsLoadingLiveData() {
        return isLoadingLiveData;
    }

    BreedViewModel(String breed) {
        dogsApi = DogsApiClient.getClient(DogsApplication.getInstance()).create(IDogsApi.class);
        subBreedsLiveData.setValue(new ArrayList<>());
        init(breed);
    }


    private void init(String breed) {
        isLoadingLiveData.setValue(true);
        dogsApi.getSubBreeds(breed)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<Breeds>() {
                    @Override
                    public void onSuccess(Breeds breeds) {
                        isLoadingLiveData.setValue(false);
                        getAvatars(breed, breeds.getMessage());
                    }

                    @Override
                    public void onError(Throwable e) {
                        isLoadingLiveData.setValue(false);
                        errorLiveData.setValue(e.getMessage());
                    }
                });
    }

    private void getAvatars(String breed, List<String> breedNames) {
        breedNames.forEach(subBreed -> getRandomAvatar(breed, subBreed));
    }

    private void getRandomAvatar(String breed, String subBreed) {
        isLoadingLiveData.setValue(true);
        dogsApi.getRandomSubBreedImage(breed, subBreed)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<Avatar>() {
                    @Override
                    public void onSuccess(Avatar avatar) {
                        List<Breed> b = subBreedsLiveData.getValue();
                        Objects.requireNonNull(b).add(new Breed(subBreed, avatar));
                        subBreedsLiveData.setValue(b);
                        isLoadingLiveData.setValue(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        isLoadingLiveData.setValue(false);
                        errorLiveData.setValue(e.getMessage());
                    }
                });
    }
}
