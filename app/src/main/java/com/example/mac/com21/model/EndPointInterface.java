package com.example.mac.com21.model;

import com.example.mac.com21.Pojos.Autor;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;


public interface EndPointInterface {

    String base_url = "http://34.211.243.185:8080/";
    //Agregamos la ruta de imágenes en el webservice
    String image_base_url = "http://34.211.243.185:8080/images/";
    @GET("autores")
    Call<List<Autor>> getAutores();
    @GET("autores/{id}")
    Call<Autor> getAutor(@Path("id") String id);
    //Mantener especial cuidado en utilizar o no utilizar
//los slash "/" para acceder a los recursos
//el método post requiere que no se utilicen
    @FormUrlEncoded
    @POST("autores")
    Call<ResponseBody> addAutor(
            @Field("nombre") String nombre);
    //Mantener especial cuidado en utilizar o no utilizar
//los slash "/" para acceder a los recursos
//el método post requiere que no se utilicen
    @FormUrlEncoded
    @POST("chistes")
    Call<ResponseBody> addChiste(
            @Field("idautor") int idautor,
            @Field("nombre") String nombre,
            @Field("texto") String texto);
    //El método de agregar ahora incorporando
//subida de imágenes.
    @Multipart
    @POST("chistes")
    Call<ResponseBody> addChiste(
            @Part("idautor") int idautor,
            @Part("nombre") String nombre,
            @Part("texto") String texto,
            @Part MultipartBody.Part image);

}
