package com.xxm.toolbase.utils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.Random;

/**
 * Created by Administrator on 2019/1/18 0018
 * 随机4位生成图形验证码
 */
public class CreateCodeUtil {

//    private final char[] CHARS = {'1', '2', '3', '4', '5', '6', '7',
//            '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'j', 'k', 'l',
//            'm', 'n', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
//            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
//            'N', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};     //预定随机数库

    private final char[] CHARS = {'1', '2', '3', '4', '5', '6', '7',
            '8', '9'};     //预定随机数库
    private final int CodeLength = 4; // 随机数个数
    private final int LineNumber = 0;     //线条数目
    private final int WIDTH = 160, HEIGHT = 100; // 位图长、宽
    private final int FontSize = 50;     //随机数字体大小
    private int base_padding_left;
    private final int random_padding_left = 30,
            base_padding_top = 55, random_padding_top = 10;
    private Random random = new Random();
    private String randomCode;


    private static CreateCodeUtil mCodeUtils;

    public static CreateCodeUtil getInstance() {
        if (mCodeUtils == null) {
            mCodeUtils = new CreateCodeUtil();
        }
        return mCodeUtils;
    }

    /*********************************************************************************
     *	方  法 名：createRandomBitmap
     *	功能描述：生成随机验证码视图
     *  Data     ：2015-6-6[J]
     *********************************************************************************/
    public Bitmap createRandomBitmap() {
        /**
         * (1)生成一组随机数
         * */
        randomCode = createRandomText();     //生成4个随机数
        /***
         * (2)创建位图Bitmap,画布Canvas,初始化画笔Paint
         * */
        Bitmap bitmap = Bitmap.createBitmap(WIDTH, HEIGHT, Bitmap.Config.ARGB_8888);    //创建位图，并指定其长、宽
        Canvas canvas = new Canvas(bitmap);     //创建指定位图的画布
        canvas.drawColor(Color.WHITE);     //设置画布的背景为白色
        Paint paint = new Paint();     //定义画笔paint
        paint.setTextSize(FontSize);     //设置画笔字体大小
        /**
         * (3)生成四个随机数风格各异(颜色、位置、形状)的位图
         * */
        base_padding_left = 20;
        for (int i = 0; i < randomCode.length(); i++) {
            //设置一个随机数的风格
            int color = RandomColor();
            paint.setColor(color);     //设置(画笔)该随机数的颜色
            paint.setFakeBoldText(false);//设置画笔为非粗体
            float skewX = random.nextInt(11) / 10;
            skewX = random.nextBoolean() ? skewX : (-skewX);
            paint.setTextSkewX(skewX);    //设置字体倾斜方向(负数表示右斜,整数表示左斜)
            //设置一个随机数位置
//	 int padding_left = base_padding_left + random.nextInt(random_padding_left);
            int padding_top = base_padding_top + random.nextInt(random_padding_top);
            //绘制该随机数
            canvas.drawText(randomCode.charAt(i) + "", base_padding_left, padding_top, paint);
            base_padding_left += random_padding_left;
        }
        /**
         * (4)绘制线条
         **/
        for (int i = 0; i < LineNumber; i++) {
            mdrawLine(canvas, paint);
        }
        canvas.save(Canvas.ALL_SAVE_FLAG);    //保存
        canvas.restore();
        return bitmap;
    }

    public String getRandomCode() {
        return randomCode;
    }

    /*********************************************************************************
     *	方  法 名：createRandomText
     *	功能描述：
     *  Data     ：2015-6-6[J]
     *********************************************************************************/
    private String createRandomText() {
        StringBuilder buffer = new StringBuilder();
        for (int i = 0; i < CodeLength; i++) {
            buffer.append(CHARS[random.nextInt(CHARS.length)]);    //CHARS下标限定在0~CodeLength之间
        }
        return buffer.toString();     //生成4个随机数
    }

    /********************************************************************************
     *	方  法 名：RandomColor
     *	功能描述：生成一个随机颜色
     *  Data     ：2015-6-6[J]
     *********************************************************************************/
    private int RandomColor() {
        int red = random.nextInt(256);     //红色：0~256之间
        int green = random.nextInt(256);     //绿色：0~256之间
        int blue = random.nextInt(256);     //蓝色：0~256之间
        return Color.rgb(red, green, blue);    //返回生成随机颜色值
    }

    /*********************************************************************************
     *	方  法 名：mdrawLine
     *	功能描述：绘制一条线条,参数：当前画布，当前画笔
     *  Data     ：2015-6-6[J]
     **********************************************************************************/
    private void mdrawLine(Canvas canvas, Paint paint) {
        //a.设置该线条颜色
        int color = RandomColor();
        paint.setColor(color);
        //b.设置该随机数的位置(起点和终点,0~WIDTH,0~HEIGHT)
        int startX = random.nextInt(WIDTH);
        int startY = random.nextInt(HEIGHT);
        int stopX = random.nextInt(WIDTH);
        int stopY = random.nextInt(HEIGHT);
        canvas.drawLine(startX, startY, stopX, stopY, paint);
    }
}
