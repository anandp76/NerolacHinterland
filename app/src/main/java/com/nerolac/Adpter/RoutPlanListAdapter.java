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
import android.widget.RelativeLayout;

import com.nerolac.R;

import java.util.ArrayList;

public class RoutPlanListAdapter extends BaseAdapter {

    private ArrayList<String> mListItems = new ArrayList<>();
    Activity context;
    int ads = 0;

    public RoutPlanListAdapter(Activity context, ArrayList<String> mListItems) {
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
            convertView = mInflater.inflate(R.layout.activity_rout_plan_list_item, null);
            holder = new ViewHolder();
            holder.mLayoutItem = convertView.findViewById(R.id.mLayoutItem);
            convertView.setTag(holder);
            } else {
            holder = (ViewHolder) convertView.getTag();
            }



        return convertView;
    }


    private class ViewHolder {
    RelativeLayout mLayoutItem;
    }


}