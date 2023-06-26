package com.example.licentaapp.Clase;

public class Singleton {
    private static Singleton instance;
    private String asociatieNume;

    public Singleton() {
    }

    public static synchronized Singleton getInstance() {
        if (instance == null) {
            instance = new Singleton();
        }
        return instance;
    }

    public String getAsociatieNume() {
        return asociatieNume;
    }

    public void setAsociatieNume(String nume) {
        asociatieNume = nume;
    }


}
