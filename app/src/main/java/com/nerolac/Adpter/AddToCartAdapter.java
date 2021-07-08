package com.nerolac.Adpter;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.nerolac.ACTOrderSummry;
import com.nerolac.DataBase.Database;
import com.nerolac.Modal.AddcartModal;
import com.nerolac.Modal.productsModal;
import com.nerolac.R;

import java.util.ArrayList;
import java.util.List;
public class AddToCartAdapter extends
        RecyclerView.Adapter<AddToCartAdapter.ViewHolder> {


    public static List<AddcartModal> ShopAreaSqFt_list;
    private Context context;
    private AddToCart_listener ShopAreaSqFt_listener;
    ACTOrderSummry farmerMeetingImageFragment;
    Database database;
    String retailerid;
    public AddToCartAdapter(Context mContext, ArrayList<AddcartModal> gt_painting_area_sq_ft,String rid) {
        ShopAreaSqFt_list = gt_painting_area_sq_ft;
        context = mContext;
        this.ShopAreaSqFt_list = gt_painting_area_sq_ft;
        database = new Database(context);
        this.retailerid = rid;
        farmerMeetingImageFragment = ACTOrderSummry.instance;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.addtocart_item, parent, false);

        ViewHolder viewHolder =
                new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder,
                                 int position) {
        AddcartModal retailers = ShopAreaSqFt_list.get(position);

        holder.mTextTitle.setText(retailers.getdescription());
        holder.msku.setText("SKU: "+retailers.getsku());
        holder.mpacksize.setText("Pack Size: "+retailers.getpack());
        holder.qunTXT.setText(String.valueOf(retailers.getquntity()));
        if(!retailers.getprice().equals("0")){
            holder.priceExt.setText(retailers.getprice());

        }


        holder.totalamount.setText(totalprice(retailers.getquntity() , Integer.parseInt(retailers.getprice())));
        holder.priceExt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {


                if(!holder.priceExt.getText().toString().equals("")){
                    ShopAreaSqFt_list.get(position).setprice(charSequence.toString());
                    holder.totalamount.setText(totalprice(retailers.getquntity(),Integer.parseInt(charSequence.toString())));
                    farmerMeetingImageFragment.changeinTotal();

                }
                else {
                    holder.totalamount.setText(totalprice(retailers.getquntity(),0));
                    ShopAreaSqFt_list.get(position).setprice("0");
                    farmerMeetingImageFragment.changeinTotal();
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        holder.minQuntity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println(retailers.getquntity());
                if(retailers.getquntity()>1){
                    int qu = retailers.getquntity();
                    qu = qu -1;
                    ShopAreaSqFt_list.get(position).setquntity(qu);
                    holder.qunTXT.setText(String.valueOf(qu));
                    holder.totalamount.setText(totalprice(qu,Integer.parseInt(retailers.getprice())));
                    farmerMeetingImageFragment.changeinTotal();
                }
                else{

                }

            }
        });
        holder.maxQuntity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println(retailers.getquntity());
                if(retailers.getquntity()>=1){
                    int qu = retailers.getquntity();
                    qu = qu + 1;
                    ShopAreaSqFt_list.get(position).setquntity(qu);
                    holder.qunTXT.setText(String.valueOf(qu));
                    holder.totalamount.setText(totalprice(qu,Integer.parseInt(retailers.getprice())));
                    farmerMeetingImageFragment.changeinTotal();
                }
            }
        });
        holder.removeItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                database.deleteorderItem(retailerid,retailers.getproduct_id());
                farmerMeetingImageFragment.remomevfromcart();
            }
        });

        //since only one radio button is allowed to be selected,
        // this condition un-checks previous selections

    }

    public void set_ShopAreaSqFt_Selection(AddToCart_listener listener) {
        try {
            ShopAreaSqFt_listener = listener;
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
    }
    public interface AddToCart_listener {
       // void ShopAreaSqFt_Selection(List<MetadetaModel> medicineListDatumList, int position, ImageView imgwork);
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
        EditText priceExt;
        ImageView minQuntity,maxQuntity;
        TextView qunTXT;
        TextView totalamount;
        TextView removeItem;
        public ViewHolder(View view) {
            super(view);


            mTextTitle = view.findViewById(R.id.mTextTitle);
            msku = view.findViewById(R.id.mTextProductId);
            mpacksize = view.findViewById(R.id.packsize);
            minQuntity = view.findViewById(R.id.mImgMinus);
            maxQuntity = view.findViewById(R.id.mImgPlus);
priceExt =view.findViewById(R.id.pricevalue);
            qunTXT =view.findViewById(R.id.mTextQuantity);
            totalamount =view.findViewById(R.id.prize);
            removeItem = view.findViewById(R.id.remove);



        }
    }
}

