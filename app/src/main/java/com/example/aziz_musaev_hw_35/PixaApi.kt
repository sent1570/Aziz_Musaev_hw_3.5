package com.example.aziz_musaev_hw_35

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PixaApi {
    @GET("api/")
    fun getImages(@Query("key") key:String = "34533825-d95889eb66ea8e3adc5ef5e7a",
    @Query("q") pictureWord:String, @Query("per_page") perPage: Int = 20,
                  @Query("page")page:Int,): Call<PixaModel>

}