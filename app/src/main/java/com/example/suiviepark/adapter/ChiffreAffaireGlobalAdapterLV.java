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
import com.example.suiviepark.model.ChiffreAffaire;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;


public class ChiffreAffaireGlobalAdapterLV extends ArrayAdapter<ChiffreAffaire> {


    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    DecimalFormat formatter = new DecimalFormat("0.000");
    private final Activity activity;
    private final ArrayList<ChiffreAffaire> listChiffreAffaire;

    public ChiffreAffaireGlobalAdapterLV(Activity activity, ArrayList<ChiffreAffaire> listChiffreAffaire) {
        super(activity, R.layout.item_chiffre_affaire, listChiffreAffaire);
        // TODO Auto-generated constructor stub
        this.activity = activity;
        this.listChiffreAffaire = listChiffreAffaire;
    }

    public View getView(int position, View view, ViewGroup parent) {

        LayoutInflater inflater = activity.getLayoutInflater();
        Context context = parent.getContext();
        View rowView = inflater.inflate(R.layout.item_chiffre_affaire , null, true);

        ChiffreAffaire ca = listChiffreAffaire.get(position);


        TextView txt_raison_social = (TextView) rowView.findViewById(R.id.txt_raison_social);
        TextView txt_num_ticket = (TextView) rowView.findViewById(R.id.txt_num_ticket);
        TextView txt_date_piece = (TextView) rowView.findViewById(R.id.txt_date_piece);


        TextView txt_total_ht = (TextView) rowView.findViewById(R.id.txt_t_ht);
        TextView txt_t_net_ht = (TextView) rowView.findViewById(R.id.txt_t_net_ht);
        TextView txt_t_ttc = (TextView) rowView.findViewById(R.id.txt_t_ttc);


        DecimalFormat decF = new DecimalFormat("0.000");

        try {
            txt_raison_social.setText(ca.getRaisonSociale());
            txt_num_ticket.setText(ca.getNumeroPiece());
            txt_date_piece.setText(sdf.format(ca.getDatePiece()));


            txt_total_ht.setText(decF.format(ca.getTotalHT()) + "");
            txt_t_net_ht.setText(decF.format(ca.getTotalNetHT()));
            txt_t_ttc.setText(decF.format(ca.getTotalTTC()));


        } catch (Exception ex) {
            Log.e("ERROR_adapter", ex.getMessage().toString());
        }


        return rowView;

    }


}


