package com.xxm.democustomview.practice;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;

public class Practice9DrawPathView extends View {

    public Practice9DrawPathView(Context context) {
        super(context);
    }

    public Practice9DrawPathView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Practice9DrawPathView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

//        练习内容：使用 canvas.drawPath() 方法画心形


        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.BLACK);
        //实心心形
        paint.setStyle(Paint.Style.FILL);
        //空心心形
//        paint.setStyle(Paint.Style.STROKE);

        Path path = new Path();
        path.addArc(getWidth() / 2 - 200, getHeight() / 2 - 100, getWidth() / 2, getHeight() / 2 + 100, 135, 225);
        path.arcTo(getWidth() / 2, getHeight() / 2 - 100, getWidth() / 2 + 200, getHeight() / 2 + 100, 180, 225, false);
        path.lineTo(getWidth() / 2, getHeight() / 2 + 225);
        //空心心形
//        path.close();
        canvas.drawPath(path, paint);
    }
}
