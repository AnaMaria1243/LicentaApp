package com.example.licentaapp.Clase;

public class SablonIntretinere {

    private String titlu;
    private String nrAp;
    private String plataServiciuAdministrator;
    private String plataPaza;
    private String plataCuratenie;
    private String fondReparatii;
    private String fondCheltuieliNeprevazute;
    private String plataSlubrizare;
    private String plataLuminaComuna;
    private String plataApaIndividual;

    public SablonIntretinere() {
    }

    public SablonIntretinere(String titlu, String nrAp, String plataServiciuAdministrator, String plataPaza, String plataCuratenie, String fondReparatii, String fondCheltuieliNeprevazute, String plataSlubrizare, String plataLuminaComuna, String plataApaIndividual) {
        this.titlu = titlu;
        this.nrAp = nrAp;
        this.plataServiciuAdministrator = plataServiciuAdministrator;
        this.plataPaza = plataPaza;
        this.plataCuratenie = plataCuratenie;
        this.fondReparatii = fondReparatii;
        this.fondCheltuieliNeprevazute = fondCheltuieliNeprevazute;
        this.plataSlubrizare = plataSlubrizare;
        this.plataLuminaComuna = plataLuminaComuna;
        this.plataApaIndividual = plataApaIndividual;
    }

    public String getTitlu() {
        return titlu;
    }

    public void setTitlu(String titlu) {
        this.titlu = titlu;
    }

    public String getNrAp() {
        return nrAp;
    }

    public void setNrAp(String nrAp) {
        this.nrAp = nrAp;
    }

    public String getPlataServiciuAdministrator() {
        return plataServiciuAdministrator;
    }

    public void setPlataServiciuAdministrator(String plataServiciuAdministrator) {
        this.plataServiciuAdministrator = plataServiciuAdministrator;
    }

    public String getPlataPaza() {
        return plataPaza;
    }

    public void setPlataPaza(String plataPaza) {
        this.plataPaza = plataPaza;
    }

    public String getPlataCuratenie() {
        return plataCuratenie;
    }

    public void setPlataCuratenie(String plataCuratenie) {
        this.plataCuratenie = plataCuratenie;
    }

    public String getFondReparatii() {
        return fondReparatii;
    }

    public void setFondReparatii(String fondReparatii) {
        this.fondReparatii = fondReparatii;
    }

    public String getFondCheltuieliNeprevazute() {
        return fondCheltuieliNeprevazute;
    }

    public void setFondCheltuieliNeprevazute(String fondCheltuieliNeprevazute) {
        this.fondCheltuieliNeprevazute = fondCheltuieliNeprevazute;
    }

    public String getPlataSlubrizare() {
        return plataSlubrizare;
    }

    public void setPlataSlubrizare(String plataSlubrizare) {
        this.plataSlubrizare = plataSlubrizare;
    }

    public String getPlataLuminaComuna() {
        return plataLuminaComuna;
    }

    public void setPlataLuminaComuna(String plataLuminaComuna) {
        this.plataLuminaComuna = plataLuminaComuna;
    }


    public String getPlataApaIndividual() {
        return plataApaIndividual;
    }

    public void setPlataApaIndividual(String plataApaIndividual) {
        this.plataApaIndividual = plataApaIndividual;
    }
}
