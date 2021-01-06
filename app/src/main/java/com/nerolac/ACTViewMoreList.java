package com.nerolac;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.nerolac.Adpter.RetailerListAdapter;
import com.nerolac.Adpter.ViewMoreListAdapter;

import java.util.ArrayList;

import static com.nerolac.Utils.CommonData.setTranceprent;

public class ACTViewMoreList extends Activity {

    ArrayList<String> mListItem = new ArrayList<>();
    ListView mListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_list_view_more);
        setTranceprent(ACTViewMoreList.this,R.color.appblue);
        mListView = (ListView)findViewById(R.id.mListView);
        init();
        ViewMoreListAdapter retailerListAdapter = new ViewMoreListAdapter(ACTViewMoreList.this,mListItem);
        mListView.setAdapter(retailerListAdapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(ACTViewMoreList.this,ACTTehsilMoreList.class);
                intent.putExtra("name",mListItem.get(i));
                startActivity(intent);
            }
        });



    }
    void init(){
        mListItem.add("Ayodhya");
        mListItem.add("Gorakhpur");
        mListItem.add("Jhansi");
        mListItem.add("Kanpur");
        mListItem.add("Lucknow");
        mListItem.add("Meerut");
        mListItem.add("Mirzapur");
    }


}

