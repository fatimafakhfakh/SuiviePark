package com.example.suiviepark.task;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.example.suiviepark.ArticleVenduParJourActivity;
import com.example.suiviepark.ChiffreAffaireGlobalActivity;
import com.example.suiviepark.ConnectionClass;
import com.example.suiviepark.adapter.ArticleVenduAdapterLV;
import com.example.suiviepark.adapter.ChiffreAffaireGlobalAdapterLV;
import com.example.suiviepark.model.ArticleVendu;
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

public class ArticleVenduParSessionTask extends AsyncTask<String, String, String> {


    Activity activity;

    String  NumeroSession ;
    ListView lv_list_art_vendu;
    ProgressBar pb ;


    String z = "";
    ConnectionClass connectionClass;
    String user, password, base, ip;

    DateFormat dtfSQL = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

    ArrayList<ArticleVendu> listArticleVendu = new ArrayList<>();

    double  total_ttc_art_vendu = 0  ;

    public ArticleVenduParSessionTask(Activity activity,  String  NumeroSession  , ListView lv_list_art_vendu, ProgressBar pb ) {
        this.activity = activity;
        this.NumeroSession = NumeroSession  ;
        this.lv_list_art_vendu = lv_list_art_vendu;
        this.pb = pb;



        SharedPreferences prefe = activity.getSharedPreferences(Param.PEF_SERVER, Context.MODE_PRIVATE);
        user = prefe.getString("user", user);
        ip = prefe.getString("ip", ip);
        password = prefe.getString("password", password);
        base = prefe.getString("base", base);


        Log.e("BON_CMD" ,Param.PEF_SERVER +"-"+ip+"-"+base) ;

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

                String query   = "  DECLARE\t@return_value int\n" +
                        "\n" +
                        "EXEC\t@return_value = [dbo].[ArticleVenduParJournee2]\n" +
                        "\t\t@NumeroSession = N'"+NumeroSession+"'\n" +
                        "\n" +
                        "SELECT\t'Return Value' = @return_value\n" +
                        "\n"  ;


                Log.e("queryart_vendu_par_sess",""+query) ;
                PreparedStatement ps = con.prepareStatement(query);
                ResultSet rs = ps.executeQuery();

                total_ttc_art_vendu =0 ;
                while (rs.next()) {

                    String CodeArticle = rs.getString("CodeArticle");
                    String Designation = rs.getString("Designation");

                    int Quantite = rs.getInt("Quantite");
                    double MontantTTC = rs.getDouble("MontantTTC");
                    String NomUtilisateur = rs.getString("NomUtilisateur");

                    total_ttc_art_vendu = total_ttc_art_vendu + MontantTTC ;


                    ArticleVendu  articleVendu  = new ArticleVendu(CodeArticle ,Designation ,Quantite ,MontantTTC ,NomUtilisateur) ;
                    listArticleVendu.add(articleVendu) ;


                }

                z = "Success";
            }
        } catch (Exception ex) {
            z = "Error retrieving data from table";
            Log.e("error_art_vendu",""+ex.getMessage().toString()) ;
        }
        return z;
    }


    @Override
    protected void onPostExecute(String r) {

        pb.setVisibility(View.INVISIBLE);

        ArticleVenduAdapterLV articleVenduAdapterLV = new ArticleVenduAdapterLV(activity , listArticleVendu) ;
        lv_list_art_vendu.setAdapter(articleVenduAdapterLV);



        final NumberFormat instance = NumberFormat.getNumberInstance(Locale.FRENCH);
        instance.setMinimumFractionDigits(3);
        instance.setMaximumFractionDigits(3);
       ArticleVenduParJourActivity. txt_tot.setText(instance.format(total_ttc_art_vendu));

    }

}