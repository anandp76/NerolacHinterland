package com.nerolac.Adpter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.nerolac.Modal.Attendence;
import com.nerolac.Modal.VisitelogModal;
import com.nerolac.R;

import java.util.ArrayList;

import static com.nerolac.Utils.CommonData.getDay;
import static com.nerolac.Utils.CommonData.getMonth;
import static com.nerolac.Utils.CommonData.getTime;

public class Visiteradapter extends BaseAdapter {
    private ArrayList<VisitelogModal> mListItems = new ArrayList<>();
    Activity context;
    int ads = 0;

    public Visiteradapter(Activity context, ArrayList<VisitelogModal> mListItems) {
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
        final Visiteradapter.ViewHolder holder;
        if (convertView == null) {
            LayoutInflater mInflater = LayoutInflater.from(context);
            convertView = mInflater.inflate(R.layout.visitor_log_layout, null);
            holder = new Visiteradapter.ViewHolder();
            holder.mTextTitle = convertView.findViewById(R.id.mTextTitle);
            holder.mTextDate = convertView.findViewById(R.id.mTextDate);

            holder.mvisitorselfy = convertView.findViewById(R.id.mImgPhoto);
            convertView.setTag(holder);
        } else {
            holder = (Visiteradapter.ViewHolder) convertView.getTag();
        }
        VisitelogModal attendence = mListItems.get(position);
        holder.mTextTitle.setText(attendence.getfld_ufname() + " " +attendence.getfld_ulname());
        holder.mTextDate.setText("Visit on: " + getTime(attendence.getfld_visit_date()));
        Glide.with(context.getApplicationContext()).load(attendence.getfld_file_path()).placeholder(R.drawable.ic_nero_add_img).error(R.drawable.ic_nero_add_img).into(holder.mvisitorselfy);


        return convertView;
    }


    private class ViewHolder {
        RelativeLayout mLayoutItem;
        TextView mTextDate;
        TextView mTextTitle;
        ImageView mvisitorselfy;

    }


}
