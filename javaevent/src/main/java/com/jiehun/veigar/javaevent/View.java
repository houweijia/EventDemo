package com.jiehun.veigar.javaevent;

import com.jiehun.veigar.javaevent.listener.OnClickListener;
import com.jiehun.veigar.javaevent.listener.OnTouchListener;

/**
 * @description:
 * @author: houwj
 * @date: 2019/4/11
 */
public class View {

    public View(int left, int top, int right, int bottom) {
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
    }

    private int left;
    private int top;
    private int right;
    private int bottom;

    private OnTouchListener mOnTouchListener;
    private OnClickListener mOnClickListener;

    public void setOnTouchListener(OnTouchListener onTouchListener) {
        this.mOnTouchListener = onTouchListener;
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.mOnClickListener = onClickListener;
    }

    public boolean isContainer(int x, int y) {
        if (x > left && x < right && y > top && y < bottom) {
            return true;
        }
        return false;
    }

    public boolean dispatchTouchEvent(MotionEvent event) {
        boolean result = false;
        if (mOnTouchListener != null && mOnTouchListener.onTouch(this, event)) {
            result = true;
        }

        if (!result && onTouchEvent(event)) {
            return true;
        }

        return false;
    }

    private boolean onTouchEvent(MotionEvent event) {
        if (mOnClickListener != null) {
            mOnClickListener.onClick(this);
            return true;
        }

        return false;
    }
}
