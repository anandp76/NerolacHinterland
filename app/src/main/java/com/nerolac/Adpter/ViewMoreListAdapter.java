/**
 * Created by admin1 on 27/2/18.
 */
package com.nerolac.Adpter;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nerolac.R;

import java.util.ArrayList;

public class ViewMoreListAdapter extends BaseAdapter {

    private ArrayList<String> mListItems = new ArrayList<>();
    Activity context;
    int ads = 0;

    public ViewMoreListAdapter(Activity context, ArrayList<String> mListItems) {
        this.mListItems = mListItems;
        this.context = context;
    }

    @Override
    public int getCount() {
        return mListItems.size();
    }

    @Override
    public Object getItem(int arg0) {
        return null;
    }

    @Override
    public long getItemId(int arg0) {
        return 0;
    }


    @Override
    public int getViewTypeCount() {
        if (getCount() > 0) {
            return getCount();
        } else {
            return super.getViewTypeCount();
        }
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
            convertView = mInflater.inflate(R.layout.activity_view_more_list_item, null);
            holder = new ViewHolder();
            holder.mLayoutItem = convertView.findViewById(R.id.mLayoutItem);
            holder.mTextTitle = convertView.findViewById(R.id.mTextTitle);
            convertView.setTag(holder);
            } else {
            holder = (ViewHolder) convertView.getTag();
            }
            String mStrTitle = mListItems.get(position);
            if(position % 2 == 0){
            holder.mLayoutItem.setBackgroundColor(Color.parseColor("#ffffff"));
            }else {
            holder.mLayoutItem.setBackgroundColor(Color.parseColor("#f2f2f2"));
            }
            holder.mTextTitle.setText(mStrTitle);


        return convertView;
    }


    private class ViewHolder {
        LinearLayout mLayoutItem;
        TextView mTextTitle;
    }


}