package com.juyng.movieapplication;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface NetworkService {
    //영화 조회
    @Headers({"X-Naver-Client-Id: XdrIxb2U9sAHv5dOQw42", "X-Naver-Client-Secret: b1QissjMDG"})
    @GET("/v1/search/movie.json")
    Call<MovieResult> getMovieList(@Query("query") String query);
}
