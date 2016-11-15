package com.maninder.marvelcomics.data.remote;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.maninder.marvelcomics.data.ComicsDataSource;
import com.maninder.marvelcomics.data.remote.model.Comics;
import com.maninder.marvelcomics.data.remote.model.Image;
import com.maninder.marvelcomics.data.remote.model.Prices;
import com.maninder.marvelcomics.data.remote.service.ComicsListService;
import com.maninder.marvelcomics.data.remote.util.Utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import javax.inject.Singleton;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Maninder on 14/11/16.
 */

@Singleton
public class ComicsRemoteDataSource implements ComicsDataSource {
    private static Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
    public String hashSignature;
    public long timeStamp;
    public String key = "";

    public final String LOG = "ComicsRemoteDataSource";

    public ComicsRemoteDataSource() {
        this.timeStamp = calendar.getTimeInMillis() / 1000L;
        this.hashSignature = Utils.md5(String.valueOf(timeStamp) + ComicsRemoteDataCons.privateKey + ComicsRemoteDataCons.publicKey);
        key = "&ts=" + timeStamp + "&apikey=" + ComicsRemoteDataCons.publicKey + "&hash=" + hashSignature;

    }


    /**
     * Allow to get the Comics List from server by Retrofit 2
     * @param loadComicsListCallback
     */
    @Override
    public void getComicsList(@NonNull final LoadComicsListCallback loadComicsListCallback) {
        ComicsListService comicsListService = RestClient.getService(ComicsListService.class);

        String url = ComicsRemoteDataCons.comics_list + key;
        Observable<JsonObject> setListObservable = comicsListService.getComicsList(url);
        setListObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new Subscriber<JsonObject>() {
                    @Override
                    public void onCompleted() {
                        Log.d(LOG, "onCompeted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(LOG, "onError");
                        loadComicsListCallback.onDataNotAvailable();
                    }

                    @Override
                    public void onNext(JsonObject jsonObject) {
                        Log.d(LOG, "onNext");
                        getComicsfromJson(loadComicsListCallback, jsonObject);
                    }
                });
    }

    /**
     * This allow to get the List of Comics from JSONObject get from API
     * This is a very simple implementation.
     *
     * @param loadComicsListCallback callback to send the List of comics retrieved
     * @param marvelObject           This is the JSONObject get from Server
     */
    private void getComicsfromJson(LoadComicsListCallback loadComicsListCallback, JsonObject marvelObject) {
        List<Comics> comicsList = new ArrayList<>();
        JsonObject dataObject = marvelObject.getAsJsonObject("data");
        JsonArray jsonListComics = dataObject.getAsJsonArray("results");
        for (int i = 0; i < jsonListComics.size(); i++) {
            Comics comics = new Comics();
            JsonObject jsonComicsObject = jsonListComics.get(i).getAsJsonObject();
            if (jsonComicsObject.has("id")) {
                comics.id = jsonComicsObject.get("id").getAsInt();
            }

            if (jsonComicsObject.has("title") && jsonComicsObject.get("title") != JsonNull.INSTANCE) {
                comics.title = jsonComicsObject.get("title").getAsString();
            }

            if (jsonComicsObject.has("description") && jsonComicsObject.get("description") != JsonNull.INSTANCE) {
                comics.description = jsonComicsObject.get("description").getAsString();
            } else {
                comics.description = "";
            }

            if (jsonComicsObject.has("pageCount")) {
                comics.pageCount = jsonComicsObject.get("pageCount").getAsInt();
            }

            if (jsonComicsObject.has("prices")) {
                JsonArray jsonPriceComics = jsonComicsObject.getAsJsonArray("prices");
                if (jsonPriceComics.size() > 0) {
                    JsonObject jsonSinglePrice = jsonPriceComics.get(0).getAsJsonObject();
                    if (jsonSinglePrice.has("price")) {
                        Prices price = new Prices();
                        price.price = jsonSinglePrice.get("price").getAsDouble();
                        price.type = jsonSinglePrice.get("type").getAsString();
                        comics.prices = price;
                    }
                }
            }

            if (jsonComicsObject.has("thumbnail")) {
                JsonObject thumbJsonObject = jsonComicsObject.getAsJsonObject("thumbnail");
                if (thumbJsonObject.has("path") && jsonComicsObject.get("path") != JsonNull.INSTANCE) {
                    Image image = new Image();
                    image.path = thumbJsonObject.get("path").getAsString();
                    image.extension = thumbJsonObject.get("extension").getAsString();
                    comics.thumbnail = image;
                }
            }

            if (jsonComicsObject.has("images")) {
                JsonArray jsonImageComics = jsonComicsObject.getAsJsonArray("images");
                if (jsonImageComics.size() > 0) {
                    JsonObject jsonSingleImage = jsonImageComics.get(0).getAsJsonObject();
                    if (jsonSingleImage.has("path") && jsonComicsObject.get("path") != JsonNull.INSTANCE) {
                        Image image = new Image();
                        image.path = jsonSingleImage.get("path").getAsString();
                        image.extension = jsonSingleImage.get("extension").getAsString();
                        comics.images = image;
                    }
                }
            }

            if (jsonComicsObject.has("creators")) {
                JsonObject jsonCreatorObj = jsonComicsObject.get("creators").getAsJsonObject();
                if (jsonCreatorObj.has("items")) {
                    JsonArray jsonAuthor = jsonCreatorObj.get("items").getAsJsonArray();
                    if (jsonAuthor != null && jsonAuthor.size() > 0) {
                        JsonObject jsonObjAuthor = jsonAuthor.get(0).getAsJsonObject();
                        if (jsonObjAuthor.has("name") && jsonObjAuthor.get("name") != JsonNull.INSTANCE) {
                            comics.author = jsonObjAuthor.get("name").getAsString();
                        }
                        if (jsonObjAuthor.has("role") && jsonObjAuthor.get("role") != JsonNull.INSTANCE) {
                            comics.role = jsonObjAuthor.get("role").getAsString();
                        }
                    }
                }
            }
            comicsList.add(comics);
        }
        if (comicsList.size() > 0) {
            loadComicsListCallback.onComicsListLoaded(comicsList);
        } else {
            loadComicsListCallback.onDataNotAvailable();
        }
    }

    @Override
    public void getComics(@NonNull LoadComicsCallback loadComicsCallback, int id) {

    }

    @Override
    public void saveComicsList(@NonNull List<Comics> comicsList) {

    }
}
