package com.example.licentaapp.Clase;

import android.os.Parcel;
import android.os.Parcelable;


public class Asociatie  {

    private String numeAsociatie;
    private String adresaAsociatie;
    private String nrEtaje;
    private String totalAp;
    private String suprafataComuna;
    private SpatiuVerde spatiuVerde;
    private String presedintele;

    public Asociatie() {
    }

    public Asociatie(String numeAsociatie, String adresaAsociatie, String nrEtaje, String totalAp, String suprafataComuna, SpatiuVerde spatiuVerde, String presedintele) {
        this.numeAsociatie = numeAsociatie;
        this.adresaAsociatie = adresaAsociatie;
        this.nrEtaje = nrEtaje;
        this.totalAp = totalAp;
        this.suprafataComuna = suprafataComuna;
        this.spatiuVerde = spatiuVerde;
        this.presedintele = presedintele;
    }

    public String getNumeAsociatie() {
        return numeAsociatie;
    }

    public void setNumeAsociatie(String numeAsociatie) {
        this.numeAsociatie = numeAsociatie;
    }

    public String getAdresaAsociatie() {
        return adresaAsociatie;
    }

    public void setAdresaAsociatie(String adresaAsociatie) {
        this.adresaAsociatie = adresaAsociatie;
    }

    public String getNrEtaje() {
        return nrEtaje;
    }

    public void setNrEtaje(String nrEtaje) {
        this.nrEtaje = nrEtaje;
    }

    public String getTotalAp() {
        return totalAp;
    }

    public void setTotalAp(String totalAp) {
        this.totalAp = totalAp;
    }

    public String getSuprafataComuna() {
        return suprafataComuna;
    }

    public void setSuprafataComuna(String suprafataComuna) {
        this.suprafataComuna = suprafataComuna;
    }

    public SpatiuVerde getSpatiuVerde() {
        return spatiuVerde;
    }

    public void setSpatiuVerde(SpatiuVerde spatiuVerde) {
        this.spatiuVerde = spatiuVerde;
    }

    public String getPresedintele() {
        return presedintele;
    }

    public void setPresedintele(String presedintele) {
        this.presedintele = presedintele;
    }



}
