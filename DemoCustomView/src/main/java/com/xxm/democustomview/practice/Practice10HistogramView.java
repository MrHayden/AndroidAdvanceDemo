package com.xxm.democustomview.practice;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class Practice10HistogramView extends View {

    //矩形间距
    private int padding = 20;
    //矩形宽度
    private int rectWidth = 80;

    private String[] names = {"Froyo", "GB", "IC S", "JB", "KitKat", "L", "M"};
    private int[] heights = {0, 20, 20, 100, 150, 210, 100};

    private Paint paint;

    public Practice10HistogramView(Context context) {
        super(context);
    }

    public Practice10HistogramView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Practice10HistogramView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

//        综合练习
//        练习内容：使用各种 Canvas.drawXXX() 方法画直方图

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.WHITE);
        paint.setStrokeWidth(3f);
        paint.setStyle(Paint.Style.STROKE);

        int nameLength = names.length;

        //线的长度和高度
        int lineH = 400;
        int lineW = nameLength * (padding + rectWidth) + padding;

        //移动中心原点到白线坐标原点
        canvas.translate((getWidth() - lineW) / 2, getHeight() - (getHeight() - lineH) / 2);

        //绘制白线坐标
        canvas.drawLine(0, 0, 0, -lineH, paint);
        canvas.drawLine(0, 0, lineW, 0, paint);

        //绘制直方条
        float startX = padding;
        float startY = 0;
        paint.setColor(Color.GREEN);
        paint.setStyle(Paint.Style.FILL);
        for (int i = 0; i < nameLength; i++) {
            canvas.drawRect(startX, startY, startX + rectWidth, -heights[i], paint);
            startX += (rectWidth + padding);
        }

        //绘制直方条对应文字说明
        paint.setColor(Color.WHITE);
        paint.setStrokeWidth(3f);
        paint.setTextSize(20);
        int textLeft = padding;
        for (int i = 0; i < nameLength; i++) {
            float startLeft = textLeft + (rectWidth - getTextBounds(names[i]).width()) / 2;
            canvas.drawText(names[i], startLeft, getTextBounds(names[i]).height() + 10, paint);
            textLeft += (padding + rectWidth);
        }

    }

    /**
     * 获取文字的长宽
     *
     * @param text
     * @return
     */
    private Rect getTextBounds(String text) {
        Rect rect = new Rect();
        paint.getTextBounds(text, 0, text.length(), rect);
        return rect;
    }
}
