package com.crone.skillbranchtest.network.rest;

import com.crone.skillbranchtest.network.models.HousesModelRes;
import com.crone.skillbranchtest.network.models.PersonsModelRes;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;


public interface RestService {

    @GET("houses/{houseId}")
    Call<HousesModelRes> getHouse(@Path("houseId") String houseId);

    @GET("characters/{personId}")
    Call<PersonsModelRes> getPerson(@Path("personId") String personId);

}
