package com.nerolac.Adpter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.nerolac.ACTOrderSummry;
import com.nerolac.DataBase.Database;
import com.nerolac.Modal.AddcartModal;
import com.nerolac.R;

import java.util.ArrayList;
import java.util.List;

public class ViewAddToCartAdapter extends
        RecyclerView.Adapter<ViewAddToCartAdapter.ViewHolder>  {

    public static List<AddcartModal> ShopAreaSqFt_list;
    private Context context;
    private AddToCartAdapter.AddToCart_listener ShopAreaSqFt_listener;

    public ViewAddToCartAdapter(Context mContext, ArrayList<AddcartModal> gt_painting_area_sq_ft) {
        //ShopAreaSqFt_list = gt_painting_area_sq_ft;
        context = mContext;
        this.ShopAreaSqFt_list = gt_painting_area_sq_ft;

    }

    @Override
    public ViewAddToCartAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.viewaddcard_item, parent, false);

        ViewAddToCartAdapter.ViewHolder viewHolder =
                new ViewAddToCartAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewAddToCartAdapter.ViewHolder holder,
                                 int position) {
        AddcartModal retailers = ShopAreaSqFt_list.get(position);

        holder.mTextTitle.setText(retailers.getdescription());
        holder.msku.setText("SKU: "+retailers.getsku());
        holder.mpacksize.setText("Pack Size: "+retailers.getpack());
        holder.qunTXT.setText("Qty: "+String.valueOf(retailers.getquntity()));

        holder.priceExt.setText("Rate: Rs. "+retailers.getprice());


        holder.totalamount.setText("Item Total: Rs. "+retailers.getamount());



        //since only one radio button is allowed to be selected,
        // this condition un-checks previous selections

    }


    @Override
    public int getItemCount() {
        return ShopAreaSqFt_list.size();
    }


    private   String totalprice(int qu,int price){
        int caltotal = 0;
        try {
            caltotal = qu * price;
        } catch(NumberFormatException nfe) {
            System.out.println("Could not parse " + nfe);
        }

        return ("RS. "+ caltotal);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        RelativeLayout mLayoutItem;
        ImageView mImgSelect;
        TextView mTextTitle;
        TextView msku;
        TextView mpacksize;
        TextView priceExt;

        TextView qunTXT;
        TextView totalamount;
        TextView removeItem;
        public ViewHolder(View view) {
            super(view);


            mTextTitle = view.findViewById(R.id.mTextTitle);
            msku = view.findViewById(R.id.mTextProductId);
            mpacksize = view.findViewById(R.id.packsize);

            priceExt =view.findViewById(R.id.pricevalue);
            qunTXT =view.findViewById(R.id.mTextQuantity);
            totalamount =view.findViewById(R.id.prize);
            removeItem = view.findViewById(R.id.remove);



        }
    }
}


