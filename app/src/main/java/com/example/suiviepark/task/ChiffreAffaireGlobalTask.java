package com.example.suiviepark.task;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;


import com.example.suiviepark.ChiffreAffaireGlobalActivity;
import com.example.suiviepark.ConnectionClass;
import com.example.suiviepark.adapter.ChiffreAffaireGlobalAdapterLV;
import com.example.suiviepark.model.ChiffreAffaire;
import com.example.suiviepark.param.Param;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class ChiffreAffaireGlobalTask extends AsyncTask<String, String, String> {


    Activity activity;

    ListView lv_hist_ca;
    ProgressBar pb;


    String z = "";
    ConnectionClass connectionClass;
    String user, password, base, ip;

    String NomUtilisateur;
    Date date_debut , date_fin  ;
    DateFormat dtfSQL = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

    ArrayList<ChiffreAffaire> listChiffreAffaire = new ArrayList<>();

    double  total_ca  = 0  ;
    public ChiffreAffaireGlobalTask(Activity activity, Date date_debut , Date date_fin  , ListView lv_hist_ca, ProgressBar pb ) {
        this.activity = activity;
        this.date_debut = date_debut  ;
        this.date_fin = date_fin  ;
        this.lv_hist_ca = lv_hist_ca;
        this.pb = pb;



        SharedPreferences prefe = activity.getSharedPreferences(Param.PEF_SERVER, Context.MODE_PRIVATE);
        user = prefe.getString("user", user);
        ip = prefe.getString("ip", ip);
        password = prefe.getString("password", password);
        base = prefe.getString("base", base);


        Log.e("BON_CMD" ,Param.PEF_SERVER +"-"+ip+"-"+base) ;

      /*  SharedPreferences pref=activity.getSharedPreferences("usersession", Context.MODE_PRIVATE);
        SharedPreferences.Editor edt=pref.edit();
        NomUtilisateur= pref.getString("NomUtilisateur",NomUtilisateur);*/

        connectionClass = new ConnectionClass();

    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pb.setVisibility(View.VISIBLE);
    }

    @Override
    protected String doInBackground(String... params) {

        try {
            Connection con = connectionClass.CONN(ip, password, user, base);
            if (con == null) {
                z = "Error in connection with SQL server";
            } else {

                String queryHis_bc = "  DECLARE\t@return_value int\n" +
                        "\n" +
                        "EXEC\t@return_value = [dbo].[ChiffreAffaireGlobalParPeriode]\n" +
                        "\t\t@DateDebut = N'"+df.format(date_debut)+"',\n" +
                        "\t\t@DateFin = N'"+df.format(date_fin)+"'\n" +
                        "\n" +
                        "SELECT\t'Return Value' = @return_value\n ";


                Log.e("queryHis_bc",""+queryHis_bc) ;
                PreparedStatement ps = con.prepareStatement(queryHis_bc);
                ResultSet rs = ps.executeQuery();

                total_ca =0 ;
                while (rs.next()) {

                    Date DatePiece = dtfSQL.parse(rs.getString("DatePiece"));
                    String NumeroPiece = rs.getString("NumeroPiece");
                    String TypeOperation = rs.getString("TypeOperation");

                    String CodeClient = rs.getString("CodeClient");
                    String RaisonSociale = rs.getString("RaisonSociale");

                    double TotalHT = rs.getDouble("TotalHT");
                    double TotalRemise = rs.getDouble("TotalRemise");
                    double TotalNetHT = rs.getDouble("TotalNetHT");
                    double TotalTTC = rs.getDouble("TotalTTC");
                    double TotalTVA = rs.getDouble("TotalTVA");

                    String NumeroSession = rs.getString("NumeroSession");

                    total_ca = total_ca +TotalTTC ;

                    ChiffreAffaire chiffreAffaire = new ChiffreAffaire(DatePiece,NumeroPiece, TypeOperation,   CodeClient , RaisonSociale , TotalHT,TotalRemise ,TotalNetHT ,TotalTTC ,TotalTVA , NumeroSession);
                    listChiffreAffaire.add(chiffreAffaire);

                }

                z = "Success";
            }
        } catch (Exception ex) {
            z = "Error retrieving data from table";
        }
        return z;
    }


    @Override
    protected void onPostExecute(String r) {

        pb.setVisibility(View.INVISIBLE);


        ChiffreAffaireGlobalAdapterLV  adapterLV = new ChiffreAffaireGlobalAdapterLV(activity  ,listChiffreAffaire) ;
        lv_hist_ca.setAdapter(adapterLV);

        DecimalFormat decF  = new DecimalFormat("0.000") ;
      //  EtatCommande.txt_tot_commande.setText(decF.format(total_bc)+" Dt");
        final NumberFormat instance = NumberFormat.getNumberInstance(Locale.FRENCH);
        instance.setMinimumFractionDigits(3);
        instance.setMaximumFractionDigits(3);
        ChiffreAffaireGlobalActivity. txt_tot_ca.setText(instance.format(total_ca));


    }


}