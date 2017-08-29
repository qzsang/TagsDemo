package com.qzsang.tagsdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

        iniSingleLineTagLayout();
        initTagLayout();

    }

    public void iniSingleLineTagLayout () {
        sltl_tags = (SingleLineTagLayout) findViewById(R.id.sltl_tags);

        sltl_tags.setOnCreatChildView(new SingleLineTagLayout.OnCreatChildView() {

            @Override
            public View onCreatTagView(String str) {
                View childView = View.inflate(MainActivity.this, R.layout.item_single_user_tag, null);
                TextView textView = (TextView) childView.findViewById(R.id.tv_tag_name);
                textView.setText(str);
                return childView;
            }

            @Override
            public View onCreatAppendView() {
                View childView = View.inflate(MainActivity.this, R.layout.item_single_user_tag_append, null);
                return childView;
            }
        });

        List<String> list = new ArrayList<String>();
        list.add("产品");
        list.add("设计");
        list.add("程序员");
        list.add("销售");
        list.add("大律师");
        list.add("龙珠超");
        list.add("火影忍者");
        list.add("海贼王");
        list.add("火影忍者博人传");
        sltl_tags.setTags(list);
    }

    public void initTagLayout() {
        tl_tags = (TagLayout) findViewById(R.id.tl_tags);

        final List <String> list = new ArrayList<>();
        list.add("产品");
        list.add("设计");
        list.add("程序员");
        list.add("销售");
        list.add("大律师");
        list.add("龙珠超");
        list.add("火影忍者");
        list.add("海贼王");
        list.add("火影忍者博人传");
        tl_tags.setOnCreatChildView(new TagLayout.OnCreatChildView() {
            @Override
            public View onCreatTagView(int positon) {
                View childView = null;
                if (positon == list.size()) {
                    childView = View.inflate(MainActivity.this, R.layout.item_user_custom_tag_add, null);
                } else {
                    childView = View.inflate(MainActivity.this, R.layout.item_user_custom_tag, null);
                    TextView textView = (TextView) childView.findViewById(R.id.tv_tag_name);
                    textView.setText("" + list.get(positon));
                }

                return childView;
            }

            @Override
            public int getCount() {
                return list.size() + 1;
            }

            @Override
            public void itemClick(int position) {
                Toast.makeText(MainActivity.this,"我点了"  + position, Toast.LENGTH_SHORT).show();
            }
        });
        tl_tags.initView();
    }
}
