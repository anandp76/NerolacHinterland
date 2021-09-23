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
import com.nerolac.Modal.PainterModal;
import com.nerolac.Modal.VisitelogModal;
import com.nerolac.R;

import java.util.ArrayList;

import static com.nerolac.Utils.CommonData.getTime;

public class Painteradapter extends BaseAdapter {
    private ArrayList<PainterModal> mListItems = new ArrayList<>();
    Activity context;
    int ads = 0;

    public Painteradapter(Activity context, ArrayList<PainterModal> mListItems) {
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
        final Painteradapter.ViewHolder holder;
        if (convertView == null) {
            LayoutInflater mInflater = LayoutInflater.from(context);
            convertView = mInflater.inflate(R.layout.painter_item_layout, null);
            holder = new Painteradapter.ViewHolder();
            holder.mTextTitle = convertView.findViewById(R.id.mTextTitle);
            holder.mTextDate = convertView.findViewById(R.id.mTextDate);

           // holder.mvisitorselfy = convertView.findViewById(R.id.mImgPhoto);
            convertView.setTag(holder);
        } else {
            holder = (Painteradapter.ViewHolder) convertView.getTag();
        }
        PainterModal attendence = mListItems.get(position);
        holder.mTextTitle.setText(attendence.getmStrfld_painter_name());
        holder.mTextDate.setText("Experience : "+attendence.getmStrfld_experience());
        //Glide.with(context.getApplicationContext()).load(attendence.getfld_file_path()).placeholder(R.drawable.ic_nero_add_img).error(R.drawable.ic_nero_add_img).into(holder.mvisitorselfy);


        return convertView;
    }


    private class ViewHolder {
        RelativeLayout mLayoutItem;
        TextView mTextDate;
        TextView mTextTitle;
        ImageView mvisitorselfy;

    }


}

