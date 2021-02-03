package com.example.suiviepark.model;

public class ArticleVendu {

    private   String  CodeArticle  ;
    private   String  Designation  ;
    private   int   Quantite   ;
    private   double   MontantTTC    ;
    private   String  NomUtilisateur  ;

    public ArticleVendu(String codeArticle, String designation, int quantite, double montantTTC, String nomUtilisateur) {
        CodeArticle = codeArticle;
        Designation = designation;
        Quantite = quantite;
        MontantTTC = montantTTC;
        NomUtilisateur = nomUtilisateur;
    }

    public String getCodeArticle() {
        return CodeArticle;
    }

    public void setCodeArticle(String codeArticle) {
        CodeArticle = codeArticle;
    }

    public String getDesignation() {
        return Designation;
    }

    public void setDesignation(String designation) {
        Designation = designation;
    }

    public int getQuantite() {
        return Quantite;
    }

    public void setQuantite(int quantite) {
        Quantite = quantite;
    }

    public double getMontantTTC() {
        return MontantTTC;
    }

    public void setMontantTTC(double montantTTC) {
        MontantTTC = montantTTC;
    }

    public String getNomUtilisateur() {
        return NomUtilisateur;
    }

    public void setNomUtilisateur(String nomUtilisateur) {
        NomUtilisateur = nomUtilisateur;
    }

    @Override
    public String toString() {
        return "ArticleVendu{" +
                "CodeArticle='" + CodeArticle + '\'' +
                ", Designation='" + Designation + '\'' +
                ", Quantite=" + Quantite +
                ", MontantTTC=" + MontantTTC +
                ", NomUtilisateur='" + NomUtilisateur + '\'' +
                '}';
    }
}
