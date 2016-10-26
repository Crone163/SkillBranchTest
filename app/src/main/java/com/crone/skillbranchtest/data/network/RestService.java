package com.crone.skillbranchtest.data.network;

import com.crone.skillbranchtest.data.network.responces.HousesModelRes;
import com.crone.skillbranchtest.data.network.responces.PersonsModelRes;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;


public interface RestService {

    @GET("houses/{houseId}")
    Call<HousesModelRes> getHouse(@Path("houseId") String houseId);

    @GET("characters/{personId}")
    Call<PersonsModelRes> getPerson(@Path("personId") String personId);

}
