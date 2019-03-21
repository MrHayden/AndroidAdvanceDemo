package com.xxm.democustomview.practice;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class Practice2DrawCircleView extends View {

    private Paint paint;

    public Practice2DrawCircleView(Context context) {
        super(context);
    }

    public Practice2DrawCircleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Practice2DrawCircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

//        练习内容：使用 canvas.drawCircle() 方法画圆
//        一共四个圆：1.实心圆 2.空心圆 3.蓝色实心圆 4.线宽为 20 的空心圆


        //开启抗锯齿
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.BLACK);

        //实心圆
        paint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(getWidth() / 2 - 200, 200, 150, paint);

        //空心圆
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawCircle(getWidth() / 2 + 200, 200, 150, paint);

        //蓝色实心圆
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.BLUE);
        canvas.drawCircle(getWidth() / 2 - 200, 550, 150, paint);

        //线宽为 20 的空心圆
        paint.setStrokeWidth(20f);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawCircle(getWidth() / 2 + 200, 550, 150, paint);
    }
}
