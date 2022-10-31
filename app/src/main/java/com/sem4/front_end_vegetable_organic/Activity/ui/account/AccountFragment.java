package com.sem4.front_end_vegetable_organic.Activity.ui.account;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.sem4.front_end_vegetable_organic.Activity.XemDonActivity;
import com.sem4.front_end_vegetable_organic.R;

public class AccountFragment extends Fragment {
    TextView history;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_account, container, false);
        history = root.findViewById(R.id.History);
        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), XemDonActivity.class));
            }
        });
        return root;
    }
}