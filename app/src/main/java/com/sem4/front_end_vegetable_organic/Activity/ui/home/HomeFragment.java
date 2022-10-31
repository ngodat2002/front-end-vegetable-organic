package com.sem4.front_end_vegetable_organic.Activity.ui.home;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.google.android.material.navigation.NavigationView;
import com.nex3z.notificationbadge.NotificationBadge;
import com.sem4.front_end_vegetable_organic.Activity.GioHangActivity;
import com.sem4.front_end_vegetable_organic.Activity.MainActivity;
import com.sem4.front_end_vegetable_organic.Activity.SearchActivity;
import com.sem4.front_end_vegetable_organic.Adapter.LoaiSpAdapter;
import com.sem4.front_end_vegetable_organic.Adapter.SanPhamMoiAdapter;
import com.sem4.front_end_vegetable_organic.R;
import com.sem4.front_end_vegetable_organic.model.Menu;
import com.sem4.front_end_vegetable_organic.model.Product;
import com.sem4.front_end_vegetable_organic.retrofit.ApiBanHang;
import com.sem4.front_end_vegetable_organic.retrofit.RetrofitClient;
import com.sem4.front_end_vegetable_organic.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class HomeFragment extends Fragment {
    Toolbar toolbarHomePage;
    RecyclerView recyclerViewHomePage;
    NavigationView navigationView;
    ListView listviewHomePage;
    LoaiSpAdapter loaiSpAdapter;
    List<Menu> mangloaisp;
    CompositeDisposable compositeDisposable=new CompositeDisposable();
    ApiBanHang apiBanHang;
    List<Product>mangSpMoi;
    SanPhamMoiAdapter spAdapter;
    NotificationBadge badge;
    FrameLayout frameLayout;
    TextView search;
//            , call;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home,container,false);
        apiBanHang= RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBanHang.class);
        
        //anh xa
//        call = root.findViewById(R.id.call);
        search = root.findViewById(R.id.search);
        toolbarHomePage = root.findViewById(R.id.toolbarhomepage);

        recyclerViewHomePage = root.findViewById(R.id.recycleviewhomepage);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerViewHomePage.setLayoutManager(layoutManager);
        recyclerViewHomePage.setHasFixedSize(true);

        badge = root.findViewById(R.id.menu_sl);
        frameLayout = root.findViewById(R.id.framegiohang);
        navigationView = root.findViewById(R.id.navigationview);
        listviewHomePage = root.findViewById(R.id.listviewhomepage);
        // khoi tao list
        mangloaisp = new ArrayList<>();
        mangSpMoi = new ArrayList<>();

        if(Utils.manggiohang == null){
            Utils.manggiohang = new ArrayList<>();
        }else {
            int totalItem = 0;
            for(int i=0;i<Utils.manggiohang.size();i++){
                totalItem = totalItem+Utils.manggiohang.get(i).getSoluong();
            }
            badge.setText(String.valueOf(totalItem));
        }

        frameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),GioHangActivity.class));
            }
        });

//        call.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + "0906677088"));
//                startActivity(intent);
//            }
//        });
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                Log.d("TAG", "onClick: " + search.getTransitionName());
                startActivity(intent);
            }
        });
        //getBanner

        //getLoaiSp
//        compositeDisposable.add(apiBanHang.getLoaiSp()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(
//                        menuModel->{
//                            if(menuModel.isSuccess()){
//                                mangloaisp=menuModel.getResult();
//                                // khoi tao adapter
//                                loaiSpAdapter=new LoaiSpAdapter(mangloaisp, getActivity());
//                                listviewHomePage.setAdapter(loaiSpAdapter);
//                            }
//                        }
//                ));
        //getSpMoi
        compositeDisposable.add(apiBanHang.getSpMoi()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        productModel -> {
                            if(productModel.isSuccess()){
                                mangSpMoi=productModel.getResult();
                                spAdapter=new SanPhamMoiAdapter(getActivity(),mangSpMoi);
                                recyclerViewHomePage.setAdapter(spAdapter);
                            }
                        }
//                        ,
//                        throwable -> {
//                            Toast.makeText(getActivity(), "Không kết nối dc với server " + throwable.getMessage(), Toast.LENGTH_LONG).show();
//                        }
                ));
        return root;
    }
    @Override
    public void onResume() {
        super.onResume();
        int totalItem=0;
        for(int i=0;i<Utils.manggiohang.size();i++){
            totalItem=totalItem+Utils.manggiohang.get(i).getSoluong();
        }
        badge.setText(String.valueOf(totalItem));
    }
}