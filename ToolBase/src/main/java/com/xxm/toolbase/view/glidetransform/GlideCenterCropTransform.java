package com.xxm.toolbase.view.glidetransform;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.RectF;
import android.support.annotation.NonNull;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.xxm.toolbase.utils.TransformationUtils;

import java.nio.ByteBuffer;
import java.security.MessageDigest;

/**
 * A Glide {@link BitmapTransformation} to circle crop an image.  Behaves similar to a
 * {@link GlideCenterCropTransform} transform, but the resulting image is masked to a circle.
 * <p>
 * <p> Uses a PorterDuff blend mode, see http://ssp.impulsetrain.com/porterduff.html. </p>
 */
public class GlideCenterCropTransform extends BitmapTransformation {
    // The version of this transformation, incremented to correct an error in a previous version.
    // See #455.
    private static final int VERSION = 1;
    private static final String ID = "lib.frame.utils.GlideCenterCropTransform." + VERSION;
    private static final byte[] ID_BYTES = ID.getBytes(CHARSET);

    private final int borderWidth;
    private final int borderColor; // 边框颜色

    private Paint borderPaint;

    public GlideCenterCropTransform() {
        // Intentionally empty.
        this(0, 0);
    }

    public GlideCenterCropTransform(int borderWidth, int borderColor) {
        borderPaint = new Paint();
        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setAntiAlias(true);
        this.borderWidth = borderWidth;
        this.borderColor = borderColor;
    }

    /**
     * @deprecated Use {@link #GlideCenterCropTransform()}.
     */
    @Deprecated

    public GlideCenterCropTransform(@SuppressWarnings("unused") Context context) {
        this();
    }

    /**
     * @deprecated Use {@link #GlideCenterCropTransform()}
     */
    @Deprecated
    public GlideCenterCropTransform(@SuppressWarnings("unused") BitmapPool bitmapPool) {
        this();
    }

    // Bitmap doesn't implement equals, so == and .equals are equivalent here.
    @SuppressWarnings("PMD.CompareObjectsWithEquals")
    @Override
    protected Bitmap transform(@NonNull BitmapPool pool, @NonNull Bitmap inBitmap, int outWidth, int outHeight) {
        if (borderWidth == 0)
            return TransformationUtils.centerCrop(pool, inBitmap, outWidth, outHeight);
        else {
            int borderWidth = this.borderWidth;
            int width = outWidth - borderWidth * 2;
            int height = outHeight - borderWidth * 2;
            Bitmap toTransform = TransformationUtils.centerCrop(pool, inBitmap, width, height);

            Bitmap result = pool.get(outWidth, outHeight, Bitmap.Config.ARGB_8888);
            TransformationUtils.BITMAP_DRAWABLE_LOCK.lock();
            try {
                Canvas canvas = new Canvas(result);
                canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);

                RectF rectf = new RectF(borderWidth / 2, borderWidth / 2, result.getWidth() - borderWidth / 2, result.getHeight() - borderWidth / 2);
                borderPaint.setColor(borderColor);
                borderPaint.setStrokeWidth(borderWidth);
                canvas.drawRect(rectf, borderPaint);

                canvas.drawBitmap(toTransform, borderWidth, borderWidth, TransformationUtils.DEFAULT_PAINT);
                TransformationUtils.clear(canvas);
            } finally {
                TransformationUtils.BITMAP_DRAWABLE_LOCK.unlock();
            }
            return result;
        }
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof GlideCenterCropTransform && ((GlideCenterCropTransform) o).borderWidth == borderWidth && ((GlideCenterCropTransform) o).borderColor == borderColor;
    }

    @Override
    public int hashCode() {
        return ID.hashCode() + borderWidth + borderColor;
    }

    @Override
    public void updateDiskCacheKey(MessageDigest messageDigest) {
        messageDigest.update(ID_BYTES);
        byte[] roundData = ByteBuffer.allocate(4).putInt(borderWidth).array();
        messageDigest.update(roundData);
        byte[] colorData = ByteBuffer.allocate(4).putInt(borderColor).array();
        messageDigest.update(colorData);
    }
}
