package com.chenzy.demo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by tocet on 2016/12/29.
 */

public class TestView extends View {

    private Paint mPaint;
    private int vWidth, vHeight;
    private Point circleP;

    public TestView(Context context) {
        super(context);

        init();
    }

    public TestView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        init();
    }

    public TestView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    private void init(){

        mPaint = new Paint();
        // 抗锯齿
        mPaint.setAntiAlias(true);
        // 防抖动
        mPaint.setDither(true);
        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(10.0f);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
        super.onMeasure(widthMeasureSpec,heightMeasureSpec);

        vWidth = getMeasuredWidth();
        vHeight = getMeasuredHeight();

        circleP = new Point(vWidth/2,vHeight/2);
    }

    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);

        int R;
        if(vWidth>vHeight){
            R=vHeight;
        }else{
            R=vWidth;
        }

        canvas.drawCircle(circleP.x,circleP.y,R/8,mPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        super.onTouchEvent(event);

        switch (event.getAction()){

            case MotionEvent.ACTION_DOWN:
                break;

            case MotionEvent.ACTION_MOVE:

                int R;
                if(vWidth>vHeight){
                    R=vHeight;
                }else{
                    R=vWidth;
                }

                R=R/8;

                int XX = vWidth-R;
                int YY = vHeight-R;
                float x = event.getX();
                float y = event.getY();
                //控制在布局内
                if((x>=R && x<=XX) && (y>=R && y<=YY)){
                    circleP = new Point(x,y);
                    invalidate();
                }
                //超出画圆布局处理
                else{

                    if(x<R){
                        x=R;
                    }
                    else if(x>XX){
                        x=XX;
                    }

                    if(y<R){
                        y=R;
                    }
                    else if(y>YY){
                        y=YY;
                    }

                    circleP = new Point(x,y);
                    invalidate();
                }

                Log.d("onTouchEvent","---x:"+x+"  ----y:"+y);

                break;

            case MotionEvent.ACTION_UP:
                break;
        }

        return true;
    }

    private class Point {
        float x, y;

        public Point(float x, float y) {
            this.x = x;
            this.y = y;
        }
    }

}
