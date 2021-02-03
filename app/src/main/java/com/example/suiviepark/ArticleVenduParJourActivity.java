package com.example.suiviepark;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.suiviepark.model.SessionCaisse;
import com.example.suiviepark.param.Param;
import com.example.suiviepark.task.ArticleVenduParSessionTask;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import devs.mulham.horizontalcalendar.HorizontalCalendar;
import devs.mulham.horizontalcalendar.HorizontalCalendarView;
import devs.mulham.horizontalcalendar.utils.HorizontalCalendarListener;

public class ArticleVenduParJourActivity extends AppCompatActivity {

    TabLayout tabSession  ;
    DateFormat dtfSQL = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    ListView  lv_list_article_vendu  ;
    ProgressBar  pb  ;

   public static TextView  txt_tot ;

   RelativeLayout   ll_session  ;
   TextView  txt_date_ouverture , txt_date_cloture  ,txt_libelle_cloture  , txt_nom_utilisateur ;
   ImageView   img_cloture  ;

    FloatingActionButton fab_arrow;
    RelativeLayout layoutBottomSheet;
    BottomSheetBehavior sheetBehavior;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_vendu_par_jour);


        tabSession  = (TabLayout)  findViewById(R.id.tab_session) ;
        lv_list_article_vendu = (ListView) findViewById(R.id.lv_list) ;
        pb = (ProgressBar)  findViewById(R.id.pb) ;
        txt_tot = (TextView) findViewById(R.id.txt_tot) ;

        ll_session = (RelativeLayout)  findViewById(R.id.ll_session) ;
        txt_date_ouverture= (TextView) findViewById(R.id.txt_date_ouverture) ;
        txt_date_cloture= (TextView) findViewById(R.id.txt_date_cloture) ;
        txt_libelle_cloture= (TextView) findViewById(R.id.txt_libelle_cloture) ;
        txt_nom_utilisateur= (TextView) findViewById(R.id.txt_nom_utilisateur) ;
        img_cloture = (ImageView) findViewById(R.id.img_cloture) ;


        pb.setVisibility(View.INVISIBLE);
        ListSessionTabTask  listSessionTabTask  = new ListSessionTabTask(this ,new Date() ,tabSession ) ;
        listSessionTabTask.execute() ;

        /* starts before 1 month from now */
        Calendar startDate = Calendar.getInstance();
        startDate.add(Calendar.DAY_OF_MONTH, -10);

        /* ends after 1 month from now */
        Calendar endDate = Calendar.getInstance();
        endDate.add(Calendar.DAY_OF_MONTH, 10);


        HorizontalCalendar horizontalCalendar = new HorizontalCalendar.Builder(this, R.id.calendarView)
                .range(startDate, endDate)
                .datesNumberOnScreen(5)
                .build();


        horizontalCalendar.setCalendarListener(new HorizontalCalendarListener() {
            @Override
            public void onDateSelected(Calendar date, int position) {
                Log.e("Date_selected", position + " " + date.getTime());

                ListSessionTabTask  listSessionTabTask  = new ListSessionTabTask(ArticleVenduParJourActivity.this ,date.getTime() ,tabSession ) ;
                listSessionTabTask.execute() ;

            }

            @Override
            public void onCalendarScroll(HorizontalCalendarView calendarView, int dx, int dy) {

            }

            @Override
            public boolean onDateLongClicked(Calendar date, int position) {

                return true;
            }

        });



        layoutBottomSheet = (RelativeLayout)  findViewById(R.id.bottom_sheet);
        fab_arrow = (FloatingActionButton)  findViewById(R.id.fab_arrow);
        sheetBehavior = BottomSheetBehavior.from(layoutBottomSheet);
        sheetBehavior.setHideable(false);

        sheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch (newState) {
                    case BottomSheetBehavior.STATE_HIDDEN:
                        break;
                    case BottomSheetBehavior.STATE_EXPANDED: {

                        // Toast.makeText(getActivity() , "Close Sheet" ,Toast.LENGTH_LONG).show();
                        fab_arrow.setImageResource(R.drawable.ic_arrow_down);

                      /* ReptureStockClientTaskTask reptureStockClientTaskTask = new ReptureStockClientTaskTask(getActivity() ,"1" , exptens_lv_stock_en_repture) ;
                        reptureStockClientTaskTask.execute() ;*/

                    }
                    break;
                    case BottomSheetBehavior.STATE_COLLAPSED: {
                        // Toast.makeText(getActivity() , "Expand Sheet" ,Toast.LENGTH_LONG).show();
                        fab_arrow.setImageResource(R.drawable.ic_arrow_up);
                    }
                    break;
                    case BottomSheetBehavior.STATE_DRAGGING:
                        break;
                    case BottomSheetBehavior.STATE_SETTLING:
                        break;
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });


    }



    public class ListSessionTabTask extends AsyncTask<String, String, String> {

        String res;

        Activity activity;
        TabLayout tab_session ;

        Date dateSession;


        ConnectionClass connectionClass;
        String user, password, base, ip;


        ArrayList<SessionCaisse> listSession  = new ArrayList<>();
        SimpleDateFormat  sdf  = new SimpleDateFormat("dd/MM/yyyy") ;
        SimpleDateFormat  sdhF  = new SimpleDateFormat("dd/MM/yyyy HH:mm") ;

        public ListSessionTabTask(Activity activity,   Date dateSession ,TabLayout tab_session) {

            this.activity = activity;
            this.dateSession = dateSession;
            this.tab_session = tab_session;


            SharedPreferences pref = activity.getSharedPreferences(Param.PEF_SERVER, Context.MODE_PRIVATE);
            user = pref.getString("user", user);
            ip = pref.getString("ip", ip);
            password = pref.getString("password", password);
            base = pref.getString("base", base);
            connectionClass = new ConnectionClass();


        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();


        }

        @Override
        protected String doInBackground(String... strings) {

            try {
                Connection con = connectionClass.CONN(ip, password, user, base);  // Connect to database
                Log.e("con", "" + con);
                if (con == null) {
                    res = "Check Your Internet Access!";
                } else {

                    String query = " select  * from  SessionCaisse   where    '"+sdf.format(dateSession)+"'  \n" +
                            "between  CONVERT (date  , DateOuverture)  and  CONVERT (date  , DateCloture) ";


                    Log.e("query_responsable", query);

                    Statement stmt = con.createStatement();
                    ResultSet rs = stmt.executeQuery(query);

                    while (rs.next()) {

                        String NumeroSession = rs.getString("NumeroSession");
                        Date DateSession = dtfSQL.parse(rs.getString("DateSession"));
                        Date DateOuverture = dtfSQL.parse(rs.getString("DateOuverture"));
                        String UserOuverture = rs.getString("UserOuverture");
                        int Cloturer = rs.getInt("Cloturer");
                        Date DateCloture = dtfSQL.parse(rs.getString("DateCloture"));
                        String UserClouture = rs.getString("UserClouture");


                        SessionCaisse  sessionCaisse  = new SessionCaisse(NumeroSession ,DateSession ,DateOuverture ,UserOuverture ,Cloturer ,DateCloture ,UserClouture );
                        listSession.add(sessionCaisse) ;
                    }
                }
                con.close();

            } catch (Exception ex) {

                res = ex.getMessage();
                Log.e("ERROR", res.toString());

            }
            return null;

        }


        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            tab_session.removeAllTabs();
            for (SessionCaisse sess : listSession) {
                tab_session.addTab(tab_session.newTab().setText(sess.getNumeroSession())); //0

            }


            if (listSession.size()> 0)
            {

                ll_session.setVisibility(View.VISIBLE);

                txt_date_ouverture.setText(sdhF.format(listSession.get(0).getDateOuverture()));
                txt_nom_utilisateur.setText(listSession.get(0).getUserOuverture());


                if (listSession.get(0).getCloturer() ==0 )
                {
                    txt_date_cloture.setVisibility(View.GONE);
                    txt_libelle_cloture.setVisibility(View.GONE);
                    img_cloture.setVisibility(View.GONE);

                }
                else   if (listSession.get(0).getCloturer() ==1 ){

                    txt_date_cloture.setVisibility(View.VISIBLE);
                    txt_libelle_cloture.setVisibility(View.VISIBLE);
                    img_cloture.setVisibility(View.VISIBLE);

                    txt_date_cloture.setText(sdhF.format(listSession.get(0).getDateCloture()));
                    txt_nom_utilisateur.setText(listSession.get(0).getUserClouture());
                }

                String  NumeroSession  = listSession.get(0).getNumeroSession() ;
                ArticleVenduParSessionTask articleVenduParSessionTask  = new ArticleVenduParSessionTask(activity , NumeroSession ,lv_list_article_vendu , pb) ;
                articleVenduParSessionTask.execute();

            }
            else if(listSession.size()== 0)
            {

                ll_session.setVisibility(View.GONE);

                ArticleVenduParSessionTask articleVenduParSessionTask  = new ArticleVenduParSessionTask(activity , "" ,lv_list_article_vendu , pb) ;
                articleVenduParSessionTask.execute();

            }

            tab_session.setOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {

                    try {
                        String  NumeroSession  = listSession.get(tab.getPosition()).getNumeroSession() ;


                        if (listSession.get( tab.getPosition()).getCloturer() ==0 )
                        {
                            txt_date_cloture.setVisibility(View.GONE);
                            txt_libelle_cloture.setVisibility(View.GONE);
                            img_cloture.setVisibility(View.GONE);

                        }
                        else   if (listSession.get(tab.getPosition() ).getCloturer() ==1 ){

                            txt_date_cloture.setVisibility(View.VISIBLE);
                            txt_libelle_cloture.setVisibility(View.VISIBLE);
                            img_cloture.setVisibility(View.VISIBLE);

                            txt_date_cloture.setText(sdhF.format(listSession.get(tab.getPosition() ).getDateCloture()));
                            txt_nom_utilisateur.setText(listSession.get(tab.getPosition() ).getUserClouture());
                        }

                        ArticleVenduParSessionTask articleVenduParSessionTask  = new ArticleVenduParSessionTask(activity , NumeroSession ,lv_list_article_vendu , pb) ;
                        articleVenduParSessionTask.execute();
                    }
                    catch (Exception  ex )
                    {
                     Log.e("ERROR" ,ex.getMessage().toString()) ;
                    }

                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {

                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {

                }
            });


        }


    }

}