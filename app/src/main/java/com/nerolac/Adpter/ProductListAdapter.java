/**
 * Created by admin1 on 27/2/18.
 */
package com.nerolac.Adpter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nerolac.ACTFormUpdate;
import com.nerolac.Modal.productsModal;
import com.nerolac.Modal.productsModal;
import com.nerolac.Modal.Retailers;
import com.nerolac.Modal.productsModal;
import com.nerolac.R;

import java.util.ArrayList;
import java.util.List;

public class ProductListAdapter extends BaseAdapter implements Filterable {

    public ArrayList<productsModal> mListItems = new ArrayList<>();
    public static ArrayList<productsModal> mListItemsFilter = new ArrayList<>();
    private ArrayList<productsModal> arSearch = new ArrayList<>();
    Activity context;
    int ads = 0;
    ItemFilter mFilter;
    public ProductListAdapter(Activity context, ArrayList<productsModal> mListItems) {
        this.mListItems = mListItems;
        this.mListItemsFilter = mListItems;
        this.arSearch = new ArrayList<>();
        this.arSearch.addAll(mListItems);
        this.context = context;
        mFilter = new ItemFilter();
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            LayoutInflater mInflater = LayoutInflater.from(context);
            convertView = mInflater.inflate(R.layout.activity_product_list_item, null);
            holder = new ViewHolder();
            holder.mLayoutItem = convertView.findViewById(R.id.mLayoutItem);
            holder.mImgSelect = convertView.findViewById(R.id.mImgSelect);

            holder.mTextTitle = convertView.findViewById(R.id.mTextTitle);
            holder.msku = convertView.findViewById(R.id.msku);
            holder.mpacksize = convertView.findViewById(R.id.packsize);
            //holder.mImgCall = convertView.findViewById(R.id.mImgCall);
            //holder.mImgPhoto = convertView.findViewById(R.id.mImgPhoto);

            convertView.setTag(holder);
            } else {
            holder = (ViewHolder) convertView.getTag();
            }
        final productsModal retailers = mListItemsFilter.get(position);
        if(retailers.isChecked()){
            holder.mImgSelect.setImageResource(R.drawable.ic_ok_on);

        }
        else {
            holder.mImgSelect.setImageResource(R.drawable.ic_ok_off);

        }
         holder.mImgSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(retailers.isChecked()){
                    holder.mImgSelect.setImageResource(R.drawable.ic_ok_off);

                    for (int i = 0; i < mListItems.size(); i++) {
                        productsModal Sretailers = mListItems.get(i);
                        if (Sretailers.getdescription().toLowerCase().contains(retailers.getdescription().toLowerCase()) && Sretailers.getsku().toLowerCase().contains(retailers.getsku().toLowerCase())) {
                            mListItems.get(i).setChecked(false);
                        }
                    }

                }
                else {
                    holder.mImgSelect.setImageResource(R.drawable.ic_ok_on);

                    for (int i = 0; i < mListItems.size(); i++) {
                        productsModal Sretailers = mListItems.get(i);
                        if (Sretailers.getdescription().toLowerCase().contains(retailers.getdescription().toLowerCase()) && Sretailers.getsku().toLowerCase().contains(retailers.getsku().toLowerCase())) {
                            mListItems.get(i).setChecked(true);
                        }
                    }
                }

            }
        });


        holder.mTextTitle.setText(retailers.getdescription().trim());
        holder.msku.setText("SKU: "+retailers.getsku());
        holder.mpacksize.setText("Pack Size: "+retailers.getpack());

           /* if(position % 2 == 0){
            holder.mLayoutItem.setBackgroundColor(Color.parseColor("#ffffff"));
            }else {
            holder.mLayoutItem.setBackgroundColor(Color.parseColor("#f2f2f2"));
            }

            final Retailers retailers = mListItemsFilter.get(position);
            holder.mTextTitle.setText(retailers.getTbShopName());
            holder.mTextOwnerName.setText(retailers.getTbFirstName());
            holder.mTextAddresss.setText(retailers.getTbAddress1()+", "+retailers.getTbVillage()+", "+retailers.getTbBlock()+", "+retailers.getTbTehsil());
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

        holder.mImgCall.setVisibility(View.GONE);*/

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
                    ArrayList<productsModal> filteredList = new ArrayList<>();
                    for (productsModal row : arSearch) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getdescription().toLowerCase().contains(charString.toLowerCase()) ||row.getsku().toLowerCase().contains(charString.toLowerCase())) {
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
                mListItemsFilter = (ArrayList<productsModal>) filterResults.values;


                notifyDataSetChanged();
            }
        };
    }



    private class ViewHolder {
    RelativeLayout mLayoutItem;
    ImageView mImgSelect;
        TextView mTextTitle;
        TextView msku;
        TextView mpacksize;
    }

    private class ItemFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            String filterString = constraint.toString().toLowerCase();
            FilterResults results = new FilterResults();
            final List<productsModal> list = mListItems;
            int count = list.size();
            final ArrayList<productsModal> nlist = new ArrayList<productsModal>(count);
            String filterableStringName;
            for (int i = 0; i < count; i++) {
                filterableStringName = list.get(i).getdescription()+"#"+list.get(i).getsku();
                System.out.println("CCCCCCC "+filterableStringName);
                System.out.println("CCCCCCC2 "+filterString.toLowerCase());
                if (filterableStringName.toLowerCase().contains(filterString.toLowerCase())) {
                    nlist.add(list.get(i));
                }else {
                    if(i==count-1 && filterString.contains("")  || filterString.contains("")){
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
            mListItemsFilter = (ArrayList<productsModal>) results.values;
            notifyDataSetChanged();
        }
}
}