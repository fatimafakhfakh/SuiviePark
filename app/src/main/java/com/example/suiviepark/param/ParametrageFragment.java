package com.example.suiviepark.param;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.suiviepark.R;
import com.example.suiviepark.activity.SplachScreenActivity;
import com.example.suiviepark.model.ParametrageViewModel;


public class ParametrageFragment extends Fragment {

    private ParametrageViewModel parametrageViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState)  {
        parametrageViewModel =
                ViewModelProviders.of(this).get(ParametrageViewModel.class);
        View root = inflater.inflate(R.layout.fragment_parametrage, container, false);


        final CardView btn_param_server = root.findViewById(R.id.btn_param_server);
        final CardView btn_disconnect = root.findViewById(R.id.btn_disconnect);


        parametrageViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                //   textView.setText(s);
            }
        });


        btn_param_server.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent toParam = new Intent(getActivity(), Parametrage.class);
                startActivity(toParam);

            }
        });


        btn_disconnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences pref = getActivity().getSharedPreferences(Param.PREF_USER, Context.MODE_PRIVATE);
                SharedPreferences.Editor edt = pref.edit();
                edt.putBoolean("etatuser", false);
                edt.commit();
                getActivity().finish();
                Toast.makeText(getActivity(), "Déconnexion ..", Toast.LENGTH_LONG).show();
                Intent inte = new Intent(getActivity().getApplicationContext(), SplachScreenActivity.class);
                startActivity(inte);

            }
        });

        return root;
    }

}