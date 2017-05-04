package com.siziksu.ru.data.client;

import com.siziksu.ru.common.model.response.users.Users;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IRandomUserClientService {

    String URI = "api/1.1/";
    String GET_USERS = URI + "?seed=abc&nat=es";

    @GET(GET_USERS)
    Observable<Users> getUsers(
            @Query("page") Integer page,
            @Query("results") Integer results
    );
}
