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

import com.bumptech.glide.Glide;
import com.nerolac.Modal.Distributor;
import com.nerolac.Modal.Retailers;
import com.nerolac.R;

import java.util.ArrayList;
import java.util.List;

import androidx.core.app.ActivityCompat;

public class DistributorListAdapter extends BaseAdapter implements Filterable {

    private ArrayList<Distributor> mListItems = new ArrayList<>();
    public static ArrayList<Distributor> mListItemsFilter = new ArrayList<>();
    Activity context;
    int ads = 0;
    private ItemFilter mFilter = new ItemFilter();
    public DistributorListAdapter(Activity context, ArrayList<Distributor> mListItems) {
        this.mListItems = mListItems;
        this.mListItemsFilter = mListItems;
        this.context = context;
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
            convertView = mInflater.inflate(R.layout.activity_distributor_list_item, null);
            holder = new ViewHolder();
            holder.mLayoutItem = convertView.findViewById(R.id.mLayoutItem);
            holder.mTextTitle = convertView.findViewById(R.id.mTextTitle);
            holder.mTextAddresss = convertView.findViewById(R.id.mTextAddresss);
            holder.mTextOwnerName = convertView.findViewById(R.id.mTextOwnerName);
            holder.mImgPhoto = convertView.findViewById(R.id.mImgPhoto);
            holder.mImgCall = convertView.findViewById(R.id.mImgCall);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (position % 2 == 0) {
            holder.mLayoutItem.setBackgroundColor(Color.parseColor("#ffffff"));
        } else {
            holder.mLayoutItem.setBackgroundColor(Color.parseColor("#f2f2f2"));
        }
        final Distributor distributor = mListItemsFilter.get(position);
        holder.mTextTitle.setText(distributor.getmStrDisName());
        holder.mTextAddresss.setText(distributor.getmStrDisAddress() + ", " + distributor.getmStrDisVilage()+ ", " + distributor.getmStrDisBlock()+ ", " + distributor.getmStrDisTehsil());
        holder.mTextOwnerName.setText(distributor.getmStrDisOwnerName());
        Glide.with(context.getApplicationContext()).load(distributor.getmStrDisPhoto()).placeholder(R.drawable.ic_nero_add_img).error(R.drawable.ic_nero_add_img).into(holder.mImgPhoto);
        holder.mImgCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + distributor.getmStrDisMobile()));
                callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                return;
                }
                context.startActivity(callIntent);
                }
            });


        return convertView;
    }

    @Override
    public Filter getFilter() {
        if(mFilter==null) {

            mFilter=new ItemFilter();

        }

        return mFilter;
    }

    private class ViewHolder {
    RelativeLayout mLayoutItem;
    TextView mTextTitle;
    TextView mTextOwnerName;
    TextView mTextAddresss;
    ImageView mImgPhoto;
    ImageView mImgCall;
    }

    private class ItemFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            String filterString = constraint.toString().toLowerCase();
            FilterResults results = new FilterResults();
            final List<Distributor> list = mListItems;
            int count = list.size();
            final ArrayList<Distributor> nlist = new ArrayList<Distributor>(count);
            String filterableStringName;
            for (int i = 0; i < count; i++) {
                filterableStringName = list.get(i).getmStrDisTehsil()+"#"+list.get(i).getmStrDisBlock();
                if (filterableStringName.toLowerCase().contains(filterString.toLowerCase())) {
                    nlist.add(list.get(i));
                }else {
                    if(i==count-1 && filterString.contains("all tehsil")  || filterString.contains("all blocks")){
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
            mListItemsFilter = (ArrayList<Distributor>) results.values;
            notifyDataSetChanged();
        }

    }


}