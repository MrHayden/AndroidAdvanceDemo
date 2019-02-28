package com.xxm.toolbase.view.glidetransform;

/**
 * Copyright (C) 2017 Wasabeef
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Shader;
import android.support.annotation.NonNull;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.util.Preconditions;
import com.xxm.toolbase.utils.TransformationUtils;

import java.nio.ByteBuffer;
import java.security.MessageDigest;

public class GlideRoundConnerTransform extends BitmapTransformation {

    private static final String ID = "lib.frame.utils.GlideRoundConnerTransform";
    private static final byte[] ID_BYTES = ID.getBytes(CHARSET);

    private final int roundingRadius;
    private final int borderWidth;
    private final int borderColor; // 边框颜色


    /**
     * @param roundingRadius the corner radius (in device-specific pixels).
     * @throws IllegalArgumentException if rounding radius is 0 or less.
     */
    public GlideRoundConnerTransform(int roundingRadius, int borderWidth, int borderColor) {
        Preconditions.checkArgument(roundingRadius > 0, "roundingRadius must be greater than 0.");
        this.roundingRadius = roundingRadius;
        this.borderWidth = borderWidth;
        this.borderColor = borderColor;
    }

    /**
     * @param roundingRadius the corner radius (in device-specific pixels).
     * @throws IllegalArgumentException if rounding radius is 0 or less.
     * @deprecated Use {@link #GlideRoundConnerTransform(int, int, int)}
     */
    @Deprecated
    public GlideRoundConnerTransform(@SuppressWarnings("unused") BitmapPool bitmapPool, int roundingRadius) {
        this(roundingRadius, 0, 0);
    }

    /**
     * @param roundingRadius the corner radius (in device-specific pixels).
     * @throws IllegalArgumentException if rounding radius is 0 or less.
     * @deprecated Use {@link #GlideRoundConnerTransform(int, int, int)}
     */
    @Deprecated
    public GlideRoundConnerTransform(@SuppressWarnings("unused") Context context, int roundingRadius) {
        this(roundingRadius, 0, 0);
    }

    @Override
    protected Bitmap transform(@NonNull BitmapPool pool, @NonNull Bitmap inBitmap, int outWidth, int outHeight) {

        int width = outWidth - borderWidth;
        int height = outHeight - borderWidth;
        int roundingRadius = this.roundingRadius;
        Preconditions.checkArgument(width > 0, "width must be greater than 0.");
        Preconditions.checkArgument(height > 0, "height must be greater than 0.");
        Preconditions.checkArgument(roundingRadius > 0, "roundingRadius must be greater than 0.");

        Bitmap toTransform = TransformationUtils.centerCrop(pool, inBitmap, width, height);
        toTransform = TransformationUtils.getAlphaSafeBitmap(pool, toTransform);

        Bitmap result = pool.get(outWidth, outHeight, Bitmap.Config.ARGB_8888);

        result.setHasAlpha(true);

        Paint paint = new Paint();
        paint.setAntiAlias(true);
        RectF rect = new RectF(borderWidth, borderWidth, result.getWidth() - borderWidth, result.getHeight() - borderWidth);
        TransformationUtils.BITMAP_DRAWABLE_LOCK.lock();
        try {
            Canvas canvas = new Canvas(result);
            canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
            if (borderWidth != 0) {
                RectF rectf = new RectF(0, 0, result.getWidth(), result.getHeight());
                paint.setColor(borderColor);
                canvas.drawRoundRect(rectf, roundingRadius, roundingRadius, paint);
                paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
                canvas.drawRoundRect(rect, roundingRadius - borderWidth, roundingRadius - borderWidth, paint);
                paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OVER));
            }
            BitmapShader shader = new BitmapShader(toTransform, Shader.TileMode.CLAMP,
                    Shader.TileMode.CLAMP);
            paint.setShader(shader);
            canvas.drawRoundRect(rect, roundingRadius - borderWidth, roundingRadius - borderWidth, paint);

            TransformationUtils.clear(canvas);
        } finally {
            TransformationUtils.BITMAP_DRAWABLE_LOCK.unlock();
        }

        if (!toTransform.equals(inBitmap)) {
            pool.put(toTransform);
        }
        return result;
    }

    @Override
    public boolean equals(Object o) {
        return (o instanceof GlideRoundConnerTransform)
                && ((GlideRoundConnerTransform) o).roundingRadius == roundingRadius
                && ((GlideRoundConnerTransform) o).borderColor == borderColor
                && ((GlideRoundConnerTransform) o).borderWidth == borderWidth;
    }

    @Override
    public int hashCode() {
        return ID.hashCode() + roundingRadius + borderWidth + borderColor;
    }

    @Override
    public void updateDiskCacheKey(MessageDigest messageDigest) {
        messageDigest.update(ID_BYTES);

        byte[] radiusData = ByteBuffer.allocate(4).putInt(roundingRadius).array();
        messageDigest.update(radiusData);

        byte[] widthData = ByteBuffer.allocate(4).putInt(borderWidth).array();
        messageDigest.update(widthData);

        byte[] colorData = ByteBuffer.allocate(4).putInt(borderColor).array();
        messageDigest.update(colorData);
    }
}