package com.example.suiviepark.menu;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;


import com.example.suiviepark.ArticleVenduParJourActivity;
import com.example.suiviepark.ChiffreAffaireGlobalActivity;
import com.example.suiviepark.ConnectionClass;
import com.example.suiviepark.R;
import com.example.suiviepark.model.BoutonMenu;
import com.example.suiviepark.model.MenuViewModel;
import com.example.suiviepark.param.Param;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class MenuPrincipalFragment extends Fragment {

    String user, password, base, ip;
    private MenuViewModel menuViewModel;
    ConnectionClass connectionClass;
    String CodeSociete, NomUtilisateur, CodeLivreur;


    GridView gv_menu_principal;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        menuViewModel =
                ViewModelProviders.of(this).get(MenuViewModel.class);
        View root = inflater.inflate(R.layout.fragment_menu_principal, container, false);


        SharedPreferences pref = getActivity().getSharedPreferences(Param.PEF_SERVER, Context.MODE_PRIVATE);
        String NomSociete = pref.getString("NomSociete", "");
        TextView txt_libelle_soc = (TextView) root.findViewById(R.id.txt_libelle_soc);

        connectionClass = new ConnectionClass();

        SharedPreferences prefe = getActivity().getSharedPreferences("usersession", Context.MODE_PRIVATE);
        SharedPreferences.Editor edte = prefe.edit();
        NomUtilisateur = prefe.getString("NomUtilisateur", NomUtilisateur);

        // SharedPreferences pref = getSharedPreferences("usersessionsql", Context.MODE_PRIVATE);
        SharedPreferences.Editor edt = pref.edit();
        user = pref.getString("user", user);
        ip = pref.getString("ip", ip);
        password = pref.getString("password", password);
        base = pref.getString("base", base);
        CircleImageView img_soc = (CircleImageView) root.findViewById(R.id.img_soc);

        txt_libelle_soc.setText(NomSociete);
        if (NomSociete.equals("CCM")) {
            img_soc.setImageResource(R.drawable.ic_logo_ccm);
        } else if (NomSociete.equals("GMT")) {
            img_soc.setImageResource(R.drawable.ic_logo_gmt);
        } else if (NomSociete.contains("I2S")) {
            img_soc.setImageResource(R.drawable.i2s);
        } else if (NomSociete.contains("CMVI")) {
            img_soc.setImageResource(R.drawable.cmvi_logo);
        } else if (NomSociete.contains("MTD")) {
            img_soc.setImageResource(R.drawable.mtd_logo_transportatio);
        }

        gv_menu_principal = (GridView) root.findViewById(R.id.gv_menu_principal);

        ArrayList<BoutonMenu> list_bouton_principal = new ArrayList<>();

        // fill  list bouton
        list_bouton_principal.add(new BoutonMenu(R.drawable.ic_chiffre_affaire, "CA  par restaurant"));
        list_bouton_principal.add(new BoutonMenu(R.drawable.ic_chiffre_affaire, "CA des reastaurant"));
        list_bouton_principal.add(new BoutonMenu(R.drawable.ic_statistique_rapport_act, "Article Vendu par Période"));
        list_bouton_principal.add(new BoutonMenu(R.drawable.ic_benefice, "Benefice Article Vendu"));
        list_bouton_principal.add(new BoutonMenu(R.drawable.ic_art_vendu_par_journee, "Article Vendu par journée"));

        /*DoMenuParametrable doMenuParametrable = new DoMenuParametrable(list_bouton_principal);
        doMenuParametrable.execute("");*/

        MenuPrincipalGVAdapter adapter = new MenuPrincipalGVAdapter(getActivity(), list_bouton_principal);
        gv_menu_principal.setAdapter(adapter);


        return root;
    }


    //select ModuleRepresantant from ParametreDiver
    public class DoMenuParametrable extends AsyncTask<String, String, String> {
        String z = "";
        Boolean isSuccess = false;
        Boolean actif_module_represantant = false;
        Boolean actif_module_production = false;

        ArrayList<BoutonMenu> list_bouton_principal;

        public DoMenuParametrable(ArrayList<BoutonMenu> list_bouton_principal) {

            this.list_bouton_principal = list_bouton_principal;
        }

        @Override
        protected void onPreExecute() {

        }


        @Override
        protected String doInBackground(String... params) {

            try {

                Connection con = connectionClass.CONN(ip, password, user, base);
                if (con == null) {
                    z = "Error in connection with SQL server";
                } else {

                    String query = "select ModuleRepresantant , ModuleProduction  from ParametreDiver";

                    Log.e("query_menu", query);
                    Statement stmt = con.createStatement();
                    ResultSet rs = stmt.executeQuery(query);


                    if (rs.next()) {
                        boolean TEST = false;

                        actif_module_represantant = rs.getBoolean("ModuleRepresantant");
                        actif_module_production = rs.getBoolean("ModuleProduction");
                    }


                }
            } catch (SQLException ex) {
                isSuccess = false;
                z = ex.toString();
            }

            return z;
        }


        @Override
        protected void onPostExecute(String r) {

            if (actif_module_represantant) {
                list_bouton_principal.add(new BoutonMenu(R.drawable.ic_customer, "Gestion Representant"));

            }
            if (actif_module_production) {
                list_bouton_principal.add(new BoutonMenu(R.drawable.ic_production, "Production"));
            }

            MenuPrincipalGVAdapter adapter = new MenuPrincipalGVAdapter(getActivity(), list_bouton_principal);
            gv_menu_principal.setAdapter(adapter);

        }
    }


    public class MenuPrincipalGVAdapter extends BaseAdapter {
        private Activity activity;
        ArrayList<BoutonMenu> listBouton;


        public MenuPrincipalGVAdapter(Activity activity, ArrayList<BoutonMenu> listBouton) {
            this.activity = activity;
            this.listBouton = listBouton;
        }

        public View getView(final int position, final View convertView, ViewGroup parent) {


            LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View gridView;


            final BoutonMenu boutonMenu = listBouton.get(position);

            gridView = inflater.inflate(R.layout.item_bouton, null);


            CardView bouton = gridView.findViewById(R.id.bouton_x);
            TextView txt_libelle = gridView.findViewById(R.id.txt_libelle);
            ImageView img_icon = gridView.findViewById(R.id.ic_icon);

            img_icon.setImageResource(boutonMenu.getIcon());
            txt_libelle.setText(boutonMenu.getLibelle());


            bouton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                   if (boutonMenu.getLibelle().equals("CA  par restaurant")) {

                       getActivity().startActivity(new Intent(getActivity()  , ChiffreAffaireGlobalActivity.class));


                    }

                   else if  (boutonMenu.getLibelle().equals("Article Vendu par Période")) {

                        getActivity().startActivity(new Intent(getActivity()  , ArticleVenduParJourActivity.class));

                    }



                }
            });


            return gridView;
        }

        @Override
        public int getCount() {
            Log.e("SIZE_BASE", listBouton.size() + "");
            return listBouton.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }


    }

}