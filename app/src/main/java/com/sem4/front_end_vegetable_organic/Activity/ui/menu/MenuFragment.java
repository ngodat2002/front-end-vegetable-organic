package com.sem4.front_end_vegetable_organic.Activity.ui.menu;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sem4.front_end_vegetable_organic.Activity.CuActivity;
import com.sem4.front_end_vegetable_organic.Activity.QuaActivity;
import com.sem4.front_end_vegetable_organic.Activity.RauActivity;
import com.sem4.front_end_vegetable_organic.Activity.SearchActivity;
import com.sem4.front_end_vegetable_organic.R;

public class MenuFragment extends Fragment {
    TextView tvSearch;
    LinearLayout rau, cu, qua;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_menu, container, false);
        tvSearch = root.findViewById(R.id.search);
        rau = root.findViewById(R.id.Rau);
        cu = root.findViewById(R.id.Cu_qua);
        qua = root.findViewById(R.id.Trai_cay);

        tvSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), SearchActivity.class));
            }
        });
        rau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), RauActivity.class));
            }
        });
        cu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), QuaActivity.class));
            }
        });
        qua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), CuActivity.class));
            }
        });
        return root;
    }
}