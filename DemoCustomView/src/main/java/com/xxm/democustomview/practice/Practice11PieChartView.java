package com.xxm.democustomview.practice;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class Practice11PieChartView extends View {
    private static final int RADIUS = 200;// 半径
    private static final int LINE_LENGTH = 50;// 线的长度
    private static final int OFFSET = 20;// 扇形偏移距离
    private static final int OFFSET_INDEX = 2;// 偏移的扇形
    private Paint arcPaint, linePaint, textPaint;
    private RectF rectF;
    private String[] names = {"a", "bb", "ccc", "dddd", "eeeee"};
    private int[] percents = {10, 20, 30, 25, 15};
    private int[] colors = {Color.WHITE, Color.YELLOW, Color.BLUE, Color.GRAY, Color.GREEN};

    public Practice11PieChartView(Context context) {
        this(context, null);
    }

    public Practice11PieChartView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Practice11PieChartView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        arcPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        arcPaint.setStyle(Paint.Style.FILL);

        linePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        linePaint.setStrokeWidth(3);
        linePaint.setColor(Color.WHITE);

        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setTextSize(30);
        textPaint.setColor(Color.WHITE);

        rectF = new RectF(-RADIUS, -RADIUS, RADIUS, RADIUS);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

//        综合练习
//        练习内容：使用各种 Canvas.drawXXX() 方法画饼图

        float startAngle = 0;

        canvas.translate(getWidth() / 2, getHeight() / 2);// 画布移到中心

        for (int i = 0; i < percents.length; i++) {
            // 扇形的角度
            float sweepAngle = 360 * percents[i] / 100;
            // 扇形的中心线相对于坐标系的角度(0~2π)
            double theta = (startAngle + sweepAngle / 2) * Math.PI / 180;

            if (i == OFFSET_INDEX) {// 偏移的扇形
                canvas.save();
                // 计算该扇形的圆心偏移坐标，并移动画布到圆心
                canvas.translate((float) (OFFSET * Math.cos(theta)), (float) (OFFSET * Math.sin(theta)));
                drawContent(canvas, startAngle, sweepAngle, theta, i);
                canvas.restore();
            } else {
                drawContent(canvas, startAngle, sweepAngle, theta, i);
            }

            startAngle += sweepAngle;
        }

    }

    /**
     * 画每个扇形的内容：扇形、线、文字
     *
     * @param canvas
     * @param startAngle
     * @param sweepAngle
     * @param theta
     * @param i
     */
    private void drawContent(Canvas canvas, float startAngle, float sweepAngle, double theta, int i) {
        Log.d("test", "i = " + i + ", theta = " + theta);

        // 画扇形
        arcPaint.setColor(colors[i]);
        canvas.drawArc(rectF, startAngle, sweepAngle, true, arcPaint);

        // 画斜线
        float lineStartX = (float) (RADIUS * Math.cos(theta));
        float lineStartY = (float) (RADIUS * Math.sin(theta));
        float lineStopX = (float) ((RADIUS + LINE_LENGTH) * Math.cos(theta));
        float lineStopY = (float) ((RADIUS + LINE_LENGTH) * Math.sin(theta));
        canvas.drawLine(lineStartX, lineStartY, lineStopX, lineStopY, linePaint);

        // 画横线和文字
        float lineEndX;
        Rect r = getTextBounds(names[i], textPaint);
        if (theta > Math.PI / 2 && theta <= Math.PI * 3 / 2) {// 左半边，往左画横线
            lineEndX = lineStopX - LINE_LENGTH;
            // 画线
            canvas.drawLine(lineStopX, lineStopY, lineEndX, lineStopY, linePaint);

            // 画文字
            canvas.drawText(names[i], lineEndX - r.width() - 10, lineStopY + r.height() / 2, textPaint);
        } else {// 往右画横线
            lineEndX = lineStopX + LINE_LENGTH;
            // 画线
            canvas.drawLine(lineStopX, lineStopY, lineEndX, lineStopY, linePaint);

            // 文字
            canvas.drawText(names[i], lineEndX + 10, lineStopY + r.height() / 2, textPaint);
        }
    }

    /**
     * 测量文字大小
     *
     * @param text
     * @param paint
     * @return
     */
    private Rect getTextBounds(String text, Paint paint) {
        Rect rect = new Rect();
        paint.getTextBounds(text, 0, text.length(), rect);
        return rect;
    }
}