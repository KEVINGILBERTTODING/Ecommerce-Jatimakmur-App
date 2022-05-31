package com.kencana.kampunganggrek.cekongkir.api;

/**
 * Created by Robby Dianputra on 10/31/2017.
 */

import com.kencana.kampunganggrek.cekongkir.model.city.ItemCity;
import com.kencana.kampunganggrek.cekongkir.model.cost.ItemCost;
import com.kencana.kampunganggrek.cekongkir.model.province.ItemProvince;

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
    @Headers("key:a868f02b0acee22c5cd0b64a4922aa69")
    Call<ItemProvince> getProvince();

    // City
    @GET("city")
    @Headers("key:a868f02b0acee22c5cd0b64a4922aa69")
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
