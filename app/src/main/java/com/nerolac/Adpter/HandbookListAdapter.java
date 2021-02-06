/**
 * Created by admin1 on 27/2/18.
 */
package com.nerolac.Adpter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nerolac.Modal.Handbook;
import com.nerolac.Modal.Inbox;
import com.nerolac.R;
import com.nerolac.ViewPdfFile;

import java.util.ArrayList;

import static com.nerolac.Utils.CommonData.ChangeFormate;

public class HandbookListAdapter extends BaseAdapter {

    private ArrayList<Handbook> mListItems = new ArrayList<>();
    Activity context;
    int ads = 0;

    public HandbookListAdapter(Activity context, ArrayList<Handbook> mListItems) {
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
            convertView = mInflater.inflate(R.layout.handbook_item, null);
            holder = new ViewHolder();
            holder.mLayoutItem = convertView.findViewById(R.id.mLayoutItem);
            holder.mTextTitle = convertView.findViewById(R.id.mTextTitle);
            holder.mImgViewPdf = convertView.findViewById(R.id.mImgViewPdf);
            convertView.setTag(holder);
            } else {
            holder = (ViewHolder) convertView.getTag();
            }
            if(position % 2 == 0){
            holder.mLayoutItem.setBackgroundColor(Color.parseColor("#ffffff"));
            }else {
            holder.mLayoutItem.setBackgroundColor(Color.parseColor("#f2f2f2"));
            }

            final Handbook inbox = mListItems.get(position);
            holder.mTextTitle.setText(inbox.getmStrTitle());
            holder.mImgViewPdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ViewPdfFile.class);
                intent.putExtra("mStrPdf",inbox.getmStrFileUrl());
                context.startActivity(intent);
            }
        });

        return convertView;
    }


    private class ViewHolder {
    RelativeLayout mLayoutItem;
    TextView mTextTitle;
    ImageView mImgViewPdf;
    }


}