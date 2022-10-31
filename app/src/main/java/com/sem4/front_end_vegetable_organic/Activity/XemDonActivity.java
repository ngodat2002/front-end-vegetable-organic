package com.sem4.front_end_vegetable_organic.Activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.sem4.front_end_vegetable_organic.Adapter.DonHangAdapter;
import com.sem4.front_end_vegetable_organic.R;
import com.sem4.front_end_vegetable_organic.model.Order;
import com.sem4.front_end_vegetable_organic.model.OrderDetail;
import com.sem4.front_end_vegetable_organic.model.User;
import com.sem4.front_end_vegetable_organic.retrofit.ApiBanHang;
import com.sem4.front_end_vegetable_organic.retrofit.RetrofitClient;
import com.sem4.front_end_vegetable_organic.utils.Utils;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class XemDonActivity extends AppCompatActivity {
    CompositeDisposable compositeDisposable=new CompositeDisposable();
    ApiBanHang apiBanHang;
    RecyclerView redonhang;
    Toolbar toolbar;
    List<Order> listorder;
    List<OrderDetail> listOrderDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xem_don);
        getOrder();

        initView();
        initToolbar();


    }

    private void getOrder() {
        apiBanHang= RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBanHang.class);
        User user = new User();
        SharedPreferences sharedPreferences = getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        int user_id = sharedPreferences.getInt("user_id",user.getUserId());

        compositeDisposable.add(apiBanHang.xemDonHang(user_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        orderModel -> {
                            listorder=orderModel.getResult();

                            if(listorder.isEmpty()){
                                Toast.makeText(XemDonActivity.this,"Lich su don hang trong",Toast.LENGTH_LONG).show();
                            }
                            DonHangAdapter adapter=new DonHangAdapter(getApplicationContext(),orderModel.getResult());
                            redonhang.setAdapter(adapter);
//                            adapter.notifyDataSetChanged();
                        },
                        throwable -> {

                        }
                )
        );

    }


    private void initToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });
    }

    private void initView() {
        apiBanHang= RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBanHang.class);
        redonhang=findViewById(R.id.recycleview_donhang);
        toolbar=findViewById(R.id.toolbar);
        LinearLayoutManager layoutManager= new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        redonhang.setLayoutManager(layoutManager);
        redonhang.setHasFixedSize(true);
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}