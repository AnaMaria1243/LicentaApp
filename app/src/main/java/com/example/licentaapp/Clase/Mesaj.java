package com.example.licentaapp.Clase;

public class Mesaj {
    private String user;
    private String data;
    private String continut;

    public Mesaj(String user, String data, String continut) {
        this.user = user;
        this.data = data;
        this.continut = continut;
    }

    public Mesaj() {
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getContinut() {
        return continut;
    }

    public void setContinut(String continut) {
        this.continut = continut;
    }
}
