package com.example.nikulsheev.ui.main

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface EchoController {
    @GET("/random")
    fun test(@Query("json") json : Boolean):Call<gif>
}