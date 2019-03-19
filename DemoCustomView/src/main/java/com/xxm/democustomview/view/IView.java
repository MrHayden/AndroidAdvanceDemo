package com.xxm.democustomview.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.xxm.democustomview.R;

/**
 * Created by xxm on 2019/3/18 0018
 */
public class IView extends View {

    private Context mContext;
    private Paint paint;

    private int startAngle = 0;
    private int sweepAngle = 0;
    private int mWidth = 0;
    private int mHeight = 0;

    public IView(Context context) {
        super(context);
        init(context);
    }

    public IView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public IView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
        paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(2.0f);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
    }

    /**
     * 绘制颜色	drawColor, drawRGB, drawARGB	使用单一颜色填充整个画布
     * 绘制基本形状	drawPoint, drawPoints, drawLine, drawLines, drawRect, drawRoundRect, drawOval, drawCircle, drawArc	依次为 点、线、矩形、圆角矩形、椭圆、圆、圆弧
     * 绘制图片	drawBitmap, drawPicture	绘制位图和图片
     * 绘制文本	drawText, drawPosText, drawTextOnPath	依次为 绘制文字、绘制文字时指定每个文字位置、根据路径绘制文字
     * 绘制路径	drawPath	绘制路径，绘制贝塞尔曲线时也需要用到该函数
     * 顶点操作	drawVertices, drawBitmapMesh	通过对顶点操作可以使图像形变，drawVertices直接对画布作用、 drawBitmapMesh只对绘制的Bitmap作用
     * 画布剪裁	clipPath, clipRect	设置画布的显示区域
     * 画布快照	save, restore, saveLayerXxx, restoreToCount, getSaveCount	依次为 保存当前状态、 回滚到上一次保存的状态、 保存图层状态、 回滚到指定状态、 获取保存次数
     * 画布变换	translate, scale, rotate, skew	依次为 位移、缩放、 旋转、错切
     * Matrix(矩阵)	getMatrix, setMatrix, concat	实际上画布的位移，缩放等操作的都是图像矩阵Matrix， 只不过Matrix比较难以理解和使用，故封装了一些常用的方法。
     *
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int radius = Math.min(mWidth, mHeight) / 4;
        RectF rect = new RectF(-radius, -radius, radius, radius);

        Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.ic_launcher);
        if (bitmap != null)
            canvas.drawBitmap(bitmap, 20, 20, paint);


//        canvas.drawLine(200.0f,200.0f,300.0f,300.0f,paint);

//        canvas.drawRoundRect(rect,20,20,paint);

        canvas.translate(getWidth() / 2, getHeight() / 2);
//        canvas.drawRect(rect,paint);

        for (int i = 0; i < 6; i++) {
            if (i % 2 == 0) {
                paint.setColor(Color.RED);
                sweepAngle = 30;
            } else {
                paint.setColor(Color.BLACK);
                sweepAngle = 30 * 2;
            }
            canvas.drawArc(rect, startAngle, sweepAngle, true, paint);
            startAngle += sweepAngle;
        }

    }
}
