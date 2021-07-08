/**
 * Created by admin1 on 27/2/18.
 */
package com.nerolac.Adpter;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;

import com.bumptech.glide.Glide;
import com.nerolac.ACTFormUpdate;
import com.nerolac.ACTOrderSummry;
import com.nerolac.ACTProductList;
import com.nerolac.DataBase.Database;
import com.nerolac.Modal.Retailers;
import com.nerolac.Modal.Retailers;
import com.nerolac.R;

import java.util.ArrayList;
import java.util.List;

public class SearchDealerListAdapter extends BaseAdapter implements Filterable {

    public static ArrayList<Retailers> mListItemsFilter = new ArrayList<>();
    private ArrayList<Retailers> arSearch = new ArrayList<>();
    Database database;
    Activity context;
    int ads = 0;
    ItemFilter mFilter;
    private ArrayList<Retailers> mListItems = new ArrayList<>();

    public SearchDealerListAdapter(Activity context, ArrayList<Retailers> mListItems) {
        this.mListItems = mListItems;
        this.mListItemsFilter = mListItems;
        this.arSearch = new ArrayList<>();
        this.arSearch.addAll(mListItems);
        this.context = context;
        mFilter = new ItemFilter();
        database = new Database(context);

    }

    @Override
    public int getCount() {
        return mListItemsFilter.size();
    }

    @Override
    public Object getItem(int arg0) {
        return mListItemsFilter.get(arg0);
    }

    @Override
    public long getItemId(int arg0) {
        return 0;
    }

    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            LayoutInflater mInflater = LayoutInflater.from(context);
            convertView = mInflater.inflate(R.layout.activity_dealer_list_item, null);
            holder = new ViewHolder();
            holder.mLayoutItem = convertView.findViewById(R.id.mLayoutItem);
            holder.mLayoutUpdateInfo = convertView.findViewById(R.id.mLayoutUpdateInfo);
            holder.mLayoutOrder = convertView.findViewById(R.id.mLayoutOrder);
            holder.mTextTitle = convertView.findViewById(R.id.mTextTitle);
            holder.mTextAddresss = convertView.findViewById(R.id.mTextAddresss);
            holder.mTextOwnerName = convertView.findViewById(R.id.mTextOwnerName);
            holder.mImgCall = convertView.findViewById(R.id.mImgCall);
            holder.mImgPhoto = convertView.findViewById(R.id.mImgPhoto);
            holder.lastorder = convertView.findViewById(R.id.mlast_order);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final Retailers retailers = mListItemsFilter.get(position);
        holder.mLayoutUpdateInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ACTFormUpdate.class);
                intent.putExtra("retailerId", retailers.getTbId());
                intent.putExtra("retailerName", retailers.getTbShopName());
                intent.putExtra("retailerowner", retailers.getTbFirstName());
                intent.putExtra("block", retailers.getTbBlock());
                intent.putExtra("tehsil", retailers.getTbTehsil());
                intent.putExtra("village", retailers.getTbVillage());
                intent.putExtra("gst_available", retailers.getgst_available());
                intent.putExtra("getfld_gst_number", retailers.getfld_gst_number());
                intent.putExtra("mobile", retailers.getTbMobile());
                intent.putExtra("address1", retailers.getTbAddress1());
                intent.putExtra("address2", retailers.getTbAddress2());
                context.startActivity(intent);

            }
        });

        holder.mLayoutOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (database.GT_Addtocart(retailers.getTbId()).size() > 0) {
                    Intent intent = new Intent(context, ACTOrderSummry.class);
                    intent.putExtra("retailerId", retailers.getTbId());
                    intent.putExtra("retailerName", retailers.getTbShopName());
                    intent.putExtra("retailerowner", retailers.getTbFirstName());
                    intent.putExtra("retailerImage", retailers.getTbImgOne());

                    context.startActivity(intent);
                    context.finish();
                } else {


                    Intent intent = new Intent(context, ACTProductList.class);
                    intent.putExtra("retailerId", retailers.getTbId());
                    intent.putExtra("retailerName", retailers.getTbShopName());
                    intent.putExtra("retailerowner", retailers.getTbFirstName());
                    intent.putExtra("retailerImage", retailers.getTbImgOne());
                    intent.putExtra("productids", "0");
                    context.startActivity(intent);
                    context.finish();
                }

            }
        });

        if (position % 2 == 0) {
            holder.mLayoutItem.setBackgroundColor(Color.parseColor("#ffffff"));
        } else {
            holder.mLayoutItem.setBackgroundColor(Color.parseColor("#f2f2f2"));
        }

        holder.lastorder.setText(retailers.getlast_order());
        holder.mTextTitle.setText(retailers.getTbShopName());
        holder.mTextOwnerName.setText(retailers.getTbFirstName());
        holder.mTextAddresss.setText(retailers.getTbAddress1() + ", " + retailers.getTbVillage() + ", " + retailers.getTbBlock() + ", " + retailers.getTbTehsil());
        Glide.with(context.getApplicationContext()).load(retailers.getTbImgOne()).placeholder(R.drawable.ic_nero_add_img).error(R.drawable.ic_nero_add_img).into(holder.mImgPhoto);
        holder.mImgCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + retailers.getTbMobile()));
                callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                context.startActivity(callIntent);
            }
        });

        holder.mImgCall.setVisibility(View.GONE);

        return convertView;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    mListItemsFilter = arSearch;
                } else {
                    ArrayList<Retailers> filteredList = new ArrayList<>();
                    for (Retailers row : arSearch) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getTbShopName().toLowerCase().contains(charString.toLowerCase()) ) {
                            filteredList.add(row);
                        }
                    }

                    mListItemsFilter = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = mListItemsFilter;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mListItemsFilter = (ArrayList<Retailers>) filterResults.values;


                notifyDataSetChanged();
            }
        };
    }


    private class ViewHolder {
        RelativeLayout mLayoutItem;
        RelativeLayout mLayoutUpdateInfo;
        RelativeLayout mLayoutOrder;
        TextView mTextAddresss;
        TextView mTextOwnerName;
        TextView mTextTitle, lastorder;
        ImageView mImgCall;
        ImageView mImgPhoto;
    }

    private class ItemFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            String filterString = constraint.toString().toLowerCase();
            FilterResults results = new FilterResults();
            final List<Retailers> list = mListItems;
            int count = list.size();
            final ArrayList<Retailers> nlist = new ArrayList<Retailers>(count);
            String filterableStringName;
            for (int i = 0; i < count; i++) {
                filterableStringName = list.get(i) + "#" + list.get(i);
                System.out.println("CCCCCCC " + filterableStringName);
                System.out.println("CCCCCCC2 " + filterString.toLowerCase());
                if (filterableStringName.toLowerCase().contains(filterString.toLowerCase())) {
                    nlist.add(list.get(i));
                } else {
                    if (i == count - 1 && filterString.contains("all tehsil") || filterString.contains("all blocks")) {
                        nlist.addAll(mListItems);
                    }

                }
            }
            results.values = nlist;
            results.count = nlist.size();
            return results;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mListItemsFilter = (ArrayList<Retailers>) results.values;
            notifyDataSetChanged();
        }
    }
}