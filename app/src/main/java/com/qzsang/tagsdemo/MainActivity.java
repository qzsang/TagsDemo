package com.qzsang.tagsdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.qzsang.taglayoutlibrary.SingleLineTagLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    SingleLineTagLayout sltl_tags;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sltl_tags = (SingleLineTagLayout) findViewById(R.id.sltl_tags);

        sltl_tags.setOnCreatChildView(new SingleLineTagLayout.OnCreatChildView() {


            @Override
            public View onCreatTagView(String str, LayoutInflater factory) {
               View view = factory.inflate(R.layout.item_tag, null);
                TextView textView = (TextView) view.findViewById(R.id.tv_tag);
                textView.setText(str + "");
                return view;
            }

            @Override
            public View onCreatAppendView(LayoutInflater factory) {
                View view = factory.inflate(R.layout.item_tag, null);
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


    }
}
