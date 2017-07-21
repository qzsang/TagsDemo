package com.qzsang.tagsdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.qzsang.taglayoutlibrary.SingleLineTagLayout;
import com.qzsang.taglayoutlibrary.TagLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    SingleLineTagLayout sltl_tags;
    TagLayout tl_tags;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sltl_tags = (SingleLineTagLayout) findViewById(R.id.sltl_tags);
        tl_tags = (TagLayout) findViewById(R.id.tl_tags);

        sltl_tags.setOnCreatChildView(new SingleLineTagLayout.OnCreatChildView() {


            @Override
            public View onCreatTagView(String str) {
               View view = View.inflate(MainActivity.this, R.layout.item_tag, null);
                TextView textView = (TextView) view.findViewById(R.id.tv_tag);
                textView.setText(str + "");
                return view;
            }

            @Override
            public View onCreatAppendView() {
                View view = View.inflate(MainActivity.this, R.layout.item_tag, null);
                TextView textView = (TextView) view.findViewById(R.id.tv_tag);
                textView.setText("----");
                return view;
            }
        });


        findViewById(R.id.btn_click).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                List<String> list = new ArrayList<String>();
                list.add("sdfasdfasdf");
                list.add("sdfasdfasdf");
                list.add("sdfasdfasdf");
                sltl_tags.setTags(list);
            }
        });


        //-----------------------------------------

       final List <String> list = new ArrayList<>();
       for (int i = 1; i <= 100; i ++) {
           list.add("我tag" + i);
       }
        tl_tags.setOnCreatChildView(new TagLayout.OnCreatChildView() {
            @Override
            public View onCreatTagView(int positon) {
                View view = View.inflate(MainActivity.this, R.layout.item_tag, null);
                TextView textView = (TextView) view.findViewById(R.id.tv_tag);
                textView.setText(list.get(positon));
                return view;
            }

            @Override
            public int getCount() {
                return list.size();
            }

            @Override
            public void itemClick(int position) {
                Toast.makeText(MainActivity.this,"我点了"  + position, Toast.LENGTH_SHORT).show();
            }
        });
        tl_tags.initView();

    }
}
