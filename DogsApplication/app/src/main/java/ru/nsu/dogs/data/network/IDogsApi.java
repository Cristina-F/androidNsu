package ru.nsu.dogs.data.network;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import ru.nsu.dogs.data.model.Avatar;
import ru.nsu.dogs.data.model.Breeds;

public interface IDogsApi {
    @GET("/api/breeds/list")
    Single<Breeds> getBreeds();

    @GET("/api/breed/{breed}/images/random")
    Single<Avatar> getRandomBreedImage(@Path("breed") String breed);

    @GET("/api/breed/{breed}/images")
    Single<Breeds> getBreedImages(@Path("breed") String breed);

    @GET("/api/breed/{breed}/list")
    Single<Breeds> getSubBreeds(@Path("breed") String breed);

    @GET("/api/breed/{breed}/{subBreed}/images/random")
    Single<Avatar> getRandomSubBreedImage(@Path("breed") String breed, @Path("subBreed") String subBreed);
}
