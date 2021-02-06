/**
 * Created by admin1 on 27/2/18.
 */
package com.nerolac.Adpter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nerolac.Modal.Attendence;
import com.nerolac.Modal.RoutPlan;
import com.nerolac.R;

import java.util.ArrayList;

import static com.nerolac.Utils.CommonData.getDay;
import static com.nerolac.Utils.CommonData.getMonth;
import static com.nerolac.Utils.CommonData.getTime;

public class AttendenceListAdapter extends BaseAdapter {

    private ArrayList<Attendence> mListItems = new ArrayList<>();
    Activity context;
    int ads = 0;

    public AttendenceListAdapter(Activity context, ArrayList<Attendence> mListItems) {
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
            convertView = mInflater.inflate(R.layout.activity_attendence_list_item, null);
            holder = new ViewHolder();
            holder.mLayoutItem = convertView.findViewById(R.id.mLayoutItem);
            holder.mTextOutTime = convertView.findViewById(R.id.mTextOutTime);
            holder.mTextInTime = convertView.findViewById(R.id.mTextInTime);
            holder.mTextComment = convertView.findViewById(R.id.mTextComment);
            holder.mTextHours = convertView.findViewById(R.id.mTextHours);
            holder.mTxtMonth = convertView.findViewById(R.id.mTxtMonth);
            holder.mTxtDay = convertView.findViewById(R.id.mTxtDay);
            convertView.setTag(holder);
            } else {
            holder = (ViewHolder) convertView.getTag();
            }
            Attendence attendence = mListItems.get(position);
            holder.mTextOutTime.setText(getTime(attendence.getmStrTimeOut()));
            holder.mTextInTime.setText(getTime(attendence.getmStrTimeIn()));
            holder.mTextHours.setText(attendence.getmStrHurs());
            holder.mTxtDay.setText(getDay(attendence.getmStrDate()));
            holder.mTxtMonth.setText(getMonth(attendence.getmStrDate()));
            if(attendence.getmStrComment().length()>0){
            holder.mTextComment.setVisibility(View.VISIBLE);
            holder.mTextComment.setText(attendence.getmStrComment());
            }else {
            holder.mTextComment.setVisibility(View.GONE);
            }

        return convertView;
    }


    private class ViewHolder {
    RelativeLayout mLayoutItem;
    TextView mTextOutTime;
    TextView mTextInTime;
    TextView mTextComment;
    TextView mTextHours;
    TextView mTxtMonth;
    TextView mTxtDay;
    }


}