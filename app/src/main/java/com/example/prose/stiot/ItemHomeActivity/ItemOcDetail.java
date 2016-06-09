package com.example.prose.stiot.ItemHomeActivity;

/**
 * Created by billy on 19/04/16.
 */
public class ItemOcDetail {
    private String mesure;
    private String unite;
    private String valeur;

    public ItemOcDetail(String mesure,String valeur, String unite ) {
        this.valeur = valeur;
        this.unite = unite;
        this.mesure = mesure;
    }

    public String getMesure() {
        return mesure;
    }

    public String getUnite() {
        return unite;
    }



    public String getValeur() {
        return valeur;
    }

    /*public void setValeur(String valeur) {
        Valeur = valeur;
    }*/
}
