package com.sem4.front_end_vegetable_organic.Activity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sem4.front_end_vegetable_organic.Adapter.CuAdapter;
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

public class CuActivity extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView recyclerView;
    ApiBanHang apiBanHang;
    CompositeDisposable compositeDisposable=new CompositeDisposable();
    int page=1;
    int category_id;
    CuAdapter adapterCu;
    List<Product> sanPhamList;
    LinearLayoutManager linearLayoutManager;
    Handler handler=new Handler();
    boolean isLoading=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cu);
        apiBanHang= RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBanHang.class);
        category_id=getIntent().getIntExtra("category_id",6);
        AnhXa();
        ActionToolBar();
        getData(page);
        addEventLoad();
    }

    private void addEventLoad() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if(isLoading == false){
                    if (linearLayoutManager.findLastCompletelyVisibleItemPosition() == sanPhamList.size()-1){
                        isLoading = true;
                        loadMore();
                    }
                }
            }
        });
    }

    private void loadMore() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                // add null
                sanPhamList.add(null);
                adapterCu.notifyItemInserted(sanPhamList.size()-1);
            }
        });
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // remover null
                sanPhamList.remove(sanPhamList.size() - 1);
                adapterCu.notifyItemRemoved(sanPhamList.size());
                page=page+1;
//                getData(page);
                adapterCu.notifyDataSetChanged();
                isLoading = false;
            }
        },2000);
    }

    private void getData(int page) {
        compositeDisposable.add(apiBanHang.getSanPham(page,category_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        productModel -> {
                            if(productModel.isSuccess()){
                                if(adapterCu == null){
                                    sanPhamList = productModel.getResult();
                                    adapterCu = new CuAdapter(getApplicationContext(),sanPhamList);
                                    recyclerView.setAdapter(adapterCu);
                                }else {
                                    int vitri = sanPhamList.size()-1;
                                    int soluongadd = productModel.getResult().size();
                                    for(int i = 0;i < soluongadd;i++){
                                        sanPhamList.add(productModel.getResult().get(i));
                                    }
                                    adapterCu.notifyItemRangeInserted(vitri,soluongadd);
                                }
                            }else {
                                Toast.makeText(getApplicationContext(), "Hết Dữ Liệu", Toast.LENGTH_LONG).show();
                                isLoading=true;
                            }
                        },
                        throwable -> {
                            Toast.makeText(getApplicationContext(), "Không kết nối server", Toast.LENGTH_LONG).show();
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

    private void AnhXa() {
        toolbar=findViewById(R.id.toolbar);
        recyclerView=findViewById(R.id.recycleview_cu);
        linearLayoutManager=new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        sanPhamList=new ArrayList<>();
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}