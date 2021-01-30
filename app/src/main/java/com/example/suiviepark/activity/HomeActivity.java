package com.example.suiviepark.activity;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.suiviepark.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class HomeActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        BottomNavigationView navViewBottom = findViewById(R.id.nav_view_bottom);


        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder
                (R.id.navigation_menu,     R.id.navigation_parametrage)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navViewBottom, navController);

      //  menuLateral () ;

    }


    /*public   void  menuLateral ()
    {
        //  latéral
        NavigationView  nav_menu=findViewById(R.id.nav_view);
        View headerView = nav_menu.getHeaderView(0);


        CardView btn_vente = (CardView)   headerView.findViewById(R.id.btn_vente) ;
        btn_vente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.nav_host_fragment,new MenuVenteFragment())
                        .addToBackStack(MenuAchatFragment.class.getSimpleName())
                        .commit();
            }
        });



        CardView btn_achat = (CardView)   headerView.findViewById(R.id.btn_achat) ;
        btn_achat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.nav_host_fragment,new MenuAchatFragment())
                        .addToBackStack(MenuAchatFragment.class.getSimpleName())
                        .commit();


            }
        });



        CardView btn_statistique = (CardView)   headerView.findViewById(R.id.btn_statistique) ;
        btn_statistique.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.nav_host_fragment,new MenuStatistiqueFragment())
                        .addToBackStack(MenuAchatFragment.class.getSimpleName())
                        .commit();

            }
        });


        CardView btn_stock = (CardView)   headerView.findViewById(R.id.btn_stock) ;
        btn_stock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.nav_host_fragment,new MenuStockFragment())
                        .addToBackStack(MenuAchatFragment.class.getSimpleName())
                        .commit();
            }
        });




        CardView btn_caisse = (CardView)   headerView.findViewById(R.id.btn_caisse) ;
        btn_caisse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.nav_host_fragment,new CaisseFragment())
                        .addToBackStack(MenuAchatFragment.class.getSimpleName())
                        .commit();
            }
        });



        CardView btn_tresorerie = (CardView)   headerView.findViewById(R.id.btn_tresorerie) ;
        btn_tresorerie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.nav_host_fragment,new MenuTresorerieFragment())
                        .addToBackStack(MenuAchatFragment.class.getSimpleName())
                        .commit();
            }
        });


    }*/

}