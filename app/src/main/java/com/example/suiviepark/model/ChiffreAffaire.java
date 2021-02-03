package com.example.suiviepark.model;

import java.util.Date;

public class ChiffreAffaire {

    Date DatePiece  ;
    String NumeroPiece   ;
    String TypeOperation  ;
    String CodeClient ;
    String RaisonSociale ;
    double TotalHT  ;
    double TotalRemise  ;
    double TotalNetHT  ;
    double TotalTTC  ;
    double TotalTVA  ;
    String NumeroSession ;


    public ChiffreAffaire(Date datePiece, String numeroPiece, String typeOperation, String codeClient, String raisonSociale, double totalHT, double totalRemise, double totalNetHT, double totalTTC, double totalTVA, String numeroSession) {
        DatePiece = datePiece;
        NumeroPiece = numeroPiece;
        TypeOperation = typeOperation;
        CodeClient = codeClient;
        RaisonSociale = raisonSociale;
        TotalHT = totalHT;
        TotalRemise = totalRemise;
        TotalNetHT = totalNetHT;
        TotalTTC = totalTTC;
        TotalTVA = totalTVA;
        NumeroSession = numeroSession;
    }


    public Date getDatePiece() {
        return DatePiece;
    }

    public void setDatePiece(Date datePiece) {
        DatePiece = datePiece;
    }

    public String getNumeroPiece() {
        return NumeroPiece;
    }

    public void setNumeroPiece(String numeroPiece) {
        NumeroPiece = numeroPiece;
    }

    public String getTypeOperation() {
        return TypeOperation;
    }

    public void setTypeOperation(String typeOperation) {
        TypeOperation = typeOperation;
    }

    public String getCodeClient() {
        return CodeClient;
    }

    public void setCodeClient(String codeClient) {
        CodeClient = codeClient;
    }

    public String getRaisonSociale() {
        return RaisonSociale;
    }

    public void setRaisonSociale(String raisonSociale) {
        RaisonSociale = raisonSociale;
    }

    public double getTotalHT() {
        return TotalHT;
    }

    public void setTotalHT(double totalHT) {
        TotalHT = totalHT;
    }

    public double getTotalRemise() {
        return TotalRemise;
    }

    public void setTotalRemise(double totalRemise) {
        TotalRemise = totalRemise;
    }

    public double getTotalNetHT() {
        return TotalNetHT;
    }

    public void setTotalNetHT(double totalNetHT) {
        TotalNetHT = totalNetHT;
    }

    public double getTotalTTC() {
        return TotalTTC;
    }

    public void setTotalTTC(double totalTTC) {
        TotalTTC = totalTTC;
    }

    public double getTotalTVA() {
        return TotalTVA;
    }

    public void setTotalTVA(double totalTVA) {
        TotalTVA = totalTVA;
    }

    public String getNumeroSession() {
        return NumeroSession;
    }

    public void setNumeroSession(String numeroSession) {
        NumeroSession = numeroSession;
    }

    @Override
    public String toString() {
        return "ChiffreAffaire{" +
                "DatePiece=" + DatePiece +
                ", NumeroPiece='" + NumeroPiece + '\'' +
                ", TypeOperation='" + TypeOperation + '\'' +
                ", CodeClient='" + CodeClient + '\'' +
                ", RaisonSociale='" + RaisonSociale + '\'' +
                ", TotalHT=" + TotalHT +
                ", TotalRemise=" + TotalRemise +
                ", TotalNetHT=" + TotalNetHT +
                ", TotalTTC=" + TotalTTC +
                ", TotalTVA=" + TotalTVA +
                ", NumeroSession='" + NumeroSession + '\'' +
                '}';
    }
}
