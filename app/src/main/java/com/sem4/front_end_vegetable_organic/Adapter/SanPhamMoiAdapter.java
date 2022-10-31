package com.sem4.front_end_vegetable_organic.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import com.sem4.front_end_vegetable_organic.Activity.ChiTietActivity;
import com.sem4.front_end_vegetable_organic.Interface.ItemClikListener;
import com.sem4.front_end_vegetable_organic.R;
import com.sem4.front_end_vegetable_organic.model.Product;
import com.sem4.front_end_vegetable_organic.utils.Utils;

import java.text.DecimalFormat;
import java.util.List;

public class SanPhamMoiAdapter extends RecyclerView.Adapter<SanPhamMoiAdapter.MyViewHolder> {
    Context context;
    List<Product>array;

    public SanPhamMoiAdapter(Context context, List<Product> array) {
        super();
        this.context = context;
        this.array = array;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sp_moi,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Product product = array.get(position);
        if(product==null){
            return;
        }
        holder.txtten.setText(product.getProduct_name());
        DecimalFormat decimalFormat=new DecimalFormat("###,###,###");
        holder.txtgia.setText("Giá : "+decimalFormat.format(Double.parseDouble(product.getPrice()))+" Đ");
        Glide.with(context).load(Utils.ipLoadImage + array.get(position).getProduct_image()).into(holder.imghinhanh);
        holder.setItemClikListener(new ItemClikListener() {
            @Override
            public void onClik(View view, int pos, boolean isLongClik) {
                if(!isLongClik){
                    // clik
                    Intent intent=new Intent(context, ChiTietActivity.class);
                    intent.putExtra("chitiet",product);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {

        return array.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txtgia,txtten;
        ImageView imghinhanh;
        private ItemClikListener itemClikListener;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtgia= itemView.findViewById(R.id.itensp_gia);
            txtten= itemView.findViewById(R.id.itensp_ten);
            imghinhanh= itemView.findViewById(R.id.itensp_image);
            itemView.setOnClickListener(this);
        }

        public void setItemClikListener(ItemClikListener itemClikListener) {
            this.itemClikListener = itemClikListener;
        }

        @Override
        public void onClick(View view) {
            itemClikListener.onClik(view,getAdapterPosition(),false);
        }
    }


}

