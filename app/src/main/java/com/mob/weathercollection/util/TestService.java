package com.mob.weathercollection.util;

import com.mob.weathercollection.model.Todo;

import retrofit2.Call;
import retrofit2.http.GET;

public interface TestService {
    @GET("/todos/1")
    Call<Todo> getTodo();
}
