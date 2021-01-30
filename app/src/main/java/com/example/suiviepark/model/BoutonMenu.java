package com.example.suiviepark.model;

public class BoutonMenu {

    private   int  icon  ;
    private   String  libelle  ;

    public BoutonMenu(int icon, String libelle) {
        this.icon = icon;
        this.libelle = libelle;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    @Override
    public String toString() {
        return "BoutonMenu{" +
                "icon=" + icon +
                ", libelle='" + libelle + '\'' +
                '}';
    }
}
