package com.example.mac.com21.Pojos;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Usuario on 23/7/18.
 */
public class Chiste  implements Serializable {

    private int id;
    private int id_autor;
    private String nombre;
    private String texto;
    private String image;
    private Date creation_at;
    private Date updated_at;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_autor() {
        return id_autor;
    }

    public void setId_autor(int id_autor) {
        this.id_autor = id_autor;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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
}
