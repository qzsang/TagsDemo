


SingleLineTagLayout  主要实现如下图的效果，简单封装了下


![这里写图片描述](https://github.com/qzsang/ScaleView/doc/img1.png)

上代码
``` java
      //布局



        <com.qzsang.taglayoutlibrary.SingleLineTagLayout
                  android:id="@+id/sltl_tags"
                  android:layout_width="wrap_content"
                  android:layout_height="wrap_content"
                  app:hint="我是没有任何标签时候的提示"
                  />


        //设置创建的回调
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

            //设置数据
            List<String> tags = new ArrayList<>();
            tags.add("我是一个tag;");
            tags.add("我是一个tag;");
            tags.add("我是一个tag;");
            tags.add("我是一个tag;");
            sltl_tags.setTags(tags);

	}
```