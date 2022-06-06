package com.JatimakmurApp.OngkirApi.api;


import com.JatimakmurApp.OngkirApi.model.city.ItemCity;
import com.JatimakmurApp.OngkirApi.model.cost.ItemCost;
import com.JatimakmurApp.OngkirApi.model.province.ItemProvince;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {

    // Province
    @GET("province")
    @Headers("key:06eb8d23d6c41a1f8c62d7c405b8ff21")
    Call<ItemProvince> getProvince();

    // City
    @GET("city")
    @Headers("key:06eb8d23d6c41a1f8c62d7c405b8ff21")
    Call<ItemCity> getCity(@Query("province") String province);

    // Cost
    @FormUrlEncoded
    @POST("cost")
    Call<ItemCost> getCost(@Field("key") String Token,
                           @Field("origin") String origin,
                           @Field("destination") String destination,
                           @Field("weight") String weight,
                           @Field("courier") String courier);

}
