/**
 * Created by admin1 on 27/2/18.
 */
package com.nerolac.Adpter;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.nerolac.ACTRoutPlanForm;
import com.nerolac.Modal.RoutPlan;
import com.nerolac.R;
import com.nerolac.Utils.PreferenceManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import androidx.appcompat.app.AlertDialog;

import static com.nerolac.ACTTravelLog.mycall;
import static com.nerolac.Utils.CommonData.BaseUrl;
import static com.nerolac.Utils.CommonData.getDay;
import static com.nerolac.Utils.CommonData.getMonth;
import static com.nerolac.Utils.CommonData.hidePDialog;
import static com.nerolac.Utils.CommonData.mShowAlert;
import static com.nerolac.Utils.CommonData.showProgress;

public class RoutPlanListAdapter extends BaseAdapter {

    private ArrayList<RoutPlan> mListItems = new ArrayList<>();
    Activity context;
    int ads = 0;
    RequestQueue queue;

    public RoutPlanListAdapter(Activity context, ArrayList<RoutPlan> mListItems) {
        this.mListItems = mListItems;
        this.context = context;
        queue = Volley.newRequestQueue(context);
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
            holder.mTxtDate = convertView.findViewById(R.id.mTxtDate);
            holder.mTxtMon = convertView.findViewById(R.id.mTxtMon);
            holder.mTextTitle = convertView.findViewById(R.id.mTextTitle);
            holder.mTextAddresss = convertView.findViewById(R.id.mTextAddresss);
            holder.mImgDelete = convertView.findViewById(R.id.mImgDelete);
            holder.mImgUpdate = convertView.findViewById(R.id.mImgUpdate);
            convertView.setTag(holder);
            } else {
            holder = (ViewHolder) convertView.getTag();
            }
            final RoutPlan routPlan = mListItems.get(position);
            holder.mTextTitle.setText(routPlan.getmStrVillage());
            holder.mTextAddresss.setText(routPlan.getmStrBlock()+", "+routPlan.getmStrTehsil()+", "+routPlan.getmStrDistrict());
            holder.mTxtDate.setText(getDay(routPlan.getmStrCreated()));
            holder.mTxtMon.setText(getMonth(routPlan.getmStrCreated()));

            holder.mImgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder alert = new AlertDialog.Builder(context);
                alert.setTitle("Delete entry");
                alert.setMessage("Are you sure you want to delete?");
                alert.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                        dialog.dismiss();
                        showProgress(context);
                        mFunGetMataData1(routPlan.getmStrId());

                    }
                });
                alert.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // close dialog
                        dialog.cancel();
                    }
                });
                alert.show();
            }
            });
            holder.mImgUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            Intent intent = new Intent(context, ACTRoutPlanForm.class);
            intent.putExtra("mStrRawState",routPlan.getmStrState());
            intent.putExtra("mStrRawDistrict",routPlan.getmStrDistrict());
            intent.putExtra("mStrRawTehsil",routPlan.getmStrTehsil());
            intent.putExtra("mStrRawBlock",routPlan.getmStrBlock());
            intent.putExtra("mStrRawVillage",routPlan.getmStrVillage());
            intent.putExtra("mStrRawKilometer",routPlan.getmStrKiloMeter());
            intent.putExtra("mStrRawTravelId",routPlan.getmStrId());
            intent.putExtra("mStrRawOption","1");
            context.startActivity(intent);
            }
        });
        return convertView;
    }


    private class ViewHolder {
    RelativeLayout mLayoutItem;
    TextView mTxtDate;
    TextView mTxtMon;
    TextView mTextTitle;
    TextView mTextAddresss;
    ImageView mImgDelete;
    ImageView mImgUpdate;
    }

    void mFunGetMataData1(final String mStrid) {
        StringRequest strRequest = new StringRequest(Request.Method.POST,BaseUrl+"deleteUsersTravel",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String str) {
                        hidePDialog();
                        try {
                            JSONObject response = new JSONObject(str);
                            System.out.println("<><><>## " + response.toString());
                            String mStrStatus = response.getString("statusCode");
                            if (mStrStatus.equals("200")) {
                            mShowAlert("Record deleted successfully!!",context);
                            mycall();
                            } else {
                            mShowAlert(context.getResources().getString(R.string.Something),context);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        hidePDialog();
                        mShowAlert(context.getString(R.string.Something),context);
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", PreferenceManager.getNEROTOKEN(context));
                return params;
            }
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("user_travel_id",mStrid);
                params.put("user_id",PreferenceManager.getNEROUSERID(context));
                return params;
            }
        };
        queue.add(strRequest);
    }


}