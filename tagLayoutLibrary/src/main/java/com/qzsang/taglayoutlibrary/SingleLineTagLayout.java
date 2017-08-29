package com.qzsang.taglayoutlibrary;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by qzsang on 2017/7/19.
 *
 * HORIZONTAL方向
 *
 * 单行的tags布局    tag超出带省略号
 *
 */

public class SingleLineTagLayout extends ViewGroup {

    private String hint = "这里还没有任何标签哦";//没有标签的提示内容
    private float hintSize = 0;//没有标签的提示内容 字体大小
    private int hintPaddingLeft = 0;
    private int hintColor = 0;

    private int showItemCount = 0;//能显示的item数量

    private List<?> tags = null;//标签

    private int appendViewWidth = 0;//追加的view的宽度,即  显示...view的宽度

    private boolean isShowAppendView = false;//是否显示追加的view

    private OnCreatChildView onCreatChildView;

    public SingleLineTagLayout(Context context) {
        super(context);
        init (context, null);
    }

    public SingleLineTagLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init (context, attrs);
    }



    private void init (Context context, AttributeSet attrs) {


        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SingleLineTagLayout, 0, 0);
        String tempStrNote = a.getString(R.styleable.SingleLineTagLayout_SingleLineTagLayoutHint);
        hintPaddingLeft = a.getDimensionPixelSize(R.styleable.SingleLineTagLayout_SingleLineTagLayoutPaddingLeft, hintPaddingLeft);
        hintSize = a.getDimension(R.styleable.SingleLineTagLayout_SingleLineTagLayoutSize, hintSize);
        hintColor = a.getColor(R.styleable.SingleLineTagLayout_SingleLineTagLayoutColor, hintColor);
        a.recycle();


        if (!TextUtils.isEmpty(tempStrNote)) {
            hint = tempStrNote;
        }
        initView();

    }

    public void initView () {
        removeAllViews();

        //新增提示
        if (getTagCount() == 0 || onCreatChildView == null) {
            TextView textView = new TextView(getContext());
            textView.setText(hint);
            if (hintPaddingLeft != 0) {
                textView.setPadding(hintPaddingLeft,0,0,0);
            }
            if (hintSize != 0) {
                textView.setTextSize(TypedValue.COMPLEX_UNIT_PX,hintSize);
            }
            if (hintColor != 0) {
                textView.setTextColor(hintColor);
            }

            addView(textView);
            showItemCount = 1;
        } else {
            //增加tag
            for (int i = 0; i < tags.size(); i++) {
                addView(onCreatChildView.onCreatTagView(tags.get(i).toString()));
            }

            //增加tag 后面的...
            addView(onCreatChildView.onCreatAppendView());

            showItemCount = tags.size() + 1;
        }
    }


    public void setTags(List<?> tags) {
        this.tags = tags;
        if (onCreatChildView == null) {
            return;
        }
        initView();
    }

    public List<?> getTags() {
        return tags;
    }

    public int getTagCount() {
        return tags == null ? 0 : tags.size();
    }

    public void setOnCreatChildView(OnCreatChildView onCreatChildView) {
        this.onCreatChildView = onCreatChildView;
    }

    /**
     * 使其支持margin
     * @param attrs
     * @return
     */
    @Override
    public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attrs){
        return new MarginLayoutParams(getContext(), attrs);
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new MarginLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        if (widthMeasureSpec == 0 && heightMeasureSpec == 0) {
            super.onMeasure(widthMeasureSpec,heightMeasureSpec);
            return;
        }
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);


        isShowAppendView = false;
        // 计算出所有的childView的宽和高
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        int cCount = getChildCount();
        if (getTagCount() != 0) {

            //计算showItemCount
            View appendView = getChildAt(cCount - 1);
            MarginLayoutParams appendLP = (MarginLayoutParams) appendView.getLayoutParams();
            appendViewWidth = (appendView.getMeasuredWidth() + appendLP.rightMargin)  + appendLP.leftMargin;

            int startL = 0;
            for (int i = 0; i < cCount; i++) {
                int cRealWidth = getChildRealWidth(getChildAt(i));

                startL += cRealWidth;

                if (startL > sizeWidth) {
                    while (i >= 0) {
                        startL -= getChildRealWidth(getChildAt(i));
                        if ( (startL  + appendViewWidth) < sizeWidth) {//满足条件
                            break;
                        }
                        i--;
                    }

                    showItemCount = i + 1;
                    isShowAppendView = true;
                    break;

                }
            }

        }


        int width = 0;
        int height = 0;
        for (int i = 0; i < cCount; i++) {
            View childView = getChildAt(i);
            int cRealWidth = getChildRealWidth(childView);
            width += cRealWidth;
            int tempHeight = getChildRealHeight(childView);
            if (tempHeight > height) {//高度取最高
                height = tempHeight;
            }
        }
        if (!isShowAppendView && getTagCount() > 0) {
            width -= appendViewWidth;
        }

        if (sizeWidth < width) {
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


    private int getChildRealWidth (View childView) {
        if (childView == null) return 0;

        int cWidth = childView.getMeasuredWidth();
        MarginLayoutParams lp = (MarginLayoutParams) childView.getLayoutParams();
        int cl = lp.leftMargin;
        int cr = cWidth + lp.rightMargin;

        return cr - cl;
    }

    private int getChildRealHeight (View childView) {
        if (childView == null) return 0;

        int cHeight = childView.getMeasuredHeight();
        MarginLayoutParams lp = (MarginLayoutParams) childView.getLayoutParams();

        int ct = lp.topMargin;
        int cb = cHeight + lp.bottomMargin;


        return cb - ct;
    }
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int cCount = getChildCount();
        int cWidth = 0;
        int cHeight = 0;
        MarginLayoutParams lp;
        int measuredHeight = getMeasuredHeight();
        int startL = 0;

        //让其从左到右排列
        for (int i = 0; i < cCount && i < showItemCount; i++) {
            View childView ;

            if ( (i == cCount - 1 || i == showItemCount - 1)) {//最后一个
                if (isShowAppendView) {
                    childView = getChildAt(cCount - 1);
                } else if (getTagCount() == 0) {
                    childView = getChildAt(i);
                } else {
                    break;
                }
            } else {
                childView = getChildAt(i);
            }

            cWidth = childView.getMeasuredWidth();
            cHeight = childView.getMeasuredHeight();
            lp = (MarginLayoutParams) childView.getLayoutParams();

            int cl = lp.leftMargin;
            int cr = cWidth + lp.rightMargin;
            int ct = lp.topMargin;
            int cb = cHeight + lp.bottomMargin;

            int cRealHeight = cb - ct;
            int startHeight = 0;

            if (cRealHeight < measuredHeight) {
                startHeight = (measuredHeight - cRealHeight) / 2;
            }
            childView.layout(cl + startL,ct + startHeight,cr + startL,cb + startHeight);
            startL += (cr + cl);
        }

    }


    public interface OnCreatChildView {
        View onCreatTagView (String str);
        View onCreatAppendView();
    }

}

