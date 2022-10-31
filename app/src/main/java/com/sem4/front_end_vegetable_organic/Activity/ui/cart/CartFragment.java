package com.sem4.front_end_vegetable_organic.Activity.ui.cart;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sem4.front_end_vegetable_organic.Activity.GioHangActivity;
import com.sem4.front_end_vegetable_organic.Activity.MainActivity;
import com.sem4.front_end_vegetable_organic.Activity.ThanhToanActivity;
import com.sem4.front_end_vegetable_organic.Adapter.GioHangAdapter;
import com.sem4.front_end_vegetable_organic.R;
import com.sem4.front_end_vegetable_organic.api.ApiClient;
import com.sem4.front_end_vegetable_organic.api.InterfaceApi;
import com.sem4.front_end_vegetable_organic.model.EventBus.TinhTongEvent;
import com.sem4.front_end_vegetable_organic.model.Product;
import com.sem4.front_end_vegetable_organic.utils.Utils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.DecimalFormat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartFragment extends Fragment {
    TextView giohangtrong,tongtien;
    Toolbar toolbar;
    RecyclerView recyclerView;
    Button btnmuahang;
    GioHangAdapter adapter;
    long tongtiensp = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_cart, container, false);
//        startActivity(new Intent(getActivity(),GioHangActivity.class));

        return root;
    }

}