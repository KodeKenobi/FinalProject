package com.synerdy.kodekenobi.ViewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.synerdy.kodekenobi.Interface.ItemClickListener;
import com.synerdy.kodekenobi.R;

public class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView productName,productPrice,txtProductQuantity;
    private ItemClickListener itemClickListener;
   public CartViewHolder(View itemView)
   {
       super(itemView);
        productName=itemView.findViewById(R.id.cart_product_name);
        productPrice=itemView.findViewById(R.id.cart_product_price);
        txtProductQuantity=itemView.findViewById(R.id.cart_product_quantity);

   }

    @Override
    public void onClick(View v) {
       itemClickListener.onClick(v,getAdapterPosition(),false);

    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }
}
