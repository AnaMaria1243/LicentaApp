package com.example.licentaapp.Clase;

public class PDF {
    private String titlu;
    private String url;

    public PDF(String titlu, String url) {
        this.titlu = titlu;
        this.url = url;
    }

    public String getTitlu() {
        return titlu;
    }

    public void setTitlu(String titlu) {
        this.titlu = titlu;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
