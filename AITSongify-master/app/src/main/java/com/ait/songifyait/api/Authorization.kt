package com.ait.songifyait.api

import com.ait.songifyait.data.Token
import retrofit2.Call
import retrofit2.http.*
//IF AUTHORIZATION IS NOT WORKING
//You might need to register this app as one of your apps
//Instructions
// 1) Go to Spotify developer dashboard and create a new app
// 2) Once created, go to edit settings and copy the root package name of this app
//      "com.ait.songifyait" and the SHA1 fingerprint which can be found by clicking
//         gradle menu on the right side and searching "signingreport" and then clicking
//          enter
// 3) After this, take your client ID and client secret from the dashboard
//      and encode them with a base64 encoding (just google one) with the format
//      base64encode<client_id:client_secret>
// 4) Take this and set it as the text after Basic in the authorization header. Mine starts with MfFh

//MjFhNmRjMzgwNTEzNDFkNDgyODZjMjI1YjQxNDc5MjQ6OTJlOGYzZTJhODg4NDQxZDgzYzdkMmJjMmJmMDczYTc=
interface Authorization {
    @Headers("Authorization: Basic MjFhNmRjMzgwNTEzNDFkNDgyODZjMjI1YjQxNDc5MjQ6OTJlOGYzZTJhODg4NDQxZDgzYzdkMmJjMmJmMDczYTc=",
        "Content-Type: application/x-www-form-urlencoded")
    @POST("/api/token")
    fun getToken(
        @Query("grant_type") grantType: String = "client_credentials"): Call<Token>
}