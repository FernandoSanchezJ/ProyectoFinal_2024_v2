package com.example.pyoyectofinal;

import java.util.List;

public class Post {
    private String titulo;
    private String CP;
    private String ubicacion;
    private String municipio;
    private List<String> imageUrls;

    public Post(String titulo, String CP, String ubicacion, String municipio, List<String> imageUrls) {
        this.titulo = titulo;
        this.CP = CP;
        this.ubicacion = ubicacion;
        this.municipio = municipio;
        this.imageUrls = imageUrls;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getCP() {
        return CP;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public String getMunicipio() {
        return municipio;
    }

    public List<String> getImageUrls() {
        return imageUrls;
    }
}

