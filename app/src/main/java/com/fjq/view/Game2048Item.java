package com.fjq.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by FJQ on 2016/3/3.
 * 构造器一个参数的调二个参数的 二个参数的构造器调三个参数的构造器
 */
public class Game2048Item extends View {
    public static final String TAG = "Game2048Item";
    /**
     * 该View上的数字
     */
    private int mNumber;
    private String mNumberVal;
    private Paint mPaint;

    private float outerRadiusRat=50f;

    /**
     * 绘制文字的区域
     */
    private Rect mBound;
    public Game2048Item(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
    }

    public Game2048Item(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Game2048Item(Context context) {
        this(context, null);
    }

    public void setNumber(int number){
        this.mNumber=number;
        mNumberVal=number+"";
        Typeface font = Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD);
        mPaint.setTextSize(60);
        mPaint.setTypeface(font);
        mBound=new Rect();
        mPaint.getTextBounds(mNumberVal,0,mNumberVal.length(),mBound);
        invalidate();
    }

    public int getNumber(){
        return mNumber;
    }
    /**
     * 根据数字的大小变化每个块的背景颜色
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawRoundRect(canvas);
        if (mNumber != 0) {
            drawText(canvas);
        }
    }
    //绘制圆角正方形
    private void drawRoundRect(Canvas canvas){
        String mBgColor;
        switch (mNumber) {
            case 0:
                mBgColor = "#CCC0B3";
                break;
            case 2:
                mBgColor = "#EEE4DA";
                break;
            case 4:
                mBgColor = "#EDE0C8";
                break;
            case 8:
                mBgColor = "#F2B179";// #F2B179
                break;
            case 16:
                mBgColor = "#F49563";
                break;
            case 32:
                mBgColor = "#F5794D";
                break;
            case 64:
                mBgColor = "#F55D37";
                break;
            case 128:
                mBgColor = "#EEE863";
                break;
            case 256:
                mBgColor = "#EDB04D";
                break;
            case 512:
                mBgColor = "#ECB04D";
                break;
            case 1024:
                mBgColor = "#EB9437";
                break;
            case 2048:
                mBgColor = "#EA7821";
                break;
            default:
                //空白的块
                mBgColor = "#EA7821";
                break;
        }
        mPaint.setColor(Color.parseColor(mBgColor));
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);
        canvas.drawRoundRect(0,0,getWidth(),getHeight(),outerRadiusRat,outerRadiusRat,mPaint);
    }
   //绘制文字
    private void drawText(Canvas canvas) {
           mPaint.setColor(Color.BLACK);
           float x = (getWidth()-mBound.width())/2;
           float y = (getHeight()+mBound.height())/2;
           //由于基准线并不是在左上角开始的
           canvas.drawText(mNumberVal,x,y,mPaint);
    }
}
