package com.sem4.front_end_vegetable_organic.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
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

public class QuaAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    Context context;
    List<Product> array;
    private static final int VIEW_TYPE_DATA=0;
    private static final int VIEW_TYPE_LOADING=1;

    public QuaAdapter(Context context, List<Product> array) {
        super();
        this.context = context;
        this.array = array;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType==VIEW_TYPE_DATA){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_qua,parent,false);
            return new QuaAdapter.MyViewHolder(view);
        }else {
            View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading,parent,false);
            return new QuaAdapter.LoadingViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof QuaAdapter.MyViewHolder){
            QuaAdapter.MyViewHolder myViewHolder=(QuaAdapter.MyViewHolder) holder;
            Product product = array.get(position);
            myViewHolder.tensp.setText(product.getProduct_name());
            DecimalFormat decimalFormat=new DecimalFormat("###,###,###");
            myViewHolder.giasp.setText("Giá: "+decimalFormat.format(Double.parseDouble(product.getPrice()))+" Đ/Kg");
            myViewHolder.mota.setText(product.getDescription());
//            Glide.with(context).load(product.getProduct_image()).into(myViewHolder.hinhanh);
            Glide.with(context).load(Utils.ipLoadImage + array.get(position).getProduct_image()).into(myViewHolder.hinhanh);
            myViewHolder.setItemClikListener(new ItemClikListener() {
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
        }else {
            QuaAdapter.LoadingViewHolder loadingViewHolder=(QuaAdapter.LoadingViewHolder) holder;
            loadingViewHolder.progressBar.setIndeterminate(true);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return array.get(position)==null ? VIEW_TYPE_LOADING:VIEW_TYPE_DATA;
    }

    @Override
    public int getItemCount() {

        return array.size();
    }
    public class LoadingViewHolder extends RecyclerView.ViewHolder{
        ProgressBar progressBar;

        public LoadingViewHolder(@NonNull View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.progressbar);
        }
    }
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tensp,giasp,mota;
        ImageView hinhanh;
        private ItemClikListener itemClikListener;
        public MyViewHolder(@NonNull View itemView) {

            super(itemView);
            tensp=itemView.findViewById(R.id.itemrau_ten);
            giasp=itemView.findViewById(R.id.itemrau_gia);
            mota=itemView.findViewById(R.id.itemrau_mota);
            hinhanh=itemView.findViewById(R.id.itemrau_image);
            itemView.setOnClickListener(this);
        }

        public void setItemClikListener(ItemClikListener itemClikListener) {
            this.itemClikListener = itemClikListener;
        }

        @Override
        public void onClick(View view) {
//            itemClikListener.onClik(view,getLayoutPosition(),false);
            itemClikListener.onClik(view,getAdapterPosition(),false);

        }
    }
}
