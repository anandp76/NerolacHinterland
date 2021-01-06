package com.nerolac;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ListView;

import com.nerolac.Adpter.AnajMandiListAdapter;
import com.nerolac.Adpter.RetailerListAdapter;

import java.util.ArrayList;

import static com.nerolac.Utils.CommonData.setTranceprent;

public class ACTAnajMandiList extends Activity {

    ArrayList<String> mListItem = new ArrayList<>();
    ListView mListView;
    ImageView mImgAddNew;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_list_anajmandi);
        setTranceprent(ACTAnajMandiList.this,R.color.appblue);
        mListView = (ListView)findViewById(R.id.mListView);
        mImgAddNew = (ImageView) findViewById(R.id.mImgAddNew);
        init();
        AnajMandiListAdapter retailerListAdapter = new AnajMandiListAdapter(ACTAnajMandiList.this,mListItem);
        mListView.setAdapter(retailerListAdapter);

        mImgAddNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ACTAnajMandiList.this,ACTAnajMandiForm.class);
                startActivity(intent);
            }
        });


    }
    void init(){
        mListItem.add("hello");
        mListItem.add("hello");
        mListItem.add("hello");
        mListItem.add("hello");
        mListItem.add("hello");
        mListItem.add("hello");
        mListItem.add("hello");
    }


}

