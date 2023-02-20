package com.example.test001.widget;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;

public class FJNestedScrollView extends NestedScrollView {
    public FJNestedScrollView(@NonNull Context context) {
        super(context);
    }

    public FJNestedScrollView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public FJNestedScrollView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        listener.change(l, t, oldl, oldt);
    }

    public void setScrollListener(onScroll listener) {
        this.listener = listener;
    }

    private onScroll listener;

    public interface onScroll {
        void change(int l, int t, int oldl, int oldt);
    }

}
