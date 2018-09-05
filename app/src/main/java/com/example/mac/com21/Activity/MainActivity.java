package com.example.mac.com21.Activity;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.mac.com21.Pojos.Autor;
import com.example.mac.com21.R;
import com.example.mac.com21.model.EndPointInterface;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    List<String> stringListAutores=new ArrayList<>();
    List<Autor> listAutores;
    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd")
            .create();
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://34.211.243.185:8080/")
//.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.listView);
        EndPointInterface apiService =
                retrofit.create(EndPointInterface.class);
        Call<List<Autor>> call = apiService.getAutores();
        call.enqueue(new Callback<List<Autor>>() {
            @Override
            public void onResponse(Call<List<Autor>> call,
                                   Response<List<Autor>> response) {
                listAutores = response.body();
                fillData(listAutores);
            }
            @Override
            public void onFailure(Call<List<Autor>> call, Throwable t) {
                Log.d("UDBFAILURE:Error",t.getMessage());
            }
        });
        FloatingActionButton fab = (FloatingActionButton)
                findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//Apertura de actividad de agregar.
                Intent intent = new
                        Intent(MainActivity.this,AddActivity.class);
                startActivity(intent);
            }
        });
    }
    private void fillData(List<Autor> autores) {
        for(Autor autor:autores){
            this.stringListAutores.add(autor.getNombre());
        }
        ArrayAdapter<String> arrayAdapter = new
                ArrayAdapter<>(this,android.R.layout.simple_list_item_1,this.stringListAutores)
                ;
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int
                    i, long l) {
                Intent intent = new
                        Intent(MainActivity.this,GridListActivity.class);
                intent.putExtra("idautor",String.valueOf(listAutores.get(i).getId()));
                startActivity(intent);
            }
        });
    }
}

