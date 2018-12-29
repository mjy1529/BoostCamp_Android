package com.juyng.movieapplication;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class MainActivity extends AppCompatActivity {

    EditText editText;
    @BindView(R.id.searchBtn)
    Button searchBtn;
    private ArrayList<Movie> movieList;
    private MoviesAdapter mAdapter;
    RecyclerView recyclerView;

    NetworkService service;

    public static final String TAG = "MAIN";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initView();
        initNetworkService();
    }

    public void initView() {
        editText = (EditText) findViewById(R.id.editText);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    public void initNetworkService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://openapi.naver.com")
                .addConverterFactory(GsonConverterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
        service = retrofit.create(NetworkService.class);
    }

    @OnClick(R.id.searchBtn)
    public void onButtonClick() {
        String word = editText.getText().toString();
        if(!editText.getText().toString().equals("")) {
            Call<MovieResult> request = service.getMovieList(word);
            request.enqueue(new Callback<MovieResult>() {
                @Override
                public void onResponse(Call<MovieResult> call, Response<MovieResult> response) {
                    if (response.isSuccessful()) {
                        Log.d(TAG, "성공");
                        movieList = response.body().items;
                        mAdapter = new MoviesAdapter(getApplicationContext(), movieList);
                        recyclerView.setAdapter(mAdapter);
                    }
                }

                @Override
                public void onFailure(Call<MovieResult> call, Throwable t) {
                    Log.d(TAG, "실패 : " + t.getMessage());
                }
            });
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(editText.getWindowToken(), 0);
        } else {
            Toast.makeText(this, "검색어를 입력해주세요.", Toast.LENGTH_SHORT).show();
        }
    }
}
