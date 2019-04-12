package com.jiehun.veigar.javaevent;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.LinkedList;
import java.util.List;

/**
 * @description:
 * @author: houwj
 * @date: 2019/4/11
 */
public class ViewGroup extends View {
    List<View> childList = new ArrayList<>();
    private View[] mChildren = new View[0];

    public ViewGroup(int left, int top, int right, int bottom) {
        super(left, top, right, bottom);
    }

    public void addView(View view) {
        if (view == null) {
            return;
        }
        childList.add(view);
        mChildren = childList.toArray(new View[childList.size()]);
    }

    private TouchTarget mFirstTouchTarget;
    private TouchTarget newTouchTarget;
    private boolean     handled;

    public boolean dispatchTouchEvent(MotionEvent ev) {
        System.out.println(name+" dispatchTouchEvent ");
        boolean intercepted = onInterceptTouchEvent(ev);
        int actionMasked = ev.getActionMasked();
        if (actionMasked != MotionEvent.ACTION_CANCEL && !intercepted) {
            if (actionMasked == MotionEvent.ACTION_DOWN) {
                final View[] childrens = mChildren;

                for (int i = childrens.length-1; i >= 0; i--) {
                    View child = mChildren[i];
                    if (!child.isContainer(ev.getX(), ev.getY())) {
                        continue;
                    }

                    //能够接受事件
                    if (dispatchTransformedTouchEvent(ev, child)) {
                        //采取了Message的方式进行 链表结构
                        newTouchTarget = addTouchTarget(child);
                        break;

                    }
                }
            }

            if (mFirstTouchTarget == null) {
                handled = dispatchTransformedTouchEvent(ev, null);
            }
        }
        return intercepted;
    }

    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return false;
    }


    //分发处理
    private boolean dispatchTransformedTouchEvent(MotionEvent event, View child) {
        boolean handled = false;
        if (child != null) {
            handled = child.dispatchTouchEvent(event);
        } else {
            handled = super.dispatchTouchEvent(event);
        }
        return handled;
    }

    private TouchTarget addTouchTarget(View child) {
        final TouchTarget target = TouchTarget.obtain(child);
        target.next = mFirstTouchTarget;
        mFirstTouchTarget = target;
        return target;
    }

    private static final class TouchTarget {
        private              View        child;//当前缓存的View
        private              TouchTarget next;
        private static       TouchTarget sRecycleBin;
        private static       int         sRecycleCount;
        private static final Object[]    sRecycleLock = new Object[0];
        private final        int         MAX_RECYCLED = 32;

        public static TouchTarget obtain(View child) {
            TouchTarget target;
            synchronized (sRecycleLock) {
                if (sRecycleBin == null) {
                    target = new TouchTarget();
                } else {
                    target = sRecycleBin;
                }

                sRecycleBin = target.next;
                sRecycleCount--;
                target.next = null;
            }
            target.child = child;
            return target;
        }

        public void recycle() {
            if (child == null) {
                throw new IllegalStateException("already recycled once");
            }

            synchronized (sRecycleLock) {
                if (sRecycleCount < MAX_RECYCLED) {
                    next = sRecycleBin;
                    sRecycleBin = this;
                    sRecycleCount += 1;
                } else {
                    next = null;
                }
                child = null;
            }
        }
    }

    private String name;

    @Override
    public String toString() {
        return "" + name;
    }

    public void setName(String name) {
        this.name = name;
    }
}