package com.example.mac.com21.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.mac.com21.Adapters.ChistesAdapter;
import com.example.mac.com21.Details.DetailActivity;
import com.example.mac.com21.Pojos.Autor;
import com.example.mac.com21.Pojos.Chiste;

import com.example.mac.com21.model.EndPointInterface;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class GridListActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private GridView gridView;
    private ChistesAdapter adaptadorChistes;

    private Autor autor;


    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd")
            .create();


    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(EndPointInterface.base_url)//La interface tiene el base url
            //.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_list);


        gridView = (GridView) findViewById(R.id.gridview);

        EndPointInterface apiService = retrofit.create(EndPointInterface.class);

        String idautor = getIntent().getStringExtra("idautor");

        Call<Autor> call = apiService.getAutor(idautor);

        call.enqueue(new Callback<Autor>() {
            @Override
            public void onResponse(Call<Autor> call, Response<Autor> response) {
                Autor autor = response.body();

                fillData(autor);
            }


            @Override
            public void onFailure(Call<Autor> call, Throwable t) {
                Log.d("UDBLOG:Error", t.getMessage());
            }
        });


    }

    private void fillData(Autor autor) {
        this.autor = autor;
        adaptadorChistes = new ChistesAdapter(GridListActivity.this, autor.getChisteList());
        this.gridView.setAdapter(adaptadorChistes);
        this.gridView.setOnItemClickListener(this);
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Chiste chiste = this.autor.getChisteList().get(i);
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(DetailActivity.EXTRA_PARAM_ID, chiste);

            startActivity(intent);


    }

}
