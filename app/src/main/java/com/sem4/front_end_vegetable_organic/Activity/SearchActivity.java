package com.sem4.front_end_vegetable_organic.Activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sem4.front_end_vegetable_organic.Adapter.RauAdapter;
import com.sem4.front_end_vegetable_organic.R;
import com.sem4.front_end_vegetable_organic.model.Product;
import com.sem4.front_end_vegetable_organic.retrofit.ApiBanHang;
import com.sem4.front_end_vegetable_organic.retrofit.RetrofitClient;
import com.sem4.front_end_vegetable_organic.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SearchActivity extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView recyclerView;
    EditText editsearch;
    RauAdapter adapterRau;
    List<Product> sanPhamList;
    ApiBanHang apiBanHang;
    CompositeDisposable compositeDisposable=new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initView();
        ActionToolBar();
    }

    private void initView() {
        sanPhamList = new ArrayList<>();
        apiBanHang = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBanHang.class);
        editsearch = findViewById(R.id.editsearch);
        toolbar = findViewById(R.id.toolbar);
        recyclerView=findViewById(R.id.recycleview_search);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        editsearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()==0){
                    sanPhamList.clear();
                    adapterRau=new RauAdapter(getApplicationContext(),sanPhamList);
                    recyclerView.setAdapter(adapterRau);
                }else {
                    getDataSearch(s.toString());

                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }

    private void getDataSearch(String s) {
        sanPhamList.clear();
        compositeDisposable.add(apiBanHang.search(s)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        productModel -> {
                            if(productModel.isSuccess()){
                                sanPhamList=productModel.getResult();
                                adapterRau=new RauAdapter(getApplicationContext(),sanPhamList);
                                recyclerView.setAdapter(adapterRau);
                            }
                        },throwable -> {
                            Toast.makeText(getApplicationContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                ));
    }

    private void ActionToolBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}