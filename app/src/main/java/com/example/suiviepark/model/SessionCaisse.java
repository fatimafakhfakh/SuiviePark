package com.example.suiviepark.model;

import java.util.Date;

public class SessionCaisse {

    private String NumeroSession;
    private Date DateSession;
    private Date DateOuverture;
    private String UserOuverture;
    private int Cloturer;
    private Date DateCloture;
    private String UserClouture;

    public SessionCaisse(String numeroSession, Date dateSession, Date dateOuverture, String userOuverture, int cloturer, Date dateCloture, String userClouture) {
        NumeroSession = numeroSession;
        DateSession = dateSession;
        DateOuverture = dateOuverture;
        UserOuverture = userOuverture;
        Cloturer = cloturer;
        DateCloture = dateCloture;
        UserClouture = userClouture;
    }

    public String getNumeroSession() {
        return NumeroSession;
    }

    public void setNumeroSession(String numeroSession) {
        NumeroSession = numeroSession;
    }

    public Date getDateSession() {
        return DateSession;
    }

    public void setDateSession(Date dateSession) {
        DateSession = dateSession;
    }

    public Date getDateOuverture() {
        return DateOuverture;
    }

    public void setDateOuverture(Date dateOuverture) {
        DateOuverture = dateOuverture;
    }

    public String getUserOuverture() {
        return UserOuverture;
    }

    public void setUserOuverture(String userOuverture) {
        UserOuverture = userOuverture;
    }

    public int getCloturer() {
        return Cloturer;
    }

    public void setCloturer(int cloturer) {
        Cloturer = cloturer;
    }

    public Date getDateCloture() {
        return DateCloture;
    }

    public void setDateCloture(Date dateCloture) {
        DateCloture = dateCloture;
    }

    public String getUserClouture() {
        return UserClouture;
    }

    public void setUserClouture(String userClouture) {
        UserClouture = userClouture;
    }

    @Override
    public String toString() {
        return "SessionCaisse{" +
                "NumeroSession='" + NumeroSession + '\'' +
                ", DateSession=" + DateSession +
                ", DateOuverture=" + DateOuverture +
                ", UserOuverture='" + UserOuverture + '\'' +
                ", Cloturer=" + Cloturer +
                ", DateCloture=" + DateCloture +
                ", UserClouture='" + UserClouture + '\'' +
                '}';
    }
}
