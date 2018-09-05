package com.example.mac.com21.Activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.mac.com21.Pojos.Chiste;

import com.example.mac.com21.R;
import com.example.mac.com21.model.EndPointInterface;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;

import java.io.File;
import java.io.IOException;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class AddActivity extends AppCompatActivity {

    ProgressBar progressBar;
    ImageView imageView;
    ScrollView scrollView;
    static int REQUEST_CODE_CHOOSE = 432;
    //Contenedor de la imagen
    File imageFile;
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(EndPointInterface.base_url)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    EndPointInterface apiService =
            retrofit.create(EndPointInterface.class);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        imageView=(ImageView)findViewById(R.id.imagenchiste);
    }
    public void add(View view) {
        EditText nombre, texto;
        nombre = (EditText) findViewById(R.id.txtnombre);
        texto = (EditText) findViewById(R.id.txttexto);
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.mainlayout);
        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressbar);
        relativeLayout.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        Chiste chiste = new Chiste();
        chiste.setId_autor(1);//Id de usuario Anonimo.
        chiste.setNombre(nombre.getText().toString());
        chiste.setTexto(texto.getText().toString());
        RequestBody requestBody=null;
//Hay imagen para enviar
        if(imageFile != null){
            requestBody = RequestBody.create(
                    MediaType.parse(getContentResolver().getType(mSelected.get(0))),
                    imageFile);
            MultipartBody.Part body =
                    MultipartBody.Part.createFormData("imagen",
                            imageFile.getName(), requestBody);
            Call<ResponseBody> call = apiService.addChiste(
                    chiste.getId_autor(),
                    chiste.getNombre(),
                    chiste.getTexto(),
                    body);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call,
                                       Response<ResponseBody> response) {
                    try {
                        Log.d("UDBTEST:info",response.body().string().toString());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    AddActivity.this.finish();
                }
                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Log.d("UDBTEST:error reason",t.getMessage());
                    Toast.makeText(AddActivity.this,"No fue posible publicar su informacion", Toast.LENGTH_SHORT).show();
                }
            });
        }else{
            Call<ResponseBody> call = apiService.addChiste(
                    chiste.getId_autor(),
                    chiste.getNombre(),
                    chiste.getTexto()
            );
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call,
                                       Response<ResponseBody> response) {
                    try {
                        Log.d("UDBTEST:info",response.body().string().toString());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    AddActivity.this.finish();
                }
                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Log.d("UDBTEST:error reason",t.getMessage());
                    Toast.makeText(AddActivity.this,"No fue posible publicar su informacion", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
    public void fileFromGallery(View view){
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
// Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {
// Show an explanation to the user *asynchronously* -- don't block
// this thread waiting for the user's response! After the user
// sees the explanation, try again to request the permission.
            } else {
// No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new
                                String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        1);
            }
        }else{
//Librería matisse
            Matisse.from(this)
                    .choose(MimeType.of(MimeType.JPEG))//SOLO ACEPTARA JPEG
                    .countable(true)
                    .maxSelectable(1)//SOLO UN ARCHIVO
                    .thumbnailScale(0.85f)
                    .imageEngine(new GlideEngine())
                    .forResult(REQUEST_CODE_CHOOSE);//
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[]
                                                   grantResults) {
        switch (requestCode) {
            case 1: {
// If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] ==
                        PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(AddActivity.this,"Permisos otorgados para acceder a fotos", Toast.LENGTH_SHORT);
                } else {
// permission denied,Disable the
// functionality that depends on this permission.
                    Toast.makeText(AddActivity.this, "Permiso denegado para acceder a fotos", Toast.LENGTH_SHORT).show();
                }
                return;
            }
// other 'case' lines to check for other
// permissions this app might request
        }
    }
    List<Uri> mSelected;
    //Utilizando la libreria Matisse para el resultado
    protected void onActivityResult(int requestCode, int resultCode, Intent
            data) {
        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == RESULT_OK) {
            mSelected = Matisse.obtainResult(data);
//Obteniendo la imagen
            Log.d("Matisse", "mSelected: " + mSelected);
            String path = getRealPathFromUri(this, mSelected.get(0));
            imageFile = new File(path);
            Glide.with(this).load(mSelected.get(0)).centerCrop().into(imageView);
        }
    }
    public static String getRealPathFromUri(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = { MediaStore.Images.Media.DATA };
            cursor = context.getContentResolver().query(contentUri, proj, null,
                    null, null);
            int column_index =
                    cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }
}