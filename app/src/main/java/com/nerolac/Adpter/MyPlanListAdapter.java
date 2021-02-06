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
import com.nerolac.Modal.MyPlan;
import com.nerolac.Modal.RawLocation;
import com.nerolac.R;

import java.util.ArrayList;
import java.util.List;

import androidx.core.app.ActivityCompat;

public class MyPlanListAdapter extends BaseAdapter implements Filterable {

    private ArrayList<RawLocation> mListItems = new ArrayList<>();
    public static ArrayList<RawLocation> mListItemsFilter = new ArrayList<>();
    Activity context;
    int ads = 0;
    private ItemFilter mFilter = new ItemFilter();
    public MyPlanListAdapter(Activity context, ArrayList<RawLocation> mListItems) {
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
            convertView = mInflater.inflate(R.layout.activity_my_plan_list_item, null);
            holder = new ViewHolder();
            holder.mLayoutItem = convertView.findViewById(R.id.mLayoutItem);
            holder.mTextTehsil = convertView.findViewById(R.id.mTextTehsil);
            holder.mTextBlock = convertView.findViewById(R.id.mTextBlock);
            holder.mTextPTag = convertView.findViewById(R.id.mTextPTag);
            holder.mTextVillage = convertView.findViewById(R.id.mTextVillage);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (position % 2 == 0) {
            holder.mLayoutItem.setBackgroundColor(Color.parseColor("#ffffff"));
        } else {
            holder.mLayoutItem.setBackgroundColor(Color.parseColor("#f2f2f2"));
        }
        RawLocation myPlan = mListItemsFilter.get(position);
        holder.mTextTehsil.setText(myPlan.getmStrTehsil()+", "+myPlan.getmStrDistrict());
        holder.mTextBlock.setText(myPlan.getmStrBlock());
        holder.mTextVillage.setText(myPlan.getmStrVillage());
        if(myPlan.getmStrPlan().equals("0")){
            //holder.mTextVillage.setTextColor(Color.parseColor("#00205b"));
            holder.mTextPTag.setVisibility(View.GONE);
        }else {
            //holder.mTextVillage.setTextColor(Color.parseColor("#e91a4e"));
            holder.mTextPTag.setVisibility(View.VISIBLE);
        }
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
    TextView mTextTehsil;
    TextView mTextBlock;
    TextView mTextPTag;
    TextView mTextVillage;
    }

    private class ItemFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            String filterString = constraint.toString().toLowerCase();
            FilterResults results = new FilterResults();
            final List<RawLocation> list = mListItems;
            int count = list.size();
            final ArrayList<RawLocation> nlist = new ArrayList<RawLocation>(count);
            String filterableStringName;
            //String filterableStringNumber;
            for (int i = 0; i < count; i++) {
                filterableStringName = list.get(i).getmStrTehsil()+"#"+list.get(i).getmStrBlock();
                //filterableStringNumber = list.get(i).getmStrBlock();
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
            mListItemsFilter = (ArrayList<RawLocation>) results.values;
            notifyDataSetChanged();
        }

    }


}