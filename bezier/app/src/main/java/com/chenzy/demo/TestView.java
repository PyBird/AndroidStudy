package com.chenzy.demo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
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
    private Paint mPaintLine;
    private int vWidth, vHeight;
    private Point circleP;
    private Path mPath = new Path();
    private float mX;
    private float mY;

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

        // 抗锯齿
        mPaintLine = new Paint();
        mPaintLine.setAntiAlias(true);
        // 防抖动
//        mPaintLine.setDither(true);
        mPaintLine.setStyle(Paint.Style.STROKE);
        mPaintLine.setColor(Color.GREEN);
        mPaintLine.setStrokeWidth(5.0f);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
        super.onMeasure(widthMeasureSpec,heightMeasureSpec);

        vWidth = getMeasuredWidth();
        vHeight = getMeasuredHeight();

        circleP = new Point(vWidth/2,vHeight/2);

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int width = widthSize;
        int height = heightSize;
//        if (widthMode == MeasureSpec.EXACTLY)
//        {
//            width = widthSize;
//        } else
//        {
//            float textWidth = mPaint.getStrokeWidth();
//            int desired = (int) (getPaddingLeft() + textWidth + getPaddingRight());
//            width = desired;
//        }
//
//        if (heightMode == MeasureSpec.EXACTLY)
//        {
//            height = heightSize;
//        }
//        else
//        {
//            int desired = (int) (getPaddingTop() + textHeight + getPaddingBottom());
//            height = desired;
//        }

        setMeasuredDimension(width, height);
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

        canvas.drawPath(mPath, mPaintLine);
        canvas.drawCircle(circleP.x,circleP.y,R/8,mPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        super.onTouchEvent(event);

        switch (event.getAction()){

            case MotionEvent.ACTION_DOWN:
                mPath.reset();
                mX=event.getX();
                mY=event.getY();
                mPath.moveTo(mX,mY);
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
                }

                    float cX = (x + mX) / 2;
                    float cY = (y + mY) / 2;


//                mPath.quadTo(mX,mY,Math.abs(x-mX),Math.abs(y-mY));
//                mPath.quadTo(mX,mY,mX+x,mY+y);
//                mPath.quadTo(mX,mY,x,y);
                mPath.quadTo(mX,mY,cX,cY);
                mX=x;
                mY=y;

//                final float previousX = mX;
//                final float previousY = mY;
//
//                final float dx = Math.abs(x - previousX);
//                final float dy = Math.abs(y - previousY);
//
//                //两点之间的距离大于等于3时，生成贝塞尔绘制曲线
//                if (dx >= 3 || dy >= 3)
//                {
//                    //设置贝塞尔曲线的操作点为起点和终点的一半
//                    float cX = (x + previousX) / 2;
//                    float cY = (y + previousY) / 2;
//
//                    //二次贝塞尔，实现平滑曲线；previousX, previousY为操作点，cX, cY为终点
//                    mPath.quadTo(previousX, previousY, cX, cY);
//
//                    //第二次执行时，第一次结束调用的坐标值将作为第二次调用的初始坐标值
//                    mX = x;
//                    mY = y;
//                }

                Log.d("onTouchEvent","---x:"+x+"  ----y:"+y);
                Log.d("onTouchEvent","---mX:"+mX+"  ----mY:"+mY);
                invalidate();
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