//public class AddToCartAdapter extends BaseAdapter {
//    public ArrayList<AddcartModal> mListItems = new ArrayList<>();
//    public static ArrayList<AddcartModal> mListItemsFilter = new ArrayList<>();
//    ACTOrderSummry farmerMeetingImageFragment;
//    Activity context;
//    int ads = 0;
//    Database database;
//    AddToCartAdapter.ItemFilter mFilter;
//    String retailerid;
//    public AddToCartAdapter(Activity context, ArrayList<AddcartModal> mListItems,String rid) {
//        this.mListItems = mListItems;
//        this.mListItemsFilter = mListItems;
//        this.context = context;
//        mFilter = new AddToCartAdapter.ItemFilter();
//        farmerMeetingImageFragment = ACTOrderSummry.instance;
//        database = new Database(context);
//        this.retailerid = rid;
//
//    }
//
//    @Override
//    public int getCount() {
//        return mListItemsFilter.size();
//    }
//
//    @Override
//    public Object getItem(int arg0) {
//        return mListItemsFilter.get(arg0);
//    }
//
//    @Override
//    public long getItemId(int arg0) {
//        return 0;
//    }
//
//    public int getViewTypeCount() {
//        return 1;
//    }
//
//    @Override
//    public int getItemViewType(int position) {
//        return position;
//    }
//
//
//
//    @Override
//    public View getView(final int position, View convertView, ViewGroup parent) {
//        final AddToCartAdapter.ViewHolder holder;
//        if (convertView == null) {
//            LayoutInflater mInflater = LayoutInflater.from(context);
//            convertView = mInflater.inflate(R.layout.addtocart_item, null);
//            holder = new AddToCartAdapter.ViewHolder();
//           // holder.mLayoutItem = convertView.findViewById(R.id.mLayoutItem);
//            //holder.mImgSelect = convertView.findViewById(R.id.mImgSelect);
//
//            holder.mTextTitle = convertView.findViewById(R.id.mTextTitle);
//            holder.msku = convertView.findViewById(R.id.mTextProductId);
//            holder.mpacksize = convertView.findViewById(R.id.packsize);
//            holder.minQuntity = convertView.findViewById(R.id.mImgMinus);
//            holder.maxQuntity = convertView.findViewById(R.id.mImgPlus);
//holder.priceExt =convertView.findViewById(R.id.pricevalue);
//            holder.qunTXT =convertView.findViewById(R.id.mTextQuantity);
//            holder.totalamount =convertView.findViewById(R.id.prize);
//            holder.removeItem = convertView.findViewById(R.id.remove);
//            convertView.setTag(holder);
//        } else {
//            holder = (AddToCartAdapter.ViewHolder) convertView.getTag();
//        }
//
//
//
//        final AddcartModal retailers = mListItemsFilter.get(position);
//        holder.mTextTitle.setText(retailers.getdescription());
//        holder.msku.setText("SKU: "+retailers.getsku());
//        holder.mpacksize.setText("Pack Size: "+retailers.getpack());
//        holder.qunTXT.setText(String.valueOf(retailers.getquntity()));
//        holder.priceExt.setText(retailers.getprice());
//
//
//        holder.totalamount.setText(totalprice(retailers.getquntity() , Integer.parseInt(retailers.getprice())));
//        holder.priceExt.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//
//                if(charSequence != ""){
//                    mListItemsFilter.get(position).setprice(charSequence.toString());
//                    holder.totalamount.setText(totalprice(retailers.getquntity(),Integer.parseInt(charSequence.toString())));
//                    farmerMeetingImageFragment.changeinTotal();
//
//                }
//                else {
//                    holder.totalamount.setText(totalprice(retailers.getquntity(),0));
//                    mListItemsFilter.get(position).setprice("0");
//                    farmerMeetingImageFragment.changeinTotal();
//                }
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//
//            }
//        });
//        holder.minQuntity.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                System.out.println(retailers.getquntity());
//                if(retailers.getquntity()>1){
//                    int qu = retailers.getquntity();
//                    qu = qu -1;
//                    mListItemsFilter.get(position).setquntity(qu);
//                    holder.qunTXT.setText(String.valueOf(qu));
//                    holder.totalamount.setText(totalprice(qu,Integer.parseInt(retailers.getprice())));
//                    farmerMeetingImageFragment.changeinTotal();
//                }
//                else{
//
//                }
//
//            }
//        });
//        holder.maxQuntity.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                System.out.println(retailers.getquntity());
//                if(retailers.getquntity()>=1){
//                    int qu = retailers.getquntity();
//                    qu = qu + 1;
//                    mListItemsFilter.get(position).setquntity(qu);
//                    holder.qunTXT.setText(String.valueOf(qu));
//                    holder.totalamount.setText(totalprice(qu,Integer.parseInt(retailers.getprice())));
//                    farmerMeetingImageFragment.changeinTotal();
//                }
//            }
//        });
//        holder.removeItem.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                database.deleteorderItem(retailerid,retailers.getproduct_id());
//                farmerMeetingImageFragment.remomevfromcart();
//            }
//        });
//           /* if(position % 2 == 0){
//            holder.mLayoutItem.setBackgroundColor(Color.parseColor("#ffffff"));
//            }else {
//            holder.mLayoutItem.setBackgroundColor(Color.parseColor("#f2f2f2"));
//            }
//
//            final Retailers retailers = mListItemsFilter.get(position);
//            holder.mTextTitle.setText(retailers.getTbShopName());
//            holder.mTextOwnerName.setText(retailers.getTbFirstName());
//            holder.mTextAddresss.setText(retailers.getTbAddress1()+", "+retailers.getTbVillage()+", "+retailers.getTbBlock()+", "+retailers.getTbTehsil());
//            Glide.with(context.getApplicationContext()).load(retailers.getTbImgOne()).placeholder(R.drawable.ic_nero_add_img).error(R.drawable.ic_nero_add_img).into(holder.mImgPhoto);
//            holder.mImgCall.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent callIntent = new Intent(Intent.ACTION_CALL);
//                callIntent.setData(Uri.parse("tel:" + retailers.getTbMobile()));
//                callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
//                    return;
//                }
//                context.startActivity(callIntent);
//            }
//        });
//
//        holder.mImgCall.setVisibility(View.GONE);*/
//
//        return convertView;
//    }
//private   String totalprice(int qu,int price){
//      int caltotal = 0;
//    try {
//        caltotal = qu * price;
//    } catch(NumberFormatException nfe) {
//        System.out.println("Could not parse " + nfe);
//    }
//
//      return ("RS. "+ caltotal);
//}
//
//
//
//    private class ViewHolder {
//        RelativeLayout mLayoutItem;
//        ImageView mImgSelect;
//        TextView mTextTitle;
//        TextView msku;
//        TextView mpacksize;
//        EditText priceExt;
//        ImageView minQuntity,maxQuntity;
//        TextView qunTXT;
//        TextView totalamount;
//        TextView removeItem;
//    }
//
//    private class ItemFilter extends Filter {
//        @Override
//        protected FilterResults performFiltering(CharSequence constraint) {
//            String filterString = constraint.toString().toLowerCase();
//            FilterResults results = new FilterResults();
//            final List<AddcartModal> list = mListItems;
//            int count = list.size();
//            final ArrayList<AddcartModal> nlist = new ArrayList<AddcartModal>(count);
//            String filterableStringName;
//            for (int i = 0; i < count; i++) {
//                filterableStringName = list.get(i)+"#"+list.get(i);
//                System.out.println("CCCCCCC "+filterableStringName);
//                System.out.println("CCCCCCC2 "+filterString.toLowerCase());
//                if (filterableStringName.toLowerCase().contains(filterString.toLowerCase())) {
//                    nlist.add(list.get(i));
//                }else {
//                    if(i==count-1 && filterString.contains("all tehsil")  || filterString.contains("all blocks")){
//                        nlist.addAll(mListItems);
//                    }
//
//                }
//            }
//            results.values = nlist;
//            results.count = nlist.size();
//            return results;
//        }
//
//        @SuppressWarnings("unchecked")
//        @Override
//        protected void publishResults(CharSequence constraint, FilterResults results) {
//            mListItemsFilter = (ArrayList<AddcartModal>) results.values;
//            notifyDataSetChanged();
//        }
//    }

//}
