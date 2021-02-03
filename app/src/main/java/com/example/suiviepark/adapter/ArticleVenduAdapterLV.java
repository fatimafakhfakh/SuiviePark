package com.example.suiviepark.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.suiviepark.R;
import com.example.suiviepark.model.ArticleVendu;
import com.example.suiviepark.model.ChiffreAffaire;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;


public class ArticleVenduAdapterLV extends ArrayAdapter<ArticleVendu> {


    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    DecimalFormat formatter = new DecimalFormat("0.000");
    private final Activity activity;
    private final ArrayList<ArticleVendu> listArticleVendu;

    public ArticleVenduAdapterLV(Activity activity, ArrayList<ArticleVendu> listArticleVendu) {
        super(activity, R.layout.item_article_vendu, listArticleVendu);
        // TODO Auto-generated constructor stub
        this.activity = activity;
        this.listArticleVendu = listArticleVendu;
    }

    public View getView(int position, View view, ViewGroup parent) {

        LayoutInflater inflater = activity.getLayoutInflater();
        Context context = parent.getContext();
        View rowView = inflater.inflate(R.layout.item_article_vendu , null, true);

        ArticleVendu a = listArticleVendu.get(position);


        TextView txt_designation = (TextView) rowView.findViewById(R.id.txt_designation);
        TextView txt_etablie_par = (TextView) rowView.findViewById(R.id.txt_etablie_par);
        TextView txt_quantite = (TextView) rowView.findViewById(R.id.txt_qt);


        TextView txt_montant_ttc = (TextView) rowView.findViewById(R.id.txt_montant_ttc);



        DecimalFormat decF = new DecimalFormat("0.000");

        try {
            txt_designation.setText(a.getDesignation());
            txt_etablie_par.setText(a.getNomUtilisateur());

            txt_quantite.setText(a.getQuantite() + "");
            txt_montant_ttc.setText(decF.format(a.getMontantTTC()));



        } catch (Exception ex) {
            Log.e("ERROR_adapter", ex.getMessage().toString());
        }


        return rowView;

    }


}


