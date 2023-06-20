package com.example.licentaapp.Clase;

public class Apartament {
    private String nrApartament;
    private String etajApartament;
    private String numeProprietar;
    private String suprafataApartament;
    private String contact;

    public Apartament() {
    }

    public Apartament(String nrApartament, String etajApartament, String numeProprietar, String suprafataApartament, String contact) {
        this.nrApartament = nrApartament;
        this.etajApartament = etajApartament;
        this.numeProprietar = numeProprietar;
        this.suprafataApartament = suprafataApartament;
        this.contact = contact;
    }

    public String getNrApartament() {
        return nrApartament;
    }

    public void setNrApartament(String nrApartament) {
        this.nrApartament = nrApartament;
    }

    public String getEtajApartament() {
        return etajApartament;
    }

    public void setEtajApartament(String etajApartament) {
        this.etajApartament = etajApartament;
    }

    public String getNumeProprietar() {
        return numeProprietar;
    }

    public void setNumeProprietar(String numeProprietar) {
        this.numeProprietar = numeProprietar;
    }

    public String getSuprafataApartament() {
        return suprafataApartament;
    }

    public void setSuprafataApartament(String suprafataApartament) {
        this.suprafataApartament = suprafataApartament;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
}
