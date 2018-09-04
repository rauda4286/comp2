package com.example.mac.com21.Pojos;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Autor implements Serializable {

    private int id;
    private String nombre;
    private Date creation_at;
    private Date updated_at;

    @SerializedName("chistesautor")
    private
    List<Chiste> chisteList;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Date getCreation_at() {
        return creation_at;
    }

    public void setCreation_at(Date creation_at) {
        this.creation_at = creation_at;
    }

    public Date getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }

    public List<Chiste> getChisteList() {
        return chisteList;
    }

    public void setChisteList(List<Chiste> chisteList) {
        this.chisteList = chisteList;
    }
}
