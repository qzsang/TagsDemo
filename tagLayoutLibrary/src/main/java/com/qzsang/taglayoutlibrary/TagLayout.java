package com.qzsang.taglayoutlibrary;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by qzsang on 2017/7/21.
 */

public class TagLayout extends ViewGroup implements View.OnClickListener{

    private OnCreatChildView mOnCreatChildView;

    public TagLayout(Context context) {
        super(context);
        init (context, null);
    }

    public TagLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init (context, attrs);
    }


    private void init (Context context, AttributeSet attrs) {
        initView();
    }

    public void initView () {
        removeAllViews();
        if (mOnCreatChildView == null) {
            return;
        }else {
            int count = mOnCreatChildView.getCount();
            for (int i = 0; i < count; i++) {
                View childView = mOnCreatChildView.onCreatTagView(i);
                childView.setTag(i);
                addView(childView);
                childView.setOnClickListener(this);
            }
        }

    }


    public void setOnCreatChildView(OnCreatChildView onCreatChildView) {
        this.mOnCreatChildView = onCreatChildView;
    }




    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);

        // 计算出所有的childView的宽和高
        measureChildren(widthMeasureSpec, heightMeasureSpec);

        int cCount = getChildCount();

        int width = 0;
        int height = 0;
        int lineMaxHeight = 0;//每行最高子view的高度
        boolean isMulLine = false;//是否多行
        for (int i = 0; i < cCount; i++) {
            View childView = getChildAt(i);
            width += childView.getMeasuredWidth();
            if (width > sizeWidth ) {
                isMulLine = true;
                width = childView.getMeasuredWidth();
                height += lineMaxHeight;//换行统计上一个高度
                lineMaxHeight = 0;
            }

            int measuredHeigh = childView.getMeasuredHeight();
            if (measuredHeigh > lineMaxHeight) {
                lineMaxHeight = measuredHeigh;
            }
            //对最后一个 时  统计改行高度
            if (i == cCount - 1) {
                height += lineMaxHeight;
            }

        }

        if (isMulLine) {//换行过取最大宽度
            width = sizeWidth;
        }

        /**
         * 如果是wrap_content设置为我们计算的值
         * 否则：直接设置为父容器计算的值
         */
        setMeasuredDimension((widthMode == MeasureSpec.EXACTLY) ? sizeWidth
                : width, (heightMode == MeasureSpec.EXACTLY) ? sizeHeight
                : height);


    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int cCount = getChildCount();
        int parentWidth = getMeasuredWidth();

        int startL = 0;
        int startT = 0;
        int lineMaxHeight = 0;//每行最高子view的高度

        for (int i = 0; i < cCount; i++) {
            View childView = getChildAt(i);
            int measuredHeigh = childView.getMeasuredHeight();
            int measuredWidth = childView.getMeasuredWidth();

            int cl = startL;
            int cr = startL + measuredWidth;
            int ct = startT;
            int cb = ct + measuredHeigh;

            startL += measuredWidth;
            if (startL > parentWidth) {//下一个换行
                startL = 0;
                startT += lineMaxHeight;
                lineMaxHeight = 0;

                cl = startL;
                cr = startL + measuredWidth;
                ct = startT;
                cb = ct + measuredHeigh;

                startL += measuredWidth;

            }
            if (lineMaxHeight < measuredHeigh) {
                lineMaxHeight = measuredHeigh;
            }



            childView.layout(cl,ct  ,cr ,cb);

        }


    }

    @Override
    public void onClick(View v) {

        Integer positon = (Integer) v.getTag();
        if (mOnCreatChildView != null && positon != null) {
            mOnCreatChildView.itemClick(positon);
        }
    }


    public interface OnCreatChildView {
        View onCreatTagView (int positon);
        int getCount ();
        void itemClick (int position);
    }
}
