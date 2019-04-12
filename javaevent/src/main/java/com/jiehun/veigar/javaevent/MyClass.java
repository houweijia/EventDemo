package com.jiehun.veigar.javaevent;


import com.jiehun.veigar.javaevent.listener.OnClickListener;
import com.jiehun.veigar.javaevent.listener.OnTouchListener;

public class MyClass {
    public static void main(String[] args){
        ViewGroup viewGroup = new ViewGroup(0,0,1080,1920);
        viewGroup.setName("顶级容器");
        ViewGroup viewGroup2 = new ViewGroup(0,0,540,810);
        viewGroup2.setName("第二层容器");

        View view = new View(0,0,200,200);

        viewGroup2.addView(view);
        viewGroup.addView(viewGroup2);

        MotionEvent motionEvent = new MotionEvent(100,100);



        viewGroup.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                System.out.println("顶级的OnTouch事件");
                return false;
            }
        });
        viewGroup2.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                System.out.println("第二级容器的OnTouch事件");
                return false;
            }
        });

        view.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("view的onClick事件");
            }
        });

        view.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                System.out.println("view的OnTouch事件");
                return false;
            }
        });
        motionEvent.setActionMasked(MotionEvent.ACTION_DOWN);

        viewGroup.dispatchTouchEvent(motionEvent);
    }
}
