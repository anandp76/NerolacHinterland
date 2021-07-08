/**
 * Created by admin1 on 27/2/18.
 */
package com.nerolac.Adpter;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
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
import com.nerolac.ACTOrderList;
import com.nerolac.ACT_UPLOAD_BILL_ACTIVITY;
import com.nerolac.Modal.OderRetailerModal;
import com.nerolac.R;
import com.nerolac.Utils.CommonData;

import java.util.ArrayList;
import java.util.List;

public class OrderListAdapter extends BaseAdapter implements Filterable {

    public  ArrayList<OderRetailerModal> mListItemsFilter = new ArrayList<>();
    AlertDialog alertDialog;
    Activity context;
    int ads = 0;
    ItemFilter mFilter;
    ACTOrderList farmerMeetingImageFragment;
    private ArrayList<OderRetailerModal> mListItems = new ArrayList<>();
    private ArrayList<OderRetailerModal> arSearch = new ArrayList<>();

    public OrderListAdapter(Activity context, ArrayList<OderRetailerModal> mListItems) {
        this.mListItems = mListItems;
        this.mListItemsFilter = mListItems;
        this.context = context;
        this.arSearch = new ArrayList<>();
        this.arSearch.addAll(mListItems);
        mFilter = new ItemFilter();
        farmerMeetingImageFragment = ACTOrderList.instance;

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
            convertView = mInflater.inflate(R.layout.activity_order_list_item, null);
            holder = new ViewHolder();
            holder.mLayoutItem = convertView.findViewById(R.id.mLayoutItem);
            holder.mTextTitle = convertView.findViewById(R.id.mTextTitle);
            holder.mTextAddresss = convertView.findViewById(R.id.mTextAddresss);
            holder.mTextOwnerName = convertView.findViewById(R.id.mTextOwnerName);
            holder.mImgCall = convertView.findViewById(R.id.mImgCall);
            holder.mImgPhoto = convertView.findViewById(R.id.mImgPhoto);
            holder.mImgbillupload = convertView.findViewById(R.id.mIngbilling);
            holder.mImgdelete = convertView.findViewById(R.id.mIngdelete);
holder.amountdate = convertView.findViewById(R.id.mTextDate);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final OderRetailerModal retailers = mListItemsFilter.get(position);
        holder.mTextTitle.setText(retailers.getfld_shop_name());
        if (position % 2 == 0) {
            holder.mLayoutItem.setBackgroundColor(Color.parseColor("#ffffff"));
        } else {
            holder.mLayoutItem.setBackgroundColor(Color.parseColor("#f2f2f2"));
        }


        holder.mTextOwnerName.setText(retailers.getfld_name());
        holder.amountdate.setText("Estimate Number:"+retailers.getfld_estimate_number()+"\nOrder Amount: RS. "+retailers.getfld_order_amount()+"  Date: "+ CommonData.orderdate(retailers.getfld_order_date()));
        holder.mTextAddresss.setText(retailers.getfld_address1() + ", " + retailers.getTbVillage());
        Glide.with(context.getApplicationContext()).load(retailers.getfld_img()).placeholder(R.drawable.ic_nero_add_img).error(R.drawable.ic_nero_add_img).into(holder.mImgPhoto);
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
        holder.mImgdelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertmessage(retailers.getfld_rorder_id());
            }
        });
        holder.mImgCall.setVisibility(View.GONE);
        if (retailers.getfld_order_date().equalsIgnoreCase(CommonData.getTimeformatWithoutTime())) {
            holder.mImgdelete.setVisibility(View.VISIBLE);
        }
        if (retailers.getfld_bill_copy().equalsIgnoreCase("null")) {
            holder.mImgbillupload.setVisibility(View.VISIBLE);

        } else {
            holder.mImgbillupload.setVisibility(View.GONE);
        }


        holder.mImgbillupload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ACT_UPLOAD_BILL_ACTIVITY.class);
                intent.putExtra("OderRetailerModal",retailers);
                context.startActivity(intent);
            }
        });




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
                    ArrayList<OderRetailerModal> filteredList = new ArrayList<>();
                    for (OderRetailerModal row : arSearch) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getfld_shop_name().toLowerCase().contains(charString.toLowerCase()) ) {
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
                mListItemsFilter = (ArrayList<OderRetailerModal>) filterResults.values;


                notifyDataSetChanged();
            }
        };
    }

    private void alertmessage(final String orderid) {

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.common_popup_layout,
                null);
        final AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.MyDialogTheme);

        builder.setView(layout);
        builder.setCancelable(false);
        alertDialog = builder.create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        alertDialog.show();

        TextView title_popup = layout.findViewById(R.id.title_popup);
        TextView message_popup = layout.findViewById(R.id.message_popup);
        TextView no_text_popup = layout.findViewById(R.id.no_text_popup);
        TextView yes_text_popup = layout.findViewById(R.id.yes_text_popup);

        yes_text_popup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();

                //   DeleteImage(appointmentId);
                farmerMeetingImageFragment.removeimage(orderid);

            }
        });

        no_text_popup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

    }

    private class ViewHolder {
        RelativeLayout mLayoutItem;
        TextView mTextAddresss;
        TextView mTextOwnerName;
        TextView mTextTitle;
        ImageView mImgCall;
        ImageView mImgPhoto;
        ImageView mImgbillupload;
        ImageView mImgdelete;
        TextView amountdate;
    }

    private class ItemFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            String filterString = constraint.toString().toLowerCase();
            FilterResults results = new FilterResults();
            final List<OderRetailerModal> list = mListItems;
            int count = list.size();
            final ArrayList<OderRetailerModal> nlist = new ArrayList<OderRetailerModal>(count);
            String filterableStringName;
            for (int i = 0; i < count; i++) {
                filterableStringName = list.get(i).getfld_name() + "#" + list.get(i).getfld_rorder_id();
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
            mListItemsFilter = (ArrayList<OderRetailerModal>) results.values;
            notifyDataSetChanged();
        }
    }
}