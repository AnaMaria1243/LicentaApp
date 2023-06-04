package com.example.licentaapp.Clase;

import android.os.Parcel;
import android.os.Parcelable;


public class Asociatie implements Parcelable {

    private String numeAsociatie;
    private String adresa;
    private int nrEtaje;
    private int totalAp;
    private float suprafataComuna;
    private SpatiuVerde spatiuVerde;
    private String presedintele;

    public Asociatie(String numeAsociatie, String adresa, int nrEtaje, int totalAp, float suprafataComuna, SpatiuVerde spatiuVerde, String presedintele) {
        this.numeAsociatie = numeAsociatie;
        this.adresa = adresa;
        this.nrEtaje = nrEtaje;
        this.totalAp = totalAp;
        this.suprafataComuna = suprafataComuna;
        this.spatiuVerde = spatiuVerde;
        this.presedintele = presedintele;
    }

    public Asociatie(Parcel parcel) {
        this.numeAsociatie= parcel.readString();
        this.adresa= parcel.readString();
        this.nrEtaje = parcel.readInt();
        this.spatiuVerde = SpatiuVerde.valueOf(parcel.readString());
        this.suprafataComuna = parcel.readFloat();
        this.presedintele= parcel.readString();

    }


    public static final Creator<Asociatie> CREATOR = new Creator<Asociatie>() {
        @Override
        public Asociatie createFromParcel(Parcel in) {
            return new Asociatie(in);
        }

        @Override
        public Asociatie[] newArray(int size) {
            return new Asociatie[size];
        }
    };

    public String getNumeAsociatie() {
        return numeAsociatie;
    }

    public void setNumeAsociatie(String numeAsociatie) {
        this.numeAsociatie = numeAsociatie;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public int getNrEtaje() {
        return nrEtaje;
    }

    public void setNrEtaje(int nrEtaje) {
        this.nrEtaje = nrEtaje;
    }

    public int getTotalAp() {
        return totalAp;
    }

    public void setTotalAp(int totalAp) {
        this.totalAp = totalAp;
    }

    public float getSuprafataComuna() {
        return suprafataComuna;
    }

    public void setSuprafataComuna(float suprafataComuna) {
        this.suprafataComuna = suprafataComuna;
    }

    public void setSpatiuVerde(SpatiuVerde spatiuVerde) {
        this.spatiuVerde = spatiuVerde;
    }

    public SpatiuVerde getSpatiuVerde() {
        return spatiuVerde;
    }


    public String getPresedintele() {
        return presedintele;
    }

    public void setPresedintele(String presedintele) {
        this.presedintele = presedintele;
    }

    @Override
    public String toString() {
        return "Asociatie{" +
                "numeAsociatie='" + numeAsociatie + '\'' +
                ", adresa='" + adresa + '\'' +
                ", nrEtaje=" + nrEtaje +
                ", totalAp=" + totalAp +
                ", suprafataComuna=" + suprafataComuna +
                ", spatiuVerde='" + spatiuVerde + '\'' +
                ", presedintele='" + presedintele + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(numeAsociatie);
        parcel.writeString(adresa);
        parcel.writeInt(nrEtaje);
        parcel.writeInt(totalAp);
        parcel.writeFloat(suprafataComuna);
        parcel.writeString(spatiuVerde.name());
        parcel.writeString(presedintele);
    }
}
