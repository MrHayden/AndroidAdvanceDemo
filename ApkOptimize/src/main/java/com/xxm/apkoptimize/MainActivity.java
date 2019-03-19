package com.xxm.apkoptimize;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

/**
 * apk优化
 * 1.SVG
 *2.tint着色器
 * 3.strings.xml资源配置
 * 4.so的配置
 * 5.移除无用resource资源（注意反射不能移除）
 * 6.代码混淆
 * 7.资源压缩(没有keep，所有未用资源一律压缩，不用物理删除)
 * 8.webp
 * 9.压缩对齐、res资源混淆（APK包）
 *
 */
public class MainActivity extends AppCompatActivity {

    private ImageView ivLocalSVG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        VectorDrawableCompat a = VectorDrawableCompat.create(getResources(), R.drawable.ic_local_back, getTheme());
        a.setTint(Color.RED); //设置单一的颜色
        a.setTintList(ColorStateList.valueOf(Color.RED));//设置状态性的，比如点击一个颜色，未点击一个颜色
        ivLocalSVG = findViewById(R.id.iv_local_svg);
        ivLocalSVG.setImageDrawable(a);
    }
}
