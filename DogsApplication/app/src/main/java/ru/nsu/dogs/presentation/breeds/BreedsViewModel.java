package ru.nsu.dogs.presentation.breeds;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import ru.nsu.dogs.R;
import ru.nsu.dogs.DogsApplication;
import ru.nsu.dogs.data.model.Avatar;
import ru.nsu.dogs.data.model.Breed;
import ru.nsu.dogs.data.model.Breeds;
import ru.nsu.dogs.data.network.DogsApiClient;
import ru.nsu.dogs.data.network.IDogsApi;

public class BreedsViewModel extends ViewModel {
    private MutableLiveData<String> headerLiveData = new MutableLiveData<>();
    private MutableLiveData<List<Breed>> breedsLiveData = new MutableLiveData<>();
    private MutableLiveData<String> errorLiveData = new MutableLiveData<>("");
    private MutableLiveData<Boolean> isLoadingLiveData = new MutableLiveData<>(false);
    private IDogsApi dogsApi;

    LiveData<String> observeErrorLiveData() {
        return errorLiveData;
    }

    LiveData<String> observeHeaderLiveData() {
        return headerLiveData;
    }

    LiveData<List<Breed>> observeBreedsLiveData() {
        return breedsLiveData;
    }

    LiveData<Boolean> observeIsLoadingLiveData() {
        return isLoadingLiveData;
    }

    public BreedsViewModel() {
        dogsApi = DogsApiClient.getClient(DogsApplication.getInstance()).create(IDogsApi.class);
        breedsLiveData.setValue(new ArrayList<>());
        init();
    }

    private void init() {
        isLoadingLiveData.setValue(true);
        dogsApi.getBreeds()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<Breeds>() {
                    @Override
                    public void onSuccess(Breeds breeds) {
                        getAvatars(breeds.getMessage());
                        headerLiveData.setValue(
                                DogsApplication.getInstance()
                                        .getString(R.string.breeds_header, breeds.getMessage().size()));
                    }

                    @Override
                    public void onError(Throwable e) {
                        isLoadingLiveData.setValue(false);
                        errorLiveData.setValue(
                                DogsApplication.getInstance()
                                        .getString(R.string.error_message, e.getMessage()));
                    }
                });
    }

    private void getAvatars(List<String> breedNames) {
        breedNames.forEach(this::getRandomAvatar);
    }

    private void getRandomAvatar(String name) {
        isLoadingLiveData.setValue(true);
        dogsApi.getRandomBreedImage(name)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<Avatar>() {
                    @Override
                    public void onSuccess(Avatar avatar) {
                        List<Breed> b = breedsLiveData.getValue();
                        Objects.requireNonNull(b).add(new Breed(name, avatar));
                        breedsLiveData.setValue(b);
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
