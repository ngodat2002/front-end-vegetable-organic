package com.sem4.front_end_vegetable_organic.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sem4.front_end_vegetable_organic.R;
import com.sem4.front_end_vegetable_organic.model.Order;

import java.util.List;


public class DonHangAdapter extends RecyclerView.Adapter<DonHangAdapter.MyViewHolder> {
    private RecyclerView.RecycledViewPool viewPool=new RecyclerView.RecycledViewPool();
    Context context;
    List<Order>listdonhang;

    public DonHangAdapter(Context context, List<Order> listdonhang) {
        super();
        this.context = context;
        this.listdonhang = listdonhang;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_donhang,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Order donhang=listdonhang.get(position);

        holder.txtdonhang.setText("Đơn hàng: "+ donhang.getOrder_id());
        holder.txttien.setText("Tổng tiền đơn hàng(Đã trừ phần giảm giá): "+ donhang.getAmount()+"Đ");
        LinearLayoutManager layoutManager=new LinearLayoutManager(
                holder.reChiTiet.getContext(),
                LinearLayoutManager.VERTICAL,
                false
        );
        layoutManager.setInitialPrefetchItemCount(donhang.getItem().size());
        // adapter chi tiet
        ChitietAdapter chitietAdapter=new ChitietAdapter(context, donhang.getItem());
        holder.reChiTiet.setLayoutManager(layoutManager);
        holder.reChiTiet.setAdapter(chitietAdapter);
        holder.reChiTiet.setRecycledViewPool(viewPool);
    }

    @Override
    public int getItemCount() {

        return listdonhang.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txtdonhang, txttien;
        RecyclerView reChiTiet;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtdonhang=itemView.findViewById(R.id.iddonhang);
            txttien=itemView.findViewById(R.id.idtongtien);
            reChiTiet=itemView.findViewById(R.id.recycleview_chitiet);
        }
    }
}
