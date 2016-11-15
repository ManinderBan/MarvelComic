package com.maninder.marvelcomics.data.remote.service;

import com.google.gson.JsonObject;

import retrofit2.http.GET;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by Maninder on 14/11/16.
 */

public interface ComicsListService {

    @GET
    Observable<JsonObject> getComicsList(@Url String comics);
}
