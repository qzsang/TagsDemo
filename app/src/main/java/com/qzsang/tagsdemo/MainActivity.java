package com.qzsang.tagsdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
                textView.setText("....");
                return view;
            }
        });

        List<String> tags = new ArrayList<>();
        tags.add("我是一个tag;");
        tags.add("我是一个tag;");
        tags.add("我是一个tag;");
        tags.add("我是一个tag;");
        sltl_tags.setTags(tags);

    }
}
